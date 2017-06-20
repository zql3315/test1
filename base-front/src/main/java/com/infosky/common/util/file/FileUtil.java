package com.infosky.common.util.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.codec.binary.Base64;
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

    /**
     * 将图片文件转化为字节数组字符串，并对其进行Base64编码处理
     * 
     * @param filePath 文件路径  "d://aaa//a1.jpg"
     * 
     * @return 返回Base64编码过的字节数组字符串
     */
    public static String GetImageStr(String filePath) {
        InputStream in = null;
        byte[] data = null;
        try {
            in = new FileInputStream(filePath);
            data = new byte[in.available()];
            in.read(data);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Base64.encodeBase64String(data);
    }

    /**
     * base64字符串转化成图片
     * 
     * @param fileStr  base64的文件字符串
     * @param destFilePath 新生成的文件 "d://aaa//222.jpg"
     * 
     * @return
     */
    public static boolean GenerateImage(String fileStr, String destFilePath) {
        if (fileStr == null) {
            return false;
        }
        try {
            //Base64解码
            byte[] b = Base64.decodeBase64(fileStr);
            for (int i = 0; i < b.length; ++i) {
                if (b[i] < 0) {//调整异常数据
                    b[i] += 256;
                }
            }
            OutputStream out = new FileOutputStream(destFilePath);
            out.write(b);
            out.flush();
            out.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
