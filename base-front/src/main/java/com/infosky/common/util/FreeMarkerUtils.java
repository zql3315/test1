package com.infosky.common.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * FreeMarker 生成静态html页面工具类
 * 
 * @author n004881
 * 
 */
public class FreeMarkerUtils {

    private static Log logger = LogFactory.getLog(FreeMarkerUtils.class);

    private static Configuration freemarker_cfg = null;

    /**
     * 获取freemarker的配置. freemarker本身支持classpath,目录和从ServletContext获取.
     */
    protected static Configuration getFreeMarkerCFG() {
        if (null == freemarker_cfg) {
            freemarker_cfg = new Configuration();
            freemarker_cfg.setDefaultEncoding("UTF-8");
            freemarker_cfg.setClassForTemplateLoading(FreeMarkerUtils.class, "/freemarkertemplate");
        }
        return freemarker_cfg;
    }

    /**
     * 生成静态文件.
     * 
     * @param templateFileName  模板文件名,例如"view.ftl"
     * @param propMap  用于处理模板的属性Object映射
     * @param htmlFilePath  要生成的静态文件的路径,相对设置中的根路径,例如 "/tpxw/1/2005/4/"
     * @param htmlFileName  要生成的文件名,例如 "1.htm"
     */
    public static boolean geneHtmlFile(String templateFileName, Map<String, Map<String, String>> propMap, String htmlFilePath, String htmlFileName) {
        // @todo 从配置中取得要静态文件存放的根路径:需要改为自己的属性类调用
        String sRootDir = PropertiesConfig.readValue("cms.site.staticfiles.path");
        Writer out = null;
        try {
            Template t = getFreeMarkerCFG().getTemplate(templateFileName, "UTF-8");
            t.setEncoding("UTF-8");
            creatDirs(sRootDir, htmlFilePath);
            File afile = new File(sRootDir + "/" + htmlFilePath + "/" + htmlFileName);
            out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(afile), "UTF-8"));
            t.process(propMap, out);
        } catch (TemplateException e) {
            logger.error("Error while processing FreeMarker template " + templateFileName, e);
            return false;
        } catch (IOException e) {
            logger.error("Error while generate Static Html File " + htmlFileName, e);
            return false;
        } finally {
            if (out != null) try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return true;
    }

    /**
     * 创建多级目录
     * 
     * @param aParentDir  根路径
     * @param aSubDir  相对路径
     * @return boolean 是否成功
     */
    public static boolean creatDirs(String aParentDir, String aSubDir) {
        File aFile = new File(aParentDir);
        if (aFile.exists()) aFile.mkdirs();
        File aSubFile = new File(aParentDir + aSubDir);
        if (!aSubFile.exists()) {
            return aSubFile.mkdirs();
        } else {
            return true;
        }
    }

}
