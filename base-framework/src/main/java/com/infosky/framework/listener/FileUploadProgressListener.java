package com.infosky.framework.listener;

import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.ProgressListener;

/**
 * 文件上传进度<br>
 * 
 * @author n004881
 */
public class FileUploadProgressListener implements ProgressListener {

    private HttpSession session;

    public FileUploadProgressListener() {
    }

    public FileUploadProgressListener(HttpSession session) {
        this.session = session;
        Progress status = new Progress();
        session.setAttribute("upload_ps", status);
    }

    /**
     * pBytesRead 到目前为止读取文件的比特数 pContentLength 文件总大小 pItems 目前正在读取第几个文件
     */
    public void update(long pBytesRead, long pContentLength, int pItems) {
        Progress status = (Progress) session.getAttribute("upload_ps");
        status.setBytesRead(pBytesRead);
        status.setContentLength(pContentLength);
        status.setItems(pItems);
        session.setAttribute("upload_ps", status);
    }
}