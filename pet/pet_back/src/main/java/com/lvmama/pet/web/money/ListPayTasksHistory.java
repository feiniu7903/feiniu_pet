package com.lvmama.pet.web.money;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Paging;

import com.lvmama.comm.bee.vo.ord.CompositeQuery;
import com.lvmama.comm.bee.vo.view.PaginationVO;
import com.lvmama.comm.pet.po.money.CashMoneyDraw;
import com.lvmama.comm.pet.service.money.CashAccountService;
import com.lvmama.comm.pet.service.user.UserUserProxy;
import com.lvmama.pet.web.BaseAction;

/**
 * 
 * @author songlianjun 修改： 1.修改打款查询后台SQL 逻辑 2.修改分页查询，仅调用远程方法一次
 */
public class ListPayTasksHistory extends BaseAction {
	/**
	 * LOG.
	 */
	private static final Log LOG = LogFactory.getLog(ListPayTasksHistory.class);
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private List<Map<String, Object>> playMoneyList;

	private CashAccountService cashAccountService;
	private UserUserProxy userUserProxy;
	CompositeQuery compositeQuery = new CompositeQuery();
	private Date createTimeStart, createTimeEnd;
	private String userId;
	private String status;
	private String type;
	//TODO
//	/**
//	 * 提现单服务接口.
//	 */
//	private IFincMoneyDrawService fincMoneyDrawService;

	/**
	 * setFincMoneyDrawService.
	 * 
	 * @param fincMoneyDrawService
	 *            提现单服务接口
	 */
	//TODO
//	public void setFincMoneyDrawService(
//			final IFincMoneyDrawService fincMoneyDrawService) {
//		this.fincMoneyDrawService = fincMoneyDrawService;
//	}

	/**
	 * 手工处理.<br>
	 * 界面交互
	 */
	public void manualHandle(final Map attributeMap) {
		// 手工处理标识 true代表手工成功，false代表手工失败
		final boolean manualHandleFlag = (Boolean.valueOf(attributeMap.get(
				"manualHandleFlag").toString()));
		// 操作人登录用户名
		final String userName = this.getSessionUserName();
		// 提现单ID
		final Long moneyDrawId = (Long) attributeMap.get("moneyDrawId");
		final String str = manualHandleFlag ? "确定要手工处理成功？" : "确定要手工处理失败？";
		Messagebox.show(str, "确认信息", Messagebox.YES | Messagebox.NO,
				Messagebox.ERROR, new EventListener() {
					public void onEvent(Event evt) {
						switch (((Integer) evt.getData()).intValue()) {
						// the Yes button is pressed
						case Messagebox.YES:
							cashAccountService.manualHandle(manualHandleFlag,moneyDrawId, userName);
							// 刷新页面
							refreshComponent("search");
							break;
						}
					}
				});
	}

	public void loadDataList() {

		PaginationVO paginationVO = new PaginationVO();

		Paging paging = (Paging) this.getComponent().getFellow("_paging");
		paginationVO.setActivePage(paging.getActivePage());
		paginationVO.setPageSize(paging.getPageSize());
		if (status != null && status.trim().length() > 0) {
			paginationVO.addQueryParam("status", status);
		}
		paginationVO.addQueryParam("userNo", userId);
		paginationVO.addQueryParam("createTimeStart", createTimeStart);

		if (createTimeEnd != null) {
			paginationVO.addQueryParam("createTimeEnd",
					new Date(createTimeEnd.getTime() + 1000 * 3600 * 24 - 1));
		}
		playMoneyList = new ArrayList<Map<String, Object>>();
		PaginationVO paginationResult = cashAccountService
				.queryMoneyDrawHistory(paginationVO);
		List<Map<String, Object>> queryList = paginationResult.getResultList();
		initialPageInfo(paginationResult.getTotalRows(), compositeQuery);

		for (int i = 0; i < queryList.size(); i++) {
			Map<String, Object> map = (Map<String, Object>) queryList.get(i);
			CashMoneyDraw  cashMoneyDraw =  (CashMoneyDraw)map.get("fincMoneyDraw");
			map.put("user", userUserProxy.getUserUserByPk(cashAccountService.queryCashAccountByPk(cashMoneyDraw.getCashAccountId()).getUserId()));
			playMoneyList.add(map);
		}
		paging.setTotalSize((int)paginationResult.getTotalRows());
	}

	public List<Map<String, Object>> getPlayMoneyList() {
		return playMoneyList;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Date getCreateTimeStart() {
		return createTimeStart;
	}

	public Date getCreateTimeEnd() {
		return createTimeEnd;
	}

	public String getStatus() {
		return status;
	}

	public void setCreateTimeStart(Date createTimeStart) {
		this.createTimeStart = createTimeStart;
	}

	public void setCreateTimeEnd(Date createTimeEnd) {
		this.createTimeEnd = createTimeEnd;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
