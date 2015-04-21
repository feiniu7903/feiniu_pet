<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<title>booking海外酒店订单</title>
<link rel="stylesheet" type="text/css" href="${basePath }/themes/base/jquery.ui.all.css" >
<link rel="stylesheet" type="text/css" href="${basePath }/themes/ebk/admin.css" >
<link rel="stylesheet" type="text/css" href="${basePath}/themes/ebk/ui-components.css" />
<script src="http://pic.lvmama.com/js/jquery1.8.js"></script>
<script type="text/javascript" src="${basePath}/js/base/jquery-1.4.4.min.js"></script>
<script type="text/javascript" src="${basePath}/js/base/jquery-ui-1.8.5.js"></script>
<script type="text/javascript" src="${basePath}/js/base/jquery.datepick-zh-CN.js"></script>
<script type="text/javascript" src="http://pic.lvmama.com/js/ui/lvmamaUI/lvmamaUI.js" charset="utf-8"></script>
<script type="text/javascript" src="${basePath}/js/base/dialog.js?v=1"></script>
<link rel="stylesheet" href="http://pic.lvmama.com/styles/new_v/ob_common/ui-components.css">
<script type="text/javascript">
$(function(){
	$('#begin_date').datepicker({dateFormat : 'yy-mm-dd'});
	$('#end_date').datepicker({dateFormat : 'yy-mm-dd'});
	
	$('#checkout_from').datepicker({dateFormat : 'yy-mm-dd'});
	$('#checkout_until').datepicker({dateFormat : 'yy-mm-dd'});
	
	$(".page").click(function(){
		var direction = $(this).attr("direction");
		var offset = $(this).attr("offset");
		
		$("#direction").val(direction);
		$("#offset").val(offset);
		
		//document.bookingOrderForm.submit();
		
		var _form = $("#bookingOrderForm");
		_form.attr("action",_form.attr("search_action"));
		_form.submit();
		
	});
	
	$("#searchBtn").click(function(){
		var begin_date = $("#begin_date").val();
		var end_date = $("#end_date").val();
		var checkout_from = $("#checkout_from").val();
		var checkout_until = $("#checkout_until").val();
		var id = $("#orderId").val();
		
		if(($.trim(begin_date)!="" && $.trim(end_date)=="") || ($.trim(begin_date)=="" && $.trim(end_date)!="")){
			alert("请填写完整的查询条件");
			return false;
		}
		
		if(($.trim(checkout_from)!="" && $.trim(checkout_until)=="") || ($.trim(checkout_from)=="" && $.trim(checkout_until)!="")){
			alert("请填写完整的查询条件");
			return false;
		}
		
		if(($.trim(begin_date)!="" && $.trim(end_date)!="" && $.trim(checkout_from)!="" && $.trim(checkout_until)!="")
				||($.trim(begin_date)!="" && $.trim(end_date)!="" && $.trim(id)!="") 
				||($.trim(checkout_from)!="" && $.trim(checkout_until)!="" && $.trim(id)!="")){
			
			alert("三个查询条件一次只能填一个");
			return false;
		}
		
		if(($.trim(begin_date)!="" && $.trim(end_date)!="")){
			if(!compareDay(begin_date,end_date)){
				alert("下单结束时间必须大于下单开始时间");
				return false;
			}
			if(!isDateString(begin_date)){
				alert("日期格式不正确,正确的日期格式为yyyy-mm-dd");
				return false;
			}
			if(!isDateString(end_date)){
				alert("日期格式不正确,正确的日期格式为yyyy-mm-dd");
				return false;
			}
		}
		
		if(($.trim(checkout_from)!="" && $.trim(checkout_until)!="")){
			if(!compareDay(checkout_from,checkout_until)){
				alert("入住结束时间必须大于入住开始时间");
				return false;
			}
			if(!isDateString(checkout_from)){
				alert("日期格式不正确,正确的日期格式为yyyy-mm-dd");
				return false;
			}
			if(!isDateString(checkout_until)){
				alert("日期格式不正确,正确的日期格式为yyyy-mm-dd");
				return false;
			}
		}
		
		//document.bookingOrderForm.submit();
		
		var _form = $("#bookingOrderForm");
		_form.attr("action",_form.attr("search_action"));
		_form.submit();
		
	});
	
	/**
	* 导出booking订单
	*/
	$("#exportBtn").click(function(){
		var begin_date = $("#begin_date").val();
		var end_date = $("#end_date").val();
		var checkout_from = $("#checkout_from").val();
		var checkout_until = $("#checkout_until").val();
		var id = $("#orderId").val();
		
		if($.trim(begin_date)=="" && $.trim(end_date)=="" && $.trim(checkout_from)=="" && $.trim(checkout_until)=="" && $.trim(id)=="" ){
			alert("请填写一个查询条件");
			return false;
		}
		
		if(($.trim(begin_date)!="" && $.trim(end_date)=="") || ($.trim(begin_date)=="" && $.trim(end_date)!="")){
			alert("请填写完整的查询条件");
			return false;
		}
		
		if(($.trim(checkout_from)!="" && $.trim(checkout_until)=="") || ($.trim(checkout_from)=="" && $.trim(checkout_until)!="")){
			alert("请填写完整的查询条件");
			return false;
		}
		
		if(($.trim(begin_date)!="" && $.trim(end_date)!="" && $.trim(checkout_from)!="" && $.trim(checkout_until)!="")
				||($.trim(begin_date)!="" && $.trim(end_date)!="" && $.trim(id)!="") 
				||($.trim(checkout_from)!="" && $.trim(checkout_until)!="" && $.trim(id)!="")){
			
			alert("三个查询条件一次只能填一个");
			return false;
		}
		
		if(($.trim(begin_date)!="" && $.trim(end_date)!="")){
			if(!compareDay(begin_date,end_date)){
				alert("下单结束时间必须大于下单开始时间");
				return false;
			}
			if(!isDateString(begin_date)){
				alert("日期格式不正确,正确的日期格式为yyyy-mm-dd");
				return false;
			}
			if(!isDateString(end_date)){
				alert("日期格式不正确,正确的日期格式为yyyy-mm-dd");
				return false;
			}
		}
		
		if(($.trim(checkout_from)!="" && $.trim(checkout_until)!="")){
			if(!compareDay(checkout_from,checkout_until)){
				alert("入住结束时间必须大于入住开始时间");
				return false;
			}
			if(!isDateString(checkout_from)){
				alert("日期格式不正确,正确的日期格式为yyyy-mm-dd");
				return false;
			}
			if(!isDateString(checkout_until)){
				alert("日期格式不正确,正确的日期格式为yyyy-mm-dd");
				return false;
			}
		}
		
		var _form = $("#bookingOrderForm");
		_form.attr("action",_form.attr("excel_action"));
		_form.submit();
	}); 
	
});

/***************判断两个日期大小***************/

function compareDay(a,b){
	//a , b 格式为 yyyy-MM-dd   
	var a1 = a.split("-");   
	var b1 = b.split("-");
	var d1 = new Date(a1[0],a1[1],a1[2]);  
	var d2 = new Date(b1[0],b1[1],b1[2]);  
	
	if(Date.parse(d1) - Date.parse(d2) >= 0) {
		//a>=b 
		return false;    
	}
	return true;
}

/***************判断日期是否为标准格式yyyy-MM-dd ***************/
function isDateString(sDate){
	var mp = /\d{4}-\d{2}-\d{2}/;
    var matchArray = sDate.match(mp);
    if(matchArray==null) return false;

	var iaMonthDays = [31,28,31,30,31,30,31,31,30,31,30,31];
	var iaDate = new Array(3);

	var year, month, day;
    iaDate = sDate.split("-");
    year = parseFloat(iaDate[0]); 
    month = parseFloat(iaDate[1]);
    day = parseFloat(iaDate[2]);
	if(year < 1900|| year > 2100) return false;
	if(((year %4==0)&&(year %100!=0))||(year %400==0)) iaMonthDays[1]=29;
	if((month < 1) || (month > 12)) return false;
	if((day < 1) || (day > iaMonthDays[month - 1]))return false;

	return true;
}

  
</script>
</head>
<body>
	<%//request.setAttribute("tabName","CHECK_PENDING"); %>
	<!--<jsp:include page="/WEB-INF/pages/back/ebk/prod/prodAuditTabs.jsp" />-->
	<ul class="gl_top">
	<form id="bookingOrderForm" name="bookingOrderForm" method="post"
	      excel_action="${basePath}bookingOrder/exportBookingOrderList.do" 
		  search_action="${basePath}bookingOrder/bookingOrderList.do" >
		<input id="direction" type="hidden" name="direction" />
		<input id="offset" type="hidden" name="offset" />
		<table class="newfont06" border="0"  cellpadding="0"  >
				<tr height="30">
					<td>下单时间：</td>
					<td >
						 <input style="width: 70px;margin-right: 0px;" type="text" id="begin_date" name="begin_date" value="${param.begin_date}"/>
						 <label>~</label>
						 <input style="width: 70px" type="text" id="end_date" name="end_date" value="${param.end_date}"/>
					</td>
					<td>入住时间：</td>
					<td >
						 <input style="width: 70px;margin-right: 0px;" type="text" id="checkout_from" name="checkout_from" value="${param.checkout_from}"/>
						 <label>~</label>
						 <input style="width: 70px" type="text" id="checkout_until" name="checkout_until" value="${param.checkout_until}"/>
					</td>
					<td width="96px">订单号：</td>
					<td>
					    <input type="text" id="orderId" name="id" maxlength="20" value="${param.id }"/>
					</td>
				</tr>
				<tr height="30">
					<td>
						<input id="searchBtn" type="button" class="button btn btn-small" value="查询"/>
	 				</td>
	 				<td>
						<input id="exportBtn" type="button" class="button btn btn-small" value="导出"/>
	 				</td>
				</tr>
		</table>
	 </form>
	 </ul>
	 <div class="tab_top"></div>
	<table border="0"   cellpadding="0" class="gl_table">
		<tr>
			<th width="40">booking订单号</th>
			<th width="100">预定时间</th>
			<th width="100">入住时间</th>
			<th width="50">离店时间</th>
			<th width="50">酒店ID</th>
			<th width="50">酒店名称</th>
			<th width="50">酒店国家</th>
			<th width="50">酒店城市</th>
			<th width="50">用户国家</th>
			<th width="50">用户城市</th>
			<th width="50">支付金额</th>
			<th width="50">booking佣金</th>
			<th width="50">Lv佣金</th>
			<th width="50">到账日期</th>
			<th width="50">状态</th>
		</tr>
		<s:iterator value="bookingOrderList" status="bookingOrder">
			<tr>
				<td><s:property value="id" /></td>
				<td><s:date name="creation_datetime" format="yyyy-MM-dd HH:mm:ss"/></td>
				<td><s:date name="arrival_date" format="yyyy-MM-dd"/></td>
				<td><s:date name="departure_date" format="yyyy-MM-dd"/></td>
				<td><s:property value="hotel_id" /></td>
				<td><s:property value="hotel_name" /></td>
				<td><s:property value="hotel_countrycode" /></td>
				<td><s:property value="hotel_city" /></td>
				<td><s:property value="guest_country" /></td>
				<td><s:property value="guest_city" /></td>
				<td><s:property value="totalcost" /></td>
				<td><s:property value="commission" /></td>
				<td><s:property value="euro_fee" /></td>
				<td><s:date name="fee_calculation_date" format="yyyy-MM-dd"/></td>
				<td>
					<s:if test='status=="完成"'>
						<font color="green">完成</font>
					</s:if>
					<s:elseif test='status=="取消"'>取消 </s:elseif>
				</td>
			</tr>
		</s:iterator>
		<!-- 
		<tr>
			<td>总条数：<s:property value="ebkProdProductPage.totalResultSize" /></td>
			<td colspan="8" align="right">${pageView}</td>
		</tr>
		-->
		<tr>
			<td colspan="15" align="center">
			<a href="javascript:void(0);" class="page" direction="first" offset="0" >首页</a>
			<s:if test='bookingOrderList!=null && bookingOrderList.size()>0'>
				<a href="javascript:void(0);" class="page" direction="previous" offset="<s:property value="offset"/>" >上一页</a>
				<a href="javascript:void(0);"  class="page" direction="next" offset="<s:property value="offset"/>" >下一页</a>
			</s:if>
			</td>
		</tr>
	</table>
</body>
</html>