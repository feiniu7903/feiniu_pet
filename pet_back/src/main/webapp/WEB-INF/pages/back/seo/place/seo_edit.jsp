<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="mis" uri="/tld/lvmama-tags.tld"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel="stylesheet" type="text/css" href="${basePath}css/ui-common.css"></link>
<link rel="stylesheet" type="text/css" href="${basePath}css/ui-components.css"></link>
<link rel="stylesheet" type="text/css" href="${basePath}css/panel-content.css"></link>
<title>seo-友情链接-编辑</title>
<script src="${basePath}/js/base/jquery.form.js"></script>
</head>       
 <body>
    <form  id="editSeoLinksForm">
        <input type="hidden" name="editSeoLinks.placeId"  value="${editSeoLinks.placeId}"/>
        <input type="hidden" name="editSeoLinks.seoLinksId" id="seoId"  value="${editSeoLinks.seoLinksId}"/>
        <input type="hidden" name="place.stage"   value="${place.stage}"/>
        <input type="hidden" name="editSeoLinks.location"   value="${editSeoLinks.location}"/>
        <table class="p_table">
            <tr>
                 <td>目的地：<span>${editSeoLinks.placeName }</span></td> 
            </tr>
            <tr>
                <td><b></b>目标位置： <span>${editSeoLinks.location}</span></td>
            </tr>
            <tr>
                <td><b>*</b>关键字：</td>
            </tr>
            <tr>
                <td><s:textarea id="linkName" name="editSeoLinks.linkName" maxLength="100" rows="2" cssStyle="width:300px"/></td>
            </tr>
            <tr>
                <td><b>*</b>链接地址：</td>
            </tr>
            <tr>
                <td><s:textarea id="linkUrls" name="editSeoLinks.linkUrl" maxLength="100" rows="2" cssStyle="width:300px"/></td>
            </tr>
            <tr>
                <td>备注：</td>
            </tr>
            <tr>
                <td><s:textarea name="editSeoLinks.remark"  maxLength="100" rows="2" cssStyle="width:300px"/></td>
            </tr>
            <tr>
                <td align="right"><input class="btn btn-small w5" type="button" onclick="return submitForm();" value="确认" /></td>
            </tr>
        </table>
    </form>
      <script type="text/javascript">
    function submitForm(){
           if($("#linkName").val()==""){
               alert("关键字不能为空");
               return false;
           }
           
           if($("#linkUrls").val()==""){
               alert("链接地址不能为空");
               return false;
           }
    	
        var placeName=$("input[name='seoLinks.placeName']").val();
        var linkUrl=$("input[name='seoLinks.linkUrl']").val();
        var action="${basePath}/seoLinks/doSeoPlaceEdits.do";
        var options = { 
                url:action,
                type : "POST", 
                success:function(data){ 
                    if(data=="true") {
                        alert("操作成功!"); 
                        popClose();
                        reQuerySeoListTable("&seoLinks.placeName="+placeName+"&seoLinks.linkUrl="+linkUrl);
                    } else { 
                        alert("修改失败"); 
                    } 
                }, 
                error:function(){ 
                    alert("出现错误"); 
                } 
            };
            $('#editSeoLinksForm').ajaxSubmit(options);
        return false;
    }
    
    </script>
</body>
</html>