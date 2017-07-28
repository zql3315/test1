package com.infosky.sys.dao;

import org.springframework.stereotype.Repository;

import com.infosky.framework.dao.DAO;
import com.infosky.sys.entity.po.SensitiveWord;

@Repository
public interface SensitiveWordDAO extends DAO<SensitiveWord, String> {

}