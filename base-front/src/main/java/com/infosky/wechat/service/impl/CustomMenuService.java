package com.infosky.wechat.service.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Collection;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.PropertyFilter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.infosky.common.mapper.BeanMapper;
import com.infosky.common.query.jpa.Searchable;
import com.infosky.framework.dao.DAO;
import com.infosky.framework.service.JpaService;
import com.infosky.framework.web.PageResult;
import com.infosky.wechat.dao.CustomMenuDAO;
import com.infosky.wechat.entity.dto.CustomMenuDTO;
import com.infosky.wechat.entity.dto.HttpCustomMenu;
import com.infosky.wechat.entity.po.CustomMenu;

@Service
@Transactional
public class CustomMenuService extends JpaService<CustomMenu, CustomMenuDTO, PageResult<CustomMenuDTO>, String> {

    @Autowired
    private CustomMenuDAO dao;

    @Autowired
    private WeiXinPublicAccountService weiXinPublicAccountService;

    public List<CustomMenuDTO> findByPid(String pid) {
        List<CustomMenu> listp = dao.findByPid(pid);
        return (List<CustomMenuDTO>) BeanMapper.mapList(listp, CustomMenuDTO.class);
    }

    @Override
    public Collection<CustomMenuDTO> findAll(Searchable s, PageResult<CustomMenuDTO> page) {
        return super.findAll(s, page);
    }

    /** {@inheritDoc} */

    @Override
    protected DAO<CustomMenu, String> getDAO() {
        return dao;
    }

    /**
     * 
     * @param json
     */
    public boolean sorts(JSONObject json) {

        try {
            JSONArray array = json.getJSONArray("data");
            for (int i = 0; i < array.size(); i++) {
                JSONObject temp = (JSONObject) array.get(i);
                CustomMenu s = dao.findOne(temp.getString("id"));
                s.setOrderNum(i + 1);
                //logger.debug("============"+temp);
                if (temp.get("children") != null) {
                    //logger.debug("8888888="+temp.get("children"));
                    JSONArray array1 = temp.getJSONArray("children");
                    for (int j = 0; j < array1.size(); j++) {
                        JSONObject temp1 = (JSONObject) array1.get(j);
                        CustomMenu s1 = dao.findOne(temp1.getString("id"));
                        s1.setOrderNum(j + 1);

                        //logger.debug("++++++++"+temp1);	

                    }

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public String release() {
        //获取自定义菜单列表
        List<CustomMenu> list = dao.findByPid("0");
        List<HttpCustomMenu> httpList = HttpCustomMenu.toHttp(list);
        for (int i = 0; i < httpList.size(); i++) {
            HttpCustomMenu httpCustomMenu = httpList.get(i);
            List<CustomMenu> sub_list = dao.findByPid(httpCustomMenu.getId());
            //logger.debug("pid="+httpCustomMenu.getId());
            httpList.get(i).setSub_button(HttpCustomMenu.toHttp(sub_list));
            if (sub_list != null && sub_list.size() > 0) {
                //logger.debug("子菜单数量："+sub_list.size());
                httpList.get(i).setType(null);
                httpList.get(i).setKey(null);
                httpList.get(i).setUrl(null);
            }
        }
        JSONObject json = new JSONObject();
        //过滤json转换时去掉不要的属性
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.setExcludes(new String[] {
                "handler", "hibernateLazyInitializer", "filed1", "filed2", "filed1"
        });
        jsonConfig.setJsonPropertyFilter(new PropertyFilter() {

            public boolean apply(Object obj, String name, Object value) {
                if (value == null || value.equals("") || (value instanceof Collection && ((Collection<HttpCustomMenu>) value).size() <= 0)) {
                    return true;
                } else if ("id".equals(name)) {
                    return true;
                } else {
                    return false;
                }
            }
        });
        json.put("button", JSONArray.fromObject(httpList, jsonConfig));
        OutputStreamWriter out = null;
        String result = "";
        try {
            String accesstoken = weiXinPublicAccountService.getAccessToken();
            URL postUrl = new URL("https://api.weixin.qq.com/cgi-bin/menu/create?access_token=" + accesstoken);
            URLConnection connection = postUrl.openConnection();

            connection.setDoOutput(true);// 发送POST请求必须设置如下两行  
            connection.setDoInput(true);
            out = new OutputStreamWriter(connection.getOutputStream(), "UTF-8");
            //logger.debug(json.toString());
            out.write(json.toString().replaceAll("menu", "click"));
            out.flush();// flush输出流的缓冲  
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
            String line;
            StringBuffer str = new StringBuffer();
            while ((line = reader.readLine()) != null) {
                str.append(line);
            }
            result = JSONObject.fromObject(str.toString()).getString("errmsg");
            out.close();
            reader.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 根据Id删除菜单
     * @param id
     */
    public void deleteById(String id) {
        dao.deleteById(id);
    }

    /**
     * 查询一级菜单
     * @return
     */
    public List<CustomMenuDTO> queryFirstMenus() {
        List<CustomMenu> weiXinCustomMenuLst = dao.queryFirstMenus();

        return (List<CustomMenuDTO>) BeanMapper.mapList(weiXinCustomMenuLst, CustomMenuDTO.class);
    }

}
