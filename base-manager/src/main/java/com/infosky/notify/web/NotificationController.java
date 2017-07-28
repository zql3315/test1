package com.infosky.notify.web;

import java.io.File;
import java.io.FileInputStream;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.infosky.framework.service.PagingService;
import com.infosky.framework.web.CrudController;
import com.infosky.framework.web.PageResult;
import com.infosky.notify.entity.dto.NotificationDTO;
import com.infosky.notify.service.impl.NotificationService;

/**
 * 表操作
 * 
 * @author  n004881
 * @version  [版本号, 2015年2月9日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@Controller
@RequestMapping("/notification")
public class NotificationController extends CrudController<String, PageResult<NotificationDTO>, NotificationDTO> {

    @Autowired
    private NotificationService service;

    @RequestMapping(value = "/download/{id}")
    @ResponseBody
    public ResponseEntity<byte[]> download(@PathVariable String id) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", "dict.xls");

        byte[] bytes = null;

        try {
            NotificationDTO notifiDTO = service.find(id);

            bytes = IOUtils.toByteArray(new FileInputStream(new File(notifiDTO.getAttachment())));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ResponseEntity<byte[]>(bytes, headers, HttpStatus.OK);
    }

    /** {@inheritDoc} */
    public PagingService<NotificationDTO, PageResult<NotificationDTO>, String> getService() {
        return service;
    }

}
