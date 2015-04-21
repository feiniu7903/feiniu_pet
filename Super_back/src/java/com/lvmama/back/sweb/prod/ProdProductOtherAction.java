package com.lvmama.back.sweb.prod;

import com.lvmama.comm.bee.po.prod.*;
import com.lvmama.comm.bee.service.prod.ProdProductModelPropertyService;
import com.lvmama.comm.bee.service.prod.ProdProductTagService;
import com.lvmama.comm.bee.service.prod.ProductModelPropertyService;
import com.lvmama.comm.bee.service.prod.ProductModelTypeService;
import com.lvmama.comm.jms.MessageFactory;
import com.lvmama.comm.pet.po.prod.ProdAssemblyPoint;
import com.lvmama.comm.pet.po.prod.ProdProductModelProperty;
import com.lvmama.comm.pet.po.prod.ProdProductTag;
import com.lvmama.comm.pet.po.pub.CodeItem;
import com.lvmama.comm.pet.po.pub.CodeSet;
import com.lvmama.comm.pet.po.pub.ComSubject;
import com.lvmama.comm.pet.service.pub.ComLogService;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.ProdProductModelPropertyVO;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.MessageFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Results({
		@Result(name = "routeInput", location = "/WEB-INF/pages/back/prod/product_route_other.jsp"),
		@Result(name = "auditingShow", location = "/WEB-INF/pages/back/prod/auditing/product_route_other_auditing_show.jsp"),
		@Result(name = "hotelInput", location = "/WEB-INF/pages/back/prod/product_hotel_other.jsp"),
		@Result(name = "goAction", location = "/prod/toProductOther.do?productId=${productId}&messageShow=${messageShow}", type = "redirect") })

public class ProdProductOtherAction extends ProductAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = -974938095452141020L;
	private ComLogService comLogService;
	private List<CodeItem> routeCateGoryList = new ArrayList<CodeItem>();
	private List<CodeItem> isPlayList = new ArrayList<CodeItem>();// 是否游玩
	private List<CodeItem> routeStandardList = new ArrayList<CodeItem>();// 线路标准
	private List<CodeItem> departAreaList = new ArrayList<CodeItem>();// 出境区域
	private List<String> departAreaCheckedList = new ArrayList<String>();// 出境区域选中的列表
	private List<CodeItem> hotelTypeList = new ArrayList<CodeItem>();// 房型特色
	private List<String> hotelTypeCheckedList = new ArrayList<String>();// 房型特色选中的列表
	private List<CodeItem> roomFeaturesList = new ArrayList<CodeItem>();
	private List<ProdAssemblyPoint> assemblyList;//上车地点
	private List<CodeItem> servicesList = new ArrayList<CodeItem>();
	private List<CodeItem> channelList = new ArrayList<CodeItem>();//
	private List<String> subject = new ArrayList<String>();
	private String routeCateGory;
	private String isPlay;
	private String routeStandard;
	private String departArea;
	private String comPcSubjectList1;
	private String comPcSubjectList2;
	private String hotelType;
	private String assemblyPoint;
	private Long assemblyPointId;
	/**
	 * 其他信息-产品2属性数据集合
	 */
	private String modelProData;

	/**
	 * 模块服务
	 */
	private ProductModelTypeService productModelTypeService;
	/**
	 * 模块属性服务
	 */
	private ProductModelPropertyService productModelPropertyService;
	/**
	 * 模块属性
	 */
	List<ProductModelProperty> modelPropertyList;
	/**
	 * 模块属性
	 */
	List<ProductModelProperty> modelPropertyList2;
	/**
	 * 模块列表
	 */
	private List<ProductModelType> modelTypeList;
	
	private ProdProductModelPropertyService prodProductModelPropertyService;
	private ProdProductTagService prodProductTagService;
	
	private List<ProductTypeVO> productTypeVOList;

	//用于数据绑定时列表
	private String initDataStr;
	private String initPropertyOne;

	//用于搜集属性1中选中值
	private String dataStr;
	
	private String travelTime;
	
	private String productModelProperty1;
	private String productModelProperty2;
	private String productModelProperty3;

	public ProdProductOtherAction() {
		super();
		setMenuType("other");
	}
    @Action(value = "/prod/toProductOtherAuditingShow")
    public String toProductOtherAuditingShow(){
        String resultName =  this.goEdit();
        if(resultName.equals("routeInput")){
          return "auditingShow";
        }else{
            return PRODUCT_EXCEPTION_PAGE;
        }


    }
	@Override
	@Action(value = "/prod/toProductOther")
	public String goEdit() {
		String result= "";
		//排斥线路和酒店套餐外的产品
		if(!doBefore()){
			return PRODUCT_EXCEPTION_PAGE;
		}
		//加载线路的商品信息
		if("ROUTE".equalsIgnoreCase(productType)){
			loadData();
		}
		
		//加载产品的属性
		filterModelProperty(product);
		assemblyList = prodProductService.queryAssembly(productId);
		//初始化当前产品的属性关联数据
		initData(); 
		//初始化产品属性1的相关属性
		initProductPropertyOne();
		
		if("ROUTE".equalsIgnoreCase(productType)){
			result = "routeInput";
		}else if("HOTEL_SUIT".equalsIgnoreCase(product.getSubProductType())){
			result = "hotelInput";
		}
		return result;
	}
	/**
	 * 初始化当前产品的属性关联数据
	 */
	private void initData(){
		List<ProdProductModelPropertyVO> initList=(List<ProdProductModelPropertyVO>)prodProductModelPropertyService.getProdProductModelPropertyVOByProductId(String.valueOf(productId));
		if(initList!=null&&initList.size()>0){
			StringBuffer sb=new StringBuffer("");
			StringBuffer sb2=new StringBuffer("");
			for(ProdProductModelPropertyVO model:initList){
				if(model.getIsMaintain().equals("Y")){
					sb.append(model.getSecondModelId()+","+model.getId()+","+model.getProperty()+"_");
				}else{
					sb2.append(model.getId()+",");
				}
				
			}
			if(sb.toString()!=null&&!"".equals(sb.toString())){
				initDataStr=sb.toString().substring(0,sb.toString().length()-1);
			}
			if(sb2.toString()!=null&&!"".equals(sb2.toString())){
				initPropertyOne=sb2.toString().substring(0,sb2.toString().length()-1);
			}
		}
	}
	
	/**
	 * 初始化产品属性1的相关属性
	 */
	private void initProductPropertyOne(){
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("isMaintain", "N");
		ProdProduct ProdProduct = prodProductService.getProdProductById(productId);
		String productType = ProdProduct.getSubProductType();
		List<ProductModelType> list = (List<ProductModelType>) productModelTypeService.select(map);
		
		if("ROUTE".equalsIgnoreCase(ProdProduct.getProductType())){
			ProdRoute prodRoute = prodProductService.getProdRouteById(productId);
			travelTime = prodRoute.getTravelTime();
		}
		
		if (list != null && list.size() > 0) {
			productTypeVOList = new ArrayList<ProductTypeVO>();
			ProductTypeVO productTypeVO = null;
			for (ProductModelType modelType : list) {
				if (modelType.getIsMaintain() != null && !"".equals(modelType.getIsMaintain()) && "N".equals(modelType.getIsMaintain())) {
					productTypeVO = new ProductTypeVO();
					map.clear();
					map.put("secondModelId", modelType.getId());
					List<ProductModelProperty> modelPropertyList = (List<ProductModelProperty>) productModelPropertyService.select(map);
					map = checkContains(modelPropertyList, productType);
					Boolean flag = (Boolean) map.get("returnFlag");
					modelPropertyList = (List<ProductModelProperty>) map.get("returnList");
					if (flag!=null&&flag.booleanValue()==true) {
						productTypeVO.setModelType(modelType);
						productTypeVO.setProductModelPropertyList(modelPropertyList);
						productTypeVOList.add(productTypeVO);
					}
				}
			}
		}
	}
	/**
	 * 判断产品属性1中游玩人数、往返交通等列表是否符合产品的属性以及获取适应产品属性的列表数据
	 */
	private Map<String,Object> checkContains(List<ProductModelProperty> list,String productType){
		boolean returnFlag = false;
		List<ProductModelProperty> newList = new ArrayList<ProductModelProperty>();
		if (list != null && list.size() > 0) {
			for (ProductModelProperty model : list) {
				String[] modelArray = model.getProductTypes();
				boolean flag = false;
				for (String str : modelArray) {
					if (str.equals(productType)) {
						flag = true;
						if (!returnFlag)
							returnFlag = true;
						break;
					}
				}
				if (flag) {
					newList.add(model);
				}
			}
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("returnFlag", returnFlag);
		map.put("returnList", newList);
		return map;
	}

	@Action(value = "/prod/saveProductOther")
	public String saveProductOther() throws IOException {
		PrintWriter out=this.getResponse().getWriter();
		try{
			System.out.println(modelProData);
			Long messageProductId=0l;
			if(modelProData!=null&&!"".equals(modelProData)){
				List<ProdProductModelProperty> list=new ArrayList<ProdProductModelProperty>();
				String productId = null;
				String[] data=modelProData.split("[*]");
				if(data!=null&&data.length>=2){
					productId=data[0];
					 messageProductId=Long.valueOf(productId);
					String propertyStr=data[1];
					
					if(propertyStr!=null&&!"".equals(propertyStr)){
						if(propertyStr.indexOf(",")>-1){
							String[] propertyData=propertyStr.split(",");
							ProdProductModelProperty prodProductModelProperty=null;
							
							for(String tempProperty:propertyData){
								prodProductModelProperty=new ProdProductModelProperty();
								prodProductModelProperty.setProductId(new Long(productId));
								prodProductModelProperty.setModelPropertyId(new Long(tempProperty));
								prodProductModelProperty.setIsMaintain("Y");
								list.add(prodProductModelProperty);
							}
						}else{
							ProdProductModelProperty prodProductModelProperty=new ProdProductModelProperty();
							prodProductModelProperty.setProductId(new Long(productId));
							prodProductModelProperty.setModelPropertyId(new Long(propertyStr));
							prodProductModelProperty.setIsMaintain("Y");
							list.add(prodProductModelProperty);
						}
					}
				}
				prodProductModelPropertyService.saveProdProductModelProperty(list);
				
				//增加选中的品牌标签
				if(data != null && data.length >= 2){
					addBrandTags(list, productId);
				}
			}
			
			//发送修改销售产品的通知ebk消息
			productMessageProducer.sendMsg(MessageFactory.newProductUpdateEbkMessage(messageProductId));
			out.print("OK");
		}catch(Exception ex){
			ex.printStackTrace();
			out.print("ERROR");
		}finally{
			out.close();
		}
		return null;
	}

	//增加选中的品牌标签
	private void addBrandTags(List<ProdProductModelProperty> list, String productId) {
		String tagName = null;
		List<Long> productIds = new ArrayList<Long>();
		List<ProdProductTag> prodProductTags = new ArrayList<ProdProductTag>();
		
		for(ProdProductModelProperty prodProductModelProperty  : list){
				Map<String, Object> params = new HashMap<String,Object>();
				params.put("id", prodProductModelProperty.getModelPropertyId());//属性ID
				params.put("secondModelId", new Long(7));	//品牌2级模块属性 ID
				params.put("isValid", "Y");
				List<ProductModelProperty>  productModelPropertyList = productModelPropertyService.select(params);
				
				//当前添加品牌属性，新增产品与标签关联
				if(productModelPropertyList != null && productModelPropertyList.size() > 0){
					tagName = productModelPropertyList.get(0).getProperty();
					ProdProductTag prodProductTag = new ProdProductTag();
					prodProductTag.setProductId(Long.parseLong(productId));
					prodProductTag.setCreator(Constant.PROD_PRODUCT_TAG_CREATOR.SYSTEM.getCode());
					
					productIds.add(Long.parseLong(productId));
					prodProductTags.add(prodProductTag);
				}
		}
		
		//标签保存(1,有记录---先删后插      2,没记录---新插)
		prodProductTagService.addSystemProgProductTags(prodProductTags, productIds, tagName);
	}
	
	
	@Action(value = "/prod/saveProductOther2")
	public String saveProductOther2() throws Exception {
		try{
			prodProductModelPropertyService.updateProdRule(String.valueOf(productId), routeCateGory, routeStandard, departArea,travelTime, dataStr);
			try {
				// 发送修改销售产品的消息
				productMessageProducer.sendMsg(MessageFactory.newProductUpdateMessage(product.getProductId()));
				
				//发送修改销售产品的通知ebk消息
				productMessageProducer.sendMsg(MessageFactory.newProductUpdateEbkMessage(product.getProductId()));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return "goAction";
	}
	@Override
	public void save() {
		// TODO Auto-generated method stub

	}

	/**
	 * 保存标签
	 * */
	@Action("/prod/insertOther")
	public String insertOther() {
		product = this.prodProductService.getProdProduct(productId);
		
		ProdRoute prodRoute = (ProdRoute)product;
		String logContent = getLogContent(prodRoute);
		
		prodRoute.setRouteCategory(routeCateGory);
		prodRoute.setIsPlay(isPlay);
		prodRoute.setRouteStandard(routeStandard);
		prodRoute.setRouteTitle_one(comPcSubjectList1);
		prodRoute.setRouteTitle_two(comPcSubjectList2);
		prodRoute.setHotelType(hotelType);
		prodRoute.setDepartArea(departArea);
		prodRoute.setRouteTitle(comPcSubjectList1 + "," + comPcSubjectList2);
		prodProductService.updateRouteOther(prodRoute,this.getOperatorName());
		
		sendUpdateProductMsg(product.getProductId());
		
		/**zx 20120307 add log*/
		comLogService.insert("PROD_PRODUCT",product.getProductId(), ((ProdRoute) product).getRouteId(),this.getOperatorName(),
				Constant.COM_LOG_ORDER_EVENT.updateProdProduct.name(),
				"更新销售产品标签", logContent, "PROD_PRODUCT");
		/***/
		
		return "goAction";
	}
	
	

	/* (non-Javadoc)
	 * @see com.lvmama.back.sweb.prod.ProductAction#doBefore()
	 */
	@Override
	public boolean doBefore() {
		// TODO Auto-generated method stub
		boolean flag = super.doBefore();
		if(!flag){
			return flag;
		}
		
		if(!("ROUTE".equalsIgnoreCase(productType) || "HOTEL_SUIT".equalsIgnoreCase(product.getSubProductType()))){
			return false;
		}
		return true;
	}

	/**
	 * 加载线路的商品信息
	 */
	public void loadData() {	
		ProdRoute prodRoute = (ProdRoute) product;
		productId = prodRoute.getProductId();
		isPlay = prodRoute.getIsPlay();
		routeCateGory = prodRoute.getRouteCategory();
		departArea = prodRoute.getDepartArea();
		routeStandard = prodRoute.getRouteStandard();
		hotelType = prodRoute.getHotelType();

		if (Constant.PRODUCT_TYPE.ROUTE.name().equals(product.getProductType())) {
			//comPcSubjectList = comPcSubjectService.selectByUsedBy("ROUTE");
			departAreaList = this.getAllList(prodRoute.getDepartArea(), departAreaList,"PROD_ROUTE_DEPART_AREA");
			isPlayList = this.getAllList(prodRoute.getIsPlay(), isPlayList,"PROD_ROUTE_IS_PLAY");
			routeCateGoryList = this.getAllList(prodRoute.getRouteCategory(),routeCateGoryList, "PROD_ROUTE_CATEGORY");
			routeStandardList = this.getAllList(prodRoute.getRouteStandard(),routeStandardList, "PROD_ROUTE_ROUTE_STANDARD");
			hotelTypeList = this.getAllList(prodRoute.getHotelType(), hotelTypeList,"PROD_ROUTE_REATURE");
			
			/**获取房型特色，出境区域选中的列表项 start*/
			for (CodeItem iter : hotelTypeList) {
				if(iter.isChecked()){
					hotelTypeCheckedList.add(iter.getCode());
				}
			}
			for (CodeItem iter : departAreaList) {
				if(iter.isChecked()){
					departAreaCheckedList.add(iter.getCode());
				}
			}
			/** end */
			
			if (prodRoute != null && prodRoute.getRouteTitle() != null) {
				String routeTitle[] = prodRoute.getRouteTitle().split(",");
				int len = 0;
				if(prodRoute.getRouteTitle().endsWith(","))
					len = routeTitle.length ;
				else 
					len = routeTitle.length -1;
				
				for(int i=0;i < len;i++){
					if(routeTitle.length<=1){
						comPcSubjectList1 = routeTitle[i].trim();
						comPcSubjectList2 = null;
					}
					else{
						comPcSubjectList1 = routeTitle[i].trim();
						comPcSubjectList2 = routeTitle[i+1].trim();
					}
				}
			} else {
				comPcSubjectList1 = null;
				comPcSubjectList2 = null;
			}
		}
	}

	private String replaceBlank(String str) {
		if (str == null || "".equals(str))
			return null;
		Pattern p = Pattern.compile("\\s*|\t|\r|\n");
		Matcher m = p.matcher(str);
		String after = m.replaceAll("");
		return after;
	}
	
	private void filterModelProperty(ProdProduct product){

		Map<String, Object> params = new HashMap<String, Object>();
		if(product.getSubProductType().equals("FREENESS")){
			params.put("isGroupSql", "( PRODUCT_TYPE like '%FREENESS;' or PRODUCT_TYPE like '%;FREENESS' or PRODUCT_TYPE like 'FREENESS;%' or PRODUCT_TYPE like '%;FREENESS;%' or  PRODUCT_TYPE ='FREENESS')");
		}else if(product.getSubProductType().equals("GROUP")){
			params.put("isGroupSql", "( PRODUCT_TYPE like '%GROUP;' or PRODUCT_TYPE like '%;GROUP' or PRODUCT_TYPE like 'GROUP;%' or PRODUCT_TYPE like '%;GROUP;%' or  PRODUCT_TYPE ='GROUP')");
		}else{
			String[] productTypes = {product.getSubProductType()};
			params.put("productTypes",productTypes);
		}
		modelPropertyList = productModelPropertyService.select(params);
		modelPropertyList2 = removeSameModelProperty(modelPropertyList);
		filterProperty(modelPropertyList);
		modelTypeList = productModelTypeService.select(new HashMap());
	}
	
	private void filterProperty(List<ProductModelProperty> modelList) {
		for (ProductModelProperty a : modelList) {
			a.setProperty(replaceBlank(a.getProperty()));
		}
	}
	
	/**
	 * 去掉一级模块类型并且二级模块类型相同的模块属性
	 * @param mpList
	 * @return
	 */
	public List<ProductModelProperty> removeSameModelProperty(List<ProductModelProperty> mpList) {
		List<ProductModelProperty> pmpList = new ArrayList<ProductModelProperty>(); 
		for (ProductModelProperty mp : mpList) {
			boolean flag = true;
			for (ProductModelProperty pmp : pmpList) {
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

	/**
	 * 初始化CodeItem
	 * */
	private List<CodeItem> getAllList(String property, List<CodeItem> list, String listType) {
		list = CodeSet.getInstance().getCachedCodeList(listType);
		String s = property;
		if (list != null && s != null) {
			String[] listSplit = s.split(",");
			for (int i = 0; i < listSplit.length; i++) {
				for (int j = 0; j < list.size(); j++) {
					CodeItem item = list.get(j);
					String d = listSplit[i].trim();
					if (d.equals(item.getCode())) {
						item.setChecked("true");
						break;
					}

				}
			}
		} 
		return list;
	}
	
	@Action("/prod/addAssembly")
	public String addAssembly() {
		if(!"".equals(assemblyPoint.trim())&&assemblyPoint!=null){
			ProdAssemblyPoint ap = new ProdAssemblyPoint();
			ap.setProductId(productId);
			ap.setAssemblyPoint(assemblyPoint);
			prodProductService.saveAssembly(ap, this.getOperatorName());
			//记录敏感词标识
			prodProductService.markProductSensitive(productId, hasSensitiveWord);		
		}
		return "goAction";
	}
	@Action("/prod/delAssembly")
	public String delAssembly(){
		if(assemblyPointId!=null){
		prodProductService.delAssembly(assemblyPointId, this.getOperatorName());
			//记录敏感词标识
		prodProductService.markProductSensitive(productId, null);
		}
		return "goAction";
	}
	

	public String getTravelTime() {
		return travelTime;
	}

	public void setTravelTime(String travelTime) {
		this.travelTime = travelTime;
	}

	public String getDataStr() {
		return dataStr;
	}

	public void setDataStr(String dataStr) {
		this.dataStr = dataStr;
	}

	public String getInitPropertyOne() {
		return initPropertyOne;
	}

	public void setInitPropertyOne(String initPropertyOne) {
		this.initPropertyOne = initPropertyOne;
	}

	public String getInitDataStr() {
		return initDataStr;
	}

	public void setInitDataStr(String initDataStr) {
		this.initDataStr = initDataStr;
	}

	public List<CodeItem> getRouteCateGoryList() {
		return routeCateGoryList;
	}

	public void setRouteCateGoryList(List<CodeItem> routeCateGoryList) {
		this.routeCateGoryList = routeCateGoryList;
	}

	public List<CodeItem> getIsPlayList() {
		return isPlayList;
	}

	public void setIsPlayList(List<CodeItem> isPlayList) {
		this.isPlayList = isPlayList;
	}

	public List<CodeItem> getRouteStandardList() {
		return routeStandardList;
	}

	public void setRouteStandardList(List<CodeItem> routeStandardList) {
		this.routeStandardList = routeStandardList;
	}

	public List<CodeItem> getDepartAreaList() {
		return departAreaList;
	}

	public void setDepartAreaList(List<CodeItem> departAreaList) {
		this.departAreaList = departAreaList;
	}

	public List<CodeItem> getHotelTypeList() {
		return hotelTypeList;
	}

	public void setHotelTypeList(List<CodeItem> hotelTypeList) {
		this.hotelTypeList = hotelTypeList;
	}

	public List<ComSubject> getComPcSubjectList() {
		return new ArrayList<ComSubject>();
	}

	public List<CodeItem> getRoomFeaturesList() {
		return roomFeaturesList;
	}

	public void setRoomFeaturesList(List<CodeItem> roomFeaturesList) {
		this.roomFeaturesList = roomFeaturesList;
	}

	public List<CodeItem> getServicesList() {
		return servicesList;
	}

	public void setServicesList(List<CodeItem> servicesList) {
		this.servicesList = servicesList;
	}

	public String getRouteCateGory() {
		return routeCateGory;
	}

	public void setRouteCateGory(String routeCateGory) {
		this.routeCateGory = routeCateGory;
	}

	public String getIsPlay() {
		return isPlay;
	}

	public void setIsPlay(String isPlay) {
		this.isPlay = isPlay;
	}

	public String getRouteStandard() {
		return routeStandard;
	}

	public void setRouteStandard(String routeStandard) {
		this.routeStandard = routeStandard;
	}

	public String getDepartArea() {
		return departArea;
	}

	public void setDepartArea(String departArea) {
		this.departArea = departArea;
	}

	public String getComPcSubjectList1() {
		return comPcSubjectList1;
	}

	public void setComPcSubjectList1(String comPcSubjectList1) {
		this.comPcSubjectList1 = comPcSubjectList1;
	}

	public String getComPcSubjectList2() {
		return comPcSubjectList2;
	}

	public void setComPcSubjectList2(String comPcSubjectList2) {
		this.comPcSubjectList2 = comPcSubjectList2;
	}

	public String getHotelType() {
		return hotelType;
	}

	public void setHotelType(String hotelType) {
		this.hotelType = hotelType;
	}

	public List<CodeItem> getChannelList() {
		return channelList;
	}

	public void setChannelList(List<CodeItem> channelList) {
		this.channelList = channelList;
	}

	public List<String> getSubject() {
		return subject;
	}

	public void setSubject(List<String> subject) {
		this.subject = subject;
	}

	public List<ProdAssemblyPoint> getAssemblyList() {
		return assemblyList;
	}

	public void setAssemblyList(List<ProdAssemblyPoint> assemblyList) {
		this.assemblyList = assemblyList;
	}

	public String getAssemblyPoint() {
		return assemblyPoint;
	}

	public void setAssemblyPoint(String assemblyPoint) {
		this.assemblyPoint = assemblyPoint;
	}

	public Long getAssemblyPointId() {
		return assemblyPointId;
	}

	public void setAssemblyPointId(Long assemblyPointId) {
		this.assemblyPointId = assemblyPointId;
	}

	/**
	 * @param comLogService the comLogService to set
	 */
	public void setComLogService(ComLogService comLogService) {
		this.comLogService = comLogService;
	}

	public List<String> getHotelTypeCheckedList() {
		return hotelTypeCheckedList;
	}

	public void setHotelTypeCheckedList(List<String> hotelTypeCheckedList) {
		this.hotelTypeCheckedList = hotelTypeCheckedList;
	}

	public List<String> getDepartAreaCheckedList() {
		return departAreaCheckedList;
	}

	public void setDepartAreaCheckedList(List<String> departAreaCheckedList) {
		this.departAreaCheckedList = departAreaCheckedList;
	}
	
	private String getLogContent(ProdRoute prodRoute){
		StringBuffer sb = new StringBuffer("");
		
		/** 记录 线路分类 日志*/
		if(!compareObject(prodRoute.getRouteCategory(), routeCateGory)){
			String orginalVlaue = "";
			String newValue = "";
			routeCateGoryList = this.getAllList(prodRoute.getRouteCategory(),routeCateGoryList, "PROD_ROUTE_CATEGORY");
			for (CodeItem item : routeCateGoryList) {
				if(item.isChecked()){
					orginalVlaue = item.getName();
					break;
				}
			}
			for (CodeItem item : routeCateGoryList) {
				if(null != routeCateGory && item.getCode().equals(routeCateGory)){
					newValue = item.getName();
					break;
				}
			}
			sb.append(MessageFormat.format("更新线路分类[原值为:{0},新值为:{1}]", orginalVlaue,newValue)).append(";");
		}
		
		/** 记录 是否纯玩 日志*/
		if(!compareObject(prodRoute.getIsPlay(), isPlay)){
			String orginalVlaue = "";
			String newValue = "";
			if(null != prodRoute.getIsPlay() && prodRoute.getIsPlay().equals("Y"))orginalVlaue = "是";
			else if(null != prodRoute.getIsPlay() && prodRoute.getIsPlay().equals("N"))orginalVlaue = "否";
			if(null != isPlay && isPlay.equals("Y"))newValue = "是";
			else if(null != isPlay && isPlay.equals("N"))newValue = "否";
			sb.append(MessageFormat.format("更新是否纯玩[原值为:{0},新值为:{1}]", orginalVlaue,newValue)).append(";");
		}
		
		/** 记录 线路标准 日志*/
		if(!compareObject(prodRoute.getRouteStandard(), routeStandard)){
			String orginalVlaue = "";
			String newValue = "";
			routeStandardList = this.getAllList(prodRoute.getRouteStandard(),routeStandardList, "PROD_ROUTE_ROUTE_STANDARD");
			for (CodeItem item : routeStandardList) {
				if(item.isChecked()){
					orginalVlaue = item.getName();
					break;
				}
			}
			for (CodeItem item : routeStandardList) {
				if(null != routeStandard && item.getCode().equals(routeStandard)){
					newValue = item.getName();
					break;
				}
			}
			sb.append(MessageFormat.format("更新线路标准[原值为:{0},新值为:{1}]", orginalVlaue,newValue)).append(";");
		}
		
		/** 记录 出境区域 日志*/
		if(!compareObject(prodRoute.getDepartArea(), departArea)){
			String orginalVlaue = "";
			String newValue = "";
			departAreaList = this.getAllList(prodRoute.getDepartArea(), departAreaList,"PROD_ROUTE_DEPART_AREA");
			for (int i = 0; i < departAreaList.size(); i++) {
				CodeItem item = departAreaList.get(i);
				if(item.isChecked()){
					orginalVlaue += item.getName();
					orginalVlaue += ",";
				}
			}
			if(orginalVlaue.indexOf(",") != -1){
				orginalVlaue = orginalVlaue.substring(0, orginalVlaue.lastIndexOf(","));
			}
			
			if (departAreaList != null && departArea != null) {
				String[] listSplit = departArea.split(",");
				for (int i = 0; i < listSplit.length; i++) {
					for (int j = 0; j < departAreaList.size(); j++) {
						CodeItem item = departAreaList.get(j);
						String d = listSplit[i].trim();
						if (d.equals(item.getCode())) {
							newValue += item.getName();
						}
					}
					if(i != (listSplit.length-1)){
						newValue += ",";
					}
				}
			}
			sb.append(MessageFormat.format("更新出境区域[原值为:{0},新值为:{1}]", orginalVlaue,newValue)).append(";");
		}
		
		/** 记录 线路主题 日志*/
		if(!compareObject(prodRoute.getRouteTitle(), comPcSubjectList1 + "," + comPcSubjectList2)){
			sb.append(MessageFormat.format("更新线路主题[原值为:{0},新值为:{1}]", prodRoute.getRouteTitle(),comPcSubjectList1 + "," + comPcSubjectList2)).append(";");
		}
		
		/** 记录 房型特色 日志*/
		if(!compareObject(prodRoute.getHotelType(), hotelType)){
			String orginalVlaue = "";
			String newValue = "";
			hotelTypeList = this.getAllList(prodRoute.getHotelType(), hotelTypeList,"PROD_ROUTE_REATURE");
			for (int i = 0; i < hotelTypeList.size(); i++) {
				CodeItem item = hotelTypeList.get(i);
				if(item.isChecked()){
					orginalVlaue += item.getName();
					orginalVlaue += ",";
				}
			}
			if(orginalVlaue.indexOf(",") != -1){
				orginalVlaue = orginalVlaue.substring(0, orginalVlaue.lastIndexOf(","));
			}
			
			if (hotelTypeList != null && hotelType != null) {
				String[] listSplit = hotelType.split(",");
				for (int i = 0; i < listSplit.length; i++) {
					for (int j = 0; j < hotelTypeList.size(); j++) {
						CodeItem item = hotelTypeList.get(j);
						String d = listSplit[i].trim();
						if (d.equals(item.getCode())) {
							newValue += item.getName();
						}
					}
					if(i != (listSplit.length-1)){
						newValue += ",";
					}
				}
			}
			sb.append(MessageFormat.format("更新房型特色[原值为:{0},新值为:{1}]", orginalVlaue,newValue)).append(";");
		}
		return sb.toString();
	}
	
	private boolean compareObject(String obj1,String obj2){
		if(obj1 == null && obj2 == null)return true;
		else if(null != obj1 && null != obj2 && obj1.trim().equals(obj2.trim()))return true;
		else return false;
	}
	
	/**
	 * 根据模块类型Id获取模块类型对象
	 * @param id
	 * @return
	 */
	public ProductModelType getModeTypeById(Long id) {
		if (modelTypeList != null) {
			for (ProductModelType modelType : modelTypeList) {
				if (id.longValue() == modelType.getId().longValue()) {
					return modelType;
				}
			}
		}
		return null;
	}
	
	private String getNameByCode(){
		
		return null;
	}

	public List<ProductTypeVO> getProductTypeVOList() {
		return productTypeVOList;
	}

	public void setProductTypeVOList(List<ProductTypeVO> productTypeVOList) {
		this.productTypeVOList = productTypeVOList;
	}
	
	public String getModelProData() {
		return modelProData;
	}

	public void setModelProData(String modelProData) {
		this.modelProData = modelProData;
	}
	
	public List<ProductModelProperty> getModelPropertyList() {
		return modelPropertyList;
	}

	public void setProductModelPropertyService(
			ProductModelPropertyService productModelPropertyService) {
		this.productModelPropertyService = productModelPropertyService;
	}

	public void setProductModelTypeService(
			ProductModelTypeService productModelTypeService) {
		this.productModelTypeService = productModelTypeService;
	}

	public List<ProductModelProperty> getModelPropertyList2() {
		return modelPropertyList2;
	}
	public ProdProductModelPropertyService getProdProductModelPropertyService() {
		return prodProductModelPropertyService;
	}

	public void setProdProductModelPropertyService(
			ProdProductModelPropertyService prodProductModelPropertyService) {
		this.prodProductModelPropertyService = prodProductModelPropertyService;
	}
	public String getProductModelProperty1() {
		return productModelProperty1;
	}

	public void setProductModelProperty1(String productModelProperty1) {
		this.productModelProperty1 = productModelProperty1;
	}

	public String getProductModelProperty2() {
		return productModelProperty2;
	}

	public void setProductModelProperty2(String productModelProperty2) {
		this.productModelProperty2 = productModelProperty2;
	}

	public String getProductModelProperty3() {
		return productModelProperty3;
	}

	public void setProductModelProperty3(String productModelProperty3) {
		this.productModelProperty3 = productModelProperty3;
	}

	public void setProdProductTagService(ProdProductTagService prodProductTagService) {
		this.prodProductTagService = prodProductTagService;
	}
	
}
