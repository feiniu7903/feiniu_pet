package com.lvmama.comm.bee.service.fax;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.bee.po.fax.OrdFaxRecv;
import com.lvmama.comm.bee.po.fax.OrdFaxRecvLink;
import com.lvmama.comm.pet.vo.Page;

public interface OrdFaxRecvService {
	/**
	 * 新增回传，回传关联,更新传真发送，更新传真回传状态
	 * @param ordFaxRecv
	 */
	Long receiveOrdFaxRecv(OrdFaxRecv record);
	/**
	 * 根据参数选择回传
	 * @param param
	 */
	List<OrdFaxRecv> selectByParam(Map param);
	/**
	 * 统计回传数
	 * @param param
	 */
	Long selectByParamCount(Map param);
	/**
	 * 新增回传关联
	 * @param OrdFaxRecvLink
	 */
	Long insertLinkAndUpdateRecvStatus(OrdFaxRecvLink record);
	/**
	 * 根据凭证ID查找传真回传
	 * @param ebkCertificateId
	 */
	List<OrdFaxRecv> queryOrdFaxRecvCertificateId(Long ebkCertificateId);
	/**
	 * 查询回传关联
	 * @param ebkCertificateId
	 */
	Page<OrdFaxRecvLink> selectLinksByParams(Map<String,Object> params,Long pageSize, Long currentPage);
	/**
	 * 统计回传关联数
	 * @param params
	 */
	Long selectLinksCountByParams(Map<String, Object> params);
	/**
	 * 根据回传关联编号删除关联
	 * @param ordFaxRecvLinkId
	 */
	int deleteByLinkId(Long ordFaxRecvLinkId);
	/**
	 * 更新回传关联状态
	 * @param ordFaxRecvLinkId
	 */
	void updateOrdFaxRecvLinkResultStatus(OrdFaxRecvLink ordFaxRecvLink);
	/**
	 * 根据回传编号查询回传
	 * @param ordFaxRecvId
	 */
	OrdFaxRecv selectByPrimaryKey(Long ordFaxRecvId);
	/**
	 * 根据回传编号选择关联的多个凭证
	 * @param recvId
	 */
	List<Long> selectLinkCertificateIdsByRecvId(Long recvId);
	/**
	 * 根据回传编号选择关联和凭证
	 * @param recvId
	 */
	List<OrdFaxRecvLink> selectLinkAndCertificateByRecvId(Long recvId);
	/**
	 * 批量更新回传为无效
	 * @param recvIdMapList
	 */
	void updateOrdFaxRecvValidToFalse(Map<String,List<Long>> recvIdMapList);
}
