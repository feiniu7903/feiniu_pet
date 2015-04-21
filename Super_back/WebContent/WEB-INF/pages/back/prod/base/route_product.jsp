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
<%@ include file="/WEB-INF/pages/back/base/jquery.jsp"%>
<script type="text/javascript" src="<%=basePath%>js/prod/common.js"></script>
<script type="text/javascript" src="<%=basePath%>js/prod/date.js"></script>
<%@ include file="/WEB-INF/pages/back/base/jsonSuggest.jsp"%>
<script type="text/javascript" src="<%=basePath%>js/prod/validate.js"></script>
<script type="text/javascript" src="<%=basePath%>js/prod/product.js"></script>
<script type="text/javascript" src="<%=basePath%>js/prod/route.js"></script>
<script type="text/javascript" src="<%=basePath%>js/prod/prod_visa.js"></script>
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
  <form method="post" action="<%=basePath%>prod/${saveProductUrl}.do" name="prodFrom" class="mySensitiveForm">
  <s:hidden name="product.productId" id="productId"/>
  <s:hidden name="product.productType" />
  <input type="hidden" id="subProductType" value="<s:property value='product.subProductType'/>"/>
  <div class="row2">    
        	<s:include value="/WEB-INF/pages/back/prod/base/header.jsp"></s:include>
            <!-- 线路新增属性 -->
            <table border="0" cellspacing="0" cellpadding="0" class="newTableB" >
            	<s:if test="!product.hasSelfPack()">
	            	<tr>
	            		<td style="width:137px;"><em>是否多行程：<span class="require">[*]</span></em></td>
	            		<td colspan="3">
	            			<s:if test='product.productId == null'>
	            				<s:radio name="product.isMultiJourney" list="#{'Y':'是','N':'否'}"></s:radio>
	            			</s:if>
							<s:else>
								${product.zhMultiJourney }
							</s:else>
						</td>
	            	</tr>
            	</s:if>
            	<tr>
            		<td style="width:137px;"><em>行程：<span class="require">[*]</span></em></td>
            		<td colspan="3"><s:textfield name="product.days"/>天</td>
            	</tr>
            	<tr>
            		<td><em>线路类型：<span class="require">[*]</span></em></td>
            		<td colspan="3"><s:radio list="subProductTypeList" name="product.subProductType" listKey="code" listValue="name"/>
            		</td>
            	</tr>
            	<tr>
            		<td><em>区域划分：</em></td>
		            <td colspan="2">
		       		<s:select name="product.regionName" list="regionNamesList" listKey="code" listValue="name"></s:select>
            		</td>
            		<td>
            		<span id="qiFlagId" style="display:none"><s:checkbox fieldValue="true" name="product.qiFlag"/>实时变价</span>
            		</td>	
            	</tr>
            	<tr>
            		<td><em>提前确定成团小时数：</em></td>
            		<td><s:textfield name="product.aheadConfirmHours" cssClass="text1" /></td>
            		<td><em>最少成团人数：</em></td>
            		<td><s:textfield name="product.initialNum" cssClass="text1" /></td>
            	</tr>
            	<tr>
            		<td><em>团号前辍：</em></td>
            		<td><s:textfield name="product.travelGroupCode" cssClass="text1" /></td>
            		<td><em>定金：</em></td>
            		<td><s:textfield name="product.payDeposit" cssClass="text1" /></td>
            	</tr>
            	<tr>
		  			<td><em>组团类型：</em></td>
		  			<td><s:radio list="groupTypeList" name="product.groupType" listKey="code" listValue="name"/></td>
		  			<td class="visa_document" style="display:none"><em>送签类型：<span class="require">[*]</span></em></td>
					<td class="visa_document" style="display:none"><s:radio name="product.visaType"
							id="visaType" onclick="clearsDocumentdiv()"
							list="#{'BUSINESS_VISA':'商务签证','VISIT_VISA':'探亲访友签证','MATCH_VISA':'赛事签证','GROUP_LEISURE_TORUS_VISA':'团体旅游签证','PERSONAL_VISA':'个人旅游签证','REGISTER_VISA':'签注'}" /></td>
		  		</tr>
		  		<tr class="visa_document" style="display:none">
						<td><em>国家：<span class="require">[*]</span></em></td>
						<td><s:hidden name="product.country" id="product_country"/><input name="product_country_suggest" value="${product.country}" id="country"/></td>
						<td><em>送签城市：<span class="require">[*]</span></em></td>
						<td><s:select name="product.city"  id="city"
								list="#{'':'-- 请选择  --','SH_VISA_CITY':'上海送签','BJ_VISA_CITY':'北京送签','GZ_VISA_CITY':'广州送签','CD_VISA_CITY':'成都送签','TJ_VISA_CITY':'天津送签','WH_VISA_CITY':'武汉送签','SY_VISA_CITY':'沈阳送签','REGION':'户籍所在地'}" />
						
						<a href="javascript:void(0)" class="showDocument">显示材料</a>
						</td>
				</tr>
            </table>
            <div id="visa_document_div"></div>
            <!-- 线路新增属性 -->  
        <s:include value="/WEB-INF/pages/back/prod/base/other_prop.jsp"/> 
  </div><!--row2 end-->
  <s:if test="!product.hasSelfPack() && !product.IsAperiodic()">   <!-- 超级自由行现在不签合同 --> 
  <div class="row2" id="contract_info_div_id">
   	<h2 class="titBotton">电子合同签约<input type="checkbox" name="productEContract" value="true" checked  product_EContract="${productEContract}"/></h2>
  	<table border="0" cellspacing="0" cellpadding="0" class="newTableB" id="productEContract_content">
  		<tr>
  			<td><em>电子合同范本：<span class="require">[*]</span></em></td>
  			<td><s:select list="econtractTemplateList" name="prodEContract.eContractTemplate" listKey="code" listValue="name"/></td>
  		</tr>
  		<tr>
  			<td><em>旅游手续：<span class="require">[*]</span></em></em></td>
  			<td>由旅游者自行办理的旅游手续</td>
  		</tr>
  		<tr>
  			<td>&nbsp;</td>
  			<td><s:checkboxlist name="productTravelFormalities" list="travelFormalitiesList" listKey="code" listValue="name"/><s:textfield name="prodEContract.otherTravelFormalities" disabled="true" value="/"/></td>
  		</tr>
  		<tr>
  			<td><em>导游服务：</em></td>
  			<td><s:radio list="guideServiceList" name="productGuideServices" listKey="code" listValue="name"/></td>
  		</tr>
  		<tr>
  			<td><em>组团方式：<span class="require">[*]</span></em></td>
  			<td><s:radio list="groupTypeList" name="productGroupTypes" listKey="code" listValue="name"/>(被委托组团<s:textfield name="prodEContract.agency" disabled="true" />)</td>
  			<td><em>保证金金额:</em></td>
  			<td><s:textfield name="prodEContract.margin"  cssClass="text1"/></td>
  		</tr>
  		<%--
  		<tr>
  			<td>&nbsp;</td>
  			<td>地接社名称/地址/联系人/联系方式/电话<span class="require">[*]</span></td>
  		</tr>
  		<tr>
  			<td>&nbsp;</td>
  			<td><s:textfield name="prodEContract.agencyAddress" cssStyle="width:500px"/></td>
  		</tr>
  		 --%>
  		<tr>
  			<td><em>其他条款：</em></td>
  			<td><s:textarea name="prodEContract.complement" cssStyle="width:400px" rows="5"/></td>
  		</tr>
  	</table>
  </div>  
  </s:if>
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
