package com.github.haomao.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisZSetCommands;
import org.springframework.data.redis.core.*;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

import java.util.*;

/**
 * @Author:ggq
 * @Date:2018/12/13
 * @Description:
 */
@Component
public class RedisDemo {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    public void testStringAndHash(){
        //由于使用JDK序列化,所以Redis保存整数时不能计算
        redisTemplate.opsForValue().set("int_key","1");

        stringRedisTemplate.opsForValue().set("num2","1");
        //使用运算
        stringRedisTemplate.opsForValue().increment("num2",1);

        Jedis jedis = (Jedis)stringRedisTemplate.getConnectionFactory().getConnection().getNativeConnection();
        //减1操作，这个命令RedisTemplate不支持，所以先获取底层Jedis连接在操作
        jedis.decr("num2");

        Map<String,String> hash = new HashMap<>();
        hash.put("filed1","value1");
        hash.put("filed2","value2");
        stringRedisTemplate.opsForHash().putAll("hash",hash);
        stringRedisTemplate.opsForHash().put("hash","field3","value3");

        //绑定散列操作的key，这样可以连续对同一个散列数据类型操作
        BoundHashOperations<String, Object, Object> hashOps = stringRedisTemplate.boundHashOps("hash");
        hashOps.delete("field1");
        hashOps.put("field4","value4");
    }

    public void testList(){
        //链表从左到右顺序为v10,v8,v6,v4,v2
        stringRedisTemplate.opsForList().leftPushAll("list1","v2","v4","v6","v8","v10");
        //链表从左到右顺序为v2,v4,v6,v10
        stringRedisTemplate.opsForList().rightPushAll("list2","v2","v4","v6","v10");
        //绑定list2链表操作
        BoundListOperations listOps = stringRedisTemplate.boundListOps("list2");
        //从右边弹出一个成员
        Object result1 = listOps.rightPop();
        System.out.println("-----------result1-->"+result1);
        //获取定位元素，下标从0开始
        Object result2 = listOps.index(1);
        System.out.println("-----------result2-->"+result2);
        //从左边插入链表
        listOps.leftPush("v1");
        //求链表长度
        Long size = listOps.size();
        //求链表下标区间成员，整个链表下标范围为0到size-1,这里不取最后一个元素
        List elements = listOps.range(0, size - 2);
    }

    public void testSet(){
        //因为集合不允许重复，所以只是插入5个成员到集合中
        stringRedisTemplate.opsForSet().add("set1","v1","v1","v2","v3","v4","v5");
        stringRedisTemplate.opsForSet().add("set2","v2","v4","v6","v8");
        BoundSetOperations setOps = stringRedisTemplate.boundSetOps("set1");
        //增加两个元素
        setOps.add("v6","v7");
        //删除两个元素
        setOps.remove("v1","v7");
        Set set1 = setOps.members();
        System.out.println("---------set1-->"+set1);
        //求成员数
        Long size = setOps.size();
        //求交集
        Set inter = setOps.intersect("set2");
        System.out.println("-----------交集-->"+inter);
        //求差集
        Set diff = setOps.diff("set2");
        System.out.println("-----------差集-->"+diff);
        //求差集，并用新集合diff保存
        setOps.diffAndStore("set2","diff");
        //求并集
        Set union = setOps.union("set2");
        System.out.println("-----------并集-->"+union);
    }

    public void testZSet(){
        Set<ZSetOperations.TypedTuple<String>> typedTupleSet = new HashSet<>();
        for(int i=1;i<=9;i++){
            //分数
            double score = i*0.1;
            ZSetOperations.TypedTuple<String> typedTuple = new DefaultTypedTuple<>("value"+i,score);
            typedTupleSet.add(typedTuple);
        }
        //往有序集合插入元素
        stringRedisTemplate.opsForZSet().add("zset1",typedTupleSet);
        BoundZSetOperations zSetOps = stringRedisTemplate.boundZSetOps("zset1");
        //增加元素
        zSetOps.add("value10",0.26);
        Set setRange = zSetOps.range(1, 6);
        System.out.println("----------1到6区间元素-->"+setRange);
//        按照分数获取有序集合
        Set setScore = zSetOps.rangeByScore(0.2, 0.6);
        System.out.println("----------分数0.2到0.6之间元素-->"+setScore);
        //定义值范围
        RedisZSetCommands.Range range = new RedisZSetCommands.Range();
        range.gt("value3");//大于value3
        //range.gte("value3");//大于等于value3
        //range.lt("value8");//小于value8
        range.lte("value8");//小于等于value8
        //按字符串排序
        Set setLex = zSetOps.rangeByLex(range);
        System.out.println("----------按照字符串排序-->"+setLex);
        zSetOps.remove("value9","value2");
        //求分数
        Double value8Score = zSetOps.score("value8");
        System.out.println("----------value8Score-->"+value8Score);
        //在下标区间下，按分数排序，同时返回value和score
        Set<ZSetOperations.TypedTuple<String>> rangeSet = zSetOps.rangeWithScores(1, 6);
        System.out.println("----------在下标区间下，按分数排序-->"+rangeSet);
        //在分数区间下，按分数排序，同时返回value和score
        Set<ZSetOperations.TypedTuple<String>> rangeScore = zSetOps.rangeByScoreWithScores(1, 6);
        System.out.println("----------在分数区间下，按分数排序-->"+rangeScore);
        //按分数从大到小排序
        Set reverseRange = zSetOps.reverseRange(2, 8);
        System.out.println("----------按分数从大到小排序-->"+reverseRange);
    }

    /**
     * Redis事务机制
     */
    public void testMulti(){
        redisTemplate.opsForValue().set("key1","value1");
        List list = (List)redisTemplate.execute(new SessionCallback() {
            @Override
            public Object execute(RedisOperations operations) throws DataAccessException {
                //设置要监控key1
                operations.watch("key1");
                //开启事务,在exec之前，全部都只是进入队列
                operations.multi();
                operations.opsForValue().set("key2","value2");
                /**
                 * 由于是对字符串运算，此时redis服务器会抛出异常，但后面的命令会继续执行
                 */
                operations.opsForValue().increment("key1",1);
                //获取值将为null，因为redis只是把命令放入队列
                Object value2 = operations.opsForValue().get("key2");
                System.out.println("---------value2-->"+value2);
                //执行exec命令，先判断key是否在监控后被修改过，如果是则不执行事务，否则就执行事务
                return operations.exec();
            }
        });
        System.out.println("---------list-->"+list);
    }

    /**
     * Redis流水线
     */
    public void testPipeline(){
        long start = System.currentTimeMillis();
        redisTemplate.executePipelined(new SessionCallback() {
            @Override
            public Object execute(RedisOperations operations) throws DataAccessException {
                for (int i=1 ;i<=100000;i++){
                    operations.opsForValue().set("pipeline_"+i,"value_"+i);
                    String value = (String)operations.opsForValue().get("pipeline_"+i);
                    if (i==10000){
                        System.out.println("命令只是进入队列,所以为空-->"+value);
                    }
                }
                return null;
            }
        });
        long end = System.currentTimeMillis();
        System.out.println("------------耗时："+(end-start)+"毫秒");
    }

    /**
     * Redis Lua脚本
     */
    public void testLua(){
        DefaultRedisScript<String> rs = new DefaultRedisScript<>();
        //设置脚本
        rs.setScriptText("return 'Hello Redis'");
        //定义返回类型，没有这个定义，Spring不会返回结果
        rs.setResultType(String.class);
        RedisSerializer<String> stringSerializer = redisTemplate.getStringSerializer();
        //执行Lua脚本
        String result = (String)redisTemplate.execute(rs, stringSerializer, stringSerializer, null);
        System.out.println("ok");
    }

    public void testLua2(String key1,String key2,String value1,String value2){
        //定义Lua脚本
        String lua = "redis.call('set',KEYS[1],ARGV[1]) \n" +
                "redis.call('set',KEYS[2],ARGV[2]) \n" +
                "local str1 = redis.call('get',KEYS[1]) \n" +
                "local str2 = redis.call('get',KEYS[2]) \n" +
                "if str1 == str2 then \n" +
                "return 1 \n" +
                "end \n" +
                "return 0 \n";
        System.out.println("-----------lua-->"+lua);
        DefaultRedisScript<Long> rs = new DefaultRedisScript<>();
        rs.setScriptText(lua);
        rs.setResultType(Long.class);
        RedisSerializer<String> stringSerializer = redisTemplate.getStringSerializer();
        List<String> keyList = new ArrayList<>();
        keyList.add(key1);
        keyList.add(key2);
        Long result = (Long)redisTemplate.execute(rs, stringSerializer, stringSerializer, keyList, value1, value2);
        System.out.println("----------result-->"+result);
    }
}
