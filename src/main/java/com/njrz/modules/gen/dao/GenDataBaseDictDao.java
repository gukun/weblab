package com.njrz.modules.gen.dao;

import java.util.List;

import com.njrz.common.persistence.CrudDao;
import com.njrz.common.persistence.annotation.MyBatisDao;
import com.njrz.modules.gen.entity.GenTable;
import com.njrz.modules.gen.entity.GenTableColumn;

/**
 * 业务数据  的Dao接口
 * 〈主要是业务表和业务字段表的关联查询〉
 * @author qizhonghai 
 * @since v1.0
 * @date 2016-3-8 上午10:27:46
 */
@MyBatisDao
public abstract interface GenDataBaseDictDao extends CrudDao<GenTableColumn>
{
	/**
	 * 根据业务表信息  查询数据库的表名及表描述信息（获取的是整个数据库的表）
	 * @param paramGenTable   业务表信息对象   
	 * @return List<GenTable>
	 */
  public abstract List<GenTable> findTableList(GenTable paramGenTable);

  /**
   *  根据业务表信息  查询业务表字段,查整个数据库，获取数据库表的字段
   * @param  paramGenTable   业务表信息对象
   * @return List<GenTableColumn>
   */
  public abstract List<GenTableColumn> findTableColumnList(GenTable paramGenTable);

  /**
   *  根据业务表信息  查询
   * @param  paramGenTable   业务表信息对象
   * @return List<GenTableColumn>
   */
  public abstract List<String> findTablePK(GenTable paramGenTable);
}
