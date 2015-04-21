package com.lvmama.tnt.partner.biz.service;

import com.lvmama.tnt.partner.comm.RequestVO;
import com.lvmama.tnt.partner.comm.ResponseVO;
import com.lvmama.vst.api.biz.vo.DistrictSignRequestVo;
import com.lvmama.vst.api.biz.vo.DistrictSignVo;
import com.lvmama.vst.api.vo.PageVo;

/**
 * 地理位置查询接口
 * 
 * @author gaoyafeng
 * 
 */
public interface DistrictSignService {
	/**
	 * 根据ID查询地理位置
	 * 
	 * @param distrSignId
	 *            地理位置ID
	 * @return 地理位置
	 */
	ResponseVO<DistrictSignVo> findDistrictSignDetail(
			RequestVO<Long> distrSignIdInfo);

	/**
	 * 查询地理位置分页列表
	 * 
	 * @param pageParam
	 *            地理位置分页对象
	 * @param districtSignRequestVo
	 *            地理位置查询对象 （可为空）
	 * @return 地理位置分页列表
	 */
	ResponseVO<PageVo<DistrictSignVo>> findDistrictSignList(
			RequestVO<DistrictSignRequestVo> districtSignRequestVo,
			PageVo<DistrictSignVo> pageParam);
}
