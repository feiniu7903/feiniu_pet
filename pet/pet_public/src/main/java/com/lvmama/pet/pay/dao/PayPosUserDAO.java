package com.lvmama.pet.pay.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.pet.po.pay.PayPosUser;
import com.lvmama.comm.pet.vo.Page;

/**
 * 银行POS员工操作类.
 * @author huyunyan
 */
public class PayPosUserDAO extends BaseIbatisDAO{
	
	/**
	 * 取符合查询条件的数据.
	 * @param parameter
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Page<PayPosUser> selectPosUserPageByParam(Map<String, String> para,Page page){
		Long totalResultSize = (Long) super.queryForObject("PAY_POS_USER.selectByParamCount", para);
		//分页查询
		page.setTotalResultSize(totalResultSize);				
		para.put("startRows", String.valueOf(page.getStartRows()));				
		para.put("endRows", String.valueOf(page.getEndRows()));
		page.setItems(super.queryForList("PAY_POS_USER.selectQueryList", para));
		return page;
	}
	
	/**
	 * 插入POS员工.
	 * @param payPosUser
	 */
	public Long insert(final PayPosUser payPosUser){
		return (Long)super.insert("PAY_POS_USER.insert",payPosUser);
	}
	/**
	 * 更新POS员工数据.
	 * @param payPosUser
	 */
	public void update(final PayPosUser payPosUser){
		super.update("PAY_POS_USER.update",payPosUser);
	}
	/**
	 * 查找POS员工.
	 * @param param
	 * @return
	 */
	public List<PayPosUser> queryByParam(Map param) {
		return (List<PayPosUser>)super.queryForList("PAY_POS_USER.queryByParam",param);
	}
	/**
	 * 获得查找的个数.
	 * @param param
	 * @return
	 */
	public Long selectByParamCount(Map param){
		try{
			return (Long)super.queryForObject("PAY_POS_USER.selectByParamCount",param);
		}catch(Exception ex){
			return 0L;
		}
	}
	/**
	 * 获得单个POS员工.
	 * @param param
	 * @return
	 */
	public PayPosUser selectById(Long param){
		return (PayPosUser)super.queryForObject("PAY_POS_USER.selectById",param);
	}
	
	/**
	 * 更新POS员工状态.
	 */
	public void updatePosStatusById(Map params) {
		super.update("PAY_POS_USER.updatePosStatusById",params);
	}
	/**
	 * 得到查询终端查询结果.
	 */
	public List<HashMap> selectQueryList(Map param){
		return (List<HashMap>)super.queryForList("PAY_POS_USER.selectQueryList",param);
	}
	
	/**
	 * 根据用户的用户名和登陆密码取用户的响应信息.
	 * @param param
	 * @return
	 */
	public PayPosUser queryPosUserBy(Map param) {
		PayPosUser posUser = new PayPosUser();
		List<PayPosUser> list = (List<PayPosUser>)super.queryForList("PAY_POS_USER.selectByLogin",param);
		if(list!=null&&list.size()>0){
			posUser = list.get(0);
		}
	 return posUser;
	}
	public PayPosUser selectByEmpNo(String empNo) {
		return (PayPosUser)super.queryForObject("PAY_POS_USER.selectByEmpNo",empNo);
	}
	
}
