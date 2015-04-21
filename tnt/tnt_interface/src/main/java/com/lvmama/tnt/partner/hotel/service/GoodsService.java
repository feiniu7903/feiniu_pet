package com.lvmama.tnt.partner.hotel.service;

import java.util.Date;
import java.util.List;

import com.lvmama.tnt.partner.comm.RequestVO;
import com.lvmama.tnt.partner.comm.ResponseVO;
import com.lvmama.vst.api.hotel.prod.vo.GoodsRequestVo;
import com.lvmama.vst.api.hotel.prod.vo.GoodsTimePriceVo;
import com.lvmama.vst.api.hotel.prod.vo.GoodsVo;
import com.lvmama.vst.api.vo.PageVo;

/**
 * 商品查询接口
 * 
 * @author gaoyafeng
 * 
 */
public interface GoodsService {
	/**
	 * 根据商品Id查询商品详情
	 * 
	 * @param suppGoodsId
	 *            商品ID
	 * @return 商品详情
	 */
	public ResponseVO<GoodsVo> findGoodsDetail(
			RequestVO<Long> suppGoodsIdInfo);

	/**
	 * 根据参数查询商品
	 * 
	 * @param pageParam
	 *            商品分页对象
	 * @param goodsRequestVo
	 *            商品查询对象 （可为空）
	 * @return 商品分页列表
	 */
	public ResponseVO<PageVo<GoodsVo>> findGoodsList(
			RequestVO<GoodsRequestVo> goodsRequestVo, PageVo<GoodsVo> pageParam);

	/**
	 * 根据商品ID时间区间查询时间价格表
	 * 
	 * @param goodsIds
	 *            商品ID列表
	 * @param startDate
	 *            开始时间
	 * @param endDate
	 *            结束时间
	 * @return 时间价格表
	 */
	public ResponseVO<PageVo<GoodsTimePriceVo>> findGoodsTimePrice(
			RequestVO<List<Long>> goodsIds, Date startDate, Date endDate);
}
