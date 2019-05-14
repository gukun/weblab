 package com.njrz.modules.gen.template;
 
 import freemarker.template.Configuration;
 import freemarker.template.Template;
 import java.io.StringWriter;
 import java.util.Map;
 /**
  * FreeMarker模板引擎帮助类
  * 〈基于模板来生成文本输出〉
  * 〈功能详细描述〉     
  * @author qizhonghai 
  * @since v1.0
  * @date 2016-3-8 上午11:32:53
  */
 public class FreemarkerHelper
 {
	 /**
	  *负责管理FreeMarker模板的Configuration实例
	  */
   private static Configuration _tplConfig = new Configuration();
 
   static { _tplConfig.setClassForTemplateLoading(FreemarkerHelper.class, "/");
   }
 
   /**
    * 解析模板
    * @param  tplName 模板文件名（包括了路径）
    * @param  encoding 字符集名称
    * @param  paras map数据
    * @return String
    */
   public String parseTemplate(String tplName, String encoding, Map<String, Object> paras)
   {
     try
     {
     StringWriter swriter = new StringWriter();
      Template mytpl = null;
      //获得模板文件
      mytpl = _tplConfig.getTemplate(tplName, encoding);
      //往模板里写入paras数据
      mytpl.process(paras, swriter);
       return swriter.toString();
     } catch (Exception e) {
       e.printStackTrace();
       return e.toString();
     }
   }
 
   /**
    * 以utf-8形式解析模板
    * @param  tplName 模板文件名（包括了路径）
    * @param  paras map数据
    * @return String
    */
   public String parseTemplate(String tplName, Map<String, Object> paras)
   {
     return parseTemplate(tplName, "utf-8", paras);
   }
 }

