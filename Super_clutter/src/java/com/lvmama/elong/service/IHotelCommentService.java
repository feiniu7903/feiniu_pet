package com.lvmama.elong.service;

import com.lvmama.elong.exception.ElongServiceException;
import com.lvmama.elong.model.HotelCommentCondition;

import net.sf.json.JSONObject;

public interface IHotelCommentService {
	JSONObject getComments(HotelCommentCondition condition) throws ElongServiceException ;
}
