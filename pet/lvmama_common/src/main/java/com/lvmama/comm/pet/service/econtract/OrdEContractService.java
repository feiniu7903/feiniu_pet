package com.lvmama.comm.pet.service.econtract;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.bee.po.ord.OrdEContract;
import com.lvmama.comm.bee.po.ord.OrdEContractComment;
import com.lvmama.comm.bee.po.ord.OrdEContractLog;
import com.lvmama.comm.bee.po.ord.OrdEcontractBackUpFile;
import com.lvmama.comm.bee.po.ord.OrdEcontractSignLog;
import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.pet.po.prod.ProdEContract;

/**
 * @author kevin
 * @version  1.0  2011-9-26
 *  
 */
public interface OrdEContractService {
	 /**
     * 
     * @param ordEContract
     */
	 void updateOrdEContract(final OrdEContract ordEContract);
	 /**
	  * 根据订单号查询是否有修改过的日志记录
	  */
	 Long existByOrderId(final Long orderId);
	 /**
     * 
     * @param orderId
     * @return
     */
	 OrdEContract queryByOrderId(Long orderId);
	/**
	 * 插入一条订单合同备注
	 * @param obj
	 * @return
	 */
	OrdEContractComment insertOrdEContractComment(final OrdEContractComment obj);
	/**
	 * 根据订单电子合同编号查询备注列表
	 * @param eContractId
	 * @return
	 */
	List<OrdEContractComment> queryEContractCommentByEContractId(final String eContractId);
	/**
	 * 根据订单ID查询备注列表.
	 * @param orderId
	 * @return
	 */
	OrdEContractComment queryEContractCommentByOrderId(final Long  orderId);
 
	/**
	 * 生成合同记录信息
	 */
	OrdEContract createOrderContract(final OrdOrder order,final ProdEContract prodContract,final Long contentFileId, final Long fixedFileId,final String userName);
	/**
	 * 更新电子合同信息
	 * @param contract
	 * @param order
	 * @param complementXml
	 * @param contentFileId
	 * @param fixedFileId
	 * @param operater
	 * @return
	 */
	public OrdEContract updateOrderContract(final OrdEContract contract,final OrdOrder order,final String complementXml,final Long contentFileId,final Long fixedFileId,final String operater);
	/**
	 * 取得PDF中的填充数据
	 * @param order
	 * @return
	 */
	Map<String, Object> getDataStore(final OrdOrder order,final ProdEContract prodEContract);
	/**
	 * 取得合同信息
	 * @param orderId
	 * @return
	 */
	Long getContractContent(final Long orderId);
	/**
	 * 电子合同签约
	 * @param orderServiceProxy
	 * @param comLogDAO
	 * @param orderId
	 * @param signLog
	 * @return
	 */
	boolean signContract(final Long orderId,final OrdEcontractSignLog signLog);
	/**
	 * 根据订单获取合同附加条款
	 * @param orderId
	 * @return
	 */
	String getContractComplement(final Long orderId,final String complementTemplate);	
	
	/**
	 * 根据订单，合同产品信息初始化补充条款
	 * @param order
	 * @param prodContract
	 * @return
	 */
	public String initComplementXml(final OrdOrder order,final ProdEContract prodContract);
	
	//OrdEcontractBackUpFileService
	/**
	 * 插入电子合同签约备份文件记录
	 */
	OrdEcontractBackUpFile insertEcontractBackUpFile(OrdEcontractBackUpFile object);
	/**
	 * 更新电子合同线下签约备份文件
	 * @param object
	 * @return
	 */
	int updateEcontractBackUpFile(OrdEcontractBackUpFile object);
	/**
	 * 根据条件查询签约备份文件列表
	 * @param parameters
	 * @return
	 */
	List<OrdEcontractBackUpFile> queryEcontractBackUpFile(Map<String,Object> parameters);
	
	/**
	 * 插入合同日志
	 * @param object
	 * @return
	 */
	OrdEContractLog insertEContractLog(final OrdEContractLog object);
	
	/**
	 * 插入电子合同签约日志记录
	 */
	OrdEcontractSignLog insertEcontractSignLog(OrdEcontractSignLog object);
	
	/**
	 * 根据条件查询签约日志列表
	 * @param parameters
	 * @return
	 */
	List<OrdEcontractSignLog> queryEcontractSignLog(Map<String,Object> parameters);
	 /**
	  * 根据条件取得合同
	  * @param parameters
	  * @return
	  */
	 List<OrdEContract> queryOrdEContract(Map<String,Object> parameters);
	/**
	 * 根据订单电子合同编号查询备注列表
	 * @param eContractId
	 * @return
	 */
	List<OrdEContractComment> queryByEContractId(final String eContractId);
	OrdEContract getOrdEContractByOrderId(Long orderId);
	OrdEContract getOrdEconractByEContractId(String econtractId);
}