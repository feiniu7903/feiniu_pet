package com.lvmama.pet.sweb.callcenter;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import com.lvmama.comm.BackBaseAction;
import com.lvmama.comm.pet.po.conn.LvccChannel;
import com.lvmama.comm.pet.po.conn.LvccPromotionActivity;
import com.lvmama.comm.pet.service.conn.LvccChannelService;
import com.lvmama.comm.pet.service.conn.LvccPromotionActivityService;
import com.lvmama.comm.pet.vo.Page;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.utils.json.JSONResult;

@Results({
		@Result(name = "edit_activity", location = "/WEB-INF/pages/back/conn/edit_activity.jsp"),
		@Result(name = "lvcc_promotion_activity", location = "/WEB-INF/pages/back/conn/lvcc_promotion_activity.jsp"),
		@Result(name = "lvcc_channel", location = "/WEB-INF/pages/back/conn/lvcc_channel.jsp")})
/** 
 * 市场部推广活动.		
 * 
 * @author shihui
 */
public class LvccPromotionActivityAction extends BackBaseAction {

	private static final long serialVersionUID = 5081084927220183757L;

	private Long activityId;

	private String name;

	private Date createBeginTime;

	private Date createEndTime;

	private String valid;

	private LvccPromotionActivityService lvccPromotionActivityService;

	private LvccChannelService lvccChannelService;

	private LvccPromotionActivity activity;
	
	private Long[] channelId;
	
	private LvccChannel lvccChannel;
	
	/**
	 * 页面入口
	 * 
	 * @return
	 */
	@Action(value = "/lvcc/toActivityList")
	public String toActivityList() {
		Map<String, Object> params = new HashMap<String, Object>();
		if (StringUtils.isNotEmpty(name)) {
			params.put("name", name);
		}
		if (createBeginTime != null) {
			params.put("createBeginTime", createBeginTime);
		}
		if (createEndTime != null) {
			params.put("createEndTime", DateUtil.getDateAfterDays(createEndTime, 1));
		}
		if (StringUtils.isNotEmpty(valid)) {
			params.put("valid", valid);
		}
		Long totalRecords = lvccPromotionActivityService
				.selectByParamsCount(params);
		pagination = Page.page(30, page);
		pagination.setTotalResultSize(totalRecords);
		pagination.buildUrl(getRequest());

		params.put("_startRow", pagination.getStartRows());
		params.put("_endRow", pagination.getEndRows());

		pagination
				.setItems(lvccPromotionActivityService.selectByParams(params));
		return "lvcc_promotion_activity";
	}

	/**
	 * 新增或修改弹出框
	 * */
	@Action(value = "/lvcc/showEditDialog")
	public String showEditDialog() {
		if (activityId != null) {
			activity = lvccPromotionActivityService
					.selectByPrimaryKey(activityId);
			String channel = activity.getChannel();
			String[] channelStr = channel.split(",");
			channelId = new Long[channelStr.length];
			for (int i = 0; i < channelStr.length; i++) {
				channelId[i] = Long.parseLong(channelStr[i]);
			}
		}
		return "edit_activity";
	}

	/**
	 * 新增或修改活动
	 * */
	@Action(value = "/lvcc/editActivity")
	public void editActivity() {
		JSONResult result = new JSONResult();
		try {
			if (activity == null) {
				throw new Exception("数据为空!");
			}
			if(channelId == null || channelId.length == 0) {
				throw new Exception("渠道为空!");
			}
			if(activity.getEndDate().before(activity.getBeginDate())) {
				throw new Exception("活动结束日期不能早于开始日期!");
			}
			if(activity.getEndDate().before(DateUtil.getDayStart(new Date()))) {
				throw new Exception("活动结束日期不能早于当前日期!");
			}
			activity.setName(activity.getName().trim());
			//校验名称是否已经存在
			if(lvccPromotionActivityService.checkNameIsExsited(activity)) {
				throw new Exception("该名称已经存在!");
			}
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < channelId.length; i++) {
				sb.append(channelId[i]).append(",");
			}
			if (sb.length() > 0) {
				sb.setLength(sb.length() - 1);
			}
			activity.setChannel(sb.toString());
			// 新增
			if (activity.getActivityId() == null) {
				lvccPromotionActivityService.insert(activity);
			} else {// 修改
				lvccPromotionActivityService.update(activity);
			}
		} catch (Exception e) {
			result.raise(e.getMessage());
		}
		result.output(getResponse());
	}
	
	/**
	 * 新增渠道
	 * */
	@Action(value = "/lvcc/addChannel")
	public void addChannel() {
		JSONResult result = new JSONResult();
		try {
			if(lvccChannel == null) {
				throw new Exception("数据为空!");
			}
			if (StringUtils.isEmpty(lvccChannel.getName())) {
				throw new Exception("渠道名称为空!");
			}
			lvccChannel.setName(lvccChannel.getName().trim());
			//校验名称是否已经存在
			if(lvccChannelService.checkNameIsExsited(lvccChannel)) {
				throw new Exception("该名称已经存在!");
			}
			Long channelId = lvccChannelService.insert(lvccChannel);
			result.put("channelId", channelId);
			result.put("name", lvccChannel.getName());
		} catch (Exception e) {
			result.raise(e.getMessage());
		}
		result.output(getResponse());
	}
	
	/**
	 * 渠道管理弹出框
	 * */
	@Action(value = "/lvcc/showChannel")
	public String showChannel() {
		return "lvcc_channel";
	}
	
	/**
	 * 修改活动状态
	 * */
	@Action(value = "/lvcc/changeValid")
	public void changeValid() {
		JSONResult result = new JSONResult();
		try {
			if(activityId == null) {
				throw new Exception("数据为空!");
			}
			if (StringUtils.isEmpty(valid)) {
				throw new Exception("数据为空!");
			}
			//有效变为无效
			if("Y".equalsIgnoreCase(valid)) {
				lvccPromotionActivityService.updateValidById(activityId, "N");
			} else {//无效变为有效，先将其他有效的变为无效
				LvccPromotionActivity activity = lvccPromotionActivityService.selectByPrimaryKey(activityId);
				if(activity.getEndDate().before(DateUtil.getDayStart(new Date()))) {
					throw new Exception("该活动结束日期不能早于当前日期!");
				}
				lvccPromotionActivityService.changeValid(activityId);
			}
		} catch (Exception e) {
			result.raise(e.getMessage());
		}
		result.output(getResponse());
	}

	public Long getActivityId() {
		return activityId;
	}

	public void setActivityId(Long activityId) {
		this.activityId = activityId;
	}

	public Date getCreateBeginTime() {
		return createBeginTime;
	}

	public void setCreateBeginTime(Date createBeginTime) {
		this.createBeginTime = createBeginTime;
	}

	public Date getCreateEndTime() {
		return createEndTime;
	}

	public void setCreateEndTime(Date createEndTime) {
		this.createEndTime = createEndTime;
	}

	public String getValid() {
		return valid;
	}

	public void setValid(String valid) {
		this.valid = valid;
	}

	public void setLvccPromotionActivityService(
			LvccPromotionActivityService lvccPromotionActivityService) {
		this.lvccPromotionActivityService = lvccPromotionActivityService;
	}

	public void setLvccChannelService(LvccChannelService lvccChannelService) {
		this.lvccChannelService = lvccChannelService;
	}

	public LvccPromotionActivity getActivity() {
		return activity;
	}

	public void setActivity(LvccPromotionActivity activity) {
		this.activity = activity;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<LvccChannel> getLvccChannelList() {
		return lvccChannelService.selectAll();
	}

	public Long[] getChannelId() {
		return channelId;
	}

	public void setChannelId(Long[] channelId) {
		this.channelId = channelId;
	}

	public LvccChannel getLvccChannel() {
		return lvccChannel;
	}

	public void setLvccChannel(LvccChannel lvccChannel) {
		this.lvccChannel = lvccChannel;
	}
}
