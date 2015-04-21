package com.lvmama.pet.payment.callback.web;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import com.lvmama.comm.BaseAction;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.pet.payment.service.CommPosSocketService;
import com.lvmama.pet.utils.PosUtil;
/**
 * POS机支付处理.
 * @author liwenzhan
 *
 */
@Result(name = "success", location = "/WEB-INF/pages/pos/pos_pay.ftl", type = "freemarker")
public class CommPosAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8776417638583717372L;
	/**
	 * LOG.
	 */
	private static final Logger log = Logger.getLogger(CommPosAction.class);
	/**
	 * 接受的数据.
	 */
	private String message;
	/**
	 * 返回的数据.
	 */
	private String returnMessages = "";
	/**
	 * 交通银行POS处理的SERVICE.
	 */
	private CommPosSocketService commPosSocketService;

	/**
	 * 接收到POS机的信息进行处理并返回字符串给POS机.
	 * @return
	 */
	@Action("/pos/notifyPos")
	public String notifyPos() {
		try {
			String notifyMesage="";
			log.info("COMMPOS message="+message);
			if (!StringUtil.isEmptyString(message)&&!StringUtil.isEmptyString(notifyMessage(message))&& PosUtil.isSucc(message)) {
				String code=PosUtil.acceptMessage(notifyMessage(message));
				String footStr = notifyMessage(message).substring(notifyMessage(message).length()-8,notifyMessage(message).length());
				String headStr = code.substring(4,8);
				if ("1000".equals(headStr)) {
					notifyMesage = commPosSocketService.posUserLogin(code);
				}
				if ("2000".equals(headStr)) {
					notifyMesage = commPosSocketService.queryOrderAmountByOrderId(code);
				}
				if ("3000".equals(headStr)) {
					notifyMesage = commPosSocketService.orderPayNotice(code);
				}
				StringBuffer sb=new StringBuffer(notifyMesage);
				sb.append(footStr);
				log.info("COMMPOS returnMessages:"+sb.toString());
				returnMessages=PosUtil.encryptBASE64(sb.toString().getBytes());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "success";
	}
	
	/**
	 * 对接受的数据进行解析.
	 * @param message
	 * @return
	 */
	private static String notifyMessage(String mess){
		byte[] c;
		String s = "";
		try {
			c = PosUtil.decryptBASE64(mess);
			s = new String(c, "UTF-8");
		} catch (Exception e) {
			log.info("accept code Exception !!");
		}
		return s;
	}
	
	
	
	/**
	 * 加载接受的数据.
	 * @return
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * 设置接受的数据.
	 * @param message
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * 加载交通银行POS处理的SERVICE.
	 * @return
	 */
	public CommPosSocketService getCommPosSocketService() {
		return commPosSocketService;
	}

	/**
	 * 设置交通银行POS处理的SERVICE.
	 * @param commPosSocketService
	 */
	public void setCommPosSocketService(
			CommPosSocketService commPosSocketService) {
		this.commPosSocketService = commPosSocketService;
	}

	/**
	 * 加载返回的数据.
	 * @return
	 */
	public String getReturnMessages() {
		return returnMessages;
	}

	/**
	 * 设置返回的数据.
	 * @param returnMessages
	 */
	public void setReturnMessages(String returnMessages) {
		this.returnMessages = returnMessages;
	}

	public static void main(String[] args) throws Exception {
		String message="MzAwIDMwMDAzMDEzMTAwNDcyMjcyMzU2NDQwOTAxMTAwNDQzOTIwMTIxMjEzMTA0NTA3MjAxMjEyMTMwMDAzOTEgICAgICAgICAgICAgICAgICAgIDYyMjE2ODIyMzEzMDI4OTkgICAwMTA1MDAwMSAgICAwMDEwNDUwNzY1MDE4NTAwOTk1MjAwMDA1MzE5MDI3NTYwMDEgICAgICAgICAgMDAwMDAwNTg4NDAwMDAwMDAwMDAwNyAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICA1OTJCNzNGQw==";		
		String code=notifyMessage(message);
		System.out.println(code);
		String headStr = code.substring(4,8);
		System.out.println(headStr);
		System.out.println(code.substring(174,184));
		System.out.println(code.substring(244,252));
		
	}
	
}
