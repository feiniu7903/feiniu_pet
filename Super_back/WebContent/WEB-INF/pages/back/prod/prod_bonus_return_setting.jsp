<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title>产品返现设置</title>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/base/jquery-1.4.4.min.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/ui/jquery-ui-1.8.5.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/base/lvmama_common.js"></script>
		<link rel="stylesheet" href="<%=request.getContextPath()%>/themes/base/jquery.ui.all.css" />
		<link rel="stylesheet" href="<%=request.getContextPath()%>/themes/cc.css" />
	</head>
	<body>
		<div  class="table_box">
		<div class="mrtit3" style="padding:10px;">
		<form id="search_form" method="post">
			<table>
				<tr>
					<td>产品ID：</td>
					<td>
						<input type="text" name="productId" value="${param.productId }"/>
					</td>
					<td>产品名称：</td>
					<td>
						<input type="text" name="productName" value="${param.productName }"/>
					</td>
					<td>产品子类型：</td>
					<td>
						<s:select list="subProductTypeList" name="subProductType"
                                  listKey="code" listValue="name"></s:select>
					</td>
					<td>是否返现：</td>
					<td>
						<s:select name="isRefundable" list="#{'':'请选择','Y':'是','N':'否'}"></s:select>
					</td>
					<td>返现设置：</td>
					<td>
						<s:select name="isManualBonus" list="#{'':'请选择','Y':'手动','N':'自动'}"></s:select>
					</td>
					<td><input type="submit" value="查询" class="right-button08"/></td>
				</tr>
			</table>
		</form>
		</div>
		<form id="result_form">
			<table cellspacing="1" cellpadding="4" border="0" bgcolor="#666666" width="100%" class="newfont06" style="font-size: 12; text-align: center;">
				<tr bgcolor="#eeeeee">
					<td>&nbsp;</td>
					<td height="35" width="8%">产品ID</td>
					<td width="18%">销售产品名称</td>
					<td width="10%">产品子类型</td>				
					<td>是否返现</td>
					<td>返现设置</td>
					<td>返现金额</td>
					<td>操作</td>
				</tr>
				<s:iterator value="pagination.records" var="product">
				<tr bgcolor="#ffffff">
					<td><input type="checkbox" name="productIds" value="${product.productId }"  /></td>
					<td>${product.productId}</td>
					<td>${product.productName}</td>
					<td>${product.zhSubProductType }</td>
					<td>
						<s:if test='#product.isRefundable=="Y"'>是</s:if><s:else>否</s:else>
					</td>
					<td>
						<s:if test='#product.isManualBonus=="Y"'>手动</s:if><s:else>自动</s:else>
					</td>
					<td>
						${product.cashRefundY }
					</td>
					<td>
						<a href="javascript:;" data-id="${product.productId}" data-manual="${product.isManualBonus}" data-maxCashRefund="${product.maxCashRefund/100 }" name="setting-btn">设置</a>
					</td>
				</tr>
				</s:iterator>
				<tr bgcolor="#ffffff">
					<td><input type="checkbox" class="chk_all" /></td>
					<td colspan="7" align="left">
						<input type="button" value="批量设置为自动返现" class="myOperateBtn" isManualBonus="N" />&nbsp;&nbsp;
						<input type="button" value="批量设置为手动返现" class="myOperateBtn" isManualBonus="Y" />&nbsp;&nbsp;
						<input type="button" value="批量设置不返现" class="myOperateBtn" isRefundable="N" />
						<input type="hidden" name="isRefundable" />
						<input type="hidden" name=isManualBonus />
		            </td>
				</tr>
			</table>
		</form>
		<table width="90%" border="0" align="center">
			<s:include value="/WEB-INF/pages/back/base/pag.jsp" />
		</table>
	</div>
	
	
	
	<div id="setting_box" title="产品返现设置" style="display: none">
		<form id="setting_form">
			<input type="hidden" name="productId" id="setting_productId" />
			<label for="returnMode">返现类型:</label>
			<input type="radio" name="returnMode" id="setting_mode_manual" value="manual" />手动返现
			<input type="radio" name="returnMode"  id="setting_mode_auto" value="auto" />自动返现
			<br/>
			<br/>
			<label for="returnMoney">返现金额(元):</label>
			<input type="text" name="returnMoney" id="setting_returnMoney" class="text ui-widget-content ui-corner-all" style="width: 50px;"/>
		</form>
	</div>
	
	
	
	<script type="text/javascript">
		$(function(){
			
			$("#setting_box").dialog({
				modal: true,
				autoOpen: false,
				buttons: {
					 "保存": function() {
						 if($("#setting_mode_manual").attr("checked")==true){
							 var bonusReturn=$.trim($("#setting_returnMoney").val());
							 if(bonusReturn==""){
								 alert("请输入返现金额");
								 return;
							 }
							 if(isNaN(bonusReturn)||!/^\d+(.\d{0,2}){0,1}$/.test(bonusReturn)){
								 alert("金额必须为整数或者小数,小数点后不超过2位!");
								 return;
							 }
						 }
						var params=$("#setting_form").serialize();
						$.ajax({
						   type: "GET",
						   dataType:"json",
						   url: "<%=request.getContextPath()%>/prod/saveProdBonusSetting.do?"+params,
						   success: function(data){
						     alert(data.msg);
						     if(data.success==true){
						    	 $("#search_form").submit();
						     }
						   }
						});
						 
					 },
					 "取消": function() {
					 	$( this ).dialog( "close" );
					 }
				}
			});
			
			$("#setting_mode_manual").click(function(){
				if($(this).attr("checked")==true){
					$("#setting_returnMoney").attr("disabled",false);
				}
			});
			
			$("#setting_mode_auto").click(function(){
				if($(this).attr("checked")==true){
					$("#setting_returnMoney").attr("disabled",true);
				}
			});
			
			
			$("a[name='setting-btn']").click(function(){
				var productId=$(this).attr("data-id");
				var manual=$(this).attr("data-manual");
				var bonusReturn=$(this).attr("data-maxCashRefund");
				$("#setting_productId").val(productId);
				if("Y"==manual){
					$("#setting_mode_manual").attr("checked",true);
					$("#setting_returnMoney").attr("disabled",false);
				}else if("N"==manual){
					$("#setting_mode_auto").attr("checked",true);
					$("#setting_returnMoney").attr("disabled",true);
				}
				$("#setting_returnMoney").val(bonusReturn);
				$( "#setting_box" ).dialog("open");
			});
			
			$("input.chk_all").change(function() {
            	var checked = $(this).attr("checked");
            	if(checked) {
            		$("input[type=checkbox][name=productIds]").attr("checked", true);
            	} else {
            		$("input[type=checkbox][name=productIds]").attr("checked", false);
            	}
            });
			

            $("input.myOperateBtn").click(function() {
            	var len = $("input[type=checkbox][name=productIds]:checked").length;
            	if(len < 1) {
            		alert("请选择产品!");
            		return false;
            	}
            	var isManualBonus = $(this).attr("isManualBonus");
            	var isRefundable = $(this).attr("isRefundable");
            	var $form = $(this).parent();
            	var msg = "";
            	if(typeof(isManualBonus) != 'undefined') {
            		$form.find("input[name=isManualBonus]").val(isManualBonus);
            		if(isManualBonus == "Y") {
            			msg = "确定批量设置为手动返现吗?";
            		} else if(isManualBonus == "N"){
            			msg = "确定批量设置为自动返现吗?";
            		}
            	} else if(typeof(isRefundable) != 'undefined') {
            		$form.find("input[name=isRefundable]").val(isRefundable);
            		msg = "确定批量设置为不返现吗?";
            	} else {
            		alert("发生异常!");
            		return false;
            	}
            	if($.trim(msg) == "") {
            		alert("发生异常!");
            		return false;
            	}
            	var confirm = window.confirm(msg);
        		if (confirm) {
        			$.post(
	                        "/super_back/prod/batchOperate.do",
	                        $("#result_form").serialize(),
	                        function (data) {
	                        	var dt = eval("(" + data +")");
	                            if (dt.success) {
	                                alert("操作成功");
	                                location.reload(window.location.href);
	                            } else {
	                                alert(dt.msg);
	                            }
	                        }
	                );
        		}
            });
		});
	</script>
	</body>
</html>