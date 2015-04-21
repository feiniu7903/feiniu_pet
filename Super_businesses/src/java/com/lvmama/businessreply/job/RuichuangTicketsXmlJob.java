package com.lvmama.businessreply.job;

import java.io.File;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONArray;
import org.json.JSONObject;
import com.lvmama.businessreply.utils.etao.XMLBuilderUtil;
import com.lvmama.businessreply.utils.ruichuang.TicketsJson;
import com.lvmama.comm.pet.po.search.ProductPlaceSearchInfo;
import com.lvmama.comm.pet.service.search.ProductSearchInfoService;
import com.lvmama.comm.utils.FileUtil;
import com.lvmama.comm.utils.MD5;
import com.lvmama.comm.vo.Constant;

/**
 * 旅游门票 job
 * 
 * @author liukang
 * 
 */
public class RuichuangTicketsXmlJob implements Runnable {
	
	private Log log=LogFactory.getLog(RuichuangTicketsXmlJob.class);
	// 取搜素产品和景点信息
	private ProductSearchInfoService productSearchInfoService;
	
	private final static int pageSize=1000;

	/** 
	* 获得总页数 
	* 
	* @param totalItems 
	* 总记录数 
	* @return 
	*/ 
	private int getTotalPage(int totalItems, int pageSize) { 
		int rows = totalItems; 
		int mod = rows % pageSize; 
		if (mod == 0) { 
		return rows / pageSize; 
		} 
		return rows / pageSize + 1; 
	}
	
	
	@Override
	public void run() {
		if(Constant.getInstance().isJobRunnable()){
			log.info("RuichuangTicketsJob running...");
			try {
				String basePath=Constant.getInstance().getProperty("lvc.ticketsToot.path");
				log.info("RuichuangTicketsJob basePath:"+basePath);
				String rootDirPath = XMLBuilderUtil.getDirPath("", basePath);
				File rootDir = new File(rootDirPath);
				if(!rootDir.exists()){
					log.info("RuichuangTicketsJob mkdir,"+rootDir.getPath());
					rootDir.mkdir();
				}
				XMLBuilderUtil.delFile(rootDirPath);
				//查询总数量
				Long count=productSearchInfoService.queryPlaceProdctSearchInfoByCount();
				if(null!=count&&count>0){
					log.info("RuichuangTicketsJob count size,"+count);
				}else{
					log.error("RuichuangTicketsJob count size "+count);
				}
				Map<String,Object> parameters = new HashMap<String,Object>();
				List<ProductPlaceSearchInfo> list=new ArrayList<ProductPlaceSearchInfo>();
				int totalPage=getTotalPage(count.intValue(),pageSize);
				for (int page = 1; page <=totalPage; page++) {
					int startRows=(page - 1) * pageSize + 1;
					int endRows = 0;
					if(page==totalPage){
						endRows = count.intValue();
					}else {
						endRows = startRows+pageSize-1;
					}
					parameters.put("startRows", startRows);
					parameters.put("endRows", endRows);
					System.out.println(parameters);
					List<ProductPlaceSearchInfo> listTwo =productSearchInfoService.queryPlaceProdctSearchInfoByParam(parameters);
					list.addAll(listTwo);
				}
				log.info("RuichuangTicketsJob list size,"+list.size());
				Map<Long,List<ProductPlaceSearchInfo>> map= new HashMap<Long,List<ProductPlaceSearchInfo>>();
				//需要的景点名称
				Map<String, String> dataMap=TicketsJson.readTxt2ReplaceMap();
				log.info("RuichuangTicketsJob place name mapping size:"+dataMap.size());
				//循环list 给map赋值 一个城市对应多个门票
				for (ProductPlaceSearchInfo ppi : list) {
					//先判断是不是想要的景点名称
					if(dataMap.containsKey(ppi.getPlaceName())){
						log.info("RuichuangTicketsJob add place:"+ppi.getPlaceName());
						//如果键值不存在
						if(!map.containsKey(ppi.getPlaceId())){
							List<ProductPlaceSearchInfo> productList=new ArrayList<ProductPlaceSearchInfo>();
							productList.add(ppi);
							map.put(ppi.getPlaceId(), productList);
						}else{
							List<ProductPlaceSearchInfo> productList=map.get(ppi.getPlaceId());
							productList.add(ppi);
							map.put(ppi.getPlaceId(), productList);
						}
					}
				}
				//一个城市对应一个json
				Map<String ,JSONArray> cityMap=new HashMap<String, JSONArray>();
				JSONArray jsonArr=TicketsJson.buildJSONString(dataMap,map);
					if(null!=jsonArr&&jsonArr.length()>0){
						log.info("RuichuangTicketsJob JSONArray size:"+jsonArr.length());
					}else{
						log.error("RuichuangTicketsJob JSONArray  can not by created");
					}
				for(int i=0;i<jsonArr.length();i++){
					JSONObject jsonObj=jsonArr.getJSONObject(i);
					String cityName=jsonObj.get("city").toString();
					//如果存在
					if(cityMap.containsKey(cityName)){
						cityMap.get(cityName).put(jsonObj);
					}else{
						JSONArray arra=new JSONArray();
						arra.put(jsonObj);
						cityMap.put(cityName, arra);
					}
				}
				//循环保存到文件中
				for (Map.Entry<String ,JSONArray> entry:cityMap.entrySet()) {
					File savedFile = new File(basePath+"/"+MD5.encode(entry.getKey())+".json");
					log.info("RuichuangTicketsJob start save city json file path :"+savedFile.getPath());
					XMLBuilderUtil.saveXML(savedFile.getAbsolutePath(),entry.getValue().toString());
					log.info("RuichuangTicketsJob success save city json file :"+savedFile.getPath());
				}
				
				File [] file =  rootDir.listFiles();
				if(null!=file&&file.length>0){
					log.info("RuichuangTicketsJob json file size :"+file.length);
				}else{
					log.error("RuichuangTicketsJob json file can not by created");
				}
				log.info("RuichuangTicketsJob create zip file, path:"+basePath+"/Tickets.zip");
				//压缩
				FileUtil.ZipFiles(file,basePath+"/Tickets.zip");
				log.info("2345景点门票接口数据生成成功");
				log.info("RuichuangTicketsJob success created zip file, path:"+basePath+"/Tickets.zip");
			} catch (Exception e) {
				e.printStackTrace();
				log.error("RuichuangTicketsJob error:"+e.getMessage());
			}
		}
		
	}

	public void setProductSearchInfoService(
			ProductSearchInfoService productSearchInfoService) {
		this.productSearchInfoService = productSearchInfoService;
	}
	
}
