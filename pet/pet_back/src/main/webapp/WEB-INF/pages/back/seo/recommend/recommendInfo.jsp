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
<link rel="stylesheet" href="<%=basePath %>/css/place/backstage_table.css"/>
<link rel="stylesheet" href="<%=basePath %>/css/place/panel.css"/>
<s:include value="/WEB-INF/pages/pub/jquery.jsp"/>
<script type="text/javascript">
var basePath="<%=basePath %>";
</script>
<script type="text/javascript" src="<%=basePath %>/js/place/houtai.js"></script>
<script type="text/javascript" src="<%=basePath %>/js/seo/panel_custom.js"></script>
<script type="text/javascript" src="<%=basePath %>/js/seo/recommend.js"></script>
<script type="text/javascript">

function getRecommendInfoSource(recommendBlockId){
	parent.document.getElementById("getRecommendInfoSourceIframeObc").src=basePath+"/seo/getRecommendInfoSource.do?id="+recommendBlockId;
	 $('#getRecommendInfoSourceIframeObc', window.parent.document).load(function openIframeWin(){ 
	   $('#getRecommendInfoSourceClick', window.parent.document).trigger("click");
	   $('#getRecommendInfoSourceIframeObc', window.parent.document).unbind("load",openIframeWin); //移除事件监听
     }); 
}

function batchRecommendInfoClick(){
	   $('#sonBlockId', window.parent.document).val($("#sonBlockId").val());
	   $('#sonBlockModeType', window.parent.document).val($("#sonBlockModeType").val());
	   $('#batchRecommendInfoClick', window.parent.document).trigger("click");
}

function copyRecommendInfoClick(){
	   $('#sonBlockId', window.parent.document).val($("#sonBlockId").val());
	   $('#sonBlockModeType', window.parent.document).val($("#sonBlockModeType").val());
	   $('#copyRecommendInfoClick', window.parent.document).trigger("click");
}

function delRecommendInfo(recommendInfoId){
	if(confirm("确定删除？")==true)
	{
		var param = {"id":recommendInfoId};
		$.ajax({type:"POST", url:"<%=basePath %>/seo/recommendInfo!delRecommendInfo.do", data:param, dataType:"json", success:function (data) {
			if(data.flag=='true'){
				alert("删除成功!");
				window.location.reload(true);
			}else{
				alert("删除出错!");		
			}
	    }});
	}
}

function endFunction(id){
	window.location.reload(true);
}

function saveRecommendInfoSeq(){
	if($('input[name="recommendInfoId_seq"]').length==0){
		alert("没有可保存的数据");
		return false;
	}
	var param="";
	$('input[name="recommendInfoId_seq"]').each(function(){    
		param=param+ "recommendInfoId="+$(this).val()+"&";    

	});  
	$('input[name="recommendInfo_seq"]').each(function(){    
		param=param+ "recommendInfoSeq="+$(this).val()+"&";    

	}); 
	$.ajax({type:"POST", url:"<%=basePath %>/seo/recommendInfo!saveRecommendInfoSeq.do?"+param, dataType:"json", success:function (data) {
		if(data.flag=='true'){
			alert("修改成功!");
			window.location.reload(true);
		}else{
			alert("修改出错!");		
		}
    }});
}
</script>
</head>
<body style="background:#fff; padding-bottom:100px"> 


<div id="panel_content">
                <input type="hidden" id="sonBlockId" value="<s:property value="sonBlock.recommendBlockId"/>"/>
                <input type="hidden" id="sonBlockModeType" value="<s:property value="sonBlock.modeType"/>"/>
            	<div class="p_crumbs"><p><a href="javascript:void(0)">ID:<s:property value="sonBlock.recommendBlockId"/></a> &gt; <a href="javascript:void(0)"><s:property value="recommendBlock.name"/>--<s:property value="sonBlock.name"/></a> &gt; 
            	<a href="javascript:void(0)">数据类型：
			          <s:if test='sonBlock.modeType=="1"'>目的地</s:if>
				      <s:elseif test='sonBlock.modeType=="2"'>景点</s:elseif>
				      <s:elseif test='sonBlock.modeType=="3"'>产品</s:elseif>
				      <s:elseif test='sonBlock.modeType=="4"'>其他</s:elseif>
				      <s:elseif test='sonBlock.modeType=="5"'>酒店</s:elseif>
				      <s:elseif test='sonBlock.modeType=="6"'>景点和目的地</s:elseif>
            	</a></p></div>
                <div class="p_oper">
                	<!-- 注意：此处调用弹窗的class必须排在第一位，如btn_padd 排在前面，且btn_padd与对应弹窗#padd_box中padd是相同的 -->
                	<input type="button" value="添&#12288;加" class="btn_padd p_btn" onclick="getRecommendInfoSource('<s:property value="sonBlock.recommendBlockId"/>')"/>&#12288;&#12288;
                	<s:if test="sonBlock.modeType!=4">
                	<input type="button" value="批量添加" class="btn_bulkadd p_btn" onclick="batchRecommendInfoClick()"/>&#12288;&#12288;
                	</s:if>
                	<input type="button" value="复制添加" class="btn_copyadd p_btn" onclick="copyRecommendInfoClick()"/>&#12288;&#12288;
                	<input type="button" value="保存排序" class="p_btn" onclick="saveRecommendInfoSeq()"/>
                </div>
                <div class="oper_box">
                	<table class="p_table">
                        <thead>
                            <tr>
                                <th> </th>
                                <th>排序值</th>
                                <s:if test='sonBlock.modeType=="3"'>
                                <th>状态</th>
                                </s:if>
                                <s:if test="sonBlock.modeType!=4">
                                <th>对象ID</th>
                                </s:if>
                                <th>对象名称</th>
                                <th>操作</th>
                            </tr>
                        </thead>
                        <tbody>
                           <s:iterator value="pagination.items" var="info">
                            <tr>
                                <td><input type="checkbox" name="recommendInfoId_seq" value="<s:property value="#info.recommendInfoId"/>"/></td>
                                <td><input type="text" name="recommendInfo_seq" value="<s:property value="#info.seq"/>" class="p_num"/></td>
                                <s:if test='sonBlock.modeType=="3"'>
                                <td>
						  			<s:if test='status=="true"'>
								    		正常
								    	</s:if>
								    	<s:else>
								    		已下线/不可售
								    	</s:else>
                                </td>
								</s:if>
								<s:if test='sonBlock.modeType!=4'>
                                <td><s:property value="#info.recommObjectId"/></td>
                                </s:if>
                                <td><s:property value="#info.title"/></td>
                                <td><a href="javascript:void(0)" class="p_btn_edit">编辑<i class="p_arrow"></i></a> <a href="javascript:void(0)" class="p_btn_del" onclick="delRecommendInfo('<s:property value="#info.recommendInfoId"/>')">删除</a></td>
                            </tr>
                            <tr class="p_detail">
                            	<td colspan="6">
                            	<h5>详细信息</h5>
                                <div class="detail_box">
                                	<form class="form_hor" onsubmit="saveRecommendInfoForm('ri<s:property value="#info.recommendInfoId"/>');return false;" id="ri<s:property value="#info.recommendInfoId"/>">
                                    <ul class="ul_detail vip_info clearfix">
                                        <s:if test='sonBlock.modeType!=4'>
                                        <li>
                                            <label><span class="label_text">对象ID：</span><input type="text" id="recommObjectId" value="<s:property value="#info.recommObjectId"/>" readonly="readonly" disabled="disabled" /></label>
                                        </li>
                                        </s:if>
                                        <li>
                                            <label><span class="label_text">对象URL：</span><input type="text" class="p_long" id="url" value="<s:property value="#info.url"/>"/></label>
                                        </li>
                                        <li>
                                            <label><span class="label_text">对象名称：</span><input type="text" id="title" value="<s:property value="#info.title"/>"/></label>
                                        </li>
                                        <li>
                                            <label><span class="label_text">图片地址：</span><input type="text" id="imgUrl" value="<s:property value="#info.imgUrl"/>"/></label> 
                                              <input type="button" value="上传" class="btn_upfile p_btn" onclick="uploadImg('<s:property value="#info.recommendInfoId"/>');"/>
                                        </li>
                                        <li>
                                            <label><span class="label_text">排序值：</span><input type="text" id="seq" value="<s:property value="#info.seq"/>"/></label>
                                        </li>
                                        <li>
                                            <label><span class="label_text">介绍：</span><input type="text" id="remark" class="p_long" value="<s:property value="#info.remark"/>"/></label>
                                        </li>
                                    </ul>
                                    <ul class="ul_detail clearfix">
                                        <li>
                                            <label><span class="label_text">备用字段1：</span><input type="text" id="bakWord1" value="<s:property value="#info.bakWord1"/>"/></label>
                                        </li>
                                        <li>
                                            <label><span class="label_text">备用字段2：</span><input type="text" id="bakWord2" value="<s:property value="#info.bakWord2"/>"/></label>
                                        </li>
                                        <li>
                                            <label><span class="label_text">备用字段3：</span><input type="text" id="bakWord3"  value="<s:property value="#info.bakWord3"/>"/></label>
                                        </li>
                                        <li>
                                            <label><span class="label_text">备用字段4：</span><input type="text" id="bakWord4"  value="<s:property value="#info.bakWord4"/>"/></label>
                                        </li>
                                        <li>
                                            <label><span class="label_text">备用字段5：</span><input type="text" id="bakWord5"  value="<s:property value="#info.bakWord5"/>"/></label>
                                        </li>
                                        <li>
                                            <label><span class="label_text">备用字段6：</span><input type="text" id="bakWord6"  value="<s:property value="#info.bakWord6"/>"/></label>
                                        </li>
                                        <li>
                                            <label><span class="label_text">备用字段7：</span><input type="text" id="bakWord7"  value="<s:property value="#info.bakWord7"/>"/></label>
                                        </li>
                                        <li>
                                            <label><span class="label_text">备用字段8：</span><input type="text" id="bakWord8"  value="<s:property value="#info.bakWord8"/>"/></label>
                                        </li>
                                        <li>
                                            <label><span class="label_text">备用字段9：</span><input type="text" id="bakWord9"  value="<s:property value="#info.bakWord9"/>"/></label>
                                        </li>                                       
                                        <li>
                                            <label><span class="label_text">备用字段10：</span><input type="text" id="bakWord10"  value="<s:property value="#info.bakWord10"/>"/></label>
                                        </li>
                                    </ul>
                                    <p class="p_sure">
                                    <input type="hidden" value="<s:property value="#info.recommendInfoId"/>" id="recommendInfoId"/>
                                    <button class="p_btn" type="submit">保&#12288;存</button></p>
                                    </form>
                                </div>
                                </td>
                            </tr>
                            </s:iterator>
                        </tbody>
                    </table>
                    <div class="p_page">
                        <div class="pages"><span>共<s:property value="pagination.totalResultSize"/>条取最上面<s:property value="sonBlock.itemNumberLimit"/>条</span><s:property escape="false" value="@com.lvmama.comm.utils.Pagination@pagination(pagination.pageSize,pagination.totalPageNum,pagination.url,pagination.currentPage)"/></div>
                    </div>
                </div>
            </div>

<input type="hidden" value="上传图片" id="uploadImgClick"/>
<div class="js_zs js_cl_all" id="uploadImg" style="width:650px;">
	<h3><a class="close" href="javascript:void(0);" onclick="closeWin('uploadImg')">X</a>上传图片</h3>
	  <div class="tab_ztxx_all">
	   <iframe id="uploadImgIframeObc" src="" width="100%" height="100" frameborder="0" scrolling="no" ></iframe>
     </div>
</div>
</body>
</html>
