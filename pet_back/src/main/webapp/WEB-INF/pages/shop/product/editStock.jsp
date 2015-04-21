<%@ page language="java" import="java.util.*,com.lvmama.comm.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
	    <meta content="text/html; charset=utf-8" http-equiv="Content-Type">
		<link rel="stylesheet" type="text/css" href="<%=basePath%>themes/icon.css">
		<link rel="stylesheet" type="text/css" href="<%=basePath%>themes/default/easyui.css">
		<link rel="stylesheet" type="text/css" href="<%=basePath%>themes/mis.css">
		<script type="text/javascript" src="<%=basePath %>js/base/jquery-1.4.4.min.js"></script>
		<script type="text/javascript" src="<%=basePath %>js/base/ajaxupload.js"></script>
  </head>
  <body style="padding: 0 0 0 0; margin:0 0 0 0; font-size: 12px">
  	<s:form id="viewContentForm" action="/shop/saveCooperateCouponStock.do" method ="POST" enctype="multipart/form-data">
		<s:hidden name="productId"/>
		<s:hidden name="productName"/>
		<s:hidden name="count"/>
		<table  width="100%" class="datatable">
			<thead>
				<tr>
					<th colspan="2">编辑<font color="red"><s:property value="productName"/></font>产品的库存   </th>
				</tr>
			</thead>
			<tr>
				<td width="100">当前库存</td>
				<td><font color="red"><s:property value="count"/></font></td>
			</tr>
			<tr>
				<td>上传文件</td>
				<td><s:file id="uploadFile" style="font-size:13px;margin:0;padding:0;" name="file"/></td>
			</tr>
			<tr>
				<td>合作网站优惠券</td>
				<td><div style="overflow:auto;">
					<table>
						<tr>
							<td>
								<s:textarea name="couponString" cssStyle="width: 400px; height: 300px;"/>
							</td>
							<s:if test="null!=errorList && errorList.size() > 0">
							<td><div>
								<table>
									<colgroup>
										<col width="50"/>
										<col width="400"/>
										<col width="150"/>
									</colgroup>
									<thead>
										<tr>
											<th>行号</th>
											<th>信息</th>
											<th>出错原因</th>
										</tr>
									</thead>
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
								</div>
							</td>
							</s:if>
						</tr>
					</table>
				</div></td>
			</tr>
			<tr>
				<td>清空原库存</td>
				<td><s:checkbox name="cleanOldData"/></td>
			</tr>
			<tr>
				<td></td>
				<td><s:submit value="上传更新"/><s:if test="null!=messageText"><s:property value="messageText"/></s:if></td>
			</tr>
		</table>
	</s:form>
  </body>
</html>
