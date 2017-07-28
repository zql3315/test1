package com.infosky.sys.entity.po;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.infosky.framework.annotation.Comment;
import com.infosky.framework.entity.po.PO;

@Entity
@Table(name = "sys_sensitiveword")
@Comment(" 敏感字表")
public class SensitiveWord extends PO<String> {

    /**
     * 
     */
    private static final long serialVersionUID = -8506554270240771058L;

    /** 敏感字符	 */
    @Comment(" 敏感字符")
    private String sensitiveWord;

    /** 替换字符	 */
    @Comment(" 替换字符")
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
