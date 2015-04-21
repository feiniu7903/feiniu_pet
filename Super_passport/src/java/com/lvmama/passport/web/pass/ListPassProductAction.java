package com.lvmama.passport.web.pass;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Messagebox;

import com.lvmama.ZkBaseAction;
import com.lvmama.comm.bee.po.pass.PassProduct;
import com.lvmama.comm.bee.service.pass.PassCodeService;
import com.lvmama.comm.bee.service.pass.PassProductService;

public class ListPassProductAction extends ZkBaseAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private PassCodeService passCodeService;
	private Integer totalRowCount;
	private String isWeekend;
	
	/**
	 * 查询条件
	 */
	private Map<String, Object> queryOption = new HashMap<String, Object>();
	/**
	 * 资源列表
	 */
	private List<PassProduct> passProducts = new ArrayList<PassProduct>();
	
	
	/**
	 * 查询
	 */
	public void doQuery() {
		totalRowCount=passCodeService.selectPassProductRowCount(queryOption);
		_totalRowCountLabel.setValue(totalRowCount.toString()); 
		_paging.setTotalSize(totalRowCount.intValue());
		
		queryOption.put("_startRow", _paging.getActivePage()*_paging.getPageSize()+1);
		queryOption.put("_endRow", _paging.getActivePage()*_paging.getPageSize()+_paging.getPageSize());
		
		passProducts = passCodeService.queryPassProduct(queryOption);
	}

	public void doDelete(final Long passProdId) {
		try {
			Messagebox.show("确定删除该记录?", "提示信息", Messagebox.YES | Messagebox.NO, 
					Messagebox.ERROR, new EventListener() { 
						public void onEvent(Event evt) { 
							switch (((Integer) evt.getData()).intValue()) { 
								case Messagebox.YES: 
									passCodeService.deletePassProduct(passProdId);
									refreshComponent("search");
									break; 
								case Messagebox.NO: 
									break; 
							} 
						}
			});
		} catch (InterruptedException e) {
			return ;
		}
		
	}
 
	public void setPassCodeService(PassCodeService passCodeService) {
		this.passCodeService = passCodeService;
	}

	public Map<String, Object> getQueryOption() {
		return queryOption;
	}

	public void setQueryOption(Map<String, Object> queryOption) {
		this.queryOption = queryOption;
	}

	public List<PassProduct> getPassProducts() {
		return passProducts;
	}

	public void setPassProducts(List<PassProduct> passProducts) {
		this.passProducts = passProducts;
	}

	public Integer getTotalRowCount() {
		return totalRowCount;
	}

	public void setTotalRowCount(Integer totalRowCount) {
		this.totalRowCount = totalRowCount;
	}

	public String getIsWeekend() {
		return isWeekend;
	}

	public void setIsWeekend(String isWeekend) {
		this.isWeekend = isWeekend;
	}

	

}
