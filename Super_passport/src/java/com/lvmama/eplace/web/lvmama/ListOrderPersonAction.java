package com.lvmama.eplace.web.lvmama;

import java.util.ArrayList;
import java.util.List;

import com.lvmama.ZkBaseAction;
import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.po.ord.OrdPerson;
import com.lvmama.comm.bee.service.ord.OrderService;
import com.lvmama.comm.spring.SpringBeanProxy;
import com.lvmama.comm.vo.Constant;

public class ListOrderPersonAction extends ZkBaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7166741082078491805L;
	private OrderService orderServiceProxy=(OrderService)SpringBeanProxy.getBean("orderServiceProxy");
	private Long orderId;
	private List<OrdPerson> ordPersonList = new ArrayList<OrdPerson>();
	public void doBefore() throws Exception {
		OrdOrder ordOrder = orderServiceProxy.queryOrdOrderByOrderId(orderId);
		List<OrdPerson> pList = ordOrder.getPersonList();
		if (pList != null && !pList.isEmpty()) {
			for (OrdPerson person : pList) {
				if (Constant.ORD_PERSON_TYPE.TRAVELLER.name().equals(person.getPersonType())) {
					ordPersonList.add(person);
				}
			}
		}
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public List<OrdPerson> getOrdPersonList() {
		return ordPersonList;
	}

}
