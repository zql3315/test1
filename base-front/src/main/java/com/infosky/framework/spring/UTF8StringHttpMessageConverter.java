package com.infosky.framework.spring;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.util.FileCopyUtils;

/**
 * 修改spring核心代码的字符串转换的默认格式iso->UTF-8
 * 
 * @author n004881
 */
public class UTF8StringHttpMessageConverter extends StringHttpMessageConverter {

    public static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");

    private static final MediaType utf8 = new MediaType("text", "plain", Charset.forName("UTF-8"));

    private boolean writeAcceptCharset = true;

    @Override
    protected MediaType getDefaultContentType(String dumy) {
        return utf8;
    }

    protected List<Charset> getAcceptedCharsets() {
        return Arrays.asList(utf8.getCharSet());
    }

    protected void writeInternal(String s, HttpOutputMessage outputMessage) throws IOException {
        if (this.writeAcceptCharset) {
            outputMessage.getHeaders().setAcceptCharset(getAcceptedCharsets());
        }
        Charset charset = utf8.getCharSet();
        FileCopyUtils.copy(s, new OutputStreamWriter(outputMessage.getBody(), charset));
    }

    public boolean isWriteAcceptCharset() {
        return writeAcceptCharset;
    }

    public void setWriteAcceptCharset(boolean writeAcceptCharset) {
        this.writeAcceptCharset = writeAcceptCharset;
    }
}
