<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="l" uri="/tld/lvmama-tags.tld"%>
<%
 String basePath = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>目的地</title>
<link rel="stylesheet" href="<%=basePath %>/css/place/backstage_table.css"/>
<link rel="stylesheet" href="<%=basePath %>/css/place/panel.css"/>
<link rel="stylesheet" href="<%=basePath %>/js/base/autocomplete/jquery.ui.autocomplete.css"/>
<s:include value="/WEB-INF/pages/pub/jquery.jsp"/>
<script type="text/javascript">
	var basePath="<%=basePath %>";
</script>
<script type="text/javascript" src="<%=basePath %>/js/place/houtai.js"></script>
<script type="text/javascript" src="<%=basePath %>/js/seo/panel_custom.js"></script>
<script type="text/javascript" src="<%=basePath %>/js/mobile/mobile_recommend.js"></script>
<jsp:include page="/WEB-INF/pages/pub/timepicker.jsp" />
<script type="text/javascript">
	// 目的地- 添加 .  转到 mobile_source_freetour.jsp 
	// type : 1 绑定触发地 ; 2 绑定目的地 
	function getRecommendInfoSource(id,type,pageChannel){
		// 出发地 
		 var url = basePath+"/mobile/mobileRecommendBlock!getMobileRecommendInfoSource.do?mobileRecommendBlock.id="+id+"&pageChannel="+pageChannel+"&bindType="+type;
		 // 目的地 
		 if(type == 2) { 
			url = basePath+"/mobile/mobileRecommendBlock!getMobileRecommendInfoSource.do?mobileRecommendInfo.id="+id+"&pageChannel="+pageChannel+"&bindType="+type;
		 }
		 parent.document.getElementById("getRecommendInfoSourceIframeObc").src=url;
		 $('#getRecommendInfoSourceIframeObc', window.parent.document).load(function openIframeWin(){ 
			   $('#getRecommendInfoSourceClick', window.parent.document).trigger("click");
			   $('#getRecommendInfoSourceIframeObc', window.parent.document).unbind("load",openIframeWin); //移除事件监听
	     }); 
	}
	
	// 删除 
	function delRecommendInfo(recommendInfoId){
		if(confirm("是否彻底删除？")==true){
			var param = {"mobileRecommendInfo.id":recommendInfoId};
			$.ajax({type:"POST", url:"<%=basePath %>/mobile/mobileRecommendInfo!delMobileRecommendInfo.do", data:param, dataType:"json", success:function (data) {
				if(data.flag=='true'){
					alert("删除成功!");
					window.location.reload(true);
				}else{
					alert("删除出错!");		
				}
		    }});
		}
	}
	
	function openCopyWin(){
		openWin('copyTags');
	}
	
	//复制 
	function copyAll(formObj){
		var tag =$("#"+formObj).find('input[name="tagname"]:checked').val();
		if(null == tag || "" == tag) {
			alert('请选择一个标签!');
			return false;
		}
		
		var stag =$("#queryListForm").find('input[name="mobileRecommendInfo.tag"]:checked').val();
		if(stag == tag) {
			alert('请不要复制相同的标签!');
			return false;
		}
		
		var ids = getCheckedValue('recommendInfoId_seq');
		if( null == ids || "" == ids ) {
			alert('请选择至少一条记录!');
			return false;
		}
		if(confirm("是否复制所选数据？")==true){
			var param = {"ids":getCheckedValue('recommendInfoId_seq'),"tag":tag};
			$.ajax({type:"POST", url:"<%=basePath %>/mobile/mobileRecommendInfo!copyMobileRecommendInfo.do", data:param, dataType:"json", success:function (data) {
				if(data.flag=='true'){
					alert("复制成功!");
				    $("#queryListForm").submit();
				}else{
					alert("复制出错!");		
				}
		    }});
		}
	}
	
	// 绑定目的地 .  
	function addDest(){
		openWin("uploadImg");
	}
	
</script>
<style type="">
.ul_detail li {
    display: inline;
    float: left;
    margin-bottom: 5px;
    text-align: left;
    width: 100%;
}
</style>
</head>
<body style="background:#fff; padding-bottom:100px"> 
    <div id="panel_content">
    	<div class="p_crumbs"><p><a href="javascript:void(0)">ID:<s:property value="sonBlock.id"/></a> &gt; <a href="javascript:void(0)"><s:property value="recommendBlock.blockName"/>--<s:property value="sonBlock.blockName"/></a> </p></div>
        <!-- 查询  -->
        <input type="hidden" id="sonBlockId" value="<s:property value="sonBlock.id"/>"/>
        <input type="hidden" id="sonBlockModeType" value="<s:property value="sonBlock.blockType"/>"/>
        <div class="p_oper">
			<form action="<%=basePath%>/mobile/mobileRecommendInfo.do" method="post" id="queryListForm">
				<table border="0" cellspacing="0" cellpadding="0" class="search_table" width="100%">
				    <input type="hidden" name="recommendBlockId"  value="<s:property value="recommendBlockId"/>" />
					<tr>
						<td>标签：</td>
						<td>  
							  <input type="radio" value="FREE_TOUR" name="mobileRecommendInfo.tag" id="tagIdFREE_TOUR"/>周边自由行
						      <input type="radio" value="GROUP" name="mobileRecommendInfo.tag" id="tagIdGROUP"/>周边跟团游
						      <input type="radio" value="LDISTANCE_TRAVEL" name="mobileRecommendInfo.tag" id="tagIdLDISTANCE_TRAVEL"/>长途游
						      <input type="radio" value="OVERSEA_TRAVEL" name="mobileRecommendInfo.tag" id="tagIdOVERSEA_TRAVEL"/>出境游
						      <input type="radio" value="" name="mobileRecommendInfo.tag"  checked/>全部
						</td>
					</tr>
					<tr>
						<td colspan="2">
							<!-- 注意：此处调用弹窗的class必须排在第一位，如btn_padd 排在前面，且btn_padd与对应弹窗#padd_box中padd是相同的 -->
				        	<input type="submit" value="查询" class="btn_bulkadd p_btn" />&#12288;&#12288;
				        	<input type="button" value="绑定" class="btn_padd p_btn" onclick="getRecommendInfoSource('<s:property value="sonBlock.id"/>','1','dest')"/>&#12288;&#12288;
				        	<input type="button" value="批量删除" class="btn_padd p_btn" onclick="delAll()"/>&#12288;&#12288;
				        	<input type="button" value="批量复制" class="btn_padd p_btn" onclick="openCopyWin()"/>&#12288;&#12288;
						</td>
					</tr>
				</table>
			</form>
        </div>
        
        <!-- 列表  -->
        <div class="oper_box">
        	<table class="p_table">
                <thead>
                    <tr>
                    <th><input type="checkbox" onclick="checkedAll(this);" value=""/></th>
                        <th>id</th>
                        <th>排序值</th>
                        <th>缩略图</th>
                        <th>产品类型</th>
                        <th>周边目的地</th>
                        <th>操作</th>
                    </tr>
                </thead>
                <tbody>
                   <s:iterator value="pagination.items" var="info">
                    <tr>
                        <td><input type="checkbox" name="recommendInfoId_seq" value="<s:property value="#info.id"/>"/></td>
                        <td><s:property value="#info.id"/></td>
                        <td><s:property value="#info.seqNum"/></td>
                        <td><img src="http://pic.lvmama.com<s:property value="#info.recommendImageUrl"/>" width="70" height="25"/></td>
                        <td>
                        <s:if test='#info.tag == "FREE_TOUR"'>
                                                                              周边自由行
                        </s:if>
                        <s:if test='#info.tag == "GROUP"'>
                                                                             周边跟团游
                        </s:if>
                        <s:if test='#info.tag == "LDISTANCE_TRAVEL"'>
                               	  长途游 
                        </s:if>
                        <s:if test='#info.tag == "OVERSEA_TRAVEL"'>
                               	 出境游 
                        </s:if>
                        <s:if test='#info.tag == "OTHER"'>
                                                                                         其它 
                        </s:if>
                         &nbsp;</td>
                        <td><s:property value="#info.recommendTitle"/></td>
                        <td>
                            <a href="javascript:void(0)" class="p_btn_edit">编辑<i class="p_arrow"></i></a> 
                        	<a href="javascript:void(0)" class="p_btn_del" onclick="delRecommendInfo('<s:property value="#info.id"/>')">删除&nbsp;</a>
                        </td>
                    </tr>
                    <tr class="p_detail">
                    	<td colspan="8">
                    	<h5>详细信息</h5>
                        <div class="detail_box">
                        	<form class="form_hor" onsubmit="updateRecommendInfo('ri<s:property value="#info.id"/>','<s:property value="#info.id"/>','dest');return false;" id="ri<s:property value="#info.id"/>">
                            <input type="hidden" id="id" value="<s:property value="#info.id"/>"/>
                            <ul class="ul_detail vip_info clearfix">
                                 <li>
				                      <span class="label_text">标签(TAG)：</span>
				                      <span class="label_text"><input type="radio"  name="tag" value="FREE_TOUR" <s:if test='#info.tag == "FREE_TOUR"'> checked </s:if> />周边自由行 &nbsp;</span>
				                      <span class="label_text"><input type="radio"  name="tag" value="GROUP" <s:if test='#info.tag == "GROUP"'> checked </s:if> />周边跟团游 &nbsp;</span>
				                      <span class="label_text"><input type="radio"  name="tag" value="LDISTANCE_TRAVEL" <s:if test='#info.tag == "LDISTANCE_TRAVEL"'> checked </s:if> />长途游 &nbsp;</span>
				                      <span class="label_text"><input type="radio"  name="tag" value="OVERSEA_TRAVEL" <s:if test='#info.tag == "OVERSEA_TRAVEL"'> checked </s:if> />出境游 &nbsp;</span>
				                      <span class="label_text"><input type="radio"  name="tag" value="OTHER" <s:if test='#info.tag == "OTHER"'> checked </s:if> />其它 &nbsp;</span>
				                  </li>
				                  <li>
                                    <label><span class="label_text">排序值：</span>
                                    <input type="text" id="seq" value="<s:property value="#info.seqNum"/>"/></label>
                                                                                               （请输入数字，从小到大依次排序）
                                </li>
				                <li>
		                            <label>
			                            <span class="label_text">图片：</span>
			                            <input type="text" id=imgUrl value="<s:property value="#info.recommendImageUrl"/>"/>
		                            </label> 
		                            <input type="button" value="上传" class="btn_upfile p_btn" onclick="uploadImg('<s:property value="#info.id"/>');"/></br>
		                        </li>
		                        <li id="imgLiId" >
		                            <img  src="http://pic.lvmama.com<s:property value="#info.recommendImageUrl"/>" alt="图片找不到" id="imgUrl_dest" width="150" height="60"  />
		                        </li>
		                        <li>
		                            <label>
			                            <span class="label_text">HD图片：</span>
			                            <input type="text" id=imgHDUrl value="<s:property value="#info.recommendHDImageUrl"/>"/>
		                            </label> 
		                            <input type="button" value="上传" class="btn_upfile p_btn" onclick="uploadHDImg('<s:property value="#info.id"/>');"/></br>
		                        </li>
		                        <li id="imgHDLiId" >
		                            <img  src="http://pic.lvmama.com<s:property value="#info.recommendHDImageUrl"/>" alt="图片找不到" id="imgHDUrl_dest" width="150" height="60"  />
		                        </li>
                            </ul>
                            <p class="p_sure">
	                        <button class="p_btn" type="submit">保&#12288;存</button></p>
                            </form>
                        </div>
                        </td>
                     </tr>
                     
                    </s:iterator>
                </tbody>
            </table>
            <div class="p_page">
                <div class="pages"><span>共<s:property value="pagination.totalResultSize"/>条</span><s:property escape="false" value="@com.lvmama.comm.utils.Pagination@pagination(pagination.pageSize,pagination.totalPageNum,pagination.url,pagination.currentPage)"/></div>
            </div>
        </div>
    </div>
    
    <!-- 上传图片 -->
	<input type="hidden" value="上传图片" id="uploadImgClick"/>
	<div class="js_zs js_cl_all" id="uploadImg" style="width:650px;">
		<h3><a class="close" href="javascript:void(0);" onclick="closeWin('uploadImg')">X</a>上传图片</h3>
		    <iframe id="uploadImgIframeObc" src="" width="100%" height="100" frameborder="0" scrolling="no" ></iframe>
	</div>
	
	
    <!-- 选择标签  -->
	<input type="hidden" value="选择标签" id="copyTagsClick"/>
	<div class="js_zs js_cl_all" id="copyTags" style="width:650px;">
		<h3><a class="close" href="javascript:void(0);" onclick="closeWin('copyTags')">X</a>选择标签</h3>
		<div class="tab_ztxx_all">
		      <form id="copyRecommendInfoForm" method="post" onsubmit="copyAll('copyRecommendInfoForm');return false;" >
			  <table width="100%" border="0"  class="tab_ztxx">
			     <tr id="son_modeType">
				      <td class="bgNavBlue" align="left">标签:
				          <input type="radio" name="tagname" value="FREE_TOUR"  id="tagIdFREE_TOUR"/>周边自由行
					      <input type="radio" name="tagname" value="GROUP"  id="tagIdGROUP"/>周边跟团游
					      <input type="radio" name="tagname" value="LDISTANCE_TRAVEL"  id="tagIdLDISTANCE_TRAVEL"/>长途游
					      <input type="radio" name="tagname" value="OVERSEA_TRAVEL"  id="tagIdOVERSEA_TRAVEL"/>出境游
				      </td>
			     </tr>
			  </table>
			  <input value="确定" type="submit" />
			</form>
		</div>
	</div>
    
    <script type="text/javascript">
       var t_tag = '<s:property value="mobileRecommendInfo.tag"/>';
       if(null != t_tag ) {
    	   $('#tagId'+t_tag).attr('checked',true);
       }
    </script>
</body>
</html>
