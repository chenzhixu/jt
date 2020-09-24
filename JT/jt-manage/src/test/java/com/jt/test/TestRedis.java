package com.jt.test;


import com.sun.org.apache.xalan.internal.xsltc.compiler.util.StringStack;
import org.junit.jupiter.api.Test;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.*;
import redis.clients.jedis.params.SetParams;

import java.util.*;

//@SpringBootTest //如果需要在测试类中引入spring容器机制才使用该注解
public class TestRedis {


    /**
     * 测试远程redis服务器是否可用
     * host: 192.168.126.129
     * port: 6379
     * 思路:
     *      1.实例化链接对象
     *      2.利用对象执行redis命令
     *
     * 报错调试:
     *      1.检查Redis.conf的配置文件是否按照要求修改 ip/保护/后台
     *      2.redis启动方式      redis-server redis.conf
     *      3.关闭防火墙         systemctl  stop firewalld.service
     * */
    @Test
    public void test01(){
        Jedis jedis=new Jedis("192.168.126.129");
        jedis.set("redis","测试redis是否可用");
        String string=jedis.get("redis");
        System.out.println(string);
    }


    /**
     * String类型API学习
     * 需求: 判断key是否存在于Redis.如果存在则不赋值,否则入库.
     */
    @Test
    public void test02(){
        Jedis jedis=new Jedis("192.168.126.129",6379);
        if(jedis.exists("redis")){
            System.out.println("数据已存在");
        }else{
            jedis.set("redis","aaaa");
        }
        String string=jedis.get("redis");
        System.out.println(string);
    }

    //redis.set操作,后面的操作会将之前的value覆盖
    //可以利用优化的API实现业务功能.
    //业务: 如果数据存在则不允许赋值
    @Test
    public void test03(){
        Jedis jedis = new Jedis("192.168.126.129", 6379);
        jedis.flushAll();   //清空redis服务器缓存
        //如果key存在则不执行赋值操作.
        jedis.setnx("redis", "测试赋值操作!!!");
        jedis.setnx("redis", "测试赋值操作~~~~2!!!");
        String string=jedis.get("redis");
        System.out.println(string);
    }




    /**
     * 测试添加超时时间的有效性.  保证原子性操作
     * 业务: 向redis中保存一个数据之后,要求设定10秒有效.
     * 原子性: 要么同时成功,要么同时失败.
     *
     * 小结: setnx 如果key存在则不赋值
     *      setex  保证原子性操作,并且添加超时时间.
     */
    @Test
    public void test04(){
        Jedis jedis=new Jedis("192.168.126.129",6379);
       /* jedis.set("aa","aa");
        int a=1/0;//如果出现异常，该数据将永不删除.
        jedis.expire("aa",10);*/
       jedis.setex("aa",10, "aa");
       // jedis.psetex(,); // 设置毫秒
        String string=jedis.get("aa");//单位秒
        System.out.println(string);
    }


    /**
     * 需求: 要求添加一个数据,只有数据存在时才会赋值,并且需要添加超时时间
     *      保证原子性操作.
     *  private static final String XX = "xx";  有key的时候才赋值
     *  private static final String NX = "nx";  没有key时才赋值
     *  private static final String PX = "px";  毫秒
     *  private static final String EX = "ex";  秒
     *  redis分布式锁的问题
     * */
    @Test
    public void test05(){
        Jedis jedis=new Jedis("192.168.126.129",6379);
        SetParams setParams=new SetParams();
        setParams.xx().ex(10);
        jedis.set("aaa","ccc",setParams);
        String string=jedis.get("aaa");
        System.out.println(string);
    }



    /*测试hash数据类型*/
    @Test
    public void testHash(){
        Jedis jedis=new Jedis("192.168.126.129",6379);
        jedis.hset("person","name","tomcat猫");
        jedis.hset("person","age","22");
        Map<String,String> map=jedis.hgetAll("person");
        System.out.println(map);


    }




    /*测试hash数据类型*/

    @Test
    public void testList(){
        Jedis jedis=new Jedis("192.168.126.129",6379);
        jedis.lpush("list2","1,2,3,4,5");
        String string=jedis.rpop("list2");
        System.out.println(string);
    }
    /**
     * 控制redis事务控制
     * 说明:操作redis适用于事务控制
     *      但是如果是多台redis则不太适用事务.
     * */
    @Test
    public void testTransaction(){
        Jedis jedis=new Jedis("192.168.126.129",6379);
        //开启事务
        Transaction transaction=jedis.multi();
        try {
            transaction.set("aa","aa");
            transaction.exec();//提交事务
        }catch(Exception e){
            transaction.discard(); //回滚事务
        }
        String string=jedis.rpop("list2");
        System.out.println(string);
    }


    /**
     * 测试Redis分片机制快速入门
     * 业务思路:
     *      用户需要通过API来操作3台redis.用户无需关心数据如何存储,
     *      只需要了解数据能否存储即可.
     * 思考: CGB2005的数据存储到哪台redis中？
     *      redis分片是如何实现数据存储的!
     */
    @Test
    public void testShards(){
        List<JedisShardInfo> list=new ArrayList<>();
        JedisShardInfo jedisShardInfo=new JedisShardInfo("192.168.126.129",6379);
        JedisShardInfo jedisShardInfo2=new JedisShardInfo("192.168.126.129",6380);
        JedisShardInfo jedisShardInfo3=new JedisShardInfo("192.168.126.129",6381);
        list.add(jedisShardInfo);
        list.add(jedisShardInfo2);
        list.add(jedisShardInfo3);
        ShardedJedis shardedJedis=new ShardedJedis(list);
        shardedJedis.set("CGB2005","2020/09/11 !!!redis分片学习");
        String string=shardedJedis.get("CGB2005");
        System.out.println(string);
    }


    /**
     * 哨兵的测试
     * 参数说明:  masterName: 主机的变量名称
     *           sentinels:   链接哨兵的集合.
     * 理解: 哨兵虽然链接3台redis. 但是3台redis中存储的数据都是一样的.对于用户而言
     * 就是一台.
     */
    @Test
    public void testSentinel(){
        Set<String> sets=new HashSet<>();
        sets.add("192.168.126.129:26379");
        JedisSentinelPool sentinelPool=new JedisSentinelPool("mymaster",sets);
        Jedis jedis=sentinelPool.getResource();
        jedis.set("sentinel","测试哨兵...");
        String  string=jedis.get("sentinel");
        System.out.println(string);
        jedis.close();


    }

    @Test
    public void testCluster(){
        Set<HostAndPort> sets=new HashSet<>();
        sets.add(new HostAndPort("192.168.126.129",7000));
        sets.add(new HostAndPort("192.168.126.129",7001));
        sets.add(new HostAndPort("192.168.126.129",7002));
        sets.add(new HostAndPort("192.168.126.129",7003));
        sets.add(new HostAndPort("192.168.126.129",7004));
        sets.add(new HostAndPort("192.168.126.129",7005));
        JedisCluster jedisCluster=new JedisCluster(sets);
        //利用程序操作redis集群
        jedisCluster.set("cluster","redis集群测试");
        String string=jedisCluster.get("cluster");
        System.out.println(string);
    }



}
