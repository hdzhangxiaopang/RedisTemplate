package com.redis;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by zhangguilin on 1/24/2018.
 */
public class RedisTemplateString {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("application-redis.xml");
        final StringRedisTemplate stringRedisTemplate = applicationContext.getBean("stringRedisTemplate", StringRedisTemplate.class);

        /**
         * 存取String值
         * */
        stringRedisTemplate.opsForValue().set("name","zhangsan");
        System.out.println(stringRedisTemplate.opsForValue().get("name"));

        /**
         * 设置十秒失效，十秒内查询有效，十秒之后查询结果为null
         * */
        stringRedisTemplate.opsForValue().set("A","AAA",10,TimeUnit.SECONDS);
        System.out.println(stringRedisTemplate.opsForValue().get("A"));

        /**
         * 该方法是用value参数覆盖（overwrite）给定key所存储的字符串值，从偏移量offset开始
         * */
        stringRedisTemplate.opsForValue().set("B","hello world");
        stringRedisTemplate.opsForValue().set("B","redis",6);
        System.out.println(stringRedisTemplate.opsForValue().get("B"));

        /**
         * key值存在返回false保存不了,key不存在返回true保存成功
         * */
        stringRedisTemplate.opsForValue().set("spring","boot");
        System.out.println(stringRedisTemplate.opsForValue().setIfAbsent("spring","cloud")); //false
        System.out.println(stringRedisTemplate.opsForValue().get("spring")); // boot
        System.out.println(stringRedisTemplate.opsForValue().setIfAbsent("mybatis","Hibernate")); //true
        System.out.println(stringRedisTemplate.opsForValue().get("mybatis")); // Hibernate


        Map<String,String> mapA = new HashMap<>();
        mapA.put("100","A");
        mapA.put("101","B");
        mapA.put("102","C");
        /**
         * 为多个键设置他们的值
         * */
        stringRedisTemplate.opsForValue().multiSet(mapA);

        List<String> listA = new ArrayList<>();
        listA.add("100");
        listA.add("101");
        listA.add("102");
        /**
         * 获取多个键值
         * */
        System.out.println(stringRedisTemplate.opsForValue().multiGet(listA));

        /**
         * 为多个键分别设置他们的值，如果存在则返回false，不存在返回true
         * */
        Map<String,String> mapB = new HashMap<>();
        mapB.put("100","A");
        mapB.put("101","B");
        mapB.put("102","C");

        Map<String,String> mapC = new HashMap<>();
        mapC.put("1001","A");
        mapC.put("1002","B");
        mapC.put("1003","C");

        System.out.println(stringRedisTemplate.opsForValue().multiSetIfAbsent(mapB));
        System.out.println(stringRedisTemplate.opsForValue().multiSetIfAbsent(mapC));

        /**
         * 设置键的字符串值并返回其旧值
         * */
        stringRedisTemplate.opsForValue().set("D","DDD");
        System.out.println(stringRedisTemplate.opsForValue().getAndSet("D","DDDDD"));

        /**
         * 支持整数
         * */
        System.out.println(stringRedisTemplate.opsForValue().increment("increlong",1));
        System.out.println(stringRedisTemplate.opsForValue().get("increlong"));

        /**
         * 支持浮点数
         * */
        System.out.println(stringRedisTemplate.opsForValue().increment("incredouble",1.5));
        System.out.println(stringRedisTemplate.opsForValue().get("incredouble"));

        /**
         * 如果key已经存在并且是一个字符串，则该命令将该值追加到字符串的末尾。如果键不存在，
         * 则它被创建并设置为空字符串，因此Append在此种情况下类似于Set.
         * */
        System.out.println(stringRedisTemplate.opsForValue().append("E","EEE"));
        System.out.println(stringRedisTemplate.opsForValue().get("E"));
        System.out.println(stringRedisTemplate.opsForValue().append("E","EEEEE"));

        /**
         * 获取key所对应的value字符串
         * */
        stringRedisTemplate.opsForValue().set("java","SpringCloud");
        System.out.println(stringRedisTemplate.opsForValue().get("java",0,5)); //Spring
        System.out.println(stringRedisTemplate.opsForValue().get("java",0,-1));//SpringCloud
        System.out.println(stringRedisTemplate.opsForValue().get("java",-3,-1));//oud

        /**
         * 返回key所对应的value值的长度
         * */
        stringRedisTemplate.opsForValue().set("java","SpringCloud");
        System.out.println(stringRedisTemplate.opsForValue().size("java"));

        /**
         * 对key所存储的字符串值，设置或消除指定偏移量上的位（bit）
         * key键对应的值value对应的ascii码，在offset的位置（从左向右数）变value
         * 因为二进制只有0和1，在setbit中true为1，false为0.
         * */
        stringRedisTemplate.opsForValue().set("bitValue","a");
        // 'a'的ASCII码是 97，转换为二进制是：01100001
        // 'b'的ASCII码是 98，转换为二进制是：01100010
        // 'c'的ASCII码是 99，转换为二进制是：01100011
        /**
         * 如果将'a'变为'b'则需要将第六位变为1，第七位变为0
         * */
        stringRedisTemplate.opsForValue().setBit("bitValue",6,true);
        stringRedisTemplate.opsForValue().setBit("bitValue",7,false);
        System.out.println(stringRedisTemplate.opsForValue().get("bitValue"));

        /**
         * 获取键值对应的ASCII码在offset处的值
         * */
        System.out.println(stringRedisTemplate.opsForValue().getBit("bitValue",6));


    }
}
