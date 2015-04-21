<%@ page language="java" pageEncoding="utf-8" contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="l" uri="/tld/lvmama-tags.tld"%>
<%
 String basePath = request.getContextPath();
%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>驴妈妈_景点/目的地管理后台</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" href="<%=basePath %>/css/place/backstage_table.css"/>
<link rel="stylesheet" href="<%=basePath %>/css/place/panel.css"/>
<s:include value="/WEB-INF/pages/pub/jquery.jsp"/>
<script type="text/javascript" src="<%=basePath %>/js/place/houtai.js"></script>
<script type="text/javascript" src="<%=basePath %>/js/seo/panel_custom.js"></script>
<script type="text/javascript" src="<%=basePath %>/js/mobile/mobile_recommend.js"></script>
<script type="text/javascript">
$(function(){
	$("#getRecommendInfoSourceClick").click(function(){
		openWin("getRecommendInfoSource");
	});
	$("#batchRecommendInfoClick").click(function(){
		openWin("batchRecommendInfo");
	});
	$("#copyRecommendInfoClick").click(function(){
		openWin("copyRecommendInfo");
	});
});

/**
 * block复制. 
 */
function copyRecommendBlock(blockId) {
	if(confirm("确定要复制吗？")==true){
		var param = {"mobileRecommendBlock.id":blockId};
		$.ajax({type:"POST", url:"<%=basePath %>/mobile/mobileRecommendBlock!copyRecommendBlock.do", data:param, dataType:"json", success:function (data) {
	        if(data.flag=='true'){
	        	alert("复制成功");
	        	window.location.reload(true);
	        }else{
	            alert("复制出错！");
	        }
	    }});
	}
}

function updateRecommendBlockParent(recommendBlockId,name){
	$("#u_blockId").val(recommendBlockId);
	$("#u_name").val(name);
	$("#u_pageChannel").val($("#pageChannel").val());
	openWin('updateRecommendBlockParent');
}

/** *************************** 节点操作    **************************** **/

// 添加根节点页面. 
function addRecommendBlockParent(formObj){
	var recommendBlockName=$("#"+formObj+" #mobileRecommendBlockName").val();
	if(recommendBlockName == '' || null == recommendBlockName) {
		recommendBlockName = "新建模块";
	}
	var pageChannel=$("#pageChannel").val();
	var param = {"mobileRecommendBlock.pageChannel":pageChannel,"mobileRecommendBlock.blockName":recommendBlockName};
	$.ajax({type:"POST", url:"<%=basePath %>/mobile/mobileRecommendBlock!addMobileRecommendBlock.do", data:param, dataType:"json", success:function (data) {
        if(data.flag=='true'){
        	alert("添加成功");
        	window.location.reload(true);
        }else{
            alert("复制出错！");
        }
    }});
}

//添加子项 
function updateRecommendBlockSon(id,parentBlockId){
	$("#son_modeType").css("display","");
	$("#son_blockId").val(id);
	$("#son_parentBlockId").val(parentBlockId);
	$("#son_pageChannel").val($("#pageChannel").val());
	
	$("#son_name").val("");
	if(id!=""){
		var param = {"mobileRecommendBlock.id":id};
		// 修改时用到. 
		$.ajax({type:"POST", url:"<%=basePath %>/mobile/mobileRecommendBlock!getMobileRecommendBlockById.do", data:param, dataType:"json", success:function (data) {
	        if(data!=null){
	        	data=data[0];
	        	$("#son_name").val(data.blockName);
	        	$("#objectId").val(data.objectId);
	        	$("#objectType").val(data.objectType);
	        	$("#seq_num").val(data.seqNum);
	        	$("#reserve1").val(data.reserve1);
	        	$("#reserve2").val(data.reserve2);
	        	$("#reserve3").val(data.reserve3);
	        	$("#reserve4").val(data.reserve4);
	        	$("#reserve5").val(data.reserve5);
	        	
	        	$("#t_bindBlockId").hide();
	        	
	        	if(data.blockType=="1"){
	        		$("#son_blockType1").attr("checked",'checked');
	        	}else if(data.blockType=="2"){
	        		$("#son_blockType2").attr("checked",'checked');
	        		$("#t_bindBlockId").show();
	        	}else if(data.blockType=="3"){
	        		$("#son_blockType3").attr("checked",'checked');
	        	}else if(data.blockType=="4"){
	        		$("#son_blockType4").attr("checked",'checked');
	        	}else {
	        		$("#son_blockType0").attr("checked",'checked');
	        	}
	        	// $("#son_modeType").css("display","none");
	        }
	     }});
	}
	
	openWin('updateRecommendBlockSon');
}

/**
 * 绑定object对象 
 */
function bingBlockObject(recommendInfoId){
	 document.getElementById("bindBlockIframeObc").src=basePath+"/mobile/mobileRecommendBlock!getObjectInfoList.do";
	 $('#bindBlockIframeObc').load(function openIframeWin(){ 
		openWin("bindBlock");
	   $('#bindBlockIframeObc').unbind("load",openIframeWin); //移除事件监听
    }); 
}

/**
 * 删除 type: 0根节点  1：子节点 
 */
function delRecommendBlock(recommendBlockId,type){
	if(confirm("删除该模块将同时删除模块下所有信息！删除后无法恢复.确定删除该模块？")==true){
		var param = {"mobileRecommendBlock.id":recommendBlockId,"delType":type};
		$.ajax({type:"POST", url:"<%=basePath %>/mobile/mobileRecommendBlock!delMobileRecommendBlock.do", data:param, dataType:"json", success:function (data) {
	        if(data.flag=='true'){
	        	alert("删除成功");
	        	window.location.reload(true);
	        }else{
	            alert("删除出错！");
	        }
	    }});	
	}
}

// 点击菜单链接. 
function pageContent(id){
	document.getElementById("iframeObc").src="<%=basePath %>/mobile/mobileRecommendInfo.do?recommendBlockId="+id;
}


function selectAll(){
	var obj = document.fom.elements;
	for (var i=0;i<obj.length;i++){
		if (obj[i].name == "delid"){
			obj[i].checked = true;
		}
	}
}


function unselectAll(){
	var obj = document.fom.elements;
	for (var i=0;i<obj.length;i++){
		if (obj[i].name == "delid"){
			if (obj[i].checked==true) obj[i].checked = false;
			else obj[i].checked = true;
		}
	}
}

function scysInfo(){
	$.ajax({type:"POST", url:"/seo/recommendBlock!scy.do", dataType:"json", success:function (json) {
			alert(json.msg);
	}});
}

// 关闭绑定列表页面. 
function closeObjWin(winId){
	var _id="#"+winId;
	$(_id).hide();
}

function radioChange(v){
	if(v == '2') {
		$("#t_bindBlockId").show();
	}else {
		$("#t_bindBlockId").hide();
	}
}

</script>
</head>

<body>
<input type="hidden" size="20" id="pageChannel" name="mobileRecommendBlock.pageChannel" value="<s:property value="pageChannel"/>"/>
<div class="xh_panel">
	<div class="panel_aside" style="height: 743px; display: block;">
    	<div class="modal-sbox" id="float_modal">
        	<form name="fom" id="fom" method="post" class="form-horizontal">
                <div class="form-group">
                    <label for="modal_id" class="form-label">模块ID：</label>
                    <div class="form-item"><input type="text" id="modal_id"/></div>
                </div>
                <div class="form-group">
                    <label for="modal_name" class="form-label">模块名称：</label>
                    <div class="form-item"><input type="text" id="modal_name"/> <input type="button" value="查询" class="p_btn btn_search"/> <input type="reset" value="清空" class="p_btn clear_search"/></div>
                </div>
            </form>
            
			<form action="#" id="addpageForm" method="post" name="addpageForm" onsubmit="addRecommendBlockParent('addpageForm');return false;"> 
				<div class="oper_btn">
                    <div class="form-item">(当前总数<s:property value="recos.size"/>个)  模块名称：
                      <input type="text" name="mobileRecommendBlock.name" id="mobileRecommendBlockName"/>
                      <input type="submit" value="添加页面" class="p_btn"/>
                    </div>
				</div>
			</form>

        </div>
        <div class="modal_slist" style="height: 663px;">
        <div class="oper_list">
        	<ul id="ul_oper_list" class="ul_oper_list">
                <s:iterator value="recos" status="sts">
				<li class="ul_oper_item">
                    <span class="simple_ico"></span>
                   	<a href="javascript:void(0)" onclick="delRecommendBlock('<s:property value="recos[#sts.count-1][0].id"/>','0');" >删除</a>
                   	<a href="javascript:void(0)" onclick="updateRecommendBlockSon('','<s:property value="recos[#sts.count-1][0].id"/>')">添加子项</a>
                   	<a href="javascript:void(0)" onclick="updateRecommendBlockParent('<s:property value="recos[#sts.count-1][0].id"/>','<s:property value="recos[#sts.count-1][0].blockName"/>')" id="modify<s:property value="recos[#sts.count-1][0].id"/>">修改</a>
                   	<a href="javascript:void(0)" onclick="showSunList('<s:property value="recos[#sts.count-1][0].id"/>');" class="oper_item"><s:property value="recos[#sts.count-1][0].id"/>  <s:property value="recos[#sts.count-1][0].blockName"/></a>
                   	(<s:property value="recos[#sts.count-1][1].size"/>)
                    <ul class="ul_oper_list">
                      <s:iterator value="recos[#sts.count-1][1]">
                    	<li>
						  <a href="javascript:void(0)" onclick="pageContent('<s:property value="id"/>');" class="oper_item"><s:property value="id"/>  <s:property value="blockName"/></a>
                          <a href="javascript:void(0)" onclick="updateRecommendBlockSon('<s:property value="id" />','')" id="modify<s:property value="id"/>">修改</a>
						  <a href="javascript:void(0)" onclick="copyRecommendBlock('<s:property value="id" />')" id="modify<s:property value="id"/>">复制</a>
						  <a href="javascript:delRecommendBlock('<s:property value="id"/>','1');" >删除</a>&nbsp;&nbsp;
                        </li>
                      </s:iterator>
                    </ul>
                </li>
                </s:iterator>
            </ul>
        </div></div>
    </div><!-- div panel_aside -->
    <div class="panel_main">
    	<div class="panel_control" id="panel_control" style="margin-left: 350px;"></div>
    	<!-- div panel_content -->
    	<div class="panel_content">
    	<iframe id="iframeObc" src="" width="100%" height="600" frameborder="0"></iframe>
    	</div>
    </div><!-- div panel_main -->
</div>


	<!-- 修改根节点名称   -->
	<div class="js_zs js_cl_all" id="updateRecommendBlockParent">
		<h3><a class="close" href="javascript:void(0);" onclick="closeWin('updateRecommendBlockParent')">X</a>编辑当前页面名称</h3>
		  <div class="tab_ztxx_all">
		  <form action="mobileRecommendBlock!updateRecommendBlock.do"  method="post" onsubmit="">
		  <input type="hidden" name="mobileRecommendBlock.pageChannel" id="u_pageChannel" value="" />
		  <input type="hidden" name="mobileRecommendBlock.id" id="u_blockId" value=""/>
		 <table width="100%" border="0" class="tab_ztxx">
		   <tr>
		     <td class="bgNavBlue" align="left">
		              页面名称:<input type="text" size="20" name="mobileRecommendBlock.blockName" id="u_name" value=""/>
		     </td>
		   </tr>
		   <tr>
		     <td class="bgNavBlue" align="left">
		     <input value="修改名称" type="submit"/>
		     </td>
		   </tr>
		 </table>
		</form>
	 </div>
	</div>		                  

   <!-- 子项编辑页面  -->
   <div class="js_zs js_cl_all" id="updateRecommendBlockSon">
	<h3><a class="close" href="javascript:void(0);" onclick="closeWin('updateRecommendBlockSon')">X</a>编辑当前页面模块</h3>
	  <div class="tab_ztxx_all">
      <form action="mobileRecommendBlock!updateRecommendBlock.do" id="recommendBlockSonForm" method="post"  onsubmit="return checkForm();" >
	  <input type="hidden" name="mobileRecommendBlock.id" id="son_blockId" value=""/>
	  <input type="hidden" name="mobileRecommendBlock.parentId" id="son_parentBlockId" value=""/>
	  <table width="100%" border="0"  class="tab_ztxx">
	     <tr>
	      <td class="bgNavBlue" align="left">名称:<input type="text" size="20" name="mobileRecommendBlock.blockName" id="son_name"/>
	        <span id="t_bindBlockId" style="display:none;"><a href="javascript:void(0)" onclick="bingBlockObject('','')">[绑定对象]</a></span>
	      	<input type="hidden" size="20" name="mobileRecommendBlock.objectId" id="objectId"/>
	      	<input type="hidden" size="20" name="mobileRecommendBlock.objectType" id="objectType"/>
	      </td>
	      <td class="bgNavBlue" align="left">排 序    :<input type="text" size="20" name="mobileRecommendBlock.seqNum" id="seq_num"/>(从大到小降序)</td>
	     </tr>
	     <tr id="son_modeType">
		     <td class="bgNavBlue" align="left" colspan="2">
			      类型:<input type="radio" value="1" name="mobileRecommendBlock.blockType" id="son_blockType1" onchange="radioChange(1)"/>内容推荐 
			      <input type="radio" value="2" name="mobileRecommendBlock.blockType" id="son_blockType2" onchange="radioChange(2)"/>目的地推荐
			      <input type="radio" value="3" name="mobileRecommendBlock.blockType" id="son_blockType3" onchange="radioChange(3)"/>产品推荐
			      <input type="radio" value="4" name="mobileRecommendBlock.blockType" id="son_modeType0" checked="checked" onchange="radioChange(4)"/>其它
		      </td>
	    </tr>
	     <tr>
	      <td class="bgNavBlue" align="left">备用字段1:<input type="text" size="20" name="mobileRecommendBlock.reserve1" id="reserve1"/></td>
	      <td class="bgNavBlue" align="left">备用字段2:<input type="text" size="20" name="mobileRecommendBlock.reserve2" id="reserve2"/></td>
	     </tr>
	     <tr>
	      <td class="bgNavBlue" align="left">备用字段3:<input type="text" size="20" name="mobileRecommendBlock.reserve3" id="reserve3"/></td>
	      <td class="bgNavBlue" align="left">备用字段4:<input type="text" size="20" name="mobileRecommendBlock.reserve4" id="reserve4"/></td>
	     </tr>
	      <tr>
	      <td class="bgNavBlue" align="left">备用字段5:<input type="text" size="20" name="mobileRecommendBlock.reserve5" id="reserve5"/></td>
	      <td class="bgNavBlue" align="left"></td>
	     </tr>
	  </table>
	  <input value="提交" type="submit" />
	</form>
	</div>
	<script type="text/javascript">
	     function checkForm(){
	    	 if($.trim($("#son_name").val())==''){
	    		 alert("名称不能为空！");
	    		 return false;
	    	 }else {
	    		 return true;
	    	 }
	     }
	</script>
  </div>


	<!-- 添加推荐信息窗口 -->
	<input type="hidden" id="sonBlockId" value=""/>
	<input type="hidden" value="添加推荐信息" id="getRecommendInfoSourceClick"/>
	<div class="js_zs js_cl_all" id="getRecommendInfoSource" style="width:800px;margin-left:-500px">
		<h3><a class="close" href="javascript:void(0);" onclick="closeWin('getRecommendInfoSource')">X</a>添加推荐</h3>
		  <div class="tab_ztxx_all">
	      <iframe id="getRecommendInfoSourceIframeObc" src="" width="100%" height="800" frameborder="0" scrolling="no" ></iframe>
	     </div>
	</div>

     <!-- block添加对象名称  -->
    <input type="hidden" value="绑定对象 " id="bindBlockClick"/>
	<div class="js_zs js_cl_all" id="bindBlock" style="width:650px;">
		<h3><a style="float: right;" href="javascript:void(0);" onclick="closeObjWin('bindBlock')">X</a>添加对象名称</h3>
		  <div class="tab_ztxx_all">
	         <iframe id="bindBlockIframeObc" src="" width="100%" height="800" frameborder="0" scrolling="no" ></iframe>
	     </div>
	</div>

	
</body>
</html>
