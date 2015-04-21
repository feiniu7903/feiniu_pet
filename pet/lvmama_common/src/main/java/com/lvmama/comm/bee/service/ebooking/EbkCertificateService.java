package com.lvmama.comm.bee.service.ebooking;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.bee.po.ebooking.EbkCertificate;
import com.lvmama.comm.bee.po.ebooking.EbkCertificateItem;
import com.lvmama.comm.bee.po.ebooking.EbkTask;
import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.pet.po.sup.SupBCertificateTarget;
import com.lvmama.comm.pet.vo.Page;

/**
 * 凭证创建部分见： EbkCertificateLogic
 * @author ranlongfei 2013-3-15
 * @version
 */
public interface EbkCertificateService {
	/**
	 * 生成凭证
	 * @param order
	 * @param sbct
	 * @param orderItemMetaIdList 
	 * @return
	 */
	List<EbkCertificate> createSupplierCertificate(OrdOrder order,Map<Long, SupBCertificateTarget> sbct,String ebkCertificateEvent,String userMemoStatus, String orderItemMetaIdList);
	
	/**
	 * 查询一个凭证的详情
	 * @param key
	 * @return
	 */
	EbkCertificate selectEbkCertificateDetailByPrimaryKey(final Long key);
	
	/**
	 * 查询ebk实体，无效也要查出来
	 * @param key
	 * @return
	 */
	EbkCertificate selectEbkCertificateDetailByPrimaryKeyAndValid(final Long key);

	Page<EbkTask> queryEbkTaskPageListSQL(Long currentPage,Long pageSize,Map<String,Object> parameterObject);
	
	EbkTask queryEbkTaskByEbkTaskId(Long ebkTaskId);
	/**
	 * 根据凭证ID查询
	 * @param ebkCertificateId
	 * @return
	 */
	EbkCertificate selectByPrimaryKey(Long ebkCertificateId);
	/**
	 * 根据凭证ID查询
	 * @param ebkCertificateId
	 * @return
	 */
	List<EbkCertificateItem> selectEbkCertificateItemByEbkCertificateId(Long ebkCertificateId);
	int update(EbkCertificate ebkCertificate);
	/**
	 * 根据子子项ID查询对应的凭证子项
	 * 
	 * @author: ranlongfei 2013-4-1 下午5:55:29
	 * @param orderItemMetaId
	 * @return
	 */
	EbkCertificateItem selectEbkCertificateItemByOrderItemMetaId(Long orderItemMetaId);
	/**
	 * 查询一个凭证的详情和接受的数据内容
	 * @param key
	 * @return
	 */
	EbkCertificate selectEbkCertDetailAndGetContentByPrimaryKey(final Long key);

	/**
	 * 手工生成传真任务
	 * 
	 * @author: ranlongfei 2013-4-22 下午1:49:31
	 * @param cert
	 * @param ordOrder
	 * @param operator 
	 * @return
	 */
	boolean createCertificateEbkFaxTask(EbkCertificate cert, OrdOrder ordOrder, String ebkCertificateEvent, String operator);
	
	/**
	 * 根据id查询
	 * @param orderId
	 * @return
	 */
	List<EbkCertificate> selectEbkCertificateByOrderId(Long orderId);

	/**
	 * 修改凭证备注栏信息
	 * @author shihui
	 * */
	int updateChangeInfo(String changeInfo, Long ebkCertificateId);
}
