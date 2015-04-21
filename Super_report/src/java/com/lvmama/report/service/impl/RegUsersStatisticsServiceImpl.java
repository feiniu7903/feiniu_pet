package com.lvmama.report.service.impl;

import java.lang.reflect.Field;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.lvmama.report.dao.TotalAnalysisTVDAO;
import com.lvmama.report.dao.WeekAnalysisTVDAO;
import com.lvmama.report.po.TotalAnalysisTV;
import com.lvmama.report.po.WeekAnalysisTV;
import com.lvmama.report.service.RegUsersStatisticsService;
import com.lvmama.report.vo.TotalAnalysisVo;
import com.lvmama.report.vo.UserAnalysisVO;

/**
 * 完成页面的行列转换,原页面的格式为
 * [时间, 用户数, 注册数....]
 * [2011-12-11, 12, 11]
 * [2011-12-12, 13, 12]...
 * 行列转换后页面的格式为
 * [时间:2011-12-11,2011-12-12..]
 * [用户数:12,13..]
 * [注册数:11,12..]
 *步骤:  数据库行列转换的查询由原来的多行数据, 转化为一行数据,  数据中每个字段的内容如: [1, 2, 3, 4...]. 逗号分割,
 * 后分拆字段数据值存放到一个vo对象(fristWeek =1, secondWeek =2 ... ), 在页面中循环显示vo对象, 从而实现页面的行列转换
 * @author yangchen
 */
public class RegUsersStatisticsServiceImpl implements RegUsersStatisticsService {
	/**
	 * 本周的DAO对象
	 */
	private WeekAnalysisTVDAO weekAnalysisTVDAO;
	/**
	 * 整体状况dao对象
	 */
	private TotalAnalysisTVDAO totalAnalysisTVDAO;
	/**
	 * 列出UserAnalysisVO,TotalAnalysis的属性(该数组与UserAnalysisVO, TotalAnalysis对象中的属性顺序一一对应)
	 */
	private final String[] weekFields = { "firstWeek", "secondWeek",
			"thirdWeek", "fourthWeek", "fifthWeek", "sixthWeek", "seventhWeek",
			"eighthWeek", "ninthWeek", "tenthWeek", "eleventhWeek",
			"twelfthWeek", "thirteenthWeek", "fourteenthWeek",
			"fifteenthWeek","sixteenthWeek","seventeenthWeek"};

	/**
	 * 查询周的用户数据统计(po对象)
	 * @param parameters 查询参数
	 * @return tv
	 */
	public WeekAnalysisTV selectWeekAnalysisTV(final Map parameters) {
		List<WeekAnalysisTV> list = weekAnalysisTVDAO
				.selectWeekAnalysisTVList(parameters);
		if (list.size() > 0) {
			return list.get(0);
		} else {
			return null;
		}
	}

	/**
	 * 查询整体用户数据统计(po对象)
	 * @param parameters  查询参数
	 * @return tv
	 */
	public TotalAnalysisTV selectTotalAnalysisTV(final Map parameters) {
		List<TotalAnalysisTV> list = totalAnalysisTVDAO
				.selectTotalAnalysisTVList(parameters);
		if (list.size() > 0) {
			return list.get(0);
		} else {
			return null;
		}
	}

	/**
	 * 获取Vo的记录集合(行列转化结果)
	 * @param parameters  查询参数
	 * @return list
	 */
	public List<UserAnalysisVO> getUserAnalysisVOList(final Map parameters)
			throws IllegalArgumentException, SecurityException,
			IllegalAccessException, NoSuchFieldException {
		// 初始化vo对象的名称的数组的下标
		Integer count = -1;
		// 存放vo对象的名称属性的数组(vo对象的名称显示页面表格第一列的值)
		String[] voNames = { "时间", "整体注册用户数", "后 导入用户数", "电话注册用户数", "淘宝注册",
				"前台页面注册用户数", "SEM注册用户数", "无线注册用户数", "普通注册用户数", "快速注册用户数",
				"新注册邮箱验证用户数", "新注册手机验证用户数", "注册地域分布", "普通注册邮箱验证用户数",
				"普通注册手机验证用户数", "快速注册邮箱验证用户数", "快速注册手机验证用户数", "登录用户数", "登录次数",
				"点评用户数", "下单用户数", "下单次数", "二次下单用户数", "交叉下单用户数", "SEM下单用户数",
				"支付用户数", "支付次数", "二次支付用户数", "交叉支付用户数", "SEM支付用户数" };

		// 用户注册统计的VO(行列转化结果)
		List<UserAnalysisVO> userStatisticsVoList = new ArrayList<UserAnalysisVO>();

		// 获取用户注册统计的本周记录
		WeekAnalysisTV po = selectWeekAnalysisTV(parameters);

		// 遍历统计本周的对象WeekAnalysisTV的所有属性,(数据库中查询的顺序必须与WeekAnalysisTV,TotalAnalysis对象的属性的顺序一致)
		for (Field field : po.getClass().getFields()) {
			// n个字段就有n行的记录(UserAnalysisVO对象)
			UserAnalysisVO vo = new UserAnalysisVO();

			//每循环一个字段,vo对象的名称的数组的下标+1
			count++;

			// 填充vo对象
			setVoAssignment(vo, field, po);
			// 为vo对象设置名称
			vo.setName(voNames[count]);
			
			userStatisticsVoList.add(vo);
		}
		return userStatisticsVoList;
	}

	/**
	 * ,获取整体数据的vo对象的集合
	 * @param parameters 查询参数
	 * @return TotalAnalysisVo列表
	 */
	public List<TotalAnalysisVo> getTotalAnalysisTV(final Map parameters)
			throws IllegalArgumentException, SecurityException,
			IllegalAccessException, NoSuchFieldException {

		Integer count = -1;
		List<TotalAnalysisVo> totalAnalysisVOList = new ArrayList<TotalAnalysisVo>();

		String[] voNames = { "时间", "用户总数", "邮箱填充用户总数", "手机填充用户总数", "累积邮箱填充率",
				"累积手机填充率", "邮箱验证用户总数", "手机验证用户总数", "累积邮箱验证率", "累积手机验证率",
				"累积下单用户占比", "累积二次下单用户占比", "累积交叉下单用户占比", "累积SEM注册下单用户占比",
				"累积非SEM用户注册下单占比", "累积支付用户占比", "累积二次支付用户占比", "累积交叉支付用户占比",
				"累积SEM注册支付用户占比", "累积非SEM用户注册支付占比" };

		TotalAnalysisTV po = selectTotalAnalysisTV(parameters);
		for (Field field : po.getClass().getFields()) {

			count++;
			TotalAnalysisVo vo = new TotalAnalysisVo();

			//填充vo
			setVoAssignment(vo, field, po);
			vo.setName(voNames[count]);

			totalAnalysisVOList.add(vo);
		}
		return totalAnalysisVOList;
	}

	/**
	 * 填充vo
	 * @param vo vo对象
	 * @param field 文件对象
	 * @param po po对象
	 * @throws IllegalArgumentException异常
	 * @throws SecurityException
	 * @throws IllegalAccessException
	 * @throws NoSuchFieldException
	 */
	public void setVoAssignment(final Object vo, final Field field,
			final Object po) throws SecurityException, IllegalAccessException,
			IllegalArgumentException, NoSuchFieldException {

		String[] data = {};

		// 以逗号分割字符
		if (field.get(po) != null) {
			data = field.get(po).toString().split(",");
		}

		// 把分割后的数据对应的赋值给对应的VO属性
		for (int i = 0; i < data.length; i++) {
			//查出的数据中有末尾为点号的,去除点号
			if ((".").equals(data[i].toString().substring(data[i].toString().length()-1, data[i].toString().length()))) {
				data[i] = data[i].toString().substring(0, data[i].toString().length() -1);
			}
			vo.getClass().getField(weekFields[i].toString()).set(vo, data[i].toString());
		}

		// 获取周增长,增长率的数据(地域,时间没有周增长)
		if (!field.getName().equals("weekArea") && field.get(po) != null && !field.getName().equals("createTime")) {
				setWeekGrowth(vo);
		}
	}

	/**
	 * vo对象设置周增长数据的方法
	 * @param vo  TotalAnalysisVo,UserAnalysisVO vo对象
	 * @param field  文件对象
	 * @param po TotalAnalysisTV,UserAnalysisVo对象
	 * @throws NumberFormatException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	public void  setWeekGrowth(final Object vo)
	throws NumberFormatException, IllegalArgumentException, IllegalAccessException
	{
			//增长率初始为100%
			String lastWeekRate = "0";
			//增长值
			String lastWeekValue = "0";
			// 设置精度为两位
			DecimalFormat dcmFmt = new DecimalFormat("0.0");
			// 判断VO对象的类型
			if (vo instanceof UserAnalysisVO) {
				// 设置上周增长数据
				if (null != ((UserAnalysisVO) vo).getSecondWeek()) {
					
					lastWeekValue = dcmFmt.format(Long.parseLong(((UserAnalysisVO) vo).getFirstWeek())
								- Long.parseLong(((UserAnalysisVO) vo).getSecondWeek()));
					
					//被除数不能0
					if (!("0").equals(((UserAnalysisVO) vo).getSecondWeek())) {
						lastWeekRate=dcmFmt.format(Double
								.parseDouble(lastWeekValue)/Double.parseDouble(((UserAnalysisVO) vo).getSecondWeek())*100);
						((UserAnalysisVO) vo).setLastWeek(lastWeekValue+"["+lastWeekRate+"%]");
					}
					else {
						((UserAnalysisVO) vo).setLastWeek(lastWeekValue);
					}

					//重新初始化增长率
					lastWeekRate="0";
				}
				// 设置上上周增长数据
				if (null != ((UserAnalysisVO) vo).getThirdWeek()) {
					
					lastWeekValue=dcmFmt.format((Long.parseLong(((UserAnalysisVO) vo).getSecondWeek())
						- Long.parseLong(((UserAnalysisVO) vo).getThirdWeek())));
									
					//被除数不能0
					if (!("0").equals(((UserAnalysisVO) vo).getThirdWeek())) {
						lastWeekRate =dcmFmt.format(Double
							.parseDouble(lastWeekValue)/Double.parseDouble(((UserAnalysisVO) vo).getThirdWeek())*100);
						((UserAnalysisVO) vo).setUpLastWeek(lastWeekValue+"["+lastWeekRate+"%]");
					}
					else {
						((UserAnalysisVO) vo).setUpLastWeek(lastWeekValue);
					}
					
				}
			} else if (vo instanceof TotalAnalysisVo) {
				// 设置上周增长数据
				if (null != ((TotalAnalysisVo) vo).getSecondWeek()) {
					
					 lastWeekValue = dcmFmt.format(Double.parseDouble(((TotalAnalysisVo) vo).getFirstWeek())
							- Double.parseDouble(((TotalAnalysisVo) vo).getSecondWeek()));
						
					//被除数不能0
					if (!("0").equals(((TotalAnalysisVo) vo).getSecondWeek())) {
						lastWeekRate =dcmFmt.format(Double
							.parseDouble(lastWeekValue)/Double.parseDouble(((TotalAnalysisVo) vo).getSecondWeek())*100);
						
							((TotalAnalysisVo) vo).setLastWeek(lastWeekValue+"["+lastWeekRate+"%]");
					}
					else{
						((TotalAnalysisVo) vo).setLastWeek(lastWeekValue);
					}
					
					//重新初始化增长率
					lastWeekRate="0";
				}
				// 设置上上周增长数据
				if (null != ((TotalAnalysisVo) vo).getThirdWeek()) {
					lastWeekValue=dcmFmt.format(Double.parseDouble(((TotalAnalysisVo) vo).getSecondWeek())
					- Double.parseDouble(((TotalAnalysisVo) vo).getThirdWeek()));
					
					if (!("0").equals(((TotalAnalysisVo) vo).getThirdWeek())) {
						lastWeekRate =dcmFmt.format(Double
							.parseDouble(lastWeekValue)/Double.parseDouble(((TotalAnalysisVo) vo).getThirdWeek())*100);
							
							((TotalAnalysisVo) vo).setUpLastWeek(lastWeekValue+"["+lastWeekRate+"%]");
					}else {
						((TotalAnalysisVo) vo).setUpLastWeek(lastWeekValue);
					}
						
				}
			}
	}
	public void setWeekAnalysisTVDAO(final WeekAnalysisTVDAO weekAnalysisTVDAO) {
		this.weekAnalysisTVDAO = weekAnalysisTVDAO;
	}

	public void setTotalAnalysisTVDAO(
			final TotalAnalysisTVDAO totalAnalysisTVDAO) {
		this.totalAnalysisTVDAO = totalAnalysisTVDAO;
	}
}
