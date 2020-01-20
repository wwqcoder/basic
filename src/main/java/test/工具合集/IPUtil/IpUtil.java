package test.工具合集.IPUtil;

/**
 * @创建人 zhaojingen
 * @创建时间 2019/8/9
 * @描述
 */
import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;
import java.util.regex.Pattern;

/**
 * Created by lijunfu on 14-5-22.
 */
public class IpUtil {

    private final static String UNKONW = "unknown";
    private final static String X_FORWARDED_FOR = "x-forwarded-for";
    private final static String PROXY_CLIENT_IP = "Proxy-Client-IP";
    private final static String WL_PROXY_CLIENT_IP = "WL-Proxy-Client-IP";
    private static volatile InetAddress LOCAL_ADDRESS = null;
    public static final String LOCALHOST = "127.0.0.1";
    public static final String ANYHOST = "0.0.0.0";
    private static final Pattern IP_PATTERN = Pattern.compile("\\d{1,3}(\\.\\d{1,3}){3,5}$");
    public static final String LOCAL_IP = getLocalHost();

    /**
     * 获取IP地址
     *
     * @param request
     * @return
     */
    public static String getIp(HttpServletRequest request) {
        String ip = null;
        try {
            ip = request.getHeader(X_FORWARDED_FOR);
            if (ip == null || ip.length() == 0 || UNKONW.equalsIgnoreCase(ip)) {
                ip = request.getHeader(PROXY_CLIENT_IP);
            }
            if (ip == null || ip.length() == 0 || UNKONW.equalsIgnoreCase(ip)) {
                ip = request.getHeader(WL_PROXY_CLIENT_IP);
            }
            if (ip == null || ip.length() == 0 || UNKONW.equalsIgnoreCase(ip)) {
                ip = request.getRemoteAddr();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        String[] ipArr = ip.split(",");
        if (ipArr.length > 1) {
            ip = ipArr[0];
        }
        return ip;
    }

    /**
     * 遍历本地网卡，返回第一个合理的IP。
     *
     * @return 本地网卡IP
     */
    public static InetAddress getLocalAddress() {
        if (LOCAL_ADDRESS != null)
        { return LOCAL_ADDRESS;}
        InetAddress localAddress = getLocalAddress0();
        LOCAL_ADDRESS = localAddress;
        return localAddress;
    }

    public static String getLocalHost() {
        InetAddress address = getLocalAddress();
        return address == null ? LOCALHOST : address.getHostAddress();
    }

    private static InetAddress getLocalAddress0() {
        InetAddress localAddress = null;
        try {
            localAddress = InetAddress.getLocalHost();
            if (isValidAddress(localAddress)) {
                return localAddress;
            }
        } catch (Throwable e) {
//            System.out.println("Failed to retrieving ip address, " + e.getMessage());
        }
        try {
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            if (interfaces != null) {
                while (interfaces.hasMoreElements()) {
                    try {
                        NetworkInterface network = interfaces.nextElement();
                        Enumeration<InetAddress> addresses = network.getInetAddresses();
                        if (addresses != null) {
                            while (addresses.hasMoreElements()) {
                                try {
                                    InetAddress address = addresses.nextElement();
                                    if (isValidAddress(address)) {
                                        return address;
                                    }
                                } catch (Throwable e) {
//                                    System.out.println("Failed to retrieving ip address, " + e.getMessage());
                                }
                            }
                        }
                    } catch (Throwable e) {
//                        System.out.println("Failed to retrieving ip address, " + e.getMessage());
                    }
                }
            }
        } catch (Throwable e) {
//            System.out.println("Failed to retrieving ip address, " + e.getMessage());
        }
//        System.out.println("Could not get local host ip address, will use 127.0.0.1 instead.");
        return localAddress;
    }

    private static boolean isValidAddress(InetAddress address) {
        if (address == null || address.isLoopbackAddress())
        { return false;}
        String name = address.getHostAddress();
        return (name != null
                && !ANYHOST.equals(name)
                && !LOCALHOST.equals(name)
                && IP_PATTERN.matcher(name).matches());
    }

}
