package com.infosky.common.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;

/**
 * 地址工具类：获取客户端ip、获取当前城市等
 * 
 * @author n004881
 */
public class AddUtils {

    // 新浪接口
    private static String SINAIPLOOKUPURI = "http://int.dpool.sina.com.cn/iplookup/iplookup.php?format=json&ip=";

    // 淘宝接口
    private static String TAOBAOLOOKUPURI = "http://ip.taobao.com/service/getIpInfo.php?ip=";

    /**
     * 获取客户端ip 获取客户端的IP地址的方法是：request.getRemoteAddr（），这种方法在大部分情况下都是有效的。但是在通过了Apache，Squid等反向代理软件就不能获取到客户端的真实IP地址了。
     * 如果使用了反向代理软件，将http://192.168.1.110：2046/ 的URL反向代理为 http://www.javapeixun.com.cn / 的URL时，
     * 用request.getRemoteAddr（）方法获取的IP地址是：127.0.0.1　或　192.168.1.110，而并不是客户端的真实IP。
     * 经过代理以后，由于在客户端和服务之间增加了中间层，因此服务器无法直接拿到客户端的IP，服务器端应用也无法直接通过转发请求的地址返回给客户端。 但是在转发请求的HTTP头信息中，增加了X－FORWARDED－FOR信息。用以跟踪原有的客户端IP地址和原来客户端请求的服务器地址。
     * 当我们访问http://www.javapeixun.com.cn /index.jsp/ 时，其实并不是我们浏览器真正访问到了服务器上的index.jsp文件， 而是先由代理服务器去访问http://192.168.1.110：2046/index.jsp
     * ，代理服务器再将访问到的结果返回给我们的浏览器， 因为是代理服务器去访问index.jsp的，所以index.jsp中通过request.getRemoteAddr（）的方法获取的IP实际上是代理服务器的地址，并不是客户端的IP地址。 有的时候
     * 返回的IP地址始终是unknown，也并不是如上所示的127.0.0.1　或　192.168.1.110了，而我访问http://192.168.1.110：2046/index.jsp 时，则能返回客户端的真实IP地址， 写了个方法去验证。原因出在了Squid上。squid.conf
     * 的配制文件　forwarded_for 项默认是为on，如果 forwarded_for 设成了 off 　则：X-Forwarded-For： unknown。 如果通过了多级反向代理的话，X-Forwarded-For的值并不止一个，而是一串Ｉｐ值
     * 那么则是取X-Forwarded-For中第一个非unknown的有效IP字符串.
     * 
     */
    public static String getClientIpAddr(HttpServletRequest request) {
        String ipAddress = null;
        // ipAddress =request.getRemoteAddr();
        ipAddress = request.getHeader("x-forwarded-for");
        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getRemoteAddr();
            ipAddress = ipAddress.equals("0:0:0:0:0:0:0:1") ? "127.0.0.1" : ipAddress;
            if (ipAddress.equals("127.0.0.1")) {
                // 根据网卡取本机配置的IP
                InetAddress inet = null;
                try {
                    inet = InetAddress.getLocalHost();
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                }
                ipAddress = inet.getHostAddress();
            }
        }

        // 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
        if (ipAddress != null && ipAddress.length() > 15) { // "***.***.***.***".length()
                                                            // = 15
            if (ipAddress.indexOf(",") > 0) {
                ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
            }
        }
        return ipAddress;
    }

    public static void main(String[] args) {
        try {
            String ip1 = getRemoteIP();
            System.out.println("myIP:" + ip1);
            String ip2 = getMyIPLocal();
            System.out.println("myLocalIP:" + ip2);
            String address = null;
            address = getCurrentCityName("58.213.152.250");
            System.out.println("====================");
            System.out.println(address);
            System.out.println(DecodeUnicode.decodeUnicode(address));
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }

    /**
     * 获取当前城市名称
     * 
     * 根据新浪、淘宝、饿了么等接口获取客户端当前城市
     * 
     * @param ip
     * @return
     * @throws Throwable
     */
    public static String getCurrentCityName(String ipAddress) throws Exception {
        String cityName = null;
        if (isInner(ipAddress)) {
            ipAddress = getRemoteIP();
        }
        if (StringUtils.isBlank(ipAddress)) {
            throw new Exception("ip地址不能为空");
        }
        if (!isboolIp(ipAddress)) throw new Exception("无效的ip地址" + ipAddress);
        try {
            cityName = getAddressInfoIP(ipAddress == null ? TAOBAOLOOKUPURI : TAOBAOLOOKUPURI + ipAddress);
            if (StringUtils.isNotBlank(cityName) && cityName.contains("city")) {// 淘宝数据格式
                return JSONObject.fromObject(cityName).getJSONObject("data").getString("city");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (StringUtils.isBlank(cityName)) {
            try {
                cityName = getAddressInfoIP(ipAddress == null ? SINAIPLOOKUPURI : SINAIPLOOKUPURI + ipAddress);
                if (StringUtils.isNotBlank(cityName) && cityName.contains("city")) {// 新浪数据格式
                    return JSONObject.fromObject(cityName).getString("city");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;

    }

    /**
     * 是否内网ip
     * 
     * @param ip
     * @return
     */
    public static boolean isInner(String ip) {
        String reg = "(10|172|192)\\.([0-1][0-9]{0,2}|[2][0-5]{0,2}|[3-9][0-9]{0,1})\\.([0-1][0-9]{0,2}|[2][0-5]{0,2}|[3-9][0-9]{0,1})\\.([0-1][0-9]{0,2}|[2][0-5]{0,2}|[3-9][0-9]{0,1})";
        Pattern p = Pattern.compile(reg);
        Matcher matcher = p.matcher(ip);
        return matcher.find();
    }

    /**
     * 判断是否为合法IP
     * 
     * @return the ip
     */
    public static boolean isboolIp(String ipAddress) {
        if (ipAddress.length() < 7 || ipAddress.length() > 15 || StringUtils.isBlank(ipAddress)) {
            return false;
        }
        String rexp = "([1-9]|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])(\\.(\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])){3}";
        Pattern pat = Pattern.compile(rexp);
        Matcher matcher = pat.matcher(ipAddress);
        return matcher.matches();
    }

    /**
     * 获取本地对外ip地址
     * 
     * @return
     * @throws IOException
     */
    public static String getRemoteIP() throws IOException {
        InputStream ins = null;
        try {
            URL url = new URL("http://1212.ip138.com/ic.asp");
            URLConnection con = url.openConnection();
            ins = con.getInputStream();
            InputStreamReader isReader = new InputStreamReader(ins, "GB2312");
            BufferedReader bReader = new BufferedReader(isReader);
            StringBuffer webContent = new StringBuffer();
            String str = null;
            while ((str = bReader.readLine()) != null) {
                webContent.append(str);
            }
            int start = webContent.indexOf("[") + 1;
            int end = webContent.indexOf("]");
            return webContent.substring(start, end);
        } finally {
            if (ins != null) {
                ins.close();
            }
        }
    }

    /**
     * 获取本机局域网IP
     * 
     * @return
     * @throws IOException
     */
    private static String getMyIPLocal() throws IOException {
        InetAddress ia = InetAddress.getLocalHost();
        return ia.getHostAddress();
    }

    /**
     * 根据对外ip获取地址详细信息
     * 
     * @return
     * @throws IOException
     */
    private static String getAddressInfoIP(String urlLink) throws IOException {
        InputStream ins = null;
        try {
            URL url = new URL(urlLink);
            URLConnection con = url.openConnection();
            ins = con.getInputStream();
            InputStreamReader isReader = new InputStreamReader(ins, "UTF-8");
            BufferedReader bReader = new BufferedReader(isReader);
            StringBuffer webContent = new StringBuffer();
            String str = null;
            while ((str = bReader.readLine()) != null) {
                webContent.append(str);
            }
            return webContent.toString();
        } finally {
            if (ins != null) {
                ins.close();
            }
        }
    }

}
