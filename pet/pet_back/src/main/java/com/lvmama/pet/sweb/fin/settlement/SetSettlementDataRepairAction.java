package com.lvmama.pet.sweb.fin.settlement;
/**
 * @author shangzhengyuan
 * @descritpion 结算单数据修复
 */
import java.util.ArrayList;
import java.util.List;

import com.lvmama.comm.BackBaseAction;
import com.lvmama.comm.jms.Message;
import com.lvmama.comm.jms.MessageFactory;
import com.lvmama.comm.jms.TopicMessageProducer;
import com.lvmama.comm.utils.StringUtil;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
@Results(value = { @Result(name = "index", location = "/WEB-INF/pages/back/fin/settlement/set_settlement_datarepair.ftl")})

public class SetSettlementDataRepairAction extends BackBaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4766242935986006367L;
	/**
	 * 消息发送接口
	 */
	private TopicMessageProducer orderMessageProducer;
	private String settlementData;
	private String msg;
	@Action(value="/fin/set/datarepair")
	public String dataRepair(){
		if(StringUtil.isEmptyString(settlementData)){
			return "index";
		}
		List<Message> list = parseData();
		for(Message message:list){
			orderMessageProducer.sendMsg(message);
		}
		if(StringUtil.isEmptyString(msg) && !StringUtil.isEmptyString(settlementData)){
			setMsg("数据修复发送信息成功");
		}
		setSettlementData(null);
		return "index";
	}
	private List<Message> parseData(){
		String operateName = getSessionUser().getUserName();
		List<Message> list = new ArrayList<Message>();
		if(StringUtil.isEmptyString(settlementData)){
			return list;
		}
		setMsg("");
		String[] strs = settlementData.split("\r\n|\r|\n");
		for(String str:strs){
			String[] substrs = str.split("##");
			Long id = null;
			try{
			   id = Long.parseLong(substrs[1]);
			}catch(Exception e){
			}
			if(null!=substrs && (substrs.length==3 || substrs.length==4) && (null!=id && id>0)){
				Message message = MessageFactory.newOrderSettleRepair(id,substrs[0]);
				message.setAddition(substrs[2]+"="+operateName);
				if(substrs.length==4){
					message.setAddition(substrs[2]+"="+substrs[3]);
				}
				list.add(message);
			}else{
				setMsg(getMsg()+str+"<br/>");
			}
		}
		return list;
	}
	public TopicMessageProducer getOrderMessageProducer() {
		return orderMessageProducer;
	}
	public void setOrderMessageProducer(TopicMessageProducer orderMessageProducer) {
		this.orderMessageProducer = orderMessageProducer;
	}
	public String getSettlementData() {
		return settlementData;
	}
	public void setSettlementData(String settlementData) {
		this.settlementData = settlementData;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
}
