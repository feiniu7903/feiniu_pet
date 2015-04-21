package com.lvmama.pet.sweb.lvmamacard;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;

import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.service.SmsService;
import com.lvmama.comm.bee.service.ord.OrderService;
import com.lvmama.comm.pet.po.lvmamacard.LvmamaStoredCard;
import com.lvmama.comm.pet.po.money.StoredCardUsage;
import com.lvmama.comm.pet.po.pub.ComSms;
import com.lvmama.comm.pet.service.lvmamacard.LvmamacardService;
import com.lvmama.comm.pet.service.money.StoredCardService;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.utils.lvmamacard.DESUtils;
import com.lvmama.comm.utils.lvmamacard.LvmamaCardUtils;
import com.lvmama.comm.utils.lvmamacard.RandomNumberGeneratorUtils;
import com.lvmama.comm.vo.Constant;

/**
 * @author nixianjun
 *
 */
@Results({
		@Result(name = "input", location = "/WEB-INF/pages/back/lvmamacard/cardManage.jsp"),
 		@Result(name = "remark", location = "/WEB-INF/pages/back/lvmamacard/cardManage_remark.jsp"),
		@Result(name = "dodetail", location = "/WEB-INF/pages/back/lvmamacard/cardManage_dodetail.jsp") })
 public class CardManageAction extends CardBaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 35635634631L;
	@Autowired
	private LvmamacardService lvmamacardService;
	private Integer searchAmount;
	private String searchValidDate;
	private String searchstatus;
	private String cardNoBegin;
	private String cardNoEnd;
	private String cardNo;
	private String beizhu;
	private String mobile;
	private LvmamaStoredCard lvmamaStoredCard;
	@Autowired
	private StoredCardService storedCardService;
	@Autowired
	private OrderService orderServiceProxy;
	@Autowired
	private SmsService smsService;
	private List<StoredCardUsage> resutlList;

	@Action("/cardManage/query")
	public String query() {
		Map param = new HashMap<String, Object>();
		if (StringUtils.isNotEmpty(searchValidDate)) {
			param.put("searchValidDate", DateUtil.stringToDate(searchValidDate
					+ " 23:59:59", "yyyy-MM-dd HH:mm:ss"));
		}
		if (StringUtils.isNotEmpty(searchstatus)) {
			param.put("status", searchstatus);
		} else {
			param.put("statusList", Constant.CARD_STATUS.getMap().keySet());
		}
		if (StringUtils.isNotEmpty(cardNoBegin)) {
			param.put("cardNoBegin", cardNoBegin);
		}
		if (StringUtils.isNotEmpty(cardNoEnd)) {
			param.put("cardNoEnd", cardNoEnd);
		}
		if (null != searchAmount) {
			param.put("amount", searchAmount * 100);
		}
		pagination = initPage();
		pagination.setCurrentPage(pagination.getCurrentPage());
		pagination.setTotalResultSize(lvmamacardService
				.countByParamForLvmamaStoredCard(param));
		pagination.setPageSize(10L);
		if (pagination.getTotalResultSize() > 0) {
			param.put("start", pagination.getStartRows());
			param.put("end", pagination.getEndRows());
			pagination.setAllItems(lvmamacardService
					.queryByParamForLvmamaStoredCard(param));
		}
		pagination.buildUrl(getRequest());
		return "input";
	}

	/**
	 * 延期
	 * 
	 * @author nixianjun 2013-11-28
	 */
	@Action("/cardManage/doDelay")
	public void doDepaly() {
		Map result = new HashMap();
		result.put("success", "false");
		if (StringUtils.isEmpty(cardNo)) {
			result.put("success", "false");
			result.put("message", "卡号为空！");
			this.sendAjaxResultByJson(JSONObject.fromObject(result).toString());
			return ;
		}
		LvmamaStoredCard lvmamaCard = lvmamacardService
				.getOneStoreCardByCardNo(cardNo);
		if(lvmamaCard==null){
			result.put("success", "false");
			result.put("message", "卡号:"+cardNo+"不存在");
			this.sendAjaxResultByJson(JSONObject.fromObject(result).toString());
			return ;
		}
		if (lvmamaCard.getOverTime() == null) {
				result.put("message", "卡号:" + cardNo + "此卡永久有效");
				this.sendAjaxResultByJson(JSONObject.fromObject(result).toString());
			return;
		}
		if (lvmamaCard.getStatus()
				.equals(Constant.CARD_STATUS.FREEZE.getCode())) {
			result.put("message", "卡号:" + lvmamaCard.getCardNo() + "已经冻结，不能延期！");
			this.sendAjaxResultByJson(JSONObject.fromObject(result).toString());
			return;
		}
		if (lvmamaCard.getBalance() < LvmamaCardUtils.FIFTY) {
			result.put("success", "false");
			result.put("message", "卡号:" + lvmamaCard.getCardNo()+ "金额小于50元，不能延期！");
			this.sendAjaxResultByJson(JSONObject.fromObject(result).toString());
			return;
		}

		lvmamacardService.doDeplay(lvmamaCard,super.getSessionUser().getUserName());
		result.put("success", "true");
		this.sendAjaxResultByJson(JSONObject.fromObject(result).toString());
	}
	

	/**
	 * 冻结
	 * 
	 * @author nixianjun 2013-11-28
	 */
	@Action("/cardManage/doFrozen")
	public void doFrozen() {
		Map result = new HashMap();
		result.put("success", "false");
		if (StringUtils.isNotEmpty(cardNo)) {
			LvmamaStoredCard lvmamaCard = lvmamacardService
					.getOneStoreCardByCardNo(cardNo);
			if (null != lvmamaCard) {
				Map map = new HashMap();
				map.put("cardNo", cardNo);
				map.put("status", Constant.CARD_STATUS.FREEZE.getCode());
				lvmamacardService.updateByParamForLvmamaStoredCard(map);
				super.comLogService.insert(LvmamaCardUtils.STORED_CARD, null,lvmamaCard.getStoredCardId(), super.getSessionUser().getUserName(), Constant.COM_LOG_OBJECT_TYPE.STOREDCARD.getCode(), "卡冻结", "卡冻结"+cardNo, null);
				result.put("success", "true");
			}
		}
		this.sendAjaxResultByJson(JSONObject.fromObject(result).toString());
	}
	/**
	 * 解冻
	 * 
	 * @author nixianjun 2014-1-10
	 */
	@Action("/cardManage/doUnFrozen")
	public void doUnFrozen(){
			Map result = new HashMap(); 
			result.put("success", "false");
			if (StringUtils.isNotEmpty(cardNo)) {
				LvmamaStoredCard lvmamaCard = lvmamacardService
						.getOneStoreCardByCardNo(cardNo);
				if (null != lvmamaCard&&lvmamaCard.getStatus().equals(Constant.CARD_STATUS.FREEZE.getCode())) {
					lvmamacardService.doUnFrozen(lvmamaCard,super.getSessionUser().getUserName());
					result.put("success", "true");
				}
			}
			this.sendAjaxResultByJson(JSONObject.fromObject(result).toString());
	}
	/**
	 * 批量解冻
	 * 
	 * @author nixianjun 2014-1-10
	 */
	@Action("/cardManage/batchdoUnFrozen")
	public void batchdoUnFrozen(){
		Map map = new HashMap<String, Object>();
		map.put("success", false);
		if(StringUtils.isBlank(this.arrayStr)){
			this.sendAjaxResultByJson(JSONObject.fromObject(map).toString());
		   return;
		}
		String[] cardNoList = arrayStr.split(",");
		if (cardNoList.length > 0) {
			for(String strCardNo:cardNoList){
				LvmamaStoredCard lvmamaCard = lvmamacardService
						.getOneStoreCardByCardNo(strCardNo);
				if (null != lvmamaCard&&lvmamaCard.getStatus().equals(Constant.CARD_STATUS.FREEZE.getCode())) {
					lvmamacardService.doUnFrozen(lvmamaCard,super.getSessionUser().getUserName());
 				}
			}
 			map.put("success", "true");
		}
		this.sendAjaxResultByJson(JSONObject.fromObject(map).toString());
	}
	/**
	 * 重置密码，并发送手机短信
	 * 
	 * @author nixianjun 2014-1-10
	 */
	@Action("/cardManage/doReset")
	public  void doReset(){
		 Map result=new HashMap();
		 result.put("success", false);
		 if (StringUtils.isNotEmpty(cardNo)&&StringUtils.isNotEmpty(this.mobile)) {
				LvmamaStoredCard lvmamaCard = lvmamacardService
						.getOneStoreCardByCardNo(cardNo);
				if (null != lvmamaCard) {
					Map map = new HashMap();
					map.put("cardNo", cardNo);
					List<String> passwordList=RandomNumberGeneratorUtils.getbatchPasswordList(10);
					String password=passwordList.get(1);
					map.put("password", DESUtils.getInstance().getEncString(
							password));
					lvmamacardService.updateByParamForLvmamaStoredCard(map);
					//发送手机号
					  ComSms sms = new ComSms();
		                sms.setMobile(this.mobile);
		                sms.setContent("尊敬的用户,您所拥有的礼品卡的卡号为:"+cardNo+",密码为"+password+"；请妥善保管此条短信。");
		                sms.setObjectId(null);
		                sms.setObjectType(null);
		                sms.setMms("false");
		                smsService.sendSms(sms);
					super.comLogService.insert(LvmamaCardUtils.STORED_CARD, null,lvmamaCard.getStoredCardId(), super.getSessionUser().getUserName(), Constant.COM_LOG_OBJECT_TYPE.STOREDCARD.getCode(), "卡重置", "卡重置，卡号："+cardNo+"发送的手机号码："+this.mobile, null);
					result.put("success", "true");
				} 
			}
 
		 this.sendAjaxResultByJson(JSONObject.fromObject(result).toString());
	}

	/**
	 * 明细
	 * 
	 * @author nixianjun 2013-11-28
	 */
	@Action("/cardManage/detail")
	public String dodetail() {
		if (StringUtils.isNotBlank(cardNo)) {
			lvmamaStoredCard = lvmamacardService
					.getOneStoreCardByCardNo(cardNo);
			Map<String, Object> usageparam = new HashMap<String, Object>();
			usageparam.put("cardId", lvmamaStoredCard.getStoredCardId());
			// usageparam.put("type", CARD_TYPE.newed.getCode());
			List<StoredCardUsage> list = storedCardService
					.queryUsageListByCardId(usageparam);
			resutlList = new ArrayList<StoredCardUsage>();
			if (list != null && list.size() > 0) {
				for (int i = 0; i < list.size(); i++) {
					Map cardUsage = new HashMap();
					StoredCardUsage usage = list.get(i);
					String productName = "";
					OrdOrder order = orderServiceProxy
							.queryOrdOrderByOrderId(usage.getOrderId());
					if (order != null && order.getOrdOrderItemProds() != null
							&& order.getOrdOrderItemProds().size() > 0) {
						for (int t = 0; t < order.getOrdOrderItemProds().size(); t++) {
							productName = productName
									+ "/"
									+ order.getOrdOrderItemProds().get(t)
											.getProductName();
						}
					}
					usage.setProductName(order == null ? "" : productName
							.substring(1, productName.length()));
					usage.setOrderPayFloat(order == null ? 0l : order
							.getOrderPayFloat());
					resutlList.add(usage);
 			}
			}
		} else {
			return "error";
		}

		return "dodetail";
	}
	
	/**
	 * 弹出备注
	 * @return
	 */
	@Action("/cardManage/remark")
	public String remark() {
		if (StringUtils.isNotBlank(cardNo)) {
			lvmamaStoredCard = lvmamacardService.getOneStoreCardByCardNo(cardNo);
		} else {
			return "error";
		}
		return "remark";
	}
	
	/**
	 * 修改备注
	 * @return
	 * @throws Exception 
	 */
	@Action("/cardManage/updateRemark")
	public String updateRemark() throws Exception{
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("cardNo",this.cardNo);
		map.put("beizhu", this.beizhu+"");
		this.lvmamacardService.updateByParamForLvmamaStoredCard(map);
		this.outputToClient("true");
		return null;
	}
	

	/**
	 * 批量延期或者冻结
	 * 
	 * @return
	 * @author nixianjun 2013-12-3
	 */
	@Action("/cardManage/batchDeplayOrDoFrozen")
	public void batchDeplayOrDoFrozen() {
		Map map = new HashMap<String, Object>();
		map.put("success", "false");
		if (StringUtils.isBlank(paramStatus) || StringUtils.isBlank(arrayStr)) {
			map.put("success", "false");
		} else {

			String[] cardNoList = arrayStr.split(",");
			if (cardNoList.length > 0) {
				Map param = new HashMap<String, Object>();
				// 冻结
				if (paramStatus.equals(Constant.CARD_STATUS.FREEZE.getCode())) {
					param.put("cardNoArray", cardNoList);
					param.put("status", Constant.CARD_STATUS.FREEZE.getCode());
					lvmamacardService.updateByParamForLvmamaStoredCard(param);
					super.comLogService.insert(LvmamaCardUtils.STORED_CARD, null,null, super.getSessionUser().getUserName(), Constant.COM_LOG_OBJECT_TYPE.STOREDCARD.getCode(), "批量冻结", " 冻结卡号："+arrayStr, null);
					map.put("success", "true");
					// 延期
				} else if (paramStatus.equals("DEPLAY")) {
					String errorStr="";String successStr="";
					for (String strcardNo : cardNoList) {
						Boolean flag=doDeplayByCardNo(strcardNo);
						if(flag==false){
							errorStr=errorStr+strcardNo+",";
						}else{
							successStr=successStr+strcardNo+",";
						}
					}
					map.put("success", "true");
					map.put("message", "延期失败的卡号:"+errorStr+"\n 延期成功的卡号："+successStr);

				}
			}
		}
		this.sendAjaxResultByJson(JSONObject.fromObject(map).toString());
	}
	
	private Boolean doDeplayByCardNo(String strcardNo){
		if (StringUtils.isEmpty(strcardNo)) {
 			return false;
		}
		LvmamaStoredCard lvmamaCard = lvmamacardService
				.getOneStoreCardByCardNo(strcardNo);
		if(lvmamaCard==null){
			return false;
		}
		if (lvmamaCard.getOverTime() == null) {
 		   return false;
     	}
		if (lvmamaCard.getStatus()
				.equals(Constant.CARD_STATUS.FREEZE.getCode())) {
			return false;
		}
		if (lvmamaCard.getBalance() < LvmamaCardUtils.FIFTY) {
			return false;
		}
		lvmamacardService.doDeplay(lvmamaCard,super.getSessionUser().getUserName());
		return true;
 	}

	public Integer getSearchAmount() {
		return searchAmount;
	}

	public void setSearchAmount(Integer searchAmount) {
		this.searchAmount = searchAmount;
	}

	public String getSearchstatus() {
		return searchstatus;
	}

	public void setSearchstatus(String searchstatus) {
		this.searchstatus = searchstatus;
	}

	public LvmamacardService getLvmamacardService() {
		return lvmamacardService;
	}

	public void setLvmamacardService(LvmamacardService lvmamacardService) {
		this.lvmamacardService = lvmamacardService;
	}

	public String getSearchValidDate() {
		return searchValidDate;
	}

	public void setSearchValidDate(String searchValidDate) {
		this.searchValidDate = searchValidDate;
	}

	public String getCardNoBegin() {
		return cardNoBegin;
	}

	public void setCardNoBegin(String cardNoBegin) {
		this.cardNoBegin = cardNoBegin;
	}

	public String getCardNoEnd() {
		return cardNoEnd;
	}

	public void setCardNoEnd(String cardNoEnd) {
		this.cardNoEnd = cardNoEnd;
	}


	public static void main(String[] args) {
		System.out.println(Constant.CARD_STATUS.getMap());
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public LvmamaStoredCard getLvmamaStoredCard() {
		return lvmamaStoredCard;
	}

	public List<StoredCardUsage> getResutlList() {
		return resutlList;
	}

	public void setResutlList(List<StoredCardUsage> resutlList) {
		this.resutlList = resutlList;
	}

	public String getBeizhu() {
		return beizhu;
	}

	public void setBeizhu(String beizhu) {
		this.beizhu = beizhu;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
}
