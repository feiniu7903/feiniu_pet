<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="mis" uri="/tld/lvmama-tags.tld"%>
<s:set var="basePath"><%=request.getContextPath()%></s:set>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>简介添加</title>
<s:include value="/WEB-INF/pages/pub/jquery.jsp"/>
<script type="text/javascript" src="${basePath}/js/base/jquery.form.js"></script>
<script type="text/javascript" src="${basePath}/js/place/place.js"></script>
<script charset="utf-8" src="${basePath}/kindeditor-4.0.2/kindeditor-min.js"></script>
<script charset="utf-8" src="${basePath}/kindeditor-4.0.2/lang/zh_CN.js"></script>
<script type="text/javascript" src="/super_back/js/prod/sensitive_word.js"></script>
<script type="text/javascript">
var editor;
$(function(){
	   KindEditor.lang({
           zileibie : '子类别',
	   	   'formatblock.formatBlock' : {
	   			h6 : '小标题',
	   			h4 : '大标题',
	   		},
   });
    editor = KindEditor.create('#desc_id',{
        resizeType : 1,
        width:'800px',
        filterMode : true,
        items : [
                 'undo', 'redo','|',
                 'insertunorderedlist',
                 'formatblock', 'forecolor', 'bold',
                 'link', 'unlink', '|', 'image','table', 'fullscreen', 'zileibie',
             ],
        uploadJson:'/pet_back/upload/uploadImg.do'
    });
});

function checkDescInfoForm(){

	if(editor.html()==""){
        alert("介绍信息不能为空");
        return false;
    }
    $("#description").val(editor.html());
    $("#desc_id").removeClass("sensitiveVad");
    var sensitiveValidator=new SensitiveWordValidator($('#checkDescInfoForm'), true);
	if(!sensitiveValidator.validate()){
		return;
	}
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
	<form action="${basePath}/place/doPlaceUpdate.do" id="checkDescInfoForm" method="post" class="mySensitiveForm">
	<input name="place.placeId" type="hidden" value="${place.placeId}"/>
	<input name="place.stage" type="hidden" value="${place.stage}"/>
	<input name="oldPlaceName" value="${place.name}"  type="hidden"/>
	<input name="place.name" value="${place.name}" type="hidden"/>
	<input name="stage" type="hidden" value="${place.stage}"/>
	<input name="placeId" type="hidden" value="${place.placeId}"/>
	<table class="p_table">
     <tr>
        <td class="p_label"  width="15%" >描述信息：</td>
        <td>
            	<textarea id="desc_id" cols="70"  style="width: 800px;height: 450px;" name="desc_id" class="sensitiveVad">
    			      <s:if test="place.description==null">
		        		 <h4>您需要知道的“上海长风海洋世界”</h4>
		                    <ul class="ul">
		                        <li>最能代表当今游乐技术的先进性</li>
		                        <li>充分发挥华侨城在大型演艺方面的特色和优势</li>
		                        <li>突出中西合璧的多元文化体验</li>
		                        <li>突出环境生态和园林绿化</li>
		                        <li>利用丰富的水资源开展多层次的水上娱乐项目</li>
		                        <li>多个大型室内场馆使其成为全天候的旅游场所</li>
		                    </ul>
		                    <h4>上海长风海洋世界简介</h4>
		                    <p>长风海洋世界坐落在风景优美的长风公园内，与杜莎夫人蜡像馆同样，它是隶属于莫林集团旗下，集大型海洋动物表演与水族馆鱼类展览为一体的综合海洋主题公园。长风海洋世界的极地白鲸表演馆是中国内地最大，华东地区首家拥有白鲸的海洋动物表演馆，可同时容纳2000人。馆内除了小白鲸，还有加州海狮，海豚等海洋哺乳动物精灵的精彩表演，游客能亲身感受人和动物和谐相处的惊喜。</p>
		        	</s:if>    	
	            <s:property value="place.description"/>
            	</textarea>
            	<input id="description" name="place.description" type="hidden" value="" class="sensitiveVad"/>
        </td>
      </tr>
    </table>
   <p class="tc mt10">
   <input type="button" id="btn_ok" class="btn btn-small w3" onclick="javascript:checkDescInfoForm();" value="提交" />
    </p>
    </form>
</body>
</html>