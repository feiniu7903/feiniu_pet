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
<title>首页推荐页面（82）</title>
<link rel="stylesheet" href="<%=basePath %>/css/place/backstage_table.css"/>
<link rel="stylesheet" href="<%=basePath %>/css/place/panel.css"/>
<s:include value="/WEB-INF/pages/pub/jquery.jsp"/>
<script type="text/javascript">
var basePath="<%=basePath %>";
</script>
<script type="text/javascript" src="<%=basePath %>/js/place/houtai.js"></script>
<script type="text/javascript" src="<%=basePath %>/js/seo/panel_custom.js"></script>
<script type="text/javascript" src="<%=basePath %>/js/mobile/mobile_recommend.js?version=20130923"></script>
<jsp:include page="/WEB-INF/pages/pub/timepicker.jsp" />
<script type="text/javascript">
	// 添加 . 
	function getRecommendInfoSource(recommendBlockId,type){
		 parent.document.getElementById("getRecommendInfoSourceIframeObc").src=basePath+"/mobile/mobileRecommendBlock!getMobileRecommendInfoSource.do?mobileRecommendBlock.id="+recommendBlockId+"&pageChannel="+type;
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
	
	//更改状态
	function changeRecommendInfoStatus(recommendInfoId,status){
		var m = "确认现在是否开启？";
		if("N" == status) {
			 m = "确认现在是否关闭？";
		}
		if(confirm(m)==true){
			var param = {"mobileRecommendInfo.id":recommendInfoId,"mobileRecommendInfo.isValid":status};
			$.ajax({type:"POST", url:"<%=basePath %>/mobile/mobileRecommendInfo!changeRecommendInfoStatus.do", data:param, dataType:"json", success:function (data) {
				if(data.flag=='true'){
					alert("更改成功!");
					window.location.reload(true);
				}else{
					alert("更改出错!");		
				}
		    }});
		}
	}
	
	// 批量开启 
	function changeAllStatus(status) {
		var ids = getCheckedValue('recommendInfoId_seq');
		if( null == ids || "" == ids ) {
			alert('请选择至少一条记录!');
			return false;
		}
		
		if(confirm('是否批量开启所选记录?')==true){
			var param = {"ids":ids,"status":status};
			$.ajax({type:"POST", url:"<%=basePath %>/mobile/mobileRecommendInfo!changeRecommendInfoStatus.do", data:param, dataType:"json", success:function (data) {
				if(data.flag=='true'){
					alert("更改成功!");
					window.location.reload(true);
				}else{
					alert("更改出错!");		
				}
		    }});
		}
		
	}
	
	// 查看操作日志. 
	function recommendInfoLog(){
		alert('该功能暂未开放.');
	}
	
	function endFunction(id){
		window.location.reload(true);
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
        <input type="hidden" id="sonBlockId" value="<s:property value="sonBlock.id"/>"/>
        <input type="hidden" id="sonBlockModeType" value="<s:property value="sonBlock.blockType"/>"/>
    	<div class="p_crumbs"><p><a href="javascript:void(0)">ID:<s:property value="sonBlock.id"/></a> &gt; <a href="javascript:void(0)"><s:property value="recommendBlock.blockName"/>--<s:property value="sonBlock.blockName"/></a> </p></div>
        <!-- 查询  -->
        <div class="p_oper">
       		<!-- <h3 class="newTit">查询</h3> -->
			<form action="<%=basePath%>/mobile/mobileRecommendInfo.do" method="post" id="queryListForm">
				<table border="0" cellspacing="0" cellpadding="0" class="search_table" width="100%">
					<%-- <tr>
						<td>标题：</td>
						<td>
							<input id="recommendTitle" name="mobileRecommendInfo.recommendTitle" class="newtext1"/>
						</td>
						
						<td>状态：</td>
						<td>
							  <input type="radio" value="Y" name="mobileRecommendInfo.isValid" id="son_blockType1"/>开启
						      <input type="radio" value="N" name="mobileRecommendInfo.isValid" id="son_blockType2"/>关闭
						      <input type="radio" value="" name="mobileRecommendInfo.isValid" id="son_blockType3" checked/>全部
						</td>
					</tr>
					<tr>
						<td>链接地址：</td>
						<td>
							<input id="url" name="mobileRecommendInfo.url" class="newtext1"/>
						</td>
						
						<td>有效期范围：</td>
						<td><input id="beginDate" type="text" class="newtext1"
							name="startDate" value="${startDate}"/> ~ <input id="endDate" type="text"
							class="newtext1" name="endDate" value="${endDate}"/>
						</td>
					</tr> --%>
					<tr>
						<td colspan="4">
							<!-- 注意：此处调用弹窗的class必须排在第一位，如btn_padd 排在前面，且btn_padd与对应弹窗#padd_box中padd是相同的 -->
				        	<!-- <input type="button" value="查询" class="btn_bulkadd p_btn" onclick="batchRecommendInfoClick()"/>&#12288;&#12288; -->
				        	<input type="button" value="添&#12288;加" class="btn_padd p_btn" onclick="getRecommendInfoSource('<s:property value="sonBlock.id"/>','index')"/>&#12288;&#12288;
				        	<input type="button" value="批量删除" class="btn_padd p_btn" onclick="delAll()"/>&#12288;&#12288;
				        	<input type="button" value="批量开启" class="btn_padd p_btn" onclick="changeAllStatus('Y')"/>&#12288;&#12288;
				        	<input type="button" value="批量关闭" class="btn_padd p_btn" onclick="changeAllStatus('N')"/>&#12288;&#12288;
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
                        <th>标题</th>
                        <th>链接地址</th>
                        <th>HD链接地址</th>
                        <th>开始时间</th>
                        <th>结束时间</th>
                        <th>状态</th>
                        <th>操作</th>
                    </tr>
                </thead>
                <tbody>
                   <s:iterator value="pagination.items" var="info">
                    <tr>
                        <td><input type="checkbox" name="recommendInfoId_seq" value="<s:property value="#info.id"/>"/></td>
                        <td><s:property value="#info.id"/></td>
                        <td><s:property value="#info.seqNum"/></td>
                        <td><s:property value="#info.recommendTitle"/></td>
                        <td><s:property value="#info.url"/></td>
                        <td><s:property value="#info.hdUrl"/></td>
                        <td><s:date name="#info.beginDate" format="yyyy-MM-dd" /></td>
                        <td><s:date name="#info.endDate" format="yyyy-MM-dd" /></td>
                        <s:if test='#info.isValid == "Y"'>
                                <td>开启</td>
                        </s:if>
                         <s:if test='#info.isValid != "Y"'>
                                <td>关闭</td>
                        </s:if>
                        <td>
                            <s:if test='#info.isValid == "Y"'>
                                <a href="javascript:void(0)" class="p_btn_del" onclick="changeRecommendInfoStatus('<s:property value="#info.id"/>','N')">关闭&nbsp;</a> 
                            </s:if>
                        	<s:if test='#info.isValid != "Y"'>
                        	    <a href="javascript:void(0)" class="p_btn_edit">编辑<i class="p_arrow"></i></a> 
                                <a href="javascript:void(0)" class="p_btn_del" onclick="changeRecommendInfoStatus('<s:property value="#info.id"/>','Y')">开启&nbsp;</a> 
                            </s:if>
                        	<a href="javascript:void(0)" class="p_btn_del" onclick="delRecommendInfo('<s:property value="#info.id"/>')">删除&nbsp;</a>
                        	<a href="javascript:void(0)" class="p_btn_del"  onclick="recommendInfoLog('<s:property value="#info.id"/>')" >日志</i></a> 
                        </td>
                    </tr>
                    <tr class="p_detail">
                    	<td colspan="9">
                    	<h5>详细信息</h5>
                        <div class="detail_box">
                        	<form class="form_hor" onsubmit="saveRecommendInfoForm('ri<s:property value="#info.id"/>','<s:property value="#info.id"/>','index');return false;" id="ri<s:property value="#info.id"/>">
                            <input type="hidden" id="id" value="<s:property value="#info.id"/>"/>
                            <input type="hidden" id="isValid" value="<s:property value="#info.isValid"/>"/>
                            <ul class="ul_detail vip_info clearfix">
                            	<li>
                                    <label><span class="label_text">标题：</span><input type="text" class="p_long" id="recommendTitle" value="<s:property value="#info.recommendTitle"/>"/></label>
                               		（2-16个汉字）
                                </li>
                                <li>
                                    <label><span class="label_text">链接地址：</span><input type="text" class="p_long" id="url" value="<s:property value="#info.url"/>"/></label>
                                </li>
                                <li>
                                    <label><span class="label_text">HD链接地址：</span><input type="text" class="p_long" id="hdUrl" value="<s:property value="#info.hdUrl"/>"/></label>
                                </li>
                                <li>
                                    <label><span class="label_text">排序值：</span><input type="text" id="seq" value="<s:property value="#info.seqNum"/>"/></label>
                                                                                               （请输入数字，从小到大依次排序）
                                </li>
                                <li>
                                    <label><span class="label_text">推荐图：</span><input type="text" id=imgUrl value="<s:property value="#info.recommendImageUrl"/>"/></label> 
                                      <input type="button" value="上传" class="btn_upfile p_btn" onclick="uploadImg('<s:property value="#info.id"/>');"/>
                                </li>
                                <li style="text-align:center;">
				                 	   <img src="http://pic.lvmama.com<s:property value="#info.recommendImageUrl"/>" alt="iPhone:60*24" id="imgUrl_iphone" width="300" height="120"  />iPhone:600*240
				                       <img src="http://pic.lvmama.com<s:property value="#info.recommendImageUrl"/>" alt="Android3.1:22*6" id="imgUrl_android3" width="110" height="30" />Android3.1:220*60
				                       <img src="http://pic.lvmama.com<s:property value="#info.recommendImageUrl"/>" width="110" height="30"  alt="Android2.1:22*6" id="imgUrl_android2"/>Android2.1:220*60
				                 </li>
				                 <li>
                                    <label><span class="label_text">HD推荐图：</span><input type="text" id=imgHDUrl value="<s:property value="#info.recommendHDImageUrl"/>"/></label> 
                                      <input type="button" value="上传" class="btn_upfile p_btn" onclick="uploadHDImg('<s:property value="#info.id"/>');"/>
                                </li>
                                <li style="text-align:center;">
				                 	   <img src="http://pic.lvmama.com<s:property value="#info.recommendHDImageUrl"/>" alt="iPhone:60*24" id="imgUrl_iphone" width="300" height="120"  />iPhone:600*240
				                </li>
                                <li >
                                    <label><span class="label_text">有效期范围：</span>
                                      	   <input type="text" id="beginDate<s:property value="#info.id"/>" onfocus="bindDateSelect(this)" value="<s:date name="#info.beginDate" format="yyyy-MM-dd" />"/> ~
                                    </label> 
                                      	   <input type="text" id="endDate<s:property value="#info.id"/>"  onfocus="bindDateSelect(this)" value="<s:date name="#info.endDate" format="yyyy-MM-dd" />"/>
                                </li>
                                
                                <li>
                                    <label><span class="label_text">内容：</span>
                                    <textarea class="p_long" id="recommendContent" rows="5" cols="80"  ><s:property value="#info.recommendContent"/></textarea>
                                    </label>
                                     (10-50个汉字)
                                </li>
                            </ul>
                            <p class="p_sure">
                            <input type="hidden" value="<s:property value="#info.id"/>" id="recommendInfoId"/>
	                        <button class="p_btn" type="submit">保&#12288;存</button></p>
                            </form>
                        </div>
                        </td>
                     </tr>
                    </s:iterator>
                </tbody>
            </table>
            <div class="p_page">
                <div class="pages"><span>共<s:property value="pagination.totalResultSize"/>条</span>
                <s:property escape="false" value="@com.lvmama.comm.utils.Pagination@pagination(pagination.pageSize,pagination.totalPageNum,pagination.url,pagination.currentPage)"/></div>
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
		$(function(){
		    $('#beginDate').datepicker({dateFormat: 'yy-mm-dd'});
		    $('#endDate').datepicker({dateFormat: 'yy-mm-dd'});
		});
		
		// 绑定时间选择框 
		function bindDateSelect(obj) {
			 $(obj).datepicker({dateFormat: 'yy-mm-dd'});
			 $(obj).focus();
		}
	</script>
</body>
</html>
