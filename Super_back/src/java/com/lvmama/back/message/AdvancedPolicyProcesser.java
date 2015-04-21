package com.lvmama.back.message;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.comm.bee.po.insurance.InsPolicyInfo;
import com.lvmama.comm.bee.po.meta.MetaProduct;
import com.lvmama.comm.bee.po.meta.MetaProductOther;
import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.po.ord.OrdOrderItemMeta;
import com.lvmama.comm.bee.po.ord.OrdPerson;
import com.lvmama.comm.bee.service.insurance.PolicyInfoService;
import com.lvmama.comm.bee.service.insurance.PolicyOperationLogService;
import com.lvmama.comm.bee.service.meta.MetaProductService;
import com.lvmama.comm.bee.service.ord.OrderService;
import com.lvmama.comm.jms.Message;
import com.lvmama.comm.jms.MessageProcesser;
import com.lvmama.comm.jms.TopicMessageProducer;
import com.lvmama.comm.pet.po.ins.InsInsurant;
import com.lvmama.comm.pet.po.ins.InsPolicyOperationLog;
import com.lvmama.comm.utils.IdentityCardUtil;
import com.lvmama.comm.vo.Constant;

/**
 * 预保单生成监听器
 * 逻辑：监听订单的支付消息。当订单支付完成并订单处于非取消状态情况下，查找订单子项中的保险产品，每一项保险产品
 * 生成一份预保单
 * @author Brian
 *
 */
public class AdvancedPolicyProcesser implements MessageProcesser {
	private static final Log LOG = LogFactory.getLog(AdvancedPolicyProcesser.class);
	
	private OrderService orderServiceProxy;
	private MetaProductService metaProductService;
	private PolicyInfoService policyInfoService;
	private PolicyOperationLogService policyOperationLogService;
	private TopicMessageProducer policyMessageProducer;
	
	@Override
	public void process(Message message) {
		if ( message.isOrderPaymentMsg()){
			OrdOrder order=orderServiceProxy.queryOrdOrderByOrderId(message.getObjectId());
			if (order.isPaymentSucc() && !order.isCanceled()) {
				List<OrdOrderItemMeta> list = order.getAllOrdOrderItemMetas();
				//查找保险类的销售产品
				for (OrdOrderItemMeta meta : list) {
					if (Constant.PRODUCT_TYPE.OTHER.name().equalsIgnoreCase(meta.getProductType())) {
						MetaProduct metaProduct = metaProductService.getMetaProduct(meta.getMetaProductId(), Constant.PRODUCT_TYPE.OTHER.name());
						if (metaProduct!=null&&Constant.SUB_PRODUCT_TYPE.INSURANCE.name().equalsIgnoreCase(metaProduct.getSubProductType())) {
							generatePolicy(order, meta, (MetaProductOther) metaProduct, message.getAddition());
						}
					}
				}	
			}
		}
		
		if (message.isOrderCancelMsg()) {
			Map<String,Object> parameters = new HashMap<String,Object>();
			parameters.put("orderId",message.getObjectId());
			List<InsPolicyInfo> policies = policyInfoService.query(parameters);
			for (InsPolicyInfo policy : policies) {
				cancelPolicy(policy);
			}
		}
	}
	
	/**
	 * 产生预订单
	 * @param order 订单
	 * @param meta 订单子项
	 * @param metaProductOther 销售产品
	 */
	private synchronized void generatePolicy(final OrdOrder order,
			final OrdOrderItemMeta meta, final MetaProductOther metaProductOther,final String operatorName) {
		if (null == order || null == meta || null == metaProductOther) {
			LOG.warn("OrdOrder, OrdOrderItemMeta or MetaProductOther is null!");
		}
		
		Map<String, Object> parm = new HashMap<String, Object>();
		parm.put("orderId", order.getOrderId());
		parm.put("metaProductId", meta.getMetaProductId());
		if (policyInfoService.countInsPolicyInfo(parm) > 0) {
			LOG.info("订单" + order.getOrderId() + "关于" + meta.getProductName()+ "的预保单已经生产过，无需在重复产生!");
			return;
		}

		InsPolicyInfo policy = new InsPolicyInfo();
		policy.setOrderId(order.getOrderId());
		policy.setOrderUserId(order.getUserId());
		policy.setMetaProductId(meta.getMetaProductId());
		policy.setMetaProductName(meta.getProductName());
		policy.setProductIdSupplier(meta.getProductIdSupplier());
		policy.setMetaProductQuantity(meta.getProductQuantity() * meta.getQuantity());
		policy.setProductTypeSupplier(meta.getProductTypeSupplier());
		policy.setActualSettlementPrice(meta.getActualSettlementPrice());
		policy.setInsuranceDay(metaProductOther.getInsuranceDay());
		policy.setSupplierId(meta.getSupplierId());
		policy.setEffectiveDate(order.getVisitTime());
		policy.setPolicyStatus(Constant.POLICY_STATUS.UNVERIFIED.name());
		policy.setManual("N");
		policy.setValid("Y");

		List<OrdPerson> ordPersons = order.getTravellerList();
		List<InsInsurant> insInsurants = new ArrayList<InsInsurant>();

		if (null != ordPersons && !ordPersons.isEmpty()) {
			OrdPerson person = ordPersons.get(0);
			InsInsurant insInsurant = new InsInsurant();
			insInsurant.setOrderId(order.getOrderId());
			insInsurant.setName(person.getName());
			insInsurant.setCertType(person.getCertType());
			insInsurant.setCertNo(person.getCertNo());
			insInsurant.setMobileNumber(person.getMobile());
			insInsurant.setPersonType(Constant.POLICY_PERSON.APPLICANT.name());
			
			if (null != person.getCertNo() && IdentityCardUtil.verify(person.getCertNo())) {
				insInsurant.setBirthday(IdentityCardUtil.getDate(person.getCertNo()));
				insInsurant.setSex(IdentityCardUtil.getSex(person.getCertNo()));
			} else {
				//其它证件类型则直接从person中将出生日期copy到insInsurant对象中
				insInsurant.setBirthday(person.getBrithday());
				insInsurant.setSex(person.getGender());
			}
			
			insInsurants.add(insInsurant);
		}

		for (OrdPerson person : ordPersons) {
			if (Constant.ORD_PERSON_TYPE.TRAVELLER.name().equalsIgnoreCase(
					person.getPersonType())) {
				//前后台下单对于投保的逻辑不一致，后台对于每位选择投保的游客在ord_person里有标识记录，而前台只要购买了保险产品就全部投保，并且没有对该字段做标识。
				//暂时做下单来源判断解决不一致产生的bug
				boolean flag = true;
				if(Constant.CHANNEL.BACKEND.name().equalsIgnoreCase(order.getChannel())) {
					if(StringUtils.isNotEmpty(person.getNeedInsure()) && "true".equalsIgnoreCase(person.getNeedInsure())) {
					} else {
						flag = false;
					}
				}
				if(flag) {
					InsInsurant insInsurant = new InsInsurant();
					insInsurant.setOrderId(order.getOrderId());
					insInsurant.setName(person.getName());
					insInsurant.setCertType(person.getCertType());
					insInsurant.setCertNo(person.getCertNo());
					insInsurant.setMobileNumber(person.getMobile());
					insInsurant.setPersonType(Constant.POLICY_PERSON.INSURANT
							.name());
					
					//如果证件类型为身份证,则从身份证号码中提取出出生日期、性别信息.
					if (null != person.getCertNo() && IdentityCardUtil.verify(person.getCertNo())) {
						insInsurant.setBirthday(IdentityCardUtil.getDate(person.getCertNo()));
						insInsurant.setSex(IdentityCardUtil.getSex(person.getCertNo()));
					} else {
					//其它证件类型则直接从person中将出生日期copy到insInsurant对象中,忽略性别选项.
						insInsurant.setBirthday(person.getBrithday());
						insInsurant.setSex(person.getGender());
					}
					
					insInsurants.add(insInsurant);
				}
			}
		}

		policy = policyInfoService.insert(policy, insInsurants);
		InsPolicyOperationLog log = new InsPolicyOperationLog();
		log.setPolicyId(policy.getPolicyId());
		log.setType(Constant.POLICY_LOG_STATUS.CREATE.name());
		if (null != operatorName) {
			log.setOperator(operatorName);
		}
		policyOperationLogService.insert(log);
		LOG.info("订单" + order.getOrderId() + "生成" + policy.getMetaProductName()+ "的预保单成功!");
		
		LOG.info("policy.getMetaProductQuantity():" + policy.getMetaProductQuantity());
		LOG.info("insInsurants.size() - 1:" + (insInsurants.size() - 1));
		LOG.info("isCompletedInsurantsInfo(insInsurants):" + isCompletedInsurantsInfo(insInsurants));
		LOG.info("policy.getMetaProductQuantity() == insInsurants.size() - 1:" + (policy.getMetaProductQuantity() == insInsurants.size() - 1));
		
		if (policy.getMetaProductQuantity() == insInsurants.size() - 1 && isCompletedInsurantsInfo(insInsurants)) {
			LOG.info("update.....");
			policy.setPolicyStatus(Constant.POLICY_STATUS.REQUESTED.name());
			policy.setPolicyResult(null);
			policy.setManual("N");
			policyInfoService.update(policy);
			
			log = new InsPolicyOperationLog();
			log.setPolicyId(policy.getPolicyId());
			log.setType(Constant.POLICY_LOG_STATUS.REQUEST.name());
			log.setOperator("SYSTEM");
			policyOperationLogService.insert(log);
		}
	}
	
	/**
	 * 将保单申请作废
	 * @param policy 保单
	 */
	private void cancelPolicy(final InsPolicyInfo policy) {
		if (Constant.POLICY_STATUS.REQUESTED.name().equals(policy.getPolicyStatus())) {
			policy.setPolicyStatus(Constant.POLICY_STATUS.CANCELLED.name());
			policy.setPolicyResult(null);
			policy.setManual("N");
			policyInfoService.update(policy);
			
			InsPolicyOperationLog log = new InsPolicyOperationLog();
			log.setPolicyId(policy.getPolicyId());
			log.setType(Constant.POLICY_LOG_STATUS.CANCEL.name());
			log.setOperator("SYSTEM");
			policyOperationLogService.insert(log);
		}
	}
	
	/**
	 * 投保人信息是否完善且不重复
	 * @param insInsurants 投保人的信息
	 * @return 是否完善
	 */
	private boolean isCompletedInsurantsInfo(List<InsInsurant> insInsurants) {
		if (null == insInsurants || insInsurants.isEmpty()) {
			return false;
		}
		Set<String> infoSet = new HashSet<String>();
		for (InsInsurant ins : insInsurants) {
			if (null == ins 
					|| StringUtils.isEmpty(ins.getName())
					|| StringUtils.isEmpty(ins.getCertType())
					|| StringUtils.isEmpty(ins.getCertNo())
					|| StringUtils.isEmpty(ins.getSex())
					|| null == ins.getBirthday()
					|| infoSet.contains(ins.getName())
					|| infoSet.contains(ins.getCertNo())) {
				return false;
			} else {
				if (Constant.POLICY_PERSON.INSURANT
						.name().equals(ins.getPersonType())) {
					infoSet.add(ins.getCertNo());
					infoSet.add(ins.getName());
				}
			}
		}
		return true;
	}

	public OrderService getOrderServiceProxy() {
		return orderServiceProxy;
	}

	public void setOrderServiceProxy(OrderService orderServiceProxy) {
		this.orderServiceProxy = orderServiceProxy;
	}

	public MetaProductService getMetaProductService() {
		return metaProductService;
	}

	public void setMetaProductService(MetaProductService metaProductService) {
		this.metaProductService = metaProductService;
	}

	public PolicyInfoService getPolicyInfoService() {
		return policyInfoService;
	}

	public void setPolicyInfoService(PolicyInfoService policyInfoService) {
		this.policyInfoService = policyInfoService;
	}

	public PolicyOperationLogService getPolicyOperationLogService() {
		return policyOperationLogService;
	}

	public void setPolicyOperationLogService(
			PolicyOperationLogService policyOperationLogService) {
		this.policyOperationLogService = policyOperationLogService;
	}

	public TopicMessageProducer getPolicyMessageProducer() {
		return policyMessageProducer;
	}

	public void setPolicyMessageProducer(TopicMessageProducer policyMessageProducer) {
		this.policyMessageProducer = policyMessageProducer;
	}
}
