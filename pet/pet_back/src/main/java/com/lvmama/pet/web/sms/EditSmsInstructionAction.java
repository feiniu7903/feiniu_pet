package com.lvmama.pet.web.sms;

import java.util.List;
import java.util.StringTokenizer;

import org.apache.commons.lang3.StringUtils;

import com.lvmama.comm.pet.po.sms.SmsInstruction;
import com.lvmama.comm.pet.service.sms.SmsInstructionService;
import com.lvmama.pet.utils.ZkMessage;

/**
 * 编辑优惠劵
 * @author yangchen
 */
public class EditSmsInstructionAction extends com.lvmama.pet.web.BaseAction {
	/**
	 * 序列值
	 */
	private static final long serialVersionUID = 8156221674438142718L;
	/**
	 * . 报表逻辑接口
	 */
	private SmsInstructionService smsInstructionService;
	/**
	 * 小驴说事标识
	 */
	private String instructionCode;
	/**
	 * 需要编辑的小驴说事
	 */
	private SmsInstruction instruction;

	/**
	 * 之前
	 */
	public void doBefore() {
		if (null != instructionCode) {
			instruction = smsInstructionService.queryByPrimaryKey(instructionCode);
			System.out.println(instruction.getCouponCode());
		}
		if (null == instructionCode) {
			instruction = new SmsInstruction();
		}
	}

	/**
	 * 保存
	 */
	public void save() {
		boolean b = false;
		if (!StringUtils.isEmpty(instruction.getCouponId())) {
			StringTokenizer st = new StringTokenizer(instruction.getCouponId(), ",");
			try {
				while (st.hasMoreTokens()) {
					Long.parseLong(st.nextToken());
				}
			} catch (Exception e) {
			    e.printStackTrace();
				ZkMessage.showInfo("所输入的优惠券标识不合法。优惠券标识必须为整形，如多个优惠券标识，请使用半角逗号分隔!");
				return;
			}
		}
		if (null != instructionCode) {
			// 判断,指令不能
			if (!smsInstructionService.queryByPrimaryKey(instructionCode)
					.getInstructionCode()
					.equals(instruction.getInstructionCode())) {
				ZkMessage.showInfo("指令不能修改");
				b = true;
			}
			if (!b) {
				smsInstructionService.update(instruction);
				//MemcachedUtil.remove("SMS_UPLINK_CHANNEL_SETS_IN_MEMCACHE");
			}
		} else {
			// 判断是否有相同指令
			List<SmsInstruction> smsList = smsInstructionService.queryAll();
			for (int i = 0; i < smsList.size(); i++) {
				if (instruction.getInstructionCode().equals(
						smsList.get(i).getInstructionCode())) {
					ZkMessage.showInfo("改指令已有相同,从输入其他指令");
					b = true;
					break;
				}
			}
			if (!b) {
				smsInstructionService.save(instruction);
				//MemcachedUtil.remove("SMS_UPLINK_CHANNEL_SETS_IN_MEMCACHE");
			}
		}
		if (!b) {
			ZkMessage.showInfo("保存成功!");
			refreshParent("search");
			getComponent().detach();
		}
	}

	public final void setSmsInstructionsService(
			SmsInstructionService smsInstructionsService) {
		this.smsInstructionService = smsInstructionsService;
	}

	public String getInstructionCode() {
		return instructionCode;
	}

	public void setInstructionCode(final String instructionCode) {
		this.instructionCode = instructionCode;
	}

	public SmsInstruction getInstruction() {
		return instruction;
	}

	public void setInstruction(final SmsInstruction instruction) {
		this.instruction = instruction;
	}

}
