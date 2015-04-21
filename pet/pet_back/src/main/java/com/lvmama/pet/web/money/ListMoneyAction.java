package com.lvmama.pet.web.money;

import com.lvmama.comm.pet.po.user.UserUser;
import com.lvmama.comm.pet.service.money.CashAccountService;
import com.lvmama.comm.pet.service.user.UserUserProxy;
import com.lvmama.comm.vo.CashAccountVO;
import com.lvmama.pet.web.BaseAction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ListMoneyAction extends BaseAction{

	/**
	 * 
	 */
	UserUserProxy userUserProxy;
	private CashAccountService cashAccountService;
	private HashMap<String,Object> searchParams = new HashMap<String,Object>();
	
	private static final long serialVersionUID = 1L;

	private List<HashMap<String,Object>> userList;
	
	public HashMap<String, Object> getSearchParams() {
		return searchParams;
	}
	
	
	
	public void doSearch(){
		searchParams.put("maxRows",100);
		//不能全部为空
		if ((searchParams.get("email")==null||searchParams.get("email").equals(""))
				&&(searchParams.get("likeUserName")==null||searchParams.get("likeUserName").equals(""))
				&&(searchParams.get("mobileNumber")==null||searchParams.get("mobileNumber").equals(""))){
			alert("请至少输入一个查询参数");
			return;
		}
		//
		List<UserUser> list = userUserProxy.getUsers(searchParams);
		userList = new ArrayList<HashMap<String,Object>>();
		for(int i=0;i<list.size();i++){
			HashMap<String,Object> map =new HashMap<String,Object>();
			map.put("user", list.get(i));
			CashAccountVO moneyAccount = cashAccountService.queryMoneyAccountByUserId(list.get(i).getId());
			map.put("moneyAccount", moneyAccount);
			if (moneyAccount.getMaxDrawMoney()==0L) map.put("drawAble", "false"); else map.put("drawAble", "true");
			userList.add(map);
		}
	}



	public List<HashMap<String, Object>> getUserList() {
		return userList;
	}


}
