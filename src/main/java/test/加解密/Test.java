package test.加解密;

/**
 * @创建人 zhaojingen
 * @创建时间 2019/6/10
 * @描述
 */
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.KeyStore;
import java.security.PublicKey;
import java.security.cert.Certificate;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;

import javax.security.cert.CertificateException;
import javax.security.cert.X509Certificate;

public class Test {
    public static void main(String[] args)
            throws CertificateException, IOException, javax.security.cert.CertificateException {
        File pfx = new File("D:\\test.pfx");
        String privKeyPswdString = "password";
        FileInputStream fileInputStream = new FileInputStream(pfx);
        File cer = new File("D:\\test.cer");
        FileInputStream fileInputStreamc = new FileInputStream(cer);
        getPrivateKeyInfo(fileInputStream, privKeyPswdString);
        getPublicKeyInfo(fileInputStreamc);
    }

    /**
     * 传入私钥数据流和密码 获取私钥有效时间
     *
     * @Title: getPrivateKeyInfo
     * @Description: fileInputStream 私钥文件数据流
     * privKeyPswdString 私钥密码
     * Mr.lu(lushunde.com)
     * @Date 2018年3月9日 上午12:24:37
     */
    public static void getPrivateKeyInfo(FileInputStream fileInputStream, String privKeyPswdString) {

        String keyAlias = null;
        try {
            KeyStore keyStore = KeyStore.getInstance("PKCS12");

            char[] nPassword = null;
            if ((privKeyPswdString == null) || privKeyPswdString.trim().equals("")) {
                nPassword = null;
            } else {
                nPassword = privKeyPswdString.toCharArray();
            }
            keyStore.load(fileInputStream, nPassword);
            fileInputStream.close();

            Enumeration<String> enumeration = keyStore.aliases();

            if (enumeration.hasMoreElements()) {
                keyAlias = enumeration.nextElement();
            }

            Certificate certificate = keyStore.getCertificate(keyAlias);

            PublicKey publicKey = certificate.getPublicKey();
            X509Certificate cert = X509Certificate.getInstance(certificate.getEncoded());
            Date notBefore = cert.getNotBefore();
            Date notAfter = cert.getNotAfter();
            System.out.println("=============私钥日期================");
            System.out.println(new SimpleDateFormat("yyyy-MM-dd").format(notBefore));
            System.out.println(new SimpleDateFormat("yyyy-MM-dd").format(notAfter));

            System.out.println("=============私钥日期================");

        } catch (Exception e) {
            System.out.println(e);
        }

    }

    public static void getPublicKeyInfo(FileInputStream fileInputStream) {
        try {

            X509Certificate cert = X509Certificate.getInstance(fileInputStream);
            PublicKey publicKey = cert.getPublicKey();
            Date notBefore = cert.getNotBefore();
            Date notAfter = cert.getNotAfter();
            // BASE64Encoder base64Encoder = new BASE64Encoder();
// String publicKeyString = base64Encoder.encode(publicKey.getEncoded());
            System.out.println("—————–公钥——————–");
            System.out.println(new SimpleDateFormat("yyyy - MM - dd").format(notBefore));
            System.out.println(new SimpleDateFormat("yyyy - MM - dd").format(notAfter));
            System.out.println("—————–公钥——————–");
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}

