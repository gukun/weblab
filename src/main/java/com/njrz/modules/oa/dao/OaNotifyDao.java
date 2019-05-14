/**
 * Copyright &copy; 2015-2020  All rights reserved.
 */
package com.njrz.modules.oa.dao;

import com.njrz.common.persistence.CrudDao;
import com.njrz.common.persistence.annotation.MyBatisDao;
import com.njrz.modules.oa.entity.OaNotify;

/**
 * 通知通告DAO接口
 * @author
 * @version 2016-03-28
 */
@MyBatisDao
public interface OaNotifyDao extends CrudDao<OaNotify> {
	
	/**
	 * 获取通知数目
	 * @param oaNotify
	 * @return
	 */
	public Long findCount(OaNotify oaNotify);
	
}