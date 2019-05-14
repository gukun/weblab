/**
 * Copyright &copy; 2015-2020  All rights reserved.
 */
package com.njrz.modules.sys.web;

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
import com.njrz.modules.sys.entity.Lesson;
import com.njrz.modules.sys.service.LessonService;

/**
 * 课程Controller
 * @author gk
 * @version 2016-05-09
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/lesson")
public class LessonController extends BaseController {

	@Autowired
	private LessonService lessonService;
	
	@ModelAttribute
	public Lesson get(@RequestParam(required=false) String id) {
		Lesson entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = lessonService.get(id);
		}
		if (entity == null){
			entity = new Lesson();
		}
		return entity;
	}
	
	/**
	 * 课程列表页面
	 */
	@RequiresPermissions("sys:lesson:list")
	@RequestMapping(value = {"list", ""})
	public String list(Lesson lesson, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Lesson> page = lessonService.findPage(new Page<Lesson>(request, response), lesson); 
		model.addAttribute("page", page);
		return "modules/sys/lessonList";
	}

	/**
	 * 查看，增加，编辑课程表单页面
	 */
	@RequiresPermissions(value={"sys:lesson:view","sys:lesson:add","sys:lesson:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(Lesson lesson, Model model) {
		model.addAttribute("lesson", lesson);
		return "modules/sys/lessonForm";
	}

	/**
	 * 保存课程
	 */
	@RequiresPermissions(value={"sys:lesson:add","sys:lesson:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(Lesson lesson, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, lesson)){
			return form(lesson, model);
		}
		if(!lesson.getIsNewRecord()){//编辑表单保存
			Lesson t = lessonService.get(lesson.getId());//从数据库取出记录的值
			MyBeanUtils.copyBeanNotNull2Bean(lesson, t);//将编辑表单中的非NULL值覆盖数据库记录中的值
			lessonService.save(t);//保存
		}else{//新增表单保存
			lessonService.save(lesson);//保存
		}
		addMessage(redirectAttributes, "保存课程成功");
		return "redirect:"+Global.getAdminPath()+"/sys/lesson/?repage";
	}
	
	/**
	 * 删除课程
	 */
	@RequiresPermissions("sys:lesson:del")
	@RequestMapping(value = "delete")
	public String delete(Lesson lesson, RedirectAttributes redirectAttributes) {
		lessonService.delete(lesson);
		addMessage(redirectAttributes, "删除课程成功");
		return "redirect:"+Global.getAdminPath()+"/sys/lesson/?repage";
	}
	
	/**
	 * 批量删除课程
	 */
	@RequiresPermissions("sys:lesson:del")
	@RequestMapping(value = "deleteAll")
	public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
		String idArray[] =ids.split(",");
		for(String id : idArray){
			lessonService.delete(lessonService.get(id));
		}
		addMessage(redirectAttributes, "删除课程成功");
		return "redirect:"+Global.getAdminPath()+"/sys/lesson/?repage";
	}
	
	/**
	 * 导出excel文件
	 */
	@RequiresPermissions("sys:lesson:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public String exportFile(Lesson lesson, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "课程"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<Lesson> page = lessonService.findPage(new Page<Lesson>(request, response, -1), lesson);
    		new ExportExcel("课程", Lesson.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出课程记录失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/sys/lesson/?repage";
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("sys:lesson:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<Lesson> list = ei.getDataList(Lesson.class);
			for (Lesson lesson : list){
				lessonService.save(lesson);
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条课程记录");
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入课程失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/sys/lesson/?repage";
    }
	
	/**
	 * 下载导入课程数据模板
	 */
	@RequiresPermissions("sys:lesson:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "课程数据导入模板.xlsx";
    		List<Lesson> list = Lists.newArrayList(); 
    		new ExportExcel("课程数据", Lesson.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/sys/lesson/?repage";
    }
	

}