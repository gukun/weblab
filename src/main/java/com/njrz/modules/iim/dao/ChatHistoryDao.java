/**
 * Copyright &copy; 2015-2020  All rights reserved.
 */
package com.njrz.modules.iim.dao;

import java.util.List;

import com.njrz.common.persistence.CrudDao;
import com.njrz.common.persistence.annotation.MyBatisDao;
import com.njrz.modules.iim.entity.ChatHistory;

/**
 * 聊天记录DAO接口
 * @author
 * @version 2016-03-28
 */
@MyBatisDao
public interface ChatHistoryDao extends CrudDao<ChatHistory> {
	
	
	/**
	 * 查询列表数据
	 * @param entity
	 * @return
	 */
	public List<ChatHistory> findLogList(ChatHistory entity);
	
	
	public int findUnReadCount(ChatHistory chatHistory);
	
}