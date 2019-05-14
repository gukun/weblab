/**
 * Copyright &copy; 2015-2020  All rights reserved.
 */
package com.njrz.modules.tools.dao;

import com.njrz.common.persistence.CrudDao;
import com.njrz.common.persistence.annotation.MyBatisDao;
import com.njrz.modules.tools.entity.TestInterface;

/**
 * 接口DAO接口
 * @author lgf
 * @version 2016-03-28
 */
@MyBatisDao
public interface TestInterfaceDao extends CrudDao<TestInterface> {
	
}