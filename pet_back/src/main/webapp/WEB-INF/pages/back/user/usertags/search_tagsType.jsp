<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
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
			<table class="p_table form-inline" width="80%">
				<tr class="p_label">
					<td class="p_label" colspan="3" height="20px"></td>
				</tr>
				<tr class="p_label">
					<td>一级类别:
						<s:select id="typeFirstType" list="userTagsFirstType" name="userTagsType.typeFirstType" listKey="elementValue" listValue="elementValue"></s:select>
					</td>
					<td>二级类别：<input id="typeSecondType" name="userTagsType.typeSecondType" ></td>
					<td><input type="button" class="btn btn-small w5"
						id="addUserTagsType" value="添&nbsp;&nbsp;加" onclick="saveAndCheck()"/></td>
				</tr>
			</table>
		</div>

		<div id="tagListTable" class="p_box">
			<table class="p_table table_center">
				<tr>
					<th width="18"></th>
					<th>编号</th>
					<th>一级类别名称</th>
					<th>二级类别名称</th>
					<th>操作</th>
				</tr>

            <s:iterator value="usertagsTypeList" var="userTagsType">
				<!-- 内容部分 -->
				<tr>
					<td><input type="checkbox" value="${userTagsType.typeId}" nam="chk" id="79" /></td>
					<td>${userTagsType.typeId}</td>
					<td>${userTagsType.typeFirstType}</td>
					<td>${userTagsType.typeSecondType}</td>
					<td class="gl_cz">
						<a href="javascript:doUpdate('${userTagsType.typeId}');">修改</a>
						<a href="javascript:doDelete('${userTagsType.typeId}');">删除</a>
					</td>
				</tr>
				<!-- 内容部分 -->
			</s:iterator>
				
				<tr>
					
				<td><input class="J_select-all" type="checkbox"></td>
				<td><input type="button" class="btn btn-small w5" onclick="doPageAllDelete()" value="删&nbsp;&nbsp;除"></td>
				<td align="right">总<s:property value="pagination.totalResultSize" />条</td>
					<td colspan="2"><div style="text-align: right;">
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

function saveAndCheck() {
	
	var typeFirstType = $("#typeFirstType").val();
    var typeSecondType = $("#typeSecondType").val().trim();
   
    if ("" == typeSecondType) {
        alert("请输入正确的二级类别");
        $("#typeSecondType").focus();
        return;
    }
    
    $.ajax({
        type : "POST",
        async : false,
        url : "${basePath}/tagsType/isExistCheck.do",
        data : {
            "userTagsType.typeFirstType" : typeFirstType,
            "userTagsType.typeSecondType" : typeSecondType,
        },
        success : function(json) {
        	if(json=="exist"){
                alert("二级类别已存在!");
            }else if(json=="false"){
               alert("操作失败!");
            }else{
               alert("操作成功!");
               window.location.reload(true);
            }
        }
    }); 
}

function doDelete(typeId){
	if(confirm("你确定删除这条类别")){
		$.ajax({
	        type : "POST",
	        url : "${basePath}/tagsType/delete.do",
	        data : {
	            "deleteTagTypesList" : typeId,
	        },
	        success : function(json) {
	            if(json=="true"){
	                alert("删除成功!");
	                window.location.reload(true);
	            }else{
	                alert("删除失败!");
	            }
	        }
	    });
	}
}

function doPageAllDelete(){
	var arr = "";
	$(":checkbox:checked").each(function(i,n){
	      if (n.value != "on") {
	          arr = arr + n.value + ",";  
	      }
	    });
	  if (arr.length == 0) {
	        alert("请选择要删除的");
	        return;
	    };
   if(confirm("你确定要删除选中的这些类别")){
	   $.ajax({
           type : "POST",
           url : "${basePath}/tagsType/delete.do",
           data : {
               "deleteTagTypesList" : arr,
           },
           success : function(json) {
               if(json=="true"){
                   alert("删除成功!");
                   window.location.reload(true);
               }else{
                   alert("删除失败!");
               }
           }
       });
   }
}

function doUpdate(typeId){
    $("#popDiv").load("${basePath}/tagsType/goUpdate.do?userTagsType.typeId="+typeId,function() {
           $(this).dialog({
               modal:true,
               title:"编辑标签类别",
               width:600,
               height:300
           });
     }); 
}
</script>
</html>