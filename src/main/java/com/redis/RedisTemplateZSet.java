package com.redis;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.redis.core.*;

import java.util.*;

/**
 * Created by zhangguilin on 1/24/2018.
 */
public class RedisTemplateZSet {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("application-redis.xml");
        final RedisTemplate<String,Object> redisTemplate = applicationContext.getBean("redisTemplate",RedisTemplate.class);

        /**
         * 新增一个有序集合,存在的话为false,不存在的话为true
         * */
        System.out.println(redisTemplate.opsForZSet().add("zSetA","AAA",1.0));

        /**
         * 新增一个有序集合
         * */
        ZSetOperations.TypedTuple<Object> objectTypedTupleB = new DefaultTypedTuple<Object>("BBB",1.1);
        ZSetOperations.TypedTuple<Object> objectTypedTupleC = new DefaultTypedTuple<Object>("CCC",1.2);
        ZSetOperations.TypedTuple<Object> objectTypedTupleD = new DefaultTypedTuple<Object>("DDD",1.3);
        ZSetOperations.TypedTuple<Object> objectTypedTupleE = new DefaultTypedTuple<Object>("EEE",1.4);
        ZSetOperations.TypedTuple<Object> objectTypedTupleF = new DefaultTypedTuple<Object>("FFF",1.5);
        Set<ZSetOperations.TypedTuple<Object>> zSetOperationses = new HashSet<ZSetOperations.TypedTuple<Object>>();
        zSetOperationses.add(objectTypedTupleD);
        zSetOperationses.add(objectTypedTupleC);
        zSetOperationses.add(objectTypedTupleB);
        zSetOperationses.add(objectTypedTupleF);
        zSetOperationses.add(objectTypedTupleE);
        System.out.println(redisTemplate.opsForZSet().add("zSetA",zSetOperationses));
        System.out.println(redisTemplate.opsForZSet().range("zSetA",0,-1));

        /**
         * 有序集合中移除一个或者多个元素
         * */
        System.out.println(redisTemplate.opsForZSet().range("zSetA",0,-1));
        System.out.println(redisTemplate.opsForZSet().remove("zSetA","EEE"));
        System.out.println(redisTemplate.opsForZSet().range("zSetA",0,-1));

        /**
         * 增加元素的score值,并返回增加后的值
         * */
        System.out.println(redisTemplate.opsForZSet().incrementScore("zSetA","AAA",5));
        System.out.println(redisTemplate.opsForZSet().range("zSetA",0,-1));

        /**
         * 返回有序集中指定成员的排名,其中有序集合成员分数按分数值递增(从小到大)顺序排列,0表示第一
         * */
        System.out.println(redisTemplate.opsForZSet().range("zSetA",0,-1));
        System.out.println(redisTemplate.opsForZSet().rank("zSetA","DDD"));

        /**
         * 返回有序集中指定成员的排名,其中有序集成员按分数值递减（从大到小）顺序排列
         * */
        System.out.println(redisTemplate.opsForZSet().range("zSetA",0,-1));
        System.out.println(redisTemplate.opsForZSet().reverseRank("zSetA","DDD"));

        /**
         * 通过索引区间返回有序集合指定区间内的成员,其中有序集合成员按分数值递增（从小到大）顺序排列
         * */
        System.out.println(redisTemplate.opsForZSet().range("zSetA",0,-1));

        /**
         * 通过索引区间返回有序集合指定区间内的成员,其中有序集合成员按分数值递增（从小到大）顺序排列
         * */
        Set<ZSetOperations.TypedTuple<Object>> typedTuples = redisTemplate.opsForZSet().rangeWithScores("zSetA",0,-1);
        Iterator<ZSetOperations.TypedTuple<Object>> iterator = typedTuples.iterator();
        while (iterator.hasNext()){
            ZSetOperations.TypedTuple<Object> typedTuple = iterator.next();
            System.out.println("value:"+typedTuple.getValue()+"score:"+typedTuple.getScore());
        }

        /**
         * 通过分数返回有序集合指定区间内的成员,其中有序集合成员按分数值递增（从小到大）顺序排列
         * */
        System.out.println(redisTemplate.opsForZSet().rangeByScore("zSetA",0,1.4));

        /**
         * 通过分数返回有序集合指定区间内的成员对象,其中有序集成员按分数值递增（从小到大）顺序排列
         * */
        Set<ZSetOperations.TypedTuple<Object>> tupleSet = redisTemplate.opsForZSet().rangeByScoreWithScores("zSetA",0,1.4);
        Iterator<ZSetOperations.TypedTuple<Object>> typedTupleIterator = tupleSet.iterator();
        while (typedTupleIterator.hasNext()){
            ZSetOperations.TypedTuple<Object> objectTypedTuple = typedTupleIterator.next();
            System.out.println("value:"+objectTypedTuple.getValue()+"-----"+"score:"+objectTypedTuple.getScore());
        }

        /**
         * 通过分数返回有序集合指定区间内的成员,并在索引范围内,其中有序集成员按分数值递增（从小到大）顺序排序
         * */
        System.out.println(redisTemplate.opsForZSet().rangeByScore("zSetA",0,5));
        System.out.println(redisTemplate.opsForZSet().rangeByScore("zSetA",0,5,1,2));

        /**
         * 通过分数返回有序集合指定区间内的成员对象,并在索引范围内,其中有序集成员按照
         * 分数值递增（从小到大）顺序排序
         * */
        Set<ZSetOperations.TypedTuple<Object>> typedTupleSet = redisTemplate.opsForZSet().rangeByScoreWithScores("zSetA",0,5,1,2);
        Iterator<ZSetOperations.TypedTuple<Object>> tupleIterator = typedTupleSet.iterator();
        while (tupleIterator.hasNext()){
            ZSetOperations.TypedTuple<Object> typedTuple = tupleIterator.next();
            System.out.println("value: "+typedTuple.getValue()+"  "+"score: "+typedTuple.getScore());
        }

        /**
         * 通过分数返回有序集合指定区间内的成员个数
         * */
        System.out.println(redisTemplate.opsForZSet().rangeByScore("zSetA",0,2));
        System.out.println(redisTemplate.opsForZSet().count("zSetA",0,2));

        /**
         * 获取有序集合中的成员数,内部调用的就是zCard方法
         * */
        System.out.println(redisTemplate.opsForZSet().size("zSetA"));

        /**
         * 获取有序集合的成员数
         * */
        System.out.println(redisTemplate.opsForZSet().zCard("zSetA"));

        /**
         * 获取指定成员的score值
         * */
        System.out.println(redisTemplate.opsForZSet().score("zSetA","DDD"));

        /**
         * 移除指定索引位置的成员,其中有序集合成员按分数值递增（从小到大）顺序排序
         * */
        System.out.println(redisTemplate.opsForZSet().range("zSetA",0,-1));
        System.out.println(redisTemplate.opsForZSet().removeRange("zSetA",0,2));
        System.out.println(redisTemplate.opsForZSet().range("zSetA",0,-1));

        /**
         * 根据指定的score值的范围来移除成员
         * */
        System.out.println(redisTemplate.opsForZSet().add("zSetB","AAA",5.0));
        System.out.println(redisTemplate.opsForZSet().add("zSetB","BBB",1.1));
        System.out.println(redisTemplate.opsForZSet().add("zSetB","CCC",1.2));
        System.out.println(redisTemplate.opsForZSet().add("zSetB","DDD",1.3));
        System.out.println(redisTemplate.opsForZSet().add("zSetB","EEE",1.4));
        System.out.println(redisTemplate.opsForZSet().add("zSetB","QQQ",1.6));
        System.out.println(redisTemplate.opsForZSet().add("zSetB","WWW",1.7));
        System.out.println(redisTemplate.opsForZSet().range("zSetB",0,-1));
        System.out.println(redisTemplate.opsForZSet().removeRangeByScore("zSetB",1.2,1.4));
        System.out.println(redisTemplate.opsForZSet().range("zSetB",0,-1));

        /**
         * 计算给定的一个有序集的并集,并存储在新的destKey中,key相同的话会把score值相加
         * */
        System.out.println(redisTemplate.opsForZSet().unionAndStore("zSetA","zSetB","zSetC"));
        Set<ZSetOperations.TypedTuple<Object>> tuples = redisTemplate.opsForZSet().rangeWithScores("zSetC",0,-1);
        Iterator<ZSetOperations.TypedTuple<Object>> iteratorDest = tuples.iterator();
        while (iteratorDest.hasNext()){
            ZSetOperations.TypedTuple<Object> typedTuple = iteratorDest.next();
            System.out.println("value:"+typedTuple.getValue()+"  "+"score:"+typedTuple.getScore());
        }

        /**
         * 计算给定的多个有序集的并集,并存储在新的destKey中
         * */
        System.out.println(redisTemplate.opsForZSet().add("zSetC","BBB",2));
        System.out.println(redisTemplate.opsForZSet().add("zSetC","CCC",5));
        System.out.println(redisTemplate.opsForZSet().add("zSetC","EEE",3));
        System.out.println(redisTemplate.opsForZSet().add("zSetC","QQQ",4));
        System.out.println(redisTemplate.opsForZSet().add("zSetC","WWW",1));

        List<String> stringList = new ArrayList<String>();
        stringList.add("zSetA");
        stringList.add("zSetB");
        System.out.println(redisTemplate.opsForZSet().unionAndStore("zSetC",stringList,"zSetD"));
        Set<ZSetOperations.TypedTuple<Object>> tuples1 = redisTemplate.opsForZSet().rangeWithScores("zSetD",0,-1);
        Iterator<ZSetOperations.TypedTuple<Object>> tupleIterator1 = tuples1.iterator();
        while(tupleIterator1.hasNext()){
            ZSetOperations.TypedTuple<Object> typedTuple = tupleIterator1.next();
            System.out.println(typedTuple.getValue()+""+typedTuple.getScore());
        }

        /**
         * 计算给定的一个或多个有序集的交集并将结果集存储在新的有序集合key中
         * */
        System.out.println(redisTemplate.opsForZSet().intersectAndStore("zSetA","zSetB","zSetE"));
        Set<ZSetOperations.TypedTuple<Object>> tuples2 = redisTemplate.opsForZSet().rangeWithScores("zSetE",0,-1);
        Iterator<ZSetOperations.TypedTuple<Object>> tupleIterator2 = tuples2.iterator();
        while (tupleIterator2.hasNext()){
            ZSetOperations.TypedTuple<Object> typedTuple = tupleIterator2.next();
            System.out.println(typedTuple.getValue()+""+typedTuple.getScore());
        }

        /**
         * 计算给定一个或多个有序集的交集并将结果及存储在新的有序集合key中
         * */
        List<String> stringList1 = new ArrayList<String>();
        stringList1.add("zSetA");
        stringList1.add("zSetB");
        System.out.println(redisTemplate.opsForZSet().intersectAndStore("zSetC",stringList1,"zSetF"));
        Set<ZSetOperations.TypedTuple<Object>> tuples3 = redisTemplate.opsForZSet().rangeWithScores("zSetF",0,-1);
        Iterator<ZSetOperations.TypedTuple<Object>> tupleIterator3 = tuples3.iterator();
        while (tupleIterator3.hasNext()){
            ZSetOperations.TypedTuple<Object> typedTuple = tupleIterator3.next();
            System.out.println(typedTuple.getValue()+"~~~~"+typedTuple.getScore());
        }

        /**
         * 遍历zset
         * */
        Cursor<ZSetOperations.TypedTuple<Object>> cursor = redisTemplate.opsForZSet().scan("zSetC", ScanOptions.NONE);
        while(cursor.hasNext()){
            ZSetOperations.TypedTuple<Object> typedTuple = cursor.next();
            System.out.println(typedTuple.getValue()+"...."+typedTuple.getScore());
        }














    }
}
