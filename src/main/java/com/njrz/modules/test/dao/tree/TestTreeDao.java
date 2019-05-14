/**
 * Copyright &copy; 2015-2020  All rights reserved.
 */
package com.njrz.modules.test.dao.tree;

import com.njrz.common.persistence.TreeDao;
import com.njrz.common.persistence.annotation.MyBatisDao;
import com.njrz.modules.test.entity.tree.TestTree;

/**
 * 组织机构DAO接口
 * @author liugf
 * @version 2016-03-28
 */
@MyBatisDao
public interface TestTreeDao extends TreeDao<TestTree> {
	
}