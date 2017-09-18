package com.infosky.common.util.OCR;


import java.io.File;
import java.io.IOException;
  
  
/**
 * http://blog.csdn.net/ycb1689/article/details/8520954
 * 
 * Java文字识别程序的关键是寻找一个可以调用的OCR引擎。tesseract-ocr就是一个这样的OCR引擎，在1985年到1995年由HP实验室开发，现在在Google。
 * tesseract-ocr 3.0发布，支持中文。不过tesseract-ocr 3.0不是图形化界面的客户端，别人写的FreeOCR图形化客户端还不支持导入新的 3.0 traineddata。
 * 但这标志着，现在有自由的中文OCR软件了。
 * java中使用tesseract-ocr3.01的步骤如下：
 * 1.下载安装tesseract-ocr-setup-3.01-1.exe（3.0以上版本才增加了中文识别）
 * 2.在安装向导中可以选择需要下载的语言包。
 * 3.到网上搜索下载java图形处理所需的2个包：jai_imageio-1.1-alpha.jar，swingx-1.6.1.jar
 * 
 * @author n004881
 *
 */
public class OcrTest {  
  
 public static void main(String[] args) {  
        String path = "D://aaa//temp/ocr1.png";       
        System.out.println("ORC Test Begin......");  
        try {       
            String valCode = new OCRUtil().recognizeText(new File(path), "png");       
            System.out.println(valCode);       
        } catch (IOException e) {       
            e.printStackTrace();       
        } catch (Exception e) {    
            e.printStackTrace();    
        }         
        System.out.println("ORC Test End......");  
    }    
  
}  
