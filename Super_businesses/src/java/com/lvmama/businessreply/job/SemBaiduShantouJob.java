package com.lvmama.businessreply.job;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lvmama.businessreply.po.EtaoProduct;
import com.lvmama.businessreply.utils.etao.GroupBuyBuildFactory;
import com.lvmama.businessreply.utils.etao.XMLBuilderUtil;
import com.lvmama.businessreply.utils.shantou.ShantouBuild;
import com.lvmama.comm.pet.po.search.Shantou;
import com.lvmama.comm.pet.service.search.ProductSearchInfoService;
import com.lvmama.comm.utils.DomUtils;
import com.lvmama.comm.vo.Constant;

public class SemBaiduShantouJob implements Runnable {
	//取Product_Seach_Info信息的Service
	private ProductSearchInfoService productSearchInfoService; 
	@Override
	public void run() {
		if (Constant.getInstance().isJobRunnable()) {
			   String basePath = Constant.getInstance().getProperty("etao_path");
 				Map<String,Object> parameters = new HashMap<String,Object>();
 				Integer count = productSearchInfoService.countSelectShantouListByParam(parameters).intValue();
 				Integer rowCount = 900;
 				Integer pageCount = getTotalPages(count, rowCount);
 				StringBuilder xmlBuilderTotal = new StringBuilder();//建立xml
 				for(int curPage =1;curPage<=pageCount;curPage++){
 					int startRow = (curPage - 1) * rowCount + 1;
 					int endRow = curPage * rowCount;
 					parameters.put("startRows", startRow);
 					parameters.put("endRows", endRow);
 					List<Shantou>  results=productSearchInfoService.selectShantouListByParam(parameters);
 					xmlBuilderTotal.append(ShantouBuild.buildXMLForShantou(results, pageCount, curPage));
 				}
 				String rootDirPath = XMLBuilderUtil.getDirPath(basePath, Constant.getInstance().getProperty("SHANTOU_ROOT_PATH"));
 				File rootDir = new File(rootDirPath);
 				if(!rootDir.exists()){
 					rootDir.mkdir();
 				}
 	 			XMLBuilderUtil.delFile(rootDirPath);
 				String filePath = XMLBuilderUtil.getFilePath(rootDirPath, Constant.getInstance().getProperty("SHANTOU_FILANAME"));
 				File savedFile = new File(filePath);
 				XMLBuilderUtil.saveXML(savedFile.getAbsolutePath(), xmlBuilderTotal.toString());

		}
	}
	public void setProductSearchInfoService(
			ProductSearchInfoService productSearchInfoService) {
		this.productSearchInfoService = productSearchInfoService;
	}
	/**
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

}
