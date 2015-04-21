package com.lvmama.pet.job.ebk;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.lvmama.comm.bee.po.ebooking.EbkTask;
import com.lvmama.comm.bee.po.meta.MetaProduct;
import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.po.prod.ProdProduct;
import com.lvmama.comm.bee.service.ebooking.EbkCertificateService;
import com.lvmama.comm.bee.service.meta.MetaProductService;
import com.lvmama.comm.bee.service.ord.OrderService;
import com.lvmama.comm.bee.service.prod.ProdProductService;
import com.lvmama.comm.pet.po.perm.PermUser;
import com.lvmama.comm.pet.service.perm.PermUserService;
import com.lvmama.comm.pet.service.pub.ComLogService;
import com.lvmama.comm.pet.service.work.PublicWorkOrderService;
import com.lvmama.comm.pet.vo.InvokeResult;
import com.lvmama.comm.pet.vo.Page;
import com.lvmama.comm.pet.vo.WorkOrderCreateParam;
import com.lvmama.comm.vo.Constant;

public class EbkLazyWorkOrderJob implements Runnable{
	private Logger log = Logger.getLogger(EbkLazyWorkOrderJob.class);
	@Autowired
	private PublicWorkOrderService publicWorkOrderService;
	@Autowired
	private ComLogService comLogRemoteService; 	
	@Autowired
	private EbkCertificateService ebkCertificateService;
	@Autowired
	private OrderService orderServiceProxy;	
	@Autowired
	private ProdProductService prodProductService;
	@Autowired
	private PermUserService permUserService;
	
	private MetaProductService metaProductService;
	
	public void run(){
		if (Constant.getInstance().isJobRunnable()) {
			log.info("Auto EbkLazyWorkOrderJob running.....");
			Map<String,Object> params=new HashMap<String,Object>();
			params.put("certificateStatus", Constant.EBK_TASK_STATUS.CREATE.toString());
			params.put("consumeTimeStart", 10);
			//不定期订单不需要工单
			params.put("isAperiodic", Constant.TRUE_FALSE.FALSE.getAttr1());
			Page<EbkTask> pEbktaskList=ebkCertificateService.queryEbkTaskPageListSQL(1L, 1000L, params); 
			List<EbkTask> ebktaskList =pEbktaskList.getItems();
			int allLazyCount=0;
			int successCreateCount=0;
			if(ebktaskList!=null){
				allLazyCount=ebktaskList.size();
				for(EbkTask ebkTask:ebktaskList){
					try{
						OrdOrder order=orderServiceProxy.queryOrdOrderByOrderId(ebkTask.getOrderId());
						ProdProduct prodProduct=prodProductService.getProdProductById(order.getMainProduct().getProductId());
						PermUser permUser=permUserService.getPermUserByUserId(prodProduct.getManagerId());
						boolean flag=createEbkLazyWorkOrder(order,ebkTask,prodProduct,permUser);
						if(flag)successCreateCount++;
					}catch(Exception ex){
						ex.printStackTrace();
						log.error(ex);
					}
				}
			}
			log.info("It has ebk lazy order count="+ allLazyCount +", success work order created count="+successCreateCount);
			log.info("Auto EbkLazyWorkOrderJob end.....");
		}
	}
	
	/**
	 * 新增延时工单
	 * @param order  订单
	 * @param ebkTask  ebk任务
	 * @param prodProduct  销售产品
	 * @param permUser  产品经理
	 * @return
	 */
	private boolean createEbkLazyWorkOrder(OrdOrder order,EbkTask ebkTask,ProdProduct prodProduct,PermUser permUser){
		if(!Constant.EBK_TASK_STATUS.CREATE.toString().equals(ebkTask.getEbkCertificate().getCertificateStatus())){
			return false;
		}
		try{
			String taskContent="供应商名称："+ebkTask.getEbkCertificate().getSupplierName()+"，<br/>供应商电话："+ebkTask.getEbkCertificate().getToTel()+"，<br/>" +
					"类型："+ ebkTask.getEbkCertificate().getZhEbkCertificateType() +"，<br/>确认状态：未处理。";
			WorkOrderCreateParam param = new WorkOrderCreateParam(); 
			if(Constant.PRODUCT_TYPE.ROUTE.getCode().equals(prodProduct.getProductType())){
				if(Constant.SUB_PRODUCT_TYPE.GROUP_FOREIGN.getCode().equals(prodProduct.getSubProductType()) 
						|| Constant.SUB_PRODUCT_TYPE.FREENESS_FOREIGN.getCode().equals(prodProduct.getSubProductType())){
					param.setWorkOrderTypeCode(Constant.WORK_ORDER_TYPE_AND_SENDGROUP.EBKYSGD.getWorkOrderTypeCode());
					//add by zhushuying 通过匹配采购产品中所在组织获取接收组织id
					if (null != prodProduct) {
						Long metaProductId = order.getAllOrdOrderItemMetas().get(0)
								.getMetaProductId();
						MetaProduct metaProdcut = metaProductService
								.getMetaProduct(metaProductId);
						if (null != metaProdcut
								&& StringUtils.isNotBlank(metaProdcut
										.getWorkGroupId())) {
							Long receiveGroupId = Long.valueOf(metaProdcut
									.getWorkGroupId());
							param.setReceiveGroupId(receiveGroupId);  //获取接收组织Id
						}
					}
					//end by zhushuying
				}else if(Constant.SUB_PRODUCT_TYPE.FREENESS.getCode().equals(prodProduct.getSubProductType())){
					param.setWorkOrderTypeCode(Constant.WORK_ORDER_TYPE_AND_SENDGROUP.EBKYSGDMDDZYX.getWorkOrderTypeCode());
				}else if(Constant.SUB_PRODUCT_TYPE.GROUP.getCode().equals(prodProduct.getSubProductType())){
					param.setWorkOrderTypeCode(Constant.WORK_ORDER_TYPE_AND_SENDGROUP.EBKYSGDDX.getWorkOrderTypeCode());
				}else{
					log.debug("can't create work order,order_id="+ order.getOrderId() +", PRODUCT_TYPE="+prodProduct.getProductType()+",SUB_PRODUCT_TYPE="+prodProduct.getSubProductType());
					return false;
				}
			}else if(Constant.PRODUCT_TYPE.HOTEL.getCode().equals(prodProduct.getProductType())){
				param.setWorkOrderTypeCode(Constant.WORK_ORDER_TYPE_AND_SENDGROUP.EBKYSGDJD.getWorkOrderTypeCode());
			}else{
				log.debug("can't create work order,order_id="+ order.getOrderId() +", PRODUCT_TYPE="+prodProduct.getProductType()+",SUB_PRODUCT_TYPE="+prodProduct.getSubProductType());
				return false;
			}
			//判断是否已经存在未处理工单
			if(publicWorkOrderService.isExists(order.getOrderId(), prodProduct.getProductId(), param.getWorkOrderTypeCode(), Constant.WORK_ORDER_STATUS.UNCOMPLETED.getCode())){
				log.debug("work order is exists,can't create work order,order_id="+ order.getOrderId() +",product_id="+ prodProduct.getProductId() +", PRODUCT_TYPE="+prodProduct.getProductType()+",SUB_PRODUCT_TYPE="+prodProduct.getSubProductType());
				return false;
			}
			param.setOrderId(order.getOrderId());//订单号 
			param.setProductId(prodProduct.getProductId());//销售产品id 
			param.setReceiveUserName(permUser.getUserName());//接收人用户名perm_user.user_Name 
			if(order.getTravellerList()!=null && order.getTravellerList().size()>0){
				taskContent=taskContent+"<br/>游客姓名："+order.getTravellerList().get(0).getName()+",联系电话："+order.getTravellerList().get(0).getMobile();
				param.setWorkTaskContent(taskContent);//任务内容 必需 
				param.setVisitorUserName(order.getTravellerList().get(0).getName());// 游客姓名
			}
			InvokeResult ivokeresult = publicWorkOrderService.createWorkOrder(param); 
			if(ivokeresult!=null &&ivokeresult.getCode() == 0){ 
				log.debug("success created work order:workOrderId="+ivokeresult.getWorkOrderId()+", workTaskId="+ivokeresult.getWorkTaskId());
				return true;
			}else{
				if(ivokeresult!=null)
				{
					log.error(ivokeresult.getDescription());
				}
			}
		}catch(Exception ex){
			log.error(ex);
		}
		return false;
	}

	public MetaProductService getMetaProductService() {
		return metaProductService;
	}

	public void setMetaProductService(MetaProductService metaProductService) {
		this.metaProductService = metaProductService;
	}
}
