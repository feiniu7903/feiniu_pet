﻿<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<title>驴妈妈供应商管理系统</title>
<link rel="stylesheet" href="http://pic.lvmama.com/styles/new_v/reset.css">
<link rel="stylesheet" href="http://pic.lvmama.com/styles/msg_ord_snspt.css">
<link rel="stylesheet" href="http://pic.lvmama.com/styles/global_backpop.css">
<link rel="stylesheet" href="http://pic.lvmama.com/styles/ebooking/base.css">
 
<style> 
#input{width:120px;height:30px;cursor:pointer;} 
</style> 
<link rel="stylesheet" href="${contextPath}/css/base/select2/select2.css">
<script src="http://pic.lvmama.com/js/jquery1.8.js"></script>
<script src="${contextPath}/js/base/select2.min.js"></script>
<script src="${contextPath}/js/base/jquery.validate.js"></script>
<script src="http://pic.lvmama.com/js/ebooking/ebooking.js"></script>
<script type="text/javascript">
$(function(){
	$("#sumbitApply").click(function(){
		var housePriceIds=$("#submitApplyForm").find("input[name=housePriceIds]").val();
		if(checkForm(this)){
			//前台提交变价申请,然后后台进行审核
			$.ajax({
				url : "${basePath}ebooking/houseprice/submitChangePriceSuggest.do",
				data : "housePriceIds="+housePriceIds,
				type : "POST",
				dataType : "json",
				success : function(dt) {
					if (dt.success) {
						window.location.href="${basePath}ebooking/houseprice/submitedApply.do";
					} else {
						alert(dt.errorMessage);
					}
				}
			});
		}
	});
	
	
	$("#addChangePriceSuggestForm #productBranch").select2();
	//全选、取消全选.
	 $("#week_all").click(function() {
		 var check=$(this).attr("checked");
		 if(check=='checked'){
			 $("input[name='ebkHousePrice.applyWeek']").attr("checked",true).attr("disabled",true); 
		 }else if(check==undefined){
		 	$("input[name='ebkHousePrice.applyWeek']").attr("checked",false).attr("disabled",false); 
		 }
	 });
		
		$.validator.addMethod("money", function(value, element) {
			return /^\d+[\.]?\d{0,2}$/g.test(value)
		 }, "请保留两位小数");

		$("#addChangePriceSuggestForm").validate({
			rules: {    
				"ebkHousePrice.startDate":{
					required:true
				},
				"ebkHousePrice.endDate":{
					required:true
				},
				"ebkHousePrice.breakfastCount":{
					required:true
				},
				"ebkHousePrice.metaBranchId":{
					required:true
				},
				"settlementPriceFen":{
					number:true,
					required:true,
					money:true
				}, 
				"ebkHousePrice.suggestPrice":{
					digits:true,
					required:true
				},
				"ebkHousePrice.marketPrice":{
					digits:true,
					required:true
				}
			}, 
			messages: {    
				"ebkHousePrice.startDate":{
					required:"请选择开始时间"
				},
				"ebkHousePrice.endDate":{
					required:"请选择结束时间"
				},
				"ebkHousePrice.breakfastCount":{
					required:"请选择早餐数"
				},
				"ebkHousePrice.metaBranchId":{
					required:"请选择产品"
				},
				"settlementPriceFen":{
					number:"请输入数字",
					required:"不能为空",
					money:"请保留两位小数"
				}, 
				"ebkHousePrice.suggestPrice":{
					digits:"请输入整数",
					required:"不能为空"
				},
				"ebkHousePrice.marketPrice":{
					digits:"请输入整数",
					required:"不能为空"
				}
			}
		});
		
		$(".apply").ui("calendar", {
			input : "#Calendar51",
			parm : {
				dateFmt : "yyyy-MM-dd",
				maxDate : "#F{$dp.$D('Calendar61')}",
				minDate : "#F{'<s:date name='createTimeBegin' format='yyyy-MM-dd'/>'}"
			}
		});
		$(".apply").ui("calendar", {
			input : "#Calendar61",
			parm : {
				dateFmt : 'yyyy-MM-dd',
				minDate : "#F{$dp.$D('Calendar51')}"
			}
		});
		/*当选择线路产品时，禁止早餐数选择下拉列表和建议售价*/
		$("#productBranch").change(function(){
			var pb=$("#productBranch").find("option:selected");
			var value=pb.text();
			if(value!='' && pb.attr("productType")=="ROUTE"){
				$("#housePriceProductType").val("ROUTE");
				$("#breakfastCount").val("");
				$("#breakfastCount").attr("disabled","disabled");
				$("#suggestPrice").val("");
				$("#suggestPrice").attr("disabled","disabled");
			}else if(value!=''&& pb.attr("productType")=="HOTEL"){
				$("#housePriceProductType").val("HOTEL");
				$("#suggestPrice").attr("disabled",false);
	  			$("#breakfastCount").attr("disabled",false);
			}
		});
	});
 	
	
function checkForm(f){
	if(!controlSumit()) {
		return false;
	}
	var housePriceIds = "${housePriceIds}";
	var totalResultSize="${appliedSuggestCount}";
	if($.trim(housePriceIds)==""||totalResultSize=="0"){
		alert("不存在可以变价的产品");
		return false;
	}
	return true;
}

function keyupValidHandler(name){
	$("#addChangePriceSuggestForm").valid();
	var validator = $("#addChangePriceSuggestForm").validate();
	if(validator.element($("#"+name))){
		$("label[for='" + name + "']").remove();
	}
}
	var bClicked = false;
	function controlSumit() {
		if (bClicked) {
			return false;
		}
		bClicked = true;
		return true;
	}
	function addChange() {
		if(!controlSumit()) {
			return false;
		}
		$("#addChangePriceSuggestForm").submit();
	}
	setInterval("bClicked = false;", 2000);
</script>
</head>
<body id="body_fjwh" class="ebooking_price">
<jsp:include page="../../common/head.jsp"></jsp:include>
<div class="breadcrumb">
	<ul class="breadcrumb_nav">
		<li class="home">首页</li>
    	<li>变价申请</li>
    </ul>
    <a href="http://www.lvmama.com/zt/ppt/ebk/fangjia-guide.ppt" class="ppt_xz">变价申请操作PPT下载</a>
</div>
<!--以上是公用部分-->
<!--订单处理开始-->
<dl class="order_nav">
	<dt>变价申请</dt>
    <dd class="order_nav_dd"><a href="javascript:void(0)">变价申请</a></dd>
    <dd><a href="${basePath}ebooking/houseprice/searchHousePrice.do">价格查询</a></dd>
    <dd><a href="${basePath}ebooking/houseprice/submitedApply.do">已提交申请</a></dd>
</dl>
<form id="addChangePriceSuggestForm" action="${basePath}ebooking/houseprice/addChangePriceSuggest.do" method="post">
<input id="housePriceProductType" type="hidden" name="ebkHousePrice.productType" value="">
<ul class="order_all">
	<li class="order_all_li" style="display:block;">
    	<h4>提交变价申请</h4>
		<ul class="apply">
    		<li>申请主题：<input class="width_278" type="text" name="ebkHousePrice.subject"><span class="c_gray">　（可选填）</span></li>
            <li>选择产品：
			<select id="productBranch" name="ebkHousePrice.metaBranchId">
				<option value="">全部产品</option>
				<s:iterator value="productBranchList" var="pb">
					<s:if test="productType == 'ROUTE'">
					<option value="${pb.metaBranchId }" productType="${pb.productType }">${pb.metaProductName } - ${pb.branchName }(${pb.metaBranchId })</option>
					</s:if>
					<s:else>
					<option value="${pb.metaBranchId }" productType="${pb.productType }">${pb.branchName }(${pb.metaBranchId })</option>
					</s:else>
				</s:iterator>
			</select>
            </li>
            <li>选择日期：<input id="Calendar51" class="date_bg" type="text" name="ebkHousePrice.startDate"  value="">
            ~<input id="Calendar61" class="date_bg" type="text" name="ebkHousePrice.endDate"  ></li>
            <li>适用星期：
                <label for="week_all"><input id="week_all" name="week_all" type="checkbox" value="">全部</label>
                <label for="week_1"><input id="week_1" name="ebkHousePrice.applyWeek" type="checkbox" value="一">星期一</label>
                <label for="week_2"><input id="week_2" name="ebkHousePrice.applyWeek" type="checkbox" value="二">星期二</label>
                <label for="week_3"><input id="week_3" name="ebkHousePrice.applyWeek" type="checkbox" value="三">星期三</label>
                <label for="week_4"><input id="week_4" name="ebkHousePrice.applyWeek" type="checkbox" value="四">星期四</label>
                <label for="week_5"><input id="week_5" name="ebkHousePrice.applyWeek" type="checkbox" value="五">星期五</label>
                <label for="week_6"><input id="week_6" name="ebkHousePrice.applyWeek" type="checkbox" value="六">星期六</label>
                <label for="week_7"><input id="week_7" name="ebkHousePrice.applyWeek" type="checkbox" value="七">星期日</label>
            </li>
            <li>　　早餐：
					<select name="ebkHousePrice.breakfastCount" id="breakfastCount" >
	                	<option value="">选择早餐数</option>
	                    <option value="0">0</option>
	                    <option value="1">1</option>
	                    <option value="2">2</option>
	                    <option value="3">3</option>
	                    <option value="4">4</option>
	                    <option value="5">5</option>
	                    <option value="6">6</option>
	                    <option value="7">7</option>
	                    <option value="8">8</option>
	                    <option value="9">9</option>
	                    <option value="10">10</option>
	                </select>            	            	
            </li>
            <li>
            	定价明细：结算价：<span class="c_orange">￥</span><input class="width_58" type="text" id="settlementPriceId" name="settlementPriceFen" maxlength="20" onkeyup="keyupValidHandler('settlementPriceId')">
            	建议售价：<span class="c_orange">￥</span><input class="width_58" type="text" id="suggestPrice" name="ebkHousePrice.suggestPrice" maxlength="20" onkeyup="keyupValidHandler('suggestPrice')">
            	门市价：<span class="c_orange">￥</span><input class="width_58" type="text" id="marketPriceId" name="ebkHousePrice.marketPrice" maxlength="20" onkeyup="keyupValidHandler('marketPriceId')">
            	<span style="color:red;"> 注：建议售价指网络销售价</span>
            </li>
            <li><span class="remarks_t">　　备注：</span><textarea class="remarks" onFocus="if(value=='费用包含内容如有变更，请在此描述'){value=''}" onBlur="if (value ==''){value='费用包含内容如有变更，请在此描述'}" name="ebkHousePrice.memo">费用包含内容如有变更，请在此描述</textarea><span class="c_gray remarks_t">　（可选填）</span></li>
            <li><input type="button" class="tianjia_bj" value="添加到变价申请栏" onclick="addChange();"/>
            <s:if test="appliedSuggestCount!=0">
	            <span class="c_gray tianjia_r_bg">已添加成功${appliedSuggestCount}条申请</span>
            </s:if>
            </li>
    	</ul>
    </li>
</ul>
</form>
<form id="submitApplyForm" action="${basePath}ebooking/houseprice/submitChangePriceSuggest.do" method="post" onsubmit="return(checkForm(this));">
<input type="hidden" name="housePriceIds" value="<s:property value='housePriceIds'/>"/>
<div class="sql_t">变价申请栏<span>( ${ebkHousePricePage.totalResultSize} 条申请)</span></div>
<table class="bianjia_sql" width="958" align="center" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <th >申请主题</th>
    <th >产品名称</th>
    <th >开始日期</th>
    <th >结束日期</th>
    <th >适用星期</th>
    <th >定价明细<br><span>(结算价/建议售价/门市价)</span></th>
    <th >早餐情况</th>
    <th >操作</th>
  </tr>
  <s:iterator value="ebkHousePricePage.items" var="item">
  <tr>
    <td>${subject}</td>
    <td>${ metaProductBranchName}( ${metaBranchId} )</td>
    <td><s:date name="startDate" format="yyyy-MM-dd"/></td>
    <td><s:date name="endDate" format="yyyy-MM-dd"/></td>
    <td>${applyWeekString}</td>
    <td>
    	<span class="c_orange">${ settlementPriceYuan}</span>/
    	<span class="c_orange">
    		<s:if test="suggestPriceYuan>0">${ suggestPriceYuan}</s:if>
    		<s:else>~</s:else>
    	</span>/
    	<span class="c_orange">${ marketPriceYuan}</span>
    </td>
    <td>
    	<s:if test="breakfastCount > 0">
		    ${ breakfastCount}早
    	</s:if>
    	<s:else>无</s:else>
    </td>
    <td><a href="${basePath}ebooking/houseprice/deleteChangePriceSuggest.do?ebkHousePrice.housePriceId=${housePriceId}">删除</a></td>
  </tr>
   </s:iterator>
</table>

<div class="submit_sq">
	<input id="sumbitApply" type="button" class="submit_but but_width128" value="提交申请" />
</div>
</form>
<!--公用底部-->
<script type="text/javascript" src="http://pic.lvmama.com/js/ui/lvmamaUI/lvmamaUI.js"  charset="utf-8"></script>
<jsp:include page="../../common/footer.jsp"></jsp:include>
</body>
</html>
