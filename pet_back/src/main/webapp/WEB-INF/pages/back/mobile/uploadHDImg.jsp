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
function  loadHDImg(){
	var host = "http://pic.lvmama.com";
	var img_url = '<s:property value="mobileRecommendInfo.recommendHDImageUrl"/>';
	var h_img_url = host+img_url;
	$('#ri<s:property value="mobileRecommendInfo.id"/>', window.parent.document).find("#imgHDUrl").val(img_url);
    window.parent.$("#imgHDLi").show();
    window.parent.$("#imgHDUrl_ipad").attr('src',h_img_url);
    
 	// 对应自由行 上传图片
    try{
    	 window.parent.$("#imgHDUrl").val(img_url);
    	 window.parent.$("#imgHDLiId").show();
    	 window.parent.$("#imgHDUrl").attr('src',h_img_url);
    	 window.parent.$("#imgHDUrl_dest").attr('src',h_img_url);
    	 window.parent.$("#recommendHDImageUrl").val(img_url);
    	 $('#ri<s:property value="mobileRecommendInfo.id"/>', window.parent.document).find("#imgHDUrl_dest").attr('src',h_img_url);
    }catch(e){
    	
    }
    
    $('#uploadImgClick', window.parent.document).trigger("click");
}
</script>
</head>
  
<body style="background:#fff">
	<s:if test='mobileRecommendInfo.recommendHDImageUrl!=null && mobileRecommendInfo.recommendHDImageUrl!=""'>
	<script type="text/javascript">
		setTimeout("loadHDImg()", 2000);
	</script>
	  <b>上传成功：</b>http://pic.lvmama.com<s:property value="mobileRecommendInfo.recommendHDImageUrl"/>
	  <br/>
	</s:if>
	
	<!-- uploadMsg -->
	  <b style="color:red">${uploadMsg}</b>
	  <br/>
<br/>
请控制上传的图片大小不要超过1MB
<br/>
<form action="<%=basePath %>/mobile/mobileRecommendInfo!uploadHDImg.do?sonBlock.id=<s:property value="sonBlock.id"/>&mobileRecommendInfo.id=<s:property value="mobileRecommendInfo.id"/>" method="post" enctype="multipart/form-data">
       <input type="file" id="imgHDUrl" name="imgFileUrl"/>
       <input type="submit" value="开始上传"/>
</form>
<br/> 
<span style="color:red">HD首页推荐：</span>1640*512（最大尺寸裁剪不同设备尺寸） 
<span style="color:red">HD目的地：</span>180*180 
<span style="color:red">自由行：</span>92*92 </br>
<span style="color:red">攻略首页：</span>（第一条记录）240*350 （第二条记录）240*170 （其余）170*170 
<br/>
</body>

</html>
