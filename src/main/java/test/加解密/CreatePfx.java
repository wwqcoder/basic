package test.加解密;

/**
 * @创建人 zhaojingen
 * @创建时间 2019/6/10
 * @描述 使用java工具类从密钥库导出pfx私钥：
 */
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.Key;
import java.security.KeyStore;
import java.security.cert.Certificate;
import java.util.Enumeration;

public class CreatePfx {
    public static void main(String[] args) throws  Exception{
        CreatePfx.coverToPfx();
    }
    /**
     * 将keystore转为pfx
     *
     * */
     /* @param keyStoreFile 生成的文件名和路径*/
    /* @param pfxPsw 密码*/
    /*  @param pfxFile 原文件路径及名称*/

    public static void coverToPfx() throws Exception {
        String keyStoreFile = "D:/test.keystore";
        String pfxPsw = "password";
        String pfxFile = "D:/test.pfx";

        KeyStore inputKeyStore = null;
        FileInputStream input = null;
        FileOutputStream output = null;
        String keyAlias = "";
        try {
            inputKeyStore = KeyStore.getInstance("JKS");
            input = new FileInputStream(keyStoreFile);
            char[] password = null;
            if ((pfxPsw == null) || pfxPsw.trim().equals("")) {
                password = null;
            } else {
                password = pfxPsw.toCharArray();
            }
            inputKeyStore.load(input, password);
            KeyStore outputKeyStore = KeyStore.getInstance("PKCS12");
            outputKeyStore.load(null, pfxPsw.toCharArray());
            Enumeration enums = inputKeyStore.aliases();
            while (enums.hasMoreElements()) {
                keyAlias = (String) enums.nextElement();
                System.out.println("alias=[" + keyAlias + "]");
                if (inputKeyStore.isKeyEntry(keyAlias)) {
                    Key key = inputKeyStore.getKey(keyAlias, password);
                    Certificate[] certChain = inputKeyStore.getCertificateChain(keyAlias);
                    outputKeyStore.setKeyEntry(keyAlias, key, pfxPsw.toCharArray(), certChain);
                }
            }
            output = new FileOutputStream(pfxFile);
            outputKeyStore.store(output, password);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
            }
            if (output != null) {
                try {
                    output.close();
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
    }

}
