package com.heko.redPacket;

import com.alibaba.fastjson.JSONObject;

import com.heko.lua.Basic;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisSentinelPool;

import java.util.Map;
import java.util.concurrent.CountDownLatch;

/**
 * @Author:heko
 * @Date:2020/3/8 19:42
 * @Description:
 */
public class GenerateRedPacket {
    public static void generateRedPacket() throws InterruptedException {
        final Jedis jedis = new Jedis(Basic.host);
        jedis.flushAll();
        final CountDownLatch latch = new CountDownLatch(Basic.threadCount);
        for(int i=0;i<Basic.threadCount;i++){
            final int page = i;
            Thread thread = new Thread(){
                public void run(){
                    int per = Basic.hongBaoCount/Basic.threadCount;
                    JSONObject obj = new JSONObject();
                    for(int j=page*per;j<(page+1)*per;j++){
                        obj.put("id","rid_"+j);
                        obj.put("money",j);
                        System.out.println(obj.toJSONString());
                        jedis.lpush("hongBaoPoolKey", obj.toJSONString());
                    }
                    latch.countDown();
                }
            };
            thread.start();
        }
        latch.await();
    }
}
