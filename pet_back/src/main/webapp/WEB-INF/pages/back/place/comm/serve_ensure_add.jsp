<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="mis" uri="/tld/lvmama-tags.tld"%>
<s:set var="basePath"><%=request.getContextPath()%></s:set>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>服务保障</title>
<script type="text/javascript">
function checkServiceEnSureForm(){
		var ruyuanbaozhang;
		if($("input[name=place.ruyuanbaozhang]:checked").attr("checked")){
			ruyuanbaozhang="ruyuanbaozhang";
		}
	   var kuaisuruyuan;
	    if($("input[name=place.kuaisuruyuan]:checked").attr("checked")){
	    	kuaisuruyuan="kuaisuruyuan";
		}
	   var suishitui;
	    if($("input[name=place.suishitui]:checked").attr("checked")){
	    	suishitui="suishitui";
		}
	   var guijiupei;
	    if($("input[name=place.guijiupei]:checked").attr("checked")){
	    	guijiupei="guijiupei";
		}
	
	$("#ruyuanbaozhang").val(ruyuanbaozhang);
	$("#kuaisuruyuan").val(kuaisuruyuan);
	$("#suishitui").val(suishitui);
	$("#guijiupei").val(guijiupei);
	
	var checkDescInfoFormOptions = { 
			url:"${basePath}/place/doPlaceUpdate.do",
			dataType:"",
			type : "POST", 
			success:function(data){ 
				if(data== "success") {
					alert("操作成功!"); 
					popClose();
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
	<form action="${basePath}/place/doPlaceUpdate.do" id="checkDescInfoForm" method="post" >
		<input name="place.placeId" type="hidden" value="${place.placeId}"/>
		<input name="place.stage" type="hidden" value="${place.stage}"/>
		<input name="oldPlaceName" value="${place.name}"  type="hidden"/>
		<input name="place.name" value="${place.name}" type="hidden"/>
		<input name="place.trafficInfo" type="hidden" value="${trafficInfo}"/>
		<input name="stage" type="hidden" value="${place.stage}"/>
		<input name="placeId" type="hidden" value="${place.placeId}"/>
		<!-- 服务保障 -->
		<input id="ruyuanbaozhang" name="place.ruYuanBaoZhang" type="hidden" />
		<input id="kuaisuruyuan"name="place.kuaiSuRuYuan" type="hidden"/>
		<input id="suishitui"name="place.suiShiTui" type="hidden"/>
		<input id="guijiupei"name="place.guiJiuPei" type="hidden"/>
		<table class="p_table">
		 <tr>
			<td class="p_label"  width="15%" >保障内容：</td>
			<td>
				<input type="checkbox" name="place.ruyuanbaozhang" <s:if test="%{place.ruYuanBaoZhang!=null}"> checked="checked"</s:if>/>入园保证
				<input type="checkbox" name="place.kuaisuruyuan" <s:if test="%{place.kuaiSuRuYuan!=null}"> checked="checked"</s:if>/>快速入园
				<input type="checkbox" name="place.suishitui" <s:if test="%{place.suiShiTui!=null}"> checked="checked"</s:if> />随时退
				<input type="checkbox" name="place.guijiupei" <s:if test="%{place.guiJiuPei!=null}"> checked="checked"</s:if> />贵就陪
			</td>
		  </tr>
		</table>
	   <p class="tc mt10">
	   <input type="button" id="btn_ok" class="btn btn-small w3" onclick="javascript:checkServiceEnSureForm();" value="提交" />
		</p>
    </form>
</body>
</html>