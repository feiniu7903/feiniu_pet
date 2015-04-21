package com.lvmama.bee.web.eplace;


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
import com.lvmama.comm.bee.service.ebooking.EbkAnnouncementService;
import com.lvmama.comm.bee.service.ord.OrderService;
import com.lvmama.comm.bee.vo.ord.CompositeQuery;
import com.lvmama.comm.bee.vo.ord.CompositeQuery.PerformDetailRelate;
import com.lvmama.comm.bee.vo.ord.PerformDetail;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.utils.ServletUtil;
import com.lvmama.comm.vo.Constant;

@Results( {
	@Result(name = "success", location = "/WEB-INF/pages/eplace/index.jsp")
	}
)
public class HomeAction extends EbkBaseAction {
	private static final long serialVersionUID = 1L;
	private List<PerformDetail> fulfillList;
	//预订票数，已取票数，待取票数，预计人数，已玩人数，未玩人数
	private Long[] tongJI={0l,0l,0l,0l,0l,0l};
	private OrderService orderServiceProxy;
	private CompositeQuery compositeQuery;
	private PerformDetailRelate performDetailRelate;
	private EbkAnnouncementService ebkAnnouncementService;
	
	@Action("/eplace/homepage")
	public String home(){
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
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("beginDate", new Date());
		params.put("orderByBeginDateDesc", "true");
		params.put("start", 1);
		params.put("end", 5);
		setRequestAttribute("announceList", ebkAnnouncementService.findEbkAnnouncementListByMap(params));
		return SUCCESS;
	}
	@Action("/eplace/findPerformOrderCount")
	public void findPerformOrderCount() {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("supplierId", this.getCurrentSupplierId());
		// 查询游玩数量
		this.sendAjaxResultByJson("");
	}
	public Long[] getTongJI() {
		return tongJI;
	}
	public void setEbkAnnouncementService(
			EbkAnnouncementService ebkAnnouncementService) {
		this.ebkAnnouncementService = ebkAnnouncementService;
	}
	public void setOrderServiceProxy(OrderService orderServiceProxy) {
		this.orderServiceProxy = orderServiceProxy;
	}
}
