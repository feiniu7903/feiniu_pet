package com.lvmama.eplace.web.lvmama;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.jxls.transformer.XLSTransformer;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Window;

import com.lvmama.ZkBaseAction;
import com.lvmama.comm.bee.po.ord.OrdOrderItemMeta;
import com.lvmama.comm.bee.po.pass.PassPortCode;
import com.lvmama.comm.bee.po.pass.PassPortLog;
import com.lvmama.comm.bee.po.pass.UserRelateSupplierProduct;
import com.lvmama.comm.bee.service.eplace.EPlaceService;
import com.lvmama.comm.bee.service.ord.OrderService;
import com.lvmama.comm.bee.service.pass.PassCodeService;
import com.lvmama.comm.bee.vo.ord.CompositeQuery;
import com.lvmama.comm.bee.vo.ord.CompositeQuery.PageIndex;
import com.lvmama.comm.bee.vo.ord.CompositeQuery.PerformDetailRelate;
import com.lvmama.comm.bee.vo.ord.CompositeQuery.PerformDetailSortTypeEnum;
import com.lvmama.comm.bee.vo.ord.PerformDetail;
import com.lvmama.comm.pet.po.pub.CodeItem;
import com.lvmama.comm.pet.po.sup.SupPerformTarget;
import com.lvmama.comm.pet.service.sup.PerformTargetService;
import com.lvmama.comm.spring.SpringBeanProxy;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.utils.ResourceUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.passport.utils.ZkMessage;

/**
 * 
 * @author Huangl
 * 
 */
public class ListOrderFillAction extends ZkBaseAction {

	private static final long serialVersionUID = 5123568398598027070L;
	private static final String ORDER = "ORD_ORDER";
	private static final String ORDER_ITEM = "ORD_ORDER_ITEM_META";
	private Map<String, Object> queryOption = new HashMap<String, Object>();
	private List<PerformDetail> fulfillList = new ArrayList<PerformDetail>();
	private List<CodeItem> statusList = new ArrayList<CodeItem>();
	private OrderService orderServiceProxy = (OrderService) SpringBeanProxy.getBean("orderServiceProxy");
	private PageIndex pageIndex;
	private CompositeQuery compositeQuery;
	private PerformDetailRelate performDetailRelate = new PerformDetailRelate();
	private EPlaceService eplaceService;
	private boolean orderStatus = false;
	private String userString;
	private String operationDiplay;
	private String display;
	private PerformDetailSortTypeEnum sort= PerformDetailSortTypeEnum.ORDER_ID_DESC;
	private boolean isExport = false;
	private PerformTargetService performTargetService;
	private PassCodeService passCodeService;

	public void doBefore() throws Exception {
		this.statusList.add(new CodeItem("", "全部"));
		this.statusList.add(new CodeItem(Constant.ORDER_PERFORM_STATUS.PERFORMED.toString(), "已履行"));
		this.statusList.add(new CodeItem(Constant.ORDER_PERFORM_STATUS.UNPERFORMED.toString(), "未履行"));
		//设置默认时间
		Date temp=DateUtil.toDate(DateFormatUtils.format(new Date(), "yyyy-MM-dd"),"yyyy-MM-dd");
		this.queryOption.put("playTimeStart", DateUtils.addDays(temp,-30));
		this.queryOption.put("playTimeEnd", temp);
	}

	public void doQuery() throws Exception {
		this.doQuery(Constant.ORD_PERSON_TYPE.CONTACT.name());
	}
	
	public void doQuery(String personType) throws Exception {

		compositeQuery = new CompositeQuery();
		performDetailRelate = new PerformDetailRelate();

		Long userId = this.getSessionUser().getPassPortUserId();	
		performDetailRelate.setPassPortUserId(userId);
		/* 根据供应商名称查询 */
		if (queryOption.get("supplierName") != null && !"".equals(queryOption.get("supplierName"))) {
			String supplierName = (String) queryOption.get("supplierName");
			performDetailRelate.setSupplierName(supplierName.trim());
		}
		/* 根据采购产品查询 */
		if (queryOption.get("productName") != null && !"".equals(queryOption.get("productName"))) {
			String productName = (String) queryOption.get("productName");
			performDetailRelate.setMetaProductName(productName);
		}
		
		/* 根据下拉采购产品类别ID查询 */
		if (queryOption.get("metaProductBranchId") != null && !"".equals(queryOption.get("metaProductBranchId"))) {
			Long metaProductBranchId = (Long) queryOption.get("metaProductBranchId");
			if( metaProductBranchId==null || metaProductBranchId==-1||metaProductBranchId==0){
				performDetailRelate.setPassPortUserId(this.getSessionUser().getPassPortUserId());
			}else{
				performDetailRelate.setMetaProductBranchId(metaProductBranchId);
			}
		}

		/* 根据履行状态查询 */
		if (Constant.ORDER_PERFORM_STATUS.PERFORMED.toString().equals((String) queryOption.get("status"))) {
			performDetailRelate.setPerformStatus(Constant.ORDER_PERFORM_STATUS.PERFORMED);
		} else if (Constant.ORDER_PERFORM_STATUS.UNPERFORMED.toString().equals((String) queryOption.get("status"))) {
			performDetailRelate.setPerformStatus(Constant.ORDER_PERFORM_STATUS.UNPERFORMED);
		}

		/* 根据时间查询 */
		if(queryOption.get("playTimeStart") != null&&queryOption.get("playTimeEnd")!=null){
			performDetailRelate.setVisitTimeStart((Date) queryOption.get("playTimeStart"));
			performDetailRelate.setVisitTimeEnd((Date) queryOption.get("playTimeEnd"));
		}
		
		/* 根据订单状态查询 */
		if (orderStatus) {
			performDetailRelate.setOrderStatus(Constant.ORDER_STATUS.CANCEL.name());
		}
		
		/* 根据供手机号码查询 */
		if (queryOption.get("MoblieNumber") != null && !"".equals(queryOption.get("MoblieNumber"))) {
			String MoblieNumber = (String) queryOption.get("MoblieNumber");
			performDetailRelate.setContactMobile(MoblieNumber.trim());
		}
		
		/* 根据订单编号查询 */
		if (queryOption.get("orderId") != null && !"".equals(queryOption.get("orderId".trim()))) {
			Long orderId = Long.valueOf((String)queryOption.get("orderId"));
			performDetailRelate.setOrderId(orderId);
		}
		
		/* 根据供姓名查询 */
		if (queryOption.get("travellerName") != null && !"".equals(queryOption.get("travellerName"))) {
			String travellerName = (String) queryOption.get("travellerName");
			performDetailRelate.setContactName(travellerName.trim());
		}
		performDetailRelate.setPassPort(false);
		UserRelateSupplierProduct userRelateSupplierProduct = eplaceService.getSupplierUserForTargetId(userId);
		if (userRelateSupplierProduct!=null&&Constant.CCERT_TYPE.DIMENSION.name().equals(
				userRelateSupplierProduct.getSupPerformTarget().getCertificateType())) {
			performDetailRelate.setPassPort(true);
		}
		/**
		 * 联系人类型,此条件必加
		 */
		performDetailRelate.setPersonType(personType);
		compositeQuery.setPerformDetailRelate(performDetailRelate);
		compositeQuery.getPerformTypeList().add(sort);
		if(isExport){
			pageIndex = new PageIndex();
			pageIndex.setBeginIndex(0);
			pageIndex.setEndIndex(100000);
			compositeQuery.setPageIndex(pageIndex);
		}else{
			initialPageInfo(orderServiceProxy.queryPerformDetailCount(compositeQuery), compositeQuery);
		}
		fulfillList = orderServiceProxy.queryPerformDetail(compositeQuery);
	}

	public void chkListChecked(boolean isChecked){
		List<PerformDetail> chkList=new ArrayList<PerformDetail>();
		for (int i = 0; i < this.fulfillList.size(); i++) {
			PerformDetail performDetail=(PerformDetail)this.fulfillList.get(i);
			if(!performDetail.isDisabledPass()){
				performDetail.setChecked(isChecked);
			}
			chkList.add(performDetail);
		}
		this.fulfillList=chkList;
	}
	public void changeStatus(String status) {
		if ("".equals(status)) {
			this.queryOption.remove("status");
		} else {
			this.queryOption.put("status", status);
		}
	}

	public void doExcel() throws Exception {
		try {
			isExport = true;
			sort = PerformDetailSortTypeEnum.ORDER_ID_DESC;
			this.doQuery(Constant.ORD_PERSON_TYPE.CONTACT.name());
			isExport = false;
			File templateResource = ResourceUtil.getResourceFile("/WEB-INF/resources/template/fulfill.xls");
			String templateFileName = templateResource.getAbsolutePath();
			String destFileName = Constant.getTempDir() + "/excel.xls";

			Map beans = new HashMap();
			beans.put("fulfillList", fulfillList);
			XLSTransformer transformer = new XLSTransformer();
			transformer.transformXLS(templateFileName, beans, destFileName);

			File file = new File(destFileName);
			if (file != null && file.exists()) {
				Filedownload.save(file, "application/vnd.ms-excel");
			} else {
				alert("下载失败");
				return;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	public void exportPersonInfo(){
		try {
			isExport = true;
			sort= PerformDetailSortTypeEnum.ORDER_ID_DESC;
			this.doQuery(Constant.ORD_PERSON_TYPE.TRAVELLER.name());
			isExport = false;
			File templateResource = ResourceUtil.getResourceFile("/WEB-INF/resources/template/ordertravellerList.xls");
			String templateFileName = templateResource.getAbsolutePath();
			String destFileName = Constant.getTempDir() + "/excel.xls";

			Map beans = new HashMap();
			beans.put("fulfillList", fulfillList);
			XLSTransformer transformer = new XLSTransformer();
			transformer.transformXLS(templateFileName, beans, destFileName);

			File file = new File(destFileName);
			if (file != null && file.exists()) {
				Filedownload.save(file, "application/vnd.ms-excel");
			} else {
				alert("下载失败");
				return;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void doPrint(Window parentWindow, List<PerformDetail> fulfillList) throws Exception {
		// this.doQueryAll();
		isExport = true;
		sort = PerformDetailSortTypeEnum.ORDER_ID_DESC;
		this.doQuery(Constant.ORD_PERSON_TYPE.CONTACT.name());
		isExport = false;
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("fulfillList", fulfillList);
		String url = "/eplace/lvmama/list_print.zul";
		Window win = (Window) Executions.createComponents(url, parentWindow, params);
		win.setWidth("100%");
		win.setHeight("100%");
		win.setMaximizable(true);
		win.setClosable(true);
		win.doModal();
	}

	public void doQueryAll() {
		pageIndex = new PageIndex();
		pageIndex.setBeginIndex(1);
		pageIndex.setEndIndex(99999);
		compositeQuery = new CompositeQuery();
		compositeQuery.setPerformDetailRelate(performDetailRelate);
		compositeQuery.setPageIndex(pageIndex);
		fulfillList = orderServiceProxy.queryPerformDetail(compositeQuery);
	}
	
	/**
	 * 通过复选框选中订单通关.
	 * @param map
	 */
	public void passByChk(List chkList){
		String msg="";
		for (int i = 0; i < chkList.size(); i++) {
			PerformDetail performDetail=(PerformDetail)chkList.get(i);
			if(performDetail.isChecked()){
				msg+=this.pass(performDetail);
			}
		}
		if(StringUtils.isNotEmpty(msg)){
			ZkMessage.showInfo(msg);	
		}
	}
	
	/**
	 * 通过传递Map参数通关.
	 * @param map
	 */
	public void passByParam(Map<String, Long> map){
		PerformDetail performDetail =new PerformDetail();
		Long orderId = map.get("orderId");
		Long targetId = map.get("targetId");
		Long adultQuantity = map.get("adultQuantity");
		Long childQuantity = map.get("childQuantity");
		Long orderItemMetaId = map.get("orderItemMetaId");
		performDetail.setOrderId(orderId);
		performDetail.setTargetId(targetId);
		performDetail.setAdultQuantity(adultQuantity);
		performDetail.setChildQuantity(childQuantity);
		performDetail.setOrderItemMetaId(orderItemMetaId);
		String msg=this.pass(performDetail);
		if(!StringUtils.isNotEmpty(msg)){
			ZkMessage.showInfo(msg);	
		}
		
	}
	/**
	 * 执行通关动作.
	 * 
	 * @param performDetail
	 */
	public String pass(PerformDetail performDetail) {
		String msg="";
		SupPerformTarget supPerformTarget = this.performTargetService.getSupPerformTarget(performDetail.getTargetId());
		boolean flag = this.addPerform(performDetail.getOrderId(), performDetail.getTargetId(), performDetail.getAdultQuantity(), performDetail.getChildQuantity());
		if (Constant.CCERT_TYPE.DIMENSION.name().equals(supPerformTarget.getCertificateType())) {
			// 二维码信息更新
			List<Long> list = new ArrayList<Long>();
			list.add(performDetail.getOrderId());
			list.add(performDetail.getOrderItemMetaId());
			PassPortCode passPortCode = passCodeService.getPassPortCodeByObjectIdAndTargetId(list, performDetail.getTargetId());
			if (passPortCode != null) {
				passPortCode.setStatus(Constant.PASSCODE_USE_STATUS.USED.name());
				passPortCode.setUsedTime(new Date());
				// 更新通关点信息
				passCodeService.updatePassPortCode(passPortCode);
			}
		}
		PassPortLog passPortLog = new PassPortLog();
		passPortLog.setContent("通过E景通订单管理通关");
		passPortLog.setCreateDate(new Date());
		passPortLog.setOrderId(performDetail.getOrderId());
		passPortLog.setOrderItemMetaId(performDetail.getOrderItemMetaId());
		passPortLog.setPassPortUserId(this.getUser().getPassPortUserId());
		eplaceService.addPassPortLog(passPortLog);
		if (flag) {
			msg="订单【"+performDetail.getOrderId()+"】凭证正常通关;";
			super.refreshComponent("search");
		} else {
			msg="订单【"+performDetail.getOrderId()+"】已经履行;";
		}
		return msg;
	}

	/**
	 * 添加履行信息
	 * 
	 * @param orderId
	 * @param targetId
	 * @param adultQuantity
	 * @param childQuantity
	 */
	private boolean addPerform(Long orderId, Long targetId, Long adultQuantity, Long childQuantity) {
		boolean flag = false;
		CompositeQuery compositeQuery = new CompositeQuery();
		compositeQuery.getMetaPerformRelate().setOrderId(orderId);
		compositeQuery.getMetaPerformRelate().setTargetId(String.valueOf(targetId));
		compositeQuery.getPageIndex().setBeginIndex(1);
		compositeQuery.getPageIndex().setEndIndex(1000000000);
		List<OrdOrderItemMeta> orderItemMetas = orderServiceProxy
				.compositeQueryOrdOrderItemMetaByMetaPerformRelate(compositeQuery);
		int size = orderItemMetas.size();
		if (size > 1) {
			flag = orderServiceProxy.insertOrdPerform(targetId, orderId, ORDER, adultQuantity, childQuantity);
		} else {
			Long orderItemMetaId = orderItemMetas.get(0).getOrderItemMetaId();
			flag = orderServiceProxy.insertOrdPerform(targetId, orderItemMetaId, ORDER_ITEM, adultQuantity,
					childQuantity);
		}
		return flag;
	}
	
	public void doSort(Map<String, String> map) throws Exception{
		String s = map.get("sortType");
		if("prod".equals(s)){
			if(PerformDetailSortTypeEnum.META_PRODUCT_NAME_DESC.name().equals(sort.name())){
				sort = PerformDetailSortTypeEnum.META_PRODUCT_NAME_ASC;
			}else{
				sort = PerformDetailSortTypeEnum.META_PRODUCT_NAME_DESC;
			}
			
		}else if("traveller".equals(s)){
			if(PerformDetailSortTypeEnum.CONTACT_NAME_DESC.name().equals(sort.name())){
				sort = PerformDetailSortTypeEnum.CONTACT_NAME_ASC;
			}else{
				sort = PerformDetailSortTypeEnum.CONTACT_NAME_DESC;
			}
		}else if("orderId".equals(s)){
			if(PerformDetailSortTypeEnum.ORDER_ID_ASC.name().equals(sort.name())){
				sort = PerformDetailSortTypeEnum.ORDER_ID_DESC;
			}else{
				sort = PerformDetailSortTypeEnum.ORDER_ID_ASC;
			}
		}
		this.doQuery();
	}

	public Map<String, Object> getQueryOption() {
		return queryOption;
	}

	public void setQueryOption(Map<String, Object> queryOption) {
		this.queryOption = queryOption;
	}

	public List<CodeItem> getStatusList() {
		return statusList;
	}

	public void setStatusList(List<CodeItem> statusList) {
		this.statusList = statusList;
	}

	public List getFulfillList() {
		return fulfillList;
	}

	public void setFulfillList(List fulfillList) {
		this.fulfillList = fulfillList;
	}
 
	public void setOrderServiceProxy(OrderService orderServiceProxy) {
		this.orderServiceProxy = orderServiceProxy;
	}

	public void setEplaceService(EPlaceService eplaceService) {
		this.eplaceService = eplaceService;
	}

	public void setPassCodeService(PassCodeService passCodeService) {
		this.passCodeService = passCodeService;
	}

	public boolean isOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(boolean orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getUserString() {
		return userString;
	}

	public void setUserString(String userString) {
		this.userString = userString;
	}

	public String getOperationDiplay() {
		return operationDiplay;
	}

	public void setOperationDiplay(String operationDiplay) {
		this.operationDiplay = operationDiplay;
	}

	public String getDisplay() {
		return display;
	}

	public void setDisplay(String display) {
		this.display = display;
	}

	public PerformDetailSortTypeEnum getSort() {
		return sort;
	}

	public void setSort(PerformDetailSortTypeEnum sort) {
		this.sort = sort;
	}

	public void setPerformTargetService(PerformTargetService performTargetService) {
		this.performTargetService = performTargetService;
	}
}
