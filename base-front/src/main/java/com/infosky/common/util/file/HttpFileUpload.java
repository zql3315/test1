package com.infosky.common.util.file;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.infosky.common.util.HTTPContentTypeUtil;

/**
 * 
 * 只是写的一个示例，filePath,和FileName根据需要进行调整。
 */
public class HttpFileUpload {

    static Logger log = LoggerFactory.getLogger(HttpFileUpload.class);

    /**
     * @param args
     */
    public static void main(String[] args) {
        String filePath = "D:\\server.keystore";
        // String url = "http://y.archermind.com/xtms/xde/fileupload/fileupload/";
        //        String url = "http://10.20.81.63:8080/hj-manager/fileUploads?folderName=wechathead";
        //        String url = "http://10.20.81.63:8080/hn-manager/upload/fileUpload?folderName=test";
        //        String url = "http://10.20.81.55:8080/pksb-wechat/fileUpload?folderName=test&type=file";
        String url = "http://mp.wep.cn/pksb-wechat/fileUpload?folderName=test&type=file";
        //        System.out.println("================");
        //        String ret = uploadHttpPost(filePath, url);
        //        System.out.println("================" + JSONObject.fromObject(ret).toString());
        Map<String, String> paramtMap = new HashMap<String, String>();
        paramtMap.put("folderName", "wechathead");
        Map<String, String> fileMap = new HashMap<String, String>();
        fileMap.put("file", filePath);
        String ret = formUpload(url, fileMap);
        System.out.println(ret);
    }

    /**
     * 上传文件
     * 
     * @param urlStr 上传接口地址
     * @param fileMap 文件地址（）
     * @return
     */
    public static String formUpload(String urlStr, String filePath) {
        Map<String, String> fileMap = new HashMap<String, String>();
        fileMap.put("file", filePath);
        return formUpload(urlStr, fileMap, null);
    }

    /**
     * 多文件上传文件
     * 
     * @param urlStr 上传接口地址
     * @param fileMap 多文件地址
     * @return
     */
    public static String formUpload(String urlStr, Map<String, String> fileMap) {
        return formUpload(urlStr, fileMap, null);
    }

    /**
     * 上传文件
     * 
     * @param urlStr 上传地址
     * @param fileMap
     * @param paramtMap 上传参数
     * @return
     */
    public static String formUpload(String urlStr, Map<String, String> fileMap, Map<String, String> paramtMap) {
        String res = "";
        HttpURLConnection conn = null;
        DataInputStream in = null;
        OutputStream out = null;
        BufferedReader reader = null;
        String BOUNDARY = "---------------------------123821742118716"; // boundary就是request头和上传文件内容的分隔符
        try {
            URL url = new URL(urlStr);
            conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(30000);
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 6.1; zh-CN; rv:1.9.2.6)");
            conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + BOUNDARY);

            out = new DataOutputStream(conn.getOutputStream());
            // text
            if (paramtMap != null) {
                StringBuffer strBuf = new StringBuffer();
                Iterator<Map.Entry<String, String>> iter = paramtMap.entrySet().iterator();
                while (iter.hasNext()) {
                    Map.Entry<String, String> entry = iter.next();
                    String inputName = (String) entry.getKey();
                    String inputValue = (String) entry.getValue();
                    if (inputValue == null) {
                        continue;
                    }
                    strBuf.append("\r\n").append("--").append(BOUNDARY).append("\r\n");
                    strBuf.append("Content-Disposition: form-data; name=\"" + inputName + "\"\r\n\r\n");
                    strBuf.append(inputValue);
                }
                out.write(strBuf.toString().getBytes());
            }

            // file
            if (fileMap != null) {
                Iterator<Map.Entry<String, String>> iter = fileMap.entrySet().iterator();
                while (iter.hasNext()) {
                    Map.Entry<String, String> entry = iter.next();
                    String inputName = (String) entry.getKey();
                    String inputValue = (String) entry.getValue();
                    if (inputValue == null) {
                        continue;
                    }
                    File file = new File(inputValue);
                    String filename = file.getName();
                    String contentType = HTTPContentTypeUtil.readValue(filename.substring(filename.lastIndexOf("."), filename.length()));
                    if (StringUtils.isBlank(contentType)) contentType = "application/octet-stream";

                    StringBuffer strBuf = new StringBuffer();
                    strBuf.append("\r\n").append("--").append(BOUNDARY).append("\r\n");
                    strBuf.append("Content-Disposition: form-data; name=\"" + inputName + "\"; filename=\"" + filename + "\"\r\n");
                    // strBuf.append("Content-Type:" + contentType + "\r\n\r\n");
                    strBuf.append("Content-Type: ");
                    strBuf.append(contentType);
                    strBuf.append(" \r\n\r\n");

                    out.write(strBuf.toString().getBytes());

                    in = new DataInputStream(new FileInputStream(file));
                    int bytes = 0;
                    byte[] bufferOut = new byte[1024];
                    while ((bytes = in.read(bufferOut)) != -1) {
                        out.write(bufferOut, 0, bytes);
                    }
                }
            }

            byte[] endData = ("\r\n--" + BOUNDARY + "--\r\n").getBytes();
            out.write(endData);
            out.flush();

            // 读取返回数据
            StringBuffer strBuf = new StringBuffer();
            reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = null;
            while ((line = reader.readLine()) != null) {
                strBuf.append(line).append("\n");
            }
            res = strBuf.toString();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                conn.disconnect();
                conn = null;
            }
            try {
                if (out != null) out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (in != null) in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (reader != null) reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return res;
    }

    //  HttpPost方式
    public static String uploadHttpPost(String localFile, String url) {
        CloseableHttpClient httpClient = null;
        CloseableHttpResponse response = null;
        try {
            httpClient = HttpClients.createDefault();
            // 把一个普通参数和文件上传给下面这个地址 是一个servlet
            HttpPost httpPost = new HttpPost(url);
            // 把文件转换成流对象FileBody
            FileBody bin = new FileBody(new File(localFile));

            MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder.create();
            // 相当于<input type="file" name="file"/>
            multipartEntityBuilder.addPart("file", bin);
            // 相当于<input type="text" name="userName" value=userName>
            StringBody userName = new StringBody("Scott", ContentType.create("text/plain", Consts.UTF_8));
            multipartEntityBuilder.addPart("userName", userName);
            // 相当于<input type="text" name="pass" value=pass>
            StringBody password = new StringBody("123456", ContentType.create("text/plain", Consts.UTF_8));
            multipartEntityBuilder.addPart("pass", password);
            HttpEntity reqEntity = multipartEntityBuilder.build();
            httpPost.setEntity(reqEntity);
            // 发起请求 并返回请求的响应
            response = httpClient.execute(httpPost);
            log.debug("The response value of token:" + response.getFirstHeader("token"));
            // 获取响应对象
            HttpEntity resEntity = response.getEntity();
            if (resEntity != null) {
                // 打印响应长度
                log.debug("Response content length: " + resEntity.getContentLength());
                String result = EntityUtils.toString(resEntity, Charset.forName("UTF-8"));
                // 打印响应内容
                log.debug(result);
                return result;
            }
            // 销毁
            EntityUtils.consume(resEntity);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                if (httpClient != null) {
                    httpClient.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    //HttpPost方式
    public static String uploadHttpPost(String name, InputStream inputStream, String url, String fileName) {
        CloseableHttpClient httpClient = null;
        CloseableHttpResponse response = null;
        try {
            httpClient = HttpClients.createDefault();
            // 把一个普通参数和文件上传给下面这个地址 是一个servlet
            HttpPost httpPost = new HttpPost(url);
            //            StringBody userName = new StringBody("Scott", ContentType.create("text/plain", Consts.UTF_8));
            //            StringBody password = new StringBody("123456", ContentType.create("text/plain", Consts.UTF_8));

            HttpEntity reqEntity = MultipartEntityBuilder.create().
            // 相当于<input type="file" name="file"/>
                    addBinaryBody(name, inputStream, ContentType.DEFAULT_BINARY, fileName).
                    //addPart("file", bin).
                    // 相当于<input type="text" name="userName" value=userName>
                    //            addPart("userName", userName).addPart("pass", password).
                    build();
            httpPost.setEntity(reqEntity);
            // 发起请求 并返回请求的响应
            response = httpClient.execute(httpPost);
            //            System.out.println("The response value of token:"+ response.getFirstHeader("token"));
            // 获取响应对象
            HttpEntity resEntity = response.getEntity();
            if (resEntity != null) {
                // 打印响应长度
                //                System.out.println("Response content length: "+ resEntity.getContentLength());
                // 打印响应内容
                //                System.out.println(EntityUtils.toString(resEntity,Charset.forName("UTF-8")));
                return EntityUtils.toString(resEntity, Charset.forName("UTF-8"));
            }
            // 销毁
            EntityUtils.consume(resEntity);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                if (httpClient != null) {
                    httpClient.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

}
