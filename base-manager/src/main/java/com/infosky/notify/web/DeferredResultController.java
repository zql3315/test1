package com.infosky.notify.web;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Maps;
import com.infosky.common.query.jpa.Operator;
import com.infosky.common.query.jpa.SearchRequest;
import com.infosky.common.query.jpa.Searchable;
import com.infosky.notify.entity.dto.NotificationDTO;
import com.infosky.notify.service.impl.NotificationService;
import com.infosky.sys.service.impl.PushService;

/**
 * 异步通知
 * 
 * @author  n004881
 * @version  [版本号, 2015年3月30日]
 */
@Controller
@RequestMapping("deferredResult")
public class DeferredResultController {

    @Autowired
    private PushService<String> pushService;

    @Autowired
    private NotificationService notificationService;

    @RequestMapping("/polling")
    @ResponseBody
    public Object poll() {
        //如果用户第一次来 立即返回
        if (!pushService.isOnline("admin")) {

            Searchable s = new SearchRequest();
            s.addSearchParam("isRead", Operator.EQ, "non-read");
            //查询未读消息的个数
            List<NotificationDTO> notifies = (List<NotificationDTO>) notificationService.findAll(s);

            Map<String, Object> data = Maps.newHashMap();
            data.put("notifications", notifies);
            data.put("count", notifies.size());

            pushService.online("admin");
            return data;
        } else {
            //长轮询
            return pushService.newDeferredResult("admin");
        }
    }
}
