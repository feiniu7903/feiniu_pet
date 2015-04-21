/**
 * 
 */
package com.lvmama.back.remote;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import com.lvmama.comm.bee.po.ord.OrdInvoice;
import com.lvmama.comm.bee.service.ord.OrderService;
import com.lvmama.comm.bee.vo.ord.CompositeQuery;
import com.lvmama.comm.bee.vo.ord.CompositeQuery.PageIndex;
import com.lvmama.comm.bee.vo.ord.CompositeQuery.SortTypeEnum;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.vo.Constant;

/**
 * @author yangbin
 *
 */
public class InvoiceRemoteServiceImpl implements InvoiceRemoteService,Serializable {

	private OrderService orderServiceProxy;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4913620637834236480L;

	/* (non-Javadoc)
	 * @see com.lvmama.back.remote.InvoiceRemoteService#changeStatus(java.lang.Long, java.lang.String)
	 */
	@Override
	public boolean changeStatus(Long invoiceId, String invoiceNo) {
		return orderServiceProxy.updateInvoiceNo(invoiceId, invoiceNo, "INVOICE-Client");
	}

	/* (non-Javadoc)
	 * @see com.lvmama.back.remote.InvoiceRemoteService#queryInvoiceDetail(java.lang.Long)
	 */
	@Override
	public InvoiceInfo queryInvoiceDetail(Long invoiceId) {
		return conver(orderServiceProxy.selectOrdInvoiceByPrimaryKey(invoiceId));
	}

	/* (non-Javadoc)
	 * @see com.lvmama.back.remote.InvoiceRemoteService#queryInvoiceList()
	 */
	@Override
	public List<InvoiceInfo> queryInvoiceList() {
		CompositeQuery compositeQuery=new CompositeQuery();
		compositeQuery.getInvoiceRelate().setStatus(Constant.INVOICE_STATUS.APPROVE.name());
		PageIndex index=new PageIndex();
		index.setBeginIndex(0);
		index.setEndIndex(10);
		compositeQuery.setPageIndex(index);
		return converList(orderServiceProxy.queryOrdInvoice(compositeQuery));
	}

	/* (non-Javadoc)
	 * @see com.lvmama.back.remote.InvoiceRemoteService#queryInvoiceList(java.util.Date, java.util.Date)
	 */
	@Override
	public List<InvoiceInfo> queryInvoiceListByReq(Date start, Date end,String companyId) {
		CompositeQuery compositeQuery=new CompositeQuery();
		compositeQuery.getOrderTimeRange().setCreateInvoiceStart(DateUtil.getDayStart(start));
		compositeQuery.getOrderTimeRange().setCreateInvoiceEnd(DateUtil.getDayEnd(end));
		compositeQuery.getInvoiceRelate().setStatus(Constant.INVOICE_STATUS.APPROVE.name());
		compositeQuery.getStatus().setPaymentStatus(Constant.PAYMENT_STATUS.PAYED.name());
		compositeQuery.getExcludedContent().setOrderStatus(Constant.ORDER_STATUS.CANCEL.name());
		PageIndex index=new PageIndex();
		index.setBeginIndex(0);
		index.setEndIndex(10);
		compositeQuery.setPageIndex(index);
		compositeQuery.getTypeList().add(SortTypeEnum.INVOICE_ID_ASC);
		return converList(orderServiceProxy.queryOrdInvoice(compositeQuery));
	}
	
	private List<InvoiceInfo> converList(List<OrdInvoice> list){
		List<InvoiceInfo> infos=new ArrayList<InvoiceInfo>();
		if(CollectionUtils.isNotEmpty(list)){
			for(OrdInvoice info:list){
				infos.add(conver(info));
			}
		}
		
		return infos;
	}
	
	private InvoiceInfo conver(OrdInvoice invoice){
		InvoiceInfo info=new InvoiceInfo();
		info.setAmount(invoice.getAmountYuan());
		info.setTitle(invoice.getTitle());
		info.setDetail(invoice.getZhDetail());
		info.setInvoiceId(invoice.getInvoiceId());
		info.setMemo(invoice.getMemo());
		return info;
	}

	/**
	 * @param orderServiceProxy the orderServiceProxy to set
	 */
	public void setOrderServiceProxy(OrderService orderServiceProxy) {
		this.orderServiceProxy = orderServiceProxy;
	}

	
}
