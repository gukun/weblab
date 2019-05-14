package com.njrz.modules.gen.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.njrz.common.persistence.Page;
import com.njrz.common.utils.StringUtils;
import com.njrz.common.web.BaseController;
import com.njrz.modules.gen.entity.GenTemplate;
import com.njrz.modules.gen.service.GenTemplateService;
import com.njrz.modules.sys.entity.User;
import com.njrz.modules.sys.utils.UserUtils;

/**
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 *
 * @author qizhonghai
 * @date 2016-3-8 下午4:58:34
 * @since v1.0
 */
@Controller
@RequestMapping({"${adminPath}/gen/genTemplate"})
public class GenTemplateController extends BaseController {

    @Autowired
    private GenTemplateService genTemplateService;

    /**
     * 根据id查询
     *
     * @param
     * @return GenTemplate
     */
    @ModelAttribute
    public GenTemplate get(@RequestParam(required = false) String id) {
        if (StringUtils.isNotBlank(id)) {
            return this.genTemplateService.get(id);
        }
        return new GenTemplate();
    }

    /**
     * 分页查询代码模板
     *
     * @param
     * @return String
     */
    @RequiresPermissions({"gen:genTemplate:view"})
    @RequestMapping({"list", ""})
    public String list(GenTemplate genTemplate, HttpServletRequest request, HttpServletResponse response, Model model) {
        User user = UserUtils.getUser();
        if (!user.isAdmin()) {
            genTemplate.setCreateBy(user);
        }
        Page<GenTemplate> page = this.genTemplateService.find(new Page<GenTemplate>(request, response), genTemplate);
        model.addAttribute("page", page);
        return "modules/gen/genTemplateList";
    }

    /**
     * @param
     * @return String
     */
    @RequiresPermissions({"gen:genTemplate:view"})
    @RequestMapping({"form"})
    public String form(GenTemplate genTemplate, Model model) {
        model.addAttribute("genTemplate", genTemplate);
        return "modules/gen/genTemplateForm";
    }

    /**
     * 保存
     *
     * @param
     * @return String
     */
    @RequiresPermissions({"gen:genTemplate:edit"})
    @RequestMapping({"save"})
    public String save(GenTemplate genTemplate, Model model, RedirectAttributes redirectAttributes) {
        if (!beanValidator(model, genTemplate, new Class[0])) {
            return form(genTemplate, model);
        }
        this.genTemplateService.save(genTemplate);
        addMessage(redirectAttributes, new String[]{"保存代码模板'" + genTemplate.getName() + "'成功"});
        return "redirect:" + this.adminPath + "/gen/genTemplate/?repage";
    }

    /**
     * @param
     * @return String
     */
    @RequiresPermissions({"gen:genTemplate:edit"})
    @RequestMapping({"delete"})
    public String delete(GenTemplate genTemplate, RedirectAttributes redirectAttributes) {
        this.genTemplateService.delete(genTemplate);
        addMessage(redirectAttributes, new String[]{"删除代码模板成功"});
        return "redirect:" + this.adminPath + "/gen/genTemplate/?repage";
    }
}

