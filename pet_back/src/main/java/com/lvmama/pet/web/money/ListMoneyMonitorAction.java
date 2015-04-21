package com.lvmama.pet.web.money;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.lvmama.comm.bee.vo.ord.CompositeQuery;
import com.lvmama.comm.pet.po.money.CashAccount;
import com.lvmama.comm.pet.po.user.UserUser;
import com.lvmama.comm.pet.service.money.CashAccountService;
import com.lvmama.comm.pet.service.user.UserUserProxy;
import com.lvmama.comm.vo.CashAccountChangeLogVO;
import com.lvmama.comm.vo.Constant;
import com.lvmama.pet.web.BaseAction;

public class ListMoneyMonitorAction extends BaseAction{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<HashMap<String,Object>> tansList; //交易记录
	private CashAccountService cashAccountService;
	private UserUserProxy userUserProxy;
	private String userId;

	public void loadDataList() throws Exception {
		if (userId==null||userId.equals("")||userId.equalsIgnoreCase("0")){
			alert("请先选择一个用户");
			return;
		}
		UserUser user=userUserProxy.getUserUserByUserNo(userId);
		if(user==null){
		   alert("用户不存在");
		   return;
		}
		CashAccount cashAccount=cashAccountService.queryOrCreateCashAccountByUserId(user.getId());
		
		CompositeQuery compositeQuery = new CompositeQuery();

		compositeQuery.getMoneyAccountChangeLogRelate().setCashAccountId(cashAccount.getCashAccountId());
		compositeQuery.getMoneyAccountChangeLogRelate().setUserNo(userId);
		compositeQuery.getMoneyAccountChangeLogRelate().setMoneyAccountChangeType(Constant.MoneyAccountChangeType.ALL);
		initialPageInfo(cashAccountService.queryMoneyAccountChangeLogCount(compositeQuery),compositeQuery);
		List<CashAccountChangeLogVO> queryList = cashAccountService.queryMoneyAccountChangeLog(compositeQuery);
		tansList = new ArrayList<HashMap<String,Object>>();
		for(CashAccountChangeLogVO changeLog:queryList){
			HashMap<String,Object> map = new HashMap<String,Object>();
			map.put("changeLog", changeLog);
			map.put("user", userUserProxy.getUserUserByUserNo(userId));
 			tansList.add(map);
		}
	}

	public void checkBalance(){
		try{
			cashAccountService.balance();
			alert("正常!");
		}catch(Exception e){
			e.printStackTrace();
			alert("异常，请校对！");
		}
		
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public List<HashMap<String, Object>> getTansList() {
		return tansList;
	}

}
