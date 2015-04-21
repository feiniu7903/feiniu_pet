package com.lvmama.clutter.web.interceptor;

import java.net.URLEncoder;
import java.util.Set;
import java.util.TreeSet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;

import com.itextpdf.text.log.SysoLogger;
import com.lvmama.clutter.utils.ClutterConstant;
import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.po.ord.OrdOrderChannel;
import com.lvmama.comm.bee.po.ord.OrdOrderItemProd;
import com.lvmama.comm.bee.service.ord.OrderService;
import com.lvmama.comm.pet.service.ord.OrdOrderChannelService;
import com.lvmama.comm.utils.CpsUtil;
import com.lvmama.comm.utils.InternetProtocol;
import com.lvmama.comm.utils.MD5;
import com.lvmama.comm.utils.StackOverFlowUtil;
import com.lvmama.comm.vo.Constant;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

/**
 * 前台创建订单拦截器 主要在前台创建订单完成后拦截，根据Cookie中的值来判断订单是否有推广来源，如有，则记录。
 * 
 * @author Brian
 * 
 */

public final class ClutterCreateOrderInterceptor extends AbstractInterceptor {
	private static final long serialVersionUID = -5907324346474728872L;
	private static final Log LOG = LogFactory
			.getLog(ClutterCreateOrderInterceptor.class);
	private static final String ORDER_OUTTER_CHANNEL = "oUC"; // Cookie中记录订单站外渠道的名字
	private static final String ORDER_OUTTER_CHANNEL_TIME = "oUT"; // Cookie中记录订单站外渠道的时间
	private static final String ORDER_INNER_CHANNEL = "oIC"; // Cookie中记录订单站内渠道的名字
	private static final String ORDER_INNER_CHANNEL_TIME = "oIT"; // Cookie中记录订单站内渠道的时间

	private OrdOrderChannelService ordOrderChannelService;
	private OrderService orderServiceProxy;

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		debug("进入前台下单拦截器......");
		String result = invocation.invoke();
		if ("order_success".equals(result)) {
			debug("成功创建订单,进入订单推广来源的判断");
			if (null != getRequest().getAttribute("orderId")) {
				Long orderId = Long.parseLong((String) getRequest()
						.getAttribute("orderId"));
				OrdOrder order = orderServiceProxy
						.queryOrdOrderByOrderId(orderId);
				if (null == order) {
					return result;
				}
				// 记录站内的渠道
				saveInnerChannel(order);
			}

		}
		return result;
	}

	/**
	 * 记录站内渠道
	 * 
	 * @param order
	 */
	public void saveInnerChannel(final OrdOrder order) {
		Set<LoscString> set = new TreeSet<LoscString>();
		Cookie cookie_channel = getCookie(ORDER_OUTTER_CHANNEL);
		Cookie cookie_time = getCookie(ORDER_OUTTER_CHANNEL_TIME);

		if (null != cookie_channel
				&& StringUtils.isNotBlank(cookie_channel.getValue())
				&& null != cookie_time
				&& StringUtils.isNotBlank(cookie_time.getValue())) {
			String value = cookie_channel.getValue();
			String time = cookie_time.getValue();
			int j = 0;
			for (int i = 0; i < value.length(); i = i + 6) {
				set.add(new LoscString(time.substring(j, j + 4), value
						.substring(i, i + 6)));
				j = j + 4;
			}
		}

		cookie_channel = getCookie(ORDER_INNER_CHANNEL);
		cookie_time = getCookie(ORDER_INNER_CHANNEL_TIME);
		if (null != cookie_channel
				&& StringUtils.isNotBlank(cookie_channel.getValue())
				&& null != cookie_time
				&& StringUtils.isNotBlank(cookie_time.getValue())) {
			String value = cookie_channel.getValue();
			String time = cookie_time.getValue();
			int j = 0;
			for (int i = 0; i < value.length(); i = i + 6) {
				set.add(new LoscString(time.substring(j, j + 4), value
						.substring(i, i + 6)));
				j = j + 4;
			}
		}

		if (set.size() != 0) {
			for (LoscString ls : set) {
				ordOrderChannelService.insert(new OrdOrderChannel(order
						.getOrderId(), ls.getValue(),null,null));
			}
		} else {//the channel is m.lvmama.com
			ordOrderChannelService.insert(new OrdOrderChannel(order
					.getOrderId(), Constant.CHANNEL.TOUCH.name()+"_LVMM",null,null));
		}

	}

	/**
	 * 获取HttpRequest
	 * 
	 * @return
	 */
	private HttpServletRequest getRequest() {
		return ServletActionContext.getRequest();
	}

	/**
	 * 获取指定Cookie
	 * 
	 * @param name
	 *            Cookie的名字
	 * @return
	 */
	private final Cookie getCookie(final String name) {
		Cookie[] cookies = this.getRequest().getCookies();
		for (Cookie cookie : cookies) {
			if (cookie.getName().equalsIgnoreCase(name)) {
				return cookie;
			}
		}
		return null;
	}

	/**
	 * 打印调试信息
	 * 
	 * @param message
	 */
	private final void debug(final String message) {
		if (LOG.isDebugEnabled()) {
			LOG.debug(message);
		}
	}

	public OrdOrderChannelService getOrdOrderChannelService() {
		return ordOrderChannelService;
	}

	public void setOrdOrderChannelService(
			OrdOrderChannelService ordOrderChannelService) {
		this.ordOrderChannelService = ordOrderChannelService;
	}

	public void setOrderServiceProxy(OrderService orderServiceProxy) {
		this.orderServiceProxy = orderServiceProxy;
	}
}

class LoscString implements java.lang.Comparable<Object> {
	private String time;
	private String value;

	public LoscString(String time, String value) {
		this.value = value;
		this.time = time;
	}

	public String getValue() {
		return value;
	}

	public String getTime() {
		return time;
	}

	@Override
	public int compareTo(Object o) {
		if (o instanceof LoscString) {
			LoscString v = (LoscString) o;
			if (getTime().equals(v.getTime())) {
				return 1;
			} else {
				return getTime().compareTo(v.getTime());
			}
		} else {
			return -1;
		}
	}
}
