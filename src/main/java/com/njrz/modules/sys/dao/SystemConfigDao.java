/**
 * Copyright &copy; 2015-2020  All rights reserved.
 */
package com.njrz.modules.sys.dao;

import com.njrz.common.persistence.CrudDao;
import com.njrz.common.persistence.annotation.MyBatisDao;
import com.njrz.modules.sys.entity.SystemConfig;

/**
 * 系统配置DAO接口
 * @author liugf
 * @version 2016-03-28
 */
@MyBatisDao
public interface SystemConfigDao extends CrudDao<SystemConfig> {
	
}