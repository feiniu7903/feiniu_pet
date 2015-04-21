package com.lvmama.back.message;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.po.ord.OrdPerson;
import com.lvmama.comm.bee.service.ord.OrderService;
import com.lvmama.comm.jms.Message;
import com.lvmama.comm.jms.MessageProcesser;
import com.lvmama.comm.pet.po.perm.PermUser;
import com.lvmama.comm.pet.po.pub.ComLog;
import com.lvmama.comm.pet.po.work.WorkGroup;
import com.lvmama.comm.pet.po.work.WorkGroupUser;
import com.lvmama.comm.pet.service.perm.PermUserService;
import com.lvmama.comm.pet.service.pub.ComLogService;
import com.lvmama.comm.pet.service.work.WorkGroupService;
import com.lvmama.comm.pet.service.work.WorkGroupUserService;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.work.builder.WorkOrderSenderBiz;
/**
 * 业务创建的时自动分配任务到客服
 * @author chenkeke
 *
 */
public class OrderCreateAutoAssignProcesser implements MessageProcesser{
	private OrderService orderServiceProxy;
	private WorkGroupUserService workGroupUserService;
	private WorkGroupService workGroupService;
	private WorkOrderSenderBiz workOrderProxy;
	private PermUserService permUserService;
	private ComLogService comLogService;


	@Override
	public void process(Message message) {
		if(message.isOrderCreateMsg()){
			OrdOrder order = orderServiceProxy.queryOrdOrderByOrderId(message.getObjectId());
			if(order!=null){
				// 是长途跟团游,长途自由行,出境跟团游,出境自由行	,资源需确认的，要发工单			
				if((Constant.SUB_PRODUCT_TYPE.GROUP_LONG.name().equalsIgnoreCase(order.getOrderType())
						||Constant.SUB_PRODUCT_TYPE.FREENESS_LONG.name().equalsIgnoreCase(order.getOrderType())
						||Constant.SUB_PRODUCT_TYPE.GROUP_FOREIGN.name().equalsIgnoreCase(order.getOrderType())
						||Constant.SUB_PRODUCT_TYPE.FREENESS_FOREIGN.name().equalsIgnoreCase(order.getOrderType()))){

					//信息分单
					boolean maked =false;
					PermUser assignUser = this.getPermUser(order);
					boolean isBackend =false;
					//验证是否后台下单 start
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("objectId", order.getOrderId());
					map.put("objectType", Constant.OBJECT_TYPE.ORD_ORDER.name());
					map.put("logType", Constant.COM_LOG_ORDER_EVENT.placeOrder.name());
					map.put("maxResults", 1);
					map.put("skipResults", 0);
					List<ComLog> comList = comLogService.queryByMap(map);
					if (comList != null && comList.size() > 0) {
						isBackend=true;
					}
					boolean isNotNeedResourceConfirmFlag=false;//无需资源审核发送提醒工单
					//验证是否后台下单 end
					//信息审核未通过时 发送信息分单和工单
					if(!order.isApproveInfoPass()) {
						maked = orderServiceProxy.makeOrdOrderAuditByOrderId("系统", order.getOrderId(), assignUser.getUserName());
					}
					//无需资源审核分单
					else if(!isBackend && !order.isNeedResourceConfirm()){
						maked = orderServiceProxy.makeOrdOrderConfirmAuditByOrderId("系统", order.getOrderId(), assignUser.getUserName());
//						maked=false;//无需资源审核的只需分单，不发工单
						isNotNeedResourceConfirmFlag=true;
					}

					if(maked){
						//工单分配 START
						Map<String, Object> params = new HashMap<String, Object>();
						params.put("userId", assignUser.getUserId());
						List<WorkGroupUser> workGroupUsers = workGroupUserService.queryWorkGroupUserByParams(params);
						List<WorkGroup> workGroups =null;
						WorkGroupUser assignWorkGroupUser=null;
						for (WorkGroupUser workGroupUser : workGroupUsers) {
							params = new HashMap<String, Object>();
							params.put("workGroupId", workGroupUser.getWorkGroupId());
							params.put("valid", "true");
							params.put("departmentName", "客户服务中心");
							params.put("groupName", "客户服务中心预订部");
							workGroups= workGroupService.getWorkGroupWithDepartment(params);
							if(workGroups.size()>0){
								assignWorkGroupUser = workGroupUser;
								break;
							}
						}
						if(assignWorkGroupUser!=null){
							if(isNotNeedResourceConfirmFlag){
								workOrderProxy.sendWorkOrder(order,
										Constant.WORK_ORDER_TYPE_AND_SENDGROUP.WXZYSHFDTX.getWorkOrderTypeCode(),
										null,Boolean.TRUE, Boolean.FALSE, null,
										null, assignWorkGroupUser.getWorkGroupId(), assignWorkGroupUser.getUserName(),null,true);
							}else{
								workOrderProxy.sendWorkOrder(order,
										Constant.WORK_ORDER_TYPE_AND_SENDGROUP.XXSH.getWorkOrderTypeCode(),
										"/super_back/ord/doGetOrderByOrderId.do?orderType="+order.getOrderType()+"&orderId="+order.getOrderId(),
										Boolean.TRUE, Boolean.FALSE, null,
										null, assignWorkGroupUser.getWorkGroupId(), assignWorkGroupUser.getUserName(),null,true);
							}
						}
						//工单分配 END
					}
					//如果是渠道产品的时候不发送资源审核工单
					if(!order.getHasSupplierChannelOrder()){
						/*if(order.isNeedResourceConfirm()){
	
							ProdProduct prodProduct = prodProductService
								.getProdProduct(order.getOrdOrderItemProds().get(0).getProductId());
							MetaProduct metaProdcut =null;
							Map<String, WorkGroupUser> sendedWorkGroup = new HashMap<String, WorkGroupUser>();
							for (OrdOrderItemMeta ordOrderItemMeta : order.getAllOrdOrderItemMetas()) {
								if(Constant.PRODUCT_TYPE.ROUTE.name().equals(ordOrderItemMeta.getProductType())
										&& ordOrderItemMeta.isNeedResourceConfirm()
										&& ordOrderItemMeta.getSupplierFlag().equals("false")
										&& !((Constant.SUB_PRODUCT_TYPE.GROUP_FOREIGN.name().equalsIgnoreCase(order.getOrderType())
												 ||Constant.SUB_PRODUCT_TYPE.FREENESS_FOREIGN.name().equalsIgnoreCase(order.getOrderType()))
												 &&Constant.REGION_NAMES.GANGAO.name().equals(prodProduct.getRegionName()))){
									metaProdcut = metaProductService.getMetaProduct(ordOrderItemMeta.getMetaProductId());						
									if(metaProdcut.getWorkGroupId()!=null && !"".equals(metaProdcut.getWorkGroupId())){
										WorkGroupUser receiver = null;
										//同一个订单并同一个组中只发给一个人
										if(sendedWorkGroup.get(metaProdcut.getWorkGroupId())==null){
											receiver = workOrderService.getFitUser(
													null, Long.parseLong(metaProdcut.getWorkGroupId()),
													order.getOrderId(),
													order.getMobileNumber(), null, null);
										}else{
											receiver = sendedWorkGroup.get(metaProdcut.getWorkGroupId());
										}
										if(receiver!=null){
											orderServiceProxy.makeOrdOrderItemMetaToAuditByAssignUser("系统",receiver.getUserName(), ordOrderItemMeta);	
											workOrderProxy.sendWorkOrder(order,
													Constant.WORK_ORDER_TYPE_AND_SENDGROUP.ZYSH.getWorkOrderTypeCode(),
													"/super_back/ordItem/workGetOrderAll.do?checkAuditName="+ordOrderItemMeta.getOrderItemMetaId(),
													Boolean.TRUE, Boolean.TRUE, null,
													null, receiver.getWorkGroupId(), receiver.getUserName(),ordOrderItemMeta.getOrderItemMetaId(),
													true);
										}
										sendedWorkGroup.put(metaProdcut.getWorkGroupId(), receiver);
									}
								}
							
							}
						}*/
						
					}
				}
			}
		}
	}
	private PermUser getPermUser(OrdOrder order){
		Map<String, Object> params = new HashMap<String, Object>();
		// 所以订单有关的手机号码 开始
		OrdPerson pars = new OrdPerson();
		pars.setObjectId(order.getOrderId());
		pars.setObjectType(Constant.OBJECT_TYPE.ORD_ORDER.name());
		List<OrdPerson> ordPersons= orderServiceProxy.getOrdPersons(pars);
		List<String> mobiles = new ArrayList<String>();
		mobiles.add(order.getMobileNumber());
		for (OrdPerson ordPerson : ordPersons) {//mobiles.indexOf(ordPerson.getMobile())==-1,在ordPerson.getMobile()中查找电话是否存在
			if(ordPerson.getMobile()!=null && !"".equals(ordPerson.getMobile()) && mobiles.indexOf(ordPerson.getMobile())==-1){
				mobiles.add(ordPerson.getMobile());
				mobiles.add("0"+ordPerson.getMobile());
			}
		}
		params.put("mobiles", mobiles);
		//params.put("mobile", order.getMobileNumber());
		// 所以订单有关的手机号码 结束
		params.put("parentOrgId", 67);
		if(Constant.SUB_PRODUCT_TYPE.GROUP_LONG.name().equalsIgnoreCase(order.getOrderType())
				||Constant.SUB_PRODUCT_TYPE.FREENESS_LONG.name().equalsIgnoreCase(order.getOrderType())){
			params.put("departmentName", "长线");
		}
		if(Constant.SUB_PRODUCT_TYPE.GROUP_FOREIGN.name().equalsIgnoreCase(order.getOrderType())
				||Constant.SUB_PRODUCT_TYPE.FREENESS_FOREIGN.name().equalsIgnoreCase(order.getOrderType())){
			params.put("departmentName", "境外");
		}		
		params.put("startFeedBackTime", DateUtil.formatDate(DateUtil.dsDay_Date(order.getCreateTime(), -7), "yyyyMMdd"));
		params.put("endFeedBackTime", DateUtil.formatDate(order.getCreateTime(), "yyyyMMdd"));
		List<PermUser> permUsers = permUserService.getOrderProcessUsersByLVCC(params);
		PermUser permUserByCrCountMax = null;
		List<PermUser> permUsersByCrCountMax = new ArrayList<PermUser>();
		for(PermUser permUser:permUsers){
			if(permUserByCrCountMax==null){
				permUserByCrCountMax = permUser;
			}
			if( permUser.getCrCount()==permUserByCrCountMax.getCrCount()){
				permUsersByCrCountMax.add(permUser);
			}
		}
		PermUser permUser = null;
		//如果有聊天记录时
		if(permUsersByCrCountMax.size()>0){
			//如果LVCC联系的客服同时存在相同的联系次数时   取未处理工单最少的一个
			if(permUsersByCrCountMax.size()>1){
				//最少分单者
				permUser = this.getComAuditPermUserByMin(permUsersByCrCountMax,order.getOrderId());
			}else{
				permUser = permUsersByCrCountMax.get(0);
			}
		}//没有聊天记录时
		else{
			//取得在线 并工单最少的客服
			params.put("workStatus","ONLINE");
			params.put("valid","Y");
			Long usersCount = permUserService.selectUsersCountByParams(params);
			params.put("skipResults", 0);
			params.put("maxResults", usersCount);
			permUsers = permUserService.selectUsersByParams(params);
			
			if(permUsers.size()==0){
				//取得所有 并工单最少的客服
				params.put("workStatus",null);
				params.put("valid","Y");
				usersCount = permUserService.selectUsersCountByParams(params);
				params.put("skipResults", 0);
				params.put("maxResults", usersCount);
				permUsers = permUserService.selectUsersByParams(params);
			}
			List<PermUser> permUserLeaders = new ArrayList<PermUser>(); //非专员
			List<PermUser> permUserComs = new ArrayList<PermUser>(); //专员
			for (PermUser permUserTemp : permUsers) {
				if(permUserTemp.getPosition()==null || permUserTemp.getPosition().indexOf("专员")==-1){
					permUserLeaders.add(permUserTemp);
				}else{
					permUserComs.add(permUserTemp);
				}
			}
			//最少分单者专员
			permUser = this.getComAuditPermUserByMin(permUserComs,order.getOrderId());
			if(permUser==null){
				//最少分单者非专员
				permUser = this.getComAuditPermUserByMin(permUserLeaders,order.getOrderId());
			}
		}
		return permUser;
	}
	/**
	 * 审核中最少的工单个数客服
	 * @param permUsers
	 * @return
	 */
	private PermUser getComAuditPermUserByMin(List<PermUser> permUsers,Long orderId){
		if(permUsers.size()==0){
			return null;
		}
		PermUser permUserByMin = null;
		Long minCount = null;
		Map<Long, List<PermUser>> permUserMap = new HashMap<Long, List<PermUser>>();
		List<PermUser> permUserList = new ArrayList<PermUser>();
		for(PermUser permUser:permUsers){
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("takenOperator", permUser.getUserName());
			params.put("infoApproveStatus",Constant.INFO_APPROVE_STATUS.UNVERIFIED.name() );
			params.put("orderStatus",Constant.ORDER_STATUS.NORMAL );
			Long count = orderServiceProxy.queryOrdOrderCount(params);
			if(minCount==null){
				minCount = count;
			}
			if(minCount>count){
				minCount = count;
			}
			if(permUserMap.get(count)==null){
				permUserList = new ArrayList<PermUser>(); 
			}else{
				permUserList = permUserMap.get(count);
			}
			permUserList.add(permUser);
			permUserMap.put(count, permUserList);			
		}
		permUserList = permUserMap.get(minCount);
		Map<String, Object> params = new HashMap<String, Object>();
		Date beforDate  = new Date();
		Date comLogDate = null;
		for (PermUser permUser : permUserList) {
			params.clear();
			params.put("objectType", Constant.OBJECT_TYPE.ORD_ORDER.name());
			params.put("logType", Constant.COM_LOG_ORDER_EVENT.approvePass);
			params.put("objectType", Constant.OBJECT_TYPE.ORD_ORDER.name());
			params.put("operatorName", permUser.getUserName());
			comLogDate = comLogService.queryByMapMaxCreateTime(params);
			if(comLogDate==null){
				permUserByMin = permUser;
				break;
			}else if(comLogDate.before(beforDate)){
				beforDate = comLogDate;
				permUserByMin = permUser;
			}
		}
		return permUserByMin;
	}
	public void setOrderServiceProxy(OrderService orderServiceProxy) {
		this.orderServiceProxy = orderServiceProxy;
	}
	public void setWorkOrderProxy(WorkOrderSenderBiz workOrderProxy) {
		this.workOrderProxy = workOrderProxy;
	}
	public void setWorkGroupUserService(WorkGroupUserService workGroupUserService) {
		this.workGroupUserService = workGroupUserService;
	}
	public void setPermUserService(PermUserService permUserService) {
		this.permUserService = permUserService;
	}
	public void setWorkGroupService(WorkGroupService workGroupService) {
		this.workGroupService = workGroupService;
	}
	public void setComLogService(ComLogService comLogService) {
		this.comLogService = comLogService;
	}
}
