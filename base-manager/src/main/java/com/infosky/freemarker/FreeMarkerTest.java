package com.infosky.freemarker;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * 测试FreeMarker 生成静态html页面
 * 
 * @author n004881
 * 
 */
public class FreeMarkerTest {

    private final Log logger = LogFactory.getLog(getClass());

    private Configuration freemarker_cfg = null;

    public static void main(String[] args) {
        FreeMarkerTest test = new FreeMarkerTest();
        Map<String, String> aItem = new HashMap<String, String>();
        aItem.put("title", "66666");
        aItem.put("addtime", "66666");
        aItem.put("showContent", "666666");
        Map<String, Map<String, String>> root = new HashMap<String, Map<String, String>>();
        root.put("newsitem", aItem);
        String sGeneFilePath = "/tpxw/";
        String sFileName = "1.htm";
        boolean bOK = test.geneHtmlFile("template.ftl", root, sGeneFilePath, sFileName);
        System.out.println(bOK);
    }

    /**
     * 获取freemarker的配置. freemarker本身支持classpath,目录和从ServletContext获取.
     */
    protected Configuration getFreeMarkerCFG() {
        if (null == freemarker_cfg) {
            // Initialize the FreeMarker configuration;
            // - Create a configuration instance
            freemarker_cfg = new Configuration();
            // - FreeMarker支持多种模板装载方式,可以查看API文档,都很简单:路径,根据Servlet上下文,classpath等等
            // htmlskin是放在classpath下的一个目录
            freemarker_cfg.setClassForTemplateLoading(this.getClass(), "/freemarkertemplate");
        }
        return freemarker_cfg;
    }

    /**
     * 生成静态文件.
     * 
     * @param templateFileName
     *            模板文件名,例如"view.ftl"
     * @param propMap
     *            用于处理模板的属性Object映射
     * @param htmlFilePath
     *            要生成的静态文件的路径,相对设置中的根路径,例如 "/tpxw/1/2005/4/"
     * @param htmlFileName
     *            要生成的文件名,例如 "1.htm"
     */
    public boolean geneHtmlFile(String templateFileName, Map<String, Map<String, String>> propMap, String htmlFilePath, String htmlFileName) {
        // @todo 从配置中取得要静态文件存放的根路径:需要改为自己的属性类调用
        String sRootDir = "d:/webtest/htmlfile1";

        try {
            Template t = getFreeMarkerCFG().getTemplate(templateFileName);

            // 如果根路径存在,则递归创建子目录
            creatDirs(sRootDir, htmlFilePath);

            File afile = new File(sRootDir + "/" + htmlFilePath + "/" + htmlFileName);

            Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(afile)));

            t.process(propMap, out);
        } catch (TemplateException e) {
            logger.error("Error while processing FreeMarker template " + templateFileName, e);
            return false;
        } catch (IOException e) {
            logger.error("Error while generate Static Html File " + htmlFileName, e);
            return false;
        }

        return true;
    }

    /**
     * 创建多级目录
     * 
     * @param aParentDir
     *            根路径
     * @param aSubDir
     *            相对路径
     * @return boolean 是否成功
     */
    public static boolean creatDirs(String aParentDir, String aSubDir) {
        File aFile = new File(aParentDir);
        if (!aFile.exists()) {
            File aSubFile = new File(aParentDir + aSubDir);
            if (!aSubFile.exists()) {
                return aSubFile.mkdirs();
            } else {
                return true;
            }
        } else {
            return false;
        }
    }

}
