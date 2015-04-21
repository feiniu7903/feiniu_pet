package com.lvmama.pet.place.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import com.lvmama.comm.pet.po.comment.CmtLatitudeStatistics;
import com.lvmama.comm.pet.po.comment.DicCommentLatitude;
import com.lvmama.comm.pet.po.place.Place;
import com.lvmama.comm.pet.po.place.PlaceActivity;
import com.lvmama.comm.pet.po.place.PlaceHotel;
import com.lvmama.comm.pet.po.place.PlaceHotelNotice;
import com.lvmama.comm.pet.po.place.PlaceHotelOtherRecommend;
import com.lvmama.comm.pet.po.place.PlaceHotelRoom;
import com.lvmama.comm.pet.po.seo.RecommendInfo;
import com.lvmama.comm.pet.service.place.PlaceService;
import com.lvmama.comm.pet.service.pub.ComLogService;
import com.lvmama.comm.pet.service.sensitiveW.SensitiveWordService;
import com.lvmama.comm.pet.vo.PlaceStateDests;
import com.lvmama.comm.pet.vo.PlaceVo;
import com.lvmama.comm.utils.homePage.PlaceUtils;
import com.lvmama.comm.vo.Constant;
import com.lvmama.pet.comment.dao.CmtLatitudeDAO;
import com.lvmama.pet.comment.dao.CmtLatitudeStatistisDAO;
import com.lvmama.pet.comment.dao.DicCommentLatitudeDAO;
import com.lvmama.pet.place.dao.PlaceActivityDAO;
import com.lvmama.pet.place.dao.PlaceDAO;
import com.lvmama.pet.place.dao.PlaceHotelDAO;
import com.lvmama.pet.place.dao.PlaceHotelNoticeDao;
import com.lvmama.pet.place.dao.PlaceHotelRecommendDao;
import com.lvmama.pet.place.dao.PlaceHotelRoomDao;
import com.lvmama.pet.pub.dao.ComSubjectDAO;
import com.lvmama.pet.seo.dao.RecommendInfoDAO;

class PlaceServiceImpl implements PlaceService {
	@Autowired
	private PlaceDAO placeDAO;
	@Autowired
	private RecommendInfoDAO recommendInfoDAO;
	@Autowired
	private ComSubjectDAO comSubjectDAO;
	@Autowired
	private ComLogService comLogService;
	@Autowired
	private DicCommentLatitudeDAO dicCommentLatitudeDAO;
	@Autowired
	private PlaceHotelDAO placeHotelDAO;
	@Autowired
	private CmtLatitudeDAO cmtLatitudeDAO;
	@Autowired
	private CmtLatitudeStatistisDAO cmtLatitudeStatistisDAO;
	
 	public List<Place> queryPlaceListByParam(Map<String,Object> param) {
		return placeDAO.queryPlaceList(param);
	}
	
	public Long countPlaceListByParam(Map<String,Object> param) {
		return placeDAO.countPlaceList(param);
	}
	public void batchSavePlaceSeq(String placeIds,String userName) {
		if(StringUtils.isNotBlank(placeIds)){
			List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
			Map<String,Object> param=null;
			String[] items=placeIds.split(",");
			if(items.length>0){
				for(String item:items){
					String[] place=item.split("_");
					param=new HashMap<String,Object>();
					param.put("placeId", place[0]);
					param.put("seq", place[1]);
					list.add(param);
					comLogService.insert("SCENIC_LOG_PLACE", null,Long.valueOf(place[0]), userName,
						Constant.SCENIC_LOG_PLACE.updatePlaceInfo.name(),"修改排序值", "修改[排序]值为【"+place[1]+"】", "");
					
				}
				placeDAO.savePlaceSeq(list);
			}
		}
	}
	
	public List<PlaceVo> queryPlaceAndParent(Map<String,Object> param){
		List<Place> placeList= placeDAO.queryPlaceList(param);
		List<PlaceVo> placeVoList=new ArrayList<PlaceVo>();
		PlaceVo placeVo;
		Place parentPlace;
		 for(Place p:placeList){
			 placeVo=new PlaceVo();
			 placeVo.setPlace(p);
			 if(p.getParentPlaceId()!=null){
				 parentPlace=queryPlaceByPlaceId(p.getParentPlaceId());
				 placeVo.setParentPlace(parentPlace);
			 }
			 placeVoList.add(placeVo);
		 }
		 return placeVoList;
	}
	public void savePlace(Place place) {
		if(place.getPlaceId()!=null&&!"".equals(place.getPlaceId())){
				placeDAO.updatePlace(place);
			if(null!=place.getFirstTopic()&&!"".equals(place.getFirstTopic())) {
				comSubjectDAO.updateUsedCount(place.getFirstTopic());
			}
			if(null!=place.getScenicSecondTopic()&&!"".equals(place.getScenicSecondTopic())) {
				comSubjectDAO.updateUsedCount(place.getScenicSecondTopic());
			}
			if(null!=place.getFirstTopicOld()&&!"".equals(place.getFirstTopicOld())) {
				comSubjectDAO.updateUsedCount(place.getFirstTopicOld());
			}
			if(null!=place.getScenicSecondTopicOld()&&!"".equals(place.getScenicSecondTopicOld())) {
				comSubjectDAO.updateUsedCount(place.getScenicSecondTopicOld());
			}
		}else{
			//插入数据
			place.setPlaceId(placeDAO.getNextPlaceId());
			placeDAO.insertPlace(place);
		}
	}
	
	
	
	private String updateContent(String oldPlaces,String newPlaces,String PlaceCol){
		
		StringBuffer content =new StringBuffer("");
		if(oldPlaces!=null && !oldPlaces.equals(newPlaces)){//被修改了
			if(null==newPlaces){
				newPlaces="";
			 }
			content.append("把["+PlaceCol+"]为【"+oldPlaces+"】,修改为【"+newPlaces+"】;");
		}else if(oldPlaces==null && newPlaces!=null){//添加了
			if(null==oldPlaces){oldPlaces="";}
			content.append("添加["+PlaceCol+"]值为【"+newPlaces+"】;");
 		}
		return content.toString();
	}
	private void updateComLog(Place oldPlace,Place newPlace,Place nowplace,String userName) {
		StringBuffer content=new StringBuffer("");
		//翻译
		if ("Y".equals(oldPlace.getIsValid())) {
			oldPlace.setIsValid("有效");
		} else {
			oldPlace.setIsValid("无效");
		}

		if ("Y".equals(newPlace.getIsValid())) {
			newPlace.setIsValid("有效");
		} else {
			newPlace.setIsValid("无效");
		}
		if(!"1".equals(nowplace.getStage())){
			content.append(updateContent(oldPlace.getAddress(),newPlace.getAddress(),"地址")) ;	
		}
		
		if(nowplace.getStage().equals("1")){
			if("COUNTRY".equals(newPlace.getPlaceType())){
				newPlace.setPlaceType("国家");
			}else if("PROVINCE".equals(newPlace.getPlaceType())){
				newPlace.setPlaceType("省份/州/郡");
			}else if("ZZQ".equals(newPlace.getPlaceType())){
				newPlace.setPlaceType("自治区");
			}else if("ZXS".equals(newPlace.getPlaceType())){
				newPlace.setPlaceType("直辖市");
			}else if("TBXZQ".equals(newPlace.getPlaceType())){
				newPlace.setPlaceType("特别行政区");
			}else if("CITY".equals(newPlace.getPlaceType())){
				newPlace.setPlaceType("城市 ");
			}else if("FOREIGN".equals(newPlace.getPlaceType())){
				newPlace.setPlaceType("出境目的地");
			}else if("OTHER".equals(newPlace.getPlaceType())){
				newPlace.setPlaceType("特殊");
			}
			
			if("COUNTRY".equals(oldPlace.getPlaceType())){
				oldPlace.setPlaceType("国家");
			}else if("PROVINCE".equals(oldPlace.getPlaceType())){
				oldPlace.setPlaceType("省份/州/郡");
			}else if("ZZQ".equals(oldPlace.getPlaceType())){
				oldPlace.setPlaceType("自治区");
			}else if("ZXS".equals(oldPlace.getPlaceType())){
				oldPlace.setPlaceType("直辖市");
			}else if("TBXZQ".equals(oldPlace.getPlaceType())){
				oldPlace.setPlaceType("特别行政区");
			}else if("CITY".equals(oldPlace.getPlaceType())){
				oldPlace.setPlaceType("城市 ");
			}else if("FOREIGN".equals(oldPlace.getPlaceType())){
				oldPlace.setPlaceType("出境目的地");
			}else if("OTHER".equals(oldPlace.getPlaceType())){
				oldPlace.setPlaceType("特殊");
			}				
		}
		
		if(nowplace.getStage().equals("2")){
			if("ABROAD".equals(oldPlace.getPlaceType())){ oldPlace.setPlaceType("境外");}
			else{oldPlace.setPlaceType("境内");}
			
			if("ABROAD".equals(newPlace.getPlaceType())){ newPlace.setPlaceType("境外");}
			else{newPlace.setPlaceType("境内");}	
		}
		
		if(nowplace.getStage().equals("3")){
			PlaceHotel ph =  newPlace.getPlaceHotel();
			PlaceHotel oldPh = oldPlace.getPlaceHotel();
			if(oldPh !=null ){
				String newPlaceHotelStar = ph.getHotelStar();
				String oldPlaceHotelStar = oldPh.getHotelStar();
				
				if("1".equals(newPlaceHotelStar)){
					ph.setHotelStar("简约型酒店");
				}else if("2".equals(newPlaceHotelStar)){
					ph.setHotelStar("二星级酒店");
				}else if("3".equals(newPlaceHotelStar)){
					ph.setHotelStar("舒适型酒店");
				}else if("4".equals(newPlaceHotelStar)){
					ph.setHotelStar("三星级酒店");
				}else if("5".equals(newPlaceHotelStar)){
					ph.setHotelStar("品质型酒店");
				}else if("6".equals(newPlaceHotelStar)){
					ph.setHotelStar("四星级酒店");
				}else if("7".equals(newPlaceHotelStar)){
					ph.setHotelStar("豪华型酒店");
				}else if("8".equals(newPlaceHotelStar)){
					ph.setHotelStar("五星级酒店");
				}else{
					ph.setHotelStar("");
				}
				
				
				if("1".equals(oldPlaceHotelStar)){
					oldPh.setHotelStar("简约型酒店");
				}else if("2".equals(oldPlaceHotelStar)){
					oldPh.setHotelStar("二星级酒店");
				}else if("3".equals(oldPlaceHotelStar)){
					oldPh.setHotelStar("舒适型酒店");
				}else if("4".equals(oldPlaceHotelStar)){
					oldPh.setHotelStar("三星级酒店");
				}else if("5".equals(oldPlaceHotelStar)){
					oldPh.setHotelStar("品质型酒店");
				}else if("6".equals(oldPlaceHotelStar)){
					oldPh.setHotelStar("四星级酒店");
				}else if("7".equals(oldPlaceHotelStar)){
					oldPh.setHotelStar("豪华型酒店");
				}else if("8".equals(oldPlaceHotelStar)){
					oldPh.setHotelStar("五星级酒店");
				}else{
					oldPh.setHotelStar("");
				}
			}
		}
		
		if ("template_abroad".equals(oldPlace.getTemplate())) {
			oldPlace.setTemplate("国外");
		} else if ("template_zhongguo".equals(oldPlace.getTemplate())) {
			oldPlace.setTemplate("国内");
		} else {
			oldPlace.setTemplate("无");
		}

		if ("template_abroad".equals(newPlace.getTemplate())) {
			newPlace.setTemplate("国外");
		} else if ("template_zhongguo".equals(newPlace.getTemplate())) {
			newPlace.setTemplate("国内");
		} else {
			newPlace.setTemplate("无");
		}
		//翻译end
		
		content.append(updateContent(oldPlace.getIsValid(),newPlace.getIsValid(),"状态"));
		
		content.append(updateContent(oldPlace.getProvince(),newPlace.getProvince(),"省份"));
		
		content.append(updateContent(oldPlace.getCity(),newPlace.getCity(),"城市"));
		
		content.append(updateContent(oldPlace.getSeoName(),newPlace.getSeoName(),"别名"));
		
		content.append(updateContent(oldPlace.getSeq().toString(),newPlace.getSeq().toString(),"排序值"));
		
		if("1".equals(nowplace.getStage())){
			content.append(updateContent(oldPlace.getTemplate(),newPlace.getTemplate(),"模板"));
			
			content.append(updateContent(oldPlace.getPlaceType(),newPlace.getPlaceType(),"目的地类型"));
			
			content.append(updateContent(oldPlace.getEnName(),newPlace.getEnName(),"英文名称"));	
			
			content.append(updateContent(oldPlace.getAirportCode(),newPlace.getAirportCode(),"三字编码"));	
	
		}
		
		if("2".equals(nowplace.getStage())){
			content.append(updateContent(oldPlace.getPlaceType(),newPlace.getPlaceType(),"地域"));	
				
			content.append(updateContent(oldPlace.getScenicOpenTime(),newPlace.getScenicOpenTime(),"开放时间"));	
				
			content.append(updateContent(oldPlace.getScenicRecommendTime(),newPlace.getScenicRecommendTime(),"推荐游玩时长"));
			
			content.append(updateContent(oldPlace.getFirstTopic(),newPlace.getFirstTopic(),"主主题"));
			
			content.append(updateContent(oldPlace.getScenicSecondTopic(),newPlace.getScenicSecondTopic(),"次主题"));
			
			content.append(updateContent(oldPlace.getTrafficInfo(),newPlace.getTrafficInfo(),"交通信息"));
		}
		
		if("3".equals(nowplace.getStage())){
			PlaceHotel ph =  newPlace.getPlaceHotel();
			PlaceHotel oldPh = oldPlace.getPlaceHotel();
			content.append(updateContent(oldPlace.getEnName(),newPlace.getEnName(),"英文名称"));	

			content.append(updateContent("DOMESTIC".equals(oldPlace.getPlaceType())?"境内":"境外","DOMESTIC".equals(newPlace.getPlaceType())?"境内":"境外","境内境外"));
			
			if(oldPh !=null ){
				
				content.append(updateContent(oldPh.getAddressEnglish(),ph.getAddressEnglish(),"英文地址"));	
	
				content.append(updateContent(oldPh.getHotelPosition(),ph.getHotelPosition(),"酒店位置"));	
	
				content.append(updateContent(oldPh.getPicDisplay(),ph.getPicDisplay(),"列表显示图片"));
				
				content.append(updateContent(oldPh.getHotelRoomNum()+"",ph.getHotelRoomNum()+"","房间数"));
	
				content.append(updateContent(oldPh.getHotelPhone(),ph.getHotelPhone(),"酒店电话"));
	
				content.append(updateContent(oldPh.getHotelFax(),ph.getHotelFax(),"酒店传真"));
				
				content.append(updateContent(oldPh.getHotelZipCode(),ph.getHotelZipCode(),"酒店邮编"));
	
				content.append(updateContent(oldPh.getHotelEmail(),ph.getHotelEmail(),"酒店电邮"));
				
				content.append(updateContent(oldPh.getHotelOpenTimeStr(),ph.getHotelOpenTimeStr(),"开业时间"));
	
				content.append(updateContent(oldPh.getHotelDecorationTimeStr(),ph.getHotelDecorationTimeStr(),"装修时间"));
				
				content.append(updateContent(oldPh.getHotelCompany(),ph.getHotelCompany(),"酒店集团"));
	
				content.append(updateContent(oldPh.getHotelBrand(),ph.getHotelBrand(),"酒店品牌"));
	
				content.append(updateContent(oldPh.getHotelStar(),ph.getHotelStar(),"星级标准"));
				
				content.append(updateContent(oldPh.getHotelLevel(),ph.getHotelLevel(),"酒店级别"));
				
				content.append(updateContent(oldPh.getHotelType(),ph.getHotelType(),"酒店类型"));
				
				content.append(updateContent(oldPh.getHotelTopic(),ph.getHotelTopic(),"酒店主题"));
				
				content.append(updateContent(oldPh.getCheckinTime(),ph.getCheckinTime(),"入住时间"));
	
				content.append(updateContent(oldPh.getCheckoutTime(),ph.getCheckoutTime(),"离店时间"));
	
				content.append(updateContent(oldPh.getBreakfastType(),ph.getBreakfastType(),"早餐类型"));
	
				content.append(updateContent(oldPh.getBreakfastPrice(),ph.getBreakfastPrice(),"早餐价格"));
	
				content.append(updateContent(oldPh.getHotelForeigner(),ph.getHotelForeigner(),"酒店是否接待外宾"));
		
				content.append(updateContent(oldPh.getCreditCard(),ph.getCreditCard(),"接受信用卡"));
	
				content.append(updateContent(oldPh.getIntegratedFacilities(),ph.getIntegratedFacilities(),"综合设施"));
				
				content.append(updateContent(oldPh.getRoomFacilities(),ph.getRoomFacilities(),"客房设施"));
	
				content.append(updateContent(oldPh.getRecreationalFacilities(),ph.getRecreationalFacilities(),"娱乐设施"));
	
				content.append(updateContent(oldPh.getDiningFacilities(),ph.getDiningFacilities(),"餐饮设施"));
	
				content.append(updateContent(oldPh.getServices(),ph.getServices(),"服务项目"));
			}

		}

		
		if(oldPlace.getRemarkes()!=null && !oldPlace.getRemarkes().equals(newPlace.getRemarkes())){
			content.append("修改了[基本信息的简介];");
		}else if(oldPlace.getRemarkes()==null && newPlace.getRemarkes()!=null){
			content.append("添加了[基本信息的简介];");
		}
		
		if(oldPlace.getDescription()!=null && !oldPlace.getDescription().equals(newPlace.getDescription())){
			content.append("修改了[介绍];");
		}else if(oldPlace.getDescription()==null && newPlace.getDescription()!=null){
			content.append("添加了[介绍];");
		}
		if(""!=content.toString() && nowplace.getStage()!=null){
			
			String logType="";
			String logName="";
			if("1".equals(nowplace.getStage())){logType=Constant.SCENIC_LOG_PLACE.updatePlaceInfo.name();logName="修改目的地基本信息";}
			if("2".equals(nowplace.getStage())){logType=Constant.SCENIC_LOG_PLACE.updatePlaceInfo.name();logName="修改景点基本信息";}
			if("3".equals(nowplace.getStage())){logType=Constant.SCENIC_LOG_PLACE.updatePlaceInfo.name();logName="修改酒店基本信息";}
			
			comLogService.insert("SCENIC_LOG_PLACE", null, nowplace.getPlaceId(), userName,
					logType,logName, content.toString(), "");
			
		}
	}
	private void  insertComLog(Place place,String userName){
		StringBuffer content=new StringBuffer("");
		place = getPlaceByName(place.getName(),"N");
		
		String logName="";
		if("1".equals(place.getStage())){logName="新建目的地基本信息";}
		if("2".equals(place.getStage())){logName="新建景点基本信息";}
		if("3".equals(place.getStage())){logName="新建酒店基本信息";}
		content.append("["+logName+"];添加[名称]值为【"+place.getName()+"】;添加[状态]值为【无效】;添加[排序值]值为【0】;");
		comLogService.insert("SCENIC_LOG_PLACE", null, place.getPlaceId(), userName,
				Constant.SCENIC_LOG_PLACE.updatePlaceInfo.name(),logName, content.toString(), "");
	}
	
	public void savePlace(Place place,String userName) {
		
 		if(place.getPlaceId()!=null&&!"".equals(place.getPlaceId())){
			Place oldPlace = this.queryPlaceByPlaceId(place.getPlaceId());
			
			if (String.valueOf(Constant.STAGE_OF_HOTEL).equals(oldPlace.getStage())) {
				PlaceHotel ph = place.getPlaceHotel();
				if(ph!=null){
					if(ph.getHotelStar() != null){
				        if(ph.getHotelStar().equals("1") || ph.getHotelStar().equals("2")){
				            ph.setHotelLevel("二星级/简约");
				        }else if(ph.getHotelStar().equals("3") || ph.getHotelStar().equals("4")){
				            ph.setHotelLevel("三星级/舒适");
				        }else if(ph.getHotelStar().equals("5") || ph.getHotelStar().equals("6")){
				            ph.setHotelLevel("四星级/品质");
				        }else if(ph.getHotelStar().equals("7") || ph.getHotelStar().equals("8")){
				            ph.setHotelLevel("五星级/豪华");
				        }
				    }
				}
				
			}
			
			//更新PLACE数据
			placeDAO.updatePlace(place);
			
			// 如果更新的是酒店,需要更新PLACE_HOTEL酒店附加信息
			if (String.valueOf(Constant.STAGE_OF_HOTEL).equals(oldPlace.getStage())) {
				
				PlaceHotel ph = place.getPlaceHotel();
				//修复statuc2接受多个同名参数时,拼接之后 逗号之后存在空格的问题
				fixSpace(ph);
				
				if(ph == null || ph.getPlaceId() == null){
					ph.setPlaceId(place.getPlaceId());
					placeHotelDAO.insert(ph);
				}else{
					placeHotelDAO.update(ph);
				}
				
			}
			
			if(StringUtils.isNotEmpty(place.getFirstTopic())) {
				comSubjectDAO.updateUsedCount(place.getFirstTopic());
			}
			if(StringUtils.isNotEmpty(place.getScenicSecondTopic())) {
				comSubjectDAO.updateUsedCount(place.getScenicSecondTopic());
			}
			if(StringUtils.isNotEmpty(place.getFirstTopicOld())) {
				comSubjectDAO.updateUsedCount(place.getFirstTopicOld());
			}
			if(StringUtils.isNotEmpty(place.getScenicSecondTopicOld())) {
				comSubjectDAO.updateUsedCount(place.getScenicSecondTopicOld());
			}
			
			Place newPlace = this.queryPlaceByPlaceId(place.getPlaceId());
			
			//增加日志
			updateComLog(oldPlace,newPlace,place,userName);
			
		}else{
			//插入数据
			place.setPlaceId(placeDAO.getNextPlaceId());
			placeDAO.insertPlace(place);
			//增加日志
			insertComLog(place, userName);
		}
	}

	private void fixSpace(PlaceHotel ph){
		if(StringUtils.isNotBlank(ph.getHotelTopic())){
			ph.setHotelTopic(ph.getHotelTopic().replace(" ", ""));
		}
		if(StringUtils.isNotBlank(ph.getBreakfastType())){
			ph.setBreakfastType(ph.getBreakfastType().replace(" ", ""));
		}
		if(StringUtils.isNotBlank(ph.getBreakfastPrice())){
			ph.setBreakfastPrice(ph.getBreakfastPrice().replace(" ", ""));
		}
		if(StringUtils.isNotBlank(ph.getCreditCard())){
			ph.setCreditCard(ph.getCreditCard().replace(" ", ""));
		}
		if(StringUtils.isNotBlank(ph.getIntegratedFacilities())){
			ph.setIntegratedFacilities(ph.getIntegratedFacilities().replace(" ", ""));
		}
		if(StringUtils.isNotBlank(ph.getRoomFacilities())){
			ph.setRoomFacilities(ph.getRoomFacilities().replace(" ", ""));
		}
		if(StringUtils.isNotBlank(ph.getRecreationalFacilities())){
			ph.setRecreationalFacilities(ph.getRecreationalFacilities().replace(" ", ""));
		}
		if(StringUtils.isNotBlank(ph.getDiningFacilities())){
			ph.setDiningFacilities(ph.getDiningFacilities().replace(" ", ""));
		}
		if(StringUtils.isNotBlank(ph.getServices())){
			ph.setServices(ph.getServices().replace(" ", ""));
		}
	}

	public boolean isExistPlaceNameCheck(String placeName) {
		Map<String,Object> param=new HashMap<String,Object>();
		param.put("placeName", placeName);
		Long count=placeDAO.countPlaceList(param);
		if(count!=null&&count.longValue()!=0){
			return true;
		}else{
			return false;
		}
	}
	
	@Override
	public List<Place> getRouteFrom(final Map<String, Object> map) {
		if (map != null) {
			if (null == map.get("isValid")) {
				map.put("isValid", "Y");
			}
			if (null == map.get("productType")) {
				map.put("productType", Constant.PRODUCT_TYPE.ROUTE.name());
			}
			if (null == map.get("stage")) {
				map.put("stage", Constant.PLACE_STAGE.PLACE_FOR_DEST.getCode());
			}
			return placeDAO.getFromPlace(map);
		} else {
			return null;
		}
		
	}
	

	@Override
	public long selectSonPlaceCount(Long parentPlaceId, Long stage) {
		return placeDAO.selectSonPlaceCount(parentPlaceId, stage);
	}
	
	@Override
	public List<Place> getSonPlaceByParentPlaceId(long parentPlaceId,long rownum, Long stage) {
		return placeDAO.getSonPlaceByParentPlaceId(parentPlaceId, rownum, stage);
	}

	@Override
	public List<Place> getPlaceBySameParentPlaceId(Long parentPlaceId,Long stage) {
		return placeDAO.getPlaceBySameParentPlaceId(parentPlaceId, stage,null);
	}
	
	@Override
	public List<Place> getPlaceBySameParentPlaceId(Long parentPlaceId,Long stage,Long size) {
		return placeDAO.getPlaceBySameParentPlaceId(parentPlaceId, stage,size);
	}
	
	public List<Place> getPlaceInfoBySameParentPlaceId(Long parentPlaceId,String stage,Long size) {
		return placeDAO.getPlaceInfoBySameParentPlaceId(parentPlaceId, stage,size);
	}
	
	public List<Place> getPlaceInfoBySameParentPlaceIdTrain(Long parentPlaceId,String stage,Long size) {
		return placeDAO.getPlaceInfoBySameParentPlaceIdTrain(parentPlaceId, stage,size);
	}

	@Override
	public List<Place> getPlaceInfoBySameParentPlaceId(Long parentPlaceId,
	        String stage, Long size, String placeId) {
	    return placeDAO.getPlaceInfoBySameParentPlaceId(parentPlaceId, stage,size,placeId);
	}
	
	@Override
	public List<Place> getSonPlaceByPlaceIdAndStage(Place place) {
		return placeDAO.getSonPlaceByPlaceIdAndStage(place);
	}

	public List<Place> queryPlaceAutocomplate(String word,String stage) {
		return placeDAO.queryPlaceAutocomplate(word,stage);
	}
	public Place queryPlaceByPlaceId(Long placeId) {
		Place place =  placeDAO.findByPlaceId(placeId);
		if(null != place && "3".equals(place.getStage())){//如果place是酒店,查询酒店附加信息表PLACE_HOTEL
			PlaceHotel placeHotel = placeHotelDAO.searchPlaceHotel(placeId);
			place.setPlaceHotel(placeHotel);
		}
		return place;
	}
	@Override
	public Place queryPlaceAndComSearchTranscodeByPlaceId(Long param) {
		Place place =  placeDAO.queryPlaceAndComSearchTranscodeByPlaceId(param);
		return place;
	}
	
	@Override
	public Place getPlaceByPinYin(String pinYin) {
		return placeDAO.getPlaceByPinYin(pinYin);
	}

	@Override
	public List<Place> getPlaceByProductId(Long productId) {
		return placeDAO.getPlaceByProductId(productId);
	}

	/**
	 * 获取目的地  默认境外的推荐数据及各区域数据
	 * 
	 * @return
	 */
	public PlaceStateDests getDestRecommend(Map<String,Long> recommendBlockIds) {
		PlaceStateDests placeStateDests = new PlaceStateDests();
		int size=20;
		this.getAreaRecommendData(placeStateDests,recommendBlockIds,size);
		return placeStateDests;
	}
	
	/**
	 * 获取区域推荐数据
	 * 
	 * @param placeStateDests
	 * @param size
	 */
	private void getAreaRecommendData(PlaceStateDests placeStateDests,Map<String,Long> recommendBlockIds,int size){
		// 洲推荐
		Map<String,Object> param = new HashMap<String, Object>();
		param.put("recommendBlockId", recommendBlockIds.get("statesRecommendBlockId"));
		List<RecommendInfo> viewRecommendInfos = recommendInfoDAO.queryRecommendInfoByParam(param);
		convertUrl(viewRecommendInfos);
		placeStateDests.setStateDestsRecomm(viewRecommendInfos);

		// 亚洲
		placeStateDests.setAsiaDests(placeDAO.getCountryByParentPlaceId(recommendBlockIds.get("asiaPlaceId"), 1, size));

		// 欧洲
		placeStateDests.setEuropeDests(placeDAO.getCountryByParentPlaceId(recommendBlockIds.get("europePlaceId"), 1, size));

		// 美洲
		List<Place> america = new ArrayList<Place>();
		america.addAll(placeDAO.getCountryByParentPlaceId(recommendBlockIds.get("northAmericaPlaceId"), 1, size));// 北美洲
		america.addAll(placeDAO.getCountryByParentPlaceId(recommendBlockIds.get("southAmericaPlaceId"), 1, size));// 南美洲
		placeStateDests.setAmericaDests(america);

		// 非洲
		placeStateDests.setAfricaDests(placeDAO.getCountryByParentPlaceId(recommendBlockIds.get("africaPlaceId"), 1, size));

		// 大洋洲
		placeStateDests.setOceaniaDests(placeDAO.getCountryByParentPlaceId(recommendBlockIds.get("oceaniaPlaceId"), 1, size));
		
	}
	
	
	
	
	
	@Override
	public void replaceCmtTitle(Long placeId, Long productId, String cmtTitle, String userName) {
		//修改点评的维度用placeId去对应做
		Place place = this.queryPlaceByPlaceId(placeId);
		String newSubject = cmtTitle;
		if (null != place && "2".equalsIgnoreCase(place.getStage())) {
			//主题为空或没对应4个维度的默认"其它"主题
			if(StringUtils.isBlank(newSubject)){
				newSubject = "其它";
			}
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("subject", newSubject);
			List<DicCommentLatitude> latitudes = dicCommentLatitudeDAO.getDicCommentLatitudeList(param);
			if(latitudes.size() == 0){
				newSubject = "其它";
			}
			param.clear();
			param.put("subject", newSubject);
			List<DicCommentLatitude> latitudes_new = dicCommentLatitudeDAO.getDicCommentLatitudeList(param);
			
			param.clear();
			param.put("productId", productId);
			//一次性拉出产品的8个维度，2组list遍历新旧维度对应修改
			List<CmtLatitudeStatistics> cmtLatitudeStatistics = cmtLatitudeStatistisDAO.queryLatitudesByParam(param);
			if(cmtLatitudeStatistics.size() > 0){
				for(int i = 0; i < cmtLatitudeStatistics.size(); i++){
					param.clear();
					String oldLatitudeId = cmtLatitudeStatistics.get(i).getLatitudeId();
					param.put("oldLatitudeId", oldLatitudeId);
					
					if(latitudes_new.get(i%4) != null && !oldLatitudeId.equalsIgnoreCase(latitudes_new.get(i%4).getLatitudeId())){
						String newLatitudeId = latitudes_new.get(i%4).getLatitudeId();
						param.put("newLatitudeId", newLatitudeId);
						param.put("productId", productId);
						cmtLatitudeDAO.updateLatitudeForChangedCmtTitle(param);
					}
				}
			}
			//删除产品的维度统计
			param.clear();
			param.put("productId", productId);
			cmtLatitudeStatistisDAO.deleteLatitudeStatisticsByParam(param);
		}
	}
	
	
	
	
	
	@Override
	public void updateCmtTitle(Long placeId, String cmtTitle, String userName) {
		Place place = this.queryPlaceByPlaceId(placeId);
		String newSubject = cmtTitle;
		String oldSubject = place.getCmtTitle();//新主题替换当前景点点评主题
		if (null != place && "2".equalsIgnoreCase(place.getStage())) {
			//主题为空或没对应4个维度的默认"其它"主题
			if(StringUtils.isBlank(newSubject)){
				newSubject = "其它";
			}
			if(StringUtils.isBlank(oldSubject)){
				oldSubject = "其它";
			}
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("subject", newSubject);
			List<DicCommentLatitude> latitudes_newSubject = dicCommentLatitudeDAO.getDicCommentLatitudeList(param);
			if(latitudes_newSubject.size() == 0){
				newSubject = "其它";
			}
			param.clear();
			param.put("subject", oldSubject);
			List<DicCommentLatitude> latitudes_oldSubject = dicCommentLatitudeDAO.getDicCommentLatitudeList(param);
			if(latitudes_oldSubject.size() == 0){
				oldSubject = "其它";
			}
			if(newSubject != null && !newSubject.equalsIgnoreCase(oldSubject)){
				param.clear();
				param.put("newSubject", newSubject);
				param.put("oldSubject", oldSubject);
				List<DicCommentLatitude> dicCommentLatitudeMapping = dicCommentLatitudeDAO.queryUpdateLatitudeMapping(param);
				for (DicCommentLatitude latitude : dicCommentLatitudeMapping) {
					param.clear();
					param.put("oldLatitudeId", latitude.getOldLatitudeId());
					param.put("newLatitudeId", latitude.getNewLatitudeId());
					param.put("placeId", placeId);
					cmtLatitudeDAO.updateLatitudeForChangedCmtTitle(param);
					cmtLatitudeStatistisDAO.updateLatitudeForChangedCmtTitle(param);
				}
			}
			
			place.setCmtTitle(cmtTitle);
			placeDAO.updatePlace(place);
			
			comLogService.insert("SCENIC_LOG_PLACE", null,placeId, userName,
					Constant.SCENIC_LOG_PLACE.updatePlaceInfo.name(),"修改点评主题", "修改[点评主题]值为【"+ cmtTitle +"】", "");
		}
	}
	
	private void convertUrl(List<RecommendInfo> viewRecommendInfos) {
		for (RecommendInfo viewRecommendInfo : viewRecommendInfos) {
			String id ="";
			try {
			String url = viewRecommendInfo.getUrl();
			String suffix = url.substring(url.lastIndexOf("/") + 1);
			if (suffix.startsWith("place")) {
				id = suffix.replace("place", "");
				Place place = queryPlaceByPlaceId(Long.valueOf(id));
					if (place != null) {
						viewRecommendInfo.setUrl("http://www.lvmama.com/dest/" + place.getPinYinUrl());
					}
			}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	@Override
	public List<Place> selectSuggestPlaceByName(String name) {
		return this.placeDAO.selectSuggestPlaceByName(name);
	}
	@Override
	public List<Place> selectSuggestPlaceByNameEBK(String name) {
		return this.placeDAO.selectSuggestPlaceByNameEBK(name);
	}
	@Override
	public List<Place> getRootDest(){
		return this.placeDAO.getRootDest();
	}
	/**
	 * 面包屑
	 */
	public Map<String,Object> loadNavigation(Long placeId){
		Place place = placeDAO.findByPlaceId(placeId);
		
		Map<String,Object> navigationMap=new HashMap<String, Object>();
		List<Place> placeNavigation = new ArrayList<Place>();
		Map<Long, List<Place>> placeDest = new HashMap<Long, List<Place>>();
		List<Place> placesBlocks = new ArrayList<Place>();
		
		navigationMap.put("place", place);
		if (place!=null && place.getParentPlaceId() != null) {
				placesBlocks = loadPlaces(placesBlocks,place);
				// 层级变成从大到小。中国》江苏》南京
				for (int i = placesBlocks.size() - 1; i >= 0; i--) {
					Place p = placesBlocks.get(i);
					if(p==null) {
						continue;
					}
					if (p.getParentPlaceId()!=null) {
						List<Place> citisDest = this.placeDAO.getPlaceBySameParentPlaceId(p.getParentPlaceId(), 1L,null);
						placeDest.put(p.getPlaceId(), citisDest);
					}
					placeNavigation.add(p);
				}
				// 如果最后一级是目的地时，查找当前目的地同级
				if (place != null && "1".equals(place.getStage())) {
					List<Place> citisDest = this.placeDAO.getPlaceBySameParentPlaceId(place.getParentPlaceId(),  1L,null);
					placeNavigation.add(place);
					placeDest.put(place.getPlaceId(), citisDest);
				}
				navigationMap.put("placeNavigation", placeNavigation);
				navigationMap.put("placeDest", placeDest);
		}
		return navigationMap;
	}
	
	private List<Place> loadPlaces(List<Place> placesBlocks ,Place place) {
		if (place != null) {
			Long destId = place.getParentPlaceId();
			if (destId != null) {
				Place comPlace = this.placeDAO.findByPlaceId(Long.valueOf(destId));
				placesBlocks.add(comPlace);
				if (comPlace != null && comPlace.getParentPlaceId() != null && !destId.equals(comPlace.getParentPlaceId())) {
					loadPlaces(placesBlocks,comPlace);
				}
			} else {
				placesBlocks.add(place);
			}
		}
		return placesBlocks;
	}

	public List<Place> getPlaceByProductIdAndStage(Long productId,Long stage){
		 return placeDAO.getPlaceByProductIdAndStage(productId, stage);
	}

	@Override
	public List<Place> selectDestByRootId(Long id) {
		return placeDAO.selectDestByRootId(id);
	}

	@Override
	public Place getPlaceByName(String name,String valid) {
		return placeDAO.getPlaceByName(name,valid);
	}

	@Override
	public List<Place> getPlaceListByParentIds(Map<String, List> param) {
		return placeDAO.getPlaceByParentIds(param);
	}

	@Override
	public List<Place> getCountryRecord() {
		return placeDAO.getCountryRecord();
	}

	@Override
	public Place getDescripAndTrafficByPlaceId(Long id) {
		 
		return placeDAO.getDescripAndTrafficByPlaceId(id);
	}

	@Override
	public Place getPlaceByPinYinWithOutCLOB(String pinYin) {
		return placeDAO.getPlaceByPinYinWithOutCLOB(pinYin);
	}

	@Override
	public Place queryPlaceByPlaceIdWithOutCLOB(long placeId) {
		return placeDAO.queryPlaceByPlaceIdWithOutCLOB(placeId);
	}

	@Override
	public List<Place> queryParentPlace(Long parentPalceId) {
		return placeDAO.getListPlaceByParentIds(parentPalceId);
	}

	private void updateHasSensitiveWordByPlaceId(Long placeId,
			boolean hasSensitiveWord) {
		String str = "N";
		if(hasSensitiveWord) {
			str = "Y";
		}
		placeDAO.updateHasSensitiveWordByPlaceId(placeId, str);
	}
	
	private SensitiveWordService sensitiveWordService;
	
	private PlaceActivityDAO placeActivityDAO;
	
	private PlaceHotelNoticeDao placeHotelNoticeDao;
	
	private PlaceHotelRoomDao placeHotelRoomDao;
	
	private PlaceHotelRecommendDao placeHotelRecommendDao;

	@Override
	public boolean checkAndUpdateIsHasSensitiveWords(Long placeId) {
		if(placeId != null) {
			Place place = queryPlaceByPlaceId(placeId);
			boolean flag = validateSW_1(place);//为true则为有敏感词
			updateHasSensitiveWordByPlaceId(placeId, flag);
			return flag;
		}
		return false;
	}

	//校验产品各部分信息是否包含敏感词
    private boolean validateSW_1(Place place) {
    	if(place != null) {
	    	Long placeId = place.getPlaceId();
	    	Pattern p_html = Pattern.compile("<[^>]+>", Pattern.CASE_INSENSITIVE);
	    	//名称
	    	if(sensitiveWordService.checkSensitiveWords(place.getName())) {
	    		return true;
	    	}
	    	//地址
	    	if(sensitiveWordService.checkSensitiveWords(place.getAddress())) {
	    		return true;
	    	}
	    	//高频关键字
	    	if(sensitiveWordService.checkSensitiveWords(place.getHfkw())) {
	    		return true;
	    	}
	    	//简介
	    	if(StringUtils.isNotEmpty(place.getRemarkes())) {
				Matcher m_html = p_html.matcher(place.getRemarkes());
		        String content = m_html.replaceAll("");
		        if(sensitiveWordService.checkSensitiveWords(content)) {
		    		return true;
		    	}
			}
	    	
	    	//**************************景点*************************
	    	if("2".equals(place.getStage())) {
		    	//优化别名
		    	if(sensitiveWordService.checkSensitiveWords(place.getSeoName())) {
		    		return true;
		    	}
		    	//景区介绍
		    	if(StringUtils.isNotEmpty(place.getDescription())) {
			    	Matcher m_html = p_html.matcher(place.getDescription());
			        String description = m_html.replaceAll("");
					if(sensitiveWordService.checkSensitiveWords(description)) {
			    		return true;
			    	}
		    	}
				
				//景点活动
				List<PlaceActivity> paList = placeActivityDAO.queryPlaceActivityListByPlaceId(placeId);
				if(!paList.isEmpty()) {
					for (int i = 0; i < paList.size(); i++) {
						PlaceActivity pa = paList.get(i);
						//标题
						if(sensitiveWordService.checkSensitiveWords(pa.getTitle())) {
				    		return true;
				    	}
						//内容
						if(StringUtils.isNotEmpty(pa.getContent())) {
							Matcher m_html = p_html.matcher(pa.getContent());
					        String content = m_html.replaceAll("");
							if(sensitiveWordService.checkSensitiveWords(content)) {
					    		return true;
					    	}
						}
					}
				}
				//**************************景点*************************
				//**************************酒店*************************
	    	} else if("3".equals(place.getStage())) {
	    		//酒店位置
				if(sensitiveWordService.checkSensitiveWords(place.getPlaceHotel().getHotelPosition())) {
		    		return true;
		    	}
		    	//一句话推荐
		    	PlaceHotelNotice phn = new PlaceHotelNotice();
		    	phn.setNoticeType(PlaceUtils.RECOMMEND);
		    	phn.setPlaceId(placeId);
		    	List<PlaceHotelNotice> phnList = placeHotelNoticeDao.queryByHotelNotice(phn);
		    	if(!phnList.isEmpty()) {
					for (int i = 0; i < phnList.size(); i++) {
						PlaceHotelNotice pn = phnList.get(i);
						//公告内容
						if(sensitiveWordService.checkSensitiveWords(pn.getNoticeContent())) {
				    		return true;
				    	}
					}
				}
		    	//房型
		    	PlaceHotelRoom phr = new PlaceHotelRoom();
		    	phr.setPlaceId(placeId);
		    	List<PlaceHotelRoom> phrList = placeHotelRoomDao.queryAllPlaceHotelRoom(phr);
		    	if(!phrList.isEmpty()) {
					for (int i = 0; i < phrList.size(); i++) {
						PlaceHotelRoom pr = phrList.get(i);
						//房型名称
						if(sensitiveWordService.checkSensitiveWords(pr.getRoomName())) {
				    		return true;
				    	}
						//房型介绍
						if(StringUtils.isNotEmpty(pr.getRoomRecommend())) {
							Matcher m_html = p_html.matcher(pr.getRoomRecommend());
					        String roomRecommend = m_html.replaceAll("");
							if(sensitiveWordService.checkSensitiveWords(roomRecommend)) {
					    		return true;
					    	}
						}
					}
				}
		    	//酒店特色和玩法
		    	PlaceHotelOtherRecommend phor = new PlaceHotelOtherRecommend();
		    	phor.setPlaceId(placeId);
		    	List<PlaceHotelOtherRecommend> phorList = placeHotelRecommendDao.queryAllPlaceHotelRecommen(phor);
		    	if(!phorList.isEmpty()) {
					for (int i = 0; i < phorList.size(); i++) {
						PlaceHotelOtherRecommend pr = phorList.get(i);
						//名称
						if(sensitiveWordService.checkSensitiveWords(pr.getRecommendName())) {
				    		return true;
				    	}
						//介绍
						if(StringUtils.isNotEmpty(pr.getRecommentContent())) {
							Matcher m_html = p_html.matcher(pr.getRecommentContent());
					        String recommentContent = m_html.replaceAll("");
							if(sensitiveWordService.checkSensitiveWords(recommentContent)) {
					    		return true;
					    	}
						}
					}
				}
	    	}
	    	//**************************酒店*************************
		}
		return false;
    }
    
	public void setSensitiveWordService(SensitiveWordService sensitiveWordService) {
		this.sensitiveWordService = sensitiveWordService;
	}

	public void setPlaceActivityDAO(PlaceActivityDAO placeActivityDAO) {
		this.placeActivityDAO = placeActivityDAO;
	}

	public void setPlaceHotelDAO(PlaceHotelDAO placeHotelDAO) {
		this.placeHotelDAO = placeHotelDAO;
	}

	public void setPlaceHotelNoticeDao(PlaceHotelNoticeDao placeHotelNoticeDao) {
		this.placeHotelNoticeDao = placeHotelNoticeDao;
	}

	public void setPlaceHotelRecommendDao(
			PlaceHotelRecommendDao placeHotelRecommendDao) {
		this.placeHotelRecommendDao = placeHotelRecommendDao;
	}

	public void setPlaceDAO(PlaceDAO placeDAO) {
		this.placeDAO = placeDAO;
	}

	public void setRecommendInfoDAO(RecommendInfoDAO recommendInfoDAO) {
		this.recommendInfoDAO = recommendInfoDAO;
	}

	public void setComSubjectDAO(ComSubjectDAO comSubjectDAO) {
		this.comSubjectDAO = comSubjectDAO;
	}

	public void setComLogService(ComLogService comLogService) {
		this.comLogService = comLogService;
	}

	public void setDicCommentLatitudeDAO(DicCommentLatitudeDAO dicCommentLatitudeDAO) {
		this.dicCommentLatitudeDAO = dicCommentLatitudeDAO;
	}

	public void setCmtLatitudeDAO(CmtLatitudeDAO cmtLatitudeDAO) {
		this.cmtLatitudeDAO = cmtLatitudeDAO;
	}

	public void setCmtLatitudeStatistisDAO(
			CmtLatitudeStatistisDAO cmtLatitudeStatistisDAO) {
		this.cmtLatitudeStatistisDAO = cmtLatitudeStatistisDAO;
	}

	public void setPlaceHotelRoomDao(PlaceHotelRoomDao placeHotelRoomDao) {
		this.placeHotelRoomDao = placeHotelRoomDao;
	}

	@Override
	public void markPlaceSensitive(Long placeId, String hasSensitiveWord) {
		if(placeId != null) {
    		//记录敏感词
			if(StringUtils.isNotEmpty(hasSensitiveWord) && "Y".equals(hasSensitiveWord)) {
				updateHasSensitiveWordByPlaceId(placeId, true);
			} else {
				checkAndUpdateIsHasSensitiveWords(placeId);
			}
		}
	}
}
