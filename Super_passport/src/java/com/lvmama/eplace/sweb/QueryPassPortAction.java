package com.lvmama.eplace.sweb;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.BackBaseAction;
import com.lvmama.comm.bee.po.pass.UserRelateSupplierProduct;
import com.lvmama.comm.bee.service.eplace.EPlaceService;
import com.lvmama.comm.bee.service.ord.OrderService;
import com.lvmama.comm.bee.vo.ord.CompositeQuery;
import com.lvmama.comm.bee.vo.ord.CompositeQuery.PageIndex;
import com.lvmama.comm.bee.vo.ord.CompositeQuery.PerformDetailRelate;
import com.lvmama.comm.bee.vo.ord.CompositeQuery.PerformDetailSortTypeEnum;
import com.lvmama.comm.bee.vo.ord.PerformDetail;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.vo.Constant;
@Results( {
		@Result(name = "query", location = "/WEB-INF/epalce/queryPassPort.jsp")
		}
)
/**
 * 查询通关类(jsp版).
 * 
 * @author huangl
 */
public class QueryPassPortAction extends BackBaseAction {
	private static final long serialVersionUID = 1L;
	private EPlaceService eplaceService;
	private OrderService orderServiceProxy ;
	private PageIndex pageIndex;
	private CompositeQuery compositeQuery;
	private PerformDetailRelate performDetailRelate = new PerformDetailRelate();
	private Map<String, Object> queryOption = new HashMap<String, Object>();
	private List<PerformDetail> fulfillList = new ArrayList<PerformDetail>();
	private PerformDetailSortTypeEnum sort= PerformDetailSortTypeEnum.ORDER_ID_DESC;
	/**
	 * 合并记录数
	 */
	private Integer uniteCount = 0;
	private boolean operate=true;
	/**
	 * 跳转显示查询页面.
	 * @return
	 */
	@Action(value="/port/jumpQueryPort",interceptorRefs=@InterceptorRef("authority"))
	public String jumpQueryPort(){
		return "query";
	}
	/**
	 * 综合查询， 通过手机号，订单号，通关码等查询订单列表.
	 */
	@Action(value="/port/queryPassPort",interceptorRefs=@InterceptorRef("authority"))
	public String queryPassPort() {
		this.getRequest().setAttribute("todayDate", DateUtil.getFormatDate(new Date(), "yyyy-MM-dd"));
		String port_mobile = this.getRequest().getParameter("port_mobile");
		String port_orderId = this.getRequest().getParameter("port_orderId");
		String port_userName = this.getRequest().getParameter("port_userName");
		String port_passPort = this.getRequest().getParameter("port_passPort");
		if (checkData(port_mobile, port_orderId, port_userName, port_passPort)) {
			long total = eplaceService.getSupplierUserTargetIdTotal(this.getOperatorLongId());
			if (total > 1) {
				this.operate = false;
			}
			compositeQuery = new CompositeQuery();
			performDetailRelate = new PerformDetailRelate();

			Long userId = this.getOperatorLongId();
			performDetailRelate.setPassPortUserId(userId);

			String pageStr = "";
			if (StringUtils.isEmpty(port_mobile)&&StringUtils.isEmpty(port_orderId)&&StringUtils.isEmpty(port_userName)&&StringUtils.isEmpty(port_passPort)){
				return "query";
			}	
			/* 根据供手机号码查询 */
			if (StringUtils.isNotEmpty(port_mobile)) {
				performDetailRelate.setContactMobile(port_mobile.trim());
				this.getRequest().setAttribute("port_mobile", port_mobile.trim());
				pageStr += "&port_mobile=" + port_mobile.trim();
			}
			/* 根据订单编号查询 */
			if (StringUtils.isNotEmpty(port_orderId)) {
				performDetailRelate.setOrderId(Long.valueOf(port_orderId.trim()));
				this.getRequest().setAttribute("port_orderId", port_orderId.trim());
				pageStr += "&port_orderId=" + port_orderId.trim();
			}

			/* 根据供姓名查询 */
			if (StringUtils.isNotEmpty(port_userName)) {
				performDetailRelate.setContactName(port_userName.trim());
				this.getRequest().setAttribute("port_userName", port_userName.trim());
				pageStr += "&port_userName=" + port_userName.trim();
			}
			/* 根据通关码查询 */
			if (StringUtils.isNotEmpty(port_passPort)) {
				performDetailRelate.setCode(port_passPort.trim());
				this.getRequest().setAttribute("port_passPort", port_passPort.trim());
				pageStr += "&port_passPort=" + port_passPort.trim();
			}
			// performDetailRelate.setVisitTimeStart(DateUtils.addDays(new
			// Date(), -1));
			// performDetailRelate.setVisitTimeEnd(new Date());
			performDetailRelate.setPassPort(false);
			UserRelateSupplierProduct userRelateSupplierProduct = eplaceService.getSupplierUserForTargetId(userId);
			if (userRelateSupplierProduct != null && Constant.CCERT_TYPE.DIMENSION.name().equals(userRelateSupplierProduct.getSupPerformTarget().getCertificateType())) {
				performDetailRelate.setPassPort(true);
			}
			performDetailRelate.setPersonType(Constant.ORD_PERSON_TYPE.CONTACT.name());
			compositeQuery.setPerformDetailRelate(performDetailRelate);
			compositeQuery.getPerformTypeList().add(sort);

			PageIndex page = new PageIndex();
			Long totalRecords = orderServiceProxy.queryPerformDetailCount(compositeQuery);
			pagination = initPage();
			pagination.setTotalResultSize(totalRecords);
			page.setBeginIndex(((Long)pagination.getStartRows()).intValue());
			page.setEndIndex(((Long)pagination.getEndRows()).intValue());
			compositeQuery.setPageIndex(page);

			List<PerformDetail> list = orderServiceProxy.queryPerformDetail(compositeQuery);
			Map<Long, PerformDetail> orderMap = this.getUnitePerformDetail(list);
			uniteCount = orderMap.size();
//			pagination.setMsg("当前页合并（" + uniteCount + "）条");
			for (PerformDetail performDetail : list) {
				PerformDetail temp = orderMap.get(performDetail.getOrderId());
				if (temp != null) {
					if (!fulfillList.contains(temp)) {
						this.fulfillList.add(temp);
					}
				} else {
					this.fulfillList.add(performDetail);
				}
			}

			pagination.setUrl("port/queryPassPort.do?" + pageStr + "&page=");
		}
		return "query";
	}
	/**
	 * 如果条件数据都为空，返回fasle
	 * @return
	 */
	private boolean checkData(String port_mobile,String port_orderId,String port_userName,String port_passPort){
		boolean flag = true;
		if((port_mobile == null ||"".equals(port_mobile)) && (port_orderId==null || "".equals("port_orderId")) && (port_userName==null || "".equals("port_userName")) &&(port_passPort==null || "".equals("port_passPort"))){
			flag = false;
		}
		return flag;
	}
	/**
	 * 合并订单子指项
	 * @param list
	 * @return
	 */
	private Map<Long,PerformDetail> getUnitePerformDetail( List<PerformDetail> list){
		Map<Long,PerformDetail>orderMap=new HashMap<Long,PerformDetail>();
		Map<Long,List<Integer>>orderIndex=new HashMap<Long,List<Integer>>();
		for (int i=0; i<list.size() ; i++) {
			  Long orderId=list.get(i).getOrderId();
			  int counter=0;
			  List<Integer> indexList=new ArrayList<Integer>();
			  Object obj=orderIndex.get(orderId);
			  if(obj==null){
				 for(int j=0; j<list.size() ; j++){
					 PerformDetail temp=list.get(j);
					 if(temp.getOrderId().equals(orderId)){
						 counter=counter+1;
						 if(counter>1){
							 indexList.add(j);
						 }
					 }
				 }
				 if(counter>1){
					 indexList.add(i); 
					 orderIndex.put(orderId, indexList);
				 }
			  }
		}
		
		Iterator<Long> it=orderIndex.keySet().iterator();
		 while(it.hasNext()){
			 Long orderId=it.next();
			 List<Integer> indexList=orderIndex.get(orderId);
			 PerformDetail performDetail=list.get(indexList.get(0));
			for (int i=1;i<indexList.size();i++) {
				 PerformDetail temp= list.get(indexList.get(i));
				 String prodName=performDetail.getMetaProductName();
				 Long childQuantity= performDetail.getChildQuantity()+temp.getChildQuantity();
				 Long adultQuantity= performDetail.getAdultQuantity()+temp.getAdultQuantity();
				 prodName=prodName+"/"+temp.getMetaProductName();
				 performDetail.setMetaProductName(prodName);
				 performDetail.setAdultQuantity(adultQuantity);
				 performDetail.setChildQuantity(childQuantity);
				 orderMap.put(orderId, performDetail);
			 }
		 }
		 return orderMap;
	}
	
	public String initPageStr(){
		StringBuffer strBuf=new StringBuffer();
		return strBuf.toString();
	}
	public OrderService getOrderServiceProxy() {
		return orderServiceProxy;
	}

	public void setOrderServiceProxy(OrderService orderServiceProxy) {
		this.orderServiceProxy = orderServiceProxy;
	}

	public PageIndex getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(PageIndex pageIndex) {
		this.pageIndex = pageIndex;
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

	public Map<String, Object> getQueryOption() {
		return queryOption;
	}

	public void setQueryOption(Map<String, Object> queryOption) {
		this.queryOption = queryOption;
	}

	public List<PerformDetail> getFulfillList() {
		return fulfillList;
	}

	public void setFulfillList(List<PerformDetail> fulfillList) {
		this.fulfillList = fulfillList;
	}

	public PerformDetailSortTypeEnum getSort() {
		return sort;
	}

	public void setSort(PerformDetailSortTypeEnum sort) {
		this.sort = sort;
	}
	public Integer getUniteCount() {
		return uniteCount;
	}
	public void setUniteCount(Integer uniteCount) {
		this.uniteCount = uniteCount;
	}
	public boolean isOperate() {
		return operate;
	}
	public void setOperate(boolean operate) {
		this.operate = operate;
	}
	public void setEplaceService(EPlaceService eplaceService) {
		this.eplaceService = eplaceService;
	}
 
}
