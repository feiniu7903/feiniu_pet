package com.lvmama.comm.vst.service;

import java.util.List;

import com.lvmama.comm.vst.vo.VstProdGoodsVo;
import com.lvmama.comm.vst.vo.VstProdProductVo;

public interface VstProdProductService {

	VstProdProductVo findProdProductListById(Long id);
	
	List<VstProdProductVo> findProdProductListByBlurName(String blurName);
	
	/**
	 * 获取商品信息
	 * @param goodsId 商品id
	 * @return 商品信息
	 */
	VstProdGoodsVo getVstProdGoodsVo(Long goodsId);
}
