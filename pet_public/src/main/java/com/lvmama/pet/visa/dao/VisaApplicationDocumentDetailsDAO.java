package com.lvmama.pet.visa.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.pet.po.visa.VisaApplicationDocumentDetails;

public class VisaApplicationDocumentDetailsDAO extends BaseIbatisDAO {
	/**
	 * 新增签证材料明细
	 * @param details 签证材料明细
	 * @return 插入的签证材料明细
	 * <p>将传入的签证材料明细插入数据库，插入的数据除NULL外，不做任何合法性校验，由数据库来决定</p>
	 */
	public VisaApplicationDocumentDetails insert(final VisaApplicationDocumentDetails details) {
		if (null != details) {
			super.insert("VISA_APPLICATION_DOCUMENT_DETAILS.insert", details);
			return details;
		} else {
			return null;
		}
		
	}
	
	/**
	 * 更新签证材料明细
	 * @param details 签证材料明细
	 * @return 插入的签证材料明细
	 * <p>将传入的签证材料明细插入数据库，插入的数据除NULL外，不做任何合法性校验，由数据库来决定</p>
	 */
	public VisaApplicationDocumentDetails update(final VisaApplicationDocumentDetails details) {
		if (null != details) {
			super.insert("VISA_APPLICATION_DOCUMENT_DETAILS.update", details);
			return details;
		} else {
			return null;
		}
		
	}	
	
	/**
	 * 删除签证材料明细
	 * @param detailsId 材料标识
	 * <p>根据材料标识物理删除签证材料明细</p>
	 */
	public void delete(final Long detailsId) {
		if (null != detailsId) {
			super.delete("VISA_APPLICATION_DOCUMENT_DETAILS.delete", detailsId);
		}
	}
	
	/**
	 * 根据资料标识批量删除明细
	 * @param documentId 资料标识
	 */
	public void deleteByDocumentId(final Long documentId) {
		if (null != documentId) {
			super.delete("VISA_APPLICATION_DOCUMENT_DETAILS.deleteByDocumentId", documentId);
		}
	}
	
	/**
	 * 根据查询条件查询签证材料明细
	 * @param param 查询条件
	 * @return 附件条件的材料列表
	 * 当参数是NULL或者空时，会返回空列表
	 */
	@SuppressWarnings("unchecked")
	public List<VisaApplicationDocumentDetails> query(final Map<String, Object> param) {
		if (null != param && !param.isEmpty()) {
			return (List<VisaApplicationDocumentDetails>) super.queryForList("VISA_APPLICATION_DOCUMENT_DETAILS.query", param);
		} else {
			return new ArrayList<VisaApplicationDocumentDetails>(0);
		}
	}
	
	/**
	 * 根据标识查询签证材料明细
	 * @param detailsId
	 * @return
	 */
	public VisaApplicationDocumentDetails queryByPrimaryKey(final Long detailsId) {
		if (null != detailsId) {
			return (VisaApplicationDocumentDetails) super.queryForObject("VISA_APPLICATION_DOCUMENT_DETAILS.queryByPrimaryKey", detailsId);
		} else {
			return null;
		}
	}
	
	/**
	 * 根据标识查询签证材料明细
	 * @param detailsId
	 * @return
	 */
	public VisaApplicationDocumentDetails queryByDocumentId(final Long documentId) {
		if (null != documentId) {
			return (VisaApplicationDocumentDetails) super.queryForObject("VISA_APPLICATION_DOCUMENT_DETAILS.queryByDocumentId", documentId);
		} else {
			return null;
		}
	}
	
	/**
	 * 复制签证明细
	 * @param srcDocumentId 源签证资料
	 * @param targetDocumentId 目标签证资料
	 * <p>将源签证资料的明细追加到目标签证资料中
	 */
	public void copy(final Long srcDocumentId, final Long targetDocumentId) {
		if (null != srcDocumentId && null != targetDocumentId) {
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("srcDocumentId", srcDocumentId);
			param.put("targetDocumentId", targetDocumentId);
			super.insert("VISA_APPLICATION_DOCUMENT_DETAILS.copy", param);
		}
	}
}
