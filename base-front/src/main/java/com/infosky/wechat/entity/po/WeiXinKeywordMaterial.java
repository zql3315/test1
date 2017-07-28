package com.infosky.wechat.entity.po;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.infosky.framework.annotation.Comment;
import com.infosky.framework.entity.po.PO;

@Entity
@Table(name = "weixin_keyword_material")
@Comment("微信关键字素材关联关系表")
public class WeiXinKeywordMaterial extends PO<String> {

    private static final long serialVersionUID = -3044829538687716108L;

    /*========================================================================*
    *                         Public Fields (公共属性)                                                                
    *========================================================================*/

    /*========================================================================*
     *                         Private Fields (私有属性)                                                                
     *========================================================================*/

    /**
     * 关键字
     */
    @ManyToOne
    @JoinColumn(name = "KID")
    private WeiXinKeyword weiXinKeyword;

    /**
     * 素材
     */
    @ManyToOne
    @JoinColumn(name = "MID")
    private WeiXinMaterial weiXinMaterial;

    public WeiXinKeyword getWeiXinKeyword() {
        return weiXinKeyword;
    }

    public void setWeiXinKeyword(WeiXinKeyword weiXinKeyword) {
        this.weiXinKeyword = weiXinKeyword;
    }

    public WeiXinMaterial getWeiXinMaterial() {
        return weiXinMaterial;
    }

    public void setWeiXinMaterial(WeiXinMaterial weiXinMaterial) {
        this.weiXinMaterial = weiXinMaterial;
    }

    /*========================================================================*
     *                         Construct Methods (构造方法) 
     *========================================================================*/

    /*========================================================================*
     *                         Public Methods (公有方法)                                                                   
     *========================================================================*/

    /*========================================================================*
     *                         Private Methods (私有方法)                                                                   
     *========================================================================*/
}
