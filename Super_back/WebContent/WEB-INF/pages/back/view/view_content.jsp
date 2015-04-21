<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>super后台——产品详情</title>
<link href="<%=basePath%>style/houtai.css" rel="stylesheet" type="text/css" />
<s:include value="/WEB-INF/pages/back/base/jquery.jsp"/>
<script type="text/javascript" src="<%=basePath%>kindeditor-3.5.1/kindeditor-min.js"></script>
<script type="text/javascript">
function goAction(){
		window.location.href='prod/getDescribe.do';
}
	var editor;
    KindEditor.ready(function(K) {
    	editor = K.create('#editor_id');
    });
</script>
</head>
 
<body>

<div class="main main12">

	<div class="row1">
    	<h3>产品列表<a href="#">添加新产品</a></h3>
         <jsp:include page="/WEB-INF/pages/back/prod/product_menu.jsp"></jsp:include>
   </div><!--row1 end-->   
   <form action="prod/insertDescribe.do" method="post"> 
   <div class="row2">

   		<dl>
        	<dt><b>产品经理推荐</b></dt>
            <dd><textarea name="managerRecommend" cols="text2" rows="text2" class="text2"><s:property value='viewPage.contents.get("MANAGERRECOMMEND").contentRn'/> </textarea></dd>
            
        	<dt><b>公告</b><span>(建议文字简略，不超过3条)</span></dt>
            <dd><textarea name="notice" cols="text2" rows="text2" class="text2">${  requestScope.listContent[1].content }</textarea></dd>
        	<dt><b>费用说明</b></dt>
            <dd>
            	<p>费用包含</p>
                <p><textarea id="cost" name="includeCost" cols="text2" rows="text2" class="text2">${  requestScope.listContent[2].content }</textarea></p>
            	<p>费用不包含</p>
                <p><textarea name="noincludeCost" cols="text2" rows="text2" class="text2">${  requestScope.listContent[3].content }</textarea></p>
            	<p>购物说明</p>
                <p><textarea name="explanation" cols="text2" rows="text2" class="text2">${  requestScope.listContent[4].content }</textarea></p>
            </dd>
        	<dt><b>签证/签注</b><span>(用于出境游的产品)</span><em>保持现状</em></dt>
            <dd><textarea name="visa" cols="text2" rows="text2" class="text2">${  requestScope.listContent[5].content }</textarea></dd>
          
      </dl> 
   </div><!--row2 end-->
   <div class="row3">
        <p><input type="submit" value="保存0" id="save"/></p>      
   </div><!--row3 end-->
   </form>
</div><!--main01 main05 end-->
</body>
</html>

