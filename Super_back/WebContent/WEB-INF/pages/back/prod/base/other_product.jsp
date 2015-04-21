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
<title>super后台——门票销售产品</title>
<link href="<%=basePath%>style/houtai.css" rel="stylesheet" type="text/css" />
 
<%@ include file="/WEB-INF/pages/back/base/jquery.jsp"%>
<script type="text/javascript" src="<%=basePath%>js/prod/common.js"></script>
<script type="text/javascript" src="<%=basePath%>js/prod/date.js"></script>
<%@ include file="/WEB-INF/pages/back/base/jsonSuggest.jsp"%>
<script type="text/javascript" src="<%=basePath%>js/prod/validate.js"></script>
<script type="text/javascript" src="<%=basePath%>js/prod/product.js"></script>
<script type="text/javascript" src="<%=basePath%>js/base/log.js"></script>
<script type="text/javascript" src="<%=basePath%>js/prod/sensitive_word.js"></script>
</head> 
<body>
<div class="main main08">
	<div class="row1">
    	<h3 class="newTit">销售产品信息
    	<s:if test="product.productId != null">
    		<jsp:include page="/WEB-INF/pages/back/prod/goUpStep.jsp"></jsp:include>
    	</s:if>
    	</h3>
        <jsp:include page="/WEB-INF/pages/back/prod/product_menu.jsp"></jsp:include>
        
  </div><!--row1 end-->
  <form method="post" action="<%=basePath%>prod/saveOtherProduct.do" name="prodFrom" class="mySensitiveForm"><s:hidden name="product.productId" id="productId"/>
  <s:hidden name="product.productType" />
  <div class="row2">    
       		<s:include value="/WEB-INF/pages/back/prod/base/header.jsp"></s:include>
            <!-- 其他产品属性 -->            
            <p><b>产品类型：<span class="require">[*]</span></b><s:radio list="subProductTypeList" name="product.subProductType" listKey="code" listValue="name"/></p>            
            <p><b>其他区域：</b>&nbsp;&nbsp;
            <s:radio name="product.isForegin" list="#{'N':'境内','Y':'境外'}"  listKey="key" listValue="value" labelSeparator="&nbsp;" />
       		<s:select name="product.regionName" list="regionNamesList" listKey="code" listValue="name"></s:select></p>

      <div id="applicableTravel">
          <p>
              <b>适用行程天数：</b>&nbsp;&nbsp;
              <s:textfield name="product.applicableTravelDaysLimit" cssStyle="width: 50px"/> 天～
              <s:textfield name="product.applicableTravelDaysCaps" cssStyle="width: 50px"/> 天
          </p>

          <p>
              <b>适用产品类型：</b>&nbsp;&nbsp;
              <s:checkboxlist list="routeSubProductTypeList"
                              listKey="code" listValue="name"
                              name="product.applicableSubProductType" value="product.applicableSubProductTypeList"/>
          </p>
      </div>
            <!-- 其他产品属性 --> 
        <s:include value="/WEB-INF/pages/back/prod/base/other_prop.jsp"/> 
  </div><!--row2 end-->    
  <span class="msg"></span>
  <!-- 原始的代码 zx0306 修改<p></p>
	  <p class="main4Bottom"><em class="button saveForm" ff="prodFrom">保存</em></p> -->
	  <p class="main4Bottom">
	  	<input type="button" value="保存" class="button01 add_margin_left add_margin_left01 saveForm" ff="prodFrom"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	  	<s:if test="product.productId != null">
  		<a href="#log" class="showLogDialog" param="{'objectType':'PROD_PRODUCT','objectId':<s:property value="product.productId"/>}">查看操作日志</a>
  		</s:if>
	  </p>
</form>
</div><!--main01 main04 end-->
</body>
</html>
