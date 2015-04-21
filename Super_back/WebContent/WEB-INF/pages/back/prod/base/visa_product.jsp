<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>super后台——签证销售产品</title>
<link href="<%=basePath%>style/houtai.css" rel="stylesheet"
	type="text/css" />
<%@ include file="/WEB-INF/pages/back/base/jquery.jsp"%>
<script type="text/javascript" src="<%=basePath%>js/prod/common.js"></script>
<script type="text/javascript" src="<%=basePath%>js/prod/date.js"></script>
<%@ include file="/WEB-INF/pages/back/base/jsonSuggest.jsp"%>
<script type="text/javascript" src="<%=basePath%>js/prod/validate.js"></script>
<script type="text/javascript" src="<%=basePath%>js/prod/product.js"></script>
<script type="text/javascript" src="<%=basePath%>js/prod/prod_visa.js"></script>
<script type="text/javascript" src="<%=basePath%>js/base/log.js"></script>
<script type="text/javascript" src="<%=basePath%>js/prod/sensitive_word.js"></script>
<script type="text/javascript">
		  function visaValidTimeinit(){
			 var validTime='${product.visaValidTime}';
			 var visavalidetime=$("input[name=product.visaValidTime]").val();
			 if(validTime=="" && visavalidetime==""){
				  var type=$("input[name=product.visaType]:checked").val();
				  if(type=="GROUP_LEISURE_TORUS_VISA"){
					  $("#visaValidTime").val("根据行程，以使馆签发为准");  
				  }
			  }
		  }
		  
	</script>
</head>
<body>
	<div class="main main08">
		<div class="row1">
			<h3 class="newTit">
				销售产品信息
				<s:if test="product.productId != null">
					<jsp:include page="/WEB-INF/pages/back/prod/goUpStep.jsp"></jsp:include>
				</s:if>
			</h3>
			<jsp:include page="/WEB-INF/pages/back/prod/product_menu.jsp"></jsp:include>
		</div>
		<form method="post" action="<%=basePath%>prod/saveAsiaProduct.do"
			name="prodFrom" class="mySensitiveForm">
			<s:hidden name="product.productId" id="productId" />
			<s:hidden name="product.productType"/>
			<input type="hidden" name="has_visa_prod" value="true"/>
			<input type="hidden" name="product.subProductType" value="VISA"/>
			<div class="row2">
				<s:include value="/WEB-INF/pages/back/prod/base/header.jsp"></s:include>
				<!-- 签证新增属性 -->
				<table class="newTableB" border="0" cellspacing="0" cellpadding="0">
				
					<tr>
						<td><em>区域：<span class="require">[*]</span></em></td>
						<td colspan="3">
							<span style="display:none;">
								<s:radio name="product.isForegin" list="#{'N':'境内','Y':'境外'}"  listKey="key" listValue="value" labelSeparator="&nbsp;" />
	      					</span>	
	      					<s:select name="product.regionName" list="regionNamesList" listKey="code" listValue="name"></s:select>
						</td>
					</tr>
				
					<tr>
						<td><em>送签类型：<span class="require">[*]</span></em></td>
						<td colspan="3"><s:radio name="product.visaType"
								id="visaType" onclick="clearsDocumentdiv()"
								onchange="visaValidTimeinit()"
								list="#{'GROUP_LEISURE_TORUS_VISA':'团体旅游签证','PERSONAL_VISA':'个人旅游签证'}" /></td>
					</tr>
					<tr>
						<td style="width: 137px;"><em>签证有效期：<span
								class="require">[*]</span></em></td>
						<td colspan="3"><s:textfield name="product.visaValidTime"
								id="visaValidTime" cssClass="text1" /></td>
					</tr>
					<tr>
						<td><em>材料截止收取提前：</em></td>
						<td colspan="3"><s:textfield
								name="product.visaMaterialAheadDay" cssClass="text1" />天</td>
					</tr>
					<tr>
						<td><em>是否自备签：<span class="require">[*]</span></em></td>
						<td colspan="3"><s:radio name="product.visaSelfSign"
								list="#{'true':'是','false':'否'}"/></td> 
					</tr>
					<tr>
						<td><em>国家：<span class="require">[*]</span></em></td>
						<td><s:hidden name="product.country" id="product_country"/><input name="product_country_suggest" value="${product.country}" id="country"/></td>
						<td><em>送签城市：<span class="require">[*]</span></em></td>
						<td><s:select name="product.city"  id="city"
								list="#{'':'-- 请选择  --','SH_VISA_CITY':'上海送签','BJ_VISA_CITY':'北京送签','GZ_VISA_CITY':'广州送签','CD_VISA_CITY':'成都送签','TJ_VISA_CITY':'天津送签','WH_VISA_CITY':'武汉送签','SY_VISA_CITY':'沈阳送签'}" />
						
						<a href="javascript:void(0)" class="showDocument">显示材料</a>
						</td>
					</tr>
				</table>
				<div id="visa_document_div"></div>
				<s:include value="/WEB-INF/pages/back/prod/base/other_prop.jsp" />
			</div>
			<p class="main4Bottom">
				<input type="button" value="保存"
					class="button01 add_margin_left add_margin_left01 saveForm"
					ff="prodFrom" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<s:if test="product.productId != null">
					<a href="#log" class="showLogDialog"
						param="{'objectType':'PROD_PRODUCT','objectId':<s:property value="product.productId"/>}">查看操作日志</a>
				</s:if>
			</p>
		</form>
	</div>
</body>
</html>
<script type="text/javascript">
$(document).ready(function(){
	$('input[name=product.isForegin][value=Y]').attr('checked', true);
	$('input[name=product.isForegin][value=Y]').click();
});
</script>
