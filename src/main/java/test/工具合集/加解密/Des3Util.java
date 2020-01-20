package test.工具合集.加解密;

/**
 * @创建人 zhaojingen
 * @创建时间 2019/8/9
 * @描述
 */
import org.apache.commons.lang.StringUtils;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.Security;
import java.util.Arrays;

/**
 * Created by wywangzhenlong on 14-5-20.
 * <p/>
 * 字符串 DESede(3DES) 加密
 * ECB模式/使用PKCS7方式填充不足位,目前给的密钥是192位
 * 3DES（即Triple DES）是DES向AES过渡的加密算法（1999年，NIST将3-DES指定为过渡的
 * 加密标准），是DES的一个更安全的变形。它以DES为基本模块，通过组合分组方法设计出分组加
 * 密算法，其具体实现如下：设Ek()和Dk()代表DES算法的加密和解密过程，K代表DES算法使用的
 * 密钥，P代表明文，C代表密表，这样，
 * 3DES加密过程为：C=Ek3(Dk2(Ek1(P)))
 * 3DES解密过程为：P=Dk1((EK2(Dk3(C)))
 * 在java中调用sun公司提供的3DES加密解密算法时，需要使 用到$JAVA_HOME/jre/lib/目录下如下的4个jar包：
 * jce.jar
 * security/US_export_policy.jar
 * security/local_policy.jar
 * ext/sunjce_provider.jar
 */

public class Des3Util {

    //定义加密算法,可用 DES,DESede,Blowfish
    private static final String Algorithm = "DESede";

    //24字节的默认密钥
/*    public static final byte[] DEFAULT_KEY = {
            0x31, 0x32, 0x33, 0x34, 0x35, 0x36, 0x37, 0x38, 0x39, 0x30,
            0x31, 0x32, 0x33, 0x34, 0x35, 0x36, 0x37, 0x38, 0x39, 0x30,
            0x31, 0x32, 0x33, 0x34
    };*/
    public static byte[] DEFAULT_KEY = new byte[]{
            0X65, 0X64, 0X69, 0X61, 0X63, 0X65, 0X6C, 0X72, 0X6F, 0X66,
            0X79, 0X65, 0X6B, 0X6E, 0X65, 0X67, 0X6E, 0X61, 0X69, 0X6A,
            0X75, 0X79, 0X79, 0X77
    };

    /**
     * 将元数据进行自动补位后进行3DES加密, 返回16进制表示的字符串
     * <p/>
     * 补位后 byte[] = 描述有效数据长度(int)的byte[]+原始数据byte[]+补位byte[](补位byte为0x00)
     *
     * @param sourceData 需要加密的元数据字符串
     * @return 返回3DES加密后的16进制表示的字符串
     */
    public static String encrypt2HexString(byte[] keys, String sourceData) {
        byte[] source = new byte[0];
        try {
            //元数据
            source = sourceData.getBytes("UTF-8");

            //1.原数据byte长度
            Integer merchantData = source.length;
//            System.out.println("原数据byte长度:" + merchantData);
            //2.计算补位
            int x = (merchantData + 4) % 8;
            int y = (x == 0) ? 0 : (8 - x);
//            System.out.println("需要补位 :" + y);
            //3.将有效数据长度byte[]添加到原始byte数组的头部
            byte[] sizeByte = intToByteArray(merchantData);
            byte[] resultByte = new byte[merchantData + 4 + y];
            resultByte[0] = sizeByte[0];
            resultByte[1] = sizeByte[1];
            resultByte[2] = sizeByte[2];
            resultByte[3] = sizeByte[3];

            //4.填充补位数据
            for (int i = 0; i < merchantData; i++) {
                resultByte[4 + i] = source[i];
            }
            for (int i = 0; i < y; i++) {
                resultByte[merchantData + 4 + i] = 0x00;
            }
//            System.out.println("补位后的byte数组长度:" + resultByte.length);
//            System.out.println("加密前的数据:" + Des3Util.bytes2Hex(resultByte));
            byte[] desdata = Des3Util.encrypt(keys, resultByte);
//            System.out.println("加密后的长度:" + desdata.length);
            return bytes2Hex(desdata);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 3DES 解密 进行了补位的16进制表示的字符串数据
     * </p>
     *
     * @param keys 秘钥
     * @param data 被3des加密后的字符串(3des加密时会进行自动补位)
     * @return 解密后的有效字符串
     */
    public static String decrypt4HexString(byte[] keys, String data) {
        byte[] hexSourceData = new byte[0];
        try {
            hexSourceData = hex2byte(data.getBytes("UTF-8"));
            //解密
            byte[] unDesResult = Des3Util.decrypt(keys, hexSourceData);
            byte[] dataSizeByte = new byte[4];
            dataSizeByte[0] = unDesResult[0];
            dataSizeByte[1] = unDesResult[1];
            dataSizeByte[2] = unDesResult[2];
            dataSizeByte[3] = unDesResult[3];

            //有效数据长度
            int dsb = byteArrayToInt(dataSizeByte, 0);

            byte[] tempData = new byte[dsb];
            for (int i = 0; i < dsb; i++) {
                tempData[i] = unDesResult[4 + i];
            }
            return hex2bin(toHexString(tempData));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 加密数据
     * <p/>
     *
     * @param keybyte 加密密钥，长度为24字节
     * @param src     被加密的数据缓冲区(源)
     * @return
     */
    private static byte[] encrypt(byte[] keybyte, byte[] src) {
        try {
            //生成密钥
            SecretKey deskey = new SecretKeySpec(keybyte, Algorithm);
            //加密
            Cipher c1 = Cipher.getInstance(Algorithm);
            c1.init(Cipher.ENCRYPT_MODE, deskey);
            return c1.doFinal(src);//在单一方面的加密或解密
        } catch (java.security.NoSuchAlgorithmException e1) {
            e1.printStackTrace();
        } catch (javax.crypto.NoSuchPaddingException e2) {
            e2.printStackTrace();
        } catch (java.lang.Exception e3) {
            e3.printStackTrace();
        }
        return null;
    }

    /**
     * 解密数据
     * <p/>
     *
     * @param keybyte keybyte为加密密钥，长度为24字节
     * @param src     src为加密后的缓冲区
     * @return
     */
    private static byte[] decrypt(byte[] keybyte, byte[] src) {
        try {
            //生成密钥
            SecretKey deskey = new SecretKeySpec(keybyte, Algorithm);
            //解密
            Cipher c1 = Cipher.getInstance(Algorithm);
            c1.init(Cipher.DECRYPT_MODE, deskey);
            return c1.doFinal(src);
        } catch (java.security.NoSuchAlgorithmException e1) {
            e1.printStackTrace();
        } catch (javax.crypto.NoSuchPaddingException e2) {
            e2.printStackTrace();
        } catch (java.lang.Exception e3) {
            e3.printStackTrace();
        }
        return null;
    }

    /**
     * 将byte数组 转换为16进制表示的字符串
     *
     * @param ba
     * @return
     */
    private static String toHexString(byte[] ba) {
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < ba.length; i++)
            str.append(String.format("%x", ba[i]));
        return str.toString();
    }

    private static String hex2bin(String hex) {
        String digital = "0123456789abcdef";
        char[] hex2char = hex.toCharArray();
        byte[] bytes = new byte[hex.length() / 2];
        int temp;
        for (int i = 0; i < bytes.length; i++) {
            temp = digital.indexOf(hex2char[2 * i]) * 16;
            temp += digital.indexOf(hex2char[2 * i + 1]);
            bytes[i] = (byte) (temp & 0xff);
        }
        return new String(bytes);
    }

    /**
     * 将byte数组 转换为int
     *
     * @param b
     * @param offset 位游方式
     * @return
     */
    private static int byteArrayToInt(byte[] b, int offset) {
        int value = 0;
        for (int i = 0; i < 4; i++) {
            int shift = (4 - 1 - i) * 8;
            value += (b[i + offset] & 0x000000FF) << shift;//往高位游
        }
        return value;
    }

    private static byte[] hex2byte(byte[] b) {
        if ((b.length % 2) != 0) {
            throw new IllegalArgumentException("长度不是偶数");
        }
        byte[] b2 = new byte[b.length / 2];
        for (int n = 0; n < b.length; n += 2) {
            String item = new String(b, n, 2);
            // 两位一组，表示一个字节,把这样表示的16进制字符串，还原成一个进制字节
            b2[n / 2] = (byte) Integer.parseInt(item, 16);
        }
        b = null;
        return b2;
    }

    /**
     * 将int 转换为 byte 数组
     *
     * @param i
     * @return
     */
    private static byte[] intToByteArray(int i) {
        byte[] result = new byte[4];
        result[0] = (byte) ((i >> 24) & 0xFF);
        //必须把我们要的值弄到最低位去，有人说不移位这样做也可以， result[0] = (byte)(i  & 0xFF000000);
        //，这样虽然把第一个字节取出来了，但是若直接转换为byte类型，会超出byte的界限，出现error。再提下数
        // 之间转换的原则（不管两种类型的字节大小是否一样，原则是不改变值，内存内容可能会变，比如int转为
        // float肯定会变）所以此时的int转为byte会越界，只有int的前三个字节都为0的时候转byte才不会越界。
        // 虽然 result[0] = (byte)(i  & 0xFF000000); 这样不行，但是我们可以这样 result[0] = (byte)((i  & //0xFF000000) >>24);
        result[1] = (byte) ((i >> 16) & 0xFF);
        result[2] = (byte) ((i >> 8) & 0xFF);
        result[3] = (byte) (i & 0xFF);
        return result;
    }

    private static String bytes2Hex(byte[] bts) {
        String des = "";
        String tmp = null;
        for (int i = 0; i < bts.length; i++) {
            tmp = (Integer.toHexString(bts[i] & 0xFF));
            if (tmp.length() == 1) {
                des += "0";
            }
            des += tmp;
        }
        return des;
    }


    public static void main(String[] args) {
        //DEFAULT_KEY为默认秘钥
        //添加新安全算法,如果用JCE就要把它添加进去
        Security.addProvider(new com.sun.crypto.provider.SunJCE());
        String szSrc = "//\\123123";
        System.out.println("加密前的字符串:" + szSrc);
        byte[] encoded = new byte[0];
        try {
            String encodedstr = encrypt2HexString(DEFAULT_KEY, szSrc);
            System.out.println("加密后的字符串:" + encodedstr);
            System.out.println("解密后的字符串：" + decrypt4HexString(DEFAULT_KEY , encodedstr));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
