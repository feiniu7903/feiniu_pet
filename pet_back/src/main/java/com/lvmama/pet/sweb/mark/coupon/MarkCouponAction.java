package com.lvmama.pet.sweb.mark.coupon;

import com.lvmama.comm.BackBaseAction;
import com.lvmama.comm.pet.po.mark.MarkChannel;
import com.lvmama.comm.pet.po.mark.MarkCoupon;
import com.lvmama.comm.pet.po.pub.CodeItem;
import com.lvmama.comm.pet.po.pub.CodeSet;
import com.lvmama.comm.pet.service.mark.MarkChannelService;
import com.lvmama.comm.pet.service.mark.MarkCouponService;
import com.lvmama.comm.pet.vo.Page;
import com.lvmama.comm.utils.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import java.io.UnsupportedEncodingException;
import java.util.*;

@Results({
	@Result(name = "list", location = "/WEB-INF/pages/back/mark/couponList.jsp")
})
public class MarkCouponAction extends BackBaseAction {
	/**
	 * 序列值
	 */
	private static final long serialVersionUID = 2625737919203400027L;
    /**
     * 优惠批次远程服务
     */
	private MarkCouponService markCouponService; 
	/**
	 * 营销渠道远程服务
	 */
	private MarkChannelService markChannelService;
	
    /**
     * 优惠批次列表
     */
	private List<MarkCoupon> markCouponList = new ArrayList<MarkCoupon>();
	
	/**
	 * 优惠批次id
	 */
	private Long couponId;
	/**
	 * 优惠批次名称
	 */
	private String couponName;
	/**
	 * 优惠批次类型
	 */
	private String couponType;
	/**
	 * 优惠批次的对象
	 */ 
	private String couponTarget;
	/**
	 * 优惠批次是否有效
	 */
	private String valid = "true";
	/**
	 * 优惠批次开始时间
	 */
	private String termValidityBeginTime;
	/**
	 * 优惠批次结束时间
	 */
	private String termValidityEndTime;
	/**
	 * 优惠批次的开头码
	 */
	private String firstCode;
	/**
	 * 优惠码
	 */
	private String couponCode;
    /**
     * 渠道名
     */
	private String channelName;

	/**
	 * 新增或修改的优惠券批次对象
	 */
	private MarkCoupon markCoupon;
	/**
	 * 修改优惠批次时需要的第一级渠道
	 */
	private MarkChannel firstMarkChannel;
	/**
	 * 修改优惠批次时需要的第二级渠道
	 */
	private MarkChannel secondMarkChannel;
	/**
	 * 支付渠道列表
	 */
	private List<CodeItem> paymentChannelList;//
	
	/**
	 * 根据查询条件查询符合条件的优惠券(活动)结果集.
	 * @return
	 */
	@Action(value="/mark/coupon/queryCouponList")
	public String queryCouponList() throws Exception{
		Map<String,Object> searchConds = new HashMap<String,Object>();
		if (StringUtils.isNotBlank(couponName)) {
			
			searchConds.put("couponName", new String(couponName.getBytes("ISO8859-1"),"UTF-8"));
		}
		if (StringUtils.isNotBlank(couponCode)) {
			searchConds.put("couponCode", couponCode);
		}
		if (StringUtils.isNotBlank(channelName)) {
            searchConds.put("channelName", new String(channelName.trim().getBytes("ISO8859-1"),"UTF-8"));
		}
		if (StringUtils.isNotBlank(firstCode)) {
			searchConds.put("firstCode", firstCode);
		}
		if (StringUtils.isNotBlank(couponType)) {
			searchConds.put("couponType", couponType);
		}
		if (StringUtils.isNotBlank(couponTarget)) {
			searchConds.put("couponTarget", couponTarget);
		}
		if(StringUtils.isNotEmpty(termValidityBeginTime)) {
			searchConds.put("beginTime", DateUtil.toDate(termValidityBeginTime, "yyyy-MM-dd"));
		}
		if(StringUtils.isNotEmpty(termValidityEndTime)) {
			searchConds.put("endTime", DateUtil.toDate(termValidityEndTime, "yyyy-MM-dd"));
		}
		if(!"all".equals(valid))
		{
			searchConds.put("valid", valid);
		}
		Integer totalRecords = markCouponService.selectMarkCouponRowCount(searchConds);
		pagination = Page.page(10, page);
		pagination.setTotalResultSize(totalRecords);
		//pagination.buildUrl(getRequest());
		pagination.setUrl(getReqUrl());
		searchConds.put("_startRow", pagination.getStartRows());
	 
		searchConds.put("_endRow", pagination.getEndRows());
		markCouponList = markCouponService.selectMarkCouponByParam(searchConds);
		
		return "list";
	}
	
	private String getReqUrl() {
		StringBuffer sb = new StringBuffer();
		Enumeration<String> pns = getRequest().getParameterNames();
		while (pns.hasMoreElements()) {
			String pn = pns.nextElement();
			if ("page".equalsIgnoreCase(pn)) {
				continue;
			} 
			if ("couponName".equalsIgnoreCase(pn)) {
				try {
					sb.append(pn + "=" + new String(getRequest().getParameter(pn).getBytes("ISO8859-1"),"UTF-8") + "&");
				} catch (Exception e) {
				
				}
				continue;
			}
            if ("channelName".equalsIgnoreCase(pn)) {
				try {
					sb.append(pn + "=" + new String(getRequest().getParameter(pn).getBytes("ISO8859-1"),"UTF-8") + "&");
				} catch (Exception e) {

				}
				continue;
			}
			
			sb.append(pn + "=" + getRequest().getParameter(pn) + "&");
		}
		return "/pet_back/mark/coupon/queryCouponList.do?" + sb.toString() + "page=";
		
	}
	
	/**
	 * 打开新增优惠券(活动)页面.
	 * @return
	 */
	@Action(value="/mark/coupon/editMarkCoupon",results={@Result(location = "/WEB-INF/pages/back/mark/addMarkCoupon.jsp")}) 
	public String addMarkCoupon() {
		if (null == this.couponId) {
			markCoupon = new MarkCoupon();
		} else {
			markCoupon = markCouponService.selectMarkCouponByPk(this.couponId);
			if (null != markCoupon && markCoupon.getChannelId() != null) {
				MarkChannel thirdChannel = markChannelService.selectByPrimaryKey(markCoupon.getChannelId());
				if (null != thirdChannel) {
					secondMarkChannel = markChannelService.selectByPrimaryKey(thirdChannel.getFatherId());
				}
				if (null != secondMarkChannel) {
					firstMarkChannel = markChannelService.selectByPrimaryKey(secondMarkChannel.getFatherId());
				}
			}
			if (null == markCoupon) {
				markCoupon = new MarkCoupon();
			}
		}
		paymentChannelList = CodeSet.getInstance().getCachedCodeList("MARK_PAYMENT_CHANNEL");
		return SUCCESS;
	}

	public String getCouponName() throws Exception{
		return new String(couponName.getBytes("ISO8859-1"),"UTF-8");
	}

	public Long getCouponId() {
		return couponId;
	}

	public void setCouponId(Long couponId) {
		this.couponId = couponId;
	}

	public void setCouponName(String couponName) {
		this.couponName = couponName;
	}

	public String getCouponType() {
		return couponType;
	}

	public void setCouponType(String couponType) {
		this.couponType = couponType;
	}

	public String getCouponTarget() {
		return couponTarget;
	}

	public void setCouponTarget(String couponTarget) {
		this.couponTarget = couponTarget;
	}

	public String getValid() {
		return valid;
	}

	public void setValid(final String valid) {
		this.valid = valid;
	}

	public String getTermValidityBeginTime() {
		return termValidityBeginTime;
	}

	public void setTermValidityBeginTime(String termValidityBeginTime) {
		this.termValidityBeginTime = termValidityBeginTime;
	}

	public String getTermValidityEndTime() {
		return termValidityEndTime;
	}

	public void setTermValidityEndTime(String termValidityEndTime) {
		this.termValidityEndTime = termValidityEndTime;
	}

	public String getFirstCode() {
		return firstCode;
	}

	public void setFirstCode(String firstCode) {
		this.firstCode = firstCode;
	}

	public String getCouponCode() {
		return couponCode;
	}

    public String getChannelName() throws UnsupportedEncodingException {
        return new String(channelName.getBytes("ISO8859-1"),"UTF-8");
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public void setCouponCode(String couponCode) {
		this.couponCode = couponCode;
	}


	public List<MarkCoupon> getMarkCouponList() {
		return markCouponList;
	}

	public MarkCoupon getMarkCoupon() {
		return markCoupon;
	}

	public void setMarkCoupon(MarkCoupon markCoupon) {
		this.markCoupon = markCoupon;
	}
	
	public void setMarkCouponService(MarkCouponService markCouponService) {
		this.markCouponService = markCouponService;
	}

	public MarkChannel getFirstMarkChannel() {
		return firstMarkChannel;
	}

	public MarkChannel getSecondMarkChannel() {
		return secondMarkChannel;
	}

	public void setMarkChannelService(MarkChannelService markChannelService) {
		this.markChannelService = markChannelService;
	}

	public List<CodeItem> getPaymentChannelList() {
		return paymentChannelList;
	}

}
