package com.lvmama.report.dao;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.report.po.WeekAnalysisTV;

public class WeekAnalysisTVDAO extends BaseIbatisDAO {
	/**
     * 查询注册统计周记录
     * @param parameters
     * @return
     */
	public List<WeekAnalysisTV> selectWeekAnalysisTVList(Map parameters)
    {
    	return super.queryForList("WEEK_ANALYSIS_TV.selectObjAll", parameters);
    }
    
}