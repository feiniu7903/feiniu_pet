package com.lvmama.back.web.ord.sale;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.zkoss.zul.Tree;
import org.zkoss.zul.Treecell;
import org.zkoss.zul.Treechildren;
import org.zkoss.zul.Treecol;
import org.zkoss.zul.Treecols;
import org.zkoss.zul.Treeitem;
import org.zkoss.zul.Treerow;

import com.lvmama.back.web.BaseAction;
import com.lvmama.comm.bee.po.ord.NcComplaint;
import com.lvmama.comm.bee.po.ord.OrdSaleService;
import com.lvmama.comm.bee.po.ord.OrdSaleServiceDeal;
import com.lvmama.comm.bee.service.complaint.NcComplaintService;
import com.lvmama.comm.bee.service.ord.OrderService;
import com.lvmama.comm.pet.service.sale.OrdSaleServiceService;
import com.lvmama.comm.pet.service.sale.OrdSaleServiceServiceDeal;
import com.lvmama.comm.spring.SpringBeanProxy;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.vo.Constant;

/**
 * 订单退款增加对像类.
 * 
 * @author huangl
 */
public class OrdSaleTreeAction extends BaseAction {
	private static final long serialVersionUID = 1L;
	/**
	 * 订单子项编号.
	 */
	private String orderId;
	/**
	 * 售后服务-服务处理内容树控件显示.
	 */
	public Tree ordSaleTree;
	/**
	 * 新投诉信息
	 */
	public Tree complaintTree;
	/**
	 * 树控件显示.
	 */
	public Tree orderTree;
	
	

	public void initSaleTreeList(String orderId,String sysCode) {
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
			Map map=new HashMap();
			map.put("orderId", orderId);
			if(StringUtils.isNotEmpty(sysCode)){
				map.put("sysCode", sysCode);
			}else{
				map.put("sysCode",Constant.COMPLAINT_SYS_CODE.SUPER.getCode());
			}
			List ordSaleServiceList = this.getOrdSaleServiceService()
					.getOrdSaleServiceAllByParam(map);
			if (ordSaleServiceList.size()>0) {
				Treechildren treechildordOrder = new Treechildren();
				treechildordOrder.setClass("");
				for (int i = 0; i < ordSaleServiceList.size(); i++) {
					OrdSaleService ordsaleservice = (OrdSaleService)ordSaleServiceList.get(i);
					Treeitem treeOrdItem = new Treeitem();
					if (i != 0) {
						treeOrdItem.setOpen(false);
					}
					Treerow treerow = new Treerow();
					treerow.setStyle("border-bottom:1px;");
					
					Treecell treecell1 = new Treecell();
					treecell1.setLabel(ordsaleservice.getServiceTypeName() + "");
					treecell1.setStyle("word-break: break-all; border-bottom:1px solid #cccccc;");
					Treecell treecell2 = new Treecell();
					treecell2.setLabel(ordsaleservice.getApplyContent());
					treecell2.setZclass("word-wrap");
					treecell2.setStyle("word-break: break-all; border-bottom:1px solid #cccccc;");
					
					Treecell treecell3 = new Treecell();
					treecell3.setLabel(ordsaleservice.getOperatorName() + "");
					treecell3.setStyle("word-break: break-all; border-bottom:1px solid #cccccc;");
					
					Treecell treecell4 = new Treecell();
					treecell4.setLabel(DateUtil.getFormatDate(ordsaleservice.getCreateTime(),"yyyy-MM-dd HH:mm:ss"));
					treecell4.setStyle("word-break: break-all; border-bottom:1px solid #cccccc;");
					treerow.appendChild(treecell1);
					treerow.appendChild(treecell2);
					treerow.appendChild(treecell3);
					treerow.appendChild(treecell4);
					treeOrdItem.appendChild(treerow);

					Treechildren treechildordItem = new Treechildren();
					Map saleDealMap=new HashMap();
					saleDealMap.put("saleServiceId", ordsaleservice.getSaleServiceId());
					List ordSaleServiceDealList=this.getOrdSaleServiceServiceDeal().getOrdSaleServiceAllByParam(saleDealMap);
					for (int j = 0; j < ordSaleServiceDealList.size(); j++) {
							OrdSaleServiceDeal ordsaleservicedeal = (OrdSaleServiceDeal)ordSaleServiceDealList.get(j);
							Treeitem treeItem = new Treeitem();
							treeItem.setOpen(false);
							Treerow treerowItem = new Treerow();
							Treecell treecellItem0 = new Treecell();
							treecellItem0.setLabel("");
							Treecell treecellItem1 = new Treecell();
							treecellItem1.setLabel(ordsaleservicedeal.getDealContent());
							treecellItem1.setZclass("word-wrap");
							Treecell treecellItem2 = new Treecell();
							treecellItem2.setLabel(ordsaleservicedeal.getOperatorName());
							Treecell treecellItem3 = new Treecell();
							treecellItem3.setLabel(DateUtil.getFormatDate(ordsaleservicedeal.getCreateTime(),"yyyy-MM-dd HH:mm:ss"));
							treerowItem.appendChild(treecellItem0);
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
	public void initComplaintTreeList(String orderId,String sysCode) {
		this.complaintTree.getChildren().clear();
		Treecols treecols = new Treecols();
		Treecol treecol1 = new Treecol();
		treecol1.setLabel("申请售后服务");
		Treecol treecolServiceNum = new Treecol();
		treecolServiceNum.setLabel("投诉单号");
		Treecol treecol2 = new Treecol();
		treecol2.setLabel("申请内容");
		Treecol treecol3 = new Treecol();
		treecol3.setLabel("申请人");
		Treecol treecolStatus = new Treecol();
		treecolStatus.setLabel("投诉状态");
		Treecol treecol5 = new Treecol();
		treecol5.setLabel("申请时间");
		treecols.appendChild(treecol1);
		treecols.appendChild(treecolServiceNum);
		treecols.appendChild(treecol2);
		treecols.appendChild(treecol3);
		treecols.appendChild(treecolStatus);
		treecols.appendChild(treecol5);
		complaintTree.appendChild(treecols);
		if (orderId != null) {
			Map map=new HashMap();
			map.put("orderId", orderId);
			if(StringUtils.isNotEmpty(sysCode)){
				map.put("sysCode", sysCode);
			}else{
				map.put("sysCode",Constant.COMPLAINT_SYS_CODE.SUPER.getCode());
			}
			List<NcComplaint> complaintsList= this.getNcComplaintService().queryComplaintByParams(map);
			if (complaintsList.size()>0) {
				Treechildren treechildordOrder = new Treechildren();
				treechildordOrder.setClass("");
				for (int i = 0; i < complaintsList.size(); i++) {
					NcComplaint complaint = (NcComplaint)complaintsList.get(i);
					Treeitem treeOrdItem = new Treeitem();
					if (i != 0) {
						treeOrdItem.setOpen(false);
					}
					Treerow treerow = new Treerow();
					treerow.setStyle("border-bottom:1px;");
					Treecell treecell1 = new Treecell();
					treecell1.setLabel("新投诉");
					treecell1.setStyle("word-break: break-all; border-bottom:1px solid #cccccc;");
					
					Treecell treecellServiceNum = new Treecell();
					treecellServiceNum.setSclass("serviceNum");
					treecellServiceNum.setLabel(complaint.getComplaintId() + "");
					treecellServiceNum.setStyle("word-break: break-all; border-bottom:1px solid #cccccc;");
					
					Treecell treecell2 = new Treecell();
					treecell2.setLabel(complaint.getDetailsComplaint());
					treecell2.setZclass("word-wrap");
					treecell2.setStyle("word-break: break-all; border-bottom:1px solid #cccccc;");
					Treecell treecell3 = new Treecell();
					treecell3.setLabel(complaint.getEntryPeople());
					treecell3.setStyle("word-break: break-all; border-bottom:1px solid #cccccc;");
					
					Treecell treecellStatus = new Treecell();
					treecellStatus.setLabel(complaint.getStrProcessStatus() + "");
					treecellStatus.setStyle("word-break: break-all; border-bottom:1px solid #cccccc;");
					
					Treecell treecell4 = new Treecell();
					treecell4.setLabel(DateUtil.getFormatDate(complaint.getCreateTime(),"yyyy-MM-dd HH:mm:ss"));
					treecell4.setStyle("word-break: break-all; border-bottom:1px solid #cccccc;");
					treerow.appendChild(treecell1);
					treerow.appendChild(treecellServiceNum);
					treerow.appendChild(treecell2);
					treerow.appendChild(treecell3);
					treerow.appendChild(treecellStatus);
					treerow.appendChild(treecell4);
					treeOrdItem.appendChild(treerow);

					treechildordOrder.appendChild(treeOrdItem);
				}
				complaintTree.appendChild(treechildordOrder);
			}
		}
		
	}


	public void initTreeList(String orderId) {
//		this.orderTree.getChildren().clear();
//		Treechildren treechildordOrder = new Treechildren();
//		Treecols treecols = new Treecols();
//		Treecol treecol1 = new Treecol();
//		treecol1.setLabel("序号");
//		Treecol treecol2 = new Treecol();
//		treecol2.setLabel("产品名称");
//		Treecol treecol3 = new Treecol();
//		treecol3.setLabel("数量");
//		Treecol treecol5 = new Treecol();
//		treecol5.setLabel("选择部分退款");
//		treecols.appendChild(treecol1);
//		treecols.appendChild(treecol2);
//		treecols.appendChild(treecol3);
//		treecols.appendChild(treecol5);
//		this.orderTree.appendChild(treecols);
//
//		if (orderId != null) {
//			OrdOrder historyOrderDetail = this.getOrderServiceProxy().queryOrdOrderByOrderId(new Long(orderId));
//			if (historyOrderDetail.getOrdOrderItemProds() != null) {
//				for (int i = 0; i < historyOrderDetail.getOrdOrderItemProds()
//						.size(); i++) {
//					OrdOrderItemProd ordorderitemprod = historyOrderDetail
//							.getOrdOrderItemProds().get(i);
//					Treeitem treeOrdItem = new Treeitem();
//					if (i != 0) {
//						treeOrdItem.setOpen(false);
//					}
//					Treerow treerow = new Treerow();
//					Treecell treecell1 = new Treecell();
//					treecell1.setLabel(ordorderitemprod.getOrderId() + "");
//					Treecell treecell2 = new Treecell();
//					treecell2.setLabel(ordorderitemprod.getProductName());
//					Treecell treecell3 = new Treecell();
//					treecell3.setLabel(ordorderitemprod.getQuantity() + "");
//					Treecell treecell4 = new Treecell();
//					treecell4.setLabel("");
//					treerow.appendChild(treecell1);
//					treerow.appendChild(treecell2);
//					treerow.appendChild(treecell3);
//					treerow.appendChild(treecell4);
//					treeOrdItem.appendChild(treerow);
//
//					Treechildren treechildordItem = new Treechildren();
//					for (int j = 0; j < ordorderitemprod.getOrdOrderItemMetas()
//							.size(); j++) {
//						OrdOrderItemMeta ordorderitemmeta = ordorderitemprod
//								.getOrdOrderItemMetas().get(j);
//						if (ordorderitemmeta != null) {
//							Treeitem treeItem = new Treeitem();
//							treeItem.setOpen(false);
//							Treerow treerowItem = new Treerow();
//							Treecell treecellItem1 = new Treecell();
//							treecellItem1.setLabel(ordorderitemmeta
//									.getOrderItemMetaId()
//									+ "");
//							Treecell treecellItem2 = new Treecell();
//							treecellItem2.setLabel(ordorderitemmeta
//									.getProductName());
//							Treecell treecellItem3 = new Treecell();
//							treecellItem3.setLabel(ordorderitemmeta
//									.getQuantity()
//									+ "");
//							Treecell treecellItem4 = new Treecell();
//							Checkbox chkBoxItem = new Checkbox();
//							chkBoxItem.setChecked("true".indexOf(ordorderitemmeta.getRefund())>-1?true:false);
//							treecellItem4.appendChild(chkBoxItem);
//							treerowItem.appendChild(treecellItem1);
//							treerowItem.appendChild(treecellItem2);
//							treerowItem.appendChild(treecellItem3);
//							treerowItem.appendChild(treecellItem4);
//							treeItem.appendChild(treerowItem);
//							treechildordItem.appendChild(treeItem);
//						}
//					}
//					treeOrdItem.appendChild(treechildordItem);
//					treechildordOrder.appendChild(treeOrdItem);
//				}
//			}
//		}
//		this.orderTree.appendChild(treechildordOrder);
	}
	public OrdSaleServiceService getOrdSaleServiceService() {
		return (OrdSaleServiceService) SpringBeanProxy
				.getBean("ordSaleServiceService");
	}
	
	public NcComplaintService getNcComplaintService() {
		return (NcComplaintService) SpringBeanProxy
				.getBean("ncComplaintService");
	}
	
	public OrderService getOrderServiceProxy() {
		return (OrderService) SpringBeanProxy.getBean("orderServiceProxy");
	}

	public OrdSaleServiceServiceDeal getOrdSaleServiceServiceDeal() {
		return (OrdSaleServiceServiceDeal)SpringBeanProxy.getBean("ordSaleServiceServiceDeal");
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
	public Tree getComplaintTree() {
		return complaintTree;
	}
	public void setComplaintTree(Tree complaintTree) {
		this.complaintTree = complaintTree;
	}
	
	
}
