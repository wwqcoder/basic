package test.JedisTest;

/**
 * @创建人 zhaojingen
 * @创建时间 2019/6/24
 * @描述
 */

import redis.clients.jedis.Jedis;

public class JedisTest {
    public static void main(String args[]) {
        String host  = "192.144.250.103";
        int port = 7617;
        Jedis jedis = null;
        try {
            jedis = new Jedis(host,port);//建立连接
            jedis.auth("jRbpgD2fbCAfcnKg");//设置连接密码
            //jedis.select(1);//选着数据保存在哪个数据库db0 db1 ...
            jedis.set("name","kun");
            String name = jedis.get("name");
            System.out.println("name = " + name);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (null != jedis){
                try {
                    jedis.close();
                }catch (Exception e){
                    System.out.println("redis连接关闭失败");
                    e.printStackTrace();
                }
            }
        }

    }
}

