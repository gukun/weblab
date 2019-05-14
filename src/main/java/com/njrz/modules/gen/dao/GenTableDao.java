package com.njrz.modules.gen.dao;

import org.apache.ibatis.annotations.Param;

import com.njrz.common.persistence.CrudDao;
import com.njrz.common.persistence.annotation.MyBatisDao;
import com.njrz.modules.gen.entity.GenTable;

/**
 * 业务表  Dao接口
 * 〈一句话功能简述〉
 * 〈功能详细描述〉     
 * @author qizhonghai 
 * @since v1.0
 * @date 2016-3-8 上午10:30:57
 */
@MyBatisDao
public abstract interface GenTableDao extends CrudDao<GenTable>
{
	/**
	 * 在数据库中执行一条sql  
	 * @param  paramString    sql语句
	 * @return int
	 */
  public abstract int buildTable(@Param("sql") String paramString);
}

