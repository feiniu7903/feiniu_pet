<%@ page contentType="text/html; charset=UTF-8"%>
<html>
<body>
	<form action="<%=request.getContextPath()%>/train/imp_submit.do"
		method="post">
		<table>
			<tr>
				<td>操作</td>
				<td><select name="type">
						<option value="1">车站</option>
						<option value="2">城市</option>
						<option value="3">车次</option>
						<option value="4">经停</option>
						<option value="5">经停</option>
				</select></td>
				<td><input type="text" name="date"/>格式为yyyyMMdd,经停需要指定日期</td>
			</tr>
			<tr>
				<td>
					内容
				</td>
				<td><textarea rows="10" cols="100" name="content"></textarea></td>
			</tr>
		</table>
		<input type="submit" value="保存"/>
	</form>
</body>
</html>