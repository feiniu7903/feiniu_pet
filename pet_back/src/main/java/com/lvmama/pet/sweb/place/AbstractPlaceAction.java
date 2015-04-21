package com.lvmama.pet.sweb.place;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.comm.BackBaseAction;
import com.lvmama.comm.bee.service.SmsService;
import com.lvmama.comm.jms.MessageFactory;
import com.lvmama.comm.jms.TopicMessageProducer;
import com.lvmama.comm.pet.po.place.Place;
import com.lvmama.comm.pet.po.place.PlaceSearchPinyin;
import com.lvmama.comm.pet.po.pub.ComSms;
import com.lvmama.comm.pet.po.pub.ComSubject;
import com.lvmama.comm.pet.po.search.ProductSearchInfo;
import com.lvmama.comm.pet.service.place.PlacePlaceDestService;
import com.lvmama.comm.pet.service.place.PlaceSearchPinyinService;
import com.lvmama.comm.pet.service.place.PlaceService;
import com.lvmama.comm.pet.service.pub.ComSearchInfoUpdateService;
import com.lvmama.comm.pet.service.pub.ComSubjectService;
import com.lvmama.comm.pet.service.search.ProductSearchInfoService;
import com.lvmama.comm.pet.vo.PlaceVo;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.PageElementModel;
import com.lvmama.comm.vo.enums.ComSubjectTypeEnum;


@Results( {
	/**产品SEQ调整**/
	@Result(name = "productSeqList", location = "/WEB-INF/pages/back/place/product_seq_list.jsp")	
})
public abstract  class AbstractPlaceAction extends BackBaseAction {
	private TopicMessageProducer productMessageProducer;
	/**
	 * 每次修改place信息之后需要向COM_SEARCH_INFO_UPDATE表中增加一条记录，用于同步PLACE表与PLACE_SEARCH_INFO的信息
	 * add by yanggan 
	 */
	protected ComSearchInfoUpdateService comSearchInfoUpdateService;
	/**
	 * 景点/酒店/目的地/产品公用ACTION
	 */
	private static final long serialVersionUID = 1L;
	private PlaceService placeService;
	private PlacePlaceDestService placePlaceDestService;
	private ComSubjectService comSubjectService;
	private ProductSearchInfoService productSearchInfoService;
	protected PlaceSearchPinyinService placeSearchPinyinService;
	private List<ComSubject> subjectList;
	private List<PlaceVo> placeVoList;
	private String stage;
	private Place place;
	private List<Place> placeList;
	public List<Place> getPlaceList() {
		return placeList;
	}
	public void setPlaceList(List<Place> placeList) {
		this.placeList = placeList;
	}

	private ProductSearchInfo productSearchInfo;
	private List<ProductSearchInfo> viewProductSearchInfoList;
	private List<PageElementModel> placeTypeList=Collections.emptyList();
	private List<PageElementModel> isValidList=Collections.emptyList();
	private List<PageElementModel> templateList=Collections.emptyList();
	private List<PageElementModel> isHasActivityList=Collections.emptyList();
	private List<PageElementModel> scenicGradeList=Collections.emptyList();
	private List<PageElementModel>  productTypeList=Collections.emptyList();
	private List<PageElementModel>  subProductTypeList=Collections.emptyList();
	private List<PageElementModel>  isExitList=Collections.emptyList();
	private List<PlaceSearchPinyin> pinyinList = new ArrayList<PlaceSearchPinyin>();
	private String placeIds;
	private String placePhotoIds;
	private String placeProductIds;
	private String placeId;
	private String msg;
	private String autoPlaceName;
	private String word;
	private String productType;
	private String oldPlaceName;
	private String savePlaceNameFlag="Y";
	private String startDate;
	private String endDate;
	private SmsService smsService;
	private String hasSensitiveWord;

	public String getSavePlaceNameFlag() {
		return savePlaceNameFlag;
	}
	public void setSavePlaceNameFlag(String savePlaceNameFlag) {
		this.savePlaceNameFlag = savePlaceNameFlag;
	}

	private List<ProductSearchInfo> productSearchInfoList;
	public String getWord() {
		return word;
	}
	public void setWord(String word) {
		this.word = word;
	}
	/**
	 * 产品SEQ排序查询
	 */
	@Action("/place/productSeqList")
	public String productSeqList() throws Exception {		
		productList();
		return "productSeqList";
	}
	protected void productList(){
		pagination=initPage();
		pagination.setPageSize(15);
		Map<String,Object> param=productSearchInfoInit(productSearchInfo);
		pagination.setTotalResultSize(productSearchInfoService.countProductSearchInfoByParam(param));
		if(pagination.getTotalResultSize()>0){
			param.put("startRows", pagination.getStartRows());
			param.put("endRows", pagination.getEndRows());
			viewProductSearchInfoList=productSearchInfoService.queryProductSearchInfoByParam(param);
		}
		pagination.buildUrl(getRequest());
		initPageElement();
	}
	@Action("/place/saveProductSeq")
	protected void saveProductSeq() throws Exception {
		if(StringUtils.isNotBlank(placeProductIds)){
			try{
				this.productSearchInfoService.batchSaveProductSeq(this.getPlaceProductIds());		
				
					String[] items=placeProductIds.split(",");
					if(items.length>0){
						for(String item:items){
							String[] product=item.split("_");			
							productMessageProducer.sendMsg(MessageFactory.newProductUpdateMessage(Long.parseLong(product[0])));
						}
					}
				
				
				this.outputToClient("success");
			}catch(Exception ex){
				ex.printStackTrace();
				this.outputToClient("error");
			}			
		}
	}
	
	//层级关系
 	private String placeSuperior;
	public abstract void setCurrentStage();
	
	protected void list(){
		setCurrentStage();
		pagination=initPage();
		pagination.setPageSize(15);
		Map<String,Object> param=builderParam(place);
		pagination.setTotalResultSize(placeService.countPlaceListByParam(param));
		if(pagination.getTotalResultSize()>0){
			param.put("startRows", pagination.getStartRows());
			param.put("endRows", pagination.getEndRows());
			placeList=placeService.queryPlaceListByParam(param);
			//placeVoList=placePlaceDestService.calculationPlaceSuperior(list);
		}
		pagination.buildUrl(getRequest());
		initPageElement();
	}
	@Action("/place/saveSeq")
	public String saveseq() throws Exception {
		try{
			this.getPlaceService().batchSavePlaceSeq(this.getPlaceIds(),this.getSessionUserName());
			this.outputToClient("success");
		}catch(Exception ex){
			ex.printStackTrace();
			this.outputToClient("error");
		}
		return null;
	}
	@Action("/place/autocomplate")
	public String autocomplate() throws Exception {
		try{
			if(StringUtils.isEmpty(stage)){
				stage="1";
			}
			List<Place> placeList=placeService.queryPlaceAutocomplate(word,stage);
			StringBuffer sb=new StringBuffer("<words>");
			for(Place place:placeList){
				sb.append(" <word placeId='"+place.getPlaceId()+"'>"+place.getName()+"</word>");
			}
			sb.append("</words>");
			this.outputToClient(sb.toString());
		}catch(Exception ex){
			ex.printStackTrace();
			this.outputToClient("error");
		}
		return null;
	}
	@Action("/place/isExistCheck")
	public String isExistCheck() throws Exception {
		try{
			if(place!=null&&place.getName()!=null&&!"".equals(place.getName())){
				if(placeService.isExistPlaceNameCheck(place.getName())){
					this.outputToClient("Y");//已经存在
				}else{
					if(savePlaceNameFlag.equals("Y")){
						place.setStage(stage);
						place.setIsValid("N");
						place.setSeq(0L);
						placeService.savePlace(place,this.getSessionUserName());
						//添加操作日志
						place=placeService.getPlaceByName(place.getName(),"N");
						this.outputToClient(""+place.getPlaceId());//已经存在
					}
					
				}
			}
		}catch(Exception ex){
			ex.printStackTrace();
			this.outputToClient("error");
		}
		return null;
	}
	protected void relationProduct() throws Exception {
		if(StringUtils.isNotBlank(placeId)){
			Map<String,Object> param=new HashMap<String,Object>();
			param.put("placeId", placeId);
			param.put("productType", productType);
			productSearchInfoList=productSearchInfoService.queryProductSearchInfoByParam(param);
		}
	}	
	protected void edit() {
		if(StringUtils.isNotBlank(placeId)){
			place=placeService.queryPlaceByPlaceId(new Long(placeId));
			PlaceSearchPinyin param = new PlaceSearchPinyin();
			param.setObjectId(new Long(placeId));
			param.setObjectType(Constant.PLACE_SEARCH_PINYIN_OBJECT_TYPE_PLACE_NAME);
			pinyinList = placeSearchPinyinService.queryPlacePinyinList(param);
			placeSuperior=placePlaceDestService.queryPlaceSuperior(new Long(placeId),place.getName());
		}
		initData();
	}
	protected void initPlaceByPlaceId(){
		if(StringUtils.isNotBlank(placeId)){
			place=placeService.queryPlaceByPlaceId(new Long(placeId));
		}
	}
	protected void add() {
		initData();
	}
	protected void save() {
		placeService.savePlace(place);
		
		comSearchInfoUpdateService.placeUpdated(place.getPlaceId(),place.getStage());
		initData();
	}

	protected void update() {
        if(StringUtils.isNotBlank(place.getFirstTopic()) && StringUtils.isBlank(place.getFirstTopicOld()) && StringUtils.isBlank(place.getCmtTitle())){
            place.setCmtTitle(place.getFirstTopic());
        }else{
            if( null!=place.getFirstTopicOld() && null != place.getFirstTopic() && !place.getFirstTopicOld().equals(place.getFirstTopic())){
                Place pla = placeService.queryPlaceByPlaceId(place.getPlaceId());
                ComSms sms = new ComSms();
                sms.setMobile("15821191479");
                sms.setContent("温馨提示 [驴妈妈后台数据变化],景点ID'"+place.getPlaceId()+"',名称'"+pla.getName()+"'的类型由'"+place.getFirstTopicOld()+"'更改为'"+place.getFirstTopic()+"'状态修改时间为:"+DateUtil.formatDate(new Date(),"yyyy-MM-dd HH:mm:ss"));
                sms.setObjectId(null);
                sms.setObjectType(null);
                sms.setMms("false");
                smsService.sendSms(sms);
                System.out.println("LOG :如果对景点的主主题操作, 则发送短信提醒, 短信发送成功 !");
            }
        } 
        
        placeService.savePlace(place,super.getSessionUserName());
        placeSuperior=placePlaceDestService.queryPlaceSuperior(place.getPlaceId(),place.getName());
        comSearchInfoUpdateService.placeUpdated(place.getPlaceId(),place.getStage());
        
        placeService.markPlaceSensitive(place.getPlaceId(), hasSensitiveWord);
        initData();
	}
	
	protected Map<String, Object> productSearchInfoInit(ProductSearchInfo productSearchInfo) {
		Map<String, Object> param = new HashMap<String, Object>();		
		if(productSearchInfo!=null){
			if (productSearchInfo.getProductAlltoPlaceIds()!=null &&!"".equals(productSearchInfo.getProductAlltoPlaceIds())) {
				param.put("productAlltoPlaceIds",productSearchInfo.getProductAlltoPlaceIds());
			}
			if (productSearchInfo.getProductAlltoPlaceContent()!=null &&!"".equals(productSearchInfo.getProductAlltoPlaceContent())) {
				param.put("productAlltoPlaceContent",productSearchInfo.getProductAlltoPlaceContent());
			}
			if (productSearchInfo.getProductAlltoPlace()!=null &&!"".equals(productSearchInfo.getProductAlltoPlace())) {
				param.put("productAlltoPlace",productSearchInfo.getProductAlltoPlace());
			}
			if (productSearchInfo.getFromDestContent()!=null &&!"".equals(productSearchInfo.getFromDestContent())) {
				param.put("fromDestContent",productSearchInfo.getFromDestContent());
			}
			if (productSearchInfo.getFromDest()!=null &&!"".equals(productSearchInfo.getFromDest())) {
				param.put("fromDest",productSearchInfo.getFromDest());
			}
			if (productSearchInfo.getSubProductType()!=null &&!"".equals(productSearchInfo.getSubProductType())) {
				param.put("subProductType",new ArrayList<String>().add("productSearchInfo.getSubProductType()"));
			}
			if (productSearchInfo.getProductType()!=null &&!"".equals(productSearchInfo.getProductType())) {
				param.put("productType",productSearchInfo.getProductType());
			}
		}
		return param;
	}
	protected Map<String, Object> builderParam(Place place) {
		Map<String, Object> param = new HashMap<String, Object>();
		List<String> stages = new ArrayList<String>();
		stages.add(stage);
		if("1".equals(stage)){//如果是查询目的地，则包含查询stage=0的数据（国家，州）
			stages.add("0");
		}
		param.put("stages", stages);
		if(place!=null){
			if(place.getPlaceId()!=null&&!"".equals(place.getPlaceId())){
				param.put("placeId", place.getPlaceId());
			}
			if(place.getName()!=null&&!"".equals(place.getName())){
				param.put("name", place.getName());
			}
			if(place.getIsValid()!=null&&!"".equals(place.getIsValid())){
				param.put("isValid", place.getIsValid());
			}
			if(place.getTemplate()!=null&&!"".equals(place.getTemplate())){
				param.put("template", place.getTemplate());
			}
			if(place.getIsExit()!=null&&!"".equals(place.getIsExit())){
				param.put("isExit", place.getIsExit());
			}
			if(place.getPlaceType()!=null&&!"".equals(place.getPlaceType())){
				param.put("placeType", place.getPlaceType());
			}
			if(place.getParentPlaceId()!=null&&!"".equals(place.getParentPlaceId())){
				param.put("parentPlaceId", place.getParentPlaceId());
			}
			if(place.getParentPlaceName()!=null&&!"".equals(place.getParentPlaceName())){
				param.put("parentPlaceName", place.getParentPlaceName());
			}
			if(place.getFirstTopic()!=null&&!"".equals(place.getFirstTopic())){
				param.put("firstTopic", place.getFirstTopic());
			}
			if(place.getIsHasActivity()!=null&&!"".equals(place.getIsHasActivity())){
				param.put("isHasActivity", place.getIsHasActivity());
			}
			
			
		}	
		if(startDate!=null&&!"".equals(startDate)){
			 
			param.put("startDate",convertToDate(startDate+" 00:00:00"));
		}
		if(endDate!=null&&!"".equals(endDate)){
			 
			param.put("endDate",convertToDate(endDate+" 23:59:59"));
		}
			
		return param;
	}
	private Date convertToDate(String dateStr){
		try {
			SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			return format.parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	private void initPageElement(){
		placeTypeList=Constant.PLACE_TYPE.getList();
		isValidList=Constant.IS_VALID.getList();
		templateList=Constant.TEMPLATE.getList();
		isHasActivityList=Constant.IS_HAS_ACTIVITY.getList();
		scenicGradeList=Constant.SCENIC_GRADE.getList();
		productTypeList=Constant.PRODUCT_TYPE.getList();
		subProductTypeList=Constant.SUB_PRODUCT_TYPE.getList();
		isExitList=Constant.IS_EXIT.getList(); //新加字段 （国内、国外）
		setCurrentStage();
	}
	 void initSubjectList(){
		if(stage.equals("2")){
			ComSubject comSubject = new ComSubject();
			comSubject.setSubjectType(ComSubjectTypeEnum.PLACE.getCode());
			subjectList=comSubjectService.findComSubjects(comSubject,Integer.MAX_VALUE);
		}
		if(stage.equals("3")){
			ComSubject comSubject = new ComSubject();
			comSubject.setSubjectType(ComSubjectTypeEnum.HOTEL.getCode());
			comSubject.setIsValid("Y");
			subjectList=comSubjectService.findComSubjects(comSubject,Integer.MAX_VALUE);
			/*
			 * 以下逻辑是合并 酒店主题中包含的主题名称 但是此主题名称不存在于COM_SUBJECT主题表中
			 * 合并后显示在酒店编辑页面
			 */
			List<String> subjectNameList = new ArrayList<String>();
			for(ComSubject cs : subjectList){
				subjectNameList.add(cs.getSubjectName());
			}
			if(place!=null && place.getPlaceHotel() != null){
				String  hotelTopic = place.getPlaceHotel().getHotelTopic();
				if(StringUtils.isNotEmpty(hotelTopic)){
					String[] hotelTopics = hotelTopic.split(",");
					for(String s : hotelTopics ){
						if(!subjectNameList.contains(s.trim())){
							ComSubject cs = new ComSubject();
							cs.setSubjectName(s);
							cs.setComSubjectId(-1l);
							subjectList.add(0,cs);
						}
					}
				}
			}
		}
	}
	/**动态拿到增加页面中的所有下拉菜单数据**/
	protected void initData(){
		initPageElement();
		initSubjectList();
	}
	
	public List<ProductSearchInfo> getViewProductSearchInfoList() {
		return viewProductSearchInfoList;
	}
	public void setViewProductSearchInfoList(List<ProductSearchInfo> viewProductSearchInfoList) {
		this.viewProductSearchInfoList = viewProductSearchInfoList;
	}
	public ProductSearchInfo getProductSearchInfo() {
		return productSearchInfo;
	}
	public void setProductSearchInfo(ProductSearchInfo productSearchInfo) {
		this.productSearchInfo = productSearchInfo;
	}
	public PlaceService getPlaceService() {
		return placeService;
	}
	public void setPlaceService(PlaceService placeService) {
		this.placeService = placeService;
	}
	public PlacePlaceDestService getPlacePlaceDestService() {
		return placePlaceDestService;
	}
	public void setPlacePlaceDestService(PlacePlaceDestService placePlaceDestService) {
		this.placePlaceDestService = placePlaceDestService;
	}
	public ComSubjectService getComSubjectService() {
		return comSubjectService;
	}
	public void setComSubjectService(ComSubjectService comSubjectService) {
		this.comSubjectService = comSubjectService;
	}
	public List<ComSubject> getSubjectList() {
		return subjectList;
	}
	public void setSubjectList(List<ComSubject> subjectList) {
		this.subjectList = subjectList;
	}
	public List<PlaceVo> getPlaceVoList() {
		return placeVoList;
	}
	public void setPlaceVoList(List<PlaceVo> placeVoList) {
		this.placeVoList = placeVoList;
	}
	public String getStage() {
		return stage;
	}
	public void setStage(String stage) {
		this.stage = stage;
	}
	public Place getPlace() {
		return place;
	}
	public void setPlace(Place place) {
		this.place = place;
	}
	public List<PageElementModel> getPlaceTypeList() {
		return placeTypeList;
	}
	public void setPlaceTypeList(List<PageElementModel> placeTypeList) {
		this.placeTypeList = placeTypeList;
	}
	public List<PageElementModel> getIsValidList() {
		return isValidList;
	}
	public void setIsValidList(List<PageElementModel> isValidList) {
		this.isValidList = isValidList;
	}
	public List<PageElementModel> getProductTypeList() {
		return productTypeList;
	}
	public void setProductTypeList(List<PageElementModel> productTypeList) {
		this.productTypeList = productTypeList;
	}
	public List<PageElementModel> getSubProductTypeList() {
		return subProductTypeList;
	}
	public void setSubProductTypeList(List<PageElementModel> subProductTypeList) {
		this.subProductTypeList = subProductTypeList;
	}
	public List<PageElementModel> getTemplateList() {
		return templateList;
	}
	public void setTemplateList(List<PageElementModel> templateList) {
		this.templateList = templateList;
	}
	public List<PageElementModel> getIsExitList() {
		return isExitList;
	}
	public void setIsExitList(List<PageElementModel> isExitList) {
		this.isExitList = isExitList;
	}
	public List<PageElementModel> getIsHasActivityList() {
		return isHasActivityList;
	}
	public void setIsHasActivityList(List<PageElementModel> isHasActivityList) {
		this.isHasActivityList = isHasActivityList;
	}
	public List<PageElementModel> getScenicGradeList() {
		return scenicGradeList;
	}
	public void setScenicGradeList(List<PageElementModel> scenicGradeList) {
		this.scenicGradeList = scenicGradeList;
	}
	public String getPlaceIds() {
		return placeIds;
	}
	public void setPlaceIds(String placeIds) {
		this.placeIds = placeIds;
	}
	public String getPlaceProductIds() {
		return placeProductIds;
	}
	public void setPlaceProductIds(String placeProductIds) {
		this.placeProductIds = placeProductIds;
	}
	public String getPlaceId() {
		return placeId;
	}
	public void setPlaceId(String placeId) {
		this.placeId = placeId;
	}
	
	public String getPlacePhotoIds() {
		return placePhotoIds;
	}
	public void setPlacePhotoIds(String placePhotoIds) {
		this.placePhotoIds = placePhotoIds;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}	
	public String getPlaceSuperior() {
		return placeSuperior;
	}
	public void setPlaceSuperior(String placeSuperior) {
		this.placeSuperior = placeSuperior;
	}
	public String getAutoPlaceName() {
		return autoPlaceName;
	}
	public void setAutoPlaceName(String autoPlaceName) {
		this.autoPlaceName = autoPlaceName;
	}
	public ProductSearchInfoService getProductSearchInfoService() {
		return productSearchInfoService;
	}
	public void setProductSearchInfoService(
			ProductSearchInfoService productSearchInfoService) {
		this.productSearchInfoService = productSearchInfoService;
	}
	public String getProductType() {
		return productType;
	}
	public void setProductType(String productType) {
		this.productType = productType;
	}
	public List<ProductSearchInfo> getProductSearchInfoList() {
		return productSearchInfoList;
	}
	public void setProductSearchInfoList(
			List<ProductSearchInfo> productSearchInfoList) {
		this.productSearchInfoList = productSearchInfoList;
	}
	public String getOldPlaceName() {
		return oldPlaceName;
	}
	public void setOldPlaceName(String oldPlaceName) {
		this.oldPlaceName = oldPlaceName;
	}
	public List<PlaceSearchPinyin> getPinyinList() {
		return pinyinList;
	}
	public void setPlaceSearchPinyinService(PlaceSearchPinyinService placeSearchPinyinService) {
		this.placeSearchPinyinService = placeSearchPinyinService;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public void setComSearchInfoUpdateService(ComSearchInfoUpdateService comSearchInfoUpdateService) {
		this.comSearchInfoUpdateService = comSearchInfoUpdateService;
	}
	public void setProductMessageProducer(TopicMessageProducer productMessageProducer) {
		this.productMessageProducer = productMessageProducer;
	}
    public SmsService getSmsService() {
        return smsService;
    }
    public void setSmsService(SmsService smsService) {
        this.smsService = smsService;
    }
	public String getHasSensitiveWord() {
		return hasSensitiveWord;
	}
	public void setHasSensitiveWord(String hasSensitiveWord) {
		this.hasSensitiveWord = hasSensitiveWord;
	}
	
}
