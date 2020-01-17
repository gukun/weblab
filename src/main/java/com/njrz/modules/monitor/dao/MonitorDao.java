/**
 * Copyright &copy; 2015-2020  All rights reserved.
 */
package com.njrz.modules.monitor.dao;

import com.njrz.common.persistence.CrudDao;
import com.njrz.common.persistence.annotation.MyBatisDao;
import com.njrz.modules.monitor.entity.Monitor;

/**
 * 系统监控DAO接口
 * @author liugf
 * @version 2020-03-28
 */
@MyBatisDao
public interface MonitorDao extends CrudDao<Monitor> {
	
}