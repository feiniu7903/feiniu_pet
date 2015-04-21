package com.lvmama.report.service;

import java.util.List;
import java.util.Map;

import com.lvmama.report.po.PointRules;
import com.lvmama.report.vo.ShopPointVo;

/**
 * 积分业务类
 * @author yangchen
 */
public interface ReportPointService {
	/**
	 * 查询
	 * @param param
	 *            参数
	 * @return 集合
	 */
	List<ShopPointVo> queryAll(Map<String, Object> param);

	/**
	 * 查询积分统计的数据
	 * @param param
	 *            参数
	 * @return 积分统计的数据
	 */
	Long queryPointStatistics(Map<String, Object> param);

	/**
	 * 查询会员统计的数据
	 * @param param
	 *            参数
	 * @return 积分统计的数据
	 */
	Long queryMemStatistics(Map<String, Object> param);

	/**
	 * 动态获取类型
	 * @return 动态列表
	 */
	List<PointRules> queryTypeList();

	/**
	 * 可用积分
	 * @return 返回可用积分
	 */
	Long sumPoint();

	/**
	 * 过期积分
	 * @return 过期积分总和
	 */
	Long expiredPoint();

	/**
	 * 保存客服发放数据的记录
	 * @param info
	 *            属性
	 */
	void savePutPoint(Map<String, Object> info);
}
