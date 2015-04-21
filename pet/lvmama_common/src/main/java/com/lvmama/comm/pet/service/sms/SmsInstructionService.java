package com.lvmama.comm.pet.service.sms;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.pet.po.sms.SmsInstruction;

/**
 * 上行指令的逻辑接口
 * @author Brian
 *
 */
public interface SmsInstructionService {
	/**
	 * 保存上行指令
	 * @param instructions
	 *            上行指令
	 * @return 上行指令
	 */
	SmsInstruction save(SmsInstruction instructions);

	/**
	 * 删除上行指令
	 * @param instructionCode 上行指令
	 */
	void deleteByPrimaryKey(String instructionCode);

	/**
	 * 修改上行指令
	 * @param instructions
	 *            修改后的对象
	 */
	void update(SmsInstruction instructions);

	/**
	 * 根据Code查询一条数据
	 * @param instructionCode e
	 * @return 数据
	 */
	SmsInstruction queryByPrimaryKey(String instructionCode);
	/**
	 * 查询上行指令的集合
	 * @param param
	 *            参数
	 * @return 列表的集合
	 */
	List<SmsInstruction> query(Map<String, Object> param);

	/**
	 * 查询所有的上行指令
	 * @return 所有上行指令的集合
	 */
	List<SmsInstruction> queryAll();

	/**
	 * 总条数
	 * @param param  参数
	 * @return 总条数
	 */
	Long count(final Map<String, Object> param);
}
