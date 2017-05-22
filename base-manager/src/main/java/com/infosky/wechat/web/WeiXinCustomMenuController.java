package com.infosky.wechat.web;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Maps;
import com.infosky.common.query.jpa.Operator;
import com.infosky.common.query.jpa.SearchRequest;
import com.infosky.common.query.jpa.Searchable;
import com.infosky.framework.annotation.Description;
import com.infosky.framework.service.PagingService;
import com.infosky.framework.web.CrudController;
import com.infosky.framework.web.PageResult;
import com.infosky.wechat.common.WxConfigUtil;
import com.infosky.wechat.entity.dto.CustomMenuDTO;
import com.infosky.wechat.entity.dto.WeiXinKeywordDTO;
import com.infosky.wechat.service.impl.CustomMenuService;
import com.infosky.wechat.service.impl.WeiXinKeywordService;
import com.infosky.wechat.service.impl.WeiXinPublicAccountService;

/**
 * 自定义菜单控制类
 * @author n004881
 *
 */
@Controller
@RequestMapping("weiXin/customMenu/")
@Description(value = "自定义菜单")
public class WeiXinCustomMenuController extends CrudController<String, PageResult<CustomMenuDTO>, CustomMenuDTO> {

    @Autowired
    private CustomMenuService customMenuService;

    @Autowired
    private WeiXinKeywordService weiXinKeywordService;

    @Autowired
    private WeiXinPublicAccountService weiXinPublicAccountService;

    private static final Logger logger = LoggerFactory.getLogger(CustomMenuService.class);

    /**
     * 预览页面
     * @param model
     * @return
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "preview")
    @Description(value = "预览页面")
    public String preview(Model model) {
        Searchable s = new SearchRequest();
        s.addSearchParam("parent.id", Operator.ISNULL, null);
        Sort sort = new Sort(Sort.Direction.ASC, "orderNum");
        Collection<CustomMenuDTO> list = customMenuService.findAllBySort(s, sort);
        model.addAttribute("model", list);

        List<WeiXinKeywordDTO> weiXinKeywordLst = (List<WeiXinKeywordDTO>) weiXinKeywordService.findAll();
        model.addAttribute("weiXinKeywordLst", weiXinKeywordLst);

        return getView("list");
    }

    @RequestMapping(value = "getPreview")
    @ResponseBody
    @Description(value = "微信预览")
    public Map<String, Object> list1(Model model) {
        Map<String, Object> map = new HashMap<String, Object>();
        Searchable s = new SearchRequest();
        s.addSearchParam("parent.id", Operator.ISNULL, null);
        Sort sort = new Sort(Sort.Direction.ASC, "orderNum");
        Collection<CustomMenuDTO> list = customMenuService.findAllBySort(s, sort);
        map.put("previewlist", list);
        System.out.println(list);
        return map;
    }

    /**
     * 保存自定义菜单
     * @param model
     * @return
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "sortMenu")
    @ResponseBody
    @Description(value = "预览页面")
    public Map<String, String> sortMenu(HttpServletRequest request, @RequestBody JSONObject json) {
        boolean b = customMenuService.sorts(json);
        Map<String, String> map = new HashMap<String, String>();

        if (b) {
            map.put("result", "成功");
        } else {
            map.put("result", "失败");
        }

        return map;
    }

    @RequestMapping(value = "addMenu")
    @ResponseBody
    @Description(value = "添加菜单")
    public String addMenu(HttpServletRequest request, @RequestBody JSONObject json) {
        String pid = (json.get("pid") != null && !json.getString("pid").equals("0")) ? json.getString("pid") : null;
        String menuName = json.getString("menuName");
        String triggerType = json.getString("triggerType");
        String state = json.getString("state");
        Integer subnum = json.getInt("subnum");

        List<CustomMenuDTO> sub_list = customMenuService.findByPid(pid);

        //添加之前把ordernumber重新赋值
        for (int i = 0; i < sub_list.size(); i++) {
            CustomMenuDTO cm = sub_list.get(i);
            cm.setOrderNum(i + 1);
            customMenuService.save(cm);

        }

        //获取orderNum
        CustomMenuDTO parent = null;
        Integer orderNum = 1;
        if (StringUtils.isNotBlank(pid)) {
            parent = new CustomMenuDTO();
            orderNum = sub_list.size() + 1;
            parent.setId(pid);
        } else {
            orderNum = subnum + 1;
        }

        CustomMenuDTO customenu = new CustomMenuDTO();
        customenu.setOrderNum(orderNum);
        customenu.setMenuName(menuName);
        customenu.setCreateTime(new Date());
        customenu.setTriggerType(triggerType);
        customenu.setState(state);
        customenu.setParent(parent);
        customMenuService.save(customenu);

        return "SUCCESS";

    }

    @RequestMapping(value = "editMenu")
    @ResponseBody
    @Description(value = "编辑菜单")
    public String editMenu(HttpServletRequest request, @RequestBody JSONObject json) {
        String pid = json.getString("pid");
        String triggerContent = json.getString("triggerContent");
        String triggerType = json.getString("triggerType");

        CustomMenuDTO customenu = customMenuService.find(pid);
        customenu.setTriggerContent(triggerContent);
        customenu.setTriggerType(triggerType);

        customMenuService.update(customenu);

        return "SUCCESS";
    }

    @RequestMapping(value = "deleteMenu")
    @ResponseBody
    @Description(value = "删除")
    public String deleteMenu(HttpServletRequest request, @RequestBody JSONObject json) {
        String pid = json.getString("pid");
        CustomMenuDTO customenu = customMenuService.find(pid);
        List<CustomMenuDTO> subMenuLst = customenu.getChildren();

        if (subMenuLst != null && subMenuLst.size() > 0) {
            customMenuService.deleteAll(subMenuLst);
        }

        customMenuService.delete(customenu);

        //二级菜单删除不了需做特殊处理
        if (customenu != null) {
            customMenuService.deleteById(pid);
        }

        return "SUCCESS";
    }

    @RequestMapping(value = "renameMenu")
    @ResponseBody
    @Description(value = "菜单重命名")
    public String renameMenu(HttpServletRequest request, @RequestBody JSONObject json) {
        String pid = json.getString("pid");
        String menuName = json.getString("menuName");

        CustomMenuDTO customenu = customMenuService.find(pid);
        customenu.setMenuName(menuName);
        customMenuService.update(customenu);

        return "SUCCESS";
    }

    @RequestMapping(value = "addSublist")
    @ResponseBody
    @Description(value = "添加菜单")
    public String addSublist(HttpServletRequest request, @RequestBody JSONObject json) {
        //logger.debug("menu="+json);
        String pid = (json.get("1") != null && !json.getString("1").equals("0")) ? json.getString("1") : null;
        String menuName = json.getString("2");
        String triggerType = json.getString("3");
        String state = json.getString("4");
        Integer subnum = json.getInt("5");
        List<CustomMenuDTO> sub_list = customMenuService.findByPid(pid);
        //添加之前把ordernumber重新赋值
        for (int i = 0; i < sub_list.size(); i++) {
            CustomMenuDTO cm = sub_list.get(i);
            cm.setOrderNum(i + 1);
            customMenuService.save(cm);

        }
        //获取orderNum
        CustomMenuDTO parent = null;
        Integer orderNum = 1;
        if (StringUtils.isNotBlank(pid)) {
            parent = new CustomMenuDTO();
            //List<CustomMenuDTO> sub_list = customMenuService.findByPid(pid);
            orderNum = sub_list.size() + 1;
            parent.setId(pid);
        } else {
            orderNum = subnum + 1;
        }
        CustomMenuDTO customenu = new CustomMenuDTO();
        customenu.setOrderNum(orderNum);

        customenu.setMenuName(menuName);
        customenu.setCreateTime(new Date());
        customenu.setTriggerType(triggerType);
        customenu.setState(state);
        customenu.setParent(parent);
        customMenuService.save(customenu);
        return "SUCCESS";

    }

    @RequestMapping("toAdd2")
    @Description(value = "跳转添加子菜单")
    public String toAdd(HttpServletRequest request, Model model) {
        String id = request.getParameter("id");
        model.addAttribute("id", id);
        logger.debug("id=" + id);
        return getView("add");
    }

    @RequestMapping(value = "toEdit/{id}")
    @Description(value = "跳转编辑页面")
    public String toEdit(@PathVariable String id, Model model) {
        CustomMenuDTO customMenuDTO = customMenuService.find(id);
        List<CustomMenuDTO> subList = customMenuService.findByPid(id);
        customMenuDTO.setChildren(subList);
        model.addAttribute("model", customMenuDTO);
        return getView("edit");
    }

    @RequestMapping(value = "addbypid/{pid}")
    @Description(value = "添加子菜单")
    public String toAdd(@PathVariable String pid, Model model) {
        model.addAttribute("model", pid);
        System.out.println("pid=" + pid);
        return getView("add");
    }

    @RequestMapping(value = "findByPid/{pid}")
    @ResponseBody
    @Description("查看子菜单")
    public PageResult<CustomMenuDTO> menu2manager(@PathVariable String pid, @ModelAttribute PageResult<CustomMenuDTO> page) {
        List<CustomMenuDTO> list = customMenuService.findByPid(pid);
        page.setData(list);
        page.setRecordsTotal(list.size());
        return page;
    }

    @RequestMapping(value = "release")
    @ResponseBody
    @Description(value = "发布自定义菜单")
    public Map<String, Object> release(HttpServletRequest request, Model model) {
        Map<String, Object> result = Maps.newHashMap();
        result.put("result", false);

        try {
            int code = createPersonalMenu(result);

            if (code == 0) {
                result.put("result", true);
            }
        } catch (Exception e) {
            logger.error("releaseError", e);
        }
        return result;

    }

    /**
     * 发布自定义菜单
     * 
     * @param uid 用户
     * @param size 自动发布的次数
     * @param session
     * @return int 状态码
     */
    private int createPersonalMenu(Map<String, Object> result) {
        int statusCode = -1;

        try {
            //获取AccessToken
            String accessToken = weiXinPublicAccountService.getAccessToken();

            //从数据中获取自定义菜单数据
            List<CustomMenuDTO> weiXinCustomMenuDTOLst = (List<CustomMenuDTO>) customMenuService.findAll();

            if (weiXinCustomMenuDTOLst == null || weiXinCustomMenuDTOLst.size() <= 0) {
                return statusCode;
            }

            //拼接自定义菜单的json数据
            StringBuffer json = new StringBuffer();
            json.append("{\"button\":");
            this.buildJsonString(weiXinCustomMenuDTOLst, json, "");
            json.append("}");
            System.out.println(json);
            statusCode = Integer.valueOf(WxConfigUtil.weiXinMenuWork("create", accessToken, json.toString()));
        } catch (Exception e) {
            result.put("result", false);
            return statusCode;
        }

        return statusCode;
    }

    /**
     * 拼接微信借口菜单字符串字符串
     * @param personalMenuList 菜单集合
     * @param json 
     * @param parentid 
     */
    private void buildJsonString(List<CustomMenuDTO> weiXinCustomMenuDTOLst, StringBuffer json, String pid) {
        //根据父菜单获取所有子菜单
        List<CustomMenuDTO> firstMenuLst = getWeiXinMenuByPid(weiXinCustomMenuDTOLst, pid);

        if (firstMenuLst != null && firstMenuLst.size() <= 0) {
            return;
        }

        for (int i = 0; i < firstMenuLst.size(); i++) {
            CustomMenuDTO weiXinMenu = firstMenuLst.get(i);

            if (i > 0) {
                json.append(",{");
            } else {
                json.append("[{");
            }

            List<CustomMenuDTO> weiXinMs = getWeiXinMenuByPid(weiXinCustomMenuDTOLst, weiXinMenu.getId());

            if (weiXinMs == null || weiXinMs.size() == 0) {
                json.append("\"type\":");
                json.append("\"" + weiXinMenu.getTriggerType() + "\",");
                json.append("\"name\":").append("\"").append(weiXinMenu.getMenuName()).append("\",");
                if (weiXinMenu.getTriggerType().equals("click")) {
                    json.append("\"key\":").append("\"").append(weiXinMenu.getTriggerContent()).append("\"");

                } else {
                    json.append("\"url\":").append("\"").append(weiXinMenu.getTriggerContent()).append("\"");

                }

            } else {
                json.append("\"name\":").append("\"").append(weiXinMenu.getMenuName()).append("\",");
                json.append("\"sub_button\":");
                this.buildJsonString(weiXinCustomMenuDTOLst, json, weiXinMenu.getId());
            }
            if (i == (firstMenuLst.size() - 1)) {
                json.append("}]");
            } else {
                json.append("}");
            }
        }
    }

    /**
     * 根据父菜单获取所有子菜单
     * @param weiXinMenusList
     * @param parentid
     * @return
     */
    private List<CustomMenuDTO> getWeiXinMenuByPid(List<CustomMenuDTO> weiXinMenusList, String pid) {
        List<CustomMenuDTO> menuLst = new ArrayList<CustomMenuDTO>();

        if (StringUtils.isBlank(pid)) {
            return customMenuService.queryFirstMenus();
        }

        //首先遍历出该菜单的所有匹配pid下的所有菜单
        for (CustomMenuDTO weiXinMenu : weiXinMenusList) {
            if (weiXinMenu.getParent() != null && weiXinMenu.getParent().getId().equals(pid)) {
                menuLst.add(weiXinMenu);
            }
        }
        return menuLst;
    }

    @Override
    public PagingService<CustomMenuDTO, PageResult<CustomMenuDTO>, String> getService() {
        return customMenuService;
    }

}
