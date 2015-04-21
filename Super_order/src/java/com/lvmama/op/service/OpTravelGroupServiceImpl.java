/**
 * 
 */
package com.lvmama.op.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.util.Assert;

import com.lvmama.com.dao.ComLogDAO;
import com.lvmama.comm.bee.po.meta.MetaProduct;
import com.lvmama.comm.bee.po.meta.MetaProductBranch;
import com.lvmama.comm.bee.po.meta.MetaTravelCode;
import com.lvmama.comm.bee.po.op.OpTravelGroup;
import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.po.ord.OrdOrderItemProd;
import com.lvmama.comm.bee.po.prod.ProdProduct;
import com.lvmama.comm.bee.po.prod.ProdProductBranch;
import com.lvmama.comm.bee.po.prod.ProdRoute;
import com.lvmama.comm.bee.po.prod.TimePrice;
import com.lvmama.comm.bee.service.op.IOpTravelGroupNumService;
import com.lvmama.comm.bee.service.op.IOpTravelGroupService;
import com.lvmama.comm.bee.service.prod.ProdProductBranchService;
import com.lvmama.comm.pet.po.pub.ComLog;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.utils.ord.RouteUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.op.dao.OpTravelGroupDAO;
import com.lvmama.prd.dao.MetaProductBranchDAO;
import com.lvmama.prd.dao.MetaProductDAO;
import com.lvmama.prd.dao.MetaTravelCodeDAO;
import com.lvmama.prd.dao.ProdProductBranchDAO;
import com.lvmama.prd.dao.ProdProductDAO;
import com.lvmama.prd.logic.ProductTimePriceLogic;

/**
 * 团相关服务实现，团人数更新操作{@link IOpTravelGroupNumService}.
 * 与其他的对数据操作分开接口{@link IOpTravelGroupService}.
 * @author yangbin
 *
 */
public class OpTravelGroupServiceImpl implements IOpTravelGroupService {
	/**
	 * 团DAO
	 */
	private OpTravelGroupDAO opTravelGroupDAO;
	/**
	 * 日志
	 */
	private ComLogDAO comLogDAO;

	private ProdProductDAO prodProductDAO;
	private ProdProductBranchDAO prodProductBranchDAO;
	private MetaProductBranchDAO metaProductBranchDAO;
	private MetaProductDAO metaProductDAO;
	private MetaTravelCodeDAO metaTravelCodeDAO;
	private ProductTimePriceLogic productTimePriceLogic;
	private ProdProductBranchService prodProductBranchService;
	/**
	 * 把参数生成map对象给更新操作使用
	 * @param travelGroupId
	 * @param count
	 * @return
	 */
	private Map<String,Object> createMap(Long travelGroupId,long count){
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("travelGroupId", travelGroupId);
		map.put("count", count);
		return map;
	}

	/* (non-Javadoc)
	 * @see com.lvmama.op.service.IOpTravelGroupService#updatePayNot(java.lang.Long, long)
	 */
	@Override
	public void updatePayNot(Long travelGroupId, long count) {
		opTravelGroupDAO.update("updatePayNot", createMap(travelGroupId, count));
	}

	/* (non-Javadoc)
	 * @see com.lvmama.op.service.IOpTravelGroupService#updatePayNot2Part(java.lang.Long, long)
	 */
	@Override
	public void updatePayNot2Part(Long travelGroupId, long count) {
		opTravelGroupDAO.update("updatePayNot2Part", createMap(travelGroupId, count));
	}

	/* (non-Javadoc)
	 * @see com.lvmama.op.service.IOpTravelGroupService#updatePayNot2Success(java.lang.Long, long)
	 */
	@Override
	public void updatePayNot2Success(Long travelGroupId, long count) {
		opTravelGroupDAO.update("updatePayNot2Success", createMap(travelGroupId, count));
	}

	/* (non-Javadoc)
	 * @see com.lvmama.op.service.IOpTravelGroupService#updatePayPart2Success(java.lang.Long, long)
	 */
	@Override
	public void updatePayPart2Success(Long travelGroupId, long count) {
		opTravelGroupDAO.update("updatePayPart2Success", createMap(travelGroupId, count));
	}

	public void updateGroupInitialNum(Long travelGroupId,long count,String operator){
		OpTravelGroup group=new OpTravelGroup();
		group.setTravelGroupId(travelGroupId);
		group=opTravelGroupDAO.selectByPrimary(group);
		if(group!=null){
			group.setInitialGroupNum(count);
			opTravelGroupDAO.update(group);		
			
			insertLog(travelGroupId,LOG_OBJECT_TYPE,"changeGroupInitial","变更计划人数","变更计划人数到:"+count,operator);
		}
	}

	
	
	@SuppressWarnings("unchecked")
	@Override
	public long selectCountByParam(Map<String, Object> parameter) {
		Map<String,Object> copyParameter=(Map<String,Object>)((HashMap<String,Object>)parameter).clone();
		if(copyParameter.containsKey("maxResult")){
			copyParameter.remove("maxResult");
		}
		if(copyParameter.containsKey("skipResult")){
			copyParameter.remove("skipResult");
		}
		return opTravelGroupDAO.selectCountByParam(copyParameter);
	}
	
	

	@Override
	public List<OpTravelGroup> selectListByParam(Map<String, Object> parameter) {
		return opTravelGroupDAO.selectByParam(parameter);
	}

	public void setOpTravelGroupDAO(OpTravelGroupDAO opTravelGroupDAO) {
		this.opTravelGroupDAO = opTravelGroupDAO;
	}

	@Override
	public OpTravelGroup getOpTravelGroup(Long id) {
		OpTravelGroup group=new OpTravelGroup();
		group.setTravelGroupId(id);
		return opTravelGroupDAO.selectByPrimary(group);
	}

	@Override
	public void changeStatus(Long id, String status,String memo, String operator) {
		OpTravelGroup group = this.getOpTravelGroup(id);
		Assert.notNull(group,"团信息不存在");
		//此处未对之前的状态做逻辑处理，需要时再补充
		group.setTravelGroupStatus(status);
		if(StringUtils.equals(Constant.TRAVEL_GROUP_STATUS.CONFIRM.name(), status)){
			group.setMakeTime(new Date());
		}
		if(memo != null) {
			group.setMemo(memo);
		}
		//需要根据不同的状态发送消息		
		opTravelGroupDAO.update(group);		
		if(StringUtils.equals(Constant.TRAVEL_GROUP_STATUS.CANCEL.name(), status)) {
			List<ProdProductBranch> list = prodProductBranchDAO.getProductBranchByProductId(group.getProductId(),null,null,null);
			for (ProdProductBranch branch : list) {
				TimePrice tp = new TimePrice();
				tp.setProdBranchId(branch.getProdBranchId());
				tp.setBeginDate(group.getVisitTime());
				tp.setEndDate(group.getVisitTime());
				productTimePriceLogic.deleteTimePrice(tp, operator);
			}
		}
		insertLog(id,"变更团状态","变更状态 ="+group.getTravelGroupStatusStr(),operator);
	}
	
	private void insertLog(Long objectId,String objectType,String logType,String logName,String content,String operator){
		ComLog log=new ComLog();
		log.setObjectId(objectId);
		log.setObjectType(objectType);
		log.setLogType(logType);
		log.setLogName(logName);
		log.setOperatorName(operator);
		log.setContent(content);
		comLogDAO.insert(log);
	}
	
	private void insertLog(Long objectId,String logName,String content,String operator){
		insertLog(objectId,LOG_OBJECT_TYPE,"changeStatus",logName,content,operator);
	}
	private static final String LOG_OBJECT_TYPE="OP_TRAVEL_GROUP";

	public void setComLogDAO(ComLogDAO comLogDAO) {
		this.comLogDAO = comLogDAO;
	}

	@Override
	public OpTravelGroup getOptravelGroup(String code) {
		return opTravelGroupDAO.selectByGroupCode(code);
	}

	@Override
	public void update(OpTravelGroup group) {
		opTravelGroupDAO.update(group);
	}

	@Override
	public void updateGroupPayNot(OrdOrder order) {
		long count=getOrderQuantity(order);
		OpTravelGroup group = this.opTravelGroupDAO.selectByGroupCode(order.getTravelGroupCode());
		if(count>0&&group!=null){			
			updatePayNot(group.getTravelGroupId(), count);
		}
	}

	@Override
	public void updateGroupPayPart(OrdOrder order) {
		long count=getOrderQuantity(order);
		if(count>0){
			OpTravelGroup group = this.opTravelGroupDAO.selectByGroupCode(order.getTravelGroupCode());
			if(group!=null){
				updatePayNot2Part(group.getTravelGroupId(), count);
			}
		}
	}

	/**
	 * 取出订单当中路线当中对应团的人数
	 * @param order 操作的订单实体
	 * @return 对应的人数，如果不是团订单返回0;
	 */
	public Long getOrderQuantity(final OrdOrder order) {
		long count = 0;
		// 是否有团号相关的产品，并且团号不为空
		if (RouteUtil.hasTravelGroupProduct(order.getOrderType())
				&& StringUtils.isNotEmpty(order.getTravelGroupCode())) {
			List<OrdOrderItemProd> ordOrderItemProdList = order
					.getOrdOrderItemProds();
			for (OrdOrderItemProd ordOrderItemProd : ordOrderItemProdList) {
				if (RouteUtil.hasTravelGroupProduct(ordOrderItemProd
						.getSubProductType())) {
					ProdProductBranch branch = prodProductBranchService.selectProdProductBranchByPK(ordOrderItemProd.getProdBranchId());
					if (branch != null) {
						count += (branch.getAdultQuantity() + branch
								.getChildQuantity())
								* ordOrderItemProd.getQuantity();
					}
				}
			}
		}
		return count;
	}
 
	/**
	 * @param prodProductBranchService the prodProductBranchService to set
	 */
	public void setProdProductBranchService(
			ProdProductBranchService prodProductBranchService) {
		this.prodProductBranchService = prodProductBranchService;
	}

	@Override
	public void rollbackGroupNum(OrdOrder order) {
		// 如果订单是处理时需要对数据进行恢复，参数改为负值
		String status = order.getPaymentStatus();
		long count=getOrderQuantity(order);
		OpTravelGroup group=getOptravelGroup(order.getTravelGroupCode());
		if(count>0 && group!=null){
			if (Constant.PAYMENT_STATUS.UNPAY.name().equals(status)) { // 未支付的从未支付当中去掉部分
				updatePayNot(group.getTravelGroupId(),-count);
			} else if (Constant.PAYMENT_STATUS.PARTPAY.name().equals(status)) {
				group.setPayPartNum(group.getPayPartNum() - count);
				update(group);
			} else if (Constant.PAYMENT_STATUS.PAYED.name().equals(status)) {
				group.setPaySuccessNum(group.getPaySuccessNum() - count);
				update(group);
			}
		}
	}
 
	@Override
	public void changeGroupwordAble(Long id,String operator) {
		Assert.isTrue(!(id==null||id<1),"团信息不存在");

		OpTravelGroup group=getOpTravelGroup(id);
		Assert.notNull(group,"团不存在，不可以操作");
		Assert.isTrue(!group.isGroupWordAbled(),"状态已经是可发出团通知状态，不可以再变更");

		group.setGroupWordAble("true");
		update(group);
		insertLog(id, "groupWordAble","变更可发出团通知书", operator);
	}

	@Override
	public List<OpTravelGroup> selectProductListByParam(
			Map<String, Object> parameter) {
		return opTravelGroupDAO.selectProductListByParam(parameter);
	}

	@Override
	public long selectProductCountByParam(Map<String, Object> parameter) {
		return opTravelGroupDAO.selectProductCount(parameter);
	}

	@Override
	public List<OpTravelGroup> selectListByProductDate(Long productId, Date startDate) {
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("productId", productId);
		map.put("startVisit", startDate);
		//只能查100条
		map.put("maxResult", 100);
		return opTravelGroupDAO.selectByParam(map);
	}
	
	@Override
	public void createTravelGroupByProductId(Long productId) {
		ProdProduct product = prodProductDAO.selectProductDetailByPrimaryKey(productId);
		Date start=DateUtil.getDayStart(new Date());
		Date end=product.getOfflineTime();
		if(end==null||end.before(start)) {
			return;
		}

		ProdProductBranch branch=prodProductBranchDAO.getProductDefaultBranchByProductId(product.getProductId());
		if(branch==null){
			return;
		}
		
		//读取时间价格表
		List<TimePrice> list=productTimePriceLogic.selectSaleProdTimePriceByProductId(product.getProductId(),branch.getProdBranchId(), start.getTime(), end.getTime());
		if(CollectionUtils.isEmpty(list)){
			return;
		}
		
		for(TimePrice tp:list){
			this.createTravelGroup(product, branch, tp);
		}
	}



	/**
	 * 根据时间价格表生成团信息
	 * @param timePrice
	 */
	private void createTravelGroup(ProdProduct product,ProdProductBranch branch, TimePrice timePrice){
		
		String travelGroupCode= getTravelGroupCode(product,branch.getProdBranchId(),timePrice);
		
		OpTravelGroup group=opTravelGroupDAO.selectByGroupCode(travelGroupCode);
		
		if(group==null){
			group=new OpTravelGroup();
			group.setTravelGroupCode(travelGroupCode);
			group.setProductId(product.getProductId());
			group.setProductName(product.getProductName());
			group.setVisitTime(timePrice.getSpecDate());
			group.setSellPrice(timePrice.getPrice());
			group.setOrgId(product.getOrgId());
			long time=timePrice.getSpecDate().getTime();
			
			List<TimePrice> tps = this.productTimePriceLogic.selectIntersection(product.getProductId(),branch.getProdBranchId(), time, time);//读取产品的结算价
			
			if(CollectionUtils.isNotEmpty(tps))
			{
				TimePrice tp=tps.get(0);
				if(tp.getDayStock()<0){
					group.setInitialGroupNum(-1);
				}else{
					group.setInitialGroupNum((branch.getAdultQuantity()+branch.getChildQuantity())*tp.getDayStock());					
				}
				group.setSettlementPrice(tp.getSettlementPrice());
			}
			ProdRoute prodRoute = (ProdRoute) product;
			if (prodRoute != null) {
				Long initalNum = prodRoute.getInitialNum();
				if (initalNum == null) {
					initalNum = 0L;
				}
				group.setInitialNum(initalNum);
				group.setDays(prodRoute.getDays());
			} else {
				group.setInitialNum(0);
			}
			group.setTravelGroupStatus(Constant.TRAVEL_GROUP_STATUS.NORMAL.name());
			
			if(opTravelGroupDAO.selectByGroupCode(travelGroupCode)==null){//再添加一次确保上面不会重复
				try{
					opTravelGroupDAO.insert(group);
				}catch(DuplicateKeyException ex){//该地方捕捉团号已经存在的异常					
				}
			}
		}
	}

	private String getTravelGroupCode(ProdProduct product,Long prodBranchId, TimePrice timePrice) {
		String travelGroupCode = RouteUtil.makeTravelGroupCode(product, timePrice.getSpecDate());
		List<MetaProductBranch> mpbs = metaProductBranchDAO.getMetaProductBranchByProdBranchId(prodBranchId);
		if(mpbs!=null && mpbs.size()>0){
			MetaProductBranch mpb = mpbs.get(0); 
			MetaProduct metaProduct = metaProductDAO.getMetaProductByBranchId(mpb.getMetaBranchId());
			if(metaProduct== null){
				return travelGroupCode;
			}
			if(Constant.SUPPLIER_CHANNEL.JINJIANG.getCode().equals(metaProduct.getSupplierChannel()) ){
				Map<String,Object> params = new HashMap<String,Object>();
				params.put("supplierProductId", mpb.getProductIdSupplier());
				params.put("specDate", DateUtil.accurateToDay(timePrice.getSpecDate()));
				params.put("supplierChannel", Constant.SUPPLIER_CHANNEL.JINJIANG.getCode());
				List<MetaTravelCode> metaTravelCodes = metaTravelCodeDAO.selectByCondition(params);
				if(metaTravelCodes!=null && metaTravelCodes.size()>0){
					travelGroupCode = metaTravelCodes.get(0).getTravelCodeId();
				}
			}
			if(Constant.SUPPLIER_CHANNEL.SH_HOLIDAY.getCode().equals(metaProduct.getSupplierChannel())){
				MetaTravelCode metaTravelCode = metaTravelCodeDAO.selectBySuppAndDate(mpb.getProductIdSupplier(), DateUtil.accurateToDay(timePrice.getSpecDate()));
				if(metaTravelCode!=null){
					travelGroupCode = metaTravelCode.getTravelCode();
				}
			}
		}
		return travelGroupCode;
	}

	public void setProductTimePriceLogic(ProductTimePriceLogic productTimePriceLogic) {
		this.productTimePriceLogic = productTimePriceLogic;
	}

	/**
	 * @param prodProductDAO the prodProductDAO to set
	 */
	public void setProdProductDAO(ProdProductDAO prodProductDAO) {
		this.prodProductDAO = prodProductDAO;
	}

	/**
	 * @param prodProductBranchDAO the prodProductBranchDAO to set
	 */
	public void setProdProductBranchDAO(ProdProductBranchDAO prodProductBranchDAO) {
		this.prodProductBranchDAO = prodProductBranchDAO;
	}

	public void setMetaProductBranchDAO(MetaProductBranchDAO metaProductBranchDAO) {
		this.metaProductBranchDAO = metaProductBranchDAO;
	}

	public void setMetaProductDAO(MetaProductDAO metaProductDAO) {
		this.metaProductDAO = metaProductDAO;
	}

	public void setMetaTravelCodeDAO(MetaTravelCodeDAO metaTravelCodeDAO) {
		this.metaTravelCodeDAO = metaTravelCodeDAO;
	}
	
	
	
}
