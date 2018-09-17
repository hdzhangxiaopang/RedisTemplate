package com.redis;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author zhangguilin
 * @date 1/24/2018
 */
public class RedisTemplateHash {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("application-redis.xml");
        final RedisTemplate<String,Object> redisTemplate = applicationContext.getBean("redisTemplate",RedisTemplate.class);
        StringRedisTemplate stringRedisTemplate = applicationContext.getBean("stringRedisTemplate", StringRedisTemplate.class);

        /**
         * 设置散列hashKey的值
         * */
        redisTemplate.opsForHash().put("redisHash","AAA",26);
        redisTemplate.opsForHash().put("redisHash","BBB",6);
        /**
         * 从键中的哈希获取给定hashKey的值
         * */
        System.out.println(redisTemplate.opsForHash().get("redisHash","AAA"));
        //结果：26

        Map<String,Object> objectMap = new HashMap<>();
        objectMap.put("name","zhangsan");
        objectMap.put("age",18);
        objectMap.put("grade","1001");
        redisTemplate.opsForHash().putAll("newRedisHash",objectMap);
        System.out.println(redisTemplate.opsForHash().get("newRedisHash","name"));
        // 结果： zhangsan

        /**
         * 删除给定的哈希HashKeys
         * */
        System.out.println(redisTemplate.opsForHash().delete("newRedisHash","grade"));
        System.out.println(redisTemplate.opsForHash().entries("newRedisHash"));

        /**
         * 确定哈希hashKey是否存在
         * */
        System.out.println(redisTemplate.opsForHash().hasKey("redisHash","AAA"));
        System.out.println(redisTemplate.opsForHash().hasKey("redisHash","CCC"));


        /**
         * 从哈希中获取给定hashKey的值
         * */
        List<Object> objects = new ArrayList<>();
        objects.add("name");
        objects.add("age");
        System.out.println(redisTemplate.opsForHash().multiGet("newRedisHash",objects));

        /**
         * 通过给定的delta增加散列hashKey的值（整型）
         * */
        System.out.println(stringRedisTemplate.opsForHash().get("newRedisHash","age"));
        //System.out.println(stringRedisTemplate.opsForHash().increment("newRedisHash","age",1));


        /**
         * 通过给定的delta增加散列hashKey的值（浮点型）
         * */
        System.out.println(redisTemplate.opsForHash().get("newRedisHash","age"));
        //System.out.println(redisTemplate.opsForHash().increment("newRedisHash","age",1.2));

        /**
         * 获取key所对应的散列表的key
         * */
        System.out.println(redisTemplate.opsForHash().keys("newRedisHash"));


        /**
         * 获取key所对应的散列的大小数
         * */
        System.out.println(redisTemplate.opsForHash().size("newRedisHash"));


        /**
         * 使用m中提供的多个散列字段设置到key对应的散列表中
         * */
        Map<String,Object> map = new HashMap<>();
        map.put("name","lisi");
        map.put("age",27);
        map.put("class","1002");
        redisTemplate.opsForHash().putAll("redisHashA",map);
        System.out.println(redisTemplate.opsForHash().entries("redisHashA"));


        /**
         * 仅当hashKey不存在时才设置散列hashKey的值
         * */
        System.out.println(redisTemplate.opsForHash().putIfAbsent("redisHash","AAA",26));
        System.out.println(redisTemplate.opsForHash().putIfAbsent("redisHash","CCC",26));

        /**
         * 获取整个哈希存储的根值根据密钥
         * */
        System.out.println(redisTemplate.opsForHash().values("redisHash"));

        /**
         * 获取整个哈希存储根据密钥
         * */
        System.out.println(redisTemplate.opsForHash().entries("redisHash"));

        /**
         * 使用Cursor在key的hash中迭代,相当于迭代器
         * */
        Cursor<Map.Entry<Object,Object>> cursor = redisTemplate.opsForHash().scan("redisHash", ScanOptions.NONE);
        while(cursor.hasNext()){
            Map.Entry<Object,Object> entry = cursor.next();
            System.out.println(entry.getKey()+":"+entry.getValue());
        }


    }
}
