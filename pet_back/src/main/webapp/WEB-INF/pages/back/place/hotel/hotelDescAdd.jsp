<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="mis" uri="/tld/lvmama-tags.tld"%>
<s:set var="basePath"><%=request.getContextPath()%></s:set>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>简介添加</title>
<s:include value="/WEB-INF/pages/pub/new_jquery.jsp"/>
 <script type="text/javascript">
var editor;
$(function(){
	editor = KindEditor.create('#desc_id',{
    	resizeType : 1,
    	width:'800px',
    	filterMode : true,
    	uploadJson:'/pet_back/upload/uploadImg.do'
    });
});
function checkDescInfoForm(){
	if(editor.html()==""){
		alert("介绍信息不能为空");
		return false;
	}
	$("#description").val(editor.html());
	
	var checkDescInfoFormOptions = { 
			url:"${basePath}/place/doPlaceUpdate.do",
			dataType:"",
			type : "POST", 
			success:function(data){ 
				if(data== "success") {
					alert("操作成功!"); 
					$("#popDiv").dialog("close");
				} else { 
					alert("操作失败，请稍后再试!"); 
				} 
			}, 
			error:function(){ 
				alert("出现错误"); 
			} 
		};
	$('#checkDescInfoForm').ajaxSubmit(checkDescInfoFormOptions);
};
</script>
</head>
<body>
<!--搜索-->
	<form action="${basePath}/place/doPlaceUpdate.do" id="checkDescInfoForm" method="post" >
	<input name="place.placeId" type="hidden" value="${place.placeId}"/>
	<input name="place.stage" type="hidden" value="${place.stage}"/>
	<input name="oldPlaceName" value="${place.name}"  type="hidden"/>
	<input name="place.name" value="${place.name}" type="hidden"/>
	<input id="description" name="place.description" type="hidden" value=""/>
	<input name="stage" type="hidden" value="${place.stage}"/>
	<input name="placeId" type="hidden" value="${place.placeId}"/>
	<table class="p_table">
     <tr>
        <td class="p_label"  width="15%" >描述信息：</td>
        <td>
        	<textarea id="desc_id" cols="70"  style="width: 90%;height: 450px;"><s:property value="place.description"/></textarea>
        </td>
      </tr>
    </table>
	<p class="tc mt10 mb10">
	    <input class="btn btn-small w5" value="确定" onclick="javascript:checkDescInfoForm();"></input>
	</p>    
  
    </form>
</body>
</html>