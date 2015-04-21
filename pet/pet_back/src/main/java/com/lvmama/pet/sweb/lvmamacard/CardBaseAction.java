package com.lvmama.pet.sweb.lvmamacard;

import java.io.File;
import java.util.Date;
import java.util.Map;

import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import net.sf.jxls.transformer.XLSTransformer;

import com.lvmama.comm.BackBaseAction;
import com.lvmama.comm.pet.service.pub.ComLogService;
import com.lvmama.comm.utils.ResourceUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.Constant.CARD_AMOUNT;
import com.lvmama.comm.vo.Constant.CARD_IN_STATUS;
import com.lvmama.comm.vo.Constant.CARD_OUT_STATUS;
/**
 * 驴妈妈储值卡基础action
 * @author nixianjun
 *
 */
@Results({
	
	@Result(name="error",location="/WEB-INF/pages/back/lvmamacard/error.jsp")
})
public class CardBaseAction extends BackBaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 134825L;

	protected ComLogService comLogService;
	protected String paramStatus;
	protected  String  arrayStr;
	public String getParamStatus() {
		return paramStatus;
	}
	public Map<String,String> getYuanList(){
		return CARD_AMOUNT.getAllMap();
	}
	public Map<String,String> getStatusList(){
		return CARD_IN_STATUS.getAllMap();
	}
	
	public Map<String,String> getOutStatusList(){
		return CARD_OUT_STATUS.getAllMap();
	}
	
	public Map<String, String> getCardStatusList() {
		return Constant.CARD_STATUS.getMap();
	}
	
  	public String getArrayStr() {
		return arrayStr;
	}
	public void setArrayStr(String arrayStr) {
		this.arrayStr = arrayStr;
	}
	public void setParamStatus(String paramStatus) {
		this.paramStatus = paramStatus;
	}
	/**
  	 * 写excel通过模板 bean
  	 * @param beans
  	 * @param template
  	 * @return
  	 * @throws Exception
  	 * @author nixianjun 2013-8-12
  	 */
	public  String writeExcelByjXls(Map<String,Object> beans,String template,String destFileName){
		try {
		File templateResource = ResourceUtil.getResourceFile(template);
		XLSTransformer transformer = new XLSTransformer();
 		transformer.transformXLS(templateResource.getAbsolutePath(), beans, destFileName);
		return destFileName;
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	public ComLogService getComLogService() {
		return comLogService;
	}
	public void setComLogService(ComLogService comLogService) {
		this.comLogService = comLogService;
	}
	
}
