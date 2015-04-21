<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="l" uri="/tld/lvmama-tags.tld"%>
<%
 String basePath = request.getContextPath();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>新增推荐的信息mobile_recommend_inf_source.jsp</title>
<link rel="stylesheet" href="<%=basePath %>/css/place/backstage_table.css"/>
<link rel="stylesheet" href="<%=basePath %>/css/place/panel.css"/>
<s:include value="/WEB-INF/pages/pub/jquery.jsp"/>
<script type="text/javascript">
	var basePath="<%=basePath %>";
</script>
<script type="text/javascript" src="<%=basePath %>/js/place/houtai.js"></script>
<script type="text/javascript" src="<%=basePath %>/js/seo/panel_custom.js"></script>
<script type="text/javascript" src="<%=basePath %>/js/mobile/mobile_recommend.js?version=20130923"></script>
<style type="text/css">
	.ul_detail li {
	    display: inline;
	    float: left;
	    margin-bottom: 5px;
	    text-align: left;
	    width: 550px;
	}
	.js_zs {
		left:54%;
	}
</style>
</head>
  
<body style="background:#fff">
	<h5>添加首页推荐 </h5>
	   <div class="detail_box">
	   <form class="form_hor" onsubmit="saveRecommendInfoForm('ri','','index');return false;" id="ri">
			<input type="hidden" id="sonBlockId" value="<s:property value="mobileRecommendBlock.id"/>"/>
			<input type="hidden" id="pageChannel" value="<s:property value="pageChannel"/>"/>
	         <input type="hidden" class="p_long" id="isValid" value="N"/>
             <ul class="ul_detail vip_info clearfix">
             	<li>
                     <label><span class="label_text">标题：</span><input type="text" class="p_long" id="recommendTitle" value=""/></label>
                		（2-16个汉字）
                 </li>
                 <li>
                     <label><span class="label_text">链接地址：</span><input type="text" class="p_long" id="url" value=""/></label>
                 </li>
                 <li>
                     <label><span class="label_text">HD链接地址：</span><input type="text" class="p_long" id="hdUrl" value=""/></label>
                 </li>
                 <li>
                     <label><span class="label_text">排序值：</span><input type="text" id="seq" value=""/></label>（请输入数字，从小到大依次排序）
                 </li>
                 <li>
                     <label><span class="label_text">推荐图：</span><input type="text" id="imgUrl" value=""/></label> 
                       <input type="button" value="上传" class="btn_upfile p_btn" onclick="uploadImg('');"/>
                 </li>
                 <li id="imgLi" style="display:none">
                     <label>
                 	   <img src="" width="300" height="120" alt="iPhone:60*24" id="imgUrl_iphone"/>
                       <img src="" width="110" height="30" alt="Android3.1:22*6" id="imgUrl_android3"/>
                       <img src="" width="110" height="30" alt="Android2.1:22*6" id="imgUrl_android2"/> 
                     </label> 
                 </li>
                 <li>
                     <label><span class="label_text">HD推荐图：</span><input type="text" id="imgHDUrl" value=""/></label> 
                       <input type="button" value="上传" class="btn_upfile p_btn" onclick="uploadHDImg('');"/>
                 </li>
                 <li id="imgHDLi" style="display:none">
                     <label>
                 	   <img src="" width="300" height="120" alt="ipad:60*24" id="imgHDUrl_ipad"/>
                     </label> 
                 </li>
                 <li>
                     <label>
                           <span class="label_text">有效期范围：</span>
                    	   <input type="text" id="beginDate" value=""/> ~
                     </label> 
                      <input type="text" id="endDate" value=""/>
                 </li>
                 <li>
                     <label>
                     <span class="label_text">内容：</span>
                     <textarea class="p_long" id="recommendContent" rows="5" cols="80"></textarea>
                     </label>
                      (10-50个汉字)
                 </li>
             </ul>
             <p class="p_sure"><button class="p_btn" type="submit">保存,再添加一条</button></p>
           </form>
	   </div>

		<input type="hidden" value="上传图片" id="uploadImgClick"/>
		<div class="js_zs js_cl_all" id="uploadImg" style="width:650px;">
			<h3><a class="close" href="javascript:void(0);" onclick="closeWin('uploadImg')">X</a>上传图片</h3>
			  <div class="tab_ztxx_all">
			   <iframe id="uploadImgIframeObc" src="" width="100%" height="100" frameborder="0" scrolling="no" ></iframe>
		     </div>
		</div>
		
		<script type="text/javascript">
			$(function(){
			    $('#beginDate').datepicker({dateFormat: 'yy-mm-dd'});
			    $('#endDate').datepicker({dateFormat: 'yy-mm-dd'});
			});
		</script>
  </body>
</html>
