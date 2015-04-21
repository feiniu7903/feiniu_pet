package com.lvmama.comm.utils;

import com.lvmama.comm.pet.po.fin.FinBizItem;
import com.lvmama.comm.pet.po.fin.FinBizItem.BIZ_STATUS;
import com.lvmama.comm.pet.po.pay.FinReconResult;
import com.lvmama.comm.vo.Constant;

public class FinConvertUtil {
	
	
	private static final  String CREATE_USER="system";

	/**
     * 勾兑对象转换为流水对象
     * @param FinReconResult 勾兑结果对象
     * @return 流水对象
     */
	public static FinBizItem changeReconToBizItem(FinReconResult finReconResult,FinBizItem finBizItem){
		if(finBizItem == null){
			finBizItem = new FinBizItem();
		}
		finBizItem.setAmount(finReconResult.getAmount());
		finBizItem.setBankAmount(finReconResult.getBankAmount());
		finBizItem.setCallbackTime(finReconResult.getCallbackTime());
		finBizItem.setTransactionTime(finReconResult.getTransactionTime());
		finBizItem.setTransactionType(finReconResult.getTransactionType());
		finBizItem.setGateway(finReconResult.getGateway());
		finBizItem.setBankReconTime(finReconResult.getBankReconTime());
		finBizItem.setCreateTime(finReconResult.getCreateTime());
		finBizItem.setMemo(finReconResult.getMemo());
		finBizItem.setOrderId(finReconResult.getOrderId());
		finBizItem.setGlStatus(finReconResult.getGlStatus());
		finBizItem.setGlTime(finReconResult.getGlTime());
		finBizItem.setFeeType(finReconResult.getTransactionType());
		if(Constant.RECON_STATUS.SUCCESS.name().equals(finReconResult.getReconStatus())){
			finBizItem.setBizStatus(BIZ_STATUS.MATCH.name());
		}else{
			finBizItem.setBizStatus(BIZ_STATUS.CREATE.name());
		}
		finBizItem.setCancelStatus("N");
		finBizItem.setCreateUser(CREATE_USER);
		finBizItem.setReconResultId(finReconResult.getReconResultId());
        return finBizItem;
	}
}
