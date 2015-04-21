<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%    
String path = request.getContextPath();   
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";      
%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">
<title>上航线路产品入库</title>
<link rel="stylesheet" type="text/css" href="/super_back/themes/base/jquery.ui.all.css" >
<link rel="stylesheet" type="text/css" href="/super_back/style/ui-components.css" >
<link rel="stylesheet" type="text/css" href="/super_back/style/ui-common.css"></link>
<link rel="stylesheet" type="text/css" href="/super_back/style/panel-content.css"></link>
<script type="text/javascript" src="/super_back/js/base/jquery-1.4.4.min.js"></script>
<script type="text/javascript" src="/super_back/js/base/jquery-ui-1.8.5.js" ></script>
<script type="text/javascript" src="/super_back/js/base/log.js" ></script>
<script type="text/javascript" src="/pet_back/js/base/dialog.js"></script>
<script type="text/javascript">
	
	$(function(){
		$("#duijieForm [name='routeType']").val("${routeType}");
		$("#duijieForm [name='supplierId']").val("${supplierId}");
	});
	
	
	function productDetailDiv(supplierProductId,lvmamaproductId){
		var url = "<%=basePath%>/route/shholidayProductInfoDetail.do?productId="+supplierProductId+"&pageId="+lvmamaproductId;
		var titleName = "供应商线路产品详情";
		$("<iframe frameborder='0' id='showDetailWin'></iframe>").dialog({
			autoOpen: true, 
	        modal: true,
	        title : titleName,
	        position: 'top',
	        width: 880
		}).width(840).height(550).attr("src",url);
		
	}
	
	function saveStocked(id){
		if(!confirm("入库操作会有延迟，请耐心等待！")){
			alert('操作取消');
			return;
		}
		
		var url = "<%=basePath%>/route/saveMetaProductUnStocked.do?productId="+id+"&supplierId="+${supplierId};;
		$.get(url, function(dt){
			var data=dt;
			if(data.success){
				alert("操作成功");
				$('#'+id).html(data.productId);
				$('#a'+id).html("入库");
				//location.reload(true);
			}else{
				alert(data.msg);
			}
		  });
		
	}
	
	function checkDuijieForm(){
		var supplierName = $.trim($("#duijieForm #supplierId").val());
		if(supplierName == ""){
			alert("必须选择一个供应商名称！");
			return false;
		}
		$("#duijieForm #destination").val($.trim($("#duijieForm #destination").val()));
		$("#duijieForm #keyword").val($.trim($("#duijieForm #keyword").val()));
		document.duijieForm.submit();
	}
</script>
</head>
<body>
<form name='duijieForm' id="duijieForm" method='post' action='<%=basePath %>/route/duijieProductInStock.do'>
	<table class="p_table form-inline">
		<tr>
			<td class="p_label">供应商名称：</td>
			<td>
			<select id="supplierId" name="supplierId">
			<option value=""></option>
			<s:iterator value="supplierList" var="sp">
			<option value="${ sp.supplierId}">${ sp.supplierName}</option>
			</s:iterator>
			</select>
			</td>
			<td class="p_label">目的地：</td>
			<td width="12%">
				<input type="text" id="destination" name="destination"  value="${destination}"/>
			</td>
			<td class="p_label">线路类型</td>
			<td >
			<select id="routeType" name="routeType">
				<option value=""></option>
				<s:iterator value="routeTypeList" var="item">
	             <option value="${item.code}">${item.cnName }</option>
             	</s:iterator>
			</select>
			</td>
			<td class="p_label">关键字</td>
			<td><input type="text" id="keyword" name="keyword"  value="${keyword}"/></td>
		</tr>
		<tr>
			<td colspan="4"/>
			<td colspan="4" align="left">
			<input type="button" value="查 询"
				class="right-button08 btn btn-small" id="btnDistributorQuery" onclick="checkDuijieForm()"/>
			</td>
		</tr>
	</table>
</form>
<table class="p_table table_center Padding5">
	<tbody>
		<tr >
			<th width="6%">ID</th>
			<th width="6%">销售产品ID</th>
			<th >产品名称</th>
			<th width="10%">目的地</th>
			<th width="15%">是否已入库</th>
			<th width="10%">操作</th>
		</tr>
		<s:iterator value="productInfoPage.items" var="pr">
			<tr>
				<td >${pr.supplierProdId }</td>
				<td id="<s:property value="#pr.supplierProdId"/>">${pr.lvmamaProdId }</td>
				<td> ${pr.supplierProdName } </td>
				<td>${pr.destinationCity }</td>
				<td>
				<s:if test="#pr.valid">是</s:if>
				<s:else>否</s:else>
				</td>
				<td>
					<a href="javascript:productDetailDiv('${pr.supplierProdId }','${pr.lvmamaProdId }');">查看</a> &nbsp;&nbsp;
					<s:if test="#pr.valid">
					入库
					</s:if>
					<s:else>
					<span id="a<s:property value="#pr.supplierProdId"/>">
					<a href="javascript:saveStocked('${pr.supplierProdId}');" >入库</a>
					</span>
					</s:else>
				</td>
			</tr>
		</s:iterator>
		<tr>
			<td colspan="2" align="left">
			</td>
			<td colspan="3" align="right" style="text-align:right">
			 <s:property escape="false" value="@com.lvmama.comm.utils.Pagination@pagination(productInfoPage)"/>
			</td>
		</tr>
		
	</tbody>
</table>
</body>
</html>





