package com.lvmama.comm.search.vo;

import java.util.List;
import java.io.Serializable;
import com.lvmama.comm.search.annotation.FilterParam;

/**
 * 搜索基础VO
 * 
 * @author YangGan
 *
 */
public class SearchVO  implements Serializable{

	private static final long serialVersionUID = 1L;
	protected String fromDest;
	/** 搜索接口的调用方, 默认=浏览器, 点评栏目=dianpin , 手机端=client **/
	protected String fromPage;

	protected String fromDestId;
	
	protected String keyword;
	private String orikeyword;
	
	private String cookieid;
	public String getOrikeyword() {
		return orikeyword;
	}

	public void setOrikeyword(String orikeyword) {
		this.orikeyword = orikeyword;
	}

	public SearchVO() {
		super();
	}
	
	public SearchVO(String fromDest, String keyword) {
		super();
		this.fromDest = fromDest;
		this.keyword = keyword;
	}
	public SearchVO(String fromDest, String fromDestId, String keyword, String fromPage, Integer page, Integer pageSize) {
		super();
		this.fromDest = fromDest;
		this.fromDestId = fromDestId;
		this.keyword = keyword;
		this.fromPage = fromPage;
		this.page = page;
		this.pageSize = pageSize;
	}

	public SearchVO(String fromDest, String keyword, Integer page, Integer pageSize) {
		super();
		this.fromDest = fromDest;
		this.keyword = keyword;
		this.page = page;
		this.pageSize = pageSize;
	}
	@FilterParam(value="P",transcode=false)
	private Integer page =1;
	
	private Integer pageSize = 10;
	
	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public String getFromDest() {
		return fromDest;
	}

	public void setFromDest(String fromDest) {
		this.fromDest = fromDest;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}
	
	public void setPage(String page){
		this.page = Integer.parseInt(page);
	}

	public String getFromDestId() {
		return fromDestId;
	}

	public void setFromDestId(String fromDestId) {
		this.fromDestId = fromDestId;
	}

	public String getFromPage() {
		return fromPage;
	}

	public void setFromPage(String fromPage) {
		this.fromPage = fromPage;
	}

	public String getCookieid() {
		return cookieid;
	}

	public void setCookieid(String cookieid) {
		this.cookieid = cookieid;
	}

	
	
}
