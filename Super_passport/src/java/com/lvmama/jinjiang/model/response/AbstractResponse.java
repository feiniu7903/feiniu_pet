package com.lvmama.jinjiang.model.response;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import net.sf.json.JSONObject;
import net.sf.json.util.JSONUtils;

import com.lvmama.jinjiang.JinjiangClient;
import com.lvmama.jinjiang.Response;
import com.lvmama.jinjiang.comm.TimestampToDateMorpher;
import com.lvmama.jinjiang.model.Contact;
import com.lvmama.jinjiang.model.Group;
import com.lvmama.jinjiang.model.GroupPrice;
import com.lvmama.jinjiang.model.Guest;
import com.lvmama.jinjiang.model.Journey;
import com.lvmama.jinjiang.model.Line;
import com.lvmama.jinjiang.model.MaterialAttach;
import com.lvmama.jinjiang.model.MediaAttach;
import com.lvmama.jinjiang.model.Order;
import com.lvmama.jinjiang.model.OrderDetail;
import com.lvmama.jinjiang.model.OutOrder;
import com.lvmama.jinjiang.model.Receivable;
import com.lvmama.jinjiang.model.SimpleGroup;
import com.lvmama.jinjiang.model.Subject;
import com.lvmama.jinjiang.model.Visa;


/**
 * 返回信息
 * @author chenkeke
 *
 */
public abstract class AbstractResponse implements Response{
	private final static Log LOG=LogFactory.getLog(JinjiangClient.class);
	private String errorcode;
	private String errormessage;
	private boolean success;
	@Override
	public <T extends Response> T parse(String responseJson) {
		
		T res = (T)this;
		try {
			Map<String, Class> params = new HashMap<String, Class>();
			
			params.put("contacts",Contact.class);
			params.put("groups",Group.class);
			params.put("groupPrices",GroupPrice.class);
			params.put("prices",GroupPrice.class);
			params.put("guests",Guest.class);
			params.put("journeys",Journey.class);
			params.put("lines",Line.class);
			params.put("materialAttachs",MaterialAttach.class);
			params.put("mediaAttachs",MediaAttach.class);
			params.put("orders",Order.class);
			params.put("orderDetails",OrderDetail.class);
			params.put("outOrders",OutOrder.class);
			params.put("receivables",Receivable.class);
			params.put("simpleGroups",SimpleGroup.class);
			params.put("subjects",Subject.class);
			params.put("visas",Visa.class);
			
			params.put("contact",Contact.class);
			params.put("group",Group.class);
			params.put("groupPrice",GroupPrice.class);
			params.put("price",GroupPrice.class);
			params.put("guest",Guest.class);
			params.put("journey",Journey.class);
			params.put("line",Line.class);
			params.put("materialAttach",MaterialAttach.class);
			params.put("mediaAttach",MediaAttach.class);
			params.put("order",Order.class);
			params.put("orderDetail",OrderDetail.class);
			params.put("outOrder",OutOrder.class);
			params.put("receivable",Receivable.class);
			params.put("simpleGroup",SimpleGroup.class);
			params.put("subject",Subject.class);
			params.put("visa",Visa.class);
			if(this instanceof GetLineResposne){
				params.put("attach",MediaAttach.class);
				params.put("attachs",MediaAttach.class);
			}
			if(this instanceof GetVisasResposne){
				params.put("attachs",MaterialAttach.class);
				params.put("attach",MaterialAttach.class);
			}
			//
			JSONUtils.getMorpherRegistry().registerMorpher(new TimestampToDateMorpher());
			JSONObject jsonObject = JSONObject.fromObject(responseJson);
			res=  (T)JSONObject.toBean(jsonObject, this.getClass(),params);
			if(res instanceof AbstractResponse){
				AbstractResponse response = (AbstractResponse)res;
				{
					if(ERRORCODE.ERR_0000.getCode().equals(response.getErrorcode())){
						response.setSuccess(true);
					}else{
						response.setSuccess(false);
					}
				}
			}
		} catch (Exception e) {
			if(res instanceof AbstractResponse){
				AbstractResponse response = (AbstractResponse)res;
				response.setSuccess(false);
				response.setErrormessage(e.getMessage());
				LOG.error("jinJiang response parse error:"+e);
			}
		}
		return res;
	}
	/**
	 * 响应状态
	 */
	@Override
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public String getErrorcode() {
		return errorcode;
	}
	public void setErrorcode(String errorcode) {
		this.errorcode = errorcode;
	}
	public String getErrormessage() {
		return errormessage;
	}
	public void setErrormessage(String errormessage) {
		this.errormessage = errormessage;
	}
}
