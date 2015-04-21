package com.lvmama.comm.vst.service;

import java.util.Date;
import java.util.List;
import com.lvmama.comm.bee.po.meta.MetaProductBranch;
import com.lvmama.comm.bee.po.prod.ProdProduct;
import com.lvmama.comm.bee.po.prod.ProdProductBranch;
import com.lvmama.comm.vst.vo.VstProdGoodsTimePriceVo;


/**
 * VST系统更新super产品信息服务
 * @author taiqichao
 *
 */
public interface VstProdUpdateService {

	/**
	 * 更新产品上下线状态
	 * @param prodProduct 销售产品
	 * @param prodProductBranch 销售产品类别
	 * @param onLine 是否上线
	 */
	void updateProductLineStatus(ProdProduct prodProduct,ProdProductBranch prodProductBranch, boolean onLine);

	/**
	 * 保存采购时间价格表
	 * @param metaProductBranch 采购类别
	 * @param timePrice 时间价格表
	 */
	void saveTimePrice(MetaProductBranch metaProductBranch,List<VstProdGoodsTimePriceVo> timePrice);

	/**
	 * 更新采购库存
	 * @param metaProductBranch 采购类别
	 * @param stock 库存增加值(正数增加，负数减少)
	 * @param start 开始时间
	 * @param end 结束时间
	 */
	void updateStock(MetaProductBranch metaProductBranch, Long stock, Date start,Date end);
	
	
}
