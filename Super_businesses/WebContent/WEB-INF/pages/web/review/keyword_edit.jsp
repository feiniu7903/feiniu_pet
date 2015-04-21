<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<s:set var="basePath"><%=request.getContextPath()+"/"%></s:set>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>关键字</title>
<s:include value="/WEB-INF/pages/pub/reviewHeadJs.jsp" />
</head>
	<body>
        <form action="${basePath}keyword/doUpdatekeyWord.do" method="post" id="form1">
          <s:hidden name="keyId"></s:hidden>
            <table class="p_table table_center">
                <tr><td class="p_label">关键字ID:</td><td>${keyWord.kId }</td></tr>
                <tr><td class="p_label">创建时间:</td><td>${keyWord.kDate }</td></tr>
                <tr><td class="p_label">关键字为:</td><td><input id="k_content" name="kContent"  type="text" value="${keyWord.kContent}"/></td></tr>
                <tr><td colspan="2">
                <button class="btn btn-small w5" type="submit" onclick="return doUpdateKey()">提交</button>
                </td></tr>
            </table>
        </form>
	</body>
	<script type="text/javascript">
function doUpdateKey(){   
    if(""==$("#k_content").val()){
        alert("关键字不能为空");
        return false;
    }
    $("#form1").submit();
    return true;
}
</script>
</html>