package com.lvmama.pet.visa.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.pet.po.visa.VisaApplicationDocument;

/**
 * 签证所需材料的数据库操作类
 * @author Brian
 *
 */
public class VisaApplicationDocumentDAO extends BaseIbatisDAO {
	/**
	 * 新增签证材料
	 * @param document 签证材料
	 * @return 插入的签证材料
	 * <p>将传入的签证材料插入数据库，插入的数据除NULL外，不做任何合法性校验，由数据库来决定</p>
	 */
	public VisaApplicationDocument insert(final VisaApplicationDocument document) {
		if (null != document) {
			super.insert("VISA_APPLICATION_DOCUMENT.insert", document);
			return document;
		} else {
			return null;
		}
		
	}
	
	/**
	 * 删除签证材料
	 * @param documentId 材料标识
	 * <p>根据材料标识物理删除签证材料</p>
	 */
	public void delete(final Long documentId) {
		if (null != documentId) {
			super.delete("VISA_APPLICATION_DOCUMENT.delete", documentId);
		}
	}
	
	/**
	 * 根据查询条件查询签证材料
	 * @param param 查询条件
	 * @return 附件条件的材料列表
	 * 当参数是NULL或者空时，会返回空列表
	 */
	@SuppressWarnings("unchecked")
	public List<VisaApplicationDocument> query(final Map<String, Object> param) {
		if (null != param && !param.isEmpty()) {
			return (List<VisaApplicationDocument>) super.queryForList("VISA_APPLICATION_DOCUMENT.query", param);
		} else {
			return new ArrayList<VisaApplicationDocument>(0);
		}
	}
	
	/**
	 * 根据标识查询签证材料
	 * @param documentId
	 * @return
	 */
	public VisaApplicationDocument queryByPrimaryKey(final Long documentId) {
		if (null != documentId) {
			return (VisaApplicationDocument) super.queryForObject("VISA_APPLICATION_DOCUMENT.queryByPrimaryKey", documentId);
		} else {
			return null;
		}
	}	
	
	/**
	 * 根据查询条件查询签证材料的数目
	 * @param param 查询条件
	 * @return 附件条件的材料数目
	 * 当参数是NULL或者空时，会返回0
	 */	
	public Long count(final Map<String, Object> param) {
		if (null != param && !param.isEmpty()) {
			return (Long) super.queryForObject("VISA_APPLICATION_DOCUMENT.count", param);
		} else {
			return 0L;
		}
	}
}
