package com.lvmama.train.service.request;

import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.train.ReqVo;
import com.lvmama.comm.vo.train.product.LineReqVo;
import com.lvmama.train.service.TrainResponse;
import com.lvmama.train.service.response.LineInfoQueryResponse;

/**
 * 1.3.	车次基本信息查询接口
 * @author XuSemon
 * @date 2013-8-12
 */
public class LineInfoQueryRequest extends AbstractTrainRequest {
	
	public LineInfoQueryRequest(ReqVo vo){
		super(vo);
		if(!(vo instanceof LineReqVo))
			throw new RuntimeException("未接受到正确的处理数据!");
		LineReqVo lvo = (LineReqVo)vo;
		put("request_type", lvo.getRequestType());
		put("request_date", lvo.getRequestDate());
		put("train_id", lvo.getTrainId());
	}

	@Override
	public Class<? extends TrainResponse> getClazz() {
		// TODO Auto-generated method stub
		return LineInfoQueryResponse.class;
	}

	@Override
	public String getUrl() {
		// TODO Auto-generated method stub
		return "http://" + hostaddress + Constant.TRAIN_INTERFACE.TRAIN_INFO_QUERY.getUrl();
	}

	@Override
	public String getBaseUrl() {
		// TODO Auto-generated method stub
		return Constant.TRAIN_INTERFACE.TRAIN_INFO_QUERY.getUrl();
	}

}
