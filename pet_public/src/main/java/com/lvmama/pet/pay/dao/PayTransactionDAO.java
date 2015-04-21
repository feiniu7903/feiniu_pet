package com.lvmama.pet.pay.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.pet.po.pay.PayTransaction;

public class PayTransactionDAO extends BaseIbatisDAO {
	/**
	 * 添加记录.
	 * @param payTransaction
	 * @return
	 */
	public Long insert(PayTransaction payTransaction){
		return (Long)super.insert("PAY_TRANSACTION.insert", payTransaction);
	}
	/**
	 * 根据订单ID获取{@link FincTransaction}.
	 * @param orderId
	 *            订单ID
	 * @return <pre>
	 * 指定订单ID的{@link FincTransaction}，
	 * 如果指定订单ID没有对应{@link FincTransaction}，则返回<code>null</code>
	 * </pre>
	 */
	public List<PayTransaction> selectPayTransactionByObjectId(Long orderId){
		final List<PayTransaction> list = super.queryForList("PAY_TRANSACTION.selectPayTransactionByObjectId", orderId);
			return list;
	}
	
	public List<PayTransaction> selectByParams(Map params) {
		  int skipResults = 0;
		  int maxResults = 10;
		  if (params.get("skipResults") != null) {
		   skipResults = Integer
		     .parseInt(params.get("skipResults").toString());
		  }
		  if (params.get("maxResults") != null) {
		   maxResults = Integer.parseInt(params.get("maxResults").toString());
		  }
		  return super.queryForList(
		    "PAY_TRANSACTION.selectByParams", params, skipResults,maxResults);
	}
}
