package com.lvmama.comm.bee.service.tmall;


import java.util.List;
import java.util.Map;

import com.lvmama.comm.bee.po.tmall.TaobaoTicketSku;

public interface TaobaoTicketSkuService {
	public void insertTaobaoTicketSku(TaobaoTicketSku taobaoTicketSku);
	public int updateTaobaoTicketSku(TaobaoTicketSku taobaoTicketSku);
	public int deleteTaobaoTicketSku(Long id);
	public List<TaobaoTicketSku> getTaobaoTicketSku(Map<String, Object> prams);
	public TaobaoTicketSku getTaobaoTicketSku(Long id);
	public List<TaobaoTicketSku> getTaobaoTicketSkuList(Long tbProdSyncId);
	public Long getSeq();
}
