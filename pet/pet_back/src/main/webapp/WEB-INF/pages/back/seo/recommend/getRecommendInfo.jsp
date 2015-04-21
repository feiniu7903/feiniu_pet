<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="l" uri="/tld/lvmama-tags.tld"%>
<%
 String basePath = request.getContextPath();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>查询需要推荐的信息</title>
<link rel="stylesheet" href="<%=basePath %>/css/place/backstage_table.css"/>
<link rel="stylesheet" href="<%=basePath %>/css/place/panel.css"/>
<s:include value="/WEB-INF/pages/pub/jquery.jsp"/>
<script type="text/javascript">
var basePath="<%=basePath %>";
</script>
<script type="text/javascript" src="<%=basePath %>/js/place/houtai.js"></script>
<script type="text/javascript" src="<%=basePath %>/js/seo/panel_custom.js"></script>
<script type="text/javascript" src="<%=basePath %>/js/seo/recommend.js"></script>
<script type="text/javascript" src="<%=basePath %>/js/place/jquery.autocomplate.js"></script>
<script type="text/javascript">
	$(function(){
		//让iframe外部的load代码先执行，让iframe处于显示状态，之后再执行这里的代码，取出的坐标值就不会为0了（隐藏状态下标签的left值为0）
		setTimeout(function(){
			$("#auto_placeId").autocomplete("<%=basePath %>/place/autocomplate.do?stage=1&stage=2&stage=3", {dataType: "xml", width: "auto",bindId:"toPlaceId"});
		    $("#auto_fromPlaceId").autocomplete("<%=basePath %>/place/autocomplate.do?stage=1&stage=2&stage=3", {dataType: "xml", width: "auto",bindId:"fromPlaceId"});		
		},200);
	});
</script>
<script type="text/javascript">
function addRecommendInfo(recommendBlockId,recommendObjectId,title){
	if(recommendBlockId!=""){
		var data ={"recommendInfo.recommendBlockId":recommendBlockId,"objectId":recommendObjectId,"recommendInfo.title":title,"recommendInfo.seq":0};
		$.ajax({type:"POST", url:basePath+"/seo/recommendInfo!saveRecommendInfo.do", data:data, dataType:"json", success:function (json) {
		 		if(json.flag=="true"){
		 			alert("添加成功");
		 			endFunction(recommendBlockId);
		 		}else{
		 			alert("添加失败");
		 		}
		}});
	}else{
		alert("请选择需要添加的列表");
	}
}

function chkSearchSource(form){
	var reg = /^[0-9]{1,20}$/;
	if($("#objectId").val()!=""){
		if(!reg.test($.trim($("#objectId").val()))){
		    alert("请输入正确的id:"+$.trim($("#objectId").val()));
			return false;
		}
	}
	return true;
}

/**
 * recommend.js 添加处理完成之后调用
 */
function endFunction(id){
	parent.document.getElementById("iframeObc").src=basePath+"/seo/recommendInfo.do?id="+id+"&"+new Date();
	
	$("#ri #memberPrice").val("");
	$("#ri #marketPrice").val("");
	$("#ri #recommendInfoId").val("");
	$("#ri #seq").val("0");
	$("#ri #url").val("");
	$("#ri #imgUrl").val("");
	$("#ri #title").val("");
	$("#ri #remark").val("");
	$("#ri #bakWord1").val("");
	$("#ri #bakWord2").val("");
	$("#ri #bakWord3").val("");
	$("#ri #bakWord4").val("");
	$("#ri #bakWord5").val("");
	$("#ri #bakWord6").val("");
	$("#ri #bakWord7").val("");
	$("#ri #bakWord8").val("");
	$("#ri #bakWord9").val("");
	$("#ri #bakWord10").val("");
}
</script>
</head>
  
<body style="background:#fff">
<input type="hidden" id="sonBlockId" value="<s:property value="sonBlock.recommendBlockId"/>"/>
<input type="hidden" id="sonBlockModeType" value="<s:property value="sonBlock.modeType"/>"/>
<s:if test='sonBlock.modeType!="4"'>
         <div id="panel_content">
                <input type="hidden" value="10700" id="sunBlokId">
                <div class="p_oper">
                	<form action="<%=basePath %>/seo/getRecommendInfoSource.do" method="post" onsubmit="return chkSearchSource(this);">
					<table>
						<tr>
						   <s:if test='sonBlock.modeType=="3"'>
							<td> 产品ID:<input type="text" name="objectId" value="<s:property value="objectId"/>" id="objectId"></td> 
			                <td>产品名称:<input type="text" name="keywords" value="<s:property value="keywords"/>"></td>
							<td>类型:<select name="productType" >
							<option value="">-选择-</option> 
							<option value="TICKET" <s:if test="productType=='TICKET'" >selected="selected"</s:if> >门票</option> 
							<option value="HOTEL" <s:if test="productType=='HOTEL'" >selected="selected"</s:if>>酒店</option> 
							<option value="ROUTE" <s:if test="productType=='ROUTE'" >selected="selected"</s:if>>线路</option> 
							<option value="OTHER" <s:if test="productType=='OTHER'" >selected="selected"</s:if>>其他</option> 
							</select></td>
							<td>出发标的:
							<input type="text" id="auto_fromPlaceId" name="autoFromPlaceName"  value="<s:property value="autoFromPlaceName"/>">
	                            <input type="hidden" name="fromPlaceId" value="<s:property value="fromPlaceId"/>"/>
							</td>
							<td>目的标的:
							   <input type="text" id="auto_placeId" name="autoPlaceName"  value="<s:property value="autoPlaceName"/>">
	                            <input type="hidden" name="toPlaceId" value="<s:property value="toPlaceId"/>"/>	
							</td>
						   </s:if>
						   <s:if test='sonBlock.modeType==2 ||sonBlock.modeType== 1 ||sonBlock.modeType== 5||sonBlock.modeType== 6'>
						    <td><s:if test='sonBlock.modeType=="6"'>目的地/景区</s:if><s:if test='sonBlock.modeType=="1"'>目的地</s:if><s:if test='sonBlock.modeType=="2"'>景区</s:if><s:if test='sonBlock.modeType=="5"'>酒店</s:if>ID:<input type="text" name="objectId" value="<s:property value="objectId"/>" id="objectId"></td> 
			                <td><s:if test='sonBlock.modeType=="6"'>目的地/景区</s:if><s:if test='sonBlock.modeType=="1"'>目的地</s:if><s:if test='sonBlock.modeType=="2"'>景区</s:if><s:if test='sonBlock.modeType=="5"'>酒店</s:if>名称:<input type="text" name="keywords" value="<s:property value="keywords"/>"></td>
						   </s:if>
							<td><input type="hidden" name="id" value="<s:property value="id"/>"/><input type="submit" value="查询"></td>
						</tr>
					</table>
					</form>
                </div>
                <div class="oper_box">
                	<table class="p_table">
                        <thead>
                            <tr>
                                <th>对象ID</th>
                            <s:if test='sonBlock.modeType=="3"'>
                                <th>类型</th>
                                <th>对象名称</th>
						   </s:if>
						   <s:if test='sonBlock.modeType== "2"||sonBlock.modeType== "1"||sonBlock.modeType== "5"||sonBlock.modeType== "6"'>
                                <th><s:if test='sonBlock.modeType=="6"'>目的地/景区</s:if><s:if test='sonBlock.modeType=="1"'>目的地</s:if><s:if test='sonBlock.modeType=="2"'>景区</s:if><s:if test='sonBlock.modeType=="5"'>酒店</s:if>ID</th>
						   </s:if>
                                <th><s:if test='sonBlock.modeType=="3"'>目的地</s:if>名称</th>
                                <th>操作</th>
                            </tr>
                        </thead>
                        <tbody>
			   				<s:iterator value="productsList">
			    				 <tr>
				                    <td><s:property value="productId"/></td>
				                    <td>
				                     <s:if test='productType=="TICKET"'>门票</s:if>
				                     <s:elseif test='productType=="HOTEL"'>酒店</s:elseif>
				                     <s:elseif test='productType=="ROUTE"'>线路</s:elseif>
				                     <s:else>其他</s:else>
				                    </td>
				                    <td><s:property value="productName"/></td>
				                    <td><s:property value="toDest"/></td>
				                    <td>
				                    	<a href="javascript:void(0)" onclick="addRecommendInfo('<s:property value="sonBlock.recommendBlockId"/>','<s:property value="productId"/>','<s:property value="productName"/>');" class="open">添加</a>
				                    </td>
				                  </tr>
			   				</s:iterator>
			   				<s:iterator value="places">
				    		 <tr>
			                    <td><input type="hidden" name="objectId" value="<s:property value="placeId"/>"/></td>
			                    <td><s:property value="placeId"/></td>
			                    <td><a href="http://www.lvmama.com/dest/<s:property value="pinYinUrl"/>" target="_blank"><s:property value="name"/></a></td>
			                    <td><a href="javascript:void(0)" onclick="addRecommendInfo('<s:property value="sonBlock.recommendBlockId"/>','<s:property value="placeId"/>','<s:property value="name"/>');" class="open">添加</a></td>
			                  </tr>
			   				</s:iterator>
			   				<s:iterator value="mudidi">
			   				 <tr>
			                    <td><input type="hidden" name="objectId" value="<s:property value="placeId"/>"/></td>
			                    <td><s:property value="placeId"/></td>
			                    <td><a href="http://www.lvmama.com/dest/<s:property value="pinYinUrl"/>" target="_blank"><s:property value="name"/></a></td>
			                    <td><a href="javascript:void(0)" onclick="addRecommendInfo('<s:property value="sonBlock.recommendBlockId"/>','<s:property value="placeId"/>','<s:property value="name"/>');" class="open">添加</a></td>
			                  </tr>
			   				</s:iterator>
			   				<s:iterator value="hotels">
			   				 <tr>
			                    <td><input type="hidden" name="objectId" value="<s:property value="placeId"/>"/></td>
			                    <td><s:property value="placeId"/></td>
			                    <td><a href="http://www.lvmama.com/dest/<s:property value="pinYinUrl"/>" target="_blank"><s:property value="name"/></a></td>
			                    <td><a href="javascript:void(0)" onclick="addRecommendInfo('<s:property value="sonBlock.recommendBlockId"/>','<s:property value="placeId"/>','<s:property value="name"/>');" class="open">添加</a></td>
			                  </tr>
			   				</s:iterator>
                        </tbody>
                    </table>
                    <div class="p_page">
                         <div class="pages"><s:property escape="false" value="@com.lvmama.comm.utils.Pagination@pagination(pagination)"/></div>
                    </div>
                </div>
            </div>
  </s:if>
  <s:else>

                            	<h5>详细信息</h5>
                                <div class="detail_box">
                                	<form class="form_hor" onsubmit="saveRecommendInfoForm('ri');return false;" id="ri">
                                    <ul class="ul_detail vip_info clearfix">
                                        <li>
                                            <label><span class="label_text">对象名称：</span><input type="text" id="title" value=""/></label>
                                        </li>
                                        <li>
                                            <label><span class="label_text">对象URL：</span><input type="text" class="p_long" id="url" value=""/></label>
                                        </li>
                                        <li>
                                            <label><span class="label_text">图片地址：</span><input type="text" id="imgUrl" value=""/></label> 
                                            <input type="button" value="上传" class="btn_upfile p_btn" onclick="uploadImg('');"/>
                                        </li>
                                        <li>
                                            <label><span class="label_text">排序值：</span><input type="text" id="seq" value="0"/></label>
                                        </li>
                                        <li>
                                            <label><span class="label_text">介绍：</span><input type="text" id="remark" class="p_long" value=""/></label>
                                        </li>
                                    </ul>
                                    <ul class="ul_detail clearfix">
                                        <li>
                                            <label><span class="label_text">备用字段1：</span><input type="text" id="bakWord1" value=""/></label>
                                        </li>
                                        <li>
                                            <label><span class="label_text">备用字段2：</span><input type="text" id="bakWord2" value=""/></label>
                                        </li>
                                        <li>
                                            <label><span class="label_text">备用字段3：</span><input type="text" id="bakWord3"  value=""/></label>
                                        </li>
                                        <li>
                                            <label><span class="label_text">备用字段4：</span><input type="text" id="bakWord4"  value=""/></label>
                                        </li>
                                        <li>
                                            <label><span class="label_text">备用字段5：</span><input type="text" id="bakWord5"  value=""/></label>
                                        </li>
                                        <li>
                                            <label><span class="label_text">备用字段6：</span><input type="text" id="bakWord6"  value=""/></label>
                                        </li>
                                        <li>
                                            <label><span class="label_text">备用字段7：</span><input type="text" id="bakWord7"  value=""/></label>
                                        </li>
                                        <li>
                                            <label><span class="label_text">备用字段8：</span><input type="text" id="bakWord8"  value=""/></label>
                                        </li>
                                        <li>
                                            <label><span class="label_text">备用字段9：</span><input type="text" id="bakWord9"  value=""/></label>
                                        </li>                                       
                                        <li>
                                            <label><span class="label_text">备用字段10：</span><input type="text" id="bakWord10"  value=""/></label>
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
  </s:else>
	

  </body>
</html>
