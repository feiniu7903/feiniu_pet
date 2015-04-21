<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<s:include value="/WEB-INF/pages/pub/new_jquery.jsp"/>
 <title>PC内容，主题列表管理</title>
</head>
<body>	
<div class="iframe-content">
<div class="p_box">
		<form id="saveSubjectForm" method="post" action="${basePath}/pub/subject/subjectSaveOrUpdate.do" >
			<input name="comSubject.comSubjectId" value="${comSubject.comSubjectId }" type="hidden">
			<input name="comSubject.subjectType" value="${comSubject.subjectType }" type="hidden">
			<input name="comSubject.usedByCount" value="${comSubject.usedByCount }" type="hidden">
			
			<table class="p_table"  >
				<tr>
					<td width="25%" class="p_label"><span class="red">*</span>主题名称：</td>
					<td width="75%"><input id="comSubjectSubjectName" name="comSubject.subjectName" value="${comSubject.subjectName }"></td>
				</tr>
				<tr>
					<td class="p_label"><span class="red">*</span>主题拼音：</td>
					<td><input id="comSubjectSubjectPinyin" name="comSubject.subjectPinyin" value="${comSubject.subjectPinyin }"></td>
				</tr>
				<tr>
					<td class="p_label"><span class="red">*</span>主题类型：</td>
					<td>${comSubject.subjectTypeStr }</td>
				</tr>
				<tr>
					<td class="p_label">是否标红：</td>
					<td><select name="comSubject.ifBold" id="comSubject.ifBold">
							<option value="N">否</option>
							<option
								<s:if test='comSubject.ifBold=="Y"'>selected="true"</s:if>
								value="Y">是</option>
					</select></td>
				</tr>
				<tr>
					<td class="p_label">创建时间</td>
					<td><s:date name="comSubject.createTime" format="yyyy-MM-dd hh:mm:ss"/></td>
				</tr>
				<tr>
					<td class="p_label">更新时间</td>
					<td><s:date name="comSubject.updateTime" format="yyyy-MM-dd hh:mm:ss"/></td>
				</tr>
				<tr>
					<td class="p_label">状态：</td>
					<td><select name="comSubject.isValid" id="comSubject.isValid">
							<option value="Y">是</option>
							<option
								<s:if test='comSubject.isValid=="N"'>selected="true"</s:if>
								value="N">否</option>
					</select></td>
				</tr>
				<tr>
					<td class="p_label">排序值：</td>
					<td><input name="comSubject.seq" value="${comSubject.seq}" id="comSubjectSeq"></td>
				</tr>
				<tr>
					<td class="p_label">引用次数：</td>
					<td>${comSubject.usedByCount }</td>
				</tr>
			</table>
			<p class="p_box">
				<input class="btn btn-small w5" value="确定" type="button" onclick="javascript:saveSubject();">
 			</p>
 		</form>
	</div>
	</div>
<script type="text/javascript">
	function saveSubject() {
		if($("#comSubjectSubjectName").val()==""){
			alert("主题名称不能为空");
			$("#comSubjectSubjectName").focus();
			return false;
		}
		if($("#comSubjectSubjectPinyin").val()==""){
			alert("主题拼音不能为空");
			$("#comSubjectSubjectPinyin").focus();
			return false;
		}
		if($("#comSubjectSeq").val()==""){
			alert("SEQ不能为空");
			$("#comSubjectSeq").focus();
			return false;
		}
		var url="${basePath}/pub/subject/subjectSaveOrUpdate.do";
		var div='saveSubjectForm';
		checkAndSubmit(url,div);
  	}
	//提交之后，重刷新table
	function checkAndSubmit(url,form) {
	var options = {
			url:url,
			type : 'POST',
			dataType:'json',
 			success:function(data){
          		   if(data.success==true) {
					 alert("操作成功!");
					 $("#popDiv").dialog("close");
					 window.location.reload();
					} else {
					  alert("操作失败："+data.message);
				    }
 			},
			error:function(){
                     alert("出现错误");
                 }
		};
	
	$('#'+form).ajaxSubmit(options);
}
</script>
</body>
</html>
