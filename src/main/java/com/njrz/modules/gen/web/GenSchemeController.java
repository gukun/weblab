package com.njrz.modules.gen.web;

import java.util.ArrayList;
import java.util.List;

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
import com.njrz.modules.gen.entity.GenScheme;
import com.njrz.modules.gen.service.GenSchemeService;
import com.njrz.modules.gen.service.GenTableService;
import com.njrz.modules.gen.util.GenUtils;
import com.njrz.modules.sys.entity.Menu;
import com.njrz.modules.sys.entity.User;
import com.njrz.modules.sys.service.SystemService;
import com.njrz.modules.sys.utils.UserUtils;

/**
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 *
 * @author qizhonghai
 * @date 2016-3-9 上午9:10:17
 * @since v1.0
 */
@Controller
@RequestMapping({"${adminPath}/gen/genScheme"})
public class GenSchemeController extends BaseController {

    @Autowired
    private GenSchemeService genSchemeService;

    @Autowired
    private GenTableService genTableService;

    @Autowired
    public SystemService systemService;

    /**
     * 根据id查询
     *
     * @param
     * @return GenScheme
     */
    @ModelAttribute
    public GenScheme get(@RequestParam(required = false) String id) {
        if (StringUtils.isNotBlank(id)) {
            return this.genSchemeService.get(id);
        }
        return new GenScheme();
    }

    /**
     * 分页查询
     *
     * @param
     * @return String
     */
    @RequiresPermissions({"gen:genScheme:view"})
    @RequestMapping({"list", ""})
    public String list(GenScheme genScheme, HttpServletRequest request, HttpServletResponse response, Model model) {
        User user = UserUtils.getUser();
        if (!user.isAdmin()) {
            genScheme.setCreateBy(user);
        }
        Page<GenScheme> page = this.genSchemeService.find(new Page<GenScheme>(request, response), genScheme);
        model.addAttribute("page", page);

        return "modules/gen/genSchemeList";
    }

    /**
     * @param
     * @return String
     */
    @RequiresPermissions({"gen:genScheme:view"})
    @RequestMapping({"form"})
    public String form(GenScheme genScheme, Model model) {
        if (StringUtils.isBlank(genScheme.getPackageName())) {
            genScheme.setPackageName("com.njrz.modules");
        }
        model.addAttribute("genScheme", genScheme);
        model.addAttribute("config", GenUtils.getConfig());
        model.addAttribute("tableList", this.genTableService.findAll());
        return "modules/gen/genSchemeForm";
    }

    /**
     * @param
     * @return String
     */
    @RequiresPermissions({"gen:genScheme:edit"})
    @RequestMapping({"save"})
    public String save(GenScheme genScheme, Model model, RedirectAttributes redirectAttributes) {
        if (!beanValidator(model, genScheme, new Class[0])) {
            return form(genScheme, model);
        }

        String result = this.genSchemeService.save(genScheme);
        addMessage(redirectAttributes, new String[]{"操作生成方案'" + genScheme.getName() + "'成功<br/>" + result});
        return "redirect:" + this.adminPath + "/gen/genScheme/?repage";
    }


    @RequiresPermissions({"gen:genScheme:edit"})
    @RequestMapping({"delete"})
    public String delete(GenScheme genScheme, RedirectAttributes redirectAttributes) {
        this.genSchemeService.delete(genScheme);
        addMessage(redirectAttributes, new String[]{"删除生成方案成功"});
        return "redirect:" + this.adminPath + "/gen/genScheme/?repage";
    }

    @RequestMapping({"menuForm"})
    private String menuForm(String gen_table_id, Menu menu, Model model) {
        if ((menu.getParent() == null) || (menu.getParent().getId() == null)) {
            menu.setParent(new Menu(Menu.getRootId()));
        }
        menu.setParent(systemService.getMenu(menu.getParent().getId()));
        if (StringUtils.isBlank(menu.getId())) {
            List<Menu> list = new ArrayList<Menu>();
            List<Menu> sourcelist = systemService.findAllMenu();
            Menu.sortList(list, sourcelist, menu.getParentId(), false);
            if (list.size() > 0) {
                menu.setSort(Integer.valueOf(((Menu) list.get(list.size() - 1)).getSort().intValue() + 30));
            }
        }
        GenScheme genScheme;
        if ((genScheme = this.genSchemeService.findUniqueByProperty("gen_table_id", gen_table_id)) != null) {
            menu.setName(genScheme.getFunctionName());
        }
        model.addAttribute("menu", menu);
        model.addAttribute("gen_table_id", gen_table_id);
        return "modules/gen/genMenuForm";
    }

    @RequestMapping({"createMenu"})
    private String createMenu(String gen_table_id, Menu menu, RedirectAttributes redirectAttributes) {
        GenScheme genScheme;
        if ((genScheme = this.genSchemeService.findUniqueByProperty("gen_table_id", gen_table_id)) == null) {
            addMessage(redirectAttributes, new String[]{"创建菜单失败，请先生成代码！"});
            return "redirect:" + this.adminPath + "/gen/genTable/?repage";
        }
        genScheme.setGenTable(this.genTableService.get(gen_table_id));
        this.genSchemeService.createMenu(genScheme, menu);
        addMessage(redirectAttributes, new String[]{"创建菜单'" + genScheme.getFunctionName() + "'成功<br/>"});
        return "redirect:" + this.adminPath + "/gen/genTable/?repage";
    }
}

