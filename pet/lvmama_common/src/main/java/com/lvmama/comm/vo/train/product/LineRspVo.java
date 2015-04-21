package com.lvmama.comm.vo.train.product;

import java.util.List;

import com.lvmama.comm.vo.train.RspVo;

public class LineRspVo extends RspVo {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3489173413741273123L;
	
	public LineRspVo(){}
	public LineRspVo(List<LineInfo> lineInfos){
		this.lineInfos = lineInfos;
	}
	
	/**
	 * 车次信息
	 */
	List<LineInfo> lineInfos;
	public List<LineInfo> getLineInfos() {
		return lineInfos;
	}
	public void setLineInfos(List<LineInfo> lineInfos) {
		this.lineInfos = lineInfos;
	}
	
}
