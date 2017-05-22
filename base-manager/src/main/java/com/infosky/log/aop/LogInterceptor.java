package com.infosky.log.aop;

import java.lang.reflect.Array;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.infosky.common.util.BrowserUtils;
import com.infosky.common.util.ContextHolderUtils;
import com.infosky.common.util.IpUtil;
import com.infosky.common.util.date.DataUtils;

/**
 * 日志切面
 * @author n004881
 *
 */
@Component
@Aspect
public class LogInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(LogInterceptor.class);

    @Before("target(com.infosky.framework.web.CrudController) && execution(public * com.infosky.*.web..*.*(..)) ")
    public void beforeMethod(JoinPoint joinPoint) throws Exception {
        String temp = joinPoint.getStaticPart().toShortString();
        String longTemp = joinPoint.getStaticPart().toLongString();
        joinPoint.getStaticPart().toString();
        String classType = joinPoint.getTarget().getClass().getName();
        String methodName = temp.substring(10, temp.length() - 1);
        Class<?> className = Class.forName(classType);
        Class[] args = new Class[joinPoint.getArgs().length];
        String[] sArgs = (longTemp.substring(longTemp.lastIndexOf("(") + 1, longTemp.length() - 2)).split(",");
        for (int i = 0; i < args.length; i++) {
            if (sArgs[i].endsWith("String[]")) {
                args[i] = Array.newInstance(Class.forName("java.lang.String"), 1).getClass();
            } else if (sArgs[i].endsWith("Long[]")) {
                args[i] = Array.newInstance(Class.forName("java.lang.Long"), 1).getClass();
            } else if (sArgs[i].indexOf(".") == -1) {
                if (sArgs[i].equals("int")) {
                    args[i] = int.class;
                } else if (sArgs[i].equals("char")) {
                    args[i] = char.class;
                } else if (sArgs[i].equals("float")) {
                    args[i] = float.class;
                } else if (sArgs[i].equals("long")) {
                    args[i] = long.class;
                }
            } else {
                args[i] = Class.forName(sArgs[i]);
            }
        }
        //		Method method = className.getMethod(methodName.substring(methodName.indexOf(".") + 1, methodName.indexOf("(")), args);
        //		Description methodDescription = AnnotationUtils.findAnnotation(method, Description.class);
        HttpServletRequest request = ContextHolderUtils.getRequest();
        String broswer = BrowserUtils.checkBrowse(request);
        if (broswer != null)
            logger.debug("ip:" + IpUtil.getIpAddr(request) + " 浏览器：" + broswer + " 当前时间：" + DataUtils.date2Str(DataUtils.datetimeFormat) + " className:" + className + "  methodName:" + methodName);
    }
}