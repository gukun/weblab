package com.njrz.modules.gen.entity;

import org.hibernate.validator.constraints.Length;

import com.njrz.common.persistence.DataEntity;

/**
 * 生成方案的Entity
 *
 * @author qizhonghai
 * @date 2016-3-8 上午10:12:11
 * @since v1.0
 */
public class GenScheme extends DataEntity<GenScheme> {
    private static final long serialVersionUID = 1L;

    /**
     * 名称
     */
    private String name;

    /**
     * 分类  crud--增删改查， crud_many--增删改查（包含从表），tree--树结构，dao--仅持久层
     */
    private String category;

    /**
     * 生成包路径 ,如：com.njrz.modules
     */
    private String packageName;

    /**
     * 生成模块名 ,如：test
     */
    private String moduleName;

    /**
     * 生成子模块名,如：one
     */
    private String subModuleName;

    /**
     * 生成功能名,如：员工请假
     */
    private String functionName;

    /**
     * 生成功能名简写,如：请假
     */
    private String functionNameSimple;

    /**
     * 生成功能作者
     */
    private String functionAuthor;

    /**
     * 生成的表对象
     */
    private GenTable genTable;

    /**
     *
     */
    private String flag;

    /**
     * 是否代替原有文件
     */
    private Boolean replaceFile;

    public GenScheme() {
    }

    public GenScheme(String id) {
        super(id);
    }

    @Length(min = 1, max = 200)
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPackageName() {
        return this.packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getModuleName() {
        return this.moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public String getSubModuleName() {
        return this.subModuleName;
    }

    public void setSubModuleName(String subModuleName) {
        this.subModuleName = subModuleName;
    }

    public String getCategory() {
        return this.category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getFunctionName() {
        return this.functionName;
    }

    public void setFunctionName(String functionName) {
        this.functionName = functionName;
    }

    public String getFunctionNameSimple() {
        return this.functionNameSimple;
    }

    public void setFunctionNameSimple(String functionNameSimple) {
        this.functionNameSimple = functionNameSimple;
    }

    public String getFunctionAuthor() {
        return this.functionAuthor;
    }

    public void setFunctionAuthor(String functionAuthor) {
        this.functionAuthor = functionAuthor;
    }

    public GenTable getGenTable() {
        return this.genTable;
    }

    public void setGenTable(GenTable genTable) {
        this.genTable = genTable;
    }

    public String getFlag() {
        return this.flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public Boolean getReplaceFile() {
        return this.replaceFile;
    }

    public void setReplaceFile(Boolean replaceFile) {
        this.replaceFile = replaceFile;
    }
}
