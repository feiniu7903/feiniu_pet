package com.lvmama.train.web;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.lvmama.comm.BaseAction;
import com.lvmama.comm.bee.service.TrainDataSyncService;
import com.lvmama.comm.bee.service.prod.ProdTrainService;
import com.lvmama.comm.pet.po.search.ProdTrainCache;
import com.lvmama.comm.pet.service.search.ProdTrainCacheService;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.utils.ResourceUtil;
import com.lvmama.comm.utils.json.JSONResult;
import com.lvmama.comm.vo.train.product.CityStationInfo;
import com.lvmama.comm.vo.train.product.LineInfo;
import com.lvmama.comm.vo.train.product.LineStopsInfo;
import com.lvmama.comm.vo.train.product.LineStopsStationInfo;
import com.lvmama.comm.vo.train.product.Station2StationInfo;
import com.lvmama.comm.vo.train.product.StationInfo;
import com.lvmama.train.PinyinUtil;

@Results({
	@Result(name="input",location="/WEB-INF/train/form.jsp")
})
public class ProductImportAction extends BaseAction{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8452168954L;
	private TrainDataSyncService trainDataSyncService;
	private static final String stationFileName = "station";
	private static final String cityStationFileName = "city_station";
	private static final String lineFileName = "train_2";
	private static final String lineStopsFileName = "train_station";
	private static final int submitNum = 500;
	private ProdTrainCacheService prodTrainCacheService;
	private static final Log logger = LogFactory.getLog(ProductImportAction.class);
	private ProdTrainService prodTrainService;
	private static String url = "/WEB-INF/resources/train_dir/";
	private String content;
	private String type;
	private String date;
	
	@Action(value="/train/imp")
	public void importTrainInfo(){
		logger.info("Train data import begin..");
		File _d=null;
		if(url.startsWith("d:/")){
			_d = new File(url);
		}else{
			_d = ResourceUtil.getResourceFile(url);
		}
		if(StringUtils.isEmpty(fileName)){
			if(_d.isDirectory()){
				File[] fileList = _d.listFiles();
				for(File file : fileList){
					imp(file);
				}
				logger.info("Import data finished. End Time :" + System.currentTimeMillis());
			}
		}else{
			File file = new File(_d,fileName);
			if(!file.exists()){
				logger.info("file don't exists:"+fileName);
				return;
			}
			imp(file);
		}
	}
	
	private void imp(File file){
		if(file.getName().startsWith(stationFileName) && "1".equals(type)){
			logger.info("Train info import begin. File name : " + file.getName() + ".Begin Time :" + System.currentTimeMillis());
			importStation(file);
		}else if(file.getName().startsWith(cityStationFileName) && "2".equals(type)){
			logger.info("Train info import begin. File name : " + file.getName() + ".Begin Time :" + System.currentTimeMillis());
			importCityStation(file);
		}else if(file.getName().startsWith(lineFileName) && "3".equals(type)){
			logger.info("Train info import begin. File name : " + file.getName() + ".Begin Time :" + System.currentTimeMillis());
			importLine(file);
		}else if(file.getName().startsWith(lineStopsFileName) && "4".equals(type)){
			logger.info("Train info import begin. File name : " + file.getName() + ".Begin Time :" + System.currentTimeMillis());
			importLineStops(file);
		}
	}
	
	@Action("/train/queryImpCount")
	public void queryCount(){
		JSONResult result = new JSONResult();
		Date dt = DateUtil.toDate(date, "yyyy-MM-dd");
		if(dt!=null){
			long count = prodTrainCacheService.queryCount(dt);
			result.put("date", DateUtil.formatDate(dt, "yyyy-MM-dd"));
			result.put("count", count);
		}
		result.output(getResponse());
	}
	
	@Action("/train/imp_clear")
	public void clearCache(){
		prodTrainService.initTrainCacheMap(StringUtils.equals(content, "true"));
		logger.info("delete cache------------------------------------------");
	}

	public void importStation(File file){
		FileReader fr = null;
		BufferedReader br = null;
		try {
			fr = new FileReader(file);
			br = new BufferedReader(fr);
			String input;
			List<StationInfo> stationInfos = new ArrayList<StationInfo>();
			while((input = br.readLine()) != null){
				logger.info(input);
				if(stationInfos.size() == submitNum){
					boolean isSuccess = trainDataSyncService.insertStationInfos(stationInfos);
					stationInfos.clear();
					if(!isSuccess){
						logger.info("车站数据批次插入失败");
					}
				}
				String[] ss = input.split("\t");
				if(ss.length != 3) throw new Exception("文件出错，请确认需要导入的车站信息文件是否正确");
				String stationName = ss[0].trim();
				String pinyin = ss[1].trim();
				String py = ss[2].trim();
				StationInfo stationInfo = new StationInfo();
				stationInfo.setStation_name(stationName);
				stationInfo.setStation_pinyin(pinyin);
				stationInfo.setStation_index(py);
				stationInfo.setStatus(1);
				stationInfos.add(stationInfo);
			}
			if(stationInfos.size() > 0){
				boolean isSuccess = trainDataSyncService.insertStationInfos(stationInfos);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(fr != null){
				try {
					fr.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(br != null){
				try {
					br.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	public void importCityStation(File file){
		FileReader fr = null;
		BufferedReader br = null;
		try {
			fr = new FileReader(file);
			br = new BufferedReader(fr);
			String input;
			Map<String, CityStationInfo> map = new HashMap<String, CityStationInfo>();
			while((input = br.readLine()) != null){
				logger.info(input);
				String[] ss = input.split("\t");
				if(ss.length != 2) throw new Exception("文件出错，请确认需要导入的城市车站信息文件是否正确");
				String cityName = ss[0].trim();
				String stationName = ss[1].trim();
				CityStationInfo cityStationInfo = map.get(cityName);
				if(cityStationInfo == null){
					cityStationInfo = new CityStationInfo();
					cityStationInfo.setCity_name(cityName);
					cityStationInfo.setCity_pinyin(PinyinUtil.getMixPinyin(cityName));
					cityStationInfo.setStatus(1);
					cityStationInfo.setStation_list(new ArrayList<String>());
					cityStationInfo.getStation_list().add(stationName);
					map.put(cityName, cityStationInfo);
				}else{
					if(cityStationInfo.getStation_list() == null){
						cityStationInfo.setStation_list(new ArrayList<String>());
					}
					cityStationInfo.getStation_list().add(stationName);
				}
			}
			List<CityStationInfo> cityStationInfos = new ArrayList<CityStationInfo>();
			for(Map.Entry<String, CityStationInfo> entry : map.entrySet()){
				if(cityStationInfos.size() == submitNum){
					boolean isSuccess = trainDataSyncService.insertCityStationInfos(cityStationInfos);
					cityStationInfos.clear();
					if(!isSuccess){
						logger.info("城市车站数据批次插入失败");
					}
				}
				CityStationInfo csi = entry.getValue();
				cityStationInfos.add(csi);
			}
			if(cityStationInfos.size() > 0){
				boolean isSuccess = trainDataSyncService.insertCityStationInfos(cityStationInfos);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(fr != null){
				try {
					fr.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(br != null){
				try {
					br.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	public void importLine(File file){
		FileReader fr = null;
		BufferedReader br = null;
		try {
			fr = new FileReader(file);
			br = new BufferedReader(fr);
			String input;
			List<LineInfo> lineInfos = new ArrayList<LineInfo>();
			while((input = br.readLine()) != null){
				logger.info(input);
				if(lineInfos.size() == submitNum){
					boolean isSuccess = trainDataSyncService.insertLineInfos(lineInfos, null);
					lineInfos.clear();
					if(!isSuccess){
						logger.info("车次数据批次插入失败");
					}
				}
				String[] ss = input.split("\t");
				if(ss.length != 8) throw new Exception("文件出错，请确认需要导入的车次信息文件是否正确");
				String trainId = ss[0].trim();
				String originStation = ss[1].trim();
				String terminalStation = ss[2].trim();
				String trainType = ss[3].trim();
				String departTime = ss[4].trim();
				String arriveTime = ss[5].trim();
				String costTime = ss[6].trim();
				String parkStation = ss[7].trim();
				
				LineInfo lineInfo = new LineInfo();
				lineInfo.setTrain_id(trainId);
				lineInfo.setOrigin_station(originStation);
				lineInfo.setTerminal_station(terminalStation);
				lineInfo.setTrain_type(Integer.parseInt(trainType));
				lineInfo.setDepart_time(departTime);
				lineInfo.setArrive_time(arriveTime);
				lineInfo.setCost_time(costTime);
				lineInfo.setPark_station(Integer.parseInt(parkStation));
				lineInfo.setStatus(1);
				lineInfos.add(lineInfo);
			}
			if(lineInfos.size() > 0){
				boolean isSuccess = trainDataSyncService.insertLineInfos(lineInfos, null);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(fr != null){
				try {
					fr.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(br != null){
				try {
					br.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	public void importLineStops(File file){
		String fileName = file.getName();
		String date = fileName.substring(fileName.lastIndexOf("_")  + 1, fileName.indexOf("."));
		String requestDate = date.substring(0, 4) + "-" + date.substring(4, 6) + "-" + date.substring(6);
		FileReader fr = null;
		BufferedReader br = null;
		try {
			fr = new FileReader(file);
			br = new BufferedReader(fr);
			String input;
			Map<String, LineStopsInfo> map = new HashMap<String, LineStopsInfo>();
			while((input = br.readLine()) != null){
				logger.info(input);
				String[] ss = input.split("\t");
				if(ss.length != 8) throw new Exception("文件出错，请确认需要导入的车次经停信息文件是否正确");
				String trainId = ss[0].trim();
				String stationNo = ss[1].trim();
				String stationName = ss[2].trim();
				String arriveTime = ss[3].trim();
				String departTime = ss[4].trim();
				String costTime = ss[5].trim();
				String restTime = ss[6].trim();
				String status = ss [7].trim();
				
				LineStopsInfo lineStopsInfo = map.get(trainId);
				if(lineStopsInfo == null){
					lineStopsInfo = new LineStopsInfo();
					lineStopsInfo.setTrain_id(trainId);
					if("1".equals(status)){
						lineStopsInfo.setStatus(Integer.parseInt(status));
					}
					lineStopsInfo.setPark_station(new ArrayList<LineStopsStationInfo>());
					map.put(trainId, lineStopsInfo);
				}else{
					if(lineStopsInfo.getPark_station() == null){
						lineStopsInfo.setPark_station(new ArrayList<LineStopsStationInfo>());
					}
				}
				LineStopsStationInfo station = new LineStopsStationInfo();
				station.setStation_no(Integer.parseInt(stationNo));
				station.setStation_name(stationName);
				station.setArrive_time(arriveTime);
				station.setDepart_time(departTime);
				station.setCost_time(costTime);
				station.setRest_time(restTime);
				
				lineStopsInfo.getPark_station().add(station);
			}
			List<LineStopsInfo> lineStopsInfos = new ArrayList<LineStopsInfo>();
			for(Map.Entry<String, LineStopsInfo> entry : map.entrySet()){
				if(lineStopsInfos.size() == submitNum){
					boolean isSuccess = trainDataSyncService.insertLineStopInfos(lineStopsInfos, requestDate);
					lineStopsInfos.clear();
					if(!isSuccess){
						logger.info("车次经停数据批次插入失败");
					}
				}
				LineStopsInfo lsi = entry.getValue();
				lineStopsInfos.add(lsi);
			}
			if(lineStopsInfos.size() > 0){
				boolean isSuccess = trainDataSyncService.insertLineStopInfos(lineStopsInfos, requestDate);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(fr != null){
				try {
					fr.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(br != null){
				try {
					br.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	@Action(value="/train/impTicketPrice")
	public void importTicketPrice(){
		String date = fileName.substring(fileName.lastIndexOf("_")  + 1, fileName.indexOf("."));
		String requestDate = date.substring(0, 4) + "-" + date.substring(4, 6) + "-" + date.substring(6);
		File file=null;
		if(url.startsWith("d:")){
			file = new File(url,fileName);
		}else{
		 file = ResourceUtil.getResourceFile(url+fileName);
		}
		if(!file.exists()) 
			logger.info("导入文件不存在，请确认导入地址");
		FileReader fr = null;
		BufferedReader br = null;
		try {
			fr = new FileReader(file);
			br = new BufferedReader(fr);
			String input;
			List<Station2StationInfo> station2StationInfos = new ArrayList<Station2StationInfo>();
			int pos=0;
//			int count=0;
			while((input = br.readLine()) != null){
				//logger.info(input);
				if(pos++<start){
					continue;
				}
				if(end>0){
					if(pos>=end){
						break;
					}
				}
				if(station2StationInfos.size() == submitNum){
//					count++;
					System.out.println("begin insertTicketPriceInfos");
					Date date1 = new Date();
					boolean isSuccess = trainDataSyncService.insertTicketPriceInfos(station2StationInfos, requestDate);
					System.out.println("begin insertTicketPriceInfos:"+(new Date().getTime()-date1.getTime()));
					station2StationInfos.clear();
					if(!isSuccess){
						logger.info("车票价格数据批次插入失败");
					}
//					if(count>=2){
//						break;
//					}
				}
				String[] ss = input.split("\t");
				if(ss.length != 20) throw new Exception("文件出错，请确认需要导入的车票价格信息文件是否正确");
				String trainId = ss[0].trim();
				String departStation = ss[1].trim();
				String arriveStation = ss[2].trim();
				String departTime = ss[3].trim();
				String arriveTime = ss[4].trim();
				String costTime = ss[5].trim();
				String seatBusi = ss[6].trim();
				String seatClass0 = ss[7].trim();
				String seatClass1 = ss[8].trim();
				String seatClass2 = ss[9].trim();
				String bedSeniorTop = ss[10].trim();
				String bedSeniorBot = ss[11].trim();
				String bedSoftTop = ss[12].trim();
				String bedSoftBot = ss[13].trim();
				String bedHardTop = ss[14].trim();
				String bedHardMid = ss[15].trim();
				String bedHardBot = ss[16].trim();
				String seatSoft = ss[17].trim();
				String seatHard = ss[18].trim();
				String seatNone = ss[19].trim();
				
				Station2StationInfo ssi = new Station2StationInfo();
				ssi.setTrain_id(trainId);ssi.setDepart_station(departStation);ssi.setArrive_station(arriveStation);
				ssi.setDepart_date(requestDate);ssi.setDepart_time(departTime);ssi.setArrive_time(arriveTime);
				ssi.setCost_time(costTime);ssi.setStatus(1);
				ssi.setSeat_busi(Float.parseFloat(seatBusi));
				ssi.setSeat_class0(Float.parseFloat(seatClass0));
				ssi.setSeat_class1(Float.parseFloat(seatClass1));
				ssi.setSeat_class2(Float.parseFloat(seatClass2));
				ssi.setBed_senior_top(Float.parseFloat(bedSeniorTop));
				ssi.setBed_senior_bot(Float.parseFloat(bedSeniorBot));
				ssi.setBed_soft_top(Float.parseFloat(bedSoftTop));
				ssi.setBed_soft_bot(Float.parseFloat(bedSoftBot));
				ssi.setBed_hard_top(Float.parseFloat(bedHardTop));
				ssi.setBed_hard_mid(Float.parseFloat(bedHardMid));
				ssi.setBed_hard_bot(Float.parseFloat(bedHardBot));
				ssi.setSeat_soft(Float.parseFloat(seatSoft));
				ssi.setSeat_hard(Float.parseFloat(seatHard));
				ssi.setSeat_none(Float.parseFloat(seatNone));
				if(StringUtils.equals(type, "update")){
					ssi.setStatus(2);
				}
				station2StationInfos.add(ssi);
			}
			if(station2StationInfos.size() > 0){
				System.out.println("begin insertTicketPriceInfos");
				Date date1 = new Date();
				boolean isSuccess = trainDataSyncService.insertTicketPriceInfos(station2StationInfos, requestDate);
				System.out.println("begin insertTicketPriceInfos:"+(new Date().getTime()-date1.getTime()));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(fr != null){
				try {
					fr.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(br != null){
				try {
					br.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	@Action("/train/imp_go")
	public String goInput(){
		return "input";
	}
	@Action("/train/imp_submit")
	public void submit(){
		try{
			String ff=null;
			if("1".equals(type)){
				ff=stationFileName;
			}else if("2".equals(type)){
				ff=cityStationFileName;
			}else if("3".equals(type)){
				ff=lineFileName;
			}else if("4".equals(type)){
				ff=lineStopsFileName;
			}else if("5".equals(type)){
				ff = "train_ss";
			}else{
				throw new Exception("类型错误，不可以添加");
			}
			if(StringUtils.isNotEmpty(date)){
				ff+="_"+date;
			}
			File file = new File("/tmp/"+ff+".txt");
			FileWriter fw = new FileWriter(file);
			fw.write(content);
			fw.close();
			if("5".equals(type)){
				importTicketPrice();
			}else{
				imp(file);
			}
			file.delete();
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	@Action("/train/queryTrainCache")
	public void queryTrainCache(){
		JSONResult result = new JSONResult();
		Date dt = DateUtil.toDate(date, "yyyy-MM-dd");
		ProdTrainCache cache = prodTrainCacheService.queryLastCacheByDate(dt);
		if(cache!=null){
		result.put("lineName", cache.getLineName());
		result.put("productName", cache.getProductName());
		}else{
			result.put("mm","没找到");
		}
		result.output(getResponse());
	}
	@Action("/train/copyTrainCache")
	public void copyTrain(){
		prodTrainCacheService.copyDataToNewDay(DateUtil.toDate(date,"yyyy-MM-dd"));
	}
	
	
	public TrainDataSyncService getTrainDataSyncService() {
		return trainDataSyncService;
	}
	public void setTrainDataSyncService(TrainDataSyncService trainDataSyncService) {
		this.trainDataSyncService = trainDataSyncService;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	public static void main(String[] args) throws Exception{
		ApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"applicationContext-passport-beans.xml"});
		
		ProductImportAction pia = new ProductImportAction();
		pia.setTrainDataSyncService(context.getBean("trainDataSyncService",TrainDataSyncService.class));
//		File file = new File("d:/out/train_data_20130916/train_xxx.txt");
		pia.setFileName("ticket_price_20130930.txt");
		pia.url="d:/out/train_data_20130916";
		pia.importTicketPrice();
//		pia.importLine(file);
		
//		File file = new File("d:/out/train_data_20130916/train_station_20130909.txt");
//		pia.importLineStops(file);
		
//		pia.import
	}
	private int start=0;
	private int end=-1;
	private String fileName;

	public void setStart(int start) {
		this.start = start;
	}

	public void setProdTrainCacheService(ProdTrainCacheService prodTrainCacheService) {
		this.prodTrainCacheService = prodTrainCacheService;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void setProdTrainService(ProdTrainService prodTrainService) {
		this.prodTrainService = prodTrainService;
	}

	public void setEnd(int end) {
		this.end = end;
	}
}
