package com.lvmama.comm.bee.service.ebooking;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.bee.po.ebooking.EbkMultiJourney;
import com.lvmama.comm.bee.po.ebooking.EbkProdContent;
import com.lvmama.comm.bee.po.ebooking.EbkProdJourney;
import com.lvmama.comm.bee.po.ebooking.EbkProdProduct;
import com.lvmama.comm.bee.po.ebooking.EbkProdRelation;
import com.lvmama.comm.bee.po.ebooking.EbkProdSnapshot;
import com.lvmama.comm.bee.po.ebooking.EbkProdTimePrice;
import com.lvmama.comm.bee.po.prod.ProductModelProperty;
import com.lvmama.comm.pet.po.pub.ComPicture;

/**
 * 
 * EBooking 产品管理-快照
 *
 */
public interface EbkProdSnapshotService {
	
	
	/**
	 * 保存 -产品快照
	 * @author ZHANG Nan
	 * @param ebkProdProduct 产品对象
	 * @return 主键
	 */
	public Long saveProdSnapshot(EbkProdProduct ebkProdProduct);
	/**
	 * 根据产品ID删除最新的一个版本
	 * @author ZHANG Nan
	 * @param productId
	 * @return 影响行数
	 */
	public int deleteProdSnapshotByLast(Long productId);
	/**
	 * 根据产品ID获取最新的一个版本快照
	 * @author ZHANG Nan
	 * @param productId 
	 * @return 版本快照
	 */
	public EbkProdSnapshot getProdSnapshotByLast(Long productId);
	
	/**
	 * 获取产品最近的两个快照
	 * @author ZHANG Nan
	 * @param productId 产品ID
	 * @return 产品最近的两个快照
	 */
	public List<EbkProdProduct> getEbkProdProductVersionObj(Long productId);
	
	
	/**
	 * 比较产品基本信息
	 * @param ebkProdProductList
	 * @return
	 */
	public Map<String,Object> compareEbkProdProductBase(List<EbkProdProduct> ebkProdProductList);
	
	/**
	 * 比较产品经理推荐
	 * @param ebkProdProductList
	 * @return
	 */
	public Map<String,Object> compareEbkProdProductRecommend(List<EbkProdProduct> ebkProdProductList);
	
	/**
	 * 比较行程
	 * @param ebkProdProductList
	 * @return
	 */
	public Map<String, List<EbkProdJourney>> compareEbkProdProductTrip(List<EbkProdProduct> ebkProdProductList);
	
	/**
	 * 比较费用
	 * @param ebkProdProductList
	 * @return
	 */
	public Map<String,Object> compareEbkProdProductCost(List<EbkProdProduct> ebkProdProductList);
	
	/**
	 * 比较其他信息
	 * @param ebkProdProductList
	 * @return
	 */
	public Map<String,Object> compareEbkProdProductOther(List<EbkProdProduct> ebkProdProductList);
	
	/**
	 * 比较交通
	 * @param ebkProdProductList
	 * @return
	 */
	public Map<String, List<EbkProdContent>> compareEbkProdProductTraffic(List<EbkProdProduct> ebkProdProductList);
	
	/**
	 * 比较图片
	 * @param ebkProdProductList
	 * @return
	 */
	public Map<String, List<ComPicture>> compareEbkProdProductPics(List<EbkProdProduct> ebkProdProductList);
	
	/**
	 * 版本比较-tab页
	 * @author ZHANG Nan
	 * @param productId 产品ID
	 * @return 返回TAB页类型是否修改
	 */
	public Map<String,String> compareTabsChange(Long productId);
	
	
	/**
	 * 对比基础信息参数修改
	 * @author ZHANG Nan
	 * @param productId 产品ID
	 * @return 返回基础信息页参数修改
	 */
	public Map<String,Object> compareEbkProdProductBase(Long productId);
	/**
	 * 审核-产品推荐及特色比较
	 */
	public Map<String,Object> compareEbkProdProductRecommend(Long productId);
	/**
	 * 审核-行程描述比较
	 */
	public Map<String, List<EbkProdJourney>> compareEbkProdProductTrip(Long productId);
	/**
	 * 审核-费用说明比较
	 */
	public Map<String,Object> compareEbkProdProductCost(Long productId);
	/**
	 * 审核-多行程比较
	 */
	public Map<String, List<EbkMultiJourney>> compareEbkProdProductMultiTrip(Long productId);
	/**
	 * 审核-发车信息比较
	 */
	public Map<String, List<EbkProdContent>> compareEbkProdProductTraffic(Long productId);
	
	/**
	 * 审核-其它条款比较
	 */
	public Map<String,Object> compareEbkProdProductOther(Long productId);
	
	/**
	 * 审核-关联销售产品
	 * @param productId
	 * @return
	 */
	public Map<String,List<EbkProdRelation>> compareEbkProdProductRelation(Long productId);
	
	public Map<String,List<EbkProdRelation>> compareEbkProdProductRelation(List<EbkProdProduct> ebkProdProductList);
	
	
	/**
	 * 获取时间价格表 旧版本
	 * @author ZHANG Nan
	 * @param ebkProdProductId
	 * @return
	 */
	public List<EbkProdTimePrice> getOldEbkProdTimePrice(Long ebkProdProductId);	
	
	/**
	 * 查询产品类型对应的所有其他属性
	 * @param subProductType
	 * @return
	 */
	public List<ProductModelProperty> getModelPropertyList(String subProductType);
}
