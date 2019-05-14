/**
 * Copyright &copy; 2015-2020  All rights reserved.
 */
package com.njrz.modules.test.service.tree;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.njrz.common.service.TreeService;
import com.njrz.common.utils.StringUtils;
import com.njrz.modules.test.dao.tree.TestTreeDao;
import com.njrz.modules.test.entity.tree.TestTree;

/**
 * 组织机构Service
 * @author liugf
 * @version 2016-03-28
 */
@Service
@Transactional(readOnly = true)
public class TestTreeService extends TreeService<TestTreeDao, TestTree> {

	public TestTree get(String id) {
		return super.get(id);
	}
	
	public List<TestTree> findList(TestTree testTree) {
		if (StringUtils.isNotBlank(testTree.getParentIds())){
			testTree.setParentIds(","+testTree.getParentIds()+",");
		}
		return super.findList(testTree);
	}
	
	@Transactional(readOnly = false)
	public void save(TestTree testTree) {
		super.save(testTree);
	}
	
	@Transactional(readOnly = false)
	public void delete(TestTree testTree) {
		super.delete(testTree);
	}
	
}