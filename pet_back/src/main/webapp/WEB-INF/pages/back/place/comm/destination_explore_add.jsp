<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="mis" uri="/tld/lvmama-tags.tld"%>
<s:set var="basePath"><%=request.getContextPath()%></s:set>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>目的地探索</title>
<s:include value="/WEB-INF/pages/pub/jquery.jsp"/>
<script type="text/javascript" src="${basePath}/js/base/jquery.form.js"></script>
<script type="text/javascript" src="${basePath}/js/place/place.js"></script>
<script charset="utf-8" src="${basePath}/kindeditor-4.0.2/kindeditor-min.js"></script>
<script charset="utf-8" src="${basePath}/kindeditor-4.0.2/lang/zh_CN.js"></script>
<script type="text/javascript">
var editor;
$(function(){
	   KindEditor.lang({
           zileibie : '子类别'
   });
    editor = KindEditor.create('#desc_id',{
        resizeType : 1,
        width:'800px',
        filterMode : true,
        items : [
                 'image',
             ],
        uploadJson:'/pet_back/upload/uploadImg.do'
    });
});

function checkDescInfoForm(){

	if(editor.html()==""){
        alert("信息不能为空");
        return false;
    }
    $("#destinationExplore").val(editor.html());
		
	var checkDescInfoFormOptions = {
			url:"${basePath}/place/doPlaceUpdate.do",
			dataType:"",
			type : "POST", 
			success:function(data){ 
				if(data== "success") {
					alert("操作成功!"); 
					popClose();
				} else { 
					alert("操作失败，请稍后再试!"); 
				} 
			}, 
			error:function(){ 
				alert("出现错误"); 
			} 
		};
	$('#checkDescInfoForm').ajaxSubmit(checkDescInfoFormOptions);
}
</script>
</head>
<body>
<!--搜索-->
	<form action="${basePath}/place/doPlaceUpdate.do" id="checkDescInfoForm" method="post" >
	<input name="place.placeId" type="hidden" value="${place.placeId}"/>
	<input name="place.stage" type="hidden" value="${place.stage}"/>
	<input name="oldPlaceName" value="${place.name}"  type="hidden"/>
	<input name="place.name" value="${place.name}" type="hidden"/>
	<input name="stage" type="hidden" value="${place.stage}"/>
	<input name="placeId" type="hidden" value="${place.placeId}"/>
	<input id="destinationExplore" name="place.destinationExplore" type="hidden" value=""/>
	<table class="p_table">
     <tr>
        <td class="p_label"  width="15%" >描述信息：</td>
        <td>
            	<textarea id="desc_id" cols="70"  style="width: 800px;height: 450px;">
    			      <s:if test="place.destinationExplore==null">
    			      	
		        		<h4>目的地探索</h4>
	                    <ul class="ul-imgtext">
	                        <li>
	                            <img src="//placehold.it/220x128" width="220" height="128" alt="某景点图片" />
	                            <h6>迪士尼贴心服务</h6>
	                            <p>殷切贴心的景区服务，特别为您安排了米老鼠早晨传呼、睡前电视播放精彩迪士尼故事等等，让您享受与众不同的假期。</p>
	                        </li>
	                        <li>
	                            <img src="//placehold.it/220x128" width="220" height="128" alt="某景点图片" />
	                            <h6>迪士尼贴心服务</h6>
	                            <p>殷切贴心的景区服务，特别为您安排了米老鼠早晨传呼、睡前电视播放精彩迪士尼故事等等，让您享受与众不同的假期。</p>
	                        </li>
	                        <li>
	                            <img src="//placehold.it/220x128" width="220" height="128" alt="某景点图片" />
	                            <h6>迪士尼贴心服务</h6>
	                            <p>殷切贴心的景区服务，特别为您安排了米老鼠早晨传呼、睡前电视播放精彩迪士尼故事等等，让您享受与众不同的假期。</p>
	                        </li>
	                    </ul>
		        	</s:if>    	
	            <s:property value="place.destinationExplore"/>
            	</textarea>
        </td>
      </tr>
    </table>
   <p class="tc mt10">
   <input type="button" id="btn_ok" class="btn btn-small w3" onclick="javascript:checkDescInfoForm();" value="提交" />
    </p>
    </form>
</body>
</html>