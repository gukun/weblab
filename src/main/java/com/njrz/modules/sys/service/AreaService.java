/**
 * Copyright &copy; 2015-2020  All rights reserved.
 */
package com.njrz.modules.sys.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.njrz.common.service.TreeService;
import com.njrz.modules.sys.dao.AreaDao;
import com.njrz.modules.sys.entity.Area;
import com.njrz.modules.sys.utils.UserUtils;

/**
 * 区域Service
 * @author
 * @version 2016-03-28
 */
@Service
@Transactional(readOnly = true)
public class AreaService extends TreeService<AreaDao, Area> {

	public List<Area> findAll(){
		return UserUtils.getAreaList();
	}

	@Transactional(readOnly = false)
	public void save(Area area) {
		super.save(area);
		UserUtils.removeCache(UserUtils.CACHE_AREA_LIST);
	}
	
	@Transactional(readOnly = false)
	public void delete(Area area) {
		super.delete(area);
		UserUtils.removeCache(UserUtils.CACHE_AREA_LIST);
	}
	
}
