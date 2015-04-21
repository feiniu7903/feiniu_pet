package com.lvmama.train.service.response;

import java.util.List;

import com.lvmama.comm.utils.JsonUtil;
import com.lvmama.comm.vo.train.product.LineInfo;
import com.lvmama.comm.vo.train.product.LineRspVo;

public class LineInfoQueryResponse extends AbstractTrainResponse {

	@Override
	public void parse(String response) throws RuntimeException {
		List<LineInfo> lineInfos = JsonUtil.getList4Json(response, LineInfo.class, null);
		this.getRsp().setVo(new LineRspVo(lineInfos));
	}

}
