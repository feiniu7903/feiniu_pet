package com.lvmama.front.web.dacun;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Map;

import freemarker.core.Configurable;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
/**
 * 通freemarker生成html文件，供大促使用
 * @author nixianjun
 *
 */
public class CreateHTML {
	private Configurable freemarker_cfg = null;

	/**
	 * 创建多级目录
	 * 
	 * @param path
	 *            String
	 * @return boolean 是否成功
	 */
	private boolean creatDirs(String path) {
		File aFile = new File(path);
		if (!aFile.exists()) {
			return aFile.mkdirs();
		} else {
			return true;
		}
	}

	/**
	 * 生成静态文件.
	 * 
	 * @param templateFileName
	 *            模板文件名,相对htmlskin路径,例如"/tpxw/view.ftl"
	 * @param propMap
	 *            用于处理模板的属性Object映射
	 * @param htmlFilePath
	 *            要生成的静态文件的路径,相对设置中的根路径,例如 "/tpxw/1/2005/4/"
	 * @param htmlFileName
	 *            要生成的文件名,例如 "1.htm"
	 * @param templateFilePath
	 *            模板路径
	 * @return boolean true代表生成文件成功
	 */
	@SuppressWarnings("unchecked")
	public void geneHtmlFile(String templateFileName, Map propMap,
			String htmlFilePath, String htmlFileName, String templateFilePath) {

		try {
			Template t = this.getFreeMarkerCFG(templateFilePath).getTemplate(
					templateFileName);
			// 如果根路径存在,则递归创建子目录
			this.creatDirs(htmlFilePath);
			File afile = new File(htmlFilePath + "/" + htmlFileName);
			Writer out = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(afile)));
			t.process(propMap, out);
			out.flush();
			out.close();
		} catch (TemplateException e) {
			System.out.print(e.getMessage());
		} catch (IOException e) {
			System.out.print(e.getMessage());
		} catch (Exception e) {
			System.out.print(e.getMessage());
		}
	}

	/**
	 * 
	 * 获取freemarker的配置. freemarker本身支持classpath,目录和从ServletContext获取.
	 * 
	 * @param templateFilePath
	 *            获取模板路径
	 * @return Configuration 返回freemaker的配置属性
	 * @throws Exception
	 */
	private Configuration getFreeMarkerCFG(String templateFilePath)
			throws Exception {
		if (null == this.freemarker_cfg) {

			this.freemarker_cfg = new Configuration();
			try {
				((Configuration) this.freemarker_cfg)
						.setDirectoryForTemplateLoading(new File(
								templateFilePath));
			} catch (Exception ex) {
				throw ex;
			}
		}
		return (Configuration) this.freemarker_cfg;
	}

}