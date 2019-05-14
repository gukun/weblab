/**
 * Copyright &copy; 2015-2020  All rights reserved.
 */
package com.njrz.modules.sys.dao;

import java.util.List;

import com.njrz.common.persistence.CrudDao;
import com.njrz.common.persistence.annotation.MyBatisDao;
import com.njrz.modules.sys.entity.Menu;

/**
 * 菜单DAO接口
 * @author
 * @version 2016-03-28
 */
@MyBatisDao
public interface MenuDao extends CrudDao<Menu> {

	public List<Menu> findByParentIdsLike(Menu menu);

	public List<Menu> findByUserId(Menu menu);
	
	public int updateParentIds(Menu menu);
	
	public int updateSort(Menu menu);
	
}
