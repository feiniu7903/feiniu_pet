package com.lvmama.back.utils;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

public class BackPagTag extends BodyTagSupport{
	private String actionUrl;
	public int doEndTag() throws JspException
	{

		try {
			if(actionUrl!=null&&!"".equals(actionUrl)){
				int lastIndexNumber = actionUrl.lastIndexOf("?");
				String action=actionUrl;
				if(lastIndexNumber > -1){
					action=actionUrl.substring(0,lastIndexNumber);
				}
				StringBuffer strBuf=new StringBuffer();
				strBuf.append("<form  id=\"pgForm\" action=\""+action+"\" method=\"post\">");
				if(lastIndexNumber > -1){
					String paramters=actionUrl.substring(actionUrl.lastIndexOf("?")+1,actionUrl.length());
					
					String[] map=new String[2];
					String[] par=paramters.split("&");
					List<String[]> list=new ArrayList<String[]>();
					for(int i=0;i<par.length;i++){
						map=par[i].split("=");
						list.add(map);
					}

					if(lastIndexNumber > -1){
						for(int j=0;j<list.size();j++){
							String[] mapTemp=list.get(j);
							if(mapTemp[0].equals("page")){
									
							}else{
								if(mapTemp.length==2){
									strBuf.append("<input type=\"text\"  style=\"display: none;\" name=\""+mapTemp[0]+"\" value=\""+mapTemp[1]+"\"/>");	
								}else{
									strBuf.append("<input type=\"text\"  style=\"display: none;\" name=\""+mapTemp[0]+"\" value=\"\"/>");
								}
							}
						}
					}
				}
				
				strBuf.append("<input type=\"text\"  style=\"display: none;\"  id=\"fpage\" name=\"page\" value=\"\"/>");
				strBuf.append("<input type=\"text\"  style=\"display: none;\" id=\"perPageRecord\" name=\"perPageRecord\" value=\"\"/>");
				
				strBuf.append("</form>");
				pageContext.getOut().print(strBuf.toString());
			}
			
		} catch (Exception e) {
			System.out.println("BackPagTag.doEndTag actionUrl:"+actionUrl);
			e.printStackTrace();
		}
		return SKIP_BODY;
	}
	public String getActionUrl() {
		return actionUrl;
	}
	public void setActionUrl(String actionUrl) {
		this.actionUrl = actionUrl;
	}

}
