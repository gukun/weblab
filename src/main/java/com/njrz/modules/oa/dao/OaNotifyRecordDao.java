/**
 * Copyright &copy; 2015-2020  All rights reserved.
 */
package com.njrz.modules.oa.dao;

import java.util.List;

import com.njrz.common.persistence.CrudDao;
import com.njrz.common.persistence.annotation.MyBatisDao;
import com.njrz.modules.oa.entity.OaNotifyRecord;

/**
 * 通知通告记录DAO接口
 * @author
 * @version 2016-03-28
 */
@MyBatisDao
public interface OaNotifyRecordDao extends CrudDao<OaNotifyRecord> {

	/**
	 * 插入通知记录
	 * @param oaNotifyRecordList
	 * @return
	 */
	public int insertAll(List<OaNotifyRecord> oaNotifyRecordList);
	
	/**
	 * 根据通知ID删除通知记录
	 * @param oaNotifyId 通知ID
	 * @return
	 */
	public int deleteByOaNotifyId(String oaNotifyId);
	
}