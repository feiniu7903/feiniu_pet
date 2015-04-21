package com.lvmama.report.web.sales;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.comm.utils.DateUtil;
import com.lvmama.report.po.CCStaffsSales;
import com.lvmama.report.service.ICCStaffsSalesStatisticsService;
import com.lvmama.report.web.BaseAction;

/**
 * 呼叫中心员工销售额统计.
 * @author sunruyi
 * @see java.util.ArrayList
 * @see java.util.HashMap
 * @see java.util.List
 * @see java.util.Map
 * @see org.apache.commons.logging.Log
 * @see org.apache.commons.logging.LogFactory
 * @see com.lvmama.report.po.CCStaffsSales
 * @see com.lvmama.report.service.ICCStaffsSalesStatisticsService
 * @see com.lvmama.report.web.BaseAction
 */
public class CCStaffsSalesStatisticsAction extends BaseAction {
	/**
	 * 序列化.
	 */
	private static final long serialVersionUID = -1175798217436304331L;
	/**
	 * 日志记录器.
	 */
	private static final Log LOG = LogFactory.getLog(CCStaffsSalesStatisticsAction.class);
	/**
	 * 查询参数Map.
	 */
	private Map<String, Object> parameters = new HashMap<String, Object>();
	/**
	 * 员工组别列表.
	 */
	private List<String> staffGroupList = new ArrayList<String>();
	/**
	 * 呼叫中心员工销售额列表.
	 */
	private List<CCStaffsSales> ccStaffsSalesList = new ArrayList<CCStaffsSales>();
	/**
	 * 组件有效性.true为无效
	 */
	private boolean isDisabled = true;
	/**
	 * 报表服务.
	 */
	private ICCStaffsSalesStatisticsService ccStaffsSalesStatisticsService;
	/**
	 * 需要截取orderBy字符串的开始索引.
	 */
	private final int subOrderByBegin = 0;
	/**
	 * 需要截取orderBy字符串的结束索引.
	 */
	private final int subOrderByEnd = 10;
	/**
	 * 查询呼叫中心员工销售额.
	 */
	public void search() {
		initStartAndEndDate();
		ccStaffsSalesList.clear();
		parameters.put("staffGroupList", staffGroupList);
		Long totalRowCount = ccStaffsSalesStatisticsService.ccStaffsSalesCount(parameters);
		initialPageInfoByMap(totalRowCount, parameters);
		ccStaffsSalesList = ccStaffsSalesStatisticsService.queryCCStaffsSales(parameters,false);
		if (ccStaffsSalesList.size() > 0) {
			isDisabled = false;
		} else {
			isDisabled = true;
		}
	}

	/**
	 * 数据导出
	 * @throws Exception
	 *             Exception
	 */
	public void doExport() throws Exception {
		initStartAndEndDate();
		Map<String, Object> parameTemp = parameters;
		parameTemp.remove("_startRow");
		parameTemp.remove("_endRow");
		List<CCStaffsSales> ccStaffsSalesStatistics = ccStaffsSalesStatisticsService.queryCCStaffsSales(parameTemp,true);
		Map beans = new HashMap();
		beans.put("excelList", ccStaffsSalesStatistics);
		super.doExcel(beans, "/WEB-INF/resources/template/CCStaffsSalesStatisticsTemplate.xls");
	}

	/**
	 * 初始化呼叫中心员工分组查询条件.
	 * @param isTrue
	 *            是否被勾选.
	 * @param staffGroup
	 *            被勾选的员工分组值.
	 * @return 呼叫中心员工分组.
	 */
	public List<String> initStaffGroupList(final boolean isTrue, final String staffGroup) {
		//如果是全选，则先清空staffGroupList
		if("全选".equals(staffGroup)){
			staffGroupList.clear();
		}
		boolean flag = isTrue && !staffGroupList.contains(staffGroup);
		if (flag) {
			staffGroupList.add(staffGroup);
		} else {
			staffGroupList.remove(staffGroup);
		}
		return staffGroupList;
	}

	/**
	 * 初始化排序参数Map.
	 * 功能：点击"员工组别"、"员工ID"、"销售额"可分别按数字顺序/倒叙排序。
	 * @param param
	 *            排序参数.
	 */
	public void initOrderBy(final String param) {
		//如果有order by position 去掉,因sql中不能同时出现两个order by
		if (parameters.containsKey("orderByPosition")) {
			parameters.remove("orderByPosition");
		}
		//首次调用时，对排序方式直接赋值
		if (!parameters.containsKey("orderBy")) {
			parameters.put("orderBy", param);
			return;
		}
		String orderBy = (String) parameters.get("orderBy");
		if ("CC.USER_NAME DESC".equals(param) && "CC.USER_NAME DESC".equals(orderBy)) {
			//如果形参为按"员工ID"降序,之前也是按"员工ID"降序的话,改为按"员工ID"升序
			parameters.put("orderBy", "CC.USER_NAME ASC");
		} else if ("CC.USER_NAME DESC".equals(param) && "CC.USER_NAME ASC".equals(orderBy)) {
			//如果形参为按"员工ID"降序,之前也是按"员工ID"升序的话,改为按"员工ID"降序
			parameters.put("orderBy", "CC.USER_NAME DESC");
		} else if ("ORDER_SALES DESC".equals(param) && "ORDER_SALES DESC".equals(orderBy)) {
			//如果形参为按"员工销售额"降序,之前也是按"员工销售额"降序的话,改为按"员工销售额"升序
			parameters.put("orderBy", "ORDER_SALES ASC");
		} else if ("ORDER_SALES DESC".equals(param) && "ORDER_SALES ASC".equals(orderBy)) {
			//如果形参为按"员工销售额"降序,之前也是按"员工销售额"升序的话,改为按"员工销售额"降序
			parameters.put("orderBy", "ORDER_SALES DESC");
		} else if (!orderBy.contains(param.substring(subOrderByBegin, subOrderByEnd))) {
			//如果形参与之前的排序方式不同(例如：形参是按"员工ID"排序，之前是按"员工销售额"排序),改为形参的排序方式
			parameters.put("orderBy", param);
		}
	}
	/**
	 * 初始化按员工组别分组排序.
	 * @param param 员工组别.
	 */
	public void initOrderByPosition(final String param) {
		//如果有order by user_name/real_name/order_sales 去掉,因sql中不能同时出现两个order by
		if (parameters.containsKey("orderBy")) {
			parameters.remove("orderBy");
		}
		//首次调用时，对排序方式直接赋值
		if (!parameters.containsKey("orderByPosition")) {
			parameters.put("orderByPosition", param);
			return;
		}
		String orderByPosition = (String) parameters.get("orderByPosition");
		if ("CC.POSITION DESC".equals(param) && "CC.POSITION DESC".equals(orderByPosition)) {
			parameters.put("orderByPosition", "CC.POSITION ASC");
		} else if ("CC.POSITION DESC".equals(param) && "CC.POSITION ASC".equals(orderByPosition)) {
			parameters.put("orderByPosition", "CC.POSITION DESC");
		} 
	}
	/**
	 * 初始化开始结束时间.
	 */
	private void initStartAndEndDate(){
		if(parameters.get("startDate") != null && parameters.get("endDate") != null){
			Date startDate = (Date)parameters.get("startDate");
			Date endDate = (Date)parameters.get("endDate");
			startDate = DateUtil.getDayStart(startDate);
			endDate = DateUtil.getDayEnd(endDate);
			parameters.put("startDate", startDate);
			parameters.put("endDate", endDate);
		}
	}
	public Map<String, Object> getParameters() {
		return parameters;
	}

	public void setParameters(final Map<String, Object> parameters) {
		this.parameters = parameters;
	}

	public List<String> getStaffGroupList() {
		return staffGroupList;
	}

	public void setStaffGroupList(final List<String> staffGroupList) {
		this.staffGroupList = staffGroupList;
	}

	public List<CCStaffsSales> getCcStaffsSalesList() {
		return ccStaffsSalesList;
	}

	public void setCcStaffsSalesList(final List<CCStaffsSales> ccStaffsSalesList) {
		this.ccStaffsSalesList = ccStaffsSalesList;
	}

	public static Log getLog() {
		return LOG;
	}

	public boolean isDisabled() {
		return isDisabled;
	}
}
