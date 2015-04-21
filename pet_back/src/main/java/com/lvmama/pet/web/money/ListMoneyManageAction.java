package com.lvmama.pet.web.money;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.zkoss.zk.ui.Executions;

import com.lvmama.comm.bee.vo.ord.CompositeQuery;
import com.lvmama.comm.pet.po.money.CashAccount;
import com.lvmama.comm.pet.po.money.CashMoneyDraw;
import com.lvmama.comm.pet.service.money.CashAccountService;
import com.lvmama.comm.pet.service.user.UserUserProxy;
import com.lvmama.pet.web.BaseAction;

public class ListMoneyManageAction extends BaseAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private HashMap<String,Object> searchParams = new HashMap<String,Object>();

	private List<HashMap<String,Object>> cashMoneyDrawList;
	private UserUserProxy userUserProxy;
	private CashAccountService cashAccountService;
	private String type;
	
	@Override
	protected void doBefore() throws Exception {
		type =(String) Executions.getCurrent().getArg().get("type");
	}


	/**
	 * 查询条件
	 */
	private CompositeQuery compositeQuery = new CompositeQuery();
	public void loadDataList(){
		if (type.equals("approve")){
			if(searchParams.get("auditStatus")==null){
				searchParams.put("auditStatus", "'UNVERIFIED','WaitUserConfirm'");
			}
			compositeQuery.getMoneyDrawRelate().setFincCashStatus(searchParams.get("auditStatus")+"");
		}
		if (searchParams.get("userId")!=null){
			compositeQuery.getMoneyDrawRelate().setUserNo((String)searchParams.get("userId"));
		}
		
		if (searchParams.get("userMobile")!=null){
			compositeQuery.getMoneyDrawRelate().setUserMobile(StringUtils.trim((String)searchParams.get("userMobile")));
		}
		if (searchParams.get("bankAccountName")!=null){
			compositeQuery.getMoneyDrawRelate().setBankAccountName(StringUtils.trim((String)searchParams.get("bankAccountName")));
		}
		
		if (searchParams.get("createTimeBegin")!=null){
			compositeQuery.getMoneyDrawRelate().setCreateTimeStart((Date)searchParams.get("createTimeBegin"));
		}else{
			compositeQuery.getMoneyDrawRelate().setCreateTimeStart(null);
		}
		Date endDate = (Date)searchParams.get("createTimeEnd");
		if (endDate!=null){
			compositeQuery.getMoneyDrawRelate().setCreateTimeEnd(new Date(endDate.getTime()+1000*3600*24-1));
		}else{
			compositeQuery.getMoneyDrawRelate().setCreateTimeEnd(null);
		}
		
		initialPageInfo(cashAccountService.queryMoneyDrawCount(compositeQuery),compositeQuery);
		List<CashMoneyDraw> queryList = cashAccountService.queryMoneyDraw(compositeQuery);
		cashMoneyDrawList = new ArrayList<HashMap<String,Object>>();
		for ( int i =0;i<queryList.size();i++){
			HashMap<String,Object> map = new HashMap<String,Object>();
			map.put("fincMoneyDraw", queryList.get(i));
			CashAccount cashAccount=cashAccountService.queryCashAccountByPk(queryList.get(i).getCashAccountId());
			if(cashAccount.isAccountValid()){
				map.put("cashAccountValid","可用");
			}else{
				map.put("cashAccountValid","禁用");
			}
			map.put("user", userUserProxy.getUserUserByPk(cashAccount.getUserId()));
			if (type.equals("approve")){
				map.put("viewAble", false);
				map.put("approveAble", true);
			}else{
				map.put("viewAble", true);
				map.put("approveAble", false);
			}
			cashMoneyDrawList.add(map);
		}
	}
	
	public HashMap<String, Object> getSearchParams() {
		return searchParams;
	}

	public void setSearchParams(HashMap<String, Object> searchParams) {
		this.searchParams = searchParams;
	}


	public List<HashMap<String, Object>> getCashMoneyDrawList() {
		return cashMoneyDrawList;
	}

}
