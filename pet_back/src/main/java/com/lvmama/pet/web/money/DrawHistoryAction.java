package com.lvmama.pet.web.money;

import java.util.List;

import org.zkoss.zk.ui.Executions;

import com.lvmama.comm.bee.vo.ord.CompositeQuery;
import com.lvmama.comm.pet.po.money.CashMoneyDraw;
import com.lvmama.comm.pet.service.money.CashAccountService;
import com.lvmama.pet.web.BaseAction;

public class DrawHistoryAction extends BaseAction{

	


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private List<CashMoneyDraw> cachMoneyDrawList;
	private CashAccountService cashAccountService;
	private String userId;
	private CompositeQuery compositeQuery;
	@Override
	protected void doBefore() throws Exception {
		userId = (String)Executions.getCurrent().getArg().get("userId");
		compositeQuery = new CompositeQuery();
		compositeQuery.getMoneyDrawRelate().setUserNo(userId);
	}
	
	protected void doAfter(){
		initialPageInfo(cashAccountService.queryMoneyDrawCount(compositeQuery),compositeQuery);
		cachMoneyDrawList = cashAccountService.queryMoneyDraw(compositeQuery);
		System.out.println(1);
	}
	
	public void loadDataList(){
		initialPageInfo(cashAccountService.queryMoneyDrawCount(compositeQuery),compositeQuery);
		cachMoneyDrawList = cashAccountService.queryMoneyDraw(compositeQuery);
	}

	public List<CashMoneyDraw> getCashMoneyDrawList() {
		return cachMoneyDrawList;
	}
}
