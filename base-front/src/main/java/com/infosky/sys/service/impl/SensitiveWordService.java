package com.infosky.sys.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.infosky.framework.dao.DAO;
import com.infosky.framework.service.JpaService;
import com.infosky.framework.web.PageResult;
import com.infosky.sys.dao.SensitiveWordDAO;
import com.infosky.sys.entity.dto.SensitiveWordDTO;
import com.infosky.sys.entity.po.SensitiveWord;

@Service
@Transactional
public class SensitiveWordService extends JpaService<SensitiveWord, SensitiveWordDTO, PageResult<SensitiveWordDTO>, String> {

    @Autowired
    private SensitiveWordDAO dao;

    /** {@inheritDoc} */

    @Override
    protected DAO<SensitiveWord, String> getDAO() {
        return dao;
    }
}
