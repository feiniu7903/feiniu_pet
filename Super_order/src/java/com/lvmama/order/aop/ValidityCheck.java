package com.lvmama.order.aop;

import java.lang.reflect.Method;

import org.springframework.aop.MethodBeforeAdvice;

import com.lvmama.comm.bee.vo.ord.BuyInfo;
import com.lvmama.comm.bee.vo.ord.CompositeQuery;
import com.lvmama.comm.utils.UtilityTool;

/**
 * 有效性校验.
 * 
 * <pre>
 * 方法的输入参数不能为空
 * </pre>
 * 
 * @author tom
 * @version Super二期 10/10/11
 * @since Super二期
 * @see com.lvmama.UtilityTool#isValid(Object)
 */
public final class ValidityCheck implements MethodBeforeAdvice {
	/**
	 * before.
	 * 
	 * <pre>
	 * Callback before a given method is invoked.
	 * </pre>
	 * 
	 * @param arg0
	 *            method being invoked
	 * @param arg1
	 *            arguments to the method
	 * @param arg2
	 *            target of the method invocation. May be null
	 * @throws Throwable
	 *             if this object wishes to abort the call. Any exception thrown
	 *             will be returned to the caller if it's allowed by the method
	 *             signature. Otherwise the exception will be wrapped as a
	 *             runtime exception.
	 */
	@Override
	public void before(final Method arg0, final Object[] arg1, final Object arg2)
			throws Throwable {
		int i = 1;
		for (Object o : arg1) {
			// 方法参数检查
			if (arg0.getName().equals("callbackForDrawMoney")) {
				// callbackForDrawMoney方法第一个参数不需要检查
				if (i > 1) {
					if (!UtilityTool.isValid(o)) {
						error(arg0.getName(), i);
					}
				}
			} else if (arg0.getName().equals("updateFaxMemo")) {
				if (i != 2) {
					if (!UtilityTool.isValid(o)) {
						error(arg0.getName(), i);
					}
				}
			} else if (arg0.getName().equals("queryOrderPersonCount")) {
				// queryOrderPersonCount方法只检查第一个参数
				if (i == 1) {
					if (!UtilityTool.isValid(o)) {
						error(arg0.getName(), i);
					}
				}
			} else if (arg0.getName().equals("updateFincMoneyDrawAuditStatus")) {
				// updateFincMoneyDrawAuditStatus方法第三个参数不需要检查
				if (i != 3) {
					if (!UtilityTool.isValid(o)) {
						error(arg0.getName(), i);
					}
				}
			} else if (arg0.getName().equals("updateOrderItemMetaFaxMemo")) {
				// updateOrderItemMetaFaxMemo方法第一个参数不需要检查
				if (i > 1) {
					if (!UtilityTool.isValid(o)) {
						error(arg0.getName(), i);
					}
				}
			} else if (arg0.getName().equals("createSettlement")) {
				if (i > 3) {
					if (!UtilityTool.isValid(o)) {
						error(arg0.getName(), i);
					}
				}
			} else if (arg0.getName().equals("updateOrdSettlementMemo")) {
				if (i > 1) {
					if (!UtilityTool.isValid(o)) {
						error(arg0.getName(), i);
					}
				}
			} else if (arg0.getName().equals("reCharge")) {
				if (i < 4) {
					if (!UtilityTool.isValid(o)) {
						error(arg0.getName(), i);
					}
				}
			}
			else if (arg0.getName().equals("resourceAmple")) {
				if (i != 4 && i !=3) {
					if (!UtilityTool.isValid(o)) {
						error(arg0.getName(), i);
					}
				}
			}else if(arg0.getName().equals("resourceLack")){
				if(i!=3){
					if (!UtilityTool.isValid(o)) {
						error(arg0.getName(), i);
					}
				}
			}
			else if (arg0.getName().equals(
					"queryOrdRefundmentByOrderIdAndStatus")) {
			} else if (arg0.getName().equals("applyDraw2Bank")) {
			} else if (arg0.getName().equals("queryMoneyAccountChangeLog")
					|| arg0.getName().equals("queryMoneyAccountChangeLogCount")) {
				if (o.getClass().getSimpleName().equals("CompositeQuery")) {
					final CompositeQuery compositeQuery = (CompositeQuery) o;
					if (!UtilityTool.isValid(compositeQuery
							.getMoneyAccountChangeLogRelate().getUserId())
							|| !UtilityTool.isValid(compositeQuery
									.getMoneyAccountChangeLogRelate()
									.getMoneyAccountChangeType())) {
						error(arg0.getName(), i);
					}
				}
			}else if (arg0.getName().equals("insertOrdPerform")) {
			}else if (arg0.getName().equals("updateCertificateStatusAndTypeOrConfirmChannel")) {
				//doEplacePassport E景通通关不需要参数检查
			}else if (arg0.getName().equals("checkVisitorIsExisted")) {
				// checkVisitorIsExisted方法不检查第三个参数
				if (i != 3) {
					if (!UtilityTool.isValid(o)) {
						error(arg0.getName(), i);
					}
				}
			} else if (arg0.getName().equals("updateOrderVisitTime")) {
				//updateOrderVisitTime ebooking更新订单时间不需要检查
			}else {
				if (!UtilityTool.isValid(o)) {
					error(arg0.getName(), i);
				}
			}
			// 方法参数属性检查
			if (arg0.getName().equals("cancelAndCreateNewOrder")) {
				if (i == 1) {
					final BuyInfo buyInfo = (BuyInfo) o;
					if (!UtilityTool.isValid(buyInfo.getUserId())) {
						error(arg0.getName(), i);
					} else if (!UtilityTool.isValid(buyInfo.getItemList())) {
						error(arg0.getName(), i);
					} else if (!UtilityTool.isValid(buyInfo.getPersonList())) {
						error(arg0.getName(), i);
					}
				}
			}
			i++;
		}
	}

	/**
	 * error.
	 * 
	 * @param methodName
	 *            方法名称
	 * @param number
	 *            参数序号
	 */
	private static void error(final String methodName, final int number) {
		throw new IllegalArgumentException("input parameter is null!" + "\r\n"
				+ methodName + " method number " + number
				+ " parameter is null!");
	}
}
