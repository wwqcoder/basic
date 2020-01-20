package test.加解密;

/**
 * @创建人 zhaojingen
 * @创建时间 2019/6/10
 * @描述
 */

import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import cn.hutool.core.codec.Base64;
import org.apache.commons.codec.binary.Hex;

public class HmacTest {
    private static String src = "hello hmac";

    public static void main(String[] args) {
        jdkHmacMD5();
        System.out.println(HmacSHA256(src.getBytes(),src.getBytes()));
    }

    public static void jdkHmacMD5() {
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance("HmacMD5");// 初始化KeyGenerator
            SecretKey secretKey = keyGenerator.generateKey();// 产生秘钥
            byte[] key = secretKey.getEncoded();// 获得秘钥(默认生成)
            // byte[]key=Hex.decodeHex(new
            // char[]{'a','a','a','a','a','a','a','a','a','a'});//手动生成秘钥(十位)
            SecretKey secretKey2 = new SecretKeySpec(key, "HmacMD5");// 还原秘钥
            Mac mac = Mac.getInstance(secretKey2.getAlgorithm());// 实例化mac
            // 初始化mac
            mac.init(secretKey);
            byte[] hmacMD5Bytes = mac.doFinal(src.getBytes());
            System.out.println("jdk hmacMD5: " + Hex.encodeHex(hmacMD5Bytes));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * HMAC算法加密
     * @param message 待加密信息
     * @param key 密钥
     * @return
     */
    public static String HmacSHA256(byte[] message, byte[] key){
        long begin = System.currentTimeMillis();
        try {
            Mac hmacSha256Mac = Mac.getInstance("HMACSha256");
            SecretKeySpec secretKey = new SecretKeySpec(key, "HMACSha256");
            hmacSha256Mac.init(secretKey);
            byte[] result = hmacSha256Mac.doFinal(message);
            long end = System.currentTimeMillis();
            return Base64.encode(result);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

}
