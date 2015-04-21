package com.lvmama.finance.common.service;

import java.util.List;

import com.lvmama.finance.common.ibatis.po.ComboxItem;

public interface ComboxItemService {
	List<ComboxItem> getComboxItems(String sqlId);
}
