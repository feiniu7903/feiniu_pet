<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<s:set var="basePath"><%=request.getContextPath()%></s:set>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>选择产品的淘宝类目</title>
</head>
<body>
	<form id="push_form" method="post"
		action="${basePath}/taobaoProd/push.do">
		<table class="cg_xx" width="100%" border="0" cellspacing="0"
			cellpadding="0">
			<input id="productId" name="productId" type="hidden" value="${prodProduct.productId}" />
			<tr>
				<td>销售产品名称:    ${prodProduct.productName}</td>
			</tr>
			<tr>
				<td>上线时间:    ${prodProduct.onlineTimeStr}</td>
			</tr>
			<tr>
				<td>下线时间:    ${prodProduct.offlineTimeStr}</td>
			</tr>
			<tr>
				<td>状态:    ${prodProduct.strOnLine}</td>
			</tr>
			<tr>
			<td>
			<s:select name="first" id="first" size="10" style="width:150px"
				list="cateList1" listKey="cid" listValue="name" multiple="multiple"></s:select>
			<s:select name="second" id="second" size="10" style="width:150px"
				list="cateList2" listKey="cid" listValue="name" multiple="multiple"></s:select>
			<s:select name="third" id="third" size="10" style="width:150px"
				list="cateList3" listKey="cid" listValue="name" multiple="multiple"></s:select>
			<s:select name="fourth" id="fourth" size="10" style="width:150px"
				list="cateList4" listKey="cid" listValue="name" multiple="multiple"></s:select>
			<s:select name="fifth" id="fifth" size="10" style="width:150px"
				list="cateList5" listKey="cid" listValue="name" multiple="multiple"></s:select>
			</td>
			</tr>
			
		</table>
		<center>
			<input id="saveBtn" type="button" value="  保存并上传淘宝    " class="button" />
		</center>
	</form>
</body>
<script type="text/javascript" >
		$(function(){
			$("#saveBtn").click(function(){
				var first = $("#fourth").val();
				var second = $("#second").val();
				var third = $("#third").val();
				var fourth = $("#fourth").val();
				var fifth = $("#fifth").val();
				if (first == null || second == null || third == null || fourth == null) {
					alert("请选择目录");
					return;
				}
				if ($("#fifth").html() != "" && fifth == null) {
					alert("请选择景区");
					return;
				}
				var cateName = $("#first").find("option:selected").text() + "->" +
				$("#second").find("option:selected").text() + "->" +
				$("#third").find("option:selected").text();
				if ($("#third").find("option:selected").text() != $("#fourth").find("option:selected").text()) {
					cateName += "->" + $("#fourth").find("option:selected").text();
				}
				if (fifth != null) {
					cateName += "->" + $("#fifth").find("option:selected").text();
				}
				$.getJSON("${basePath}/taobaoProd/push.do",{cid:$("#fourth").val(),productId:$("#productId").val(),pidvid:$("#fifth").val(),cateName:encodeURI(cateName)},function(myJSON){
		        	if (myJSON.flag == "success") {
		        		alert("上传淘宝成功!");
		        		$("#query_form").submit();
		        	} else {
		        		alert(myJSON.msg);
		        	}
		        });
			});
			
			$("#first").change(function(){  
			        $.getJSON("${basePath}/taobaoProd/selectCate.do",{cid:$(this).val()},function(myJSON){
			        	var myOptions="";  
			            for(var i=0;i<myJSON.length;i++){  
			                myOptions+='<option value=' + myJSON[i].cid + '>'+myJSON[i].name+'</option>';  
			                
			            }
			            $("#second").html(myOptions);
			            $("#third").html("");
			            $("#fourth").html("");
			            $("#fifth").html("");
			        });
		    });  
			$("#second").change(function(){  
		        $.getJSON("${basePath}/taobaoProd/selectCate.do",{cid:$(this).val()},function(myJSON){
		        	var myOptions="";  
		            for(var i=0;i<myJSON.length;i++){  
		                myOptions+='<option value=' + myJSON[i].cid + '>'+myJSON[i].name+'</option>';  
		                
		            }
		            $("#third").html(myOptions);
		            $("#fourth").html("");
		            $("#fifth").html("");
		        });
			});  
		   $("#third").change(function(){  
			        $.getJSON("${basePath}/taobaoProd/selectCate.do",{cid:$(this).val()},function(myJSON){
			        	var myOptions="";  
			            for(var i=0;i<myJSON.length;i++){  
			                myOptions+='<option value=' + myJSON[i].cid + '>'+myJSON[i].name+'</option>';  
			                
			            }
			            $("#fourth").html(myOptions);
			            $("#fifth").html("");
			        });
		   });  
		   $("#fourth").change(function(){  
		        $.getJSON("${basePath}/taobaoProd/selectRegion.do",{cid:$(this).val()},function(myJSON){
		        	var myOptions="";  
		            for(var i=0;i<myJSON.length;i++){  
		                myOptions+='<option value=' + myJSON[i].pid + ':' + myJSON[i].vid + '>'+myJSON[i].name+'</option>';  
		                
		            }
		            $("#fifth").html(myOptions);
		        });
	   });  
		   
	    
		})
		</script>
<div id="fff"></div>
</html>