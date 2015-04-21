<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<script type="text/javascript">
function closeMyDiv(obj){
$("select").each(function(){$(this).show();});
$("#addAddressDialg").hide();
	
}
function closeMyDiv2(){
$("select").each(function(){$(this).show();});
$("#editAddressDialg").hide();

}
	$(function(){
		$("select[name=usrReceivers.province]").change(function(){
			var val=$(this).val();
			var $selectCity=$("select[name=usrReceivers.city]")
			$selectCity.empty();
			if($.trim(val)!=''){
				$.post("/super_back/usrReceivers/citys.do",{"provinceId":val},function(dt){
					var data=eval("("+dt+")")
					for(var i=0;i<data.list.length;i++){
						var city=data.list[i];
						var $option=$("<option/>");
						$option.val(city.cityId).text(city.cityName);
						$selectCity.append($option);
					}
				});
			}else{
				var $option=$("<option/>");
				$option.val("").text("请选择");
				$selectcity.append($option);
			}
		});
	});
</script>
	</head>

	<body>
		<div class="view-window" style="display: ">
			<s:form theme="simple" id="insertUpdateAddressForm">
				<s:hidden name="usrReceivers.userId" />
				<s:hidden name="usrReceivers.receiverId" />
				<s:hidden name="personId" />
				<s:hidden name="orderId" />
				<table width="100%" border="0" class="contactlist">
					<tr>
						<td width="17%">
							地址：
						</td>
						<td width="34%"><select name="usrReceivers.province">
										<s:iterator value="provinceList" var="province">
										<option value="${province.provinceId}" <s:if test="usrReceivers.province==#province.provinceId">selected</s:if>>${province.provinceName}</option>
										</s:iterator>
									</select>
									<select name="usrReceivers.city">
										<s:if test="cityList!=null&&cityList.size()>0">
											<s:iterator value="cityList" var="city">
											<option value="${city.cityId}" <s:if test="#city.cityId==usrReceivers.city">selected</s:if>>${city.cityName}</option>
											</s:iterator>
										</s:if>
										<s:else>
										<option value="">请选择</option>										
										</s:else>
									</select>
							<input name="usrReceivers.address" type="text" id="address"
								value="${usrReceivers.address}" />
						</td>
						<td>
							联系人：
						</td>
						<td>
							<input name="usrReceivers.receiverName" type="text"
								id="receiverName" value="${usrReceivers.receiverName}" />
						</td>
					</tr>
					<tr>
						<td>
							联系电话：
						</td>
						<td>
							<input name="usrReceivers.mobileNumber" type="text"
								id="mobileNumber" value="${usrReceivers.mobileNumber}" />
						</td>
						<td>
							邮编：
						</td>
						<td>
							<input name="usrReceivers.postCode" type="text"
								value="${usrReceivers.postCode}" />
						</td>
					</tr>
				</table>
				<p>
					<input type="reset" name="btnResetAddress" value="清空" class="button">
					&nbsp;&nbsp;&nbsp;
					<s:if test="receiverId==null||receiverId==''">					   
						<input type="button" name="btnSaveAddress" value="保存" onclick="addAddress();"
							class="button">&nbsp;&nbsp;&nbsp;
							<input type="button" id="closebtn" onclick="closeMyDiv();"
							name="btnCloseAddress" value="关闭" class="button">
					</s:if>
					<s:else>
						<input type="button" name="btnSaveAddress" value="保存" onclick="editAddress();"
							class="button">&nbsp;&nbsp;&nbsp;
							<input type="button" id="closebtn" onclick="closeMyDiv2();"
							name="btnCloseAddress" value="关闭" class="button">
					</s:else>
				</p>
			</s:form>
		</div>
	</body>
</html>
