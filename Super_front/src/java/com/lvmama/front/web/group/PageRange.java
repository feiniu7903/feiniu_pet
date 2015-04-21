package com.lvmama.front.web.group;
/**
 * 团购分页范围对象
 * @author songlianjun
 *
 */
public class PageRange {
	private Long minPage ;
	private Long maxPage;
	
	public PageRange(Long min,Long max){
		this.maxPage=max;
		this.minPage= min;
		
	}

	public Long getMinPage() {
		return minPage;
	}

	public Long getMaxPage() {
		return maxPage;
	}
}
