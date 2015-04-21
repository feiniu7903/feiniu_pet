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
<title>super后台——行程展示</title>
<link href="<%=basePath%>style/houtai.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<%=basePath%>js/base/jquery-1.4.4.min.js"></script>
<script type="text/javascript" src="<%=basePath%>js/prod/journey.js"></script>
<script type="text/javascript" src="<%=basePath%>js/prod/view_journey.js"></script>
<script type="text/javascript" src="<%=basePath%>js/base/ajaxupload.js"></script>
<script type="text/javascript" src="<%=basePath%>js/base/log.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/ui/jquery-ui-1.8.5.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/base/jquery.datepick-zh-CN.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/base/jquery.jsonSuggest.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/base/remoteUrlLoad.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/base/jquery.showLoading.min.js"></script>
<link href="<%=request.getContextPath()%>/themes/base/showLoading.css" rel="stylesheet" type="text/css" />
<link href="<%=request.getContextPath()%>/themes/suggest/jquery.suggest.css" rel="stylesheet" type="text/css" />
<link href="<%=request.getContextPath()%>/themes/base/jquery.ui.all.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<%=basePath%>js/prod/sensitive_word.js"></script>
<script type="text/javascript">	
function addJourney(){
	$('#addJourney').show();
	$('#showTips').empty();
	$('#showTips').hide();
	$('#showJourneyImages').empty();
	$('#showJourneyImages').hide();
	$('#saveButton').show();
	cleanAddJourneyForm($("form[name=productJourneyForm]"));
}
function addTips(productId,journeyId){
	$('#addJourney').hide();
	$('#showJourneyImages').empty();
	$('#showJourneyImages').hide();
	$('#saveButton').hide();
	showTipsList(productId,journeyId);
}
function uploadJourneyPic(productId,journeyId){
	$('#addJourney').hide();
	$('#saveButton').hide();
	$('#showTips').empty();
	$('#showTips').hide();
	showJourneyImageList(productId,journeyId);
}
</script>
</head>
 
<body>
<div class="main main02">
	<div class="row1">
    	<h3 class="newTit">销售产品信息 1</h3>
    </div>
   <div class="row2">
   		<dl class="trave">
        	<dt><strong class="add_margin_left">行程展示</strong><s:if test="multiJourneyName != null">&nbsp;&nbsp;&nbsp;${multiJourneyName }&nbsp;&nbsp;&nbsp;
        	<a style="font-weight:normal;color:blue;" href="/super_back/view/queryMultiJourneyList.do?productId=${product.productId }">返回行程</a></s:if></dt>
            <dd>
            <form class="mySensitiveForm">
             <table class="newTable" width="90%" border="0" cellspacing="0" cellpadding="0" id="productJourney">
                <tr class="newTableTit">
                   <td style="width:5%">第*天 </td>
                   <td style="width:10%">标题</td>
                   <td style="width:10%">用餐</td>
                   <td style="width:10%">住宿</td>
                   <td style="width:10%">交通</td>
                   <td style="width:45%">内容</td>
                 </tr>
                 <s:iterator value="dataList">
                 <tr id="tr_<s:property value="journeyId"/>" journeyId="<s:property value="journeyId"/>" productId="<s:property value="productId"/>">
                 	<td><s:property value='seq'/></td>
                 	<td><s:property value='title'/></td>
                 	<td><s:property value='dinner'/><input type="hidden" name="dinner${journeyId}" value="<s:property value='dinner'/>" class="sensitiveVad" /></td>
                 	<td><s:property value='hotel'/><input type="hidden" name="hotel${journeyId}" value="<s:property value='hotel'/>" class="sensitiveVad" /></td>
                 	<td><s:property value='trafficDesc'/></td>
                 	<td><s:property value='content'/><input type="hidden" name="content${journeyId}" value="<s:property value='content'/>" class="sensitiveVad" /></td>
                 </tr>
                 </s:iterator>
               </table>
               </form>
            </dd>
        </dl><!--trave end--> 

   </div><!--row5 end-->
</div><!--main01 main05 end-->

</body>
</html>

