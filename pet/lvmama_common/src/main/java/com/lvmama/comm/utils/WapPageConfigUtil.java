package com.lvmama.comm.utils;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

public class WapPageConfigUtil extends BodyTagSupport{
	
	private String url;
	private String totalPage;
	private String currentPage;
	
	public int doEndTag() throws JspException
	{
		if(url!=null&&!"".equals(url)){
			try {
				if(Integer.parseInt(totalPage)>1) {
					pageContext.getOut().write("<p>");
					if(currentPage.equals("1")){
						pageContext.getOut().write("<a href=\""+url+"page="+(Long.valueOf(currentPage)+1>=Long.valueOf(totalPage)?totalPage:Long.valueOf(currentPage)+1)+"\">下一页</a>");
					} else if(currentPage.equals(totalPage)) {
						pageContext.getOut().write("<a href=\""+url+"page="+(Long.valueOf(currentPage)-1==0?1:Long.valueOf(currentPage)-1)+"\">上一页</a>");
					}else{
						pageContext.getOut().write("<a href=\""+url+"page="+(Long.valueOf(currentPage)-1==0?1:Long.valueOf(currentPage)-1)+"\">上一页</a>&nbsp;&nbsp;");
						pageContext.getOut().write("<a href=\""+url+"page="+(Long.valueOf(currentPage)+1>=Long.valueOf(totalPage)?totalPage:Long.valueOf(currentPage)+1)+"\">下一页</a>");
					}
					pageContext.getOut().write("&nbsp;("+currentPage+"/"+totalPage+")");
					pageContext.getOut().write("</p>");
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		
		}
		return SKIP_BODY;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(String totalPage) {
		this.totalPage = totalPage;
	}

	public String getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(String currentPage) {
		this.currentPage = currentPage;
	}

}
