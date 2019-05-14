/**
 * Copyright &copy; 2015-2020  All rights reserved.
 */
package com.njrz.modules.sys.entity;

import org.hibernate.validator.constraints.Length;

import com.njrz.common.persistence.DataEntity;
import com.njrz.common.utils.excel.annotation.ExcelField;

/**
 * 课程Entity
 * @author gk
 * @version 2016-05-09
 */
public class Lesson extends DataEntity<Lesson> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 课程名
	
	public Lesson() {
		super();
	}

	public Lesson(String id){
		super(id);
	}

	@Length(min=1, max=64, message="课程名长度必须介于 1 和 64 之间")
	@ExcelField(title="课程名", align=2, sort=4)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}