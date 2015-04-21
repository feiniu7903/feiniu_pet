<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>super后台——产品图片</title>
<link href="<%=basePath%>style/houtai.css" rel="stylesheet" type="text/css" />
<s:include value="/WEB-INF/pages/back/base/jquery.jsp"/>
<script type="text/javascript" src="<%=basePath%>js/base/ajaxupload.js"></script>
<script type="text/javascript" src="<%=basePath%>js/prod/image.js"></script>
<script type="text/javascript" src="<%=basePath%>js/base/log.js"></script>
<style type="text/css">
#image_tb td img{width:80px;height:40px;}
</style>
</head>
 
<body>
<div class="main main12">
	<div class="row1">
    	<h3 class="newTit">销售产品信息 </h3>
    </div><!--row1 end-->
   <div class="row2">
   		<table width="90%" border="0" cellspacing="0" cellpadding="0" class="additionaTable newTable" id="image_tb">
          <tr class="newTableTit">
            <td>编号</td>
            <td>图片名称</td>
            <td>图片</td>
          </tr>
          <tr id="product_icon" <s:if test="product.smallImage==null">style="display:none"</s:if>>
            <td>&nbsp;</td>
            <td>小图</td>
            <td class="icon"><s:if test="product.smallImage!=null"><img src="http://pic.lvmama.com/pics/${product.smallImage}"/></s:if></td>
          </tr>
          <s:iterator value="pictureList">
          <tr type="big" id="tr_${pictureId}">
            <td>${pictureId}</td>
            <td>${pictureName}</td>
            <td><img src="http://pic.lvmama.com/pics/${pictureUrl}"/></td>
          </tr>
          </s:iterator>
        </table>
   </div><!--row2 end-->   
</div><!--main01 main05 end-->
</body>
</html>



