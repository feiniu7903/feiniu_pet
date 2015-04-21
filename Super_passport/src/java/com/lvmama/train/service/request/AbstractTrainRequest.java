/**
 * 
 */
package com.lvmama.train.service.request;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import com.lvmama.comm.vo.train.ReqVo;
import com.lvmama.passport.utils.WebServiceConstant;
import com.lvmama.train.service.TrainRequest;

/**
 * @author yangbin
 *
 */
public abstract class AbstractTrainRequest implements TrainRequest{

	private Map<String, String> map;
	protected final String hostaddress=WebServiceConstant.getProperties("train.account.host");
	private static final String merchantId = WebServiceConstant.getProperties("train.supplier.merchantid");
	protected String requestType;
	protected Long objectId;
	
	public AbstractTrainRequest() {
		super();
		map = new HashMap<String, String>();
	}
	
	public AbstractTrainRequest(ReqVo vo){
		super();
		map = new HashMap<String, String>();
		put("merchant_id", merchantId);
//		put("sign", vo.getSign());
	}
	
	@Override
	public Map<String, String> getParam() {
		return map;
	}


	protected void put(String key, String value){
		if(StringUtils.isEmpty(value)||"null".equalsIgnoreCase(value)){
			map.put(key, "");
		}else{
//			try {
//				map.put(key, URLEncoder.encode(value,"UTF-8"));
//			} catch (UnsupportedEncodingException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
			map.put(key, value);
		}
	}

	public String getRequestType() {
		return requestType;
	}

	protected void setRequestType(String requestType) {
		this.requestType = requestType;
	}

	public Long getObjectId() {
		return objectId;
	}

	protected void setObjectId(Long objectId) {
		this.objectId = objectId;
	}
	
	
}
