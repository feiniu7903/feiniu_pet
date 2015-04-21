/**
 * 
 */
package com.lvmama.train.service.request;

import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.train.ReqVo;
import com.lvmama.comm.vo.train.product.LineStopsReqVo;
import com.lvmama.train.service.TrainResponse;
import com.lvmama.train.service.response.LineStopsResponse;

/**
 * 1.4.	车次经停信息查询接口
 * @author yangbin
 *
 */
public class LineStopsRequest extends AbstractTrainRequest{

	public LineStopsRequest(ReqVo vo) {
		super(vo);
		if(!(vo instanceof LineStopsReqVo))
			throw new RuntimeException("未接受到正确的处理数据!");
		LineStopsReqVo lsvo = (LineStopsReqVo)vo;
		put("request_type", lsvo.getRequestType());
		put("request_date", lsvo.getRequestDate());
		put("train_id", lsvo.getTrainId());
	}

	@Override
	public Class<? extends TrainResponse> getClazz() {
		// TODO Auto-generated method stub
		return LineStopsResponse.class;
	}

	@Override
	public String getUrl() {
		// TODO Auto-generated method stub
		return "http://" + hostaddress + Constant.TRAIN_INTERFACE.TRAIN_PASS_QUERY.getUrl();
	}

	@Override
	public String getBaseUrl() {
		// TODO Auto-generated method stub
		return Constant.TRAIN_INTERFACE.TRAIN_PASS_QUERY.getUrl();
	}
}
