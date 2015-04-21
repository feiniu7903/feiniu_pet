package com.lvmama.businessreply.job;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lvmama.businessreply.utils.etao.XMLBuilderUtil;
import com.lvmama.businessreply.utils.ruichuang.Ruichuang;
import com.lvmama.comm.pet.po.search.ProductSearchInfo;
import com.lvmama.comm.pet.service.search.ProductSearchInfoService;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.vo.Constant;

public class RuichuangBuildXmlJob implements Runnable {
	//取搜素信息
	private ProductSearchInfoService productSearchInfoService;
	
	private final static String PREFIX="lvmama";

	@Override
	public void run() {
		if(Constant.getInstance().isJobRunnable()){
			try {
				Map<String,Object> parameters = new HashMap<String,Object>();
				//获得总条数
				parameters.put("isValid", "Y");
				parameters.put("channel","FRONTEND");
				parameters.put("productType","ROUTE");
				Long count = productSearchInfoService.countProductSearchInfoByParam(parameters);
				//创建文件夹
				String basePath=Constant.getInstance().getProperty("lvc.root.path");
				String rootDirPath = XMLBuilderUtil.getDirPath("", basePath);
				File rootDir = new File(rootDirPath);
				if(!rootDir.exists()){
					rootDir.mkdir();
				}
				XMLBuilderUtil.delFile(rootDirPath);
				int  rowCount = 500; //每页显示500条
				int  pageCount = getTotalPages(count.intValue(), rowCount); //总页数
				for(int curPage =1;curPage<=pageCount;curPage++){
					parameters.put("startRows",  (curPage - 1) * rowCount + 1);
					parameters.put("endRows", curPage * rowCount);
					List<ProductSearchInfo>  results=productSearchInfoService.queryProductSearchInfoByParam(parameters);
					File savedFile = new File(basePath + "/" + PREFIX + "_" + DateUtil.formatDate(new Date(), "yyyyMMdd") + "_" +  curPage + ".xml");
					XMLBuilderUtil.saveXML(savedFile.getAbsolutePath(), Ruichuang.buildXMLForRuiChuang(results));
				}
				//创建Map。xml
				StringBuilder mapXmlBuilder = new StringBuilder();//建立xml
				mapXmlBuilder.append("<?xml version=\"1.0\" encoding=\"utf-8\"?><map>");
				mapXmlBuilder.append("<filename>" + PREFIX + "</filename>");
				mapXmlBuilder.append("<lastmod>"+ DateUtil.formatDate(new Date(), "yyyyMMdd") +"</lastmod>");
				mapXmlBuilder.append("<url>http://www.lvmama.com/Businesses/ruichuang/</url>");
				mapXmlBuilder.append("<total>" +pageCount+ "</total>");
				mapXmlBuilder.append("</map>");
				
				File savedMapFile = new File(basePath+"/map.xml");
				XMLBuilderUtil.saveXML(savedMapFile.getAbsolutePath(), mapXmlBuilder.toString());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	/**总页数
	 * @param totalResultSize
	 * @param pageSize
	 * @return
	 */
	public int getTotalPages(int totalResultSize, int pageSize) {
		if (totalResultSize % pageSize > 0)
			return totalResultSize / pageSize + 1;
		else
			return totalResultSize / pageSize;
	}


	public void setProductSearchInfoService(
			ProductSearchInfoService productSearchInfoService) {
		this.productSearchInfoService = productSearchInfoService;
	}

}
