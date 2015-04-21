package com.lvmama.report.web.point;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.zk.ui.Component;
import org.zkoss.zul.Checkbox;

import com.lvmama.report.po.PointRules;
import com.lvmama.report.service.ReportPointService;
import com.lvmama.report.vo.ShopPointVo;
import com.lvmama.report.web.BaseAction;

/**
 * 积分统计Action
 * @author yangchen
 */
public class ReportPointStatisticsAction extends BaseAction {
	/**
	 * 序列值
	 */
	private static final long serialVersionUID = 5208814124956269946L;
	/** 服务类 **/
	private ReportPointService pointService;
	/** 参数 **/
	private Map<String, Object> searchConds = new HashMap<String, Object>();
	/** ShopPointVo对象列表 **/
	private List<ShopPointVo> shopPointVoList = new ArrayList<ShopPointVo>();
	/** 统计的积分 **/
	private Long pointStatisics = 0L;
	/** 统计的会员数量 **/
	private Long memStatisics = 0L;
	/** 可用积分 **/
	private Long sumUsePoint = 0L;
	/** 类型列表 **/
	private List<PointRules> typeList = new ArrayList<PointRules>();
	/** 判断多少行 **/
	private List<Long> sizeList = new ArrayList<Long>();
	/** 存放List **/
	private List<List<PointRules>> poinRuleList = new ArrayList<List<PointRules>>();
	/** 过期积分统计 **/
	private Long expiredPoint = 0L;

	/**
	 * 动态显示查询类型
	 */
	public void doBefore() {
		typeList = pointService.queryTypeList();
		// 获取类型
		showType();
	}

	/**
	 * 查询
	 */
	public void search() {
		// 获取查询条件
		Map<String, Object> param = this.createMap();

		// 获取可用积分的总量
		sumUsePoint = pointService.sumPoint();
		// 获取积分统计
		pointStatisics = pointService.queryPointStatistics(param);
		// 获取会员统计
		memStatisics = pointService.queryMemStatistics(param);
		// 过期积分统计
		expiredPoint = pointService.expiredPoint();
		// 清空集合
		shopPointVoList.clear();
		// 获取查出的对象列表
		shopPointVoList = setConvert();
	}

	/**
	 * 数据导出
	 * @throws Exception Exception
	 */
	 public void doExport() throws Exception {
		shopPointVoList = setConvert();
		Map<String, Object> beans = new HashMap<String, Object>();
		beans.put("excelList", shopPointVoList);
		super.doExcel(beans, "/WEB-INF/resources/template/ReportPointStatistics.xls");
	}

	 /**
		 * 获取查询的参数
		 * @return map
		 */
		public Map<String, Object> createMap() {
			String sqlString = "";
			Map<String, Object> param = new HashMap<String, Object>();
			for (int i = 0; i < typeList.size(); i++) {
				if (this.typeList.get(i).getIsChecked().equals("true")) {
					if ("".equals(sqlString)) {
						sqlString = "l.rule_id = '" + typeList.get(i).getRuleId() + "' ";
					} else {
						sqlString += "  or l.rule_id ='" + typeList.get(i).getRuleId()
								+ "'";
					}
				}
			}
			if (null != searchConds.get("startDate")
					|| null != searchConds.get("endDate")) {
					param.put("startDate", searchConds.get("startDate"));
					param.put("endDate", searchConds.get("endDate"));
			}
			if (!"".equals(sqlString)) {
				param.put("sql", sqlString);
			}
			return param;
		}

	/**
	 * 选中类型
	 * @param ruleId
	 *            类型Id
	 * @param checked
	 *            是否选中
	 */
	public void checkRule(final String ruleId, final Boolean checked) {
		for (int i = 0; i < typeList.size(); i++) {
			if (this.typeList.get(i).getRuleId().equals(ruleId)) {
				typeList.get(i).setIsChecked(checked.toString());
				break;
			}
		}
	}

	/**
	 * 设置比例
	 * @return 返回ShopPointVo对象列表
	 */
	public List<ShopPointVo> setConvert() {
		// 获取查询条件
		Map<String, Object> param = this.createMap();

		shopPointVoList = pointService.queryAll(param);
		for (int i = 0; i < shopPointVoList.size(); i++) {
			shopPointVoList.get(i).setMemProportion(
					getProportionConvert(shopPointVoList.get(i)
							.getMembersCount(), memStatisics));
			shopPointVoList.get(i).setPointProportion(
					getProportionConvert(shopPointVoList.get(i).getSumPoint(),
							pointStatisics));
		}
		return shopPointVoList;
	}

	/**
	 * 单击全选按钮
	 * @param checked
	 *            是否选中
	 */
	@SuppressWarnings("unchecked")
	public void checkAll(final Boolean checked) {
		for (int i = 0; i < typeList.size(); i++) {
			typeList.get(i).setIsChecked(checked.toString());
		}
		Component rows = getComponent().getFellow("rows_id");
		List<Component> rowList = rows.getChildren();
		for (int i = 1; i < rowList.size() - 1; i++) {
			List<Component> checkboxs = ((Component) rowList.get(i)
					.getChildren().get(0)).getChildren();
			for (Component c : checkboxs) {
				if (c instanceof Checkbox) {
					((Checkbox) c).setChecked(checked);
				}
			}
		}
	}

	public ReportPointService getPointService() {
		return pointService;
	}

	public void setPointService(ReportPointService pointService) {
		this.pointService = pointService;
	}

	public Map<String, Object> getSearchConds() {
		return searchConds;
	}

	public void setSearchConds(final Map<String, Object> searchConds) {
		this.searchConds = searchConds;
	}

	public List<ShopPointVo> getShopPointVoList() {
		return shopPointVoList;
	}

	public void setShopPointVoList(final List<ShopPointVo> shopPointVoList) {
		this.shopPointVoList = shopPointVoList;
	}

	public Long getPointStatisics() {
		return pointStatisics;
	}

	public void setPointStatisics(final Long pointStatisics) {
		this.pointStatisics = pointStatisics;
	}

	public Long getMemStatisics() {
		return memStatisics;
	}

	public void setMemStatisics(final Long memStatisics) {
		this.memStatisics = memStatisics;
	}

	public Long getSumUsePoint() {
		return sumUsePoint;
	}

	public void setSumUsePoint(final Long sumUsePoint) {
		this.sumUsePoint = sumUsePoint;
	}

	public List<Long> getSizeList() {
		return sizeList;
	}

	public void setSizeList(final List<Long> sizeList) {
		this.sizeList = sizeList;
	}

	public List<List<PointRules>> getPoinRuleList() {
		return poinRuleList;
	}

	public void setPoinRuleList(final List<List<PointRules>> poinRuleList) {
		this.poinRuleList = poinRuleList;
	}

	public Long getExpiredPoint() {
		return expiredPoint;
	}

	public void setExpiredPoint(final Long expiredPoint) {
		this.expiredPoint = expiredPoint;
	}

	/**
	 * 循环显示判断的复选框(List套List,每10个对象封装为一个List)
	 */
	public void showType() {
		Integer size = 0;
		if (typeList.size() % 10 == 0) {
			size = typeList.size() / 10;
		} else {
			size = typeList.size() / 10 + 1;
		}
		for (int i = 0; i < size; i++) {
			List<PointRules> ruleList = new ArrayList<PointRules>();
			for (int j = i * 10; j < (i + 1) * 10 && j < typeList.size(); j++) {
				ruleList.add(typeList.get(j));
			}
			poinRuleList.add(ruleList);
			if (i == size - 1) {
				break;
			}
		}
	}

	/**
	 * 获取比列
	 * @param num
	 *            数量
	 * @param sum
	 *            总数
	 * @return 比例
	 */
	public String getProportionConvert(final Long num, final Long sum) {
		if (num != null || sum != null) {
			// 获取比例
			if (sum == 0) {
				return "0.0%";
			} else {
				BigDecimal b = new BigDecimal(num * 100.0 / sum);
				String str = String.valueOf(b);	
				if (str.indexOf(".")!=-1) {
					str = str.substring(0, str.indexOf(".") + 2);
				}
				return str + "%";
			}
		}
		return "0.0%";
	}
}
