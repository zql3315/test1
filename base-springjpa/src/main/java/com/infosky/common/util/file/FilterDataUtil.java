package com.infosky.common.util.file;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.Normalizer;
import java.text.Normalizer.Form;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

/** 
 * 对字符串进行验证之前先进行规范化
 * 过滤数据，标签
 * @author n004881
 */
public class FilterDataUtil {

    /*========================================================================*
    *                         Public Fields (公共属性)                                                                
    *========================================================================*/

    /*========================================================================*
     *                         Private Fields (私有属性)                                                                
     *========================================================================*/

    /**
     * HTML标签转义方法 —— java代码库
     * XSSCheck
     * @param content
     * @return
     */
    public static String filterHtml(String content) {
        if (content == null) return null;
        content = Normalizer.normalize(content, Form.NFKC);
        String html = content;
        html = StringUtils.replace(html, "'", "&apos;");
        //html = StringUtils.replace(html, "\"", "&quot;");
        html = StringUtils.replace(html, "\t", "&nbsp;&nbsp;");// 替换跳格
        //html = StringUtils.replace(html, " ", "&nbsp;");// 替换空格
        html = StringUtils.replace(html, "<", "&lt;");
        html = StringUtils.replace(html, ">", "&gt;");
        return html;
    }

    /**
     * HTML标签转义方法 —— java代码库
     * XSSCheck----过滤json
     * @param content
     * @return
     */
    public static String filterHtmlForJson(String content) {
        if (content == null) return null;
        String html = content;
        html = StringUtils.replace(html, "'", "&apos;");
        //html = StringUtils.replace(html, "\"", "&quot;");
        html = StringUtils.replace(html, "\t", "&nbsp;&nbsp;");// 替换跳格
        //html = StringUtils.replace(html, " ", "&nbsp;");// 替换空格
        html = StringUtils.replace(html, "<", "&lt;");
        html = StringUtils.replace(html, ">", "&gt;");
        return html;
    }

    /**
     * HTML标签转义方法 —— java代码库
     * XSSCheck----过滤图片名称
     * @param content
     * @return
     */
    public static String filterHtmlForFileName(String content) {
        if (content == null) return null;
        String html = content;
        html = StringUtils.replace(html, "'", "&apos;");
        html = StringUtils.replace(html, "\"", "&quot;");
        html = StringUtils.replace(html, "\t", "&nbsp;&nbsp;");// 替换跳格
        html = StringUtils.replace(html, " ", "&nbsp;");// 替换空格
        html = StringUtils.replace(html, "<", "&lt;");
        html = StringUtils.replace(html, ">", "&gt;");
        html = StringUtils.replace(html, "/", "");
        html = StringUtils.replace(html, "..", "");
        return html;
    }

    /**
     * 过滤uri
     * @param uri
     * @return
     * @throws UnsupportedEncodingException 
     */
    public static String filterURI(String uri) throws UnsupportedEncodingException {
        if (uri == null) return null;
        uri = URLEncoder.encode(uri, "UTF-8");
        uri = URLDecoder.decode(uri, "UTF-8");
        return uri;
    }

    /**
     * 对字符串进行验证之前先进行规范化
     * @return
     */
    public static String filterRequestParam(String param) {
        if (param != null) param = Normalizer.normalize(param, Form.NFKC);
        return param;
    }

    /**
     * 过滤特殊字符
     * @param input
     * @return
     */
    public static String encodeHtml(String input) {
        if (input == null) {
            return null;
        }

        StringBuffer out = new StringBuffer();
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (c == '<') {
                out.append("&lt;");
            } else if (c == '>') {
                out.append("&gt;");
            } else if (c == '\"') {
                out.append("&quot;");
            } else if (c == '&') {
                out.append("&quot;");
            } else if (c > 0x20 && c < 0x126) {
                out.append(c);
            } else {
                out.append("&#" + (int) c + ";");
            }
        }

        return out.toString();
    }

    /**
     * 专享积分的昵称过滤 特殊字符和空格
     * @param str
     * @return
     */
    public static String filterCustNickName(String str) {
        // 清除掉所有特殊字符  
        String regEx = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        String nickName = m.replaceAll("").trim();

        //清除掉所有空格
        nickName = nickName.replaceAll(" ", "");
        return nickName;
    }

    public static void main(String[] args) {
        try {
            String result = filterURI("http://weixin.piccjs.com/test");
            //System.out.println(result);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
    /*========================================================================*
     *                         Construct Methods (构造方法) 
     *========================================================================*/

    /*========================================================================*
     *                         Public Methods (公有方法)                                                                   
     *========================================================================*/

    /*========================================================================*
     *                         Private Methods (私有方法)                                                                   
     *========================================================================*/
}
