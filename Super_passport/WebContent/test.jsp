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
<title>永利国旅测试</title>
</head>
<body>
	<form action="<%=basePath%>/yongliguolv/transCheck.do" method="post">
		<table>
			<tr>
				<td><textarea rows="5" cols="40" name="Content"><?xml version="1.0" encoding=" GB2312"?><VerifySyncReq><SpSeq>20121105259898</SpSeq><TransType>2000</TransType><PGoodsId>230</PGoodsId><PGoodsName>远帆</PGoodsName><Amt>10</Amt><ResiduaryAmt>10</ResiduaryAmt><ResiduaryTimes>10</ResiduaryTimes><TransTime>20071231120505</TransTime><Status>3</Status><PhoneNo>13805008265</PhoneNo></VerifySyncReq></textarea></td>
			</tr>
			<tr>
				<td><input type="submit" value=" 提交测试 " /></td>
			</tr>
		</table>
	</form>
</body>
</html>