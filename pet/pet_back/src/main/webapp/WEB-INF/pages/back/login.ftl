<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>驴妈妈信息管理系统</title>
	<link rel="stylesheet" type="text/css" href="themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="themes/icon.css">
	<link rel="stylesheet" type="text/css" href="themes/main.css">
	<script type="text/javascript" src="js/base/jquery-1.4.4.min.js"></script>
	<script type="text/javascript" src="js/base/jquery.easyui.min.js"></script>
	<script type="text/javascript">
		if(parent.loginFuc) {
			parent.loginFuc();
		}
	</script>
</head>
<body>
<div id="w" class="easyui-window" title="欢迎使用驴妈妈信息管理系统" closable="false" collapsible="false" minimizable="false" maximizable="false" style="width:355px;height:206px; background: #fafafa;">
    <form id="ff" method="post">
        <div style="color:red;" class="message"><label>${message?if_exists}</label></div>
        <div style="margin:20px;">
            <span for="username" class="login">用户名：</span>
            <input id="username" class="easyui-validatebox" type="text" name="user.userName" required="true"></input>
        </div>
        <div style="margin:20px;">
            <span for="passport" class="login">密　码：</span>
            <input id="password" class="easyui-validatebox" type="password" name="user.password" required="true"></input>
        </div>
        <div style="margin:20px;">
            <span class="login"> </span>
            <input type="submit" value="登录" />
            <input type="reset" value="取消" />
        </div>
    </form>
</div>
	
</body>
</html>