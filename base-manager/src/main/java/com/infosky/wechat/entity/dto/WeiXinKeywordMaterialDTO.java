package com.infosky.wechat.entity.dto;

import com.infosky.framework.entity.dto.DTO;

public class WeiXinKeywordMaterialDTO extends DTO<String> {

    /*========================================================================*
    *                         Public Fields (公共属性)                                                                
    *========================================================================*/

    /*========================================================================*
     *                         Private Fields (私有属性)                                                                
     *========================================================================*/

    private static final long serialVersionUID = 4702751933269918310L;

    private WeiXinKeywordDTO weiXinKeyword;

    private WeiXinMaterialDTO weiXinMaterial;

    public WeiXinKeywordDTO getWeiXinKeyword() {
        return weiXinKeyword;
    }

    public void setWeiXinKeyword(WeiXinKeywordDTO weiXinKeyword) {
        this.weiXinKeyword = weiXinKeyword;
    }

    public WeiXinMaterialDTO getWeiXinMaterial() {
        return weiXinMaterial;
    }

    public void setWeiXinMaterial(WeiXinMaterialDTO weiXinMaterial) {
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
