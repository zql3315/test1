package com.infosky.common.util.file;

import java.io.File;

import org.apache.commons.lang.StringUtils;

public class FileUtil {

    /**
     * 创建目录（包含抽象目录）
     * 
     * @param savePath
     */
    public static void createDirectroy(String savePath) {
        try {
            //初始化目录
            if (StringUtils.isNotEmpty(savePath)) {
                savePath = FilterDataUtil.filterURI(savePath);
            }
            File file = new File(FilterDataUtil.filterURI(savePath));
            if (!file.exists()) {
                File directroy = file.getParentFile();
                if (!directroy.exists()) {
                    directroy.mkdirs();
                }
                file.mkdirs();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 创建文件（包含抽象目录）
     * 
     * @param filePath
     */
    public static void createFile(String filePath) {
        try {
            //初始化目录
            if (StringUtils.isNotEmpty(filePath)) {
                filePath = FilterDataUtil.filterURI(filePath);
            }
            File file = new File(FilterDataUtil.filterURI(filePath));
            if (!file.exists()) {
                File directroy = file.getParentFile();
                if (!directroy.exists()) {
                    directroy.mkdirs();
                }
                file.createNewFile();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
