<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="lvp" uri="/tld/product.tld" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>super后台——产品行程打包</title>
<s:include value="/WEB-INF/pages/back/base/jquery.jsp"/>
<s:include value="/WEB-INF/pages/back/base/jsonSuggest.jsp"/>
<script type="text/javascript" src="<%=basePath%>js/timeprice/time.js"></script>
<script type="text/javascript" src="<%=basePath%>js/base/log.js"></script>
<script type="text/javascript" src="/pet_back/js/base/dialog.js"></script>
<script type="text/javascript">
	var has_traffic=true;
	var has_ticket=true;
	var has_hotel=true;
	var has_route=true;
</script>
<script type="text/javascript" src="<%=basePath%>js/prod/journey.js"></script>
<style type="text/css">
	.dn{width:50px;}
	.content span{display:block}
	.tableForm{margin:0 10px;}
	.tableForm input,.tableForm b,.tableForm select{position:relative;top:3px;}
	.tableForm em{margin:0 10px;}
	#journeyProductDiv{margin:0 5px;}
	.journeyProductDivTit,.titBottonB{margin:5px 20px;}
</style>
</head> 
<body>
<div class="main main02">
	<input type="hidden" name="productId" value="${product.productId}" id="productId"/>
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
       <table width="90%" border="0" cellspacing="0" cellpadding="0" id="journey_tb" class="newTable">
         <tr class="newTableTit">
           <td>编号</td>
           <td>时间范围</td>
           <td>行程标题</td>
           <td>出发地</td>
           <td>目的地</td>
           <td colspan="4">关联产品</td>
           <td>操作</td>
         </tr>
         <tr>
           <td>&nbsp;</td>
           <td>&nbsp;</td>
           <td>&nbsp;</td>
           <td>&nbsp;</td>
           <td>&nbsp;</td>
           <td>交通组</td>
           <td>酒店组</td>
           <td>门票组</td>
           <td>当地观光游组</td>
           <td>&nbsp;</td>
         </tr>
         <s:iterator value="prodJourneyList" var="journey">         
         <tr id="tr_pj_${journey.prodJourenyId}" pjId="${journey.prodJourenyId}">
           <td>${journey.prodJourenyId}</td>
           <td class="time">${journey.journeyTimeStr}</td>
           <td>${journey.fromPlace.name}到${journey.toPlace.name}</td>
           <td>${journey.fromPlace.name}</td>
           <td>${journey.toPlace.name}</td>
           <td class="traffic">
           		<div class="content">
           			<s:if test="#journey.trafficList!=null && #journey.trafficList.size()>0">
           			<s:iterator value="#journey.trafficList" id="pp">
	           			<span>${pp.prodBranch.prodProduct.productName}</span>
	           		</s:iterator>
	                </s:if>
           		</div>
           		<div>
	                <span><a href="#" class="edit" tt="TRAFFIC">修改产品</a></span>
           		</div>                
           </td>
           <td class="hotel">
           		<div class="content">
           			<s:if test="#journey.hotelList!=null && #journey.hotelList.size()>0">
           			<s:iterator value="#journey.hotelList" id="pp">
	           			<span>${pp.prodBranch.prodProduct.productName}</span>
	           		</s:iterator>
	                </s:if>
           		</div>
           		<div>
	                <span><a href="#" class="edit" tt="HOTEL">修改产品</a></span>
           		</div> 
           </td>
           <td class="ticket">
           		<div class="content">
           			<s:if test="#journey.ticketList && #journey.ticketList.size()>0">
           			<s:iterator value="#journey.ticketList" id="pp">
	           			<span>${pp.prodBranch.prodProduct.productName}</span>
	           		</s:iterator>
	                </s:if>
           		</div>
           		<div>
	                <span><a href="#" class="edit" tt="TICKET">修改产品</a></span>
           		</div> 
           </td>
           <td class="route">
           		<div class="content">
           			<s:if test="#journey.routeList && #journey.routeList.size()>0">
           			<s:iterator value="#journey.routeList" id="pp">
	           			<span>${pp.prodBranch.prodProduct.productName}</span>
	           		</s:iterator>
	                </s:if>
           		</div>
           		<div>
	                <span><a href="#" class="edit" tt="ROUTE">修改产品</a></span>
           		</div> 
           </td>
           <td class="op">
           		<a href="#delete" class="journey_delete" result="${journey.prodJourenyId}">删除</a>
           		<a href="#edit" class="edit_journey_time" result="${journey.prodJourenyId}" time="${journey.journeyTime}">修改行程时间</a>
           		<a href="#log" class="showLogDialog"
									param="{'parentType':'PROD_PRODUCT_JOURNEY','parentId':${journey.prodJourenyId}}">操作日志</a>
           </td>
         </tr>
         </s:iterator>
       </table>
        <div>
		<input class="newForm button01 add_margin_left add_margin_left01" type="button" value="新建行程" />
		<input class="newPackForm button01 add_margin_left add_margin_left01" type="button" value="新建套餐" />
	   </div>  
	   
       <div style="display: none" id="editJourneyTimeDiv">     
       	<form action="<%=basePath%>prod/saveJourneyTime.do" onsubmit="return false"><s:hidden name="prodJourney.prodJourenyId"/>       		
       		<table>
       			<tr>       			
       				<td colspan="2">行程天数:<span style="display:none"><s:textfield name="prodJourney.minTime.days" cssClass="dn"/>天<s:textfield name="prodJourney.minTime.nights" cssClass="dn"/>晚--</span><s:textfield name="prodJourney.maxTime.days" cssClass="dn"/>天<s:textfield name="prodJourney.maxTime.nights" cssClass="dn"/>晚</td>
       			</tr>
       		</table>
       	</form>  
       </div>
       <div style="display: none" id="newFormDiv">
       <form action="<%=basePath%>prod/saveSelfPackJourney.do" onsubmit="return false"  name="journeyForm"><s:hidden name="prodJourney.prodJourenyId"/>
       		<s:hidden name="prodJourney.productId"/>
       		<table>
       			<tr>       			
       				<td colspan="2"><span class="rooter_nums">行程天数:</span><span style="display:none"><s:textfield name="prodJourney.minTime.days" cssClass="dn"/>天<s:textfield name="prodJourney.minTime.nights" cssClass="dn"/>晚--</span><s:textfield name="prodJourney.maxTime.days" cssClass="dn"/>天<s:textfield name="prodJourney.maxTime.nights" cssClass="dn"/>晚</td>
       			</tr>
       			<tr>	
					<td>
       					<span class="rooter_nums">出发地：</span><s:select name="prodJourney.fromPlaceId" list="prodPlaceList" listKey="placeId" listValue="placeName"/>
					</td>       			
					<td>
						<span class="rooter_nums">目的地：</span><s:select name="prodJourney.toPlaceId" list="prodPlaceList" listKey="placeId" listValue="placeName"/>
					</td>
       			</tr>
       			<tr>
       				<td colspan="2"><p><em class="button" id="journeyFormBtn" ff="journeyForm">保存</em></p></td>
       			</tr>
       		</table>
       	</form>
       </div>
       <div id="showPackDiv"></div>
       <div id="journeyProductDiv" style="display: none"></div>     
       <div id="create_journey_pack_div" url="${basePath }/prod/toEditPack.do?productId=${product.productId }"></div>
  </div><!--row2 end--> 
</div><!--main01 main05 end-->
</body>
</html>


