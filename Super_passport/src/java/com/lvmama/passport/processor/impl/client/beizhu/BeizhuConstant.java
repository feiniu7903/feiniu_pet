package com.lvmama.passport.processor.impl.client.beizhu;

import java.util.HashMap;
import java.util.Map;

import com.lvmama.passport.utils.Md5;
import com.lvmama.passport.utils.WebServiceConstant;

public class BeizhuConstant {

	private static Map<String, String> codeMap = new HashMap<String, String>();
	private static BeizhuConstant beizhuConstant = null;

	public static String username = WebServiceConstant.getProperties("beizhu.username");
	public static String password = Md5.encode(WebServiceConstant.getProperties("beizhu.password"));
	public static String key = WebServiceConstant.getProperties("beizhu.key");

	public static BeizhuConstant getInstance() {
		if (beizhuConstant == null) {
			synchronized (BeizhuConstant.class) {
				if (beizhuConstant == null) {
					beizhuConstant = new BeizhuConstant();
				}
			}
		}
		return beizhuConstant;
	}

	public static enum BEIZHU_URLS {
		BEIZHU_URLS_ORDER_SEND(WebServiceConstant.getProperties("beizhu.url.order.send")), BEIZHU_URLS_ORDER_SEARCH(WebServiceConstant.getProperties("beizhu.url.order.search")), BEIZHU_URLS_ORDER_CANCEL(
				WebServiceConstant.getProperties("beizhu.url.order.cancel"));

		private String url;

		BEIZHU_URLS(String url) {
			this.url = url;
		}

		public String getUrl() {
			return url;
		}
	}

	/**
	 * 8:景区到付，16：在线支付
	 * 
	 * @author zhangkexing
	 */
	public static enum BEIZHU_PAY_STATE {
		/** 8:景区到付 */
		PAY_TO_SUPPLIER("8"),
		/** 16：在线支付 */
		PAY_TO_LVMAMA("16");

		private String payTo;

		BEIZHU_PAY_STATE(String payTo) {
			this.payTo = payTo;
		}

		public String getPayTo() {
			return payTo;
		}
	}

	public static enum BEIZHU_ORDER_STATUS{
		/**订单未使用*/
		UNUSED,
		/**订单已经使用*/
		USED,
		/**订单已经被取消*/
		CANCEL
	}
	
	@SuppressWarnings("static-access")
	public static String getCodeMsg(String code) {
		return getInstance().codeMap.get(code);
	}

	private BeizhuConstant() {
		codeMap.put("0", "推送成功");
		codeMap.put("50007", "商品序号有问题");
		codeMap.put("50012", "商品数量有问题");
		codeMap.put("50015", "合作序号有问题");
		codeMap.put("50020", "身份证有问题");
		codeMap.put("50025", "手机不对");
		codeMap.put("50035", "日期有问题");
		codeMap.put("50040", "日期太早");
		codeMap.put("50045", "合作序号不存在");
		codeMap.put("50047", "合作权限不够");
		codeMap.put("50050", "商品不存在");
		codeMap.put("50055", "商品已售完");
		codeMap.put("50061", "状态位出错了");
		codeMap.put("50062", "余额钱不够");
		codeMap.put("50063", "单号有问题");
		codeMap.put("50064", "密码验证出错");
		codeMap.put("50065", "用户名出错了");
		codeMap.put("50066", "状态不符");
		codeMap.put("50067", "订单不存在");
		codeMap.put("50068", "产品编号非法");
		codeMap.put("50069", "您插入的订单号已经存在");
		codeMap.put("50071", "密码输入错误");
		codeMap.put("50072", "订单已经使用或没付钱(游客已经游玩)");
		codeMap.put("50073", "Format 格式不对");
		codeMap.put("50074", "Type不存在");
		codeMap.put("50075", "Sign签名验证不对");
		codeMap.put("30141", "没有到付票");
		codeMap.put("30165", "这个票过期了");
		codeMap.put("31006", "没有任何修改");
		codeMap.put("31009", "验证未通过");
		codeMap.put("30177", "余额钱不够");
	}

}
