package com.infosky.build.dao;

import org.springframework.stereotype.Repository;

import com.infosky.build.entity.po.Project;
import com.infosky.framework.dao.DAO;

@Repository
public interface ProjectDAO extends DAO<Project, String> {

}
