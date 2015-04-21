<%@ page language="java" import="java.util.*,com.lvmama.comm.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta content="text/html; charset=utf-8" http-equiv="Content-Type">
<link rel="stylesheet" type="text/css" href="<%=basePath%>themes/icon.css">
<link rel="stylesheet" type="text/css" href="<%=basePath%>themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="<%=basePath%>themes/mis.css">

<script src="http://pic.lvmama.com/min/index.php?f=js/new_v/jquery-1.7.2.min.js,/js/ui/lvmamaUI/lvmamaUI.js"></script>
<title>编辑库存</title>
<script type="text/javascript">
	$(function(){
		$('.icon-tag2').ui('lvtip',{
	  		placement: 'bottom',
	  		delay: 100
   		 });
	});
	function exportStock(productId){
		var url = "<%=basePath%>shop/shopProduct/exportStock.do?productId="+productId;
		window.open(url);
	}
	//清除流.以免报错
	  function clear(){
	   out.clear();
	   out = pageContext.pushBody();
	  }

</script>
</head>
<body>
	<form method='post' action='<%=basePath%>shop/shopProduct/saveCooperateCouponStock.do' enctype='multipart/form-data'>
		<input type="hidden" name="productId" value="${productId }"/>
		<input type="hidden" name="productName" value="${productName }"/>
		<input type="hidden" name="count" value="${count }"/>
		<table  width="100%" class="datatable">
			<thead>
				<tr>
					<th colspan="2">编辑<font color="red">${productName}</font>产品的库存   </th>
				</tr>
			</thead>
			<tr>
				<td width="100">当前库存</td>
				<td><font color="red">${count}</font></td>
			</tr>
			<tr>
				<td>上传文件</td>
				<td>
					<input type="file" id="uploadFile" style="font-size:13px;margin:0;padding:0;" name="file"/>
					<i class="icon-tag2" tip-content="1.文件格式需为txt,编码格式需为UTF-8<br>2.中文、英文、数字组成，可有空格 <br>3.每行100个字节，文件最大不超过5M">温馨小提示</i>
				</td>
			</tr>
			<tr>
				<td>清空原库存</td>
				<td><s:checkbox name="cleanOldData"/></td>
			</tr>
			<tr>
				<td colspan="2" align="center">
					<s:submit value="上传更新"/>
					<input type="button" value="导出库存" onclick="exportStock('${productId }');clear();"/>
					<s:if test="null!=messageText">
						<font color="red" size="3"><s:property value="messageText"/></font>
						<a href="javascript:window.opener=null;window.open('','_self');window.close();">关闭</a>
					</s:if>
				</td>
			</tr>
		</table>
	</form>
	<s:if test="null!=errorList && errorList.size() > 0">
	<table  width="100%" class="datatable">
		<tr>
			<td colspan="3" align="center">
				<font color="red">上传失败</font>
			</td>
		</tr>
		
		<tr>
			<th>行号</th>
			<th>信息</th>
			<th>出错原因</th>
		</tr>
		
		<tbody>
			<s:iterator value="errorList" var="coupon"  status="cp" >
				<tr>
					<td><s:property value="#coupon.id"/></td>
					<td><span title="<s:property value="#coupon.couponInfo"/>"><s:property value="@com.lvmama.comm.utils.StringUtil@subStringStr(#coupon.couponInfo,30)"/></span></td>
					<td><s:property value="#coupon.valid"/></td>
				</tr>
			</s:iterator>
		</tbody>
	</table>
	</s:if>
</body>
</html>