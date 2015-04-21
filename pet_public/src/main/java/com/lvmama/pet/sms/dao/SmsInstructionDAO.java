package com.lvmama.pet.sms.dao;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.pet.po.sms.SmsInstruction;

/**
 * 短信上行指令数据库实现
 * @author Brian
 *
 */
public class SmsInstructionDAO extends BaseIbatisDAO {
	/**
	 * 日志输出器
	 */
	private static final Log LOG = LogFactory.getLog(SmsInstructionDAO.class);

	/**
	 * 查询上行指令
	 * @param param 查询条件
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<SmsInstruction> query(final Map<String, Object> param) {
		LOG.debug("查询所有的指令集");
		return super.queryForList("SMS_INSTRUCTION.query", param);
	}

	/**
	 * 新增上行指令
	 * @param instructions 上行指令
	 * @return
	 */
	public SmsInstruction save(final SmsInstruction instructions) {
		return (SmsInstruction) super.insert("SMS_INSTRUCTION.insert", instructions);
	}

	/**
	 * 删除上行指令
	 * @param instructionCode
	 */
	public void deleteByPrimaryKey(final String instructionCode) {
		 super.delete("SMS_INSTRUCTION.delete", instructionCode);
	}

	/**
	 * 更新上行指令
	 * @param instructions
	 */
	public void update(final SmsInstruction instructions) {
		super.update("SMS_INSTRUCTION.update", instructions);
	}

	/**
	 * 根据Code查询一条数据
	 * @param instructionCode param
	 * @return 优惠劵的对象
	 */
	public SmsInstruction queryByPrimaryKey(final String instructionCode) {
		return (SmsInstruction) super.queryForObject(
				"SMS_INSTRUCTION.queryByPrimaryKey", instructionCode);
	}
	/**
	 * 总条数
	 * @param param 参数
	 * @return 条数
	 */
	public Long count(final Map<String, Object> param) {
		return (Long) super.queryForObject("SMS_INSTRUCTION.countQuery", param);
	}

}
