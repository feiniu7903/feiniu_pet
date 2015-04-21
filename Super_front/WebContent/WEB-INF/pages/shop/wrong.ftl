<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
	<html xmlns="http://www.w3.org/1999/xhtml">
		<head>
			<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
			<title>重复提交</title>
		</head>
		<body>
			<form name=loading> 
　				<p align=center> 
					<font color="#0066ff" size="2">请勿重复提交！系统正在返回页面，请稍等</font><font color="#0066ff" size="2" face="Arial">...</font>
　　				<input type=text name=chart size=46 style="font-family:Arial; font-weight:bolder; color:#0066ff; background-color:#fef4d9; padding:0px; border-style:none;"> 
　　				<input type=text name=percent size=47 style="color:#0066ff; text-align:center; border-width:medium; border-style:none;"> 
　　				<script>　 
						var bar=0　 
						var line="||"　 
						var amount="||"　 
						count()　 
						function count(){　 
						bar=bar+2　 
						amount =amount + line　 
						document.loading.chart.value=amount　 
						document.loading.percent.value=bar+"%"　 
						if (bar<99)　 {
							setTimeout("count()",100);}　 
						else {
							window.location = "http://www.lvmama.com/points/prod/${productId}";
						}　 
					}
				</script> 
　			</p> 
		</form> 
		<p align="center"> 如果您的浏览器不支持跳转,<a style="text-decoration: none" href="http://www.lvmama.com/points/prod/${productId}"><font color="#FF0000">请点这里</font></a>.</p>
	</body>
</html>


