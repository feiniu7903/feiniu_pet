<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="mis" uri="/tld/lvmama-tags.tld"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>上传淘宝上的门票产品</title>

		<link rel="stylesheet" href="../js/op/groupBudget/component/My97DatePicker/skin/default/datepicker.css">
		<link rel="stylesheet" href="../js/op/groupBudget/component/My97DatePicker/skin/WdatePicker.css">
		<script src="../js/op/groupBudget/component/My97DatePicker/WdatePicker.js"></script>
		<script src="../js/op/groupBudget/component/My97DatePicker/lang/en.js"></script>
		<s:include value="/WEB-INF/pages/back/base/jquery.jsp" />
		<%@ include file="/WEB-INF/pages/back/base/jsonSuggest.jsp"%>
		<%@ include file="/WEB-INF/pages/back/base/timepicker.jsp"%>
		<script type="text/javascript" src="<%=basePath%>js/base/dialog.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/prod/condition.js">
		</script>
		<script type="text/javascript" src="<%=basePath%>js/market/juhuasuan.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/base/jquery.form.js"></script>
		<script type="text/javascript" >
		$(function(){
			$("#searchPlace").jsonSuggest({
				url:"/super_back/prod/searchPlace.do",
				maxResults: 20,
				minCharacters:1,
				onSelect:function(item){
					$("#comPlaceId").val(item.id);
				}
			});
			
			$("a.pushJuHuaSuan").click(function(){
				var tbProductInterfaceId=$(this).attr("data");
				$("#pushJuHuaSuan").showWindow({
					url:"<%=basePath%>juhuasuanProd/toCate.do",
					data:{"tbProductInterfaceId":tbProductInterfaceId}});
			});
			$("#updBtn").click(function() {
                jQuery.ajax({
                    url: "${basePath}/taobaoProd/updateTaobaoStatus.do",
                    dataType:'json',
                    data: $('#query_form').serialize(),
                    type: "POST",
                    success: function(myJSON){
                        if (myJSON.flag == "success") {
                        	alert("更新淘宝状态成功!");
    		        		$("#query_form").submit();
                        } else {
                        	alert(myJSON.msg);
                        }
                    }
                });
                return false;
            });
		})
		</script>
	</head>

	<body>
		<div class="main main02">
			<div class="row1">
				<h3 class="newTit">
					淘宝门票产品
				</h3>
				<form id="query_form" name="query_form" action="<%=basePath%>juhuasuanProd/queryProductList.do" method="post">
					<input type="hidden" name="productType"
						value="<s:property value="productType"/>" />
					<table border="0" cellspacing="0" cellpadding="0" class="newInput"
						width="100%">
						<tr>
							<td>
								<em>产品名称：</em>
							</td>
							<td>
								<input type="text" class="newtext1" name="productName"
									value="<s:property value="productName"/>" />
							</td>
							<td>
								<em>产品ID：</em>
							</td>
							<td>
								<input type="text" class="newtext1" name="productId"
									value="<s:property value="productId"/>" />
							</td>
							<td>
								<em>产品编号：</em>
							</td>
							<td>
								<input type="text" class="newtext1" name="bizcode"
									value="<s:property value="bizcode"/>" />
							</td>
							<td><em>类别ID：</em></td>
							<td>
								<input type="text" name="prodBranchId" class="newtext1"
									value="<s:property value="prodBranchId"/>" />
							</td>
						</tr>
						<tr>
							<td>
								<em>标的：</em>
							</td>
							<td>
								<input type="text" name="placeName" id="searchPlace"
									value="<s:property value="placeName"/>" />
								<input type="hidden" name="placeId" id="comPlaceId"
									value="<s:property value="placeId"/>" />
							</td>
							<td>
								<em>上下线时间：</em>
							</td>
							<td>
								<s:select list="#{'':'请选择','true':'有效','false':'无效'}"
									name="onlineStatus"></s:select>
							</td>
							<td>
								<em>所属公司</em>
							</td>
							<td>
								<s:select list="filialeNameList" name="filialeName"
									listKey="code" listValue="name"></s:select>
							</td>
							<td>
								<em>状态：</em>
							</td>
							<td>
								<s:select list="#{'':'请选择','true':'上线','false':'下线'}"
									name="onLine"></s:select>
							</td>
						</tr>
						<tr>
							<td><em>上传淘宝时间：</em></td>
							<td colspan="3">
								<input id="startTime" name="startTime" type="text" 
									onclick="WdatePicker({isShowClear:true,readOnly:true})" 
									class="input_text01 Wdate"
									value="<s:property value="startTime"/>" />
									~
								<input id="endTime" name=endTime type="text" 
									onclick="WdatePicker({isShowClear:true,readOnly:true})" 
									class="input_text01 Wdate" 
									value="<s:property value="endTime"/>" />
							</td>
							<td>
								<em>淘宝状态：</em>
							</td>
							<td>
								<s:select list="#{'':'请选择','onsale':'上架','instock':'下架'}"
									name="tbStatus"></s:select>
							</td>
							<td><em>期票：</em></td>
       		 				<td>
       		 					<input type="checkbox" name="isAperiodic" value="true" 
       		 							<s:if test='isAperiodic=="true"'>checked</s:if> />
       		 				</td>
						</tr>
						<tr>
							<td><em>上传聚划算时间：</em></td>
							<td colspan="6">
								<input id="uploadJhsTimeStart" name="uploadJhsTimeStart" type="text" 
									class="input_text01 Wdate" 
									onclick="WdatePicker({isShowClear:true,readOnly:true})" 
									value="<s:property value="uploadJhsTimeStart"/>" /> ~
								<input id="uploadJhsTimeEnd" name="uploadJhsTimeEnd" type="text" 
									class="input_text01 Wdate" 
									onclick="WdatePicker({isShowClear:true,readOnly:true})" 
									value="<s:property value="uploadJhsTimeEnd"/>" />
							</td>
							<td>
								<input type="submit" class="button" value="查询" />
							</td>
						</tr>
					</table>
				</form>
			</div>
			<div class="row2">
				<input type="button" class="button" id="updBtn" value="更新淘宝状态" />
				<table border="0" cellspacing="0" cellpadding="0" class="newTable">
					<tr class="newTableTit">
						<td class="idw">
							淘宝ID
						</td>
						<td class="idw">
							产品ID
						</td>
						<td class="prodw">
							销售产品编号
						</td>
						<td>
							销售产品名称
						</td>
						<td class="timew">
							上线时间
						</td>
						<td class="timew">
							下线时间
						</td>
						<td class="timew">
							录入时间
						</td>
						<td class="statew">
							状态
						</td>
						<td class="timew">
							上传淘宝时间
						</td>
						<td class="statew">
							淘宝状态
						</td>
						<td class="timew">
							发布聚划算时间
						</td>
						<td>
							淘宝类目
						</td>
						<td class="opt_w">
							操作
						</td>
					</tr>
					<s:iterator value="pagination.records" var="tbProduct">
						<tr id="tr_<s:property value="prodProduct.productId"/>">
							<td>
								<s:property value="tbProductReturnId" />
							</td>
							<td>
								<s:property value="prodProduct.productId" />
							</td>
							<td>
								<s:property value="prodProduct.bizcode" />
							</td>
							<td>
								<s:property value="prodProduct.productName" />
							</td>
							<td>
								<s:date name="prodProduct.onlineTime" format="yyyy-MM-dd" />
							</td>
							<td>
								<s:date name="prodProduct.offlineTime" format="yyyy-MM-dd" />
							</td>
							<td>
								<s:date name="prodProduct.createTime" format="yyyy-MM-dd HH:ss:mm" />
							</td>
							<td class="online">
								<s:property value="prodProduct.strOnLine" />
							</td>
							<td>
								<s:property value="tbPushDateStr" />
							</td>
							<td>
								<s:property value="strTbStatus" />
							</td>
							<td>
								<s:date name="jhsPushDate" format="yyyy-MM-dd HH:ss:mm" />
							</td>
							<td>
								<s:property value="cateName" />
							</td>
							<td>
								<a href="javascript:void(0)" data="<s:property value="tbProductInterfaceId"/>" class="pushJuHuaSuan">发布到聚划算</a>|
								<a href="http://www.lvmama.com/product/preview.do?id=<s:property value="productId"/>"
										target="_blank">预览</a>
							</td>
						</tr>
					</s:iterator>
				</table>
			</div>
			<table width="90%" border="0" align="center">
				<s:include value="/WEB-INF/pages/back/base/pag.jsp" />
			</table>
		</div>
		<div id="pushJuHuaSuan" url="<%=basePath%>juhuasuanProd/toCate.do"></div>
	</body>
</html>


