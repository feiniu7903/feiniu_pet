package com.lvmama.pet.web.sms;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.zul.Label;

import com.lvmama.comm.pet.service.sms.SmsRemoteService;
import com.lvmama.comm.pet.vo.SmsLogStat;
/**
 * 短信量统计
 * @author Brian
 *
 */
public class SmsStatAction extends com.lvmama.pet.web.BaseAction {
	/**
	 * 序列值
	 */
	private static final long serialVersionUID = -7484157787596513357L;
	/**
	 *  查询条件
	 */
	private Map<String, Object> searchConds = new HashMap<String, Object>();
	/**
	 * 短信的逻辑层
	 */
	private SmsRemoteService smsRemoteService;
	/**
	 * 业务通道的短信统计
	 */
	private List<SmsLogStat> normalChannelList;
	/**
	 *  业务通道短信合计
	 */
	private long normalTotalCount = 0;
	/**
	 * 群发通道的短信统计
	 */
	private List<SmsLogStat> qunfaChannelList;
	/**
	 *  业务通道短信合计
	 */
	private long qunfaTotalCount = 0;
	/**
	 * 彩信通道的短信统计
	 */
	private List<SmsLogStat> mmsChannelList;
	/**
	 * 梦网通道短信合计
	 */
	private long montnetsTotalCount = 0;
	/**
	 * 梦网短信的短信统计
	 */	
	private List<SmsLogStat> montnetsChannelList;

	/**
	 * 统计
	 */
	public void stat() {
		normalTotalCount = 0L;
		qunfaTotalCount = 0L;
		montnetsTotalCount = 0L;

		searchConds.remove("type");
		normalChannelList = smsRemoteService.queryStat(searchConds);
		for (SmsLogStat s : normalChannelList) {
			normalTotalCount += s.getTotalCount();
		}
		((Label) this.getComponent().getFellow("_totalNormalTotalCountLabel")).setValue(normalTotalCount + "");

		searchConds.put("type", "QUNFA");
		qunfaChannelList = smsRemoteService.queryStat(searchConds);
		for (SmsLogStat s : qunfaChannelList) {
			qunfaTotalCount += s.getTotalCount();
		}
		
		searchConds.put("type", "MONTNETS");
		montnetsChannelList = smsRemoteService.queryStat(searchConds);
		for (SmsLogStat s : montnetsChannelList) {
			montnetsTotalCount += s.getTotalCount();
		}
		
		((Label) this.getComponent().getFellow("_totalQunFaTotalCountLabel")).setValue(qunfaTotalCount + "");

		((Label) this.getComponent().getFellow("_totalTotalCountLabel")).setValue((qunfaTotalCount
				+ normalTotalCount)  + "");	
		((Label) this.getComponent().getFellow("_totalTotalMMSCountLabel")).setValue(smsRemoteService.queryMMSStat(searchConds)  + "");
		((Label) this.getComponent().getFellow("_totalMontnetsTotalCountLabel")).setValue(montnetsTotalCount  + "");
		
	}

	public final void setSmsRemoteService(SmsRemoteService smsRemoteService) {
		this.smsRemoteService = smsRemoteService;
	}
	
	public Map<String, Object> getSearchConds() {
		return searchConds;
	}
	public void setSearchConds(final Map<String, Object> searchConds) {
		this.searchConds = searchConds;
	}

	public List<SmsLogStat> getNormalChannelList() {
		return normalChannelList;
	}

	public List<SmsLogStat> getQunfaChannelList() {
		return qunfaChannelList;
	}

	public List<SmsLogStat> getMmsChannelList() {
		return mmsChannelList;
	}

	public long getNormalTotalCount() {
		return normalTotalCount;
	}

	public long getQunfaTotalCount() {
		return qunfaTotalCount;
	}

	public List<SmsLogStat> getMontnetsChannelList() {
		return montnetsChannelList;
	}

	public long getMontnetsTotalCount() {
		return montnetsTotalCount;
	}
	
}
