package com.lvmama.comm.work.builder;

import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.po.ord.OrdOrderItemProd;
import com.lvmama.comm.bee.po.prod.ProdProduct;
import com.lvmama.comm.pet.po.perm.PermUser;
import com.lvmama.comm.pet.vo.WorkOrderCreateParam;
import com.lvmama.comm.vo.Constant;

public abstract class AbstractWorkOrderBuilder {

	/**
	 * 特殊要求审核工单构造
	 * @param order
	 * @param user
	 * @param prodProduct
	 * @param managerPermUserName
	 * @param taskContent
	 */
	public static WorkOrderCreateParam initFaxWorkBuilder(OrdOrder order,PermUser user,ProdProduct prodProduct,String managerPermUserName,String taskContent){
		WorkOrderCreateParam param = new WorkOrderCreateParam(); 
//		OrdOrderItemProd ordOrderItemProd=order.getMainProduct();
		/*if(Constant.PRODUCT_TYPE.ROUTE.getCode().equals(ordOrderItemProd.getProductType()) 
				&& (Constant.SUB_PRODUCT_TYPE.FREENESS_FOREIGN.getCode().equals(ordOrderItemProd.getSubProductType()) 
				|| Constant.SUB_PRODUCT_TYPE.GROUP_FOREIGN.getCode().equals(ordOrderItemProd.getSubProductType())
				|| Constant.SUB_PRODUCT_TYPE.FREENESS_LONG.getCode().equals(ordOrderItemProd.getSubProductType())
				|| Constant.SUB_PRODUCT_TYPE.GROUP_LONG.getCode().equals(ordOrderItemProd.getSubProductType()))){
			param.setWorkOrderTypeCode(Constant.WORK_ORDER_TYPE_AND_SENDGROUP.YHTSYQSH.getWorkOrderTypeCode());//工单类型标志 必需 
			param.setSendGroupId(Constant.WORK_ORDER_TYPE_AND_SENDGROUP.YHTSYQSH.getSendGroupId());
//			param.setSendUserName(user.getUserName());
		}else{*/
			param.setWorkOrderTypeCode(Constant.WORK_ORDER_TYPE_AND_SENDGROUP.YHTSYQSHHT.getWorkOrderTypeCode());
//		}
		param.setOrderId(order.getOrderId());//订单号 
		param.setProductId(prodProduct.getProductId());//销售产品id 
		param.setReceiveUserName(managerPermUserName);//接收人用户名perm_user.user_Name 
		param.setVisitorUserName(order.getUserName());//游客姓名 
		if(order.getTravellerList()!=null && order.getTravellerList().size()>0){
			taskContent="游客姓名："+order.getTravellerList().get(0).getName()+",联系电话："+order.getTravellerList().get(0).getMobile()+"<br/>"+taskContent;
		}
		param.setWorkTaskContent(taskContent);//任务内容 必需 
		return param;
	}
}
