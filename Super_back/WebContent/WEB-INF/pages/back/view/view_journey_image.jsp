<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>super后台——行程图片上传</title>
<link href="<%=basePath%>style/houtai.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<%=basePath%>js/prod/view_journey_image.js"></script>
</head>
<body>
<div id="uploadJourneyPic" >
<p><font class="add_margin_left">上传图片</font></p>
<form action="view/uploadJourneyPic.do" method="post" name="uploadProductJourneyPicForm" id="uploadProductJourneyPicForm"> 
	<input type="hidden" id="uploadJourneyPicProductId" name="uploadJourneyPicProductId" value=""/>
	<input type="hidden" id="uploadJourneyPicJourneyId" name="uploadJourneyPicJourneyId" value=""/>
	<table class="newTableB" width="80%"  cellspacing="0" cellpadding="0" >
		<tr>
			<td style="text-align: right;">图片名称：</td>
			<td colspan="4"><s:textfield cssClass="text1" id="productJourneyPicName" name="productJourneyPicName"/>(请先录入图片名称，再上传图片。图片尺寸：320×240；比例为：4:3)</td>
		</tr>
		<tr>
			<td style="text-align: right;">上传图片：</td>
			<td colspan="4"><input type="file" id="uploadFile" style="font-size:13px;margin:0;padding:0;" name="file"/>
			<input type="button" id="productJourneyUploadFileButton" value="上传" class="button01 add_margin_left add_margin_left01" ff="uploadProductJourneyPicForm"/>
			</td>
		</tr>
	</table>
</form> 
</div>
<div id="divspace" style=" height: 10px;">
	&nbsp;
</div>
<div id="journeyImageList">
	<table id="journeyImageTable" class="newTable" width="90%" border="0" cellspacing="0" cellpadding="0">
		<tr class="newTableTit">
		    <td>编号</td>
		    <td>图片名称</td>
		    <td>图片</td>
		    <td>操作</td>
		</tr>
		<s:iterator value="journeyImageList">
		  <tr type="big" id="tr_${pictureId}">
		    <td>${pictureId}</td>
		    <td>${pictureName}</td>
		    <td><img src="http://pic.lvmama.com/pics/${pictureUrl}"/></td>
		    <td><a href="javascript:deleteJourneyImages(${pictureId});">删除</a></td>
		  </tr>
		</s:iterator>
	</table>
</div>
<div id="divspace" style=" height: 10px;">
	&nbsp;
</div>
</body>
</html>

