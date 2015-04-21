/**
 * 
 */
package com.lvmama.back.sweb.meta;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.comm.bee.po.meta.MetaProductTicket;
import com.lvmama.comm.vo.Constant;

/**
 * @author yangbin
 *
 */
@Results({
	@Result(name = "input", location = "/WEB-INF/pages/back/meta/add_ticket.jsp")
})
public class MetaProductTicketAction extends MetaProductEditAction<MetaProductTicket> {

	public MetaProductTicketAction() {
		super(Constant.PRODUCT_TYPE.TICKET.name());
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 327119166212421670L;

	@Override
	@Action("/meta/toAddTicket")
	public String addMetaProduct() {
		return goAfter();
	}
	@Override
	@Action("/meta/toEditTicket")
	public String toEdit() {
		// TODO Auto-generated method stub
		doBefore();
		return goAfter();
	}

	@Override
	@Action("/meta/saveTicket")
	public void save() {
		saveMetaProduct();		
	}
	@Override
	protected String[] getFieldParams() {
		return new String[]{"todayOrderAble"};
	}

	
}
