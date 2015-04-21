package com.lvmama.pet.place.service;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.lvmama.comm.pet.po.place.Place;
import com.lvmama.comm.pet.po.place.PlaceSearchPinyin;
import com.lvmama.comm.pet.po.pub.ComPinyin;
import com.lvmama.comm.pet.service.place.PlaceSearchPinyinService;
import com.lvmama.comm.pet.service.place.PlaceService;
import com.lvmama.comm.utils.RandomFactory;
import com.lvmama.comm.vo.Constant;
import com.lvmama.pet.place.dao.PlaceSearchPinyinDAO;
import com.lvmama.pet.pub.dao.ComPinyinDAO;

public class PlaceSearchPinyinServiceImpl implements PlaceSearchPinyinService {
	@Autowired
	private PlaceService placeService;
	@Autowired
	private PlaceSearchPinyinDAO placeSearchPinyinDAO;
	@Autowired
	private ComPinyinDAO comPinyinDAO;

	@Override
	public List<PlaceSearchPinyin> queryPlacePinyinList(PlaceSearchPinyin placeSearchPinyin) {
		return placeSearchPinyinDAO.queryPlacePinyinList(placeSearchPinyin);
	}
	
	/**
	 * 依据文字获取拼音对象
	 */
	@Override
	public List<PlaceSearchPinyin> queryPinyinListByName(String objectName) {
		//字符分拆
		ArrayList<String> wordList = new ArrayList<String>();
		if(StringUtils.isNotEmpty(objectName)) {
			for(int i=0;i<objectName.length();i++) {
				wordList.add(String.valueOf(objectName.charAt(i)));
			}
		}
		
		//查询汉语字典
		ComPinyin comPinyin = new ComPinyin();
		comPinyin.setWordList(wordList);
		List<ComPinyin> pinyinList = comPinyinDAO.findComPinyin(comPinyin);
		
		if(pinyinList==null || pinyinList.isEmpty()) {
			return null;
		}

		Map<String, List<String>> pinYinMap = new HashMap<String, List<String>>();
		for (int i = 0; i < pinyinList.size(); i++) {
			List<String> pinYinlt = pinYinMap.get(pinyinList.get(i).getWord());
			if (pinYinlt == null) {
				List<String> newList = new ArrayList<String>();
				newList.add(pinyinList.get(i).getPinyin());
				pinYinMap.put(pinyinList.get(i).getWord(), newList);
			} else {
				pinYinlt.add(pinyinList.get(i).getPinyin());
				pinYinMap.put(pinyinList.get(i).getWord(), pinYinlt);
			}
		}
		
		//返回一窜中文对应的拼音的列表和简拼
		List<PlaceSearchPinyin> wordPinyin = new ArrayList<PlaceSearchPinyin>();
		PlaceSearchPinyin param = new PlaceSearchPinyin();
		param.setPinYin("");
		param.setJianPin("");
		param.setObjectName(objectName);
		wordPinyin.add(param);
		for(String word:wordList) {
			wordPinyin = getWordPinyinList(word, pinYinMap,wordPinyin);
		}
		
		return wordPinyin;
	}
	
	/**
	 * 返回一窜中文对应的拼音的列表和简拼
	 * 
	 * @param word
	 * @param pinYinMap
	 * @param beforeResult
	 * @return
	 */
	private List<PlaceSearchPinyin> getWordPinyinList(String word, Map<String, List<String>> pinYinMap, List<PlaceSearchPinyin> beforeResult) {
		List<PlaceSearchPinyin> result = new ArrayList<PlaceSearchPinyin>();
		PlaceSearchPinyin placeSearchPy;
		
		for (PlaceSearchPinyin psp2:beforeResult) {
			List<String> pinyin = pinYinMap.get(word);
			if(pinyin!=null) {
				for(String py:pinyin) {
					placeSearchPy = new PlaceSearchPinyin();
					placeSearchPy.setJianPin((psp2.getJianPin()+py.charAt(0)).toUpperCase());
					py = psp2.getPinYin() + py;
					placeSearchPy.setPinYin(py.toLowerCase());
					placeSearchPy.setObjectName(psp2.getObjectName());
					result.add(placeSearchPy);
				}
			}
		}
		
		List<String> pinyin = pinYinMap.get(word);
		if(pinyin==null) {
			return beforeResult;
		}
		
		return result;
	}

	@Override
	public void deleteHFKWPlaceSearchPinyin(PlaceSearchPinyin placeSearchPinyin) {
		placeSearchPinyinDAO.deleteHFKWPlaceSearchPinyin(placeSearchPinyin);
		updatePlaceHFKW(placeSearchPinyin);
	}

	@Override
	public void saveOrUpdate(final PlaceSearchPinyin placeSearchPinyin) {
		if (null != placeSearchPinyin
				&& null != placeSearchPinyin.getObjectId()
				&& null != placeSearchPinyin.getPinYin()
				&& null != placeSearchPinyin.getObjectType()
				&& null != placeSearchPinyin.getJianPin()
				&& null != placeSearchPinyin.getObjectName()) {
			if (Constant.PLACE_SEARCH_PINYIN_OBJECT_TYPE_PLACE_NAME.equalsIgnoreCase(placeSearchPinyin.getObjectType())) {
				//删除place_search_pinyin表中该目的地的PLACE_NAME的记录
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("objectId", placeSearchPinyin.getObjectId());
				params.put("objectType", placeSearchPinyin.getObjectType());
				placeSearchPinyinDAO.deletePlaceSearchPinyinByMap(params);
				
				Place place = placeService.queryPlaceByPlaceId(placeSearchPinyin.getObjectId());
				if (null != place ) {
					String placePinYin = getPlaceNamePinyin(placeSearchPinyin.getPinYin()); 
					if (String.valueOf(Constant.STAGE_OF_CITY).equals(place.getStage())&& StringUtils.isBlank(place.getPinYin()) && StringUtils.isBlank(place.getPinYinUrl())) {
						StringBuilder compositePinYinUrl = new StringBuilder();
						if (null != place.getParentPlaceId()) {
							Place parentPlace =  placeService.queryPlaceByPlaceId(place.getParentPlaceId());
							if (null != parentPlace && StringUtils.isNotBlank(parentPlace.getPinYin())) {
								compositePinYinUrl.append(parentPlace.getPinYin()).append("_");
							}
						}
						compositePinYinUrl.append(placePinYin);
						place.setPinYinUrl(compositePinYinUrl.toString());
						place.setPinYin(placePinYin);
					} else if (StringUtils.isBlank(place.getPinYin()) && StringUtils.isBlank(place.getPinYinUrl())){
						place.setPinYinUrl(placePinYin);
						place.setPinYin(placePinYin);
					}
					place.setName(placeSearchPinyin.getObjectName());
					placeService.savePlace(place);
				}	
			} 
			placeSearchPinyinDAO.saveOrUpdate(placeSearchPinyin);
			updatePlaceHFKW(placeSearchPinyin);
		}
		
		
	}

	@Override
	public void updatePlaceHFKW(PlaceSearchPinyin placeSearchPinyin) {
		placeSearchPinyinDAO.updatePlaceHFKW(placeSearchPinyin);
	}
	
	/**
	 * 为了防止重复的拼音，将重复的拼音后面加一自然数。
	 * @param pinyin
	 * @return
	 * 此方法可能导致死循环，切记
	 */
	private String getPlaceNamePinyin(String pinyin) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("pinYin", pinyin);
		long result = placeService.countPlaceListByParam(param);
		if(result > 0) {
			pinyin = pinyin + RandomFactory.randomString(1);
			pinyin = getPlaceNamePinyin(pinyin);
		}
		return pinyin;
	}
	
}
