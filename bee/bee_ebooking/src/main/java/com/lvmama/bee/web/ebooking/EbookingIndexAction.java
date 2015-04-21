package com.lvmama.bee.web.ebooking;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.bee.web.EbkBaseAction;
import com.lvmama.comm.bee.po.ebooking.EbkAnnouncement;
import com.lvmama.comm.bee.service.ebooking.EbkAnnouncementService;
import com.lvmama.comm.bee.service.ebooking.EbkTaskService;
import com.lvmama.comm.bee.service.ord.OrderService;
import com.lvmama.comm.bee.vo.ord.CompositeQuery;
import com.lvmama.comm.bee.vo.ord.CompositeQuery.PerformDetailRelate;
import com.lvmama.comm.bee.vo.ord.PerformDetail;
import com.lvmama.comm.pet.vo.Page;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.utils.ServletUtil;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.vo.Constant;
/**
 * 进入首页
 * @author ranlongfei 2012-12-6
 * @version
 */
@Results(value={
		@Result(name="index",location="/WEB-INF/pages/ebooking/index.jsp")
	})
public class EbookingIndexAction extends EbkBaseAction {


	private EbkAnnouncementService ebkAnnouncementService;
	
	private List<EbkAnnouncement> ebkAnnouncementList;
	
	private Integer confirmHotelOrderCount = 0;
	
	private Integer cancelHotelOrderCount = 0;
	
	//线路待确认订单数量
	private Integer confirmRouteOrderCount=0;
	
	//待发出团通知
	private Integer confirmGroupCount=0;
	
	//产品类别
	private String productType;
	
	private EbkTaskService ebkTaskService;
	private List<PerformDetail> fulfillList;
	//预订票数，已取票数，待取票数，预计人数，已玩人数，未玩人数
	private Long[] tongJI={0l,0l,0l,0l,0l,0l};
	private OrderService orderServiceProxy;
	private CompositeQuery compositeQuery;
	private PerformDetailRelate performDetailRelate;
	/**
	 * 
	 */
	private static final long serialVersionUID = -9079411726549797647L;

	@Action("/ebookingindex")
	public String ebookingIndex() {
		// 公告
		findAnnouncementList();
		// 订单数
		findOrderCount();
		findEplaceTicketCount();
		return "index";
	}
	private void findEplaceTicketCount(){
		if(this.getCurrentSupplierId() == null) {
			return;
		}
		Date today=new Date();
		Date timeStart=DateUtil.getDayStart(today);
		Date timeEnd=DateUtil.getDayEnd(today);
		compositeQuery = new CompositeQuery();
		performDetailRelate = new PerformDetailRelate();
		/* 根据时间查询 */
		performDetailRelate.setVisitTimeStart(timeStart);
		performDetailRelate.setVisitTimeEnd(timeEnd);
		//用户产品权限限制
		List<Long> permProductList = (List<Long>)ServletUtil.getSession(this.getRequest(), this.getResponse(), Constant.Session_EBOOKING_USER_META_BRANCH_LIST);
		performDetailRelate.setBranchIds(permProductList);
		compositeQuery.setPerformDetailRelate(performDetailRelate);
		if(CollectionUtils.isNotEmpty(permProductList))
		{
			fulfillList = orderServiceProxy.queryPerformDetailForEplaceList(compositeQuery);
		}else{
			fulfillList=new ArrayList<PerformDetail>();
		}
		
		for(PerformDetail item:fulfillList){
			tongJI[0]=tongJI[0]+item.getQuantity();
			tongJI[3]=tongJI[3]+item.getAdultQuantity()+item.getChildQuantity();
			if(item.isNotPass()){
				tongJI[2]=tongJI[2]+item.getQuantity();
				tongJI[5]=tongJI[5]+item.getAdultQuantity()+item.getChildQuantity();
			}else{
				if(Constant.PAYMENT_TARGET.TOLVMAMA.name().equalsIgnoreCase(item.getPaymentTarget())){
					tongJI[1]=tongJI[1]+item.getQuantity();
				}else{
					//景区支付
					tongJI[1]=tongJI[1]+item.getRealQuantity();
				}
				tongJI[4]=tongJI[4]+item.getRealAdultQuantity()+item.getRealChildQuantity();
			}
		}
	}
	@Action("/findCreateTaskCount")
	public void findCreateTaskCount() {
		if(this.getCurrentSupplierId() == null) {
			this.sendAjaxResultByJson("0");
			return;
		}
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userMemoStatus", "true");
		params.put("supplierId", this.getCurrentSupplierId());
		params.put("certificateStatus", Constant.EBK_TASK_STATUS.CREATE);
		//不定期订单不需要进入待处理统计
		params.put("isAperiodic", Constant.TRUE_FALSE.FALSE.getAttr1());
		Integer confirmOrderCount=0;
		if(Constant.PRODUCT_TYPE.HOTEL.name().equals(productType)){
			params.put("productType", productType);
		}
		if(Constant.PRODUCT_TYPE.ROUTE.name().equals(productType)){
			params.put("productType", productType);
		}
		confirmOrderCount = ebkTaskService.findEbkTaskCountByExample(params);
		this.sendAjaxResultByJson(confirmOrderCount+"");
	}
	
	/**
	 * 查询订单数：待确认
	 * 
	 * @author: ranlongfei 2012-12-6 下午6:32:35
	 */
	private void findOrderCount() {
		if(this.getCurrentSupplierId() == null) {
			return;
		}
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userMemoStatus", "true");
		params.put("supplierId", this.getCurrentSupplierId());
		params.put("certificateStatus", Constant.EBK_TASK_STATUS.CREATE);
		//不定期订单不需要进入待处理统计
		params.put("isAperiodic", Constant.TRUE_FALSE.FALSE.getAttr1());
		
		//酒店 待确认
		params.put("productType", Constant.PRODUCT_TYPE.HOTEL);
		confirmHotelOrderCount = ebkTaskService.findEbkTaskCountByExample(params);
		//线路 待确认
		params.put("productType", Constant.PRODUCT_TYPE.ROUTE);
		confirmRouteOrderCount = ebkTaskService.findEbkTaskCountByExample(params);
		
		//待发出团通知
		params.put("groupNotice", "groupNotice");
		Date date = DateUtil.dsDay_Date(new Date(), 3);
		params.put("visitTimeEnd", DateUtil.formatDate(date, "yyyy-MM-dd") + " 23:59:59");
		params.put("groupWordStatus", "0");
		confirmGroupCount = ebkTaskService.findEbkTaskCountByExample(params);
	}
	
	/**
	 * 查询公告列表
	 * 
	 * @author: ranlongfei 2012-12-6 下午6:30:53
	 */
	private void findAnnouncementList() {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("beginDate", new Date());
		params.put("orderByBeginDateDesc", "true");
		Page<EbkAnnouncement> ebkAnnouncementPage = new Page<EbkAnnouncement>();
		ebkAnnouncementPage.setCurrentPage(1L);
		params.put("start", 1);
		params.put("end", 12);
		ebkAnnouncementList = ebkAnnouncementService.findEbkAnnouncementListByMap(params);
	}
	@Action("/findEbookingMessage")
	public void findEbookingMessage(){
		if(super.isLogined()) {
			try {
				String msg = findEbkTaskMsg();
				if(!StringUtil.isEmptyString(msg)) {
					this.sendAjaxMsg(msg);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	private String findEbkTaskMsg(){
		StringBuilder sysMessage = new StringBuilder(100);
		findOrderCount();
		if (confirmHotelOrderCount != null && confirmHotelOrderCount > 0) {	
			sysMessage.append("<div style='display:block;'>");
			sysMessage.append("<a href='");
			sysMessage.append(this.getBasePath());
			sysMessage.append("/ebooking/task/confirmTaskList.do");
			sysMessage.append("'>酒店待处理订单 ");
			sysMessage.append(confirmHotelOrderCount);
			sysMessage.append(" 笔</a>");
			sysMessage.append("</br>");
		}
		if (confirmRouteOrderCount != null && confirmRouteOrderCount > 0) {
			if (sysMessage.length() == 0) {
				sysMessage.append("<div style='display:block;'>");
			}
			sysMessage.append("<a href='");
			sysMessage.append(this.getBasePath());
			sysMessage.append("/ebooking/task/confirmRouteTaskList.do");
			sysMessage.append("'>线路待处理订单 ");
			sysMessage.append(confirmRouteOrderCount);
			sysMessage.append(" 笔</a>");
			sysMessage.append("</br>");
		}
		if (confirmGroupCount != null && confirmGroupCount > 0) {	
			if (sysMessage.length() == 0) {
				sysMessage.append("<div style='display:block;'>");
			}
			sysMessage.append("<a href='");
			sysMessage.append(this.getBasePath());
			sysMessage.append("/ebooking/task/groupAdviceNoteList.do");
			sysMessage.append("'>待发出团通知 ");
			sysMessage.append(confirmGroupCount);
			sysMessage.append(" 笔</a>");
			sysMessage.append("</br>");
		}
		if (sysMessage.length() > 0) {
			sysMessage.append("</div>");
		}
		return sysMessage.toString();
	}
	public EbkAnnouncementService getEbkAnnouncementService() {
		return ebkAnnouncementService;
	}
	public void setEbkAnnouncementService(EbkAnnouncementService ebkAnnouncementService) {
		this.ebkAnnouncementService = ebkAnnouncementService;
	}
	public List<EbkAnnouncement> getEbkAnnouncementList() {
		return ebkAnnouncementList;
	}
	public void setEbkAnnouncementList(List<EbkAnnouncement> ebkAnnouncementList) {
		this.ebkAnnouncementList = ebkAnnouncementList;
	}
	public EbkTaskService getEbkTaskService() {
		return ebkTaskService;
	}
	public void setEbkTaskService(EbkTaskService ebkTaskService) {
		this.ebkTaskService = ebkTaskService;
	}

	public Integer getConfirmHotelOrderCount() {
		return confirmHotelOrderCount;
	}

	public void setConfirmHotelOrderCount(Integer confirmHotelOrderCount) {
		this.confirmHotelOrderCount = confirmHotelOrderCount;
	}

	public Integer getCancelHotelOrderCount() {
		return cancelHotelOrderCount;
	}

	public void setCancelHotelOrderCount(Integer cancelHotelOrderCount) {
		this.cancelHotelOrderCount = cancelHotelOrderCount;
	}

	public Integer getConfirmRouteOrderCount() {
		return confirmRouteOrderCount;
	}

	public void setConfirmRouteOrderCount(Integer confirmRouteOrderCount) {
		this.confirmRouteOrderCount = confirmRouteOrderCount;
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}
	public List<PerformDetail> getFulfillList() {
		return fulfillList;
	}
	public void setFulfillList(List<PerformDetail> fulfillList) {
		this.fulfillList = fulfillList;
	}
	public Long[] getTongJI() {
		return tongJI;
	}
	public void setTongJI(Long[] tongJI) {
		this.tongJI = tongJI;
	}
	public OrderService getOrderServiceProxy() {
		return orderServiceProxy;
	}
	public void setOrderServiceProxy(OrderService orderServiceProxy) {
		this.orderServiceProxy = orderServiceProxy;
	}
	public CompositeQuery getCompositeQuery() {
		return compositeQuery;
	}
	public void setCompositeQuery(CompositeQuery compositeQuery) {
		this.compositeQuery = compositeQuery;
	}
	public PerformDetailRelate getPerformDetailRelate() {
		return performDetailRelate;
	}
	public void setPerformDetailRelate(PerformDetailRelate performDetailRelate) {
		this.performDetailRelate = performDetailRelate;
	}
}
