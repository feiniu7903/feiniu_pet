/**
 * 
 */
package com.lvmama.comm.bee.service.prod;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.bee.po.prod.ProdJourneyProduct;
import com.lvmama.comm.bee.po.prod.ProdProductJourney;
import com.lvmama.comm.bee.po.prod.ProdProductJourneyPack;
import com.lvmama.comm.utils.json.ResultHandle;
import com.lvmama.comm.utils.json.ResultHandleT;

/**
 * 自由行专用行程服务.
 * @author yangbin
 *
 */
public interface ProdProductJourneyService {

	/**
	 * 保存一个行程段基本信息
	 * @param journey
	 * @param operatorName
	 * @return
	 */
	ProdProductJourney save(ProdProductJourney journey,String operatorName);
	
	/**
	 * 保存一个行程产品.
	 * @param product
	 * @param journeyId 行程id
	 * @param productType 产品类型
	 * @param operatorName
	 * @return
	 */
	ResultHandleT<ProdJourneyProduct> save(ProdJourneyProduct product,String operatorName);
	
	
	/**
	 * 删除一个行程段.
	 * @param pk
	 */
	void deleteProductJourney(Long pk,String operatorName);
	
	/**
	 * 删除一个行程当中的产品.
	 * @param pk
	 */
	void deleteJourneyProduct(Long pk,String operatorName);
	
	/**
	 * 修改行程产品属性
	 */
	void changeJourneyProdutProp();
	
	
	/**
	 * 
	 * @param productId
	 * @return
	 */
	List<ProdProductJourney> selectProductJourneyListByProductId(Long productId);
	
	/**
	 * 读取一个行程段里面的所有的产品，并分类排放
	 * @param prodJourneyId
	 * @param unique 是否需要针对产品对应多个类别时去掉复复
	 * @return
	 */
	Map<String,List<ProdJourneyProduct>> selectJourneyProductDetailMap(Long prodJourneyId,boolean unique);
	
	/**
	 * 取出一个行程当中的一类产品
	 * @param prodJourneyId 行程ID
	 * @param type 产品类型
	 * @return
	 */
	List<ProdJourneyProduct> selectJourneyProductListByJourneyType(Long prodJourneyId,String type);
	
	/**
	 * 针对产品去掉重复的打包产品列表
	 * @param prodJourneyId
	 * @param type
	 * @return
	 */
	List<ProdJourneyProduct> selectJourneyProductUniqueList(Long prodJourneyId,String type);
	
	List<ProdJourneyProduct> selectJourneyProductByGroup(Long journeyGroupId);
	
	ProdProductJourney selectProductJourneyByPK(Long prodJourenyId);
	
	/**
	 * 修改优惠金额.
	 * @param journeyProduct
	 */
	ResultHandle changeJourneyProdutDiscount(ProdJourneyProduct journeyProduct,String operatorName);
	
	/**
	 * 修改行程产品是否必选.
	 * @param journeyProduct
	 * @param selected
	 */
	void changeJourneyProductRequire(ProdJourneyProduct journeyProduct,String selected,String operatorName);
	
	/**
	 * 修改行程产品当中的默认产品，在同一行程当中同类型如果选中有排他性.
	 * @param journeyProduct
	 * @param selected
	 */
	void changeJourneyProductDefault(ProdJourneyProduct journeyProduct,String selected,String operatorName);
	
	/**
	 * 修改行程属性
	 * @param productJourney
	 * @param type
	 * @param selected
	 */
	void changeJourneyPolicy(ProdProductJourney productJourney,String type,String selected,String operatorName);
	
	void changeJourneyTime(ProdProductJourney productJourney,String operatorName);

	ProdProductJourneyPack queryProductJourneyPackByPackId(Long packId);

	List<ProdProductJourneyPack> queryProductJourneyPackByProductId(Long productId);

	void savePack(ProdProductJourneyPack prodProductJourneyPack,String operatorName);

	void deletePack(Long packId,String operatorName);

	void updatePackOnLine(ProdProductJourneyPack prodProductJourneyPack,String operatorName);
}
