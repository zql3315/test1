package com.infosky.demo.web;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.infosky.framework.annotation.Description;

/**
 * 文件上传与下载
 * 
 * @author n004881
 */
@Controller
@RequestMapping("demo/file/")
@Description("上传下载")
public class FileUpAndDownController {

    /**
     * 
     * @return demo页面
     */
    @RequestMapping(value = "index")
    public String index() {
        return "demo/upload";
    }

    /**
     * 下载文件
     * 
     */
    @RequestMapping("download")
    public ResponseEntity<byte[]> download() throws IOException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", "upload.zip");
        File file = new File("d://upload.zip");
        return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file), headers, HttpStatus.CREATED);
    }
}
