package com.lvmama.pet.businessCoupon.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.lvmama.comm.pet.po.businessCoupon.BusinessCoupon;
import com.lvmama.comm.pet.po.prod.ProdProductTag;
import com.lvmama.comm.pet.service.businessCoupon.BusinessCouponService;
import com.lvmama.comm.vo.Constant;
import com.lvmama.pet.businessCoupon.dao.BusinessCouponDAO;

public class BusinessCouponServiceImpl implements BusinessCouponService {
	@Autowired
	private BusinessCouponDAO businessCouponDAO;
	
	@Override
	public List<BusinessCoupon> selectByParam(final Map<String, Object> param) {
		return businessCouponDAO.selectByParam(param);
	}
	
	public BusinessCoupon selectByPK(Long businessCouponId) {
		if(businessCouponId == null) return null;
		
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("businessCouponId", businessCouponId);
		List<BusinessCoupon> list = businessCouponDAO.selectByParam(param);
		
		if(list != null && list.size() > 0 ){
			return list.get(0);
		}else{
			return null;
		}
	}
	
	@Override
	public BusinessCoupon insertBusinessCoupon(BusinessCoupon entity){
		 return businessCouponDAO.insertBusinessCoupon(entity);
	}
	@Override
	public Integer updateByPrimaryKey(BusinessCoupon record) {
		return businessCouponDAO.updateByPrimaryKey(record);
	}
	
	@Override
	public Integer selectBusinessCouponRowCount(Map<String, Object> param) {
		return businessCouponDAO.selectRowCount(param);
	}
	
	@Override
	public List<BusinessCoupon> selectByIDs(Map<String, Object> param) {
		return businessCouponDAO.selectByIds(param);
	}
	@Override
	public List<BusinessCoupon> selectWithProdInfo(Map<String, Object> param) {
		return businessCouponDAO.selectWithProdInfo(param);
	}
	/**
	 *同时保存优惠策略和绑定的产品
	 * @param entity
	 * @param productId
	 * @param branchIds
	 * @return String (返回多个操作的businessCouponId: 34523,4527)
	 */
	@Override
	public List<Long> saveBusinessCouponAndBusinessCoupon(BusinessCoupon entity,Long productId,String branchIds) {

		List<Long> businessCouponIds = new ArrayList<Long>();
		
		//绑多个产品类别
		if (branchIds != null && !"".equals(branchIds)) {
			String[] arrayBranchId = branchIds.split(",");
			for (String branchId : arrayBranchId) {
				if (StringUtils.isNotBlank(branchId)){
					entity.setBranchId(Long.valueOf(branchId));
					BusinessCoupon businessCoupon = businessCouponDAO.insertBusinessCoupon(entity);
					businessCouponIds.add(businessCoupon.getBusinessCouponId());	//保存优惠策略
				}
			}
		} else {
			//优惠绑当前产品
			if(entity.getProductId() != null){
				BusinessCoupon businessCoupon = businessCouponDAO.insertBusinessCoupon(entity);
				businessCouponIds.add(businessCoupon.getBusinessCouponId());
			}
		}
		
		return businessCouponIds;
	}
	/**
	 *同时更新优惠策略和绑定的产品
	 * @param record
	 * @param productId
	 * @param branchIds
	 * @return void
	 */
	@Override
	public void updateBusinessCouponAndBusinessCouponProduct(BusinessCoupon record) {
		
		businessCouponDAO.updateByPrimaryKey(record);
		
	}

	/**
	 * 保存优惠前做条件检查
	 * @param businessCoupon
	 * @param branchIds
	 * @return
	 */
	public String checkOptionBeforeSaveBusinessCoupon(BusinessCoupon businessCoupon, String branchIds){
		
		String result = "true";
		
		if (branchIds != null && !"".equals(branchIds)) {
			String[] arrayBranchId = branchIds.split(",");
			Map<String, Object> param = new HashMap<String, Object>();
			
			for (String branchId : arrayBranchId) {
				if (StringUtils.isNotBlank(branchId)){
					
					param.put("valid", "true");//true,时间是有效的
					param.put("validDate", "true");
					param.put("branchId", branchId);
					param.put("productId", businessCoupon.getProductId());
					param.put("metaType", businessCoupon.getMetaType());
					param.put("excludeBusinessCouponId", businessCoupon.getBusinessCouponId());//不包含自己
					param.put("couponType", com.lvmama.comm.vo.Constant.BUSINESS_COUPON_TYPE.MORE.getCode().toString());//多订多惠
					param.put("couponTarget", businessCoupon.getCouponTarget());//绑产品
					
					//获取该产品类别(销售/采购)多订多惠的优惠
					List<BusinessCoupon> list = selectWithProdInfo(param);
					
					if(list != null && list.size() > 0){
						//当前产品类别优惠是早订早惠
						if(com.lvmama.comm.vo.Constant.BUSINESS_COUPON_TYPE.EARLY.getCode().toString().equalsIgnoreCase(businessCoupon.getCouponType())){
								return "couponTypeMutex";//类型互斥
						}else if(com.lvmama.comm.vo.Constant.BUSINESS_COUPON_TYPE.MORE.getCode().toString().equalsIgnoreCase(businessCoupon.getCouponType())){
							//当前产品类别优惠是多订多惠
							for(BusinessCoupon bc : list){
								if(!(bc.getEndTime().before(businessCoupon.getBeginTime())|| bc.getBeginTime().after(businessCoupon.getEndTime()))){
									return "MoreCouponTypeTimeMutex";//多订多惠下单有效期互斥
								}
							}
						}
					}
					param.clear();
				}
			}
		}else{
			result = "";//branchIds 为空
		}
		return result;
	}
	
	/**
	 * 判断是否满足打tag的条件
	 */
	@Override
	public ProdProductTag checkProductTag(String couponType, Long productId) {
		ProdProductTag prodProductTag =null;
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("couponType", couponType);
		param.put("productId", productId);
		BusinessCoupon businessCoupon=businessCouponDAO.selectValidDate(param);
		if(businessCoupon!=null && businessCoupon.getBeginTime()!=null &&businessCoupon.getEndTime()!=null){
			prodProductTag =new ProdProductTag();
			prodProductTag.setBeginTime(businessCoupon.getBeginTime());
			prodProductTag.setEndTime(businessCoupon.getEndTime());
			prodProductTag.setProductId(productId);
			prodProductTag.setCreator(Constant.PROD_PRODUCT_TAG_CREATOR.SYSTEM.getCode());
		}
		return prodProductTag;
	}
	@Override
	public Integer deleteFromBusinessCoupon(Long businessCouponId) {
		// TODO Auto-generated method stub
		return businessCouponDAO.delelteFromBusinessCoupon(businessCouponId);
	}
	
	public void setBusinessCouponDAO(BusinessCouponDAO businessCouponDAO) {
		this.businessCouponDAO = businessCouponDAO;
	}


	


}
