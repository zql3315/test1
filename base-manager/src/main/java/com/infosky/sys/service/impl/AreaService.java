package com.infosky.sys.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.infosky.common.query.jpa.Searchable;
import com.infosky.common.util.DynamicSearchUtils;
import com.infosky.framework.dao.DAO;
import com.infosky.framework.service.JpaService;
import com.infosky.framework.web.PageResult;
import com.infosky.sys.dao.AreaDAO;
import com.infosky.sys.entity.dto.AreaDTO;
import com.infosky.sys.entity.po.Area;

/**
 * 区域管理
 * 
 * @author n004881
 */
@Service
@Transactional
public class AreaService extends JpaService<Area, AreaDTO, PageResult<AreaDTO>, String> {

    @Autowired
    private AreaDAO dao;

    /**
     * 删除热门城市
     * 
     * @return
     */
    public void delHotCity(List<AreaDTO> b) {
        for (AreaDTO area : b) {
            Area po = dao.findOne(area.getId());
            po.setIsHot(0);
        }
    }

    /**
     * Ztree
     * 
     * @return
     */
    public List<Map<String, Object>> buildTree(Searchable s) {
        Specification<Area> specification = DynamicSearchUtils.toSpecification(s);
        List<Area> dictionary = dao.findAll(specification);
        return iterator(dictionary);
    }

    /**
     * 迭代子节点
     * 
     * @param resources
     * @return
     * @see [类、类#方法、类#成员]
     */
    protected List<Map<String, Object>> iterator(List<Area> set) {
        List<Map<String, Object>> treeData = Lists.newArrayList();
        for (Area d : set) {
            Map<String, Object> node = Maps.newHashMap();
            node.put("id", d.getId());
            node.put("pId", d.getParent() != null ? d.getParent().getId() : null);
            node.put("name", d.getName());
            node.put("type", d.getLevel());
            node.put("open", true);
            if (d.getLevel() < 2) {
                node.put("iconSkin", "  ico_open ");
            }
            // 如果有子节点
            if (!d.getChildren().isEmpty()) {
                List<Map<String, Object>> subNode = iterator(d.getChildren());
                node.put("children", subNode);
            }

            treeData.add(node);
        }
        return treeData;
    }

    /** {@inheritDoc} */

    @Override
    protected DAO<Area, String> getDAO() {
        return dao;
    }

}
