package test.加解密;

/**
 * @创建人 zhaojingen
 * @创建时间 2019/6/10
 * @描述
 */
import java.io.FileInputStream;
import java.security.KeyStore;
import java.security.PrivateKey;

import sun.misc.BASE64Encoder;

@SuppressWarnings("restriction")
public class SslKey {

    public static KeyStore getKeyStore(String keyStorePath, String password) throws Exception {
        FileInputStream is = new FileInputStream(keyStorePath);
        KeyStore ks = KeyStore.getInstance("JKS");
        ks.load(is, password.toCharArray());
        is.close();
        return ks;
    }

    public static PrivateKey getPrivateKey() {
        try {

            BASE64Encoder encoder = new BASE64Encoder();
            KeyStore ks = getKeyStore("D://test.keystore", "password");
            PrivateKey key = (PrivateKey) ks.getKey("*.test.com", "password".toCharArray());
            String encoded = encoder.encode(key.getEncoded());
            System.out.println("-----BEGIN RSA PRIVATE KEY-----");
            System.out.println(encoded);
            System.out.println("-----END RSA PRIVATE KEY-----");
            return key;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) {
        getPrivateKey();
    }

}
