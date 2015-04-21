package com.lvmama.search.lucene.service.autocomplete;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lvmama.comm.search.SearchConstants;
import com.lvmama.comm.search.vo.AutoCompletePlaceDto;
import com.lvmama.comm.search.vo.AutoCompletePlaceObject;
import com.lvmama.comm.search.vo.ClientPlaceSearchVO;
import com.lvmama.comm.search.vo.ClientRouteSearchVO;
import com.lvmama.comm.utils.MemcachedUtil;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.search.lucene.dao.impl.AutoCompleteDao;
import com.lvmama.search.util.LocalCacheManager;

/**
 * 自动补全查询
 * 
 * @author huangzhi
 * 
 */
@Service("autoCompleteService")
public class AutoCompleteServiceImpl implements AutoCompleteService {
	private Log loger = LogFactory.getLog(getClass());
	@Autowired
	private AutoCompleteDao autoCompleteDao;
	public static final int DEFAULT_MEMCACHE_VALID_TIME = 60*60*2;
	/**提高下拉补全中某中文，拼音，标签...等某一类属性的权重**/
	private Long boost = -100l;
	
	public List<AutoCompletePlaceDto> getAutoCompletePlaceDtoList(int channelType, Long fromPlaceId) {
		List<AutoCompletePlaceDto> result = new ArrayList<AutoCompletePlaceDto>();
		switch (channelType) {
		case SearchConstants.CLIENT_CHANNEL_TYPE:
			if (fromPlaceId==null) {
				result = autoCompleteDao.getClientAutoCompletePlace();
			} else {//fromPlaceId 借用为stage
				result = autoCompleteDao.getClientAutoCompletePlaceByStage(fromPlaceId);
			}
			break;
		case SearchConstants.CLIENT_CHANNEL_SUBJECT:
			if (fromPlaceId.equals(9999L)) {
				result = autoCompleteDao.getClientSubjectAll();
			}else {
				result = autoCompleteDao.getClientSubjectByCity(fromPlaceId);
			}
			break;
		case SearchConstants.CLIENT_ROUTER_SUBJECT:
			if (fromPlaceId.equals(9999L)) {
				result = autoCompleteDao.getClientRouteSubjectAll();
			}else {
				result = autoCompleteDao.getClientRouteSubjectByCity(fromPlaceId);
			}
			break;
		default:
			break;
		}
		return result;
	}
	
	public List<AutoCompletePlaceDto> getAutoCompletePlaceDtoList(int channelType, ClientPlaceSearchVO searchVo) {
		List<AutoCompletePlaceDto> result = new ArrayList<AutoCompletePlaceDto>();
		switch (channelType) {		
		case SearchConstants.CLIENT_PLACE_AUTOCOMPLETE:
				result = autoCompleteDao.getClientPlaceAutoComplete(searchVo);			
			break;			
		default:
			break;
		}
		return result;
	}
	
	public List<AutoCompletePlaceDto> getAutoCompletePlaceDtoList(int channelType, ClientRouteSearchVO searchVo) {
		List<AutoCompletePlaceDto> result = new ArrayList<AutoCompletePlaceDto>();
		switch (channelType) {		
		case SearchConstants.CLIENT_ROUTE_AUTOCOMPLETE:
				result = autoCompleteDao.getClientRouteAutoComplete(searchVo);			
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
		nanya.setSeq(0L);
		result.add(nanya);
		AutoCompletePlaceDto ouzhou = new AutoCompletePlaceDto();
		ouzhou.setName("欧洲~ouzhou~oz");
		ouzhou.setSeq(0L);
		result.add(ouzhou);
		AutoCompletePlaceDto aoxin = new AutoCompletePlaceDto();
		aoxin.setName("澳新~aoxin~ax");
		aoxin.setSeq(0L);
		result.add(aoxin);
		AutoCompletePlaceDto meizhou = new AutoCompletePlaceDto();
		meizhou.setName("美洲~meizhou~mz");
		meizhou.setSeq(0L);
		result.add(meizhou);
		AutoCompletePlaceDto rihan = new AutoCompletePlaceDto();
		rihan.setName("日韩~rihan~rh");
		rihan.setSeq(0L);
		result.add(rihan);
		AutoCompletePlaceDto dayangzhou = new AutoCompletePlaceDto();
		dayangzhou.setName("大洋洲~dayangzhou~dyz");
		dayangzhou.setSeq(0L);
		result.add(dayangzhou);
		AutoCompletePlaceDto dongnanaya = new AutoCompletePlaceDto();
		dongnanaya.setName("东南亚~dongnanya~dny");
		dongnanaya.setSeq(0L);
		result.add(dongnanaya);
		AutoCompletePlaceDto zhongdongfei = new AutoCompletePlaceDto();
		zhongdongfei.setName("中东非~zhongdongfei~zdf");
		zhongdongfei.setSeq(0L);
		result.add(zhongdongfei);
		AutoCompletePlaceDto gangaodiqu = new AutoCompletePlaceDto();
		gangaodiqu.setName("港澳地区~gangaodiqu~gadq");
		gangaodiqu.setSeq(0L);
		result.add(gangaodiqu);
		AutoCompletePlaceDto yazhou = new AutoCompletePlaceDto();
		yazhou.setName("亚洲~yazhou~yz");
		yazhou.setSeq(0L);
		result.add(yazhou);
		AutoCompletePlaceDto zhongguo = new AutoCompletePlaceDto();
		zhongguo.setName("中国~zhongguo~zg");
		zhongguo.setSeq(0L);
		result.add(zhongguo);
	}

	
	@SuppressWarnings("unchecked")
	@Override
	public List<AutoCompletePlaceObject> getAutoCompletePlacePlaceObjectListMatched(int channelType, Long fromPlaceId, String keyword, int topNum) {
		List<AutoCompletePlaceObject> autoCompleteObjectList = getAutoCompletePlaceData(channelType, fromPlaceId);
		List<AutoCompletePlaceObject> autoCompleteObjectListCheckedAgain = matchDistinctSort(keyword, autoCompleteObjectList);
		// 取前面topNum个
		if (autoCompleteObjectListCheckedAgain.size() > topNum) {
			autoCompleteObjectListCheckedAgain = autoCompleteObjectListCheckedAgain.subList(0, topNum);
		}
		return autoCompleteObjectListCheckedAgain;
	}
	
	public List<AutoCompletePlaceObject> getAutoCompletePlaceObjectListMatched(int channelType, ClientPlaceSearchVO searchVo, int topNum) {
		List<AutoCompletePlaceObject> autoCompleteObjectList = getAutoCompletePlaceData(channelType, searchVo);
		List<AutoCompletePlaceObject> autoCompleteObjectListCheckedAgain = matchDistinctSort(searchVo.getKeyword(), autoCompleteObjectList);
		// 取前面topNum个
		if (autoCompleteObjectListCheckedAgain.size() > topNum) {
			autoCompleteObjectListCheckedAgain = autoCompleteObjectListCheckedAgain.subList(0, topNum);
		}
		return autoCompleteObjectListCheckedAgain;
	}
	
	public List<AutoCompletePlaceObject> getAutoCompleteRouteObjectListMatched(int channelType, ClientRouteSearchVO searchVo, int topNum) {
		List<AutoCompletePlaceObject> autoCompleteObjectList = getAutoCompletePlaceDataForClient(channelType, searchVo);
		List<AutoCompletePlaceObject> autoCompleteObjectListCheckedAgain = matchDistinctSort(searchVo.getToDest(), autoCompleteObjectList);
		loger.info("#### find match size"+autoCompleteObjectListCheckedAgain.size()+" toDest:"+searchVo.getToDest());
		// 取前面topNum个
		if (autoCompleteObjectListCheckedAgain.size() > topNum) {
			autoCompleteObjectListCheckedAgain = autoCompleteObjectListCheckedAgain.subList(0, topNum);
		}
		return autoCompleteObjectListCheckedAgain;
	}

	

	private List<AutoCompletePlaceObject> matchDistinctSort(String keyword, List<AutoCompletePlaceObject> autoCompleteObjectList) {
		// 根据关键字匹配结果列表,删除不匹配的对象,排重,生成排序权重:matchSEQ
		ArrayList<AutoCompletePlaceObject> autoCompleteObjectListChecked = new ArrayList<AutoCompletePlaceObject>();
		//HashMap<String, String> check = new HashMap<String, String>();
		keyword = keyword.toLowerCase();
		for (int i = 0; i < autoCompleteObjectList.size(); i++) {
			String matchWrod = autoCompleteObjectList.get(i).getMatchword().toLowerCase();
			String words = autoCompleteObjectList.get(i).getWords().toLowerCase();
			Long Seq = autoCompleteObjectList.get(i).getSeq();
			String shortId = autoCompleteObjectList.get(i).getShortId();
			// 前缀匹配
			if (matchWrod.indexOf(keyword) >= 0) {
				//if (check.get(matchWrod) == null || !check.get(matchWrod).equals(words)) {
					/**
					 * 按拼音SEQ的规则设置排序SEQ:
					 * 拼音匹配位置_拼音长度_SEQ,考虑到SEQ为[-10000,20000]之间, 而且是负值排在前面,所以
					 * 用一个参数100000去加SEQ 的值作为排序数[110000 , 80000
					 * ]，则越大的越排前,为保持字符串比较，则开平方保证值为3位数
					 ***/
					String matchSEQ = Math.sqrt(matchWrod.indexOf(keyword)) + "_" + Math.sqrt(matchWrod.length()) + "_" + Math.sqrt((100000 + Seq));
					autoCompleteObjectList.get(i).setMatchSEQ(matchSEQ);
					/*
					 * *HFKW,DEST_SUBJECTS,DEST_TAGS_NAME 如果被匹配，则这里需要把相应字段替换为place_ID对应的景点名称NAME字段
					 * */
					/*for (int j = 0; j < getAutoCompletePlaceDtoList.size() ; j++) {
						if (shortId!=null&&(shortId.equals(getAutoCompletePlaceDtoList.get(j).getShortId()))) {
							String nameStr= getAutoCompletePlaceDtoList.get(j).getName();
							if (nameStr!=null&&nameStr.indexOf("~")>0) {
								nameStr = nameStr.substring(0, nameStr.indexOf("~", 0));
								autoCompleteObjectList.get(i).setWords(nameStr);
								break;
							}
							
						}
					}*/
					autoCompleteObjectListChecked.add(autoCompleteObjectList.get(i));
					//check.put(matchWrod, words);
				//}
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
				}
			}
		}
		loger.debug("=== 二次排重排序匹配后总数  ：" + autoCompleteObjectListCheckedAgain.size());
		// 打印列表
		/*for (int i = 0; i < autoCompleteObjectListCheckedAgain.size() && i < 50; i++) {
			loger.debug(autoCompleteObjectListCheckedAgain.get(i).getShortId() + " : " + autoCompleteObjectListCheckedAgain.get(i).getMatchword() + "  "
					+ autoCompleteObjectListCheckedAgain.get(i).getWords() + "  " + autoCompleteObjectListCheckedAgain.get(i).getSeq() + "  =  "
					+ autoCompleteObjectListCheckedAgain.get(i).getMatchSEQ());
		}*/
		return autoCompleteObjectListCheckedAgain;
	}


	@SuppressWarnings("unchecked")
	private List<AutoCompletePlaceObject> getAutoCompletePlaceData(int channelType,  ClientPlaceSearchVO searchVo) {
		String key = "getAutoCompletePlaceObjectList_" + channelType ;
		loger.debug("key:"+key);
		List<AutoCompletePlaceDto> getAutoCompletePlaceDtoList  = new ArrayList<AutoCompletePlaceDto>();
		List<AutoCompletePlaceObject> autoCompleteObjectList = new ArrayList<AutoCompletePlaceObject> ();
		autoCompleteObjectList = (List<AutoCompletePlaceObject>) MemcachedUtil.getInstance().get(key);
		if (autoCompleteObjectList == null || autoCompleteObjectList.size() < 1) {
			getAutoCompletePlaceDtoList = getAutoCompletePlaceDtoList(channelType, searchVo);
			autoCompleteObjectList = ConvertAutoCompletePlaceDtoToAutoCompleteObject(getAutoCompletePlaceDtoList);			
			if(autoCompleteObjectList != null){
				 MemcachedUtil.getInstance().set(key,MemcachedUtil.TWO_HOUR,autoCompleteObjectList);
			}
		}
		loger.debug("=== channel : " + channelType + "total count: " + autoCompleteObjectList.size());
		return autoCompleteObjectList;
	}
	
	private boolean isFreenessSearch(List<String> subProductTypes){
		if(subProductTypes!=null&&subProductTypes.contains(Constant.SUB_PRODUCT_TYPE.FREENESS.name())){
			return true;
		}
		return false;
	}
	
	@SuppressWarnings("unchecked")
	private List<AutoCompletePlaceObject> getAutoCompletePlaceDataForClient(int channelType,  ClientRouteSearchVO searchVo) {
		//**toDest 不能作为key 来存储数据。。
		String key = "getAutoCompletePlaceObjectList_";
		/**
		 * 但对处理目的地自由行的自动完成接口
		 */
		if(isFreenessSearch(searchVo.getSubProductType())){
			key += "client_autocomplete_freeness_data_key"+channelType;
		} else {
			try {
				if(searchVo.getSubProductType()!=null&&searchVo.getFromDest()!=null){
					key = key + searchVo.getSubProductType().toString()+"_"+URLEncoder.encode(searchVo.getFromDest(), "UTF-8");
				} else if(searchVo.getFromDest()!=null){
					key=  key +URLEncoder.encode(searchVo.getFromDest(), "UTF-8");
				} else {
					key =key + channelType;
				}
				
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		List<AutoCompletePlaceDto> getAutoCompletePlaceDtoList  = new ArrayList<AutoCompletePlaceDto>();
		List<AutoCompletePlaceObject> autoCompleteObjectList = new ArrayList<AutoCompletePlaceObject> ();
		autoCompleteObjectList = (List<AutoCompletePlaceObject>) MemcachedUtil.getInstance().get(key);
		
		if (autoCompleteObjectList == null || autoCompleteObjectList.size() < 1) {
			getAutoCompletePlaceDtoList = getAutoCompletePlaceDtoList(channelType, searchVo);
			loger.info("#### find size database:"+getAutoCompletePlaceDtoList.size());
			autoCompleteObjectList = ConvertAutoCompletePlaceDtoToAutoCompleteObject(getAutoCompletePlaceDtoList);			
			if(autoCompleteObjectList != null){
				 MemcachedUtil.getInstance().set(key,MemcachedUtil.TWO_HOUR,autoCompleteObjectList);
			}
		}
		
		loger.debug("=== channel : " + channelType + "total count: " + autoCompleteObjectList.size());
		return autoCompleteObjectList;
	}
	
	
	@SuppressWarnings("unchecked")
	private List<AutoCompletePlaceObject> getAutoCompletePlaceData(int channelType,  ClientRouteSearchVO searchVo) {
		String key = "getAutoCompletePlaceObjectList_" + channelType ;
		List<AutoCompletePlaceDto> getAutoCompletePlaceDtoList  = new ArrayList<AutoCompletePlaceDto>();
		List<AutoCompletePlaceObject> autoCompleteObjectList = new ArrayList<AutoCompletePlaceObject> ();
		autoCompleteObjectList = (List<AutoCompletePlaceObject>) LocalCacheManager.get(key);	
		if (autoCompleteObjectList == null || autoCompleteObjectList.size() < 1) {
			getAutoCompletePlaceDtoList = getAutoCompletePlaceDtoList(channelType, searchVo);
			autoCompleteObjectList = ConvertAutoCompletePlaceDtoToAutoCompleteObject(getAutoCompletePlaceDtoList);			
			if(autoCompleteObjectList != null){
				LocalCacheManager.put(key, autoCompleteObjectList, 2*60*60*1000);
			}
		}
		loger.debug("=== channel : " + channelType + "total count: " + autoCompleteObjectList.size());
		return autoCompleteObjectList;
	}
	
	@SuppressWarnings("unchecked")
	private List<AutoCompletePlaceObject> getAutoCompletePlaceData(int channelType, Long fromPlaceId) {
		String key = "getAutoCompletePlaceObjectList_" + channelType + "_" + fromPlaceId;
		List<AutoCompletePlaceDto> getAutoCompletePlaceDtoList  = new ArrayList<AutoCompletePlaceDto>();
		List<AutoCompletePlaceObject> autoCompleteObjectList = new ArrayList<AutoCompletePlaceObject>();
		autoCompleteObjectList = (List<AutoCompletePlaceObject>) LocalCacheManager.get(key);	
		if (autoCompleteObjectList == null || autoCompleteObjectList.size() < 1) {
			getAutoCompletePlaceDtoList = getAutoCompletePlaceDtoList(channelType, fromPlaceId);
			autoCompleteObjectList = ConvertAutoCompletePlaceDtoToAutoCompleteObject(getAutoCompletePlaceDtoList);			
			if(autoCompleteObjectList != null){
				LocalCacheManager.put(key, autoCompleteObjectList, 2*60*60*1000);
			}
		}
		loger.debug("=== channel : " + channelType + "total count: " + autoCompleteObjectList.size());
		return autoCompleteObjectList;
	}

	// 空匹配，直接打印前TOPnum个SEQ最大的结果
	public List<AutoCompletePlaceObject> getAutoCompletePlacePlaceObjectListDefault(int channelType, Long fromPlaceId, int topNum) {
		String key = "getAutoCompletePlaceObjectListDefault_" + channelType + "_" + fromPlaceId;
		List<AutoCompletePlaceObject> autoCompleteObjectList = (ArrayList<AutoCompletePlaceObject>) com.lvmama.comm.utils.MemcachedUtil.getInstance().get(key);
		// 存入缓存
		if (autoCompleteObjectList == null || autoCompleteObjectList.size() < 1) {
			List<AutoCompletePlaceDto> getAutoCompletePlaceDtoList = getAutoCompletePlaceDtoList(channelType, fromPlaceId);
			autoCompleteObjectList = ConvertAutoCompletePlaceDtoToAutoCompleteObject(getAutoCompletePlaceDtoList);
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
			// 打印列表
			/*for (int i = 0; i < autoCompleteObjectListCheckedAgain.size() && i < 50; i++) {
				loger.debug(autoCompleteObjectListCheckedAgain.get(i).getShortId() + " : " + autoCompleteObjectListCheckedAgain.get(i).getMatchword() + "  "
						+ autoCompleteObjectListCheckedAgain.get(i).getWords() + "  " + autoCompleteObjectListCheckedAgain.get(i).getSeq() + "  =  "
						+ autoCompleteObjectListCheckedAgain.get(i).getMatchSEQ());
			}*/


//			// 取前面topNum个
//			if (autoCompleteObjectListCheckedAgain.size() > topNum) {
//				autoCompleteObjectListCheckedAgain = autoCompleteObjectListCheckedAgain.subList(0, topNum);
//			}
			autoCompleteObjectList = autoCompleteObjectListCheckedAgain;
			LocalCacheManager.put(key, autoCompleteObjectList, 2*60*60*1000);
			loger.debug("缓存了！");
		}

		return autoCompleteObjectList;
	}

	// 把数据库中查询的结果 转换成匹配用的格式列表 : LIST[ 匹配字符_中文名称_排序串_SEQ] ,
	private ArrayList<AutoCompletePlaceObject> ConvertAutoCompletePlaceDtoToAutoCompleteObject(List<AutoCompletePlaceDto> autoCompletePlaceDtoList) {
		ArrayList<AutoCompletePlaceObject> autoCompleteObjectList = new ArrayList<AutoCompletePlaceObject>();
		for (int i = 0; i < autoCompletePlaceDtoList.size(); i++) {
			Long seq = autoCompletePlaceDtoList.get(i).getSeq();
			String stage = autoCompletePlaceDtoList.get(i).getStage();
			String shortId = autoCompletePlaceDtoList.get(i).getShortId();
			String pinyin = autoCompletePlaceDtoList.get(i).getPinyin();
			String name = autoCompletePlaceDtoList.get(i).getName();
			autoCompleteObjectList.addAll(ConvertAutoCompleteObject(name, stage, shortId, pinyin, Long.valueOf(seq + this.boost)));
			
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

	// 把一个: [中文~拼音~简拼,]xN 的字符串 转换成 : LIST[ 匹配字符_中文名称_排序串_SEQ]
	//格式升级：[中文A~拼音1、拼音2、; 中文1、中文2、中文3~ , 中文A~拼音1、拼音2、; 中文1、中文2、中文3~ ] 注意加了分号";"中文下的顿号"、"
	private ArrayList<AutoCompletePlaceObject> ConvertAutoCompleteObject(String wordsPinyinString, String stage, String shortId, String pinyin, Long seq) {
		ArrayList<AutoCompletePlaceObject> autoCompleteObjectList = new ArrayList<AutoCompletePlaceObject>();
		String[] result1;
		// loger.debug(wordsPinyinString);
		if (StringUtils.isNotEmpty(wordsPinyinString)) {
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
}
