package com.njrz.modules.gen.web;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.njrz.common.persistence.Page;
import com.njrz.common.utils.StringUtils;
import com.njrz.common.web.BaseController;
import com.njrz.modules.gen.entity.GenScheme;
import com.njrz.modules.gen.entity.GenTable;
import com.njrz.modules.gen.entity.GenTableColumn;
import com.njrz.modules.gen.service.GenSchemeService;
import com.njrz.modules.gen.service.GenTableService;
import com.njrz.modules.gen.util.GenUtils;
import com.njrz.modules.sys.entity.User;
import com.njrz.modules.sys.utils.UserUtils;

/**
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 *
 * @author qizhonghai
 * @date 2016-3-8 下午3:28:45
 * @since v1.0
 */
@Controller
@RequestMapping({"${adminPath}/gen/genTable"})
public class GenTableController extends BaseController {

    @Autowired
    private GenTableService genTableService;

    @Autowired
    private GenSchemeService genSchemeService;

    /**
     * 根据id查询,如果无id则返回原对象
     *
     * @param genTable
     * @return GenTable
     */
    public GenTable get(GenTable genTable) {
        if (StringUtils.isNotBlank(genTable.getId())) {
            return this.genTableService.get(genTable.getId());
        }
        return genTable;
    }

    /**
     * 根据业务表信息分页查询
     *
     * @param genTable
     * @return String
     */
    @RequiresPermissions({"gen:genTable:list"})
    @RequestMapping({"list", ""})
    public String list(GenTable genTable, HttpServletRequest request, HttpServletResponse response, Model model) {
        genTable = get(genTable);
        User user = UserUtils.getUser();
        if (!user.isAdmin()) {
            genTable.setCreateBy(user);
        }
        Page<GenTable> page = this.genTableService.find(new Page<GenTable>(request, response), genTable);
        model.addAttribute("page", page);
        return "modules/gen/genTableList";
    }

    /**
     * 根据业务表信息(业务表信息、配置信息、所有的业务表集合)
     *
     * @param
     * @return String
     */
    @RequiresPermissions(value = {"gen:genTable:view", "gen:genTable:add", "gen:genTable:edit"}, logical = Logical.OR)
    @RequestMapping({"form"})
    public String form(GenTable genTable, Model model) {
        genTable = get(genTable);
        model.addAttribute("genTable", genTable);
        model.addAttribute("config", GenUtils.getConfig());
        model.addAttribute("tableList", this.genTableService.findAll());
        return "modules/gen/genTableForm";
    }

    /**
     * 保存
     *
     * @param
     * @return String
     */
    @RequiresPermissions(value = {"gen:genTable:add", "gen:genTable:edit"}, logical = Logical.OR)
    @RequestMapping({"save"})
    public String save(GenTable genTable, Model model, RedirectAttributes redirectAttributes) {
        if (!beanValidator(model, genTable, new Class[0])) {
            return form(genTable, model);
        }
        if ((StringUtils.isBlank(genTable.getId())) && (!this.genTableService.checkTableName(genTable.getName()))) {
            addMessage(redirectAttributes, new String[]{"添加失败！" + genTable.getName() + " 记录已存在！"});
            return "redirect:" + this.adminPath + "/gen/genTable/?repage";
        }

        if ((StringUtils.isBlank(genTable.getId())) && (!this.genTableService.checkTableNameFromDB(genTable.getName()))) {
            addMessage(redirectAttributes, new String[]{"添加失败！" + genTable.getName() + "表已经在数据库中存在,请从数据库导入表单！"});
            return "redirect:" + this.adminPath + "/gen/genTable/?repage";
        }

        this.genTableService.save(genTable);
        addMessage(redirectAttributes, new String[]{"保存业务表'" + genTable.getName() + "'成功"});
        return "redirect:" + this.adminPath + "/gen/genTable/?repage";
    }

    /**
     * 从数据库导入表单信息
     * 从数据库导入的表都是单表
     *
     * @param
     * @return String
     */
    @RequiresPermissions({"gen:genTable:importDb"})
    @RequestMapping({"importTableFromDB"})
    public String importTableFromDB(GenTable genTable, Model model, RedirectAttributes redirectAttributes) {
        genTable = get(genTable);
        if (!StringUtils.isBlank(genTable.getName())) {
            if (!this.genTableService.checkTableName(genTable.getName())) {
                addMessage(redirectAttributes, new String[]{"下一步失败！" + genTable.getName() +
                        " 表已经添加！"});
                return "redirect:" + this.adminPath + "/gen/genTable/?repage";
            }
            genTable = this.genTableService.getTableFormDb(genTable);
            //单表
            genTable.setTableType("0");
            this.genTableService.saveFromDB(genTable);
            addMessage(redirectAttributes, new String[]{"数据库导入表单'" + genTable.getName() +
                    "'成功"});
            return "redirect:" + this.adminPath + "/gen/genTable/?repage";
        }
        //获取数据库表的集合
        List<GenTable> tableList = this.genTableService.findTableListFormDb(new GenTable());
        model.addAttribute("tableList", tableList);
        model.addAttribute("config", GenUtils.getConfig());
        return "modules/gen/importTableFromDB";
    }

    /**
     * 移除业务表记录和方案记录
     *
     * @param
     * @return String
     */
    @RequiresPermissions({"gen:genTable:del"})
    @RequestMapping({"delete"})
    public String delete(GenTable genTable, RedirectAttributes redirectAttributes) {
        genTable = get(genTable);
        this.genTableService.delete(genTable);
        //方案 记录的删除
        this.genSchemeService.delete(this.genSchemeService.findUniqueByProperty("gen_table_id", genTable.getId()));
        addMessage(redirectAttributes, new String[]{"移除业务表记录成功"});
        return "redirect:" + this.adminPath + "/gen/genTable/?repage";
    }

    /**
     * 删除业务表记录和方案记录
     * 且删除数据库表
     *
     * @param
     * @return String
     */
    @RequiresPermissions({"gen:genTable:del"})
    @RequestMapping({"deleteDb"})
    public String deleteDb(GenTable genTable, RedirectAttributes redirectAttributes) {
        genTable = get(genTable);
        this.genTableService.delete(genTable);
        this.genSchemeService.delete(this.genSchemeService.findUniqueByProperty("gen_table_id", genTable.getId()));
        StringBuffer sql = new StringBuffer();
        sql.append("drop table if exists " + genTable.getName() + " ;");
        this.genTableService.buildTable(sql.toString());
        addMessage(redirectAttributes, new String[]{"删除业务表记录和数据库表成功"});
        return "redirect:" + this.adminPath + "/gen/genTable/?repage";
    }

    /**
     * 删除多个业务表记录
     *
     * @param
     * @return String
     */
    @RequiresPermissions({"gen:genTable:del"})
    @RequestMapping({"deleteAll"})
    public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
        String[] idArray = ids.split(",");
        for (String id : idArray) {
            this.genTableService.delete(this.genTableService.get(id));
        }
        addMessage(redirectAttributes, new String[]{"删除业务表成功"});
        return "redirect:" + this.adminPath + "/gen/genTable/?repage";
    }

    /**
     * 强制同步数据库
     * 数据库表的原有的数据将消失
     *
     * @param
     * @return String
     */
    @RequiresPermissions({"gen:genTable:synchDb"})
    @RequestMapping({"synchDb"})
    public String synchDb(GenTable genTable, RedirectAttributes redirectAttributes) {
        genTable = get(genTable);
        StringBuffer sql = new StringBuffer();
        List<GenTableColumn> getTableColumnList = genTable.getColumnList();
        sql.append("drop table if exists " + genTable.getName() + " ;");
        //删除数据库对应的表
        this.genTableService.buildTable(sql.toString());
        sql = new StringBuffer();
        sql.append("create table " + genTable.getName() + " (");
        String pk = "";
        for (GenTableColumn column : getTableColumnList) {
            if (column.getIsPk().equals("1")) {
                sql.append("  " + column.getName() + " " + column.getJdbcType() + " comment '" + column.getComments() + "',");
                pk = pk + column.getName() + ",";
            } else {
                sql.append("  " + column.getName() + " " + column.getJdbcType() + " comment '" + column.getComments() + "',");
            }
        }

        sql.append("primary key (" + pk.substring(0, pk.length() - 1) + ") ");
        sql.append(") comment '" + genTable.getComments() + "'");
        //数据库新增表
        this.genTableService.buildTable(sql.toString());
        //业务表中的同步信息 修正为同步状态
        this.genTableService.syncSave(genTable);
        addMessage(redirectAttributes, new String[]{"强制同步数据库表成功"});
        return "redirect:" + this.adminPath + "/gen/genTable/?repage";
    }

    /**
     * 转到生成代码窗口
     *
     * @param
     * @return String
     */
    @RequiresPermissions({"gen:genTable:genCode"})
    @RequestMapping({"genCodeForm"})
    public String genCodeForm(GenScheme genScheme, Model model, RedirectAttributes redirectAttributes) {
        //如果生成包路径为空  则给予默认包路径
        if (StringUtils.isBlank(genScheme.getPackageName())) {
            genScheme.setPackageName("com.njrz.modules");
        }
        //得到数据库存的方案
        GenScheme oldGenScheme = this.genSchemeService.findUniqueByProperty("gen_table_id", genScheme.getGenTable().getId());
        if (oldGenScheme != null) {
            genScheme = oldGenScheme;
        }
        model.addAttribute("genScheme", genScheme);
        model.addAttribute("config", GenUtils.getConfig());
        model.addAttribute("tableList", this.genTableService.findAll());
        return "modules/gen/genCodeForm";
    }

    /**
     * 保存生成方案并生成代码
     *
     * @param
     * @return String
     */
    @RequestMapping({"genCode"})
    public String genCode(GenScheme genScheme, Model model, RedirectAttributes redirectAttributes) {
        String result = this.genSchemeService.save(genScheme);
        addMessage(redirectAttributes, new String[]{genScheme.getGenTable().getName() + "代码生成成功<br/>" + result});
        return "redirect:" + this.adminPath + "/gen/genTable/?repage";
    }
}
