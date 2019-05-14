package com.njrz.modules.gen.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;

import com.njrz.common.config.Global;
import com.njrz.common.mapper.JaxbMapper;
import com.njrz.common.utils.DateUtils;
import com.njrz.common.utils.FileUtils;
import com.njrz.common.utils.FreeMarkers;
import com.njrz.common.utils.StringUtils;
import com.njrz.modules.gen.entity.GenCategory;
import com.njrz.modules.gen.entity.GenConfig;
import com.njrz.modules.gen.entity.GenScheme;
import com.njrz.modules.gen.entity.GenTable;
import com.njrz.modules.gen.entity.GenTableColumn;
import com.njrz.modules.gen.entity.GenTemplate;
import com.njrz.modules.sys.entity.Area;
import com.njrz.modules.sys.entity.Office;
import com.njrz.modules.sys.entity.User;
import com.njrz.modules.sys.utils.UserUtils;

/**
 * 业务工具类
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 *
 * @author qizhonghai
 * @date 2016-3-7 下午5:27:43
 * @since v1.0
 */
public class GenUtils {
    private static Logger logger = LoggerFactory.getLogger(GenUtils.class);

    /**
     * 初始化  列的java字段
     *
     * @param
     * @return void
     */
    public static void initColumnField(GenTable genTable) {
        for (GenTableColumn column : genTable.getColumnList()) {
            //如果当前的表字段信息的id不是空的，说明此信息在GenTableColumn对应的表中
            if (StringUtils.isNotBlank(column.getId())) {
                continue;
            }
            //如果表字段的描述信息为空  则 以表字段名为描述信息
            if (StringUtils.isBlank(column.getComments())) {
                column.setComments(column.getName());
            }
            //如果列的数据类型的字节长度JdbcType 是以CHAR或VARCHAR或NARCHAR，则统一java类型为String型
            if ((StringUtils.startsWithIgnoreCase(column.getJdbcType(), "CHAR")) ||
                    (StringUtils.startsWithIgnoreCase(column.getJdbcType(), "VARCHAR")) ||
                    (StringUtils.startsWithIgnoreCase(column.getJdbcType(), "NARCHAR"))) {
                column.setJavaType("String");
            }
            //如果列的数据类型的字节长度JdbcType 是以DATETIME或DATE或TIMESTAMP，
            //则统一java类型为java.util.Date型,字段生成方案统一为dateselect--日期选择
            else if ((StringUtils.startsWithIgnoreCase(column.getJdbcType(), "DATETIME")) ||
                    (StringUtils.startsWithIgnoreCase(column.getJdbcType(), "DATE")) ||
                    (StringUtils.startsWithIgnoreCase(column.getJdbcType(), "TIMESTAMP"))) {
                column.setJavaType("java.util.Date");
                column.setShowType("dateselect");
            }
            //如果列的数据类型的字节长度JdbcType 是以BIGINT或NUMBER，
            //根据具体情况 变为Double，Integer，Long
            else if ((StringUtils.startsWithIgnoreCase(column.getJdbcType(), "BIGINT")) ||
                    (StringUtils.startsWithIgnoreCase(column.getJdbcType(), "NUMBER"))) {
                String[] ss = StringUtils.split(StringUtils.substringBetween(column.getJdbcType(), "(", ")"), ",");
                if ((ss != null) && (ss.length == 2) && (Integer.parseInt(ss[1]) > 0)) {
                    column.setJavaType("Double");
                } else if ((ss != null) && (ss.length == 1) && (Integer.parseInt(ss[0]) <= 10)) {
                    column.setJavaType("Integer");
                } else {
                    column.setJavaType("Long");
                }

            }

            column.setJavaField(StringUtils.toCamelCase(column.getName()));

            column.setIsPk(genTable.getPkList().contains(column.getName()) ? "1" : "0");

            column.setIsInsert("1");

            if ((!StringUtils.equalsIgnoreCase(column.getName(), "id")) &&
                    (!StringUtils.equalsIgnoreCase(column.getName(), "create_by")) &&
                    (!StringUtils.equalsIgnoreCase(column.getName(), "create_date")) &&
                    (!StringUtils.equalsIgnoreCase(column.getName(), "del_flag")))
                column.setIsEdit("1");
            else {
                column.setIsEdit("0");
            }

            if ((StringUtils.equalsIgnoreCase(column.getName(), "name")) ||
                    (StringUtils.equalsIgnoreCase(column.getName(), "title")) ||
                    (StringUtils.equalsIgnoreCase(column.getName(), "remarks")) ||
                    (StringUtils.equalsIgnoreCase(column.getName(), "update_date")))
                column.setIsList("1");
            else {
                column.setIsList("0");
            }

            if ((StringUtils.equalsIgnoreCase(column.getName(), "name")) ||
                    (StringUtils.equalsIgnoreCase(column.getName(), "title")))
                column.setIsQuery("1");
            else {
                column.setIsQuery("0");
            }

            if ((StringUtils.equalsIgnoreCase(column.getName(), "name")) ||
                    (StringUtils.equalsIgnoreCase(column.getName(), "title")))
                column.setQueryType("like");
            else {
                column.setQueryType("=");
            }

            if (StringUtils.startsWithIgnoreCase(column.getName(), "user_id")) {
                column.setJavaType(User.class.getName());
                column.setJavaField(column.getJavaField().replaceAll("Id", ".id|name"));
                column.setShowType("userselect");
            } else if (StringUtils.startsWithIgnoreCase(column.getName(), "office_id")) {
                column.setJavaType(Office.class.getName());
                column.setJavaField(column.getJavaField().replaceAll("Id", ".id|name"));
                column.setShowType("officeselect");
            } else if (StringUtils.startsWithIgnoreCase(column.getName(), "area_id")) {
                column.setJavaType(Area.class.getName());
                column.setJavaField(column.getJavaField().replaceAll("Id", ".id|name"));
                column.setShowType("areaselect");
            } else if ((StringUtils.startsWithIgnoreCase(column.getName(), "create_by")) ||
                    (StringUtils.startsWithIgnoreCase(column.getName(), "update_by"))) {
                column.setJavaType(User.class.getName());
                column.setJavaField(column.getJavaField() + ".id");
                column.setShowType("input");
            } else if ((StringUtils.startsWithIgnoreCase(column.getName(), "create_date")) ||
                    (StringUtils.startsWithIgnoreCase(column.getName(), "update_date"))) {
                column.setShowType("dateselect");
            } else if ((StringUtils.equalsIgnoreCase(column.getName(), "remarks")) ||
                    (StringUtils.equalsIgnoreCase(column.getName(), "content"))) {
                column.setShowType("textarea");
            } else if (StringUtils.equalsIgnoreCase(column.getName(), "parent_id")) {
                column.setJavaType("This");
                column.setJavaField("parent.id|name");
                column.setShowType("treeselect");
            } else if (StringUtils.equalsIgnoreCase(column.getName(), "parent_ids")) {
                column.setShowType("input");
                column.setQueryType("like");
            } else if (StringUtils.equalsIgnoreCase(column.getName(), "del_flag")) {
                column.setShowType("radiobox");
                column.setDictType("del_flag");
            } else {
                column.setShowType("input");
            }
        }
    }

    public static String getTemplatePath() {
        try {
            File file = new DefaultResourceLoader().getResource("").getFile();
            if (file != null)
                return file.getAbsolutePath() + File.separator +
                        StringUtils.replaceEach(GenUtils.class.getName(),
                                new String[]{"util." + GenUtils.class.getSimpleName(), "."}, new String[]{"template", File.separator});
        } catch (Exception e) {
            logger.error("{}", e);
        }

        return "";
    }

    /**
     * 解析xml文件  得到对应对象
     *
     * @param fileName 文件名
     * @param clazz    所要转成的java类    Class
     * @return T
     */
    public static <T> T fileToObject(String fileName, Class<T> clazz) {
        try {
            String pathName = "/templates/modules/gen/" + fileName;
            Resource resource = new ClassPathResource(pathName);
            InputStream is = resource.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            StringBuilder sb = new StringBuilder();
            while (true) {
                String line = br.readLine();
                if (line == null) {
                    break;
                }
                sb.append(line).append("\r\n");
            }
            if (is != null) {
                is.close();
            }
            if (br != null) {
                br.close();
            }
            return JaxbMapper.fromXml(sb.toString(), clazz);
        } catch (IOException e) {
            logger.warn("Error file convert: {}", e.getMessage());
        }
        return null;
    }

    /**
     * 解析xml文件  得到对应对象
     *
     * @param
     * @return GenConfig
     */
    public static GenConfig getConfig() {
        return (GenConfig) fileToObject("config.xml", GenConfig.class);
    }

    /**
     * @param config       配置对象
     * @param category     代码生成分类
     * @param isChildTable 是否是子表  true：是，false：不是
     * @return List<GenTemplate>
     */
    public static List<GenTemplate> getTemplateList(GenConfig config, String category, boolean isChildTable) {
        List<GenTemplate> templateList = new ArrayList<GenTemplate>();
        if ((config != null) && (config.getCategoryList() != null) && (category != null)) {
            for (GenCategory e : config.getCategoryList()) {
                //如果配置对象里的代码生成分类  与传过来的分类参数category相同  则进入if
                if (category.equals(e.getValue())) {
                    //list 存的是config.xml中<template>或<childTable>中的值集合
                    List<String> list = null;
                    if (!isChildTable)
                        list = e.getTemplate();
                    else {
                        list = e.getChildTableTemplate();
                    }
                    if (list == null)
                        break;
                    for (String s : list) {
                        if (StringUtils.startsWith(s, GenCategory.CATEGORY_REF)) {
                            templateList.addAll(getTemplateList(config, StringUtils.replace(s, GenCategory.CATEGORY_REF, ""), false));
                        } else {
                            GenTemplate template = (GenTemplate) fileToObject(s, GenTemplate.class);
                            if (template != null) {
                                templateList.add(template);
                            }
                        }
                    }
                    break;
                }
            }
        }
        return templateList;
    }

    /**
     * 把genScheme 转为map形式
     *
     * @param genScheme 生成方案的Entity
     * @return Map<String,Object>
     */
    public static Map<String, Object> getDataModel(GenScheme genScheme) {
        Map<String, Object> model = new HashMap<String, Object>();
        //生成包路径 ,如：com.njrz.modules
        model.put("packageName", StringUtils.lowerCase(genScheme.getPackageName()));
        //生成包路径尾名 ,如：com.njrz.modules中的modules
        model.put("lastPackageName", StringUtils.substringAfterLast((String) model.get("packageName"), "."));
        //生成模块名 ,如：test
        model.put("moduleName", StringUtils.lowerCase(genScheme.getModuleName()));
        //生成子模块名,如：one
        model.put("subModuleName", StringUtils.lowerCase(genScheme.getSubModuleName()));
        //业务表的对应实体类名称 首字符小写
        model.put("className", StringUtils.uncapitalize(genScheme.getGenTable().getClassName()));
        //业务表的对应实体类名称 首字符大写
        model.put("ClassName", StringUtils.capitalize(genScheme.getGenTable().getClassName()));
        //生成功能名,如：员工请假
        model.put("functionName", genScheme.getFunctionName());
        //生成功能名简写,如：请假
        model.put("functionNameSimple", genScheme.getFunctionNameSimple());
        model.put("functionAuthor", StringUtils.isNotBlank(genScheme.getFunctionAuthor()) ? genScheme.getFunctionAuthor() : UserUtils.getUser().getName());
        model.put("functionVersion", DateUtils.getDate());
        //moduleName/subModuleName/className
        model.put("urlPrefix", model.get("moduleName") + (StringUtils.isNotBlank(genScheme.getSubModuleName()) ?
                "/" + StringUtils.lowerCase(genScheme.getSubModuleName()) : "") + "/" + model.get("className"));
        model.put("viewPrefix",
                model.get("urlPrefix"));

        //moduleName:subModuleName:className
        model.put("permissionPrefix", model.get("moduleName") + (StringUtils.isNotBlank(genScheme.getSubModuleName()) ?
                ":" + StringUtils.lowerCase(genScheme.getSubModuleName()) : "") + ":" + model.get("className"));
        //数据库类型 即是什么数据库
        model.put("dbType", Global.getConfig("jdbc.type"));

        model.put("table", genScheme.getGenTable());

        return model;
    }

    /**
     * 生产文件
     *
     * @param tpl           模板实体类
     * @param model         数据map
     * @param isReplaceFile 是否代替原有文件  true：是，false：不是
     * @return String ： "生成成功：" + fileName + "<br/>";
     */
    public static String generateToFile(GenTemplate tpl, Map<String, Object> model, boolean isReplaceFile) {
        //文件路径
        String projectPath = Global.getProjectPath();
        String oldPath = FreeMarkers.renderString(new StringBuilder(String.valueOf(tpl.getFilePath())).append("/").toString(), model);
        String newPath = StringUtils.replaceEach(oldPath, new String[]{"//", "/", "."}, new String[]{File.separator, File.separator, File.separator});
        String fileName = projectPath + File.separator + newPath + FreeMarkers.renderString(tpl.getFileName(), model);
        logger.debug(" fileName === " + fileName);
        String content = FreeMarkers.renderString(StringUtils.trimToEmpty(tpl.getContent()), model);
        logger.debug(" content === \r\n" + content);

        if (isReplaceFile) {
            FileUtils.deleteFile(fileName);
        }

        if (FileUtils.createFile(fileName)) {
            FileUtils.writeToFile(fileName, content, true);
            logger.debug(" file create === " + fileName);
            return "生成成功：" + fileName + "<br/>";
        }
        logger.debug(" file extents === " + fileName);
        return "文件已存在：" + fileName + "<br/>";
    }

    public static void main(String[] args) {
        try {
            GenConfig config = getConfig();
            System.out.println(config);
            System.out.println(JaxbMapper.toXml(config));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
