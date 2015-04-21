<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<title>super后台-EBooking公告</title>
<!-- css -->
<link rel="stylesheet" type="text/css" href="${basePath }/themes/base/jquery.ui.all.css" >
<link rel="stylesheet" type="text/css" href="${basePath }/themes/ebk/admin.css" >
<link rel="stylesheet" type="text/css" href="${basePath }/themes/ebk/ui-components.css" >
<script src="http://pic.lvmama.com/js/jquery142.js"></script>
<script type="text/javascript" src="${basePath}js/base/jquery.form.js"></script>
 
<script type="text/javascript"
	src="${basePath }/js/base/jquery-ui-1.8.5.js"></script>
<script type="text/javascript"
	src="${basePath }/js/base/jquery.datepick-zh-CN.js"></script>

<script type="text/javascript">

	$(function() {
		$("#releaseStatus").val("${releaseStatus}");
	});
	
	function showEdit(id) {
		var location = "${basePath}/announcement/ebkAnnouncementToAdd.do?time=" + new Date().getMilliseconds() + "&announcementId=" + id;
		$("<iframe id='addAnnounceWin' frameborder='0' ></iframe>").dialog({
			autoOpen : true,
			modal : true,
			title : "新增公告",
			position : 'top',
			width : 800,
			height : 550
		}).width(800).height(550).attr("src", location);
	}
	function closePopupWin() {
		$("#addAnnounceWin").dialog("close");
		$("#addAnnounceWin").remove();
	}
</script>
</head>
<body>
	<ul class="gl_top">
		<form action="${basePath }/announcement/ebkAnnouncementList.do"  method="post">
			<table border="0" cellpadding="0"   style="width:380px;">
				<tr>
					<td><label>发布状态： <select name="releaseStatus" class="u3"
							id="releaseStatus">
								<option value="">全部</option>
								<option value="RELEASED">已发布</option>
								<option value="UNRELEASED">未发布</option>
						</select>
					</label></td>
					<td><input type="submit"   class="u10 btn btn-small" value="查询"> <a
						id="newAnnouncement" onclick="showEdit('');" class="u10"
						href="javascript:void(0);">新增</a></td>
				</tr>
			</table>
		</form>
	</ul>
	<div class="tab_top"></div>
	<table border="0" cellspacing="0"  class="gl_table">
		<tr>
			<th width="50">序列号</th>
			<th>标题</th>
			<th width="50">发布人</th>
			<th width="110">发布时间</th>
			<th width="110">创建时间</th>
			<th width="60">发布状态</th>
			<th width="50">操作</th>
		</tr>
		<s:iterator value="ebkAnnouncementPage.items">
			<tr>
				<td><s:property value="announcementId" /></td>
				<td><s:property value="title" /></td>
				<td><s:property value="operator" /></td>
				<td><s:date name="beginDate" format="yyyy-MM-dd HH:mm" /></td>
				<td><s:date name="createTime" format="yyyy-MM-dd HH:mm" /></td>
				<td><s:property value="releaseStatus" /></td>
				<td><a href="javascript:void(0);"
					onclick="showEdit('${announcementId}');">修改</a></td>
			</tr>
		</s:iterator>
		<tr>
			<td>总条数：<s:property value="ebkAnnouncementPage.totalResultSize" />
			</td>
			<td colspan="8" align="right">${ebkAnnouncementPage.pagination }
			</td>
		</tr>
	</table>
</body>
</html>
