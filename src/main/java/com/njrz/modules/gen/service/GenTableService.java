package com.njrz.modules.gen.service;

import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.njrz.common.persistence.Page;
import com.njrz.common.service.BaseService;
import com.njrz.common.utils.StringUtils;
import com.njrz.modules.gen.dao.GenDataBaseDictDao;
import com.njrz.modules.gen.dao.GenTableColumnDao;
import com.njrz.modules.gen.dao.GenTableDao;
import com.njrz.modules.gen.entity.GenTable;
import com.njrz.modules.gen.entity.GenTableColumn;
import com.njrz.modules.gen.util.GenUtils;

/**
 * 业务表的service
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 *
 * @author qizhonghai
 * @date 2016-3-8 下午1:43:45
 * @since v1.0
 */
@Service
@Transactional(readOnly = true)
public class GenTableService extends BaseService {

    @Autowired
    private GenTableDao genTableDao;

    @Autowired
    private GenTableColumnDao genTableColumnDao;

    @Autowired
    private GenDataBaseDictDao genDataBaseDictDao;

    /**
     * 根据id查询
     *
     * @param id
     * @return GenTable 包含字段的业务表信息
     */
    public GenTable get(String id) {
        GenTable genTable = (GenTable) this.genTableDao.get(id);
        GenTableColumn genTableColumn = new GenTableColumn();
        genTableColumn.setGenTable(new GenTable(genTable.getId()));
        genTable.setColumnList(this.genTableColumnDao.findList(genTableColumn));
        return genTable;
    }

    /**
     * 分页查询
     *
     * @param page     分页对象
     * @param genTable 业务表对象
     * @return Page<GenTable>
     */
    public Page<GenTable> find(Page<GenTable> page, GenTable genTable) {
        genTable.setPage(page);
        page.setList(this.genTableDao.findList(genTable));
        return page;
    }

    /**
     * 查询所有的业务表信息
     *
     * @param
     * @return List<GenTable>
     */
    public List<GenTable> findAll() {
        return this.genTableDao.findAllList(new GenTable());
    }

    /**
     * 根据业务表信息  查询数据库的表名及表描述信息（获取的是整个数据库的表）
     *
     * @param paramGenTable 业务表信息对象
     * @return List<GenTable>
     */
    public List<GenTable> findTableListFormDb(GenTable genTable) {
        return this.genDataBaseDictDao.findTableList(genTable);
    }

    /**
     * 验证  业务表名是否可以使用（查的是GenTable对应的表里的数据）
     * true：说明该表名没有使用，可以使用
     * false：说明表名已经存在，不可使用
     *
     * @param tableName 业务表名称
     * @return boolean
     */
    public boolean checkTableName(String tableName) {
        if (StringUtils.isBlank(tableName)) {
            return true;
        }
        GenTable genTable = new GenTable();
        genTable.setName(tableName);
        List<GenTable> list = this.genTableDao.findList(genTable);
        return list.size() == 0;
    }

    /**
     * 验证  业务表名是否可以使用（查的是整个数据库）
     * true：说明该表名没有使用，可以使用
     * false：说明表名已经存在，不可使用
     *
     * @param tableName 业务表名称
     * @return boolean
     */
    public boolean checkTableNameFromDB(String tableName) {
        if (StringUtils.isBlank(tableName)) {
            return true;
        }
        GenTable genTable = new GenTable();
        genTable.setName(tableName);
        List<GenTable> list = this.genDataBaseDictDao.findTableList(genTable);
        return list.size() == 0;
    }

    /**
     * 根据业务表信息  查询数据库的表名及表描述信息（获取的是整个数据库的表）
     *
     * @param paramGenTable 业务表信息对象
     * @return GenTable
     */
    public GenTable getTableFormDb(GenTable genTable) {
        if (StringUtils.isNotBlank(genTable.getName())) {
            List<GenTable> list = this.genDataBaseDictDao.findTableList(genTable);
            if (list.size() > 0) {
                if (StringUtils.isBlank(genTable.getId())) {
                    genTable = (GenTable) list.get(0);
                    if (StringUtils.isBlank(genTable.getComments())) {
                        genTable.setComments(genTable.getName());
                    }
                    genTable.setClassName(StringUtils.toCapitalizeCamelCase(genTable.getName()));
                }

                List<GenTableColumn> columnList = this.genDataBaseDictDao.findTableColumnList(genTable);
                for (GenTableColumn column : columnList) {
                    boolean b = false;
                    for (GenTableColumn e : genTable.getColumnList()) {
                        if ((e.getName() != null) && (e.getName().equals(column.getName()))) {
                            b = true;
                        }
                    }
                    if (!b) {
                        genTable.getColumnList().add(column);
                    }

                }

                for (GenTableColumn e : genTable.getColumnList()) {
                    boolean b = false;
                    for (GenTableColumn column : columnList) {
                        if (column.getName().equals(e.getName())) {
                            b = true;
                        }
                    }
                    if (!b) {
                        e.setDelFlag("1");
                    }

                }

                genTable.setPkList(this.genDataBaseDictDao.findTablePK(genTable));

                GenUtils.initColumnField(genTable);
            }
        }

        return genTable;
    }

    /**
     * 保存（新增  修改）
     *
     * @param
     * @return void
     */
    @Transactional(readOnly = false)
    public void save(GenTable genTable) {
        //是否同步
        boolean isSync = true;
        GenTableColumn column;
        //如果genTable中的id为空  进入if
        if (StringUtils.isBlank(genTable.getId())) {
            isSync = false;
        } else {
            GenTable oldTable = get(genTable.getId());
            //如果前后字段数量不一致|| 表名不一致|| 表描述不一致   则进入if
            if ((oldTable.getColumnList().size() != genTable.getColumnList().size()) || (!oldTable.getName().equals(genTable.getName())) || (!oldTable.getComments().equals(genTable.getComments())))
                isSync = false;
            else {
                for (Iterator<GenTableColumn> localIterator = genTable.getColumnList().iterator(); localIterator.hasNext(); ) {
                    column = (GenTableColumn) localIterator.next();
                    if (StringUtils.isBlank(column.getId())) {
                        isSync = false;
                    } else {
                        GenTableColumn oldColumn = (GenTableColumn) this.genTableColumnDao.get(column.getId());
                        if ((oldColumn.getName().equals(column.getName())) &&
                                (oldColumn.getJdbcType().equals(column.getJdbcType())) &&
                                (oldColumn.getIsPk().equals(column.getIsPk())) &&
                                (oldColumn.getComments().equals(column.getComments())))
                            continue;
                        isSync = false;
                    }

                }

            }

        }

        if (!isSync) {
            genTable.setIsSync("0");
        }

        if (StringUtils.isBlank(genTable.getId())) {
            genTable.preInsert();
            this.genTableDao.insert(genTable);
        } else {
            genTable.preUpdate();
            this.genTableDao.update(genTable);
        }

        this.genTableColumnDao.deleteByGenTable(genTable);

        for (GenTableColumn col : genTable.getColumnList()) {
            col.setGenTable(genTable);

            col.setId(null);
            col.preInsert();
            this.genTableColumnDao.insert(col);
        }
    }

    /**
     * 保存同步信息
     *
     * @param
     * @return void
     */
    @Transactional(readOnly = false)
    public void syncSave(GenTable genTable) {
        genTable.setIsSync("1");
        this.genTableDao.update(genTable);
    }

    /**
     * 从数据库导入表单信息
     *
     * @param
     * @return void
     */
    @Transactional(readOnly = false)
    public void saveFromDB(GenTable genTable) {
        genTable.preInsert();
        this.genTableDao.insert(genTable);

        for (GenTableColumn column : genTable.getColumnList()) {
            column.setGenTable(genTable);
            column.setId(null);
            column.preInsert();
            this.genTableColumnDao.insert(column);
        }
    }

    /**
     * 删除 同时删除了表字段
     *
     * @param
     * @return void
     */
    @Transactional(readOnly = false)
    public void delete(GenTable genTable) {
        this.genTableDao.delete(genTable);
        this.genTableColumnDao.deleteByGenTable(genTable);
    }

    /**
     * 在数据库中执行一条sql
     *
     * @param paramString sql语句
     * @return int
     */
    @Transactional(readOnly = false)
    public void buildTable(String sql) {
        this.genTableDao.buildTable(sql);
    }
}
