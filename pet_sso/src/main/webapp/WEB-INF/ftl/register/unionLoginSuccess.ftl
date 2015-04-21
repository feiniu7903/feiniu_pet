<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>联合登录成功</title>
	<script type="text/javascript">
		document.domain="lvmama.com";
		var is_refresh=true;
		var arrStr = document.cookie.split("; ");
		for (var i = 0; i < arrStr.length; i++) {
			var temp = arrStr[i].split("=");
			if (temp[0] == "isRefresh") {
				is_refresh = temp[1];
			}
		}
		if(is_refresh=="true" || is_refresh==true){
		        if ("http://login.lvmama.com/nsso/" == window.opener.location 
				|| "login.lvmama.com/nsso/" == window.opener.location ) {
				window.opener.document.location.href = "http://www.lvmama.com";
			} else {
				window.opener.document.location.href = window.opener.document.location.href;
			}
			window.opener = null;
		}
		window.close();
	</script>
</head>
</html>
