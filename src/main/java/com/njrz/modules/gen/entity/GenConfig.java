package com.njrz.modules.gen.entity;

import java.io.Serializable;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import com.njrz.modules.sys.entity.Dict;


/**
 * 业务配置实体类
 * 〈主要是在字典表中得到代码生成分类，java类型，查询方式，字段生成方案〉
 *
 * @author qizhonghai
 * @date 2016-3-8 上午10:56:55
 * @since v1.0
 */
@XmlRootElement(name = "config")
public class GenConfig
        implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 代码生成 分类的集合
     */
    private List<GenCategory> categoryList;

    /**
     * java类型的集合
     */
    private List<Dict> javaTypeList;

    /**
     * 查询方式 的集合
     */
    private List<Dict> queryTypeList;

    /**
     * 字段生成方案的集合
     */
    private List<Dict> showTypeList;

    @XmlElementWrapper(name = "category")
    @XmlElement(name = "category")
    public List<GenCategory> getCategoryList() {
        return this.categoryList;
    }

    public void setCategoryList(List<GenCategory> categoryList) {
        this.categoryList = categoryList;
    }

    @XmlElementWrapper(name = "javaType")
    @XmlElement(name = "dict")
    public List<Dict> getJavaTypeList() {
        return this.javaTypeList;
    }

    public void setJavaTypeList(List<Dict> javaTypeList) {
        this.javaTypeList = javaTypeList;
    }

    @XmlElementWrapper(name = "queryType")
    @XmlElement(name = "dict")
    public List<Dict> getQueryTypeList() {
        return this.queryTypeList;
    }

    public void setQueryTypeList(List<Dict> queryTypeList) {
        this.queryTypeList = queryTypeList;
    }

    @XmlElementWrapper(name = "showType")
    @XmlElement(name = "dict")
    public List<Dict> getShowTypeList() {
        return this.showTypeList;
    }

    public void setShowTypeList(List<Dict> showTypeList) {
        this.showTypeList = showTypeList;
    }
}

