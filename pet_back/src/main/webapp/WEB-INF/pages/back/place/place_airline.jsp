<%@ page language="java" import="com.lvmama.comm.vo.Constant" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head><title>航空公司详细信息</title>
<script type="text/javascript" src="<%=basePath%>/js/base/ajaxupload.js"></script>
<script type="text/javascript">
		 $(function(){
			 $("#airlineFrom").validateAndSubmit(function($form,dt){
        	 			var data=eval("("+dt+")");
						if (data.success) {
							alert('操作成功！');
							document.location.reload();
						}else{
							alert(data.msg);
						}
			});
			$("#uploadFile").imageUpload({onComplete:function(file,dt){
					var data=eval("("+dt+")");
					if(data.success){
						$("#airlineIcon").val(data.fullName);
						$("#airlineIconImg").attr("src","<%=Constant.getInstance().getPrefixPic()%>"+data.fullName).show();
						alert("上传成功");
					}else{
						alert(data.msg);
					}
				}});	
			$(document).ready(function(){
				var icon=$("#airlineIcon").val();
				if($.trim(icon)!=''){
					$("#airlineIconImg").attr("src","<%=Constant.getInstance().getPrefixPic()%>"+icon).show();
				}
			});
		 });
	</script></head>
	<body>
	<form action="<%=basePath%>place/placeAirlineSave.do" id="airlineFrom" name="airlineFrom" enctype="multipart/form-data">
	    <input type="hidden" name="placeAirline.placeAirlineId" value="${placeAirline.placeAirlineId}"/>
			<table class="p_table no_bd form-inline" width="100%">
			<tr>
				<td class="label"><span class="required">*</span>代码:</td>
				<td><s:textfield name="placeAirline.airlineCode" id="airlineCode" cssClass="required"/></td>

			</tr>
			<tr>
				<td class="label"><span class="required">*</span>名称:</td>
				<td><s:textfield name="placeAirline.airlineName" id="airlineName" cssClass="required" /></td>
			</tr>
			<tr>
				<td class="label">图标:</td>
				<td><s:hidden id="airlineIcon" name="placeAirline.airlineIcon"/>
				<img style="display: none" id="airlineIconImg"/> &nbsp;&nbsp;
				<input type="button" class="btn btn-small w3" value="上传/替换" id="uploadFile" serverType="PLACE_AIRLINE"/><font color="red">文件上传后需要保存才可以生效</font>
				</td>
			</tr>
		</table>
	    <p class="tc mt10"><input type="submit"  class="btn btn-small w3" value="保存"/></p>
	</form>
	</body>
</html>