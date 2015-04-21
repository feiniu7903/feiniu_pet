/**
 * 
 */
package com.lvmama.comm.pet.service.visa;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.pet.po.visa.VisaApplicationDocument;
import com.lvmama.comm.pet.po.visa.VisaApplicationDocumentDetails;

/**
 * 签证材料
 * @author Brian
 *
 */
public interface VisaApplicationDocumentService {
	/**
	 * 新增签证材料
	 * @param country 国家
	 * @param visaType 签证类型
	 * @param city 出签城市
	 * @param occupation 人群
	 * @param operatorName 操作人员
	 * @return
	 */
	VisaApplicationDocument insert(String country, String visaType, String city, String occupation, String operatorName);
	
	/**
	 * 物理删除签证材料
	 * @param documentId 标识
	 * @param operatorName 操作人员
	 */
	void delete(Long documentId, String operatorName);
	
	/**
	 * 统计数目
	 * @param param 查询列表
	 * @return
	 */
	Long count(Map<String, Object> param);
	
	/**
	 * 根据标识查询签证材料
	 * @param documentId 标识
	 * @return
	 */
	VisaApplicationDocument queryByPrimaryKey(Long documentId);
	
	/**
	 * 根据查询条件查询签证材料
	 * @param param 条件
	 * @return
	 */
	List<VisaApplicationDocument> query(Map<String, Object> param);
	
	/**
	 * 新增签证材料明细
	 * @param details 明细
	 * @param operatorName 操作人员
	 * @return
	 */
	VisaApplicationDocumentDetails insert(VisaApplicationDocumentDetails details, String operatorName);
	
	/**
	 * 更新签证材料明细
	 * @param details 明细
	 * @param operatorName 操作人员
	 * @return
	 */	
	VisaApplicationDocumentDetails update(VisaApplicationDocumentDetails details, String operatorName);
	
	/**
	 * 根据签证资料标识查询所有明细
	 * @param documentId
	 * @return
	 */
	List<VisaApplicationDocumentDetails> queryDetailsByDocumentId(Long documentId);
	
	/**
	 * 复制签证资料
	 * @param documentId 源签证资料标识
	 * @param document 目标签证资料
	 * @return 复制后的签证资料
	 * @param operatorName 操作人员
	 * <p>会将源签证资料明细进行复制</p>
	 */
	VisaApplicationDocument copy(Long documentId, VisaApplicationDocument document, String operatorName);
	
	/**
	 * 删除签证材料明细
	 * @param detailsId 明细标识
	 * @param operatorName 操作人员
	 */
	void deleteDetails(Long detailsId, String operatorName);
}
