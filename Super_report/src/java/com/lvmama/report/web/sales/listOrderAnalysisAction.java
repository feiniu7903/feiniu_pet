package com.lvmama.report.web.sales;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.zkoss.zul.Listheader;

import com.lvmama.report.po.OrderBasicMV;
import com.lvmama.report.service.ReportService;
import com.lvmama.report.vo.OrderBasicVO;
import com.lvmama.report.web.BaseAction;

/**
 * 订单分析
 * @author yuzhizeng
 */
public class listOrderAnalysisAction extends BaseAction {

	/**
	 * 序列
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 获取OrderBasicMV列表
	 */
	private List<OrderBasicMV> analysisList = new ArrayList<OrderBasicMV>();
	/**
	 * 查询条件
	 */
	protected Map<String, Object> searchConds = new HashMap<String, Object>();
	/**
	 * 报表逻辑接口
	 */
	private ReportService reportService;
	/**
	 * OrderBasicVO统计数据
	 */
	private OrderBasicVO orderBasicVO;
	/**
	 * 表排序参数
	 */
	private Map<String, String> sortParam;
	
	public void doBefore() throws Exception {
		sortParam = new HashMap<String, String>();
		sortParam.put("sortTypeName", "create_time"); //初始默认排序
		sortParam.put("sortAscOrDesc", "asc");
	}
	
	
	public void doQuery() throws Exception {
		initMap();
		
		//初始化查询条件
		_paging.setPageSize(20);
		initialPageInfoByMap(reportService.countOfOrderBasicMVByParam(searchConds),
				searchConds);

		//获取订单分析列表
		analysisList = reportService.queryOrderBasicMVByParam(searchConds,false);
		//获取统计数据
		orderBasicVO = reportService.sumOrderBasicVOByParam(searchConds,false);
	}

	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void doExport() throws Exception {
		initMap();
		searchConds.remove("_startRow");
		searchConds.remove("_endRow");
		//获取订单分析列表和统计数据
		analysisList = reportService.queryOrderBasicMVByParam(searchConds,true);
		orderBasicVO = reportService.sumOrderBasicVOByParam(searchConds,true);
		
		Map beans = new HashMap();
		beans.put("excelList", analysisList);
		beans.put("orderBasicVO", orderBasicVO);
		doExcel(beans, "/WEB-INF/resources/template/orderAnalysis.xls");
	}

	/**
	 * 页面表格字段栏排序
	 * quantity,PAYED_QUANTITY ..对应排序的表字段
	 * @param param 页面传递变量(sortType)
	 * **/
	public void doSort(Map<String,Object> param) {
		((Listheader) this.getComponent().getFellow("QUANTITY")).setLabel("总订单量");
		((Listheader) this.getComponent().getFellow("PAYED_QUANTITY")).setLabel("已支付数");
		((Listheader) this.getComponent().getFellow("PAYED_AMOUNT")).setLabel("已支付金额(元)");
		((Listheader) this.getComponent().getFellow("UNPAYED_QUANTITY")).setLabel("未支付及取消数");
		((Listheader) this.getComponent().getFellow("UNPAYED_AMOUNT")).setLabel("未支付及取消金额(元)");
		
		//页面表格操作排序字段
		Map<String, String> sortFieldMap = new HashMap<String, String>();
		sortFieldMap.put("QUANTITY", "总订单量");
		sortFieldMap.put("PAYED_QUANTITY", "已支付数");
		sortFieldMap.put("PAYED_AMOUNT", "已支付金额(元)");
		sortFieldMap.put("UNPAYED_QUANTITY", "未支付及取消数");
		sortFieldMap.put("UNPAYED_AMOUNT", "未支付及取消金额(元)");
		
		Set<String> key = sortFieldMap.keySet();
        for (Iterator it = key.iterator(); it.hasNext();) {
            String keyValue = (String) it.next();
            
            if (keyValue.equals(param.get("sortType"))) {
            	
            	//sortParam 表排序查询参数
    			if (param.get("sortType").equals(sortParam.get("sortTypeName"))) {
    				
    				if ("desc".equals(sortParam.get("sortAscOrDesc"))) {
    					sortParam.put("sortAscOrDesc", "asc");
    					//页面显示字段栏内容。如：总订单量△
    					((Listheader) this.getComponent().getFellow(keyValue)).setLabel(sortFieldMap.get(keyValue) + "△");
    				} else {
    					sortParam.put("sortAscOrDesc", "desc");
    					((Listheader) this.getComponent().getFellow(keyValue)).setLabel(sortFieldMap.get(keyValue) + "▽");
    				}
    			} else {
    				sortParam.put("sortAscOrDesc", "desc");
    				sortParam.put("sortTypeName", keyValue);
    				((Listheader) this.getComponent().getFellow(keyValue)).setLabel(sortFieldMap.get(keyValue) + "▽");
    			}
    		}
        }

		refreshComponent("search");
	}

	/**
	 *	
	 *  
	 */
	public void initMap() {
		List<String> param = new ArrayList<String>();

		if (searchConds.get("sTicket") != null && (Boolean) searchConds.get("sTicket")) {
			param.add("TICKET");
		}
		if (searchConds.get("sHotel") != null
				&& (Boolean) searchConds.get("sHotel")) {
			param.add("HOTEL");
		}
		if (searchConds.get("sGroup") != null
				&& (Boolean) searchConds.get("sGroup")) {
			param.add("GROUP");
		}
		if (searchConds.get("sGroupLong") != null
				&& (Boolean) searchConds.get("sGroupLong")) {
			param.add("GROUP_LONG");
		}
		if (searchConds.get("sGroupForeign") != null
				&& (Boolean) searchConds.get("sGroupForeign")) {
			param.add("GROUP_FOREIGN");
		}
		if (searchConds.get("sFree") != null
				&& (Boolean) searchConds.get("sFree")) {
			param.add("FREENESS");
		}
		if (searchConds.get("sFreeLong") != null
				&& (Boolean) searchConds.get("sFreeLong")) {
			param.add("FREENESS_LONG");
		}
		if (searchConds.get("sFreeForeign") != null
				&& (Boolean) searchConds.get("sFreeForeign")) {
			param.add("FREENESS_FOREIGN");
		}
		if (searchConds.get("sSelfhelpBus") != null
				&& (Boolean) searchConds.get("sSelfhelpBus")) {
			param.add("SELFHELP_BUS");
		}	
		if (searchConds.get("sOther") != null
				&& (Boolean) searchConds.get("sOther")) {
			param.add("OTHER");
		}			
		
		if (param.isEmpty()) {
			searchConds.remove("productType");
		} else {
			searchConds.put("productType", param);
		}
		searchConds.putAll(sortParam);
	}
	
	/* * 
	 * * * * * *  get and set property  * * *  * * * * * *
	 */
	public Map<String, Object> getSearchConds() {
		return searchConds;
	}

	public void setSearchConds(Map<String, Object> searchConds) {
		this.searchConds = searchConds;
	}

	public ReportService getReportService() {
		return reportService;
	}

	public void setReportService(ReportService reportService) {
		this.reportService = reportService;
	}

	public List<OrderBasicMV> getAnalysisList() {
		return analysisList;
	}

	public void setAnalysisList(List<OrderBasicMV> analysisList) {
		this.analysisList = analysisList;
	}

	public OrderBasicVO getOrderBasicVO() {
		return orderBasicVO;
	}

	public void setOrderBasicVO(OrderBasicVO orderBasicVO) {
		this.orderBasicVO = orderBasicVO;
	}

}
