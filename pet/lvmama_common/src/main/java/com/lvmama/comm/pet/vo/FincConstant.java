package com.lvmama.comm.pet.vo;

/**
 * 常量类
 * 
 * @author yanggan
 * 
 */
public class FincConstant {
	/**
	 * JQGRID查询传递的当前页的参数名称
	 */
	public final static String PAGE_CURRPAGE = "currpage";
	/**
	 * JQGRID查询传递的分页大小的参数名称
	 */
	public final static String PAGE_PAGESIZE = "pagesize";
	/**
	 * JQGRID查询传递的排序规则的参数名称
	 */
	public final static String PAGE_ORDER = "order";
	/**
	 * JQGRID查询传递的排序规则的参数名称
	 */
	public final static String PAGE_ORDERBY = "orderby";

	/**
	 * 分页查询开始位置
	 */
	public static String PAGE_START = "start";
	/**
	 * 分页查询结束位置
	 */
	public static String PAGE_END = "end";

	/**
	 * 贷
	 */
	public static String CREDIT = "CREDIT";

	/**
	 * 借
	 */
	public static String DEBIT = "DEBIT";
	/**
	 * 存入
	 */
	public static String DEPOSIT = "DEPOSIT";

	/**
	 * 转入
	 */
	public static String SHIFTIN = "SHIFTIN";

	/**
	 * 转出
	 */
	public static String SHIFTOUT = "SHIFTOUT";
	/**
	 * 退回
	 */
	public static String RETURN = "RETURN";
	/**
	 * 冲正
	 */
	public static String REVISION = "REVISION";
	/**
	 * 结算支付
	 */
	public static String PAYMENT = "PAYMENT";

	/**
	 * 预存款
	 */
	public static String ADVANCEDEPOSITS = "ADVANCEDEPOSITS";
	/**
	 * 现金
	 */
	public static String CASH = "CASH";
	/**
	 * 押金类型 - 押金
	 */
	public static String FOREGIFTS_KIND_CASH = "CASH";
	/**
	 * 押金类型 - 担保函
	 */
	public static String FOREGIFTS_KIND_GUARANTEE = "GUARANTEE";

	/**
	 * 结算单状态 - 已确认
	 */
	public static String SETTLEMENT_STATUS_CONFIRMED = "CONFIRMED";

	/**
	 * 结算单状态 - 已结算
	 */
	public static String SETTLEMENT_STATUS_SETTLEMENTED = "SETTLEMENTED";
	/**
	 * 结算单状态 - 未确认
	 */
	public static String SETTLEMENT_STATUS_UNSETTLEMENTED = "UNSETTLEMENTED";
	
	public static String SETTLEMENT_CHANGETYPE_ADD ="ADD";
	
	public static String SETTLEMENT_CHANGETYPE_MODIFY ="MODIFY";
	public static String SETTLEMENT_CHANGETYPE_MODIFY_TOTAL_PRICE ="MODIFY_TOTAL_PRICE";
	
	public static String SETTLEMENT_CHANGETYPE_DEL ="DEL";
	
	public static String SETTLEMENT_QUEUE_ITEM_STATUS_NORMAL = "NORMAL";
	
	public static String SETTLEMENT_QUEUE_ITEM_STATUS_PAUSE = "PAUSE";
	
	/**
	 * 团结算状态 - 未做成本
	 */
	public static String GROUP_SETTLEMENT_STATUS_UNCOST = "UNCOST";
	
	/**
	 * 团结算状态 - 已做成本
	 */
	public static String GROUP_SETTLEMENT_STATUS_COSTED = "COSTED";
	
	/**
	 * 团结算状态 - 确认成本
	 */
	public static String GROUP_SETTLEMENT_STATUS_CONFIRMED = "CONFIRMED";
	
	/**
	 * 团结算状态 - 已核算
	 */
	public static String GROUP_SETTLEMENT_STATUS_CHECKED = "CHECKED";
}
