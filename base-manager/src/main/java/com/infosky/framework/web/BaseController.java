package com.infosky.framework.web;

import java.beans.PropertyEditorSupport;
import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang3.StringEscapeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;

import com.infosky.common.util.ReflectUtils;
import com.infosky.common.util.date.DateUtilsExtend;
import com.infosky.framework.View;
import com.infosky.framework.web.editor.DateEditor;

/**
 * 控制层基本接口
 */
public abstract class BaseController<K extends Serializable, P extends Serializable, D extends View> implements Controller<K, P, D> {

    // @Autowired
    // protected HttpServletRequest request;

    /**
     * 日志对象
     */
    protected Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 初始化数据绑定 
     * 1. 将所有传递进来的String进行HTML编码，防止XSS攻击 
     * 2. 将字段中Date类型转换为String类型
     */
    @InitBinder
    protected void initBinder(WebDataBinder binder) throws Exception {
        binder.registerCustomEditor(Date.class, new DateEditor());
        binder.registerCustomEditor(byte[].class, new ByteArrayMultipartFileEditor());
        binder.registerCustomEditor(String.class, new PropertyEditorSupport() {

            @Override
            public void setAsText(String text) {
                setValue(text == null ? null : StringEscapeUtils.escapeHtml4(text.trim()));
                setValue(text == null ? null : text.trim());
            }

            @Override
            public String getAsText() {
                Object value = getValue();
                return value != null ? value.toString() : "";
            }
        });
        // Date 类型转换
        binder.registerCustomEditor(Date.class, new PropertyEditorSupport() {

            @Override
            public void setAsText(String text) {
                setValue(DateUtilsExtend.parseDate(text));
            }
        });
    }

    /**
     * 获取视图名称：即prefixViewName + "/" + suffixName
     * 
     * @param suffixName
     * @return
     * @see [类、类#方法、类#成员]
     */
    protected String getView(String suffixName) {
        if (!suffixName.startsWith("/")) {
            suffixName = "/" + suffixName;
        }

        return getViewPrefix() + suffixName;
    }

    protected String getViewPrefix() {
        String currentViewPrefix = "";
        RequestMapping requestMapping = AnnotationUtils.findAnnotation(getClass(), RequestMapping.class);
        if (requestMapping != null && requestMapping.value().length > 0) {
            currentViewPrefix = requestMapping.value()[0];
        }

        if (StringUtils.isEmpty(currentViewPrefix)) {
            Class<D> entityClass = ReflectUtils.findParameterizedType(getClass(), 2);
            currentViewPrefix = entityClass.getSimpleName();
            // 去除DTO XXXDTO->XXX
            currentViewPrefix = currentViewPrefix.replace("DTO", "");
        }

        return currentViewPrefix;
    }
}
