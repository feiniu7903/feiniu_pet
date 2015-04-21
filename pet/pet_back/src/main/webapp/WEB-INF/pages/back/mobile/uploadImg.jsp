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
	var host = "http://pic.lvmama.com";
	var img_url = '<s:property value="mobileRecommendInfo.recommendImageUrl"/>';
	var h_img_url = host+img_url;
	$('#ri<s:property value="mobileRecommendInfo.id"/>', window.parent.document).find("#imgUrl").val(img_url);
    window.parent.$("#imgLi").show();
    window.parent.$("#imgUrl_iphone").attr('src',h_img_url);
    window.parent.$("#imgUrl_android3").attr('src',h_img_url);
    window.parent.$("#imgUrl_android2").attr('src',h_img_url);
    
   
    // 对应自由行 上传图片
    try{
    	 window.parent.$("#imgUrl").val(img_url);
    	 window.parent.$("#imgLiId").show();
    	 window.parent.$("#imgUrl").attr('src',h_img_url);
    	 window.parent.$("#imgUrl_dest").attr('src',h_img_url);
    	 window.parent.$("#recommendImageUrl").val(img_url);
    	 $('#ri<s:property value="mobileRecommendInfo.id"/>', window.parent.document).find("#imgUrl_dest").attr('src',h_img_url);
    }catch(e){
    	
    }
    $('#uploadImgClick', window.parent.document).trigger("click");
}
</script>
</head>
  
<body style="background:#fff">
	<s:if test='mobileRecommendInfo.recommendImageUrl!=null && mobileRecommendInfo.recommendImageUrl!=""'>
	<script type="text/javascript">
		setTimeout("loadImg()", 2000);
	</script>
	  <b>上传成功：</b>http://pic.lvmama.com<s:property value="mobileRecommendInfo.recommendImageUrl"/>
	  <br/>
	</s:if>
	
	<!-- uploadMsg -->
	  <b style="color:red">${uploadMsg}</b>
	  <br/>
<br/>
请控制上传的图片大小不要超过1MB
<br/>
<form action="<%=basePath %>/mobile/mobileRecommendInfo!uploadImg.do?sonBlock.id=<s:property value="sonBlock.id"/>&mobileRecommendInfo.id=<s:property value="mobileRecommendInfo.id"/>" method="post" enctype="multipart/form-data">
       <input type="file" id="imgUrl" name="imgFileUrl"/>
       <input type="submit" value="开始上传"/>
</form>
<br/> 
<span style="color:red">首页推荐：</span>720*270（最大尺寸裁剪不同设备尺寸） 
<span style="color:red">目的地：</span>82*82 
<span style="color:red">自由行：</span>92*92 </br>
<span style="color:red">新自由行地图：</span>576*220</br>
<span style="color:red">攻略首页：</span>（第一条记录）240*350 （第二条记录）240*170 （其余）170*170 
<br/>
</body>

</html>
