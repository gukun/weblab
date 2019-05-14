/**
 * Copyright &copy; 2015-2020  All rights reserved.
 */
package com.njrz.modules.sys.service;

import java.util.List;

import com.njrz.modules.sys.utils.UserUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.njrz.common.persistence.Page;
import com.njrz.common.service.CrudService;
import com.njrz.modules.sys.entity.Lesson;
import com.njrz.modules.sys.dao.LessonDao;

/**
 * 课程Service
 * @author gk
 * @version 2016-05-09
 */
@Service
@Transactional(readOnly = true)
public class LessonService extends CrudService<LessonDao, Lesson> {

	public Lesson get(String id) {
		return super.get(id);
	}
	
	public List<Lesson> findList(Lesson lesson) {
		return super.findList(lesson);
	}
	
	public Page<Lesson> findPage(Page<Lesson> page, Lesson lesson) {
		return super.findPage(page, lesson);
	}
	
	@Transactional(readOnly = false)
	public void save(Lesson lesson) {
		super.save(lesson);
		UserUtils.removeCache(UserUtils.CACHE_LESSON_ALL_LIST);
	}
	
	@Transactional(readOnly = false)
	public void delete(Lesson lesson) {
		super.delete(lesson);
		UserUtils.removeCache(UserUtils.CACHE_LESSON_ALL_LIST);
	}
	
}