package com.lvmama.comm.vo.train;

import com.lvmama.comm.utils.JsonUtil;
import com.lvmama.comm.vo.Constant;

public class Rsp {
	/**
	 * 返回HTTP状态
	 */
	private RspStatus status;
	/**
	 * 返回VO类
	 */
	private RspVo vo;
	/**
	 * 返回body内容
	 */
	private String rspJson;
	public Rsp(){}
	public Rsp(RspVo vo){
		this(Constant.HTTP_SUCCESS, Constant.HTTP_SUCCESS_MSG, vo);
	}
	public Rsp(String rspJson){
		this(Constant.HTTP_SUCCESS, Constant.HTTP_SUCCESS_MSG, rspJson);
	}
	public Rsp(int rspCode, String rspMsg, RspVo vo){
		this(rspCode, rspMsg, JsonUtil.getJsonString4JavaPOJO(vo));
		this.vo = vo;
	}
	public Rsp(int rspCode, String rspMsg, String rspJson){
		status = new RspStatus(rspCode, rspMsg);
		this.rspJson = rspJson;
	}
	public RspStatus getStatus() {
		return status;
	}
	public void setStatus(RspStatus status) {
		this.status = status;
	}
	public RspVo getVo() {
		return vo;
	}
	public void setVo(RspVo vo) {
		this.vo = vo;
	}
	public String getRspJson() {
		return rspJson;
	}
	public void setRspJson(String rspJson) {
		this.rspJson = rspJson;
	}
	
	@Override
	public String toString(){
		if(this.rspJson != null && !"".equals(this.rspJson))
			return this.rspJson;
		else return "";
	}
	
}
