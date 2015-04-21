package com.lvmama.search.lucene.service.autocomplete;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lvmama.comm.search.SearchConstants;
import com.lvmama.comm.search.vo.AutoCompletePlaceDto;
import com.lvmama.comm.search.vo.AutoCompletePlaceHotel;
import com.lvmama.comm.search.vo.AutoCompletePlaceObject;
import com.lvmama.comm.search.vo.AutoCompleteVerHotel;
import com.lvmama.comm.search.vo.AutoCompleteVerHotelCity;
import com.lvmama.search.lucene.dao.impl.AutoCompleteOneDao;
import com.lvmama.search.lucene.dao.impl.AutoCompleteVerDao;
import com.lvmama.search.util.LocalCacheManager;

/**
 * 自动补全查询
 * 
 * @author huangzhi
 * 
 */
@Service("autoCompleteOneService")
public class AutoCompleteOneServiceImpl implements AutoCompleteOneService {
	private Log loger = LogFactory.getLog(getClass());
	@Autowired
	private AutoCompleteOneDao autoCompleteOneDao;
	@Autowired
	private AutoCompleteVerDao autoCompleteVerDao;
	public static final int DEFAULT_MEMCACHE_VALID_TIME = 60*60*2;
	/**提高下拉补全中某中文，拼音，标签...等某一类属性的权重**/
	private Long boost = -1000l;
	
	public List<AutoCompletePlaceDto> getAutoCompletePlaceDtoList(int channelType, Long fromPlaceId) {
		List<AutoCompletePlaceDto> result = new ArrayList<AutoCompletePlaceDto>();
		switch (channelType) {
		case SearchConstants.ALL_CHANNEL_TYPE:
			result = autoCompleteOneDao.getOneSearchAutoCompletePlace(fromPlaceId);
			break;
		case SearchConstants.TICKET_CHANNEL_TYPE:
			result = autoCompleteOneDao.getTicketAutoCompletePlace();
			break;
		case SearchConstants.FREETOUR_CHANNEL_TYPE:
			result = autoCompleteOneDao.getFreetourAutoCompletePlace();
			break;
		case SearchConstants.FREELONG_CHANNEL_TYPE:
			result = autoCompleteOneDao.getFreeLongAutoCompletePlace(fromPlaceId);
			addDepartAreaDest(result);
			break;
		case SearchConstants.GROUP_CHANNEL_TYPE:
			result = autoCompleteOneDao.getGroupAutoCompletePlace(fromPlaceId);
			break;
		case SearchConstants.HOTEL_MERGE_COMPLETE:
			result = autoCompleteOneDao.getHotelAutoComplete();
			break;
		case SearchConstants.DEST_AUTO_COMPLETE:
			result = autoCompleteOneDao.getDestAutoCompletePlace();
			break;
		default:
			break;
		}
		return result;
	}
	/**
	 * 添加处境频道自动补全所用的出境区域
	 * 
	 * @param result
	 */
	private void addDepartAreaDest(List<AutoCompletePlaceDto> result) {
		AutoCompletePlaceDto nanya = new AutoCompletePlaceDto();
		nanya.setName("南亚~nanya~ny");
		nanya.setStage("FREELONG");
		result.add(nanya);
		AutoCompletePlaceDto ouzhou = new AutoCompletePlaceDto();
		ouzhou.setName("欧洲~ouzhou~oz");
		ouzhou.setStage("FREELONG");
		result.add(ouzhou);
		AutoCompletePlaceDto aoxin = new AutoCompletePlaceDto();
		aoxin.setName("澳新~aoxin~ax");
		aoxin.setStage("FREELONG");
		result.add(aoxin);
		AutoCompletePlaceDto meizhou = new AutoCompletePlaceDto();
		meizhou.setName("美洲~meizhou~mz");
		meizhou.setStage("FREELONG");
		result.add(meizhou);
		AutoCompletePlaceDto rihan = new AutoCompletePlaceDto();
		rihan.setName("日韩~rihan~rh");
		rihan.setStage("FREELONG");
		result.add(rihan);
		AutoCompletePlaceDto dayangzhou = new AutoCompletePlaceDto();
		dayangzhou.setName("大洋洲~dayangzhou~dyz");
		dayangzhou.setStage("FREELONG");
		result.add(dayangzhou);
		AutoCompletePlaceDto dongnanaya = new AutoCompletePlaceDto();
		dongnanaya.setName("东南亚~dongnanya~dny");
		dongnanaya.setStage("FREELONG");
		result.add(dongnanaya);
		AutoCompletePlaceDto zhongdongfei = new AutoCompletePlaceDto();
		zhongdongfei.setName("中东非~zhongdongfei~zdf");
		zhongdongfei.setStage("FREELONG");
		result.add(zhongdongfei);
		AutoCompletePlaceDto gangaodiqu = new AutoCompletePlaceDto();
		gangaodiqu.setName("港澳地区~gangaodiqu~gadq");
		gangaodiqu.setStage("FREELONG");
		result.add(gangaodiqu);
		AutoCompletePlaceDto yazhou = new AutoCompletePlaceDto();	
		yazhou.setName("亚洲~yazhou~yz");
		yazhou.setStage("FREELONG");
		result.add(yazhou);		
	}

	public List<AutoCompletePlaceObject> getAutoCompletePlacePlaceObjectListMatched(int channelType, Long fromPlaceId, String keyword){
		return getAutoCompletePlacePlaceObjectListMatched(channelType, fromPlaceId, keyword,null,true);
	}
	@Override
	public List<AutoCompletePlaceObject> getAutoCompletePlacePlaceObjectListMatched(int channelType, Long fromPlaceId, String keyword , Integer topNum) {
		return getAutoCompletePlacePlaceObjectListMatched(channelType, fromPlaceId, keyword, topNum, true);
	}
	@SuppressWarnings("unchecked")
	public List<AutoCompletePlaceObject> getAutoCompletePlacePlaceObjectListMatched(int channelType, Long fromPlaceId, String keyword , Integer topNum,boolean merge) {
		String key = "getAutoCompletePlaceObjectList_" + channelType + "_" + fromPlaceId;
		List<AutoCompletePlaceDto> getAutoCompletePlaceDtoList  = new ArrayList<AutoCompletePlaceDto>();
		List<AutoCompletePlaceObject> autoCompleteObjectList = new ArrayList();
		autoCompleteObjectList = (List<AutoCompletePlaceObject>) LocalCacheManager.get(key);			
		if (autoCompleteObjectList == null || autoCompleteObjectList.size() < 1) {
			getAutoCompletePlaceDtoList = getAutoCompletePlaceDtoList(channelType, fromPlaceId);
			
			if(channelType == SearchConstants.HOTEL_MERGE_COMPLETE){
				autoCompleteObjectList = convertAutoCompletePlaceDtoToAutoCompleteHotel(getAutoCompletePlaceDtoList);
			}else{
				autoCompleteObjectList = convertAutoCompletePlaceDtoToAutoCompleteObject(getAutoCompletePlaceDtoList);
			}
			if(autoCompleteObjectList != null){
				LocalCacheManager.put(key, autoCompleteObjectList, 2*60*60*1000);
				loger.debug("存入缓存成功=== channel : " + channelType + "total count: " + autoCompleteObjectList.size());
			}
		}
		
		
		loger.debug("=== channel : " + channelType + " total count: " + autoCompleteObjectList.size());
		AutoCompletePlaceObject.comparatorMatchSEQ matchMachine = new AutoCompletePlaceObject.comparatorMatchSEQ();
		HashMap<String, AutoCompletePlaceObject> distinctMap = new HashMap<String, AutoCompletePlaceObject>();
		keyword = keyword.toLowerCase();
		for (AutoCompletePlaceObject item: autoCompleteObjectList) {
			String matchWrod = item.getMatchword().toLowerCase();
			Long Seq = item.getSeq();
			String words = item.getWords();
			if (!"中国".equals(words) && !"亚洲".equals(words)) {
				// 前缀匹配	
				if (matchWrod.indexOf(keyword) >= 0) {
						/**
						 * 按拼音SEQ的规则设置排序SEQ:
						 * 拼音匹配位置_拼音长度_SEQ,考虑到SEQ为[-10000,20000]之间, 而且是负值排在前面,所以
						 * 用一个参数100000去加SEQ 的值作为排序数[110000 , 80000
						 * ]，则越大的越排前,为保持字符串比较，则开平方保证值为3位数
						 ***/
						//String matchSEQ = Math.sqrt(matchWrod.indexOf(keyword)) + "_" + Math.sqrt(matchWrod.length()) + "_" + Math.sqrt((100000 + Seq));
						String matchSEQ = Math.sqrt(matchWrod.indexOf(keyword)) + "_" + Math.sqrt(matchWrod.length())+"_"+ Math.sqrt((100000 + Seq));
						item.setMatchSEQ(matchSEQ);
						AutoCompletePlaceObject obj = distinctMap.get(words);
						if (obj==null || !merge) {
							distinctMap.put(words, item);
						}else{
							String stage = obj.getStage();
							if (stage.indexOf(item.getStage())<0) {
								  stage = item.getStage() + "+" + stage;
								  obj.setStage(stage);			
							}
							if (matchMachine.compare(obj, item)>0) {//取更大值
								obj.setMatchSEQ(item.getMatchSEQ());
							}
							distinctMap.put(words, obj);			
						}		
				}
			}
		}		
		List<AutoCompletePlaceObject> allChannelList  = new ArrayList<AutoCompletePlaceObject>(distinctMap.values());
	 	Collections.sort(allChannelList, new AutoCompletePlaceObject.comparatorMatchSEQ());
	 	if(topNum != null ){
	 		topNum = allChannelList.size()>=topNum ? topNum : allChannelList.size();
	 		return allChannelList.subList(0, topNum);
	 	}
		return allChannelList;
	}
	
	
	@SuppressWarnings("unchecked")
	public List<AutoCompleteVerHotel> getAutoCompleteVerHotelListMatched(int channelType, Long fromPlaceId, String keyword , Integer topNum,boolean merge) {
		String key = "getAutoCompleteVerHotelList";
		List<AutoCompleteVerHotel> autoCompleteVerHotelList = new ArrayList();
		autoCompleteVerHotelList = (List<AutoCompleteVerHotel>) LocalCacheManager.get(key);	
		if (autoCompleteVerHotelList == null || autoCompleteVerHotelList.size() < 1) {
				autoCompleteVerHotelList=autoCompleteVerDao.getAutoCompleteVerHotel();
				LocalCacheManager.put(key, autoCompleteVerHotelList, 2*60*60*1000);
				loger.debug("存入缓存成功=== channel : verhotel total count: " + autoCompleteVerHotelList.size());
		}
		
		
		loger.debug("=== channel : verhotel total count:" + autoCompleteVerHotelList.size());
		LinkedHashMap<String, AutoCompleteVerHotel> distinctMap = new LinkedHashMap<String, AutoCompleteVerHotel>();
		keyword = keyword.toLowerCase();
		int i=0;
		for (AutoCompleteVerHotel item: autoCompleteVerHotelList) {
			String matchWrod = item.getMatchword().toLowerCase();
			String autocompleteName = item.getAutocompleteName();
			String parentName=item.getParentName();
				// 前缀匹配	
				if (matchWrod.indexOf(keyword) >= 0) {
					i++;
					item.setOrder(i);
					distinctMap.put(autocompleteName, item);
				}
			}
		List<AutoCompleteVerHotel> allChannelList  = new ArrayList<AutoCompleteVerHotel>(distinctMap.values());
		return allChannelList;
	}
	
	public List<AutoCompleteVerHotelCity> getAutoCompleteVerHotelCitiesMatched(int channelType, Long fromPlaceId, String keyword , Integer topNum,boolean merge) {
		String key = "getAutoCompleteVerHotelCities";
		List<AutoCompleteVerHotelCity> autoCompleteVerHotelList = new ArrayList<AutoCompleteVerHotelCity>();
		autoCompleteVerHotelList = (List<AutoCompleteVerHotelCity>) LocalCacheManager.get(key);	
		if (autoCompleteVerHotelList == null || autoCompleteVerHotelList.size() < 1) {
				autoCompleteVerHotelList=autoCompleteVerDao.getAutoCompleteVerHotelCities();
				for (int i = 0; i < autoCompleteVerHotelList.size(); i++) {
					if(isHotCity(autoCompleteVerHotelList.get(i).getAutocompleteName())){
						autoCompleteVerHotelList.get(i).setHot(true);
					}
				}
				LocalCacheManager.put(key, autoCompleteVerHotelList, 2*60*60*1000);
				loger.debug("存入缓存成功=== channel : verhotelcities total count: " + autoCompleteVerHotelList.size());
		}
		
		
		loger.debug("=== channel : verhotelcities total count:" + autoCompleteVerHotelList.size());
		LinkedHashMap<String, AutoCompleteVerHotelCity> distinctMap = new LinkedHashMap<String, AutoCompleteVerHotelCity>();
		keyword = keyword.toLowerCase();
		int i=0;
		for (AutoCompleteVerHotelCity item: autoCompleteVerHotelList) {
			String matchWrod = item.getMatchword().toLowerCase();
			String autocompleteName = item.getAutocompleteName();
			String parentName=item.getParentName();
				// 前缀匹配	
				if (matchWrod.indexOf(keyword) >= 0) {
					i++;
					item.setOrder(i);
					distinctMap.put(autocompleteName, item);
				}
			}
		List<AutoCompleteVerHotelCity> allChannelList  = new ArrayList<AutoCompleteVerHotelCity>(distinctMap.values());
		return allChannelList;
	}
	
	// 空匹配，直接打印前TOPnum个SEQ最大的结果
	public List<AutoCompletePlaceObject> getAutoCompletePlacePlaceObjectListDefault(int channelType, Long fromPlaceId, int topNum) {
		String key = "getAutoCompletePlaceObjectListDefault_" + channelType + "_" + fromPlaceId;
		List<AutoCompletePlaceObject> autoCompleteObjectList = (List<AutoCompletePlaceObject>) LocalCacheManager.get(key);			
		// 存入缓存
		if (autoCompleteObjectList == null || autoCompleteObjectList.size() < 1) {
			//根据类型查询补全基础数据
			List<AutoCompletePlaceDto> getAutoCompletePlaceDtoList = getAutoCompletePlaceDtoList(channelType, fromPlaceId);
			//转换AutoCompletePlaceDto为AutoCompleteObject
			autoCompleteObjectList = convertAutoCompletePlaceDtoToAutoCompleteObject(getAutoCompletePlaceDtoList);
			ArrayList<AutoCompletePlaceObject> autoCompleteObjectListChecked = new ArrayList<AutoCompletePlaceObject>();
			HashMap<String, String> check = new HashMap<String, String>();
			loger.debug("=== 频道" + channelType + "总数 ：" + autoCompleteObjectList.size());
			for (int i = 0; i < autoCompleteObjectList.size(); i++) {
				String matchWrod = autoCompleteObjectList.get(i).getMatchword();
				String words = autoCompleteObjectList.get(i).getWords();
				Long Seq = autoCompleteObjectList.get(i).getSeq();
				if (check.get(matchWrod) == null || !check.get(matchWrod).equals(words)) {
					String matchSEQ = "_" + Math.sqrt((100000 + Seq));
					autoCompleteObjectList.get(i).setMatchSEQ(matchSEQ);
					autoCompleteObjectListChecked.add(autoCompleteObjectList.get(i));
					check.put(matchWrod, words);
				}

			}
			// 对匹配的结果排序
			Collections.sort(autoCompleteObjectListChecked, new AutoCompletePlaceObject.comparatorMatchSEQ());
			loger.debug("===匹配排序后总数：" + autoCompleteObjectListChecked.size());
			/*
			 * 二次排重：存在一个中文名称的简拼和全拼都能匹配关键字的情况,另外每个中文名称还有不同的SEQ值，需要取最小的SEQ(可能为负数).
			 * 遍历结果表，用一个HASHMAP存每个记录的键值对：中文名称：SEQ
			 * ,如果查到重复的中文名称，则把当前记录的SEQ和HASHmap中的SEQ比较，取较小的存入HASHmap,
			 */
			List<AutoCompletePlaceObject> autoCompleteObjectListCheckedAgain = new ArrayList<AutoCompletePlaceObject>();
			HashMap<String, String> check2 = new HashMap<String, String>();
			for (int i = 0; i < autoCompleteObjectListChecked.size(); i++) {
				String checkStr = autoCompleteObjectListChecked.get(i).getWords();
				if (check2.get(checkStr) == null) {// 新的数据,保留
					/* 去掉中国，亚洲等词 */
					if (!"中国".equals(checkStr) && !"亚洲".equals(checkStr)) {
						autoCompleteObjectListCheckedAgain.add(autoCompleteObjectListChecked.get(i));
						check2.put(checkStr, checkStr);

						if(autoCompleteObjectListCheckedAgain.size() > topNum) {
							break;
						}
					}
				}
			}
			loger.debug("=== 二次排重排序匹配后总数  ：" + autoCompleteObjectListCheckedAgain.size());
			autoCompleteObjectList = autoCompleteObjectListCheckedAgain;
			LocalCacheManager.put(key, autoCompleteObjectList, 2*60*60*1000);
			loger.debug("缓存成功！");
		}

		return autoCompleteObjectList;
	}

	private List<AutoCompletePlaceObject> convertAutoCompletePlaceDtoToAutoCompleteHotel(List<AutoCompletePlaceDto> autoCompletePlaceDtoList){
		List<AutoCompletePlaceObject> autoCompleteObjectList = new ArrayList<AutoCompletePlaceObject>();
		for (AutoCompletePlaceDto item : autoCompletePlaceDtoList) {
			Long seq = item.getSeq();
			String stage = item.getStage();
			String shortId = item.getShortId();
			String pinyin = item.getPinyin();
			String name = item.getName();
			Double longitude = item.getLongitude();
			Double latitude = item.getLatitude();
			
			autoCompleteObjectList.addAll(this.convertAutoCompleteHotel(name, stage, longitude, latitude , item.getDestPersentStr(), seq, item.getHfkw()));
			
			
			String tagsName = item.getTagsName();
			autoCompleteObjectList.addAll(this.convertAutoCompleteHotel(tagsName, "TAG", null, null , null, seq, null));
			
			String topic = item.getTopic();
			autoCompleteObjectList.addAll(this.convertAutoCompleteHotel(topic, "TOPIC", null, null , null, seq, null));
		}
		return autoCompleteObjectList;
	}
	// 把数据库中查询的结果 转换成匹配用的格式列表 : LIST[ 匹配字符_中文名称_排序串_SEQ] ,
	private ArrayList<AutoCompletePlaceObject> convertAutoCompletePlaceDtoToAutoCompleteObject(List<AutoCompletePlaceDto> autoCompletePlaceDtoList) {
		ArrayList<AutoCompletePlaceObject> autoCompleteObjectList = new ArrayList<AutoCompletePlaceObject>();
		for (int i = 0; i < autoCompletePlaceDtoList.size(); i++) {
			Long seq = autoCompletePlaceDtoList.get(i).getSeq();
			String stage = autoCompletePlaceDtoList.get(i).getStage();
			String shortId = autoCompletePlaceDtoList.get(i).getShortId();
			String pinyin = autoCompletePlaceDtoList.get(i).getPinyin();
			String name = autoCompletePlaceDtoList.get(i).getName();
			autoCompleteObjectList.addAll(ConvertAutoCompleteObject(name, stage, shortId, pinyin, Long.valueOf(seq+this.boost)));
			String hfkw = autoCompletePlaceDtoList.get(i).getHfkw();
			autoCompleteObjectList.addAll(ConvertAutoCompleteObject(hfkw, stage, shortId, pinyin, seq));
			String destTagsName = autoCompletePlaceDtoList.get(i).getDestTagsName();
			autoCompleteObjectList.addAll(ConvertAutoCompleteObject(destTagsName, stage, shortId, pinyin, seq));
			String destSubjects = autoCompletePlaceDtoList.get(i).getDestSubjects();
			autoCompleteObjectList.addAll(ConvertAutoCompleteObject(destSubjects, stage, shortId, pinyin, seq));
			String destPersentStr = autoCompletePlaceDtoList.get(i).getDestPersentStr();
			autoCompleteObjectList.addAll(ConvertAutoCompleteObject(destPersentStr, stage, shortId, pinyin, seq));
			String topic = autoCompletePlaceDtoList.get(i).getTopic();
			autoCompleteObjectList.addAll(ConvertAutoCompleteObject(topic, stage, shortId, pinyin, seq));
			String tagsName = autoCompletePlaceDtoList.get(i).getTagsName();
			autoCompleteObjectList.addAll(ConvertAutoCompleteObject(tagsName, stage, shortId, pinyin, seq));
			String productAlltoPlaceContent = autoCompletePlaceDtoList.get(i).getProductAlltoPlaceContent();
			autoCompleteObjectList.addAll(ConvertAutoCompleteObject(productAlltoPlaceContent, stage, shortId, pinyin, seq));
			String placeKeywordBind = autoCompletePlaceDtoList.get(i).getPlaceKeywordBind();
			autoCompleteObjectList.addAll(ConvertAutoCompleteObject(placeKeywordBind, stage, shortId, pinyin, seq));
			String departArea = autoCompletePlaceDtoList.get(i).getDepartArea();
			autoCompleteObjectList.addAll(ConvertAutoCompleteObject(departArea, stage, shortId, pinyin, seq));
		}
		/*ArrayList<AutoCompletePlaceObject> autoCompleteObjectListChecked = new ArrayList<AutoCompletePlaceObject>();
		// 排除重复的中文/拼音/简拼对象
		HashMap<String, String> check = new HashMap<String, String>();
		String checkStr;
		for (int k = 0; k < autoCompleteObjectList.size(); k++) {
			checkStr = autoCompleteObjectList.get(k).getWords() + autoCompleteObjectList.get(k).getMatchword() + autoCompleteObjectList.get(k).getSeq();
			if (check.get(checkStr) == null) {// 新的数据,保留
				autoCompleteObjectListChecked.add(autoCompleteObjectList.get(k));
				check.put(checkStr, checkStr);
			}
		}*/

		// 打印测试
		// ArrayList<AutoCompletePlaceObject> list = autoCompleteObjectList;
		// for (int i = 0; i < list.size(); i++) {
		// loger.debug(list.get(i).getMatchword() + "  :  " +
		// list.get(i).getWords() + "  : " + list.get(i).getSeq());
		// }

		return autoCompleteObjectList;
	}

	private  ArrayList<AutoCompletePlaceHotel> convertAutoCompleteHotel(String wordsPinyinString,String stage,Double longitude,Double latitude,String destParentStr,Long seq,String appendMatchwords){
		ArrayList<AutoCompletePlaceObject> list = this.ConvertAutoCompleteObject(wordsPinyinString, stage, "", "", seq);
		ArrayList<AutoCompletePlaceHotel> list_2 =new ArrayList<AutoCompletePlaceHotel>();
		for(AutoCompletePlaceObject item: list){
			AutoCompletePlaceHotel ph = new AutoCompletePlaceHotel();
			ph.setMatchSEQ(item.getMatchSEQ());
			appendMatchwords = appendMatchwords == null ? "" : appendMatchwords;
			ph.setMatchword(item.getMatchword()+appendMatchwords);
			ph.setPinyin(item.getPinyin());
			ph.setSavedStage(item.getSavedStage());
			ph.setSeq(item.getSeq());
			ph.setShortId(item.getShortId());
			ph.setStage(item.getStage());
			ph.setWords(item.getWords());
			ph.setLongitude(longitude);
			ph.setLatitude(latitude);
			ph.setDestParentStr(destParentStr);
			list_2.add(ph);
		}
		return list_2;
	}
	// 把一个: [中文~拼音~简拼,]xN 的字符串 转换成 : LIST[ 匹配字符_中文名称_排序串_SEQ]
	//格式升级：[中文A~拼音1、拼音2、; 中文1、中文2、中文3~ , 中文A~拼音1、拼音2、; 中文1、中文2、中文3~ ] 注意加了分号";"中文下的顿号"、"
	private ArrayList<AutoCompletePlaceObject> ConvertAutoCompleteObject(String wordsPinyinString, String stage, String shortId, String pinyin, Long seq) {
		ArrayList<AutoCompletePlaceObject> autoCompleteObjectList = new ArrayList<AutoCompletePlaceObject>();
		String[] result1;
		// loger.debug(wordsPinyinString);
		if (wordsPinyinString != null) {
			wordsPinyinString=wordsPinyinString.replace(';', ',');//分号";"替换为逗号
			result1 = wordsPinyinString.split(",");
			for (int i = 0; i < result1.length; i++) {
				if (!result1[i].equals("")) {
					// loger.debug("==============" + result1[i]);
					String[] result2 = result1[i].split("~");
					if (result2.length == 1 && !result1[i].equals("")) {
						//如果Name字段中有中文顿号"、"分隔符
						if (result2[0].indexOf("、")>0) {
							String[] result3 = result2[0].split("、");
							for (int j = 0; j < result3.length; j++) {
								autoCompleteObjectList.add(new AutoCompletePlaceObject(stage, shortId, pinyin, result3[j], result3[j], " ", seq));
							}
						}else{
							autoCompleteObjectList.add(new AutoCompletePlaceObject(stage, shortId, pinyin, result2[0], result2[0], " ", seq));
						}
						// loger.debug(result2[0] + " : " + result2[0]);
					} else if (result2.length == 2 && !result1[i].equals("")) {
						// 如果拼音字段中有中文顿号"、"分隔符
						if (result2[1].indexOf("、") > 0) {
							String[] result3 = result2[1].split("、");
							for (int j = 0; j < result3.length; j++) {
								autoCompleteObjectList.add(new AutoCompletePlaceObject(stage, shortId, pinyin, result2[1], result3[j], " ", seq));
							}
						} else {
							autoCompleteObjectList.add(new AutoCompletePlaceObject(stage, shortId, pinyin, result2[1], result2[0], " ", seq));
						}
						// loger.debug(result2[1] + " : " + result2[0]);
						autoCompleteObjectList.add(new AutoCompletePlaceObject(stage, shortId, pinyin, result2[0], result2[0], " ", seq));
						// loger.debug(result2[0] + " : " + result2[0]);
					} else if (result2.length == 3 && !result1[i].equals("")) {
						autoCompleteObjectList.add(new AutoCompletePlaceObject(stage, shortId, pinyin, result2[1], result2[0], " ", seq));
						// loger.debug(result2[1] + " : " + result2[0]);
						autoCompleteObjectList.add(new AutoCompletePlaceObject(stage, shortId, pinyin, result2[2], result2[0], " ", seq));
						// loger.debug(result2[2] + " : " + result2[0]);
						autoCompleteObjectList.add(new AutoCompletePlaceObject(stage, shortId, pinyin, result2[0], result2[0], " ", seq));
						// loger.debug(result2[0] + " : " + result2[0]);
					}
				}
			}
		}
		return autoCompleteObjectList;
	}
	
	private boolean isHotCity(String cityName){
		
		if("上海市".equals(cityName) || "杭州市".equals(cityName) || "常州市".equals(cityName) || "黄山市".equals(cityName) || "南京市".equals(cityName) 
				|| "苏州市".equals(cityName) || "宁波市".equals(cityName) || "溧水县".equals(cityName) || "无锡市".equals(cityName) || "三亚市".equals(cityName) 
				|| "厦门市".equals(cityName) || "丽江市".equals(cityName) || "青岛市".equals(cityName) || "澳门".equals(cityName) || "峨眉山市".equals(cityName) 
				|| "北京市".equals(cityName) || "成都市".equals(cityName) || "深圳市".equals(cityName) || "广州市".equals(cityName)){
			
			return true;
		}
		return false;
	}
	
}
