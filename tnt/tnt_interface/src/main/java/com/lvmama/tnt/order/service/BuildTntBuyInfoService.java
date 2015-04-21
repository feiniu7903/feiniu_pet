package com.lvmama.tnt.order.service;

import com.lvmama.tnt.comm.vo.ResultGod;
import com.lvmama.tnt.order.vo.TntBuyInfo;

public interface BuildTntBuyInfoService {

	public ResultGod<TntBuyInfo> build(TntBuyInfo buyInfo);

}
