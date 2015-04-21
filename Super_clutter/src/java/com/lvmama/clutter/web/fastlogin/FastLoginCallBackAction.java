package com.lvmama.clutter.web.fastlogin;

import java.net.URLDecoder;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;

import com.alipay.api.internal.util.AlipaySignature;
import com.lvmama.clutter.web.BaseAction;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.vo.Constant;

@Namespace("/mobile")
public class FastLoginCallBackAction extends BaseAction {
	private static final long serialVersionUID = -8093206274353975154L;
	private final Logger logger = Logger.getLogger(this.getClass());

	@SuppressWarnings("unchecked")
	@Action("fastLoginCallBack")
	public String fastLoginCallBack() {
		try {
			HttpServletRequest request = this.getRequest();
			// 获得回调参数
			Map<String, String> params = request.getParameterMap();

			String sign = URLDecoder.decode((String) params.get("sign"),
					"utf-8");
			params.remove("sign");
			params.remove("signtype");

			//获取签名
			String content = StringUtil.getContent(params);
			String privateKey = Constant.getInstance().getValue(
					"aplixpay.fastlogin.rsa.private");
			String rsaSign = AlipaySignature.rsaSign(content, privateKey,
					"utf-8");
			String retVal = ERROR;
			
			//签名验证
			if (null != rsaSign && rsaSign.equals(sign)) {
				retVal = SUCCESS;
			} else {
				throw new Exception("支付宝快捷登陆签名验证失败...");
			}

			this.sendAjaxMsg(retVal);
		} catch (Exception e) {
			logger.error(e);
		}
		return null;
	}
	
}
