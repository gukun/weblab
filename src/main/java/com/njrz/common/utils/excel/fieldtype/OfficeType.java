/**
 * Copyright &copy; 2015-2020  All rights reserved.
 */
package com.njrz.common.utils.excel.fieldtype;

import com.njrz.common.utils.StringUtils;
import com.njrz.modules.sys.entity.Office;
import com.njrz.modules.sys.utils.UserUtils;

/**
 * 字段类型转换
 * @author
 * @version 2016-03-28
 */
public class OfficeType {

	/**
	 * 获取对象值（导入）
	 */
	public static Object getValue(String val) {
		for (Office e : UserUtils.getOfficeList()){
			if (StringUtils.trimToEmpty(val).equals(e.getName())){
				return e;
			}
		}
		return null;
	}

	/**
	 * 设置对象值（导出）
	 */
	public static String setValue(Object val) {
		if (val != null && ((Office)val).getName() != null){
			return ((Office)val).getName();
		}
		return "";
	}
}
