/**
 * Copyright &copy; 2015-2020  All rights reserved.
 */
package com.njrz.modules.lab.entity;

import org.hibernate.validator.constraints.Length;

import com.njrz.common.persistence.DataEntity;
import com.njrz.common.utils.excel.annotation.ExcelField;

/**
 * 实验室管理Entity
 * @author gk
 * @version 2019-05-09
 */
public class Lab extends DataEntity<Lab> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 实验室名称
	private String lesson;		// 所属课程

	private String labType;		// 类别

	private String lessonName;		// 所属课程名
	
	public Lab() {
		super();
	}

	public Lab(String id){
		super(id);
	}

	@Length(min=1, max=64, message="实验室名称长度必须介于 1 和 64 之间")
	@ExcelField(title="实验室名称", align=2, sort=1)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Length(min=1, max=64, message="所属课程长度必须介于 1 和 64 之间")
	@ExcelField(title="所属课程", align=2, sort=2)
	public String getLesson() {
		return lesson;
	}

	public void setLesson(String lesson) {
		this.lesson = lesson;
	}
	
	@Length(min=1, max=64, message="类别长度必须介于 1 和 64 之间")
	@ExcelField(title="类别", align=2, sort=3)
	public String getLabType() {
		return labType;
	}

	public void setLabType(String labType) {
		this.labType = labType;
	}

	public void setLessonName(String lessonName) {
		this.lessonName = lessonName;
	}

	public String getLessonName() {
		return lessonName;
	}

}