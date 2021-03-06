/**
 * Copyright &copy; 2015-2020  All rights reserved.
 */
package com.njrz.common.persistence;

import java.util.List;

/**
 * DAO支持类实现
 * @author
 * @version 2016-03-28
 * @param <T>
 */
public interface TreeDao<T extends TreeEntity<T>> extends CrudDao<T> {

	/**
	 * 找到所有子节点
	 * @param entity
	 * @return
	 */
	public List<T> findByParentIdsLike(T entity);

	/**
	 * 更新所有父节点字段
	 * @param entity
	 * @return
	 */
	public int updateParentIds(T entity);
	
}