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
    	<h3 class="newTit">销售产品信息
    	<s:if test="product != null">
    		&nbsp;&nbsp;&nbsp;
    		<s:if test="product.productId != null">
    			产品ID:${product.productId }
    		</s:if>
    		&nbsp;&nbsp;&nbsp;
    		<s:if test="product.productName != null">
    			产品名称：${product.productName }
    		</s:if>
    	</s:if>
    	<s:if test="product.productId != null">
    		<jsp:include page="/WEB-INF/pages/back/prod/goUpStep.jsp"></jsp:include>
    	</s:if>
    	</h3>
         <jsp:include page="/WEB-INF/pages/back/prod/product_menu.jsp"></jsp:include>
   </div><!--row1 end-->    
   <div class="row2">
   		<form onsubmit="return false" id="uploadForm">
   		 <table>
   		 	<tr>
   		 		<td></td>
   		 		<td></td>
   		 		<td></td>
   		 	</tr>
   		 </table>
   		<ul class="new proNew">
        	<li><span>请选择：</span><select name="type" class="text1"><option value="ICON">小图</option><option value="BIG">大图</option></select>
        		<span id="bigNameLi" name="imgname" style="visibility: hidden;"><span>图片名</span><input type="text" name="imgname"/></span>        	
        	</li>
        	<li><span>图片：</span><input type="file" id="uploadFile" style="font-size:13px;margin:0;padding:0;" name="file"/>(大图尺寸为：440*220，比例为2:1；小图为200*100，比例为2：1)</li>
        	<li><em class="button" id='uploadFileBtn'>保存</em>
        	<a href="#log" class="showLogDialog" param="{'parentType':'PROD_PRODUCT','objectType':'PROD_PRODUCT_IMAGE_UPLOAD','parentId':${productId}}">查看操作日志</a>
        	</li>
        </ul>
        </form>
   		<table width="90%" border="0" cellspacing="0" cellpadding="0" class="additionaTable newTable" id="image_tb">
          <tr class="newTableTit">
            <td>编号</td>
            <td>图片名称</td>
            <td>图片</td>
            <td>操作</td>
          </tr>
          <tr id="product_icon" <s:if test="product.smallImage==null">style="display:none"</s:if>>
            <td>&nbsp;</td>
            <td>小图</td>
            <td class="icon"><s:if test="product.smallImage!=null"><img src="http://pic.lvmama.com/pics/${product.smallImage}"/></s:if></td>
            <td>&nbsp;</td>
          </tr>
          <s:iterator value="pictureList">
          <tr type="big" id="tr_${pictureId}">
            <td>${pictureId}</td>
            <td>${pictureName}</td>
            <td><img src="http://pic.lvmama.com/pics/${pictureUrl}"/></td>
            <td><a href="#move" class="move" tt="up" title="图片上移" result="${pictureId}">上移</a><a href="#move" class="move" tt="down" title="图片下移" result="${pictureId}">下移</a><a href="#delete" class="delete" result="${pictureId}">删除</a></td>
          </tr>
          </s:iterator>
        </table>
   </div><!--row2 end-->   
</div><!--main01 main05 end-->
</body>
</html>



