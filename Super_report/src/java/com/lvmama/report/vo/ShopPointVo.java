package com.lvmama.report.vo;


/**.
 * 积分统计的VO
 * 
 * @author yangchen
 * 
 */
public class ShopPointVo {
	/**.
	 * 类
	 * @param name 名称
	 * @param sumPoint 积分
	 * @param membersCount 会员量
	 * @param pointProportion 积分比例
	 * @param memProportion 会员比例
	 */
	public ShopPointVo(final String name, Long sumPoint, Long membersCount,
			final String pointProportion, String memProportion) {
		super();
		this.name = name;
		this.sumPoint = sumPoint;
		this.membersCount = membersCount;
		this.pointProportion = pointProportion;
		this.memProportion = memProportion;
	}
	
	public ShopPointVo() {
		super();
		// TODO Auto-generated constructor stub
	}

	/** 列名. **/
	private String name;

	/** 积分总量 .**/
	private Long sumPoint;
	/** 会员数量 **/
	private Long membersCount;
	/** 积分比例 .**/
	private String pointProportion;
	/** 会员比例 **/
	private String memProportion;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getSumPoint() {
		return sumPoint;
	}

	public void setSumPoint(Long sumPoint) {
		this.sumPoint = sumPoint;
	}

	public Long getMembersCount() {
		return membersCount;
	}

	public void setMembersCount(Long membersCount) {
		this.membersCount = membersCount;
	}

	public String getPointProportion() {
		return pointProportion;
	}

	public void setPointProportion(String pointProportion) {
		this.pointProportion = pointProportion;
	}

	public String getMemProportion() {
		return memProportion;
	}

	public void setMemProportion(String memProportion) {
		this.memProportion = memProportion;
	}

}
