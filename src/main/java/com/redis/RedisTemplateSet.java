package com.redis;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author zhangguilin
 * @date 1/24/2018
 */
public class RedisTemplateSet {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("application-redis.xml");
        final RedisTemplate<String,Object> redisTemplate = applicationContext.getBean("redisTemplate",RedisTemplate.class);

        /**
         * 无序集合中添加元素,返回添加个数
         * 可以直接在add里面添加多个值
         * */
        redisTemplate.opsForSet().add("redisSet","AAA","BBB","EEE","FFF","GGG");
        redisTemplate.opsForSet().add("redisSetA","BBB","FFF","GGG","WWW","ZZZ");
        redisTemplate.opsForSet().add("redisSetB","BBB","XXX","ZZZ");
        System.out.println(redisTemplate.opsForSet().members("redisSet"));

        /**
         * 移除集合中一个或多个成员
         * */
        String[] strings  = new String[]{"AAA","CCC"};
        System.out.println(redisTemplate.opsForSet().remove("redisSet",strings));

        /**
         * 移除并返回集合中的一个随机元素
         * */
        System.out.println(redisTemplate.opsForSet().pop("redisSet"));
        System.out.println(redisTemplate.opsForSet().members("redisSet"));

        /**
         * 将member元素从source集合移动到destination集合
         * */
        redisTemplate.opsForSet().move("redisSet","FFF","newRedisSet");
        System.out.println(redisTemplate.opsForSet().members("redisSet"));
        System.out.println(redisTemplate.opsForSet().members("newRedisSet"));

        /**
         * 无序集合的大小长度
         * */
        System.out.println(redisTemplate.opsForSet().size("redisSet"));

        /**
         * 判读member元素是否是集合key的成员
         * */
        System.out.println(redisTemplate.opsForSet().isMember("redisSet","EEE"));
        System.out.println(redisTemplate.opsForSet().isMember("redisSet","ABC"));

        /**
         * key对应的无序集合与otherKey对应的无序集合求交集
         * */
        System.out.println(redisTemplate.opsForSet().members("redisSet"));
        System.out.println(redisTemplate.opsForSet().members("newRedisSet"));
        System.out.println(redisTemplate.opsForSet().intersect("redisSet","newRedisSet"));

        /**
         * key对应的无序集合与多个otherKey对应的无序集合求交集
         * */
        System.out.println(redisTemplate.opsForSet().members("redisSet"));
        System.out.println(redisTemplate.opsForSet().members("redisSetA"));
        System.out.println(redisTemplate.opsForSet().members("redisSetB"));
        List<String> strs = new ArrayList<String>();
        strs.add("redisSet");
        strs.add("redisSetB");
        System.out.println(redisTemplate.opsForSet().intersect("redisSetA",strs));

        /**
         * key无序集合与otherKey无序集合的交集存储到destKey无序集合中
         * */
        System.out.println(redisTemplate.opsForSet().intersectAndStore("redisSetA","redisSetB","newRedisSet"));
        System.out.println(redisTemplate.opsForSet().members("newRedisSet"));

        /**
         * key无序集合与otherKey无序集合的并集
         * */
        System.out.println(redisTemplate.opsForSet().union("redisSetA","redisSetB"));

        /**
         * key无序集合与多个otherKey无序集合的并集
         * */
        List<String> stringList = new ArrayList<String>();
        stringList.add("redisSetA");
        stringList.add("redisSetB");
        System.out.println(redisTemplate.opsForSet().union("newRedisSet",stringList));

        /**
         * key无序集合与otherkey无序集合的并集存储到destKey无序集合中
         * */
        System.out.println(redisTemplate.opsForSet().unionAndStore("redisSetA","redisSetB","redisSetC"));
        System.out.println(redisTemplate.opsForSet().members("redisSetC"));

        /**
         * key无序集合与多个otherkey无序集合的并集存储到destKey无序集合中
         * */
        System.out.println(redisTemplate.opsForSet().unionAndStore("redisSet",stringList,"redisSetD"));
        System.out.println(redisTemplate.opsForSet().members("redisSetD"));

        /**
         * key无序集合与otherKey无序集合的差集
         * */
        System.out.println(redisTemplate.opsForSet().difference("redisSetA","redisSetB"));

        /**
         * key无序集合与多个otherKey无序集合的差集
         * */
        System.out.println(redisTemplate.opsForSet().difference("redisSetC",stringList));

        /**
         * key无序集合与otherKey无序集合的差集存储到destKey无序集合中
         * */
        System.out.println(redisTemplate.opsForSet().differenceAndStore("redisSetA","redisSetB","redisSetD"));
        System.out.println(redisTemplate.opsForSet().members("redisSetD"));

        /**
         * key无序集合与多个otherKey无序集合的差集存储到destKey无序集合中
         * */
        System.out.println(redisTemplate.opsForSet().differenceAndStore("redisSetC",stringList,"redisSetE"));

        /**
         * 返回集合中的所有成员
         * */
        System.out.println(redisTemplate.opsForSet().members("redisSetE"));

        /**
         * 随机获取key无序集合中的一个元素
         * */
        System.out.println(redisTemplate.opsForSet().members("redisSetA"));
        System.out.println(redisTemplate.opsForSet().randomMember("redisSetA"));

        /**
         * 获取多个key无序集合中的元素,count表示个数
         * */
        System.out.println(redisTemplate.opsForSet().randomMembers("redisSetA",3));

        /**
         * 获取多个key无序集合中的元素（去重）,count表示个数
         * */
        System.out.println(redisTemplate.opsForSet().distinctRandomMembers("redisSetA",8));

        /**
         * 遍历Set
         * */
        Cursor<Object> cursor = redisTemplate.opsForSet().scan("redisSetA", ScanOptions.NONE);
        while(cursor.hasNext()){
            System.out.println(cursor.next());
        }




    }
}
