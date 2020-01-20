package test.annotation;

import test.工具合集.加解密.ByteUtil;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Util {



    private final static String UTF8 = "UTF-8";
    private final static String MD5 = "MD5";

    /**
     * @param saltHead
     *            盐头,可空 UTF-8 编码
     * @param str
     *            需要md5的字符串 UTF-8 编码
     * @param saltTail
     *            盐尾,可空 UTF-8 编码
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
            byte[] bytes = (saltHead + str + saltTail).getBytes(UTF8);
            MessageDigest messageDigest = MessageDigest.getInstance(MD5);
            messageDigest.update(bytes);
            bytes = messageDigest.digest();
            result = ByteUtil.byte2HexLowerCase(bytes);
        } catch (NoSuchAlgorithmException e) {

        } catch (UnsupportedEncodingException e) {

        }
        return result;
    }

    /**
     * @param saltHead
     *            盐头,可空
     * @param str
     *            需要md5的字符串
     * @param saltTail
     *            盐尾,可空
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
            byte[] bytes = (saltHead + str + saltTail).getBytes(UTF8);
            MessageDigest messageDigest = MessageDigest.getInstance(MD5);
            messageDigest.update(bytes);
            bytes = messageDigest.digest();
            result = ByteUtil.byte2HexUpperCase(bytes);
        } catch (NoSuchAlgorithmException e) {

        } catch (UnsupportedEncodingException e) {

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
                if (i < 0) {
                    i += 256;
                }
                if (i < 16) {
                    buf.append("0");
                }
                buf.append(Integer.toHexString(i));
            }
            result = buf.toString().substring(8, 24);
        } catch (NoSuchAlgorithmException e) {

        } catch (UnsupportedEncodingException e) {

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
                if (i < 0) {
                    i += 256;
                }
                if (i < 16) {
                    buf.append("0");
                }
                buf.append(Integer.toHexString(i));
            }
            result = buf.toString().substring(8, 24);
        } catch (NoSuchAlgorithmException e) {

        } catch (UnsupportedEncodingException e) {

        }
        return result.toUpperCase();
    }

    public static String MD5Encode(String origin, String charsetname) {
        String resultString = null;
        try {
            resultString = new String(origin);
            MessageDigest md = MessageDigest.getInstance("MD5");
            if (charsetname == null || "".equals(charsetname)) {
                resultString = byteArrayToHexString(md.digest(resultString
                        .getBytes("utf-8")));
            }
            else {
                resultString = byteArrayToHexString(md.digest(resultString
                        .getBytes(charsetname)));
            }
        } catch (Exception exception) {

        }
        return resultString;
    }

    private static String byteArrayToHexString(byte b[]) {
        StringBuffer resultSb = new StringBuffer();
        for (int i = 0; i < b.length; i++) {
            resultSb.append(byteToHexString(b[i]));
        }
        return resultSb.toString();
    }

    private static String byteToHexString(byte b) {
        int n = b;
        if (n < 0) {
            n += 256;
        }
        int d1 = n / 16;
        int d2 = n % 16;
        return hexDigits[d1] + hexDigits[d2];
    }

    private static final String hexDigits[] = { "0", "1", "2", "3", "4", "5",
            "6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };
}
