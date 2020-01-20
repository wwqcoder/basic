package test.工具合集.加解密;

/**
 * @创建人 zhaojingen
 * @创建时间 2019/8/9
 * @描述
 */
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by lijunfu on 14-5-22.
 */
public class RsaUtil {

    public static final String RSA = "RSA";
    public static final String KEY_ALGORITHM_DETAIL = "RSA/ECB/PKCS1Padding";
    public static final String SIGNATURE_ALGORITHM = "MD5withRSA";//SHA1WithRSA 或者 MD5withRSA
    public static final String RSA_PUBLIC_KEY = "RSAPublicKey";
    public static final String RSA_PRIVATE_KEY = "RSAPrivateKey";

    /**
     * 用私钥对信息生成数字签名
     *
     * @param data       加密数据
     * @param privateKey 私钥
     * @return
     * @throws Exception
     */
    public static byte[] sign(byte[] data, String privateKey) throws Exception {
        // 解密由base64编码的私钥
        byte[] keyBytes = new BASE64Decoder().decodeBuffer(privateKey);

        // 构造PKCS8EncodedKeySpec对象
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);

        // KEY_ALGORITHM 指定的加密算法
        KeyFactory keyFactory = KeyFactory.getInstance(RSA);

        // 取私钥匙对象
        PrivateKey priKey = keyFactory.generatePrivate(pkcs8KeySpec);

        // 用私钥对信息生成数字签名
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initSign(priKey);
        signature.update(data);

        return signature.sign();
    }

    /**
     * 校验数字签名
     *
     * @param data      加密数据
     * @param publicKey 公钥
     * @param signByte  数字签名的byte
     * @return 校验成功返回true 失败返回false
     * @throws Exception
     */
    public static boolean verify(byte[] data, String publicKey, byte[] signByte)
            throws Exception {

        // 解密由base64编码的公钥
        byte[] keyBytes = new BASE64Decoder().decodeBuffer(publicKey);

        // 构造X509EncodedKeySpec对象
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);

        // KEY_ALGORITHM 指定的加密算法
        KeyFactory keyFactory = KeyFactory.getInstance(RSA);

        // 取公钥匙对象
        PublicKey pubKey = keyFactory.generatePublic(keySpec);

        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initVerify(pubKey);
        signature.update(data);

        // 验证签名是否正常
        return signature.verify(signByte);
    }

    /**
     * 解密<br>
     * 用私钥解密
     *
     * @param data
     * @param key
     * @return
     * @throws Exception
     */
    public static byte[] decryptByPrivateKey(byte[] data, String key)
            throws Exception {
        // 对密钥解密
        byte[] keyBytes = new BASE64Decoder().decodeBuffer(key);

        // 取得私钥
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(RSA);
        Key privateKey = keyFactory.generatePrivate(pkcs8KeySpec);

        // 对数据解密
        Cipher cipher = Cipher.getInstance(KEY_ALGORITHM_DETAIL);
        cipher.init(Cipher.DECRYPT_MODE, privateKey);

        return cipher.doFinal(data);
    }

    /**
     * 解密<br>
     * 用公钥解密
     *
     * @param data
     * @param key
     * @return
     * @throws Exception
     */
    public static byte[] decryptByPublicKey(byte[] data, String key)
            throws Exception {
        // 对密钥解密
        byte[] keyBytes = new BASE64Decoder().decodeBuffer(key);

        // 取得公钥
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(RSA);
        Key publicKey = keyFactory.generatePublic(x509KeySpec);

        // 对数据解密
        Cipher cipher = Cipher.getInstance(KEY_ALGORITHM_DETAIL);
        cipher.init(Cipher.DECRYPT_MODE, publicKey);

        return cipher.doFinal(data);
    }

    /**
     * 加密<br>
     * 用公钥加密
     *
     * @param data
     * @param key
     * @return
     * @throws Exception
     */
    public static byte[] encryptByPublicKey(byte[] data, String key)
            throws Exception {
        // 对公钥解密
        byte[] keyBytes = new BASE64Decoder().decodeBuffer(key);

        // 取得公钥
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(RSA);
        Key publicKey = keyFactory.generatePublic(x509KeySpec);

        // 对数据加密
        Cipher cipher = Cipher.getInstance(KEY_ALGORITHM_DETAIL);
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);

        return cipher.doFinal(data);
    }


    /**
     * 加密<br>
     * 用私钥加密
     *
     * @param data
     * @param key
     * @return
     * @throws Exception
     */
    public static byte[] encryptByPrivateKey(byte[] data, String key)
            throws Exception {
        // 对密钥解密
        byte[] keyBytes = new BASE64Decoder().decodeBuffer(key);

        // 取得私钥
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(RSA);
        Key privateKey = keyFactory.generatePrivate(pkcs8KeySpec);

        // 对数据加密
        Cipher cipher = Cipher.getInstance(KEY_ALGORITHM_DETAIL);
        cipher.init(Cipher.ENCRYPT_MODE, privateKey);

        return cipher.doFinal(data);
    }

    /**
     * 取得私钥
     *
     * @param keyMap
     * @return
     * @throws Exception
     */
    public static String getPrivateKey(Map<String, Object> keyMap)
            throws Exception {
        Key key = (Key) keyMap.get(RSA_PRIVATE_KEY);
        byte[] base64bts = new BASE64Encoder().encode(key.getEncoded()).getBytes("UTF-8");
        return new String(base64bts, "UTF-8");
    }

    /**
     * 取得公钥
     *
     * @param keyMap
     * @return
     * @throws Exception
     */
    public static String getPublicKey(Map<String, Object> keyMap)
            throws Exception {
        Key key = (Key) keyMap.get(RSA_PUBLIC_KEY);
        byte[] base64bts = new BASE64Encoder().encode(key.getEncoded()).getBytes("UTF-8");
        return new String(base64bts, "UTF-8");
    }

    /**
     * 初始化密钥
     *
     * @return
     * @throws Exception
     */
    public static Map<String, Object> initKey() throws Exception {
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(RSA);
        keyPairGen.initialize(1024);
        KeyPair keyPair = keyPairGen.generateKeyPair();
        // 公钥
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        // 私钥
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        Map<String, Object> keyMap = new HashMap<String, Object>(2);
        keyMap.put(RSA_PUBLIC_KEY, publicKey);
        keyMap.put(RSA_PRIVATE_KEY, privateKey);
        return keyMap;
    }

    public static void main(String[] args) {
        try {
            // 生成公私钥对
            Map<String, Object> map = RsaUtil.initKey();
            // 获取公钥
            RSAPublicKey publicKey = (RSAPublicKey) map.get(RSA_PUBLIC_KEY);
            // 获取私钥
            RSAPrivateKey privateKey = (RSAPrivateKey) map.get(RSA_PRIVATE_KEY);

            System.out.println("********************************");
            System.out.println("公钥加密算法：" + publicKey.getAlgorithm());
            System.out.println("公钥加密格式：" + publicKey.getFormat());

            System.out.println("私钥加密算法：" + privateKey.getAlgorithm());
            System.out.println("私钥加密格式：" + privateKey.getFormat());
            System.out.println("********************************");


            // 使用BASE64对公私钥对进行加密
//            String pk = RsaUtil.getPublicKey(map);
//            String sk = RsaUtil.getPrivateKey(map);
//            String pk = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCPjnZmw8vEbWNyMkz4Pa+bEIXtbirbLC7FEccq6WtBjCIpkgqxQyehgJ5/ycVOJi88FT3hlSP7GPTzdf0DjaAiwUPRNRX2b076aSjyj+wVrdXEI6z4e4SBbqwPavwCd6SbFtwjKJlmglcHo0eDc/Iw946OvCJxd8gmifTlNxS3UwIDAQAB";
//            String sk = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAI+OdmbDy8RtY3IyTPg9r5sQhe1uKtssLsURxyrpa0GMIimSCrFDJ6GAnn/JxU4mLzwVPeGVI/sY9PN1/QONoCLBQ9E1FfZvTvppKPKP7BWt1cQjrPh7hIFurA9q/AJ3pJsW3CMomWaCVwejR4Nz8jD3jo68InF3yCaJ9OU3FLdTAgMBAAECgYBp4ElE650KZx8UJzMLVvt/4wTTow/qi8CGyeDZrkPTmRXNEQ/fwsak32aGmvpw88qchpIYINXjqHloYhnUGA0E07mIRRbILSkLQlCajgVtWe9oh7nASFGpBdW/jKFrBpFldozGGhSRtehHIzni1V10ooNEnZpnEkprCA7WijNmuQJBAO1+r8NkGu2VXuV5Fjb4vG7mMpFciOZz+muUQK/DN4O9yBbBeXONUkFhXSz/aJBPwpZKlxXCGsnFsuYEeVu9No8CQQCavfxh05ll0/BVd110KHrVYAo0y3ME7fIR/NNn8SEipSo7XS0zcQS3i58phsw/VDmYs/7ZLdLUB5UKxNLgU3T9AkEAytu6cBBSu/spmqLKKdxev+9a5DUBLq+ECF4Svs7l3V6+yUkrX1soFnZ+6w+ilhm64TsHQGuTDCQVQkoyCv1c2wJAfka1s3tCvhcjFAuxhr4V5xRVn9m6xfYLSeSA/FyJBsWz3ffekBEVoVbeDrxC5xcrXVLdkItVddOuK7iMwaU5XQJAWpT+huR3L9JPR6I7b481sat0YuCIU6rLekhAN+jezKKSyGRh869MDasqCE8Eqp3HMsSVDy7rR/wJVMWtgroyMw==";

            String pk = "MIIBIDANBgkqhkiG9w0BAQEFAAOCAQ0AMIIBCAKCAQEA4wexmo0CWchLGe9SNi4t" +
                    "JH2uAknx8k9OsOsfP9pWYZ5D3rRJKuXsvJhy2ewcFJBGw1sEUsUp+OPah57Lwl5X" +
                    "WZIzuwUpnVX3UXAD1yAly4+GJAHHJqbyo6oGQOT+lG7JgdPmlQvLqtizxJJiYDz1" +
                    "QP/0384XcDBsUzPKFqvtlWZ9yxLUeBURIoGMbaCD6H60rI4KwpMtf1ApkXGu7Vdw" +
                    "2lVnyETJBp4qwCuA+gQqV0908rt7Ua6e11mNZHud1XYePa9XCoIxPEt/IoKtUxKT" +
                    "2+gQ2pHVuGwYQXjfp8cmIdik8vhQWRF4/zX/VDDIXEXlOA7DcQ1hrNtAv7MSqyyO" +
                    "2QIBAw==";


            String sk = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQDjB7GajQJZyEsZ" +
                    "71I2Li0kfa4CSfHyT06w6x8/2lZhnkPetEkq5ey8mHLZ7BwUkEbDWwRSxSn449qH" +
                    "nsvCXldZkjO7BSmdVfdRcAPXICXLj4YkAccmpvKjqgZA5P6UbsmB0+aVC8uq2LPE" +
                    "kmJgPPVA//TfzhdwMGxTM8oWq+2VZn3LEtR4FREigYxtoIPofrSsjgrCky1/UCmR" +
                    "ca7tV3DaVWfIRMkGnirAK4D6BCpXT3Tyu3tRrp7XWY1ke53Vdh49r1cKgjE8S38i" +
                    "gq1TEpPb6BDakdW4bBhBeN+nxyYh2KTy+FBZEXj/Nf9UMMhcReU4DsNxDWGs20C/" +
                    "sxKrLI7ZAgEDAoIBAQCXWnZnCKw72ty79OF5dB4YU8lW2/ahijR18hTVPDmWaYKU" +
                    "eDDHQ/MoZaHmnWgNtYSCPK2Mg3FQl+cFFIfW6Y+RDCJ8rhu+OU+LoAKPasPdCllt" +
                    "VoTEb0xtHAQrQ1Ri9IZWjURjXTJx5c0ttuxAKKOAqqM/3rpKyvLiIoa5x/O47b0W" +
                    "Q4iejSSsvrlHwlWvR2Nm/Ctrk9EBNjWu79wsI3ZLmJX6t500hAzp1w3vWgjorg7l" +
                    "E9/0Deo/D7k5AjwloXSvi0IgHJx0ORg1LmImZAsqfFSl0jrX2/4JUAsTFiz+tsOZ" +
                    "FFGfQANR2HmO73N+8yZqMSs4eOY+ZtYny/drhdUjAoGBAPHSdd2GDIDMQ54f+upz" +
                    "TUIIR7IDyImvkNLojRf8TUE+MOSeHGdlK7FcMSC27RT8kmFBlUP016onJff8PJo3" +
                    "R8kfFRJ3fRf/EnyWRJq/Ib65pawvKF+oKdumAayJNtkMJEMWr0gp2b4BUjx0ahaW" +
                    "nO7foVG5Y7tyBvrmS6/YdiRnAoGBAPBXN6oENNlTH9hiAhjuRl2JzBedbOpN7gYi" +
                    "fMyu1P4qv6IyFPXSrGYFN8tcEAf9t/1ZiGduwhVRm/+Su6llvCYXSWFi2i6O4148" +
                    "eH9aWsRih+Wyrh3MeD+NfyKB7wmXojx2qo3AV7YDHwyJX4SHPDy5I7Di9EzdOgSd" +
                    "tW+xbaq/AoGBAKE2+T5ZXasy176//JxM3iwFhSFX2wZ1CzdFs2VS3it+y0MUEu+Y" +
                    "x8uSy2skng39tuuBDi1N5RwaGU/9fbwk2oYUuLb6U2VUtv25gxHUwSnRGR10xZUa" +
                    "xpJuq8hbeeYIGCy5yjAb5n6rjChNnA8PE0nqa4vQ7SehWfyZh8qQTsLvAoGBAKA6" +
                    "JRwCzeY3apBBVrtJhD5b3WUTnfGJSVlsUzMfOKlx1RbMDfk3HZlYz9zoCq/+eqjm" +
                    "Wu+fLA42Z/+3J8ZD0sQPhkDskXRfQj7S+v+RkdhBr+53Hr6IUCpeVMGr9LEPwX2k" +
                    "cbPVj86sv12w6lhaKCh7bSCXTYiTfAMTzkp2SRx/AoGAQtTV8JzPi3D1qttOpmFl" +
                    "GhvEA47nLMofkNfUIb2qLUtpyZ4UWu5HrDYZ5T42Yt4rsLEkJ4QH1ZlXhrbtYWWc" +
                    "vPSHddMA/TwIJfVc20Eg1YuL/vpRL9vTzCX5VXDnnrjGzd6ps1K8EKAqq9lg5VFg" +
                    "X4mYmTREAhDJ7xqgnGeV/2U=";


            System.out.println("公钥长度=：" + pk.length());
            System.out.println("公钥：" + pk);

            System.out.println("私钥长度=：" + sk.length());
            System.out.println("私钥：" + sk);
            System.out.println("********************************");

            // 公钥加密的字符串
            String str = Md5Util.md5Lower32("","This is a test url:https://wangyin.com/wepay/web/pay","");
            System.out.println("公钥需要加密的字符串：" + str);
            System.out.println("********************************");

            byte[] publicKeyEncryptBts = RsaUtil.encryptByPublicKey(str.getBytes("UTF-8"), pk);
            byte[] base64EncodeBts = new BASE64Encoder().encode(publicKeyEncryptBts).getBytes("UTF-8");
            String base64EncodeStr = new String(base64EncodeBts, "UTF-8");
            System.out.println("公钥加密后的数据：" + base64EncodeStr);
            System.out.println("********************************");

            // 私钥进行解密
            byte[] base64DecodeBts = new BASE64Decoder().decodeBuffer(base64EncodeStr);
            byte[] sks = RsaUtil.decryptByPrivateKey(base64DecodeBts, sk);
            String skss = new String(sks, "UTF-8");
            System.out.println("私钥解密后的数据：" + skss);
            System.out.println("********************************");

            // 私钥对字符串加密
            byte[] privateKeyEncryptBts = str.getBytes("UTF-8");
            System.out.println("私钥需要加密的字符串：" + str);
            System.out.println("********************************");

            byte[] newsks = RsaUtil.encryptByPrivateKey(privateKeyEncryptBts, sk);
            String newskss = new BASE64Encoder().encode(newsks);

            System.out.println("私钥加密后的数据：" + newskss);
            System.out.println("********************************");

            // 公钥对数据进行解密
            byte[] newpks = RsaUtil.decryptByPublicKey(newsks, pk);
            String newpkss = new String(newpks, "UTF-8");
            System.out.println("公钥对私钥数据解密：" + newpkss);

            // 私钥对加密数据进行签名
            byte[] result1 = RsaUtil.sign(str.getBytes("UTF-8"), sk);
            System.out.println("私钥对数据签名：" + Arrays.toString(result1));

            // 公钥对加密数据进行验签
            boolean flag = RsaUtil.verify(str.getBytes("UTF-8"), pk, result1);
            System.out.println("公钥对数据验签：" + flag);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
