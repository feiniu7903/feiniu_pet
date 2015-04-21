<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="lv" uri="/WEB-INF/pages//tld/lvmama-tags.tld"%>
<html>
<head>
<jsp:include page="/WEB-INF/pages/common/head_meta.jsp"></jsp:include>


</head>
<body>
	<br />
	<br />
	<br />
	<br />
	<sf:form modelAttribute="tntUser" id="form">
		<sf:input path="userName" />
	</sf:form>
	<input type="button" value="OK" onclick="submit()" />
</body>
<jsp:include page="/WEB-INF/pages/common/foot.jsp"></jsp:include>
<script type="text/javascript">
var test = {
	rules : {
		name : {
			required : true,
			remote : {
				url : "test/remote",
				type : "get",
				dataType : 'json',
				data : {}
			},
		},

	},
	messages : {
		name : {
			required : "请输入理由1",
			remote : "请修正该字111段",
		},
	}
};

	$("#form").validate(test);
	var submit = function() {
		var form = $("#form");
		if (!form.validate().form()) {
			return;
		}
		form.submit();
	};
</script>
</html>
