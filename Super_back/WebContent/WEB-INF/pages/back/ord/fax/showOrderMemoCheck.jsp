<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<s:set scope="request" name="hideCss" value="true" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>特殊用户要求-审核任务</title>
<script type="text/javascript" src="${basePath}/js/base/jquery-1.4.4.min.js"></script>
<script type="text/javascript" src="${basePath}/js/ui/jquery-ui-1.8.5.js"></script>
<script type="text/javascript" src="/pet_back/js/base/dialog.js"></script>
<link rel="stylesheet" href="<%=basePath %>style/ui-common.css">
<link rel="stylesheet" href="<%=basePath %>style/ui-components.css">
<link rel="stylesheet" href="<%=basePath %>style/panel-content.css">
<link rel="stylesheet" type="text/css" href="${basePath }themes/base/jquery.ui.all.css" >
<link rel="stylesheet" type="text/css" href="${basePath }themes/ebk/admin.css" >
<link rel="stylesheet" type="text/css" href="${basePath }themes/ebk/ui-components.css" >
<link rel="stylesheet" type="text/css" href="${basePath}style/ui-common.css"></link>
<link rel="stylesheet" type="text/css" href="${basePath}style/panel-content.css"></link>
<link rel="stylesheet" type="text/css" href="${basePath }themes/base/jquery.jsonSuggest.css"></link>
</head>
<body>
<div >
		<table class="p_table form-inline" >
				<tr>
				<td class="p_label"><span>订单号：</span></td><td><a target="_blank" href="<%=basePath%>	ord/order_monitor_list!doOrderQuery.do?pageType=monitor&orderId=${ordOrder.orderId}">${ordOrder.orderId}</a></td><td class="p_label"><span>游玩时间：</span></td><td>${ordOrder.zhVisitTime }</td><td class="p_label"><span>订单状态：</span></td><td>${ordOrder.zhOrderStatus }</td>
				</tr>
				<tr>
				<td class="p_label"><span>销售产品名称：</span></td><td colspan="5"><s:property value="ordOrder.mainProduct.productName"/></td>
				</tr>
				<tr>
				<td class="p_label"><span>客人姓名：</span></td><td colspan="5"><s:property value="ordOrder.contact.name"/>(<s:property value="ordOrder.contact.mobile"/>)</td>
				</tr>
		</table>
        <table   class="p_table table_center">
            <thead>
                <tr>
                    <th>备注类型</th>
                    <th>内容</th>
                    <th>提交人</th>
					<th>创建时间</th>
                </tr>
            </thead>
			<s:iterator id="orderMemos"  value="orderMemosList"> 
				 <tr>
                    <td>${orderMemos.zhType }<s:if test="userMemo=='true'"><b style="color: #E40000;">(用户特殊要求)</b></s:if></td>
                    <td>${orderMemos.content }</td>
                    <td>${orderMemos.operatorName }</td>
					<td>${orderMemos.zhCreateTime }</td>
				</tr>
			</s:iterator>
		</table>
        <table  id="faxMemoTable" class="p_table table_center">
            <thead>
                <tr>
                    <th>采购产品名称</th>
                    <th>数量</th>
					<th>产品类型</th>
                    <th>供应商</th>
                    <th>凭证方式</th>
                    <th>处理状态</th>
                    <th>传真/EBK备注</th>
                </tr>
            </thead>
            <tbody>
            	<s:iterator id="itemMeta" value="ordOrder.allOrdOrderItemMetas" status="im">
	                <tr>
	                    <td>${itemMeta.productName }</td>
	                    <td>${itemMeta.productQuantity * itemMeta.quantity}</td>
						<td>${itemMeta.zhProductType}</td>
						<td><a href="javascript:void(0)" data="${itemMeta.supplierId}" class="showDetail" ><s:property value="supplierMap.get(#itemMeta.supplierId).supplierName"/></a></td>					
						 <s:if test="supplierFlag=='true'" >
						 	<td>EBK</td>
						 	<td>${itemMeta.zhCertificateTypeStatus}</td>
		                    <td><input type="text" id="${itemMeta.orderItemMetaId}"  oldValue="${itemMeta.faxMemo}" value="${itemMeta.faxMemo}"/></td>
						 </s:if>                  	
                    	<s:elseif test="sendFax=='true'" >
                    		<td>传真</td>
                    		<td>${itemMeta.zhEbkCertificateType} <s:property value="#request.faxStatusMap.get(orderItemMetaId)"/> </td>
		                    <td><input type="text" id="${itemMeta.orderItemMetaId}"  oldValue="${itemMeta.faxMemo}" value="${itemMeta.faxMemo}"/></td>
	                    </s:elseif>
	                    <s:else>
	                    	<td>二维码</td>
	                    	<td></td>
	                    	<td></td>
	                    </s:else>
	                </tr>
               </s:iterator>
            </tbody>
        </table>
        <!-- div p_box -->
        <table  class="p_table form-inline">
        	<tr>
        		<td align="right" width="10%">沟通结果：</td>
        		<td align="left" valign="bottom" colspan="2"><textarea style="width:500px" id="MemoContent" rows="5"></textarea>
        			<s:if test="workTaskId!=null">
        				<a onclick="SaveMeno();" style="height: 40px; font-weight: bold;cursor: pointer;">只保存沟通结果</a>
        			</s:if>
        		</td>
        	</tr>
        	<tr>
        		<td colspan="3" style="text-align: center;">
	        		<input type="button" onclick="EbkSendOrder();" value="保存传真备注并发送传真/ebk" class="btn btn-small w5" style="height: 40px; font-weight: bold;"/>
	        		<s:if test="workTaskId!=null">
	        			<input type="button" onclick="Complete();" value="完成" class="btn btn-small w5" style="height: 40px; font-weight: bold;"/>
	        		</s:if>
        		</td>
        	</tr>
        </table>
         <input type="hidden" id="orderMemoID" 
					value="${ordOrder.orderId}" />
        <input type="hidden"  id="orderId"
					value="${ordOrder.orderId}" />
		<input type="hidden"  id="operateType"
					value="${operateType}" />
       <div id="supplierDetail" url="/pet_back/sup/detail.do"></div>
       
	<script type="text/javascript">
		$(function(){
			$(".showDetail").click(function(){
				var supplierId=$(this).attr("data");
				$("#supplierDetail").showWindow({
					data:{"supplierId":supplierId}
				});
			});
		});
		function EbkSendOrder(){
			var url="ord/EbkSendOrder.do";
			var json="orderId="+$("#orderId").val()
				+"&operateType="+$("#operateType").val()
				+"&content="+$("#MemoContent").val()
				+"&workTaskId="+'${workTaskId}'
			$("#faxMemoTable input").each(function(){
				if($(this).attr("oldValue") != $(this).val()) {
					json+="&ordItemIds="+$(this).attr("id");
					json+="&faxMemos="+$(this).val();
				}
			});
			if(confirm("是否确定要发送传真/ebk")){
				$.ajax({type:"POST", url: url,data:json, success:function (result) {
					if (result=="SUCCESS") {
						alert("保存成功");
						if('${workTaskId}'!=''){
							parent.location.reload();
						}
					} else {
						alert(result);
					}
				}});
			}
			
		}
		function SaveMeno() {
			if(document.getElementById("MemoContent").value=="") {
			   alert("与客户沟通内容不能为空");
			   return false;
			}
		
			var url="ord/onlySaveResult.do";		
			//window.location=url
			var json={
					"orderId":$("#orderId").val(),
					"content":$("#MemoContent").val()
			};
			
			$.ajax({type:"POST", url: url,data:json, success:function (result) {
				var res = eval("("+result+")");
				if (res) {
					alert("保存成功");	
				} else {
					alert("保存失败");
				}
			}});
			
		}
		function saveFaxMeno(ordItemId)
		{

			var url="ordItemFax/saveOrUpdateMemo.do";	
			var json={
					"ordItemId":ordItemId,
					"faxMemo":document.getElementById(ordItemId).value
				};
			//window.location=url
			$.ajax({type:"POST", url: ${basePath} + url,data:json, success:function (result) {
				var res = eval(result);
				if (res) {
					alert("保存成功");
				} else {
					alert("保存失败");
				}
			}});
		}
		//完成
		function Complete(){
			if(confirm("是否确定要结束工单")){
				var url="ord/complete.do";	
				var json={
						"orderId":$("#orderId").val(),
						"workTaskId":'${workTaskId}'
					};
				$.ajax({type:"POST", url: url,data:json, success:function (result) {
					if (result=="SUCCESS") {
						alert("操作成功");
						parent.location.reload();
					} else {
						alert("操作失败");
					}
				}});
			}
		}
	</script>
	</div>
</body>
</html>