package com.lvmama.pet.sweb.lvmamacard;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;

import com.lvmama.comm.pet.po.lvmamacard.LvmamaStoredCard;
import com.lvmama.comm.pet.service.lvmamacard.LvmamacardService;
import com.lvmama.comm.utils.lvmamacard.DESUtils;
import com.lvmama.comm.utils.lvmamacard.LvmamaCardUtils;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.Constant.CARD_AMOUNT;
import com.lvmama.comm.vo.Constant.CARD_IN_STATUS;
/**
 * 校验卡
 * @author nixianjun
 *
 */
@Results({@Result(name="inStorageCard" ,location="/WEB-INF/pages/back/lvmamacard/inStorageCard.jsp" ),
	      @Result(name="inStorageCard_add" ,location="/WEB-INF/pages/back/lvmamacard/inStorageCard_add.jsp" ),
	      @Result(name="inStorageCard_validate" ,location="/WEB-INF/pages/back/lvmamacard/inStorageCard_validate.jsp" ),
	      @Result(name="inStorageCard_validate_cancelOrNotOut",location="/WEB-INF/pages/back/lvmamacard/inStorageCard_validate_cancelOrNotOut.jsp")
})
public class ValidateStorageCardAction extends CardBaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1457548754L;
	@Autowired
	private LvmamacardService lvmamacardService;
	/**
	 * 校验卡号和密码
	 */
	private String inCode;
	private String validateCardNo;
	private String validatePassword;
	private String searchCardNo;
	private String searchstatus;
	
	

	
	@Action("/validateStorageCard/query")
	public String query(){ 
		    Map param=new HashMap<String, Object>();
		    if(StringUtils.isNotEmpty(inCode)){
		     param.put("cardBatchNo", inCode);
		    }
		    if(StringUtils.isNotEmpty(searchstatus)&&searchstatus.equals("true")){
		    	param.put("status", Constant.STORED_CARD_STATUS.CANCEL.getCode());
		    }
		    if(StringUtils.isNotEmpty(searchCardNo)){
		    	param.put("cardNo", searchCardNo);
		    }
		    queryReslut(param,48L);
		return "inStorageCard_validate";
	}
	private void queryReslut(Map param,long pagesize){
		 pagination=initPage();
		    pagination.setCurrentPage(pagination.getCurrentPage());
		    pagination.setPageSize(pagesize);
			pagination.setTotalResultSize(lvmamacardService.countByParamForLvmamaStoredCard(param));
			if(pagination.getTotalResultSize()>0){
				 param.put("start", pagination.getStartRows());
				 param.put("end", pagination.getEndRows());
				 pagination.setAllItems(lvmamacardService.queryByParamForLvmamaStoredCard(param));
			}
			pagination.buildUrl(getRequest());  
	}
	@Action("/validateStorageCard/validatePassword")
	public void validatePassword(){
		Map map=new HashMap<String, Object>();
		if(StringUtils.isEmpty(validateCardNo)||StringUtils.isEmpty(validatePassword)){
			map.put("success","false");
		}else{
			LvmamaStoredCard lvmamaCard= lvmamacardService.getOneStoreCardByCardNo(validateCardNo);
			if(lvmamaCard == null){
				map.put("success","false2");
			}else{
				if((DESUtils.getInstance().getEncString(validatePassword)).equals(lvmamaCard.getPassword())){
					map.put("success","true");
				}else {
					map.put("success","false");
				}
			}
		}
		this.sendAjaxResultByJson(JSONObject.fromObject(map).toString());
	}
	/**
	 * 批量作废卡
	 * 
	 * @author nixianjun 2013-12-17
	 */
	@Action("/validateStorageCard/batchCanel")
	public void validateCard(){
		 Map map=new HashMap<String, Object>();
		 map.put("success","false");
		 if(StringUtils.isNotEmpty(arrayStr)){
			 String[] cardNoList=arrayStr.split(",");
			 if(cardNoList.length>0){
				    lvmamacardService.batchCancelLvmamaStoredCardByArray(cardNoList,Constant.STORED_CARD_STATUS.CANCEL.getCode());
					super.comLogService.insert(LvmamaCardUtils.STORED_CARD_IN, null,null, super.getSessionUser().getUserName(), Constant.COM_LOG_OBJECT_TYPE.INSTOREDCARD.getCode(), "批量作废",  "作废卡号："+arrayStr, null);
				 map.put("success","true");
			 }
		 }
 		this.sendAjaxResultByJson(JSONObject.fromObject(map).toString());
	}
	
	/**
	 * 查看
	 * @return
	 * @author nixianjun 2013-12-2
	 */
	@Action("/validateStorageCard/notOutOrCancelView")
	public String  notOutOrCancel(){
		Map param=new HashMap();
		if(StringUtils.isNotBlank(inCode)&&StringUtils.isNotBlank(paramStatus)){
			  param.put("cardBatchNo", inCode);
			if(paramStatus.equals(Constant.CARD_STATUS.CANCEL.getCode())){
				param.put("status", Constant.STORED_CARD_STATUS.CANCEL.getCode());
			}else{
				List list=new ArrayList<String>();
				//list.add(Constant.STORED_CARD_STOCK_STATUS.NO_STOCK.getCode());
				list.add(Constant.STORED_CARD_STOCK_STATUS.INTO_STOCK.getCode());
				param.put("stockStatusList", list);
				List statusList=new ArrayList<String>();
				statusList.add(Constant.CARD_STATUS.INITIALIZATION);
				param.put("statusList", statusList);
			}
			 if(StringUtils.isNotEmpty(searchCardNo)){
			    	param.put("cardNo", searchCardNo);
			 }
			 queryReslut(param,48L);
		}else {
			return "error";
		}
		return "inStorageCard_validate_cancelOrNotOut";
	}
	public Map<String,String> getYuanList(){return CARD_AMOUNT.getAllMap();
	}
	public Map<String,String> getStatusList(){
		return CARD_IN_STATUS.getAllMap();
	}
	public String getInCode() {
		return inCode;
	}
	public void setInCode(String inCode) {
		this.inCode = inCode;
	}
	public String getValidateCardNo() {
		return validateCardNo;
	}
	public void setValidateCardNo(String validateCardNo) {
		this.validateCardNo = validateCardNo;
	}
	public String getValidatePassword() {
		return validatePassword;
	}
	public void setValidatePassword(String validatePassword) {
		this.validatePassword = validatePassword;
	}
	public String getSearchCardNo() {
		return searchCardNo;
	}
	public void setSearchCardNo(String searchCardNo) {
		this.searchCardNo = searchCardNo;
	}
	public String getSearchstatus() {
		return searchstatus;
	}
	public void setSearchstatus(String searchstatus) {
		this.searchstatus = searchstatus;
	}
}
