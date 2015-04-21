package com.lvmama.back.web.abroadhotel.sale;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.zul.Tree;
import org.zkoss.zul.Treecell;
import org.zkoss.zul.Treechildren;
import org.zkoss.zul.Treecol;
import org.zkoss.zul.Treecols;
import org.zkoss.zul.Treeitem;
import org.zkoss.zul.Treerow;

import com.lvmama.back.web.BaseAction;
import com.lvmama.comm.abroad.po.AhotelOrdSaleService;
import com.lvmama.comm.abroad.po.AhotelOrdSaleServiceDeal;
import com.lvmama.comm.abroad.service.AbroadhotelOrderService;
import com.lvmama.comm.utils.DateUtil;

/**
 * 订单退款增加对像类.
 */
public class AhotelOrdSaleTreeAction extends BaseAction {
	private static final long serialVersionUID = 1L;
	/**
	 * 海外酒店订单的service.
	 */
	private AbroadhotelOrderService abroadhotelOrderService;
	/**
	 * 订单子项编号.
	 */
	private String orderId;
	/**
	 * 售后服务-服务处理内容树控件显示.
	 */
	public Tree ordSaleTree;
	/**
	 * 树控件显示.
	 */
	public Tree orderTree;

	public void initSaleTreeList(String orderId) {
		this.ordSaleTree.getChildren().clear();
		Treecols treecols = new Treecols();
		Treecol treecol1 = new Treecol();
		treecol1.setLabel("申请售后服务");
		Treecol treecol2 = new Treecol();
		treecol2.setLabel("申请内容");
		Treecol treecol3 = new Treecol();
		treecol3.setLabel("申请人");
		Treecol treecol5 = new Treecol();
		treecol5.setLabel("申请时间");
		treecols.appendChild(treecol1);
		treecols.appendChild(treecol2);
		treecols.appendChild(treecol3);
		treecols.appendChild(treecol5);
		ordSaleTree.appendChild(treecols);
		if (orderId != null) {
			Map<String,String> map=new HashMap<String,String>();
			map.put("orderId", orderId);
			List<AhotelOrdSaleService> ordSaleServiceList = abroadhotelOrderService.findFullAhotelOrdSaleServiceByParam(map);
			if (ordSaleServiceList.size()>0) {
				Treechildren treechildordOrder = new Treechildren();
				for (int i = 0; i < ordSaleServiceList.size(); i++) {
					AhotelOrdSaleService ordsaleservice = ordSaleServiceList.get(i);
					Treeitem treeOrdItem = new Treeitem();
					if (i != 0) {
						treeOrdItem.setOpen(false);
					}
					Treerow treerow = new Treerow();
					Treecell treecell1 = new Treecell();
					treecell1.setLabel(ordsaleservice.getServiceTypeName() + "");
					Treecell treecell2 = new Treecell();
					treecell2.setLabel(ordsaleservice.getApplyContent());
					Treecell treecell3 = new Treecell();
					treecell3.setLabel(ordsaleservice.getOperatorName() + "");
					Treecell treecell4 = new Treecell();
					treecell4.setLabel(DateUtil.getFormatDate(ordsaleservice.getCreateTime(),"yyyy-MM-dd HH:mm:ss"));
					treerow.appendChild(treecell1);
					treerow.appendChild(treecell2);
					treerow.appendChild(treecell3);
					treerow.appendChild(treecell4);
					treeOrdItem.appendChild(treerow);

					Treechildren treechildordItem = new Treechildren();
					Map<String,Long> saleDealMap=new HashMap<String,Long>();
					saleDealMap.put("saleServiceId", ordsaleservice.getSaleServiceId());
					List<AhotelOrdSaleServiceDeal> ordSaleServiceDealList = abroadhotelOrderService.getAhotelOrdSaleServiceAllByParam(saleDealMap);
					for (int j = 0; j < ordSaleServiceDealList.size(); j++) {
						AhotelOrdSaleServiceDeal ordsaleservicedeal = ordSaleServiceDealList.get(j);
							Treeitem treeItem = new Treeitem();
							treeItem.setOpen(false);
							Treerow treerowItem = new Treerow();
							Treecell treecellItem1 = new Treecell();
							treecellItem1.setLabel(ordsaleservicedeal.getDealContent());
							Treecell treecellItem2 = new Treecell();
							treecellItem2.setLabel(ordsaleservicedeal.getOperatorName());
							Treecell treecellItem3 = new Treecell();
							treecellItem3.setLabel(DateUtil.getFormatDate(ordsaleservicedeal.getCreateTime(),"yyyy-MM-dd HH:mm:ss"));
							treerowItem.appendChild(treecellItem1);
							treerowItem.appendChild(treecellItem2);
							treerowItem.appendChild(treecellItem3);
							treeItem.appendChild(treerowItem);
							treechildordItem.appendChild(treeItem);
					}
					treeOrdItem.appendChild(treechildordItem);
					treechildordOrder.appendChild(treeOrdItem);
				}
				ordSaleTree.appendChild(treechildordOrder);
			}
		}
		
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public Tree getOrdSaleTree() {
		return ordSaleTree;
	}

	public void setOrdSaleTree(Tree ordSaleTree) {
		this.ordSaleTree = ordSaleTree;
	}
}
