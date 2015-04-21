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
		<title>优惠券(活动)列表</title>
		<link rel="stylesheet" type="text/css" href="<%=basePath%>css/ui-common.css"></link>
		<link rel="stylesheet" type="text/css" href="<%=basePath%>css/ui-components.css"></link>
		<link rel="stylesheet" type="text/css" href="<%=basePath%>css/panel-content.css"></link>
		
		<jsp:include page="/WEB-INF/pages/pub/jquery.jsp" />
		<jsp:include page="/WEB-INF/pages/pub/suggest.jsp" />
		<jsp:include page="/WEB-INF/pages/pub/timepicker.jsp" />
		<script type="text/javascript" src="<%=basePath%>/js/base/log.js"></script>
		<script type="text/javascript" src="http://pic.lvmama.com/js/ui/lvmamaUI/lvmamaUI.js"  charset="utf-8"></script>
	</head>
	<body>
		<div id="newMarkCouponDiv" style="display: none"></div>

		<div class="ui_title">
			<ul class="ui_tab">
				<li class="active"><a href="#">优惠活动列表</a></li>
			</ul>
		</div>
		
		<div class="iframe-content">
			<div class="p_box">
				<form action="<%=basePath%>mark/coupon/queryCouponList.do" method="get">
					<table class="p_table form-inline" width="100%">
						<tr>
							<td class="p_label">优惠券名称：</td>
							<td>
								<input id="couponName"  name="couponName" value="${couponName}" class="newtext1"/>
							</td>
							<td class="p_label">优惠券类型：</td>
							<td>
								<input type="radio"  id="couponTypeA" name="couponType" value="A" <s:if test='couponType == "A"'>checked</s:if>>A类（规定时间无限次使用）</input>
								<input type="radio"  id="couponTypeB" name="couponType" value="B" <s:if test='couponType == "B"'>checked</s:if>>B类（规定时间只能使用一次）</input>	
							</td>
							<td class="p_label">类型：</td>
							<td>
								<input type="radio" id="couponTargetProduct" name="couponTarget" value="PRODUCT" <s:if test='couponTarget == "PRODUCT"'>checked</s:if>>产品</input>
								<input type="radio" id="couponTargetOrder" name="couponTarget" value="ORDER" <s:if test='couponTarget == "ORDER"'>checked</s:if>>订单</input>	
							</td>	
						</tr>
						<tr>
							<td class="p_label">状态：</td>
							<td>
								<input type="radio" id="validAll" name="valid" value="all" <s:if test='valid == "all"'>checked</s:if>>所有</input>
								<input type="radio" id="validTrue" name="valid" value="true" <s:if test='valid == "true"'>checked</s:if>>启用</input>
								<input type="radio" id="validFalse" name="valid" value="false" <s:if test='valid == "false"'>checked</s:if>>禁用</input>	
							</td>
							<td class="p_label">有效期：</td>
							<td><input id="termValidityBeginTime" type="text" class="newtext1"
								name="termValidityBeginTime" value="${termValidityBeginTime}"/> ~<input id="termValidityEndTime" type="text"
								class="newtext1" name="termValidityEndTime" value="${termValidityEndTime}"/></td>
								
							<td class="p_label">优惠券开头:</td>
							<td><input name="firstCode"  id="firstCode" value="${firstCode}" class="newtext1"/></td>
						</tr>
						<tr>
							<td class="p_label">优惠券代码：</td>
							<td><input id="couponCode" name="couponCode" value="${couponCode}" class="newtext1"/></td>
							<td class="p_label">渠道名：</td>
							<td><input id="channelName" name="channelName" value="${channelName}" class="newtext1"/></td>
							<td colspan="2" align="right">
								
							</td>
						</tr>											
					</table>
					<p class="tc mt20"><button class="btn btn-small w5" type="submit">查询</button>　<button id="clearBtn" class="btn btn-small w5" type="button">清空</button> <button permCode="1302" id="addNewMarkCoupon" class="btn btn-small w5" type="button">新增优惠活动</button></p>
				</form>
			</div>
			<div class="p_box">
				<table class="p_table table_center">
					<tr>
					    <th width="40px">批次号</th>
						<th width="90px">名称</th>
						<th width="50px">优惠类型</th>
						<th width="100px">渠道名</th>
						<th width="60px">优惠券开头</th>
						<th width="50px">优惠方式</th>
						<th width="100px">优惠规则计算方式</th>
						<th width="65px">开始时间</th>
						<th width="65px">结束时间</th>
						<th width="30px">状态</th>
						<th width="30px">类型</th>
						<th width="190px">操作</th>
					</tr>
					<s:iterator value="markCouponList" var="markCoupon">
						<tr>
						    <td>${markCoupon.couponId}</td>
							<td>${markCoupon.couponName}</td>
							<td>
							 <s:if test='#markCoupon.withCode=="true"'>
							                 优惠券
							 </s:if>
							 <s:if test='#markCoupon.withCode=="false"'>
							                     优惠活动
							 </s:if>
							</td>
							<td>${markCoupon.channelName}</td>
							<td>${markCoupon.firstCode}</td>
							<td>${markCoupon.favorModeZh}</td>
							<td>${markCoupon.favorRuleZh}</td>
							<td><s:date name="beginTime" format="yyyy-MM-dd"/></td>
							<td><s:date name="endTime" format="yyyy-MM-dd"/></td>
							<td><span id="validSpan${markCoupon.couponId}">${markCoupon.stateZh}</span></td>
							<td>${markCoupon.targetZh}</td>
							<td>
								<a permCode="1303" href="javascript:editValidStatus(${markCoupon.couponId},(new Date).getTime());">开启/关闭</a>&nbsp;
								<a permCode="1304" href="javascript:editMarkCoupon(this,${markCoupon.couponId});">修改</a>&nbsp;
								<s:if test='#markCoupon.withCode=="true" && #markCoupon.valid=="true"'>
								<a permCode="1305" href="javascript:createMarkCouponCodes(this,${markCoupon.couponId});">生成优惠券</a>
								</s:if>
								<s:if test='#markCoupon.withCode=="false" || #markCoupon.valid=="false"'>
								<font color="grey">生成优惠券</font>
								</s:if>
								<a permCode="1307" href="#log" class="showLogDialog" param={"objectId":${markCoupon.couponId},"objectType":'COUPON_BUSINESS_BIND'}>日志</a>
								<s:if test="!showBoundType">
								<a permCode="1306" href="javascript:addProduct(${markCoupon.couponId});"  >绑定产品</a>
								</s:if>
								<s:else>
								<font color="grey">绑定产品</font>
								</s:else>
								<br/>
								<s:if test="!showBoundType">
								<a permCode="1307" href="javascript:editBindingProduct(${markCoupon.couponId});"> 修改绑定产品</a>
								</s:if>
								<s:else>
								 <font color="grey">修改绑定产品</font>
								</s:else>
								<s:if test="!showBoundType">
								<a permCode="1307" href="javascript:addSubProductType(${markCoupon.couponId});">绑定产品类型 </a>
								</s:if>
								<s:else>
								<font color="grey">绑定产品类型 </font>
								</s:else>
							</td>
						</tr>
					</s:iterator>
					<tr>
	     				<td colspan="3"> 总条数：<s:property value="pagination.totalResultSize"/> </td>
	     				<td colspan="9" align="left">
	     					<s:property escape="false" value="@com.lvmama.comm.utils.Pagination@pagination(pagination.pageSize,pagination.totalPageNum,pagination.url,pagination.currentPage)"/>
	     				</td>
	    			</tr>		
				</table>			
			</div>
		</div>
		
		<script type="text/javascript">
	        $(function(){
	            $('#termValidityBeginTime').datepicker({dateFormat: 'yy-mm-dd'});
	            $('#termValidityEndTime').datepicker({dateFormat: 'yy-mm-dd'});
	            
	            //清空.
	            $("#clearBtn").click(function() {
	            	 $("#couponName").val("");
	            	 $("#channelName").val("");
	            	 $("#couponTypeA").attr("checked","");
	            	 $("#couponTypeB").attr("checked","");
	            	 $("#couponTargetProduct").attr("checked","");
	            	 $("#couponTargetOrder").attr("checked","");
	            	 $("#validAll").attr("checked","");
	            	 $("#validTrue").attr("checked","");
	            	 $("#validFalse").attr("checked","");
	            	 $("#termValidityBeginTime").val("");
	            	 $("#termValidityEndTime").val("");
	            	 $("#firstCode").val("");
	            	 $("#couponCode").val("");
	            });
	            //新增优惠券(活动).
	            $("#addNewMarkCoupon").click(function() {
	            	$("#newMarkCouponDiv").load("<%=basePath%>mark/coupon/editMarkCoupon.do",function() {
	            		$(this).dialog({
	            			modal:true,
	            			title:"新增优惠券(活动)",
	            			width:950,
	            			height:650
	                	});
	            	});
	            });
	         
	            
	        });
	        //修改优惠券(活动).
	        function editMarkCoupon(arg,couponId) {
	        	$("#newMarkCouponDiv").load("<%=basePath%>mark/coupon/editMarkCoupon.do?_=" + (new Date).getTime() + "&couponId=" + couponId,function() {
	        		$(this).dialog({
	        			modal:true,
	        			title:"修改优惠券(活动)",
	        			width:950,
	        			height:650
	            	});
	        	});
	        }
	        
	        //生成优惠券.
	        function createMarkCouponCodes(arg,couponId) {
	        	$("#newMarkCouponDiv").load("<%=basePath%>mark/coupon/createMarkCouponCodes.do?_=" + (new Date).getTime() + "&couponId="+couponId,function(){
	        		$(this).dialog({
	        			modal:true,
	        			title:"生成优惠券",
	        			width:950,
	        			height:650
	            	});
	        	});
	        }
	        //绑定产品.
	        function addProduct(couponId) {
	        	$("#newMarkCouponDiv").load("<%=basePath%>mark/coupon/addProduct.do?_=" + (new Date).getTime() + "&couponId="+couponId,function(){
	        		$(this).dialog({
	        			modal:true,
	        			title:"绑定产品",
	        			width:950,
	        			height:800
	            	});	
	        	});
	        }
	        
	        //修改绑定产品.
	        function editBindingProduct(couponId) {
	        	$("#newMarkCouponDiv").load("<%=basePath%>mark/coupon/editBindingProduct.do?_=" + (new Date).getTime() + "&couponId="+couponId,function(){
	        		$(this).dialog({
	        			modal:true,
	        			title:"修改绑定产品",
	        			width:950,
	        			height:630
	            	});	
	        	});
	        }
	        //绑定产品类型.
	        function addSubProductType(couponId) {
	        	$("#newMarkCouponDiv").load("<%=basePath%>mark/coupon/addSubProductType.do?_=" + (new Date).getTime() + "&couponId="+couponId,function(){
	        		$(this).dialog({
	        			modal:true,
	        			title:"绑定产品类型",
	        			width:950,
	        			height:800
	            	});	
	        	});	        	
	        }
	        
	        //开启/关闭此优惠券(活动).
	        function editValidStatus(couponId,date) {
	        	 var url = "<%=basePath%>mark/coupon/editValidStatus.do";
	        	 $.ajax({
	        	 	url: url,
	        	 	data: {"couponId":couponId,"date":date},
	        	 	dataType:"json",
	        	 	success: function(result) {
		        		if (result.success) {
		        			alert('更新成功');
		        			document.location.reload();
		        		} else {
		        			alert(result.errorMessage);
		        		}
	        	 	}
	        	 });
	        }
	   </script>		
	</body>	
</html>


