/**
 * Copyright &copy; 2015-2020  All rights reserved.
 */
package com.njrz.modules.iim.dao;

import com.njrz.common.persistence.CrudDao;
import com.njrz.common.persistence.annotation.MyBatisDao;
import com.njrz.modules.iim.entity.Mail;

/**
 * 发件箱DAO接口
 * @author
 * @version 2016-03-28
 */
@MyBatisDao
public interface MailDao extends CrudDao<Mail> {
	public int getCount(MailDao entity);
}