package com.njrz.modules.gen.dao;

import com.njrz.common.persistence.CrudDao;
import com.njrz.common.persistence.annotation.MyBatisDao;
import com.njrz.modules.gen.entity.GenTable;
import com.njrz.modules.gen.entity.GenTableColumn;

/**
 * 业务表字段   的Dao接口
 * 〈对业务字段表的增删改查〉
 * @author qizhonghai 
 * @since v1.0
 * @date 2016-3-8 上午10:28:58
 */
@MyBatisDao
public abstract interface GenTableColumnDao extends CrudDao<GenTableColumn>
{
	/**
	 * 根据业务表信息 删除业务表字段
	 * @param  paramGenTable   
	 * @return void
	 */
  public abstract void deleteByGenTable(GenTable paramGenTable);
}
