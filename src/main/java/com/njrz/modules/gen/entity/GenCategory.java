package com.njrz.modules.gen.entity;

import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import com.njrz.modules.sys.entity.Dict;

/**
 * 代码生成 分类
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 *
 * @author qizhonghai
 * @date 2016-3-8 上午10:10:24
 * @since v1.0
 */
@XmlRootElement(name = "category")
public class GenCategory extends Dict {
    private static final long serialVersionUID = 1L;

    /**
     * 模板value集合
     */
    private List<String> template;

    /**
     * 子模板value集合
     */
    private List<String> childTableTemplate;


    public static String CATEGORY_REF = "category-ref:";

    @XmlElement(name = "template")
    public List<String> getTemplate() {
        return this.template;
    }

    public void setTemplate(List<String> template) {
        this.template = template;
    }

    @XmlElementWrapper(name = "childTable")
    @XmlElement(name = "template")
    public List<String> getChildTableTemplate() {
        return this.childTableTemplate;
    }

    public void setChildTableTemplate(List<String> childTableTemplate) {
        this.childTableTemplate = childTableTemplate;
    }
}

