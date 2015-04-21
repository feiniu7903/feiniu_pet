package com.lvmama.comm.bee.service.prod;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.lvmama.comm.bee.po.prod.ProdProductBranch;
import com.lvmama.comm.bee.po.prod.ProdProductBranchItem;
import com.lvmama.comm.bee.po.prod.TimePrice;
import com.lvmama.comm.bee.po.prod.TimeRange;
import com.lvmama.comm.pet.vo.Page;
import com.lvmama.comm.utils.json.ResultHandle;
import com.lvmama.comm.utils.json.ResultHandleT;


/**
 * 销售产品类别
 * @author MrZhu
 *
 */
public interface ProdProductBranchService {
	 
	/**
	 * 根据采购产品类别查询打包的销售产品类别列表
	 * @param metaBranchId
	 * @return
	 */
	List<ProdProductBranch> getProductBranchByMetaProdBranchId(Long metaBranchId);
	/**
	 * 查询类别列表
	 * @param productId
	 * @param additional 是否是附加类别 可以为空
	 * @param online 是否上线，可以为空
	 * @return
	 */
	List<ProdProductBranch> getProductBranchByProductId(Long productId,String additional,String online);
	
	/**
	 * 查询类别
	 * @param productId
	 * @param additional
	 * @return
	 */
	List<ProdProductBranch> getProductBranchByProductId(Long productId, String additional);
	
	/**
	 * 保存类别信息;
	 * @param branch
	 * @return
	 */
	public ResultHandleT<ProdProductBranch> saveBranch(ProdProductBranch branch,String operatorName);
	
	/**
	 * 按类别ID取对象.
	 * @param pk
	 * @return
	 */
	public ProdProductBranch selectProdProductBranchByPK(Long pk);
	
	public void delete(Long pk);
	
	/**
	 * 更改产品上下线状态，由前一个状态直接变更到相反的状态
	 * @param pk 类别主键
	 * @param operatorName
	 */
	ResultHandle changeOnline(Long pk,String operatorName);
	
	/**
	 * 修改默认类别
	 * @param pk
	 * @param operatorName
	 */
	ResultHandle changeDef(Long pk,String operatorName);
	
	/**
	 * 修改默认类别
	 * @param branch
	 * @param operatorName
	 */
	ResultHandle changeDefEBK(ProdProductBranch branch,String operatorName);
	
	
	public List<ProdProductBranchItem> selectBranchItemByBranchId(Long branchId);	
	
	/**
	 * 打包一个采购产品.
	 * 
	 * @param item
	 * @param operator
	 * @return
	 * @throws BranchItemException
	 * @throws TimePriceException
	 *             direction 是<code>None</code>时，检查销售产品时间价格表有不存在采购价格时抛出
	 */
	public ProdProductBranchItem addItem(ProdProductBranchItem item,ProdProductBranch branch, String operatorName);
	
	/**
	 * 删除一个打包项
	 * @param branchItemPK
	 * @param operator
	 */
	public ResultHandle deleteItem(Long branchItemPK,String operator);
	
	public ProdProductBranchItem selectBranchItemByPK(Long pk);
	
	/**
	 * 用采购产品类别ID查询打包的所有的销售类别，去掉重复.
	 * @param metaProductId
	 * @return
	 */
	List<ProdProductBranchItem> selectUniqueBranchIdByMeta(Long metaBranchId);
	/**
	 * 取一个采购商品对应的打包的所有打包项.
	 * @param metaBranchId
	 * @return
	 */
	List<ProdProductBranchItem> selectItemListByMetaBranch(Long metaBranchId);
	
	List<ProdProductBranchItem> selectItemListByParam(Map<String, Object> param);
	/**
	 * 修改类别对应的icon.
	 * @param prodBranchId
	 * @param icon
	 */
	ResultHandle changeIcon(Long prodBranchId,String icon);
	
	/**
	 * 判断一个销售产品是否存在默认类别.
	 * @param productId
	 * @return
	 */
	boolean hasDefaultBranch(Long productId);
	
	/**
	 * 更新类别的销售价.
	 * @param prodBranchId
	 */
	void updatePriceByBranchId(Long prodBranchId);
	
	/**
	 * 取所有的类别列表的id
	 * @return
	 */
	Page<Long> selectAllBranchId(long pageSize,long page);
	
	List<ProdProductBranch> getProductBranchDetailByProductId(Map<String, Object> params);
	
	Date selectNearBranchTimePriceByBranchId(final Long prodBranchId);
	
	ProdProductBranch getProductBranchDetailByBranchId(Long branchId, Date visitTime, boolean onLine);
	
	/**
	 * 查询对应的类别上的库存时间并且填充,
	 * <p>需要自己保证prodBranch的值不为空并且里面的prodProduct属性也不为空</p>
	 * @param prodBranch
	 * @param visitTime
	 * @return
	 */
	ProdProductBranch getProductBranchDetailByBranchId(ProdProductBranch prodBranch, Date visitTime);
	
	/**
	 * 逻辑删除一个类别(设置类别的VALID字段为'N')
	 * @param pk 类别主键
	 * @param operatorName
	 */
	ResultHandle deleteBranchByLogic(Long pk,String operatorName);
	
	ResultHandle deleteBranchByLogicForEBK(Long pk,String operatorName);
	
	/**
	 * 更新指定销售类别的销售价
	 * @param prodBranchId 销售类别ID
	 */
	void updateTimePriceByProdBranchId(Long prodBranchId);
	
	/**
	 * 采购产品类别类别时间价格更新，重新计算相关销售产品类别的价钱
	 * @param metaBranchId 采购类别
	 * @param timeRange 时间区间
	 */
	void updateTimePriceByProdBranchId(final Long metaBranchId,final TimeRange timeRange);
	
	/**
	 * 查询指定产品的所有类别
	 * @param productId
	 * @return
	 */
	public List<ProdProductBranch> getProductBranchByProductId(Long productId);
	

	/**
	 * 查询上一个类别ID
	 * @param productId
	 * @param branch_serial_number
	 * @return PROD_BRANCH_ID
	 */
	public ProdProductBranch getPreProductBranch(Map<String, Object> params);
	
	/**
	 * 查询下一个类别ID
	 * @param productId
	 * @param branch_serial_number
	 * @return PROD_BRANCH_ID
	 */
	public ProdProductBranch getNextProductBranch(Map<String, Object> params);
	
	/**
	 * 修改类别排序值
	 * @param PROD_BRANCH_ID
	 * @param branch_serial_number
	 * @return
	 */
	public void updateProductBranchSerialNumber(Map<String, Object> params);
	
	boolean selectIntersectionMetaProduct(Long prodBranchId, Long beginTime, Long endTime, Long metaBranchId );
	List<Date> checkTimePriceContain(Date today, Long prodBranchId, Long metaBranchId);
	void deleteTimePrice(List<Date> dates,Long prodBranchId, String operatorName);
	
	
	/**
	 * 查询一个类别指定日期的时间价格表,受提前预订小时数的影响
	 * @param prodBranchId  产品类别
	 * @param specDate 游玩时间
	 * @return
	 */
	public TimePrice getProdTimePrice(Long prodBranchId, Date specDate);
	
	/**
	 * 更新类别上下线状态
	 * @param branch
	 * @param operatorName
	 * @return
	 */
	public ResultHandle OnOfflineProductBranch(ProdProductBranch branch,
			String operatorName);
	
	/**
	 * 针对手机客户端当天可预订,查询当前时间是否可预订
	 * */
	public boolean checkPhoneOrderTime(Long prodBranchId);
	
	public int updateByPrimaryKeySelective(ProdProductBranch record);
	
	 
	 /**
		 * 取当天的时间价格表(为门票客户端当天可预订用)
		 * @param prodBranchId
		 * @param specDate
		 * @return
		 */
	TimePrice  calcCurrentProdTimePric(final Long prodBranchId,final Date specDate);
	 
	List<ProdProductBranchItem> selectItemsByMetaProductId(Long metaProductId);
	 
	List<ProdProductBranch> selectProdTrainBranchsByParams(Map<String,Object> param);
	
	//做类别上线操作前需校验被打包的采购产品的供应商合同是否已审核
	public ResultHandle checkMetaSupplierContractStatus(Long prodBranchId);
	 
	ResultHandle checkBranchItemSupplierContractStatus(Long metaBranchId);
	 
	List<ProdProductBranch> selectProdBranchsByStationStation(Long stationStationId);
	
	List<ProdProductBranch> selectByParam(Map<String, Object> map);
	
	public List<TimePrice> selectProdTimePriceByProdBranchId(Long prodBranchId, Date specDateStart, Date specDateEnd);
	
	public List<TimePrice> selectMetaTimePriceByMetaBranchId(Long metaBranchId, Date specDateStart, Date specDateEnd);
	
	//B2B分销产品类别查询
	public List<ProdProductBranch> selectB2BProd(Map<String,Object> map);
	
	public long selectB2BProdCount(Map<String,Object> map);
	
	//根据branchId或productId等条件查询产品类别
	public ProdProductBranch selectB2BProdByParam(Map<String,Object> map);
	
	public ProdProductBranch selectB2BProdByBranchId(Long branchId);
	/**
	 * 根据采购产品Id查询所有打包此采购的销售产品类别
	 * @param metaProductId
	 * @return
	 */
	public List<ProdProductBranch> selectProdBranchByMetaProductId(Long metaProductId,boolean isBuildProdDetail);
	/**
	 * 查询产品类别
	 * @param productId
	 * @param additional
	 * @param isonline
	 * @param isBuildProdDetail 是否构建产品信息
	 * @return
	 */
	public List<ProdProductBranch> getProductBranchByProductId(Long productId, String additional, String isonline, boolean isBuildProdDetail);
	
	
	
	public void updateTimePriceForBranchId(Map<String, Object> paramMap);
}