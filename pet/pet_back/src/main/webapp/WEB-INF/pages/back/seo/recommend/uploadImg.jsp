<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="l" uri="/tld/lvmama-tags.tld"%>
<%
 String basePath = request.getContextPath();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>上传图片</title>
<link rel="stylesheet" href="<%=basePath %>/css/place/backstage_table.css"/>
<link rel="stylesheet" href="<%=basePath %>/css/place/panel.css"/>
<s:include value="/WEB-INF/pages/pub/jquery.jsp"/>
 <script type="text/javascript">
function  loadImg(){
    $('#ri<s:property value="recommendInfo.recommendInfoId"/>', window.parent.document).find("#imgUrl").val('http://pic.lvmama.com<s:property value="recommendInfo.imgUrl"/>');
    $('#uploadImgClick', window.parent.document).trigger("click");
}
</script>
</head>
  
<body style="background:#fff">
<s:if test='recommendInfo.imgUrl!=null&&recommendInfo.imgUrl!=""'>
<script type="text/javascript">
setTimeout("loadImg()", 2000);
</script>
  <b>上传成功：</b>http://pic.lvmama.com<s:property value="recommendInfo.imgUrl"/>
  <br/>
</s:if>
<br/>
请控制上传的图片大小不要超过1MB
<br/>
<form action="<%=basePath %>/seo/recommendInfo!uploadImg.do?sonBlock.recommendBlockId=<s:property value="sonBlock.recommendBlockId"/>&recommendInfo.recommendInfoId=<s:property value="recommendInfo.recommendInfoId"/>" method="post" enctype="multipart/form-data">
       <input type="file" id="imgUrl" name="imgUrl"/>
       <input type="submit" value="开始上传"/>
</form>

</html>
