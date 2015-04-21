package com.lvmama.comm.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lvmama.comm.vo.Constant.CHANNEL;
import com.lvmama.comm.vo.Constant.USER_MEMBER_GRADE;


/**
 * 会员等级工具类，此类包含了所有会员等级相关的静态方法
 * @author yuzhizeng
 *
 */
public final class MemberGradeUtil {

	/**
	 * 依据金额返回会员等级
	 * @param amount 金额(元)
	 * @return USER_MEMBER_GRADE 会员等级枚举
	 */
	public static final USER_MEMBER_GRADE getUserMemberGrade(final float amount){
		for (int i = 0 ; i < AMOUT_GRADE_ARRAY.length ; i++) {
			if (amount < AMOUT_GRADE_ARRAY[i]) {
				return USER_MEMBER_GRADE_ARRAY[i];
			}
		}
		return USER_MEMBER_GRADE_ARRAY[0];
	}
	
	/**
	 * 比较两个等级，1代表大于，0代表等于，-1代表小于
	 * @param currentGrade  当前等级
	 * @param featureGrade  未来等级
	 * @return 是否升级
	 */
	public static int compareGrade(final USER_MEMBER_GRADE currentGrade, final USER_MEMBER_GRADE featureGrade) {
		if (null == currentGrade) {
			return -1;
		}
		if (null == featureGrade) {
			return 1;
		}
		if (currentGrade.getValue() ==  featureGrade.getValue()) {
			return 0;
		} 
		if (currentGrade.getValue() >  featureGrade.getValue()) {
			return 1;
		} else {
			return -1;
		}
	}

	/**
	 * 返回会员等级枚举
	 * @param grade 
	 * @return USER_MEMBER_GRADE 会员等级枚举
	 */
	public static final USER_MEMBER_GRADE getUserMemberGrade(final String grade) {
		for (USER_MEMBER_GRADE u : USER_MEMBER_GRADE.values()) {
			if (u.getGrade().equalsIgnoreCase(grade)) {
				return u;
			}
		}
		return null;
	}
	
	/**
	 * 获取下一个等级(普通等级会员返回自己)
	 * @param oldUserMemberGrade 当前等级 
	 * @return USER_MEMBER_GRADE 下一等级
	 */
	public static final USER_MEMBER_GRADE getPriorUserMemberGrade(final USER_MEMBER_GRADE grade) {
		if (null == grade || USER_MEMBER_GRADE.NORMAL.equals(grade)) {
			return USER_MEMBER_GRADE.NORMAL;
		}
		for (int i = USER_MEMBER_GRADE_ARRAY.length - 2; i > 0 ; i--) {
			if (USER_MEMBER_GRADE_ARRAY[i].equals(grade)) {
				return USER_MEMBER_GRADE_ARRAY[i -1];
			}
		}
		return USER_MEMBER_GRADE.NORMAL;
	}
	
	//订单赠送积分系数
	public final static List< FixedOrderCoefficient> FIXED_ORDER_COFFICIENT_LIST = new ArrayList<FixedOrderCoefficient>();
	//属于网络下单方式
	public final static List<String> NETWORK_ORDER_STYLE = new ArrayList<String>();
	//用户等级所对应的金额
	public final static float[] AMOUT_GRADE_ARRAY = new float[] {
		0F,
		2000F,
		8000F,
		20000F,
		Float.MAX_VALUE
	};
	//用户等级
	public final static USER_MEMBER_GRADE[] USER_MEMBER_GRADE_ARRAY = new USER_MEMBER_GRADE[] {
		USER_MEMBER_GRADE.NORMAL,
		USER_MEMBER_GRADE.NORMAL,
		USER_MEMBER_GRADE.GOLD,
		USER_MEMBER_GRADE.PLATINUM,
		USER_MEMBER_GRADE.DIAMOND,
		USER_MEMBER_GRADE.DIAMOND
	};
	
	static {
		//电话下单
		FIXED_ORDER_COFFICIENT_LIST.add(new FixedOrderCoefficient(USER_MEMBER_GRADE.NORMAL.name(), "TELEPHONE", 1.0f));
		FIXED_ORDER_COFFICIENT_LIST.add(new FixedOrderCoefficient(USER_MEMBER_GRADE.GOLD.name(), "TELEPHONE", 1.5f));
		FIXED_ORDER_COFFICIENT_LIST.add(new FixedOrderCoefficient(USER_MEMBER_GRADE.PLATINUM.name(), "TELEPHONE", 1.8f));
		FIXED_ORDER_COFFICIENT_LIST.add(new FixedOrderCoefficient(USER_MEMBER_GRADE.DIAMOND.name(), "TELEPHONE", 2.0f));
		
        //网络下单
		FIXED_ORDER_COFFICIENT_LIST.add(new FixedOrderCoefficient(USER_MEMBER_GRADE.NORMAL.name(), "NETWORK", 1.5f));
		FIXED_ORDER_COFFICIENT_LIST.add(new FixedOrderCoefficient(USER_MEMBER_GRADE.GOLD.name(), "NETWORK", 1.8f));
		FIXED_ORDER_COFFICIENT_LIST.add(new FixedOrderCoefficient(USER_MEMBER_GRADE.PLATINUM.name(), "NETWORK", 2.0f));
		FIXED_ORDER_COFFICIENT_LIST.add(new FixedOrderCoefficient(USER_MEMBER_GRADE.DIAMOND.name(), "NETWORK", 2.5f));
		
		//下单方式
		NETWORK_ORDER_STYLE.add(CHANNEL.FRONTEND.name());
		//NETWORK_ORDER_STYLE.add(CHANNEL.ANDROID.name());
		NETWORK_ORDER_STYLE.add(CHANNEL.CLIENT.name());
		//NETWORK_ORDER_STYLE.add(CHANNEL.SYBAIN.name());
		}

	/**
	 * 返回订单系数
	 * @param grade  用户等级
	 * @param orderChannel 订单渠道
	 * @return 根据用户等级及下单渠道返回的赠送积分的系数
	 */
	public static final float getOrderCoefficient(final String grade, final String orderChannel) {
		if (null == grade || null == orderChannel) {
			return 0F;
		}
		String orderStyle = NETWORK_ORDER_STYLE.contains(orderChannel) ? "NETWORK" : "TELEPHONE";
		for (FixedOrderCoefficient foc : FIXED_ORDER_COFFICIENT_LIST) {
			if (foc.getMemberGrade().equalsIgnoreCase(grade) && foc.getOrderStyle().equals(orderStyle)) {
				return foc.getCoefficient();
			}
		}
		return 0F;
	}
	
	/**
	 * 返回积分系数
	 * @param grade 用户等级
	 * @return 积分系数
	 */
	public static final Map<String, Float> getCoefficientByGrade(String grade) { 
		Map<String, Float> coefficientMap = new HashMap<String, Float>(); 
		for (FixedOrderCoefficient foc : FIXED_ORDER_COFFICIENT_LIST) { 
			if (foc.getMemberGrade().equalsIgnoreCase(grade) ) { 
				coefficientMap.put(foc.getOrderStyle(),  foc.getCoefficient());
			} 
		} 
		return coefficientMap;
	}	
}

/**
 * 订单赠送积分系数类
 * @author Brian
 *
 */
class FixedOrderCoefficient {
	private String memberGrade;
	private String orderStyle;
	private float coefficient;
	
	/**
	 * 构造器
	 * @param memberGrade
	 * @param orderStyle
	 * @param coefficient
	 */
	FixedOrderCoefficient(final String memberGrade, final String orderStyle, final float coefficient) {
		this.memberGrade = memberGrade;
		this.orderStyle = orderStyle;
		this.coefficient = coefficient;
	}

	public String getMemberGrade() {
		return memberGrade;
	}

	public String getOrderStyle() {
		return orderStyle;
	}

	public float getCoefficient() {
		return coefficient;
	}
	
}
