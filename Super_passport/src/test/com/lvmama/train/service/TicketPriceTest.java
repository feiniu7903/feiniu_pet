package com.lvmama.train.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.lvmama.comm.bee.service.TrainDataSyncService;
import com.lvmama.comm.spring.SpringBeanProxy;
import com.lvmama.comm.utils.ResourceUtil;
import com.lvmama.comm.vo.train.product.CityStationInfo;
import com.lvmama.comm.vo.train.product.LineInfo;
import com.lvmama.comm.vo.train.product.LineStopsInfo;
import com.lvmama.comm.vo.train.product.LineStopsStationInfo;
import com.lvmama.comm.vo.train.product.Station2StationInfo;
import com.lvmama.comm.vo.train.product.StationInfo;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:applicationContext-passport-beans.xml" })
public class TicketPriceTest implements ApplicationContextAware{
	@Autowired
	private TrainDataSyncService trainDataSyncService;
	
	@Test
	public void testImportTicketPrice(){
		File file = new File("D:/train/ticket_price_20130912.txt");
		importTicketPrice(file);
	}
	
	private void importTicketPrice(File file) {
		String fileName = file.getName();
		String date = fileName.substring(fileName.lastIndexOf("_")  + 1, fileName.indexOf("."));
		String requestDate = date.substring(0, 4) + "-" + date.substring(4, 6) + "-" + date.substring(6);
		FileReader fr = null;
		BufferedReader br = null;
		try {
			fr = new FileReader(file);
			br = new BufferedReader(fr);
			String input;
			List<Station2StationInfo> station2StationInfos = new ArrayList<Station2StationInfo>();
			while((input = br.readLine()) != null){
				if(station2StationInfos.size() == 100){
					boolean isSuccess = trainDataSyncService.insertTicketPriceInfos(station2StationInfos, requestDate);
					station2StationInfos.clear();
					if(!isSuccess){
//						logger.info("车票价格数据批次插入失败");
					}
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
				station2StationInfos.add(ssi);
			}
			if(station2StationInfos.size() > 0){
				boolean isSuccess = trainDataSyncService.insertTicketPriceInfos(station2StationInfos, requestDate);
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

	@Test
	public void testImporLineStops(){
		File file = new File("D:/train/train_station_20130912.txt");
		importLineStops(file);
	}
	
	private void importLineStops(File file){
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
				if(lineStopsInfos.size() == 100){
					boolean isSuccess = trainDataSyncService.insertLineStopInfos(lineStopsInfos, requestDate);
					lineStopsInfos.clear();
					if(!isSuccess){
//						logger.info("车次经停数据批次插入失败");
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
	
	@Test
	public void testImporLine(){
		File file = new File("D:/train/train.txt");
		importLine(file);
	}
	
	private void importLine(File file) {
		FileReader fr = null;
		BufferedReader br = null;
		try {
			fr = new FileReader(file);
			br = new BufferedReader(fr);
			String input;
			List<LineInfo> lineInfos = new ArrayList<LineInfo>();
			while((input = br.readLine()) != null){
				System.out.println(input.getBytes());
				byte[] buf = input.getBytes();
				for(byte b:buf){
					System.out.print(b);
					System.out.print('\t');
				}
				if(lineInfos.size() == 100){
					boolean isSuccess = trainDataSyncService.insertLineInfos(lineInfos, null);
					lineInfos.clear();
					if(!isSuccess){
//						logger.info("车次数据批次插入失败");
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

	@Test
	public void testImportStation(){
		File _d = ResourceUtil.getResourceFile("/WEB-INF/resources");
		if(_d.isDirectory()){
			File[] fileList = _d.listFiles();
			for(File file : fileList){
				if(file.getName().startsWith("station")){
					importStation(file);
				}
			}
		}
	}
	private void importStation(File file){
		FileReader fr = null;
		BufferedReader br = null;
		try {
			fr = new FileReader(file);
			br = new BufferedReader(fr);
			String input;
			List<StationInfo> stationInfos = new ArrayList<StationInfo>();
			while((input = br.readLine()) != null){
				if(stationInfos.size() == 100){
					boolean isSuccess = trainDataSyncService.insertStationInfos(stationInfos);
					stationInfos.clear();
					if(!isSuccess){
//						logger.info("车站数据批次插入失败");
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
	
	@Test
	public void testImportCityStation(){
		File file = new File("D:/train/city_station.txt");
		importCityStation(file);
	}
	private void importCityStation(File file){
		FileReader fr = null;
		BufferedReader br = null;
		try {
			fr = new FileReader(file);
			br = new BufferedReader(fr);
			String input;
			Map<String, CityStationInfo> map = new HashMap<String, CityStationInfo>();
			while((input = br.readLine()) != null){
				String[] ss = input.split("\t");
				if(ss.length != 2) throw new Exception("文件出错，请确认需要导入的城市车站信息文件是否正确");
				String cityName = ss[0].trim();
				String stationName = ss[1].trim();
				CityStationInfo cityStationInfo = map.get(cityName);
				if(cityStationInfo == null){
					cityStationInfo = new CityStationInfo();
					cityStationInfo.setCity_name(cityName);
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
				if(cityStationInfos.size() == 100){
					boolean isSuccess = trainDataSyncService.insertCityStationInfos(cityStationInfos);
					cityStationInfos.clear();
					if(!isSuccess){
//						logger.info("城市车站数据批次插入失败");
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
	@Override
	public void setApplicationContext(ApplicationContext arg0)
			throws BeansException {
		SpringBeanProxy.setApplicationContext(arg0);
	}
}
