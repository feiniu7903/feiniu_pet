package com.lvmama.comm.vo.train.product;

import java.util.List;

import com.lvmama.comm.vo.train.RspVo;

public class LineStopsRspVo extends RspVo {
	/**
	 * 
	 */
	private static final long serialVersionUID = 54621595L;
	
	public LineStopsRspVo(){}
	public LineStopsRspVo(List<LineStopsInfo> lineStopsInfos){
		this.lineStopsInfos = lineStopsInfos;
	}
	
	/**
	 * 车次停站信息
	 */
	private List<LineStopsInfo> lineStopsInfos;
	public List<LineStopsInfo> getLineStopsInfos() {
		return lineStopsInfos;
	}
	public void setLineStopsInfos(List<LineStopsInfo> lineStopsInfos) {
		this.lineStopsInfos = lineStopsInfos;
	}
	
}
