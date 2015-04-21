package com.lvmama.pet.web.place;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.map.HashedMap;
import org.apache.struts2.convention.annotation.Action;
import org.springframework.beans.factory.annotation.Autowired;

import com.lvmama.comm.BaseAction;
import com.lvmama.comm.pet.po.place.Place;
import com.lvmama.comm.pet.service.comment.CmtCommentService;
import com.lvmama.comm.pet.service.place.PlaceService;
import com.lvmama.comm.vo.comment.CommonCmtCommentVO;
/**
 * 给php提供place数据接口
 * @author nixianjun
 *
 */
public class AjaxOutForPhpAction extends BaseAction{
	/**
	 * 
	 */
	private static final long serialVersionUID = 34563495690341L;
	@Autowired
	private  PlaceService placeService;
	//点评服务接口
	@Autowired
	private CmtCommentService cmtCommentService;
	private String  commentIds;
	private Long placeId;
	@Action("/ajax/outPlaceByPlaceIdForPhp")
	public void outPlaceByPlaceIdForPhp(){
		String jsonmsg="";
		 Place  place=placeService.queryPlaceByPlaceId(placeId);
		jsonmsg = net.sf.json.JSONObject.fromObject(place).toString();	 
		this.outputJsonToClient("callback", jsonmsg);
	}
	@Action("/ajax/outCommentsByCommentIdsForPhp")
	public void outCommentsByCommentIdsForPhp(){
		String jsonmsg="";
 		if(!commentIds.isEmpty()){
 			String[] s=commentIds.split(",");
 			Map parameters=new HashedMap();
			parameters.put("commentIds", s);
			List<CommonCmtCommentVO> voList=cmtCommentService.getCmtCommentList(parameters);
			jsonmsg = net.sf.json.JSONArray.fromObject(voList).toString();	 
 		} 
		 this.sendJsonToClient(jsonmsg);
	}
    /**
	 * 输出json数据到客户端带有param
	 * 
	 * @param param  请求参数，输出参数 callback参数
	 * @param jsonMsg 输出json message
	 * @author nixianjun 2014.1.3
	 */
	public void outputJsonToClient(String param, String jsonMsg) {
		String jsoncallback = this.getRequest().getParameter(param);// 必要
		this.getResponse().setContentType("application/json; charset=UTF-8");
		this.getResponse().setCharacterEncoding("UTF-8");
		try {
			PrintWriter out = this.getResponse().getWriter();
			out.write(jsoncallback + "(" + jsonMsg + ")");
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
    /**
	 * 输出json数据到客户端带有param
	 * 
	 * @param param  请求参数，输出参数 callback参数
	 * @param jsonMsg 输出json message
	 * @author nixianjun 2014.1.3
	 */
	public void sendJsonToClient(String jsonMsg) {
 		this.getResponse().setContentType("application/json; charset=UTF-8");
		this.getResponse().setCharacterEncoding("UTF-8");
		try {
			PrintWriter out = this.getResponse().getWriter();
			out.write(jsonMsg);
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public Long getPlaceId() {
		return placeId;
	}
	public void setPlaceId(Long placeId) {
		this.placeId = placeId;
	}
	public String getCommentIds() {
		return commentIds;
	}
	public void setCommentIds(String commentIds) {
		this.commentIds = commentIds;
	}
}
