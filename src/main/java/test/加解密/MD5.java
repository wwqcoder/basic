package test.加解密;

/**
 * @创建人 zhaojingen
 * @创建时间 2019/6/10
 * @描述
 */
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/*
MD5(Message Digest algorithm 5，信息摘要算法)
通常我们不直接使用上述MD5加密。通常将MD5产生的字节数组交给BASE64再加密一把，得到相应的字符串
Digest:汇编
*/
public class MD5 {
    public static final String KEY_MD5 = "MD5";

    public static  String  getResult(String inputStr){
        System.out.println("加密前的数据:"+inputStr);
        BigInteger bigInteger=null;

        try {
            MessageDigest md = MessageDigest.getInstance(KEY_MD5);
            byte[] inputData = inputStr.getBytes();
            md.update(inputData);
            bigInteger = new BigInteger(md.digest());
        } catch (Exception e) {e.printStackTrace();}
        System.out.println("MD5加密后:" + bigInteger.toString(16));
        return bigInteger.toString(16);
    }


    public static String generate(String str) {
        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.reset();
            messageDigest.update(str.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        byte[] byteArray = messageDigest.digest();
        StringBuffer md5StrBuff = new StringBuffer();
        for (int i = 0; i < byteArray.length; i++) {
            if (Integer.toHexString(0xFF & byteArray[i]).length() == 1) {

                md5StrBuff.append("0").append(Integer.toHexString(0xFF & byteArray[i]));
            }else {
                md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));
            }
        }
        return md5StrBuff.toString();
    }




    public static void main(String args[]){
        try {
            String inputStr = "MIIGjzCCBXegAwIBAgIMfWMwc0FH8/vR9sGeMA0GCSqGSIb3DQEBCwUAMGYxCzAJ\n" +
                    "BgNVBAYTAkJFMRkwFwYDVQQKExBHbG9iYWxTaWduIG52LXNhMTwwOgYDVQQDEzNH\n" +
                    "bG9iYWxTaWduIE9yZ2FuaXphdGlvbiBWYWxpZGF0aW9uIENBIC0gU0hBMjU2IC0g\n" +
                    "RzIwHhcNMTgwNjExMDU0NjA4WhcNMTkwODAxMDc1MTA1WjCBgzELMAkGA1UEBhMC\n" +
                    "Q04xEDAOBgNVBAgTB0JlaWppbmcxEDAOBgNVBAcTB0JlaWppbmcxOjA4BgNVBAoT\n" +
                    "MUNoaW5hYmFuayBQYXltZW50cyAoQmVpamluZykgVGVjaG5vbG9neSBDby4sIEx0\n" +
                    "ZC4xFDASBgNVBAMMCyouamRwYXkuY29tMIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8A\n" +
                    "MIIBCgKCAQEAvWv9wlUIfQhDtpQXv0WO/U5mx/rgNmIkQq2haE1+eCiCZE+2Mann\n" +
                    "dwMxzU2JK/b4FAuHMiRTNnH0EGweqX9hpsjDQBhHu67a6fgaaH2W+Jo6TY0a2sJB\n" +
                    "ANBcLlmKMn/fJmVXXNNA+8+PlFChk8FLtzwOf4+871BJRsYlJ/wa0HuOYHZYuly7\n" +
                    "mSh1YQK7dRUrJDcIwMBVqZQYlKv5MfT7wahEOOiiVnBnYn0kKCXN9Q5j8ssTWw3g\n" +
                    "yVXg0pYuITHHxp2ZC2UygajX1vhD1+JHK7XnnugkAsKZAttWBolgyFlGrSYJuNob\n" +
                    "zA6BmTJWPLzJ7+0zlkNv+zYJOYoT4ucPZwIDAQABo4IDHTCCAxkwDgYDVR0PAQH/\n" +
                    "BAQDAgWgMIGgBggrBgEFBQcBAQSBkzCBkDBNBggrBgEFBQcwAoZBaHR0cDovL3Nl\n" +
                    "Y3VyZS5nbG9iYWxzaWduLmNvbS9jYWNlcnQvZ3Nvcmdhbml6YXRpb252YWxzaGEy\n" +
                    "ZzJyMS5jcnQwPwYIKwYBBQUHMAGGM2h0dHA6Ly9vY3NwMi5nbG9iYWxzaWduLmNv\n" +
                    "bS9nc29yZ2FuaXphdGlvbnZhbHNoYTJnMjBWBgNVHSAETzBNMEEGCSsGAQQBoDIB\n" +
                    "FDA0MDIGCCsGAQUFBwIBFiZodHRwczovL3d3dy5nbG9iYWxzaWduLmNvbS9yZXBv\n" +
                    "c2l0b3J5LzAIBgZngQwBAgIwCQYDVR0TBAIwADCBmQYDVR0RBIGRMIGOggsqLmpk\n" +
                    "cGF5LmNvbYILd2FuZ3lpbi5jb22CCWpyLmpkLmNvbYIQY2hpbmFiYW5rLmNvbS5j\n" +
                    "boILYmFpdGlhby5jb22CDSouYmFpdGlhby5jb22CEiouY2hpbmFiYW5rLmNvbS5j\n" +
                    "boINKi53YW5neWluLmNvbYILKi5qci5qZC5jb22CCWpkcGF5LmNvbTAdBgNVHSUE\n" +
                    "FjAUBggrBgEFBQcDAQYIKwYBBQUHAwIwHQYDVR0OBBYEFOeyb37b2P51j9RbE9v/\n" +
                    "kJ1JdBMqMB8GA1UdIwQYMBaAFJbeYfG9HBYpUxzAzH07gwBA5hp8MIIBBAYKKwYB\n" +
                    "BAHWeQIEAgSB9QSB8gDwAHYAu9nfvB+KcbWTlCOXqpJ7RzhXlQqrUugakJZkNo4e\n" +
                    "0YUAAAFj7WFcAgAABAMARzBFAiEAk4v9GK/wkN2LmnYDh4qtQRi4c6QSdAlsk9px\n" +
                    "YqsO4dkCIGCipd+g9qzj8d6cqI9RuF9GOs+n5KA0bER3B4c80fy3AHYAb1N2rDHw\n" +
                    "MRnYmQCkURX/dxUcEdkCwQApBo2yCJo32RMAAAFj7WFX4wAABAMARzBFAiEAqF+x\n" +
                    "W5XuQHrtxR+Rfu1u5lB2+UKW484RQlz6n3Igsa4CIAtA5SAUfx/H7Rdx15Fw4dL2\n" +
                    "UcxfnESV4/xws9LHuMpYMA0GCSqGSIb3DQEBCwUAA4IBAQANNGWpuQD60HIgOM+A\n" +
                    "SNk8B7aUFdFUPbQb/v9AcN66CSWJjJv/xVLQAUe8iKrYmVLQue/VQRGdxP27DiTn\n" +
                    "XDzcO";

            String header = "Pwngy9z/z8BMDyelUghAPzNR0SsoujxKLuncs43CA/g+o8Rkl5pTLC930EbZGpoCmiOUG+eycSFU\n" +
                    "FgCeYt0d1R0DOW7PFtqV+ZT57ZAhsG8+krZnhSEU5EiQAPAh/t5KUsUSB75sZlU958eV7u6qOzhh\n" +
                    "gHn3nmSU8PpdM0yf6zY=";

            String body = "ExBHbG9iYWxTaWduIG52LXNhMTwwOgYDVQQDEzNH\n" +
                    "bG9iYWxTaWduIE9yZ2FuaXphdGlvbiBWYWxpZGF0aW9uIENBIC0gU0hBMjU2IC0g\n" +
                    "RzIwHhcNMTgwNjExMDU0NjA4WhcNMTkwODAxMDc1MTA1WjCBgzELMAkGA1UEBhMC\n" +
                    "Q04xEDAOBgNVBAgTB0JlaWppbmcxEDAOBgNVBAcTB0JlaWppbmcxOjA4BgNVBAoT\n" +
                    "MUNoaW5hYmFuayBQYXltZW50cyAoQmVpamluZykgVGVjaG5vbG9neSBDby4sIEx0\n" +
                    "ZC4xFDASBgNVBAMMCyouamRwYXkuY29tMIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8A\n" +
                    "MIIBCgKCAQEAvWv9wlUIfQhDtpQXv0WO/U5mx/rgNmIkQq2haE1+eCiCZE+2Mann\n" +
                    "dwMxzU2JK/b4FAuHMiRTNnH0EGweqX9hpsjDQBhHu67a6fgaaH2W+Jo6TY0a2sJB\n" +
                    "ANBcLlmKMn/fJmVXXNNA+8+PlFChk8FLtzwOf4+871BJRsYlJ/wa0HuOYHZYuly7\n" +
                    "mSh1YQK7dRUrJDcIwMBVqZQYlKv5MfT7wahEOOiiVnBnYn0kKCXN9Q5j8ssTWw3g\n" +
                    "yVXg0pYuITHHxp2ZC2UygajX1vhD1+JHK7XnnugkAsKZAttWBolgyFlGrSYJuNob\n" +
                    "zA6BmTJWPLzJ7+0zlkNv+zYJOYoT4ucPZwIDAQABo4IDHTCCAxkwDgYDVR0PAQH/\n" +
                    "BAQDAgWgMIGgBggrBgEFBQcBAQSBkzCBkDBNBggrBgEFBQcwAoZBaHR0cDovL3Nl\n" +
                    "Y3VyZS5nbG9iYWxzaWduLmNvbS9jYWNlcnQvZ3Nvcmdhbml6YXRpb252YWxzaGEy\n" +
                    "ZzJyMS5jcnQwPwYIKwYBBQUHMAGGM2h0dHA6Ly9vY3NwMi5nbG9iYWxzaWduLmNv\n" +
                    "bS9nc29yZ2FuaXphdGlvbnZhbHNoYTJnMjBWBgNVHSAETzBNMEEGCSsGAQQBoDIB\n" +
                    "FDA0MDIGCCsGAQUFBwIBFiZodHRwczovL3d3dy5nbG9iYWxzaWduLmNvbS9yZXBv\n" +
                    "c2l0b3J5LzAIBgZngQwBAgIwCQYDVR0TBAIwADCBmQYDVR0RBIGRMIGOggsqLmpk\n" +
                    "cGF5LmNvbYILd2FuZ3lpbi5jb22CCWpyLmpkLmNvbYIQY2hpbmFiYW5rLmNvbS5j\n" +
                    "boILYmFpdGlhby5jb22CDSouYmFpdGlhby5jb22CEiouY2hpbmFiYW5rLmNvbS5j\n" +
                    "boINKi53YW5neWluLmNvbYILKi5qci5qZC5jb22CCWpkcGF5LmNvbTAdBgNVHSUE\n" +
                    "FjAUBggrBgEFBQcDAQYIKwYBBQUHAwIwHQYDVR0OBBYEFOeyb37b2P51j9RbE9v/\n" +
                    "kJ1JdBMqMB8GA1UdIwQYMBaAFJbeYfG9HBYpUxzAzH07gwBA5hp8MIIBBAYKKwYB\n" +
                    "BAHWeQIEAgSB9QSB8gDwAHYAu9nfvB+KcbWTlCOXqpJ7RzhXlQqrUugakJZkNo4e\n" +
                    "0YUAAAFj7WFcAgAABAMARzBFAiEAk4v9GK/wkN2LmnYDh4qtQRi4c6QSdAlsk9px\n" +
                    "YqsO4dkCIGCipd+g9qzj8d6cqI9RuF9GOs+n5KA0bER3B4c80fy3AHYAb1N2rDHw\n" +
                    "MRnYmQCkURX/dxUcEdkCwQApBo2yCJo32RMAAAFj7WFX4wAABAMARzBFAiEAqF+x\n" +
                    "W5XuQHrtxR+Rfu1u5lB2+UKW484RQlz6n3Igsa4CIAtA5SAUfx/H7Rdx15Fw4dL2\n" +
                    "UcxfnESV4/xws9LHuMpYMA0GCSqGSIb3DQEBCwUAA4IBAQANNGWpuQD60HIgOM+A\n" +
                    "SNk8B7aUFdFUPbQb/v9AcN66CSWJjJv/xVLQAUe8iKrYmVLQue/VQRGdxP27DiTn\n" +
                    "XDzcO76z7ZLWvf6NkORHZU86Y4Dv+66ph2XAtbYAbNIcKQVA051EJNaRMH69vZg2\n" +
                    "L3w5rGX8IkBV9Pi5cEAjTSNvE2tO0eue4HWrADORL7x69wmK1nLppYR4j";

            String generate = generate(body);
            System.out.println(generate);
            System.out.println("md5签名："+"11dad672cef1a710adb1aee20bc5b267");

            getResult(body);


            String sing = "5027cd2604b9c3d38808e236de0d595f";
            System.out.println("加密后的数据："+sing);



        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
