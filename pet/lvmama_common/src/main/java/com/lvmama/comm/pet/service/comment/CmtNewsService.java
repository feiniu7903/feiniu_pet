package com.lvmama.comm.pet.service.comment;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.lvmama.comm.vo.comment.CmtNewsVO;

/**
 * 小驴说事逻辑接口
 * @author yuzhizeng
 *
 */
public interface CmtNewsService {
	
	/**
	 * 查询
	 * @param parms 查询条件
	 * @return 返回列表
	 */
	List<CmtNewsVO> query(Map<String, Object> params);
	
	/**
	 * 计数
	 * @param params 查询条件
	 * @return 总数
	 */
	Long count(Map<String, Object> params);
	
	/**
	 * 根据主键查询
	 * @param id 主键
	 * @return 小驴说事
	 */
	CmtNewsVO queryByPk(Serializable id);
	
	/**
	 * 保存"小驴说事"
	 * @param news "小驴说事"
	 * @return 标识
	 * 如果待保存的"小驴说事"未有标识，则会新增；否则只做更新，并返回当前"小驴说事"的标识
	 */
	Long save(CmtNewsVO news);
	
	/**
	 * 查询往期
	 * @param parms 查询条件
	 * @return 返回列表
	 */
	List<CmtNewsVO> queryReview(Map<String, Object> params);
	 
}
