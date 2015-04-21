<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>后台写点评</title> 
 
<script type="text/javascript"> 
var scores = "";
var scores0 = "";
var scores1 = "";
var scores2 = "";
var scores3 = "";
var scores4 = "";

function saveComment()
{	
    if ($('#content').val()==null || $('#content').val()=='') {
    	alert("点评内容不能为空");
    	return;
   } 
   if(scores0 == "" || scores1 == "" || scores2 == "" || scores3 == "" || scores4 == ""){
   		alert("必须填完所有维度");
   		return;
   }
   if($("#content").val()=="" || $("#content").val().length < 20 || $("#content").val().length > 500||$("#content").val()=="可以输入500个汉字...") {
		alert('回复内容长度应该在20-500个字符。');
		$("#content").focus();;
		return ;
	}
		
   	scores = scores0 + "," + scores1 + "," + scores2 + "," + scores3 + "," + scores4;
   	$('#scores').val(scores);
    $("#saveBackCommentFrom").submit();
    
    $("#addCommentDiv").dialog("close");	
    
}

function fixScores(obj){
	if(obj.name == "ct_Star0"){
		scores0 = obj.value;
	}else if(obj.name == "ct_Star1"){
		scores1 = obj.value;
	}else if(obj.name == "ct_Star2"){
		scores2 = obj.value;
	}else if(obj.name == "ct_Star3"){
		scores3 = obj.value;
	}else if(obj.name == "ct_Star4"){
		scores4 = obj.value;
	}
}
$(function(){
    $('#endDate').datepicker({dateFormat: 'yy-mm-dd'});
});

    </script>
</head>
<body>
	<div class="main main02">
		<div class="row1">
			<h3 class="newTit">后台写点评</h3>
			<form id="saveBackCommentFrom" action="${basePath}commentManager/saveBackComment.do" method="post">
				<input id="productId"  name="productId" value="${productId}" type="hidden"/>
				<input name="scores" id="scores" value="" type="hidden">
			
				<table border="1" cellspacing="0" cellpadding="0" class="search_table" width="100%">
					<tr>
						<td align="right">产品ID：</td> <td> ${productId} </td>
					</tr>
					<tr>
						<td align="right">产品名称：</td> <td> ${product.productName} </td>
					</tr>
					
					<tr>
					    <td align="right">点评时间：(这个时间前180天日期到这个时间内随机)</td> 
					    <td>
		 				    <input id="endDate" type="text" class="newtext1" name="endDate" value="${endDate}"/>
		 				</td>
					</tr>
					<tr>
						             <td align="right">总分：</td> 
						             <td>  
							            <input name="latitudeIds" value="FFFFFFFFFFFFFFFFFFFFFFFFFFFF" type="hidden">
							            <input name="latitudeNames" value="总体点评" type="hidden">
							            <input type="radio" name="ct_Star0"  onclick="fixScores(this)" value="5">5分
							            <input type="radio" name="ct_Star0"  onclick="fixScores(this)" value="4">4分
							            <input type="radio" name="ct_Star0"  onclick="fixScores(this)" value="3">3分
							            <input type="radio" name="ct_Star0"  onclick="fixScores(this)" value="2">2分
							            <input type="radio" name="ct_Star0"  onclick="fixScores(this)" value="1">1分
						             </td>
					 </tr>
					           <s:if test="commentLatitudeList != null">
					          	 <s:iterator value="commentLatitudeList" var="commentLatitude" status="c">
							            <tr>
							            	 <td align="right">${name}：</td> 
						             		 <td> 
									            <input name="latitudeIds" value="${latitudeId}" type="hidden">
									            <input type="radio" name="ct_Star<s:property value="#c.index+1"/>" onclick="fixScores(this)" value="5">5分
									            <input type="radio" name="ct_Star<s:property value="#c.index+1"/>" onclick="fixScores(this)" value="4">4分
									            <input type="radio" name="ct_Star<s:property value="#c.index+1"/>" onclick="fixScores(this)" value="3">3分
									            <input type="radio" name="ct_Star<s:property value="#c.index+1"/>" onclick="fixScores(this)" value="2">2分
									            <input type="radio" name="ct_Star<s:property value="#c.index+1"/>" onclick="fixScores(this)" value="1">1分
									         </td>
							            </tr>
									</s:iterator>
								</s:if>
					<tr>
						<td align="right">点评内容：</td>
						<td colspan="3">
							<textarea id="content" name="content" cols="80" rows="10" value="${content}"></textarea>
						</td>
					</tr>
					<tr>
					    <td colspan="4">&nbsp;&nbsp;&nbsp;&nbsp;
					    <input type="button" class="button" value="保存" onClick="saveComment()"/>&nbsp;&nbsp;&nbsp;&nbsp;
					    <input type="button" class="button" value="清空" onClick="clear()"/>&nbsp;&nbsp;&nbsp;&nbsp;</td>
					</tr>
				</table>
			</form>
		</div>
	</div>
</body>
</html>


