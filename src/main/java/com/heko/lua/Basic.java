package com.heko.lua;

/**
 * @Author:heko
 * @Date:2020/3/8 19:30
 * @Description:
 */
public class Basic {
    public static String host = "localhost";
    public static int threadCount = 5;
    public static int hongBaoCount = 100;
    public static String hongBaoPoolKey = "hongBaoPoolKey";
    public static String hongBaoDetailListKey = "hongBaoDetailListKey";
    public static String userIdRecordKey = "userIdRecordKey";
    public static String getHongBaoScript(){
        return "if redis.call('hexists',KEYS[3],KEYS[4]) ~= 0 then\n"+
                "return nil\n" +
                "else\n"+
                "local hongBao = redis.call('rpop',KEYS[1]);\n"+
                "if hongBao then\n"+
                "local x = cjson.decode(hongBao);\n"+
                "x['userId'] = KEYS[4];\n"+
                "local re = cjson.encode(x);\n"+
                "redis.call('hset',KEYS[3],KEYS[4],'1');\n"+
                "redis.call('lpush',KEYS[2],re);\n"+
                "return re;\n"+
                "end\n"+
                "end\n"+
                "return nil";
    }

}
