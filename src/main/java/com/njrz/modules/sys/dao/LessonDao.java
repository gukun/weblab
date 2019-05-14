/**
 * Copyright &copy; 2015-2020  All rights reserved.
 */
package com.njrz.modules.sys.dao;

import com.njrz.common.persistence.CrudDao;
import com.njrz.common.persistence.annotation.MyBatisDao;
import com.njrz.modules.sys.entity.Lesson;

/**
 * 课程DAO接口
 * @author gk
 * @version 2016-05-09
 */
@MyBatisDao
public interface LessonDao extends CrudDao<Lesson> {
	
}