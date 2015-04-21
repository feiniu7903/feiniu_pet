package com.lvmama.report.vo;
/**
 * 整体统计的实体对象
 * @author yangchen
 *
 */
public class TotalAnalysisVo extends UserAnalysisBasic {
	/**
	 *用户统计关注的属性
	 */
	private String name;
	/**
	 * 用户统计关注的属性
	 */
	private String englishName;
	/**
	 * 上周
	 */
	private String lastWeek; // 上周
	/**
	 * 上上周
	 */
	private String upLastWeek;

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public String getEnglishName() {
		return englishName;
	}

	public void setEnglishName(final String englishName) {
		this.englishName = englishName;
	}

	public String getLastWeek() {
		return lastWeek;
	}

	public void setLastWeek(final String lastWeek) {
		this.lastWeek = lastWeek;
	}

	public String getUpLastWeek() {
		return upLastWeek;
	}

	public void setUpLastWeek(final String upLastWeek) {
		this.upLastWeek = upLastWeek;
	}

}
