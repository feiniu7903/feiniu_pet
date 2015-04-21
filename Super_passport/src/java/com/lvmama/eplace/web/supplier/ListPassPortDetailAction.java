package com.lvmama.eplace.web.supplier;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.jxls.transformer.XLSTransformer;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Window;
import org.zkoss.zul.api.Listitem;

import com.lvmama.ZkBaseAction;
import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.po.ord.OrdOrderItemMeta;
import com.lvmama.comm.bee.po.pass.EplaceSupplier;
import com.lvmama.comm.bee.po.pass.PassPortCode;
import com.lvmama.comm.bee.po.pass.PassPortDetail;
import com.lvmama.comm.bee.po.pass.PassPortLog;
import com.lvmama.comm.bee.po.pass.UserRelateSupplierProduct;
import com.lvmama.comm.bee.service.eplace.EPlaceService;
import com.lvmama.comm.bee.service.ord.OrderService;
import com.lvmama.comm.bee.service.pass.PassCodeService;
import com.lvmama.comm.bee.vo.ord.CompositeQuery;
import com.lvmama.comm.bee.vo.ord.CompositeQuery.PassPortDetailRelate;
import com.lvmama.comm.bee.vo.ord.CompositeQuery.PerformDetailSortTypeEnum;
import com.lvmama.comm.spring.SpringBeanProxy;
import com.lvmama.comm.utils.ResourceUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.PassportConstant;
import com.lvmama.passport.utils.ZkMessage;

/**
 * 通关详细列表
 * 
 * @author chenlinjun
 * 
 */
@SuppressWarnings("unchecked")
public class ListPassPortDetailAction extends ZkBaseAction {

	private static final long serialVersionUID = -105298829562762620L;
	private static final String ORDER = "ORD_ORDER";
	private static final String ORDER_ITEM = "ORD_ORDER_ITEM_META";
	private List<PassPortDetail> passPortList = new ArrayList<PassPortDetail>();
	private OrderService orderServiceProxy = (OrderService) SpringBeanProxy.getBean("orderServiceProxy");
	private Date visitTime;
	private Long metaProductBranchId = 0l;
	private int pagingSize = 100000000;

	private EPlaceService eplaceService;
	private boolean orderStatus = false;
	private boolean operate=true;
	private boolean passed=false;
	private PassCodeService passCodeService;
	
	private PerformDetailSortTypeEnum sort= PerformDetailSortTypeEnum.STATUS;
	@Override
	protected void doBefore() throws Exception {
		this.passPortList.clear();
		CompositeQuery compositeQuery = new CompositeQuery();
		PassPortDetailRelate passPortDetailRelate = new PassPortDetailRelate();
		Long userId = this.getSessionUser().getPassPortUserId();
		//UserRelateSupplierProduct userRelateSupplierProduct = ePlaceService.getSupplierUserForTargetId(userId);
		/* 根据采购编号查询 */
		if (metaProductBranchId > 0) {
			passPortDetailRelate.setMetaProductBranchId(metaProductBranchId);
		}else if(this.metaProductBranchId==-1){
			passPortDetailRelate.setPassPortUserId(this.getSessionUser().getPassPortUserId());
		}
		if (orderStatus) {
			passPortDetailRelate.setOrderStatus(Constant.ORDER_STATUS.CANCEL.name());
		}
		/* 根据时间查询 */
		if (visitTime != null) {
			passPortDetailRelate.setVisitTimeStart(visitTime);
			//Date tmep = DateUtil.addDays(visitTime, 1);
			passPortDetailRelate.setVisitTimeEnd(visitTime);
		}
		passPortDetailRelate.setPassPort(false);
		UserRelateSupplierProduct userRelateSupplierProduct = eplaceService.getSupplierUserForTargetId(userId);
		if (userRelateSupplierProduct!=null&&Constant.CCERT_TYPE.DIMENSION.name().equals(
				userRelateSupplierProduct.getSupPerformTarget().getCertificateType())) {
			passPortDetailRelate.setPassPort(true);
		}
		// Long
		// targetId=userRelateSupplierProduct.getSupPerformTarget().getTargetId();
		// if(targetId!=null){
		// passPortDetailRelate.setTargetId(targetId);
		// }
		compositeQuery.setPassPortDetailRelate(passPortDetailRelate);
		compositeQuery.getPageIndex().setBeginIndex(0);
		compositeQuery.getPageIndex().setEndIndex(10000000);
		compositeQuery.getPerformTypeList().add(sort);
		// initialPageInfo(orderServiceProxy.queryPassPortDetailCount(compositeQuery),
		// compositeQuery);
		List<PassPortDetail> list = orderServiceProxy.queryPassPortDetail(compositeQuery);
		EplaceSupplier eplaceSupplier = (EplaceSupplier) super.session
				.getAttribute(PassportConstant.SESSION_EPLACE_SUPPLIER);
		if (eplaceSupplier != null && !eplaceSupplier.isCustomerVisible()) {
			for (PassPortDetail passPortDetail : list) {
				if(this.passed){
					if(passPortDetail.getIsPass()){
						passPortDetail.setContactMobile("---");
						this.passPortList.add(passPortDetail);
					}
				}else{
					if(passPortDetail.isNotPass()){
						passPortDetail.setContactMobile("---");
						this.passPortList.add(passPortDetail);
					}
				}
			}
		} else {
			for (PassPortDetail passPortDetail : list) {
				if(this.passed){
					if(passPortDetail.getIsPass()){
						this.passPortList.add(passPortDetail);
					}
				}else{
					if(passPortDetail.isNotPass()){
						this.passPortList.add(passPortDetail);
					}
				}
			}
		}
	    if (this.passPortList.size() > 5000) {
	        System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
	        System.out.println("list.size() = " + list.size());
	        System.out.println("passPortList.size() = " + this.passPortList.size());
	        System.out.println("getSessionUser().getPassPortUserId()" + getSessionUser().getPassPortUserId());
	        System.out.println(this.passPortList + 
	          " visitTime=" + this.visitTime + ", metaProductBranchId=" + this.metaProductBranchId + 
	          ", pagingSize=" + this.pagingSize + ", orderStatus=" + this.orderStatus + ", operate=" + 
	          this.operate + ", passed=" + this.passed + ", sort=" + this.sort + "]");
	        this.passPortList.clear();
	      }
	}

	public void doQuery() throws Exception {
		this.passPortList.clear();
		CompositeQuery compositeQuery = new CompositeQuery();
		PassPortDetailRelate passPortDetailRelate = new PassPortDetailRelate();
		//Long userId = this.getSessionUser().getPassPortUserId();
		//UserRelateSupplierProduct userRelateSupplierProduct = ePlaceService.getSupplierUserForTargetId(userId);
		/* 根据采购编号查询 */
		if (metaProductBranchId > 0) {
			passPortDetailRelate.setMetaProductid(metaProductBranchId);
		}else if(this.metaProductBranchId==-1){
			passPortDetailRelate.setPassPortUserId(this.getSessionUser().getPassPortUserId());
		}
		if (orderStatus) {
			passPortDetailRelate.setOrderStatus(Constant.ORDER_STATUS.CANCEL.name());
		}
		/* 根据时间查询 */
		if (visitTime != null) {
			passPortDetailRelate.setVisitTimeStart(visitTime);
			//Date tmep = DateUtil.addDays(visitTime, 1);
			passPortDetailRelate.setVisitTimeEnd(visitTime);
		}
		// Long
		// targetId=userRelateSupplierProduct.getSupPerformTarget().getTargetId();
		// if(targetId!=null){
		// passPortDetailRelate.setTargetId(targetId);
		// }
		compositeQuery.setPassPortDetailRelate(passPortDetailRelate);
		compositeQuery.getPageIndex().setBeginIndex(0);
		compositeQuery.getPageIndex().setEndIndex(10000000);
		compositeQuery.getPerformTypeList().add(sort);
		// initialPageInfo(orderServiceProxy.queryPassPortDetailCount(compositeQuery),
		// compositeQuery);
		List<PassPortDetail> list = orderServiceProxy.queryPassPortDetail(compositeQuery);
		EplaceSupplier eplaceSupplier = (EplaceSupplier) super.session
				.getAttribute(PassportConstant.SESSION_EPLACE_SUPPLIER);
		if (eplaceSupplier != null && !eplaceSupplier.isCustomerVisible()) {
			for (PassPortDetail passPortDetail : list) {
				if(this.passed){
					if(passPortDetail.getIsPass()){
						passPortDetail.setContactMobile("---");
						this.passPortList.add(passPortDetail);
					}
				}else{
					if(passPortDetail.isNotPass()){
						passPortDetail.setContactMobile("---");
						this.passPortList.add(passPortDetail);
					}
				}
			}
		} else {
			for (PassPortDetail passPortDetail : list) {
				if(this.passed){
					if(passPortDetail.getIsPass()){
						this.passPortList.add(passPortDetail);
					}
				}else{
					if(passPortDetail.isNotPass()){
						this.passPortList.add(passPortDetail);
					}
				}
			}
		}
	    if (this.passPortList.size() > 5000) {
	        System.out.println("=====================================================================================================");
	        System.out.println("list.size() = " + list.size());
	        System.out.println("passPortList.size() = " + this.passPortList.size());
	        System.out.println("getSessionUser().getPassPortUserId()" + getSessionUser().getPassPortUserId());
	        System.out.println("metaProductBranchId = " + metaProductBranchId);
	        System.out.println("orderStatus = " + orderStatus);
	        System.out.println("visitTime = " + visitTime);
	        System.out.println("sort = " + sort);
	        this.passPortList.clear();
	      }
	}

	/**
	 * 通关
	 */
	public void pass(Set<Listitem> set) {
		boolean flag = false;
		Long userId = this.getSessionUser().getPassPortUserId();
		// List<UserRelateSupplierProduct> userRelateSupplierProducts =
		// ePlaceService.getMeatProductBySupplierUserId(userId);
		UserRelateSupplierProduct userRelateSupplierProduct = eplaceService.getSupplierUserForTargetId(userId);
		if (Constant.CCERT_TYPE.DIMENSION.name().equals(
				userRelateSupplierProduct.getSupPerformTarget().getCertificateType())) {
			flag = true;
		}
		List<PassPortDetail> passPortList = new ArrayList<PassPortDetail>();
		if (set != null && set.size() > 0) {
			for (Iterator<Listitem> iter = set.iterator(); iter.hasNext();) {
				Listitem listitem = (Listitem) iter.next();
				PassPortDetail passPortDetail = (PassPortDetail) listitem.getValue();
				if (!passPortDetail.getIsPass()) {
					passPortList.add(passPortDetail);
				}
			}
			for (PassPortDetail passPortDetail : passPortList) {
				Long orderId = passPortDetail.getOrderId();
				Long targetId = userRelateSupplierProduct.getSupPerformTarget().getTargetId();
				//Long adultQuantity = passPortDetail.getAdultQuantity();
				//Long childQuantity = passPortDetail.getChildQuantity();
				Long adultQuantity = 0l;
				Long childQuantity = 0l;
				Long ordItemId = passPortDetail.getOrderItemMetaId();
				this.addPerform(orderId, targetId, adultQuantity, childQuantity);
				if (flag) {
					// 二维码信息更新
					List<Long> list = new ArrayList<Long>();
					list.add(orderId);
					list.add(ordItemId);
					PassPortCode passPortCode = passCodeService.getPassPortCodeByObjectIdAndTargetId(list, targetId);
					if (passPortCode != null&&Constant.PASSCODE_USE_STATUS.UNUSED.name().equals(passPortCode.getStatus())) {
						passPortCode.setStatus(Constant.PASSCODE_USE_STATUS.USED.name());
						passPortCode.setUsedTime(new Date());
						// 更新通关点信息
						passCodeService.updatePassPortCode(passPortCode);
					}
				}
				PassPortLog passPortLog = new PassPortLog();
				passPortLog.setContent("通过E景通通关");
				passPortLog.setCreateDate(new Date());
				passPortLog.setOrderId(orderId);
				passPortLog.setOrderItemMetaId(ordItemId);
				passPortLog.setPassPortUserId(this.getUser().getPassPortUserId());
				eplaceService.addPassPortLog(passPortLog);
			}
			super.refreshComponent("search");
			ZkMessage.showInfo("通关成功");
		} else {
			ZkMessage.showInfo("请选择要通关的产品");
		}
	}

	/**
	 * 添加履行信息
	 * 
	 * @param orderId
	 * @param targetId
	 * @param adultQuantity
	 * @param childQuantity
	 */
	private void addPerform(Long orderId, Long targetId, Long adultQuantity, Long childQuantity) {
		CompositeQuery compositeQuery = new CompositeQuery();
		compositeQuery.getMetaPerformRelate().setOrderId(orderId);
		compositeQuery.getMetaPerformRelate().setTargetId(String.valueOf(targetId));
		compositeQuery.getPageIndex().setBeginIndex(0);
		compositeQuery.getPageIndex().setEndIndex(1000000000);
		List<OrdOrderItemMeta> orderItemMetas = orderServiceProxy
				.compositeQueryOrdOrderItemMetaByMetaPerformRelate(compositeQuery);
		int size = orderItemMetas.size();
		if (size > 1) {
			orderServiceProxy.insertOrdPerform(targetId, orderId, ORDER, adultQuantity, childQuantity);
		} else {
			Long orderItemMetaId = orderItemMetas.get(0).getOrderItemMetaId();
			orderServiceProxy.insertOrdPerform(targetId, orderItemMetaId, ORDER_ITEM, adultQuantity, childQuantity);
		}
	}

	public void doExcel() throws Exception {
		try {
			File templateResource = ResourceUtil.getResourceFile("/WEB-INF/resources/template/passportdetail.xls");
			String templateFileName = templateResource.getAbsolutePath();
			String destFileName = Constant.getTempDir() + "/detail.xls";

			Map beans = new HashMap();
			beans.put("fulfillList", passPortList);
			XLSTransformer transformer = new XLSTransformer();
			transformer.transformXLS(templateFileName, beans, destFileName);

			File file = new File(destFileName);
			if (file != null && file.exists()) {
				Filedownload.save(file, "application/vnd.ms-excel");
			} else {
				alert("操作失败");
				return;
			}
			alert("操作成功");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void doPrint(Window win) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
//		params.put("visitTime", visitTime);
//		params.put("metaProductid", metaProductid);
//		params.put("pagingSize", pagingSize);
		params.put("passPortList", passPortList);
		String url = "/eplace/supplier/list_print.zul";
		Window win2 = (Window) Executions.createComponents(url, win, params);
		win2.setWidth("96%");
		win2.setMaximizable(true);
		win2.setClosable(true);
		win2.doModal();
	}

	public void cancelOrder(Set<Listitem> set) {
		List<PassPortDetail> canceList = new ArrayList<PassPortDetail>();
		if (set != null && set.size() > 0) {
			for (Iterator<Listitem> iter = set.iterator(); iter.hasNext();) {
				Listitem listitem = (Listitem) iter.next();
				PassPortDetail passPortDetail = (PassPortDetail) listitem.getValue();
				if (!passPortDetail.isCanceled()&&
					passPortDetail.isPayToSupplier()) {
					OrdOrder ordOrder=orderServiceProxy.queryOrdOrderByOrderId(passPortDetail.getOrderId());
					if(!Constant.ORDER_STATUS.FINISHED.name().equalsIgnoreCase(ordOrder.getOrderStatus().trim())){
						canceList.add(passPortDetail);
					}
				}
			}
			if(canceList.size()==0){
				ZkMessage.showInfo("请选择支付给供应商的且没有履行的订单");
			}else{
				for (int i = 0; i < canceList.size(); i++) {
					orderServiceProxy.cancelOrder(canceList.get(i).getOrderId(), "通过E景通系统废单", this.getSessionUser()
							.getUserId().toString());
					PassPortLog passPortLog = new PassPortLog();
					passPortLog.setContent("通过E景通系统废单");
					passPortLog.setCreateDate(new Date());
					passPortLog.setOrderId(canceList.get(i).getOrderId());
					passPortLog.setOrderItemMetaId(canceList.get(i).getOrderItemMetaId());
					passPortLog.setPassPortUserId(this.getUser().getPassPortUserId());
					eplaceService.addPassPortLog(passPortLog);
				}
				super.refreshComponent("search");
				ZkMessage.showInfo("废单成功");
			}
		} else {
			ZkMessage.showInfo("请选择订单");
		}

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
		}else if("usedTime".equals(s)){
			if(PerformDetailSortTypeEnum.USED_TIME_ASC.name().equals(sort.name())){
				sort = PerformDetailSortTypeEnum.USED_TIME_DESC;
			}else{
				sort = PerformDetailSortTypeEnum.USED_TIME_ASC;
			}
		}
		this.doQuery();
	}

	public void changePageSize(String pageNum) {
		pagingSize = Integer.parseInt(pageNum);
	}

	public List<PassPortDetail> getPassPortList() {
		return passPortList;
	}

	public void setPassPortList(List<PassPortDetail> passPortList) {
		this.passPortList = passPortList;
	}

	public void setOrderServiceProxy(OrderService orderServiceProxy) {
		this.orderServiceProxy = orderServiceProxy;
	}

	public Date getVisitTime() {
		return visitTime;
	}

	public void setVisitTime(Date visitTime) {
		this.visitTime = visitTime;
	}

	public Long getMetaProductBranchId() {
		return metaProductBranchId;
	}

	public void setMetaProductBranchId(Long metaProductBranchId) {
		this.metaProductBranchId = metaProductBranchId;
	}
 

	public void setEplaceService(EPlaceService eplaceService) {
		this.eplaceService = eplaceService;
	}

	public void setPassCodeService(PassCodeService passCodeService) {
		this.passCodeService = passCodeService;
	}

	public int getPagingSize() {
		return pagingSize;
	}

	public void setPagingSize(int pagingSize) {
		this.pagingSize = pagingSize;
	}

	public boolean isOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(boolean orderStatus) {
		this.orderStatus = orderStatus;
	}

	public boolean isOperate() {
		return operate;
	}

	public void setOperate(boolean operate) {
		this.operate = operate;
	}

	public PerformDetailSortTypeEnum getSort() {
		return sort;
	}

	public void setSort(PerformDetailSortTypeEnum sort) {
		this.sort = sort;
	}

	public boolean isPassed() {
		return passed;
	}

	public void setPassed(boolean passed) {
		this.passed = passed;
	}

}
