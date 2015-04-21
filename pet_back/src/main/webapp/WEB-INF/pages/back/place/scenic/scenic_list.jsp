<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="mis" uri="/tld/lvmama-tags.tld"%>
<s:set var="basePath"><%=request.getContextPath()%></s:set>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
<s:include value="/WEB-INF/pages/pub/new_jquery.jsp"/>
<script type="text/javascript" src="${basePath}/js/place/place.js"></script>
<script type="text/javascript" src="${basePath}/js/place/jquery.autocomplate.js"></script>
<script charset="utf-8" src="${basePath}/js/recon/My97DatePicker/WdatePicker.js"></script>
<script charset="utf-8" src="${basePath}/js/base/kindeditor-4.1.7/kindeditor.js"></script>
<script type="text/javascript">
function popClose(){
	$("span.ui-icon-closethick").click();
}	
$(function () {
    $("#auto_place").autocomplete("autocomplate.do", {dataType: "xml", width: "auto"});
    
});
function checkForm(){
	var reg = new RegExp("^((-[0-9]+)|([0-9])*)$");
	if($("input[name='place.placeId']").val()!=""){
		if(!reg.test($("input[name='place.placeId']").val())){
			alert("请输入正确的景区ID（数字）!");
			$("input[name='place.placeId']").focus();
			return false;
		}
	}
	$("#searchSecnicForm").submit();
	return true;
	
}

function showLogDia(){
	var log = $("#showLogDialog");
	alert(log);
}

</script>
<script type="text/javascript" src="${basePath}/js/place/houtai.js"></script>
<script type="text/javascript" src="${basePath}/js/base/log.js"></script>
</head>
<body>
	<div id="popDiv" style="display: none"></div>
	<div class="iframe-content">
		<div class="p_box">
		<form action="${basePath}/place/scenicList.do" method="post" id="searchSecnicForm">
			<input type="hidden" name="stage" id="stage" value="<s:property value='stage'/>"/>
				<table class="p_table form-inline" width="100%">
					<tr>
						<td class="p_label">景区ID：</td>
						<td>
							<input class="input_b" name="place.placeId" value="<s:property value="place.placeId"/>">
						</td>
						<td class="p_label">景区名称：</td>
						<td>
							<input class="input_b" name="place.name" value="<s:property value="place.name"/>">
						</td>
						<td class="p_label">上级目的地：</td>
						<td>
							<input class="input_b" id="auto_place" name="autoPlaceName" value="<s:property value="autoPlaceName"/>">
							<input type="hidden" name="place.parentPlaceId" value="<s:property value="place.parentPlaceId"/>"/>
						</td>
					</tr>
					<tr>
						<td class="p_label">景区状态：</td>
						<td>
						<s:select list="isValidList"  name="place.isValid" headerKey="" headerValue="全部" theme="simple" listKey="elementCode" listValue="elementValue"></s:select>
						</td>
						<td class="p_label">景区主主题：</td>
						<td>
						<s:select list="subjectList"  name="place.firstTopic" headerKey="" headerValue="全部" theme="simple" listKey="subjectName" listValue="subjectName"></s:select>
						</td>
						<td class="p_label">有无活动：</td>
						<td>
							<s:select list="isHasActivityList"  name="place.isHasActivity" headerKey="" headerValue="全部" theme="simple" listKey="elementCode" listValue="elementValue"></s:select>
						</td>
					</tr>
					<tr>
						<td class="p_label">时间：</td>
						<td colspan="5">
							<input name="startDate" id="startDate" value='<s:property value="startDate"/>' type="text" class="Wdate" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>
						   -<input name="endDate" id="endDate" value="<s:property value="endDate"/>" type="text" class="Wdate" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'startDate\')}'})"/>
						</td>


					</tr>
				</table>				
				<p class="tc mt20">
					<mis:checkPerm permCode="4011"><input type="button" class="btn btn-small w5" name="saveseq" id="saveseq" value="保存排序" /></mis:checkPerm>
   					<mis:checkPerm permCode="4012"><input type="button" class="btn btn-small w5" name="scenic_add" id="scenic_add" value="添加景区" /></mis:checkPerm>
					<button name="btnQuery" class="btn btn-small w5" type="submit" onclick="return searchSecnicForm()">查询</button>　
				</p>
			</form>
		</div>
		<div class="p_box">
			<table class="p_table">
				<tr>
					<th width="16"><input type="checkbox"
						name="chk_all" id="chk_all" /></th>
					<th>ID</th>
					<th>名称</th>
					<th>url(新)</th>
					<th>状态</th>
					<th>使用模板</th>
					<th>创建时间</th>
					<th>排序值</th>
					<th>操作</th>
				</tr>
				<s:iterator value="placeList">
					<tr>
						<td><input type="checkbox" name="chk_list" value="${placeId}" /></td>
						<td><s:property value="placeId" /></td>
						<td>
							<a href="http://www.lvmama.com/dest/<s:property value="pinYinUrl"/>" target="_blank"><s:property value="name" /></a>
						</td>
						<td>
							<a href="http://ticket.lvmama.com/scenic-<s:property value="placeId"/>" target="_blank"><s:property value="name" /></a>
						</td>
					    <td><s:if test='isValid=="Y"'><font color=green>有效</font></s:if><s:else><font color=red>无效</font></s:else></td>
					    <td><s:if test='placeType=="DOMESTIC"'>国内</s:if><s:elseif test='placeType=="ABROAD"'>国外</s:elseif><s:else>无</s:else></td>
						<td><s:date name="createTime" format="yyyy-MM-dd" /></td>
						<td><input type="text" class="seq_check" id="chk_list_${placeId }" value="${seq }" /></td>
						<td class="gl_cz">
							<mis:checkPerm permCode="4013">
								<a href="javascript:scenicEdit('${placeId }','${stage}');">景区基本信息</a>
							</mis:checkPerm>
    						<mis:checkPerm permCode="4014">
    							<a href="javascript:photoEdit('${placeId}');">景点图片</a>
    						</mis:checkPerm> 
    						<mis:checkPerm permCode="4015">
    							<a href="javascript:saleInfoEdit('${placeId}');">景点销售信息</a>
    						</mis:checkPerm>
							<mis:checkPerm permCode="4016">
							<a href="javascript:scenicDescEdit('${placeId}','${stage }');">景点介绍
							</a></mis:checkPerm>
    						<a href="javascript:trafficInfoEdit('${placeId}');">景点交通信息</a>
    						<mis:checkPerm permCode="4017"><a href="javascript:activityEdit('${placeId}');">景点活动</a></mis:checkPerm>
    						<mis:checkPerm permCode="4018"><a href="${basePath}/place/productSeqList.do?productSearchInfo.productAlltoPlaceIds=${placeId}">关联产品</a></mis:checkPerm>
    						<mis:checkPerm permCode="4020"><a style='display: none' href="${basePath}/QuestionAnswer/QueryAsk.do?placeId=${placeId}">问答</a></mis:checkPerm>
     						<mis:checkPerm permCode="4019"><a href="javascript:announcementEdit('${placeId}');">公告</a></mis:checkPerm>
     						 <a href="javascript:scenicDescExplore('${placeId}','${stage }');">目的地探索</a>
     						<mis:checkPerm permCode="3583"> <a href="javascript:scenicServiceEnsure('${placeId}','${stage }');">服务保障</a></mis:checkPerm>
    						<mis:checkPerm permCode="4019"><a href="javascript:deleteMem('<s:property value='@com.lvmama.comm.utils.homePage.PlaceUtils@SCENIC_MEMCACHED_PREFIX_KEY' />','${placeId}');">清除缓存</a></mis:checkPerm>
                 			<mis:checkPerm permCode="4019"><a class="showLogDialog" param="{'parentType':'SCENIC_LOG_PLACE','parentId':${placeId}}" href="#log">操作日志</a></mis:checkPerm>

						</td>
					</tr>
				</s:iterator>
				<tr>
   					<td colspan="3" align="right">总条数：<s:property value="pagination.totalResultSize"/></td>
					<td colspan="5" style="text-align: right; padding-right: 20px;"><s:property
							escape="false"
							value="@com.lvmama.comm.utils.Pagination@pagePost(pagination.pageSize,pagination.totalPageNum,pagination.url,pagination.currentPage)" /></td>
				</tr>
			</table>
		</div>
	</div>
</body>
<script type="text/javascript">
	$(function(){
		$("#scenic_add").click(function(){
			 $("#popDiv").load("${basePath}/place/placeAdd.do?stage=2",function() {
					$(this).dialog({
	            		modal:true,
	            		title:"新增景区",
	            		width:300,
	            		height:150
	                });
	            });		
		});			
	});
	
	
	function scenicDescEdit(placeId,stage) {	
	$("#popDiv").html();
    $("#popDiv").load("${basePath}/place/scenicDescAdd.do?placeId="+placeId+"&stage="+stage, function() {
        $(this).dialog({
            modal:true,
            title:"景点介绍",
            width:900,
            height:650
        });
     });
	
   }
	
	/*
	 *目的地探索
	 */
	function scenicDescExplore(placeId,stage) {	
		$("#popDiv").html();
	    $("#popDiv").load("${basePath}/place/scenicDescExploreAdd.do?placeId="+placeId+"&stage="+stage, function() {
	        $(this).dialog({
	            modal:true,
	            title:"目的地探索",
	            width:900,
	            height:650/*,
	            open:function(){
	            	var elt = document.createElement("link");
	            	elt.rel="stylesheet";
	            	elt.href="//pic.lvmama.com/styles/v5/dest.css";
	            	document.getElementsByTagName("head")[0].appendChild(elt);
	            }*/
	        });
	     });
		
	   }
	
	
	
	/*
	 *服务保障
	 */
	function scenicServiceEnsure(placeId,stage) {	
		$("#popDiv").html();
	    $("#popDiv").load("${basePath}/place/scenicServiceEnsure.do?placeId="+placeId+"&stage="+stage, function() {
	        $(this).dialog({
	            modal:true,
	            title:"服务保障",
	            width:900,
	            height:400
	        });
	     });
		
	   }
	
	function scenicEdit(placeId,stage) {
		$("#popDiv").html();
		$("#popDiv").load("${basePath}/place/scenicEdit.do?placeId=" + placeId + "&stage=" + stage, function() {
			$(this).dialog({
	        	modal:true,
	            title:"编辑景区基本信息",
	            width:800,
	            height:650
	        });
	     });							
	} ;

	function photoEdit(placeId) {
		$("#popDiv").html();
		 $("#popDiv").load("${basePath}/place/placePhotoList.do?placePhoto.placeId="+placeId+"&stage=2",function() {
		$(this).dialog({
         		modal:true,
         		title:"修改图片",
         		width:1000,
        		height:550
             });
         });		
	};

	function saleInfoEdit(placeId) {
		$("#popDiv").html();
		$("#popDiv").load("${basePath}/place/scenicSaleInfoAdd.do?placeId="+placeId+"&stage=2",function() {
		$(this).dialog({
         		modal:true,
         		title:"修改销售信息",
         		width:1000,
        		height:550
             });
         });		
	};
	
	function activityEdit(placeId) {
		$("#popDiv").html();
		$("#popDiv").load("${basePath}/place/placeActivity.do?placeId="+placeId+"&stage=2",function() {
			$("#popDiv").dialog({
         		modal:true,
         		title:"修改活动信息",
         		width:1000,
        		height:550
             });
         });		
	};
	
	function announcementEdit(placeId) {
		$("#popDiv").html();
		$("#popDiv").load("${basePath}/place/placeHotelNotice.do?placeId="+placeId+"&noticeType=SCENIC",function() {
			$("#popDiv").dialog({
         		modal:true,
         		title:"修改活动信息",
         		width:1000,
        		height:550
             });
         });		
	};
		
	function trafficInfoEdit(placeId) {
		$("#popDiv").html();
		$("#popDiv").load("${basePath}/place/scenicTrafficInfoAdd.do?placeId="+placeId+"&stage=2",function() {
			$(this).dialog({
         		modal:true,
         		title:"修改交通信息",
         		width:1000,
        		height:550
             });

			var editor;
			KindEditor.ready(function(K) {
			    editor = K.create('#desc_id',{
			    	resizeType : 1,
			    	width:'800px',
			    	filterMode : true,
			    	uploadJson:'${basePath}/upload/uploadImg.do'
			    });
			});

         });		
	};

	/**
	 *清除缓存
	 */
	function deleteMem(prekey,placeid){
		if(prekey!=""&&prekey!=null&&placeid!=""&&placeid!=null){
		 if(!confirm("是否将此缓存删除?")){
			return false;
		 }
		 var key=prekey+placeid;
		var param = {"memcachedKey":key};
				$.ajax({type:"POST", url:"${basePath}/pub/deleteMemcached.do", data:param, dataType:"json", success:function (json) {
				 		if(json.flag=="true"){
				 			alert("已经执行删除操作");
						}else if(json.flag=="false"){
							alert("执行删除操作失败");
				 		}else{
				 			alert("这个"+key+"值为null");
				 		}
				}});
		}else{
			alert("请选择频道或者分站!");
		}
	}

</script>
</html>