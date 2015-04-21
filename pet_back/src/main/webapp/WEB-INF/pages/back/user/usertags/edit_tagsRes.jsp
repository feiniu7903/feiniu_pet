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
<title>近义词标签关联编辑</title>
</head>
<s:include value="/WEB-INF/pages/pub/jquery.jsp"/>
<script charset="utf-8" src="${basePath}/js/recon/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=basePath %>/js/base/typeFirstType.js"></script>
<link rel="stylesheet" type="text/css" href="${basePath}css/ui-common.css"></link>
<link rel="stylesheet" type="text/css" href="${basePath}css/ui-components.css"></link>
<link rel="stylesheet" type="text/css" href="${basePath}css/panel-content.css"></link>

<script type="text/javascript">
$(document).ready(function(){
	$("#updateUserTags").click(function(){
		$.ajax({
	        type : "POST",
	        url : "${basePath}tagsRes/updTagsRes.do",
	        data : {
	            "tagsRes.relationshipId" : $("#relationId").val(),
	            "tagsRes.relationshipType" : $("#userTagsResType").val()
	        },
	        success : function(json) {
	            if(json=="true"){
	                alert("修改成功!");
	                window.location.reload(true);
	            }else{
	                alert("修改失败!");
	            }
	        }
		});
	});
});
</script>

<body>
    <div class="iframe-content">
        <div class="p_box">
        <form action="${basePath}tags/update.do" method="post" id="form1">
        <input type="hidden" id="relationId" name="tagsRes.relationshipId" value="${tagsRes.relationshipId}" />
            <table class="p_table form-inline" width="80%">
                <tr>
                    <td colspan="2">近义词标签关联编辑</td>
                </tr>
                 <tr class="p_label">
                    <td class="p_label">标签名称:</td>
                    <td>${tagsRes.tagsName1},${tagsRes.tagsName2}</td>
                </tr>
                <tr class="p_label">
                    <td class="p_label">近义类型:</td>
                    <td>
                    	<s:select id="userTagsResType" list="userTagsResType" name="tagsRes.relationshipType" listKey="elementCode" listValue="elementValue"  key="#tagsRes.relationshipType"></s:select>
                    </td>
                </tr>
                <tr class="p_label">
                    <td colspan="2"><input type="button" class="btn btn-small w5"
                        id="updateUserTags" value="确&nbsp;&nbsp;定" />    
                    </td>
                </tr>
            </table>
        </form>
        </div>
    </div>
</body>

</html>