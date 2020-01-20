package test.加解密;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * @创建人 zhaojingen
 * @创建时间 2019/6/10
 * @描述
 */
public class BASE64Util {
    /**
     * base 64 encode
     *
     * @param bytes
     *            待编码的byte[]
     * @return 编码后的base 64 加解密
     */
    public static String base64Encode(byte[] bytes) {
        return new BASE64Encoder().encode(bytes);
    }

    /**
     * base 64 decode
     *
     * @param base64Code
     *            待解码的base 64 加解密
     * @return 解码后的byte[]
     * @throws Exception
     */
    public static byte[] base64Decode(String base64Code) throws Exception {
        return new BASE64Decoder().decodeBuffer(base64Code);
    }

    public static void main(String[] args) throws Exception{
        String msg = "你好，中国！！！";
        final String encodeMsg = BASE64Util.base64Encode(msg.getBytes());
        System.out.println(encodeMsg);

        byte[] bytes = BASE64Util.base64Decode(encodeMsg);
        System.out.println(new String(bytes,"UTF-8"));


    }
}
