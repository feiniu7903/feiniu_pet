package com.lvmama.tnt.prod.service;

import java.util.Date;
import java.util.List;

import com.lvmama.tnt.comm.vo.Page;
import com.lvmama.tnt.order.vo.TntBuyInfo;
import com.lvmama.tnt.prod.vo.TntProdProduct;

public interface TntProdProductService {

	public List<TntProdProduct> search(Page<TntProdProduct> page);

	public long count(TntProdProduct t);

	public TntProdProduct getByBranchId(Long branchId);

	public TntProdProduct getByBranchIdSure(Long branchId);

	public List<TntProdProduct> getProdBranchList(Long productId,
			Long removeBranchId, Date visitTime);

	public TntProdProduct getProdBranchDetailByProdBranchId(TntBuyInfo buyInfo);

}
