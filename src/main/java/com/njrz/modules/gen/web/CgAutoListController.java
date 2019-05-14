package com.njrz.modules.gen.web;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.njrz.common.utils.StringUtils;
import com.njrz.common.web.BaseController;
import com.njrz.modules.gen.entity.GenScheme;
import com.njrz.modules.gen.service.CgAutoListService;
import com.njrz.modules.gen.service.GenSchemeService;

/**
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 *
 * @author qizhonghai
 * @date 2016-3-8 下午3:03:42
 * @since v1.0
 */
@Controller
@RequestMapping({"${adminPath}/gen/cgAutoList"})
public class CgAutoListController extends BaseController {
    private static Logger log = Logger.getLogger(CgAutoListController.class);

    @Autowired
    private GenSchemeService genSchemeService;


    @Autowired
    private CgAutoListService cgAutoListService;

    /**
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
     * 根据生成方案信息  动态生成列表
     *
     * @param
     * @return void
     */
    @RequestMapping({"list"})
    public void list(GenScheme genScheme, HttpServletRequest request, HttpServletResponse response) {
        long start = System.currentTimeMillis();
        //由数据以及viewList.ftl文件  生成html字符串
        String html = this.cgAutoListService.generateCode(genScheme);
        try {
            response.setContentType("text/html");
            response.setHeader("Cache-Control", "no-store");
            PrintWriter writer = response.getWriter();
            writer.println(html);
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        long end = System.currentTimeMillis();
        log.debug("动态列表生成耗时：" + (end - start) + " ms");
    }

    @RequestMapping({"test", ""})
    public ModelAndView list(GenScheme genScheme, HttpServletRequest request, HttpServletResponse response, Model model) {
        return new ModelAndView("com/cstor/modules/gen/template/viewList");
    }
}
