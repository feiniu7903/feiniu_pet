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
	<div class="iframe-content">
		<div class="p_box">
		<form method="post" id="form1">
		<input type="hidden" id="typeId" name="userTagsType.typeId" value="${userTagsType.typeId}">
		
			<table class="p_table form-inline" width="80%">
				<tr class="p_label">
					<td colspan="3">编辑标签类别</td>
				</tr>
				 <tr>
                    <td class="p_label">一级类别:</td>
                    <td>
                        <s:select id="firstType" list="userTagsFirstType" name="userTagsType.typeFirstType" listKey="elementValue" listValue="elementValue" ></s:select>
                    </td>
                </tr>
                <tr>
                    <td class="p_label">二级类别：</td>
                    <td><input id="secondType" name="userTagsType.typeSecondType" value="${userTagsType.typeSecondType}"></td>
                </tr>
                <tr>
					<td colspan="2">
					   <input type="button" class="btn btn-small w5" id="addUserTagsType" value="确&nbsp;&nbsp;定" onclick="updateAndCheck()"/>&nbsp;&nbsp;
					   <input type="button" value="取&nbsp;&nbsp;消" class="btn btn-small w5" onclick="$('#popDiv').dialog('close')">
					</td>
				</tr>
			</table>
		</form>
		</div>
	</div>
</body>
<script type="text/javascript">


function updateAndCheck(){
	var typeId = $("#typeId").val();
    var firstType = $("#firstType").find("option:selected").text();
	var secondType = $("#secondType").val();

	$.ajax({
        type : "POST",
        url : "${basePath}tagsType/update.do",
        data : {
            "userTagsType.typeId" : typeId,
            "userTagsType.typeFirstType" : firstType,
            "userTagsType.typeSecondType": secondType,
        },
        success : function(json) {
            if(json=="true"){
                alert("修改成功!");
                window.location.reload(true);
            }else if(json=="false"){
                alert("修改失败!");
            }else if(json=="exist"){
            	alert("已经存在");
            }else{
            	alert("找不到标签ID");
            }
        }
    });
}
</script>
</html>