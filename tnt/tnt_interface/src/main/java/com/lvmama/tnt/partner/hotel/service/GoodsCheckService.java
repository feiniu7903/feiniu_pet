package com.lvmama.tnt.partner.hotel.service;

import java.util.Date;

import com.lvmama.tnt.partner.comm.RequestVO;
import com.lvmama.tnt.partner.comm.ResponseVO;
import com.lvmama.vst.api.hotel.prod.vo.CheckResourceVo;

/**
 * 商品时间价格资源校验
 * 
 * @author gaoyafeng
 * 
 */
public interface GoodsCheckService {

	/**
	 * 校验商品时间价格资源(是否可订)
	 * 
	 * @param distributorId
	 *            分销商ID
	 * @param prodId
	 *            酒店ID
	 * @param productBranchId
	 *            房型ID
	 * @param goodsId
	 *            商品ID
	 * @param startDate
	 *            开始时间
	 * @param endDate
	 *            结束时间
	 * @param personNum
	 *            人数（非必须）
	 * @param roomNum
	 *            房间数（非必须）
	 * @return 资源可订结果
	 */
	public ResponseVO<CheckResourceVo> checkTimePrice(
			RequestVO<Long> prodIdInfo, Long distributorId,
			Long productBranchId, Long goodsId,String arrivalTime, Date startDate, Date endDate,
			Integer personNum, Integer roomNum);
}
