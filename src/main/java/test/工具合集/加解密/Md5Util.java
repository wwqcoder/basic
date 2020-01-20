package test.工具合集.加解密;

/**
 * @创建人 zhaojingen
 * @创建时间 2019/8/9
 * @描述
 */
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by lijunfu on 14-5-22.
 */
public class Md5Util {

    private final static String UTF8 = "UTF-8";
    private final static String MD5 = "MD5";

    /**
     * @param saltHead 盐头,可空 UTF-8 编码
     * @param str      需要md5的字符串 UTF-8 编码
     * @param saltTail 盐尾,可空 UTF-8 编码
     * @return 32位MD5小写
     */
    public static String md5Lower32(String saltHead, String str, String saltTail) {
        String result = null;
        try {
            if (saltHead == null) {
                saltHead = "";
            }
            if (saltTail == null) {
                saltTail = "";
            }
            byte[] bytes = new byte[0];
            bytes = (saltHead + str + saltTail).getBytes(UTF8);
            MessageDigest messageDigest = MessageDigest.getInstance(MD5);
            messageDigest.update(bytes);
            bytes = messageDigest.digest();
            result = ByteUtil.byte2HexLowerCase(bytes);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * @param saltHead 盐头,可空
     * @param str      需要md5的字符串
     * @param saltTail 盐尾,可空
     * @return 32位MD5大写
     */
    public static String md5Upper32(String saltHead, String str, String saltTail) {
        String result = null;
        try {
            if (saltHead == null) {
                saltHead = "";
            }
            if (saltTail == null) {
                saltTail = "";
            }
            byte[] bytes = new byte[0];
            bytes = (saltHead + str + saltTail).getBytes(UTF8);
            MessageDigest messageDigest = MessageDigest.getInstance(MD5);
            messageDigest.update(bytes);
            bytes = messageDigest.digest();
            result = ByteUtil.byte2HexUpperCase(bytes);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String md5Lower16(String str) {
        String result = null;
        try {
            MessageDigest md = MessageDigest.getInstance(MD5);
            md.update(str.getBytes(UTF8));
            byte b[] = md.digest();
            int i;
            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }
            result = buf.toString().substring(8, 24);
//            System.out.println("mdt 16bit: " + buf.toString().substring(8, 24));
//            System.out.println("md5 32bit: " + buf.toString() );
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result.toLowerCase();
    }

    public static String md5Upper16(String str) {
        String result = null;
        try {
            MessageDigest md = MessageDigest.getInstance(MD5);
            md.update(str.getBytes(UTF8));
            byte b[] = md.digest();
            int i;
            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }
            result = buf.toString().substring(8, 24);
//            System.out.println("mdt 16bit: " + buf.toString().substring(8, 24));
//            System.out.println("md5 32bit: " + buf.toString() );
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result.toUpperCase();
    }
    public static void main(String[] args) {
        String str = "360000000052304209";
        System.out.println(Md5Util.md5Lower32("", str, "").length());
        System.out.println(Md5Util.md5Lower32("", str, ""));
        System.out.println(Md5Util.md5Upper32("", str, ""));

        System.out.println(Md5Util.md5Lower16(str));
        System.out.println(Md5Util.md5Upper16(str));
    }

}
