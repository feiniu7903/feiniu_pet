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
	<body>
		<input type="hidden" name="stage" id="stage" value="<s:property value='stage'/>"/>
		名称：<input type="text" id="placeName" value=""/>
	   <p class="tc mt10"><input type="button" id="btn_open_ok" class="btn btn-small w3" value="确 定" /></p>
	</body>
	<script type="text/javascript">
		$(function(){
			$("#btn_open_ok").click(function(){
				if($.trim($("#placeName").val()) == ""){
					alert("请输入名称!");
					$("#placeName").focus();
					return;
				}
				$.ajax({
					type:"post",
					url:"isExistCheck.do",
					data:"place.name="+$.trim($("#placeName").val())+"&stage="+$("#stage").val(),
					error:function(){
						alert("与服务器交互错误!请稍候再试!");
					},
					success:function(data){
						if(data=="Y"){
							alert("名称已存在！请更换名称再试!");
							return;
						}
						if(data!="error"){
							alert("添加成功!");
							if($("#stage").val()=="1"){
								window.location.href="placeList.do?place.placeId="+data;
							}
							if($("#stage").val()=="2"){
								window.location.href="scenicList.do?place.placeId="+data;
							}
							if($("#stage").val()=="3"){
								window.location.href="hotelList.do?place.placeId="+data;
							}  		
						} else {
							alert("添加失败，请稍后再试!");
						}
					}
				});
			});
		});
	</script>
</html>