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
<script type="text/javascript" src="<%=request.getContextPath()%>/js/prod/sensitive_word.js"></script>
<link href="<%=request.getContextPath()%>/themes/base/showLoading.css" rel="stylesheet" type="text/css" />
<link href="<%=request.getContextPath()%>/themes/suggest/jquery.suggest.css" rel="stylesheet" type="text/css" />
<link href="<%=request.getContextPath()%>/themes/base/jquery.ui.all.css" rel="stylesheet" type="text/css" />

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
    	<!-- <a href="#">添加新产品</a> --></h3>
        <div class="nav">
           <jsp:include page="/WEB-INF/pages/back/prod/product_menu.jsp"></jsp:include>
		</div>
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
                   <!-- 
                   <td>游玩景点</td> -->
                   <td style="width:10%">操作</td>
                 </tr>
                 <s:iterator value="dataList">
                 <tr id="tr_<s:property value="journeyId"/>" journeyId="<s:property value="journeyId"/>" productId="<s:property value="productId"/>">
                 	<td><s:property value='seq'/></td>
                 	<td><s:property value='title'/><input type="hidden" name="title${journeyId}" value="<s:property value='title'/>" class="sensitiveVad" /></td>
                 	<td><s:property value='dinner'/><input type="hidden" name="dinner${journeyId}" value="<s:property value='dinner'/>" class="sensitiveVad" /></td>
                 	<td><s:property value='hotel'/><input type="hidden" name="hotel${journeyId}" value="<s:property value='hotel'/>" class="sensitiveVad" /></td>
                 	<td><s:property value='trafficDesc'/></td>
                 	<td><s:property value='content'/><input type="hidden" name="content${journeyId}" value="<s:property value='content'/>" class="sensitiveVad" /></td>
                 	<td><a href='#editJourney' class='editJourney'>修改</a>|<a href="view/doDelete.do?journeyId=${journeyId}&productId=${productId}&multiJourneyId=${multiJourneyId}">删除</a>|
                 		<a href="javascript:addTips(${productId},${journeyId})">小贴士维护</a>|<a href="javascript:uploadJourneyPic(${productId},${journeyId})">上传图片</a></td>
                 </tr>
                 </s:iterator>
               </table>
               </form>
            </dd>
        </dl><!--trave end--> 
        
        
        <div id="addJourney" style="display: none">
        <p><font class="add_margin_left">行程维护</font></p>
        <form action="view/saveProductJourney.do" method="post" name="productJourneyForm"> 
        <input type="hidden" name="viewJourney.multiJourneyId" value="${multiJourneyId }" />
        <input type="hidden" name="viewJourney.productId" value="${productId}"/>
        <input type="hidden" name="viewJourney.journeyId" />
   		<table class="newTableB" width="80%"  cellspacing="0" cellpadding="0">
           	<tr>
				<td style="text-align: right;"><em>第：<span class="require">[*]</span></em></td>
				<td colspan="4">
				<input type="text" class="text1" name="viewJourney.seq" style="width:40px" />天</td>
				<!--<s:textfield cssClass="text1" name="viewJourney.seq" cssStyle="width:40px;"/>天-->
			</tr>
			<tr>
				<td><em>标题：<span class="require">[*]</span></em></td>
				<td>
					<!--<s:textfield cssClass="text1" name="viewJourney.title"/>-->
					<input type="text" class="text1 sensitiveVad" name="viewJourney.title" maxlength="50"/>
				</td>
				<td colspan="3"><strong>交通：</strong>
					<s:iterator value="trafficList"><input type="checkbox" name="trafficListValues" class="com.lvmama.back.web.utils.CheckboxList" value="${code}" /><s:property value="name" /></s:iterator>
				</td>
			</tr>
			<tr>
				<td><em>内容：<span class="require">[*]</span></em></td>
				<td colspan="4"><textarea id="viewJourneyContent" name="viewJourney.content" style="text-align: left ; font-family:'Arial'; font-size: 13px; color:#000000; 
				font-style:normal; font-weight:bold; text-decoration:none" class="sensitiveVad" cols="120" rows="5">上午&#13;&#10;&#13;&#10;下午&#13;&#10;&#13;&#10;晚上
				</textarea></td>
			</tr>    
			<tr>
				<td><em>用餐：</em></td>
				<td colspan="4"><!--早餐：
					<select name="breakfast" id="breakfast">
						<option value="含">含</option>
						<option value="敬请自理">敬请自理</option>
					</select>&nbsp;&nbsp;&nbsp;午餐：
					<select name="lunch" id="lunch">
						<option value="含">含</option>
						<option value="敬请自理">敬请自理</option>
					</select>&nbsp;&nbsp;&nbsp;晚餐：
					<select name="supper" id="supper">
						<option value="含">含</option>
						<option value="敬请自理">敬请自理</option>
					</select>
					<s:textfield cssClass="text1" name="viewJourney.dinner" cssStyle="width:400px;"/> --> 
					<input type="text" class="text1 sensitiveVad" name="viewJourney.dinner" style="width:400px;" maxlength="200"/>早餐：含、中餐：含、晚餐：含  <font color="red">或</font> 早餐：敬请自理、中餐：敬请自理、晚餐：敬请自理
				</td>
			</tr>
			<tr>
				<td><em>住宿：</em></td>
				<td colspan="4">
				<!--<s:textfield cssClass="text1" name="viewJourney.hotel" />-->
				<input type="text" class="text1 sensitiveVad" name="viewJourney.hotel" />
				</td>
			</tr> 
   		</table>
        </form> 
        </div>
        
        <div id ="showJourneyImages" style="display: none"></div>
        
        <div id ="showTips" style="display: none">
         </div>
         <div id="divspace2" style="display: none;height: 10px;">&nbsp;</div>
         
         <span class="msg"></span>
         
       	 <table class="newTable" width="90%" border="0" cellspacing="0" cellpadding="0">
			<tr>
              <td>
              	<input type="button" value="新建" onclick="addJourney()" class="button01 add_margin_left add_margin_left01"/>
	       		<span id="saveButton"><input type="submit" value="保存" id="productJourneySaveButton" class="button01 add_margin_left add_margin_left01" ff="productJourneyForm"/></span>
	       		<a href="#log" class="showLogDialog" param="{'parentType':'PROD_PRODUCT','objectType':'PROD_PRODUCT_JOURNEY','parentId':${productId}}">查看操作日志</a>
	       	  </td>
            </tr>
		 </table>  
       	
   </div><!--row5 end-->
</div><!--main01 main05 end-->

</body>
</html>

