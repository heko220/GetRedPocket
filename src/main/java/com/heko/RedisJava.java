package com.heko;

import redis.clients.jedis.Jedis;

/**
 * @Author:heko
 * @Date:2020/3/8 18:22
 * @Description:
 */
public class RedisJava {
    public static void main(String[] args) {
        // 连接本地的 Redis 服务
        Jedis jedis = new Jedis("localhost");
        System.out.println("连接本地的 Redis 服务成功！");
        // 查看服务是否运行
        System.out.println("服务 正在运行: " + jedis.ping());
    }
}
