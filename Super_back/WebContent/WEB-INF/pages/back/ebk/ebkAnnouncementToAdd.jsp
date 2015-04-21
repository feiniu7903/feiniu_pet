<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<script type="text/javascript"
	src="${basePath}/js/base/jquery-1.4.4.min.js"></script>
<script type="text/javascript"
	src="${basePath}/js/base/jquery-ui-1.8.5.js"></script>
<script type="text/javascript" src="${basePath}js/base/jquery.form.js"></script>
<script type="text/javascript"
	src="${basePath}/js/base/jquery.datepick-zh-CN.js"></script>
<script type="text/javascript"
	src="${basePath}/js/base/jquery-ui-timepicker-addon.js"></script>
<script charset="utf-8" src="${basePath}/kindeditor-3.5.1/kindeditor.js"></script>
<!-- css -->
<link rel="stylesheet" type="text/css" href="${basePath}/themes/base/jquery.ui.all.css" />
<link rel="stylesheet" type="text/css" href="${basePath}/themes/ebk/admin.css" />
<link rel="stylesheet" type="text/css" href="${basePath}/themes/ebk/ui-components.css" />
<link rel="stylesheet" type="text/css" href="http://pic.lvmama.com/styles/ebooking/base.css" />
<link rel="stylesheet" type="text/css" href="${basePath}/themes/base/jquery-ui-timepicker-addon.css" />
	
<script type="text/javascript">
	var editor;
	$(document).ready(function() {
		$('#ebkAnnouncementBeginDate').datetimepicker({
			showSecond : true,
			timeFormat : 'hh:mm:ss',
			stepHour : 1,
			stepMinute : 5,
			stepSecond : 5,
			showButtonPanel : true
		});
 
		KE.show({
	        id : "contentA",
	        cssPath : '/FCKEditor/skins/'
	    });
		$("[name=ebkAnnouncement.bizType][value=${ebkAnnouncement.bizType}]").attr("checked",'checked');
	});
	
	function addAnn() {
		if($("[name=ebkAnnouncement.title]").val() == "") {
			alert("请填写标题");
			return false;
		}
		if($("#ebkAnnouncementBeginDate").val() == "") {
			alert("请选择通知时间");
			return false;
		}
		var cnn = KE.util.getData('contentA');
		$("#content").val(cnn);
		//$('#addAnnouncement').submit();
	}
	$('#addAnnouncement').ajaxForm({
		type : 'post',
		url : '${basePath}ebooking/addorUpdateAnnouncement.do',
		success : function(data) {
			if (data == "SUCCESS") {
				alert("操作成功");
				parent.window.location.reload();
				parent.window.closePopupWin();
			} else {
				alert(data);
			}
		}
	});
</script>
</head>
<body>
	<form id="addAnnouncement"
		method="post" enctype="multipart/form-data">
		<input type="hidden" name="ebkAnnouncement.announcementId"
			value="${ebkAnnouncement.announcementId}" />
		<table>
			<tr height="26">
				<td>公告标题:</td>
				<td><input class="ggbt_w330" type="text" name="ebkAnnouncement.title"
					value="${ebkAnnouncement.title}" maxlength="150" /></td>
			</tr>
			<tr>
				<td>公告内容:</td>
				<td><textarea id="contentA" name="ebkAnnouncement.content"
						style="width: 600px; height: 400px;">${ebkAnnouncement.content}</textarea>
				</td>
			</tr>
			<tr height="26">
				<td>上传附件:</td>
				<td><input type="file" name="file" /></td>
			</tr>
			<tr height="26">
				<td>通知时间:</td>
				<td><input id="ebkAnnouncementBeginDate" type="text"
					class="longDateFormat" name="ebkAnnouncement.beginDate"
					value="<s:date name='ebkAnnouncement.beginDate' format='yyyy-MM-dd HH:mm'/>" /></td>
			</tr>
			<tr height="26">
				<td>
					<input class="btn btn-small" type="submit" value="提交" onclick="return addAnn();"/>
				</td>
				<td>
					<input class="btn btn-small" type="button" value="取消" onclick="parent.window.closePopupWin()"/>
				</td>
			</tr>
		</table>
	</form>

</body>
</html>
