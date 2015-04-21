package com.lvmama.ord.dao;

import java.util.List;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.bee.po.ord.OrdEContractComment;

public class OrdEContractCommentDAO extends BaseIbatisDAO {
	/**
	 * 插入一条订单合同备注
	 * @param obj
	 * @return
	 */
	public OrdEContractComment insert(OrdEContractComment obj){
		Long id= (Long)super.insert("ORD_ECONTRACT_COMMENT.insert",obj);
		obj.setId(id);
		return obj;
	}
	/**
	 * 根据订单电子合同编号查询备注列表
	 * @param eContractId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<OrdEContractComment> queryByEContractId(final String eContractId){
		List<OrdEContractComment> result=(List<OrdEContractComment>)super.queryForList("ORD_ECONTRACT_COMMENT.queryByEContractId",eContractId);
		return result;
	}
	
	/**
	 * 根据订单ID查询备注.
	 * @param OrderId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public OrdEContractComment queryByOrderId(final Long orderId){
		List<OrdEContractComment> result=(List<OrdEContractComment>)super.queryForList("ORD_ECONTRACT_COMMENT.queryByOrderId",orderId);
		OrdEContractComment ordEContractComment=new OrdEContractComment();
		if(result!=null&&result.size()>0){
			ordEContractComment=result.get(0);
		}
		return ordEContractComment;
	}
	
}
