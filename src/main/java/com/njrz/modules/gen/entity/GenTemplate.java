package com.njrz.modules.gen.entity;

import com.njrz.common.persistence.DataEntity;
import com.njrz.common.utils.StringUtils;
import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.hibernate.validator.constraints.Length;

/**
 * 代码模板的实体类
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 *
 * @author qizhonghai
 * @date 2016-3-7 下午5:40:38
 * @since v1.0
 */
@XmlRootElement(name = "template")
public class GenTemplate extends DataEntity<GenTemplate> {
    private static final long serialVersionUID = 1L;

    /**
     * 名称
     */
    private String name;

    /**
     * 分类
     */
    private String category;

    /**
     * 生成文件路径
     */
    private String filePath;

    /**
     * 生成文件名
     */
    private String fileName;

    /**
     * 内容
     */
    private String content;

    /**
     * 无参数构造方法
     */
    public GenTemplate() {
    }

    /**
     * 有参数构造方法
     *
     * @param id
     */
    public GenTemplate(String id) {
        super(id);
    }

    @Length(min = 1, max = 200)
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFileName() {
        return this.fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFilePath() {
        return this.filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCategory() {
        return this.category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @XmlTransient
    public List<String> getCategoryList() {
        if (this.category == null) {
            return new ArrayList<String>();
        }
        return (List<String>) Lists.newArrayList(StringUtils.split(this.category, ","));
    }

    public void setCategoryList(List<String> categoryList) {
        if (categoryList == null)
            this.category = "";
        else
            this.category = ("," + StringUtils.join(categoryList, ",") + ",");
    }
}
