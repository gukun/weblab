package com.njrz.modules.gen.entity;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.validator.constraints.Length;

import com.njrz.common.persistence.DataEntity;
import com.njrz.common.utils.StringUtils;

/**
 * 业务表字段 Entity
 *
 * @author qizhonghai
 * @date 2016-3-8 上午9:26:45
 * @since v1.0
 */
public class GenTableColumn extends DataEntity<GenTableColumn> {
    private static final long serialVersionUID = 1L;

    /**
     * 归属表
     */
    private GenTable genTable;

    /**
     * 表字段名称
     */
    private String name;

    /**
     * 表字段描述
     */
    private String comments;

    /**
     * 列的数据类型的字节长度 ，如 varchar(64)
     */
    private String jdbcType;

    /**
     * 表字段对应的JAVA类型， 如：com.njrz.modules.sys.entity.User
     */
    private String javaType;

    /**
     * 表字段对应的JAVA字段名
     */
    private String javaField;

    /**
     * 当前字段是否是主键 yes：是主键，no：不是主键
     */
    private String isPk;

    /**
     * 当前字段是否为空，yes：可以为空，no:不可为空
     */
    private String isNull;

    /**
     * 是否为插入字段
     */
    private String isInsert;

    /**
     * 是否编辑字段
     */
    private String isEdit;

    /**
     * 是否列表字段
     */
    private String isList;

    private String isForm;

    /**
     * 是否查询字段
     */
    private String isQuery;

    /**
     * 查询方式（等于、不等于、大于、小于、范围、左LIKE、右LIKE、左右LIKE）
     */
    private String queryType;

    /**
     * 字段生成方案（文本框、文本域、下拉框、复选框、单选框、字典选择、人员选择、部门选择、区域选择）
     */
    private String showType;

    /**
     * 字典类型
     */
    private String dictType;

    /**
     * 排序（升序）
     */
    private Integer sort;

    private String tableName;
    private String fieldLabels;
    private String fieldKeys;
    private String searchLabel;
    private String searchKey;

    public GenTableColumn() {
    }

    public GenTableColumn(String id) {
        super(id);
    }

    public GenTableColumn(GenTable genTable) {
        this.genTable = genTable;
    }

    public GenTable getGenTable() {
        return this.genTable;
    }

    public void setGenTable(GenTable genTable) {
        this.genTable = genTable;
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

    public String getJdbcType() {
        return StringUtils.lowerCase(this.jdbcType);
    }

    public void setJdbcType(String jdbcType) {
        this.jdbcType = jdbcType;
    }

    public String getJavaType() {
        return this.javaType;
    }

    public void setJavaType(String javaType) {
        this.javaType = javaType;
    }

    public String getJavaField() {
        return this.javaField;
    }

    public void setJavaField(String javaField) {
        this.javaField = javaField;
    }

    public String getIsPk() {
        return this.isPk;
    }

    public void setIsPk(String isPk) {
        this.isPk = isPk;
    }

    public String getIsNull() {
        return this.isNull;
    }

    public void setIsNull(String isNull) {
        this.isNull = isNull;
    }

    public String getIsInsert() {
        return this.isInsert;
    }

    public void setIsInsert(String isInsert) {
        this.isInsert = isInsert;
    }

    public String getIsEdit() {
        return this.isEdit;
    }

    public void setIsEdit(String isEdit) {
        this.isEdit = isEdit;
    }

    public String getIsList() {
        return this.isList;
    }

    public void setIsList(String isList) {
        this.isList = isList;
    }

    public String getIsQuery() {
        return this.isQuery;
    }

    public void setIsQuery(String isQuery) {
        this.isQuery = isQuery;
    }

    public String getQueryType() {
        return this.queryType;
    }

    public void setQueryType(String queryType) {
        this.queryType = queryType;
    }

    public String getShowType() {
        return this.showType;
    }

    public void setShowType(String showType) {
        this.showType = showType;
    }

    public String getDictType() {
        return this.dictType == null ? "" : this.dictType;
    }

    public void setDictType(String dictType) {
        this.dictType = dictType;
    }

    public Integer getSort() {
        return this.sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getIsForm() {
        return isForm;
    }

    public void setIsForm(String isForm) {
        this.isForm = isForm;
    }

    /**
     * 获得表字段民称和表字段描述
     *
     * @param
     * @return String
     */
    public String getNameAndComments() {
        return getName() + (this.comments == null ? "" : new StringBuilder("  :  ").append(this.comments).toString());
    }

    /**
     * 获得列的数据类型 的字节长度
     *
     * @param
     * @return String
     */
    public String getDataLength() {
        String[] ss = StringUtils.split(StringUtils.substringBetween(getJdbcType(), "(", ")"), ",");
        if ((ss != null) && (ss.length == 1)) {
            return ss[0];
        }
        return "0";
    }

    public String getSimpleJavaType() {
        if ("This".equals(getJavaType())) {
            return StringUtils.capitalize(this.genTable.getClassName());
        }
        return StringUtils.indexOf(getJavaType(), ".") != -1 ? StringUtils.substringAfterLast(getJavaType(), ".")
                : getJavaType();
    }

    public String getSimpleJavaField() {
        return StringUtils.substringBefore(getJavaField(), ".");
    }

    public String getJavaFieldId() {
        return StringUtils.substringBefore(getJavaField(), "|");
    }

    public String getJavaFieldName() {
        String[][] ss = getJavaFieldAttrs();
        return ss.length > 0 ? getSimpleJavaField() + "." + ss[0][0] : "";
    }

    public String[][] getJavaFieldAttrs() {
        String[] ss = StringUtils.split(StringUtils.substringAfter(getJavaField(), "|"), "|");
        String[][] sss = new String[ss.length][2];
        if (ss != null) {
            for (int i = 0; i < ss.length; i++) {
                sss[i][0] = ss[i];
                sss[i][1] = StringUtils.toUnderScoreCase(ss[i]);
            }
        }
        return sss;
    }

    public List<String> getAnnotationList() {
        List<String> list = new ArrayList<String>();

        if ("This".equals(getJavaType())) {
            list.add("com.fasterxml.jackson.annotation.JsonBackReference");
        }
        if ("java.util.Date".equals(getJavaType())) {
            list.add("com.fasterxml.jackson.annotation.JsonFormat(pattern = \"yyyy-MM-dd HH:mm:ss\")");
        }

        if ((!"1".equals(getIsNull())) && (!"String".equals(getJavaType()))) {
            list.add("javax.validation.constraints.NotNull(message=\"" + getComments() + "不能为空\")");
        } else if ((!"1".equals(getIsNull())) && ("String".equals(getJavaType())) && (!"0".equals(getDataLength()))) {
            list.add("org.hibernate.validator.constraints.Length(min=1, max=" + getDataLength() + ", message=\""
                    + getComments() + "长度必须介于 1 和 " + getDataLength() + " 之间\")");
        } else if (("String".equals(getJavaType())) && (!"0".equals(getDataLength()))) {
            list.add("org.hibernate.validator.constraints.Length(min=0, max=" + getDataLength() + ", message=\""
                    + getComments() + "长度必须介于 0 和 " + getDataLength() + " 之间\")");
        }
        return list;
    }

    public List<String> getSimpleAnnotationList() {
        List<String> list = new ArrayList<String>();
        for (String ann : getAnnotationList()) {
            list.add(StringUtils.substringAfterLast(ann, "."));
        }
        return list;
    }

    public Boolean getIsNotBaseField() {
        if ((!StringUtils.equals(getSimpleJavaField(), "id"))
                && (!StringUtils.equals(getSimpleJavaField(), "remarks"))
                && (!StringUtils.equals(getSimpleJavaField(), "createBy"))
                && (!StringUtils.equals(getSimpleJavaField(), "createDate"))
                && (!StringUtils.equals(getSimpleJavaField(), "updateBy"))
                && (!StringUtils.equals(getSimpleJavaField(), "updateDate"))
                && (!StringUtils.equals(getSimpleJavaField(), "delFlag")))
            return Boolean.valueOf(true);
        return Boolean.valueOf(false);
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getFieldLabels() {
        return fieldLabels;
    }

    public void setFieldLabels(String fieldLabels) {
        this.fieldLabels = fieldLabels;
    }

    public String getFieldKeys() {
        return fieldKeys;
    }

    public void setFieldKeys(String fieldKeys) {
        this.fieldKeys = fieldKeys;
    }

    public String getSearchLabel() {
        return searchLabel;
    }

    public void setSearchLabel(String searchLabel) {
        this.searchLabel = searchLabel;
    }

    public String getSearchKey() {
        return searchKey;
    }

    public void setSearchKey(String searchKey) {
        this.searchKey = searchKey;
    }

}
