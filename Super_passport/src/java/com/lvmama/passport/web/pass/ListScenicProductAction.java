package com.lvmama.passport.web.pass;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lvmama.ZkBaseAction;
import com.lvmama.comm.bee.po.pass.ScenicProduct;
import com.lvmama.passport.processor.impl.client.dinosaurtown.KLCServiceClient;
import com.lvmama.passport.processor.impl.client.fangte.FangteUtil;

public class ListScenicProductAction extends ZkBaseAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 查询条件
	 */
	private Map<String, Object> queryOption = new HashMap<String, Object>();
	/**
	 * 资源列表
	 */
	private List<ScenicProduct> scenicProducts = new ArrayList<ScenicProduct>();
	
	
	/**
	 * 查询
	 */
	public void doQuery() {
		scenicProducts.clear();
		String providerName = String.valueOf(queryOption.get("providerName"));
		if("方特".equals(providerName)) {
			scenicProducts = FangteUtil.tgGetTicketType();
		}else if ("常州恐龙园".equals(providerName)) {
			scenicProducts = KLCServiceClient.getTestProducts(queryOption);
		}
	}

 
	public Map<String, Object> getQueryOption() {
		return queryOption;
	}

	public void setQueryOption(Map<String, Object> queryOption) {
		this.queryOption = queryOption;
	}


	public List<ScenicProduct> getScenicProducts() {
		return scenicProducts;
	}


}
