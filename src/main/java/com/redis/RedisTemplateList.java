package com.redis;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author zhangguilin
 * @date 1/26/2018
 */
public class RedisTemplateList {
    public static void main(String[] args) {

        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("application-redis.xml");
        final RedisTemplate<String,Object> redisTemplate = applicationContext.getBean("redisTemplate",RedisTemplate.class);

        /**
         * 将所有指定的值插入存储在键的列表的头部。如果键不存在，则在执行推送之前
         * 将其创建为空列表。（从左插入）
         * */
        redisTemplate.opsForList().leftPush("AL","JAVA");
        redisTemplate.opsForList().leftPush("AL","Python");
        redisTemplate.opsForList().leftPush("AL","C++");
        redisTemplate.opsForList().leftPush("AL","C#");

        System.out.println(redisTemplate.opsForList().range("AL",0,-1));

        /**
         * 批量把一个数组插入到列表中
         * */
        String[] stringA =  new String[]{"1","2","3"};
        redisTemplate.opsForList().leftPushAll("redisArrayA",stringA);
        System.out.println(redisTemplate.opsForList().range("redisArrayA",0,-1));//[3,2,1]

        List<Object> listA = new ArrayList<>();
        listA.add("A");
        listA.add("B");
        listA.add("C");
        redisTemplate.opsForList().leftPushAll("redisListA",listA);
        System.out.println(redisTemplate.opsForList().range("redisListA",0,-1)); //[C,B,A]

        /**
         * 只有存在key对应的列表才能将这个value值插入到key所对应列表中
         * */
        System.out.println(redisTemplate.opsForList().leftPushIfPresent("leftPushIfPresent","AAA")); //0
        System.out.println(redisTemplate.opsForList().leftPushIfPresent("leftPushIfPresent","BBB")); //0
        /**
         * 如果将下面的一行注释去掉，后面才能插入值，否则后面的值插不进去
         * */
        //System.out.println(redisTemplate.opsForList().leftPush("leftPushIfPresent","AAA"));
        System.out.println(redisTemplate.opsForList().leftPushIfPresent("leftPushIfPresent","BBB")); //2
        System.out.println(redisTemplate.opsForList().leftPushIfPresent("leftPushIfPresent","CCC")); //3

        /**
         *把value值放到key对应的列表中point的左边，如果point值不存在的话不插入
         * */
        redisTemplate.opsForList().leftPush("AL","BBB","JQuery");
        System.out.println(redisTemplate.opsForList().range("AL",0,-1));

        /**
         * 将所有指定的值插入存储的列表的头部。如果键不存在，则在执行推送之前将其创建为空列表。（从右边插入）
         * */
        redisTemplate.opsForList().rightPush("BL","JAVA");
        redisTemplate.opsForList().rightPush("BL","C");
        redisTemplate.opsForList().rightPush("BL","JS");

        /**
         * 批量把一个数组插入到列表中
         * */
        String[] stringB = new String[]{"1","2","3"};
        redisTemplate.opsForList().rightPushAll("redisArrayB",stringB);
        redisTemplate.opsForList().range("redisArrayB",0,-1);//[1,2,3]

        List<Object> listB = new ArrayList<>();
        listB.add("AAA");
        listB.add("BBB");
        listB.add("CCC");
        redisTemplate.opsForList().rightPushAll("redisListB",listB);
        redisTemplate.opsForList().range("redisListB",0,-1);//[CCC,BBB,AAA]

        /**
         * 只有存在key对应的列表才能将这个value值插入到key多对应列表中
         * 参照：leftPushIfPresent(K key, V value)
         * */
        redisTemplate.opsForList().rightPushIfPresent("rightPushIfPresent","aa");

        /**
         * 把value值放到key对应的列表中point的右边，如果point值存在的话不插入
         * */
        redisTemplate.opsForList().rightPush("AL","JAVA","OC");
        System.out.println(redisTemplate.opsForList().range("AL",0,-1));

        /**
         * 在列表中index的位置设置value值
         * */
        redisTemplate.opsForList().set("AL",1,"Spring");
        System.out.println(redisTemplate.opsForList().range("AL",0,-1));

        /**
         * 从存储在键中的列表中删除等于value元素的第一个计数事件
         * 计数参数以下列方式影响操作：
         * count>0 :删除等于从头到尾移动的值的元素。
         * count<0 :删除等于从尾到头移动的值的元素。
         * count=0 :删除等于value值所有元素。
         * */
        System.out.println(redisTemplate.opsForList().range("AL",0,-1));
        System.out.println(redisTemplate.opsForList().remove("AL",0,"Python"));
        System.out.println(redisTemplate.opsForList().range("AL",0,-1));

        /**
         * 根据下标获取列表中的值，下标是从0开始
         * */
        System.out.println(redisTemplate.opsForList().index("AL",2));

        /**
        * 弹出最左边的元素，弹出之后该值在列表中将不复存在
        * */
        System.out.println(redisTemplate.opsForList().range("AL",0,-1));
        System.out.println(redisTemplate.opsForList().leftPop("AL"));
        System.out.println(redisTemplate.opsForList().range("AL",0,-1));

        /**
         * 移出并获取列表的第一个元素，如果列表没有元素会阻塞列表直到等待超时或发现有可以弹出的元素为止
         * */
        System.out.println(redisTemplate.opsForList().leftPop("AL",60,TimeUnit.SECONDS));

        /**
         * 弹出最右边的元素，弹出之后该值在列表中将不复存在
         * */
        System.out.println(redisTemplate.opsForList().range("AL",0,-1));
        System.out.println(redisTemplate.opsForList().rightPop("AL"));
        System.out.println(redisTemplate.opsForList().range("AL",0,-1));

        /**
         * 移出并获取列表的最后一个元素，如果列表没有元素会阻塞列表直到等待超时或发现有可以弹出的元素为止
         * */
        System.out.println(redisTemplate.opsForList().rightPop("AL",60,TimeUnit.SECONDS));

        /**
         * 用于移除列表的最后一个元素，并将该元素添加到另一个列表并返回
         * */
        redisTemplate.opsForList().range("AL",0,-1);
        redisTemplate.opsForList().rightPopAndLeftPush("AL","newAL");
        redisTemplate.opsForList().range("AL",0,-1);
        redisTemplate.opsForList().range("newList",0,-1);

        /**
         * 用于移除列表的最后一个元素，并将该元素添加到另一个列表并返回，如果列表没有元素会阻塞
         * 列表直到等待超时或发现弹出元素为止。
         * */
        System.out.println(redisTemplate.opsForList().rightPopAndLeftPush("AL","newAL",60,TimeUnit.SECONDS));

















    }
}
