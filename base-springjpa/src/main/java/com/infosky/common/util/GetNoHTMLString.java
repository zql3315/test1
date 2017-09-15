package com.infosky.common.util;

import java.util.regex.Pattern;

public class GetNoHTMLString {

    //	public static void main(String[] args) {
    //		String str = "  <!DOCTYPE><!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\"        \"http://www.w3.org/TR/html4/loose.dtd\"> " +
    //				"<html><html xmlns:wb=\"http://open.weibo.com/wb\"><p><br/></p><meta http-equiv=\"Content-Type\" content=\"text/html;charset=utf-8\"/> \r\n" +
    //				"<title>一亩田农业资讯网-浙江校企产学研联动 共建泥鳅研发中心助农民致富,致富经-获取三农资讯，创造农业财</title> \r\n" +
    //				"<a href='http://www.baidu.com'>一亩田农业资讯网-浙江校企产学研联动 共建泥鳅研发中心助农民致富,致富经-获取三农资讯，创造农业财</a> \r\n" +
    //				"<meta name=\"keywords\" content=\"浙江校企产学研联动 共建泥鳅研发中心助农民致富,致富经,农产品,农业,三农,农业资讯\"/> \r\n" +
    //				"<link href=\"http://zixun.ymt.com/statics/css/news/style.css?time=11\" rel=\"stylesheet\" type=\"text/css\"/> \r\n" +
    //				"<link href=\"http://zixun.ymt.com/statics/css/news/flexslider.css\" rel=\"stylesheet\" type=\"text/css\"/> \r\n" +
    //				"2222<script type=\"text/javascript\">\r\n545445\r\n</script>777<!--!doctype-->";
    //		str = "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\"        \"http://www.w3.org/TR/html4/loose.dtd\"><html xmlns:wb=\"http://open.weibo.com/wb\">" +
    //				"<meta http-equiv=\"Content-Type\" content=\"text/html;charset=utf-8\" >  <head>  ";
    //		str = getNoHTMLString(str,1000);
    //		System.out.println(str);
    //		
    //	}

    /***
     * 
     * @param content    内容String
     * @param p   最大长度
     * @purpose：得到相应位数已过滤html、script、style 标签的内容 内容结尾 为...
     * @return
     */
    public static String getNoHTMLString(String content, int p) {
        if (null == content) return "";
        java.util.regex.Pattern p_script;
        java.util.regex.Matcher m_script;
        java.util.regex.Pattern p_script1;
        java.util.regex.Matcher m_script1;
        java.util.regex.Pattern p_style;
        java.util.regex.Matcher m_style;
        java.util.regex.Pattern p_link;
        java.util.regex.Matcher m_link;
        java.util.regex.Pattern p_meta;
        java.util.regex.Matcher m_meta;
        java.util.regex.Pattern p_a;
        java.util.regex.Matcher m_a;
        java.util.regex.Pattern p_head;
        java.util.regex.Matcher m_head;
        //		java.util.regex.Pattern p_html;
        //		java.util.regex.Matcher m_html;
        java.util.regex.Pattern p_html1;
        java.util.regex.Matcher m_html1;
        java.util.regex.Pattern p_doctype;
        java.util.regex.Matcher m_doctype;

        try {
            String regEx_meta = "<\\s*meta\\s+([^>]*)\\s*/{0,1}>";
            // 定义meta的正则表达式
            String regEx_link = "<\\s*link\\s+([^>]*)\\s*/{0,1}>";
            // 定义link的正则表达式
            String regEx_head = "<[\\s]*?head[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?head[\\s]*?>";
            // 定义script的正则表达式{或<script[^>]*?>[\\s\\S]*?<\\/script> }
            String regEx_script = "<[\\s]*?script[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?script[\\s]*?>";
            // 定义script的正则表达式{或<script[^>]*?>[\\s\\S]*?<\\/script> }
            String regEx_script1 = "<\\s*script\\s+([^>]*)\\s*/>";
            // 定义script的正则表达式{或<script[^>]*?>[\\s\\S]*?<\\/script> }
            String regEx_style = "<[\\s]*?style[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?style[\\s]*?>";
            // 定义style的正则表达式{或<style[^>]*?>[\\s\\S]*?<\\/style> }
            //	String regEx_html = "<[^>]+>"; 
            // 定义HTML标签的正则表达式

            String regEx_doctype = "<!DOCTYPE.*?>";
            // 定义DOCTYPE的正则表达式去除DOCTYPE标签
            String regEx_html = "</{0,1}html[^>]*>";
            //定义html标签的正则表达式
            String regEx_a = "</{0,1}a[^>]*>";
            // 定义<a>的正则表达式去除a标签，但是不去除里面的内容

            p_doctype = Pattern.compile(regEx_doctype, Pattern.CASE_INSENSITIVE);
            m_doctype = p_doctype.matcher(content);

            content = m_doctype.replaceAll(""); // 过滤DOCTYPE标签

            p_html1 = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
            m_html1 = p_html1.matcher(content);
            content = m_html1.replaceAll(""); // 过滤html标签但是不去除内容

            p_a = Pattern.compile(regEx_a, Pattern.CASE_INSENSITIVE);
            m_a = p_a.matcher(content);
            content = m_a.replaceAll(""); // 过滤a标签但是不去除内容

            p_meta = Pattern.compile(regEx_meta, Pattern.CASE_INSENSITIVE);
            m_meta = p_meta.matcher(content);
            content = m_meta.replaceAll(""); // 过滤link标签

            p_link = Pattern.compile(regEx_link, Pattern.CASE_INSENSITIVE);
            m_link = p_link.matcher(content);
            content = m_link.replaceAll(""); // 过滤link标签

            p_head = Pattern.compile(regEx_head, Pattern.CASE_INSENSITIVE);
            m_head = p_head.matcher(content);
            content = m_head.replaceAll(""); // 过滤script标签

            p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);
            m_script = p_script.matcher(content);
            content = m_script.replaceAll(""); // 过滤script标签

            p_script1 = Pattern.compile(regEx_script1, Pattern.CASE_INSENSITIVE);
            m_script1 = p_script1.matcher(content);
            content = m_script1.replaceAll(""); // 过滤script标签

            p_style = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE);
            m_style = p_style.matcher(content);
            content = m_style.replaceAll(""); // 过滤style标签

            //			p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
            //			m_html = p_html.matcher(content);

        } catch (Exception e) {
            return "";
        }

        if (p > 0 && content.length() > p) {
            content = content.substring(0, p) + "...";
        }

        return content;
    }

}
