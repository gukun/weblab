package com.njrz.modules.gen.service;

import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.njrz.common.persistence.Page;
import com.njrz.common.service.BaseService;
import com.njrz.common.utils.StringUtils;
import com.njrz.modules.gen.dao.GenTemplateDao;
import com.njrz.modules.gen.entity.GenTemplate;

/**
 * 代码模板的service
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 *
 * @author qizhonghai
 * @date 2016-3-8 下午2:52:11
 * @since v1.0
 */
@Service
@Transactional(readOnly = true)
public class GenTemplateService extends BaseService {

    @Autowired
    private GenTemplateDao genTemplateDao;

    /**
     * 根据id查询
     *
     * @param id
     * @return GenTemplate
     */
    public GenTemplate get(String id) {
        return (GenTemplate) this.genTemplateDao.get(id);
    }

    /**
     * 分页查询
     *
     * @param page        分页对象
     * @param genTemplate 代码模板的实体对象
     * @return Page<GenTemplate>
     */
    public Page<GenTemplate> find(Page<GenTemplate> page, GenTemplate genTemplate) {
        genTemplate.setPage(page);
        page.setList(this.genTemplateDao.findList(genTemplate));
        return page;
    }

    /**
     * 保存（新增   修改）
     *
     * @param
     * @return void
     */
    @Transactional(readOnly = false)
    public void save(GenTemplate genTemplate) {
        if (genTemplate.getContent() != null) {
            //内容反转义
            genTemplate.setContent(StringEscapeUtils.unescapeHtml4(genTemplate.getContent()));
        }
        if (StringUtils.isBlank(genTemplate.getId())) {
            genTemplate.preInsert();
            this.genTemplateDao.insert(genTemplate);
        } else {
            genTemplate.preUpdate();
            this.genTemplateDao.update(genTemplate);
        }
    }

    /**
     * 删除
     *
     * @param
     * @return void
     */
    @Transactional(readOnly = false)
    public void delete(GenTemplate genTemplate) {
        this.genTemplateDao.delete(genTemplate);
    }
}

