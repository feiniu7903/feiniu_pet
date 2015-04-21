package com.lvmama.report.vo;

/**
 * 用户统计记录对象是实体类
 * @author yangchen
 */
public class UserAnalysisVO extends UserAnalysisBasic {
	/**
	 * 用户统计关注的属性
	 */
	private String name;
	/**
	 * 用户统计关注的属性
	 */
	private String englishName;
	/**
	 * 上周
	 */
	private String lastWeek;
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

	public void setLastWeek(String lastWeek) {
		this.lastWeek = lastWeek;
	}

	public String getUpLastWeek() {
		return upLastWeek;
	}

	public void setUpLastWeek(String upLastWeek) {
		this.upLastWeek = upLastWeek;
	}

}
