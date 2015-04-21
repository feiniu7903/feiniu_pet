package com.lvmama.ord.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.com.dao.ComLogDAO;
import com.lvmama.comm.bee.po.ord.OrdOrderItemMeta;
import com.lvmama.comm.bee.po.ord.OrdOrderTraffic;
import com.lvmama.comm.bee.po.ord.OrdOrderTrafficRefund;
import com.lvmama.comm.bee.po.ord.OrdOrderTrafficTicketInfo;
import com.lvmama.comm.bee.po.ord.OrdPerson;
import com.lvmama.comm.bee.service.ord.OrderTrafficService;
import com.lvmama.comm.pet.po.pub.ComLog;
import com.lvmama.comm.utils.PriceUtil;
import com.lvmama.comm.utils.json.ResultHandleT;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.Constant.ORDER_TRAFFIC_REFUMENT;
import com.lvmama.comm.vo.train.BaseVo;
import com.lvmama.order.dao.OrdOrderTrafficRefundDAO;
import com.lvmama.order.dao.OrderItemMetaDAO;
import com.lvmama.order.dao.OrderPersonDAO;
import com.lvmama.order.dao.impl.OrdOrderTrafficDAO;
import com.lvmama.order.dao.impl.OrdOrderTrafficTicketInfoDAO;

public class OrderTrafficServiceImpl implements OrderTrafficService {

	private OrdOrderTrafficDAO orderTrafficDAO;
	private OrderPersonDAO orderPersonDAO;
	private OrderItemMetaDAO orderItemMetaDAO;
	private OrdOrderTrafficTicketInfoDAO orderTrafficTicketInfoDAO;
	private OrdOrderTrafficRefundDAO orderTrafficRefundDAO;
	private ComLogDAO comLogDAO;
	private static final Log logger=LogFactory.getLog(OrderTrafficServiceImpl.class);
	@Override
	public ResultHandleT<Boolean> lockOrder(final String supperOrderId) {
		ResultHandleT<Boolean> handle = new ResultHandleT<Boolean>();
		
		OrdOrderTraffic traffic = orderTrafficDAO.selectByPrimarySupplierOrderId(supperOrderId);
		if(traffic==null){
			handle.setMsg("订单不存在");
			return handle;
		}
		
		if(traffic.hasCreateStatus()){//正常状态，直接更改
			handle.setReturnContent(false);
			traffic.setStatus(Constant.ORDER_TRAFFIC_STATUS.LOCK.name());
			orderTrafficDAO.updateByPrimaryKey(traffic);
		}else if(traffic.hasLockStatus()){
			handle.setReturnContent(true);//表示重复的操作,不做数据的更新
		}else{
			handle.setMsg("状态无法处理");
		}
		return handle;
	}

	@Override
	public ResultHandleT<BaseVo> ticketIssueResult(final String supplierOrderId,
			List<OrdOrderTrafficTicketInfo> list) {
		ResultHandleT<BaseVo> handle = new ResultHandleT<BaseVo>();
		OrdOrderTraffic traffic = orderTrafficDAO.selectByPrimarySupplierOrderId(supplierOrderId);
		if(traffic==null){
			handle.setMsg("订单不存在");
			handle.setReturnContent(new BaseVo(Constant.REPLY_CODE.ORDER_NOTEXIST.getRetCode(),
					Constant.REPLY_CODE.ORDER_NOTEXIST.getRetMsg()));
			return handle;
		}
		
		if(traffic.hasCreateStatus()){//正常状态，修改
			if(CollectionUtils.isNotEmpty(list)){
				traffic.setStatus(Constant.ORDER_TRAFFIC_STATUS.ISSUE.name());
			}else{
				traffic.setStatus(Constant.ORDER_TRAFFIC_STATUS.FAIL.name());
			}
			Constant.ORDER_TRAFFIC_REFUMENT refument=Constant.ORDER_TRAFFIC_REFUMENT.WITHOUT; 
			if(traffic.hasIssueStatus()){//记录处理结果
				OrdOrderItemMeta itemMeta = orderItemMetaDAO.selectByPrimaryKey(traffic.getOrderItemMetaId());
				OrdPerson person = new OrdPerson();
				person.setObjectId(itemMeta.getOrderId());
				person.setObjectType("ORD_ORDER");
				person.setPersonType(Constant.ORD_PERSON_TYPE.TRAVELLER.name());
				List<OrdPerson> persons = orderPersonDAO.getOrdPersons(person);
				long price = 0L;//单位元
				for(OrdOrderTrafficTicketInfo info:list){
					person = getPerson(persons,info);
					if(person!=null){
						info.setOrdPersonId(person.getPersonId());
					}
					price+=info.getPrice();
					info.setOrderTrafficId(traffic.getOrderTrafficId());
					orderTrafficTicketInfoDAO.insert(info);
				}
				if(itemMeta.getSettlementPriceLong()>price){
					refument = Constant.ORDER_TRAFFIC_REFUMENT.NEED_REFUMENT;
				}
			}else{
				//需要退款
				refument = Constant.ORDER_TRAFFIC_REFUMENT.NEED_REFUMENT;
			}
			if(refument!=Constant.ORDER_TRAFFIC_REFUMENT.WITHOUT){
				traffic.setRefumentStatus(refument.name());
			}
			orderTrafficDAO.updateByPrimaryKey(traffic);
			handle.setReturnContent(new BaseVo(Constant.REPLY_CODE.SUCCESS.getRetCode(),
					Constant.REPLY_CODE.SUCCESS.getRetMsg()));
		}else if(traffic.hasComplete()){
			//表示重复的操作,不做数据的更新
			handle.setReturnContent(new BaseVo(Constant.REPLY_CODE.REPEAT.getRetCode(),
					Constant.REPLY_CODE.REPEAT.getRetMsg()));
		}else{
			handle.setMsg("状态无法处理");
			handle.setReturnContent(new BaseVo(Constant.REPLY_CODE.ORDER_ABNORMAL.getRetCode(),
					Constant.REPLY_CODE.ORDER_ABNORMAL.getRetMsg()));
		}
		return handle;
	}
	
	
	@Override
	public ResultHandleT<BaseVo> addRefundInfo(String supplierOrderId,
			OrdOrderTrafficRefund refund) {
		ResultHandleT<BaseVo> handle = new ResultHandleT<BaseVo>();
		OrdOrderTraffic traffic = orderTrafficDAO.selectByPrimarySupplierOrderId(supplierOrderId);
		if(traffic==null){
			handle.setMsg("订单不存在");
			handle.setReturnContent(new BaseVo(Constant.REPLY_CODE.ORDER_NOTEXIST.getRetCode(),
					Constant.REPLY_CODE.ORDER_NOTEXIST.getRetMsg()));
			return handle;
		}
		
//		boolean isFlowExist = orderTrafficTicketInfoDAO.isFlowExistsInTickets(refund.getBillNo());
//		if(!isFlowExist){
//			handle.setMsg("还未退票，无需退款");
//			handle.setReturnContent(new BaseVo(Constant.REPLY_CODE.TICKET_DRAWBACK_NOEXIST.getRetCode(),
//					Constant.REPLY_CODE.TICKET_DRAWBACK_NOEXIST.getRetMsg()));
//			return handle;
//		}
		
		//重复操作，直接返回
		if(orderTrafficRefundDAO.selectCountByBillNo(refund.getBillNo())>0L){
			handle.setReturnContent(new BaseVo(Constant.REPLY_CODE.REPEAT.getRetCode(),
					Constant.REPLY_CODE.REPEAT.getRetMsg()));
			return handle;
		}
//		long payAmount = orderTrafficTicketInfoDAO.selectSumPriceByTraffic(traffic.getOrderTrafficId());
//		logger.info("supplierOrderId:"+supplierOrderId+"  payment:"+payAmount);
		
		//这个退款是负数，目前已经退款的总和(精确到分)
		long refundAmount = orderTrafficRefundDAO.selectSumRefundByTraffic(traffic.getOrderTrafficId());
		
		//根据退票流水号来获取该流水号实际支付的价格
		//查询的总额单位为分
		float payAmount = orderTrafficTicketInfoDAO.getPayAccountByRefundId(traffic.getOrderTrafficId());
		if((refundAmount+refund.getAmount())>payAmount*0.95){
			handle.setMsg("退款金额超出付款金额");
			handle.setReturnContent(new BaseVo(Constant.REPLY_CODE.REFUND_OUTOF_PAYMENT.getRetCode(),
					Constant.REPLY_CODE.REFUND_OUTOF_PAYMENT.getRetMsg()));
			return handle;
		}
		
		refund.setCreateTime(new Date());
		refund.setOrderTrafficId(traffic.getOrderTrafficId());
		orderTrafficRefundDAO.insert(refund);
		
		//取得单张票价格，单位分
		float ticketPrice = orderTrafficTicketInfoDAO.getTicketPriceByTrafficId(traffic.getOrderTrafficId());
		OrdOrderItemMeta ooim = null;
		if(traffic.getOrderItemMetaId() != null){
			ooim = orderItemMetaDAO.selectByPrimaryKey(traffic.getOrderItemMetaId());
		}else if(traffic.getOrderItemMetaChildId() != null){
			ooim = orderItemMetaDAO.selectByPrimaryKey(traffic.getOrderItemMetaChildId());
		}

		ComLog log = new ComLog();
		log.setParentId(ooim.getOrderId());
		log.setParentType("ORD_ORDER");
		log.setObjectType("ORD_ORDER_TRAFFIC_REFUND");
		log.setObjectId(ooim.getOrderId());
		log.setOperatorName("SYSTEM");
		log.setLogType(Constant.COM_LOG_CONTRACT_EVENT.insertOrderContract.name());
		log.setLogName("退款通知");
		log.setContent("退款" + PriceUtil.convertToYuan(refund.getAmount()) + "元，是单张车票金额的" + Math.round(refund.getAmount() / ticketPrice * 100) + "%");
		comLogDAO.insert(log);
		
		handle.setReturnContent(new BaseVo(Constant.REPLY_CODE.SUCCESS.getRetCode(),
				Constant.REPLY_CODE.SUCCESS.getRetMsg()));
		return handle;
	}

	private OrdPerson getPerson(List<OrdPerson> list,final OrdOrderTrafficTicketInfo info){
		return (OrdPerson)CollectionUtils.find(list, new Predicate() {
			
			@Override
			public boolean evaluate(Object arg0) {
				OrdPerson person = (OrdPerson)arg0;
				return StringUtils.equals(person.getCatalog(),info.getIdentity())
						&& StringUtils.equals(person.getName(), info.getName())
						&& StringUtils.equals(person.getCertNo(), info.getIdentityNo());
			}
		});
	}

	@Override
	public OrdOrderTraffic getTrafficByOrderItemMetaId(Long orderItemMetaId) {
		return orderTrafficDAO.selectByPrimaryOrderItemMetaId(orderItemMetaId);
	}

	@Override
	public void updateSupplierOrderId(Long orderTrafficId,
			String supplierOrderId) {
		OrdOrderTraffic traffic = new OrdOrderTraffic();
		traffic.setOrderTrafficId(orderTrafficId);
		traffic.setSupplierOrderId(supplierOrderId);
		orderTrafficDAO.updateByPrimaryKeySelective(traffic);
	}

	@Override
	public OrdOrderTraffic makeTrafficOrder(OrdOrderTraffic traffic) {
		traffic.setStatus(Constant.ORDER_TRAFFIC_STATUS.CREATE.name());
		traffic.setRefumentStatus(Constant.ORDER_TRAFFIC_REFUMENT.WITHOUT.name());
		traffic.setCreateTime(new Date());
		Long id=orderTrafficDAO.insert(traffic);
		traffic.setOrderTrafficId(id);
		return traffic;
	}

	@Override
	public ResultHandleT<OrdOrderTraffic> updateFailStatus(Long orderTrafficId,
			String failMessage) {
		ResultHandleT<OrdOrderTraffic> result = new ResultHandleT<OrdOrderTraffic>();
		OrdOrderTraffic traffic = orderTrafficDAO.selectByPrimaryKey(orderTrafficId);
		if(traffic==null){
			result.setMsg("订单不存在ID:"+orderTrafficId);
		}else if(traffic.hasFailStatus()){
			result.setMsg("订单已经是失败状态");
		}else{
			traffic.setRefumentStatus(Constant.ORDER_TRAFFIC_REFUMENT.NEED_REFUMENT.name());
			traffic.setStatus(Constant.ORDER_TRAFFIC_STATUS.FAIL.name());
			traffic.setFailReason(failMessage);
			orderTrafficDAO.updateByPrimaryKey(traffic);
			result.setReturnContent(traffic);
		}
		return result;	
	}

	public void setOrderTrafficDAO(OrdOrderTrafficDAO orderTrafficDAO) {
		this.orderTrafficDAO = orderTrafficDAO;
	}

	@Override
	public long getTrafficAmount(Long orderTrafficId) {
		return orderTrafficTicketInfoDAO.selectSumPriceByTraffic(orderTrafficId);
	}

	@Override
	public void updateRefumentStatus(Long orderTrafficId,
			ORDER_TRAFFIC_REFUMENT refument) {
		OrdOrderTraffic record = new OrdOrderTraffic();
		record.setOrderTrafficId(orderTrafficId);
		record.setRefumentStatus(refument.name());
		orderTrafficDAO.updateByPrimaryKeySelective(record);
	}

	@Override
	public OrdOrderTraffic getTrafficBySupplierOrderId(String supplierOrderId) {
		return orderTrafficDAO.selectByPrimarySupplierOrderId(supplierOrderId);
	}
	public void setOrderItemMetaDAO(OrderItemMetaDAO orderItemMetaDAO) {
		this.orderItemMetaDAO = orderItemMetaDAO;
	}
	public void setOrderPersonDAO(OrderPersonDAO orderPersonDAO) {
		this.orderPersonDAO = orderPersonDAO;
	}
	public void setOrderTrafficTicketInfoDAO(
			OrdOrderTrafficTicketInfoDAO orderTrafficTicketInfoDAO) {
		this.orderTrafficTicketInfoDAO = orderTrafficTicketInfoDAO;
	}
	public ComLogDAO getComLogDAO() {
		return comLogDAO;
	}
	public void setComLogDAO(ComLogDAO comLogDAO) {
		this.comLogDAO = comLogDAO;
	}
	

	public void setOrderTrafficRefundDAO(
			OrdOrderTrafficRefundDAO orderTrafficRefundDAO) {
		this.orderTrafficRefundDAO = orderTrafficRefundDAO;
	}

	@Override
	public OrdOrderTrafficRefund getTrafficRefund(Long orderTrafficId,
			String billNo) {
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("orderTrafficId", orderTrafficId);
		map.put("billNo", billNo);
		List<OrdOrderTrafficRefund> list = orderTrafficRefundDAO.selectByParam(map);
		if(list.isEmpty()){
			return null;
		}
		return list.get(0);
	}

	@Override
	public Map<String, OrdOrderTrafficTicketInfo> getAllTicketsByOrderId(
			String supplierOrderId) {
		// TODO Auto-generated method stub
		List<OrdOrderTrafficTicketInfo> tickets = orderTrafficTicketInfoDAO.getAllTicketsByOrderId(supplierOrderId);
		Map<String, OrdOrderTrafficTicketInfo> result = null;
		for(OrdOrderTrafficTicketInfo ticket : tickets){
			if(result == null)
				result = new HashMap<String, OrdOrderTrafficTicketInfo>();
			result.put(ticket.getTicketId(), ticket);
		}
		return result;
	}

	@Override
	public void updateDrawbackTicketById(String ticketIds, String flowId) {
		// TODO Auto-generated method stub
		orderTrafficTicketInfoDAO.updateDrawbackTicketById(ticketIds, flowId);
	}

	@Override
	public OrdOrderTrafficTicketInfo getTicketInfoById(Long ticketId) {
		// TODO Auto-generated method stub
		return orderTrafficTicketInfoDAO.getTicketInfoById(ticketId);
	}

	@Override
	public OrdOrderTraffic gettrafficById(Long orderTrafficId) {
		// TODO Auto-generated method stub
		return orderTrafficDAO.selectByPrimaryKey(orderTrafficId);
	}

	@Override
	public OrdOrderTrafficRefund getTrafficRefundByRefundId(Long refundId) {
		// TODO Auto-generated method stub
		return orderTrafficRefundDAO.getTrafficRefundByRefundId(refundId);
	}

	@Override
	public int getTicketNoRefundNum(Long orderTrafficId) {
		// TODO Auto-generated method stub
		return orderTrafficTicketInfoDAO.getTicketNoRefundNumById(orderTrafficId);
	}

	@Override
	public OrdOrderItemMeta getOrdItemMetaById(Long orderItemMetaId) {
		// TODO Auto-generated method stub
		return orderItemMetaDAO.selectByPrimaryKey(orderItemMetaId);
	}

	@Override
	public long selectSumRefundByTraffic(Long trafficId) {
		return orderTrafficTicketInfoDAO.selectSumPriceByTraffic(trafficId);
	}
}
