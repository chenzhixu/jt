package com.jt.aop;

import com.jt.annotation.CacheFind;
import com.jt.util.ObjectMapperUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.ShardedJedis;

import java.util.Arrays;

@Component //将对象交给spring容器管理
@Aspect  //标识我是一个切面
public class CacheAOP {




    //.注入缓存redis对象，由spring为其赋值
    @Autowired
    private JedisCluster jedis; //注入redis集群配置
//    private ShardedJedis jedis;  //注入redis分片机制   优点：性能更高，内存更大
//   private Jedis jedis; //注入单台redis


    /**
     * 拦截@CacheFind注解标识的方法.
     * 通知选择: 缓存的实现应该选用环绕通知
     * 步骤:
     *  1.动态生成key  用户填写的key+用户提交的参数
     */
    @Around("@annotation(cacheFind)")
    public Object around(ProceedingJoinPoint joinPoint, CacheFind cacheFind){
        //1.如何获取用户在注解中填写的内容呢???  如何获取注解对象....
        String key=cacheFind.key();   //前缀  ITEM_CAT_PARENTID
        System.out.println(key);  //ITEM_CAT_PARENTID
        //2.如何获取目标对象的参数呢???
        Object[] array=joinPoint.getArgs(); //后缀 [0]
        String string=Arrays.toString(array);
        key+=string; //  "ITEM_CAT_PARENTID::[0]"
        System.out.println(key);
        //3.从redis中获取数据
        Object result=null;
        if(jedis.exists(key)){
        //需要获取json数据之后，直接转化为对象返回即可
           String json=jedis.get(key);
           MethodSignature methodSignature= (MethodSignature) joinPoint.getSignature(); //父转子 ，需要强转， ---向下造型
            Class targetClass=methodSignature.getReturnType();
            result=ObjectMapperUtil.toObject(json,targetClass);
            System.out.println("AOP实现缓存的查询，从redis缓存中获取数据");

        }else{
            //key不存在,应该查询数据库
            try {
                result=joinPoint.proceed();  //执行目标方法,获取返回值结果
                //将对象转换为json
                String json=ObjectMapperUtil.toJSON(result);
                if(cacheFind.seconds()>0){//判断是否需要超时时间
                    jedis.setex(key,cacheFind.seconds(),json);
                }else{
                    jedis.set(key,json);
                }
                System.out.println("AOP执行数据库操作....!!!`~~~将查询到的数据放入redis缓存中");
            } catch (Throwable throwable) {
                throwable.printStackTrace();
                //将检查异常转换为运行时异常
                throw new RuntimeException(throwable);
            }
        }
        return result;//返回目标方法执行结果
    }


    /**
     * 要求: 拦截注解方法
     * 打印:
     *      1.打印目标对象的类型
     *      2.打印方法的参数
     *      3.获取目标对象的名称及方法的名称
     * @param joinPoint
     */
    @Before("@annotation(com.jt.annotation.CacheFind)")
    public void doBefore(JoinPoint joinPoint){
      Object target=  joinPoint.getTarget(); //获取目标对象
       Object[]  args=joinPoint.getArgs(); //获取方法的参数
        Signature signature=joinPoint.getSignature();
       String targetName =signature.getDeclaringTypeName(); //获取目标对象的名字
        //获取目标对象的类型
        Class targetClassType=joinPoint.getSignature().getDeclaringType();
        //获取目标方法的名称
         String methodName=joinPoint.getSignature().getName();
        System.out.println("target Object："+target);
        System.out.println("method args:"+args);
        System.out.println("targetName:"+targetName);
        System.out.println("targetClassType:"+targetClassType);
        System.out.println("methodName："+methodName);
    }


    /**如下代码复习AOP的知识，做了一个AOP快速入门案例
     * AOP = 切入点表达式 + 通知方法.
     * 拦截需求：
     *   1.要求拦截itemCatServiceImpl的bean
     *    2.拦截com.jt.service下的所有的类
     *   3.拦截com.jt.service下的所有类及方法
     *   3.1拦截com.jt.service的所有的类.返回值为int类型的.并且add开头的方法名.并且参数一个 为String类型
     *
     */
    //@Pointcut(value = "bean(itemCatServiceImpl)") //指定一个itemCatServiceImpl类中所有方法
    //@Pointcut("within(com.jt.service..*)")   //指定当前目录以及子目录中类的所有方法。
    //@Pointcut("execution(* com.jt.service..*.*(..))")     //语法：execution(返回值类型 包名.类名.方法名(参数列表))。
    //拦截com.jt.service下的所有类的所有方法的任意参数类型   @Pointcut("execution(* com.jt.service..*.*(..))")
    //@Pointcut("execution(int com.jt.service..*.add*(String))")
    public void pointCut(){}  ///此方法内部不需要写具体实现(方法的方法名也是任意) 只是做个标识作用


    //定义前置通知
   // @Before("pointCut()")
  /*  public void before(){
        System.out.println("我是前置通知.....");
    }*/




}
