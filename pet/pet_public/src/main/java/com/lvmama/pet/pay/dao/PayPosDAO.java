package com.lvmama.pet.pay.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.pet.po.pay.PayPos;
import com.lvmama.comm.pet.vo.Page;

/**
 * 交通银行POS终端操作DAO.
 * @author huyunyan
 */
public class PayPosDAO extends BaseIbatisDAO{
	
	/**
	 * 取符合查询条件的数据.
	 * @param parameter
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Page<PayPos> selectPosPageByParam(Map<String, String> para,Page page){
		Long totalResultSize = (Long) super.queryForObject("PAY_POS.selectByParamCount", para);
		//分页查询
		page.setTotalResultSize(totalResultSize);				
		para.put("startRows", String.valueOf(page.getStartRows()));				
		para.put("endRows", String.valueOf(page.getEndRows()));
		page.setItems(super.queryForList("PAY_POS.selectQueryList", para));
		return page;
	}
	
	/**
	 * 新增POS终端.
	 * @param payPos
	 */
	public Long insert(PayPos payPos){
		return (Long)super.insert("PAY_POS.insert",payPos);
	}
	/**
	 * 更新Pos终端.
	 * @param payPos
	 */
	public void update(PayPos payPos){
		super.update("PAY_POS.update",payPos);
	}
	/**
	 * 更新POS终端状态.
	 */
	public void updateStatus(PayPos payPos) {
		super.update("PAY_POS.updateStatus",payPos);
	}
	/**
	 * 查询Pos终端.
	 * @param param
	 * @return
	 */
	public List<PayPos> queryPayPosList(Map param){
		return (List<PayPos>)super.queryForList("PAY_POS.queryByParam", param);
	}
	/**
	 * 获得查询个数.
	 * @param param
	 * @return
	 */
	public Long getSelectCount(Map param){
		try{
			return (Long)super.queryForObject("PAY_POS.selectByParamCount",param);
		}catch(Exception ex){
			return 0L;
		}
	}
	/**
	 * 根据Id查询Pos终端.
	 * @param param
	 * @return
	 */
	public PayPos selectById(Long param){
		return (PayPos)super.queryForObject("PAY_POS.selectById",param);
	}
	/**
	 * 得到查询终端查询结果.
	 */
	public List<HashMap> selectQueryList(Map param){
		return (List<HashMap>)super.queryForList("PAY_POS.selectQueryList", param);
	}
	
	/**
	 * 根据组装的条件返回POS机信息.
	 * @param param
	 * @return
	 */
	public PayPos queryPayPosBy(Map param){
		List<PayPos> list = (List<PayPos>)super.queryForList("PAY_POS.selectByTerNOAndStatus", param);
		PayPos payPos=new PayPos();
		if(list!=null&&list.size()>0){
			payPos=list.get(0);
		}
		return payPos;
	}
	/**
	 * 用终端号模糊查询
	 * @author ZHANG Nan
	 * @param terminalNo
	 * @return
	 */
	public List<PayPos> selectLikeTerminalNo(String search){
		return (List<PayPos>)super.queryForList("PAY_POS.selectLikeTerminalNoOrMemo", search);
	}
}
