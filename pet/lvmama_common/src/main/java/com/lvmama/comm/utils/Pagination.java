package com.lvmama.comm.utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.comm.pet.vo.Page;



public class Pagination{
	
	private static final Log logger=LogFactory.getLog(Pagination.class);
	private long page;
	String str = "";
	private long totalPage;
	private String action;
	private int mode;
	private long showTimes = 1;
	private String type;
	private long pageSize;
	private String cssStyle;
	private long totalResultSize = 0;
	private int[] pageSizeList = {10,20,50,100,200};
	private String currentPageParamName;

	public static String pagination(long pageSize,long totalPage,String url,long page){
		Pagination pagination = new Pagination();
		pagination.setPageSize(pageSize);
		pagination.setTotalPage(totalPage);
		pagination.setAction(url);
		pagination.setPage(page);
		pagination.setAction(url);
		pagination.setMode(10);
		return pagination.doStartTag();
	}
	
	public static String pagination(long pageSize,long totalPage,String url,long page, String type){
		Pagination pagination = new Pagination();
		pagination.setPageSize(pageSize);
		pagination.setTotalPage(totalPage);
		pagination.setAction(url);
		pagination.setPage(page);
		pagination.setAction(url);
		pagination.setMode(10);
		pagination.setType(type);
		return pagination.doStartTag();
	}
	
	public static String pagination(long pageSize,long totalPage,String url,long page, long totalResultSize){
		Pagination pagination = new Pagination();
		pagination.setPageSize(pageSize);
		pagination.setTotalPage(totalPage);
		pagination.setAction(url);
		pagination.setPage(page);
		pagination.setTotalResultSize(totalResultSize);
		pagination.setAction(url);
		pagination.setMode(10);
		return pagination.doStartTag();
	}
	public Pagination() {
		super();
	}
	public Pagination(long pageSize,long totalPage,String url,long page){
		this.pageSize=pageSize;
		this.totalPage=totalPage;
		this.action=url;
		this.page=page;
		this.mode=10;
	}
	
	public static String pagination(Page page){
		if(page != null && page.getTotalPageNum()>0){
			Pagination pagination = new Pagination();
			pagination.setPageSize(page.getPageSize());
			pagination.setTotalPage(page.getTotalPageNum());
			pagination.setAction(page.getUrl());
			pagination.setPage(page.getCurrentPage());
			pagination.setMode(12);
			pagination.setPageSizeParamName(page.getCurrentPageParamName());
			return pagination.doStartTag();
		}
		return "";
	}
	/**
	 * 使用post生成分页的方式
	 * @param pageSize
	 * @param totalPage
	 * @param url
	 * @param page
	 * @param type
	 * @param pageParamName
	 * @param currPageParamName
	 * @return
	 */
	public static String pagePost(long pageSize,long totalPage,String url,long page){
		Pagination pagination = new Pagination();
		pagination.setPageSize(pageSize);
		pagination.setTotalPage(totalPage);
		pagination.setAction(url);
		pagination.setPage(page);
		pagination.setMode(12);
		pagination.setPageSizeParamName("page");
		return pagination.doStartTag();
	}
	
	public static String pageSupplier(Page page,String other){
		if(page!=null && page.getTotalPageNum()>0){
			Pagination pagination = new Pagination();
			pagination.setPageSize(page.getPageSize());
			pagination.setTotalPage(page.getTotalPageNum());
			pagination.setAction(page.getUrl());
			pagination.setPage(page.getCurrentPage());
			pagination.setTotalResultSize(page.getTotalResultSize());
			pagination.setMode(13);
			pagination.setPageSizeParamName(page.getCurrentPageParamName());
			pagination.setOther(other);
			return pagination.doStartTag();
		}
		return "";
	}
	
	public void setOther(String other) {
		this.other = other;
	}

	public long getTotalResultSize() {
		return totalResultSize;
	}
	public void setTotalResultSize(long totalResultSize) {
		this.totalResultSize = totalResultSize;
	}
	public long getPage() {
		return page;
	}

	public void setPage(long page) {
		this.page = page;
	}


	public long getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(long totalPage) {
		this.totalPage = totalPage;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}


	private void initPage() throws Exception {
		try {
			if (this.page == 0) {
				page = 1;
			}
			if (this.totalPage == 0) {
				this.totalPage = 1;
			}
			if (this.page < 1) {
				page = 1;
			}
			if (this.totalPage < 1) {
				totalPage = 1;
			}
			if (this.page > this.totalPage) {
				this.page = this.totalPage;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void createTag(int type){
		try {
			long prevPage = this.page - 1;
			long nextPage = this.page + 1;
			long startPage = 0;
			if(pageSize==0){
				logger.error("pagination url:"+getAction()+" set pageSize=0");
				pageSize=1;
			}
			if (this.page % pageSize == 0) {
				startPage = this.page - (pageSize - 1);
			} else {
				startPage = this.page - this.page % pageSize + 1;
			}
			switch (type) {
			case 0:
				str +="<span class='number'>";
				if (prevPage < 1) {
					str +="<span title='到第一页'>&#171;</span>";
					str +="<span title='上一页'>&#139;</span>";
				} else {

					str +="<span title='到第一页'><a href='" + this.toPage(1)
							+ "'>&#171;</a></span>";

					str+="<span title='上一页'><a href='"
									+ this.toPage(prevPage)
									+ " ');'>&#139;</a></span>";
				}
				for (long i = 1; i <= this.totalPage; i++) {
					if (i > 0) {
						if (i == this.page) {

							str +="<span title='第 " + i + "页 '>[" + i
									+ "]</span>";
						} else {

							str +="<span title='第 " + i + "页'><a href='"
									+ this.toPage(i) + "'>[" + i
									+ "]</a></span>";
						}
					}
				}
				if (nextPage > this.totalPage) {
					str +="<span title='下页'>&#155;</span>";
					str +="<span title='最后一页'>&#187;</span>";
				} else {

					str +="<span title='下一页'><a href='"
							+ this.toPage(nextPage) + "'>&#155;</a></span>";

					str +="<span title='到最后一页'><a href='"
							+ this.toPage(this.totalPage)
							+ " ');'>&#187;</a></span>";
				}
				str +="</span><br/>";

				break;
			case 1: // 模式1 (10页缩略,首页,前页,后页,尾页)
				str +="<span class='number'>";
				if (prevPage < 1) {
					str +="<span title='到第一页'>&#171;</span>";
					str +="<span title='上一页'>&#139;</span>";
				} else {
					str +="<span title='到第一页'><a href='" + this.toPage(1)
							+ "'>&#171;</a></span>";

					str+="<span title='上一页'><a href='"
									+ this.toPage(prevPage)
									+ " ');'>&#139;</a></span>";
				}

				if (startPage > pageSize)

					str +="<span title='Prev "+pageSize+" Pages'><a href='"
							+ this.toPage(startPage - 1) + "'>上一页</a></span>";
				for (long i = startPage; i < startPage + pageSize; i++) {
					if (i > this.totalPage)
						break;
					if (i == this.page) {

						str +="<span title='第 " + i + "页'>[" + i
								+ "]</span>";
					} else {

						str +="<span title='Page " + i + "'><a href='"
								+ this.toPage(i) + "'>[" + i + "]</a></span>";
					}
				}
				if (this.totalPage >= startPage + pageSize)
					str +="<span title='Next "+pageSize+" Pages'><a href='"
							+ this.toPage(startPage + pageSize) + "'>下一页</a></span>";
				if (nextPage > this.totalPage) {
					str +="<span title='下页'>&#155;</span>";
					str +="<span title='最后一页'>&#187;</span>";
				} else {

					str +="<span title='下一页'><a href='"
							+ this.toPage(nextPage) + "'>&#155;</a></span>";

					str +="<span title='到最后一页'><a href='"
							+ this.toPage(this.totalPage)
							+ " ');'>&#187;</a></span>";
				}
				str +="</span><br/>";
				break;
			case 2: // 模式2 (前后缩略,页数,首页,前页,后页,尾页)
				long endPage = 0;
				str +="<span class='number'>";
				if (prevPage < 1) {
					str +="<span title='到第一页'>&#171;</span>";
					str +="<span title='上一页'>&#139;</span>";
				} else {
					str +="<span title='到第一页'><a href='" + this.toPage(1)
							+ "'>&#171;</a></span>";

					str+="<span title='上一页'><a href='"
									+ this.toPage(prevPage)
									+ " ');'>&#139;</a></span>";
				}
				if (this.page != 1)
					str +="<span title='到第一页'><a href='" + this.toPage(1)
							+ "'>[1]</a></span>";

				if (this.page >= 5)
					str +="<span>...</span>";
				if (this.totalPage > this.page + 2) {
					endPage = this.page + 2;
				} else {
					endPage = this.totalPage;
				}
				for (long i = this.page - 2; i <= endPage; i++) {
					if (i > 0) {
						if (i == this.page) {

							str +="<span title='第 " + i + "页'>[" + i
									+ "]</span>";
						} else {
							if (i != 1 && i != this.totalPage) {
								str +="<span title='Page " + i
										+ "'><a href='" + this.toPage(i)
										+ "'>[" + i + "]</a></span>";
							}
						}
					}
				}
				if (this.page + 3 < this.totalPage)
					str +="<span>...</span>";
				if (this.page != this.totalPage)
					str +="<span title= '第 " + this.totalPage
							+ "'><a href='" + this.toPage(this.totalPage)
							+ "');'>[" + this.totalPage + "]</a></span>";

				if (nextPage > this.totalPage) {
					str +="<span title='下页'>&#155;</span>";
					str +="<span title='最后一页'>&#187;</span>";
				} else {
					str +="<span title='下一页'><a href='"
							+ this.toPage(nextPage) + "'>&#155;</a></span>";

					str +="<span title='到最后一页'><a href='"
							+ this.toPage(this.totalPage)
							+ " ');'>&#187;</a></span>";
				}
				str +="</span><br/>";
				break;
			case 3: // 模式3 (10页缩进) (only IE)
				str +="<style type=\"text/css\">.p-unsele-page {font-size:12px;border:1px solid #ccc;}"+"</style>";
				str +="<table id =\"pag\" width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">";
				str +="<tr>";
				
				if(this.page==1){
					str +="<td width=\"20\" height=\"20\">";
				str +="<button class=\"page_prew\"></button>";
				str +="</td>";
				}else{
					str +="<td width=\"20\" height=\"20\">";
				 str +="<button class=\"page_prew\" onclick=\"window.location='"+toPage(prevPage)+"'"+"\"></button>";
				 str +="</td>";
				}
				if(this.page>5&&this.page+5>=this.totalPage){
					str +="<td width=\"20\" height=\"20\" align=\"center\"  class=\"p-unsele-page\">";
					str +="<a class=\"user_vip_page\"  href="+toPage(1)+" title=\"第"+1+"页\">"+1+"</a>";
					str +="</td>";
					str +="<td width=\"20\" height=\"20\" align=\"center\"  class=\"p-unsele-page\">";
					str +="...";
					str +="</td>";
					if(this.page-5>=1){
						for(long i=this.page-5;i<=this.totalPage;i++){
							str +="<td width=\"20\" height=\"20\" align=\"center\" class=\"p-unsele-page\">";
							if(this.page==i){
								str +="<font color=\"red\">"+i+"</font>";
							}else{
								str +="<a class=\"user_vip_page\"  href="+toPage(i)+""+" title=\"第"+i+"页\">"+i+"</a>";
							}
							str +="</td>";
							
						}
						if(this.page+5==this.totalPage-1){
							str +="<td width=\"20\" height=\"20\" align=\"center\"  class=\"p-unsele-page\">";
							str +="<a class=\"user_vip_page\" href="+toPage(this.totalPage)+""+" title=\"第"+this.totalPage+"页\">"+this.totalPage+"</a>";
							str +="</td>";
						}else if(this.page==this.totalPage){
							
						}else if(this.page+5>=this.totalPage){
							
						}else{
							str +="<td width=\"20\" height=\"20\" align=\"center\"  class=\"p-unsele-page\">";
							str +="...";
							str +="</td>";
							str +="<td width=\"20\" height=\"20\" align=\"center\"  class=\"p-unsele-page\">";
							str +="<a class=\"user_vip_page\" href="+toPage(this.totalPage)+""+" title=\"第"+this.totalPage+"页\">"+this.totalPage+"</a>";
							str +="</td>";
						}
					}else{
		
					
						if(this.page+pageSize<=this.totalPage){

							if(this.page-5>=1){
								for(long i=this.page-5;i<=this.page+pageSize;i++){
									str +="<td width=\"20\" height=\"20\" align=\"center\"  class=\"p-unsele-page\">";
									if(i==this.page){
										str +="<font color=\"red\">"+i+"</font>";
									}else{
									str +="<a class=\"user_vip_page\" href="+toPage(i)+""+" title=\"第"+i+"页\">"+i+"</a>";
									}
									str +="</td>";
									
								}
							}else{
							for(long i=1;i<= this.page+pageSize;i++){
								str +="<td width=\"20\" height=\"20\" align=\"center\"  class=\"p-unsele-page\">";
								if(i==this.page){
									str +="<font color=\"red\">"+i+"</font>";
								}else{
								str +="<a class=\"user_vip_page\" href="+toPage(i)+""+" title=\"第"+i+"页\">"+i+"</a>";
								}
								str +="</td>";
								
							}
							}
							if(this.page+pageSize!=this.totalPage){
							str +="<td width=\"20\" height=\"20\" align=\"center\"  class=\"p-unsele-page\">";
							str +="...";
							str +="</td>";
							str +="<td width=\"20\" height=\"20\" align=\"center\"  class=\"p-unsele-page\">";
							str +="<a class=\"user_vip_page\" href="+toPage(this.totalPage)+""+" title=\"第"+this.totalPage+"页\">"+this.totalPage+"</a>";
							str +="</td>";
							}
						}else{
						for(long i=1;i<=this.totalPage;i++){
							
							if(this.page==i){
								str +="<td width=\"20\" height=\"20\" align=\"center\"  class=\"p-unsele-page\">";
								str +="<font color=\"red\">"+i+"</font>";
								str +="</td>";
							}else{
								str +="<td width=\"20\" height=\"20\" align=\"center\"  class=\"p-unsele-page\">";
								str +="<a class=\"user_vip_page\" href="+toPage(i)+""+" title=\"第"+i+"页\">"+i+"</a>";
								str +="</td>";
							}
							
							
						}
						
						}
					}
				
				}else{
					
					if(this.page-5>=3){
						str +="<td width=\"20\" height=\"20\" align=\"center\"  class=\"p-unsele-page\">";
						str +="<a class=\"user_vip_page\"  href="+toPage(1)+" title=\"第"+1+"页\">"+1+"</a>";
						str +="</td>";
						str +="<td width=\"20\" height=\"20\" align=\"center\"  class=\"p-unsele-page\">";
						str +="...";
						str +="</td>";
					for(long i=this.page-5;i<=this.page+5;i++){
						str +="<td width=\"20\" class=\"p-unsele-page\" height=\"20\" align=\"center\" >";
						if(this.page==i){
							str +="<font color=\"red\">"+i+"</font>";
						}else{
							str +="<a class=\"user_vip_page\" href="+toPage(i)+" title=\"第"+i+"页\">"+i+"</a>";
						}
						str +="</td>";
						
						
					}
					if(this.page+5==this.totalPage-1){
						str +="<td width=\"20\" height=\"20\" align=\"center\"  class=\"p-unsele-page\">";
						str +="<a class=\"user_vip_page\" href="+toPage(this.totalPage)+""+" title=\"第"+this.totalPage+"页\">"+this.totalPage+"</a>";
						str +="</td>";
					}else if(this.page==this.totalPage){
						
					}else if(this.page+5>=this.totalPage){
						
					}else{
						str +="<td width=\"20\" height=\"20\" align=\"center\"  class=\"p-unsele-page\">";
						str +="...";
						str +="</td>";
						str +="<td width=\"20\" height=\"20\" align=\"center\"  class=\"p-unsele-page\">";
						str +="<a class=\"user_vip_page\" href="+toPage(this.totalPage)+""+" title=\"第"+this.totalPage+"页\">"+this.totalPage+"</a>";
						str +="</td>";
					}
					}else{
						if(this.page+pageSize<=this.totalPage){
							if(this.page-5>=1){
								for(long i=this.page-5;i<=this.page+pageSize;i++){
									str +="<td width=\"20\" height=\"20\" align=\"center\"  class=\"p-unsele-page\">";
									if(i==this.page){
										str +="<font color=\"red\">"+i+"</font>";
									}else{
									str +="<a class=\"user_vip_page\" href="+toPage(i)+""+" title=\"第"+i+"页\">"+i+"</a>";
									}
									str +="</td>";
									
								}
							}else{
								for(long i=1;i<=this.page+pageSize;i++){
									str +="<td width=\"20\" height=\"20\" align=\"center\"  class=\"p-unsele-page\">";
									if(i==this.page){
										str +="<font color=\"red\">"+i+"</font>";
									}else{
									str +="<a class=\"user_vip_page\" href="+toPage(i)+""+" title=\"第"+i+"页\">"+i+"</a>";
									}
									str +="</td>";
									
								}
							}
							if(this.page+pageSize!=this.totalPage){
							str +="<td width=\"20\" height=\"20\" align=\"center\"  class=\"p-unsele-page\">";
							str +="...";
							str +="</td>";
							str +="<td width=\"20\" height=\"20\" align=\"center\"  class=\"p-unsele-page\">";
							str +="<a class=\"user_vip_page\" href="+toPage(this.totalPage)+""+" title=\"第"+this.totalPage+"页\">"+this.totalPage+"</a>";
							str +="</td>";
							}
						}else{
							for(long i=1;i<=this.totalPage;i++){
								if(this.page==i){
									str +="<td width=\"20\" class=\"p-unsele-page\" height=\"20\" align=\"center\" >";
									str +="<font color=\"red\">"+i+"</font>";
									str +="</td>";
								} else {
									str +="<td width=\"20\" class=\"p-unsele-page\" height=\"20\" align=\"center\" >";
									str +="<a class=\"user_vip_page\" href="+toPage(i)+""+" title=\"第"+i+"页\">"+i+"</a>";
									str +="</td>";
								}
							}
						}
					}
				}
				
				
				if(this.page!=this.totalPage){
					str +="<td width=\"20\" height=\"20\">";
					str +="<button class=\"page_next\" onclick=\"window.location='"+toPage(nextPage)+""+"'\"></button>";
					str +="</td>";
					str +="<td width=\"40\" height=\"20\">到第</td>";
					str +="<td width=\"20\" height=\"20\"><input id=\"page\" name=\"textfield2\" type=\"text\" class=\"writein_field\" size=\"2\" /></td>";
					str +="<td width=\"20\" height=\"20\">页</td>";
					str +=" <td  height=\"20\"><button class=\"page_fir\" align=\"left\" onclick=\"toPage('"+this.action+"')\"></button></td>";
				}else{
					str +="<td width=\"20\" height=\"20\">";
					str +="<button class=\"page_next\"></button>";
					str +="</td>";
					str +="<td width=\"40\" height=\"20\">到第</td>";
					str +="<td width=\"20\" height=\"20\"><input id=\"page\" name=\"textfield2\" type=\"text\" class=\"writein_field\" size=\"2\" /></td>";
					str +="<td width=\"20\" height=\"20\">页</td>";
					str +=" <td  height=\"20\" align=\"left\"><button class=\"page_fir\" onclick=\"toPage('"+this.action+"')\"></button></td>";

				}
				
				str +="</tr>";
				str +="</table>";
				break;
			case 4: // 模式3 (箭头样式,首页,前页,后页,尾页) (only IE)
				str +="<span class='number'>";
				if (prevPage < 1) {
					str +="<span title='到第一页'><strong><<</strong></span>";
					str +="<span title='上一页'><strong><</strong> </span>";
				} else {
					str +="<span title='到第一页'><a href='" + this.toPage(1)
							+ "'><strong><<</strong></a></span>";
					str +="<span title='上一页'><a href='"
							+ this.toPage(prevPage)
							+ "'><font color='red'> < </font></a></span>";
				}
				if (nextPage > this.totalPage) {
					str +="<span title='下页'><strong>></strong></span>";
					str +="<span title='最后一页'><strong>>></strong></span>";
				} else {

					str +="<span title='下一页'><a href='"
							+ this.toPage(nextPage)
							+ "'><font color='red'> > </font></a></span>";

					str +="<span title='最后一页'><a href='"
							+ this.toPage(this.totalPage)
							+ "'><strong>>></strong></a></span>";
				}
				str +="</span><br/>";
				break;

			case 5: // 模式4 (下拉框)
				if (this.totalPage < 1) {

					str +="<select name='toPage' disabled>";
					str +="<option value='0'>No Pages</option>";

				} else {
					String chkSelect;

					str+="<select name='toPage' onchange='location=this.options[this.selectedIndex].value'>";
					for (long i = 1; i <= this.totalPage; i++) {
						if (this.page == i)
							chkSelect = " selected='selected'";
						else
							chkSelect = "";
						str +="<option value='" + this.toPage(i) + "'"
								+ chkSelect + ">到:  " + i + " /  "
								+ this.totalPage + "页</option>";
					}
				}
				str +="</select>";
				break;
			case 6: // 模式5 (输入框)
				str +="<span class='input'>";
				if (this.totalPage < 1) {
					str+="<input type='text' name='toPage' value='No Pages' class='itext' disabled='disabled'>";
					str+="<input type='button' name='go' value='GO' class='ibutton' disabled='disabled'></option>";
				} else {

					str+="<input type='text' value='输入页码:' class='ititle' readonly='readonly'>";

					str+="<input type='text' id='pageInput"
									+ this.showTimes
									+ " + ' value='"
									+ this.page
									+ "' class='itext' title='Input page'  onfocus='this.select()'>";

					str +="<input type='text' value=' / " + this.totalPage
							+ "' class='icount' readonly='readonly'>";

					str+="<input type='button' name='go' value='GO' class='ibutton' onclick='location='"
									+ this.toPage(1) + "'></input>";
				}
				str +="</span>";
				break;
			case 7:
				str +="<span class='number'>";
				if (this.totalPage <= 1) {
					str +="<span title=\"第一页\">\u7b2c\u4e00\u9875</span>";
				} else {
					if (this.page == 1) {

						str+="<span title=\"\u7b2c\u4e00\u9875\">\u7b2c\u4e00\u9875</span>";

						str +="<span>&nbsp;&nbsp;|&nbsp;&nbsp;" + this.page
								+ "&nbsp;&nbsp;<a href='"
								+ this.toPage(this.page + 1) + "'>"
								+ (this.page + 1)
								+ "</a>&nbsp;&nbsp;|&nbsp;&nbsp;</span>";

						str +="<span title=\"下一页\"><a href=\'"
								+ this.toPage(nextPage)
								+ "'>\u4e0b\u4e00\u9875</a></span>";
						String chkSelect;

						str+="<select name=\"toPage\" onchange='location=this.options[this.selectedIndex].value'>";
						for (long i = 1; i <= this.totalPage; i++) {
							if (this.page == i) {
								chkSelect = " selected=\"selected\"";

							} else {
								chkSelect = "";
							}
							str +="<option value='" + this.toPage(i) + "'"
									+ chkSelect + ">到:  " + i + " /  "
									+ this.totalPage + "页</option>";
						}

						str +="</select>";
					} else {
						if (this.page > 1 && this.page < this.totalPage) {
							str +="<span title=\"上一页\"><a href='"
									+ this.toPage(prevPage)
									+ "'>\u4e0a\u4e00\u9875</a></span>";
							str+="<span>&nbsp;&nbsp;|&nbsp;&nbsp;<a href='"
											+ this.toPage(this.page - 1)
											+ "'>"
											+ (this.page - 1)
											+ "</a>&nbsp;&nbsp;"
											+ this.page
											+ "&nbsp;&nbsp;<a href='"
											+ this.toPage(this.page + 1)
											+ "'>"
											+ (this.page + 1)
											+ "</a>&nbsp;&nbsp;|&nbsp;&nbsp;</span>";

							str+="<span title=\"\u4e0b\u4e00\u9875\"><a href='"
											+ this.toPage(nextPage)
											+ "'>\u4e0b\u4e00\u9875</a></span>";
							String chkSelect;

							str+="<select name=\"toPage\" onchange='location=this.options[this.selectedIndex].value'>";
							for (long i = 1; i <= this.totalPage; i++) {
								if (this.page == i) {
									chkSelect = " selected=\"selected\"";
								} else {
									chkSelect = "";
								}

								str +="<option value='" + this.toPage(i)
										+ "'" + chkSelect + ">到  " + i + " /  "
										+ this.totalPage + "页</option>";

							}

							str +="</select>";
						} else {
							if (this.page == this.totalPage) {

								str+="<span title=\"\u4e0a\u4e00\u9875\"><a href='"
												+ this.toPage(prevPage)
												+ "'>\u4e0a\u4e00\u9875</a></span>";

								str+="<span>&nbsp;&nbsp;|&nbsp;&nbsp;<a href='"
												+ this.toPage(this.page - 1)
												+ "'>"
												+ (this.page - 1)
												+ "</a>&nbsp;&nbsp;"
												+ this.page
												+ "&nbsp;&nbsp;|&nbsp;&nbsp;</span>";

								str+="<span title=\"最后一页\">\u6700\u540e\u4e00\u9875</span>";
								String chkSelect;

								str+="<select name=\"toPage\" onchange='location=this.options[this.selectedIndex].value'>";
								for (long i = 1; i <= this.totalPage; i++) {
									if (this.page == i) {
										chkSelect = " selected=\"selected\"";
									} else {
										chkSelect = "";
									}

									str +="<option value='"
											+ this.toPage(i) + "'" + chkSelect
											+ ">到  " + i + " /  "
											+ this.totalPage + "页</option>";
								}

								str +="</select>";
							}
						}
					}
				}
				break;
			case 8:
				if (startPage > pageSize) {
					str +=" <span id='pages' title='Prev "+pageSize+" Pages'><a href='"
							+ this.toPage(startPage - 1) + "'>上一页</a></span>";
				}
				for (long i = startPage; i < startPage + pageSize; i++) {
					if (i > this.totalPage)
						break;
					if (i == this.page) {
						str +="<span id='currentPage' title='Page " + i
								+ "'>" + i + "</span>";
					} else {
						str +="<span id='test' title='Page " + i
								+ "'><a href='" + this.toPage(i) + "'>" + i
								+ "</a></span>";

					}

				}
				if (this.totalPage >= startPage + pageSize) {
					str+="<span id='pages' title='Next "+pageSize+" Pages'><a href='"
									+ this.toPage(startPage + pageSize)
									+ "')'>下一页</a></span>";
					str +="</span><br/>";

				}

				break;
			case 9: // 模式2 (前后缩略,页数,首页,前页,后页,尾页)
				long endPage1 = 0;
				str +="<span class='number'>";
				if (prevPage < 1) {
					str +="<span id=\"last\" title='第一页'>第一页</span>";
					str +="<span id=\"last\" title='上一页'>上一页</span>";
				} else {
					str +="<span id=\"pages\"  title='到第一页'><a href='" + this.toPage(1)
							+ "'>首页</a></span>";

					str+="<span id=\"pages\" title='上一页'><a href='"
									+ this.toPage(prevPage)
									+ " ');'>上一页</a></span>";
				}
				if (this.page != 1)
					str +="<span id=\"test\" title='到第一页'><a href='" + this.toPage(1)
							+ "'>1</a></span>";

				if (this.page >= 5)
					str +="<span id=\"test\">......</span>";
				if (this.totalPage > this.page + 2) {
					endPage1 = this.page + 2;
				} else {
					endPage1 = this.totalPage;
				}
				for (long i = this.page - 2; i <= endPage1; i++) {
					if (i > 0) {
						if (i == this.page) {

							str +="<span id=\"currentPage\" title='第 " + i + "页'>" + i
									+ "</span>";
						} else {
							if (i != 1 && i != this.totalPage) {
								str +="<span id=\"test\" title='第 " + i
										+ "页'><a href='" + this.toPage(i)
										+ "'>" + i + "</a></span>";
							}
						}
					}
				}
				if (this.page + 3 < this.totalPage)
					str +="<span id=\"test\">......</span>";
				if (this.page != this.totalPage)
					str +="<span id=\"test\" title= '第 " + this.totalPage
							+ "页'><a href='" + this.toPage(this.totalPage)
							+ "');'>" + this.totalPage + "</a></span>";

				if (nextPage > this.totalPage) {
					str +="<span id=\"last\" title='下页'>下页</span>";
					str +="<span id=\"last\"  title='最后一页'>最后一页</span>";
				} else {
					str +="<span id=\"pages\"  title='下一页'><a href='"
							+ this.toPage(nextPage) + "'>下一页</a></span>";

					str +="<span id=\"toLast\" title='最后一页'><a href='"
							+ this.toPage(this.totalPage)
							+ " ');'>最后一页</a></span>";
				}
				str +="</span><br/>";
				break;
			case 10:
				str +="<div id=\"lv_page\">";
				str +="<div class=\"Pages\">";
				long endPage2 = 0;
				if (prevPage < 1) {
					str +="<a href=\"#\" title=\"上一页\" class=\"PrevPage\" >上一页</a>";
				} else {
					str +="<a href=\""+ this.toPage(prevPage)
							+ "\" title=\"上一页\" class=\"PrevPage\" >上一页</a>";
				}
				if (this.page != 1)
				str +="<a href=\""+ this.toPage(1)
						+ "\" title=\"到第一页\" class=\"PageLink\" >1</a>";

				if (this.page >= 5)
					str +="<span class=\"PageMore\">...</span>";
				if (this.totalPage > this.page + 5) {
					endPage2 = this.page + 5;
				} else {
					endPage2 = this.totalPage;
				}
				for (long i = this.page -5; i <= endPage2; i++) {
					if (i > 0) {
						if (i == this.page) {
							str +="<span class=\"PageSel\">"+i+"</span>";
						} else {
							if (i != 1 && i != this.totalPage) {
								str +="<a href=\""+this.toPage(i)+"\" class=\"PageLink\" title='第 " + i + "页'>" + i
										+ "</a>";
							}
						}
					}
				}
				if (this.page + 3 < this.totalPage)
					str +="<span class=\"PageMore\">...</span>";
				if (this.page != this.totalPage)
					str +="<a  href=\"" + this.toPage(this.totalPage)
							+ "\" class=\"PageLink\" title= '第 " + this.totalPage
							+ "页'>" + this.totalPage + "</a>";

				if (nextPage > this.totalPage) {
					str +="<a href=\"#\" title=\"下一页\" class=\"NextPage\" >下一页</a>";
					
				} else {
					str +="<a href=\""
							+ this.toPage(nextPage)
							+ " \" title=\"下一页\" class=\"NextPage PageLink PageLink_page\" >下一页</a>";
				}
				str +="</span><br/>";
				str +="</div>";
				str +="</div>";
				break;
			case 11:
				str += "<div class=\"page_div\">";
				str += "<span>总共 " + this.totalResultSize + " 条记录 每页 " + this.pageSize + " 条记录</span> ";
				long endPage11 = 0;
				if (prevPage < 1) {
					str +="<a href=\"#\" title=\"上一页\" class=\"PrevPage\" >上一页</a>";
				} else {
					str +="<a href=\""+ this.toPage(prevPage)
							+ "\" title=\"上一页\" class=\"PrevPage\" >上一页</a>";
				}
				if (this.page != 1)
				str +="<a href=\""+ this.toPage(1)
						+ "\" title=\"到第一页\" class=\"PageLink\" >1</a>";

				if (this.page >= 5)
					str +="<span class=\"PageMore\">...</span>";
				if (this.totalPage > this.page + 5) {
					endPage11 = this.page + 5;
				} else {
					endPage11 = this.totalPage;
				}
				for (long i = this.page -5; i <= endPage11; i++) {
					if (i > 0) {
						if (i == this.page) {
							str +="<span class=\"PageSel\">&nbsp;&nbsp;"+i+"&nbsp;&nbsp;</span>";
						} else {
							if (i != 1 && i != this.totalPage) {
								str +="<a href=\""+this.toPage(i)+"\" class=\"PageLink\" title='第 " + i + "页'>" + i
										+ "</a>";
							}
						}
					}
				}
				if (this.page + 3 < this.totalPage)
					str +="<span class=\"PageMore\">...</span>";
				if (this.page != this.totalPage)
					str +="<a  href='" + this.toPage(this.totalPage)
							+ "' class=\"PageLink\" title= '第 " + this.totalPage
							+ "页'>" + this.totalPage + "</a>";

				if (nextPage > this.totalPage) {
					str +="<a href=\"#\" title=\"下一页\" class=\"NextPage\" >下一页</a>";
					
				} else {
					str +="<a class=\"PageLink NextPage PageLink_page\" href='"
							+ this.toPage(nextPage)
							+ " ' title=\"下一页\" >下一页</a>";
				}
				str += "<span> 显示<select name=\"changePageSize\" onchange='location=this.options[this.selectedIndex].value'>";
				String chkSelect11 = "";
				for (int size : pageSizeList) {
					if (this.pageSize == size) {
						chkSelect11 = " selected=\"selected\"";
					} else {
						chkSelect11 = "";
					}
					str +="<option value='"
							+ this.toPageWithSize(size) + "'" + chkSelect11
							+ ">" + size + "</option>";
				}

				str +="</select>条</span>";
				break;
			case 12:
				str=createFormPagination();
				break;
			case 13:
				str=createFormPagination2();				
				break;
			case 14:
				str +="<div id=\"lv_page\">";
				str +="<div class=\"Pages\">";
				long endPageRe = 0;
				if (prevPage < 1) {
					str +="<a href='javascript:;' title=\"上一页\" class=\"PrevPage\" data-page='1'>上一页</a>";
				} else {
					str +="<a href='javascript:;' title=\"上一页\" class=\"PrevPage\" data-page='"+prevPage+"'>上一页</a>";
				}
				if (this.page != 1)
				str +="<a href='javascript:;' title=\"到第一页\" class=\"PageLink\" data-page='1' >1</a>";

				if (this.page >= 5)
					str +="<span class=\"PageMore\">...</span>";
				if (this.totalPage > this.page + 5) {
					endPageRe = this.page + 5;
				} else {
					endPageRe = this.totalPage;
				}
				for (long i = this.page -5; i <= endPageRe; i++) {
					if (i > 0) {
						if (i == this.page) {
							str +="<span class=\"PageSel\">"+i+"</span>";
						} else {
							if (i != 1 && i != this.totalPage) {
								str +="<a href='javascript:;' data-page='"+i+"' class=\"PageLink\" title='第 " + i + "页'>" + i
										+ "</a>";
							}
						}
					}
				}
				if (this.page + 3 < this.totalPage)
					str +="<span class=\"PageMore\">...</span>";
				if (this.page != this.totalPage)
					str +="<a  href='javascript:;' class=\"PageLink\" data-page='"+totalPage+"' title= '第 " + this.totalPage
							+ "页'>" + this.totalPage + "</a>";

				if (nextPage > this.totalPage) {
					str +="<a href='javascript:;' title=\"下一页\" data-page='"+totalPage+"' class=\"NextPage\" >下一页</a>";
					
				} else {
					str +="<a  href='javascript:;' title=\"下一页\" class=\"NextPage PageLink PageLink_page\" data-page='"+nextPage+"' >下一页</a>";
				}
				str +="</span><br/>";
				str +="</div>";
				str +="</div>";
				break;
			}
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}
	
	private String createParam(String param){
		StringBuffer sb=new StringBuffer();
		if(!param.isEmpty()){
			String array[]=StringUtils.split(param,"&");
			for(String str:array){
				String kv[]=str.split("=");
				if(kv==null||kv.length!=2){
					continue;
				}
				
				sb.append("<input type='hidden' name=\"");
				String key=kv[0];
				if(kv[0].contains("\"")){
					key=key.replaceAll("\"", "\'");
				}
				sb.append(key);
				sb.append("\" value='");
				sb.append(kv[1]);
				sb.append("'/>");
			}			
		}	
		if(currentPageParamName==null||"page".equals(this.currentPageParamName)){
			sb.append("<input type='hidden' name='"+pageName+"' value='");
		}else {
			sb.append("<input type='hidden' name='"+this.currentPageParamName+"' value='");
			pageName = this.currentPageParamName;
			pageAName = currentPageParamName.replaceAll("\\.", "_");
		}
		sb.append(page);
		sb.append("'/>");
		return sb.toString();
	}
	
	private String pageName = "page";
	private String pageAName = "PageLink_page";
	private String createPage(String label,long page,boolean useLi){
		StringBuffer sb=new StringBuffer();
		if(useLi){
			sb.append("<li>");			
		}
		sb.append("<a href='javascript:void(0)' title='");
		sb.append(label);
		sb.append("' class='");
		sb.append(pageAName);
		sb.append("' page='");
		sb.append(page);
		sb.append("'>");
		sb.append(label);
		sb.append("</a>");
		if(useLi){
			sb.append("</li>");
		}
		return sb.toString();
	}
	private String createPage(long page){
		return createPage(String.valueOf(page),page,true);
	}
	private String createPage(String label,long page){
		return createPage(label,page,false);		
	}
	private String createCss(){
		StringBuffer sb = new StringBuffer();
		sb.append("<style type='text/css'>");
		sb.append(".table01Footer{ height:36px; padding-left:10px; color:#666; background:#f6f6f6; border:#ddd solid 1px; border-top:none;}");
		sb.append(".table01Footer_l{ float:left; line-height:36px;}");
		sb.append(".footer_l_span{ font-weight:bold; padding:0 3px;}");
		sb.append(".page{ float:right; margin-top:8px; padding-right:10px; color:#666; line-height:18px;}");
		sb.append(".page i{ font-style:normal; float:left; display:block; border:1px solid #ccc; margin-right:10px; padding:0px 5px; color:#666;}");
		sb.append(".page i a{ color:#666;}");
		sb.append(".page ul{ overflow:hidden;zoom:1; float:left;}");
		sb.append(".page li{display:block;float:left;margin-right:10px;}");
		sb.append(".page li span{background:#ea549e; text-align:center; display:block; width:21px;height:19px; font-weight:700; color:#fff;}");
		sb.append(".page li a {width:19px; display:block; text-align:center;height:17px;border:1px solid #ccc;}");
		sb.append(".page input{  vertical-align:middle; width:38px;height:18px; border:1px solid #ccc;}");
		sb.append(".page img { vertical-align:middle;}");
		sb.append("</style>");
		return sb.toString();
	}
	
	private boolean hasPrev(){
		return page>1;
	}
	private boolean hasNext(){
		return page<totalPage;
	}
	
	/**
	 * 对供应商一类的用户使用
	 * @return
	 */
	private String createFormPagination2(){
		StringBuffer sb=new StringBuffer();
		createAction();
		sb.append(createCss());
		sb.append("<div class=\"table01Footer\">");
		sb.append("<form action='");
		sb.append(url);
		sb.append("' method='post'>");
		sb.append(createParam(param));
		sb.append("<p class=\"table01Footer_l\">共<span class=\"footer_l_span\">");
		sb.append(totalResultSize);
		sb.append("</span>条记录");
		sb.append(other);
		sb.append("</p>");
		
		sb.append("	<div class=\"page\">");
		if(page>1){
			sb.append("	<i>");
			sb.append(createPage("第一页", 1));
			sb.append("</i>");
		}
		if(hasPrev()){
			sb.append("	<i>");
			sb.append(createPage("上一页", page-1));
			sb.append("</i>");
		}
		sb.append("<ul>");
		long startPage=1;
		long endPage=totalPage;
		if(totalPage>10){
			if(totalPage-page>5){
				startPage=page-5;
				if(startPage<1){
					startPage=1;
				}		
				endPage=startPage+9;
				if(endPage>totalPage){
					endPage=totalPage;
				}
			}else{
				endPage=totalPage;
				startPage=totalPage-9;
			}
		}
		for(long i=startPage;i<=endPage;i++){
			if(i==page){
				sb.append("<li><span>");
				sb.append(i);
				sb.append("</span></li>");
			}else{
				sb.append(createPage(i));
			}
		}
		sb.append("</ul>");
		if(hasNext()){
			sb.append("	<i>");
			sb.append(createPage("下一页", page+1));
			sb.append("</i>");
		}
		if(page<totalPage){
			sb.append("	<i>");
			sb.append(createPage("尾页", totalPage));
			sb.append("</i>");
		}
		//跳转到 <input type="text" /> 页 <a href="javascript:void(0)"><img src="http://pic.lvmama.com/img/ebooking/pageBtn.gif" /></a>
		sb.append("跳转到 <input type=\"text\" name='gogo'/> 页");
		sb.append("<a href=\"javascript:void(0)\" class='"+pageAName+"' total='"+totalPage+"' result='gogo'><img src=\"http://pic.lvmama.com/img/ebooking/pageBtn.gif\" /></a>");
		sb.append("</form>");
		sb.append("</div>");
		sb.append(createScript());
		return sb.toString();
	}
	
	private String createScript(){
		StringBuffer sb=new StringBuffer();
		//sb.append("<script type='text/javascript'>function getParent($div,tagName){var $p=$div.parent();var tmp=$p.get(0).tagName.toUpperCase();alert(tmp);if(tmp=='BODY'){return null;}if(tmp==tagName.toUpperCase()){return $p;}else{return getParent($p,tagName);}}");
		
		//sb.append("$(function(){function goPage(page){var $form=getParent($(this),'form');$form.find('input[name="+pageName+"]').val(page);$form.submit();}$('a."+pageAName+"').click(function(){var page=$(this).attr('page');var result=$(this).attr('gogo');if(typeof(result)=='undefined'&&result=='gogo'){var $form=getParent($(this),'form');var val=$form.find('input[name=gogo]').val();if($.trim(val)==''){return false;}page=parseInt(val);if(page>totalPage||page<1){return false;}}goPage(page);});});</script>");
		sb.append("<script type='text/javascript'>");		
		sb.append("$(function() {								");
		sb.append(" function getParent($div, tagName) {			");
		sb.append("	var $p = $div.parent();						");
		sb.append("	var tmp = $p.get(0).tagName.toUpperCase();	");
		sb.append("	if (tmp == 'BODY') {						");
		sb.append("		return null;							");
		sb.append("	}											");
		sb.append("	if (tmp == tagName.toUpperCase()) {			");
		sb.append("		return $p;								");
		sb.append("	} else {									");
		sb.append("		return getParent($p, tagName);			");
		sb.append("	}											");
		sb.append("}												");
		sb.append("	function goPage($this,page) {				");
		sb.append("		var $form = getParent($this, 'form');	");
		sb.append("		$form.find('input[name="+pageName+"]').val(page);");
		sb.append("		$form.submit();							");
		sb.append("	}											");
		sb.append("	$('a."+pageAName+"').click(function() {		");
		sb.append("		var page = $(this).attr('page');		");
		sb.append("		var result = $(this).attr('result');	");
		sb.append("		if (result != 'undefined' && result == 'gogo') {");
		sb.append("			var $form = getParent($(this), 'form');");
		sb.append("			var val = $form.find('input[name=gogo]').val();");
		sb.append("			if ($.trim(val) == '') {			");
		sb.append("				return false;					");
		sb.append("			}									");
		sb.append("			var reg = /^(\\d+)$/;				");
		sb.append("			if(!reg.test(val)){return false;}		");
		sb.append("			page = parseInt(val);				");
		sb.append("			var totalPage=parseInt($(this).attr('total'));");
		sb.append("			if (page > totalPage || page < 1) {	");
		sb.append("				return false;					");
		sb.append("			}									");
		sb.append("		}										");
		sb.append("		goPage($(this),page);					");
		sb.append("	});											");
		sb.append("});												");
		sb.append("</script>									");
		return sb.toString();								
	}
	
	private void createAction(){
		if(action == null){
			action = "?";
		}
		int pos=action.indexOf("?");
		
		url=this.action;
		if(pos!=-1){
			url=this.action.substring(0,pos);
			param=action.substring(pos+1);
		}
	}
	
	private String url;
	private String param;
	private String other;
	private String createFormPagination(){
		createAction();
		StringBuffer sb=new StringBuffer();
		sb.append("<style type='text/css'>span.PageSel,a.PageLink{margin:0 3px;}span.PageSel{font-weight: bold}</style>");
		sb.append("<form id='paginationForm' action='");
		sb.append(url);
		sb.append("' method='post'>");
		
		sb.append(createParam(param));	
		
		long prevPage = this.page - 1;
		long nextPage = this.page + 1;
		long startPage = 0;
		if (this.page % pageSize == 0) {
			startPage = this.page - (pageSize - 1);
		} else {
			startPage = this.page - this.page % pageSize + 1;
		}
		sb.append("<div id=\"lv_page\">");
		sb.append("<div class=\"Pages\">");
		long endPage2 = 0;
		if (prevPage < 1) {
			sb.append("<a href='#' title='上一页' class='PrevPage'>上一页</a>");
		} else {
			sb.append("<a href='#' title='上一页' class='PageLink PrevPage "+pageAName+"' page='"+prevPage+"'>上一页</a>");
		}
		if (this.page != 1){
			sb.append("<a href='#' title='到第一页' class='PageLink "+pageAName+"' page='1'>1</a>");
		}
		if (this.page >= 5)
			sb.append("<span class='PageMore'>...</span>");
		if (this.totalPage > this.page + 5) {
			endPage2 = this.page + 5;
		} else {
			endPage2 = this.totalPage;
		}
		for (long i = this.page -5; i <= endPage2; i++) {
			if (i > 0) {
				if (i == this.page) {
					sb.append("<span class='PageSel'>"+i+"</span>");
				} else {
					if (i != 1 && i != this.totalPage) {
						sb.append("<a href='#' class='PageLink "+pageAName+"'  page='"+i+"' title='第" + i + "页'>" + i
								+ "</a>");
					}
				}
			}
		}
		if (this.page + 3 < this.totalPage)
			sb.append("<span class=\"PageMore\">...</span>");
		if (this.page != this.totalPage)
			sb.append("<a  href='#' page='"+totalPage+"' class=\"PageLink "+pageAName+"\" title= '第" + this.totalPage
					+ "页'>" + this.totalPage + "</a>");

		if (nextPage > this.totalPage) {
			sb.append("<a href='#' title='下一页' class='NextPage'>下一页</a>");			
		} else {
			sb.append("<a href='#' title='下一页' class='NextPage PageLink "+pageAName+"' page='"+nextPage+"' >下一页</a>");
		}
		sb.append("</span><br/>");
		sb.append("</div>");
		sb.append("</div>");
		sb.append("</form>");
		sb.append(createScript());		
		return sb.toString();
	}	
	public String doStartTag(){
		try {
			initPage();
			str +="<div id='pages_' class='pages' style='"+this.cssStyle+"'> ";
			this.createTag(this.getMode());
			str +="</div>";
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return str ;
	}

	private String toPageWithSize(int size) {
		String url=this.toPage(1);
		String params = url.substring(url.lastIndexOf("?")+1);
		if(url.length() == params.length()) {
			url += "?pageSize=" + size;
			return url;
		}
		String psStr = "";
		for(String p : params.split("&")) {
			if(p.startsWith("pageSize=")) {
				psStr = p;
				break;
			}
		}
		if(!psStr.equals("")) {
			url = url.replace(psStr, "pageSize=" + size);
		} else {
			url += "&pageSize=" + size;
		}
		return url;
	}
	
	private String toPage(long page) {
		String url="";
		if("htm".equals(this.type)){
			url = this.action+""+page+".htm";
			return url;
		} 
		if("do".equals(this.type)){
			if(this.action != null){
				boolean containsPage = this.action.contains("&page=") || this.action.contains("?page=");
				boolean containsQuestionMark = this.action.contains("?");
				if(!containsQuestionMark)
					url = this.action + "?page=" + page;
				else{
					if(containsPage){
						url=this.action + page;
					}else{
						url=this.action + "&page=" + page;
					}
				}
			}
			return url;
		}
		if ("js".equals(this.type)) {
			if (null != this.action) {
				url = action.replaceAll("argPage", String.valueOf(page));
				return url;
			}
		}
		
		//default url
		url = this.action+page;
		
		return url;
	}
	
	public int getMode() {
		return mode;
	}

	public void setMode(int mode) {
		this.mode = mode;
	}

	public long getShowTimes() {
		return showTimes;
	}

	public void setShowTimes(long showTimes) {
		this.showTimes = showTimes;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public long getPageSize() {
		return pageSize;
	}

	public void setPageSize(long pageSize) {
		this.pageSize = pageSize;
	}

	public String getCssStyle() {
		return cssStyle;
	}

	public void setCssStyle(String cssStyle) {
		this.cssStyle = cssStyle;
	}
	
	public String getPageSizeParamName() {
		return currentPageParamName;
	}

	public void setPageSizeParamName(String pageSizeParamName) {
		this.currentPageParamName = pageSizeParamName;
	}

	
}
