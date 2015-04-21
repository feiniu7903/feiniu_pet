package com.lvmama.clutter.utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.lvmama.clutter.model.MobileUserCoupon;
import com.lvmama.comm.bee.vo.view.MarkCouponUserInfo;
import com.lvmama.comm.pet.po.client.ClientUserCouponInfo;
import com.lvmama.comm.utils.ClientConstants;

public class CouponUtils {

	public static List<ClientUserCouponInfo> filterCouponInf(List<MarkCouponUserInfo> list,
			String state) {
		List<ClientUserCouponInfo> cciList = new ArrayList<ClientUserCouponInfo>();
		for (MarkCouponUserInfo markCouponUserInfo : list) {
			if (state.equals(ClientConstants.COUPON_STATE.NOT_USED.name())) {
				if ("false".equals(markCouponUserInfo.getMarkCouponCode().getUsed())
						&& !markCouponUserInfo.getMarkCoupon().isOverDue()) {
					cciList.add(setUserCouponInfo(markCouponUserInfo));
				}

			}
			if (state.equals(ClientConstants.COUPON_STATE.USED.name())) {
				if ("true".equals(markCouponUserInfo.getMarkCouponCode().getUsed())) {
					cciList.add(setUserCouponInfo(markCouponUserInfo));
				}

			}
			if (state.equals(ClientConstants.COUPON_STATE.HAS_EXPIRED.name())) {
				if (markCouponUserInfo.getMarkCoupon().isOverDue()) {
					cciList.add(setUserCouponInfo(markCouponUserInfo));
				}

			}
		}
		return cciList;
	}

	private static ClientUserCouponInfo setUserCouponInfo(MarkCouponUserInfo markCouponUserInfo) {
		ClientUserCouponInfo cci = new ClientUserCouponInfo();
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		cci.setName(markCouponUserInfo.getMarkCoupon().getCouponName());
		cci.setCode(markCouponUserInfo.getMarkCouponCode().getCouponCode());
		cci.setPrice(markCouponUserInfo.getMarkCoupon().getFavorTypeDescription());
		if(markCouponUserInfo.getMarkCoupon().getBeginTime()!=null&&markCouponUserInfo.getMarkCoupon().getEndTime()!=null){
			cci.setExpiredDate(sf.format(markCouponUserInfo.getMarkCoupon().getBeginTime()) + "至"
					+ sf.format(markCouponUserInfo.getMarkCoupon().getEndTime()));
		}
		
		cci.setExpiredData(cci.getExpiredDate());
		return cci;
	}
	
	/**
	 * 移动端专用 from v3 .
	 * @param list  优惠劵列表
	 * @param state 状态
	 * @return
	 */
	public static List<MobileUserCoupon> filterMobileUserCouponInf(List<MarkCouponUserInfo> list,
			String state) {
		List<MobileUserCoupon> cciList = new ArrayList<MobileUserCoupon>();
		for (MarkCouponUserInfo markCouponUserInfo : list) {
			if (state.equals(ClientConstants.COUPON_STATE.NOT_USED.name())) {
				if ("false".equals(markCouponUserInfo.getMarkCouponCode().getUsed())) {
					// 有效期是固定期限的 qin v3.1
					if("FIXED".equals(markCouponUserInfo.getMarkCoupon().getValidType())&& !markCouponUserInfo.getMarkCoupon().isOverDue()) {
						cciList.add(setMobileUserCouponInfo(markCouponUserInfo));
					// 有效期是非固定日期   qin v3.1 
					} else if("UNFIXED".equals(markCouponUserInfo.getMarkCoupon().getValidType())&& !markCouponUserInfo.getMarkCouponCode().isOverDue()) {
						cciList.add(setMobileUserCouponInfo(markCouponUserInfo));
					}

				}

			}
			if (state.equals(ClientConstants.COUPON_STATE.USED.name())) {
				if ("true".equals(markCouponUserInfo.getMarkCouponCode().getUsed())) {
					cciList.add(setMobileUserCouponInfo(markCouponUserInfo));
				}

			}
			if (state.equals(ClientConstants.COUPON_STATE.HAS_EXPIRED.name())) {
				if (markCouponUserInfo.getMarkCoupon().isOverDue()) {
					cciList.add(setMobileUserCouponInfo(markCouponUserInfo));
				}

			}
		}
		return cciList;
	}

	/**
	 * 移动端专用 from v3 .
	 * @param markCouponUserInfo
	 * @return
	 */
	private static MobileUserCoupon setMobileUserCouponInfo(MarkCouponUserInfo markCouponUserInfo) {
		MobileUserCoupon cci = new MobileUserCoupon();
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		cci.setName(markCouponUserInfo.getMarkCoupon().getCouponName());
		cci.setCode(markCouponUserInfo.getMarkCouponCode().getCouponCode());
		cci.setPrice(markCouponUserInfo.getMarkCoupon().getFavorTypeDescription());
		// 优惠券的有效期 
		if(null != markCouponUserInfo.getMarkCoupon().getBeginTime() && null != markCouponUserInfo.getMarkCoupon().getEndTime()) {
			cci.setExpiredDate(sf.format(markCouponUserInfo.getMarkCoupon().getBeginTime()) + "至"
					+ sf.format(markCouponUserInfo.getMarkCoupon().getEndTime()));
		} else if(null != markCouponUserInfo.getMarkCouponCode() && null !=markCouponUserInfo.getMarkCouponCode().getBeginTime() 
				&& null != markCouponUserInfo.getMarkCouponCode().getEndTime() ) {
			cci.setExpiredDate(sf.format(markCouponUserInfo.getMarkCouponCode().getBeginTime()) + "至"
					+ sf.format(markCouponUserInfo.getMarkCouponCode().getEndTime()));
		}else {
			cci.setExpiredDate("");
		}
		return cci;
	}
}
