package com.heko.getPacket;

import com.heko.lua.Basic;
import redis.clients.jedis.Jedis;

import java.util.UUID;
import java.util.concurrent.CountDownLatch;

/**
 * @Author:heko
 * @Date:2020/3/8 20:23
 * @Description:
 */
public class GetRedPacket {
    static public void getHongBao() throws InterruptedException {
        final CountDownLatch latch = new CountDownLatch(Basic.threadCount);
        for (int i = 0; i<Basic.threadCount;i++){
            Thread thread = new Thread(){
                public void run(){
                    Jedis jedis = new Jedis(Basic.host);
                    while(true){
                        String userid = UUID.randomUUID().toString();
                        Object object = jedis.eval(Basic.getHongBaoScript(),4,Basic.hongBaoPoolKey,Basic.hongBaoDetailListKey,Basic.userIdRecordKey,userid);
                        if(object != null){
                            System.out.println("用户ID："+userid+"抢到红包详情："+object);
                        }else {
                            if(jedis.llen(Basic.hongBaoPoolKey)==0){
                                break;
                            }
                        }
                    }
                    latch.countDown();
                }
            };
            thread.start();
        }
        latch.await();
    }
}
