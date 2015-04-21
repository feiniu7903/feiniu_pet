package com.lvmama.comm.bee.service.ebooking;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.lvmama.comm.bee.po.ebooking.EbkProdProduct;
import com.lvmama.comm.bee.po.ebooking.EbkProdRejectInfo;
import com.lvmama.comm.pet.vo.Page;


public interface EbkProdProductService {
	/**
	 * EBK产品管理 >产品信息表
	 * 2013-9-25
	 */
	
	/**
	 * 保存EBK产品信息
	 * @param EbkProdProduct
	 * @return
	 */
	public int saveEbkProdProduct(EbkProdProduct saveEbkProdAllMsg);
	
	/**
	 * 根据供应商ID,产品类型对审核进行分组统计总数
	 * @param supplierId
	 * @return
	 */
	public Map<String,Object> queryAllCount(Long supplierId,String[] subProductTypes);
	
	public List<Map<String,Object>> queryCountGroupByStatus(Map<String, Object> parameters);
	
	/**
	 * 根据查询条件查询供应商产品列表
	 * @param parameters
	 * @return
	 */
	public Page<EbkProdProduct> queryProduct(Map<String,Object> parameters);
	
	/**
	 * 根据EBK产品ID提交审核
	 * @param ebkProdProductId
	 * @return
	 */
	public int auditCommit(final Long ebkProdProductId,Long updateUserId);
	
	/**
	 * 根据EBK产品ID撤销审核
	 * @param ebkProdProductId
	 * @return
	 */
	public int auditRevoke(final Long ebkProdProductId);
	/**
     * 根据主键获取ebkProdProductDO
     * @param ebkProdProductId
     * @return ebkProdProductDO
     */
	public EbkProdProduct findEbkProdProductDOByPrimaryKey(Long ebkProdProductId);
	/**
	 * 根据产品Id获取产品对象和其它所有关联数据
	 * @author ZHANG Nan
	 * @param ebkProdProductId
	 * @return
	 */
	public EbkProdProduct findEbkProdProductAndBaseByPrimaryKey(Long ebkProdProductId);
	/**
	 * 根据产品Id获取产品对象和行程描述页数据
	 * @author ZHANG Nan
	 * @param ebkProdProductId
	 * @return
	 */
	public EbkProdProduct findEbkProdProductAndTripByPrimaryKey(Long ebkProdProductId);
	/**
	 * 根据产品Id获取产品对象和EBK_PROD_CONTENT表数据
	 * @author ZHANG Nan
	 * @param ebkProdProductId
	 * @return
	 */
	public EbkProdProduct findEbkProdProductAndContentByPrimaryKey(Long ebkProdProductId);
	
	public EbkProdProduct findEbkProdAllByPrimaryKey(Long ebkProdProductId);
	/**
	 * 根据产品Id删除未提交产品的产品对象和基础信息页数据
	 * @author shangzhengyuan
	 * @param ebkProdProductId
	 * @return
	 */
	public int deleteUnCommitAudit(Long ebkProdProductId);
	/**
	 * 审核通过-导入到super系统
	 * @author ZHANG Nan
	 * @param ebkProdProductId EBK产品ID
	 * @param onlineTime 上线开始时间
	 * @param offlineTime 上线结束时间
	 * @param online 是否上线
	 */
	public void prodProductAuditPass(Long ebkProdProductId,Date onlineTime,Date offlineTime,Boolean online)throws Exception;
	/**
	 * 审核不通过-记录审核不通过信息
	 * @author ZHANG Nan
	 * @param ebkProdProductId EBK产品ID
	 * @param ebkProdRejectInfoList 审核不通过信息集合
	 */
	public void prodProductAuditNoPass(Long ebkProdProductId,List<EbkProdRejectInfo> ebkProdRejectInfoList);

	int auditRecover(Long ebkProdProductId);
	
	public void updateEbkProdProductDO(EbkProdProduct ebkProdProduct);
	
	public List<EbkProdProduct> findListByExample(Map<String, Object> parameters);
	
	public EbkProdProduct findEbkProdByProductId(Long prodProductId);
}
