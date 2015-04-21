package com.lvmama.finance.common.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lvmama.finance.base.BaseAction;
import com.lvmama.finance.common.ibatis.po.ComboxItem;
import com.lvmama.finance.common.service.ComboxItemService;

@Controller
@RequestMapping(value = "combox_data_source")
public class ComboxDataSourceAction extends BaseAction {
	@Autowired
	private ComboxItemService comboxItemService;

	/**
	 * 查询我方结算主体
	 * 
	 * @return
	 */
	@RequestMapping(value = "/getSettlementCompany")
	public List<ComboxItem> getSettlementCompany() {
		return comboxItemService.getComboxItems("getSettlementCompany");
	}
}
