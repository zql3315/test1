package com.infosky.framework.web;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.infosky.common.query.jpa.Searchable;
import com.infosky.common.util.DynamicSearchUtils;
import com.infosky.framework.annotation.Description;
import com.infosky.framework.entity.dto.DTO;
import com.infosky.framework.service.PagingService;

/**
 * 增删改查视图控制层定制父类实现,暂没考虑dto->vo的转化
 * 
 * @author n004881
 * @version [版本号, 2014年12月7日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public abstract class CrudController<K extends Serializable, P extends PageResult<D>, D extends DTO<K>> extends BaseController<K, P, D> {

    private static final Logger logger = LoggerFactory.getLogger(CrudController.class);

    /**
     * 预览列表页面
     * 
     * @param model
     * @return
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "preview")
    @Description(value = "预览列表页面")
    public String preview(Model model) {
        return getView("list");
    }

    /**
     * 列表页面table分页数据Search
     * 
     * @param page
     * @return
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    @Description(value = "查询列表数据")
    public PageResult<D> list(@ModelAttribute P page, @ModelAttribute("sort") String sorts, HttpServletRequest request) {
        Searchable s = DynamicSearchUtils.toSearchable(request);
        try {
            if (StringUtils.isNotBlank(sorts)) {
                Sort sort = parseSort(sorts);
                getService().findAll(s, page, sort);
            } else {
                getService().findAll(s, page);
            }
        } catch (Exception e) {
            logger.error("", e);
        }

        return page;
    }

    /**
     * 封装排序
     */
    protected Sort parseSort(String sorts) {
        Sort sort = null;
        if (StringUtils.isNotBlank(sorts)) {
            List<Order> orders = new ArrayList<Order>();
            try {
                JSONArray array = JSONArray.fromObject(sorts);
                for (int i = 0; i < array.size(); i++) {
                    sorts = array.getString(i);
                    if (JSONObject.fromObject(sorts).getString("sort").toUpperCase().equalsIgnoreCase("desc")) {
                        orders.add(new Order(Direction.DESC, JSONObject.fromObject(sorts).getString("fileName")));
                    } else {
                        orders.add(new Order(Direction.ASC, JSONObject.fromObject(sorts).getString("fileName")));
                    }
                }
                return new Sort(orders);
            } catch (Exception e) {

            }
            if (JSONObject.fromObject(sorts).get("data") != null) {
                JSONArray array = JSONObject.fromObject(sorts).getJSONArray("data");
                for (int i = 0; i < array.size(); i++) {
                    sorts = array.getString(i);
                    if (JSONObject.fromObject(sorts).getString("sort").toUpperCase().equalsIgnoreCase("desc")) {
                        orders.add(new Order(Direction.DESC, JSONObject.fromObject(sorts).getString("fileName")));
                    } else {
                        orders.add(new Order(Direction.ASC, JSONObject.fromObject(sorts).getString("fileName")));
                    }
                }
                sort = new Sort(orders);
            } else {
                if (JSONObject.fromObject(sorts).getString("sort").toUpperCase().equalsIgnoreCase("desc")) {
                    sort = new Sort(Sort.Direction.DESC, JSONObject.fromObject(sorts).getString("fileName").toString());
                } else {
                    sort = new Sort(Sort.Direction.ASC, JSONObject.fromObject(sorts).getString("fileName").toString());
                }
            }
        }
        return sort;
    }

    @RequestMapping(value = "/find")
    @ResponseBody
    @Description(value = "查询请求")
    public List<D> find(HttpServletRequest request) {
        // 获取请求的参数
        Searchable s = DynamicSearchUtils.toSearchable(request);

        List<D> result = Lists.newArrayList();

        try {
            result = (List<D>) getService().findAll(s);
        } catch (Exception e) {
            logger.error("", e);
        }

        return result;
    }

    /**
     * 新增页面
     * 
     * @param model
     * @return
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "toAdd")
    @Description(value = "打开添加页面")
    public String toAdd(Model model) {
        return getView("add");
    }

    /**
     * 新增导入页面
     * 
     * @param model
     * @return
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "toImport")
    @Description(value = "打开导入页面")
    public String toImport(Model model) {
        return getView("import");
    }

    @RequestMapping(value = "add")
    @ResponseBody
    @Description(value = "添加数据")
    public Map<String, Object> add(@RequestBody D b) {
        Map<String, Object> result = Maps.newHashMap();
        result.put("result", false);
        result.put("msg", "添加失败");
        try {
            b = getService().save(b);
            result.put("result", true);
            result.put("id", b.getId());
            result.put("msg", "添加成功");
        } catch (Exception e) {
            logger.error("", e);
        }

        return result;
    }

    /**
     * 编辑页面
     * 
     * @param model
     * @return
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "toEdit/{id}")
    @Description(value = "打开编辑页面")
    public String toEdit(@PathVariable K id, Model model) {
        D d = this.getService().find(id);

        model.addAttribute("model", d);

        return getView("edit");
    }

    @RequestMapping(value = "edit")
    @ResponseBody
    @Description(value = "更新数据")
    public Map<String, Object> edit(@RequestBody D b) {
        Map<String, Object> result = Maps.newHashMap();
        result.put("result", false);
        result.put("msg", "编辑失败");
        try {
            getService().update(b);
            result.put("result", true);
            result.put("msg", "更新成功");
        } catch (Exception e) {
            logger.error("", e);
        }

        return result;
    }

    @RequestMapping(value = "delete")
    @ResponseBody
    @Description(value = "根据对象删除数据")
    public Map<String, Object> delete(@RequestBody D b) {
        Map<String, Object> result = Maps.newHashMap();
        result.put("result", false);

        try {
            getService().delete(b);
            result.put("result", true);
        } catch (Exception e) {
            logger.error("", e);
        }

        return result;
    }

    @RequestMapping(value = "deleteById")
    @ResponseBody
    @Description(value = "根据主键删除数据")
    public Map<String, Object> delete(K id) {
        Map<String, Object> result = Maps.newHashMap();
        result.put("result", false);

        try {
            getService().delete(id);
            result.put("result", true);
        } catch (Exception e) {
            logger.error("", e);
        }

        return result;
    }

    @RequestMapping(value = "deleteByIds")
    @ResponseBody
    @Description(value = "根据多个id批量删除数据")
    public Map<String, Object> deleteByIds(@RequestBody List<D> b) {
        Map<String, Object> result = Maps.newHashMap();
        result.put("result", false);
        try {
            getService().deleteAll(b);
            result.put("result", true);
        } catch (Exception e) {
            logger.error("", e);
        }

        return result;
    }

    @RequestMapping(value = "view/{id}")
    @Description(value = "查看数据")
    public String view(@PathVariable K id, Model model) {
        try {
            D view = getService().find(id);

            model.addAttribute("view", view);
        } catch (Exception e) {
            logger.error("", e);
            // throw 控制层异常
        }

        return getView("view");
    }

    /**
     * 校验是否存在该对象,新增时常用
     * 
     * @param b
     * @return
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "/exists")
    @ResponseBody
    public boolean exists(HttpServletRequest request) {
        // 获取请求的参数
        Searchable s = DynamicSearchUtils.toSearchable(request);

        List<D> result = Lists.newArrayList();

        try {
            result = (List<D>) getService().findAll(s);
        } catch (Exception e) {
            logger.error("", e);
        }

        if (result.isEmpty()) {
            return true;
        }

        return false;
    }

    /**
     * 获取当前模块业务类
     * 
     * @return
     * @see [类、类#方法、类#成员]
     */
    public abstract PagingService<D, P, K> getService();

    protected Map<String, Object> success() {

        Map<String, Object> reuslt = new HashMap<String, Object>();
        reuslt.put("result", true);
        return reuslt;
    }

    protected Map<String, Object> fail() {

        Map<String, Object> reuslt = new HashMap<String, Object>();
        reuslt.put("result", false);
        return reuslt;
    }
}
