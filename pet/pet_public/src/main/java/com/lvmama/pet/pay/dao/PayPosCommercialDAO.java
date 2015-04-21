package com.lvmama.pet.pay.dao;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.pet.po.pay.PayPosCommercial;
import com.lvmama.comm.pet.vo.Page;

/**
 * 交通银行POS商户DAO.
 * @author huyunyan.
 */
public class PayPosCommercialDAO extends BaseIbatisDAO{
	/**
	 * 插入商户.
	 * @param param
	 */
	public Long insert(PayPosCommercial param) {
		return (Long)super.insert("PAY_POS_COMMERCIAL.insert",param);
	}
	/**
	 * 更新商户信息.
	 * @param param
	 */
	public void update(PayPosCommercial param) {
		super.update("PAY_POS_COMMERCIAL.update",param);
	}
	/**
	 * 查询商户信息.
	 * @param param
	 * @return
	 */
	public List<PayPosCommercial> select(Map param) {
		return (List<PayPosCommercial>)super.queryForList("PAY_POS_COMMERCIAL.queryByParam", param);
	}
	/**
	 * 更改商户状态.
	 * @param param
	 */
	public void updateStatus(PayPosCommercial param) {
		super.update("PAY_POS_COMMERCIAL.updateStatusById",param);
	}
	
	/**
	 * 更改POS商户供应商..
	 * @param param
	 */
	public void updateSupplier(PayPosCommercial param) {
		super.update("PAY_POS_COMMERCIAL.updateSupplierById",param);
	}
	
	/**
	 * 根据ID得到单个商户.
	 * @param param
	 * @return
	 */
	public PayPosCommercial selectById(Long param) {
		return (PayPosCommercial)super.queryForObject("PAY_POS_COMMERCIAL.selectById",param);
	}
	/**
	 * 得到查询个数.
	 * @param param
	 * @return
	 */
	public Long getSelectCount(Map param){
		try{
			return (Long)super.queryForObject("PAY_POS_COMMERCIAL.selectCount",param);
		}catch(Exception ex){
			return 0L;
		}
	}
	
	/**
	 * 取符合查询条件的数据.
	 * @param parameter
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Page<PayPosCommercial> selectByParam(Map<String, String> para,Page page){
		Long totalResultSize = (Long) super.queryForObject("PAY_POS_COMMERCIAL.selectCount", para);
		//分页查询
		page.setTotalResultSize(totalResultSize);				
		para.put("startRows", String.valueOf(page.getStartRows()));				
		para.put("endRows", String.valueOf(page.getEndRows()));
		page.setItems(super.queryForList("PAY_POS_COMMERCIAL.queryByParam", para));
		return page;
	}
	
	/**
	 * 根据组装的条件返回商户信息.
	 * @param param
	 * @return
	 */
	public List<PayPosCommercial> queryByParam(Map param){
		return (List<PayPosCommercial>)super.queryForList("PAY_POS_COMMERCIAL.selectByParam", param);
	}
	
}
