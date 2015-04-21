package com.lvmama.passport.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.lvmama.comm.bee.po.meta.MetaProduct;
import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.po.ord.OrdOrderItemMeta;
import com.lvmama.comm.bee.po.ord.OrdOrderItemMetaAperiodic;
import com.lvmama.comm.bee.po.ord.OrdOrderItemProd;
import com.lvmama.comm.bee.po.ord.OrdPerson;
import com.lvmama.comm.bee.po.pass.PassCode;
import com.lvmama.comm.bee.po.pass.PassDevice;
import com.lvmama.comm.bee.po.pass.PassEvent;
import com.lvmama.comm.bee.po.pass.PassPort;
import com.lvmama.comm.bee.po.pass.PassPortCode;
import com.lvmama.comm.bee.po.pass.PassProduct;
import com.lvmama.comm.bee.po.pass.PassProvider;
import com.lvmama.comm.bee.po.prod.ProdChannelSms;
import com.lvmama.comm.bee.po.prod.ProdProduct;
import com.lvmama.comm.bee.po.prod.ProdProductBranch;
import com.lvmama.comm.bee.po.prod.TimePrice;
import com.lvmama.comm.bee.service.pass.PassCodeService;
import com.lvmama.comm.bee.vo.Passport;
import com.lvmama.comm.bee.vo.ord.CompositeQuery;
import com.lvmama.comm.pet.po.sup.SupPerformTarget;
import com.lvmama.comm.pet.service.pay.PayPaymentService;
import com.lvmama.comm.pet.vo.Page;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.PassportConstant;
import com.lvmama.order.dao.OrderDAO;
import com.lvmama.order.dao.OrderItemMetaAperiodicDAO;
import com.lvmama.order.dao.OrderItemMetaDAO;
import com.lvmama.order.dao.OrderItemProdDAO;
import com.lvmama.order.service.Query;
import com.lvmama.passport.dao.PassCodeDAO;
import com.lvmama.passport.dao.PassDeviceDAO;
import com.lvmama.passport.dao.PassEventDAO;
import com.lvmama.passport.dao.PassPortCodeDAO;
import com.lvmama.passport.dao.PassPortDAO;
import com.lvmama.passport.dao.PassProductDAO;
import com.lvmama.passport.dao.PassProviderDAO;
import com.lvmama.prd.dao.MetaProductDAO;
import com.lvmama.prd.dao.MetaTimePriceDAO;
import com.lvmama.prd.dao.ProdChannelSmsDAO;
import com.lvmama.prd.dao.ProdProductBranchDAO;
import com.lvmama.prd.dao.ProdProductDAO;


/**
 * @author ShiHui
 */
public class PassCodeServiceImpl implements PassCodeService {
	Logger log = Logger.getLogger(PassCodeServiceImpl.class);
	private static final String ORDER_ITEM = "ORD_ORDER_ITEM_META";
	private static final String ORDER = "ORD_ORDER";
	private PassCodeDAO passCodeDAO;
	private PassPortCodeDAO passPortCodeDAO;
	private PassEventDAO passEventDAO;
	private MetaProductDAO metaProductDAO;
	private Query queryService;
	private PayPaymentService payPaymentService;
	private ProdChannelSmsDAO prodChannelSmsDAO;
	private ProdProductDAO prodProductDAO;
	private ProdProductBranchDAO prodProductBranchDAO;
	private PassProviderDAO passProviderDAO;
	private PassDeviceDAO passDeviceDAO;
	private PassPortDAO passPortDAO;
	private PassProductDAO passProductDAO;
	private OrderItemMetaAperiodicDAO orderItemMetaAperiodicDAO;
	private OrderDAO orderDAO;
	private OrderItemMetaDAO orderItemMetaDAO;
	private OrderItemProdDAO orderItemProdDAO;
	private MetaTimePriceDAO metaTimePriceDAO;
	/**
	 * 添加通关码信息
	 */
	@Override
	public Long addPassCode(PassCode passCode) {
		return passCodeDAO.addPassCode(passCode);
	}
	@Override
	public void deletePassCode(Long codeId) {
		passCodeDAO.deletePassCode(codeId);
	}

	/**
	 * 如果选择了通关点条件，进行关联查询，否则进行单表查询
	 * 
	 * @param 查询条件
	 */
	@Override
	public List<PassCode> queryPassCodes(Map<String, Object> params) {
		return passCodeDAO.selectByParams(params);
	}

	/**
	 * 查询通关点信息记录数
	 * 
	 * @param params
	 * @return
	 */
	@Override
	public Integer selectPassCodeRowCount(Map<String, Object> params) {
		return this.passCodeDAO.selectRowCount(params);
	}

	/**
	 * 通过申请流水号查询通关码信息
	 * 
	 * @param 查询参数
	 */
	@Override
	public PassCode getCodeBySerialNo(String SerialNo) {
		return this.passCodeDAO.getCodeBySerialNo(SerialNo);
	}
	@Override
	public void updatePassCode(PassCode passCode) {
		passCodeDAO.updatePassCode(passCode);
	}
 
	private Passport mockApplyPassCode(PassCode passCode) {
		Passport passport=new Passport();
		passport.setSerialno(passCode.getSerialNo());
		passport.setCode("1234567890");
		passport.setAddCode("1234567890");
		passport.setErrorNO(PassportConstant.PASSCODE_ERROR.APPLY.name());
		passport.setSendSms(PassportConstant.PASSCODE_SMS_SENDER.LVMAMA.name());
		passport.setSendOrderid(true);
		passport.setEventType(PassportConstant.PASSCODE_TYPE.APPLAYCODE.name());
		passport.setStatus(PassportConstant.PASSCODE_STATUS.SUCCESS.name());
		passport.setExtId(passCode.getSerialNo());
		return passport;
	}
	
	/**
	 * 查询通关点的提供商信息
	 * 
	 * @author clj
	 * @param codeId
	 * 
	 * @return
	 */
	@Override
	public List<PassPortCode> queryProviderByCode(Long codeId) {
		return this.passPortCodeDAO.queryProviderByCode(codeId);
	}

	/**
	 * 生成申请流水号
	 * 
	 * @return
	 */
	@Override
	public Long getPassCodeSerialNo() {
		return this.passCodeDAO.getSerialNo();
	}

	/**
	 * 通过服务商和游玩日期查询通关点信息
	 * 
	 * @param params
	 * @return
	 */
	@Override
	public List<PassCode> selectCodeByProviderIdAndValidTime(Map<String,Object> params){
		List<PassCode> passCodeList= this.passCodeDAO.selectCodeByProviderIdAndValidTime(params);
		for(PassCode passCode:passCodeList){
			List<PassPortCode> passPortList = this.passPortCodeDAO.queryProviderByCode(passCode.getCodeId());
			passCode.setPassPortList(passPortList);
		}
		return passCodeList;
		 
	}
	/**
	 * 通过组合参数查询通关码信息
	 * 
	 * @param 查询参数
	 */
	@Override
	public PassCode getPassCodeByParams(Map<String, Object> params ) {
		 return this.passCodeDAO.getPassCodeByParams(params);
	}
	/**
	 * 查询订单是否已经履行
	 * @param params
	 * @return
	 */
	@Override
	public boolean hasPassCodePerform(Map<String, Object> params ){
		return this.passCodeDAO.hasPerform(params);
	}
	
	/**
	 * 通过辅助码MD5编号查询通关码信息
	 * 
	 * @param 查询参数
	 */
	@Override
	public PassCode getCodeByAddCodeMd5(String addCodeMd5){
		return this.passCodeDAO.getCodeByAddCodeMd5(addCodeMd5);
	}
	
	/**
	 * 查询离线模式通关信息列表
	 * @param params
	 * @return
	 */
	@Override
	public List<PassCode> selectVouchersByProviderId(Map<String,Object> params){
		return this.passCodeDAO.selectVouchersByProviderId(params);
	}

	@Override
	public Integer getSyncUpdatePassCodeCount(Map<String, Object> queryOption) {
		return passCodeDAO.getSyncUpdatePassCodeCount(queryOption);
	}

	@Override
	public Integer getUpdatePassCodeByCodeId(Map<String, Object> queryOption) {
		return passCodeDAO.getUpdatePassCodeByCodeId(queryOption);
	}

	@Override
	public Integer getUpdatePassCodeBySerId(Map<String, Object> queryOption) {
		return passCodeDAO.getUpdatePassCodeBySerId(queryOption);
	}

	@Override
	public PassCode getPassCodeByOrderIdStatus(Long orderId) {
		return this.passCodeDAO.getPassCodeByOrderIdStatus(orderId);
	}

	@Override
	public void updatePassCodeBySerialNo(PassCode passCode) {
		this.passCodeDAO.updatePassCodeBySerialNo(passCode);
	}
	@Override
	public boolean successExecute(PassCode passCode, Passport passport) {
		PassEvent event = new PassEvent();
		event.setCodeId(passCode.getCodeId());
		event.setType(PassportConstant.PASSCODE_TYPE.DESTROYCODE.name());
		event.setStatus(PassportConstant.PASSCODE_STATUS.SUCCESS.name());
		passEventDAO.updateEventStauts(event);
		
		// 设置废码状态
		passCode.setStatus(PassportConstant.PASSCODE_DESTROY_STATUS.DESTROYED.name());
		passCode.setUpdateTime(new Date());
		passCode.setStatusNo(null);
		passCode.setStatusExplanation(null);
		passCodeDAO.updatePassCode(passCode);
		
		//通关码全部废掉
		PassPortCode passPortCode =new PassPortCode();
		passPortCode.setCodeId(passCode.getCodeId());
		passPortCode.setStatus(PassportConstant.PASSCODE_USE_STATUS.DESTROYED.name());
		passPortCodeDAO.updatePassPortCodeByCodeId(passPortCode);
		return true;
	}

	@Override
	public Page<PassCode> selectPassCodeAutoPerform(Map parameterObject) {
		return this.passCodeDAO.selectAutoPerform(parameterObject);
	}

	@Override
	public PassCode getPassCodeByCodeId(Long codeId) {
		return this.passCodeDAO.getPassCodeByCodeId(codeId);
	}

	@Override
	public PassCode getPassCodeByEventId(Long eventId) {
		return this.passCodeDAO.getCodeByEventId(eventId);
	}
 
	@Override
	public PassEvent destroyCode(Long codeId) {
		PassCode passCode = passCodeDAO.getPassCodeByCodeId(codeId);
		PassEvent event = new PassEvent();
		event.setCodeId(passCode.getCodeId());
		event.setType(Constant.PASSCODE_TYPE.DESTROYCODE.name());
		event.setStatus(Constant.PASSCODE_HANDL_STATUS.NOHANDL.name());
		//TODO 以后扩展对通关点废码处理时需要此参数
		//event.setOutPortId(Long.valueOf(this.passport.getOutPortId()));
		event.setApplyTime(new Date());
		passEventDAO.addEvent(event);
		return event;
	}
	@Override
	public List<PassCode> applyCodeForOrder(OrdOrder order,Map<Long,List<SupPerformTarget>> supPerformTargetMap) {
		/**
		 * 凭证短信优先发给游玩人，游玩人不存在，才发送给联系人
		 */
		//添加百付逻辑判断,如果存在百付支付的手机号，则下发短信和传给供应商的手机号为
		//百付手机号，否则走原来的逻辑
		String paymentGateway = Constant.PAYMENT_GATEWAY.TELBYPAY.name();
		String mobile=payPaymentService.selectPayMobileNumByPaymentOrderNoAndPaymentGateway(order.getOrderId(),paymentGateway);
		OrdPerson contact = null;
		if (!order.getTravellerList().isEmpty() && null != order.getTravellerList().get(0).getMobile()) {
			contact = order.getTravellerList().get(0);
		} else {
			contact = order.getContact();
		}
		if(!StringUtils.equals(mobile,"") && mobile!=null){
			contact.setMobile(mobile);
		}
		
		Set<PassProvider> providerSet = new HashSet<PassProvider>();
		Map<PassProvider, List<OrdOrderItemMeta>> providerItemMetaMap = new HashMap<PassProvider, List<OrdOrderItemMeta>>();
		Map<OrdOrderItemMeta, List<SupPerformTarget>> itemMetaPerformMap = new HashMap<OrdOrderItemMeta, List<SupPerformTarget>>();
		Map<Long,SupPerformTarget> targetMap = new HashMap<Long, SupPerformTarget>();//去除重复
		for (OrdOrderItemProd itemProd : order.getOrdOrderItemProds()) {//该订单下的所有产品子项(销售产品)
			for (OrdOrderItemMeta itemMeta : itemProd.getOrdOrderItemMetas()) {
				List<SupPerformTarget> targets = supPerformTargetMap.get(itemMeta.getMetaProductId());
				for (SupPerformTarget supPerformTarget : targets) {
					if (Constant.CCERT_TYPE.DIMENSION.name().equals(supPerformTarget.getCertificateType())) {
						PassProvider provider = passProviderDAO.selectByPerformTargetId(supPerformTarget.getTargetId());
						if (provider!=null) {
							providerSet.add(provider);
							List<SupPerformTarget> performTargetList = itemMetaPerformMap.get(itemMeta);
							if (performTargetList==null) {
								performTargetList = new ArrayList<SupPerformTarget>();
								performTargetList.add(supPerformTarget);
								itemMetaPerformMap.put(itemMeta, performTargetList);
								targetMap.put(supPerformTarget.getTargetId(), supPerformTarget);
							}else{
								if(targetMap.get(supPerformTarget.getTargetId()) == null){
									performTargetList.add(supPerformTarget);
									targetMap.put(supPerformTarget.getTargetId(), supPerformTarget);
								}
							}
							List<OrdOrderItemMeta> metaList = providerItemMetaMap.get(provider);
							if (metaList==null) {
								metaList = new ArrayList<OrdOrderItemMeta>();
								metaList.add(itemMeta);
								providerItemMetaMap.put(provider, metaList);
							}else{
								metaList.add(itemMeta);
							}
						}
					}
				}
			}
		}
		CompositeQuery compositeQuery = new CompositeQuery();
		compositeQuery.getPageIndex().setBeginIndex(0);
		compositeQuery.getPageIndex().setEndIndex(1000000000);
		List<PassCode> passCodeList = new ArrayList<PassCode>();
		boolean isAperiodic = order.IsAperiodic();
		//Set<PassProvider> providerSet = map.keySet();
		long codeTotal=0;//要申请码的数量
		for (PassProvider passProvider : providerSet) {
			if (passProvider.isSeparatelyApply()||order.hasSelfPack()) {	//服务商对每个订单子子项独立申码
				List<OrdOrderItemMeta> list = providerItemMetaMap.get(passProvider);
				for (OrdOrderItemMeta itemMeta : list) {
					codeTotal=codeTotal+1;
					PassCode code = apply(contact, order.getOrderId(),itemMeta.getOrderItemMetaId(), ORDER_ITEM);
					List<SupPerformTarget> perfomTargetList = itemMetaPerformMap.get(itemMeta);
					for (SupPerformTarget supPerformTarget : perfomTargetList) {
						compositeQuery.getMetaPerformRelate().setTargetId(String.valueOf(supPerformTarget.getTargetId()));
						compositeQuery.getMetaPerformRelate().setOrderId(order.getOrderId());
						List<OrdOrderItemMeta> orderItemMetas = this.queryService.compositeQueryOrdOrderItemMetaByMetaPerformRelate(compositeQuery);
						String terminalContent = mergePrintContent(contact, order, orderItemMetas,passProvider);
						//不定期,单独处理
						if(isAperiodic) {
							OrdOrderItemProd orderItemProd = orderItemProdDAO.selectByPrimaryKey(orderItemMetas.get(0).getOrderItemId());
							if(orderItemProd != null) {
								initPassPortCode(code, supPerformTarget.getTargetId(), orderItemProd.getValidBeginTime(), null, terminalContent, orderItemProd.getValidEndTime(),orderItemProd.getInvalidDate(),orderItemProd.getInvalidDateMemo());
							}
						} else {
							initPassPortCode(code, supPerformTarget.getTargetId(), itemMeta.getVisitTime(), itemMeta.getValidDays(), terminalContent, null, null, null);
						}
					}
					if(passProvider.isSendSmsByProvider()){
						this.mergeSms(code, order);
					}
					passCodeList.add(code);
				}
			}else{//合并申码
				codeTotal=codeTotal+1;
				PassCode code = apply(contact,order.getOrderId(), order.getOrderId(), ORDER);
				List<OrdOrderItemMeta> list = providerItemMetaMap.get(passProvider);
				Set<Long>perfomTargetSet=new HashSet<Long>();
				for (OrdOrderItemMeta itemMeta : list) {
					List<SupPerformTarget> perfomTargetList = itemMetaPerformMap.get(itemMeta);
					for (SupPerformTarget supPerformTarget : perfomTargetList) {
							perfomTargetSet.add(supPerformTarget.getTargetId());
					}
				}
				for (Long targetId : perfomTargetSet) {
					compositeQuery.getMetaPerformRelate().setTargetId(String.valueOf(targetId));
					compositeQuery.getMetaPerformRelate().setOrderId(order.getOrderId());
					List<OrdOrderItemMeta> orderItemMetas = this.queryService.compositeQueryOrdOrderItemMetaByMetaPerformRelate(compositeQuery);
					String terminalContent = mergePrintContent(contact, order, orderItemMetas,passProvider);
					//不定期,单独处理
					if(isAperiodic) {
						OrdOrderItemProd orderItemProd = orderItemProdDAO.selectByPrimaryKey(orderItemMetas.get(0).getOrderItemId());
						if(orderItemProd != null) {
							initPassPortCode(code, targetId, orderItemProd.getValidBeginTime(), null, terminalContent, orderItemProd.getValidEndTime(),orderItemProd.getInvalidDate(),orderItemProd.getInvalidDateMemo());
						}
					} else {
						Map<String, Object> map = calcVisitTimeAndValidDays(orderItemMetas);
						initPassPortCode(code, targetId, (Date)map.get("visitTime"), (Long)map.get("validDays"), terminalContent, null,null,null);
					}
				}
				if(passProvider.isSendSmsByProvider()){
					this.mergeSms(code, order);
				}
				passCodeList.add(code);
			}
		}
		//设置订单申请码的个数
		this.passCodeDAO.updateCodeTotalByOrder(order.getOrderId(), codeTotal);
		return passCodeList;
	}
	
	private void initPassPortCode(PassCode passCode, Long targetId, Date visitTime, Long validDays, String terminalContent, Date validEndTime, String invalidDate, String invalidDateMemo) {
		PassPortCode passPortCode = new PassPortCode();
		passPortCode.setTargetId(targetId);
		passPortCode.setStatus(Constant.PASSCODE_USE_STATUS.UNUSED.name());
		passPortCode.setCodeId(passCode.getCodeId());
		passPortCode.setValidTime(DateUtil.getDayStart(visitTime));
		if(visitTime != null) {
			if(validDays != null) {
				Date invalidTime = DateUtil.getDateAfterDays(DateUtil.getDayStart(visitTime), validDays.intValue());
				passPortCode.setInvalidTime(invalidTime);
			} else if(validEndTime != null) {
				passPortCode.setInvalidTime(validEndTime);
			}
		}
		passPortCode.setTerminalContent(terminalContent);
		//增加不可游玩日期信息
		if(StringUtils.isNotEmpty(invalidDate) && StringUtils.isNotEmpty(invalidDateMemo)) {
			passPortCode.setInvalidDate(invalidDate);
			passPortCode.setInvalidDateMemo(invalidDateMemo);
		}
		// 添加通关码关联信息
		passPortCodeDAO.addPassPortCode(passPortCode);
	}

	private Map<String, Object> calcVisitTimeAndValidDays(List<OrdOrderItemMeta> itemMetas) {
		Date visitTime = null;
		Long validDays = null;
		for (OrdOrderItemMeta itemMeta : itemMetas) {
			Long tempValidDays = itemMeta.getValidDays();
			Long tempDateLong = itemMeta.getVisitTime().getTime();
			if (visitTime==null) {
				visitTime=itemMeta.getVisitTime();
			}
			if (validDays==null) {
				validDays=tempValidDays;
			}
			if (tempDateLong < visitTime.getTime()) {
				visitTime=itemMeta.getVisitTime();
			}
			if (tempValidDays > validDays) {
				validDays = tempValidDays;
			}
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("visitTime", visitTime);
		map.put("validDays", validDays);
		return map;
	}
	/**
	 * 组合短信
	 * @return
	 */
	private void mergeSms(PassCode passCode,OrdOrder order){
		ProdChannelSms template =prodChannelSmsDAO.selectByTemplateIdAndChannelCode(order.getChannel(), Constant.SMS_TEMPLATE.DIEM_PAYMENT_SUCC.name());
		if(template != null){
			String smsContent = template.getContent();
			String visitDate = null;
			if(order.getVisitTime() != null) {
				visitDate = DateUtil.getFormatDate(order.getVisitTime(), "yyyy-MM-dd");
			} else {
				visitDate = "";
			}
			smsContent = StringUtils.replace(smsContent, "${orderId}", "订单号:"+order.getOrderId().toString());
			smsContent = StringUtils.replace(smsContent, "${visitDate}", visitDate);
			smsContent = StringUtils.replace(smsContent, "${addCode}", "");
			String latestUseTime="";
			if(order.hasTodayOrder()){
				latestUseTime = "最早换票时间："+DateUtil.formatDate(order.getVisitTime(), "HH:mm")+",最晚换票时间"+DateUtil.getFormatDate(order.getLatestUseTime(), "HH:mm");
			}
			smsContent = StringUtils.replace(smsContent, "${latestUseTime}", latestUseTime);
			smsContent = StringUtils.replace(smsContent, "${code}", "");
			smsContent = StringUtils.replace(smsContent, "${productQuantityContent}", getAduletAndChild(passCode.getCodeId(), order));
			
			//设置最晚取消时间，如果没有最晚取消时间,就不给此取消的提示
			smsContent = StringUtils.replace(smsContent, "${time}", order.getLastCancelStr());
			
			String content="";
			List<OrdOrderItemProd> items = this.getOrdOrderItemProds(order);
			 
			if (passCode.isForOrder()) {
				for (OrdOrderItemProd ordOrderItemProd : items) {
					if (ordOrderItemProd.isNeedSendSms()) {
						ProdProduct product = prodProductDAO.selectByPrimaryKey(ordOrderItemProd.getProductId());
						if (content!=null) {
							content = content + "," + product.getSmsContent();
						}
					}
				}
			}else if (passCode.isForOrderItemMeta()){
				OrdOrderItemProd ordOrderItemProd = null;
				for(OrdOrderItemProd orderItemProd:items){
					List<OrdOrderItemMeta> ordOrderItemMetas=orderItemProd.getOrdOrderItemMetas();
					 for(OrdOrderItemMeta OrdOrderItemMeta:ordOrderItemMetas){
						 if(passCode.getObjectId().longValue()==OrdOrderItemMeta.getOrderItemMetaId().longValue()){
							
							 ordOrderItemProd=orderItemProd;
							  break;
						 }
					 }
				}
				if (ordOrderItemProd != null && ordOrderItemProd.isNeedSendSms()) {
					ProdProduct product = prodProductDAO.selectByPrimaryKey(ordOrderItemProd.getProductId());
					if (content!=null) {
						content = content + "," + product.getSmsContent();
					}
				}
			}
			
			smsContent = StringUtils.replace(smsContent, "${content}", content);
			
			log.info("Passport SmsContent:"+smsContent);
			
			PassCode temp=new PassCode();
			temp.setCodeId(passCode.getCodeId());
			temp.setSmsContent(smsContent);
			passCodeDAO.updatePassCode(temp);
		}
	}
	
	/**
	 * 
	 * @param order
	 * @return
	 */
	private List<OrdOrderItemProd> getOrdOrderItemProds(OrdOrder order){
		Map<Long, OrdOrderItemProd> items = new HashMap<Long, OrdOrderItemProd>();
		List<OrdOrderItemProd> prodList = new ArrayList<OrdOrderItemProd>();
		for (OrdOrderItemProd prod : order.getOrdOrderItemProds()) {
			if(items.get(prod.getProductId()) == null){
				items.put(prod.getProductId(), prod);
				prodList.add(prod);
			}
		}
		return prodList;
	}
	
	/**
	 * 查找指定采购产品在订单里的订购数，成人数，和儿童数
	 * @param itemMetas
	 * @return
	 */
	private  String getAduletAndChild(Long codeId, OrdOrder order){
		List<PassPortCode> passPortCodelist=passPortCodeDAO.searchPassPortByCodeId(codeId);
		String targetIdStr = "";
		for(PassPortCode	passPortCode : passPortCodelist) {
			targetIdStr = targetIdStr + passPortCode.getTargetId() + ",";
		}
		String targetId = targetIdStr.substring(0, targetIdStr.lastIndexOf(","));
		
		
		CompositeQuery compositeQuery = new CompositeQuery();
		compositeQuery.getPageIndex().setBeginIndex(0);
		compositeQuery.getPageIndex().setEndIndex(1000000000);
		compositeQuery.getMetaPerformRelate().setTargetId(targetId);
		compositeQuery.getMetaPerformRelate().setOrderId(order.getOrderId());
		List<OrdOrderItemMeta> orderItemMetas = this.queryService.compositeQueryOrdOrderItemMetaByMetaPerformRelate(compositeQuery);
		Set<Long> resultOrdItemId = new HashSet<Long>(); 
		for (OrdOrderItemMeta ordItemMeta : orderItemMetas) {
			resultOrdItemId.add(ordItemMeta.getOrderItemId());
		}
		
		
		List<OrdOrderItemProd> ordOrderItemProd = order.getOrdOrderItemProds();
		long adult = 0;
		long child = 0;
		for (OrdOrderItemProd ordItemProd: ordOrderItemProd) {
			for (Long ordItemId : resultOrdItemId) {
				if(ordItemProd.getOrderItemProdId().longValue() == ordItemId.longValue()) {
					ProdProductBranch prodProductBranch = prodProductBranchDAO.selectByPrimaryKey(ordItemProd.getProdBranchId());
					adult = adult + ordItemProd.getQuantity() * prodProductBranch.getAdultQuantity();
					child = child + ordItemProd.getQuantity() * prodProductBranch.getChildQuantity();
				}
			}
		}
		
		
		StringBuilder productName=new StringBuilder();
		log.info("orderItemMetas Info : size="+orderItemMetas.size()+",isStudent="+orderItemMetas.get(0).isStudent());
		if(orderItemMetas!=null&&orderItemMetas.size()==1&&orderItemMetas.get(0).isStudent()){
			productName.append("学生人数："+(adult+child));
		}else{
			productName.append("包含人数："+adult+"成人，"+child+"儿童");
		}
		log.info("getAduletAndChild:"+productName.toString());
		return productName.toString();
	}
	
	/**
	 * 显示内容
	 * 
	 * @param person
	 * @param itemMeta
	 * @param itemProd
	 * @return
	 */
	public String mergePrintContent(OrdPerson person, OrdOrder order, List<OrdOrderItemMeta> itemMetas,PassProvider passProvider) {
		MetaProduct product = metaProductDAO.getMetaProductByPk(itemMetas.get(0).getMetaProductId());
		String content = product.getTerminalContent();
		log.info("mergePrintContent content:"+content);
		StringBuilder productNameBuf = new StringBuilder();
		long adult = 0;
		long child = 0;
		long student=0;
		float price=0;
		int size=itemMetas.size();
		boolean isStudent=false;
		for (OrdOrderItemMeta itemMeta : itemMetas) {
			String productName = itemMeta.getProductName();
			price=itemMeta.getSellPriceToYuan();
			if(itemMeta.isStudent()){
				isStudent=true;
				student=student+itemMeta.getTotalAdultQuantity()+itemMeta.getTotalChildQuantity();
			}else{
				adult=adult+itemMeta.getTotalAdultQuantity();
				child=child+itemMeta.getTotalChildQuantity();
			}
			
			if(passProvider.isGmedia()){
				int len = productName.length();
				if (len > 11) {
					productName = productName.substring(0, 11) + "\n" + productName.substring(11);
				}
			}
			productNameBuf.append(productName + ",");
			
		}
		log.info("person.getCertNo:"+person.getCertNo());
		if(StringUtils.isNotBlank(person.getCertNo())){
			content = StringUtils.replace(content,"${certNo}", person.getCertNo());	
		}else{
			content = StringUtils.replace(content,"\n身份证:${certNo}","");	
		}
		content = StringUtils.replace(content, "${orderId}", order.getOrderId().toString());
		content = StringUtils.replace(content, "${productName}", productNameBuf.toString());
		//只有一个产品是显示单价
		if(size==1){
			content = StringUtils.replace(content, "${price}", String.valueOf(price));
		}else{
			content = StringUtils.replace(content, "\n单价:${price}元", "");
		}
		//只有一种学生票的销售产品
		if(size==1&&isStudent){
			content = StringUtils.replace(content, "${studentQuantity}", "" +student);
			content = StringUtils.replace(content, "成人:${adultQuantity}人", "");
			content = StringUtils.replace(content, "儿童:${childQuantity}人", "");
		//含有学生票
		}else if(isStudent){
			content = StringUtils.replace(content, "${adultQuantity}", "" + adult);
			content = StringUtils.replace(content, "${childQuantity}", "" + child);
			content = StringUtils.replace(content, "${studentQuantity}", "" +student);
		}else{
			content = StringUtils.replace(content, "${adultQuantity}", "" + adult);
			content = StringUtils.replace(content, "${childQuantity}", "" + child);
			content = StringUtils.replace(content, "\n学生：${studentQuantity}人", "");
		}
		
		if(!order.isPayToLvmama()){
			content = StringUtils.replace(content, "${OrderPrice}", "" + order.getOughtPayYuan());
		}else{
			content = StringUtils.replace(content, "${OrderPrice}", "--" );
		}
		content = StringUtils.replace(content, "${clientName}", person.getName());
		content = StringUtils.replace(content, "${clientMobile}", person.getMobile());
		log.info("mergePrintContent info:"+content);
		return content;
	}
		
	private PassCode apply(OrdPerson contact,Long orderId, Long objectId, String objectType) {
		String serialNo = DateUtil.getFormatDate(new Date(), "yyyyMMdd") + passCodeDAO.getSerialNo();
		// 通关码
		PassCode passCode = new PassCode();
		passCode.setCreateTime(new Date());
		passCode.setObjectId(objectId);
		passCode.setObjectType(objectType);
		passCode.setReapply(Constant.PASSCODE_REAPPLY_STATUS.FALSE.name());
		passCode.setMobile(contact.getMobile());
		passCode.setOrderId(orderId);
		passCode.setSerialNo(serialNo);
		passCode.setStatus(Constant.PASSCODE_HANDL_STATUS.NOHANDL.name());
		passCode.setReapplyCount(0l);
		this.passCodeDAO.addPassCode(passCode);
		return passCode;
	}
	
	public Long reApplyCode(Long codeId) {
		PassCode passCode = passCodeDAO.getPassCodeByCodeId(codeId);
		passCode.setReapply(Constant.PASSCODE_REAPPLY_STATUS.TRUE.name());
		passCode.setStatusExplanation("");
		passCodeDAO.updatePassCode(passCode);
		
		String serialNo = DateUtil.getFormatDate(new Date(), "yyyyMMdd") + passCodeDAO.getSerialNo();
		passCode.setSerialNo(serialNo);
		passCode.setCodeId(null);
		passCode.setStatus("NOHANDL");
		passCode.setCreateTime(new Date());
		passCode.setUpdateTime(new Date());
		passCode.setStatusNo(null);
		passCode.setStatusExplanation(null);
		passCode.setReapplyCount(passCode.getReapplyCount()+1);
		passCode.setCodeTotal(passCode.getCodeTotal());
		Long newCodeId = passCodeDAO.addPassCode(passCode);
		List<PassPortCode> list = passPortCodeDAO.queryPassPortCodes(codeId);
		for (PassPortCode passPortCode : list) {
			passPortCode.setCodeId(newCodeId);
			passPortCodeDAO.addPassPortCode(passPortCode);
		}
		return newCodeId;
	}
	
	public void reApplyCodeUseSameSerialNo(Long codeId){
		PassCode passCode = passCodeDAO.getPassCodeByCodeId(codeId);
		passCode.setReapplyCount(passCode.getReapplyCount()+1);
		passCodeDAO.updatePassCode(passCode);
	}
	
	/**
	 * 废码处理
	 * 
	 * @param orderId
	 */
	public List<PassEvent> destroyCode(OrdOrder order) {
		List<PassCode> list = passCodeDAO.getPassCodeByOrderId(order.getOrderId());
		List<PassEvent> eventList = new ArrayList<PassEvent>();
		for (PassCode passCode : list) {
			PassEvent event = new PassEvent();
			event.setCodeId(passCode.getCodeId());
			event.setType(Constant.PASSCODE_TYPE.DESTROYCODE.name());
			event.setStatus(Constant.PASSCODE_HANDL_STATUS.NOHANDL.name());
			//TODO 以后扩展对通关点废码处理时需要此参数
			//event.setOutPortId(Long.valueOf(this.passport.getOutPortId()));
			event.setApplyTime(new Date());
			passEventDAO.addEvent(event);
			eventList.add(event);
		}
		return eventList;
	}

	/**
	 * 更新订单联系人
	 */
	public List<PassEvent> updateContact(OrdOrder order) {
		List<PassCode> list = passCodeDAO.getPassCodeByOrderId(order.getOrderId());
		List<PassEvent> eventList = new ArrayList<PassEvent>();
		for (PassCode passCode : list) {
			PassEvent event = new PassEvent();
			event.setCodeId(passCode.getCodeId());
			event.setType(Constant.PASSCODE_TYPE.UPDATECONTACT.name());
			event.setStatus(Constant.PASSCODE_HANDL_STATUS.NOHANDL.name());
			event.setTerminalContent(order.getContact().getCertNo());
			event.setApplyTime(new Date());
			passEventDAO.addEvent(event);
			eventList.add(event);
		}
		return eventList;
	}
	/**
	 * 重发短信
	 * @param order
	 * @return
	 */
	public List<PassEvent> resend(OrdOrder order){
		List<PassCode> list = passCodeDAO.getPassCodeByOrderId(order.getOrderId());
		List<PassEvent> eventList = new ArrayList<PassEvent>();
		for (PassCode passCode : list) {
			PassEvent event = new PassEvent();
			event.setCodeId(passCode.getCodeId());
			event.setType(Constant.PASSCODE_TYPE.RESEND.name());
			event.setStatus(Constant.PASSCODE_HANDL_STATUS.NOHANDL.name());
			event.setApplyTime(new Date());
			passEventDAO.addEvent(event);
			eventList.add(event);
		}
		return eventList;
	}
	/**
	 * 重发短信
	 * @param codeId
	 * @return
	 */
	public PassEvent resend(Long codeId) {
		PassCode passCode = passCodeDAO.getPassCodeByCodeId(codeId);
		PassEvent event = new PassEvent();
		event.setCodeId(passCode.getCodeId());
		event.setType(Constant.PASSCODE_TYPE.RESEND.name());
		event.setStatus(Constant.PASSCODE_HANDL_STATUS.NOHANDL.name());
		event.setApplyTime(new Date());
		passEventDAO.addEvent(event);
		return event;
	}
	
	/**
	 * 通过时间查询重新申请码记录
	 * @param params
	 * @return
	 */
	public List<PassCode> selectByReapplyTime() {
		return this.passCodeDAO.selectByReapplyTime();
	}
	public List<PassEvent> updateContent(OrdOrder order) {
		return null;
	}

	public List<PassEvent> updatePerson(OrdOrder order) {
		return null;
	}

	/**
	 * 查询已经支付了但是没有收到支付消息的订单
	 * @return
	 */
	public List<Object> selectReapplyOrder(){
		return this.passCodeDAO.selectReapplyOrder();
	}
	
	/**
	 * 判断订单或订单子指项是否已经申请成功过码
	 * @param params
	 * @return
	 */
	public boolean hasSuccessCode(Long orderId,long codeTotal ){
		Map<String,Object> params=new HashMap<String,Object>();
		params.put("orderId",orderId);
		int successCodeNum=passCodeDAO.successCodeNum(params);
		if(successCodeNum>=codeTotal){
			return true;
		}else{
			return false;
		}
	}
	/**
	 * 订单是否已经做过申请
	 * @param orderId
	 * @return
	 */
	public boolean hasApply(Long orderId){
		Map<String,Object> params=new HashMap<String,Object>();
		params.put("orderId", orderId);
		List<PassCode> passCodeList = passCodeDAO.selectPassCodeByParams(params);
		if(passCodeList!=null&&passCodeList.size()>0){
			return true;
		}else{
		    return false;
		}
	}
	

	/**
	 * 添加设备编号信息
	 * 
	 * @param passDevice
	 * @return
	 */
	public Long addPaasDevice(PassDevice passDevice) {
		return passDeviceDAO.addPaasDevice(passDevice);
	}

	/**
	 * 修改设备编号信息
	 * 
	 * @param passDevice
	 * @return
	 */
	public int updatePaasDevice(PassDevice passDevice) {
		return passDeviceDAO.updatePaasDevice(passDevice);
	}

	/**
	 * 通过设备编号和服务商编号查询设备编号信息
	 * 
	 * @param deviceNo
	 * @param providerId
	 * @return
	 */
	public PassDevice getPaasDeviceByDeviceNoAndProviderId(String deviceNo,
			Long providerId) {
		return passDeviceDAO.getPaasDeviceByDeviceNoAndProviderId(deviceNo,
				providerId);
	}
	/**
	 * 查询设备编号信息
	 * @param param
	 * @return
	 */
	public List<PassDevice> searchPassDevice(Map<String,Object> param){
		return passDeviceDAO.searchPassDevice(param);
	}
	
	public int searchPassDeviceCount(Map<String,Object> param){
		return passDeviceDAO.searchPassDeviceCount(param);
	}
	
	/**
	 * 通过设备编号查询设备编号信息
	 * @param deviceId
	 * @return
	 */
	public PassDevice getPaasDeviceByDeviceId(Long deviceId){
		 return this.passDeviceDAO.getPaasDeviceByDeviceId(deviceId);
	}
	
	/**
	 * 通过设备编号删除
	 * 
	 * @param deviceId
	 * @return
	 */
	public int delDeviceByDeviceId(Long deviceId){
		return this.passDeviceDAO.delDeviceByDeviceId(deviceId);
	}

	public void addEvent(PassEvent event) {
		passEventDAO.addEvent(event);
	}

	public List<PassEvent> queryEvents(Map<String, Object> params) {
		return passEventDAO.selectByParams(params);
	}

	public void updateEvent(PassEvent event) {
		passEventDAO.updateEvent(event);
	}

	@Override
	public void updateEventStauts(PassEvent event) {
		this.passEventDAO.updateEventStauts(event);
	}

	@Override
	public PassEvent selectPassEventByEventId(Long eventId) {
		return this.passEventDAO.selectByEventId(eventId);
	}
	@Override
	public List<PassPortCode> queryPassPortCodes(Long codeId) {
		return passPortCodeDAO.queryPassPortCodes(codeId);
	}
	@Override
	public boolean selectPassPortCodeByPortId(Long portId) {
		return passPortCodeDAO.selectByPortId(portId);
	}
	/**
	 * 添加通关码关联信息
	 * @param passPortCodes
	 */
	@Override
	public void addPassPortCodes(PassPortCode passPortCode){
		   this.passPortCodeDAO.addPassPortCode(passPortCode);
	}
	/**
	 * 修改
	 * */
	@Override
	public void updatePassPortCode(PassPortCode passPortCode){
		this.passPortCodeDAO.updatePassPortCode(passPortCode);
	}
	/**
	 * 查询通关点关联信息
	 * @param codeId
	 * @return
	 */
	@Override
	public PassPortCode getPassPortCodeByCodeIdAndPortId(Long codeId,Long portId){
		return this.passPortCodeDAO.getPassPortCodeByCodeIdAndPortId(codeId, portId);
	}
 

	@Override
	public PassPortCode getPassPortCodeByObjectIdAndTargetId(
			List<Long> objectId, Long targetId) {
		return this.passPortCodeDAO.getPassPortCodeByObjectIdAndTargetId(objectId, targetId);
	}

	@Override
	public void updatePassPortCode(PassCode passCode, Passport data) {
		PassPortCode passPortCode = new PassPortCode();
		passPortCode.setCodeId(passCode.getCodeId());
		passPortCode.setTargetId(data.getPortId());
		passPortCode.setStatus(PassportConstant.PASSCODE_USE_STATUS.USED.name());
		if(data.getUsedDate()!=null){
			passPortCode.setUsedTime(data.getUsedDate());
		}else{
			passPortCode.setUsedTime(new Timestamp(new Date().getTime()));
		}
		// 更新通关点信息
		passPortCodeDAO.updatePassPortCode(passPortCode);
		//使用码成功，更新码的修改时间
		PassCode temp=new PassCode();
		temp.setUpdateTime(new Date());
		temp.setCodeId(passCode.getCodeId());
		passCodeDAO.updatePassCode(temp);
		OrdOrder order = orderDAO.selectByPrimaryKey(passCode.getOrderId());
		//不定义更新对应的订单项的游玩日期
		if(order.IsAperiodic()) {
			updateOrderVisitTime(order,passPortCode);
		}
	}
	
	/**
	 * 修改不定期订单相应的信息(游玩日期、密码券使用时间)
	 * 
	 * @author shihui
	 * */
	private void updateOrderVisitTime(OrdOrder order, PassPortCode passPortCode) {
		Date usedTime = passPortCode.getUsedTime();
		CompositeQuery compositeQuery = new CompositeQuery();
		compositeQuery.getPageIndex().setBeginIndex(0);
		compositeQuery.getPageIndex().setEndIndex(1000000000);
		compositeQuery.getMetaPerformRelate().setTargetId(String.valueOf(passPortCode.getTargetId()));
		compositeQuery.getMetaPerformRelate().setOrderId(order.getOrderId());
		List<OrdOrderItemMeta> orderItemMetas = this.queryService.compositeQueryOrdOrderItemMetaByMetaPerformRelate(compositeQuery);
		for (OrdOrderItemMeta orderItemMeta : orderItemMetas) {
			//更新对应密码券的使用时间
			OrdOrderItemMetaAperiodic aperiodic = orderItemMetaAperiodicDAO.selectOrderAperiodicByOrderItemId(orderItemMeta.getOrderItemMetaId());
			if(aperiodic != null) {
				aperiodic.setActivationStatus(Constant.APERIODIC_ACTIVATION_STATUS.ACTIVATED.name());
				aperiodic.setUsedTime(usedTime);
				orderItemMetaAperiodicDAO.updateStatusByPrimaryKey(aperiodic);
			}
			//更新订单子子项的游玩日期
			OrdOrderItemMeta ordItemMeta = orderItemMetaDAO.selectByPrimaryKey(orderItemMeta.getOrderItemMetaId());
			orderItemMeta.setVisitTime(usedTime);
			//取游玩当天的结算价放入订单子子项
			TimePrice timePrice = metaTimePriceDAO.getMetaTimePriceByIdAndDate(orderItemMeta.getMetaBranchId(), DateUtil.getDayStart(usedTime));
			if(timePrice != null) {
				ordItemMeta.setSettlementPrice(timePrice.getSettlementPrice());
				ordItemMeta.setActualSettlementPrice(timePrice.getSettlementPrice());
				ordItemMeta.setTotalSettlementPrice(ordItemMeta.getSettlementPrice()*ordItemMeta.getQuantity()*ordItemMeta.getProductQuantity());
			}
			orderItemMetaDAO.updateByPrimaryKey(ordItemMeta);
			
			//更新订单子项游玩日期
			OrdOrderItemProd orderItemProd = orderItemProdDAO.selectByPrimaryKey(orderItemMeta.getOrderItemId());
			if(orderItemProd.getVisitTime()!= null) {} else{
				orderItemProd.setVisitTime(usedTime);
				orderItemProdDAO.updateByPrimaryKey(orderItemProd);
			}
		}
		if(order.getVisitTime() != null) {} else {
			order.setVisitTime(usedTime);
			orderDAO.updateByPrimaryKey(order);
		}
	}
	@Override
	public void addPassPort(PassPort passPort) {
		passPortDAO.addPassPort(passPort);
	}
	@Override
	public void deletePassPort(Long portId) {
		passPortDAO.deletePassPort(portId);
	}
	@Override
	public List<PassPort> queryPassPorts(Map<String, Object> params) {
		return passPortDAO.selectByParams(params);
	}
	
	
	@Override
	public void updatePassPort(PassPort passPort) {
		passPortDAO.updatePassPort(passPort);
	}
	/**
	 * 通过业务系统编号查询通关点信息
	 * @param outPortId
	 * @return
	 */
	@Override
	public PassPort getPassPortByOutPortId(Long outPortId){
		return this.passPortDAO.getPassPortByOutPortId(outPortId);
	}
	/**
	 * 通过名称查询通关点信息
	 * @param params
	 * @return
	 */
	@Override
	public List<PassPort> getPassportByName(Map<String, String> params){
		return this.passPortDAO.getPassportByName(params);
	}
	/**
	 * 通过编号查询通关点信息
	 * @param portId
	 * @return
	 */
	@Override
	public PassPort getPassportByPortId(Long portId){
		return this.passPortDAO.getPassportByPortId(portId);
	}
	@Override
	public int queryPassPortCount(Map<String, Object> params) {
		return this.passPortDAO.queryPassPortCount(params);
	}
	/**
	 * 查询通关点信息
	 * 
	 * @param params
	 * @return
	 */
	@Override
	public List<PassCode> selectPassCodeByParams(Map<String, Object> params) {
		return this.passCodeDAO.selectPassCodeByParams(params);
	}
	 
	/**
	 * 通过通关点编号查询通关点关联信息
	 * 
	 * @param CodeId
	 * @return
	 */
	@Override
	public List<PassPortCode> searchPassPortByCodeId(Long CodeId) {
		return this.passPortCodeDAO.searchPassPortByCodeId(CodeId);
	}
	
	@Override
	public List<PassPortCode> searchPassPortByOrderId(Long orderId){
		return this.passPortCodeDAO.searchPassPortByOrderId(orderId);
	}
	
	@Override
	public void deletePassProduct(Long passProdId) {
		passProductDAO.deletePassProduct(passProdId);
		
	}
	

	@Override
	public void insertPassProduct(PassProduct passProduct) {
		passProductDAO.insertPassProduct(passProduct);
	}

	@Override
	public List<PassProduct> queryPassProduct(Map<String,Object> params) {
		return passProductDAO.queryPassProduct(params);
		
	}

	@Override
	public void updatePassProduct(PassProduct passProduct) {
		passProductDAO.updatePassProduct(passProduct);
		
	}

	public void updatePassPortPerform(Long ordId){
		List<PassPortCode> passPortCodes = searchPassPortByOrderId(ordId);
		if(CollectionUtils.isNotEmpty(passPortCodes)){
			for(PassPortCode ppc:passPortCodes){
				PassCode passCode = new PassCode();
				passCode.setCodeId(ppc.getCodeId());
				passCode.setOrderId(ordId);
				
				Passport data = new Passport();
				data.setUsedDate(new Date());
				data.setPortId(ppc.getTargetId());
				
				updatePassPortCode(passCode,data);
			}
		}
	}
	
	
	@Override
	public Integer selectPassProductRowCount(Map<String, Object> queryOption) {
		return passProductDAO.selectRowCount(queryOption);
	}

	@Override
	public PassProduct selectPassProductByParams(Map<String, Object> params) {
		return this.passProductDAO.selectPassProductByParams(params);
	}

	@Override
	public void addPassProvider(PassProvider passProvider) {
		passProviderDAO.addPassProvider(passProvider);
	}
	@Override
	public void deletePassProvider(PassProvider passProvider) {
		passProviderDAO.deletePassProvider(passProvider);
	}
	@Override
	public List<PassProvider> queryPassProviders(Map<String, Object> params) {
		return passProviderDAO.selectByParams(params);
	}
	@Override
	public void updatePassProvider(PassProvider passProvider) {
		passProviderDAO.updatePassProvider(passProvider);
	}
	@Override
	public List<PassCode> getPassCodeByAddCode(String addCode) {
		return passCodeDAO.getPassCodeByAddCode(addCode);
	}
	
	public void setPassCodeDAO(PassCodeDAO passCodeDAO) {
		this.passCodeDAO = passCodeDAO;
	}
	public void setPassPortCodeDAO(PassPortCodeDAO passPortCodeDAO) {
		this.passPortCodeDAO = passPortCodeDAO;
	}
	public void setPassEventDAO(PassEventDAO passEventDAO) {
		this.passEventDAO = passEventDAO;
	}
	public void setMetaProductDAO(MetaProductDAO metaProductDAO) {
		this.metaProductDAO = metaProductDAO;
	}
	public void setQueryService(Query queryService) {
		this.queryService = queryService;
	}
	public void setProdChannelSmsDAO(ProdChannelSmsDAO prodChannelSmsDAO) {
		this.prodChannelSmsDAO = prodChannelSmsDAO;
	}
	public void setProdProductDAO(ProdProductDAO prodProductDAO) {
		this.prodProductDAO = prodProductDAO;
	}
	public void setProdProductBranchDAO(ProdProductBranchDAO prodProductBranchDAO) {
		this.prodProductBranchDAO = prodProductBranchDAO;
	}
	public void setPassProviderDAO(PassProviderDAO passProviderDAO) {
		this.passProviderDAO = passProviderDAO;
	}
	public void setPassDeviceDAO(PassDeviceDAO passDeviceDAO) {
		this.passDeviceDAO = passDeviceDAO;
	}
	public void setPassPortDAO(PassPortDAO passPortDAO) {
		this.passPortDAO = passPortDAO;
	}
	public void setPassProductDAO(PassProductDAO passProductDAO) {
		this.passProductDAO = passProductDAO;
	}
	
	@Override
	public List<PassPortCode> queryPassPortCodeByParam(PassPortCode passPortCode) {
		return passPortCodeDAO.queryPassPortCodeByParam(passPortCode);
	}
	@Override
	public List<PassCode> getPassCodeByOrderIdStatusList(Long orderId) {
		return this.passCodeDAO.getPassCodeByOrderIdStatusList(orderId);
	}
	@Override
	public List<PassCode> getPassCodeByOrderIdAndTargetIdList(Long orderId,
			Long targetId) {
		return passCodeDAO.getPassCodeByOrderIdAndTargetIdList(orderId, targetId);
	}
	@Override
	public boolean checkCodeHasExisting(String codeType,String code) {
		return passCodeDAO.checkCodeHasExisting(codeType,code);
	}
	@Override
	public boolean destoryCode(String outStance,String destoryStatus) {
		return passCodeDAO.destoryCode(outStance,destoryStatus);
	}
	@Override
	public List<PassDevice> getDeviceListByCode(String addCode,String currentUdid) {
		return passDeviceDAO.getPassDeviceListByCode(addCode,currentUdid);
	}
	@Override
	public List<String> getAddCodesByEBK(Map params) {
		return passCodeDAO.getAddCodesByEBK(params);
	}

	@Override
	public SupPerformTarget getPerformTargetByEBK(Map params) {
		return passDeviceDAO.getPerformTargetListByEBK(params);
	}
	@Override
	public List<PassCode> queryPassCodeByParam(Map<String, Object> params) {
		return passCodeDAO.queryPassCodeByParam(params);
	}
	
	@Override
	public List<PassDevice> getDeviceListByProviderId(Long providerId) {
		return passDeviceDAO.getDeviceListByProviderId(providerId);
	}
	@Override
	public boolean addCodeExisting(String addCode) {
		return passCodeDAO.addCodeExisting(addCode);
	}
	@Override
	public Object addCodeIsInTarget(Map<String, Object> param) {
		return passCodeDAO.addCodeIsInTarget(param);
	}
	
	public void updatePassPortCodeByCodeId(PassPortCode passPortCode) {
		passPortCodeDAO.updatePassPortCodeByCodeId(passPortCode);
	}
	public void setOrderItemMetaAperiodicDAO(
			OrderItemMetaAperiodicDAO orderItemMetaAperiodicDAO) {
		this.orderItemMetaAperiodicDAO = orderItemMetaAperiodicDAO;
	}
	public void setOrderDAO(OrderDAO orderDAO) {
		this.orderDAO = orderDAO;
	}
	public void setOrderItemMetaDAO(OrderItemMetaDAO orderItemMetaDAO) {
		this.orderItemMetaDAO = orderItemMetaDAO;
	}
	public void setOrderItemProdDAO(OrderItemProdDAO orderItemProdDAO) {
		this.orderItemProdDAO = orderItemProdDAO;
	}
	/**
	 * 通过ORDERID取得数据
	 * @param orderId
	 * @return
	 */
	public List<PassProvider> selectPassProviderByOrderId(Long orderId){
		return this.passProviderDAO.selectByOrderId(orderId);
	}
	public void setMetaTimePriceDAO(MetaTimePriceDAO metaTimePriceDAO) {
		this.metaTimePriceDAO = metaTimePriceDAO;
	}
	
	@Override
	public List<PassProvider> selectByDeviceNo(String deviceNo) {
		return this.passProviderDAO.selectByDeviceNo(deviceNo);
	}
	@Override
	public List<PassPortCode> selectAllMergeSmsByParams(Map params) {
		return this.passPortCodeDAO.selectAllMergeSmsByParams(params);
	}
	public void setPayPaymentService(PayPaymentService payPaymentService) {
		this.payPaymentService = payPaymentService;
	}
	
	
	@Override
	public List<PassCode> getPassCodeBysupplierId(Map<String,Object> params){
		List<PassCode> passCodeList= this.passCodeDAO.getPassCodeBysupplierId(params);
		for(PassCode passCode:passCodeList){
			List<PassPortCode> passPortList = this.passPortCodeDAO.queryProviderByCode(passCode.getCodeId());
			passCode.setPassPortList(passPortList);
		}
		return passCodeList;
		 
	}
	@Override
	public List<PassCode> selectPassCodeListByOrderIdAndStatus(
			Map<String, Object> params) {
		return passCodeDAO.selectPassCodeListByOrderIdAndStatus(params);
	}
}

