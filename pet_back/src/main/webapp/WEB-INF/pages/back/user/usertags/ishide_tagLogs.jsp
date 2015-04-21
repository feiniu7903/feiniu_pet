<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="mis" uri="/tld/lvmama-tags.tld"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>查询所有用户标签</title>
</head>
<s:include value="/WEB-INF/pages/pub/jquery.jsp"/>
<script charset="utf-8" src="${basePath}/js/recon/My97DatePicker/WdatePicker.js"></script>
    <link rel="stylesheet" type="text/css" href="${basePath}css/ui-common.css"></link>
    <link rel="stylesheet" type="text/css" href="${basePath}css/ui-components.css"></link>
    <link rel="stylesheet" type="text/css" href="${basePath}css/panel-content.css"></link>

<body>
	<div id="popDiv" style="display: none"></div>
	<div class="iframe-content">
		<div class="p_box">
			<form action="${basePath}tagLogs/search.do?isHide=${isHide}" method="post" id="form1">
			<input type="hidden" id = "isHide" value="${isHide}">
				<table class="p_table form-inline" width="80%">
					<tr class="p_label">
						<td class="p_label" colspan="3" height="20px" align="left"></td>
					</tr>
					<tr class="p_label">
						<td>标签源名称：<input name="tagsLogs.searchLogsName" id="searchLogsName"></td>
						<td><input type="submit" class="btn btn-small w5"
							id="searchUserTags" value="查&nbsp;&nbsp;询" /></td>
					</tr>
				</table>
			</form>
		</div>

		<div id="tagListTable" class="p_box">
			<table class="p_table table_center">
				<tr>
					<th width="18"></th>
					<th>编号</th>
					<th>标签源名称</th>
					<th>操作</th>
				</tr>


            <s:if test="null != userTagsSearchLogsList && userTagsSearchLogsList.size()>0">
	            <s:iterator value="userTagsSearchLogsList" var="userTagsSearchLogs">
					<!-- 内容部分 -->
					<tr>
						<td><input type="checkbox" value="${userTagsSearchLogs.searchLogsId }" nam="chk" id="79" /></td>
						<td>${userTagsSearchLogs.searchLogsId }</td>
						<td>${userTagsSearchLogs.searchLogsName }</td>
						<td class="gl_cz">
							<a href="javascript:doRollBack('${userTagsSearchLogs.searchLogsId }');">恢复</a>
						</td>
					</tr>
					<!-- 内容部分 -->
				</s:iterator>
			</s:if>	
				<tr>
				<td><input class="J_select-all" type="checkbox"></td>
				<td><input type="button" class="btn btn-small w5" onclick="doRollBackBySelect()" value="恢&nbsp;&nbsp;复"/></td>
					<td  align="right">总条数：<s:property
							value="pagination.totalResultSize" /></td>
					<td colspan="5"><div style="text-align: right;">
							<s:property escape="false"
								value="@com.lvmama.comm.utils.Pagination@pagePost(pagination.pageSize,pagination.totalPageNum,pagination.url,pagination.currentPage)" />
						</div></td>
				</tr>
			</table>
		</div>
	</div>
</body>
<script type="text/javascript">
$(function(){
    $(".J_select-all").change(function(){
        $(":checkbox").attr("checked", $(".J_select-all").attr("checked"));
    }); 
  });

function doRollBack(searchId){
	var isHide = $("#isHide").val();
	if(confirm("确定恢复选定的标签源吗?")){
		$.ajax({
            type : "POST",
            url : "${basePath}/tagLogs/isHideUpdate.do",
            data : {
                "TagLogsList" : searchId,
                "isHide" : isHide
            },
            success : function(json) {
                if(json=="true"){
                    alert("操作成功!");
                    window.location.reload(true);
                }else{
                    alert("操作失败!");
                }
            }
        });
    }
}

function doRollBackBySelect(){
	var isHide = $("#isHide").val();
	var arr = "";
   $(":checkbox:checked").each(function(i,n){
         if (n.value != "on") {
             arr = arr + n.value + ",";  
         }
   });
   if (arr.length == 0) {
         alert("请选择要恢复的标签");
         return;
   };
   if(confirm("确定恢复选定的标签源吗?")){
        $.ajax({
            type : "POST",
            url : "${basePath}/tagLogs/isHideUpdate.do",
            data : {
                "TagLogsList" : arr,
                "isHide" : isHide
            },
            success : function(json) {
                if(json=="true"){
                    alert("操作成功!");
                    window.location.reload(true);
                }else{
                    alert("操作失败!");
                }
            }
        });
    }
}

</script>
</html>