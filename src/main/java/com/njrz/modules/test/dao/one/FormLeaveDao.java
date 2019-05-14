/**
 * Copyright &copy; 2015-2020  All rights reserved.
 */
package com.njrz.modules.test.dao.one;

import com.njrz.common.persistence.CrudDao;
import com.njrz.common.persistence.annotation.MyBatisDao;
import com.njrz.modules.test.entity.one.FormLeave;

/**
 * 请假单DAO接口
 * @author lgf
 * @version 2016-03-28
 */
@MyBatisDao
public interface FormLeaveDao extends CrudDao<FormLeave> {
	
}