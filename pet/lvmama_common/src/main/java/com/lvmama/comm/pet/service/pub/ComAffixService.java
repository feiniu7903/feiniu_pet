package com.lvmama.comm.pet.service.pub;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.pet.po.pub.ComAffix;

/**
 * 文件上传.
 * @author yangbin
 *
 */
public interface ComAffixService {

	/**
	 * 保存上传附件对象.
	 * @param affix 附件对象
	 * @param operator 操作人
	 */
	void addAffix(ComAffix affix, String operator);
	
	/**
	 * 读取附件列表.
	 * @param parameter 参数
	 * @return 附件列表
	 */
	List<ComAffix> selectListByParam(Map parameter);
	
	/**
	 * 读取附件行数.
	 * @param parameter 参数
	 * @return 行数
	 */
	long selectCountByParam(Map parameter);
	
	/**
	 * 按主键得到一个附件.
	 * @param affixId 主键
	 * @return 附件
	 */
	ComAffix getAffix(Long affixId);
	
	/**
	 * 删除附件对象.
	 * @param affix 附件对象
	 * @param operator 操作人
	 */
	void removeAffix(ComAffix affix, String operator);

	/**
	 * 根据参数读取时间最新的一个附件
	 * @param parameter 参数
	 * @return 附件
	 * @author nixianjun
	 * @CreateDate 2012-7-17
	 */
	ComAffix selectLatestRecordByParam(Map<String, Object> parameter);
	/**
     * @param parameter 参数
	 * @return 附件列数
	 * @author nixianjun
	 * @CreateDate 2012-7-20
	 */
	List selectListForTimeDescByParam(Map<String, Object> parameter);
	/**
	 * 保存上传附件对象（包括新增字段fileId）.
	 * @param affix 附件对象
	 * @param operator 操作人
	 * @author nixianjun
	 * @CreateDate 2012-7-20
	 */
	void addAffixForGroupAdvice(ComAffix affix, String operatorName);
	
	/**
	 * 按objectIds加objectType条件查询上传对象列表
	 * @param parameter
	 * @author yanzhirong
	 * @since 2013-12-23
	 * @return
	 */
	public List selectListByObjectIds(Map<String, Object> parameter);
}
