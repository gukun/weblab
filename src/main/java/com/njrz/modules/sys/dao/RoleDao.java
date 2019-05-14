/**
 * Copyright &copy; 2015-2020  All rights reserved.
 */
package com.njrz.modules.sys.dao;

import com.njrz.common.persistence.CrudDao;
import com.njrz.common.persistence.annotation.MyBatisDao;
import com.njrz.modules.sys.entity.Role;

/**
 * 角色DAO接口
 * @author
 * @version 2016-03-28
 */
@MyBatisDao
public interface RoleDao extends CrudDao<Role> {

	public Role getByName(Role role);
	
	public Role getByEnname(Role role);

	/**
	 * 维护角色与菜单权限关系
	 * @param role
	 * @return
	 */
	public int deleteRoleMenu(Role role);

	public int insertRoleMenu(Role role);
	
	/**
	 * 维护角色与公司部门关系
	 * @param role
	 * @return
	 */
	public int deleteRoleOffice(Role role);

	public int insertRoleOffice(Role role);

}
