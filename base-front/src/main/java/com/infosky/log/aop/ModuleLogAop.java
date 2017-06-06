package com.infosky.log.aop;

import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;

import com.infosky.common.util.AddUtils;
import com.infosky.framework.annotation.Description;
import com.infosky.log.entity.dto.VisitLogDTO;
import com.infosky.log.service.impl.VisitLogService;

/**
 * 模块操作日志AOP
 * 
 * @author n004881
 * @version [版本号, 2015年4月1日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Aspect
@Component
@Lazy(value = false)
public class ModuleLogAop {

    private static final Logger logger = LoggerFactory.getLogger(ModuleLogAop.class);

    @Autowired
    private VisitLogService visitLogService;

    @Autowired
    private HttpServletRequest request;

    /**
     * 匹配用户Service
     */
    @Pointcut(value = "target(com.infosky.framework.web.CrudController)")
    public void controllerPointcut() {
    }

    /**
     * put 或 get 比如查询
     */
    @Pointcut(value = "execution(* add*(..))|| execution(* add(..)) " + "|| execution(* save*(..))    || execution(* save(..)) " + "|| execution(* edit*(..))    || execution(* edit(..)) "
            + "|| execution(* update*(..))  || execution(* update(..)) " + "|| execution(* export*(..))  || execution(* export(..)) " + "|| execution(* delete*(..))  || execution(* delete(..))")
    public void methodPointcut() {
    }

    @Before(value = "controllerPointcut() && methodPointcut()")
    public void doBefore(JoinPoint jp) {
        String currentUser = SecurityUtils.getSubject().getPrincipal().toString();
        // 类描述
        Description classDescription = AnnotationUtils.findAnnotation(jp.getTarget().getClass(), Description.class);
        // 方法描述
        Description methodDescription = null;
        if (null == classDescription) {
            return;
        }

        try {
            String temp = jp.getStaticPart().toShortString();
            String longTemp = jp.getStaticPart().toLongString();
            Class<?> targetClass = jp.getTarget().getClass();
            String methodName = temp.substring(10, temp.length() - 1);
            Class<?>[] args = new Class[jp.getArgs().length];
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

            Method method = targetClass.getMethod(methodName.substring(methodName.indexOf(".") + 1, methodName.indexOf("(")), args);
            methodDescription = AnnotationUtils.findAnnotation(method, Description.class);

            if (classDescription != null && StringUtils.isNotBlank(classDescription.value()) && methodDescription != null && StringUtils.isNotBlank(methodDescription.value())) {
                VisitLogDTO visitLog = new VisitLogDTO();
                visitLog.setVisitor(currentUser);
                visitLog.setVisitTime(new Date());
                //暂时先去除参数记录，因为有些场景不需要记录参数，如参数太长，不必要的参数等等，后期可以做定制化的日志，或者其他优化
                /*Object[] param = jp.getArgs();
                Class<?>[] paramArgs = new Class[jp.getArgs().length];
                StringBuffer sb = new StringBuffer();
                for (int p = 0; p < param.length; p++) {
                    paramArgs[p] = param[p].getClass();
                    Object arg = param[p];
                    if (arg instanceof PageResult<?>) {
                        sb.append(JSONObject.fromObject(arg).toString());
                    } else if (arg instanceof DTO) {
                        sb.append(JSONObject.fromObject(arg).toString());
                    } else if (arg instanceof List) {
                        sb.append(JSONObject.fromObject(arg).toString());
                    } else if (arg instanceof JSONObject) {
                        sb.append(JSONObject.fromObject(arg).toString());
                    } else if (arg instanceof Map) {
                        sb.append(JSONObject.fromObject(arg).toString());
                    }
                }
                String visitParam = HtmlUtil.delHTMLTag(sb.toString());
                visitLog.setVisitParam(visitParam.substring(0,visitParam.length() > 500 ? 500 : visitParam.length()));*/
                visitLog.setVisitModule(classDescription.value());
                visitLog.setOperate(methodDescription.value());
                visitLog.setIp(AddUtils.getClientIpAddr(request));
                visitLogService.save(visitLog);
                logger.info("{}的操作记录:{},{}", currentUser, classDescription.value(), methodDescription.value());
            }
        } catch (Exception e) {
            logger.error("", e);
        }

    }

}
