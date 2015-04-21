package com.lvmama.comm.bee.service.prod;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lvmama.comm.bee.po.prod.ProdHotSellSeq;
import com.lvmama.comm.bee.po.prod.ProdHotel;
import com.lvmama.comm.bee.po.prod.ProdProduct;
import com.lvmama.comm.bee.po.prod.ProdProductBranch;
import com.lvmama.comm.bee.po.prod.ProdProductItem;
import com.lvmama.comm.bee.po.prod.ProdProductRelation;
import com.lvmama.comm.bee.po.prod.ProdRoute;
import com.lvmama.comm.bee.po.prod.ProdTicket;
import com.lvmama.comm.bee.po.prod.ProdTraffic;
import com.lvmama.comm.bee.po.prod.ProductGroup;
import com.lvmama.comm.bee.po.prod.TimePrice;
import com.lvmama.comm.bee.po.prod.ViewTravelTips;
import com.lvmama.comm.bee.vo.CalendarModel;
import com.lvmama.comm.bee.vo.ProdRouteDate;
import com.lvmama.comm.pet.po.place.Place;
import com.lvmama.comm.pet.po.prod.ProdAssemblyPoint;
import com.lvmama.comm.pet.po.prod.ProdEContract;
import com.lvmama.comm.pet.po.prod.ProdProductChannel;
import com.lvmama.comm.pet.po.pub.CodeItem;
import com.lvmama.comm.pet.vo.Page;
import com.lvmama.comm.utils.json.ResultHandle;
import com.lvmama.comm.vo.ViewBuyInfo;

public interface ProdProductService {
	
	//List<ProdProductPlace> selectPlaceIdByProductId(Long productId);
	/**
	 * 根据产品Id查询时间价格
	 * @param productId
	 * @param prodBranchId
	 * @param date
	 * @return
	 */
	TimePrice getTimePriceByProdId(Long productId, Long prodBranchId, Date date);
	
	/**
	 * 查询产品经理分页
	 */
	Integer selectManagerCount(Map params);
		
	/**
	 * 查询产品经理分页
	 */
	List<ProdProduct> selectManager(Map params);

	/**
	 * @author lipengcheng
	 *控制销售产品是否可售
	 **/
	
	void markIsSellable(Map params,String operatorName);
	
	/**
	 * 复制销售产品
	 * @param srcProductId 源产品id
	 * @return 新产品id
	 */
	Long copyProduct(Long srcProductId,String operatorName) throws Exception;
	
	/**
	 * 所有产品的ID列表
	 * @return
	 */
	Page<Long> selectAllProductId(long pageSize,long page);
	
	ProdProduct getProdProductPlaceById(Long productId);
	
	void updatePriceByProductId(Long productId);
	
	List<CodeItem> getChannelByProductId(Long productId);
	
	List<ProdProductChannel> selectChannelByProductId(Long productId);
	
	/**
	 * 根据产品ID获取渠道列表
	 * @param productId
	 * @return
	 */
	List<ProdProductChannel> getProductChannelByProductId(Long productId);

	/**
	 * 添加产品与渠道信息
	 * @param route
	 * @param channel
	 */
	ProdProduct addProductChannel(ProdProduct product,String[] channels,String operatorName);
	 
	/**
	 * 根据产品ID删除记录(修改VALID状态值)
	 * */
	void deleteProduct(Map params,String operatorName);
	 
	/**
	 * 更新销售产品
	 * @param product
	 * @param channels 产品渠道
	 * @param operatorName
	 */
	void updateProdProduct(ProdProduct product, String[] channels,String operatorName,String isClearProdModel);

	/**
	 * 根据ID获取一个销售产品
	 * @param productId
	 * @return 销售产品
	 */
	ProdProduct getProdProductById(Long productId, String type);
	
	/**
	 * 获取一个抽象产品
	 * @param productId
	 * @return
	 */
	ProdProduct getProdProductById(Long productId);
	
	List<ProdProductItem> getProductItems(Long productId);
	
	/**
	 * 查询一个产品下所有的类别打包的采购项数量
	 * @param productId
	 * @return
	 */
	public long selectProdProductBranchItemCount(Long productId);
	
	/**
	 * 保存自主打包的产品的时间价格表.
	 * @param bean
	 * @param operatorName
	 */
	List<Date> saveSelfPackTimePrice(TimePrice bean,Long productId,String operatorName);
	
	void saveTimePrice(TimePrice bean, Long productId,String operatorName);
	void saveLastCancelHour(TimePrice bean,Long productId,String operatorName);
	List<TimePrice> selectProdTimePriceByProductId(Long productId,Long prodBranchId, Long beginTime, Long endTime);
	
	/**
	 * 通过销售产品ID查询时间价格表上最晚取消小时数不为空的个数
	 * @param productId
	 * @return
	 */
	public Integer selectProdTimePriceCountByProductId(Long productId);
	
	/**
	 * 
	 * @param productId
	 * @param prodBranchId
	 * @param beginTime
	 * @param endTime
	 * @param selfPack 是否是自主打包.
	 * @return
	 */
	CalendarModel selectProdTimePriceByProdBranchId(Long productId,Long prodBranchId, Long beginTime, Long endTime,boolean selfPack);
	
	public List<ProdProduct> selectProductByParms(Map map);
	
	Long getProductNearMarketPrice(Long productId);
	void updateByPrimaryKey(ProdProduct pp,String operatorName);
	
	/**
	 * 更新线路当中的其他属性
	 * 
	 * @param route
	 * @param operatorName
	 */
	void updateRouteOther(ProdRoute route,String operatorName);
	/**
	 * 查询线路产品
	 * @param map
	 * @return
	 */
	List<ProdProduct> selectRouteProductByParam(Map map);
	/**
	 * 产品查询suggest提示专用
	 * @param name
	 * @return
	 */
	List<ProdProduct> selectSuggestInfoByPlacesName(String name);
	
	ProdRoute selectProdRouteByPrimaryKey(Long id);
	
	ProdTicket selectProdTicketByPrimaryKey(Long id);
	
	ProdHotel selectProdHotelByPrimaryKey(Long id);
	/**
	 * 查询主产品的附加产品
	 * @param record
	 * @return
	 */
	List<ProdProduct> getAdditionalProdProductByProductId(Long productId);
 
	boolean checkResourceNeedConfirm(Long productId, Date specDate);
	
	
	public Long selectBizCodeByProductId(Long productId);
	
	/**
	 * 根据ID与类型查询采购产品
	 * @param metaProductId ID
	 * @param type TICKET HOTEL, ROUTE, OTHER
	 * @return
	 */
	ProdProduct getProdProduct(Long ProdProductId);
	
	/**
	 * 获取下拉框组列表
	 * @return
	 */
	public List<ProductGroup> getProductGroupList();
	
	public List<ProdProduct> selectBizCode(Map param);	
	public Integer selectRowCount(Map searchConds);
	
	public void updateTimeById(Long productId,Date onlineTime,Date offlineTime,String operatorName);
	public ProdHotel getProdHotelById(Long productId);
	public Long saveAssembly(ProdAssemblyPoint ap,String operatorName);
	public List<ProdAssemblyPoint> queryAssembly(Long productId);
	public void delAssembly(Long assemblyPointId,String operatorName);
	
	/**
	 * 保存合同信息
	 * @param prodEContract
	 */
	void saveEContract(final ProdEContract prodEContract);
	
	/**
	 * 获取合同信息
	 * @param productId
	 * @return
	 */
	ProdEContract getProdEContractByProductId(final Long productId);

	boolean isProductSellable(Long prodBranchId,Long quantity,Date visitTime);

	/**
 	 * 批量更新产品经理，同时对没有组织ID的产品将其赋予新产品经理的ID
 	 * */
 	public void updateManager(HashMap params);
 	/**
	 * 批量修改销售产品id
	 * */
	public void updateOrgIds(Map<String, Object> params);
	/**
	 * 读取一个产品的结算价与销售价,在没有读取数据时值为0
	 * @param product
	 * @param date
	 * @return price对应销售价,
	 * 			settlementPrice对应结算价
	 */
	public TimePrice getProductPrice(Long product,Date date);
	/**
	 * 更新一句话推荐
	 * @param prodProduct
	 */
	void updateProdRecommendWord(ProdProduct prodProduct);
	List<ProdProductItem> selectProductByMetaId(Long productId);
 
	public Long selectCountProductListByParams(Map<String, Object> params);

	/**
	 * @author shihui
	 * 产品列表查询
	 * */
	public List<ProdProduct> selectProductListByParams(Map<String, Object> params);
	
	/**
	 * @author shihui
	 * 线路产品列表查询
	 * */
	public List<ProdRouteDate> selectRouteProductListByParams(Map<String, Object> params);
	
	/**
	 * @author shihui
	 * 类别列表查询
	 * */
	public List<ProdProductBranch> selectProductBranchListByParams(Map<String, Object> params);
	
	public Long selectCountRouteListByParams(Map<String, Object> params);
	
	/**
	 * 线路产品搜索符合条件的产品id列表
	 * */
	public List<Long> selectRouteProductIdsByParams(Map<String, Object> params);
	
	public ProdRoute getProdRouteById(Long productId);
	
	public Place getFromDestByProductId(Long productId);
	public boolean isSellProductByChannel(Long productId, String channel);
	/**
	 * 查询类别的最晚取消时间
	 * @param branchIdList
	 * @param visitTime
	 * @return
	 */
	public Date getProductsLastCancelTime(List<Long> branchIdList, Date visitTime);

	 /**
	  * 查找产品的上车地点
	  * @param productId
	  * @return
	  */
	 List<ProdAssemblyPoint> getAssemblyPoints(Long productId);

	/**
	 * 取销售产品的类别,包含产品的详细信息.
	 * @param productId
	 * @param removeBranchId
	 * @param visitTime
	 * @param additional
	 * @param checkOnline 
	 * @return
	 */
	List<ProdProductBranch> getProductBranchDetailByProductId(Long productId, Long removeBranchId,Date visitTime,String additional,boolean checkOnline);

	/**
	 * 查询某个产品下面的产品类别列表
	 * 
	 * @param productId
	 * @param additional(是否为附加产品),为空时全部读取
	 * @return
	 */
	List<ProdProductBranch> getProductBranchByProductId(Long productId, String additional);
	/**
	 * 查找主产品相关的附产品和付加产品
	 * 
	 * @param productId
	 * @param visitTime
	 * @return
	 */
	List<ProdProductRelation> getRelatProduct(Long productId, Date visitTime);
	ProdProductBranch getProdBranchDetailByProdBranchId(Long prodBranchId,Date visitTime,boolean checkOnline);
	ProdProductBranch getPhoneProdBranchDetailByProdBranchId(Long prodBranchId,Date visitTime,boolean checkOnline);
	/**
	 * 检查销售产品类别是否可卖，库存足够或可超卖
	 * 
	 * @param prodBranchId 类别ID
	 *            Map<销售产品id,订购数量>
	 * @param visitTime
	 *            游玩时
	 * @return
	 */
	boolean isSellable(Long prodBranchId, Long quantity, Date visitTime);
	 /**
	  * 检查行程是否选满
	  * @param productId
	  * @param adult
	  * @param child
	  * @param ordprodlistmMap
	  * @return <code>false</code>说明行程没有选满
	  */
	 boolean checkJourneyRequird(ViewBuyInfo buyInfo);
	 
	 TimePrice calcProdTimePrice(final Long prodBranchId,final Date specDate);
	 /**
	  * @deprecated
	  * @param prodBranchId
	  * @param specDate
	  * @param isAnniversary
	  * @return
	  */
	 TimePrice calcProdTimePrice(final Long prodBranchId, final Date specDate, boolean isAnniversary);
	 List<Long> selectProductIdsByLikeProductName(String productName);
	 ProdProductBranch getProductDefaultBranchByProductId(Long productId);
	 List<TimePrice> getMainProdTimePrice(Long productId,Long branchId);
	 List<CalendarModel> selectSaleTimePriceByProductId(Long productId);
	 List<CalendarModel> selectSaleTimePrice(Long prodBranchId);
	
	 public Integer checkTimePriceByProductId(Long productId,String isSelftSign);
	 
	 void updateTraffic(Long productId,Long metaProductId);

	 /**
	  * 根据采购类别Id查询销售产品
	  * @param metaBranchId
	  * @return
	  */
	public List<ProdProduct> selectProductByMetaBranchId(Long metaBranchId);
	 
	public void insertTimePrice(TimePrice prodTimePrice, TimePrice metaTimePrice);
		
	public void updateTimePrice(TimePrice prodTimePrice, TimePrice metaTimePrice);

	
	public void updateDynamicTimePrice(TimePrice prodTimePrice, TimePrice metaTimePrice);

	
	public void updateTimePriceNoMultiJourney(TimePrice prodTimePrice, TimePrice metaTimePrice);
	
	/**
	 * 查处所有可售卖团购产品ID
	 * @param startRow
	 * @param endRow
	 * @return
	 */
	List<Long> getAllGroupProdIds(Long startRow, Long endRow);

	Long getGroupProdIdCount();

	ProdProduct selectProductByProdBranchId(Long prodBranchId);

	/**
	 * 查询产品的时间价格表中最大的时间
	 * 
	 * @param productId
	 * @return
	 */
	Date selectMaxTimePriceByProductId(Long productId);
	 /**
	 * 不定期产品时间价格表校验
	 * */
	public ResultHandle aperiodicTimePriceValidation(TimePrice bean, Long prodBranchId);
	
	/**@author shihui
	 * 期票产品数量查询
	 * */
	public Long selectCountQiPiaoProductListByParams(Map<String, Object> params);
	
	/**
	 * @author shihui
	 * 期票产品列表查询
	 * */
	public List<ProdProduct> selectQiPiaoProductListByParams(Map<String, Object> params);
	
	/**
	 * @author shihui
	 * 期票产品类别列表信息查询
	 * */
	public List<ProdProductBranch> selectQiPiaoBranchListByParams(Map<String, Object> params);
	/**
	 * 检查被打包的采购产品对应的供应商的合同审核状态 add by shihui
	 * */
	public ResultHandle checkAllMetaSupplierContractStatus(Long productId);
	
	public void updatePaymentTarget(Map<String,Object> param);
	
	/**
	 * 根据车次名称获取车次销售产品信息
	 * @param fullName
	 * @return
	 */
	public ProdTraffic getTrainProduct(String fullName);

	/** 查询销售时间价格列表*/
	public List<TimePrice> selectProdTimePriceByParams(Map<String,Object> param);
	
	/** 更新时间价格表的多行程外键*/
	public void updateTimePriceByViewMultijourney(TimePrice timeprice);

	/**
	 * 根据销售产品ID获取最近的可售的时间价格表
	 * @param productId
	 * @return
	 */
	public TimePrice selectLowestPriceByProductId(Long productId);

	/**
	 * 根据销售产品ID获取相关的旅行须知
	 * @param productId
	 * @return
	 */
	public List<ViewTravelTips> selectViewTravelTipsByProductId(Long productId);

    public void auditing(Long productId, String auditingStatus, String operatorName);
    
    /**
     * 批量修改返现
     * */
    public void updateRefundByProductIds(Map<String, Object> params);
    
    /**
     * 校验是否有敏感词并更新
     * */
    public boolean checkAndUpdateIsHasSensitiveWords(Long productId);
    
	public List<ProdProduct> selectProdToPlaceProduct(String productChannel,long placeId,int maxCount,long productId,String incodeProdId);
	/**
	 * 
	 * @param prodBranchId
	 * @param sellPrice
	 * @return
	 */
	public TimePrice buildTimePriceByPriceAndBranchId(Long prodBranchId, Long sellPrice);
	
	/**更新产品的敏感词标识*/
	public void markProductSensitive(Long productId, String hasSensitiveWord);
	
	/**热销推荐新规则**/
	public List<ProdProduct> queryHotSeqByProdTypeAndPlaceId(String prodPlaceIds,long orderCreateTime,String productType,String subProductType,String regionName,long endRow);
	
	/**热销排行临时表保存操作**/
	public void insertProdHotSell(ProdHotSellSeq phss);
	
	/**热销排行临时表删除操作**/
    public void deleteProdHotSell();
    
    /**热销排行临时表查询操作**/
    public Map<String,List<ProdHotSellSeq>> queryProdHotSell(String channel,String baseChannel);
	/**
	 * 获取销售产品最低可返现金额
	 * @param productId 产品id
	 * @return 最低可返现金额(分)
	 */
	public long getProductBonusReturnAmount(Long productId);
	/**
	 * 获取销售产品最低可返现金额
	 * @param productId 产品
	 * @return 最低可返现金额(分)
	 */
	public long getProductBonusReturnAmount(ProdProduct prodProduct);
	
}
