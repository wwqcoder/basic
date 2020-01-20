package test.工具合集.加解密;

/**
 * @创建人 zhaojingen
 * @创建时间 2019/8/9
 * @描述
 */
import java.io.ByteArrayOutputStream;

/**
 * Created by lijunfu on 14-5-22.
 */
public class ByteUtil {

    private static String hexString = "0123456789ABCDEF";

    /**
     * 转换成16进制表示的大写字符串
     *
     * @param bts 被转换的byte数组
     * @return 16进制表示的字符串
     */
    public static String byte2HexUpperCase(byte[] bts) {
        String des = "";
        String tmp = null;
        for (int i = 0; i < bts.length; i++) {
            tmp = (Integer.toHexString(bts[i] & 0xFF));
            if (tmp.length() == 1) {
                des += "0";
            }
            des += tmp;
        }
        return des.toUpperCase();
    }

    /**
     * 转换成16进制表示的小写字符串
     *
     * @param bts 被转换的byte数组
     * @return 16进制表示的字符串
     */
    public static String byte2HexLowerCase(byte[] bts) {
        String des = "";
        String tmp = null;
        for (int i = 0; i < bts.length; i++) {
            tmp = (Integer.toHexString(bts[i] & 0xFF));
            if (tmp.length() == 1) {
                des += "0";
            }
            des += tmp;
        }
        return des.toLowerCase();
    }

    public static byte[] hex2byte(byte[] b) {
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

    public static String byte2HexString(byte[] src) throws Exception {
        StringBuilder stringBuilder = new StringBuilder("");
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        String bytes = stringBuilder.toString();
        ByteArrayOutputStream baos = new ByteArrayOutputStream(bytes.length() / 2);
        // 将每2位16进制整数组装成一个字节
        for (int i = 0; i < bytes.length(); i += 2) {
            baos.write((hexString.indexOf(bytes.charAt(i)) << 4 | hexString
                    .indexOf(bytes.charAt(i + 1))));
        }
        return new String(baos.toByteArray());
    }

    public static void main(String[] args){
        try {
            Long startTime = System.currentTimeMillis();
            String jdPayPin = DesUtil.encrypt4Quick("360000000000090124", "VW9kbk5yeVE=", "UTF-8");
            System.out.println("加密后" + jdPayPin);

            byte[] jdPyaByte= "UCdl1VyswQV+wE9/pe5EQw9jrYm6cFu6".getBytes();
            String hexStrUp = byte2HexUpperCase(jdPyaByte);
            String hexStrLow = byte2HexLowerCase(jdPyaByte);

            System.out.println("转16进制大写后" + hexStrUp);
            System.out.println("转16进制小写后" + hexStrLow);


            System.out.println(new String(hex2byte(hexStrUp.getBytes())));
            System.out.println(new String(hex2byte(hexStrLow.getBytes())));


            System.out.println(DesUtil.decrypt4Quick(new String(hex2byte(hexStrLow.getBytes())),"VW9kbk5yeVE=", "UTF-8"));

            Long endTime = System.currentTimeMillis();
            System.out.println(endTime - startTime);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
