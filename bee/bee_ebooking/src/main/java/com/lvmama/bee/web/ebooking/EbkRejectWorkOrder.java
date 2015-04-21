package com.lvmama.bee.web.ebooking;

import java.util.List;

import org.apache.log4j.Logger;

import com.lvmama.comm.bee.po.ebooking.EbkCertificateItem;
import com.lvmama.comm.bee.po.ebooking.EbkTask;
import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.po.prod.ProdProduct;
import com.lvmama.comm.pet.po.perm.PermUser;
import com.lvmama.comm.pet.po.sup.SupSupplier;
import com.lvmama.comm.pet.vo.WorkOrderCreateParam;
import com.lvmama.comm.vo.Constant;

public abstract class EbkRejectWorkOrder {
	protected static Logger log = Logger.getLogger(EbkRejectWorkOrder.class);
	/**
	 * 新增拒绝工单初始化
	 * @param order  订单
	 * @param ebkTask  ebk任务
	 * @param prodProduct  销售产品
	 * @param permUser  产品经理
	 * @param supSupplier  供应商
	 * @param reason  拒绝理由
	 * @return
	 */
	public static WorkOrderCreateParam initParam(OrdOrder order,EbkTask ebkTask,ProdProduct prodProduct,PermUser permUser,SupSupplier supSupplier,String taskContent){
		if(!Constant.EBK_CERTIFICATE_TYPE.CHANGE.getCode().equals(ebkTask.getEbkCertificate().getEbkCertificateType())
				&& !Constant.EBK_CERTIFICATE_TYPE.CONFIRM.getCode().equals(ebkTask.getEbkCertificate().getEbkCertificateType())
				&&!Constant.EBK_CERTIFICATE_TYPE.ENQUIRY.getCode().equals(ebkTask.getEbkCertificate().getEbkCertificateType())
				){
			return null;
		}
		if(!Constant.EBK_TASK_STATUS.CREATE.toString().equals(ebkTask.getEbkCertificate().getCertificateStatus())){
			return null;
		}
		WorkOrderCreateParam param = new WorkOrderCreateParam(); 
		if(Constant.PRODUCT_TYPE.ROUTE.getCode().equals(prodProduct.getProductType())){
			if(Constant.SUB_PRODUCT_TYPE.GROUP_FOREIGN.getCode().equals(prodProduct.getSubProductType()) 
					|| Constant.SUB_PRODUCT_TYPE.FREENESS_FOREIGN.getCode().equals(prodProduct.getSubProductType())){
				param.setWorkOrderTypeCode(Constant.WORK_ORDER_TYPE_AND_SENDGROUP.EBKDDBJSGD.getWorkOrderTypeCode());
			}else if(Constant.SUB_PRODUCT_TYPE.FREENESS.getCode().equals(prodProduct.getSubProductType())){
				param.setWorkOrderTypeCode(Constant.WORK_ORDER_TYPE_AND_SENDGROUP.EBKDDBJSGDMDDZYX.getWorkOrderTypeCode());
			}else if(Constant.SUB_PRODUCT_TYPE.GROUP.getCode().equals(prodProduct.getSubProductType())){
				param.setWorkOrderTypeCode(Constant.WORK_ORDER_TYPE_AND_SENDGROUP.EBKDDBJSGDDX.getWorkOrderTypeCode());
			}else{
				log.info("can't create work order,order_id="+ order.getOrderId() +", PRODUCT_TYPE="+prodProduct.getProductType()+",SUB_PRODUCT_TYPE="+prodProduct.getSubProductType());
				return null;
			}
		}else if(Constant.PRODUCT_TYPE.HOTEL.getCode().equals(prodProduct.getProductType())){
			param.setWorkOrderTypeCode(Constant.WORK_ORDER_TYPE_AND_SENDGROUP.EBKDDBJSGDJD.getWorkOrderTypeCode());
		}else{
			log.info("can't create work order,order_id="+ order.getOrderId() +", PRODUCT_TYPE="+prodProduct.getProductType()+",SUB_PRODUCT_TYPE="+prodProduct.getSubProductType());
			return null;
		}
		param.setOrderId(order.getOrderId());//订单号 
		param.setProductId(prodProduct.getProductId());//销售产品id 
		param.setReceiveUserName(permUser.getUserName());//接收人用户名perm_user.user_Name 
		param.setVisitorUserName(order.getUserName());//游客姓名 
		if(order.getTravellerList()!=null && order.getTravellerList().size()>0){
			taskContent=taskContent+"<br/>游客姓名："+order.getTravellerList().get(0).getName()+",联系电话："+order.getTravellerList().get(0).getMobile();
		}
		param.setWorkTaskContent(taskContent);//任务内容 必需 
		List<EbkCertificateItem> lst=ebkTask.getEbkCertificate().getEbkCertificateItemList();
		String url="";
		if(lst!=null && lst.size()>0){
			for(int i=0;i<lst.size();i++){
				if(i==0){
					url="/super_back/ordItem/workGetOrderAll.do?checkAuditName="+lst.get(i).getOrderItemMetaId();
				}else{
					url+="&checkAuditName="+lst.get(i).getOrderItemMetaId();
				}
			}
			param.setUrl(url);
		}
		return param;
	}
}
