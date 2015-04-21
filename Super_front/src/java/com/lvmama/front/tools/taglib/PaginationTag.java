package com.lvmama.front.tools.taglib;


import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;


public class PaginationTag extends TagSupport {
	private int page;

	private int totalPage;
	private String action;
	private int mode;
	private int showTimes = 1;
	private String type;
	private int pageSize;
	private String cssStyle;
	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}


	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
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

	public void createTag(int type) throws JspException {
		type = this.getMode();
		try {
			int prevPage = this.page - 1;
			int nextPage = this.page + 1;
			int startPage = 0;
			if (this.page % 10 == 0) {
				startPage = this.page - 9;
			} else {
				startPage = this.page - this.page % 10 + 1;
			}
			JspWriter out = pageContext.getOut();
			switch (type) {
			case 0:
				out.print("<span class='number'>");
				if (prevPage < 1) {
					out.print("<span title='一页'>&#171;</span>");
					out.print("<span title='下页'>&#139;</span>");
				} else {

					out.print("<span title='到第一页'><a href='" + this.toPage(1)
							+ "'>&#171;</a></span>");

					out
							.print("<span title='上一页'><a href='"
									+ this.toPage(prevPage)
									+ "');'>&#139;</a></span>");
				}
				for (int i = 1; i <= this.totalPage; i++) {
					if (i > 0) {
						if (i == this.page) {

							out.print("<span title='第 " + i + "页 '>[" + i
									+ "]</span>");
						} else {

							out.print("<span title='第 " + i + "页'><a href='"
									+ this.toPage(i) + "'>[" + i
									+ "]</a></span>");
						}
					}
				}
				if (nextPage > this.totalPage) {
					out.print("<span title='下页'>&#155;</span>");
					out.print("<span title='最后一页'>&#187;</span>");
				} else {

					out.print("<span title='下一页'><a href='"
							+ this.toPage(nextPage) + "'>&#155;</a></span>");

					out.print("<span title='到最后一页'><a href='"
							+ this.toPage(this.totalPage)
							+ "');'>&#187;</a></span>");
				}
				out.print("</span><br/>");

				break;
			case 1: // 模式1 (10页缩略,首页,前页,后页,尾页)
				out.print("<span class='number'>");
				if (prevPage < 1) {
					out.print("<span title='一页'>&#171;</span>");
					out.print("<span title='下页'>&#139;</span>");
				} else {
					out.print("<span title='到第一页'><a href='" + this.toPage(1)
							+ "'>&#171;</a></span>");

					out
							.print("<span title='上一页'><a href='"
									+ this.toPage(prevPage)
									+ "');'>&#139;</a></span>");
				}

				if (startPage > 10)

					out.print("<span title='Prev 10 Pages'><a href='"
							+ this.toPage(startPage - 1) + "'>上一页</a></span>");
				for (int i = startPage; i < startPage + 10; i++) {
					if (i > this.totalPage)
						break;
					if (i == this.page) {

						out.print("<span title='第 " + i + "页'>[" + i
								+ "]</span>");
					} else {

						out.print("<span title='Page " + i + "'><a href='"
								+ this.toPage(i) + "'>[" + i + "]</a></span>");
					}
				}
				if (this.totalPage >= startPage + 10)
					out.print("<span title='Next 10 Pages'><a href='"
							+ this.toPage(startPage + 10) + "'>下一页</a></span>");
				if (nextPage > this.totalPage) {
					out.print("<span title='下页'>&#155;</span>");
					out.print("<span title='最后一页'>&#187;</span>");
				} else {

					out.print("<span title='下一页'><a href='"
							+ this.toPage(nextPage) + "'>&#155;</a></span>");

					out.print("<span title='到最后一页'><a href='"
							+ this.toPage(this.totalPage)
							+ "');'>&#187;</a></span>");
				}
				out.print("</span><br/>");
				break;
			case 2: // 模式2 (前后缩略,页数,首页,前页,后页,尾页)
				int endPage = 0;
				out.print("<span class='number'>");
				if (prevPage < 1) {
					out.print("<span title='一页'>&#171;</span>");
					out.print("<span title='下页'>&#139;</span>");
				} else {
					out.print("<span title='到第一页'><a href='" + this.toPage(1)
							+ "'>&#171;</a></span>");

					out
							.print("<span title='上一页'><a href='"
									+ this.toPage(prevPage)
									+ "');'>&#139;</a></span>");
				}
				if (this.page != 1)
					out.print("<span title='到第一页'><a href='" + this.toPage(1)
							+ "'>[1]</a></span>");

				if (this.page >= 5)
					out.print("<span>...</span>");
				if (this.totalPage > this.page + 2) {
					endPage = this.page + 2;
				} else {
					endPage = this.totalPage;
				}
				for (int i = this.page - 2; i <= endPage; i++) {
					if (i > 0) {
						if (i == this.page) {

							out.print("<span title='第 " + i + "页'>[" + i
									+ "]</span>");
						} else {
							if (i != 1 && i != this.totalPage) {
								out.print("<span title='Page " + i
										+ "'><a href='" + this.toPage(i)
										+ "'>[" + i + "]</a></span>");
							}
						}
					}
				}
				if (this.page + 3 < this.totalPage)
					out.print("<span>...</span>");
				if (this.page != this.totalPage)
					out.print("<span title= '第 " + this.totalPage
							+ "'><a href='" + this.toPage(this.totalPage)
							+ "');'>[" + this.totalPage + "]</a></span>");

				if (nextPage > this.totalPage) {
					out.print("<span title='下页'>&#155;</span>");
					out.print("<span title='最后一页'>&#187;</span>");
				} else {
					out.print("<span title='下一页'><a href='"
							+ this.toPage(nextPage) + "'>&#155;</a></span>");

					out.print("<span title='到最后一页'><a href='"
							+ this.toPage(this.totalPage)
							+ "');'>&#187;</a></span>");
				}
				out.print("</span><br/>");
				break;
			case 3: // 模式3 (10页缩进) (only IE)
				out.print("<style type=\"text/css\">.p-unsele-page {font-size:12px;border:1px solid #ccc;}"+"</style>");
				out.print("<table id =\"pag\" width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">");
				out.print("<tr>");
				
				if(this.page==1){
					out.print("<td width=\"20\" height=\"20\">");
				out.print("<button class=\"page_prew\"></button>");
				out.print("</td>");
				}else{
					out.print("<td width=\"20\" height=\"20\">");
				 out.print("<button class=\"page_prew\" onclick=\"window.location='"+toPage(prevPage)+"'"+"\"></button>");
				 out.print("</td>");
				}
				if(this.page>5&&this.page+5>=this.totalPage){
					out.print("<td width=\"20\" height=\"20\" align=\"center\"  class=\"p-unsele-page\">");
					out.print("<a class=\"user_vip_page\"  href="+toPage(1)+" title=\"第"+1+"页\">"+1+"</a>");
					out.print("</td>");
					out.print("<td width=\"20\" height=\"20\" align=\"center\"  class=\"p-unsele-page\">");
					out.print("...");
					out.print("</td>");
					if(this.page-5>=1){
						for(int i=this.page-5;i<=this.totalPage;i++){
							out.print("<td width=\"20\" height=\"20\" align=\"center\" class=\"p-unsele-page\">");
							if(this.page==i){
								out.print("<font color=\"red\">"+i+"</font>");
							}else{
								out.print("<a class=\"user_vip_page\"  href="+toPage(i)+""+" title=\"第"+i+"页\">"+i+"</a>");
							}
							out.print("</td>");
							
						}
						if(this.page+5==this.totalPage-1){
							out.print("<td width=\"20\" height=\"20\" align=\"center\"  class=\"p-unsele-page\">");
							out.print("<a class=\"user_vip_page\" href="+toPage(this.totalPage)+""+" title=\"第"+this.totalPage+"页\">"+this.totalPage+"</a>");
							out.print("</td>");
						}else if(this.page==this.totalPage){
							
						}else if(this.page+5>=this.totalPage){
							
						}else{
							out.print("<td width=\"20\" height=\"20\" align=\"center\"  class=\"p-unsele-page\">");
							out.print("...");
							out.print("</td>");
							out.print("<td width=\"20\" height=\"20\" align=\"center\"  class=\"p-unsele-page\">");
							out.print("<a class=\"user_vip_page\" href="+toPage(this.totalPage)+""+" title=\"第"+this.totalPage+"页\">"+this.totalPage+"</a>");
							out.print("</td>");
						}
					}else{
		
					
						if(this.page+10<=this.totalPage){

							if(this.page-5>=1){
								for(int i=this.page-5;i<=this.page+10;i++){
									out.print("<td width=\"20\" height=\"20\" align=\"center\"  class=\"p-unsele-page\">");
									if(i==this.page){
										out.print("<font color=\"red\">"+i+"</font>");
									}else{
									out.print("<a class=\"user_vip_page\" href="+toPage(i)+""+" title=\"第"+i+"页\">"+i+"</a>");
									}
									out.print("</td>");
									
								}
							}else{
							for(int i=1;i<= this.page+10;i++){
								out.print("<td width=\"20\" height=\"20\" align=\"center\"  class=\"p-unsele-page\">");
								if(i==this.page){
									out.print("<font color=\"red\">"+i+"</font>");
								}else{
								out.print("<a class=\"user_vip_page\" href="+toPage(i)+""+" title=\"第"+i+"页\">"+i+"</a>");
								}
								out.print("</td>");
								
							}
							}
							if(this.page+10!=this.totalPage){
							out.print("<td width=\"20\" height=\"20\" align=\"center\"  class=\"p-unsele-page\">");
							out.print("...");
							out.print("</td>");
							out.print("<td width=\"20\" height=\"20\" align=\"center\"  class=\"p-unsele-page\">");
							out.print("<a class=\"user_vip_page\" href="+toPage(this.totalPage)+""+" title=\"第"+this.totalPage+"页\">"+this.totalPage+"</a>");
							out.print("</td>");
							}
						}else{
						for(int i=1;i<=this.totalPage;i++){
							
							if(this.page==i){
								out.print("<td width=\"20\" height=\"20\" align=\"center\"  class=\"p-unsele-page\">");
								out.print("<font color=\"red\">"+i+"</font>");
								out.print("</td>");
							}else{
								out.print("<td width=\"20\" height=\"20\" align=\"center\"  class=\"p-unsele-page\">");
								out.print("<a class=\"user_vip_page\" href="+toPage(i)+""+" title=\"第"+i+"页\">"+i+"</a>");
								out.print("</td>");
							}
							
							
						}
						
						}
					}
				
				}else{
					
					if(this.page-5>=3){
						out.print("<td width=\"20\" height=\"20\" align=\"center\"  class=\"p-unsele-page\">");
						out.print("<a class=\"user_vip_page\"  href="+toPage(1)+" title=\"第"+1+"页\">"+1+"</a>");
						out.print("</td>");
						out.print("<td width=\"20\" height=\"20\" align=\"center\"  class=\"p-unsele-page\">");
						out.print("...");
						out.print("</td>");
					for(int i=this.page-5;i<=this.page+5;i++){
						out.print("<td width=\"20\" class=\"p-unsele-page\" height=\"20\" align=\"center\" >");
						if(this.page==i){
							out.print("<font color=\"red\">"+i+"</font>");
						}else{
							out.print("<a class=\"user_vip_page\" href="+toPage(i)+" title=\"第"+i+"页\">"+i+"</a>");
						}
						out.print("</td>");
						
						
					}
					if(this.page+5==this.totalPage-1){
						out.print("<td width=\"20\" height=\"20\" align=\"center\"  class=\"p-unsele-page\">");
						out.print("<a class=\"user_vip_page\" href="+toPage(this.totalPage)+""+" title=\"第"+this.totalPage+"页\">"+this.totalPage+"</a>");
						out.print("</td>");
					}else if(this.page==this.totalPage){
						
					}else if(this.page+5>=this.totalPage){
						
					}else{
						out.print("<td width=\"20\" height=\"20\" align=\"center\"  class=\"p-unsele-page\">");
						out.print("...");
						out.print("</td>");
						out.print("<td width=\"20\" height=\"20\" align=\"center\"  class=\"p-unsele-page\">");
						out.print("<a class=\"user_vip_page\" href="+toPage(this.totalPage)+""+" title=\"第"+this.totalPage+"页\">"+this.totalPage+"</a>");
						out.print("</td>");
					}
					}else{
						if(this.page+10<=this.totalPage){
							if(this.page-5>=1){
								for(int i=this.page-5;i<=this.page+10;i++){
									out.print("<td width=\"20\" height=\"20\" align=\"center\"  class=\"p-unsele-page\">");
									if(i==this.page){
										out.print("<font color=\"red\">"+i+"</font>");
									}else{
									out.print("<a class=\"user_vip_page\" href="+toPage(i)+""+" title=\"第"+i+"页\">"+i+"</a>");
									}
									out.print("</td>");
									
								}
							}else{
								
							for(int i=1;i<=this.page+10;i++){
								out.print("<td width=\"20\" height=\"20\" align=\"center\"  class=\"p-unsele-page\">");
								if(i==this.page){
									out.print("<font color=\"red\">"+i+"</font>");
								}else{
								out.print("<a class=\"user_vip_page\" href="+toPage(i)+""+" title=\"第"+i+"页\">"+i+"</a>");
								}
								out.print("</td>");
								
							}
							}
							if(this.page+10!=this.totalPage){
							out.print("<td width=\"20\" height=\"20\" align=\"center\"  class=\"p-unsele-page\">");
							out.print("...");
							out.print("</td>");
							out.print("<td width=\"20\" height=\"20\" align=\"center\"  class=\"p-unsele-page\">");
							out.print("<a class=\"user_vip_page\" href="+toPage(this.totalPage)+""+" title=\"第"+this.totalPage+"页\">"+this.totalPage+"</a>");
							out.print("</td>");
							}
						}else{
						for(int i=1;i<=this.totalPage;i++){
							
							if(this.page==i){
								out.print("<td width=\"20\" class=\"p-unsele-page\" height=\"20\" align=\"center\" >");
								out.print("<font color=\"red\">"+i+"</font>");
								out.print("</td>");
							}
							else{
								out.print("<td width=\"20\" class=\"p-unsele-page\" height=\"20\" align=\"center\" >");
								out.print("<a class=\"user_vip_page\" href="+toPage(i)+""+" title=\"第"+i+"页\">"+i+"</a>");
								out.print("</td>");
							}
							
							
						}
					}
					}
				}
				
				
				if(this.page!=this.totalPage){
					out.print("<td width=\"20\" height=\"20\">");
					out.print("<button class=\"page_next\" onclick=\"window.location='"+toPage(nextPage)+""+"'\"></button>");
					out.print("</td>");
					out.print("<td width=\"40\" height=\"20\">到第</td>");
					out.print("<td width=\"20\" height=\"20\"><input id=\"page\" name=\"textfield2\" type=\"text\" class=\"writein_field\" size=\"2\" /></td>");
					out.print("<td width=\"20\" height=\"20\">页</td>");
					out.print(" <td  height=\"20\"><button class=\"page_fir\" align=\"left\" onclick=\"toPage('"+this.action+"')\"></button></td>");
				}else{
					out.print("<td width=\"20\" height=\"20\">");
					out.print("<button class=\"page_next\"></button>");
					out.print("</td>");
					out.print("<td width=\"40\" height=\"20\">到第</td>");
					out.print("<td width=\"20\" height=\"20\"><input id=\"page\" name=\"textfield2\" type=\"text\" class=\"writein_field\" size=\"2\" /></td>");
					out.print("<td width=\"20\" height=\"20\">页</td>");
					out.print(" <td  height=\"20\" align=\"left\"><button class=\"page_fir\" onclick=\"toPage('"+this.action+"')\"></button></td>");

				}
				
				out.print("</tr>");
				out.print("</table>");
				break;
			case 4: // 模式3 (箭头样式,首页,前页,后页,尾页) (only IE)
				out.print("<span class='number'>");
				if (prevPage < 1) {
					out.print("<span title='一页'><strong><<</strong></span>");
					out.print("<span title='下页'><strong><</strong> </span>");
				} else {
					out.print("<span title='到第一页'><a href='" + this.toPage(1)
							+ "'><strong><<</strong></a></span>");
					out.print("<span title='下一页'><a href='"
							+ this.toPage(prevPage)
							+ "'><font color='red'> < </font></a></span>");
				}
				if (nextPage > this.totalPage) {
					out.print("<span title='下页'><strong>></strong></span>");
					out.print("<span title='最后一页'><strong>>></strong></span>");
				} else {

					out.print("<span title='下一页'><a href='"
							+ this.toPage(nextPage)
							+ "'><font color='red'> > </font></a></span>");

					out.print("<span title='最后一页'><a href='"
							+ this.toPage(this.totalPage)
							+ "'><strong>>></strong></a></span>");
				}
				out.print("</span><br/>");
				break;

			case 5: // 模式4 (下拉框)
				if (this.totalPage < 1) {

					out.print("<select name='toPage' disabled>");
					out.print("<option value='0'>No Pages</option>");

				} else {
					String chkSelect;

					out
							.print("<select name='toPage' onchange='location=this.options[this.selectedIndex].value'>");
					for (int i = 1; i <= this.totalPage; i++) {
						if (this.page == i)
							chkSelect = " selected='selected'";
						else
							chkSelect = "";
						out.print("<option value='" + this.toPage(i) + "'"
								+ chkSelect + ">到:  " + i + " /  "
								+ this.totalPage + "页</option>");
					}
				}
				out.print("</select>");
				break;
			case 6: // 模式5 (输入框)
				out.print("<span class='input'>");
				if (this.totalPage < 1) {
					out
							.print("<input type='text' name='toPage' value='No Pages' class='itext' disabled='disabled'>");
					out
							.print("<input type='button' name='go' value='GO' class='ibutton' disabled='disabled'></option>");
				} else {

					out
							.print("<input type='text' value='输入页码:' class='ititle' readonly='readonly'>");

					out
							.print("<input type='text' id='pageInput"
									+ this.showTimes
									+ " + ' value='"
									+ this.page
									+ "' class='itext' title='Input page'  onfocus='this.select()'>");

					out.print("<input type='text' value=' / " + this.totalPage
							+ "' class='icount' readonly='readonly'>");

					out
							.print("<input type='button' name='go' value='GO' class='ibutton' onclick='location='"
									+ this.toPage(1) + "'></input>");
				}
				out.print("</span>");
				break;
			case 7:
				out.print("<span class='number'>");
				if (this.totalPage <= 1) {

					out.print("<span title=\"一页\">\u7b2c\u4e00\u9875</span>");
				} else {
					if (this.page == 1) {

						out
								.print("<span title=\"\u7b2c\u4e00\u9875\">\u7b2c\u4e00\u9875</span>");

						out.print("<span>&nbsp;&nbsp;|&nbsp;&nbsp;" + this.page
								+ "&nbsp;&nbsp;<a href='"
								+ this.toPage(this.page + 1) + "'>"
								+ (this.page + 1)
								+ "</a>&nbsp;&nbsp;|&nbsp;&nbsp;</span>");

						out.print("<span title=\"下一页\"><a href=\'"
								+ this.toPage(nextPage)
								+ "'>\u4e0b\u4e00\u9875</a></span>");
						String chkSelect;

						out
								.print("<select name=\"toPage\" onchange='location=this.options[this.selectedIndex].value'>");
						for (int i = 1; i <= this.totalPage; i++) {
							if (this.page == i) {
								chkSelect = " selected=\"selected\"";

							} else {
								chkSelect = "";
							}
							out.print("<option value='" + this.toPage(i) + "'"
									+ chkSelect + ">到:  " + i + " /  "
									+ this.totalPage + "页</option>");
						}

						out.print("</select>");
					} else {
						if (this.page > 1 && this.page < this.totalPage) {
							out.print("<span title=\"上一页\"><a href='"
									+ this.toPage(prevPage)
									+ "'>\u4e0a\u4e00\u9875</a></span>");
							out
									.print("<span>&nbsp;&nbsp;|&nbsp;&nbsp;<a href='"
											+ this.toPage(this.page - 1)
											+ "'>"
											+ (this.page - 1)
											+ "</a>&nbsp;&nbsp;"
											+ this.page
											+ "&nbsp;&nbsp;<a href='"
											+ this.toPage(this.page + 1)
											+ "'>"
											+ (this.page + 1)
											+ "</a>&nbsp;&nbsp;|&nbsp;&nbsp;</span>");

							out
									.print("<span title=\"\u4e0b\u4e00\u9875\"><a href='"
											+ this.toPage(nextPage)
											+ "'>\u4e0b\u4e00\u9875</a></span>");
							String chkSelect;

							out
									.print("<select name=\"toPage\" onchange='location=this.options[this.selectedIndex].value'>");
							for (int i = 1; i <= this.totalPage; i++) {
								if (this.page == i) {
									chkSelect = " selected=\"selected\"";
								} else {
									chkSelect = "";
								}

								out.print("<option value='" + this.toPage(i)
										+ "'" + chkSelect + ">到  " + i + " /  "
										+ this.totalPage + "页</option>");

							}

							out.print("</select>");
						} else {
							if (this.page == this.totalPage) {

								out
										.print("<span title=\"\u4e0a\u4e00\u9875\"><a href='"
												+ this.toPage(prevPage)
												+ "'>\u4e0a\u4e00\u9875</a></span>");

								out
										.print("<span>&nbsp;&nbsp;|&nbsp;&nbsp;<a href='"
												+ this.toPage(this.page - 1)
												+ "'>"
												+ (this.page - 1)
												+ "</a>&nbsp;&nbsp;"
												+ this.page
												+ "&nbsp;&nbsp;|&nbsp;&nbsp;</span>");

								out
										.print("<span title=\"\">\u6700\u540e\u4e00\u9875</span>");
								String chkSelect;

								out
										.print("<select name=\"toPage\" onchange='location=this.options[this.selectedIndex].value'>");
								for (int i = 1; i <= this.totalPage; i++) {
									if (this.page == i) {
										chkSelect = " selected=\"selected\"";
									} else {
										chkSelect = "";
									}

									out.print("<option value='"
											+ this.toPage(i) + "'" + chkSelect
											+ ">到  " + i + " /  "
											+ this.totalPage + "页</option>");
								}

								out.print("</select>");
							}
						}
					}
				}
				if (prevPage < 1) {

					out.print("<span title=\"一页\">\u7b2c\u4e00\u9875</span>");
				}
				break;
			case 8:
				if (startPage > 10) {
					out.print(" <span id='pages' title=''><a href='"
							+ this.toPage(startPage - 1) + "'>上一页</a></span>");
				}
				for (int i = startPage; i < startPage + 10; i++) {
					if (i > this.totalPage)
						break;
					if (i == this.page) {
						out.print("<span id='currentPage' title='Page  " + i
								+ "'>" + i + "</span>");
					} else {
						out.print("<span id='test' title='Page ' " + i
								+ "><a href='" + this.toPage(i) + "'>" + i
								+ "</a></span>");

					}

				}
				if (this.totalPage >= startPage + 10) {
					out
							.print("<span id='pages' title='Next 10 Pages'><a href='"
									+ this.toPage(startPage + 10)
									+ "')'>下一页</a></span>");
					out.print("</span><br/>");

				}

				break;
			case 9: // 模式2 (前后缩略,页数,首页,前页,后页,尾页)
				int endPage1 = 0;
				out.print("<span class='number'>");
				if (prevPage < 1) {
					out.print("<span id=\"last\" title='第一页'>第一页</span>");
					out.print("<span id=\"last\" title='上一页'>上一页</span>");
				} else {
					out.print("<span id=\"pages\"  title='到第一页'><a href='" + this.toPage(1)
							+ "'>首页</a></span>");

					out
							.print("<span id=\"pages\" title='上一页'><a href='"
									+ this.toPage(prevPage)
									+ "');'>上一页</a></span>");
				}
				if (this.page != 1)
					out.print("<span id=\"test\" title='到第一页'><a href='" + this.toPage(1)
							+ "'>1</a></span>");

				if (this.page >= 5)
					out.print("<span id=\"test\">......</span>");
				if (this.totalPage > this.page + 2) {
					endPage1 = this.page + 2;
				} else {
					endPage1 = this.totalPage;
				}
				for (int i = this.page - 2; i <= endPage1; i++) {
					if (i > 0) {
						if (i == this.page) {

							out.print("<span id=\"currentPage\" title='第 " + i + "页'>" + i
									+ "</span>");
						} else {
							if (i != 1 && i != this.totalPage) {
								out.print("<span id=\"test\" title='第 " + i
										+ "页'><a href='" + this.toPage(i)
										+ "'>" + i + "</a></span>");
							}
						}
					}
				}
				if (this.page + 3 < this.totalPage)
					out.print("<span id=\"test\">......</span>");
				if (this.page != this.totalPage)
					out.print("<span id=\"test\" title= '第 " + this.totalPage
							+ "页'><a href='" + this.toPage(this.totalPage)
							+ "');'>" + this.totalPage + "</a></span>");

				if (nextPage > this.totalPage) {
					out.print("<span id=\"last\" title='下页'>下页</span>");
					out.print("<span id=\"last\"  title='最后一页'>最后一页</span>");
				} else {
					out.print("<span id=\"pages\"  title='下一页'><a href='"
							+ this.toPage(nextPage) + "'>下一页</a></span>");

					out.print("<span id=\"toLast\" title='最后一页'><a href='"
							+ this.toPage(this.totalPage)
							+ "');'>最后一页</a></span>");
				}
				out.print("</span><br/>");
				break;
			case 10:
				out.print("<div id=\"lv_page\">");
				out.print("<div class=\"Pages\">");
				int endPage2 = 0;
				if (prevPage < 1) {
					if(this.page!=1){
					out.print("<a href=\"#\" title=\"上一页\" class=\"PrevPage\" >上一页</a>");
					}
				} else {
					out.print("<a href=\""+ this.toPage(prevPage)
							+ "\" title=\"上一页\" class=\"PrevPage\" >上一页</a>");
				}
				if (this.page != 1)
				out.print("<a href=\""+ this.toPage(1)
						+ "\" title=\"到第一页\" class=\"PageLink\" >1</a>");

				if (this.page >= 5)
					out.print("<span class=\"PageMore\">...</span>");
				if (this.totalPage > this.page + 5) {
					endPage2 = this.page + 5;
				} else {
					endPage2 = this.totalPage;
				}
				for (int i = this.page -5; i <= endPage2; i++) {
					if (i > 0) {
						if (i == this.page) {

							out.print("<span class=\"pagesel\">"+i+"</span>");
						} else {
							if (i != 1 && i != this.totalPage) {
								out.print("<a href=\""+this.toPage(i)+"\" class=\"PageLink\" title='第 " + i + "页'>" + i
										+ "</a>");
							}
						}
					}
				}
				if (this.page + 3 < this.totalPage)
					out.print("<span class=\"PageMore\">...</span>");
				if (this.page != this.totalPage)
					out.print("<a  href='" + this.toPage(this.totalPage)
							+ "' class=\"PageLink\" title= '第 " + this.totalPage
							+ "'>" + this.totalPage + "</a>");

				if (nextPage >this.totalPage) {

					
				} else {
					out.print("<a class=\"PageLink\" href='"
							+ this.toPage(nextPage)
							+ "' title=\"下一页\" class=\"NextPage\" >下一页</a>");
					
				}
				out.print("</span><br/>");

				
				
				
				
				out.print("</div>");
				out.print("</div>");
				break;
				
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	public int doStartTag() throws JspException {
		try {
			initPage();
			JspWriter out = pageContext.getOut();
			out.print("<div id='pages_' class='pages' style='"+this.cssStyle+"'> ");
			this.createTag(this.getMode());
			out.print("</div>");
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return super.doStartTag();
	}

	private String toPage(int page) throws Exception {
		String url="";
		if("htm".equals(this.type)){
			url = this.action+""+page+".htm";
		} else if("do".equals(this.type)){
			url = this.action+"page="+page;
		} else {
			url = this.action+page;//urlrewriter 重写，结尾增加".html"
		}
		return url;
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
