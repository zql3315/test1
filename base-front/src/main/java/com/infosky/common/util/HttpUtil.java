package com.infosky.common.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.infosky.common.constant.CharacterConstant;
import com.infosky.common.util.file.FileUtil;

/**
 * 
 * http工具类
 * 
 * @author n004881
 *
 */
public class HttpUtil {

    private static Logger log = LoggerFactory.getLogger(HttpUtil.class);

    /**
     * post请求
     * 
     * @param url
     * @param param
     * @return
     */
    public static String sendPost(String url, String param) {
        log.debug("========post请求地址：" + url);
        log.debug("========post请求参数为：" + param);
        //		log.debug("========post请求参数为："+URLEncoder.encode(param,"utf-8"));
        URL postUrl;
        StringBuffer sb = new StringBuffer();
        OutputStreamWriter out = null;
        BufferedReader reader = null;
        HttpURLConnection connection;
        try {
            postUrl = new URL(url);
            connection = (HttpURLConnection) postUrl.openConnection();
            connection.setDoOutput(true);
            connection.setDoInput(true);
            // 必须注意此处需要设置UserAgent，否则google会返回403
            connection.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.0)");
            connection.setRequestProperty("content-type", "application/x-www-form-urlencoded");
            connection.setRequestProperty("Accept-Charset", "UTF-8");
            connection.setRequestMethod("POST");
            connection.setUseCaches(false);
            out = new OutputStreamWriter(connection.getOutputStream(), "UTF-8");
            out.write(param);
            out.flush();
            out.close();
            reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            try {
                JSONObject json = JSONObject.fromObject(sb.toString());
                log.debug("========post请求响应为：" + json.toString());
            } catch (Exception e) {
                log.debug("===error=====post请求响应为：" + sb.toString());
            }
            reader.close();
            connection.disconnect();
        } catch (MalformedURLException e1) {
            e1.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (out != null) try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (reader != null) try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }

    /**
     * get请求
     * 
     * @param url
     * @return
     */
    public static String sendGet(String url) {
        log.debug("========get请求地址：" + url);
        URL postUrl;
        StringBuffer sb = new StringBuffer();
        OutputStreamWriter out = null;
        BufferedReader reader = null;
        HttpURLConnection connection;
        try {
            postUrl = new URL(url);
            connection = (HttpURLConnection) postUrl.openConnection();
            connection.setDoOutput(true);
            connection.setDoInput(true);
            reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
            String line;
            sb = new StringBuffer();
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            try {
                JSONObject json = JSONObject.fromObject(sb.toString());
                log.debug("========get请求响应为：" + json.toString());
            } catch (Exception e) {
                log.info("===error=====post请求响应为：" + sb.toString());
            }
            reader.close();
            connection.disconnect();
        } catch (MalformedURLException e1) {
            e1.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (out != null) try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (reader != null) try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }

    /**
     * 下载文件
     * 
     * @param url
     * @return
     */
    public static boolean downFile(String url, String localFilePath) {
        URL urlfile;
        StringBuffer sb = new StringBuffer();
        OutputStreamWriter out = null;
        BufferedReader reader = null;
        HttpURLConnection connection;
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        FileUtil.createFile(localFilePath);
        File savePath = new File(localFilePath);
        try {
            urlfile = new URL(url);
            connection = (HttpURLConnection) urlfile.openConnection();
            connection.setDoOutput(true);
            connection.setDoInput(true);
            // 必须注意此处需要设置UserAgent，否则google会返回403
            connection.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.0)");
            connection.setRequestProperty("content-type", "application/x-www-form-urlencoded");
            connection.setRequestProperty("Accept-Charset", CharacterConstant.UTF8);
            connection.connect();
            Map<String, List<String>> map = connection.getHeaderFields();
            int flag = 0;
            for (Map.Entry<String, List<String>> entry : map.entrySet()) {
                if (entry != null && entry.getValue() != null && entry.getValue().contains("image/jpeg")) {
                    flag = 1;
                    break;
                }
            }
            if (flag == 1) {
                bis = new BufferedInputStream(connection.getInputStream());
                bos = new BufferedOutputStream(new FileOutputStream(savePath));
                int len = 2048;
                byte[] b = new byte[len];
                while ((len = bis.read(b)) != -1) {
                    bos.write(b, 0, len);
                }
                bos.flush();
                bis.close();
                return true;
            } else {
                reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), CharacterConstant.UTF8));
                String line;
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }
            }

            connection.disconnect();
        } catch (MalformedURLException e1) {
            e1.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (bis != null) {
                try {
                    bis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }
}
