package com.infosky.wechat.common;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.infosky.wechat.service.message.resp.Article;
import com.infosky.wechat.service.message.resp.NewsMessage;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.core.util.QuickWriter;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;
import com.thoughtworks.xstream.io.xml.XppDriver;

public class MessageUtil {

    public static void main(String[] args) {
        String xml = "<xml><dbid><![CDATA[35]]></dbid><code><![CDATA[12312323]]></code><msg><![CDATA[msg]]></msg>" + "<responseId>12345678</responseId></xml>";
        System.out.println(parseXmlToMap(xml));
    }

    /**
     * 将xml字符串转变成map
     * @param xml
     * @return
     */
    @SuppressWarnings("unchecked")
    public static Map<String, String> parseXmlToMap(String xml) {
        Map<String, String> map = new HashMap<String, String>();
        InputStream inputStream = null;
        try {
            if (StringUtils.isBlank(xml)) return null;
            inputStream = new ByteArrayInputStream(xml.getBytes());
            // 读取输入流
            SAXReader reader = new SAXReader();
            Document document = reader.read(inputStream);
            // 获取xml的根元素
            Element root = document.getRootElement();
            // 遍历子节点
            List<Element> elementList = root.elements();
            for (Element e : elementList) {
                map.put(e.getName(), e.getText());
            }
        } catch (DocumentException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return map;
    }

    /**
     * 解析微信发来的请求 并转换成键值对的map对象
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    public static Map<String, String> parseXml(HttpServletRequest request) {
        Map<String, String> map = new HashMap<String, String>();
        InputStream inputStream = null;
        try {
            inputStream = request.getInputStream();
            // 读取输入流
            SAXReader reader = new SAXReader();
            Document document = reader.read(inputStream);
            // 获取xml的根元素
            Element root = document.getRootElement();
            // 遍历子节点
            List<Element> elementList = root.elements();
            for (Element e : elementList) {
                map.put(e.getName(), e.getText());
            }
        } catch (IOException e1) {
            e1.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return map;
    }

    /**
     * 将消息转换成xml
     * @param obj 消息对象
     * @return
     */
    public static String messageToXML(Object obj) {
        xstream.alias("xml", obj.getClass());
        // 消息类型为图文消息，则设置子节点，放置多个图文消息
        if (obj instanceof NewsMessage) {
            xstream.alias("item", new Article().getClass());
        }
        return xstream.toXML(obj);
    }

    /**
     * 将对象转换成xml
     * @param obj 消息对象
     * @return
     */
    public static String objecteToXML(Object obj) {
        return xstream.toXML(obj);
    }

    /**
     * 扩展xstream，使其支持CDATA块 
     */
    private static XStream xstream = new XStream(new XppDriver() {

        public HierarchicalStreamWriter createWriter(Writer out) {
            return new PrettyPrintWriter(out) {

                // 对所有xml节点的转换都增加CDATA标记   
                boolean cdata = true;

                @SuppressWarnings("rawtypes")
                public void startNode(String name, Class clazz) {
                    super.startNode(name, clazz);
                }

                protected void writeText(QuickWriter writer, String text) {
                    if (cdata) {
                        writer.write("<![CDATA[");
                        writer.write(text);
                        writer.write("]]>");
                    } else {
                        writer.write(text);
                    }
                }
            };
        }
    });
}
