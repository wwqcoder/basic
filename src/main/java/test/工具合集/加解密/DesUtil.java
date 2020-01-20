package test.工具合集.加解密;

/**
 * @创建人 zhaojingen
 * @创建时间 2019/8/9
 * @描述
 */
import org.apache.commons.lang.StringUtils;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;
import test.工具合集.logger.Logger;
import test.工具合集.logger.LoggerFactory;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import java.security.SecureRandom;

/**
 * Created by lijunfu on 14-5-22.
 */
public class DesUtil {

    private static final Logger logger = LoggerFactory.getLogger();

    /**
     * 生成密钥方法
     *
     * @param seed
     *         密钥种子
     * @return 密钥 BASE64
     * @throws Exception
     */
    public static String generateKey(String seed) throws Exception {
        byte[] seedBase64DecodeBytes = new BASE64Decoder().decodeBuffer(seed);

        SecureRandom secureRandom = new SecureRandom(seedBase64DecodeBytes);
        KeyGenerator keyGenerator = KeyGenerator.getInstance("DES");
        keyGenerator.init(secureRandom);
        SecretKey secretKey = keyGenerator.generateKey();
        byte[] bytes = secretKey.getEncoded();

        String keyBase64EncodeString = new BASE64Encoder().encodeBuffer(bytes);

        return stringBlank(keyBase64EncodeString);
    }

    /**
     * null "" 格式化为"" 如果里面有 \r或者\n都去掉
     *
     * @param value
     * @return ""
     */
    private static String stringBlank(String value) {
        if (value == null || "".equals(value)) {
            value = "";
        }
        return value.replaceAll("\r|\n", "");
    }

    /**
     * server加密,app解密,此方法和 unEncrypt4Quick 配对
     *
     * @param text
     *         明文
     * @param key
     *         密钥 BASE64
     * @param charset
     *         字符集
     * @return 密文
     * @throws Exception
     */
    public static String encrypt4Quick(String text, String key, String charset) throws Exception {
        if(StringUtils.isBlank(text)||StringUtils.isBlank(key)){
            return "";
        }
        if(StringUtils.isBlank(charset)){
            charset = "utf-8";
        }
        byte[] keyBase64DecodeBytes = Base64QuickUtil.decode(key);//base64解码key
        DESKeySpec desKeySpec = new DESKeySpec(keyBase64DecodeBytes);//前8个字节做为密钥
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        SecretKey secretKey = keyFactory.generateSecret(desKeySpec);

        Cipher cipher = Cipher.getInstance("DES");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] textBytes = text.getBytes(charset);//明文UTF-8格式
        byte[] bytes = cipher.doFinal(textBytes);//DES加密

        String encryptBase64EncodeString = Base64QuickUtil.encode(bytes);//base64编码

        return encryptBase64EncodeString;
    }

    /**
     * server解密,app加密,此方法和 encrypt4Quick 配对
     *
     * @param text
     *         密文
     * @param key
     *         密钥 BASE64
     * @param charset
     *         字符集
     * @return 密文
     * @throws Exception
     */
    public static String unEncrypt4Quick(String text, String key, String charset) throws Exception {
        String str = "";
        if(StringUtils.isBlank(text)||StringUtils.isBlank(key)){
            return str;
        }
        if(StringUtils.isBlank(charset)){
            charset = "utf-8";
        }

        return  str;
    }

    /**
     * 解密方法
     *
     * @param text
     *         密文
     * @param key
     *         密钥 BASE64
     * @param charset
     *         字符集
     * @return 明文
     * @throws Exception
     */
    public static String decrypt4Quick(String text, String key, String charset) throws Exception {
        if(StringUtils.isBlank(text)||StringUtils.isBlank(key)){
            return "";
        }
        if(StringUtils.isBlank(charset)){
            charset = "utf-8";
        }
        byte[] keyBase64DecodeBytes = Base64QuickUtil.decode(key);

        DESKeySpec desKeySpec = new DESKeySpec(keyBase64DecodeBytes);
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        SecretKey secretKey = keyFactory.generateSecret(desKeySpec);

        Cipher cipher = Cipher.getInstance("DES");
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] textBytes = Base64QuickUtil.decode(text);
        byte[] bytes = cipher.doFinal(textBytes);

        String decryptString = new String(bytes, charset);

        return stringBlank(decryptString);
    }

    public static String encrypt4WePay(String text, String key, String charset) throws Exception {
        if(StringUtils.isBlank(text)||StringUtils.isBlank(key)){
            return "";
        }
        if(StringUtils.isBlank(charset)){
            charset = "utf-8";
        }
        byte[] keyBase64DecodeBytes = new BASE64Decoder().decodeBuffer(key);//base64解码key
        DESKeySpec desKeySpec = new DESKeySpec(keyBase64DecodeBytes);//前8个字节做为密钥
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        SecretKey secretKey = keyFactory.generateSecret(desKeySpec);

        Cipher cipher = Cipher.getInstance("DES");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] textBytes = text.getBytes(charset);//明文UTF-8格式
        byte[] bytes = cipher.doFinal(textBytes);//DES加密

        String encryptBase64EncodeString = new BASE64Encoder().encodeBuffer(bytes);//base64编码
        //base64后会有/r/n回车换行字段，需要剔除掉，免得存库后查询不出来相关记录
        int mod = encryptBase64EncodeString.length() % 8;
        if (mod > 0) {
            encryptBase64EncodeString = encryptBase64EncodeString
                    .substring(0, encryptBase64EncodeString.length() - mod);
        }
        return encryptBase64EncodeString;
    }



    public static String decrypt4WePay(String text, String key, String charset) throws Exception {
        if(StringUtils.isBlank(text)||StringUtils.isBlank(key)){
            return "";
        }
        if(StringUtils.isBlank(charset)){
            charset = "utf-8";
        }
        byte[] keyBase64DecodeBytes = new BASE64Decoder().decodeBuffer(key);

        DESKeySpec desKeySpec = new DESKeySpec(keyBase64DecodeBytes);
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        SecretKey secretKey = keyFactory.generateSecret(desKeySpec);

        Cipher cipher = Cipher.getInstance("DES");
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] textBytes = new BASE64Decoder().decodeBuffer(text);
        byte[] bytes = cipher.doFinal(textBytes);

        String decryptString = new String(bytes, charset);

        return stringBlank(decryptString);
    }

    /**
     * 解密，适用于带有盐头、盐尾的加解密方式
     *
     * @param text
     *         待解密文本
     * @param privateKey
     *         私钥
     * @param saltHead
     *         盐头
     * @param saltTail
     *         盐尾
     * @return
     * @throws Exception
     */
    public static String decryptWithHeadAndTail(String text, String privateKey, String saltHead, String saltTail) throws Exception {
        if (StringUtils.isBlank(text) || StringUtils.isBlank(privateKey)) {
            return "";
        }
        byte[] base64DecodeBts = new byte[0];
        String skss = "";
        base64DecodeBts = new BASE64Decoder().decodeBuffer(text);
        byte[] sks = RsaUtil.decryptByPrivateKey(base64DecodeBts, privateKey);
        skss = new String(sks, "UTF-8");
        if (!StringUtils.isEmpty(saltHead) && skss.startsWith(saltHead)) {
            skss = skss.substring(saltHead.length(), skss.length());
        }
        if (!StringUtils.isEmpty(saltTail) && skss.endsWith(saltTail)) {
            skss = skss.substring(0, skss.length() - saltTail.length());
        }

        return skss;
    }

    public static void main(String[] args) {
        String str = "6225880163655526";
        try {
            String key1 = new String(new BASE64Encoder().encodeBuffer("gU7Jv1NE".getBytes()));
            System.out.println("秘钥  |" + key1);
            String result = DesUtil.encrypt4Quick(str, key1, "UTF-8");
            System.out.println("encrypt4Quick加密结果    | " + result);
            System.out.println("decrypt4Quick解密结果    | " + DesUtil.decrypt4Quick(result, key1, "UTF-8"));

            String key2 = "DES-网银在线-框支付-服务端组-20140429";
            String encryptStr = DesUtil.encrypt4WePay("6217000010026307226", key2, "UTF-8");
            System.out.println("encrypt4WePay加密结果    | " + result);
            System.out.println("decrypt4WePay解密结果    | " + DesUtil.decrypt4Quick(result, key1, "UTF-8"));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
