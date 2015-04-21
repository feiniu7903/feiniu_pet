<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8"/>
<title>proxy</title>
</head>
<body>
<script>
(function() {
	var getRequest = function(name) {
			var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)","i"),
				r = window.location.search.substr(1).match(reg);
			return (r!=null)?  unescape(r[2]) : null;
		},
		height = getRequest("data-frameheight");
	try {
		var el = window.top.document.getElementById(getRequest("data-frameid"));
		if (!el) return;
		el.style.height = height + 'px';
	}
	catch (e) {
	}
})();
</script>
</body>
</html>