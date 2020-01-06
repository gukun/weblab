/**
 * Copyright &copy; 2015-2020  All rights reserved.
 */
package com.njrz.modules.lab.service;

import java.util.List;

import com.njrz.common.utils.CacheUtils;
import com.njrz.modules.sys.utils.LogUtils;
import com.njrz.modules.sys.utils.UserUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.njrz.common.persistence.Page;
import com.njrz.common.service.CrudService;
import com.njrz.modules.lab.entity.Lab;
import com.njrz.modules.lab.dao.LabDao;

/**
 * 实验室管理Service
 * @author gk
 * @version 2019-05-09
 */
@Service
@Transactional(readOnly = true)
public class LabService extends CrudService<LabDao, Lab> {

	public Lab get(String id) {
		return super.get(id);
	}
	
	public List<Lab> findList(Lab lab) {
		return super.findList(lab);
	}
	
	public Page<Lab> findPage(Page<Lab> page, Lab lab) {
		return super.findPage(page, lab);
	}
	
	@Transactional(readOnly = false)
	public void save(Lab lab) {
		super.save(lab);

	}
	
	@Transactional(readOnly = false)
	public void delete(Lab lab) {
		super.delete(lab);
	}
	
}