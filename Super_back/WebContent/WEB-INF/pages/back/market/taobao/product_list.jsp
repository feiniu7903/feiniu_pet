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
		<title>所有门票产品</title>
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
			
			$("a.pushTaobao").click(function(){
				var productId=$(this).attr("data");
				$("#pushTaobao").showWindow({
					url:"<%=basePath%>taobaoProd/toCate.do",
					data:{"productId":productId}});
			});
			
			$("a.uploadMap").click(function() {
				var paraTaobaoId = $(this).attr("paraTaobaoId");
				var paraProductId = $(this).attr("paraProductId");
				$.getJSON("${basePath}/taobaoProd/uploadMap.do",{taobaoId:paraTaobaoId,productId:paraProductId},function(myJSON){
		        	if (myJSON.flag == "success") {
		        		alert("上传地图成功!");
		        		$("#query_form").submit();
		        	} else {
		        		alert(myJSON.msg);
		        	}
		        });
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
					所有门票产品
				</h3>
				<form id="query_form" action="<%=basePath%>taobaoProd/queryProductList.do" method="post" >
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
							<td>
								<em>标的：</em>
							</td>
							<td>
								<input type="text" name="placeName" id="searchPlace"
									value="<s:property value="placeName"/>" />
								<input type="hidden" name="placeId" id="comPlaceId"
									value="<s:property value="placeId"/>" />
							</td>
						</tr>
						<tr>							
							<td>
								<em>上下线时间：</em>
							</td>
							<td>
								<s:select list="#{'':'请选择','true':'有效','false':'无效'}"
									name="onlineStatus"></s:select>
							</td>
							<td>
								<em>状态：</em>
							</td>
							<td>
								<s:select list="#{'':'请选择','true':'上线','false':'下线'}"
									name="onLine"></s:select>
							</td>
							<td>
								<em>所属公司</em>
							</td>
							<td>
								<s:select list="filialeNameList" name="filialeName"
									listKey="code" listValue="name"></s:select>
							</td>
							<td><em>类别ID：</em></td>
							<td><input type="text" name="prodBranchId"
									value="<s:property value="prodBranchId"/>" /></td>
							<td><em>期票：</em></td>
       		 				<td><input type="checkbox" name="isAperiodic" value="true" <s:if test='isAperiodic=="true"'>checked</s:if> /></td>
						</tr>
						<tr>
							<td><em>已上传淘宝：</em></td>
       		 				<td><input type="checkbox" name="isTbOnline" value="Y" <s:if test='isTbOnline=="Y"'>checked</s:if> /></td>
       		 				<td>
       		 				<em>上传淘宝时间：</em>
       		 				</td>
       		 				<td  colspan="3">
								<input id="startTime" name="startTime" type="text" 
									onclick="WdatePicker({isShowClear:true,readOnly:true})" 
									class="input_text01 Wdate"
									value="<s:property value="startTime"/>" />
									~
								<input id="endTime" name="endTime" type="text" 
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
						</tr>
						<tr>
							<td>
								<input type="submit" class="button" id="serBtn" value="查询" />
							</td>
						</tr>
					</table>
				</form>
			</div>
			<div class="row2">
				<input type="button" class="button" id="updBtn" value="更新淘宝状态" />
				<table border="0" cellspacing="0" cellpadding="0" class="newTable">
					<tr class="newTableTit">
						<td class="idt">
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
						<td class="isTaobao">
							是否已上淘宝
						</td>
						<td class="taobaotime">
							上传淘宝时间
						</td>
						<td class="taobaostatus">
							淘宝状态
						</td>
						<td class="isMap">
							已传地图
						</td>
						<td class="cate">
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
								<s:property value="strIsTbOnline" />
							</td>
							<td>
								<s:date name="createDate" format="yyyy-MM-dd HH:ss:mm" />
							</td>
							<td>
								<s:property value="strTbStatus" />
							</td>
							<td>
								<s:property value="strIsMaped" />
							</td>
							<td>
								<s:property value="cateName" />
							</td>
							<td>
								<s:if test="tbProductReturnId == null">
									<a href="javascript:void(0)" data="<s:property value="productId"/>" class="pushTaobao">发布淘宝</a>|
								</s:if>
								<s:else>
									<a href="javascript:void(0)" paraTaobaoId="<s:property value="tbProductReturnId"/>" paraProductId="<s:property value="productId"/>" class="uploadMap">上传地图</a>|
								</s:else>
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
			<div id="limitSaleDiv" style="display: none"></div>
			<div id="conditionDiv" style="display: none"></div>
			<div id="recommendDiv" style="display: none">
				<form onsubmit="return false">
					<input type="hidden" name="product.productId" />
					<center style="margin:10 auto;">建议填写，当前产品的突出特点，产品的优惠活动等抓人眼球的信息。</center>
					<table style="width: 600px;">
						<tr>
							<td width="80">
								推荐一:
							</td>
							<td>
								<input type="text" name="product.recommendInfoFirst" rows="3"
									style="width: 100%" class="length" maxlength="15" />
								字数控制在15字以内，已输入：
								<span class="count"></span>&nbsp;&nbsp;<span style="color:red;">网站频道页，调取使用</span>
							</td>
						</tr>
						<tr>
							<td>
								推荐二:
							</td>
							<td>
								<input type="text" name="product.recommendInfoSecond" rows="3"
									style="width: 100%" class="length" maxlength="30" />
								字数控制在30字以内，已输入：
								<span class="count"></span>&nbsp;&nbsp;<span style="color:red;">网站使用的地方：搜索/dest/频道列表</span>
								
							</td>
						</tr>
						<tr>
							<td>
								推荐三:
							</td>
							<td>
								<textarea type="text" name="product.recommendInfoThird" rows="3"
									style="width: 100%" class="length" maxlength="50"></textarea>
								字数控制在50字以内，已输入：
								<span class="count"></span>
							</td>
						</tr>
					</table>
				</form>
			</div>
		</div>
		<div id="pushTaobao"></div>
		<div id="uploadMap"></div>
	</body>
</html>


