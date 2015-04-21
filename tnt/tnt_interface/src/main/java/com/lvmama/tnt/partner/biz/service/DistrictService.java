package com.lvmama.tnt.partner.biz.service;

import com.lvmama.tnt.partner.comm.RequestVO;
import com.lvmama.tnt.partner.comm.ResponseVO;
import com.lvmama.vst.api.biz.vo.DistrictRequestVo;
import com.lvmama.vst.api.biz.vo.DistrictVo;
import com.lvmama.vst.api.vo.PageVo;

/**
 * 供应商查询行政区划接口
 * 
 * @author gaoyafeng
 * 
 */
public interface DistrictService {

	/**
	 * 根据ID查询行政区划详情
	 * 
	 * @param distrId
	 *            行政区划ID
	 * @return 行政区划
	 */
	ResponseVO<DistrictVo> findDistrictDetail(RequestVO<Long> distrIdInfo);

	/**
	 * 根据分页条件查询行政区划分页列表
	 * 
	 * @param pageParam
	 *            行政区划分页对象
	 * @param districtRequestVo
	 *            行政区划查询对象 （可为空）
	 * @return 行政区划列表分页对象
	 */
	ResponseVO<PageVo<DistrictVo>> findDistrictList(
			RequestVO<DistrictRequestVo> districtRequestVo,
			PageVo<DistrictVo> pageParam);

}
