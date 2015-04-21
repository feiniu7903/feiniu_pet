package com.lvmama.comm.bee.po.prod;

import java.io.Serializable;

import com.lvmama.comm.vo.Constant;

public class ProdTicket extends ProdProduct implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = -8773072486704517143L;
	private Long ticketId;

    public Long getTicketId() {
        return ticketId;
    }

    public void setTicketId(Long ticketId) {
        this.ticketId = ticketId;
    }

    public String getProductType() {
		return Constant.PRODUCT_TYPE.TICKET.name();
	}
}