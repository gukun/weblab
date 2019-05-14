package com.njrz.modules.gen.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.njrz.common.persistence.Page;
import com.njrz.common.service.BaseService;
import com.njrz.common.utils.StringUtils;
import com.njrz.modules.gen.dao.GenSchemeDao;
import com.njrz.modules.gen.dao.GenTableColumnDao;
import com.njrz.modules.gen.dao.GenTableDao;
import com.njrz.modules.gen.entity.GenConfig;
import com.njrz.modules.gen.entity.GenScheme;
import com.njrz.modules.gen.entity.GenTable;
import com.njrz.modules.gen.entity.GenTableColumn;
import com.njrz.modules.gen.entity.GenTemplate;
import com.njrz.modules.gen.util.GenUtils;
import com.njrz.modules.sys.entity.Menu;
import com.njrz.modules.sys.service.SystemService;

/**
 * 生成方案  的service
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 *
 * @author qizhonghai
 * @date 2016-3-8 上午11:58:03
 * @since v1.0
 */
@Service
@Transactional(readOnly = true)
public class GenSchemeService extends BaseService {

    @Autowired
    private GenSchemeDao genSchemeDao;

    @Autowired
    private GenTableDao genTableDao;

    @Autowired
    private GenTableColumnDao genTableColumnDao;

    @Autowired
    private SystemService systemService;

    /**
     * 根据id查询
     *
     * @param
     * @return GenScheme
     */
    public GenScheme get(String id) {
        return (GenScheme) this.genSchemeDao.get(id);
    }


    @Transactional(readOnly = false)
    public void createMenu(GenScheme genScheme, Menu topMenu) {
        String permissionPrefix = StringUtils.lowerCase(genScheme.getModuleName())
                + (StringUtils.isNotBlank(genScheme.getSubModuleName()) ? ":" + StringUtils.lowerCase(genScheme.getSubModuleName()) : "")
                + ":" + StringUtils.uncapitalize(genScheme.getGenTable().getClassName());
        String url = "/" + StringUtils.lowerCase(genScheme.getModuleName())
                + (StringUtils.isNotBlank(genScheme.getSubModuleName()) ? "/" + StringUtils.lowerCase(genScheme.getSubModuleName()) : "")
                + "/" + StringUtils.uncapitalize(genScheme.getGenTable().getClassName());

        topMenu.setName(genScheme.getFunctionName());
        topMenu.setHref(url);
        topMenu.setIsShow("1");
        topMenu.setPermission(permissionPrefix + ":list");
        systemService.saveMenu(topMenu);
        Menu addMenu = new Menu();
        addMenu.setName("增加");
        addMenu.setIsShow("0");
        addMenu.setSort(Integer.valueOf(30));
        addMenu.setPermission(permissionPrefix + ":add");
        addMenu.setParent(topMenu);
        this.systemService.saveMenu(addMenu);
        Menu delMenu = new Menu();
        delMenu.setName("删除");
        delMenu.setIsShow("0");
        delMenu.setSort(Integer.valueOf(60));
        delMenu.setPermission(permissionPrefix + ":del");
        delMenu.setParent(topMenu);
        this.systemService.saveMenu(delMenu);
        Menu editMenu = new Menu();
        editMenu.setName("编辑");
        editMenu.setIsShow("0");
        editMenu.setSort(Integer.valueOf(90));
        editMenu.setPermission(permissionPrefix + ":edit");
        editMenu.setParent(topMenu);
        this.systemService.saveMenu(editMenu);
        Menu viewMenu = new Menu();
        viewMenu.setName("查看");
        viewMenu.setIsShow("0");
        viewMenu.setSort(Integer.valueOf(120));
        viewMenu.setPermission(permissionPrefix + ":view");
        viewMenu.setParent(topMenu);
        this.systemService.saveMenu(viewMenu);
        Menu importMenu = new Menu();
        importMenu.setName("导入");
        importMenu.setIsShow("0");
        importMenu.setSort(Integer.valueOf(150));
        importMenu.setPermission(permissionPrefix + ":import");
        importMenu.setParent(topMenu);
        this.systemService.saveMenu(importMenu);
        Menu exportMenu = new Menu();
        exportMenu.setName("导出");
        exportMenu.setIsShow("0");
        exportMenu.setSort(Integer.valueOf(180));
        exportMenu.setPermission(permissionPrefix + ":export");
        exportMenu.setParent(topMenu);
        this.systemService.saveMenu(exportMenu);
    }

    /**
     * 分页查询
     *
     * @param page      分页对象
     * @param genScheme 生成方案实体类参数
     * @return Page<GenScheme>
     */
    public Page<GenScheme> find(Page<GenScheme> page, GenScheme genScheme) {
        GenUtils.getTemplatePath();
        genScheme.setPage(page);
        page.setList(this.genSchemeDao.findList(genScheme));
        return page;
    }

    /**
     * 保存（新增或修改）并生成代码文件
     *
     * @param genScheme 生成方案实体类参数
     * @return String 包含所有文件的字符串
     */
    @Transactional(readOnly = false)
    public String save(GenScheme genScheme) {
        if (StringUtils.isBlank(genScheme.getId())) {
            genScheme.preInsert();
            this.genSchemeDao.insert(genScheme);
        } else {
            genScheme.preUpdate();
            this.genSchemeDao.update(genScheme);
        }
        return generateCode(genScheme);
    }

    /**
     * 删除
     *
     * @param
     * @return void
     */
    @Transactional(readOnly = false)
    public void delete(GenScheme genScheme) {
        this.genSchemeDao.delete(genScheme);
    }

    /**
     * 根据生成方案信息，生成文件
     *
     * @param
     * @return String 包含所有文件的字符串
     */
    private String generateCode(GenScheme genScheme) {
        StringBuilder result = new StringBuilder();
        //得到业务表信息
        GenTable genTable = (GenTable) this.genTableDao.get(genScheme.getGenTable().getId());
        //得到业务表字段信息 并放到业务表对象中
        genTable.setColumnList(this.genTableColumnDao.findList(new GenTableColumn(new GenTable(genTable.getId()))));
        //解析xml文件 得到配置对象
        GenConfig config = GenUtils.getConfig();

        List<GenTemplate> templateList = GenUtils.getTemplateList(config, genScheme.getCategory(), false);
        List<GenTemplate> childTableTemplateList = GenUtils.getTemplateList(config, genScheme.getCategory(), true);

        //查询到子业务表信息
        if (childTableTemplateList.size() > 0) {
            GenTable parentTable = new GenTable();
            parentTable.setParentTable(genTable.getName());
            genTable.setChildList(this.genTableDao.findList(parentTable));
        }
        Map<String, Object> childTableModel;
        for (GenTable childTable : genTable.getChildList()) {
            childTable.setParent(genTable);
            childTable.setColumnList(this.genTableColumnDao.findList(new GenTableColumn(new GenTable(childTable.getId()))));
            genScheme.setGenTable(childTable);
            childTableModel = GenUtils.getDataModel(genScheme);
            for (GenTemplate tpl : childTableTemplateList) {
                result.append(GenUtils.generateToFile(tpl, childTableModel, true));
            }

        }

        genScheme.setGenTable(genTable);
        Map<String, Object> model = GenUtils.getDataModel(genScheme);
        for (GenTemplate tpl : templateList) {
            result.append(GenUtils.generateToFile(tpl, model, true));
        }
//      a(result);
        return result.toString();
    }

    /**
     * 处理result  得所有文件共同的父目录  就是源文件所在地址
     *
     * @param
     * @return void
     */
    private Map<String, String> getResultAddr(String result) {
        Map<String, String> map = new HashMap<String, String>();
        //1、把result里面的“生成成功：” 去掉
        result = result.replace("生成成功：", "");
        String[] addrArray = result.split("<br/>");
        //2、分三组   以.java为一组    .xml为一组  .jsp为一组, 且.xml和.jsp文件不需要编译  地址直接保存在字符串中
        List<String> javaList = new ArrayList<String>();
        String xmlStr = "";
        String jspStr = "";
        for (int i = 0; i < addrArray.length; i++) {
            if (addrArray[i].endsWith(".java")) {
                javaList.add(addrArray[i]);
            } else if (addrArray[i].endsWith(".xml")) {
                xmlStr = xmlStr + addrArray[i] + ";";
            } else if (addrArray[i].endsWith(".jsp")) {
                jspStr = jspStr + addrArray[i] + ";";
            }
        }
        if (javaList.size() == 0) {
            //没有java文件
            result = null;
        } else if (javaList.size() == 1) {
            result = javaList.get(0);
        } else if (javaList.size() == 2) {
            result = getCommonStr(javaList.get(0), javaList.get(1));
        } else {
            String commonStr = getCommonStr(javaList.get(0), javaList.get(1));
            result = getCommonStr(commonStr, javaList);
        }
        map.put("java", result);
        map.put("xml", xmlStr);
        map.put("jsp", jspStr);
        return map;
    }

    /**
     * 得到最长公共字符串
     *
     * @param List<String> 字符串集合  size大于2
     * @return String
     */
    private String getCommonStr(String commonStr, List<String> javaList) {
        for (int i = 2; i < javaList.size(); i++) {
            //从第三个
            if (!javaList.get(i).contains(commonStr)) {
                commonStr = commonStr.substring(0, commonStr.length() - 1);
                //递归操作
                getCommonStr(commonStr, javaList);
            }
        }
        return commonStr;
    }

    /**
     * 得到最长公共字符串
     *
     * @param
     * @return String
     */
    private String getCommonStr(String str1, String str2) {
        int len1 = str1.length();
        int len2 = str2.length();
        String min = null;
        String max = null;
        String target = null;
        min = len1 <= len2 ? str1 : str2;
        max = len1 > len2 ? str1 : str2;
        //最外层：min子串的长度，从最大长度开始
        for (int i = min.length(); i >= 1; i--) {
            //遍历长度为i的min子串，从0开始
            for (int j = 0; j <= min.length() - i; j++) {
                target = min.substring(j, j + i);
                //遍历长度为i的max子串，判断是否与target子串相同，从0开始
                for (int k = 0; k <= max.length() - i; k++) {
                    if (max.substring(k, k + i).equals(target)) {
                        return target;
                    }
                }
            }
        }
        return target;
    }


    public static void main(String[] args) {
        StringBuilder result = new StringBuilder();
        result.append("生成成功：D:\\src\\main\\java\\com\\cstor\\modules\\test\\web\\test1\\Test1Controller.java<br/>");
        result.append("生成成功：D:\\src\\main\\java\\com\\cstor\\modules\\test\\service\\test1\\Test1Service.java<br/>");
        result.append("生成成功：D:\\src\\main\\java\\com\\cstor\\modules\\test\\dao\\test1\\Test1Dao.java<br/>");
        result.append("生成成功：D:\\src\\main\\java\\com\\cstor\\modules\\test\\entity\\test1\\Test1.java<br/>");
        result.append("生成成功：D:\\src\\main\\resources\\mappings\\modules\\test\\test1\\Test1Dao.xml<br/>");
        result.append("生成成功：D:\\src\\main\\webapp\\webpage\\modules\\test\\test1\\test1Form.jsp<br/>");
        result.append("生成成功：D:\\src\\main\\webapp\\webpage\\modules\\test\\test1\\test1List.jsp<br/>");
        System.err.println(new GenSchemeService().getResultAddr(result.toString()));

    }

    /**
     * 根据列名称及对应的值  查询
     *
     * @param
     * @return GenScheme
     */
    public GenScheme findUniqueByProperty(String propertyName, String value) {
        return (GenScheme) this.genSchemeDao.findUniqueByProperty(propertyName, value);
    }
}
