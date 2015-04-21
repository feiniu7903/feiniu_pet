package com.lvmama.comm.pet.service.pay;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lvmama.comm.pet.po.pay.PayPos;
import com.lvmama.comm.pet.vo.Page;

/**
 * 交通银行POS机终端服务.
 * @author huyunyan
 *
 */
public interface PayPosService {
	
	
	/**
	 * POS商户数据列表.
	 * 
	 * @param parameter
	 * @return
	 */
	Page<PayPos> selectPayPosPageByParam(Map<String, String> parameter,
			Long pageSize, Long page);
	/**
	 * 新增POS终端.
	 * @param commPos
	 */
	Long insert(PayPos commPos, String operatorName);
	/**
	 * 更改POS终端状态.
	 * @param commPos
	 */
	void modifyStatus(PayPos commPos,String operatorName,String type);	
	/**
	 * 更新POS终端信息.
	 * @param param
	 */
	void update(PayPos oldPayPos,PayPos payPos,String operatorName);
	/**
	 * 得到单个POS终端实体.
	 * @param param
	 * @return
	 */
	PayPos selectById(Long param);
	/**
	 * 查询得到POS终端列表.
	 * @param param
	 * @return
	 */
	List<PayPos> select(Map param);
	/**
	 * 得到查询个数.
	 * @param param
	 * @return
	 */
	Long getSelectCount(Map param);
	/**
	 * 获得查询页结果.
	 * @param param
	 * @return
	 */
	List<HashMap> selectQueryList(Map param);
	/**
	 * 用终端号模糊查询
	 * @author ZHANG Nan
	 * @param terminalNo
	 * @return
	 */
	public List<PayPos> selectLikeTerminalNo(String search);
}
