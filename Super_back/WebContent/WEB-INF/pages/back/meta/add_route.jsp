<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="mis" uri="/tld/lvmama-tags.tld"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>super后台——采购产品信息</title>
<s:include value="/WEB-INF/pages/back/base/jquery.jsp"/>
<s:include value="/WEB-INF/pages/back/base/jsonSuggest.jsp"/>
<script type="text/javascript" src="<%=basePath%>js/meta/product.js?v=20140417"></script>
<script type="text/javascript" src="<%=basePath%>js/base/log.js"></script>
</head>
  
<body>
<div class="main main04">
	<div class="row1">
    	<h3 class="newTit">采购产品信息
    	<s:if test="metaProduct.metaProductId != null">
    	<jsp:include page="/WEB-INF/pages/back/meta/goUpStep.jsp"></jsp:include>
    	</s:if>
    	</h3>
        <s:include value="/WEB-INF/pages/back/meta/nav.jsp"/>
  </div><!--row1 end-->
  <div class="row2">
  <form action="<%=basePath%>meta/saveRoute.do" name="metaProductForm" method="post" onsubmit="return false">
	  <s:hidden name="metaProduct.metaProductId"/>
	  <s:hidden name="metaProduct.productType"/>
	  <s:include value="/WEB-INF/pages/back/meta/header.jsp"/>
	  <table border="0" cellspacing="0" cellpadding="0" class="newInput newInputY" style="width:600px">
	  	<tr>
	  		<td style="width:100px"><em>产品分类：<span class="require">[*]</span></em></td>
	  		<td><s:radio list="subProductTypeList" name="metaProduct.subProductType" listKey="code" listValue="name"/></td>
	  	</tr>
	  </table>
	  <s:include value="/WEB-INF/pages/back/meta/prop.jsp"/>
	  <span class="msg"></span>
	  <!-- 原始的代码 zx0306 修改<p></p>
	  <p class="main4Bottom"><em class="button saveForm" ff="metaProductForm">保存</em></p> -->
	  <p class="main4Bottom">
	  	<mis:checkPerm permCode="1423" permParentCode="${permId}">
	  	<input type="button" value="保存" class="button01 add_margin_left add_margin_left01 saveForm" ff="metaProductForm"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	  	</mis:checkPerm>
	  	<s:if test="metaProduct.metaProductId != null">
  		<a href="#log" class="showLogDialog" param="{'objectType':'META_PRODUCT','objectId':<s:property value="metaProduct.metaProductId"/>}">查看操作日志</a>
  		</s:if>
	  </p>
  </form> 
  </div><!--row2 end-->
</div><!--main01 main04 end-->
</body>
</html>



