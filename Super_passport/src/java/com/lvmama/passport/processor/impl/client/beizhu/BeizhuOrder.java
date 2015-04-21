package com.lvmama.passport.processor.impl.client.beizhu;

import java.util.HashMap;
import java.util.Map;

import com.lvmama.passport.processor.impl.client.beizhu.BeizhuConstant.BEIZHU_PAY_STATE;

/**
 * <table>
 * <tr>
 * <td>state</td>
 * <td>number</td>
 * <td>必须</td>
 * <td>固定值(8:景区到付，16：在线支付)</td>
 * </tr>
 * <tr>
 * <td>other_id</td>
 * <td>number</td>
 * <td>必须</td>
 * <td>订单号（int类型）</td>
 * </tr>
 * <tr>
 * <td>uname</td>
 * <td>string</td>
 * <td>必须</td>
 * <td>会员用户名(由一证通提供)</td>
 * </tr>
 * <tr>
 * <td>pwd</td>
 * <td>string</td>
 * <td>必须</td>
 * <td>会员md5加密密码(由一证通系统提供,在传输过程中，此密码要MD5加密)</td>
 * </tr>
 * <tr>
 * <td>sign</td>
 * <td>string</td>
 * <td>必须</td>
 * <td>签名：对 API 输入参数进行 md5 加密 算法在最底页</td>
 * </tr>
 * <tr>
 * <td>sfz</td>
 * <td>string</td>
 * <td>必须</td>
 * <td>身份证</td>
 * </tr>
 * <tr>
 * <td>phone</td>
 * <td>string</td>
 * <td>必须</td>
 * <td>手机号</td>
 * </tr>
 * <tr>
 * <td>gid</td>
 * <td>number</td>
 * <td>必须</td>
 * <td>门票编号(由一证通系统提供)</td>
 * </tr>
 * <tr>
 * <td>num</td>
 * <td>number</td>
 * <td>必须</td>
 * <td>门票数量</td>
 * </tr>
 * <tr>
 * <td>plantime</td>
 * <td>string</td>
 * <td>必须</td>
 * <td>预订游玩时间（2013-05-01这种格式填写）</td>
 * </tr>
 * <tr>
 * <td>format</td>
 * <td>string</td>
 * <td>可选</td>
 * <td>可选，指定响应格式。默认json,目前支持xml、json</td>
 * </tr>
 * <tr>
 * <td>type</td>
 * <td>number</td>
 * <td>可选</td>
 * <td>默认电子票（1.电子票，2.纸票）</td>
 * </tr>
 * </table>
 */
public class BeizhuOrder {
	private String state;
	private String other_id;
	private String uname;
	private String pwd;
	private String sign;
	private String sfz;
	private String phone;
	private String gid;
	private String num;
	private String plantime;
	private String format ="json";
	private String type = "1";

	
	public Map<String, String> toRequestParams(){
		setUname(BeizhuConstant.getInstance().username);
		setPwd(BeizhuConstant.getInstance().password);
		
		Map<String, String> requestParams = new HashMap<String, String>();
		
		requestParams.put("state",getState());
		requestParams.put("other_id",getOther_id());
		requestParams.put("uname",getUname());
		requestParams.put("pwd",getPwd());
		requestParams.put("sfz",getSfz());
		requestParams.put("phone",getPhone());
		requestParams.put("gid",getGid());
		requestParams.put("num",getNum());
		requestParams.put("plantime",getPlantime());
		requestParams.put("format",getFormat());
		requestParams.put("type",getType());

		
		return requestParams;
	}
	
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getOther_id() {
		return other_id;
	}

	public void setOther_id(String other_id) {
		this.other_id = other_id;
	}

	public String getUname() {
		return uname;
	}

	public void setUname(String uname) {
		this.uname = uname;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getSfz() {
		return sfz;
	}

	public void setSfz(String sfz) {
		this.sfz = sfz;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getGid() {
		return gid;
	}

	public void setGid(String gid) {
		this.gid = gid;
	}

	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
	}

	public String getPlantime() {
		return plantime;
	}

	public void setPlantime(String plantime) {
		this.plantime = plantime;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("BeizhuOrder [state=");
		builder.append(state);
		builder.append(", other_id=");
		builder.append(other_id);
		builder.append(", uname=");
		builder.append(uname);
		builder.append(", pwd=");
		builder.append(pwd);
		builder.append(", sign=");
		builder.append(sign);
		builder.append(", sfz=");
		builder.append(sfz);
		builder.append(", phone=");
		builder.append(phone);
		builder.append(", gid=");
		builder.append(gid);
		builder.append(", num=");
		builder.append(num);
		builder.append(", plantime=");
		builder.append(plantime);
		builder.append(", format=");
		builder.append(format);
		builder.append(", type=");
		builder.append(type);
		builder.append("]");
		return builder.toString();
	}

}
