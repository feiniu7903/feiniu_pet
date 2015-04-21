package com.lvmama.ebk.service;

import java.beans.PropertyEditor;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.lvmama.comm.bee.po.ebooking.EbkProdBranch;
import com.lvmama.comm.bee.po.ebooking.EbkProdContent;
import com.lvmama.comm.bee.po.ebooking.EbkProdJourney;
import com.lvmama.comm.bee.po.ebooking.EbkProdProduct;
import com.lvmama.comm.bee.po.ebooking.EbkProdRelation;
import com.lvmama.comm.bee.po.ebooking.EbkProdTarget;
import com.lvmama.comm.bee.po.ebooking.EbkProdTimePrice;
import com.lvmama.comm.bee.po.ebooking.EbkProdTimePrice.OPERATE_STATUS;
import com.lvmama.comm.bee.po.prod.ProdProduct;
import com.lvmama.comm.bee.po.prod.TimeRange;
import com.lvmama.comm.bee.po.prod.TimeRange.TimeRangePropertEditor;
import com.lvmama.comm.bee.service.ebooking.EbkProdAuditService;
import com.lvmama.comm.bee.service.ebooking.EbkProdBranchService;
import com.lvmama.comm.bee.service.ebooking.EbkProdProductService;
import com.lvmama.comm.bee.service.ebooking.EbkProdSnapshotService;
import com.lvmama.comm.bee.service.ebooking.EbkProdTargetService;
import com.lvmama.comm.bee.service.ebooking.EbkProdTimePriceService;
import com.lvmama.comm.bee.service.prod.ProdProductService;
import com.lvmama.comm.jms.MessageFactory;
import com.lvmama.comm.jms.TopicMessageProducer;
import com.lvmama.comm.pet.po.pub.ComPicture;
import com.lvmama.comm.pet.po.sup.SupBCertificateTarget;
import com.lvmama.comm.pet.service.sup.BCertificateTargetService;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.Constant.CONTACT_TYPE;
import com.lvmama.comm.vo.Constant.EBK_AUDIT_ITEM_CONFIG;
import com.lvmama.comm.vo.Constant.EBK_CHANGED_ITEM;

/**
 * ebk产品审核服务(该类不保证事务)
 * @author taiqichao
 *
 */
public class EbkProdAuditServiceProxy implements EbkProdAuditService {
	
 
	private EbkProdProductService ebkProdProductService;
	
	private EbkProdSnapshotService ebkProdSnapshotService;
	
	private EbkProdTimePriceService ebkProdTimePriceService;
	
	private EbkProdBranchService ebkProdBranchService;
	
	private TopicMessageProducer productMessageProducer;
	 
	private EbkProdTargetService ebkProdTargetService;
	
	private BCertificateTargetService bCertificateTargetService;
	
	private ProdProductService prodProductService;

	@Override
	public void auditPassByUser(Long ebkProdProductId, Date onlineTime,
			Date offlineTime, Boolean online) throws Exception {
		ebkProdProductService.prodProductAuditPass(ebkProdProductId, onlineTime, offlineTime, online);
		//相关消息发送
		sendMessages(ebkProdProductId);
	}
	
	/**
	 * 发消息
	 */
	private void sendMessages(Long ebkProdProductId) {
		EbkProdProduct ebkProdProduct=ebkProdProductService.findEbkProdAllByPrimaryKey(ebkProdProductId);
		if(null!=ebkProdProduct){
			EbkProdTimePrice ebkProdTimePriceTerm=new EbkProdTimePrice();
			ebkProdTimePriceTerm.setProductId(ebkProdProductId);
			ebkProdTimePriceTerm.setStockType(null);
			List<EbkProdTimePrice> ebkProdTimePriceList=ebkProdTimePriceService.findListByTermOrderByDateASC(ebkProdTimePriceTerm);
			
			EbkProdBranch ebkProdBranchTerm=new EbkProdBranch();
			ebkProdBranchTerm.setProdProductId(ebkProdProductId);
			List<EbkProdBranch> ebkProdBranchList=ebkProdBranchService.query(ebkProdBranchTerm);
			for (EbkProdBranch ebkProdBranch : ebkProdBranchList) {
				if(null!=ebkProdTimePriceList&&ebkProdTimePriceList.size()>0){
					TimeRange tr=getTimeRange(ebkProdTimePriceList);
					if(null!=tr){
						PropertyEditor editor = new TimeRangePropertEditor();
						editor.setValue(tr);	
						productMessageProducer.sendMsg(MessageFactory.newProductMetaPriceMessage(ebkProdBranch.getMetaProdBranchId(),editor.getAsText()));
					}
				}
			}
			
			//产品上传了小图
			ProdProduct product=prodProductService.getProdProduct(ebkProdProduct.getProdProductId());
			if(null!=product&&StringUtils.isNotBlank(product.getSmallImage())){
				productMessageProducer.sendMsg(MessageFactory.newProductCreateMessage(ebkProdProduct.getProdProductId()));
			}
			
			// 发送修改销售产品的消息
			productMessageProducer.sendMsg(MessageFactory.newProductUpdateMessage(ebkProdProduct.getProdProductId()));
			// 发送place变更消息
			productMessageProducer.sendMsg(MessageFactory.newProductPlaceUpdateMessage(ebkProdProduct.getProdProductId()));
		}
	}
	
	/**
	 * 获得时间价格表区间
	 * @param ebkProdTimePriceList
	 * @return
	 */
	private TimeRange getTimeRange(List<EbkProdTimePrice> ebkProdTimePriceList){
		if(ebkProdTimePriceList.isEmpty()){
			return null;
		}
		Date startDate=ebkProdTimePriceList.get(0).getSpecDate();
		Date endDate=ebkProdTimePriceList.get(ebkProdTimePriceList.size()-1).getSpecDate();
		return new TimeRange(startDate,endDate);
	}
	
	
	@Override
	public boolean isNeedAudit(Long ebkProdProductId) {
		
		//第一次提交的，必须审核
		EbkProdProduct ebkProdProduct=ebkProdProductService.findEbkProdProductDOByPrimaryKey(ebkProdProductId);
		if(null==ebkProdProduct.getMetaProductId()){
			return true;
		}
		
		//获取产品凭证对象
		EbkProdTarget targetTerm=new EbkProdTarget();
		targetTerm.setProductId(ebkProdProductId);
		targetTerm.setTargetType(CONTACT_TYPE.SUP_B_CERTIFICATE_TARGET.name());
		List<EbkProdTarget> ebkProdTargetList=ebkProdTargetService.findListByTerm(targetTerm);
		if(null==ebkProdTargetList||ebkProdTargetList.size()==0){
			return true;
		}
		EbkProdTarget target=ebkProdTargetList.get(0);
		SupBCertificateTarget supBCertificateTarget=bCertificateTargetService.getBCertificateTargetByTargetId(target.getTargetId());
		if(StringUtils.isBlank(supBCertificateTarget.getEbkProdAuditCfg())){
			return true;
		}
		
		//获取免审点
		Map<String, EBK_AUDIT_ITEM_CONFIG> auditItems=new HashMap<String, EBK_AUDIT_ITEM_CONFIG>();
		String[] cfgList=supBCertificateTarget.getEbkProdAuditCfg().split(";");
		for (String cfg : cfgList) {
			EBK_AUDIT_ITEM_CONFIG cfgItem=EBK_AUDIT_ITEM_CONFIG.valueOf(cfg.trim());
			auditItems.put(cfgItem.name(), cfgItem);
		}
		//没有免审点故需要审核
		if(auditItems.isEmpty()){
			return true;
		}
		
		//获取所有的修改点
		Map<String, EBK_CHANGED_ITEM> changedItems=this.getChangeItems(ebkProdProductId);
		for(Map.Entry<String, EBK_AUDIT_ITEM_CONFIG> entry :auditItems.entrySet()){
			changedItems.remove(entry.getKey());//删除免审的修改点
		}
		
		//判断是否要还有要审核的点,如果还有，那么要审核
		if(!changedItems.isEmpty()){
			return true;
		}
		
		return false;
	}
	
	
	@Override
	public void auditPassBySystem(Long ebkProdProductId,Long updateUserId) throws Exception {
		EbkProdProduct ebkProdProduct=ebkProdProductService.findEbkProdProductDOByPrimaryKey(ebkProdProductId);
		if(null==ebkProdProduct.getMetaProductId()){
			throw new RuntimeException("The ebk product first submited,must be audited by user!");
		}
		ebkProdProduct.setUpdateDate(new Date());
		ebkProdProduct.setUpdateUserId(updateUserId);
		ebkProdProductService.updateEbkProdProductDO(ebkProdProduct);
		ProdProduct prodProduct=this.prodProductService.getProdProductById(ebkProdProduct.getProdProductId());
		ebkProdProductService.prodProductAuditPass(ebkProdProductId,prodProduct.getOnlineTime(),prodProduct.getOfflineTime(),Boolean.valueOf(prodProduct.getOnLine()));
		//相关消息发送
		sendMessages(ebkProdProductId);
	}
	
	
	/**
	 * 获取修改点
	 * @param ebkProdProductList
	 * @return
	 */
	private Map<String, EBK_CHANGED_ITEM> getChangeItems(Long ebkProdProductId){
		
		List<EbkProdProduct> ebkProdProductList=ebkProdSnapshotService.getEbkProdProductVersionObj(ebkProdProductId);
		
		Map<String, EBK_CHANGED_ITEM> changedItem=new HashMap<String, EBK_CHANGED_ITEM>();
		
		//基本信息比较
		Map<String,Object> baseInfo=ebkProdSnapshotService.compareEbkProdProductBase(ebkProdProductList);
		if(!baseInfo.isEmpty()){
			changedItem.put(EBK_CHANGED_ITEM.PROD_BASE_INFO.name(), EBK_CHANGED_ITEM.PROD_BASE_INFO);
		}
		
		//推荐及特色
		Map<String,Object> recommendInfo=ebkProdSnapshotService.compareEbkProdProductRecommend(ebkProdProductList);
		if(!recommendInfo.isEmpty()){
			changedItem.put(EBK_CHANGED_ITEM.PROD_RECOMMEND_FEATURE.name(), EBK_CHANGED_ITEM.PROD_RECOMMEND_FEATURE);
		}
		
		//行程描述
		Map<String, List<EbkProdJourney>> tripInfo=ebkProdSnapshotService.compareEbkProdProductTrip(ebkProdProductList);
		if(!tripInfo.isEmpty()){
			changedItem.put(EBK_CHANGED_ITEM.TRIP_DESC.name(), EBK_CHANGED_ITEM.TRIP_DESC);
		}
		
		Map<String,Object> otherInfo=ebkProdSnapshotService.compareEbkProdProductOther(ebkProdProductList);
		
		//行前须知
		String acitontoknow=(String) otherInfo.get(Constant.VIEW_CONTENT_TYPE.ACITONTOKNOW.name());
		if(null!=acitontoknow){
			changedItem.put(EBK_CHANGED_ITEM.ACITONTOKNOW.name(), EBK_CHANGED_ITEM.ACITONTOKNOW);
		}
		
		//推荐项目
		String recommendproject=(String) otherInfo.get(Constant.VIEW_CONTENT_TYPE.RECOMMENDPROJECT.name());
		if(null!=recommendproject){
			changedItem.put(EBK_CHANGED_ITEM.RECOMMENDPROJECT.name(), EBK_CHANGED_ITEM.RECOMMENDPROJECT);
		}
		
		//预订须知
		String ordertoknown=(String) otherInfo.get(Constant.VIEW_CONTENT_TYPE.ORDERTOKNOWN.name());
		if(null!=ordertoknown){
			changedItem.put(EBK_CHANGED_ITEM.ORDERTOKNOWN.name(), EBK_CHANGED_ITEM.ORDERTOKNOWN);
		}
		
		//费用说明
		Map<String,Object> costInfo=ebkProdSnapshotService.compareEbkProdProductCost(ebkProdProductList);
		if(!costInfo.isEmpty()){
			changedItem.put(EBK_CHANGED_ITEM.COST_DESC.name(), EBK_CHANGED_ITEM.COST_DESC);
		}
		
		//图片
		Map<String,List<ComPicture>> picInfo=ebkProdSnapshotService.compareEbkProdProductPics(ebkProdProductList);
		if(!picInfo.isEmpty()){
			changedItem.put(EBK_CHANGED_ITEM.PICTURE_CHANGED.name(), EBK_CHANGED_ITEM.PICTURE_CHANGED);
		}
		
		//发车信息
		Map<String, List<EbkProdContent>> trafficInfo=ebkProdSnapshotService.compareEbkProdProductTraffic(ebkProdProductList);
		if(!trafficInfo.isEmpty()){
			changedItem.put(EBK_CHANGED_ITEM.TRAFFICINFO.name(), EBK_CHANGED_ITEM.TRAFFICINFO);
		}
		
		//新的快照版本时间价格
		List<EbkProdTimePrice> ebkProdTimePriceListNew=ebkProdProductList.get(0).getEbkProdTimePrices();
		
		//库存修改
		for (EbkProdTimePrice ebkProdTimePrice : ebkProdTimePriceListNew) {
			if(OPERATE_STATUS.UPDATE_OPERATE.name().equals(ebkProdTimePrice.getOperateStatus())
					&&StringUtils.isNotBlank(ebkProdTimePrice.getStockType())){//修改的,并且是库存变更的
				changedItem.put(EBK_CHANGED_ITEM.UPDATE_STOCK.name(), EBK_CHANGED_ITEM.UPDATE_STOCK);
				break;
			}
		}
		
		//新增时间价格库存
		for (EbkProdTimePrice ebkProdTimePrice : ebkProdTimePriceListNew) {
			if(OPERATE_STATUS.ADD_OPERATE.name().equals(ebkProdTimePrice.getOperateStatus())){
				changedItem.put(EBK_CHANGED_ITEM.ADD_TIME_STOCK.name(), EBK_CHANGED_ITEM.ADD_TIME_STOCK);
				break;
			}
		}
		
		//价格修改
		for (EbkProdTimePrice ebkProdTimePrice : ebkProdTimePriceListNew) {
			if(OPERATE_STATUS.UPDATE_OPERATE.name().equals(ebkProdTimePrice.getOperateStatus())){//修改的
				if(ebkProdTimePrice.getPrice().longValue()>0
						||ebkProdTimePrice.getMarketPrice().longValue()>0
						||ebkProdTimePrice.getSettlementPrice().longValue()>0){//销售价，市场价，结算价任何一个大于零说明修改了价格
					changedItem.put(EBK_CHANGED_ITEM.UPDATE_PRICE.name(), EBK_CHANGED_ITEM.UPDATE_PRICE);
					break;
				}
			}
		}
		
		//关联销售
		Map<String,List<EbkProdRelation>> relationChangeMap=ebkProdSnapshotService.compareEbkProdProductRelation(ebkProdProductList);
		if(!relationChangeMap.isEmpty()){
			changedItem.put(EBK_CHANGED_ITEM.RELATION_PROD.name(), EBK_CHANGED_ITEM.RELATION_PROD);
		}
		
		
		return changedItem;
	}
	
	
	

	public void setEbkProdSnapshotService(
			EbkProdSnapshotService ebkProdSnapshotService) {
		this.ebkProdSnapshotService = ebkProdSnapshotService;
	}

	public void setEbkProdTargetService(EbkProdTargetService ebkProdTargetService) {
		this.ebkProdTargetService = ebkProdTargetService;
	}

	public void setbCertificateTargetService(
			BCertificateTargetService bCertificateTargetService) {
		this.bCertificateTargetService = bCertificateTargetService;
	}

	public void setProdProductService(ProdProductService prodProductService) {
		this.prodProductService = prodProductService;
	}

	public void setEbkProdProductService(EbkProdProductService ebkProdProductService) {
		this.ebkProdProductService = ebkProdProductService;
	}


	public void setEbkProdTimePriceService(
			EbkProdTimePriceService ebkProdTimePriceService) {
		this.ebkProdTimePriceService = ebkProdTimePriceService;
	}


	public void setEbkProdBranchService(EbkProdBranchService ebkProdBranchService) {
		this.ebkProdBranchService = ebkProdBranchService;
	}


	public void setProductMessageProducer(
			TopicMessageProducer productMessageProducer) {
		this.productMessageProducer = productMessageProducer;
	}

	
	
	
}
