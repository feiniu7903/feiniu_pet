<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<!-- css -->
<link rel="stylesheet" type="text/css" href="${basePath }/themes/base/jquery.ui.all.css" ></link>
<link rel="stylesheet" type="text/css" href="${basePath }/themes/ebk/admin.css" ></link>
<link rel="stylesheet" type="text/css" href="/super_back/style/houtai.css" />
<style type="text/css">
	.calculateHours {
		width: 20px;
	}
	.hourSpan {
		display: none;
	}
</style>
<script type="text/javascript">
$(function() {
	$("input.calculateHours").keyup(function() {
		var value = $.trim($(this).val());
		var name = $(this).attr("name");
		var reg = /^(\d+)$/;
		if(value != "") {
			if (reg.test(value)) {
				var valueInt = parseInt(value);
				if(name == "dayInput") {
					if(valueInt < 0) {
						alert("只能输入大于或等于0的正整数");
						$(this).val("");
						return false;
					}
				}else if(name == "hourInput") {
					if(valueInt < 0 || valueInt > 23) {
						alert("只能输入0到24之间的正整数");
						$(this).val("");
						return false;
					}
				} else {
					if(valueInt < 0 || valueInt > 60) {
						alert("只能输入0到60之间的正整数");
						$(this).val("");
						return false;
					}
				}
			} else {
				alert("只能输入正整数");
				$(this).val("");
				return false;
			}
		}
		var $td = $(this).parent("td");
		var day = $.trim($td.find("input[name=dayInput]").val());
		var hour = $.trim($td.find("input[name=hourInput]").val());
		var min = $.trim($td.find("input[name=minInput]").val());
		if(hour == "" && day == "" && min == "") {
			$td.find("span.showTimeSpan").text("");
			$td.find("input[type=hidden][name^='timePriceBean.']").val("");
		} else {
			var res = 0;
			if(day == "") {
				day = 0;
			}
			day = parseInt(day);
			if(hour == "") {
				hour = 0;
			}
			hour = parseInt(hour);
			if(min == "") {
				min = 0;
			}
			min = parseFloat(min);
			min = min / 60;
			if(day == 0) {
				res = (-1 * (hour + min)) + "";
			} else {
				res = ((day - 1) * 24 + (24 - (hour + min))) + "";
			}
			if(res.indexOf(".") > 0) {
				res = res.substring(0, res.indexOf(".") + 2);
			}
			$td.find("span.showTimeSpan").text(res + "小时");
			$td.find("input[type=hidden][name^='timePriceBean.']").val(res);
		}
	});
	
	var $time_price_dlg = null;
	var current_time_price_param = null;
	$(".showTimePrice").click(function() {
		var param = $(this).attr("param");
		current_time_price_param = eval("(" + param + ")");

		if ($time_price_dlg == null) {
			$time_price_dlg = $("<div style='display:none' class='time_price_dlg_div'>");
			$time_price_dlg.appendTo($("body"));
		}

		$time_price_dlg.load("/super_back/meta/toMetaTimePrice.do",
				current_time_price_param, function() {
					$time_price_dlg.dialog( {
						title : "时间价格表",
						width : 1000,
						modal : true
					});
				});
	});
	
	$("input:radio[name='auditedStatus']").change(function() {
		if($(this).val() == "PASSED_AUDIT") {
			$("span.hourSpan").show();
		} else {
			$("span.hourSpan").hide();
		}
	});
})
</script>
</head>
	<body>	 
         <div style='height:20px;font-size:18px; '>
         <strong>申请单号:</strong>
         <span >${ebkHousePrice.housePriceId }</span>&nbsp;&nbsp;&nbsp;
         <strong>审核状态:</strong>
         <span style="color:red;">${ebkHousePrice.auditStatus.cnName}</span></div>
         <a param="{'metaBranchId':${ebkHousePrice.metaBranchId },'editable':false}" class="showTimePrice" tt="META_PRODUCT" href="#timePrice">查看时间价格</a>
         <form id="ebkHousePriceApplyDetailForm">
     		<table class="gl_table" >
		 		<input type="hidden" name="housePriceId" id="housePriceId" value="${ebkHousePrice.housePriceId }"/>
			 	<tr>
			 		<td>申请主题:</td><td><s:property value="ebkHousePrice.subject" /></td>
			 	</tr>
			 	<tr>
			 		<td>供应商名称:</td><td><s:property value="ebkHousePrice.supSupplier.supplierName" />（ID：<s:property value="ebkHousePrice.supSupplier.supplierId" />）</td>
			 	</tr>
			 	<tr>
			 		<td>供应商电话:</td><td><s:property value="ebkHousePrice.supSupplier.mobile" /></td>
			 	</tr>
			 	<tr>
			 		<td>变价产品:</td><td><s:property value="ebkHousePrice.productName" />(<s:property value="ebkHousePrice.metaProductBranchName" />)</td>
			 	</tr>
			 	<tr>
			 		<td>变价日期:</td><td><s:date name="ebkHousePrice.startDate" format="yyyy-MM-dd"/> 至 <s:date name="ebkHousePrice.endDate" format="yyyy-MM-dd"/></td>
			 	</tr>
			 	<tr>
			 		<td>适用星期:</td><td><s:property value="ebkHousePrice.applyWeekString" /></td>
			 	</tr>
			 	<tr>
			 		<td>定价明细:</td>
			 		<td>
				 		<s:property value="ebkHousePrice.settlementPriceYuan" />
				 		<s:if test="ebkHousePrice.suggestPriceYuanStr>0">			 		
				 			/<s:property value="ebkHousePrice.suggestPriceYuanStr" />/
				 		</s:if>
				 		<s:else>
				 			/~/
				 		</s:else>
				 		<s:property value="ebkHousePrice.marketPriceYuanStr" /> RMB（结算价/建议售价/门市价）
			 		</td>
			 	</tr>
			 	<tr>
			 		<td>早餐情况:</td><td>
				 		<s:if test="ebkHousePrice.breakfastCount>0">
				 			<s:property value="ebkHousePrice.breakfastCount" />早/天
			 			</s:if>
			 			<s:else>
			 				无
			 			</s:else>
			 		</td>
			 	</tr>
			 	<tr>
			 		<td>备注:</td><td><s:property value="ebkHousePrice.memo" />
			 		</td>
			 	</tr>
			 	<tr>
			 		<td>提交人:</td><td><s:property value="ebkHousePrice.submitUser" /></td>
			 	</tr>
			 	<tr>
			 		<td>提交日期:</td><td><s:date name="ebkHousePrice.createTime" format="yyyy-MM-dd HH:mm:ss"/></td>
			 	</tr>
			 </table>
			 <table class="gl_table">
			 	<s:if test="ebkHousePrice.auditStatus.cnName=='待审核'">
			 	<tr>
			 		<td>网站提前预订小时数:<span class="require hourSpan">[*]</span></td>
			 		<td>
			 			提前<input type="text" class="text1 calculateHours" name="dayInput" />天
							<input type="text" class="text1 calculateHours" name="hourInput" />时
							<input type="text" class="text1 calculateHours" name="minInput" />分
							<span class="showTimeSpan" style="color:red;"></span>
							<input type="hidden" name="timePriceBean.aheadHourFloat" />
			 		</td>
			 	</tr>
			 	<tr>
			 		<td>退改策略:<span class="require hourSpan">[*]</span></td>
			 		<td>
			 			<input type="radio" name="timePriceBean.cancelStrategy" value="ABLE" />可退改(当提前预订小时数&lt;最晚取消小时数,产品可预授权 )<br />
						提前<input type="text" class="text1 calculateHours" name="dayInput" />天
						<input type="text" class="text1 calculateHours" name="hourInput" />时
						<input type="text" class="text1 calculateHours" name="minInput" />分
						<span class="showTimeSpan" style="color:red;"></span><br />
						<input type="hidden" name="timePriceBean.cancelHourFloat" />
						<input type="radio" name="timePriceBean.cancelStrategy" value="FORBID" />不退不改<br />
						<input type="radio" name="timePriceBean.cancelStrategy" value="MANUAL" />人工确定退改
			 		</td>
			 	</tr>
			 	<tr>
			 		<td>审核结果:<span class="require">[*]</span></td>
			 		<td>
			 			<s:iterator value="suggestAuditStatusList" id="cl">
	 						<input type="radio" name="auditedStatus" value="<s:property value="code"/>" cssClass="radio" <s:if test='#cl.isChecked()'>checked</s:if>/><s:property value="name"/>
						</s:iterator>
			 		</td>
			 	</tr>
			 	</s:if>
			 	<s:else>
			 	<tr>
			 		<td>审核结果:</td><td><s:property value="ebkHousePrice.auditStatus.cnName"/></td>
			 	</tr>
			 	 <tr>
			 		<td>审核人:</td><td><s:property value="ebkHousePrice.confirmUser" /></td>
			 	</tr>
			 	<tr>
			 		<td>审核时间:</td><td><s:date name="ebkHousePrice.confirmTime" format="yyyy-MM-dd HH:mm:ss"/></td>
			 	</tr>
			 	</s:else>
			 </table>
		 </form>
	</body>
</html>
