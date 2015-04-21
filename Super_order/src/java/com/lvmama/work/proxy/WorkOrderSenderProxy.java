package com.lvmama.work.proxy;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.comm.bee.po.meta.MetaProduct;
import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.po.ord.OrdOrderItemMeta;
import com.lvmama.comm.bee.po.prod.ProdProduct;
import com.lvmama.comm.bee.po.pub.ComAudit;
import com.lvmama.comm.bee.service.meta.MetaProductService;
import com.lvmama.comm.bee.service.prod.ProdProductService;
import com.lvmama.comm.pet.po.pub.ComLog;
import com.lvmama.comm.pet.po.work.WorkGroupUser;
import com.lvmama.comm.pet.service.pub.ComLogService;
import com.lvmama.comm.pet.service.work.PublicWorkOrderService;
import com.lvmama.comm.pet.service.work.WorkGroupUserService;
import com.lvmama.comm.pet.vo.InvokeResult;
import com.lvmama.comm.pet.vo.WorkOrderCreateParam;
import com.lvmama.comm.utils.MemcachedUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.work.builder.WorkOrderSenderBiz;
import com.lvmama.order.service.OrderAuditService;

public class WorkOrderSenderProxy implements WorkOrderSenderBiz {

	private static Log log = LogFactory.getLog(WorkOrderSenderProxy.class);

	private ComLogService comLogService;

	private ProdProductService prodProductService;

	private PublicWorkOrderService publicWorkOrderService;

	private MetaProductService metaProductService;
	private WorkGroupUserService workGroupUserService;
	private OrderAuditService orderAuditService;
	
	private final int DAY_5=5*24*60*60;

	/**
	 * 发系统工单
	 * 
	 * @param ordersList
	 *            订单集合
	 * @param workOrderType
	 *            工单类型
	 * @param url
	 *            处理链接
	 * @param isRepeatSender
	 *            是否发送重复工单
	 * @param isJdGroup
	 *            是否计调组织
	 * @param sendGroupId
	 *            发送人组织id
	 * @param sendUserName
	 *            发送人
	 * @param receiveGroupId
	 *            接收人组织id
	 * @param receiveName
	 *            接收人
	 * */
	public void sendWorkOrder(List<OrdOrder> ordersList, String workOrderType,
			String url, boolean isRepeatSender, boolean isJdGroup,
			Long sendGroupId, String sendUserName, Long receiveGroupId,
			String receiveUserName) {
		try {
			for (OrdOrder order : ordersList) {
				sendWorkOrder(order, workOrderType, url, isRepeatSender,
						isJdGroup, sendGroupId, sendUserName, receiveGroupId,
						receiveUserName, null, false);
			}
		} catch (Exception e) {
			log.error(e);
		}
	}

	/**
	 * 发系统工单
	 * 
	 * @param order
	 *            订单
	 * @param workOrderType
	 *            工单类型
	 * @param url
	 *            处理链接
	 * @param isRepeatSender
	 *            是否发送重复工单
	 * @param isJdGroup
	 *            是否计调组织
	 * @param sendGroupId
	 *            发送人组织id
	 * @param sendUserName
	 *            发送人
	 * @param receiveGroupId
	 *            接收人组织id
	 * @param receiveName
	 *            接收人
	 * @param metaProductId
	 *            采购单ID
	 * @param isNotGetFitReceiveUser
	 *            是否要重新获取接收用户，true：不需要，false：需要重新获取
	 * @param orderItemMetaId
	 *            子项ID
	 * */
	public void sendWorkOrder(OrdOrder order, String workOrderType, String url,
			boolean isRepeatSender, boolean isJdGroup, Long sendGroupId,
			String sendUserName, Long receiveGroupId, String receiveUserName,
			Long metaProductId, boolean isNotGetFitReceiveUser) {
		try {
			// 判断是否可以发送重复工单
			/*if (!isRepeatSender) {
				boolean isExistFlag = publicWorkOrderService.isExists(
						order.getOrderId(), null, workOrderType, null);
				if (isExistFlag) {
					return;
				}
			}*/
			final String memcached_key = workOrderType + "_" + order.getOrderId(); 
			// 判断是否可以发送重复工单 
			if (!isRepeatSender) {
				String val = (String) MemcachedUtil.getInstance().get(
						memcached_key);
				if (StringUtils.equals("true", val)) {
					return;
				}
				boolean isExistFlag = publicWorkOrderService.isExists(
						order.getOrderId(), null, workOrderType, null);
				if (isExistFlag) {
					return;
				}
			}

			ProdProduct prodProduct = prodProductService.getProdProduct(order
					.getMainProduct().getProductId());
			if (null != prodProduct) {
				boolean send = true;
				if (isJdGroup) {
					// 根据采购产品获取领单人
					for (OrdOrderItemMeta ordOrderItemMeta : order
							.getAllOrdOrderItemMetas()) {
						// 获取采购产品（采购产品类型是其他的排除）
						if(!ordOrderItemMeta.getProductType().equals(Constant.PRODUCT_TYPE.OTHER.name())){
							MetaProduct metaProduct = getMetaProduct(ordOrderItemMeta);
							if (null != metaProduct
									&& StringUtils.isNotBlank(metaProduct
											.getWorkGroupId())) {
								Long jdReceiveGroupId = Long.valueOf(metaProduct
										.getWorkGroupId());
	
								Map<String, String> paramOperator = new HashMap<String, String>();
								paramOperator.put("objectId", String
										.valueOf(ordOrderItemMeta
												.getOrderItemMetaId()));
								paramOperator.put("objectType",
										Constant.OBJECT_TYPE.ORD_ORDER_ITEM_META
												.name());
								List<ComAudit> comAudits = orderAuditService
										.selectComAuditByParam(paramOperator);
								if (comAudits.size() > 0) {
									Map<String, Object> paramRecriver = new HashMap<String, Object>();
									paramRecriver.put("userName", comAudits.get(0)
											.getOperatorName()); // 获取领单人
									paramRecriver.put("workGroupId",
											jdReceiveGroupId);
									paramRecriver.put("valid", "Y");
									paramRecriver.put("workGroupUserValid", "true");
									List<WorkGroupUser> workGroupUsers = workGroupUserService
											.getWorkGroupUserByPermUserAndGroup(paramRecriver);
									// 判断该领单人在该组织中是否存在，如果不存就不发工单
									if (workGroupUsers.size() > 0) {
										receiveUserName = workGroupUsers.get(0)
												.getUserName();
										send = true;
										isNotGetFitReceiveUser = true;
										receiveGroupId = jdReceiveGroupId;
										break;
									} else {
										send = false;
									}
								}
							} else {
								// 如果采购产品没有设置组织，就不发工单
								send = false;
							}
						}
					}// end for

					// 没有领单的或无需资源审核的，默认用第一个非其它采购产品组织id
					if (null == receiveGroupId) {
						for (OrdOrderItemMeta ordOrderItemMeta : order
								.getAllOrdOrderItemMetas()) {
							MetaProduct metaProduct = getMetaProduct(ordOrderItemMeta);
							if (null != metaProduct
									&& StringUtils.isNotBlank(metaProduct
											.getWorkGroupId())) {
								receiveGroupId = Long.valueOf(metaProduct
										.getWorkGroupId());
								break;
							}
						}
					}

				}

				if (send) {
					// 生成工单
					makeWorkOrder(workOrderType, url, order, prodProduct,
							isJdGroup, sendGroupId, sendUserName,
							receiveGroupId, receiveUserName,
							isNotGetFitReceiveUser);
					if(!isRepeatSender){ 
						MemcachedUtil.getInstance().set(memcached_key,MemcachedUtil.getDateAfter(DAY_5), "true"); 
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e);
		}
	}

	private MetaProduct getMetaProduct(OrdOrderItemMeta ordOrderItemMeta) {
		Long tmpMetaProductId = ordOrderItemMeta.getMetaProductId();
		MetaProduct metaProduct = metaProductService
				.getMetaProduct(tmpMetaProductId);
		return metaProduct;
	}

	/**
	 * 生成工单
	 * */
	private void makeWorkOrder(String workOrderType, String url,
			OrdOrder order, ProdProduct prodProduct, boolean isJdGroup,
			Long sendGroupId, String sendUserName, Long receiveGroupId,
			String receiveUserName, boolean isNotGetFitReceiveUser) {
		// 组织信息
		WorkOrderCreateParam param = initWorkOrderParam(workOrderType, url,
				order, prodProduct, isJdGroup, sendGroupId, sendUserName,
				receiveGroupId, receiveUserName, isNotGetFitReceiveUser);
		// 生成工单
		InvokeResult ivokeresult = publicWorkOrderService
				.createWorkOrder(param);
		String logName = "发送系统工单给";
		if (isJdGroup) {
			logName += "计调";
		} else {
			logName += "客服";
		}
		String logContent = "成功发送[";
		logContent += ivokeresult.getWorkOrderTypeName();
		logContent += "]工单;发送人：";
		String operatorName = "";
		if (StringUtils.isNotBlank(sendUserName)) {
			operatorName = sendUserName;
		} else {
			operatorName = "系统";
		}
		logContent += operatorName + ", 接收人：" + ivokeresult.getResult();

		if (ivokeresult != null && ivokeresult.getCode() == 0) {
			comLogService.insert("ORD_ORDER", null, order.getOrderId(),
					operatorName, "", logName, logContent, null);
			log.debug("success created work order:workOrderId="
					+ ivokeresult.getWorkOrderId() + ", workTaskId="
					+ ivokeresult.getWorkTaskId());
		} else {
			if (ivokeresult != null) {
				String errMsg = ivokeresult.getDescription();
				
				// 不重复记录错误日志
				Map<String, Object> paraMap = new HashMap<String, Object>();
				paraMap.put("objectType", "ORD_ORDER");
				paraMap.put("objectId", order.getOrderId());
				paraMap.put("operatorName", operatorName);
				paraMap.put("logType", "WORK_ORDER_SEND_ERROR");
				paraMap.put("content", logName + ", " + errMsg);
				Long errCnt = comLogService.queryCountByMap(paraMap);
				if (0L == errCnt) {
					comLogService.insert("ORD_ORDER", null, order.getOrderId(),
							operatorName, "WORK_ORDER_SEND_ERROR",
							"WORK_ORDER_SEND_ERROR", logName + ", " + errMsg, null);
				}
				log.error(ivokeresult.getDescription());
			}
		}
	}

	/**
	 * 组织工单信息
	 * */
	private WorkOrderCreateParam initWorkOrderParam(String workOrderTypeCode,
			String url, OrdOrder order, ProdProduct prodProduct,
			boolean isJdGroup, Long sendGroupId, String sendUserName,
			Long receiveGroupId, String receiveUserName,
			boolean isNotGetFitReceiveUser) {
		WorkOrderCreateParam param = new WorkOrderCreateParam();
		param.setWorkOrderTypeCode(workOrderTypeCode);// 工单类型标志 必需
		param.setJdGroup(isJdGroup);// 是否计调类型
		param.setSendGroupId(sendGroupId);
		param.setSendUserName(sendUserName);
		param.setReceiveGroupId(receiveGroupId);
		param.setReceiveUserName(receiveUserName);
		param.setUrl(url);
		param.setOrderId(order.getOrderId());// 订单号
		param.setProductId(prodProduct.getProductId());// 销售产品id
		param.setNotGetFitReceiveUser(isNotGetFitReceiveUser);
		param.setTakenOperator(order.getTakenOperator());
		// 如果领单人为空，将下单人作为领单人
		if (order.getTakenOperator() == null) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("objectId", order.getOrderId());
			map.put("objectType", Constant.OBJECT_TYPE.ORD_ORDER.name());
			map.put("logType", Constant.COM_LOG_ORDER_EVENT.placeOrder.name());
			map.put("maxResults", 1);
			map.put("skipResults", 0);
			List<ComLog> comList = comLogService.queryByMap(map);
			if (comList != null && comList.size() > 0) {
				ComLog comlog = comList.get(0);
				param.setTakenOperator(comlog.getOperatorName());
			}
		}
		if (order.getTravellerList() != null
				&& order.getTravellerList().size() > 0) {
			String taskContent = "游客姓名："
					+ order.getTravellerList().get(0).getName() + ",联系电话："
					+ order.getTravellerList().get(0).getMobile() + "<br/>";
			param.setWorkTaskContent(taskContent);// 任务内容 必需
			param.setVisitorUserName(order.getTravellerList().get(0).getName());// 游客姓名
		}
		return param;
	}

	public ComLogService getComLogService() {
		return comLogService;
	}

	public void setComLogService(ComLogService comLogService) {
		this.comLogService = comLogService;
	}

	public ProdProductService getProdProductService() {
		return prodProductService;
	}

	public void setProdProductService(ProdProductService prodProductService) {
		this.prodProductService = prodProductService;
	}

	public PublicWorkOrderService getPublicWorkOrderService() {
		return publicWorkOrderService;
	}

	public void setPublicWorkOrderService(
			PublicWorkOrderService publicWorkOrderService) {
		this.publicWorkOrderService = publicWorkOrderService;
	}

	public MetaProductService getMetaProductService() {
		return metaProductService;
	}

	public void setMetaProductService(MetaProductService metaProductService) {
		this.metaProductService = metaProductService;
	}

	public void setWorkGroupUserService(
			WorkGroupUserService workGroupUserService) {
		this.workGroupUserService = workGroupUserService;
	}

	public void setOrderAuditService(OrderAuditService orderAuditService) {
		this.orderAuditService = orderAuditService;
	}

}
