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

function batchRecommendInfo(){
	var objectId=$("#batchRecommendInfoObjectId").val();
	var recommendBlockId=$("#sonBlockId").val();
	if(objectId!=""&&recommendBlockId!=""){
	        var recommendObjectId=objectId.split(",");
			var data="recommendInfo.seq=0&recommendInfo.recommendBlockId="+recommendBlockId;
			var reg = /^[0-9]{1,20}$/;
			for(var i=0;i<recommendObjectId.length;i++){
				if(!reg.test(recommendObjectId[i])){
					alert("请输入正确的Id:"+recommendObjectId[i]);
					return false;
				}
				data+="&objectId="+recommendObjectId[i];
			}
			$.ajax({type:"POST", url:"<%=basePath %>/seo/recommendInfo!saveRecommendInfo.do", data:data, dataType:"json", success:function (json) {
			 		if(json.flag=="true"){
			 			var message="添加成功";
			 			if(json.msg!=""){
			 				message="添加失败的id:"+json.msg;
			 			}
			 			alert(message);
			 			$("#batchRecommendInfoObjectId").val("");
			 			pageContent(recommendBlockId);
					}else{
						alert("添加失败");
			 		}
			}});
	}else{
		alert("请选择需要添加的列表");
	}
}

function copyRecommendInfos() {
	var recommendBlockId=$("#sonBlockId").val();
	var srcBlockId=$.trim($("#srcBlockId").val());
	var reg = /^[0-9]{1,20}$/;
	if(!reg.test(srcBlockId)){
		alert("请输入正确的blockId："+srcBlockId);
		return false;
	}
	var param = {"srcBlockId":srcBlockId,"destBlockId":recommendBlockId};
	$.ajax({type:"POST", url:"<%=basePath %>/seo/recommendInfo!copyRecommendInfos.do", data:param, dataType:"json", success:function (data) {
        if(data.flag=='true'){
        	if(data.msg!=undefined){
        		alert("不存在该id:"+data.msg);
        		return false;
        	}
        	alert("复制成功");
 			pageContent(recommendBlockId);
        }else{
            alert("复制出错！");
        }
    }});
}

function pageContent(id)
{
	document.getElementById("iframeObc").src="<%=basePath %>/seo/recommendInfo.do?id="+id;
}

function updateRecommendBlockParent(recommendBlockId,name){
	$("#u_blockId").val(recommendBlockId);
	$("#u_name").val(name);
	$("#u_pageChannel").val($("#pageChannel").val());
	openWin('updateRecommendBlockParent');
}

function updateRecommendBlockSon(recommendBlockId,parentBlockId){
	$("#son_modeType").css("display","");
	$("#son_blockId").val(recommendBlockId);
	$("#son_parentBlockId").val(parentBlockId);
	$("#son_pageChannel").val($("#pageChannel").val());
	
	$("#son_name").val("");
	$("#son_itemNumberLimit").val("");
	$("#son_dataCode").val("");
	if(recommendBlockId!=""){
	 var param = {"recommendBlock.recommendBlockId":recommendBlockId};
	 $.ajax({type:"POST", url:"<%=basePath %>/seo/recommendBlock!getRecommendBlockById.do", data:param, dataType:"json", success:function (data) {
        if(data!=null){
        	data=data[0];
        	$("#son_name").val(data.name);
        	$("#son_itemNumberLimit").val(data.itemNumberLimit);
        	$("#son_dataCode").val(data.dataCode);
        	if(data.modeType=="1"){
        		$("#son_modeType1").attr("checked",'checked');
        	}else if(data.modeType=="2"){
        		$("#son_modeType2").attr("checked",'checked');
        	}else if(data.modeType=="3"){
        		$("#son_modeType3").attr("checked",'checked');
        	}else if(data.modeType=="4"){
        		$("#son_modeType4").attr("checked",'checked');
        	}else if(data.modeType=="5"){
        		$("#son_modeType5").attr("checked",'checked');
        	}else {
        		$("#son_modeType6").attr("checked",'checked');
        	}
        	$("#son_modeType").css("display","none");
        }
     }});
	}
	openWin('updateRecommendBlockSon');
}

function addRecommendBlockParent(formObj){
	var recommendBlockName=$("#"+formObj+" #recommendBlockName").val();
	var pageChannel=$("#pageChannel").val();
	var param = {"recommendBlock.pageChannel":pageChannel,"recommendBlock.levels":1,"recommendBlock.name":recommendBlockName};
	$.ajax({type:"POST", url:"<%=basePath %>/seo/recommendBlock!addRecommendBlock.do", data:param, dataType:"json", success:function (data) {
        if(data.flag=='true'){
        	alert("添加成功");
        	window.location.reload(true);
        }else{
            alert("复制出错！");
        }
    }});
}

function delRecommendBlock(recommendBlockId,levels)
{
	if(confirm("删除该模块将同时删除模块下所有信息！删除后无法恢复.确定删除该模块？")==true)
	{
		var param = {"recommendBlock.recommendBlockId":recommendBlockId,"recommendBlock.levels":levels};
		$.ajax({type:"POST", url:"<%=basePath %>/seo/recommendBlock!delRecommendBlock.do", data:param, dataType:"json", success:function (data) {
	        if(data.flag=='true'){
	        	alert("删除成功");
	        	window.location.reload(true);
	        }else{
	            alert("删除出错！");
	        }
	    }});	
	}
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

</script>
</head>

<body>
<input type="hidden" size="20" id="pageChannel" name="recommendBlock.pageChannel" value="<s:property value="pageChannel"/>"/>
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
                      <input type="text" name="recommendBlock.name" id="recommendBlockName"/><input type="submit" value="添加页面" class="p_btn"/>
			          <input type="hidden" size="20" id="recommendBlockLevels"name="recommendBlock.levels"  value="1"/>
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
                   	<a href="javascript:void(0)" onclick="delRecommendBlock('<s:property value="recos[#sts.count-1][0].recommendBlockId"/>','<s:property value="recos[#sts.count-1][0].levels"/>');" >删除</a>
                   	<a href="javascript:void(0)" onclick="updateRecommendBlockSon('','<s:property value="recos[#sts.count-1][0].recommendBlockId"/>')">添加子项</a>
                   	<a href="javascript:void(0)" onclick="updateRecommendBlockParent('<s:property value="recos[#sts.count-1][0].recommendBlockId"/>','<s:property value="recos[#sts.count-1][0].name"/>')" id="modify<s:property value="recos[#sts.count-1][0].recommendBlockId"/>">修改</a>
                   	<a href="javascript:void(0)" onclick="showSunList('<s:property value="recos[#sts.count-1][0].id"/>');" class="oper_item"><s:property value="recos[#sts.count-1][0].recommendBlockId"/>  <s:property value="recos[#sts.count-1][0].name"/></a>
                   	(<s:property value="recos[#sts.count-1][1].size"/>)
                    <ul class="ul_oper_list">
                      <s:iterator value="recos[#sts.count-1][1]">
                    	<li>
						  <a href="javascript:void(0)" onclick="pageContent('<s:property value="recommendBlockId"/>');" class="oper_item"><s:property value="recommendBlockId"/>  <s:property value="name"/></a>
                          <a href="javascript:void(0)" onclick="updateRecommendBlockSon('<s:property value="recommendBlockId" />','')" id="modify<s:property value="recommendBlockId"/>">修改</a>
						  <a href="javascript:delRecommendBlock('<s:property value="recommendBlockId"/>','<s:property value="levels"/>');" >删除</a>&nbsp;&nbsp;
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
    	<iframe id="iframeObc" src="" width="100%" height="700" frameborder="0"></iframe>
    	</div>
    </div><!-- div panel_main -->
</div>



<div class="js_zs js_cl_all" id="updateRecommendBlockParent">
	<h3><a class="close" href="javascript:void(0);" onclick="closeWin('updateRecommendBlockParent')">X</a>编辑当前页面名称</h3>
	  <div class="tab_ztxx_all">
	  <form action="seo/recommendBlock!updateRecommendBlock.do"  method="post" onsubmit="">
	  <input type="hidden" name="recommendBlock.pageChannel" id="u_pageChannel" value="" />
	  <input type="hidden" name="recommendBlock.recommendBlockId" id="u_blockId" value=""/>
	 <table width="100%" border="0" class="tab_ztxx">
	   <tr>
	     <td class="bgNavBlue" align="left">
	              页面名称:<input type="text" size="20" name="recommendBlock.name" id="u_name" value=""/>
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

<div class="js_zs js_cl_all" id="updateRecommendBlockSon">
	<h3><a class="close" href="javascript:void(0);" onclick="closeWin('updateRecommendBlockSon')">X</a>编辑当前页面模块</h3>
	  <div class="tab_ztxx_all">
      <form action="seo/recommendBlock!updateRecommendBlock.do" id="recommendBlockSonForm" method="post"  onsubmit="return checkForm();" >
      <input type="hidden" name="recommendBlock.pageChannel" id="son_pageChannel" />
	  <input type="hidden" name="recommendBlock.recommendBlockId" id="son_blockId" value=""/>
	  <input type="hidden" name="recommendBlock.parentRecommendBlockId" id="son_parentBlockId" value=""/>
	  <input type="hidden" size="20" name="recommendBlock.levels"  value="2"/>
	  <table width="100%" border="0"  class="tab_ztxx">
	     <tr>
	      <td class="bgNavBlue" align="left">名称:<input type="text" size="20" name="recommendBlock.name" id="son_name"/></td>
	    </tr>
	    <tr id="son_modeType">
	      <td class="bgNavBlue" align="left">
	      类型:<input type="radio" value="1" name="recommendBlock.modeType" id="son_modeType1"/>目的地 
	      <input type="radio" value="2" name="recommendBlock.modeType" id="son_modeType2"/>景点 
	      <input type="radio" value="3" name="recommendBlock.modeType" id="son_modeType3"/>产品 
	      <input type="radio" value="4" name="recommendBlock.modeType" id="son_modeType4" checked="checked" />其他
	      <input type="radio" value="5" name="recommendBlock.modeType" id="son_modeType5"/>酒店
	      <input type="radio" value="6" name="recommendBlock.modeType" id="son_modeType6"/>景点&目的地
	      </td>
	    </tr>
	    <tr>
	      <td class="bgNavBlue" align="left">显示数据条数:<input type="text" size="20" name="recommendBlock.itemNumberLimit" id="son_itemNumberLimit"/></td>
	    </tr>
	    <tr>
	      <td class="bgNavBlue" align="left">数据取值标识:<input type="text" size="20" name="recommendBlock.dataCode" id="son_dataCode"/></td>
	    </tr>
	  </table>
	  <input value="提交" type="submit" />
	</form>
	</div>
	<script type="text/javascript">
	     function checkForm(){
	    	 if($.trim($("#son_itemNumberLimit").val())==0){
	    		 alert("请输入正整数，不能为零！");
	    		 return false;
	    	 }else {
	    		 return true;
	    	 }
	     }
	</script>
  </div>


<input type="hidden" id="sonBlockId" value=""/>
<input type="hidden" id="sonBlockModeType" value=""/>

<input type="hidden" value="添加推荐信息" id="getRecommendInfoSourceClick"/>
<!-- 添加推荐信息窗口 -->
<div class="js_zs js_cl_all" id="getRecommendInfoSource" style="width:800px;margin-left:-500px">
	<h3><a class="close" href="javascript:void(0);" onclick="closeWin('getRecommendInfoSource')">X</a>添加推荐</h3>
	  <div class="tab_ztxx_all">
      <iframe id="getRecommendInfoSourceIframeObc" src="" width="100%" height="600" frameborder="0" scrolling="auto" ></iframe>
     </div>
</div>

<input type="hidden" value="批量添加" id="batchRecommendInfoClick"/>
<div class="js_zs js_cl_all" id="batchRecommendInfo" style="width:650px;">
	<h3><a class="close" href="javascript:void(0);" onclick="closeWin('batchRecommendInfo')">X</a>批量添加推荐信息</h3>
	  <div class="tab_ztxx_all">
                 请输入ID(必须以英文逗号分割)：<br/><textarea cols="90" rows="5" id="batchRecommendInfoObjectId" ></textarea>
       <input type="button" onclick="batchRecommendInfo();" value="添加"/>
     </div>
</div>

<input type="hidden" value="复制添加" id="copyRecommendInfoClick"/>
<div class="js_zs js_cl_all" id="copyRecommendInfo" style="width:650px;">
	<h3><a class="close" href="javascript:void(0);" onclick="closeWin('copyRecommendInfo')">X</a>复制模块数据</h3>
	  <div class="tab_ztxx_all">
                源BlockID：<input type="text" id="srcBlockId" value=""/>
      <input type="button" id="copyRecommendInfos" onclick="copyRecommendInfos();" value="复制"/>
     </div>
</div>
</body>
</html>
