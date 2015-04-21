package com.lvmama.comm.utils;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.jxls.transformer.XLSTransformer;

import com.lvmama.comm.vo.Constant;

public class ExcelUtils {

	/**
	 * 写Xls文件（模板报表）
	 * 
	 * @author: ranlongfei 2012-12-17 下午8:40:25
	 * @param list
	 * @param template
	 * @return
	 * @throws Exception
	 */
	public static String writeXlsFile(List<?> list,String template) throws Exception {
		File templateResource = ResourceUtil.getResourceFile(template);
		Map<String,Object> beans = new HashMap<String,Object>();
		beans.put("count", list.size());
		beans.put("datalist", list);
		XLSTransformer transformer = new XLSTransformer();
		String destFileName = Constant.getTempDir() + "/excel"+new Date().getTime()+".xls";
		transformer.transformXLS(templateResource.getAbsolutePath(), beans, destFileName);
		return destFileName;
	}
}
