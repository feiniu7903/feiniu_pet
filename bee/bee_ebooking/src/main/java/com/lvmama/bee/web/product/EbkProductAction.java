package com.lvmama.bee.web.product;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;

import com.lvmama.bee.corputils.ImageUtils;
import com.lvmama.bee.corputils.MyNum;
import com.lvmama.comm.bee.po.ebooking.EbkMultiJourney;
import com.lvmama.comm.bee.po.ebooking.EbkProdContent;
import com.lvmama.comm.bee.po.ebooking.EbkProdJourney;
import com.lvmama.comm.bee.po.ebooking.EbkProdModelProperty;
import com.lvmama.comm.bee.po.ebooking.EbkProdPlace;
import com.lvmama.comm.bee.po.ebooking.EbkProdProduct;
import com.lvmama.comm.bee.po.ebooking.EbkProdTarget;
import com.lvmama.comm.bee.po.prod.ProductModelProperty;
import com.lvmama.comm.bee.po.prod.ProductModelType;
import com.lvmama.comm.bee.service.ebooking.EbkMultiJourneyService;
import com.lvmama.comm.bee.service.ebooking.EbkProdContentService;
import com.lvmama.comm.bee.service.ebooking.EbkProdJourneyService;
import com.lvmama.comm.bee.service.ebooking.EbkProdModelPropertyService;
import com.lvmama.comm.bee.service.ebooking.EbkProdProductService;
import com.lvmama.comm.bee.service.ebooking.EbkProdTargetService;
import com.lvmama.comm.bee.service.prod.ProductModelPropertyService;
import com.lvmama.comm.bee.service.prod.ProductModelTypeService;
import com.lvmama.comm.pet.po.perm.PermUser;
import com.lvmama.comm.pet.po.place.Place;
import com.lvmama.comm.pet.po.pub.CodeItem;
import com.lvmama.comm.pet.po.pub.ComPicture;
import com.lvmama.comm.pet.po.sup.SupBCertificateTarget;
import com.lvmama.comm.pet.po.sup.SupPerformTarget;
import com.lvmama.comm.pet.po.sup.SupSettlementTarget;
import com.lvmama.comm.pet.service.perm.PermUserService;
import com.lvmama.comm.pet.service.place.PlaceService;
import com.lvmama.comm.pet.service.pub.ComLogService;
import com.lvmama.comm.pet.service.pub.ComPictureService;
import com.lvmama.comm.pet.service.sup.BCertificateTargetService;
import com.lvmama.comm.pet.service.sup.PerformTargetService;
import com.lvmama.comm.pet.service.sup.SettlementTargetService;
import com.lvmama.comm.pet.vo.ComFile;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.utils.RandomFactory;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.utils.json.JSONOutput;
import com.lvmama.comm.utils.json.JSONResult;
import com.lvmama.comm.utils.pic.UploadCtrl;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.Constant.EBK_AUDIT_TABS_NAME;
import com.lvmama.comm.vo.Constant.EBK_PRODUCT_VIEW_TYPE;
import com.lvmama.comm.vo.Constant.SUB_PRODUCT_TYPE;

/**
 * EBK产品管理
 * 
 * @author zhangjie 2013-09-26
 * @version
 */
@Results(value = {
		@Result(name = "target_list", location = "/WEB-INF/pages/ebooking/product/target_list.jsp"),
		@Result(name = "showPropertyList", location = "/WEB-INF/pages/ebooking/product/showPropertyList.jsp"),
		@Result(name = "SURROUNDING_GROUPeditEbkProdRecommendInit", location = "/WEB-INF/pages/ebooking/product/editGroupEbkProdRecommend.jsp"),
		@Result(name = "DOMESTIC_LONGeditEbkProdRecommendInit", location = "/WEB-INF/pages/ebooking/product/editLongEbkProdRecommend.jsp"),
		@Result(name = "ABROAD_PROXYeditEbkProdRecommendInit", location = "/WEB-INF/pages/ebooking/product/editProxyEbkProdRecommend.jsp"),
		@Result(name = "SURROUNDING_GROUPeditEbkProdTripInit", location = "/WEB-INF/pages/ebooking/product/editGroupEbkProdTrip.jsp"),
		@Result(name = "DOMESTIC_LONGeditEbkProdTripInit", location = "/WEB-INF/pages/ebooking/product/editLongEbkProdTrip.jsp"),
		@Result(name = "ABROAD_PROXYeditEbkProdTripInit", location = "/WEB-INF/pages/ebooking/product/editProxyEbkProdTrip.jsp"),
		@Result(name = "SURROUNDING_GROUPeditEbkProdCostInit", location = "/WEB-INF/pages/ebooking/product/editGroupEbkProdCost.jsp"),
		@Result(name = "DOMESTIC_LONGeditEbkProdCostInit", location = "/WEB-INF/pages/ebooking/product/editLongEbkProdCost.jsp"),
		@Result(name = "ABROAD_PROXYeditEbkProdCostInit", location = "/WEB-INF/pages/ebooking/product/editProxyEbkProdCost.jsp"),
		@Result(name = "SURROUNDING_GROUPeditEbkProdPictureInit", location = "/WEB-INF/pages/ebooking/product/editGroupEbkProdPicture.jsp"),
		@Result(name = "DOMESTIC_LONGeditEbkProdPictureInit", location = "/WEB-INF/pages/ebooking/product/editLongEbkProdPicture.jsp"),
		@Result(name = "ABROAD_PROXYeditEbkProdPictureInit", location = "/WEB-INF/pages/ebooking/product/editProxyEbkProdPicture.jsp"),
		@Result(name = "SURROUNDING_GROUPeditEbkProdTrafficInit", location = "/WEB-INF/pages/ebooking/product/editGroupEbkProdTraffic.jsp"),
		@Result(name = "DOMESTIC_LONGeditEbkProdTrafficInit", location = "/WEB-INF/pages/ebooking/product/editLongEbkProdTraffic.jsp"),
		@Result(name = "ABROAD_PROXYeditEbkProdTrafficInit", location = "/WEB-INF/pages/ebooking/product/editProxyEbkProdTraffic.jsp"),
		@Result(name = "SURROUNDING_GROUPeditEbkProdOtherInit", location = "/WEB-INF/pages/ebooking/product/editGroupEbkProdOther.jsp"),
		@Result(name = "DOMESTIC_LONGeditEbkProdOtherInit", location = "/WEB-INF/pages/ebooking/product/editLongEbkProdOther.jsp"),
		@Result(name = "ABROAD_PROXYeditEbkProdOtherInit", location = "/WEB-INF/pages/ebooking/product/editProxyEbkProdOther.jsp"),
		@Result(name = "SURROUNDING_GROUPeditEbkProductInit", location = "/WEB-INF/pages/ebooking/product/editGroupEbkProduct.jsp") ,
		@Result(name = "DOMESTIC_LONGeditEbkProductInit", location = "/WEB-INF/pages/ebooking/product/editLongEbkProduct.jsp") ,
		@Result(name = "ABROAD_PROXYeditEbkProductInit", location = "/WEB-INF/pages/ebooking/product/editProxyEbkProduct.jsp") ,
		@Result(name = "editEbkProdMultirpInit", location = "/WEB-INF/pages/ebooking/product/editProxyProdMultiJourney.jsp") ,
		@Result(name = "editMultiJourney", location = "/WEB-INF/pages/ebooking/product/editProxyProdMultiJourneyShow.jsp"),
		@Result(name = "toEbkMultiJourneyTrip", location = "/WEB-INF/pages/ebooking/product/editProxyProdMultiJourneyTrip.jsp"),
		@Result(name = "toEbkMultiJourneyCost", location = "/WEB-INF/pages/ebooking/product/editProxyProdMultiJourenyCost.jsp"),
		@Result(name = "features", location = "/WEB-INF/pages/ebooking/product/template/group/ebkProdRecommendFeaturesTemplate.jsp") ,
		@Result(name = "proxyFeatures", location = "/WEB-INF/pages/ebooking/product/template/proxy/ebkProdRecommendFeaturesTemplate.jsp") ,
		@Result(name = "costcontain", location = "/WEB-INF/pages/ebooking/product/template/group/editEbkProdCostCostcontain.jsp") ,
		@Result(name = "nocostcontain", location = "/WEB-INF/pages/ebooking/product/template/group/editEbkProdCostNoCostcontain.jsp") ,
		@Result(name = "acitontoknow", location = "/WEB-INF/pages/ebooking/product/template/group/editEbkProdOtherAcitontoknow.jsp") ,
		@Result(name = "longAcitontoknow", location = "/WEB-INF/pages/ebooking/product/template/long/editEbkProdOtherAcitontoknow.jsp") ,
		@Result(name = "recommendproject", location = "/WEB-INF/pages/ebooking/product/template/group/editEbkProdOtherRecommendproject.jsp") ,
		@Result(name = "tripcontent", location = "/WEB-INF/pages/ebooking/product/template/group/editEbkProdTripTemplate.jsp") ,
		@Result(name = "longTripcontent", location = "/WEB-INF/pages/ebooking/product/template/long/editEbkProdTripTemplate.jsp") ,
		@Result(name = "editEbkProdPicInit", location = "/WEB-INF/pages/ebooking/product/ebkProPictureEdit.jsp") ,
		@Result(name = "redirectEditEbkProdMultirpInit", type = "redirect", location = "/ebooking/product/editEbkProdMultirpInit.do?ebkProdProductId=${ebkProdProductId}&&ebkProductEditModel=EBK_AUDIT_TAB_MULTITRIP"),
		@Result(name = "showEbkProdModelPropertyList", location = "/WEB-INF/pages/ebooking/product/subPage/ebkProdProperty.jsp")})
public class EbkProductAction extends BaseEbkProductAction {

	private static final long serialVersionUID = -3359814433198544945L;

	@Autowired
	private EbkProdProductService ebkProdProductService;
	@Autowired
	private EbkProdContentService ebkProdContentService;
	@Autowired
	private EbkProdJourneyService ebkProdJourneyService;
	@Autowired
	private EbkProdModelPropertyService ebkProdModelPropertyService;
	@Autowired
	private EbkProdTargetService ebkProdTargetService;
	@Autowired
	private PerformTargetService performTargetService;//履行对象Service
	@Autowired
	private SettlementTargetService settlementTargetService;//结算对象Service
	@Autowired
	private BCertificateTargetService bCertificateTargetService;//凭证对象Service
	@Autowired
	private PlaceService placeService;
	@Autowired
	private PermUserService permUserService;
	@Autowired
	private ProductModelPropertyService productModelPropertyService;
	@Autowired
	private ProductModelTypeService productModelTypeService;
	@Autowired
	private ComPictureService comPictureService;
	@Autowired
	private ComLogService comLogService;
	
	private List<CodeItem> productTypeList;// 产品类型集合
	
	private List<ProductModelProperty> modelPropertyTypeList;// 产品属性集合
	
	private String productTypes;//供应商产品类型类型
	
	private String ebkPropertyType;//产品属性类型
	
	private String targetType;//产品对象类型
	
	private String search;//查询标的，景点名称
	
	private String pictureId;//图片ID
	
	private String pictureType;//图片类型
	
	private String moveType;//图片移动方式
	
	private File iconFile;//产品小图片
    private String iconFileContentType;
    private String iconFileFileName;
	
	private File bigFile;//产品大图片
    private String bigFileContentType;
    private String bigFileFileName;
    
    private File tripPic;//产品行程图片
    private String tripPicContentType;
    private String tripPicFileName;
    private String tripSaveDayNum;//产品需保存的行程天数
    private String tripPicDayNum;//产品行程天数
    
	private List<SupPerformTarget> supplierPerformTargetList = new ArrayList<SupPerformTarget>();//履行对象集合
	private List<SupSettlementTarget> supplierSettlementTargetList = new ArrayList<SupSettlementTarget>();//结算对象集合
	private List<SupBCertificateTarget> supplierBCertificateTargetList = new ArrayList<SupBCertificateTarget>();//凭证对象集合
	
	private String checkedPropertyIds;//选中的属性ID
	private List<String> checkedPropertyIdList = new ArrayList<String>();//选中的属性集合
	
	
	private EbkProdJourney ebkProdJourney;//多行程描述
	private EbkMultiJourney ebkMultiJourney;//多行程对象
	private EbkMultiJourneyService ebkMultiJourneyService;
	private List<EbkMultiJourney> ebkMultiJourneyList=new ArrayList<EbkMultiJourney>();//多行程List
	private String productId;
	private Long multiJourneyId;
	private String paraMultiId;
	private EbkProdContent ebkProdContent;//多行程 费用说明对象
	private String days;
	List<EbkProdJourney> ebkProdJourneyList=new ArrayList<EbkProdJourney>();//形成描述List
	
	private String isCopy = "";
	
	private List<ProductModelProperty> modelPropertyList2; // 模块属性
	private List<EbkProdModelProperty> ebkProdModelPropertys;//产品所属属性
	private List<ProductModelType> modelTypeList;
	private String subProductType;
	private String ebkProductEditModel;
	
	private Map<String,Object> showColumnMap = new HashMap<String,Object>();
	
	private String moveTerm;
	private String picName;
	
	private String templateTarget;
	
	private ComPicture toEditPicture;
	private String tourDays;//行程天数 
	
	
	private Constant.REGION_NAMES[] regionNames = Constant.REGION_NAMES.values(); 
	private Constant.VISA_CITY[] visaCitys = Constant.VISA_CITY.values();
	@Action("/ebooking/product/loadTemplate")
	public String loadTemplate(){
		return templateTarget;
	}
	
	
	/*
	 * 编辑EBK产品初始，查询出产品基础信息
	 */
	@Action("/ebooking/product/editEbkProductInit")
	public String editEbkProductInit() {
		ebkProductEditModel = EBK_AUDIT_TABS_NAME.EBK_AUDIT_TAB_BASE.name();
		if(null!=ebkProdProductId){
			initEbkProductViewType();
			showEbkPropertyModel(ebkProdProduct.getSubProductType());
			//初始所有属性
			initEbkProdModelProperty();
			//初始所有对象
			initEbkProdTarget();
			initShowColumnMap();
			initPlaceName();
		}else{
			initEbkProduct();
		}
		getCurrency();
		return ebkProductViewType+"editEbkProductInit";
	}
	
	/*
	 * 保存EBK产品基础信息
	 */
	@Action("/ebooking/product/saveEbkProduct")
	public void saveEbkProduct() {
		if(!super.isSupplierEditEbkProductJson()){
			return ;
		}
		//设置当前状态，用户等信息
		ebkProdProduct.setStatus(Constant.EBK_PRODUCT_AUDIT_STATUS.UNCOMMIT_AUDIT.name());
		if(ebkProdProduct.getEbkProdProductId()==null){
			ebkProdProduct.setCreateUserId(getSessionUser().getUserId());
			ebkProdProduct.setCreateDate(new Date());
		}
		ebkProdProduct.setUpdateUserId(getSessionUser().getUserId());
		ebkProdProduct.setUpdateDate(new Date());
		
		int productId = ebkProdProductService.saveEbkProdProduct(ebkProdProduct);
		Map<String, Object> resultMap = new HashMap<String, Object>();
		if(productId==-1){
			resultMap.put("success", Boolean.FALSE);
			resultMap.put("message", -404);
		}else{
			if(ebkProdProduct.getEbkProdProductId()==null){
				comLogService.insert("EBK_PROD_PRODUCT", Long.valueOf(productId),  Long.valueOf(productId), getSessionUser().getUserName(), "insertEbkProdProduct", "新增产品信息", "新增产品信息", "EBK_PROD_PRODUCT");
			}else{
				comLogService.insert("EBK_PROD_PRODUCT", Long.valueOf(productId), Long.valueOf(productId), getSessionUser().getUserName(), "updateEbkProdProduct", "修改产品基本信息",ebkProdProduct.getEditLogMessage(), "EBK_PROD_PRODUCT");
			}
			resultMap.put("success", Boolean.TRUE);
			resultMap.put("productId", productId);
		}
		this.sendAjaxResultByJson(JSONObject.fromObject(resultMap).toString());
	}
	
	@Action("/ebooking/product/editEbkProdPicInit")
	public String editEbkProdPicInit() throws IOException{
		toEditPicture=comPictureService.getPictureByPK(Long.valueOf(pictureId));
		getRequest().setAttribute("imgSizeStyle", getRequest().getParameter("imgSizeStyle"));
		return "editEbkProdPicInit";
	}
	@Action("/ebooking/product/saveCutEbkProdPicInit")
	public void saveCutEbkProdPicInit(){
	    JSONObject result =new JSONObject();
	    String fileWebroot = getServletContext().getRealPath("/");
	    MyNum myNum = new MyNum();

	    String imageSource = getRequest().getParameter("imageSource");//图片源
	    String imageW = String.valueOf(myNum.roundToInt(getRequest().getParameter("imageW")));//图片宽
	    String imageH = String.valueOf(myNum.roundToInt(getRequest().getParameter("imageH")));//图片高
	    String imageX = String.valueOf(myNum.roundToInt(getRequest().getParameter("imageX")));//图片X位置
	    String imageY = String.valueOf(myNum.roundToInt(getRequest().getParameter("imageY")));//图片Y位置

	    String imageRotate = getRequest().getParameter("imageRotate");
	    String viewPortW = getRequest().getParameter("viewPortW");
	    String viewPortH = getRequest().getParameter("viewPortH");

	    String selectorX = String.valueOf(myNum.roundToInt(getRequest().getParameter("selectorX")));//选中区位置X
	    String selectorY = String.valueOf(myNum.roundToInt(getRequest().getParameter("selectorY")));//选中区位置Y
	    String selectorW = String.valueOf(myNum.roundToInt(getRequest().getParameter("selectorW")));//选中区位置宽
	    String selectorH = String.valueOf(myNum.roundToInt(getRequest().getParameter("selectorH")));//选中区位置高


	    String suffix = imageSource.substring(imageSource.lastIndexOf(".")+1);
	    String cutImg ="ebooking/"+DateUtil.getFormatDate(new Date(), "yyyy/MM/")+RandomFactory.generateMixed(5)+"."+suffix;
	    int cutX = 0,cutY=0;
	    cutX = Math.abs(Integer.parseInt(imageX)-Integer.parseInt(selectorX));
	    cutY =  Math.abs(Integer.parseInt(imageY)-Integer.parseInt(selectorY));
	    ImageUtils img = null;
	    try{
    	    if(imageSource.indexOf("http")!=-1){
    	        img = ImageUtils.load(imageSource);//更新时加载源图
    	    }else{
    	        img = ImageUtils.load(fileWebroot+imageSource);//加载源图
    	    }
    	    img.zoomTo(Integer.parseInt(imageW),Integer.parseInt(imageH));//缩放源图
    	    img.rotateTo(Integer.parseInt(imageW),Integer.parseInt(imageH),Integer.parseInt(imageRotate));//旋转源图
    	    img.cutTo(Integer.parseInt(imageX),Integer.parseInt(imageY),Integer.parseInt(selectorX),Integer.parseInt(selectorY),cutX,cutY,Integer.parseInt(selectorW),Integer.parseInt(selectorH));//裁剪图片
    	    byte[] imageByte = img.getImageByte(suffix);
    	    String tempFileName = new Date().getTime()+"_"+RandomFactory.generateMixed(5)+"."+suffix;
    	    ComFile comFile = new ComFile();
    	    comFile.setFileData(imageByte);
    	    comFile.setFileName(tempFileName);
    	    File file= comFile.getFile();
    	    String fileUrl=UploadCtrl.postToRemote(comFile.getFile(), cutImg);
    	    file.deleteOnExit();
    	    if(null!=fileUrl){
    	        toEditPicture =comPictureService.getPictureByPK(toEditPicture.getPictureId());
    	        String oldUrl = toEditPicture.getPictureUrl();
                toEditPicture.setPictureUrl(fileUrl);
                comPictureService.updatePicture(toEditPicture);
                comLogService.insert("EBK_PROD_PRODUCT_IMAGE",ebkProdProduct.getEbkProdProductId(),toEditPicture.getPictureId(),getSessionUser().getUserName(),
                        Constant.COM_LOG_PRODUCT_EVENT.uploadProductBigPicture.name(),
                        "剪切图片", "图片名称为[ "+toEditPicture.getPictureName() +" ]，以前地址为"+oldUrl, "EBK_PROD_PRODUCT");
                result.put("success", Boolean.TRUE);
                result.put("fileUrl", fileUrl);
    	    }else{
    	        result.put("success", Boolean.FALSE);
                result.put("message", -404);
    	    }
	    }catch(Exception e){
	        e.printStackTrace();
	        result.put("success", Boolean.FALSE);
            result.put("message", -505);
	    }
	    JSONOutput.writeJSON(getResponse(), result,"text/html;charset=UTF-8");
	}
	/*
	 * 保存EBK产品图片信息
	 */
	@Action("/ebooking/product/saveEbkProdPicture")
	public void saveEbkProdPicture() {
		if(!super.isSupplierEditEbkProductJson()){
			return ;
		}
		JSONObject result =new JSONObject();
		try{ 
			if(ebkProdProduct==null||ebkProdProduct.getEbkProdProductId()==null){
				throw new Exception("产品不存在，请先录入产品信息再添加图片！");
			}
			
			File saveFile = StringUtils.equals(pictureType, "ICON")?iconFile:bigFile;
			String fileFileName = StringUtils.equals(pictureType, "ICON")?iconFileFileName:bigFileFileName;
			
			if(saveFile==null||!saveFile.isFile()){
				throw new Exception("图片路径不正确！");
			}
			if(!checkPictureType(saveFile)){
				throw new Exception("上传的不是图片类型文件！");
			}
			
			if(StringUtils.equals(pictureType, "ICON")){//上传的是icon需要小于50k
				if(checkImgSize(saveFile, 50)){
					throw new Exception("图片大小需要小于50K");
				}
			}else if(StringUtils.equals(pictureType, "BIG")){//上传的是icon需要小于1M
				if(checkImgSize(saveFile, 1024)){
					throw new Exception("图片大小需要小于1M");
				}
			}
			String ext = fileFileName.substring(fileFileName.lastIndexOf('.')).toLowerCase();
			String picName = fileFileName.substring(0,fileFileName.lastIndexOf('.'));
			String fullFileName = "super/"+DateUtil.getFormatDate(new Date(), "yyyy/MM/")+RandomFactory.generateMixed(5)+ext;
			String fileUrl=UploadCtrl.postToRemote(saveFile, fullFileName);
			ComPicture picture = new ComPicture();
			picture.setPictureObjectId(ebkProdProduct.getEbkProdProductId());
			picture.setPictureObjectType(StringUtils.equals(pictureType, "ICON")?"EBK_PROD_PRODUCT_SMALL":"EBK_PROD_PRODUCT_BIG");
			picture.setPictureName(picName);
			picture.setPictureUrl(fileUrl);
			picture.setIsNew(true);// 标识图片是新建产生的				
			Long pk=comPictureService.savePicture(picture);
			comLogService.insert("EBK_PROD_PRODUCT_IMAGE",ebkProdProduct.getEbkProdProductId(),pk,getSessionUser().getUserName(),
					Constant.COM_LOG_PRODUCT_EVENT.uploadProductBigPicture.name(),
					StringUtils.equals(pictureType, "ICON")?"上传了产品小图":"上传了产品大图", "图片名称为[ "+picture.getPictureName() +" ]", "EBK_PROD_PRODUCT");
			result.put("success", true);
			result.put("fileUrl", fileUrl);
			result.put("fileId", pk.toString());
			result.put("fileName", picName);
			result.put("pictureObjectType", picture.getPictureObjectType());
		}catch(Exception ex){
			result.put("success", false);
			result.put("message", ex.getMessage());
		}
		JSONOutput.writeJSON(getResponse(), result,"text/html;charset=UTF-8");
	}

	/*
	 * 保存EBK产品特色及推荐,费用说明,其他条款等信息
	 */
	@Action("/ebooking/product/saveEbkProdContent")
	public void saveEbkProdContent() {
		if(!super.isSupplierEditEbkProductJson()){
			return ;
		}
		Map<String, Object> resultMap = new HashMap<String, Object>();
		List<EbkProdContent> ebkProdContents = ebkProdProduct.getEbkProdContents();
		if(ebkProdContents==null||ebkProdContents.size()==0){
			resultMap.put("success", "false");
			resultMap.put("message", -404);
		}else{
			List<EbkProdContent> saveContents = new ArrayList<EbkProdContent>();
			for(EbkProdContent content : ebkProdContents){
				if(content!=null){
					saveContents.add(content);
				}
			}
			ebkProdContentService.saveEbkProdContentDAO(saveContents);
			for(EbkProdContent ebkProdContent : saveContents){
				if(Constant.VIEW_CONTENT_TYPE.COSTCONTAIN.name().equalsIgnoreCase(ebkProdContent.getContentType())){
					comLogService.insert("EBK_PROD_CONTENT", ebkProdProduct.getEbkProdProductId(),  ebkProdProduct.getEbkProdProductId(), getSessionUser().getUserName(), "insertEbkProdContent", "更新产品费用包含信息", "更新产品费用包含信息为："+ebkProdContent.getContent(), "EBK_PROD_PRODUCT");
				}else if(Constant.VIEW_CONTENT_TYPE.NOCOSTCONTAIN.name().equalsIgnoreCase(ebkProdContent.getContentType())){
					comLogService.insert("EBK_PROD_CONTENT", ebkProdProduct.getEbkProdProductId(),  ebkProdProduct.getEbkProdProductId(), getSessionUser().getUserName(), "insertEbkProdContent", "更新产品费用不包含信息", "更新产品费用不包含信息为："+ebkProdContent.getContent(), "EBK_PROD_PRODUCT");
				}else{
					comLogService.insert("EBK_PROD_CONTENT", ebkProdProduct.getEbkProdProductId(),  ebkProdProduct.getEbkProdProductId(), getSessionUser().getUserName(), "insertEbkProdContent", "更新产品"+Constant.VIEW_CONTENT_TYPE.getCnName(ebkProdContent.getContentType())+"信息", "更新产品"+Constant.VIEW_CONTENT_TYPE.getCnName(ebkProdContent.getContentType())+"信息", "EBK_PROD_PRODUCT");
				}
			}
	        resultMap.put("success", Boolean.TRUE);
		}
		this.sendAjaxResultByJson(JSONObject.fromObject(resultMap).toString());
		
	}
	
	/*
	 * 保存EBK产品发车信息
	 */
	@Action("/ebooking/product/saveEbkProdTrafficContent")
	public void saveEbkProdTrafficContent() {
		if(!super.isSupplierEditEbkProductJson()){
			return ;
		}
		Map<String, Object> resultMap = new HashMap<String, Object>();
		List<EbkProdContent> ebkProdContents = ebkProdProduct.getEbkProdContents();
		
		ebkProdContentService.saveEbkProdTrafficContentDAO(ebkProdContents,ebkProdProduct.getEbkProdProductId(),Constant.VIEW_CONTENT_TYPE.TRAFFICEBKINFO.name());
		comLogService.insert("EBK_PROD_CONTENT", ebkProdProduct.getEbkProdProductId(),  ebkProdProduct.getEbkProdProductId(), getSessionUser().getUserName(), "editEbkProdTrafficContent", "更新产品发车信息", "更新产品发车信息", "EBK_PROD_PRODUCT");
		
		resultMap.put("success", Boolean.TRUE);
		this.sendAjaxResultByJson(JSONObject.fromObject(resultMap).toString());
	}

	
	/*
	 * 编辑EBK产品特色及推荐信息初始
	 */
	@Action("/ebooking/product/editEbkProdRecommendInit")
	public String editEbkProdRecommendInit() {
		ebkProductEditModel = EBK_AUDIT_TABS_NAME.EBK_AUDIT_TAB_RECOMMEND.name();
		String result = isSupplierEditEbkProd();
		if(null!=result){
			return result;
		}
		List<EbkProdContent> showContents = new ArrayList<EbkProdContent>(2);
		showContents.add(new EbkProdContent());
		showContents.add(new EbkProdContent());
		List<EbkProdContent> contents = ebkProdContentService.findListByProductId(ebkProdProductId);
		if(contents!=null&&contents.size()>0){
			for(EbkProdContent content : contents){
				if(Constant.VIEW_CONTENT_TYPE.MANAGERRECOMMEND.name().equalsIgnoreCase(content.getContentType())){
					showContents.set(0,content);
				}
				if(Constant.VIEW_CONTENT_TYPE.FEATURES.name().equalsIgnoreCase(content.getContentType())){
					showContents.set(1, content);
				}
			}
		}
		ebkProdProduct.setEbkProdProductId(ebkProdProductId);
		ebkProdProduct.getEbkProdContents().clear();
		ebkProdProduct.getEbkProdContents().addAll(showContents);
		return ebkProductViewType+"editEbkProdRecommendInit";
	}

	/*
	 * 编辑EBK产品行程描述初始
	 */
	@Action("/ebooking/product/editEbkProdTripInit")
	public String editEbkProdTripInit() {
		ebkProductEditModel = EBK_AUDIT_TABS_NAME.EBK_AUDIT_TAB_TRIP.name();
		String result = isSupplierEditEbkProd();
		if(null!=result){
			return result;
		}
		EbkProdJourney ebkProdJourneyDO = new EbkProdJourney();
		ebkProdJourneyDO.setProductId(ebkProdProductId);
		List<EbkProdJourney> ebkProdJourneys = ebkProdJourneyService.findListOrderDayNumberByDO(ebkProdJourneyDO);
		if(ebkProdJourneys!=null && ebkProdJourneys.size()>0){
			this.setTripPicDayNum(ebkProdJourneys.size()+"");
		}else{
			this.setTripPicDayNum("1");
		}
		ebkProdProduct.setEbkProdProductId(ebkProdProductId);
		ebkProdProduct.setEbkProdJourneys(ebkProdJourneys);
		return ebkProductViewType+"editEbkProdTripInit";
	}
	
	/*
	 * 编辑EBK产品行程描述
	 */
	@Action("/ebooking/product/saveEbkProdTrip")
	public void saveEbkProdTrip() {
		if(!super.isSupplierEditEbkProductJson()){
			return ;
		}
		Map<String, Object> resultMap = new HashMap<String, Object>();
		List<EbkProdJourney> ebkProdJourneys = ebkProdProduct.getEbkProdJourneys();
		if(ebkProdJourneys!=null && ebkProdJourneys.size()>0){
			ebkProdJourneyService.editEbkProdJourneys(ebkProdJourneys,ebkProdProduct.getEbkProdProductId(),Long.valueOf(tripSaveDayNum));
			comLogService.insert("EBK_PROD_JOURNEY", ebkProdProduct.getEbkProdProductId(),  ebkProdProduct.getEbkProdProductId(), getSessionUser().getUserName(), "editEbkProdJourneys", "更新产品行程描述信息", getEbkProdTripLogMes(ebkProdJourneys), "EBK_PROD_PRODUCT");
		}
		
		//  ebooking 产品行程天数 update by 20140516  
		EbkProdProduct  epp =  ebkProdProductService.findEbkProdAllByPrimaryKey(ebkProdProduct.getEbkProdProductId());
		if(null!=epp && null!=epp.getEbkProdProductId()){
			epp.setTourDays(tripSaveDayNum);
		}
		ebkProdProductService.updateEbkProdProductDO(epp);
		
		resultMap.put("success", Boolean.TRUE);
		this.sendAjaxResultByJson(JSONObject.fromObject(resultMap).toString());
	}
	
	/**
	 * 根据参数查询列表
	 * */
	@Action(value="/view/queryMultiJourneyList")
	public String queryMultiJourneyList() {
		Map<String,Object> params = new HashMap<String, Object>();
		if(params.get("mJproductId") == null) {
			return "editEbkProdMultirpInit";
		}
		params.put("ebkProdProductId", productId);
		ebkMultiJourneyList = ebkMultiJourneyService.queryMultiJourneyByParams(params);
		ebkProdProduct = ebkProdProductService.findEbkProdAllByPrimaryKey(ebkProdProductId);
		return "editEbkProdMultirpInit";
	}
	
	


	/**
	 * EBK多行程管理跳转
	 * @return
	 */
	@Action("/ebooking/product/editEbkProdMultirpInit")
	public String editEbkProdMultirpInit() {
		ebkProductEditModel = EBK_AUDIT_TABS_NAME.EBK_AUDIT_TAB_MULTITRIP.name();
		String result = isSupplierEditEbkProd();
		if(null!=result){
			return result;
		}
		if(ebkProdProductId != null) {
			//queryMultiJourneyList();
			Map<String,Object> params = new HashMap<String, Object>();
			params.put("ebkProdProductId", ebkProdProductId);
			ebkProdProduct = ebkProdProductService.findEbkProdAllByPrimaryKey(ebkProdProductId);
			ebkMultiJourneyList = ebkMultiJourneyService.queryMultiJourneyByParams(params);
		}
		return "editEbkProdMultirpInit";
	}
	
	/**
	 * 跳转  多行程 行程描述
	 * @return
	 */
	@Action(value="/view/toEbkMultiJourney")
	public String toEbkMultiJourney(){
		ebkProductEditModel = EBK_AUDIT_TABS_NAME.EBK_AUDIT_TAB_MULTITRIP.name();
		ebkProdProductId = Long.valueOf(productId);
		String result = isSupplierEditEbkProd();
		if(null!=result){
			return result;
		}
		if(multiJourneyId != null&&productId!=null) {
			ebkMultiJourney = ebkMultiJourneyService.selectByPrimaryKey(multiJourneyId);
			ebkProdJourneyList=ebkProdJourneyService.getViewJourneyByMultiJourneyId(multiJourneyId);			
		}
		return "toEbkMultiJourneyTrip";
	}
	
	/*
	 * 保存EBK多行程  行程描述
	 */
	@Action("/ebooking/product/saveEbkMultiJourneyTrip")
	public void saveEbkMultiJourneyTrip() {
		if(!super.isSupplierEditEbkProductJson()){
			return ;
		}
		Map<String, Object> resultMap = new HashMap<String, Object>();
		List<EbkProdJourney> ebkProdJourneys = ebkProdProduct.getEbkProdJourneys();
		if(ebkProdJourneys!=null && ebkProdJourneys.size()>0){ 
			ebkProdJourneyService.editEbkMultiProdJourneys(ebkProdJourneys,multiJourneyId,Long.valueOf(productId),Long.valueOf(tripSaveDayNum));
			comLogService.insert("EBK_PROD_JOURNEY", multiJourneyId,  ebkProdProduct.getEbkProdProductId(), getSessionUser().getUserName(), "editEbkProdJourneys", "更新产品行程描述信息", getEbkProdTripLogMes(ebkProdJourneys), "EBK_PROD_PRODUCT");
		}
		resultMap.put("success", Boolean.TRUE);
		this.sendAjaxResultByJson(JSONObject.fromObject(resultMap).toString());
		
	}
	
	/**
	 * 跳转 新增或修改多行程
	 * @return
	 */
	@Action(value = "/view/toEditMultiJourney")
	public String toEditViewMultiJourney() {
		/*if(ebkMultiJourney != null && ebkMultiJourney.getMultiJourneyId() != null) {
			ebkMultiJourney = ebkMultiJourneyService.selectByPrimaryKey(ebkMultiJourney.getMultiJourneyId());
		}*/
		if(multiJourneyId != null) {
		ebkMultiJourney = ebkMultiJourneyService.selectByPrimaryKey(multiJourneyId);
		}
		return "editMultiJourney";
	}
	
	/**
	 * 新增或修改或复制新增行程明细
	 * */
	@Action(value="/view/saveMultiJourney")
	public void saveMultiJourney() {
		JSONResult result = new JSONResult();
		try {
			if(ebkMultiJourney == null) {
				throw new Exception("传递参数为空,操作失败!");
			}
			if(ebkMultiJourney.getMultiJourneyId() == null || "true".equals(isCopy)) {
				Long preMultiJourneyId = ebkMultiJourney.getMultiJourneyId();
				ebkMultiJourney.setMultiJourneyId(null);
				// 多行程默认无效
				ebkMultiJourney.setValid("N");
				Long newId = ebkMultiJourneyService.insert(ebkMultiJourney);
				
				/*ebkProdContent.setMultiJourneyId(null);
				Long newContentMultiId = ebkProdContentService.insert(ebkProdContent);*/
				//复制行程明细
				if(preMultiJourneyId != null && "true".equals(isCopy)) {
					List<EbkProdJourney> vjList = ebkProdJourneyService.getViewJourneyByMultiJourneyId(preMultiJourneyId);
					for (int i = 0; i < vjList.size(); i++) {
						EbkProdJourney vj = vjList.get(i);
						EbkProdJourney newVj = new EbkProdJourney();
						BeanUtils.copyProperties(newVj, vj);
						newVj.setMultiJourneyId(newId); 
						ebkProdJourneyService.insertEbkJourney(newVj); 
					}
					
					List<EbkProdContent> ecList=ebkProdContentService.getEbkContentByMultiJourneyId(preMultiJourneyId,"");
					for (int i = 0; i < ecList.size(); i++) {
						EbkProdContent vj = ecList.get(i);
						EbkProdContent newVj = new EbkProdContent();
						BeanUtils.copyProperties(newVj, vj);
						newVj.setMultiJourneyId(newId); 
						ebkProdContentService.insertEbkProdContent(newVj);
					}
				}
			} else {
				ebkMultiJourneyService.update(ebkMultiJourney);
			}
		} catch (Exception ex) {
			result.raise(ex);
		}
		result.output(getResponse());
	}
	
	/**
	 * 跳转 多行程 费用说明
	 * @return
	 */
	@Action("/view/toEbkMultiCost")
	public String toEbkMultiCost() {
		ebkProductEditModel = EBK_AUDIT_TABS_NAME.EBK_AUDIT_TAB_MULTITRIP.name();
		ebkProdProductId = Long.valueOf(productId);
		String result = isSupplierEditEbkProd();
		if(null!=result){
			return result;
		}
		if(multiJourneyId != null&&productId!=null) {
			ebkMultiJourney = ebkMultiJourneyService.selectByPrimaryKey(multiJourneyId);
			//ebkProdContent=ebkProdContentService.getEbkContentByMultiJourneyId(multiJourneyId, "");
			
			List<EbkProdContent> showContents = new ArrayList<EbkProdContent>(2);
			showContents.add(new EbkProdContent());
			showContents.add(new EbkProdContent());
			Map map=new HashMap();
			map.put("multiJourneyId", multiJourneyId);
			List<EbkProdContent> contents = ebkProdContentService.findEbkProdContentDOByIdAndType(map);
			if(contents!=null&&contents.size()>0){
				for(EbkProdContent content : contents){
					if(Constant.VIEW_CONTENT_TYPE.COSTCONTAIN.name().equalsIgnoreCase(content.getContentType())){
						showContents.set(0, content);
					} else if(Constant.VIEW_CONTENT_TYPE.NOCOSTCONTAIN.name().equalsIgnoreCase(content.getContentType())){
						showContents.set(1, content);
					}
				}
			}
			ebkProdProduct.setEbkProdContents(showContents);
		}
		return "toEbkMultiJourneyCost";
	}
	/**
	 * 更改有效状态
	 * */
	@Action(value="/view/changeValidStatus")
	public void changeValidStatus() {
		JSONResult result = new JSONResult();
		try {
			if (ebkMultiJourney == null
					|| ebkMultiJourney.getMultiJourneyId() == null) {
				throw new Exception("参数异常,操作失败!");
			}
			EbkMultiJourney mJourney = ebkMultiJourneyService
					.selectByPrimaryKey(ebkMultiJourney.getMultiJourneyId());
			if (mJourney == null) {
				throw new Exception("数据异常,操作失败!");
			}

			// 必须有行程明细才能设置为有效
			if ("N".equals(mJourney.getValid())) {
				List<EbkProdJourney> journeyList = ebkProdJourneyService
						.getViewJourneyByMultiJourneyId(ebkMultiJourney
								.getMultiJourneyId());
				if (journeyList.isEmpty()) {
					throw new Exception("请先添加行程明细信息!");
				}
				List<EbkProdContent> contentList = ebkProdContentService
						.getEbkContentByMultiJourneyId(
								ebkMultiJourney.getMultiJourneyId(),
								Constant.VIEW_CONTENT_TYPE.COSTCONTAIN.name());
				if (contentList.isEmpty()) {
					throw new Exception("请先添加费用说明");
				}
			} else {				
				// 将对应的时间价格表删除
				/*ebkMultiJourneyService.deleteTimePriceByMultiJourneyId(
						ebkMultiJourney.getProductId(),
						ebkMultiJourney.getMultiJourneyId());*/
				//发消息更新对应的信息
				/*List<Long> branchIds = ebkMultiJourneyService
						.getBranchIdsByMultiJourneyId(
								ebkMultiJourney.getProductId(),
								ebkMultiJourney.getMultiJourneyId());
				if(branchIds != null && branchIds.size() > 0) {
					for (int i = 0; i < branchIds.size(); i++) {
						productMessageProducer.sendMsg(MessageFactory.newProductSellPriceMessage(branchIds.get(i)));
					}
				}*/
			}
			mJourney.setValid("Y".equals(mJourney.getValid()) ? "N" : "Y");
			ebkMultiJourneyService.update(mJourney);
		} catch (Exception ex) {
			result.raise(ex);
		}
		result.output(getResponse());
	}
	
	/**
	 * 修改产品行程天数
	 */
	@Action("/view/saveEbkProdTourDays")
	public void saveEbkProdTourDays() {
	    if(!super.isSupplierEditEbkProductJson()){
            return ;
        }
		JSONObject result =new JSONObject();
		try{ 
			
			EbkProdProduct  epp =  ebkProdProductService.findEbkProdAllByPrimaryKey(Long.valueOf(productId));
			if(null!=epp && null!=epp.getEbkProdProductId()){
				epp.setTourDays(tourDays);
			}
			ebkProdProductService.updateEbkProdProductDO(epp);
			result.put("success", true);
		}catch(Exception ex){
			result.put("success", false);
			result.put("message", ex.getMessage());
			ex.printStackTrace();
		}		
		JSONOutput.writeJSON(getResponse(), result,"text/html;charset=UTF-8");
	}

	
	
	
	/*
	 * 保存EBK多行程费用说明
	 */
	@Action("/ebooking/product/saveEbkMultiContent")
	public String saveEbkMultiContent() {
		if(!super.isSupplierEditEbkProductJson()){
			return "toEbkMultiJourneyCost";
		}
		List<EbkProdContent> ebkProdContents = ebkProdProduct.getEbkProdContents();
		if(ebkProdContents==null||ebkProdContents.size()==0){
			//TODO
		}else{
			List<EbkProdContent> saveContents = new ArrayList<EbkProdContent>();
			for(EbkProdContent content : ebkProdContents){
				if(content!=null){
					content.setMultiJourneyId(Long.valueOf(paraMultiId));
					saveContents.add(content);
				}
			}
			ebkProdContentService.saveEbkMultiJourneyContentDAO(saveContents);
			for(EbkProdContent ebkProdContent : saveContents){
				if(Constant.VIEW_CONTENT_TYPE.COSTCONTAIN.name().equalsIgnoreCase(ebkProdContent.getContentType())){
					comLogService.insert("EBK_PROD_CONTENT", ebkProdProduct.getEbkProdProductId(),  ebkProdProduct.getEbkProdProductId(), getSessionUser().getUserName(), "insertEbkProdContent", "更新产品费用包含信息", "更新产品费用包含信息为："+ebkProdContent.getContent(), "EBK_PROD_PRODUCT");
				}else if(Constant.VIEW_CONTENT_TYPE.NOCOSTCONTAIN.name().equalsIgnoreCase(ebkProdContent.getContentType())){
					comLogService.insert("EBK_PROD_CONTENT", ebkProdProduct.getEbkProdProductId(),  ebkProdProduct.getEbkProdProductId(), getSessionUser().getUserName(), "insertEbkProdContent", "更新产品费用不包含信息", "更新产品费用不包含信息为："+ebkProdContent.getContent(), "EBK_PROD_PRODUCT");
				}else{
					comLogService.insert("EBK_PROD_CONTENT", ebkProdProduct.getEbkProdProductId(),  ebkProdProduct.getEbkProdProductId(), getSessionUser().getUserName(), "insertEbkProdContent", "更新产品"+Constant.VIEW_CONTENT_TYPE.getCnName(ebkProdContent.getContentType())+"信息", "更新产品"+Constant.VIEW_CONTENT_TYPE.getCnName(ebkProdContent.getContentType())+"信息", "EBK_PROD_PRODUCT");
				}
			}
		}
		return "redirectEditEbkProdMultirpInit";
	}
	
	
	/*
	 * 保存EBK产品行程图片信息
	 */
	@Action("/ebooking/product/saveEbkProdTripPicture")
	public void saveEbkProdTripPicture() {
	    if(!super.isSupplierEditEbkProductJson()){
            return ;
        }
		JSONObject result =new JSONObject();
		try{ 
			if(ebkProdProduct==null||ebkProdProduct.getEbkProdProductId()==null){
				throw new Exception("产品不存在，请先录入产品信息再添加图片！");
			}
			
			if(tripPic==null||!tripPic.isFile()){
				throw new Exception("图片路径不正确！");
			}
			if(!checkPictureType(tripPic)){
				throw new Exception("上传的不是图片类型文件！");
			}
			
			
			String ext = tripPicFileName.substring(tripPicFileName.lastIndexOf('.')).toLowerCase();
			String picName = tripPicFileName.substring(0,tripPicFileName.lastIndexOf('.'));
			String fullFileName = "super/"+DateUtil.getFormatDate(new Date(), "yyyy/MM/")+RandomFactory.generateMixed(5)+ext;
			String fileUrl=UploadCtrl.postToRemote(tripPic, fullFileName);
			ComPicture picture = new ComPicture();
			picture.setPictureObjectId(Long.valueOf(tripPicDayNum));
			picture.setPictureObjectType(Constant.EBK_PROD_PICTURE_TYPE.EBK_PROD_JOURNEY.name());
			picture.setPictureName(picName);
			picture.setPictureUrl(fileUrl);
			picture.setIsNew(true);// 标识图片是新建产生的				
			Long pk=comPictureService.savePicture(picture);
			result.put("success", true);
			result.put("fileUrl", fileUrl);
			result.put("fileId", pk);
			result.put("fileName", picture.getPictureName());
		}catch(Exception ex){
			result.put("success", false);
			result.put("message", ex.getMessage());
			ex.printStackTrace();
		}		
		JSONOutput.writeJSON(getResponse(), result,"text/html;charset=UTF-8");
	}
	
	
	/*
	 * 保存EBK多行程  行程图片信息
	 */
	@Action("/ebooking/product/saveEbkProdMultiTripPicture")
	public void saveEbkProdMultiTripPicture() {
	    if(!super.isSupplierEditEbkProductJson()){
            return ;
        }
		JSONObject result =new JSONObject();
		try{ 
			if(multiJourneyId==null||productId==null){
				throw new Exception("产品不存在，请先录入产品信息再添加图片！");
			}
			
			if(tripPic==null||!tripPic.isFile()){
				throw new Exception("图片路径不正确！");
			}
			if(!checkPictureType(tripPic)){
				throw new Exception("上传的不是图片类型文件！");
			}
			
			
			String ext = tripPicFileName.substring(tripPicFileName.lastIndexOf('.')).toLowerCase();
			String picName = tripPicFileName.substring(0,tripPicFileName.lastIndexOf('.'));
			String fullFileName = "super/"+DateUtil.getFormatDate(new Date(), "yyyy/MM/")+RandomFactory.generateMixed(5)+ext;
			String fileUrl=UploadCtrl.postToRemote(tripPic, fullFileName);
			ComPicture picture = new ComPicture();
			picture.setPictureObjectId(Long.valueOf(tripPicDayNum));
			picture.setPictureObjectType(Constant.EBK_PROD_PICTURE_TYPE.EBK_PROD_MULTIJOURNEY.name());
			picture.setPictureName(picName);
			picture.setPictureUrl(fileUrl);
			picture.setIsNew(true);// 标识图片是新建产生的				
			Long pk=comPictureService.savePicture(picture);
			result.put("success", true);
			result.put("fileUrl", fileUrl);
			result.put("fileId", pk);
			result.put("fileName", picture.getPictureName());
		}catch(Exception ex){
			result.put("success", false);
			result.put("message", ex.getMessage());
		}		
		JSONOutput.writeJSON(getResponse(), result,"text/html;charset=UTF-8");
	}
	
	/*
	 * 编辑EBK产品费用说明初始
	 */
	@Action("/ebooking/product/editEbkProdCostInit")
	public String editEbkProdCostInit() {
		ebkProductEditModel = EBK_AUDIT_TABS_NAME.EBK_AUDIT_TAB_COST.name();
		String result = isSupplierEditEbkProd();
		if(null!=result){
			return result;
		}
		List<EbkProdContent> showContents = new ArrayList<EbkProdContent>(2);
		showContents.add(new EbkProdContent());
		showContents.add(new EbkProdContent());
		List<EbkProdContent> contents = ebkProdContentService.findListByProductId(ebkProdProductId);
		if(contents!=null&&contents.size()>0){
			for(EbkProdContent content : contents){
				if(Constant.VIEW_CONTENT_TYPE.COSTCONTAIN.name().equalsIgnoreCase(content.getContentType())){
					showContents.set(0, content);
				} else if(Constant.VIEW_CONTENT_TYPE.NOCOSTCONTAIN.name().equalsIgnoreCase(content.getContentType())){
					showContents.set(1, content);
				}
			}
		}
		ebkProdProduct.setEbkProdProductId(ebkProdProductId);
		ebkProdProduct.setEbkProdContents(showContents);
		return ebkProductViewType+"editEbkProdCostInit";
	}
	
	/*
	 * 编辑EBK产品图片初始
	 */
	@Action("/ebooking/product/editEbkProdPictureInit")
	public String editEbkProdPictureInit() {
		ebkProductEditModel = EBK_AUDIT_TABS_NAME.EBK_AUDIT_TAB_PICTURE.name();
		String result = isSupplierEditEbkProd();
		if(null!=result){
			return result;
		}
		List<ComPicture> comPictures = comPictureService.getComPictureByObjectIdOrderBySeqNumForEbk(ebkProdProductId);
		ebkProdProduct.setComPictures(comPictures);
		return ebkProductViewType+"editEbkProdPictureInit";
	}
	
	/**
	 * 图片交换位置
	 */
	@Action("/ebooking/product/moveEbkProdPic")
	public void moveEbkProdPicNew(){
	    if(!super.isSupplierEditEbkProductJson()){
            return;
        }
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try{
			String[] ids=moveTerm.split(":");
			if("up".equalsIgnoreCase(moveType)){//向上
				ComPicture prevPic=comPictureService.getPictureByPK(Long.valueOf(ids[0]));
				ComPicture currentPic=comPictureService.getPictureByPK(Long.valueOf(ids[1]));
				Integer currentSeq=currentPic.getSeq();
				currentPic.setSeq(prevPic.getSeq());
				prevPic.setSeq(currentSeq);
				comPictureService.updatePicture(prevPic);
				comPictureService.updatePicture(currentPic);
			}
			if("down".equalsIgnoreCase(moveType)){//向下
				ComPicture nextPic=comPictureService.getPictureByPK(Long.valueOf(ids[0]));
				ComPicture currentPic=comPictureService.getPictureByPK(Long.valueOf(ids[1]));
				Integer currentSeq=currentPic.getSeq();
				currentPic.setSeq(nextPic.getSeq());
				nextPic.setSeq(currentSeq);
				comPictureService.updatePicture(nextPic);
				comPictureService.updatePicture(currentPic);
			}
			resultMap.put("success", Boolean.TRUE);
			comLogService.insert("EBK_PROD_PRODUCT_IMAGE", ebkProdProduct.getEbkProdProductId(),  Long.valueOf(ids[0]), getSessionUser().getUserName(), "editEbkProdPicture", "产品图片移动", "产品图片移动更新", "EBK_PROD_PRODUCT");
		}catch(Exception ex){
		    ex.printStackTrace();
			resultMap.put("success", Boolean.FALSE);
			resultMap.put("message", -500);
		}
		this.sendAjaxResultByJson(JSONObject.fromObject(resultMap).toString());
	}
	
	
	/**
	 * 保存图片名称
	 */
	@Action("/ebooking/product/saveEbkProdPicName")
	public void saveEbkProdPicName(){
	    if(!super.isSupplierEditEbkProductJson()){
            return;
        }
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try{
			ComPicture pic=comPictureService.getPictureByPK(Long.valueOf(pictureId));
			if(null!=pic&&StringUtils.isNotBlank(picName)){
				pic.setPictureName(this.picName);
				comPictureService.updatePicture(pic);
				resultMap.put("success", Boolean.TRUE);
			}
		}catch(Exception ex){
		    ex.printStackTrace();
			resultMap.put("success", Boolean.FALSE);
			resultMap.put("message", -500);
		}
		this.sendAjaxResultByJson(JSONObject.fromObject(resultMap).toString());
	}
	
	/*
	 * 删除EBK产品图片
	 */
	@Action("/ebooking/product/deleteEbkProdPic")
	public void deleteEbkProdPic() {
		if(!super.isSupplierEditEbkProductJson()){
			return;
		}
		Map<String, Object> resultMap = new HashMap<String, Object>();
		ComPicture cp = comPictureService.getPictureByPK(Long.valueOf(pictureId));
		if(cp==null){
			resultMap.put("success", Boolean.FALSE);
			resultMap.put("message", -404);
			this.sendAjaxResultByJson(JSONObject.fromObject(resultMap).toString());
			return ;
		}
		comPictureService.deletePicture(cp.getPictureId());
		comLogService.insert("EBK_PROD_PRODUCT_IMAGE",Long.valueOf(ebkProdProductId),cp.getPictureObjectId(),getSessionUser().getUserName(),
				Constant.EBK_PROD_PICTURE_TYPE.EBK_PROD_PRODUCT_BIG.name().equalsIgnoreCase(cp.getPictureObjectType())?Constant.COM_LOG_PRODUCT_EVENT.deleteProductBigPicture.name():Constant.COM_LOG_PRODUCT_EVENT.deleteProductSmallPicture.name(),
				"删除图片", null, "EBK_PROD_PRODUCT");
		

		resultMap.put("success", Boolean.TRUE);

		this.sendAjaxResultByJson(JSONObject.fromObject(resultMap).toString());
	}
	
	/*
	 * 编辑EBK产品发车信息初始
	 */
	@Action("/ebooking/product/editEbkProdTrafficInit")
	public String editEbkProdTrafficInit() {
		ebkProductEditModel = EBK_AUDIT_TABS_NAME.EBK_AUDIT_TAB_TRAFFIC.name();
		String result = isSupplierEditEbkProd();
		if(null!=result){
			return result;
		}
		List<EbkProdContent> showContents = new ArrayList<EbkProdContent>();
		List<EbkProdContent> contents = ebkProdContentService.findListByProductId(ebkProdProductId);
		if(contents!=null&&contents.size()>0){
			for(EbkProdContent content : contents){
				if(Constant.VIEW_CONTENT_TYPE.TRAFFICEBKINFO.name().equalsIgnoreCase(content.getContentType())){
					showContents.add(content);
				}
			}
		}
		ebkProdProduct.setEbkProdProductId(ebkProdProductId);
		ebkProdProduct.getEbkProdContents().clear();
		ebkProdProduct.getEbkProdContents().addAll(showContents);
		
		return ebkProductViewType+"editEbkProdTrafficInit";
	}
	
	
	/*
	 * 编辑EBK产品其他条款初始
	 */
	@Action("/ebooking/product/editEbkProdOtherInit")
	public String editEbkProdOtherInit() {
		ebkProductEditModel = EBK_AUDIT_TABS_NAME.EBK_AUDIT_TAB_OTHER.name();
		String result = isSupplierEditEbkProd();
		if(null!=result){
			return result;
		}
		List<EbkProdContent> showContents = new ArrayList<EbkProdContent>(4);
		showContents.add(new EbkProdContent());
		showContents.add(new EbkProdContent());
		showContents.add(new EbkProdContent());
		showContents.add(new EbkProdContent());
		List<EbkProdContent> contents = ebkProdContentService.findListByProductId(ebkProdProductId);
		if(contents!=null&&contents.size()>0){
			for(EbkProdContent content : contents){
				if(Constant.VIEW_CONTENT_TYPE.ACITONTOKNOW.name().equalsIgnoreCase(content.getContentType())){
					showContents.set(0, content);
				}else if(Constant.VIEW_CONTENT_TYPE.RECOMMENDPROJECT.name().equalsIgnoreCase(content.getContentType())){
					showContents.set(1, content);
				}else if(Constant.VIEW_CONTENT_TYPE.ORDERTOKNOWN.name().equalsIgnoreCase(content.getContentType())){
					showContents.set(2, content);
				}else if(Constant.VIEW_CONTENT_TYPE.SHOPPINGEXPLAIN.name().equalsIgnoreCase(content.getContentType())){
					showContents.set(3, content);
				}
			}
		}
		ebkProdProduct.setEbkProdProductId(ebkProdProductId);
		ebkProdProduct.setEbkProdContents(showContents);
		return ebkProductViewType+"editEbkProdOtherInit";
	}
	
	/*
	 * 查询标的，包含景点
	 */
	@Action("/ebooking/product/searchPlace")
	public void searchPlace(){				
		List<Place> list = this.placeService.selectSuggestPlaceByNameEBK(search);
		JSONArray array=new JSONArray();
		if(CollectionUtils.isNotEmpty(list)){
			for(Place cp:list){
				JSONObject obj=new JSONObject();
				obj.put("id", cp.getPlaceId());
				obj.put("text", cp.getName());
				array.add(obj);
			}
		}
		JSONOutput.writeJSON(getResponse(), array);
	}
	
	/*
	 * 查询属性列表
	 */
	@Action("/ebooking/product/showPropertyList")
	public String showPropertyList() {
		if(StringUtils.isNotBlank(this.checkedPropertyIds)){
			String ids[] = this.checkedPropertyIds.split("--");
			for (String id : ids) {
				this.checkedPropertyIdList.add(id);
			}
		}
		String firstModelId = super.getRequestParameter("firstModelId");
		String secondModelId = super.getRequestParameter("secondModelId");
		if(StringUtil.isEmptyString(firstModelId) || StringUtil.isEmptyString(secondModelId) || StringUtil.isEmptyString(subProductType)){
			return "showPropertyList";
		}
		Map<String,Object> params = new HashMap<String, Object>();
		params.put("isValid", "Y");
		params.put("firstModelId", firstModelId);
		params.put("secondModelId", secondModelId);
		params.put("isGroupSql", "( PRODUCT_TYPE like '%"+subProductType+";' or PRODUCT_TYPE like '%;"+subProductType+"' or PRODUCT_TYPE like '"+subProductType+";%' or PRODUCT_TYPE like '%;"+subProductType+";%' or PRODUCT_TYPE ='"+subProductType+"')");
		modelPropertyTypeList = productModelPropertyService.select(params);
		params.clear();
		params.put("secondModelId", secondModelId);
		modelTypeList = productModelTypeService.select(params);
		if(null!=modelPropertyTypeList && !modelPropertyTypeList.isEmpty() && null!=modelTypeList && !modelTypeList.isEmpty()){
			for(ProductModelProperty property:modelPropertyTypeList){
				for(ProductModelType type:modelTypeList){
					if(type.getId().longValue() == property.getSecondModelId().longValue()){
						property.setIsMultiChoice(type.getIsMultiChoice());
					}
				}
				//是否选中
				property.setIsValid("");
				if(null!=checkedPropertyIdList && !checkedPropertyIdList.isEmpty()){
					for(String id:checkedPropertyIdList){
						if(id.equals(String.valueOf(property.getId()))){
							property.setIsValid("checked");
						}
					}
				}
			}
		}
		return "showPropertyList";
	}
	
	/*
	 * 查询属性列表
	 */
	@Action("/ebooking/product/searchProp")
	public void searchProp() {
		JSONArray array=new JSONArray();
		String firstModelId = super.getRequestParameter("firstModelId");
		String secondModelId = super.getRequestParameter("secondModelId");
		Map<String,Object> params = new HashMap<String, Object>();
		params.put("isValid", "Y");
		params.put("firstModelId", firstModelId);
		params.put("secondModelId", secondModelId);
		params.put("isGroupSql", "( PRODUCT_TYPE like '%"+subProductType+";' or PRODUCT_TYPE like '%;"+subProductType+"' or PRODUCT_TYPE like '"+subProductType+";%' or PRODUCT_TYPE like '%;"+subProductType+";%' or PRODUCT_TYPE ='"+subProductType+"')");
		String prop = null;
		String pingying = null;
		if (null != search) {
			if (checkPy(search)) {
				pingying = search;
			} else {
				prop = search;
			}
		}		
		if (StringUtils.isNotBlank(prop)) {
			params.put("property", prop);
		}
		if (StringUtils.isNotBlank(pingying)) {
			params.put("pingying", pingying);
		}
		modelPropertyTypeList = productModelPropertyService.select(params);
		params.clear();
		params.put("secondModelId", secondModelId);
		modelTypeList = productModelTypeService.select(params);
		if(null!=modelPropertyTypeList && !modelPropertyTypeList.isEmpty() && null!=modelTypeList && !modelTypeList.isEmpty()){
			for(ProductModelProperty property:modelPropertyTypeList){
				JSONObject obj=new JSONObject();
				obj.put("id", property.getId());
				obj.put("text",property.getProperty());
				
				for(ProductModelType type:modelTypeList){
					if(type.getId().longValue() == property.getSecondModelId().longValue()){
						property.setIsMultiChoice(type.getIsMultiChoice());
						obj.put("type", type.getId());
						obj.put("isMultiChoice", type.getIsMultiChoice());
					}
				}
				//是否选中
				property.setIsValid("");
				if(null!=checkedPropertyIdList && !checkedPropertyIdList.isEmpty()){
					for(String id:checkedPropertyIdList){
						if(id.equals(String.valueOf(property.getId()))){
							property.setIsValid("checked");
						}
					}
				}
				array.add(obj);
			}
		}		
		JSONOutput.writeJSON(getResponse(), array);		
	}
	
	private boolean checkPy(String s) {
		for (int i = 0; i < s.length(); i++) {
			if (!(s.charAt(i) >= 'A' && s.charAt(i) <= 'Z')
					&& !(s.charAt(i) >= 'a' && s.charAt(i) <= 'z')) {
				return false;
			}
		}
		return true;
	}
	
	/*
	 * 查询对象
	 */
	@Action("/ebooking/product/showTargetList")
	public String showTargetList() {
		try{
			Map<String,Object> map = new HashMap<String, Object>();
			map.put("supplierId", this.getCurrentSupplierId());
			if("SUP_PERFORM_TARGET".equals(targetType)) {
				supplierPerformTargetList = performTargetService.findAllSupPerformTarget(map); 
			}else if("SUP_B_CERTIFICATE_TARGET".equals(targetType)) {
				supplierBCertificateTargetList = bCertificateTargetService.findBCertificateTarget(map);
			}else if("SUP_SETTLEMENT_TARGET".equals(targetType)) {
				supplierSettlementTargetList = settlementTargetService.findSupSettlementTarget(map);
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return "target_list";
	}
	@Action(value="/product/showEbkProdModelPropertyList")
	public String showEbkProdModelPropertyList(){
		if(null==subProductType){
			return "showEbkProdModelPropertyList";
		}
		showEbkPropertyModel(subProductType);
		return "showEbkProdModelPropertyList";
	}
	@Action(value="/product/showManangeUser")
	public void showManangeUser(){
		List<PermUser> list = this.permUserService.findPermUser(search);
		JSONArray array=new JSONArray();
		if(CollectionUtils.isNotEmpty(list)){
			for(PermUser user:list){
				JSONObject obj=new JSONObject();
				obj.put("id", user.getUserId());
				obj.put("text",user.getRealName());
				obj.put("mobile", user.getMobile());
				array.add(obj);
			}
		}
		JSONOutput.writeJSON(getResponse(), array);
	}
	@Action(value="/product/showCountry")
	public void showCountry(){
        List<PermUser> list = this.permUserService.findPermUser(search);
        JSONArray array=new JSONArray();
        if(CollectionUtils.isNotEmpty(list)){
            for(PermUser user:list){
                JSONObject obj=new JSONObject();
                obj.put("id", user.getUserId());
                obj.put("text",user.getRealName());
                obj.put("mobile", user.getMobile());
                array.add(obj);
            }
        }
        JSONOutput.writeJSON(getResponse(), array);
    }
	/**
	 * 获取产品类型集合
	 */
	public void getCurrency() {
		// 查询当前类型下的所有的产品类型
		SUB_PRODUCT_TYPE[] currencys = EBK_PRODUCT_VIEW_TYPE.getSubProductTypes(super.ebkProductViewType);
		productTypeList = new ArrayList<CodeItem>();
		for (SUB_PRODUCT_TYPE c : currencys) {
			productTypeList.add(new CodeItem(c.name(), c.getCnName()));
		}
	}
	
	private void showEbkPropertyModel(final String subProductType){
		Map<String, Object> params = new HashMap<String, Object>();
		if(subProductType.equals("FREENESS")){
			params.put("isGroupSql", "( PRODUCT_TYPE like '%FREENESS;' or PRODUCT_TYPE like '%;FREENESS' or PRODUCT_TYPE like 'FREENESS;%' or PRODUCT_TYPE like '%;FREENESS;%' or  PRODUCT_TYPE ='FREENESS')");
		}else if(subProductType.equals("GROUP")){
			params.put("isGroupSql", "( PRODUCT_TYPE like '%GROUP;' or PRODUCT_TYPE like '%;GROUP' or PRODUCT_TYPE like 'GROUP;%' or PRODUCT_TYPE like '%;GROUP;%' or  PRODUCT_TYPE ='GROUP')");
		}else{
			String[] productTypes = {subProductType};
			params.put("productTypes",productTypes);
		}
		modelPropertyList2 = productModelPropertyService.select(params);
		modelTypeList = productModelTypeService.select(new HashMap());
		if(null!=modelPropertyList2){
			modelPropertyList2 = removeSameModelProperty(modelPropertyList2);
			filterProperty(modelPropertyList2);
		}
	}
	/*
	 *初始所有属性
	 */
	private void initEbkProdModelProperty() {
		ebkProdModelPropertys = ebkProdModelPropertyService.findListByProductId(ebkProdProduct.getEbkProdProductId());
	}
	
	private void initPlaceName(){
		List<EbkProdPlace> places = ebkProdProduct.getEbkProdPlaces();
		if(null!=places && !places.isEmpty()){
			for(EbkProdPlace place:places){
				Place p = placeService.queryPlaceByPlaceId(place.getPlaceId());
				if(null!=p){
					place.setPlaceName(p.getName());
				}
			}
		}
	}
	/*
	 *初始所有对象
	 */
	private void initEbkProdTarget() {
		List<EbkProdTarget> ebkProdTargets = ebkProdTargetService.findListByProductId(ebkProdProduct.getEbkProdProductId());
		if(ebkProdTargets!=null&&ebkProdTargets.size()>0){
			for(EbkProdTarget target : ebkProdTargets){
				if(Constant.CONTACT_TYPE.SUP_PERFORM_TARGET.name().equalsIgnoreCase(target.getTargetType())){
					supplierPerformTargetList.add(performTargetService.getSupPerformTarget(target.getTargetId()));
				}
				if(Constant.CONTACT_TYPE.SUP_SETTLEMENT_TARGET.name().equalsIgnoreCase(target.getTargetType())){
					supplierSettlementTargetList.add(settlementTargetService.getSettlementTargetById(target.getTargetId()));
				}
				if(Constant.CONTACT_TYPE.SUP_B_CERTIFICATE_TARGET.name().equalsIgnoreCase(target.getTargetType())){
					supplierBCertificateTargetList.add(bCertificateTargetService.getBCertificateTargetByTargetId(target.getTargetId()));
				}
			}
		}
	}

	/*
	 * 基础信息初始
	 */
	private void initEbkProduct() {
		ebkProdProduct.setSupplierId(this.getCurrentSupplierId());
		ebkProdProduct.setProductType(super.ebkProductViewType);
	}
	private void initShowColumnMap(){
		//出发地名称
		if(null!=ebkProdProduct.getFromPlaceId() && ebkProdProduct.getFromPlaceId().longValue()!=0){
			Place fromPlace = placeService.queryPlaceByPlaceId(ebkProdProduct.getFromPlaceId());
			if(null!=fromPlace){
				showColumnMap.put("fromPlaceName", fromPlace.getName());
			}
		}
		//目的地名称
		if(null!=ebkProdProduct.getToPlaceId() && ebkProdProduct.getToPlaceId().longValue()!=0){
			Place toPlace = placeService.queryPlaceByPlaceId(ebkProdProduct.getToPlaceId());
			if(null!=toPlace){
				showColumnMap.put("toPlaceName", toPlace.getName());
			}
		}
		//驴妈妈联系人姓名
		if(null!=ebkProdProduct.getManagerId() && ebkProdProduct.getManagerId().longValue()!=0){
			PermUser user = permUserService.getPermUserByUserId(ebkProdProduct.getManagerId());
			if(null!=user){
				showColumnMap.put("manager", user);
			}
		}
	}
	/**
	 * 判断文件是否是图片文件
	 * @param file
	 * @return
	 */
	private boolean checkPictureType(File file)throws Exception {
		try {
			BufferedImage bi = ImageIO.read(file);
			if(bi == null){ 
				return false;
			}
		} catch (Exception e) {
			throw new Exception("文件不是图片格式！");
		}
		return true;
	}

	
	/**
	 * 判断文件是否大于指定大小
	 * @param file
	 * @return
	 */
	@SuppressWarnings("resource")
	public boolean checkImgSize(File f,int picSize){
		try {
			FileInputStream fis  = new FileInputStream(f);
			long size = fis.available();
			if(size>picSize*1024){
				return true;
			}else
				return false;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	/*
	 *获得行程描述日志信息
	 */
	private String getEbkProdTripLogMes(List<EbkProdJourney> ebkProdJourneys) {
		StringBuffer sb = new StringBuffer("产品行程更改为"+ebkProdJourneys.size()+"天；");
		for(EbkProdJourney ebkProdJourney : ebkProdJourneys){
			sb.append("第"+ebkProdJourney.getDayNumber().toString()+"天：");
			sb.append("标题修改为："+ebkProdJourney.getTitle()+";");
			sb.append("描述修改为："+ebkProdJourney.getContent()+";");
			sb.append("用餐修改为："+ebkProdJourney.getDinner()+";");
			sb.append("住宿修改为："+ebkProdJourney.getHotel()+";");
			if(ebkProdJourney.getTraffics()!=null&&ebkProdJourney.getTraffics().size()>0){
				sb.append("交通修改为：");
				for(String tra : ebkProdJourney.getTraffics()){
					sb.append(Constant.TRAFFIC_TYPE.getCnName(tra)+",");
				}
				sb.deleteCharAt(sb.lastIndexOf(","));
				sb.append(";");
			}
		}
		return sb.toString();
	}
	
	/**
	 * 去掉一级模块类型并且二级模块类型相同的模块属性
	 * @param mpList
	 * @return
	 */
	private List<ProductModelProperty> removeSameModelProperty(List<ProductModelProperty> mpList) {
		List<ProductModelProperty> pmpList = new ArrayList<ProductModelProperty>(); 
		for (ProductModelProperty mp : mpList) {
			boolean flag = true;
			for (ProductModelProperty pmp : pmpList) {
				if (pmp.getFirstModelId() == null || mp.getFirstModelId() == null) {
					continue;
				}
				if (pmp.getFirstModelId().longValue() == mp.getFirstModelId().longValue()
						&& pmp.getSecondModelId().longValue() == mp.getSecondModelId()) {
					flag = false;
					break;
				}
			}
			if (flag) {
				if(mp.getIsMaintain()!=null&&!"".equals(mp.getIsMaintain())&&"Y".equals(mp.getIsMaintain()))
					pmpList.add(mp);
			}
		}
		
			Comparator comp = new Comparator(){
	          public int compare(Object o1,Object o2) {
	              ProductModelProperty p1=(ProductModelProperty)o1;
	              ProductModelProperty p2=(ProductModelProperty)o2;
	             if(p1.getSeq()<p2.getSeq())
	                 return 0;
	             else
	                 return 1;
	             }
	        };
	        Collections.sort(pmpList, comp);
        List<ProductModelProperty> returnList = new ArrayList<ProductModelProperty>(); 
        for(ProductModelProperty pmp : pmpList){
        	boolean flag=false;
        	for(ProductModelProperty mp : mpList){
        		if("Y".equals(mp.getIsValid())&&mp.getSecondModelId().longValue()==pmp.getSecondModelId().longValue()){
        			flag=true;
        			returnList.add(pmp);
        			break;
        		}
        	}
        }
		return returnList;
	}
	private void filterProperty(List<ProductModelProperty> modelList) {
		for (ProductModelProperty a : modelList) {

			if(!StringUtil.isEmptyString(a.getProperty())){
				a.setProperty(a.getProperty().replaceAll("\\s*|\t|\r|\n", ""));
			}
			for(ProductModelType type:modelTypeList){
				if(type.getId().longValue()==a.getSecondModelId().longValue()){
					a.setIsMultiChoice(type.getIsMultiChoice());
				}
			}
		}
	}
	public List<CodeItem> getProductTypeList() {
		return productTypeList;
	}

	public void setProductTypeList(List<CodeItem> productTypeList) {
		this.productTypeList = productTypeList;
	}

	public List<ProductModelProperty> getModelPropertyTypeList() {
		return modelPropertyTypeList;
	}

	public void setModelPropertyTypeList(
			List<ProductModelProperty> modelPropertyTypeList) {
		this.modelPropertyTypeList = modelPropertyTypeList;
	}

	public String getProductTypes() {
		return productTypes;
	}

	public void setProductTypes(String productTypes) {
		this.productTypes = productTypes;
	}

	public String getEbkPropertyType() {
		return ebkPropertyType;
	}

	public void setEbkPropertyType(String ebkPropertyType) {
		this.ebkPropertyType = ebkPropertyType;
	}

	public String getTargetType() {
		return targetType;
	}

	public void setTargetType(String targetType) {
		this.targetType = targetType;
	}

	public String getSearch() {
		return search;
	}

	public void setSearch(String search) {
		this.search = search;
	}

	public String getPictureId() {
		return pictureId;
	}

	public void setPictureId(String pictureId) {
		this.pictureId = pictureId;
	}

	public String getPictureType() {
		return pictureType;
	}

	public void setPictureType(String pictureType) {
		this.pictureType = pictureType;
	}

	public String getMoveType() {
		return moveType;
	}

	public void setMoveType(String moveType) {
		this.moveType = moveType;
	}

	public File getIconFile() {
		return iconFile;
	}

	public void setIconFile(File iconFile) {
		this.iconFile = iconFile;
	}

	public String getIconFileContentType() {
		return iconFileContentType;
	}

	public void setIconFileContentType(String iconFileContentType) {
		this.iconFileContentType = iconFileContentType;
	}

	public String getIconFileFileName() {
		return iconFileFileName;
	}

	public void setIconFileFileName(String iconFileFileName) {
		this.iconFileFileName = iconFileFileName;
	}

	public File getBigFile() {
		return bigFile;
	}

	public void setBigFile(File bigFile) {
		this.bigFile = bigFile;
	}

	public String getBigFileContentType() {
		return bigFileContentType;
	}

	public void setBigFileContentType(String bigFileContentType) {
		this.bigFileContentType = bigFileContentType;
	}

	public String getBigFileFileName() {
		return bigFileFileName;
	}

	public void setBigFileFileName(String bigFileFileName) {
		this.bigFileFileName = bigFileFileName;
	}

	public File getTripPic() {
		return tripPic;
	}

	public void setTripPic(File tripPic) {
		this.tripPic = tripPic;
	}

	public String getTripPicContentType() {
		return tripPicContentType;
	}

	public void setTripPicContentType(String tripPicContentType) {
		this.tripPicContentType = tripPicContentType;
	}

	public String getTripPicFileName() {
		return tripPicFileName;
	}

	public void setTripPicFileName(String tripPicFileName) {
		this.tripPicFileName = tripPicFileName;
	}

	public String getTripSaveDayNum() {
		return tripSaveDayNum;
	}

	public void setTripSaveDayNum(String tripSaveDayNum) {
		this.tripSaveDayNum = tripSaveDayNum;
	}
	public String getTripPicDayNum() {
		return tripPicDayNum;
	}

	public void setTripPicDayNum(String tripPicDayNum) {
		this.tripPicDayNum = tripPicDayNum;
	}
	public List<SupPerformTarget> getSupplierPerformTargetList() {
		return supplierPerformTargetList;
	}

	public void setSupplierPerformTargetList(
			List<SupPerformTarget> supplierPerformTargetList) {
		this.supplierPerformTargetList = supplierPerformTargetList;
	}

	public List<SupSettlementTarget> getSupplierSettlementTargetList() {
		return supplierSettlementTargetList;
	}

	public void setSupplierSettlementTargetList(
			List<SupSettlementTarget> supplierSettlementTargetList) {
		this.supplierSettlementTargetList = supplierSettlementTargetList;
	}

	public List<SupBCertificateTarget> getSupplierBCertificateTargetList() {
		return supplierBCertificateTargetList;
	}

	public void setSupplierBCertificateTargetList(
			List<SupBCertificateTarget> supplierBCertificateTargetList) {
		this.supplierBCertificateTargetList = supplierBCertificateTargetList;
	}

	public String getCheckedPropertyIds() {
		return checkedPropertyIds;
	}

	public void setCheckedPropertyIds(String checkedPropertyIds) {
		this.checkedPropertyIds = checkedPropertyIds;
	}

	public List<String> getCheckedPropertyIdList() {
		return checkedPropertyIdList;
	}

	public void setCheckedPropertyIdList(List<String> checkedPropertyIdList) {
		this.checkedPropertyIdList = checkedPropertyIdList;
	}

	public List<ProductModelProperty> getModelPropertyList2() {
		return modelPropertyList2;
	}

	public void setModelPropertyList2(List<ProductModelProperty> modelPropertyList2) {
		this.modelPropertyList2 = modelPropertyList2;
	}

	public List<EbkProdModelProperty> getEbkProdModelPropertys() {
		return ebkProdModelPropertys;
	}

	public void setEbkProdModelPropertys(
			List<EbkProdModelProperty> ebkProdModelPropertys) {
		this.ebkProdModelPropertys = ebkProdModelPropertys;
	}

	public String getSubProductType() {
		return subProductType;
	}

	public void setSubProductType(String subProductType) {
		this.subProductType = subProductType;
	}

	public String getEbkProductEditModel() {
		return ebkProductEditModel;
	}

	public void setEbkProductEditModel(String ebkProductEditModel) {
		this.ebkProductEditModel = ebkProductEditModel;
	}

	public List<ProductModelType> getModelTypeList() {
		return modelTypeList;
	}

	public void setModelTypeList(List<ProductModelType> modelTypeList) {
		this.modelTypeList = modelTypeList;
	}

	public Map<String, Object> getShowColumnMap() {
		return showColumnMap;
	}

	public void setShowColumnMap(Map<String, Object> showColumnMap) {
		this.showColumnMap = showColumnMap;
	}

	public void setMoveTerm(String moveTerm) {
		this.moveTerm = moveTerm;
	}

	public void setPicName(String picName) {
		this.picName = picName;
	}

	public String getTemplateTarget() {
		return templateTarget;
	}

	public void setTemplateTarget(String templateTarget) {
		this.templateTarget = templateTarget;
	}

    public Constant.REGION_NAMES[] getRegionNames() {
        return regionNames;
    }

    public Constant.VISA_CITY[] getVisaCitys() {
        return visaCitys;
    }

	public ComPicture getToEditPicture() {
		return toEditPicture;
	}

	public void setToEditPicture(ComPicture toEditPicture) {
		this.toEditPicture = toEditPicture;
	}
	
	public Long getMultiJourneyId() {
		return multiJourneyId;
	}


	public void setMultiJourneyId(Long multiJourneyId) {
		this.multiJourneyId = multiJourneyId;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public EbkProdProductService getEbkProdProductService() {
		return ebkProdProductService;
	}


	public void setEbkProdProductService(EbkProdProductService ebkProdProductService) {
		this.ebkProdProductService = ebkProdProductService;
	}

	public String getIsCopy() {
		return isCopy;
	}


	public void setIsCopy(String isCopy) {
		this.isCopy = isCopy;
	}


	public EbkProdJourneyService getEbkProdJourneyService() {
		return ebkProdJourneyService;
	}


	public void setEbkProdJourneyService(EbkProdJourneyService ebkProdJourneyService) {
		this.ebkProdJourneyService = ebkProdJourneyService;
	}


	public EbkProdJourney getEbkProdJourney() {
		return ebkProdJourney;
	}
	public void setEbkProdJourney(EbkProdJourney ebkProdJourney) {
		this.ebkProdJourney = ebkProdJourney;
	}
	public EbkMultiJourney getEbkMultiJourney() {
		return ebkMultiJourney;
	}
	public void setEbkMultiJourney(EbkMultiJourney ebkMultiJourney) {
		this.ebkMultiJourney = ebkMultiJourney;
	}
	public EbkMultiJourneyService getEbkMultiJourneyService() {
		return ebkMultiJourneyService;
	}
	public void setEbkMultiJourneyService(
			EbkMultiJourneyService ebkMultiJourneyService) {
		this.ebkMultiJourneyService = ebkMultiJourneyService;
	}
	public List<EbkMultiJourney> getEbkMultiJourneyList() {
		return ebkMultiJourneyList;
	}
	public void setEbkMultiJourneyList(List<EbkMultiJourney> ebkMultiJourneyList) {
		this.ebkMultiJourneyList = ebkMultiJourneyList;
	}
	public EbkProdContentService getEbkProdContentService() {
		return ebkProdContentService;
	}


	public void setEbkProdContentService(EbkProdContentService ebkProdContentService) {
		this.ebkProdContentService = ebkProdContentService;
	}


	public EbkProdContent getEbkProdContent() {
		return ebkProdContent;
	}


	public void setEbkProdContent(EbkProdContent ebkProdContent) {
		this.ebkProdContent = ebkProdContent;
	}


	public String getParaMultiId() {
		return paraMultiId;
	}


	public void setParaMultiId(String paraMultiId) {
		this.paraMultiId = paraMultiId;
	}
	public List<EbkProdJourney> getEbkProdJourneyList() {
		return ebkProdJourneyList;
	}


	public void setEbkProdJourneyList(List<EbkProdJourney> ebkProdJourneyList) {
		this.ebkProdJourneyList = ebkProdJourneyList;
	}


	public String getDays() {
		return days;
	}


	public void setDays(String days) {
		this.days = days;
	}


	public String getTourDays() {
		return tourDays;
	}


	public void setTourDays(String tourDays) {
		this.tourDays = tourDays;
	}
	
}
