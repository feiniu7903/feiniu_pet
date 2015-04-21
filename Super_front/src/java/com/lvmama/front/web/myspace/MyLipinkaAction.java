package com.lvmama.front.web.myspace;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;

import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.service.ord.OrderService;
import com.lvmama.comm.pet.po.lvmamacard.LvmamaStoredCard;
import com.lvmama.comm.pet.po.money.StoredCardUsage;
import com.lvmama.comm.pet.service.lvmamacard.LvmamacardService;
import com.lvmama.comm.pet.service.money.StoredCardService;
import com.lvmama.comm.pet.vo.Page;
import com.lvmama.comm.utils.lvmamacard.DESUtils;
import com.lvmama.comm.vo.Constant;

@Results({ @Result(name = "success", location = "/WEB-INF/pages/myspace/sub/lipinka/lipinka.ftl", type = "freemarker") })
public class MyLipinkaAction extends SpaceBaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 13464326436L;

	private String cardNo;
	private String cardPassword;
	@Autowired
	private LvmamacardService lvmamacardService;
	@Autowired
	private StoredCardService storedCardService;
	@Autowired
	private OrderService orderServiceProxy;

	private   Page<LvmamaStoredCard>  pagination;  
	private long page=1L;
	private List<LvmamaStoredCard> lvmamaList;
	@Action("/myspace/lipinka/doLipinka")
	public String initLipinka() {
		Map param = new HashedMap();
		param.put("userId", this.getUser().getId());
		Long count=lvmamacardService
				.countByParamForLvmamaStoredCard(param);
		//分页
		pagination=new Page<LvmamaStoredCard>(count);
		pagination.setUrl("/myspace/lipinka/doLipinka.do?page=");
		pagination.setCurrentPage(this.page);
 		pagination.setPageSize(10L);
  		if (pagination.getTotalResultSize() > 0) {
			param.put("start", pagination.getStartRows());
			param.put("end", pagination.getEndRows());
			  lvmamaList=lvmamacardService
			.queryByParamForLvmamaStoredCard(param);
			pagination.setAllItems(lvmamaList);
  		}
		pagination.buildUrl(getRequest());

		return "success";
	}

	@Action("/myspace/lipinka/addLipinka")
	public void addLipinka() {

		Map param = new HashedMap();
		param.put("success", false);
		if (cardNo.length() == 12 && cardPassword.length() == 8) {
			LvmamaStoredCard lvmaStoredCard = lvmamacardService
					.getOneStoreCardByCardNo(cardNo);
			if (lvmaStoredCard != null
					&& DESUtils.getInstance().getEncString(cardPassword)
							.equals(lvmaStoredCard.getPassword())) {

				if (lvmaStoredCard.getUserId() != null) {
					param.put("message", "该卡已被用户绑定");
				} else if (!(lvmaStoredCard.getStatus().equals(
						Constant.CARD_STATUS.NOTUSED.getCode()) || lvmaStoredCard
						.getStatus()
						.equals(Constant.CARD_STATUS.USED.getCode()))) {
					param.put("message", "请检查该卡是否过期，冻结，不能绑定");
				} else {
					// 绑定礼品卡
					Map map = new HashedMap();
					map.put("cardNo", cardNo);
					map.put("userId", this.getUser().getId());
					lvmamacardService.updateByParamForLvmamaStoredCard(map);
					param.put("success", true);
				}
			} else {
				param.put("message", "卡号不存在或者密码不对");
			}
		} else {
			param.put("message", "卡号或者密码位数不对");
		}
		this.sendAjaxResultByJson(JSONObject.fromObject(param).toString());
	}

	/**
	 * 明细
	 * 
	 * @author nixianjun 2013-11-28
	 */
	@Action("/lipinka/detail")
	public void dodetail() {
		Map param = new HashedMap();
		param.put("success", true);
		if (StringUtils.isNotBlank(cardNo)) {
			LvmamaStoredCard lvmamaStoredCard = lvmamacardService
					.getOneStoreCardByCardNo(cardNo);
			param.put("lvmamaStoredCard", lvmamaStoredCard);

			Map<String, Object> usageparam = new HashMap<String, Object>();
			usageparam.put("cardId", lvmamaStoredCard.getStoredCardId());
			List<StoredCardUsage> list = storedCardService
					.queryUsageListByCardId(usageparam);
			List<StoredCardUsage> resutlList = new ArrayList<StoredCardUsage>();
			if (list != null && list.size() > 0) {
				for (int i = 0; i < list.size(); i++) {
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
				param.put("list", resutlList);

			}
		} else {
			param.put("success", false);

		}
		this.sendAjaxResultByJson(JSONObject.fromObject(param).toString());
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getCardPassword() {
		return cardPassword;
	}

	public void setCardPassword(String cardPassword) {
		this.cardPassword = cardPassword;
	}

	public Page<LvmamaStoredCard> getPagination() {
		return pagination;
	}

	public void setPagination(Page<LvmamaStoredCard> pagination) {
		this.pagination = pagination;
	}

	public long getPage() {
		return page;
	}

	public void setPage(long page) {
		this.page = page;
	}

	public List<LvmamaStoredCard> getLvmamaList() {
		return lvmamaList;
	}

	public void setLvmamaList(List<LvmamaStoredCard> lvmamaList) {
		this.lvmamaList = lvmamaList;
	}


}
