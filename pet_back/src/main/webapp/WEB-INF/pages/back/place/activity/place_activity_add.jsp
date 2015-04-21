<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="mis" uri="/tld/lvmama-tags.tld"%>
<s:set var="basePath"><%=request.getContextPath()%></s:set>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>活动增加</title>
<s:include value="/WEB-INF/pages/pub/new_jquery.jsp"/>
<script type="text/javascript" src="${basePath}/js/base/jquery.form.js"></script>
<script type="text/javascript" src="${basePath}/js/place/place.js"></script>
<script charset="utf-8" src="${basePath}/kindeditor-4.0.2/kindeditor-min.js"></script>
<script charset="utf-8" src="${basePath}/kindeditor-4.0.2/lang/zh_CN.js"></script>
<script type="text/javascript" src="/super_back/js/prod/sensitive_word.js"></script>
<script type="text/javascript">
var editor;
$(function(){
	 var kindEditorType=['forecolor','image'];
	editor = KindEditor.create('#'+"desc_id",{
        resizeType : 1,
        width:'800px',
        height:'500px',
        filterMode : true,
        items:kindEditorType,
        uploadJson:'/pet_back/upload/uploadImg.do'
    });
});
 
function checkActiviForm(){
	if($("#title").val()==""){
		alert("活动主题不能为空");
		$("#title").focus();
		return false;
	}
	if($("#startTime").val()==""){
		alert("活动开始时间不能为空");
		return false;
	}
	if($("#endTime").val()==""){
		alert("活动结束时间不能为空");
		return false;
	}
	if(editor.html()==""){
		alert("活动内容不能为空");
		return false;
	}
	$("#content").val(editor.html());
	$("#desc_id").removeClass("sensitiveVad");
	var sensitiveValidator=new SensitiveWordValidator($('#activityform1'), true);
	if(!sensitiveValidator.validate()){
		return;
	}
	var options = { 
		url:"${basePath}/place/placeActivitySave.do",
		dataType:"",
		type : "POST", 
		success:function(data){ 
				alert(data); 
				reloadActivity("${placeId}");
		}, 
		error:function(){ 
			alert("出现错误"); 
		} 
	};
	$('#activityform1').ajaxSubmit(options);
}

</script>
</head>
<body>
 	<form action="${basePath}/place/placeActivitySave.do" id="activityform1" method="post" class="mySensitiveForm">
		<s:hidden name="placeActivity.placeActivityId"></s:hidden>
		<input name="placeActivityId" type="hidden" value="${placeActivity.placeActivityId }" />
		<s:hidden name="placeId"></s:hidden>
		<s:hidden name="stage"></s:hidden>
		<input type="hidden" name="placeActivity.placeId" value="${placeId }" />
		<table class="p_table">
			<tr>
				<td class="p_label" ><span class="red">*</span>活动主题：</td>
				<td><s:textfield name="placeActivity.title" id="title" theme="simple" cssStyle="width:230px;" cssClass="sensitiveVad"></s:textfield></td>
			</tr>
			<tr>
				<td class="p_label" ><span class="red">*</span>开始时间:</td>
				<td> <input
					name="placeActivity.startTime" id="startTime"
					value="<s:date name='placeActivity.startTime' format='yyyy-MM-dd'/>"
					type="text" class="Wdate"
					onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'%y-%M-%d'})" />
			<tr><td class="p_label" ><span class="red">*</span>结束时间:</td>
				<td><input
					name="placeActivity.endTime" id="endTime"
					value="<s:date name='placeActivity.endTime'  format='yyyy-MM-dd'/>"
					type="text" class="Wdate"
					onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'startTime\')}'})" />
				</td>
			</tr>
			<tr>
				<td class="p_label" >内容:</td>
				<td><textarea id="desc_id" cols="70" rows="4" style="width: 600px; height: 250px;" name="desc_id" class="sensitiveVad">
						<s:property value="placeActivity.content" />
					</textarea><input id="content" name="placeActivity.content" type="hidden" class="sensitiveVad" /></td>
			</tr>
		</table>
	   <p class="tc mt10"><input type="button" id="btn_ok" class="btn btn-small w3" onclick="return checkActiviForm();" value="保存" /></p>
	</form>
	

</body>
</html>