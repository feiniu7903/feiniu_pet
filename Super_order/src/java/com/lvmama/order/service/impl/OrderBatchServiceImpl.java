package com.lvmama.order.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lvmama.comm.bee.po.ord.OrdOrderBatch;
import com.lvmama.comm.bee.po.ord.OrdOrderBatchOrder;
import com.lvmama.comm.bee.service.ord.OrderBatchService;
import com.lvmama.comm.pet.po.user.UserUser;
import com.lvmama.comm.pet.service.user.UserUserProxy;
import com.lvmama.comm.pet.vo.Page;
import com.lvmama.comm.vo.Constant;
import com.lvmama.order.dao.impl.OrderBatchDAO;

public class OrderBatchServiceImpl implements OrderBatchService {

	private OrderBatchDAO orderBatchDAO;

	private UserUserProxy userUserProxy;
	
	@Override
	public Long insert(OrdOrderBatch orderBatch) {
		return orderBatchDAO.insert(orderBatch);
	}

	@Override
	public OrdOrderBatch selectByBatchId(Long batchId) {
		return orderBatchDAO.selectByBatchId(batchId);
	}
	
	@Override
	public Page<OrdOrderBatch> selectByParams(Map<Object, Object> params) {
		Page<OrdOrderBatch> page = orderBatchDAO.selectByParams(params);
		List<OrdOrderBatch> orderPascodes = page.getItems();
		OrdOrderBatch passcode = orderPascodes!=null&&orderPascodes.size()>0?orderPascodes.get(0):null;
		//创建人
		String creatorName = null;
		if(passcode!=null){
			UserUser userUser = userUserProxy.getUserUserByPk(passcode.getCreator());
			if(userUser!=null){
				creatorName = userUser.getUserName();
			}
		}
		
		for (OrdOrderBatch ordOrderBatch : orderPascodes) {
			ordOrderBatch.setCreatorName(creatorName);
		}
		return page;
	}

	
	@Override
	public List<OrdOrderBatch> selectNeedCreateOrder(){
		List<OrdOrderBatch> lists = orderBatchDAO.selectNeedCreateOrder();
		return lists;
	}
	
	
	public OrderBatchDAO getOrderBatchDAO() {
		return orderBatchDAO;
	}

	public void setOrderBatchDAO(OrderBatchDAO orderBatchDAO) {
		this.orderBatchDAO = orderBatchDAO;
	}

	@Override
	public void inserBatchOrder(OrdOrderBatchOrder batchOrder) {
		orderBatchDAO.insertBatchOrders(batchOrder);
	}
	
	@Override
	public Page<OrdOrderBatch> listAbandonOrder(Map<Object,Object> params){
		 
		Page<OrdOrderBatch> page = orderBatchDAO.listAbandonOrder(params);
		List<OrdOrderBatch> orderPascodes = page.getItems();
		OrdOrderBatch passcode = orderPascodes!=null&&orderPascodes.size()>0?orderPascodes.get(0):null;
		//创建人
		String creatorName = null;
		if(passcode!=null){
			UserUser userUser = userUserProxy.getUserUserByPk(passcode.getCreator());
			if(userUser!=null){
				creatorName = userUser.getUserName();
			}
		}
				
		for (OrdOrderBatch ordOrderBatch : orderPascodes) {
			ordOrderBatch.setCreatorName(creatorName);
		}
				
		return page;
	}

	@Override
	public List<OrdOrderBatch> listBatchPassCode(Long batchId) {
		List<OrdOrderBatch> orderPascodes = orderBatchDAO.listBatchPassCode(batchId);
		OrdOrderBatch passcode = orderPascodes!=null&&orderPascodes.size()>0?orderPascodes.get(0):null;
		//创建人
		String creatorName = null;
		//创建人电话
		String creatorPhone = null;
		if(passcode!=null){
			UserUser userUser = userUserProxy.getUserUserByPk(passcode.getCreator());
			if(userUser!=null){
				creatorName = userUser.getUserName();
				creatorPhone = userUser.getPhoneNumber();
			}
		}
		
		for (OrdOrderBatch ordOrderBatch : orderPascodes) {
			ordOrderBatch.setCreatorName(creatorName);
			ordOrderBatch.setCreatorPhone(creatorPhone);
		}
		return orderPascodes;
	}

	public List<OrdOrderBatch> queryBatchOrdersForCancel(Long batchId){
		
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("orderStatus", "NORMAL");
		List<OrdOrderBatch> orderList = orderBatchDAO.queryBatchOrdersForCancel(params);
		List<OrdOrderBatch> list = new ArrayList<OrdOrderBatch>();
		for (OrdOrderBatch ordOrderBatch : orderList) {
			if(!Constant.PASSCODE_USE_STATUS.USED.name().equalsIgnoreCase(ordOrderBatch.getPerformStatus())){
				list.add(ordOrderBatch);
			}
		}
		return list;
	}
	
	public UserUserProxy getUserUserProxy() {
		return userUserProxy;
	}

	public void setUserUserProxy(UserUserProxy userUserProxy) {
		this.userUserProxy = userUserProxy;
	}

	@Override
	public Map<String, Integer> getBatchResult(Long batchId) {
		
		int ordCount= orderBatchDAO.getBatchOrderCount(batchId);
		int passCodeCount= orderBatchDAO.getBatchPassCodeCount(batchId);
		
		Map<String, Integer> batchResult = new HashMap<String, Integer>();
		//已经生成的订单数
		batchResult.put("ordCount", ordCount);
		//已经申码成功的订单数
		batchResult.put("passCodeCount", passCodeCount);
		
		return batchResult;
	}

	@Override
	public Map<String, Object> getBatchCount(Long batchId) {
		
		int ordCount= orderBatchDAO.getBatchOrderCount(batchId);
		int canceledCount= orderBatchDAO.getBatchCanceledCount(batchId);
		int usedCount= orderBatchDAO.getBatchUsedCount(batchId);
		int canCancelCount= orderBatchDAO.getBatchcanCancelCount(batchId);
		
		int otherCount= ordCount-canceledCount-usedCount-canCancelCount;
		canCancelCount = canCancelCount<0?0:canCancelCount;
		Map<String, Object> batchResult = new HashMap<String, Object>();
		//已经生成的订单数
		batchResult.put("ordCount", ordCount);
		//已经履行的订单数
		batchResult.put("usedCount", usedCount);
		//已经废单的订单数
		batchResult.put("canceledCount", canceledCount);
		//可以废单的订单数
		batchResult.put("canCancelCount", canCancelCount);
		
		//申码失败未取消订单数
		batchResult.put("otherCount", otherCount);
		
		return batchResult;
	}
	
	@Override
	public boolean updateBatchStatus(Map<Object,Object> params) {
		return orderBatchDAO.updateBatchStatus(params);
	}
	
	public boolean updateBatchValid(Map<Object,Object> params) {
		return orderBatchDAO.updateBatchValid(params);
	}
}
