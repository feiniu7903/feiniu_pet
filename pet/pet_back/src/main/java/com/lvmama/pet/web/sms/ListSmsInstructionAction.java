package com.lvmama.pet.web.sms;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lvmama.comm.pet.po.sms.SmsInstruction;
import com.lvmama.comm.pet.service.sms.SmsInstructionService;
import com.lvmama.pet.utils.ZkMessage;
import com.lvmama.pet.utils.ZkMsgCallBack;

/**
 * 优惠劵的Action
 * @author yangchen
 */
public class ListSmsInstructionAction extends com.lvmama.pet.web.BaseAction {
	/**
	 * 序列值
	 */
	private static final long serialVersionUID = -815614078582843909L;
	/**
	 * . 报表逻辑接口
	 */
	private SmsInstructionService smsInstructionService;
	/**
	 * 数据记录列表
	 */
	private List<SmsInstruction> analysisList = new ArrayList<SmsInstruction>();
	/**
	 * 页面查询条件
	 */
	private Map<String, Object> searchConds = new HashMap<String, Object>();
	/**
	 * 优惠劵的对象
	 */
	private SmsInstruction instruction;

	/**
	 * 查询数据
	 */
	public void search() {
		initialPageInfoByMap(smsInstructionService.count(searchConds), searchConds);
		// 查询优惠的列表的集合
		analysisList = smsInstructionService.query(searchConds);
	}

	/**
	 * 删除
	 * @param code 指令
	 **/
	public void delete(final String code) {
		ZkMessage.showQuestion("您确定需要删除此优惠劵。", new ZkMsgCallBack() {
			public void execute() {
				smsInstructionService.deleteByPrimaryKey(code);
				refreshComponent("search");
			}
		}, new ZkMsgCallBack() {
			public void execute() {
			}
		});

	}

	public final void setSmsInstructionsService(
			SmsInstructionService smsInstructionsService) {
		this.smsInstructionService = smsInstructionsService;
	}

	public List<SmsInstruction> getAnalysisList() {
		return analysisList;
	}

	public void setAnalysisList(final List<SmsInstruction> analysisList) {
		this.analysisList = analysisList;
	}

	public Map<String, Object> getSearchConds() {
		return searchConds;
	}

	public void setSearchConds(final Map<String, Object> searchConds) {
		this.searchConds = searchConds;
	}

	public SmsInstruction getInstruction() {
		return instruction;
	}

	public void setInstructions(final SmsInstruction instruction) {
		this.instruction = instruction;
	}

}
