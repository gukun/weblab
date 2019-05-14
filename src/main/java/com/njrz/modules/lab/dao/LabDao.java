/**
 * Copyright &copy; 2015-2020  All rights reserved.
 */
package com.njrz.modules.lab.dao;

import com.njrz.common.persistence.CrudDao;
import com.njrz.common.persistence.annotation.MyBatisDao;
import com.njrz.modules.lab.entity.Lab;

/**
 * 实验室管理DAO接口
 * @author gk
 * @version 2016-05-09
 */
@MyBatisDao
public interface LabDao extends CrudDao<Lab> {
	
}