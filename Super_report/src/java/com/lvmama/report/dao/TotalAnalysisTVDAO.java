package com.lvmama.report.dao;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.report.po.TotalAnalysisTV;

public class TotalAnalysisTVDAO extends BaseIbatisDAO {

	/**
     * 查询注册统计总记录
     * @param parameters
     * @return
     */
    public List<TotalAnalysisTV> selectTotalAnalysisTVList(Map parameters)
    {
    	return (List<TotalAnalysisTV>) super.queryForList("TOTAL_ANALYSIS_TV.selectObjAll", parameters);
    }
    
}