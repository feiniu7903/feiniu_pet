/**
 * 
 */
package com.lvmama.shholiday.notify;

import org.dom4j.Element;

import com.lvmama.comm.utils.MemcachedUtil;
import com.lvmama.comm.utils.XmlUtils;
import com.lvmama.shholiday.Header;
import com.lvmama.shholiday.NotifyResult;
import com.lvmama.shholiday.ShholidayNotify;

/**
 * @author yangbin
 *
 */
public abstract class AbstractShholidayNotify implements ShholidayNotify{
	
	private String bodyElementTag;
	private Header header;
	private String serialNo;
	NotifyResult result;

	/**
	 * 
	 * @param bodyElementTag 内容的节点
	 * @param rs	向供应商回复的内容模板根结点
	 */
	public AbstractShholidayNotify(String bodyElementTag,String rs) {
		super();
		this.bodyElementTag = bodyElementTag;
		result = new NotifyResult(rs);
		header = new Header("00000","");
	}

	


	@Override
	public NotifyResult process(Element root) {
		Element requestHeader = root.element("Header");
		serialNo =XmlUtils.getChildElementContent(requestHeader, "SerialNo");
		String memcached_key="shholiday_notify_"+serialNo;
		if("true".equals(MemcachedUtil.getInstance().get(memcached_key))){
			result.setSuccess(header);//直接正常返回
		}else{
			Element body = root.element(bodyElementTag);
			handle(body);
			result.setSuccess(header);
			if(header.isSuccess()){
				MemcachedUtil.getInstance().set(memcached_key, "true");
			}
		}
		return result;
	}
	
	protected abstract void handle(Element body);

	/**
	 * 错误处理
	 * @param code
	 * @param desc
	 * @param exception
	 */
	protected void setError(String code,String desc,String exception){
		header.setCode(code);
		header.setDesc(desc);
		header.setException(exception);
	}
	
	protected void setError(NOTIFY_ERROR_CODE code){
		setError(code.getCode(),code.getDesc(),code.getDesc());
	}
	
	protected void addParam(String key,Object value){
		result.addParam(key, value);
	}




	public String getSerialNo() {
		return serialNo;
	}
}
