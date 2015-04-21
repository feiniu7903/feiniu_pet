package com.lvmama.report.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.report.po.ChannelBasicMV;
import com.lvmama.report.po.LoscOrderStatisticsMV;

public class ChannelBasicMVDAO extends BaseIbatisDAO {
	
	public List<ChannelBasicMV> queryChannelBasicMV(Map param,boolean isForReportExport){
		List<ChannelBasicMV> result = new ArrayList<ChannelBasicMV>();
		result = super.queryForList("CHANNEL_BASIC_MV.queryChannelBasicMV", param, isForReportExport);
		return result;
	}
	
	public Long countChannelBasicMV(Map param,boolean isForReportExport){
		return (Long)super.queryForObject("CHANNEL_BASIC_MV.countChannelBasicMV", param,isForReportExport);
	}
	
	public Long sumAmountChannelBasicMV(Map param){
		return (Long)super.queryForObject("CHANNEL_BASIC_MV.sumAmountChannelBasicMV", param);
	}
	
	public Long sumProfitChannelBasicMV(Map param){
		return (Long)super.queryForObject("CHANNEL_BASIC_MV.sumProfitChannelBasicMV", param);
	}
	
	public List<LoscOrderStatisticsMV> loscOrderStatisticsList(Map param,boolean isForReportExport) {
		return super.queryForList("CHANNEL_BASIC_MV.queryLoscOrderStatistics", param,isForReportExport);
	}
	
	public Long loscOrderStatisticsCount(Map param) {
		return (Long)super.queryForObject("CHANNEL_BASIC_MV.loscOrderStatisticsCount", param);
	}
}
