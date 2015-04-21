package com.lvmama.back.web.ord.refundMent;

import java.util.ArrayList;
import java.util.List;

import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Tree;
import org.zkoss.zul.Treecell;
import org.zkoss.zul.Treechildren;
import org.zkoss.zul.Treecol;
import org.zkoss.zul.Treecols;
import org.zkoss.zul.Treeitem;
import org.zkoss.zul.Treerow;

import com.lvmama.back.web.BaseAction;
import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.po.ord.OrdOrderItemMeta;
import com.lvmama.comm.bee.po.ord.OrdOrderItemProd;
import com.lvmama.comm.bee.service.ord.OrderService;
import com.lvmama.comm.pet.service.sale.OrdSaleServiceService;
import com.lvmama.comm.spring.SpringBeanProxy;

/**
 * 订单退款增加对像类.
 * 
 * @author huangl
 */
public class ordTreeAddAction extends BaseAction {
	private static final long serialVersionUID = 1L;
	/**
	 * 订单子项编号.
	 */
	private String orderId;
	/**
	 * 复选框选中值.
	 */
	public List checkItemId = new ArrayList();
	/**
	 * 树控件显示.
	 */
	public Tree ordTree;

	public void initTreeList(String orderId) {
		this.ordTree.getChildren().clear();
		Treechildren treechildordOrder = new Treechildren();
		Treecols treecols = new Treecols();
		Treecol treecol1 = new Treecol();
		treecol1.setLabel("序号");
		Treecol treecol2 = new Treecol();
		treecol2.setLabel("产品名称");
		Treecol treecol3 = new Treecol();
		treecol3.setLabel("数量");
		Treecol treecol5 = new Treecol();
		treecol5.setLabel("选择部分退款");
		treecols.appendChild(treecol1);
		treecols.appendChild(treecol2);
		treecols.appendChild(treecol3);
		treecols.appendChild(treecol5);
		ordTree.appendChild(treecols);

		if (orderId != null) {
			OrdOrder historyOrderDetail = this.getOrderServiceProxy().queryOrdOrderByOrderId(new Long(orderId));
			if (historyOrderDetail.getOrdOrderItemProds() != null) {
				for (int i = 0; i < historyOrderDetail.getOrdOrderItemProds()
						.size(); i++) {
					OrdOrderItemProd ordorderitemprod = historyOrderDetail
							.getOrdOrderItemProds().get(i);
					Treeitem treeOrdItem = new Treeitem();
					if (i != 0) {
						treeOrdItem.setOpen(false);
					}
					Treerow treerow = new Treerow();
					Treecell treecell1 = new Treecell();
					treecell1.setLabel(ordorderitemprod.getOrderId() + "");
					Treecell treecell2 = new Treecell();
					treecell2.setLabel(ordorderitemprod.getProductName());
					Treecell treecell3 = new Treecell();
					treecell3.setLabel(ordorderitemprod.getQuantity() + "");
					Treecell treecell4 = new Treecell();
					treecell4.setLabel("");
					treerow.appendChild(treecell1);
					treerow.appendChild(treecell2);
					treerow.appendChild(treecell3);
					treerow.appendChild(treecell4);
					treeOrdItem.appendChild(treerow);

					Treechildren treechildordItem = new Treechildren();
					for (int j = 0; j < ordorderitemprod.getOrdOrderItemMetas()
							.size(); j++) {
						OrdOrderItemMeta ordorderitemmeta = ordorderitemprod
								.getOrdOrderItemMetas().get(j);
						if (ordorderitemmeta != null) {
							Treeitem treeItem = new Treeitem();
							treeItem.setOpen(false);
							Treerow treerowItem = new Treerow();
							Treecell treecellItem1 = new Treecell();
							treecellItem1.setLabel(ordorderitemmeta
									.getOrderItemMetaId()
									+ "");
							Treecell treecellItem2 = new Treecell();
							treecellItem2.setLabel(ordorderitemmeta
									.getProductName());
							Treecell treecellItem3 = new Treecell();
							treecellItem3.setLabel(ordorderitemmeta
									.getQuantity()
									+ "");
							Treecell treecellItem4 = new Treecell();
							Checkbox chkBoxItem = new Checkbox();
							chkBoxItem.setChecked(true);
							chkBoxItem.setAttribute("ordItemId",ordorderitemmeta.getOrderItemMetaId());
							addChkClick(chkBoxItem);
							chkBoxItem.setValue(ordorderitemmeta.getCheckItem());
							boolean isRefund=this.getOrdSaleServiceService().findOrderItemIsRefund(ordorderitemmeta.getOrderItemMetaId());
//							chkBoxItem.setDisabled(isRefund);
							if(!isRefund){
								checkItemId.add(ordorderitemmeta.getOrderItemMetaId());
							}else{
								chkBoxItem.setLabel("已申请退款");
								checkItemId.add(ordorderitemmeta.getOrderItemMetaId());
							}
							treecellItem4.appendChild(chkBoxItem);
							treerowItem.appendChild(treecellItem1);
							treerowItem.appendChild(treecellItem2);
							treerowItem.appendChild(treecellItem3);
							treerowItem.appendChild(treecellItem4);
							treeItem.appendChild(treerowItem);
							treechildordItem.appendChild(treeItem);
						}
					}
					treeOrdItem.appendChild(treechildordItem);
					treechildordOrder.appendChild(treeOrdItem);
				}
			}
		}
		ordTree.appendChild(treechildordOrder);
	}

	public void addChkClick(Checkbox chkBoxItem) {
		chkBoxItem.addEventListener("onClick", new EventListener() {// 添加一级菜单的事件监听
					public void onEvent(Event event) throws Exception {
						Checkbox chkBoxItem = (Checkbox) event.getTarget();
						if (chkBoxItem.isChecked()==false) {
							Long ordItemId=(Long)chkBoxItem.getAttribute("ordItemId");;
							checkItemId.remove(ordItemId);
						}else{
							checkItemId.add(chkBoxItem.getAttribute("ordItemId"));
						}
					}
				});
	}
	public OrderService getOrderServiceProxy() {
		return (OrderService) SpringBeanProxy.getBean("orderServiceProxy");
	}
	public OrdSaleServiceService getOrdSaleServiceService() {
		return (OrdSaleServiceService) SpringBeanProxy.getBean("ordSaleServiceService");
	}
	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public List getCheckItemId() {
		return checkItemId;
	}

	public void setCheckItemId(List checkItemId) {
		this.checkItemId = checkItemId;
	}

	public Tree getOrdTree() {
		return ordTree;
	}

	public void setOrdTree(Tree ordTree) {
		this.ordTree = ordTree;
	}
}
