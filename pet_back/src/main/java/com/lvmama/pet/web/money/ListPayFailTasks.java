package com.lvmama.pet.web.money;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.zkoss.zul.Paging;

import com.lvmama.comm.bee.vo.ord.CompositeQuery;
import com.lvmama.comm.bee.vo.view.PaginationVO;
import com.lvmama.comm.pet.po.money.CashMoneyDraw;
import com.lvmama.comm.pet.service.money.CashAccountService;
import com.lvmama.comm.pet.service.user.UserUserProxy;
import com.lvmama.comm.vo.Constant;
import com.lvmama.pet.web.BaseAction;

public class ListPayFailTasks extends BaseAction{

	/**
	 * 查询失败任务
	 */
	private static final long serialVersionUID = 1L;
	
	private List<Map<String,Object>> cashMoneyDrawList;

	private CashAccountService cashAccountService;
	private UserUserProxy userUserProxy;
	CompositeQuery compositeQuery = new CompositeQuery();
	private Date createTimeStart,createTimeEnd;
	private String type;
	public void loadDataList(){
		
		PaginationVO paginationVO = new PaginationVO();
		//查询款失败的记录
		paginationVO.addQueryParam("status", Constant.FINC_CASH_STATUS.PayCashFailed.toString());
		Paging paging=(Paging)this.getComponent().getFellow("_paging");
		paginationVO.setActivePage(paging.getActivePage());
		paginationVO.setPageSize(paging.getPageSize());

		PaginationVO paginationResult =cashAccountService.queryMoneyDrawHistory(paginationVO);
		initialPageInfo(paginationResult.getTotalRows(),compositeQuery);
		if(paginationResult.getTotalRows()>0){
			for(int i=0;i<paginationResult.getResultList().size();i++){
				Map payTaskMap  = (Map)paginationResult.getResultList().get(i);
				CashMoneyDraw  cashMoneyDraw =  (CashMoneyDraw)payTaskMap.get("fincMoneyDraw");
				payTaskMap.put("user", userUserProxy.getUserUserByPk(cashAccountService.queryCashAccountByPk(cashMoneyDraw.getCashAccountId()).getUserId()));
			}
			cashMoneyDrawList= paginationResult.getResultList();
		}else
		{
			cashMoneyDrawList =new ArrayList<Map<String,Object>>();
		}
	}
	
	
	
	public Date getCreateTimeStart() {
		return createTimeStart;
	}
	public Date getCreateTimeEnd() {
		return createTimeEnd;
	}
	public String getType() {
		return type;
	}
	public void setCreateTimeStart(Date createTimeStart) {
		this.createTimeStart = createTimeStart;
	}
	public void setCreateTimeEnd(Date createTimeEnd) {
		this.createTimeEnd = createTimeEnd;
	}
	public void setType(String type) {
		this.type = type;
	}



	public List<Map<String, Object>> getCashMoneyDrawList() {
		return cashMoneyDrawList;
	}

}
