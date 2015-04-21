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
<title>产品管理</title>
<link rel="stylesheet" type="text/css" href="<%=basePath%>css/ui-common.css"></link>
<link rel="stylesheet" type="text/css" href="<%=basePath%>css/ui-components.css"></link>
<link rel="stylesheet" type="text/css" href="<%=basePath%>css/panel-content.css"></link>

<jsp:include page="/WEB-INF/pages/pub/jquery.jsp" />
<script charset="utf-8" src="<%=basePath %>kindeditor-4.0.2/kindeditor.js"></script>
<script charset="utf-8" src="<%=basePath %>kindeditor-4.0.2/lang/zh_CN.js"></script>

<script type="text/javascript">
	$(function(){
	});
</script>
</head>
<body>
<div id="addOrModifyShopProductDiv" style="display: none"></div> 
<div class="ui_title">
	<ul class="ui_tab">
		<li class="active"><a href="#">产品管理</a></li>
	</ul>
</div>

<div class="iframe-content">
	<div class="p_box">
		<form action="<%=basePath%>shop/shopProduct/queryProductList.do" method="get" id="queryFrom">
			<table class="p_table form-inline" width="100%">
			<tr>
				<td class="p_label">产品编号：</td>
				<td>
					<input type="text" name="productCode" value="${productCode}"/>
				</td>
				<td class="p_label">产品名称：</td>
				<td colspan="3">
					<input type="text" name="productName" value="${productName}"/>
				</td>
				
			</tr>
			<tr>
				<td class="p_label">兑换类型：</td>
				<td>
					<s:select list="#{'':'全部','POINT_CHANGE':'积分兑换','RAFFLE':'抽奖'}" 
   									name="changeType" >
   					</s:select>
				</td>
				<td class="p_label">产品类型：</td>
				<td>
					<s:select list="#{'':'全部','PRODUCT':'实物','COUPON':'优惠券','COOPERATION_COUPON':'合作网站优惠券'}" 
   									name="productType" >
   					</s:select>
				</td>
				<td class="p_label">上线状态：</td>
				<td>
					<s:select list="#{'':'全部','Y':'上线','N':'已下线'}" 
   									name="isValid" >
   					</s:select>
				</td>
			</tr>
			</table>
			<p class="tc mt20">
				<button class="btn btn-small w5" type="submit">查询</button>　
				<button id="clearBtn" class="btn btn-small w5" type="reset">清空</button>&nbsp;&nbsp;&nbsp;&nbsp; 
				<button class="btn btn-small w5" onclick="addOreditShopProduct()"  type="button">新建产品</button>
			</p>
		</form>
	</div>
	
	<div class="p_box">
		<table class="p_table table_center">
			<tr>
				<th width="100px">产品编号</th>
				<th width="100px">兑换类型</th>
				<th width="100px">产品类型</th>
				<th>产品名称</th>
				<th width="70px" align="right">兑换积分</th>
				<th width="70px" align="right">库存</th>
				<th width="70px" align="center">状态</th>
				<th width="150px">推荐位置</th>
				<th width="200px">操作</th>
			</tr>
			<s:iterator value="productList" var="product">
				<tr>
					<td>${product.productCode}</td>
					<td>${product.chChangeType}</td>
					<td>${product.chProductType}</td>
					<td>${product.productName}</td>
					<td>${product.pointChange}</td>
					<td>${product.stocks}</td>
					<td>${product.chIsValid}</td>
					<td>${product.chCommend}</td>
					<td>
						<button onclick='doViewLog(${product.productId})' type="button"  style="width:65px">查看日志</button>
						<button onclick='addOreditShopProduct(${product.productId})' type="button" style="width:65px">编辑</button>
						<s:if test='"N".equals(isValid)'>
							<button onclick='doValid(${product.productId})' type="button" style="width:65px">上线</button>
						</s:if>
						<s:else>
							<button onclick='doValid(${product.productId})' type="button" style="width:65px">下线</button>
						</s:else>
						<s:if test='"true".equals(editStock)'>
							<button  onclick="toEditStock('${product.productId}','${product.productName}')" type="button" style="width:65px">编辑库存</button>
						</s:if>
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
    //新增产品OR修改产品
    function addOreditShopProduct(productId){
    	var data=$("#queryFrom").serialize();
    	if(typeof(productId) == "undefined"){
    		data+="&_=" + (new Date).getTime();
    	}else{
    		data+="&productId="+productId+"&_=" + (new Date).getTime();
    	}
    	$("#addOrModifyShopProductDiv").load(
    	"<%=basePath%>shop/shopProduct/addOrModifyShopProduct.do?"+data,
		function(){
   			if(productId!=null){
   				$(this).dialog({
   					modal:true,
   		   			title:"修改产品",
   		   			width:950,
   		   			height:650
   				});
   			}else{
   				$(this).dialog({
   					modal:true,
   		   			title:"新建产品",
   		   			width:950,
   		   			height:650
   				});
   			}
		});
    }
    //上下线
    function doValid(productId){
    	var url = "<%=basePath%>shop/shopProduct/editValidStatus.do?_=" + (new Date).getTime();
    	 $.ajax({
     	 	url: url,
     	 	data: {"productId":productId},
     	 	dataType:"json",
     	 	success: function(result) {
	        		if (result.success) {
	        			alert('操作成功！');
	        			document.location.reload();
	        		} else {
	        			alert(result.errorMessage);
	        		}
     	 	}
     	 });
    }
    //编辑库存
    function toEditStock(productId,productName){
    	var data="productId="+productId+"&productName="+productName+"&_=" + (new Date).getTime();
    	window.open("<%=basePath%>shop/shopProduct/toEditStock.do?"+data,"编辑库存","directories=0,width=600,height=350,menubar=0,resizable=0,scrollbars=0,status=0,toolbar=0");
    }
    
    //查看日志
    function doViewLog(productId){
    	var data="productId="+productId+"&_=" + (new Date).getTime();
    	$("#addOrModifyShopProductDiv").load(
	    	"<%=basePath%>shop/shopProduct/viewLog.do?"+data,
			function(){
	   			if(productId!=null){
	   				$(this).dialog({
	   					modal:true,
	   		   			title:"查看日志",
	   		   			width:950,
	   		   			height:550
	   				});
	   			}
			});
    }
</script>
</body>
</html>


