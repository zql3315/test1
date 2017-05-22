package com.infosky.framework.exception;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import com.infosky.common.util.CommonUtil;

/**
 * 添加类/接口功能描述
 * 
 * @author n004881
 */
public class MyExceptionHandler implements HandlerExceptionResolver {

    private static final Logger LOG = LoggerFactory.getLogger(MyExceptionHandler.class);

    @Override
    public ModelAndView resolveException(HttpServletRequest httpservletrequest, HttpServletResponse response, Object obj, Exception exception) {
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("result", false);
        PrintWriter out = null;
        model.put("exception", exception);
        LOG.error("", exception);
        try {
            if (CommonUtil.useAjax(httpservletrequest)) {
                response.setCharacterEncoding("UTF-8");
                if (exception instanceof MaxUploadSizeExceededException) {
                    model.put("msg", "文件过大");
                } else {
                    model.put("msg", exception.getLocalizedMessage());
                }
                response.setContentType(MediaType.APPLICATION_JSON_VALUE); // 设置ContentType
                response.setCharacterEncoding("UTF-8"); // 避免乱码
                response.setHeader("Cache-Control", "no-cache, must-revalidate");
                out = response.getWriter();
                out.write(JSONObject.fromObject(model).toString());
                return null;
            } else if (exception instanceof MaxUploadSizeExceededException) {
                if (exception instanceof MaxUploadSizeExceededException) {
                    model.put("msg", "文件过大");
                } else {
                    model.put("msg", exception.getLocalizedMessage());
                }
                return null;
            } else if (exception instanceof java.lang.Exception) {
                return new ModelAndView("common/error", model);
            } else if (exception instanceof java.lang.Throwable) {
                return new ModelAndView("common/err", model);
            } else {
                return new ModelAndView("error", model);
            }
        } catch (Exception e) {
            LOG.error(e.getLocalizedMessage());
        } finally {
            if (out != null) {
                out.flush();
                out.close();
            }
        }
        return null;
    }

}
