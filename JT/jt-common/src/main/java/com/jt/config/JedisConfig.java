package com.jt.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import redis.clients.jedis.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Configuration //此注解用于标识我是一个配置类
@PropertySource("classpath:/properties/redis.properties")
public class JedisConfig {








    @Value("${redis.nodes}")
    private  String nodes; //node,node,node


    /**
     * 引入redis集群配置
     */
    @Bean  //实例化集群的对象之后交给Spring容器管理
    public JedisCluster jedisCluster(){
        Set<HostAndPort> set=new HashSet<>();
        String[] nodeArray = nodes.split(",");
        for (String node:nodeArray) {  //host:port
            String[] nodeTemp = node.split(":");
            String host = nodeTemp[0];
            int port = Integer.parseInt(nodeTemp[1]);
           HostAndPort hostAndPort=new HostAndPort(host,port);
           set.add(hostAndPort);

        }
        JedisCluster jedisCluster=new JedisCluster(set);
        return jedisCluster;
    }



//    @Value("${redis.nodes}")
//    private  String nodes; //node,node,node

    /**spring整合redis分片机制
     *添加Redis分片的配置
     * 需求： 动态的获取IP地址和端口号
     *    动态的获取多个的节点信息，方便以后扩展
     */
//    @Bean //将ShardedJedis交给spring容器管理
/*    public ShardedJedis shardedJedis(){
        List<JedisShardInfo>  shardsList=new ArrayList<>();
        //1.获取每个节点的信息
        String[] strArr=nodes.split(",");   // [node,node,node]
        //2.遍历所有node.为list集合赋值
        for (String node:strArr) {
         String host=node.split(":")[0];
         int port=Integer.parseInt(node.split(":")[1]);
         JedisShardInfo jedisShardInfo=new JedisShardInfo(host,port);
         shardsList.add(jedisShardInfo);
        }
        ShardedJedis shardedJedis=new ShardedJedis(shardsList);
        return  shardedJedis;
    }*/


    //通过spring容器动态获取YML配置文件中的host即可
/*    @Value("${redis.host}")  // spring el表达式   如果yml和peoperties文件存在相同的属性，以yml文件为准,yml文件优先级最高
    private String host;
    @Value("${redis.port}")
    //通过spring容器动态获取YML配置文件中的端口号即可
    private Integer port;

    /**
     * 将redis对象交给spring容器管理
     * @return
     /*
    @Bean //实例化的redis对象之后，交给spring容器管理      默认条件下是单例对象
     //@Scope("prototype") //设置为多例对象
    public Jedis jedis(){
      //由于将代码写死不利于扩展,所以将固定的配置添加到配置文件中，由配置文件动态为属性赋值
        Jedis jedis=new Jedis(host,port);
        return jedis;
    }*/

}
