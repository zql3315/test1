package com.infosky.sys.entity.dto;

import com.infosky.framework.entity.dto.DTO;

public class SensitiveWordDTO extends DTO<String> {

    /**
     * 
     */
    private static final long serialVersionUID = -8515809280397335849L;

    /** 敏感字符 */
    private String sensitiveWord;

    /** 替换字符 */
    private String replaceWord;

    public String getSensitiveWord() {
        return sensitiveWord;
    }

    public void setSensitiveWord(String sensitiveWord) {
        this.sensitiveWord = sensitiveWord;
    }

    public String getReplaceWord() {
        return replaceWord;
    }

    public void setReplaceWord(String replaceWord) {
        this.replaceWord = replaceWord;
    }

}
