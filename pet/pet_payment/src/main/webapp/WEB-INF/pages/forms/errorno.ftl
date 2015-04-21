<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>error</title>
<script type="text/javascript"> 
	if("${bizType!}"=="MERGE_PAY"){
		window.location.href="http://www.lvmama.com/view/viewMergePay.do?orderIds=${objectIds!}";
	}
	else{
		window.location.href="http://www.lvmama.com/view/view.do?orderId=${objectId!}";
	}
</script>
</head>
</html>
