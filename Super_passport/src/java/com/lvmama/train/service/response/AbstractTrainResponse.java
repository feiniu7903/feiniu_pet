/**
 * 
 */
package com.lvmama.train.service.response;

import com.lvmama.comm.utils.JsonUtil;
import com.lvmama.comm.vo.train.BaseVo;
import com.lvmama.comm.vo.train.Rsp;
import com.lvmama.train.service.TrainResponse;

/**
 * @author yangbin
 *
 */
public abstract class AbstractTrainResponse implements TrainResponse{
	/**
	 * 请求返回类
	 */
	private Rsp rsp;
	/**
	 * 是否正确解析
	 */
	private boolean isSuccess;

	private boolean isCancelOrder = true; 

	public AbstractTrainResponse(){
		rsp = new Rsp();
	}
	
	public void parseError(String rsp) throws RuntimeException{
		BaseVo vo = (BaseVo)JsonUtil.getObject4JsonString(rsp, BaseVo.class, null);
		this.getRsp().setVo(vo);
	}

	@Override
	public Rsp getRsp() {
		return rsp;
	}
	public void setRsp(Rsp rsp) {
		this.rsp = rsp;
	}
	public boolean isSuccess() {
		return isSuccess;
	}
	@Override
	public void setSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}

	public boolean isCancelOrder() {
		return isCancelOrder;
	}
	@Override
	public void setCancelOrder(boolean isCancelOrder) {
		this.isCancelOrder = isCancelOrder;
	}
	
}
