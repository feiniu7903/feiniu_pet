<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="mis" uri="/tld/lvmama-tags.tld"%>
<s:set var="basePath"><%=request.getContextPath() + "/"%></s:set>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>入库功能</title>
<s:include value="/WEB-INF/pages/pub/lvmamacard.jsp" />

</head>
<body>
 
    <div id="popDiv" style="display: none"></div>
    <div class="iframe-content">
            <div class="p_box">
                       
                  <span>出错了！！！</span>
            </form>
        </div>
    </div>
</body>
</html>