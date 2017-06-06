package com.infosky.wechat.entity.dto;

import java.util.Iterator;
import java.util.List;

import com.google.common.collect.Lists;
import com.infosky.framework.entity.dto.DTO;
import com.infosky.wechat.entity.po.CustomMenu;

public class HttpCustomMenu extends DTO<String> {

    /**
     * 
     */
    private static final long serialVersionUID = 2363889857541268311L;

    public static String TYPE_CLICK = "click";

    public static String TYPE_VIEW = "view";

    /**菜单名*/
    private String name;

    /**菜单类型*/
    private String type;

    /**网页链接*/
    private String url;

    /**菜单key值*/
    private String key;

    /**子菜单*/
    private List<HttpCustomMenu> sub_button = Lists.newArrayList();

    /**调用新增永久素材接口返回的合法media_id*/
    private String media_id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public List<HttpCustomMenu> getSub_button() {
        return sub_button;
    }

    public void setSub_button(List<HttpCustomMenu> sub_button) {
        this.sub_button = sub_button;
    }

    public String getMedia_id() {
        return media_id;
    }

    public void setMedia_id(String media_id) {
        this.media_id = media_id;
    }

    public static List<HttpCustomMenu> toHttp(List<CustomMenu> list) {
        List<HttpCustomMenu> httpList = Lists.newArrayList();
        for (Iterator<CustomMenu> iterator = list.iterator(); iterator.hasNext();) {
            CustomMenu menu = iterator.next();
            HttpCustomMenu httpcusMenu = new HttpCustomMenu();
            httpcusMenu.setId(menu.getId());
            httpcusMenu.setName(menu.getMenuName());
            String type = menu.getTriggerType();
            httpcusMenu.setType(type);
            if (type.equals(TYPE_CLICK)) {
                httpcusMenu.setKey(menu.getTriggerContent());
            } else if (type.equals(TYPE_VIEW)) {
                httpcusMenu.setUrl(menu.getTriggerContent());
            }
            httpList.add(httpcusMenu);
        }
        return httpList;
    }

}
