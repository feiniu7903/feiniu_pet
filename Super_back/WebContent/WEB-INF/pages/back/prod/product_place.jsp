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
<title>super后台——产品标地信息</title>
<%@ include file="/WEB-INF/pages/back/base/jquery.jsp"%>
<%@ include file="/WEB-INF/pages/back/base/jsonSuggest.jsp"%>
<script type="text/javascript" src="<%=basePath%>js/prod/place.js"></script>
<script type="text/javascript" src="<%=basePath%>js/base/log.js"></script>
</head>
 
<body>
<div class="main main07">
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
   <div  class="row2">
   	<form name="searchPlace"><input type="hidden" name="place.productId" value="${productId}"/>
   	<s:if test="product.productType!='TRAFFIC'">
		<label class="title_list title_list01">标的</label>
		<input type="text" class="title_list title_list02" id="searchPlace"/>
		<input type="hidden" name="place.placeId" id="comPlaceId"/>
		<input type="button" value="新增" class="addPlace button add_place_list" ff="searchPlace" style="display:inline;"/>
	</s:if>
		<a href="#log" class="showLogDialog" param="{'objectType':'PROD_PRODUCT_PLACE','parentType':'PROD_PRODUCT','parentId':${productId}}">查看操作日志</a>   	
   	</form>   	
   </div>
   <div class="row2">
   		<table border="0" cellspacing="0" cellpadding="0" id="place_tb" class="newTable">
          <tr class="newTableTit">
            <td>ID号</td>
            <td>名称</td>
            <td>出发地</td>
            <td>目的地</td>                        
            <td>操作</td>
          </tr>
          <s:iterator value="placeList">
		          <tr id="tr_<s:property value="productPlaceId"/>" productPlaceId="<s:property value="productPlaceId"/>">
		          	<td><s:property value="placeId"/></td>
		            <td><s:property value="placeName"/> </td>		            
		            <td class='from'><s:if test="from=='true'">true</s:if></td>
		            <td class='to'><s:if test="to=='true'">true</s:if></td>
		            <td>
						<s:if test="product.productType!='TRAFFIC'"><a href='#delete' class='delete'>删除</a>&nbsp;<a href='#from' class='changeFT' type='FROM'>出发地</a><a href='#to' class='changeFT' type='to'>目的地</a></s:if>
						&nbsp;		
					</td>
		          </tr>
           </s:iterator>
        </table>
    </div><!--row2 end-->
    <s:if test='product.productType!="OTHER"'>  
    <div  class="row2">
    <table border="0" cellspacing="0" cellpadding="0" id="place_tb" class="newTable">
         <tr>
         	<td>
         	产品标的录入规则：<br/><br/>
         			<s:if test='product.productType=="HOTEL"'>
	                	标的设置为所属酒店并设为目的地，可添加城市为标的但不可设为目的地<br/>
	                </s:if>
	                <s:elseif test='product.productType=="ROUTE" && !product.hasSelfPack()'>
	                	出发地与目的地必须选择产品实际出发地城市与目的地城市,其他标的必须为费用中所含的景点,不可将行程中自费景区设为标的<br/> 
	                </s:elseif>
	                <s:elseif test='product.productType=="OTHER"'>
	                
	                </s:elseif>
	                <s:elseif test='product.productType=="TICKET"'>
	               		单门票、套票：标的设置为门票所属景区且设为目的地, 可添加城市为标的但不可设为目的地<br/><br/>
		   	 			联票：标的设置为联票中所含景区,无须设目的地，可添加城市为标的但不可设为目的地 <br/>
	                </s:elseif>
	                <s:elseif test='product.productType=="ROUTE" && product.hasSelfPack()'>
	                	出发地与目的地必须选择产品实际出发地城市与目的地城市,其他标的必须为费用中所含的景点,不可将行程中自费景区设为标的<br/> 
	                </s:elseif>
			</td>
        </tr>
    </table>
   </div>
   </s:if>
</div><!--main01 main05 end-->
</body>
</html>


