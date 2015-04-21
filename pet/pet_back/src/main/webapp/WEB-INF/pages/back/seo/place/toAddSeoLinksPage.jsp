<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="mis" uri="/tld/lvmama-tags.tld"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>seo-友情链接-增加</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel="stylesheet" type="text/css" href="${basePath}css/ui-common.css"></link>
<link rel="stylesheet" type="text/css" href="${basePath}css/ui-components.css"></link>
<link rel="stylesheet" type="text/css" href="${basePath}css/panel-content.css"></link>
<s:include value="/WEB-INF/pages/pub/jquery.jsp"/>
<script src="${basePath}/js/base/jquery.form.js"></script>
<script src="${basePath}/js/base/jquery.jsonSuggest-2.min.js"></script>
</head>       
 <body>
    <form   id="seoForm">
         <input type="hidden" name="place.stage" id="stage" value="<s:property value='place.stage'/>"/>
        <table class="p_table">
            <tr>
                <td><s:if test="place.stage==1">目的地:</s:if><s:elseif test="place.stage==2">景区:</s:elseif><s:else >酒店:</s:else></td></tr><tr>
                <td> 
                <input type="text" name="search" id="searchPlace" />
                <input type="hidden" name="seoLinks.placeId" id="comPlaceId"  />
                <input type="hidden" name="seoLinks.placeName" id="comPlaceName"  /></td>
             </tr>
            <tr>
                <td><b>*</b>目标位置：</td>
            </tr>
            <tr>
                <td><s:select id="location" name="seoLinks.location" list="seoLinksIndexList" listKey="code" listValue="name"></s:select></td>
            </tr>
            <tr>
                <td><b>*</b>关键字：</td>
            </tr>
            <tr>
                <td><s:textarea id="linkName" name="seoLinks.linkName" maxLength="100" rows="2" cssStyle="width:300px"/></td>
            </tr>
            <tr>
                <td><b>*</b>链接地址：</td>
            </tr>
            <tr>
                <td><s:textarea id="linkUrls" name="seoLinks.linkUrl" maxLength="100" rows="2" cssStyle="width:300px"/></td>
            </tr>
            <tr>
                <td>备注：</td>
            </tr>
            <tr>
                <td><s:textarea name="seoLinks.remark"  maxLength="100" rows="2" cssStyle="width:300px"/></td>
            </tr>
            <tr>
                <td align="right"><input class="btn btn-small w5" type="button"  value="确认"  onclick="return submitForm();"/></td>
            </tr>
        </table>
    </form>
    
    <script type="text/javascript">
    $(function(){
        $("#searchPlace").jsonSuggest({
            url:"${basePath}/seoLinks/place/searchPlace.do?stage="+$("#stage").val(),
            maxResults: 20,
            minCharacters:1,
            onSelect:function(item){
                $("#comPlaceId").val(item.id);
                $("#comPlaceName").val(item.text);
            }
        });
    });
    
    function submitForm(){
    	var strStage="请从下拉列表里选中您需要的";
    	if($("#comPlaceId").val()==""){
    		if('${place.stage}'==1){
    			strStage+="目的地";
    		}else if('${place.stage}'==2){
    			strStage+="景点";
    		}else{
    			strStage+="酒店";
    		}
    		alert("亲 "+strStage);
    		return false;
    	}
    	
    	if($("#location").val()==""){
    		alert("目标位置不能为空");
    		return false;
    	}
    	
    	if($("#linkName").val()==""){
    		alert("关键字不能为空");
    		return false;
    	}
    	
    	if($("#linkUrls").val()==""){
    		alert("链接地址不能为空");
    		return false;
    	}
    	
    	var placeName = $("#comPlaceName").val();
     	var action="${basePath}//seoLinks/saveSeoLinks.do";
        var options = { 
                url:action,
                type : "POST", 
                success:function(data){ 
                    if(data=="true") {
                        alert("操作成功!"); 
                        popClose();
                        reQuerySeoListTable("&seoLinks.placeName="+placeName);
                    } else { 
                        alert("添加失败"); 
                    } 
                }, 
                error:function(){ 
                    alert("出现错误"); 
                } 
            };
            $('#seoForm').ajaxSubmit(options);
        return false;
    }
    
    </script>
</body>
</html>