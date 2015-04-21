package com.lvmama.comm.bee.service.meta;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.lvmama.comm.bee.po.meta.MetaProductBranch;
import com.lvmama.comm.bee.po.prod.TimePrice;
import com.lvmama.comm.bee.vo.EbkDayStockDetail;
import com.lvmama.comm.bee.vo.MetaBranchRelateProdBranch;
import com.lvmama.comm.utils.json.ResultHandleT;

/**
 * @author yangbin
 *
 */
public interface MetaProductBranchService {

	/**
	 * 读取一个采购产品类别
	 * @param pk
	 * @return
	 */
	public MetaProductBranch getMetaBranch(Long pk);
	
	/**
	 * 读取一个采购产品的类别列表
	 * @param metaProductId
	 * @return
	 */
	public List<MetaProductBranch> selectBranchListByProductId(Long metaProductId);
	
	/**
	 * 按是否有效读取采购类别列表
	 * @param metaproductId
	 * @param valid
	 * @return
	 */
	public List<MetaProductBranch> selectBranchListByProductId(Long metaproductId,String valid);
	
	/**
	 * 保存
	 * @param branch
	 * @param operatorName
	 * @return
	 */
	public MetaProductBranch save(MetaProductBranch branch,String operatorName);
	
	/**
	 * 查找采购类别关联的销售产品及类别
	 * @param metaBranchId
	 * @return 
	 */
	public List<MetaBranchRelateProdBranch> selectProdProductAndProdBranchByMetaBranchId(Long metaBranchId);
	
	/**
	 * 查询一个采购指定日期的时间价格表
	 * @param metaBranchId
	 * @param specDate
	 * @return
	 */
	public TimePrice getTimePrice(Long metaBranchId,Date specDate);
	
	/**
	 * 复位需要自动清除库存的采购时间价格表的库存
	 */
	public void resetStock();
	
	
	public Long getEbkMetaBranchCount(Map<String, Object> params);
	public List<MetaProductBranch> getEbkMetaBranch(Map<String, Object> params);
	public List<MetaProductBranch> getEbkMetaBranchParam(Map<String, Object> params);
	public List<EbkDayStockDetail> getEbkDayStockDetail(Map<String, Object> params);
	public Long getEbkDayStockDetailPageCount(Map<String, Object> params);

	/**
	 * 根据采购产品id查询采购产品类别信息
	 */
	public List<MetaProductBranch> getEbkMetaBranchByProductId(Long metaProductId);
	List<MetaProductBranch> getMetaBranch(Map<String, Object> params);
	
	public void changeHousepriceSendMessage(Long metaProductId, String content,String sender);

	public List<MetaProductBranch> selectMetaProductBranchBySupplierType(Long supplierId, String productTypeSupplier, String productIdSupplier);
	
	public List<MetaProductBranch> selectMetaProductBranchBySupplierId(Long supplierId);
	/**
	 * 根据供应商Id查询出该供应商所有附加产品的SupplierType
	 * @param supplierId
	 * @return
	 */
	public List<String> selectSupplierTypeBySupplierId(Long supplierId);

	public List<MetaProductBranch> getMetaProductBranchByProdBranchId(Long prodBranchId);

	
	/**
	 * 逻辑删除产品类别
	 * @param metaBranchId
	 * @param operatorName
	 */
	public void deleteMetaProductBranch(Long metaBranchId,String operatorName);
	
	

	
	public Long getProductIdByMetaBranchId(Long metaBranchId);
	
	/**
	 * 根据采购类别ID ,产品类别查询出采购类别的valid
	 * @param metaBranchId,productType
	 * @return valid
	 */
	public ResultHandleT<String> getMetaProductBranchValid(Long metaBranchId,String productType );

}
