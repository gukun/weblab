package com.njrz.modules.gen.entity;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.validator.constraints.Length;

import com.njrz.common.persistence.DataEntity;
import com.njrz.common.utils.StringUtils;

/**
 * 业务表实体类
 *
 * @author qizhonghai
 * @date 2016-3-7 下午5:28:32
 * @since v1.0
 */
public class GenTable extends DataEntity<GenTable> {
    private static final long serialVersionUID = 1L;

    /**
     * 表名称
     */
    private String name;

    /**
     * 表描述
     */
    private String comments;

    /**
     * 表类型，0--单表 ，1--主表，2--附表，3--树结构表
     */
    private String tableType;

    /**
     * 实体类名称
     */
    private String className;

    /**
     * 关联父表 表
     */
    private String parentTable;

    /**
     * 关联父表外键
     */
    private String parentTableFk;

    /**
     * 是否同步 yes ： 同步，no：不同步
     */
    private String isSync;

    /**
     * 当前业务表 的表字段集合
     */
    private List<GenTableColumn> columnList = new ArrayList<GenTableColumn>();

    /**
     *
     */
    private String nameLike;

    /**
     *
     */
    private List<String> pkList;

    /**
     * 当前业务表  父 类对象
     */
    private GenTable parent;

    /**
     * 当前业务表的  子业务表集合
     */
    private List<GenTable> childList = new ArrayList<GenTable>();


    public GenTable() {
    }

    public GenTable(String id) {
        super(id);
    }

    @Length(min = 1, max = 200)
    public String getName() {
        return StringUtils.lowerCase(this.name);
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getComments() {
        return this.comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getClassName() {
        return this.className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getParentTable() {
        return StringUtils.lowerCase(this.parentTable);
    }

    public void setParentTable(String parentTable) {
        this.parentTable = parentTable;
    }

    public String getParentTableFk() {
        return StringUtils.lowerCase(this.parentTableFk);
    }

    public void setParentTableFk(String parentTableFk) {
        this.parentTableFk = parentTableFk;
    }

    public List<String> getPkList() {
        return this.pkList;
    }

    public void setPkList(List<String> pkList) {
        this.pkList = pkList;
    }

    public String getNameLike() {
        return this.nameLike;
    }

    public void setNameLike(String nameLike) {
        this.nameLike = nameLike;
    }

    public GenTable getParent() {
        return this.parent;
    }

    public void setParent(GenTable parent) {
        this.parent = parent;
    }

    public List<GenTableColumn> getColumnList() {
        return this.columnList;
    }

    public void setColumnList(List<GenTableColumn> columnList) {
        this.columnList = columnList;
    }

    public List<GenTable> getChildList() {
        return this.childList;
    }

    public void setChildList(List<GenTable> childList) {
        this.childList = childList;
    }

    public String getNameAndComments() {
        return getName() + (this.comments == null ? "" : new StringBuilder("  :  ").append(this.comments).toString());
    }

    public List<String> getImportList() {
        List<String> importList = new ArrayList<String>();
        for (GenTableColumn column : getColumnList()) {
            if ((column.getIsNotBaseField().booleanValue()) || (("1".equals(column.getIsQuery())) && ("between".equals(column.getQueryType())) && (
                    ("createDate".equals(column.getSimpleJavaField())) || ("updateDate".equals(column.getSimpleJavaField()))))) {
                if ((StringUtils.indexOf(column.getJavaType(), ".") != -1) && (!importList.contains(column.getJavaType()))) {
                    importList.add(column.getJavaType());
                }
            }
            if (!column.getIsNotBaseField().booleanValue())
                continue;
            for (String ann : column.getAnnotationList()) {
                if (!importList.contains(StringUtils.substringBeforeLast(ann, "("))) {
                    importList.add(StringUtils.substringBeforeLast(ann, "("));
                }
            }

        }

        if ((getChildList() != null) && (getChildList().size() > 0)) {
            if (!importList.contains("java.util.List")) {
                importList.add("java.util.List");
            }
            if (!importList.contains("com.google.common.collect.Lists")) {
                importList.add("com.google.common.collect.Lists");
            }
        }
        return importList;
    }

    public List<String> getImportGridJavaList() {
        List<String> importList = new ArrayList<String>();
        for (GenTableColumn column : getColumnList()) {
            if ((column.getTableName() != null) && (!column.getTableName().equals(""))) {
                if ((StringUtils.indexOf(column.getJavaType(), ".") != -1) && (!importList.contains(column.getJavaType()))) {
                    importList.add(column.getJavaType());
                }
            }
        }
        return importList;
    }

    public List<String> getImportGridJavaDaoList() {
        boolean isNeedList = false;
        List<String> importList = new ArrayList<String>();
        for (GenTableColumn column : getColumnList()) {
            if ((column.getTableName() != null) && (!column.getTableName().equals(""))) {
                if ((StringUtils.indexOf(column.getJavaType(), ".") != -1) && (!importList.contains(column.getJavaType()))) {
                    importList.add(column.getJavaType());
                    isNeedList = true;
                }
            }
        }
        if ((isNeedList) &&
                (!importList.contains("java.util.List"))) {
            importList.add("java.util.List");
        }

        return importList;
    }

    public Boolean getParentExists() {
        if ((this.parent != null) && (StringUtils.isNotBlank(this.parentTable)) && (StringUtils.isNotBlank(this.parentTableFk)))
            return Boolean.valueOf(true);
        return Boolean.valueOf(false);
    }

    public Boolean getCreateDateExists() {
        for (GenTableColumn c : this.columnList) {
            if ("create_date".equals(c.getName())) {
                return Boolean.valueOf(true);
            }
        }
        return Boolean.valueOf(false);
    }

    public Boolean getUpdateDateExists() {
        for (GenTableColumn c : this.columnList) {
            if ("update_date".equals(c.getName())) {
                return Boolean.valueOf(true);
            }
        }
        return Boolean.valueOf(false);
    }

    public Boolean getDelFlagExists() {
        for (GenTableColumn c : this.columnList) {
            if ("del_flag".equals(c.getName())) {
                return Boolean.valueOf(true);
            }
        }
        return Boolean.valueOf(false);
    }

    public void setIsSync(String isSync) {
        this.isSync = isSync;
    }

    public String getIsSync() {
        return this.isSync;
    }

    public void setTableType(String tableType) {
        this.tableType = tableType;
    }

    public String getTableType() {
        return this.tableType;
    }
}

