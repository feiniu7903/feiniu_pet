<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<script src="http://pic.lvmama.com/js/jquery142.js" type="text/javascript"></script>
	</head>
	<body>
		<@s.property value="contractContent" escape="false"/>
	</body>
	<script type="text/javascript">
			$.ajax({ 
				url: "/ord/isExistContract.do?orderId=${orderId}", 
				success: function(data){
					document.location.href = "/ord/viewContract.do?orderId=${orderId}";
				}
			});
	</script>
</html>


