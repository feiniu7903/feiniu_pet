<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> 
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<base href="<%=basePath%>" />
		<title>super——后台下单</title>
		<meta http-equiv="pragma" content="no-cache" />
		<meta http-equiv="cache-control" content="no-cache" />
		<meta http-equiv="expires" content="0" />
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3" />
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
		<s:include value="/WEB-INF/pages/back/base/jquery.jsp" />
		<s:include value="/WEB-INF/pages/back/base/jsonSuggest.jsp" />
		<script type="text/javascript" src="${basePath}js/prod/date.js"></script>
	</head>
	<body>
		<div class="main main01">
			<!--mainTop end-->
			<div class="mainmid" style="display: none;">
				<div class="tit">
					用户查询：
					<input name="text" type="text" />
					<span>（您可以输入手机号码，用户名，邮箱，会员卡等信息查找用户）</span><a
						href="javascript:void(0)" class="button"
						data-biaoshi="tableInquiry">查&nbsp;询</a><a
						href="javascript:void(0)" class="button">来电注册</a>
				</div>
				<!--tit end-->
				<div class="tableInquiry">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr class="tableTit">
							<td>
								&nbsp;
							</td>
							<td>
								用户名
							</td>
							<td>
								真实姓名
							</td>
							<td>
								手机号码
							</td>
							<td>
								电子邮件
							</td>
							<td>
								会员卡号
							</td>
							<td>
								注册时间
							</td>
							<td>
								操作
							</td>
						</tr>
						<tr>
							<td>
								<input name="radio" type="radio" />
							</td>
							<td>
								TEDDY9441
							</td>
							<td>
								&nbsp;
							</td>
							<td>
								&nbsp;
							</td>
							<td>
								137202791@qq.com
							</td>
							<td>
								&nbsp;
							</td>
							<td>
								2009-11-18
							</td>
							<td>
								<a href="#">修改用户信息</a><a href="#">积分记录</a><a href="#">兑换记录</a>
							</td>
						</tr>
					</table>
				</div>
				<!--tableInquiry end-->
			</div>
			<!--mainmid end-->
		</div>
		<div class="mainTop" >
			<div id="productSearchTab">
				<ul>
					<li>
						<a href="#tabs_1">门票</a>
					</li>
					<li>
						<a href="#tabs_2">酒店</a>
					</li>
					<li>
						<a href="#tabs_3">线路</a>
					</li>
				</ul>
				<div id="tabs_1">
					<form id="ticketSearchForm" action="/makeOrder/doSearchList.do" method="post">
						<s:hidden name="destPlaceId"></s:hidden>
						<s:hidden name="productType" value="TICKET"></s:hidden>
						<table width="100%">
							<tr>
								<td width="5%" height="35">
									目的地景区：
								</td>
								<td>
									<input name="placeName" type="text" id="placeNameT" />
								</td>
								<td width="5%">
									产品名称：
								</td>
								<td>
									<input name="productName" type="text" />
								</td>
								<td width="5%">
									产品ID：
								</td>
								<td>
									<input name="productId" type="text" />
								</td>
							</tr>
							<tr>
								<td height="35">
									价格范围：
								</td>
								<td>
									<select name="sellPriceMin">
										<option value="">
											不限
										</option>
										<option value="100">
											100
										</option>
										<option value="200">
											200
										</option>
										<option value="300">
											300
										</option>
										<option value="500">
											500
										</option>
										<option value="1000">
											1000
										</option>
										<option value="1500">
											1500
										</option>
									</select>
									<select name="sellPriceMax">
										<option value="">
											不限
										</option>
										<option value="100">
											100
										</option>
										<option value="200">
											200
										</option>
										<option value="300">
											300
										</option>
										<option value="500">
											500
										</option>
										<option value="1000">
											1000
										</option>
										<option value="1500">
											1500
										</option>
									</select>
								</td>
								<td>
									分公司：
								</td>
								<td>
									<s:select list="filialeNameList" name="filialeName"
										listKey="code" listValue="name" />
								</td>
								<td colspan="2">
									<a href="javascript:void(0)" class="button"
										onclick="refreshList('ticketSearchForm');">查&nbsp;询</a>
								</td>
							</tr>
						</table>
					</form>
				</div>
				<div id="tabs_2">
					<form id="hotelSearchForm" action="/makeOrder/doSearchList.do" method="post">
						<s:hidden name="destPlaceId"></s:hidden>
						<s:hidden name="productType" value="HOTEL"></s:hidden>
						<table width="100%">
							<tr>
								<td width="6%" height="35">
									目的地/城市/酒店名称：
								</td>
								<td>
									<input name="placeName" type="text" id="placeNameH" />
								</td>
								<td height="35">
									产品名称：
								</td>
								<td>
									<input name="productName" type="text" />
								</td>
								<td>
									产品ID:
								</td>
								<td>
									<input name="productId" type="text" />
								</td>
								<td width="5%">
									<font style="color:red;">*</font>入住时间：
								</td>
								<td>
									<input name="visitDate" type="text" class="date" readonly="readonly" />
								</td>
							</tr>
							<tr>
								<td width="5%">
									入住天数：
								</td>
								<td>
									<input name="visitDay" type="text" />
								</td>
								<td width="5%">
									价格范围：
								</td>
								<td>
									<select name="sellPriceMin">
										<option value="">
											不限
										</option>
										<option value="100">
											100
										</option>
										<option value="200">
											200
										</option>
										<option value="300">
											300
										</option>
										<option value="500">
											500
										</option>
										<option value="1000">
											1000
										</option>
										<option value="1500">
											1500
										</option>
									</select>
									<select name="sellPriceMax">
										<option value="">
											不限
										</option>
										<option value="100">
											100
										</option>
										<option value="200">
											200
										</option>
										<option value="300">
											300
										</option>
										<option value="500">
											500
										</option>
										<option value="1000">
											1000
										</option>
										<option value="1500">
											1500
										</option>
									</select>
								</td>
								<td>
									分公司：
								</td>
								<td>
									<s:select list="filialeNameList" name="filialeName"
										listKey="code" listValue="name" />
								</td>
								<td colspan="2">
									<a href="javascript:void(0)" class="button"
										onclick="refreshList('hotelSearchForm');">查&nbsp;询</a>
								</td>
							</tr>
						</table>
					</form>
				</div>
				<div id="tabs_3">
					<form id="routeSearchForm" action="/makeOrder/doSearchList.do" method="post">
						<s:hidden name="fromPlaceId"></s:hidden>
						<s:hidden name="destPlaceId"></s:hidden>
						<s:hidden name="productType" value="ROUTE"></s:hidden>
						<table width="100%">
							<tr>
								<td width="5%" height="35">
									出发地：
								</td>
								<td>
									<input name="fromPlaceName" id="fromPlaceNameR" type="text" />
								</td>
								<td width="5%">
									目的地:
								</td>
								<td>
									<input name="placeName" id="destPlaceNameR" type="text" />
								</td>
								<td height="35">
									产品名称：
								</td>
								<td>
									<input name="productName" type="text" />
								</td>
								<td>
									产品ID：
								</td>
								<td>
									<input name="productId" type="text" />
								</td>
							</tr>
							<tr>
								<td width="5%">
									游玩时间：
								</td>
								<td>
									<input name="beginVisitDate" type="text" class="date" readonly="readonly" />
									到
									<input name="endVisitDate" type="text" class="date" readonly="readonly"  />
								</td>
								<td>
									游玩天数：
								</td>
								<td>
									<input name="text" type="text" />
								</td>
								<td width="5%">
									价格范围：
								</td>
								<td>
									<select name="sellPriceMin">
										<option value="">
											不限
										</option>
										<option value="100">
											100
										</option>
										<option value="200">
											200
										</option>
										<option value="300">
											300
										</option>
										<option value="500">
											500
										</option>
										<option value="1000">
											1000
										</option>
										<option value="1500">
											1500
										</option>
									</select>
									<select name="sellPriceMax">
										<option value="">
											不限
										</option>
										<option value="100">
											100
										</option>
										<option value="200">
											200
										</option>
										<option value="300">
											300
										</option>
										<option value="500">
											500
										</option>
										<option value="1000">
											1000
										</option>
										<option value="1500">
											1500
										</option>
									</select>
								</td>
								<td>
									分公司：
								</td>
								<td>
									<s:select list="filialeNameList" name="filialeName"
										listKey="code" listValue="name" />
								</td>
							</tr>
							<tr>
								<td colspan="6" height="35">
									<s:checkboxlist list="subProductTypeList" name="subProductType"
										listKey="code" listValue="name" />
								</td>
								<td colspan="2">
									<a href="javascript:void(0)" class="button"
										onclick="refreshList('routeSearchForm');">查&nbsp;询</a>
								</td>
							</tr>
						</table>
					</form>
				</div>
			</div>
		</div>
		<div id="productList" href="${basePath}/makeOrder/doSearchList.do">
		</div>
		<!--main end-->
		<SCRIPT type="text/javascript">
			//加载门票、酒店、线路tabs
			$(function() {
				$('#productSearchTab').tabs();
				$('input[name = "destPlaceId"]').val('');
				//目的地景区内容自动提示返回操作函数
				//obj为选择的结果对象
				//输入目的地景区自动提示,发起请求
				$('#placeNameT, #placeNameH, #fromPlaceNameR, #destPlaceNameR').jsonSuggest({
					url:'/super_back/prod/searchPlace.do',
					maxResults: 10,
					minCharacters: 1,
					//目的地景区内容自动提示返回操作函数
					//obj为选择的结果对象
					onSelect: function(obj){
						$('input[name = "destPlaceId"]').val('');
						$('input[name = "destPlaceId"]').val(obj.id);
					}
				});
			});
			
			function refreshList(formName){
			  var m = $('#' + formName).getForm({
			            prefix:''
			        });
			  var flag = false;
			  for(key in m){
			  	if(m[key] != 'TICKET' && m[key] != 'HOTEL' && m[key] != 'ROUTE') {
			  		if(m[key] != null && m[key] != "") {
			  			flag = true;
			  			break;
			  		}
			  	}
			  }
			  if(flag) {
			  	$('#productList').refresh(m);
			  	$('input[name = "destPlaceId"]').val('');
			  } else{
			  	alert("请至少选择一个查询条件！");
			  	return;
			  }
			}
		</SCRIPT>
	</body>
</html>
