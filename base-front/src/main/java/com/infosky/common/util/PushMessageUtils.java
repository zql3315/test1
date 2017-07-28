package com.infosky.common.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PushMessageUtils {

    private static Logger logger = LoggerFactory.getLogger(PushMessageUtils.class);

    /**
     * @param fromjid  发送人
     * @param tojids 1.多个接收人（多个人逗号隔开）；2.发给所有人 (ALL)
     * @param textType 文本类型 1.小农推送消息 2.加好友结果反馈3.@好友、点赞、评论消息通知 4表示合作社邀请消息 5表示用户申请加入合作社消息6、合作社拒绝用户申请加入消息
     * 7 屏蔽用户给我发消息 8 发送同意请求用户加我为好友消息  9表示用户实名认证审核通过 10表示自动加好友提示消息 11表示删除好友通知消息 12 表示用户认证审核不通过
     * @param textContext 文本内容
     * @return
     */
    public static String sendGETSMS(String fromjid, String tojids, int textType, String textContext) {
        HttpURLConnection connection;
        try {
            URL url = new URL(PropertiesConfig.readValue("pushMsgUrl"));
            connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setRequestMethod("POST");
            connection.setUseCaches(false);
            connection.setInstanceFollowRedirects(true);
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            connection.connect();
            String param = "fromjid=panda&tojids=" + tojids + "&textType=" + textType + "&textContext=" + textContext;
            logger.info("推送消息：" + param);
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream(), "UTF-8"));
            bw.write(param);
            bw.flush();
            bw.close();
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String lines;
            StringBuffer sb = new StringBuffer("");
            while ((lines = reader.readLine()) != null) {
                lines = new String(lines.getBytes(), "utf-8");
                sb.append(lines);
            }
            lines = sb.toString();
            logger.info("推送消息返回：" + sb.toString());
            reader.close();
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
