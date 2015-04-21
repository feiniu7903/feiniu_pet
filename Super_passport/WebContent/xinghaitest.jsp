<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>星海假期推送测试</title>
</head>
<body>
	<form action="<%=basePath%>/xinghaiholiday/sync.do" method="post">
		<table>
			<tr>
				<td>Action：&nbsp;&nbsp;&nbsp;&nbsp;<input type="text" name="Action" value="HotelRoomState" /></td>
			</tr>
			<tr>
				<td>Content: &nbsp;&nbsp;&nbsp;<textarea rows="5" cols="40" name="Content">{"hotelid": "1","RoomTypeList": [{"roomid": "11","RoomStates": [{"date": "2013-04-02","roomstate": "5+"},{"date": "2013-04-03","roomstate": "5+"}]}],"RoomTypeCount": "1"}</textarea></td>
			</tr>
			<tr>
				<td><input type="submit" value=" 提交测试 " /></td>
			</tr>
		</table>
		<div>
		房态推送数据参考
		<table>
		<tr><td>Action:HotelRoomState</td></tr>
		<tr>
		<td>Content:
		{"hotelid": "1","RoomTypeList": [{"roomid": "11","RoomStates": [{"date": "2013-04-02","roomstate": "5+"},{"date": "2013-04-03","roomstate": "5+"}]}],"RoomTypeCount": "1"}</td>
		</tr>
		</table>
		</div>
		<div>
		房价推送数据参考
		<table>
		<tr><td>Action:HotelPrice</td></tr>
		<tr>
		<td>Content:
		{"hotelid": "1","RoomTypeList": [{"roomid": "11","RoomPrice": [{"date": "2013-04-02","pfprice": "100"}, {"date": "2013-04-03","pfprice": "100"}]}],"RoomTypeCount": "1"}</td>
		</tr>
		</table>
		</div>
		<div>
		订单状态推送数据参考
		<table>
		<tr><td>Action:BookOrder</td></tr>
		<tr>
		<td>Content:
		{"OrderList":[{"parentorderid": "123","xhorderid": "100","orderstate":"2"}, {"parentorderid": "234","xhorderid": "101","orderstate":"1"},{"parentorderid": "345","xhorderid": "102","orderstate":"5"}]}</td>
		</tr>
		</table>
		</div>
	</form>
</body>
</html>