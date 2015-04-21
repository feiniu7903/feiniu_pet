package com.lvmama.comm.pet.service.search;

import java.util.List;

import com.lvmama.comm.pet.po.search.ProdBranchSearchInfo;

public interface ProdBranchSearchInfoService {
	
	/**
	 * 门票,线路,酒店类别产品相关搜索表导入分批统计
	 * @author huangzhi
	 * @return
	 */
	public Integer countProductBranchIndexObj();
	
	/**
	 * 门票,线路,酒店类别产品相关搜索
	 * @param beginRow 开始行
	 * @param endRow 结束行
	 * @param bool 如果是增量设为false
	 * @return
	 */
	public List<ProdBranchSearchInfo> getProductBranchIndexDate(int beginRow, int endRow,boolean bool);

}
