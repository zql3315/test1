package com.infosky.sys.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.infosky.common.query.jpa.Operator;
import com.infosky.common.query.jpa.SearchRequest;
import com.infosky.common.query.jpa.Searchable;
import com.infosky.common.util.DynamicSearchUtils;
import com.infosky.framework.dao.DAO;
import com.infosky.framework.service.JpaService;
import com.infosky.framework.web.PageResult;
import com.infosky.sys.dao.DictionaryDAO;
import com.infosky.sys.entity.dto.DictionaryDTO;
import com.infosky.sys.entity.po.Dictionary;

/**
 * 字典管理
 * 
 * @author n004881
 */
@Service
@Transactional
public class DictionaryService extends JpaService<Dictionary, DictionaryDTO, PageResult<DictionaryDTO>, String> {

    @Autowired
    private DictionaryDAO dao;

    public Dictionary findByDatakey(String datakey) {

        return dao.findByDatakey(datakey);
    }

    /**
     * Ztree
     * 
     * @return
     */
    public List<Map<String, Object>> buildTree() {
        Searchable s = new SearchRequest();
        s.addSearchParam("parent.id", Operator.ISNULL, null);
        Specification<Dictionary> specification = DynamicSearchUtils.toSpecification(s);
        List<Dictionary> dictionary = dao.findAll(specification);
        return iterator(dictionary);
    }

    /**
     * 迭代子节点
     * 
     * @param resources
     * @return
     * @see [类、类#方法、类#成员]
     */
    protected List<Map<String, Object>> iterator(List<Dictionary> dictionary) {
        List<Map<String, Object>> treeData = Lists.newArrayList();
        for (Dictionary d : dictionary) {
            Map<String, Object> node = Maps.newHashMap();
            node.put("id", d.getId());
            node.put("pId", d.getParent() != null ? d.getParent().getId() : null);
            node.put("name", d.getItemvalue());
            node.put("open", true);

            //如果有子节点
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
    protected DAO<Dictionary, String> getDAO() {
        return dao;
    }

}
