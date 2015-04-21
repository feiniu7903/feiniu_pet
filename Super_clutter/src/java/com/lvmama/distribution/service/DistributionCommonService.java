package com.lvmama.distribution.service;

import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Map;

import com.lvmama.comm.bee.po.distribution.DistributionMessage;
import com.lvmama.comm.bee.po.distribution.DistributionOrderRefund;
import com.lvmama.comm.bee.po.distribution.DistributionProduct;
import com.lvmama.comm.bee.po.distribution.DistributorInfo;
import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.po.pass.PassEvent;
import com.lvmama.comm.pet.po.place.Place;
import com.lvmama.comm.utils.MD5;
import com.lvmama.distribution.service.impl.DistributionPushService.PUSH_TYPE;

/**
 * 分销公用逻辑
 * @author lipengcheng
 *
 */
public interface DistributionCommonService {
	
	/**
	 * 最近更新过的订单推送
	 */
	public void pushOrder(Long orderId);
	
	/**
	 * 根据条件取分销产品
	 * @param pageMap
	 * @return
	 */
	public List<DistributionProduct> getDistributionProduct(Map<String,Object> pageMap);
	
	/**
	 * 取分销产品总数
	 * @param objectMap
	 * @return
	 */
	public Long getDistributionProductCount(Map<String, Object> pageMap);
	
	
	/**
	 * 取分销产品景区接口
	 * @param paramMap
	 * @return
	 */
	public List<Place> getDistributionProductPlace(Map<String,Object> paramMap);
	
	/**
	 * 取分销产品景区接口数量
	 * @param paramMap
	 * @return
	 */
	public Long getDistributionProductPlaceCount(Map<String,Object> paramMap);
	

	/**
	 * webService方式退款
	 * @param refund
	 * @param wsdl
	 * @return
	 */
	public boolean refundForWebService(DistributionOrderRefund refund,String wsdl);
	/**
	 * http方式分销商退款接口
	 * @param refund
	 * @param url
	 * @return
	 */
	public boolean refundForHttp(DistributionOrderRefund refund, String url,Map<String,String> map);
	/**
	 * 更改退款状态
	 * @param orderId
	 */
	public void refundStatu(String orderId,Long distributionOrderRefundId);
	public String getCredenctStatus(OrdOrder ordOrder);
	public PassEvent resendByNotLvmama(Long codeId);
	
	/**
	 * job定时推送加入黑名单的产品通知分销商下线
	 * @param distributionMessage
	 */
	public void pushOffLine(DistributionMessage distributionMessage);
	
	/**
	 * job定时推送最近更新的产品
	 * @param distributionMessage
	 */
	public void pushProduct(DistributionMessage distributionMessage);
	
	/**
	 * 给分销商添加符合条件的产品
	 */
	public void addProductToDistributor(Long productId);
	
	/**
	 * 
	 * 根据分销商帐号查询分销商
	 * @param partnerCode
	 * @return
	 */
	public DistributorInfo getDistributorByCode(String partnerCode);
	/**
	 * 根据分销商Id查询分销商Ip白名单
	 * @param distributorInfoId
	 * @return
	 */
	public List<String> getDistributorIps(Long distributorInfoId);

	/**
	 * 根据销售产品的渠道取消分销
	 * @param productId
	 */
	public void cancelDistributorByProdChannel(Long productId);
	
	/**
	 * 根据采购产品的支付对象取消分销
	 * @param metaProductId
	 * @param payTarget
	 */
	public void cancelDistributorByMetaProdTarget(Long metaProductId, String payTarget);
	
	
	/**
	 * 返回产品对应需要推送的分销商
	 * @param productId
	 * @return
	 */
	public List<DistributorInfo> getDistributorsByProductId(Long productId);
	
	
	public DistributorInfo getDistributorById(Long distributorId);
	
	public DistributorInfo getDistributorById(String distributorChannel);
	
	/**
	 * 保存分销推送消息
	 * @param distributionMessage
	 */
	public void saveOrUpdateMessage(DistributionMessage distributionMessage);
	
	/**
	 * 查询可推送消息列表
	 * @param distributionMessage
	 */
	public List<DistributionMessage> queryMessageByMsgParams(DistributionMessage distributionMessage);
}
