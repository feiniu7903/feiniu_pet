package com.lvmama.ord.dao;
/**
 * 合同日志DAO
 * 作者：尚正元
 * 时间：2011-11-09，发工资前一天
 */
import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.bee.po.ord.OrdEContractLog;

public class OrdEContractLogDAO  extends BaseIbatisDAO{
	/**
	 * 插入合同日志
	 * @param object
	 * @return
	 */
	public OrdEContractLog insert(final OrdEContractLog object){
		 super.insert("ORD_ECONTRACT_LOG.insert", object);
		return object;
	}
}
