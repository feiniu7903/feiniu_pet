package com.lvmama.comm.vst.service;

import java.util.Date;
import java.util.List;

import com.lvmama.comm.utils.json.ResultHandle;
import com.lvmama.comm.utils.json.ResultHandleT;
import com.lvmama.comm.vst.vo.VstProdGoodsTimePriceVo;

/**
 * 类别（商品）及库存修改
 * <br/>Pet&Super系统实现的
 * @author ranlongfei 2013-12-20
 * @version
 */
public interface PetProdGoodsService {
	/**
	 * 上架
	 * 
	 * @author: ranlongfei 2013-12-20 下午6:31:37
	 * @param branchId 规格ID（商品）
	 * @return
	 */
	ResultHandle updateGoodsOnlineById(Long branchId);
	/**
	 * 下架
	 * 
	 * @author: ranlongfei 2013-12-20 下午6:32:55
	 * @param branchId 规格ID（商品）
	 * @return
	 */
	ResultHandle updateGoodsOfflineById(Long branchId);
	/**
	 * 新增修改时间价格表（VST商品）（super采购）
	 * 
	 * @author: ranlongfei 2013-12-20 下午6:34:19
	 * @param metaBranchId 规格ID（商品）对应super系统采购类别id
	 * @param timePrice 时间价格表
	 * @return
	 */
	ResultHandle saveGoodsTimePrice(Long metaBranchId,List<VstProdGoodsTimePriceVo> timePrice);
	/**
	 * 下单的库存扣减（super采购）
	 * 
	 * @author: ranlongfei 2013-12-20 下午6:16:48
	 * @param orderId
	 * @param metaBranchId 类别ID（商品） 对应super系统采购类别id
	 * @param stock 大于0 就增，小于0就减库存
	 * @param start 
	 * @param end 
	 * @return
	 */
	ResultHandle updateStockByOrder(Long orderId, Long metaBranchId, Long stock, Date start, Date end);
	/**
	 * 发起销售价格更新
	 * @param metaBranchId
	 * @param start
	 * @param end
	 */
	ResultHandle updateProdBranchPrice(Long metaBranchId,Date start,Date end);
	/**
	 * 根据采购类别ID ,是否有效查询出采购产品的valid
	 * @param metaBranchId,valid
	 * @return valid
	 */
	ResultHandleT<String> getMetaProductBranchValid(Long metaBranchId,String productType );
}
