package com.lvmama.search.util;

import org.apache.commons.lang3.StringUtils;


/**
 * 分页控件
 * 
 * @author taiqichao
 * 
 */
public class Pagination {
	private int page;
	private int totalPage;
	private String action;
	private int mode;
	private int showTimes = 1;
	private String type;
	private int pageSize;
	private int totalResultSize = 0;
	private int[] pageSizeList = {10,20,50,100,200};
	private String cssStyle;
	String str = "";
	private String currentPageParamName;
	
	public Pagination() {
		super();
	}

	public Pagination(int pageSize, int totalPage, String url, int page) {
		this.pageSize = pageSize;
		this.totalPage = totalPage;
		this.action = url;
		this.page = page;
		this.mode = 10;
	}

	public static String pagination(int pageSize,int totalPage,String url,int page){
		Pagination pagination = new Pagination();
		pagination.setPageSize(pageSize);
		pagination.setTotalPage(totalPage);
		pagination.setAction(url);
		pagination.setPage(page);
		pagination.setMode(10);
		return pagination.doStartTag();
	}
	
	public static String pagination(PageConfig<?> page,int mode) {
		if (page != null && page.getTotalPageNum() > 0) {
			Pagination pagination = new Pagination();
			pagination.setPageSize(page.getPageSize());
			pagination.setTotalPage(page.getTotalPageNum());
			pagination.setAction(page.getUrl());
			pagination.setPage(page.getCurrentPage());
			pagination.setMode(mode);
			return pagination.doStartTag();
		}
		return "";
	}
	public static String pagination(PageConfig<?> page) {
		return pagination(page,0);
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
				pageSize=1;
			}
			if (this.page % pageSize == 0) {
				startPage = this.page - (pageSize - 1);
			} else {
				startPage = this.page - this.page % pageSize + 1;
			}
			switch (type) {
			case 0:
				long endPage2 = 0;
				if (prevPage >= 1) {
					str += "<a href=\"" + this.toPage(prevPage) + "\" title=\"上一页\" class=\"PrevPage\" rel=\"nofollow\">上一页</a>";
				}
				if (this.page != 1)
				str +="<a href=\""+ this.toPage(1)
						+ "\" title=\"到第一页\" class=\"PageLink\"  rel=\"nofollow\">1</a>";

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
								str +="<a href=\""+this.toPage(i)+"\" class=\"PageLink\" title='第 " + i + " 页'  rel=\"nofollow\">" + i
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
							+ " 页'  rel=\"nofollow\">" + this.totalPage + "</a>";

				if (nextPage <= this.totalPage) {
					str += "<a href='" + this.toPage(nextPage) + " ' title=\"下一页\" class=\"NextPage PageLink PageLink_page\"  rel=\"nofollow\">下一页</a>";
				}
				str +="</span>";
				break;
			case 1:
				str += "<span>"+this.page+"/"+this.totalPage+"</span>";
				long endPage11 = 0;
				if (prevPage < 1) {
					str +=" <a class=\"page-prev\" href=\"#list\" title=\"上一页\" rel=\"nofollow\">上一页<span class=\"page-arrow page-arrow-left\"></span></a>";
				} else {
					str +=" <a class=\"page-prev\" href=\""+this.toPage(prevPage)+"\" title=\"上一页\" rel=\"nofollow\">上一页<span class=\"page-arrow page-arrow-left\"></span></a>";
				}
				if (nextPage > this.totalPage) {
					str +=" <a class=\"page-next\" href=\"#\" title=\"下一页\" rel=\"nofollow\">下一页<span class=\"page-arrow page-arrow-right\"></span></a>";
				} else {
					str +=" <a class=\"page-next\" href=\""+this.toPage(nextPage)+"\" title=\"下一页\" rel=\"nofollow\">下一页<span class=\"page-arrow page-arrow-right\"></span></a>";
				}
				break;
			case 12:
				str=createFormPagination();
				break;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}
	
	
	
	
	public String doStartTag() {
		try {
			initPage();
//			str += "<div id='pages_' class='pages' style='" + this.cssStyle
//					+ "'> ";
			this.createTag(this.getMode());
//			str += "</div>";
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return str;
	}

	private String toPage(long page) {
		String url = "";
		if ("htm".equals(this.type)) {
			url = this.action + "" + page + ".htm";
		} else if ("do".equals(this.type)) {
			url = this.action + "page=" + page;
		} else {
			url = this.action.replace("{p}", String.valueOf(page))+".html#list";// urlrewriter 重写，结尾增加".html"
		}
		return url;
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
	
	
	private String createFormPagination(){
		StringBuffer sb=new StringBuffer();
		if(action == null){
			action = "?";
		}
		int pos=action.indexOf("?");
		
		String url=this.action;
		String param="";
		if(pos!=-1){
			url=this.action.substring(0,pos);
			param=action.substring(pos+1);
		}
		sb.append("<style type='text/css'>span.PageSel,a.PageLink{margin:0 3px;}span.PageSel{font-weight: bold}</style>");
		sb.append("<form id='paginationForm' action='");
		sb.append(url);
		sb.append("' method='post'>");
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
		String pageName = "page";
		String pageAName = "PageLink_page";
		if(currentPageParamName==null||"page".equals(this.currentPageParamName)){
			sb.append("<input type='hidden' name='"+pageName+"' value='");
		}else {
			sb.append("<input type='hidden' name='"+this.currentPageParamName+"' value='");
			pageName = this.currentPageParamName;
			pageAName = currentPageParamName.replaceAll("\\.", "_");
		}
		sb.append(page);
		sb.append("'/>");
		long prevPage = this.page - 1;
		long nextPage = this.page + 1;
		/*	long startPage = 0;
		if (this.page % pageSize == 0) {
			startPage = this.page - (pageSize - 1);
		} else {
			startPage = this.page - this.page % pageSize + 1;
		}*/
		sb.append("<div id=\"lv_page\">");
		sb.append("<div class=\"Pages\">");
		long endPage2 = 0;
		if (prevPage < 1) {
			sb.append("<a href='javascript:void(0)' title='上一页' class='PrevPage'>上一页</a>");
		} else {
			sb.append("<a href='javascript:void(0)' title='上一页' class='PageLink PrevPage page "+pageAName+"' page='"+prevPage+"'>上一页</a>");
		}
		if (this.page != 1){
			sb.append("<a href='javascript:void(0)' title='到第一页' class='PageLink "+pageAName+"' page='1'>1</a>");
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
						sb.append("<a href='javascript:void(0)' class='PageLink "+pageAName+"'  page='"+i+"' title='第" + i + "页'>" + i
								+ "</a>");
					}
				}
			}
		}
		if (this.page + 3 < this.totalPage)
			sb.append("<span class=\"PageMore\">...</span>");
		if (this.page != this.totalPage)
			sb.append("<a  href='javascript:void(0)' page='"+totalPage+"' class=\"PageLink "+pageAName+"\" title= '第" + this.totalPage
					+ "页'>" + this.totalPage + "</a>");

		if (nextPage > this.totalPage) {
			sb.append("<a href='javascript:void(0)' title='下一页' class='NextPage'>下一页</a>");			
		} else {
			sb.append("<a href='javascript:void(0)' title='下一页' class='NextPage PageLink "+pageAName+"' page='"+nextPage+"' >下一页</a>");
		}
		sb.append("</span><br/>");
		sb.append("</div>");
		sb.append("</div>");
		sb.append("</form>");
		sb.append("<script type='text/javascript'>function getParent($div,tagName){var $p=$div.parent();var tmp=$p.get(0).tagName.toUpperCase();if(tmp=='BODY'){return null;}if(tmp==tagName.toUpperCase()){return $p;}else{return getParent($p,tagName);}}");
		sb.append("$(function(){$('a."+pageAName+"').click(function(){var page=$(this).attr('page');var $form=getParent($(this),'form');$form.find('input[name="+pageName+"]').val(page);$form.submit();});});</script>");		
		return sb.toString();
	}	

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}
	
	public int getMode() {
		return mode;
	}

	public void setMode(int mode) {
		this.mode = mode;
	}

	public int getShowTimes() {
		return showTimes;
	}

	public void setShowTimes(int showTimes) {
		this.showTimes = showTimes;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public String getCssStyle() {
		return cssStyle;
	}

	public void setCssStyle(String cssStyle) {
		this.cssStyle = cssStyle;
	}
}
