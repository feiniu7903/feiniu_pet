package com.lvmama.pet.sweb.mark.coupon;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;

import com.lvmama.comm.pet.po.mark.MarkCoupon;
import com.lvmama.comm.pet.po.mark.MarkCouponCode;
import com.lvmama.comm.pet.service.mark.MarkCouponService;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.utils.PriceUtil;
import com.lvmama.comm.vo.Constant;

public class AjaxMarkCouponAction extends CouponBackBaseAction {
	/**
	 * 序列值
	 */
	private static final long serialVersionUID = 2660634461259484693L;
	 
	/**
	 * 优惠批次标识
	 */
	private Long couponId;
	/**
	 * 优惠码的标识
	 */
	private Long couponCodeId;
	/**
	 * 更新后的优惠号码
	 */
	private String couponCode;
	/**
	 * 优惠批次
	 */
	private MarkCoupon markCoupon;
	/**
	 * 以"元"为单位的X值
	 */
	private Float argumentXYuan;
	/**
	 * 以"元"为单位的Y值
	 */	
	private Float argumentYYuan;
	/**
	 * 以"元"为单位的Z值
	 */	
	private Float argumentZYuan;
	
	/**
	 * 取反优惠批次的有效状态，即原本有效的更新为无效，无效的变为有效
	 */
	@Action(value="/mark/coupon/editValidStatus")
	public void editMarkCouponValidStatus() {
		
		Map<String, Object> param = new HashMap<String, Object>();
		MarkCoupon markCoupon = null == couponId ? null : markCouponService.selectMarkCouponByPk(couponId);
		
		if (null == markCoupon) {
			param.put("success", false);
			param.put("errorMessage", "找不到相应的优惠批次");
		} else {
			if ("true".equals(markCoupon.getValid())) {
				markCoupon.setValid("false");
			} else {
				markCoupon.setValid("true");
			}
			markCouponService.updateMarkCoupon(markCoupon);
			param.put("success", true);
			param.put("successMessage", "优惠开启或者关闭修改成功");
			
		}
		try {
			getResponse().getWriter().print(JSONObject.fromObject(param));
		} catch (IOException ioe) {
			
		}
	}
	
	/**
	 * 更新优惠码的号码
	 */
	@Action(value="/mark/coupon/modifyMarkCouponCode")
	public void modifyMarkCouponCode() {
		Map<String, Object> param = new HashMap<String, Object>();
		MarkCouponCode markCouponCode = null == couponCodeId ? null : markCouponService.selectMarkCouponCodeByPk(couponCodeId);
		
		if (null == markCouponCode || StringUtils.isBlank(couponCode)) {
			param.put("success", false);
			param.put("errorMessage", "找不到相应的优惠码");
		} else {
			markCouponCode.setCouponCode(couponCode);
			if (null != markCouponService.updateMarkCouponCode(markCouponCode, true)) {
				param.put("success", true);
				param.put("errorMessage", "");
			} else {
				param.put("success", false);
				param.put("errorMessage", "优惠号码已经存在，不能重新存在!");
			}
		}
		try {
			getResponse().getWriter().print(JSONObject.fromObject(param));
		} catch (IOException ioe) {
			
		}
	}
	
	/**
	 * 新增或修改优惠券批次，根据传入的markCoupon是否存在couponId来决定是新增还是更新
	 */
	@Action(value="/mark/coupon/addOrModifyMarkCoupon")
	public void addOrModifyMarkCoupon() {
		Map<String, Object> param = new HashMap<String, Object>();
		if (null == markCoupon) {
			param.put("success", false);
			param.put("errorMessage", "操作失败");			
		} else {
			setInsertMarkCoupon();
			if (null == markCoupon.getCouponId()) {
				markCoupon.setValid("false");//新增默认为关闭
				markCouponService.insertMarkCoupon(markCoupon);
				param.put("success", true);
				param.put("successMessage", "新增优惠成功!");
			} else {
				markCouponService.updateMarkCoupon(markCoupon);
				param.put("success", true);
				param.put("successMessage", "修改优惠成功!");
			}
			 
			String OperateName = super.getSessionUserName();
			this.saveComLog("COUPON_BUSINESS_BIND",this.markCoupon.getCouponId() , OperateName, Constant.COUPON_ACTION.COUPON_ADD.name(),
					"新增或修改优惠券(活动)", "新增或修改优惠券(活动)名称为：" + (String)this.markCoupon.getCouponName());
		}
			
		try {
			getResponse().getWriter().print(JSONObject.fromObject(param));
		} catch (IOException ioe) {
			
		}		
	}
	
	public void setInsertMarkCoupon() {
		// 设定有效时间天数
		if (null == this.markCoupon.getTermOfValidity()) {
			if (null != this.markCoupon.getBeginTime() 
					&& null != this.markCoupon.getEndTime()) {
				Integer days = DateUtil.getDaysBetween(
						markCoupon.getBeginTime(), markCoupon.getEndTime());
				markCoupon.setTermOfValidity(days.longValue());
			}
		} else {
			//markCoupon.setBeginTime(new Date());
			markCoupon.setTermOfValidity(this.markCoupon.getTermOfValidity());
		}
		
		if (!this.markCoupon.getWithCode().equals("true")) {//优惠活动默认为A类优惠券
			this.markCoupon.setFirstCode("HD");
			this.markCoupon.setCouponType(Constant.COUPON_TYPE.A.name());
		}
		
		//设定号码开头字符为大写
		if(!markCoupon.getFirstCode().isEmpty()){
			markCoupon.setFirstCode(this.markCoupon.getFirstCode().toUpperCase());
		}else{
			markCoupon.setFirstCode("");
		}
		
		if (null == markCoupon.getArgumentX() && null != this.argumentXYuan) {
			markCoupon.setArgumentX(PriceUtil.convertToFen(argumentXYuan));
		}
		if (null == markCoupon.getArgumentY() && null != this.argumentYYuan) {
			markCoupon.setArgumentY(PriceUtil.convertToFen(argumentYYuan));
		}
		if (null == markCoupon.getArgumentZ() && null != this.argumentZYuan) {
			markCoupon.setArgumentZ(PriceUtil.convertToFen(argumentZYuan));
		}
		//设定有效
		markCoupon.setValid("true");
		//设置最大优惠金额
		if(markCoupon.getMaxCoupon()!=null && markCoupon.getMaxCoupon()!=-1){
			markCoupon.setMaxCoupon(PriceUtil.convertToFen(markCoupon.getMaxCoupon()));
		}
		if(markCoupon.getUsedCoupon()==null ||"".equals(markCoupon.getUsedCoupon())){
			markCoupon.setUsedCoupon(0l);
		}

	}

	public Long getCouponId() {
		return couponId;
	}

	public void setCouponId(final Long couponId) {
		this.couponId = couponId;
	}

	public void setMarkCouponService(final MarkCouponService markCouponService) {
		this.markCouponService = markCouponService;
	}

	public MarkCoupon getMarkCoupon() {
		return markCoupon;
	}

	public void setMarkCoupon(final MarkCoupon markCoupon) {
		this.markCoupon = markCoupon;
	}

	public void setCouponCodeId(final Long couponCodeId) {
		this.couponCodeId = couponCodeId;
	}

	public void setCouponCode(final String couponCode) {
		this.couponCode = couponCode;
	}

	public void setArgumentXYuan(Float argumentXYuan) {
		this.argumentXYuan = argumentXYuan;
	}

	public void setArgumentYYuan(Float argumentYYuan) {
		this.argumentYYuan = argumentYYuan;
	}

	public void setArgumentZYuan(Float argumentZYuan) {
		this.argumentZYuan = argumentZYuan;
	}
}
