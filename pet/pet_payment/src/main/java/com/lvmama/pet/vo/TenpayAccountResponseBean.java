package com.lvmama.pet.vo;

import java.util.ArrayList;
import java.util.List;

/**
 * 财付通对账返回对象
 * @author zhangjie 
 * @version
 */
public class TenpayAccountResponseBean {

	private boolean isSuccessAccount = false;
	
	/**
	 * 交易总笔数
	 */
	private int transNum ;
	
	/**
	 * 交易总金额
	 */
	private Float transAmount ;
	
	/**
	 * 退款总金额
	 */
	private Float refundAmount ;
	
	/**
	 * 退款总金额
	 */
	private List<TenpayAccountVO> tenpayAccountVOs;
	
	/**
	 * 将对账明细封装为对账对象
	 * 
	 * @author: zhangjie
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public static TenpayAccountResponseBean parseResponseBean(String data){
		TenpayAccountResponseBean bean = new TenpayAccountResponseBean();
		String respData[] = data.split("\r\n");
		if(respData.length>1){
			String accountDate[] = respData[1].split("\n");
			String countDate[] = respData[3].split(",");
			if(accountDate.length!=Integer.valueOf(countDate[0])){
				return bean;
			}
			List<TenpayAccountVO> vos = new ArrayList<TenpayAccountVO>();
			for(int i=0;i<accountDate.length;i++){
				String trade[] = accountDate[i].split(",");
				TenpayAccountVO vo = new TenpayAccountVO();
				vo.setTransTime(trade[0].trim().substring(1));
				vo.setTenpayTradeNo(trade[1].trim().substring(1));
				vo.setMerchTradeNo(trade[2].trim().substring(1));
				vo.setPayType(trade[3].trim());
				vo.setBankPaymentTradeNo(trade[4].trim().substring(1));
				vo.setPayState(trade[5].trim());
				vo.setTradeAmount(trade[6].trim());
				vo.setRefundTradeNo(trade[7].trim().substring(1));
				vo.setRefundAmount(trade[8].trim());
				vo.setRefundState(trade[9].trim());
				vo.setTransShow(trade[10].trim());
				vos.add(vo);
			}
			bean.setTenpayAccountVOs(vos);
			bean.setTransNum(Integer.valueOf(countDate[0]));
			bean.setTransAmount(Float.valueOf(countDate[1]));
			bean.setRefundAmount(Float.valueOf(countDate[2]));

			bean.setSuccessAccount(true);
		}
		return bean;
		
	}
	
	public boolean isSuccessAccount() {
		return isSuccessAccount;
	}

	public void setSuccessAccount(boolean isSuccessAccount) {
		this.isSuccessAccount = isSuccessAccount;
	}

	public int getTransNum() {
		return transNum;
	}

	public void setTransNum(int transNum) {
		this.transNum = transNum;
	}

	public Float getTransAmount() {
		return transAmount;
	}

	public void setTransAmount(Float transAmount) {
		this.transAmount = transAmount;
	}

	public Float getRefundAmount() {
		return refundAmount;
	}

	public void setRefundAmount(Float refundAmount) {
		this.refundAmount = refundAmount;
	}
	

	public List<TenpayAccountVO> getTenpayAccountVOs() {
		return tenpayAccountVOs;
	}

	public void setTenpayAccountVOs(List<TenpayAccountVO> tenpayAccountVOs) {
		this.tenpayAccountVOs = tenpayAccountVOs;
	}
}
