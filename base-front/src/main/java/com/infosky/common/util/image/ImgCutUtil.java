package com.infosky.common.util.image;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

public class ImgCutUtil {

    public static void main(String[] args) {
        String srcpath = "d:/aaa/b.jpg";
        String subpath = "d:/aaa/a1.jpg";
        String subpath1 = "d:/aaa/b1.jpg";
        try {
            File picture = new File(srcpath);
            BufferedImage sourceImg = ImageIO.read(new FileInputStream(picture));
            System.out.println(String.format("%.1f", picture.length() / 1024.0));
            System.out.println(sourceImg.getWidth());
            System.out.println(sourceImg.getHeight());
            //            cut(srcpath, subpath, 0, 0, 500, 500);
            zoomImage(srcpath, subpath1, (int) (sourceImg.getWidth() / (sourceImg.getHeight() / 120.0)), 120);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 图片缩放
     * image.SCALE_SMOOTH //平滑优先
     * image.SCALE_FAST//速度优先
     * image.SCALE_AREA_AVERAGING //区域均值
     * image.SCALE_REPLICATE //像素复制型缩放
     * image.SCALE_DEFAULT //默认缩放模式
     * @param srcpath   源图片路径 
     * @param subpath   剪切图片存放路径 
     * @param width     剪切宽度 
     * @param height    剪切高度 
     * @throws Exception
     */
    public static void zoomImage(String src, String dest, int w, int h) throws Exception {
        BufferedImage result = null;
        File srcFile = new File(src);
        File destFile = new File(dest);
        BufferedImage bufImg = ImageIO.read(srcFile);
        /* 新生成结果图片 */
        result = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        result.getGraphics().drawImage(bufImg.getScaledInstance(w, h, java.awt.Image.SCALE_SMOOTH), 0, 0, null);
        try {
            ImageIO.write(result, dest.substring(dest.lastIndexOf(".") + 1), destFile);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    /** 
     *  对图片裁剪，并把裁剪完新图片保存 。     
     * @param srcpath   源图片路径 
     * @param subpath   剪切图片存放路径 
     * @param x         剪切点x坐标 
     * @param y         剪切点y坐标 
     * @param width     剪切宽度 
     * @param height    剪切高度 
     * @throws IOException 
     */
    public static void cut(String srcpath, String subpath, int x, int y, int width, int height) throws IOException {

        FileInputStream is = null;
        ImageInputStream iis = null;

        try {
            //读取图片文件  
            is = new FileInputStream(srcpath);

            /**//* 
                      * 返回包含所有当前已注册 ImageReader 的 Iterator，这些 ImageReader  
                      * 声称能够解码指定格式。 参数：formatName - 包含非正式格式名称 . 
                      *（例如 "jpeg" 或 "tiff"）等 。  
                      */
            String suffix = srcpath.substring(srcpath.lastIndexOf(".") + 1);

            Iterator<ImageReader> it = ImageIO.getImageReadersByFormatName(suffix);
            ImageReader reader = it.next();
            //获取图片流   
            iis = ImageIO.createImageInputStream(is);

            /**//*  
                      * <p>iis:读取源.true:只向前搜索 </p>.将它标记为 ‘只向前搜索’。 
                      * 此设置意味着包含在输入源中的图像将只按顺序读取，可能允许 reader 
                      *  避免缓存包含与以前已经读取的图像关联的数据的那些输入部分。 
                      */
            reader.setInput(iis, true);

            /**//*  
                      * <p>描述如何对流进行解码的类<p>.用于指定如何在输入时从 Java Image I/O  
                      * 框架的上下文中的流转换一幅图像或一组图像。用于特定图像格式的插件 
                      * 将从其 ImageReader 实现的 getDefaultReadParam 方法中返回  
                      * ImageReadParam 的实例。   
                      */
            ImageReadParam param = reader.getDefaultReadParam();

            /**//* 
                      * 图片裁剪区域。Rectangle 指定了坐标空间中的一个区域，通过 Rectangle 对象 
                      * 的左上顶点的坐标（x，y）、宽度和高度可以定义这个区域。  
                      */
            Rectangle rect = new Rectangle(x, y, width, height);

            //提供一个 BufferedImage，将其用作解码像素数据的目标。   
            param.setSourceRegion(rect);

            /**//* 
                      * 使用所提供的 ImageReadParam 读取通过索引 imageIndex 指定的对象，并将 
                      * 它作为一个完整的 BufferedImage 返回。 
                      */
            BufferedImage bi = reader.read(0, param);

            //保存新图片   
            ImageIO.write(bi, "jpg", new File(subpath));
        }

        finally {
            if (is != null) is.close();
            if (iis != null) iis.close();
        }

    }

}