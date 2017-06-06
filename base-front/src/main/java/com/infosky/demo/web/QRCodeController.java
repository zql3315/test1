package com.infosky.demo.web;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.infosky.common.util.QRCode.QRCodeUtil;
import com.infosky.framework.annotation.Description;

/**
 * //TODO 添加类/接口功能描述
 * 
 * @author n004881
 */
@Controller
@RequestMapping("demo/qrcode")
@Description("二维码")
public class QRCodeController {

    /**
     * 
     * @return demo页面
     */
    @RequestMapping(value = "index")
    public String index() {
        return "demo/qrcode";
    }

    /**
     * 获取二维码
     * 
     * @param content
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(value = "getQRCode")
    @ResponseBody
    public void getImg(@RequestParam(defaultValue = "二维码") String content, @RequestParam(defaultValue = "1") Integer margin, HttpServletRequest request, HttpServletResponse response) throws Exception {

        FileInputStream inputStream = null;
        OutputStream outputStream = null;
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            QRCodeUtil.encodeStream(content, null, out, margin, true);
            response.setDateHeader("Expires", 0);
            response.setHeader("Cache-Control", "no-storOe, no-cache, must-revalidate");
            response.addHeader("Cache-Control", "post-check=0, pre-check=0");
            response.setHeader("Pragma", "no-cache");
            response.setContentType("image/png");
            outputStream = response.getOutputStream();
            outputStream.write(out.toByteArray());
            outputStream.flush();
            outputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (outputStream != null) try {
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 获取二维码（带log）
     * 
     * @param content
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(value = "getLogQRCode")
    @ResponseBody
    public void getLogImg(@RequestParam(defaultValue = "二维码") String content, @RequestParam(defaultValue = "1") Integer margin, HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        FileInputStream inputStream = null;
        OutputStream outputStream = null;
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            String logo = request.getServletContext().getRealPath("static/image/logo_151x41.png");
            QRCodeUtil.encodeStream(content, logo, out, margin, true);
            response.setDateHeader("Expires", 0);
            response.setHeader("Cache-Control", "no-storOe, no-cache, must-revalidate");
            response.addHeader("Cache-Control", "post-check=0, pre-check=0");
            response.setHeader("Pragma", "no-cache");
            response.setContentType("image/png");
            outputStream = response.getOutputStream();
            outputStream.write(out.toByteArray());
            outputStream.flush();
            outputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (outputStream != null) try {
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
