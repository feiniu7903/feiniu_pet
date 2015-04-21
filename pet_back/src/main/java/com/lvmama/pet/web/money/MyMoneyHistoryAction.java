package com.lvmama.pet.web.money;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.lvmama.comm.bee.vo.ord.CompositeQuery;
import com.lvmama.comm.pet.po.money.CashMoneyDraw;
import com.lvmama.comm.pet.service.money.CashAccountService;
import com.lvmama.comm.pet.service.user.UserUserProxy;
import com.lvmama.pet.web.BaseAction;

public class MyMoneyHistoryAction extends BaseAction{
	/**
	 * @author ZHANG Nan
	 */
	private static final long serialVersionUID = -3782991479722279939L;
	private List<HashMap<String,Object>> cashMoneyDrawList=new ArrayList<HashMap<String,Object>>();
	private UserUserProxy userUserProxy;
	private CashAccountService cashAccountService;
	private String userId;
	private CompositeQuery compositeQuery = new CompositeQuery();

	@Override
	public void doAfter(){
		loadData();
	}
	public void loadData(){
		compositeQuery.getMoneyDrawRelate().setUserNo(userId);
		initialPageInfo(cashAccountService.queryMoneyDrawCount(compositeQuery),compositeQuery);
		List<CashMoneyDraw> queryList = cashAccountService.queryMoneyDraw(compositeQuery);
		cashMoneyDrawList = new ArrayList<HashMap<String,Object>>();
		for ( int i =0;i<queryList.size();i++){
			HashMap<String,Object> map = new HashMap<String,Object>();
			map.put("fincMoneyDraw", queryList.get(i));
			map.put("user", userUserProxy.getUserUserByPk(cashAccountService.queryCashAccountByPk(queryList.get(i).getCashAccountId()).getUserId()));
			cashMoneyDrawList.add(map);
		}
	}

	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public List<HashMap<String, Object>> getCashMoneyDrawList() {
		return cashMoneyDrawList;
	}

}
