<%@ page language="java" pageEncoding="UTF-8" %>
<%
String basePath = request.getContextPath();
%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="mis" uri="/tld/lvmama-tags.tld"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>手动添加标签近义</title>
</head>
<s:include value="/WEB-INF/pages/pub/jquery.jsp"/>
<script charset="utf-8" src="${basePath}/js/recon/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=basePath %>/js/base/typeFirstType.js"></script>
<link rel="stylesheet" type="text/css" href="${basePath}css/ui-common.css"></link>
<link rel="stylesheet" type="text/css" href="${basePath}css/ui-components.css"></link>
<link rel="stylesheet" type="text/css" href="${basePath}css/panel-content.css"></link>
<script type="text/javascript">
$(document).ready(function(){
	$("#searchTagsRes").click(function(){
		var tagsname = $("#tagsName2").val();
		if(tagsname != null && tagsname!=""){
	  		$("#form2").submit();
		}
	});
	
	$("#cancel").click(function(){
		$("#form3").submit();
	});
	
	$("#addTagsRes").click(function(){
		var _arrid = "";
		var _arrtype = "";
		$(".userTagsResType").each(function(i){
			if(this.value != 0){
				_arrid = _arrid +$(":input[name='_tagsId']").eq(i).val()+',';
				_arrtype = _arrtype + this.value + ',';
			}
		});
		if(_arrid == ""){
			alert("标签近义类型无修改");
			return false;
		}
		if(confirm("你确定对选中的标签进行关联吗？")){
			   $.ajax({type : "POST",
		           url : "${basePath}/tagsRes/addTagsRes.do",
		           data : {
		               "tagsIdList" : _arrid,
		               "tagsResTypeList" : _arrtype,
		               "tags.tagsId" : $("#_tagsid").val()
		           },
		           success : function(json) {
		               if(json=="true"){
		                   alert("关联成功!");
		                   window.location.reload(true);
		               }else{
		                   alert("关联失败!");
		               }
		           }
			   });
		} 
	});
});
</script>
<body>
    <div class="iframe-content">
        <div class="p_box">
        <form action="${basePath}tags/search.do" method="post" id="form3"></form>
        <form action="${basePath}tagsRes/searchTagsRes.do" method="post" id="form2">
        <input type="hidden" name="tags.tagsName" value="${tags.tagsName}">
        <input type="hidden" id="_tagsid" name="tags.tagsId" value="${tags.tagsId}">
            <table class="p_table form-inline" width="80%">
                <tr>
                    <td colspan="2">手动添加标签近义</td>
                </tr>
                <tr class="p_label">
                	<td>标签搜索：<input id="tagsName2" name="search_tagsName" value="${search_tagsName}" ></td>
					<td><input type="button" class="btn btn-small w5" id="searchTagsRes" value="查&nbsp;&nbsp;询"/></td>
				</tr>
            </table>
        </form>
        </div>
        
        <div id="tagListTable" class="p_box">
			<table class="p_table table_center">
				<tr>
					<th>标签名称</th>
					<th>近义标签</th>
					<th>近义类型</th>
				</tr>
			<s:iterator value="tagsList" var="bean">
				<tr>
					<td>${tags.tagsName}
						<input type="hidden" name="_tagsId" value="${bean.tagsId}">
					</td>
					<td>${bean.tagsName}</td>
					<td><s:select  cssClass="userTagsResType" list="userTagsResType" name="tagsRes.relationshipType" listKey="elementCode" listValue="elementValue" headerKey="0" headerValue="无"></s:select></td>
				</tr>
			</s:iterator>
			<s:if test="tagsList.size == 0">
				<tr>
					<td>
						<font color="red">没有匹配的数据</font>
					</td>
				</tr>
			</s:if>
				<tr>
			     <td colspan="3">
			     <s:if test="tagsList.size > 0">
			     	 <input type="button" id="addTagsRes" class="btn btn-small w5" value="提&nbsp;&nbsp;交"/>&nbsp;&nbsp;&nbsp;
			     </s:if>
			         <input type="button" id="cancel" class="btn btn-small w5" value="返&nbsp;&nbsp;回"/>
			     </td>
				</tr>
							
			</table>
    </div>
    
</body>
</html>