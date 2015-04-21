package com.lvmama.order.service.proxy;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.log4j.Logger;

import com.lvmama.comm.bee.po.eplace.EbkUser;
import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.po.ord.OrdOrderItemMeta;
import com.lvmama.comm.bee.po.ord.OrdOrderItemMetaAperiodic;
import com.lvmama.comm.bee.po.ord.OrdOrderItemProd;
import com.lvmama.comm.bee.po.ord.OrdPerform;
import com.lvmama.comm.bee.po.pass.PassCode;
import com.lvmama.comm.bee.po.pass.PassPortCode;
import com.lvmama.comm.bee.po.prod.TimePrice;
import com.lvmama.comm.bee.service.eplace.EbkUserService;
import com.lvmama.comm.bee.service.ord.OrderItemMetaAperiodicService;
import com.lvmama.comm.bee.service.ord.OrderPerformService;
import com.lvmama.comm.bee.service.pass.PassCodeService;
import com.lvmama.comm.bee.vo.ord.CompositeQuery;
import com.lvmama.comm.bee.vo.ord.OrdOrderPerformResourceVO;
import com.lvmama.comm.bee.vo.ord.PerformDetail;
import com.lvmama.comm.pet.po.sup.SupPerformTarget;
import com.lvmama.comm.pet.service.favor.FavorService;
import com.lvmama.comm.pet.service.pub.ComLogService;
import com.lvmama.comm.pet.service.sup.PerformTargetService;
import com.lvmama.comm.pet.vo.Page;
import com.lvmama.comm.pet.vo.favor.FavorProductResult;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.utils.json.ResultHandle;
import com.lvmama.comm.vo.Constant;
import com.lvmama.order.dao.OrderDAO;
import com.lvmama.order.dao.OrderItemMetaDAO;
import com.lvmama.order.dao.OrderItemProdDAO;
import com.lvmama.prd.dao.MetaTimePriceDAO;


/**
 * 
 * com.lvmama.order.service.proxy.OrderPerformProxy
 */
public final class OrderPerformProxy implements OrderPerformService {
	protected Logger log = Logger.getLogger(this.getClass());
	
	private OrderServiceProxy orderServiceProxy;
	private PassCodeService passCodeService;
	private PerformTargetService performTargetService;
	private ComLogService comLogService;
	private EbkUserService ebkUserService;
	private OrderPerformService orderPerformService;
	private OrderItemMetaAperiodicService orderItemMetaAperiodicService;
	private OrderItemMetaDAO orderItemMetaDAO;
	private OrderItemProdDAO orderItemProdDAO;
	private OrderDAO orderDAO;
	private MetaTimePriceDAO metaTimePriceDAO;
	private FavorService favorService;
	
	@Override
	public List<OrdOrderPerformResourceVO> queryOrderPerformByEBK(Map<String, Object> para) {
		return orderPerformService.queryOrderPerformByEBK(para);
	}
	
	@Override
	public ResultHandle perform(String addCode, String udid, Long[] quantity, Long[] orderItemMetaId, String remark, Long ebkUserId,Date performTime) {
		Map<String,Object> ordParams = new HashMap<String,Object>();
		ordParams.put("udid", udid);
		ordParams.put("addCode", addCode);
		List<OrdOrderPerformResourceVO> ordOrderPerformResourceVO = orderPerformService.queryOrderPerformByEBK(ordParams);
		if(ordOrderPerformResourceVO != null && ordOrderPerformResourceVO.size() < 1){
			ResultHandle msgHandle = new ResultHandle();
			msgHandle.isFail();
			msgHandle.setMsg("没有找到履行信息udid="+udid+",addCode="+addCode);
			return msgHandle;
		}
		Long orderId = ordOrderPerformResourceVO.get(0).getOrderId();
		
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("udid", udid);
		params.put("addCode", addCode);
		SupPerformTarget target = passCodeService.getPerformTargetByEBK(params);
		Long targetId = target.getTargetId();
		
		if(orderItemMetaId != null){
			if(isItemMetasNotInOrder(orderItemMetaId,orderId)){
				ResultHandle msgHandle = new ResultHandle();
				msgHandle.isFail();
				msgHandle.setMsg("履行信息异常");
				return msgHandle;
			}
		}
		
		return perform(orderId,targetId,quantity,orderItemMetaId,remark,ebkUserId,performTime);
	}
	
	@Override
	public ResultHandle perform(Long orderId,Long targetId,Long[] quantity,Long[] orderItemMetaId,String remark,Long ebkUserId,Date performTime) {
		boolean flag=false;
		boolean isPassport=false;
		
		ResultHandle msgHandle = new ResultHandle();
		
		OrdOrder ordOrder = null;
		if(orderId==null || targetId==null){
			msgHandle.setMsg("通关参数传输错误!");
			return msgHandle;
		}
		try {
			ordOrder = orderServiceProxy.queryOrdOrderByOrderId(orderId);
			CompositeQuery compositeQuery = new CompositeQuery();
			compositeQuery.getMetaPerformRelate().setTargetId(String.valueOf(targetId));
			compositeQuery.getMetaPerformRelate().setOrderId(ordOrder.getOrderId());
			compositeQuery.getPageIndex().setBeginIndex(0);
			compositeQuery.getPageIndex().setEndIndex(10000);
			List<OrdOrderItemMeta> orderItemMetas = orderServiceProxy.compositeQueryOrdOrderItemMetaByMetaPerformRelate(compositeQuery);
			String msg=this.doCheck(ordOrder,orderItemMetas,orderId,targetId);
			if(msg!=null&&!"".equals(msg)){
				msgHandle.setMsg(msg);
				return msgHandle;
			}
			if(orderItemMetaId == null || orderItemMetaId.length < 1) {
				orderItemMetaId = new Long[orderItemMetas.size()];
				quantity = new Long[orderItemMetas.size()];
				for(int i = 0; i < orderItemMetaId.length; i++) {
					quantity[i] = orderItemMetas.get(i).getQuantity();
					orderItemMetaId[i] = orderItemMetas.get(i).getOrderItemMetaId();
				}
			}
			EbkUser user = ebkUserService.getEbkUserById(ebkUserId);
			//通关处理
			flag=this.addItemMetaPerform(ordOrder, orderItemMetas, orderItemMetaId, quantity, targetId, remark,user.getName());
			
			if(flag){
				for(OrdOrderItemMeta itemMeta:orderItemMetas){
					List<SupPerformTarget> supPerformTarget=performTargetService.findSuperSupPerformTargetByMetaProductId(itemMeta.getMetaProductId());	
					if (Constant.CCERT_TYPE.DIMENSION.name().equals(
							supPerformTarget.get(0).getCertificateType())) {
						isPassport = true;
						break;
					}
				}
				if (isPassport) {
					// 二维码信息更新
					List<PassCode> passCodeLst=passCodeService.getPassCodeByOrderIdAndTargetIdList(ordOrder.getOrderId(), targetId);
					if(CollectionUtils.isNotEmpty(passCodeLst)){
						Date curDate = new Timestamp(new Date().getTime());
						for(PassCode passCode:passCodeLst){
							PassPortCode tempPassPortCode=new PassPortCode();
							tempPassPortCode.setCodeId(passCode.getCodeId());
							tempPassPortCode.setTargetId(targetId);
							List<PassPortCode> passPortCodeList=passCodeService.queryPassPortCodeByParam(tempPassPortCode);
							for(PassPortCode passPortCode:passPortCodeList){
								passPortCode.setStatus(Constant.PASSCODE_USE_STATUS.USED.name());
								if(performTime!=null){
									//实际通关（刷码）时间,由客户端传回客户端本地通关的时间
									passPortCode.setUsedTime(performTime);
								}else{
									passPortCode.setUsedTime(curDate);
								}
								// 更新通关点信息
								passCodeService.updatePassPortCode(passPortCode);
							}
						}
						updateOrderVisitTime(ordOrder.getOrderId(), orderItemMetas, performTime!=null?performTime:curDate);
					}
				} 
			}
		} catch (Exception e) {
			log.info("port/passInfo():passport fail to ["+orderId+"]!"+e.getMessage());
			msgHandle.setMsg("服务器内部错误,请联系管理员!");
			return msgHandle;
		}
		return msgHandle;
	}
	
	/**
	 * 修改不定期订单相应的信息(游玩日期、密码券使用时间)
	 * 
	 * @author shihui
	 * */
	private void updateOrderVisitTime(Long orderId, List<OrdOrderItemMeta> orderItemMetas, Date usedTime) {
		OrdOrder order = orderServiceProxy.queryOrdOrderByOrderId(orderId);
		if(order.getVisitTime() != null) {} else {
			order.setVisitTime(usedTime);
			orderDAO.updateByPrimaryKey(order);
		}
		//更新产品优惠后的采购产品子项
		List<FavorProductResult> favorProductResultList = favorService.getFavorMetaProductResultByOrderInfo(order);
		for (OrdOrderItemMeta orderItemMeta : orderItemMetas) {
			//更新对应密码券的使用时间
			OrdOrderItemMetaAperiodic aperiodic = orderItemMetaAperiodicService.selectOrderAperiodicByOrderItemMetaId(orderItemMeta.getOrderItemMetaId());
			if(aperiodic != null) {
				aperiodic.setActivationStatus(Constant.APERIODIC_ACTIVATION_STATUS.ACTIVATED.name());
				aperiodic.setUsedTime(usedTime);
				orderItemMetaAperiodicService.updateStatusByPrimaryKey(aperiodic);
			}
			
			//更新订单子项游玩日期
			OrdOrderItemProd orderItemProd = orderItemProdDAO.selectByPrimaryKey(orderItemMeta.getOrderItemId());
			if(orderItemProd.getVisitTime()!= null) {} else{
				orderItemProd.setVisitTime(usedTime);
				orderItemProdDAO.updateByPrimaryKey(orderItemProd);
			}
			
			//更新订单子子项的游玩日期
			OrdOrderItemMeta ordItemMeta = orderItemMetaDAO.selectByPrimaryKey(orderItemMeta.getOrderItemMetaId());
			TimePrice tp = metaTimePriceDAO.getMetaTimePriceByIdAndDate(ordItemMeta.getMetaBranchId(), DateUtil.getDayStart(usedTime));
			if(tp != null) {
				orderItemMeta.setSettlementPrice(tp.getSettlementPrice());
				orderItemMeta.setActualSettlementPrice(orderItemMeta.getSettlementPrice());
				orderItemMeta.setTotalSettlementPrice(orderItemMeta.getActualSettlementPrice()*orderItemMeta.getQuantity()*orderItemMeta.getProductQuantity());
			}
			orderItemMeta.setVisitTime(usedTime);
			
			if(CollectionUtils.isNotEmpty(favorProductResultList)) {
				//计算优惠后的结算价
				for(int i = 0; i < favorProductResultList.size(); i++){//遍历，获取对应的优惠策略结果，然后先计算出优惠后的结算价，总结算价
					FavorProductResult favorProductResult = favorProductResultList.get(i);
					if(orderItemMeta.getMetaProductId().equals(favorProductResult.getProductId()) 
							&& orderItemMeta.getMetaBranchId().equals(favorProductResult.getProductBranchId())){
						Long discountAmount = favorProductResult.getDiscountAmount(orderItemMeta, 0, true);
						//设置优惠后的采购销售价
						orderItemMeta.setActualSettlementPrice(orderItemMeta.getSettlementPrice() -  discountAmount/ (orderItemMeta.getQuantity() * orderItemMeta.getProductQuantity()));
						//设置优惠后的采购总销售价
						orderItemMeta.setTotalSettlementPrice(orderItemMeta.getActualSettlementPrice()*orderItemMeta.getQuantity()*orderItemMeta.getProductQuantity());
					}
				}
			}
			orderItemMetaDAO.updateByPrimaryKey(ordItemMeta);
		}
	}

	/**
	 * 通关验证
	 * 
	 * @param win
	 * @throws Exception
	 */
	private String doCheck(OrdOrder ordOrder,List<OrdOrderItemMeta> orderItemMetas,Long orderId,Long targetId) throws Exception {
		String msg="";
		//是否是二维码
		boolean flag = false;
		for(OrdOrderItemMeta itemMeta:orderItemMetas){
			List<SupPerformTarget> supPerformTarget=performTargetService.findSuperSupPerformTargetByMetaProductId(itemMeta.getMetaProductId());	
			if (Constant.CCERT_TYPE.DIMENSION.name().equals(
					supPerformTarget.get(0).getCertificateType())) {
				flag = true;
				break;
			}
		}
		if (flag) {
			// 二维码
			List<PassCode> passCodeLst=passCodeService.getPassCodeByOrderIdAndTargetIdList(ordOrder.getOrderId(), targetId);
			if(CollectionUtils.isNotEmpty(passCodeLst)){
				msg =  this.valid(passCodeLst.get(0).getCodeId(), targetId);
				if (msg == null) {
					if(ordOrder==null||ordOrder.isCanceled()){
						msg="订单:" + orderId + "不存在或者已经被取消";
						return msg;
					}
				}else{
					return msg;
				}
			}else{
				msg="此凭证不存在";
				return msg;
			}
		} else {
			try{
				if(ordOrder==null||ordOrder.isCanceled()){
					msg="订单:" + orderId + "不存在或者已经被取消";
					return msg;
				}
				/*取消订单未到游玩时间不能通关的限制
				Long visitTime=ordOrder.getVisitTime().getTime();
				long toDay =DateUtil.toDate(DateFormatUtils.format(new Date(), "yyyy-MM-dd"),"yyyy-MM-dd").getTime();
				if(toDay<visitTime&&ordOrder.isPayToLvmama()){
					msg="订单:" + orderId + "未到游玩日期";
					return msg;
				}*/
				boolean isPerform=false;
				//兼容旧数据-rxq
				if(orderItemMetas.size()>1){
					List<OrdPerform> ordPerformList=orderServiceProxy.queryOrdPerformByOrderId(orderId);
					if(CollectionUtils.isNotEmpty(ordPerformList)){
						isPerform=true;
					}
				}
				for(OrdOrderItemMeta itemMeta:orderItemMetas)
				{
					OrdPerform ordPerform=orderServiceProxy.queryOrdPerformByOrderItemMetaId(itemMeta.getOrderItemMetaId());
					if(ordPerform!=null){
						isPerform=true;
					}
				}
				if(isPerform){
					msg="订单:" + orderId + "已经通关过,不能重复通关";
					return msg;
				} 
			}catch(Exception e){
				msg="订单:" + orderId + "不存在或者已经被取消";
				return msg;
			}
		}
		return msg;
	}
	
	/**
	 * 二维码验证
	 * 
	 * @param codeId
	 * @param targetId
	 * @return
	 */
	private String valid(Long codeId, Long targetId) {
		PassPortCode tempPassPortCode=new PassPortCode();
		tempPassPortCode.setCodeId(codeId);
		tempPassPortCode.setTargetId(targetId);
		List<PassPortCode> passPortCodeList=passCodeService.queryPassPortCodeByParam(tempPassPortCode);
		String status = "0";
		String msg = null;
		if (CollectionUtils.isNotEmpty(passPortCodeList)) {
			PassPortCode passPortCode=passPortCodeList.get(0);
			long toDay = DateUtil.toDate(DateFormatUtils.format(new Date(), "yyyy-MM-dd"), "yyyy-MM-dd").getTime();
			long validTime = passPortCode.getValidTime().getTime();
			long invalidTime = passPortCode.getInvalidTime().getTime();
			boolean isValid = (toDay >= validTime && toDay <= invalidTime) ? true : false;
			boolean validateInvalidDate = passPortCode.validateInvalidDate();
			status = passPortCode.getStatus().trim();
			// 码没有被使用
			if (Constant.PASSCODE_USE_STATUS.UNUSED.name().equals(status) && isValid && validateInvalidDate) {
				msg = null;
			} else if (Constant.PASSCODE_USE_STATUS.DESTROYED.name().equals(status)) {
				msg = "凭证已经作废";
			} /*else if (!isValid) {
				if (toDay < validTime) {
					msg = "凭证还未到游玩日期";
				} else {
					msg = "凭证已经过期";
				}*/
			else if(!validateInvalidDate){
				msg = "今日不可游玩";
			}
			else if (Constant.PASSCODE_USE_STATUS.USED.name().equals(status)) {
				msg = "凭证在此景点已经使用过，不能重复使用";
			}
		} else {
			msg = "凭证在不能在此景点使用";
		}
		return msg;
	}
	
	/**
	 * 添加履行信息
	 * @param ordOrder
	 * @param orderItemMetas
	 * @param orderItemMetaId
	 * @param portQuantity
	 * @param targetId
	 * @param remark
	 * @return
	 */
	private boolean addItemMetaPerform(OrdOrder ordOrder,List<OrdOrderItemMeta> orderItemMetas, Long[] orderItemMetaId, Long[] portQuantity, Long targetId,String remark,String userName) {
		log.info("Eplace pass by "+Constant.OBJECT_TYPE.ORD_ORDER_ITEM_META.name()+":ORDER_ID="+ordOrder.getOrderId()+", targetId="+targetId+
				", orderItemMetas.size="+ orderItemMetas.size() +", portQuantity="+portQuantity);
		for(OrdOrderItemMeta orderItemMeta:orderItemMetas){
			Long quantity = findQuantity(orderItemMetaId, portQuantity, orderItemMeta.getOrderItemMetaId());
			boolean result = orderServiceProxy.insertOrdPerform(targetId, orderItemMeta.getOrderItemMetaId(), Constant.OBJECT_TYPE.ORD_ORDER_ITEM_META.name(), quantity * orderItemMeta.getTotalAdultProductQuantity(), quantity * orderItemMeta.getTotalChildProductQuantity(), remark);
			if(!result) {
				return false;
			}
			comLogService.insert(Constant.OBJECT_TYPE.ORD_ORDER_ITEM_META.name(), ordOrder.getOrderId(), orderItemMeta.getOrderItemMetaId(), userName,
					Constant.EBookingBizType.EPLACE.name(), "通关操作", ("已通关"+(StringUtil.isNotEmptyString(remark)?remark:"")), Constant.OBJECT_TYPE.ORD_ORDER.name());
		}
		return true;
	}
	
	/**
	 * 
	 * @author: ranlongfei 2013-2-27 下午10:02:01
	 * @param orderItemMetaId
	 * @param portQuantity
	 * @param id
	 */
	private Long findQuantity(Long[] orderItemMetaId, Long[] portQuantity, Long id) {
		for(int i = 0; i < orderItemMetaId.length; i++) {
			if(id.equals(orderItemMetaId[i])) {
				return portQuantity[i];
			}
		}
		return 0L;
	}


	public OrderServiceProxy getOrderServiceProxy() {
		return orderServiceProxy;
	}


	public void setOrderServiceProxy(OrderServiceProxy orderServiceProxy) {
		this.orderServiceProxy = orderServiceProxy;
	}


	public PassCodeService getPassCodeService() {
		return passCodeService;
	}


	public void setPassCodeService(PassCodeService passCodeService) {
		this.passCodeService = passCodeService;
	}


	public PerformTargetService getPerformTargetService() {
		return performTargetService;
	}


	public void setPerformTargetService(PerformTargetService performTargetService) {
		this.performTargetService = performTargetService;
	}


	public ComLogService getComLogService() {
		return comLogService;
	}


	public void setComLogService(ComLogService comLogService) {
		this.comLogService = comLogService;
	}


	public EbkUserService getEbkUserService() {
		return ebkUserService;
	}

	public void setEbkUserService(EbkUserService ebkUserService) {
		this.ebkUserService = ebkUserService;
	}

	@Override
	public Long insertOrdPerform(Long performTargetId, Long objectId,
			String objectType, Long adultQuantity, Long childQuantity,
			String memo) {
		throw new RuntimeException("not implements.");
	}

	@Override
	public boolean autoPerform(Long orderItemMetaId, Long performTargetId) {
		return this.orderPerformService.autoPerform(orderItemMetaId, performTargetId);
	}

	@Override
	public boolean checkAllPerformed(Long orderId) {
		return this.orderPerformService.checkAllPerformed(orderId);
	}
	
	@Override
	public List<PerformDetail> getOrderPerformDetail(List<Long> orderItemMetaIds) {
		return this.orderPerformService.getOrderPerformDetail(orderItemMetaIds);
	}
	
	@Override
	public Page<OrdOrderPerformResourceVO> queryOrderPerformByPage(
			Long pageSize, Long currentPage, List<Long> metaBranchIds,
			Map<String, Object> para) {
		return this.orderPerformService.queryOrderPerformByPage(pageSize, currentPage, metaBranchIds, para);
	}

	public void setOrderPerformService(OrderPerformService orderPerformService) {
		this.orderPerformService = orderPerformService;
	}

	@Override
	public boolean isItemMetasNotInOrder(Long[] orderItemMetaId, Long orderId) {
		return orderPerformService.isItemMetasNotInOrder(orderItemMetaId, orderId);
	}

	public void setOrderItemMetaAperiodicService(
			OrderItemMetaAperiodicService orderItemMetaAperiodicService) {
		this.orderItemMetaAperiodicService = orderItemMetaAperiodicService;
	}

	public void setOrderItemMetaDAO(OrderItemMetaDAO orderItemMetaDAO) {
		this.orderItemMetaDAO = orderItemMetaDAO;
	}

	public void setOrderItemProdDAO(OrderItemProdDAO orderItemProdDAO) {
		this.orderItemProdDAO = orderItemProdDAO;
	}

	public void setOrderDAO(OrderDAO orderDAO) {
		this.orderDAO = orderDAO;
	}

	public void setMetaTimePriceDAO(MetaTimePriceDAO metaTimePriceDAO) {
		this.metaTimePriceDAO = metaTimePriceDAO;
	}

	public void setFavorService(FavorService favorService) {
		this.favorService = favorService;
	}
	
}
