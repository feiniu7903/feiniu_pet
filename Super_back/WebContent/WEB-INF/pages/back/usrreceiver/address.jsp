<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<script type="text/javascript">
		$(document).ready(function() {
			if($("input[name='addressId']").length > 0) {
				var addressId = $("input[name=addressId]:checked").val();
				if(addressId == undefined) {
					$("input[name='addressId']").get(0).checked=true;
				}
			}
		});
		</script>
	</head>

	<body>
		<strong>配送</strong>
		<p>
			已有地址：
		</p>
		<br />
		<table style="font-size: 12px" cellspacing="1" cellpadding="4"
			border="0" bgcolor="#B8C9D6" width="100%" class="newfont03">
			<tbody>
				<tr bgcolor="#f4f4f4" align="center">
					<s:if test="hidePhysical!='true'">
					<td height="30">
						实体票
					</td>
					</s:if>
					<td>
						发票
					</td>
					<td>地址</td>
					<td>
						接收人
					</td>
					<td>
						电话
					</td>
					<td>
						邮编
					</td><s:if test="hideButton!='true'">
					<td>
						操作
					</td></s:if>
				</tr>
				<s:iterator value="usrReceiversList" id="usrReceiversVar">
					<tr bgcolor="#ffffff" align="center">
						<s:if test="hidePhysical!='true'">
						<td height="25">
							<s:if test="selectedReceiverId == #usrReceiversVar.receiverId">
								<input type="radio" name="addressId" checked="checked"
									value="${usrReceiversVar.receiverId }" />
							</s:if>
							<s:else>
								<input type="radio" name="addressId"
									value="${usrReceiversVar.receiverId }" />
							</s:else>
						</td>
						</s:if>
						<td>
						
						    <input type="radio" name="invoiceAddressId${index}" <s:if test="selectedReceiverId2 == #usrReceiversVar.receiverId"> checked="checked"</s:if> value="${usrReceiversVar.receiverId }"/>
						</td>
						<td>
							${usrReceiversVar.province} ${usrReceiversVar.city} ${usrReceiversVar.address }
						</td>
						<td>
							${usrReceiversVar.receiverName }
						</td>
						<td>
							${usrReceiversVar.mobileNumber }
						</td>
						<td>
							${usrReceiversVar.postCode }
						</td>
						<s:if test="hideButton!='true'"><td>
							<s:if test="selectedReceiverId == #usrReceiversVar.receiverId">
								<a
									href="javascript:showEditAddressDialg('${usrReceiversVar.receiverId }',true);">修改</a>
								<a href="javascript:alert('请选择其他配送地址并保存后删除!');">删除</a>
							</s:if>
							<s:else>
								<a
									href="javascript:showEditAddressDialg('${usrReceiversVar.receiverId }',false);">修改</a>
								<a
									href="javascript:deleteAddress('${usrReceiversVar.receiverId }');">删除</a>
							</s:else></td>
							</s:if>
						
					</tr>
				</s:iterator>
			</tbody>
		</table>
	</body>
</html>
