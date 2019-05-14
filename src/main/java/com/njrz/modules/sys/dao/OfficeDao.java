/**
 * Copyright &copy; 2015-2020  All rights reserved.
 */
package com.njrz.modules.sys.dao;

import com.njrz.common.persistence.TreeDao;
import com.njrz.common.persistence.annotation.MyBatisDao;
import com.njrz.modules.sys.entity.Office;

/**
 * 机构DAO接口
 * @author
 * @version 2016-03-28
 */
@MyBatisDao
public interface OfficeDao extends TreeDao<Office> {
	
	public Office getByCode(String code);
}
