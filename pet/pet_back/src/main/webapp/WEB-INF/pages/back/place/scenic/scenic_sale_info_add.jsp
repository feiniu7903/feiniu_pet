<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="mis" uri="/tld/lvmama-tags.tld"%>
<s:set var="basePath"><%=request.getContextPath()%></s:set>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>销售信息添加</title>
<s:include value="/WEB-INF/pages/pub/jquery.jsp"/>
<script src="${basePath}/js/base/jquery.form.js"></script>
<script type="text/javascript" src="${basePath}/js/place/place.js"></script>
<script charset="utf-8" src="${basePath}/kindeditor-4.0.2/kindeditor-min.js"></script>
<script charset="utf-8" src="${basePath}/kindeditor-4.0.2/lang/zh_CN.js"></script>
<script type="text/javascript">
var editor;
$(function(){
		 KindEditor.lang({
		   	   'formatblock.formatBlock' : {
		   			h5 : '标题 ',
		   	},
	   });
	
	editor = KindEditor.create('#desc_id',{
    	resizeType : 1,
    	newlineTag:'br',
    	height:'450px',
    	width:'400px',
    	filterMode : true,
    	items:['forecolor','formatblock'], 
    	uploadJson:'/pet_back/upload/uploadImg.do'
    });
});

	function checkSaleInfoForm(){
	/*
		if($("#place_feature").val()==""){
			alert("景点特色不能为空");
			$("#place_feature").select();
			return false;
		}
		if($("#place_orderNotice").val()==""){
			alert("预订须知不能为空");
			$("#place_orderNotice").select();
			return false;
		}
		if($("#place_importantTips").val()==""){
			alert("重要提示不能为空");
			$("#place_importantTips").select();
			return false;
		}
		*/
		
		var l = 0; var a = editor.html().split("");
		 for (var i=0;i<a.length;i++) {  
			 if (a[i].charCodeAt(0)<299) {
				 l++;  
			} else {
				l+=2;
			} 
		}
		if(l>2000){
			
			alert("超过"+(l-2000)+"个字节！");
			return false;
		}
		$("#orderNotice").val(editor.html());
		
		var options = { 
				url:"${basePath}/place/doPlaceUpdate.do",
				dataType:"",
				type : "POST", 
				success:function(data){ 
					if(data== "success") {
						alert("操作成功!"); 
						$("span.ui-icon-closethick").click();
					} else { 
						alert("操作失败，请稍后再试!"); 
					} 
				}, 
				error:function(){ 
					alert("出现错误"); 
				} 
			};
			$('#saleInfoform1').ajaxSubmit(options);
		
	}
</script>
<style type="text/css">
#font-css{
	font-family:Arial, Helvetica, sans-serif;
	font-size:12px;
}
</style>
</head>
<body>
<!--搜索-->
	<form action="${basePath}/place/doPlaceUpdate.do" id="saleInfoform1" method="post" >
	<s:hidden name="place.placeId"></s:hidden>
	<input name="place.stage" type="hidden" value="${place.stage}"/>
	<input name="place.name"  type="hidden" value="${place.name }"/>
	<input name="oldPlaceName" type="hidden" value="${place.name }"/>
	<input id="orderNotice" name="place.orderNotice" type="hidden" value=""/>
	<s:hidden name="stage" value="place.stage"></s:hidden>
	<s:hidden name="placeId"></s:hidden>
	<table class="p_table">
     <tr style="display: none">
        <td class="p_label"  width="15%" ><strong class="jianjie">公告：</strong></td>
        <td>
        	<s:textarea name="place.feature" theme="simple" style="width:390px" onKeyUp="textCounter('features', 2000,'features_limitTips')" onkeypress="textCounter('features', 2000,'features_limitTips')"  id="features" cols="250" rows="4"></s:textarea>
        	<div id="features_limitTips"></div>
        </td>
         <td>
        <div id="font-css" >
            公告：（公告录入内容请参照以下模板）<br>
	1.景区临时闭园信息<br>
	2.由于不可抗力的天气因素如台风、雨雪、洪水等造成景区无法游玩无法预订需提醒游客更改游玩目的地或等待景区恢复开园时再来游玩的温馨提示<br>
	3.景区临时停电停水等信息（尤其温泉等带房间类型的景区）<br>
	4.景区特殊活动政策以及活动时间，同时对于特殊人群预订造成的影响，如在此期间无法在驴妈妈上订购的需建议游客自行至现场购买等<br>
        </div>
        </td>
      </tr>
      <tr>
        <td  class="p_label" ><strong class="jianjie">预订须知 ：
        </strong></td>
        <td>
        <textarea id="desc_id" cols="70" >
        <s:if test="place.orderNotice==null">
	        <div class="dcontent">
	            <div class="dactive">
	                <h5>预订限制</h5>
	                <p>
	                    1. 该景区网络（包括其他商家平台在内）预订，每个手机号每天至多限订限取5张门票，请勿超限预订。<br>
	                    2. 本产品须提前至少1天预订并完成支付。<br>
	                    3. 由于预订本产品2小时后才能取票，所以需至少提前两小时预订。
	                </p>
	                <h5>优惠人群</h5>
	                <p>
	                    1. 身高低于1.2米(不含1.2米)的儿童免票。<br>
	                    2. 身高1.2米至1.5米(不含1.5米)的儿童购半价票入园。<br>
	                    3. 身高1.5米以上(含1.5米)的儿童购全价票入园。<br>
	                    4. 年龄在65周岁至69周岁（含65周岁)的老人凭有效证件或上海市老年优待证购半价票入园。<br>
	                    5. 年龄在70周岁以上(含70周岁)的老人凭有效证件或上海市老年优待证免费入园。
	                </p>
	                <h5>温馨提示</h5>
	                <p>
	                    1. 本产品是“上海欢乐谷”与“驴妈妈旅游网”面向散客推出的上海欢乐谷优惠电子门票。<br>
	                    2. 在本平台预订并完成支付后，我们将以短信的形式把“二维码”电子门票发送到您指定的手机。<br>
	                    3. 凭电子门票至上海欢乐谷游玩时，请到电子票专用通道直接刷码通关，无须排队换票，更无须组团。<br>
	                    4. 上海欢乐谷共有多个入园通道，右侧第3个为电子门票专用通道。<br>
	                    5. 用户如需发票可致电驴妈妈客服1010 6060索要。<br>
	                    6. 为防止恶意倒票，现我司规定二维码短信持有人必须先刷码，并与其他人一同入园方可，否则无效。<br>
	                    7. 门票仅限当日使用有效。<br>
	                    8. 本产品为夜场门票，1.2米以下儿童免票，需17:30以后方可入园，园区内大型游乐项目夜场关闭不能游玩。
	                </p>
	            </div>
	        </div>
        </s:if>
        <s:property value="place.orderNotice"/> 
        </textarea>
        	<div id="orderNotice_limitTips"></div>
        </td>

      </tr>
       <!--tr>
        <td  class="p_label" ><strong class="jianjie">行前须知：</strong></td>
        <td>
        	<s:textarea name="place.importantTips" theme="simple" style="width:350px;" onKeyUp="textCounter('importantTips', 2000,'importantTips_limitTips')" onkeypress="textCounter('importantTips', 2000,'importantTips_limitTips')"  id="importantTips"  cols="250" rows="4"></s:textarea>
        	<div id="importantTips_limitTips"></div>
        </td>
        <td>
        	<div id="font-css">
           行前须知：（交通信息）<br>
1.自驾线路：（填写热门出发地至该景区的自驾线路）<br>
2.公交线路：（填写景区当地火车站、机场等标志性地标至景区的公共交通出行方式及路线）<br>
            </div>
        </td>
      </tr-->
    </table>
   <p class="tc mt10">
   <input type="button" id="btn_ok" class="btn btn-small w3" onclick="return checkSaleInfoForm();" value="提交" />
   </p>		
    </form>
</body>
</html>