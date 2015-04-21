package com.lvmama.elong.service.impl;

import java.text.MessageFormat;

import org.apache.commons.lang3.StringUtils;

import net.sf.json.JSONObject;

import com.lvmama.comm.utils.HttpsUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.elong.exception.ElongServiceException;
import com.lvmama.elong.model.HotelCommentCondition;
import com.lvmama.elong.service.IHotelCommentService;

public class HotelCommentServiceImpl implements IHotelCommentService {

	@Override
	public JSONObject getComments(HotelCommentCondition condition) throws ElongServiceException {
		String url = Constant.getInstance().getValue("elong.hotel.comment.url");
//		url = MessageFormat.format(url, condition.getCityId(),
//				condition.getHotelIds(), condition.getSortBy(),
//				condition.getSortType(), condition.getPageSize(),
//				condition.getPageIndex());
		url = MessageFormat.format(url,condition.getHotelIds());
		String result = HttpsUtil.requestGet(url);
		if(!StringUtils.isEmpty(result)){
			return JSONObject.fromObject(result);
		}else{
			throw new ElongServiceException("网络超时，请稍后再试");
		}
	}
}
