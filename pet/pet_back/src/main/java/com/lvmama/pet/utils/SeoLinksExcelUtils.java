package com.lvmama.pet.utils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.jxls.reader.ReaderBuilder;
import net.sf.jxls.reader.XLSReader;
import net.sf.jxls.transformer.Workbook;
import net.sf.jxls.transformer.XLSTransformer;

import com.lvmama.comm.pet.po.seo.SeoLinks;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.utils.ResourceUtil;
import com.lvmama.comm.vo.Constant;

/**
 * jxls操作excel
 * @author nixianjun 2013.8.9
 *
 */
public class SeoLinksExcelUtils{
	/**
	 * 导入excel配置文件地址
	 */
	public static final String SEOLINKSEXCELCONFIGXMLABSPATH="/WEB-INF/resources/SeoLinks/SeoLinksExcel.xml";
	/**
	 * 导出excel对应模板文件地址
	 */
	public static final String SEOLINKSEXCELWRITETEMPPATH="/WEB-INF/resources/SeoLinks/seoTemplate.xlsx";
	/**
	 * 解析excel
	 * @param excelfile  导入 excel文件的地址
	 * @param xmlConfig 数据映射文件地址
	 * @return
	 * @author nixianjun 2013-8-9
	 */
  public static List<SeoLinks> parseExcelByjXls(String excelfile,String xmlConfig){
   try {
   InputStream inputXML = new BufferedInputStream(new FileInputStream(xmlConfig));
   XLSReader mainReader = ReaderBuilder.buildFromXML(inputXML);
   InputStream inputXLS =new BufferedInputStream(new FileInputStream(excelfile));
   SeoLinks seoLinks = new SeoLinks();
   List<SeoLinks> seoLinksList = new ArrayList<SeoLinks>();
   Map beans = new HashMap();
   beans.put("seoLinks", seoLinks);
   beans.put("seoLinksList", seoLinksList);
   mainReader.read(inputXLS , beans);
   return seoLinksList;
   } catch(Exception ex){
     ex.printStackTrace();
   }
     return null;
  }
 
  	/**
  	 * 写excel通过模板 bean
  	 * @param beans
  	 * @param template
  	 * @return
  	 * @throws Exception
  	 * @author nixianjun 2013-8-12
  	 */
	public static String writeExcelByjXls(Map<String,Object> beans,String template){
		try {
		File templateResource = ResourceUtil.getResourceFile(template);
		XLSTransformer transformer = new XLSTransformer();
		String destFileName = Constant.getTempDir() + "/excel"+new Date().getTime();
		transformer.transformXLS(templateResource.getAbsolutePath(), beans, destFileName);
		return destFileName;
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
}