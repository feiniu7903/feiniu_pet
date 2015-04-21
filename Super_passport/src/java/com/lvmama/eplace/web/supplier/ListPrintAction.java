package com.lvmama.eplace.web.supplier;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.lvmama.ZkBaseAction;
import com.lvmama.comm.bee.po.pass.PassPortDetail;
/**
 * 通关打印日通关报表
 * 
 * @author luoyinqi
 *
 */
public class ListPrintAction extends ZkBaseAction{
	private static final long serialVersionUID = -7593706374468500621L;
	private Date visitTime;
	private Long metaProductid;
	private int pagingSize;
	private List<PassPortDetail> passPortList = new ArrayList<PassPortDetail>();
	//private OrderService orderServiceProxy = (OrderService) SpringBeanProxy.getBean("orderServiceProxy");
	
	protected void doBefore() throws Exception {
		/*
		CompositeQuery compositeQuery = new CompositeQuery();
		PassPortDetailRelate passPortDetailRelate = new PassPortDetailRelate();

		if (metaProductid >0) {
			passPortDetailRelate.setMetaProductid(metaProductid);
		}
		if (visitTime != null) {
			passPortDetailRelate.setVisitTimeStart(visitTime);
			Date tmep = DateUtil.addDays(visitTime, 1);
			passPortDetailRelate.setVisitTimeEnd(tmep);
		}
		compositeQuery.setPassPortDetailRelate(passPortDetailRelate);
		compositeQuery.getPageIndex().setBeginIndex(0);
		compositeQuery.getPageIndex().setEndIndex(pagingSize);
		compositeQuery.getTypeList().add(SortTypeEnum.ORDER_ID_DESC);

		List<PassPortDetail> list = orderServiceProxy.queryPassPortDetail(compositeQuery);
		EplaceSupplier eplaceSupplier = (EplaceSupplier) super.session
				.getAttribute(com.lvmama.passport.vo.PassportConstant.SESSION_EPLACE_SUPPLIER);
		if (eplaceSupplier != null && !eplaceSupplier.isCustomerVisible()) {
			for (PassPortDetail passPortDetail : list) {
				passPortDetail.setContactMobile("---");
				this.passPortList.add(passPortDetail);
			}
		} else {
			this.passPortList = list;
		}
		*/

	}

	public Date getVisitTime() {
		return visitTime;
	}

	public void setVisitTime(Date visitTime) {
		this.visitTime = visitTime;
	}

	public List<PassPortDetail> getPassPortList() {
		return passPortList;
	}

	public void setPassPortList(List<PassPortDetail> passPortList) {
		this.passPortList = passPortList;
	}

	public Long getMetaProductid() {
		return metaProductid;
	}

	public void setMetaProductid(Long metaProductid) {
		this.metaProductid = metaProductid;
	}

	public int getPagingSize() {
		return pagingSize;
	}

	public void setPagingSize(int pagingSize) {
		this.pagingSize = pagingSize;
	}
}
