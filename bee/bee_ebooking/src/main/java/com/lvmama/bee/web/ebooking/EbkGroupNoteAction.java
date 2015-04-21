package com.lvmama.bee.web.ebooking;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.bee.web.EbkBaseAction;
import com.lvmama.bee.web.groupnote.ViewJourneyVo;
import com.lvmama.comm.bee.po.ebooking.GroupTravelInfo;
import com.lvmama.comm.bee.po.ebooking.GroupTravelTemplate;
import com.lvmama.comm.bee.po.ebooking.TravelFlight;
import com.lvmama.comm.bee.po.ebooking.TravellerInfo;
import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.po.ord.OrdOrderItemMeta;
import com.lvmama.comm.bee.po.ord.OrdPerson;
import com.lvmama.comm.bee.po.prod.ProdProduct;
import com.lvmama.comm.bee.po.prod.ProdRoute;
import com.lvmama.comm.bee.po.prod.ViewJourney;
import com.lvmama.comm.bee.service.ebooking.GroupTravelTemplateService;
import com.lvmama.comm.bee.service.ord.IGroupAdviceNoteService;
import com.lvmama.comm.bee.service.ord.OrderService;
import com.lvmama.comm.bee.service.prod.ProdProductService;
import com.lvmama.comm.pet.client.EContractClient;
import com.lvmama.comm.pet.client.FSClient;
import com.lvmama.comm.pet.po.pub.ComAffix;
import com.lvmama.comm.pet.service.pub.ComAffixService;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.utils.econtract.EcontractUtil;
import com.lvmama.comm.utils.json.ResultHandleT;
import com.lvmama.comm.utils.pdf.FormatLinePDFUtil;
import com.lvmama.comm.vo.Constant;

/**
 * EBK线路出团通知书
 * @author yanzhirong 2014-01-01
 * @version
 */
@ParentPackage("json-default")
@Results(value={
		@Result(name = "downPdfContract", 
				params = { "contentType", "application/octet-stream;charset=UTF-8", "inputName",
				"pdfStream", "contentDisposition", "attachment;filename=${filename}", "bufferSize", "4096" },
				type="stream"),
		@Result(name = "getGroupAdviceNoteForGroupLong", location="/WEB-INF/pages/ebooking/groupAdviceNote/groupAdviceNoteForGroupLong.jsp"),
		@Result(name = "getGroupAdviceNoteForGroupForeign", location="/WEB-INF/pages/ebooking/groupAdviceNote/groupAdviceNoteForGroupForeign.jsp"),
		@Result(name = "getGroupAdviceNoteForFreenessLong", location="/WEB-INF/pages/ebooking/groupAdviceNote/groupAdviceNoteForFreenessLong.jsp" ),
		@Result(name = "getGroupAdviceNoteForFreenessForeign", location="/WEB-INF/pages/ebooking/groupAdviceNote/groupAdviceNoteForFreenessForeign.jsp"),
		@Result(name = "previewGroupAdviceNoteGroupLong", location="/WEB-INF/pages/ebooking/groupAdviceNote/previewGroupAdviceNoteGroupLong.jsp"),
		@Result(name = "previewGroupAdviceNoteGroupForeign", location = "/WEB-INF/pages/ebooking/groupAdviceNote/previewGroupAdviceNoteGroupForeign.jsp"),
		@Result(name = "previewGroupAdviceNoteFreenessLong", location = "/WEB-INF/pages/ebooking/groupAdviceNote/previewGroupAdviceNoteFreenessLong.jsp"),
		@Result(name = "previewGroupAdviceNoteFreenessForeign", location = "/WEB-INF/pages/ebooking/groupAdviceNote/previewGroupAdviceNoteFreenessForeign.jsp")
})
public class EbkGroupNoteAction extends EbkBaseAction {

	private static final long serialVersionUID = -5974791127978080226L;
	private final String PDF_HEAD = EcontractUtil
			.getTemplateContent("/WEB-INF/resources/groupAdviceNoteTemplate/GROUP_HEAD_PDF.xml");
	private final String PDF_FOOT = "</body></html>";
	private Long orderId;
	private String productName;
	private String subProductType;
	private String firstTravellerName;
	private String travelTimeStart;
	private String travelTimeEnd;
	private String noCostContain;
	private String costContain;
	private String actionToKnow;
	private List<OrdPerson> travellerList;
	private List<ViewJourneyVo> viewJourneyVoList;
	private GroupTravelInfo groupTravelInfo;
	private OrderService orderServiceProxy;
	private ProdProductService prodProductService;
	private EContractClient contractClient;
	private FSClient fsClient;
	private ComAffixService comAffixService;
	private IGroupAdviceNoteService groupAdviceNoteServiceProxy;
	private GroupTravelTemplateService groupTravelTemplateService;
	private String[] startDate;
	private String[] flightNo;
	private InputStream pdfStream;
    private String filename;
    private String content;
    private TravellerInfo travellerInfo;
    private TravelFlight travelFlight;
    private List<TravelFlight> travelFlightList;
    private ViewJourneyVo viewJourneyVo;
    private String flightDate;
    private String place;
    private String jiesong;
    private String adultQuantity;
    private String childQuantity;
    private String quantity;
    private String hotelTips;
    private String hotelOrderList;
    private String sendType;
	/**
	 * 获取出团通知书
	 * @return
	 */
	@Action("/ebooking/task/getGroupNote")
	public String getGroupNote(){
		String type = initViewPageAndJourney(orderId);
		return getUrlPage(type);
	}
	
	private String initViewPageAndJourney(final Long orderId){
		OrdOrder ordOrder = this.orderServiceProxy
				.queryOrdOrderByOrderId(orderId);
		ProdProduct product = this.prodProductService.getProdProductById(ordOrder
				.getMainProduct().getProductId());
		ProdRoute prodRoute = this.prodProductService
				.getProdRouteById(ordOrder.getMainProduct().getProductId());
		prodRoute.setSubProductType(product.getSubProductType());
		
		productName = product.getProductName();
		subProductType = product.getSubProductType();
		firstTravellerName = ordOrder.getFristTravellerOrdPerson().getName();
		travellerList = ordOrder.getTravellerList();
		List<OrdOrderItemMeta> ordItemList = ordOrder.getAllOrdOrderItemMetas();
		if (null != ordItemList) {
			for (OrdOrderItemMeta oi : ordItemList) {
				adultQuantity = oi.getAdultQuantity().toString();
				childQuantity = oi.getChildQuantity().toString();
				quantity = oi.getQuantity().toString();
				break;
			}
		}
		
		//填写具体游玩时间范围
		travelTimeStart = DateUtil.getFormatDate(ordOrder.getVisitTime(), "yyyy-MM-dd");
		//行程天数.
		int days = prodRoute.getDays().intValue();
		Date endDate = DateUtil.dsDay_Date(ordOrder.getVisitTime(),(days - 1));
		travelTimeEnd = DateUtil.getFormatDate(endDate, "yyyy-MM-dd");
		
		Map<String, Object> map = contractClient.loadRouteTravelObject(orderId);
		if(null == map){
			return ERROR;
		}
		List<ViewJourney> rstVjList = (List<ViewJourney>) map.get("viewJourneys");
		for (ViewJourney e : rstVjList) {
			if (null == viewJourneyVoList) {
				viewJourneyVoList = new ArrayList<ViewJourneyVo>();
			}
			viewJourneyVoList.add(new ViewJourneyVo(e, ordOrder.getVisitTime()));
		}
		
		noCostContain = (String)map.get("NOCOSTCONTAIN");
		costContain = (String)map.get("COSTCONTAIN");
		actionToKnow = (String)map.get("ACITONTOKNOW");
		return product.getSubProductType();
	}
	
	/**
	 * 修改出团通知书
	 * @return
	 */
	@Action("/ebooking/task/modifyGroupNote")
	public String modifyGroupNote(){
		GroupTravelTemplate gtt = groupTravelTemplateService.selectByOrdId(orderId);
		if (null == gtt) {
			return ERROR;
		}
		subProductType = gtt.getSubProductType();
		// 获取内容
		String content = gtt.getTemplateContent();
		getViewPageAndJourney(parseJSON2Map(content));
		return getUrlPage(gtt.getSubProductType());
	}
	
	@Action("/ebooking/task/gotoGroupNote")
	public String gotoGroupNote(){
		if (null != travellerInfo) {
			travellerList = travellerInfo.getList();
		}
		if (null != groupTravelInfo) {
			travelFlightList = groupTravelInfo.getTravelFlightList();
		}
		if (null != viewJourneyVo) {
			viewJourneyVoList = viewJourneyVo.getViewJourneyList();
		}
		return getUrlPage(subProductType);
	}
	
	private void getViewPageAndJourney(Map<String, Object> dataMap) {
		if (null != dataMap.get("productName")) {
			productName = dataMap.get("productName").toString();
		}
		if (null != dataMap.get("firstTravellerName")) {
			firstTravellerName = dataMap.get("firstTravellerName").toString();
		}
		if (null != dataMap.get("travelTimeStart")) {
			travelTimeStart = dataMap.get("travelTimeStart").toString();
		}
		if (null != dataMap.get("travelTimeEnd")) {
			travelTimeEnd = dataMap.get("travelTimeEnd").toString();
		}
		if (null != dataMap.get("groupTravelInfo")) {
			JSONObject json = JSONObject.fromObject(dataMap.get("groupTravelInfo"));
			groupTravelInfo = (GroupTravelInfo)JSONObject.toBean(json, GroupTravelInfo.class);
		}
		if (null != dataMap.get("travellerList")) {
			travellerList = (List<OrdPerson>) dataMap.get("travellerList");
		}
		if (null != dataMap.get("travelFlightList")) {
			travelFlightList = (List<TravelFlight>)dataMap.get("travelFlightList");
		}
		if (null != dataMap.get("viewJourneyVoList")) {
			viewJourneyVoList = (List<ViewJourneyVo>)dataMap.get("viewJourneyVoList");
		}
		if (null != dataMap.get("NOCOSTCONTAIN")) {
			noCostContain = dataMap.get("NOCOSTCONTAIN").toString();
		}
		if (null != dataMap.get("COSTCONTAIN")) {
			costContain = dataMap.get("COSTCONTAIN").toString();
		}
		if (null != dataMap.get("ACITONTOKNOW")) {
			actionToKnow = dataMap.get("ACITONTOKNOW").toString();
		}
		if (null != dataMap.get("flightDate")) {
			flightDate = dataMap.get("flightDate").toString();
		}
		if (null != dataMap.get("place")) {
			place = dataMap.get("place").toString();
		}
		if (null != dataMap.get("jiesong")) {
			jiesong = dataMap.get("jiesong").toString();
		}
		if (null != dataMap.get("adultQuantity")) {
			adultQuantity = dataMap.get("adultQuantity").toString();
		}
		if (null != dataMap.get("childQuantity")) {
			childQuantity = dataMap.get("childQuantity").toString();
		}
		if (null != dataMap.get("quantity")) {
			quantity = dataMap.get("quantity").toString();
		}
		if (null != dataMap.get("hotelTips")) {
			hotelTips = dataMap.get("hotelTips").toString();
		}
		if (null != dataMap.get("hotelOrderList")) {
			hotelOrderList = dataMap.get("hotelOrderList").toString();
		}
	}
	
	private String getUrlPage(String type) {
		if(Constant.SUB_PRODUCT_TYPE.FREENESS_FOREIGN.name().equals(type)){
			return "getGroupAdviceNoteForFreenessForeign";
		}else if(Constant.SUB_PRODUCT_TYPE.FREENESS_LONG.name().equals(type)){
			return "getGroupAdviceNoteForFreenessLong";
		}else if(Constant.SUB_PRODUCT_TYPE.GROUP_FOREIGN.name().equals(type)){
			return "getGroupAdviceNoteForGroupForeign";
		}else if(Constant.SUB_PRODUCT_TYPE.GROUP_LONG.name().equals(type)){
			return "getGroupAdviceNoteForGroupLong";
		}else{
			return ERROR;
		}
	}
	
	private Map<String, Object> parseJSON2Map(String jsonStr){
        Map<String, Object> map = new HashMap<String, Object>();
        //最外层解析
        JSONObject json = JSONObject.fromObject(jsonStr);
        for(Object k : json.keySet()){
            Object v = json.get(k); 
            //如果内层还是数组的话，继续解析
            if(v instanceof JSONArray){
                List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
                Iterator<JSONObject> it = ((JSONArray)v).iterator();
                while(it.hasNext()){
                    JSONObject json2 = it.next();
                    list.add(parseJSON2Map(json2.toString()));
                }
                map.put(k.toString(), list);
            } else {
                map.put(k.toString(), v);
            }
        }
        return map;
    }
	
	/**
	 * 预览出团通知书
	 * @return
	 */
	@Action("/ebooking/task/previewGroupAdviceNote")
	public String previewGroupAdviceNote(){
		filename = orderId + "_GROUP_ADVICE_NOTE_" + DateUtil.getFormatDate(new java.util.Date(), "yyyyMMddHHmmss") + ".pdf";
		this.pdfStream = EcontractUtil.createInPutContractPdf(formatConent(content));
		return "downPdfContract";
	}
	
	private String formatConent(String content) {
		StringBuilder sb = new StringBuilder();
		sb.append(PDF_HEAD);
		content = FormatLinePDFUtil.replaceHtml(content, "name=\"viewJourneyVo.content\">", "</br>", 40);
		content = FormatLinePDFUtil.replaceHtml(content, "name=\"costContain\">", "</br>", 47);
		content = FormatLinePDFUtil.replaceHtml(content, "name=\"noCostContain\">", "</br>", 47);
		content = FormatLinePDFUtil.replaceHtml(content, "name=\"actionToKnow\">", "</br>", 47);
		content = FormatLinePDFUtil.replaceHtml(content, "name=\"hotelTips\">", "</br>", 47);
		content = FormatLinePDFUtil.replaceHtml(content, "name=\"hotelOrderList\">", "</br>", 47);
		sb.append(content.replace("*", ""));
		sb.append(PDF_FOOT);
		return sb.toString();
	}
	
	private void inputstreamtofile(InputStream ins, File file) {
		  try {
		   OutputStream os = new FileOutputStream(file);
		   int bytesRead = 0;
		   byte[] buffer = new byte[8192];
		   while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
		    os.write(buffer, 0, bytesRead);
		   }
		   os.close();
		   ins.close();
		  } catch (Exception e) {
		   e.printStackTrace();
		  }
	}
	
	/**
	 * 提交出团通知书
	 * @return
	 */
	@Action("/ebooking/task/submitGroupAdviceNote")
	public void submitGroupAdviceNote(){
		JSONObject json = new JSONObject();
		try {
			// 文件名：订单号_GROUP_ADVICE_NOTE_yyyyMMddHHmmss.pdf
			filename = orderId + "_GROUP_ADVICE_NOTE_" + DateUtil.getFormatDate(new java.util.Date(), "yyyyMMddHHmmss") + ".pdf";
			this.pdfStream = EcontractUtil.createInPutContractPdf(formatConent(content));
			File pdfFile = new File(filename);
			inputstreamtofile(this.pdfStream, pdfFile);
			
			Long fileId = fsClient.uploadFile(pdfFile, "COM_AFFIX");
			if (fileId != null && pdfFile.exists()) {
				pdfFile.delete();
			}

			// 文件添加记录
			ComAffix affix = new ComAffix();
			affix.setObjectId(orderId);
			affix.setObjectType("ORD_ORDER");
			affix.setUserId(this.getSessionUserName());
			affix.setName(filename);
			affix.setFileId(fileId);// 设定文件路径id
			affix.setCreateTime(new Date());
			affix.setFileType(ComAffix.GROUP_ADVICE_NOTE_FILE_TYPE);// 文件类型：出团通知书
			affix.setMemo("出团通知书");
			comAffixService.addAffixForGroupAdvice(affix, this.getSessionUserName());// 插入文件记录
			
			// 添加或修改模板内容
			updateTemplate();

			// 发送通知和邮件
			ResultHandleT<Boolean> handle = groupAdviceNoteServiceProxy
					.sendGroupAdviceNote(orderId, this.getSessionUserName());
			if (handle.isSuccess()) {
				json.put("flag", "success");
			} else {
				json.put("flag", "fail");
			}
		} catch (Exception ex) {
			json.put("flag", "fail");
			ex.printStackTrace();
		}
		this.sendAjaxResultByJson(json.toString());
	}
	
	private void updateTemplate() {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		// 更新模板信息
		dataMap.put("productName", productName);
		dataMap.put("firstTravellerName", firstTravellerName);
		dataMap.put("travelTimeStart", travelTimeStart);
		dataMap.put("travelTimeEnd", travelTimeEnd);
		dataMap.put("groupTravelInfo", groupTravelInfo);
		if (null != travellerInfo && null != travellerInfo.getList()) {
			dataMap.put("travellerList", travellerInfo.getList());
		}
		if (null != groupTravelInfo && null != groupTravelInfo.getTravelFlightList()) {
			dataMap.put("travelFlightList", groupTravelInfo.getTravelFlightList());
		}
		if (null != viewJourneyVo && null != viewJourneyVo.getViewJourneyList()) {
			dataMap.put("viewJourneyVoList", viewJourneyVo.getViewJourneyList());
		}
		dataMap.put("NOCOSTCONTAIN", noCostContain);
		dataMap.put("COSTCONTAIN", costContain);
		dataMap.put("ACITONTOKNOW", actionToKnow);
	    dataMap.put("flightDate", flightDate);
	    dataMap.put("place", place);
	    dataMap.put("jiesong", jiesong);
	    dataMap.put("adultQuantity", adultQuantity);
	    dataMap.put("childQuantity", childQuantity);
	    dataMap.put("quantity", quantity);
	    dataMap.put("hotelTips", hotelTips);
	    dataMap.put("hotelOrderList", hotelOrderList);
		
		JSONObject dataJson = JSONObject.fromObject(dataMap);
		GroupTravelTemplate groupTravelTemplate = groupTravelTemplateService.selectByOrdId(orderId);
		if (null == groupTravelTemplate) {
			groupTravelTemplate = new GroupTravelTemplate();
			groupTravelTemplate.setOrderId(orderId);
			groupTravelTemplate.setSubProductType(subProductType);
			groupTravelTemplate.setTemplateContent(dataJson.toString());
			groupTravelTemplate.setCreateUser(this.getSessionUserName());
			groupTravelTemplate.setCreateDate(Calendar.getInstance().getTime());
			groupTravelTemplateService.insert(groupTravelTemplate);
		} else {
			groupTravelTemplate.setTemplateContent(dataJson.toString());
			groupTravelTemplateService.update(groupTravelTemplate);
		}
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public void setOrderServiceProxy(OrderService orderServiceProxy) {
		this.orderServiceProxy = orderServiceProxy;
	}

	public void setProdProductService(ProdProductService prodProductService) {
		this.prodProductService = prodProductService;
	}

	public String getFirstTravellerName() {
		return firstTravellerName;
	}

	public void setFirstTravellerName(String firstTravellerName) {
		this.firstTravellerName = firstTravellerName;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getSubProductType() {
		return subProductType;
	}

	public void setSubProductType(String subProductType) {
		this.subProductType = subProductType;
	}

	public String getTravelTimeStart() {
		return travelTimeStart;
	}

	public void setTravelTimeStart(String travelTimeStart) {
		this.travelTimeStart = travelTimeStart;
	}

	public String getTravelTimeEnd() {
		return travelTimeEnd;
	}

	public void setTravelTimeEnd(String travelTimeEnd) {
		this.travelTimeEnd = travelTimeEnd;
	}

	public List<OrdPerson> getTravellerList() {
		return travellerList;
	}

	public void setTravellerList(List<OrdPerson> travellerList) {
		this.travellerList = travellerList;
	}

	public void setContractClient(EContractClient contractClient) {
		this.contractClient = contractClient;
	}

	public List<ViewJourneyVo> getViewJourneyVoList() {
		return viewJourneyVoList;
	}

	public void setViewJourneyVoList(List<ViewJourneyVo> viewJourneyVoList) {
		this.viewJourneyVoList = viewJourneyVoList;
	}

	public String getNoCostContain() {
		return noCostContain;
	}

	public void setNoCostContain(String noCostContain) {
		this.noCostContain = noCostContain;
	}

	public String getCostContain() {
		return costContain;
	}

	public void setCostContain(String costContain) {
		this.costContain = costContain;
	}

	public String getActionToKnow() {
		return actionToKnow;
	}

	public void setActionToKnow(String actionToKnow) {
		this.actionToKnow = actionToKnow;
	}

	public GroupTravelTemplateService getGroupTravelTemplateService() {
		return groupTravelTemplateService;
	}

	public void setGroupTravelTemplateService(
			GroupTravelTemplateService groupTravelTemplateService) {
		this.groupTravelTemplateService = groupTravelTemplateService;
	}

	public FSClient getFsClient() {
		return fsClient;
	}


	public void setFsClient(FSClient fsClient) {
		this.fsClient = fsClient;
	}


	public ComAffixService getComAffixService() {
		return comAffixService;
	}


	public void setComAffixService(ComAffixService comAffixService) {
		this.comAffixService = comAffixService;
	}


	public IGroupAdviceNoteService getGroupAdviceNoteServiceProxy() {
		return groupAdviceNoteServiceProxy;
	}


	public void setGroupAdviceNoteServiceProxy(
			IGroupAdviceNoteService groupAdviceNoteServiceProxy) {
		this.groupAdviceNoteServiceProxy = groupAdviceNoteServiceProxy;
	}


	public String[] getStartDate() {
		return startDate;
	}


	public void setStartDate(String[] startDate) {
		this.startDate = startDate;
	}


	public String[] getFlightNo() {
		return flightNo;
	}

	public void setFlightNo(String[] flightNo) {
		this.flightNo = flightNo;
	}

	public InputStream getPdfStream() {
		return pdfStream;
	}

	public void setPdfStream(InputStream pdfStream) {
		this.pdfStream = pdfStream;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public GroupTravelInfo getGroupTravelInfo() {
		return groupTravelInfo;
	}

	public void setGroupTravelInfo(GroupTravelInfo groupTravelInfo) {
		this.groupTravelInfo = groupTravelInfo;
	}

	public TravellerInfo getTravellerInfo() {
		return travellerInfo;
	}

	public void setTravellerInfo(TravellerInfo travellerInfo) {
		this.travellerInfo = travellerInfo;
	}

	public TravelFlight getTravelFlight() {
		return travelFlight;
	}

	public void setTravelFlight(TravelFlight travelFlight) {
		this.travelFlight = travelFlight;
	}

	public List<TravelFlight> getTravelFlightList() {
		return travelFlightList;
	}

	public void setTravelFlightList(List<TravelFlight> travelFlightList) {
		this.travelFlightList = travelFlightList;
	}

	public ViewJourneyVo getViewJourneyVo() {
		return viewJourneyVo;
	}

	public void setViewJourneyVo(ViewJourneyVo viewJourneyVo) {
		this.viewJourneyVo = viewJourneyVo;
	}

	public String getFlightDate() {
		return flightDate;
	}

	public void setFlightDate(String flightDate) {
		this.flightDate = flightDate;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public String getJiesong() {
		return jiesong;
	}

	public void setJiesong(String jiesong) {
		this.jiesong = jiesong;
	}

	public String getAdultQuantity() {
		return adultQuantity;
	}

	public void setAdultQuantity(String adultQuantity) {
		this.adultQuantity = adultQuantity;
	}

	public String getChildQuantity() {
		return childQuantity;
	}

	public void setChildQuantity(String childQuantity) {
		this.childQuantity = childQuantity;
	}

	public String getQuantity() {
		return quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

	public String getHotelTips() {
		return hotelTips;
	}

	public void setHotelTips(String hotelTips) {
		this.hotelTips = hotelTips;
	}

	public String getHotelOrderList() {
		return hotelOrderList;
	}

	public void setHotelOrderList(String hotelOrderList) {
		this.hotelOrderList = hotelOrderList;
	}

	public String getSendType() {
		return sendType;
	}

	public void setSendType(String sendType) {
		this.sendType = sendType;
	}
	
	
}
