<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="l" uri="/tld/lvmama-tags.tld"%>
<%
 String basePath = request.getContextPath();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>目的地和自由行绑定页面mobile_source_info.jsp</title>
<link rel="stylesheet" href="<%=basePath %>/css/place/backstage_table.css"/>
<link rel="stylesheet" href="<%=basePath %>/css/place/panel.css"/>
<link rel="stylesheet" href="<%=basePath %>/js/base/autocomplete/jquery.ui.autocomplete.css"/>
<s:include value="/WEB-INF/pages/pub/jquery.jsp"/>
<script type="text/javascript">
	var basePath="<%=basePath %>";
</script>
<script type="text/javascript" src="<%=basePath %>/js/base/autocomplete/jquery.ui.autocomplete.js"></script>
<script type="text/javascript" src="<%=basePath %>/js/place/houtai.js"></script>
<script type="text/javascript" src="<%=basePath %>/js/seo/panel_custom.js"></script>
<script type="text/javascript" src="<%=basePath %>/js/mobile/mobile_recommend.js?version=20131101"></script>
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
<script type="text/javascript">
  function sendSeqs(obj) {
	  if(null != obj && null != $(obj).val()) {
		  $("#rseq").val($(obj).val());
	  }
  }
  
  function sendValues(fromId,id,obj) {
	  if(null != obj && null != $(obj).val()) {
		  $("#"+fromId+" #"+id).val($(obj).val());
	  }
  }
</script>
</head>
  
<body style="background:#fff">
	<input type="hidden" id="sonBlockId" value="<s:property value="mobileRecommendBlock.id"/>"/>
	   <s:if test='pageChannel == "dest"'>
		   	 <h5>绑定产品与目的地</h5>
		      <div class="detail_box">
			   	     <ul class="ul_detail vip_info clearfix">
		             	<li>
		                      <span class="label_text">标签(TAG)：</span>
		                      <span class="label_text"><input type="checkbox"  name="tag" value="FREE_TOUR" />周边自由行 &nbsp;</span>
		                      <span class="label_text"><input type="checkbox"  name="tag" value="GROUP"/>周边跟团游 &nbsp;</span>
		                      <span class="label_text"><input type="checkbox"  name="tag" value="LDISTANCE_TRAVEL"/>长途游 &nbsp;</span>
		                      <span class="label_text"><input type="checkbox"  name="tag" value="OVERSEA_TRAVEL"/>出境游 &nbsp;</span>
		                      <span class="label_text"><input type="checkbox"  name="tag" value="OTHER"/>其它 &nbsp;</span>
		                 </li>
		                 <li>
		                     <label><span class="label_text">排序值：</span>
		                     <input type="text" id="seq" value="<s:property value="mobileRecommendInfo.seqNum"/>" onblur="sendSeqs(this)"/></label>（请输入数字，从大到小依次排序）
		                 </li>
		                <li>
                            <label>
	                            <span class="label_text">图片：</span>
	                            <input type="text" id=imgUrl value="<s:property value="mobileRecommendInfo.recommendImageUrl"/>"/>
                            </label> 
                            <input type="button" value="上传" class="btn_upfile p_btn" onclick="uploadImg('');"/></br>
                        </li>
                        <li id="imgLiId" style="display:none" >
                            <img  src="http://pic.lvmama.com<s:property value="mobileRecommendInfo.recommendImageUrl"/>" alt="图片找不到" id="imgUrl_dest" width="150" height="60"  />
                        </li>
                        
                        <li>
                            <label>
	                            <span class="label_text">HD图片：</span>
	                            <input type="text" id=imgHDUrl value="<s:property value="mobileRecommendInfo.recommendHDImageUrl"/>"/>
                            </label> 
                            <input type="button" value="上传" class="btn_upfile p_btn" onclick="uploadHDImg('');"/></br>
                        </li>
                        <li id="imgHDLiId" style="display:none" >
                            <img  src="http://pic.lvmama.com<s:property value="mobileRecommendInfo.recommendHDImageUrl"/>" alt="图片找不到" id="imgHDUrl_dest" width="150" height="60"  />
                        </li>
		             </ul>
			   </div>
		  </s:if>
		  <!-- 绑定自由行  -->
		  <s:if test='pageChannel == "freeTour"'>
		      <h5>添加自由行目的地</h5>
		      <div class="detail_box">
			   	     <ul class="ul_detail vip_info clearfix">
		             	<li>
		                   <span class="label_text">板块名称：</span><s:property value="mobileRecommendBlock.blockName"/>
		                </li>
		                <li>
		                   <label>
		                     <span class="label_text">&nbsp;&nbsp;经&nbsp;&nbsp;度：</span>
		                     <input type="text" id="longitude" value="<s:property value="mobileRecommendInfo.longitude"/>"onblur="sendValues('destForm','longitude',this)"/>
		                   </label>
		                   <label>
		                     <span class="label_text">&nbsp;&nbsp;纬&nbsp;&nbsp;度：</span>
		                     <input type="text" id="latitude" value="<s:property value="mobileRecommendInfo.latitude"/>"onblur="sendValues('destForm','latitude',this)"/>
		                   </label>
		                </li>
		                <li>
		                     <label>
		                     <span class="label_text">&nbsp;&nbsp;价&nbsp;&nbsp;格：</span>
		                     <input type="text" id="price" value="<s:property value="mobileRecommendInfo.price"/>" onblur="sendValues('destForm','price',this)"/>
		                     </label>
		                      <label>
		                     <span class="label_text">排序值：</span>
		                     <input type="text" id="seq" value="<s:property value="mobileRecommendInfo.seqNum"/>" onblur="sendSeqs(this)"/>
		                     </label>（从大到小依次排序）
		                 </li>
		                <li>
                            <label>
	                            <span class="label_text">&nbsp;&nbsp;图&nbsp;&nbsp;片：</span>
	                            <input type="text" id=imgUrl value="<s:property value="mobileRecommendInfo.recommendImageUrl"/>" onblur="sendValues('destForm','imgUrl',this)"/>
                            </label> 
                            <input type="button" value="上传" class="btn_upfile p_btn" onclick="uploadImg('');"/></br>
                        </li>
                        
                         <li>
                              <label><span class="label_text">内容：</span>
                              <textarea class="p_long" id="recommendContent" rows="5" cols="80" onblur="sendValues('destForm','recommendContent',this)" ><s:property value="mobileRecommendInfo.recommendContent" /></textarea>
                              </label>
                               (10-50个汉字)
                          </li>
                                
                        <li id="imgLiId" style="display:none" >
                            <img  src="http://pic.lvmama.com<s:property value="mobileRecommendInfo.recommendImageUrl"/>" alt="图片找不到" id="imgUrl_dest" width="150" height="60"  />
                        </li>
		           </ul>
			   </div>
	      </s:if>
		
		  <!-- 列表  -->
		  <div id="panel_content" >
		        <div class="p_oper">
		           <form action="<%=basePath %>/mobile/mobileRecommendBlock!getMobileRecommendInfoSource.do" id="destForm" method="post" >
				    <input type="hidden" id="recommendBlockId" name="mobileRecommendBlock.id" value="<s:property value="mobileRecommendBlock.id"/>"/>
				    <input type="hidden" id="pageChannel"  name="pageChannel" value="<s:property value="pageChannel"/>"/>
				    <input type="hidden" name="mobileRecommendInfo.recommendImageUrl" id=recommendImageUrl value="<s:property value="mobileRecommendInfo.recommendImageUrl"/>"/>
				    <input type="hidden" name="mobileRecommendInfo.recommendHDImageUrl" id=recommendHDImageUrl value="<s:property value="mobileRecommendInfo.recommendHDImageUrl"/>"/>
				    <input type="hidden" name="mobileRecommendInfo.tag" id=tags value="<s:property value="mobileRecommendInfo.tag"/>"/>
				    <input type="hidden" name="mobileRecommendInfo.seqNum" id=rseq value="<s:property value="mobileRecommendInfo.seqNum"/>"/>
				    <input type="hidden" name="mobileRecommendInfo.longitude" id=longitude value="<s:property value="mobileRecommendInfo.longitude"/>"/>
				    <input type="hidden" name="mobileRecommendInfo.latitude" id=latitude value="<s:property value="mobileRecommendInfo.latitude"/>"/>
				    <input type="hidden" name="mobileRecommendInfo.recommendContent" id=recommendContent value="<s:property value="mobileRecommendInfo.recommendContent"/>"/>
					<input type="hidden" name="mobileRecommendInfo.price" id=price value="<s:property value="mobileRecommendInfo.price"/>"/>
					<table>
						<tr>
						    <td>ID:<input type="text" name="objectId" value="<s:property value="objectId"/>" id="objectId"></td>  
			                <td>名称:<input type="text" name="keywords" value="<s:property value="keywords"/>"></td>
			                <td>产品类型: 
			                   <input type="radio" value="1" name="stage" checked/>目的地  
						       <input type="radio" value="2" name="stage" />景区  
						     </td> 
							<td><input type="submit" value="查询"></td>
						</tr>
					</table>
				   </form>
		        </div>
		             <div class="oper_box">
		             <table class="p_table">
		                <thead>
		                   <tr>
		                       <th>对象ID</th>
		                       <th>对象名称</th>
		                       <th>操作</th>
		                   </tr>
		                </thead>
		                <tbody>
		 				<s:iterator value="mudidi">
		 				 <tr>
		                  <td><s:property value="placeId"/></td>
		                  <td><a href="http://www.lvmama.com/dest/<s:property value="pinYinUrl"/>" target="_blank"><s:property value="name"/></a></td>
		                  <td>
		                      <!-- 目的地  -->
			                   <s:if test='pageChannel == "dest"'>
				                  <a href="javascript:void(0)" 
				                  onclick="bindDest2departure('destForm','<s:property value="placeId"/>','<s:property value="name"/>','<s:property value="stage"/>','dest');" 
				                 >添加</a>
			                   </s:if>
			                   
			                   <!-- 自由行  -->
			                   <s:if test='pageChannel == "freeTour"'>
			                   	  <a href="javascript:void(0)" 
				                  onclick="bindDest2departure('destForm','<s:property value="placeId"/>','<s:property value="name"/>','<s:property value="stage"/>','freeTour');" 
				                  >添加</a>
			                   </s:if>
		                  </td>
		                </tr>
		 				</s:iterator>
		                  </tbody>
		                 </table>
		                 <div class="p_page">
		                      <div class="pages">
		                     	 <s:property escape="false" value="@com.lvmama.comm.utils.Pagination@pagination(pagination)"/>
		                      </div>
		                 </div>
		             </div>
             </div>
            
            <!-- 上传图片 -->
			<input type="hidden" value="上传图片" id="uploadImgClick"/>
			<div class="js_zs js_cl_all" id="uploadImg" style="width:650px;">
				<h3><a class="close" href="javascript:void(0);" onclick="closeWin('uploadImg')">X</a>上传图片</h3>
				    <iframe id="uploadImgIframeObc" src="" width="100%" height="100" frameborder="0" scrolling="no" ></iframe>
			</div>
		    <script type="text/javascript">
			  // 只要有图片链接 ，则显示图片 
			  var r_img_url = '<s:property value="mobileRecommendInfo.recommendImageUrl"/>';
			  if(null != r_img_url && "" != r_img_url) {
				  $("#imgLiId").show();
			  }
			 </script>
  </body>
</html>
