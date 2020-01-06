/**
 * Copyright &copy; 2015-2020  All rights reserved.
 */
package com.njrz.modules.lab.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.collect.Lists;
import com.njrz.common.utils.DateUtils;
import com.njrz.common.utils.MyBeanUtils;
import com.njrz.common.config.Global;
import com.njrz.common.persistence.Page;
import com.njrz.common.web.BaseController;
import com.njrz.common.utils.StringUtils;
import com.njrz.common.utils.excel.ExportExcel;
import com.njrz.common.utils.excel.ImportExcel;
import com.njrz.modules.lab.entity.Lab;
import com.njrz.modules.lab.service.LabService;

/**
 * 实验室管理Controller
 * @author gk
 * @version 2019-05-09
 */
@Controller
@RequestMapping(value = "${adminPath}/lab/lab")
public class LabController extends BaseController {

	@Autowired
	private LabService labService;
	
	@ModelAttribute
	public Lab get(@RequestParam(required=false) String id) {
		Lab entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = labService.get(id);
		}
		if (entity == null){
			entity = new Lab();
		}
		return entity;
	}
	
	/**
	 * 实验室列表页面
	 */
	@RequiresPermissions("lab:lab:list")
	@RequestMapping(value = {"list", ""})
	public String list(Lab lab, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Lab> page = labService.findPage(new Page<Lab>(request, response), lab); 
		model.addAttribute("page", page);
		return "modules/lab/labList";
	}

	/**
	 * 查看，增加，编辑实验室表单页面
	 */
	@RequiresPermissions(value={"lab:lab:view","lab:lab:add","lab:lab:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(Lab lab, Model model) {
		model.addAttribute("lab", lab);
		return "modules/lab/labForm";
	}

	/**
	 * 保存实验室
	 */
	@RequiresPermissions(value={"lab:lab:add","lab:lab:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(Lab lab, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, lab)){
			return form(lab, model);
		}
		if(!lab.getIsNewRecord()){//编辑表单保存
			Lab t = labService.get(lab.getId());//从数据库取出记录的值
			MyBeanUtils.copyBeanNotNull2Bean(lab, t);//将编辑表单中的非NULL值覆盖数据库记录中的值
			labService.save(t);//保存
		}else{//新增表单保存
			labService.save(lab);//保存
		}
		addMessage(redirectAttributes, "保存实验室成功");
		return "redirect:"+Global.getAdminPath()+"/lab/lab/?repage";
	}
	
	/**
	 * 删除实验室
	 */
	@RequiresPermissions("lab:lab:del")
	@RequestMapping(value = "delete")
	public String delete(Lab lab, RedirectAttributes redirectAttributes) {
		labService.delete(lab);
		addMessage(redirectAttributes, "删除实验室成功");
		return "redirect:"+Global.getAdminPath()+"/lab/lab/?repage";
	}
	
	/**
	 * 批量删除实验室
	 */
	@RequiresPermissions("lab:lab:del")
	@RequestMapping(value = "deleteAll")
	public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
		String idArray[] =ids.split(",");
		for(String id : idArray){
			labService.delete(labService.get(id));
		}
		addMessage(redirectAttributes, "删除实验室成功");
		return "redirect:"+Global.getAdminPath()+"/lab/lab/?repage";
	}
	
	/**
	 * 导出excel文件
	 */
	@RequiresPermissions("lab:lab:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public String exportFile(Lab lab, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "实验室"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<Lab> page = labService.findPage(new Page<Lab>(request, response, -1), lab);
    		new ExportExcel("实验室", Lab.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出实验室记录失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/lab/lab/?repage";
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("lab:lab:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<Lab> list = ei.getDataList(Lab.class);
			for (Lab lab : list){
				labService.save(lab);
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条实验室记录");
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入实验室失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/lab/lab/?repage";
    }
	
	/**
	 * 下载导入实验室数据模板
	 */
	@RequiresPermissions("lab:lab:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "实验室数据导入模板.xlsx";
    		List<Lab> list = Lists.newArrayList(); 
    		new ExportExcel("实验室数据", Lab.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/lab/lab/?repage";
    }
	

}