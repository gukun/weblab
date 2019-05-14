package com.njrz.modules.gen.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.njrz.common.persistence.Page;
import com.njrz.common.service.BaseService;
import com.njrz.common.utils.StringUtils;
import com.njrz.modules.gen.dao.GenDataBaseDictDao;
import com.njrz.modules.gen.dao.GenTableColumnDao;
import com.njrz.modules.gen.dao.GenTableDao;
import com.njrz.modules.gen.entity.GenScheme;
import com.njrz.modules.gen.entity.GenTable;
import com.njrz.modules.gen.entity.GenTableColumn;
import com.njrz.modules.gen.template.FreemarkerHelper;
import com.njrz.modules.gen.util.GenUtils;

/**
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 *
 * @author qizhonghai
 * @date 2016-3-8 上午10:38:41
 * @since v1.0
 */
@Service
@Transactional(readOnly = true)
public class CgAutoListService extends BaseService {

    @Autowired
    private GenTableDao genTableDao;

    @Autowired
    private GenTableColumnDao genTableColumnDao;

    @Autowired
    private GenDataBaseDictDao genDataBaseDictDao;

    /**
     * 根据业务表id查询
     *
     * @param id GenTable的id
     * @return GenTable 包含了业务表字段的业务表信息
     */
    public GenTable get(String id) {
        GenTable genTable = (GenTable) this.genTableDao.get(id);
        GenTableColumn genTableColumn = new GenTableColumn();
        genTableColumn.setGenTable(new GenTable(genTable.getId()));
        genTable.setColumnList(this.genTableColumnDao.findList(genTableColumn));
        return genTable;
    }

    /**
     * 分页查询业务表
     *
     * @param page     分页对象
     * @param genTable 业务表对象参数
     * @return Page<GenTable>
     */
    public Page<GenTable> find(Page<GenTable> page, GenTable genTable) {
        genTable.setPage(page);
        page.setList(this.genTableDao.findList(genTable));
        return page;
    }

    /**
     * 查询所有业务表信息
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
     * @param genTable 业务表对象参数
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
     * 生成html字符串
     *
     * @param genScheme 生成方案对象参数 (必须包含业务表的id)
     * @return String
     */
    public String generateCode(GenScheme genScheme) {
        GenTable genTable = (GenTable) this.genTableDao.get(genScheme.getGenTable().getId());
        genTable.setColumnList(this.genTableColumnDao.findList(new GenTableColumn(new GenTable(genTable.getId()))));
        genScheme.setGenTable(genTable);
        Map<String, Object> model = GenUtils.getDataModel(genScheme);
        FreemarkerHelper viewEngine = new FreemarkerHelper();
        String html = viewEngine.parseTemplate("/com/cstor/modules/gen/template/viewList.ftl", model);
        return html;
    }

    /**
     * 生成 sql字符串
     *
     * @param genScheme 生成方案对象参数 (必须包含业务表的id)
     * @return String
     */
    public String generateListCode(GenScheme genScheme) {
        GenTable genTable = (GenTable) this.genTableDao.get(genScheme.getGenTable().getId());
        genTable.setColumnList(this.genTableColumnDao.findList(new GenTableColumn(new GenTable(genTable.getId()))));
        genScheme.setGenTable(genTable);
        Map<String, Object> model = GenUtils.getDataModel(genScheme);
        FreemarkerHelper viewEngine = new FreemarkerHelper();
        String html = viewEngine.parseTemplate("/com/cstor/modules/gen/template/findList.ftl", model);
        return html;
    }
}

