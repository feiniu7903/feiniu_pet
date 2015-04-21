<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="l" uri="/tld/lvmama-tags.tld"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
 	<title>重点首页seo管理</title>
 	<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
	<link rel="stylesheet" type="text/css" href="<%=basePath%>css/ui-common.css"></link>
	<link rel="stylesheet" type="text/css" href="<%=basePath%>css/ui-components.css"></link>
	<link rel="stylesheet" type="text/css" href="<%=basePath%>css/panel-content.css"></link>
	<s:include value="/WEB-INF/pages/pub/jquery.jsp" />
	<s:include value="/WEB-INF/pages/pub/suggest.jsp" />
	<script type="text/javascript" src="<%=basePath%>/js/base/log.js"></script>
    <script type="text/javascript" src="<%=basePath %>/js/place/houtai.js"></script>
    <script type="text/javascript">
    var basePath='<%=basePath %>';
    	function editSeoIndexPage(seoIndexPageId){
    		
    		var param = {'seoIndexPage.seoIndexPageId':seoIndexPageId}
    		$.ajax({type:"POST", url:basePath+"/seo/index!getSeoIndexPageById.do", data:param, dataType:"json", success:function (seoIndexPage) {
	    			if(seoIndexPage!=""){
	    				$("#ipt_seoIndexPageId").val(seoIndexPageId);
		    			$("#ipt_pageName").val(seoIndexPage[0].pageName);
		    			$("#ipt_seoTitle").val(seoIndexPage[0].seoTitle);
		    			$("#ipt_seoKeyword").val(seoIndexPage[0].seoKeyword);
		    			$("#ipt_seoDescription").val(seoIndexPage[0].seoDescription);
		    			var seoContent=$("#seoContent_hidden_"+seoIndexPageId+"").val();
		    			$("#ipt_seoContent").val(seoIndexPage[0].seoContent);

		        		$("#updateSeoIndexPage").dialog({
		            		modal:true,
		            		title:"编辑",
		            		width:750,
		            		height:600
		                });
	    			}
				},error: function(){
				    alert("提交失败");
				}
    		});
		}
    	
    	function updateIndexPageSeo(){
    		var seoIndexPageId=$("#ipt_seoIndexPageId").val();
			var pageName=$("#ipt_pageName").val();
			var seoTitle=$("#ipt_seoTitle").val();
			if(seoTitle!="" && seoTitle.length>200){
				alert("seoTitle过长，请合理设置！(按seo标准应该少于200个字符)");
				return;
			}
			var seoKeyword=$("#ipt_seoKeyword").val();
			if(seoKeyword!="" && seoKeyword.length>200){
				alert("seoKeyword过长，请合理设置！");
				return;
			}
			var seoDescription=$("#ipt_seoDescription").val();
			if(seoDescription!="" && seoDescription.length>500){
				alert("seoDescription过长，请合理设置！");
				return;
			}
			var seoContent=$("#ipt_seoContent").val();
			if(seoContent!="" && seoContent.length>50000){
				alert("seoContent过长，请合理设置！");
				return;
			}
	    	if(seoIndexPageId!=0&&seoIndexPageId!=""){
	    		if(seoTitle==""){
	    			alert("请填写seoTitle");
	    			return;
	    		}
		    	var param = {'seoIndexPage.seoIndexPageId':seoIndexPageId,'seoIndexPage.pageName':pageName,'seoIndexPage.seoTitle':seoTitle,'seoIndexPage.seoKeyword':seoKeyword,'seoIndexPage.seoDescription':seoDescription,'seoIndexPage.seoContent':seoContent}
		    		$.ajax({type:"POST", url:basePath+"/seo/index!updateIndexPageSeo.do", data:param, dataType:"json", success:function (json) {
			    			if(json.flag){
								alert("修改成功");
								window.location.reload(true);
							}else{	
								alert("修改失败");
							}
						},error: function(){
						    alert("提交失败");
						}
		    		});
			} else{
				alert("请选择子类型");
			}
    	}
    	function createFriendLinkFile(){
			var id = $("#ipt_seoIndexPageId").val();
			if(id!=""){
				var url = basePath+"/seo/single/index/createFriendLinkFile.do";
				$.ajax({type:"POST", url:url, data:{'seoIndexPage.seoIndexPageId':id}, dataType:"json", success:function (data) {
					if(data.flag){
						alert("创建成功");
					}else{	
						alert("创建失败");
					}
				}}); 
			}else{
				alert("请选择你要修改友情连接");
			}
		}
		function synFriendLinkFile(){
			var id = $("#ipt_seoIndexPageId").val();
			if(id!=""){
				var url = basePath+"/seo/single/index/synFriendLinkFile.do";
				$.ajax({type:"POST", url:url, data:{'seoIndexPage.seoIndexPageId':id}, dataType:"json", success:function (data) {
					if(data.flag){
						alert("同步成功");
					}else{	
						alert("同步失败");
					}
				}}); 
			}else{
				alert("请选择你要修改友情连接");
			}
		}
		
		function closeUpdateSeoIndexPage()
		{
			$("#updateSeoIndexPage").dialog('close');
		}
    </script>
  </head>
  
  <body>
  
  <div class="iframe-content">
  
  
  
    <div class="p_box">
    <form action="<%=basePath%>seo/index.do" method="post">
		<table class="p_table form-inline" width="100%">
		  <tbody>
			<tr>
				<td class="p_label">名称:</td> 
				<td><input type="text" name="pageName" value="${pageName}" style="height:30px"></td>
				<td><button type="submit" class="btn btn-small w5">查询</button></td>
			</tr>
			</tbody>
		</table>
	</form>
  </div>
  
  
  <div class="p_box">
  <table class="p_table table_center">
	 <tbody>
		<tr>
			<th>
				名称
			</th>
			<th>
				Title
			</th>
			<th>
				Keywords
			</th>
			<th>
				Description
			</th>
			<th>
				操作  
			</th>
		</tr>
		<s:iterator value="pagination.items" var="seo">
			<tr>
				<td  align="center">
				${seo.pageName}
				</td>
				<td>
					${seo.seoTitle}
				</td>
				<td>
					${seo.seoKeyword}
				</td>
				<td>
					${seo.seoDescription}
				</td>
				<td>
				<a href="javascript:void(0);" onClick="editSeoIndexPage('${seoIndexPageId}')" class="other_menus1 open">修改</a>
				<textarea rows="5" cols="100" id="seoContent_hidden_${seoIndexPageId}" style="display:none">${seo.seoContent }</textarea>
				</td>
			</tr>
		</s:iterator>
		        <tr>
   					<td colspan="2" align="right">总条数：<s:property value="pagination.totalResultSize"/></td>
				    <td colspan="3" align="right"><s:property escape="false" value="@com.lvmama.comm.utils.Pagination@pagination(pagination)"/></td>
  				</tr>
		</tbody>
	</table>
</div>
</div>
  
<div id="updateSeoIndexPage" style="display: none">
	    <div>
    <table class="p_table form-inline">
   		<tr>
   			<td  align="right">页面</td>
   			<td >
	   		  <input type="hidden" id="ipt_seoIndexPageId" name="seoIndexPageId" value=""/>
	   		  <input type="text" disabled="disabled" id="ipt_pageName" value="" style="width:80%;height:30px;" />
   			</td>
   		</tr>
   		<tr>
   			<td  align="right"><font color="red">*</font> Title:(页面标题)</td>
   			<td >
	   			 <textarea rows="5" cols="90" style="width:100%;height:86px;" id="ipt_seoTitle" name="seoTitle" > </textarea>
   			</td>
   		</tr>
   		<tr>
   			<td  align="right">Keywords:(页面关键词)</td>
   			<td >
	   			 <textarea rows="5" cols="90" style="width:100%;height:86px;" id="ipt_seoKeyword" name="seoKeyword"> </textarea>
   			</td>
   		</tr>
   		<tr>
   			<td  align="right">Discription:(页面描述)</td>
   			<td >
	   			 <textarea rows="5" cols="90" style="width:100%;height:86px;" id="ipt_seoDescription" name="seoDescription"  > </textarea>
   			</td>
   		</tr>
   		<tr>
   			<td  align="right">Content:(页面友情链接)</td>
   			<td >
	   			 <textarea rows="10" cols="90" style="width:100%;height:86px;"  id="ipt_seoContent" name="seoContent"  > </textarea>
   			</td>
   		</tr>
   		<tr>
   			<td  colspan="2" align="center">
   			<input type="button" class="btn btn-small w3" onclick="updateIndexPageSeo()" value="提交" >
   			<input type="button" class="btn btn-small w3" onclick="createFriendLinkFile()" value="生成文件">
			<input type="button" class="btn btn-small w3" onclick="synFriendLinkFile()" value="同步到其他系统">
			
			<input type="button" value="取消" class="btn btn-small w3" onclick="closeUpdateSeoIndexPage()">
   			</td>
   		</tr>
   </table>
  </div>
 </div>
  </body>
</html>
