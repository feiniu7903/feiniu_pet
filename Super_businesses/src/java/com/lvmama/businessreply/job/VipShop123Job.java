package com.lvmama.businessreply.job;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.lvmama.businessreply.utils.etao.XMLBuilderUtil;
import com.lvmama.businessreply.utils.vipshop.VipShop;
import com.lvmama.comm.pet.service.search.ProductSearchInfoService;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.vo.Constant;

/**
 * 百度度假知心的定时任务
 * @author Administrator
 *
 */
public class VipShop123Job implements Runnable {
	//取搜素信息
		private ProductSearchInfoService productSearchInfoService;
		@Override
		public void run() {
			if(Constant.getInstance().isJobRunnable()){
				try {
					Map<String,Object> parameters = new HashMap<String,Object>();
					//获得总条数
					parameters.put("isValid", "Y");
					parameters.put("channel","FRONTEND");
					parameters.put("productType","ROUTE");
					parameters.put("fromDest", "上海");
					Long count = productSearchInfoService.countProductSearchInfoByParam(parameters);
					
					//穿件文件夹
					String basePath=Constant.getInstance().getProperty("vipshop123.root.path");
					
					String rootDirPath = XMLBuilderUtil.getDirPath("", basePath);
					File rootDir = new File(rootDirPath);
					if(!rootDir.exists()){
						rootDir.mkdir();
					}
					XMLBuilderUtil.delFile(rootDirPath);
					
					StringBuilder sb = new StringBuilder();
					for(int start = 0; start < count; start += 500){
						parameters.put("startRows", start);
						parameters.put("endRows", start + 500);
						VipShop.buildJSONString(productSearchInfoService.queryProductSearchInfoByParam(parameters), sb);
					}
					
					File savedFile = new File(basePath + "/DATA." + DateUtil.formatDate(new Date(), "yyyyMMdd")  + ".txt");
					XMLBuilderUtil.saveXML(savedFile.getAbsolutePath(), sb.toString());
					
					
					StringBuilder mapXmlBuilder = new StringBuilder();//建立xml
					mapXmlBuilder.append("http://www.lvmama.com/Businesses/vipshop" + "/DATA." + DateUtil.formatDate(new Date(), "yyyyMMdd")  + ".txt");
					
					File savedMapFile = new File(basePath+"/index." + DateUtil.formatDate(new Date(), "yyyyMMdd")  + ".txt");
					XMLBuilderUtil.saveXML(savedMapFile.getAbsolutePath(), mapXmlBuilder.toString());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		
		public void setProductSearchInfoService(
				ProductSearchInfoService productSearchInfoService) {
			this.productSearchInfoService = productSearchInfoService;
		}		
}
