<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>~~立式设备~~</title>
	<script type="text/javascript" src="jquery.mobile/jquery-1.8.2.js"></script>
    <script type="text/javascript">
	    function addDevice() {
	    	if ($.trim($("#deviceCode").val()) == "") {
				alert("请输入设备码");
				return false;
			}
	    	if ($.trim($("#deviceName").val()) == "") {
				alert("请输入景区名称");
				return false;
			}
			$("#add").submit();
		}
	    
	    function updateDevice() {
	    	if ($.trim($("#deviceId").val()) == "") {
				alert("请输入设备id");
				return false;
			}
			if ($.trim($("#deviceCode1").val()) == "") {
				alert("请输入设备码");
				return false;
			}
			if ($.trim($("#deviceName1").val()) == "") {
				alert("请输入景区名称");
				return false;
			}
			$("#update").submit();
		}
    </script></head>
<body>
	<table>
		<tr>
			<td>
				<form id="add" action="/clutter/ck/addDevice.do" method="post">
					设备码：<input id="deviceCode" name="deviceCode" type="text">
					景区名称：<input id="deviceName" name="deviceName" type="text">
					备注<input name="memo" type="text"> <input type="button" onclick="addDevice()" value="新增">
				</form>
			</td>

		</tr>
		<tr>
			<td>
				<form id="update" action="/clutter/ck/updateDevice.do" method="post">
					设备号：<input id="deviceId" name="ckId" type="text">
					设备码：<input id="deviceCode1" name="deviceCode" type="text">
					景区名称：<input id="deviceName1" name="deviceName" type="text">
					备注<input name="memo" type="text">
					<input type="button" onclick="updateDevice()" value="修改">
				</form>
			</td>
		</tr>
	</table>
</body>
</html>