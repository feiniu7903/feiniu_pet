/**
 * 
 */
package com.lvmama.pet.mark.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import com.lvmama.comm.pet.po.mark.MarkCoupon;
import com.lvmama.comm.pet.po.mark.MarkCouponCode;
import com.lvmama.comm.pet.po.mark.MarkCouponProduct;
import com.lvmama.comm.pet.po.mark.MarkCouponUsage;
import com.lvmama.comm.pet.service.mark.MarkCouponUserService;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.UserCouponDTO;
import com.lvmama.pet.mark.dao.MarkCouponCodeDAO;
import com.lvmama.pet.mark.dao.MarkCouponDAO;
import com.lvmama.pet.mark.dao.MarkCouponProductDAO;
import com.lvmama.pet.mark.dao.MarkCouponRelateUserDAO;

/**
 * @author liuyi
 *
 */
public class MarkCouponUserServiceImpl implements MarkCouponUserService {
	protected static final Log LOG = LogFactory.getLog(MarkCouponUserServiceImpl.class);

	@Autowired
	private MarkCouponDAO markCouponDAO;
	@Autowired
	private MarkCouponCodeDAO markCouponCodeDAO;
	@Autowired
	private MarkCouponProductDAO markCouponProductDAO;
	@Autowired
	private MarkCouponRelateUserDAO markCouponRelateUserDAO;
	
	/**
	 * 获取用户相关优惠券数
	 */
	@Override
	public Long selectCountByRelateUser(Map<String, Object> param) {
		if(!param.containsKey("userId")){
			LOG.error("CountByRelateUser userId is null "+param.get("applyField"));
			return 0l;
		}
		return markCouponRelateUserDAO.selectCountByRelateUser(param);
	}


	/**
	 * 获取用户相关优惠券
	 */
	@Override
	public List<UserCouponDTO> getMySpaceUserCouponData(Map<String, Object> params) {
		List<UserCouponDTO> list = new ArrayList<UserCouponDTO>();
		List<MarkCouponCode> codeList = markCouponRelateUserDAO.selectByRelateUserId(params);
		params = new HashMap<String, Object>();
		for (MarkCouponCode markCouponCode : codeList) {
			UserCouponDTO dto = new UserCouponDTO();
			MarkCoupon coupon = markCouponDAO.selectByPrimaryKey(markCouponCode.getCouponId());
			dto.setMarkCoupon(coupon);
			markCouponCode.setValidTimeByCouponDefination(coupon);
			dto.setMarkCouponCode(markCouponCode);
		    params.put("couponId", markCouponCode.getCouponId());
		    params.put("couponProductType", 2);
		    List<MarkCouponProduct> productTypes = markCouponProductDAO.select(params);
		    if(null!=productTypes && productTypes.size() > 0){
		    	for(MarkCouponProduct product : productTypes){
		    		if(!StringUtil.isEmptyString(product.getSubProductType())){
		    			String productTypeName = Constant.SUB_PRODUCT_TYPE.getCnName((product.getSubProductType()));
		    			product.setProductName(productTypeName);
		    		}
		    	}
		    }
		    dto.setProductTypes(productTypes);
			list.add(dto);
		}
		return list;
	}
	
	@Override
	public List<UserCouponDTO> getMySpaceUserCouponData(List<MarkCouponUsage> markCouponUsageList) {
		List<UserCouponDTO> list = new ArrayList<UserCouponDTO>();
		for(int i = 0; i < markCouponUsageList.size(); i++){
			MarkCouponUsage markCouponUsage = markCouponUsageList.get(i);
			MarkCouponCode markCouponCode = markCouponCodeDAO.selectByPrimaryKey(markCouponUsage.getCouponCodeId());
			if(markCouponCode != null){
				MarkCoupon markCoupon = markCouponDAO.selectByPrimaryKey(markCouponCode.getCouponId());
				UserCouponDTO dto = new UserCouponDTO();
				dto.setOrderId(markCouponUsage.getObjectId());
				dto.setMarkCoupon(markCoupon);
				dto.setMarkCouponCode(markCouponCode);
				
				Map params = new HashMap<String, Object>();
				params.put("couponId", markCouponCode.getCouponId());
			    params.put("couponProductType", 2);
			    List<MarkCouponProduct> productTypes = markCouponProductDAO.select(params);
			    if(null!=productTypes && productTypes.size() > 0){
			    	for(MarkCouponProduct product : productTypes){
			    		if(!StringUtil.isEmptyString(product.getSubProductType())){
			    			String productTypeName = Constant.SUB_PRODUCT_TYPE.getCnName((product.getSubProductType()));
			    			product.setProductName(productTypeName);
			    		}
			    	}
			    }
			    dto.setProductTypes(productTypes);
				list.add(dto);
			}
		}
		return list;
	}

}
