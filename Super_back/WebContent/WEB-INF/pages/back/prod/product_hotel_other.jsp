<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>super后台——产品标签</title>
<link href="<%=basePath%>style/houtai.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<%=basePath%>js/base/jquery-1.4.4.min.js"></script>
<script type="text/javascript" src="<%=basePath%>js/base/log.js"></script>

<script type="text/javascript" src="/super_back/js/ui/jquery-ui-1.8.5.js"></script>
<script type="text/javascript" src="/super_back/js/base/jquery.datepick-zh-CN.js"></script>
<script type="text/javascript" src="/super_back/js/base/jquery.jsonSuggest.js"></script>
<script type="text/javascript" src="/super_back/js/base/jquery.showLoading.min.js"></script>
<link href="/super_back/themes/base/showLoading.css" rel="stylesheet" type="text/css" />
<link href="/super_back/themes/suggest/jquery.suggest.css" rel="stylesheet" type="text/css" />
<link href="/super_back/themes/base/jquery.ui.all.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="/super_back/js/base/jquery.jsonSuggest-2.min.js"></script>
<link href="/super_back/themes/base/jquery.jsonSuggest.css" rel="stylesheet" type="text/css" />

<script type="text/javascript">	
function CheckForm(){
	var a=$("#comPcSubjectList1 option:selected").text(); 
	var b=$("#comPcSubjectList2 option:selected").text();
	if(a==b){
	alert("请选择不同主题");
	return false;
	}
	else{
	return true;
	} 
}
</script>
<style type="text/css">
	.depart_list{width:600px}
	.depart_list span{width:120px;float:left;display:block}
</style>
</head>
 <body>
 
 <!-- hidden区域 -->
 <input type="hidden" id="productId" value="<s:property value='product.productId' />"/>
<div class="main main02">
	<div class="row1">
    	<h3 class="newTit">产品列表
    	<s:if test="product != null">
    		&nbsp;&nbsp;&nbsp;
    		<s:if test="product.productId != null">
    			产品ID:${product.productId }
    		</s:if>
    		&nbsp;&nbsp;&nbsp;
    		<s:if test="product.productName != null">
    			产品名称：${product.productName }
    		</s:if>
    	</s:if>
    	</h3>
        <jsp:include page="/WEB-INF/pages/back/prod/product_menu.jsp"></jsp:include>
   </div>

   <s:if test="modelPropertyList2!=null && modelPropertyList2.size>0"> 
   <div class="row3" >
         <h4 class="pro_tit">产品属性2 &nbsp;<span class="txt_grey">提示：产品属性主要用于搜索，请准确填写；需要的属性内容没有，请联系线下运营小组，进行添加；</span></h4>
         <s:iterator value="modelPropertyList2">
	         <s:if test="isMaintain='Y'">
		         <div class="pro_probox">
		            <table cellspacing="0" cellpadding="0" class="newInput">
		              <tbody><tr>                
		                <td width="120"><b><s:property value="getModeTypeById(firstModelId).modelName"/>&gt;<s:property value="getModeTypeById(secondModelId).modelName"/>：</b></td>
		                <td>
		                    <div class="link_sel_out">
		                        <input type="text" class="newtext1" name=""  value="空搜索为查看全部" onfocus="this.value=''" >
		                        <input type="button" class="opt_btn opt_btn_search" name="" value="搜索" firstModelId="<s:property value='firstModelId'/>", secondModelId="<s:property value='secondModelId'/>">
		                        <input type="button" class="opt_btn opt_btn_add" name="" value="增加" secondModelId="<s:property value='secondModelId'/>" isMaintain="<s:property value='getModeTypeById(secondModelId).isMaintain'/>" isMultiChoice="<s:property value='getModeTypeById(secondModelId).isMultiChoice'/>">
		                        <ul class="like_select"></ul> 
		                    </div>    
		                </td>
		                <td width="100">
		                   <span class="txt_grey"><s:if test='getModeTypeById(secondModelId).isMultiChoice=="Y"'>该属性可多选</s:if><s:elseif test='getModeTypeById(secondModelId).isMultiChoice=="2"'>该属性最多选2条</s:elseif><s:else>该属性只能有一个</s:else></span>
		                </td>
		                <td><span class="opt_btn pro_del" secondModelId="<s:property value='secondModelId'/>">删除选中的属性</span></td>
		              </tr>
		            </tbody></table>
		            <div class="pro_type_td" secondModelId="<s:property value='secondModelId'/>">
		                <font class="<s:property value='secondModelId'/>">
		                </font>
		            </div>
		          </div> <!--box-->
	          </s:if>
          </s:iterator>    
          <div class="pro_probox">
          	<input type="button" value="保存" name="" class="button" id="other2_save"> 
          </div>    
      </div>
      </s:if>
</div><!--main01 main02 end-->
 
</body>
<script type="text/javascript">
	function initDataBind(){
		var initDataStr="<s:property value='initDataStr'/>";
		if(initDataStr!=null&&initDataStr!=""){
			var initDataArray=initDataStr.split("_");
			for(var n=0;n<initDataArray.length;n++){
				var data=initDataArray[n].split(",");
				var productId=$("#productId").val();
				var secondModelId=data[0];
				var id=data[1];
				var propertyName=data[2];
				var $propertyArray = $(".pro_type_td").find("."+secondModelId);
				var htmlSrc = "<font class='font"+id+"'><input type='checkbox'  value='"+id+"' name='modelPropertys'><label>"+propertyName+"</label></font>";
				$propertyArray.append(htmlSrc);	
			}
		}
	}
	function initProperty1(){
		var initDataStr="<s:property value='initPropertyOne'/>";
		if(initDataStr!=null&&initDataStr!=""){
			var initDataArray=initDataStr.split(",");
			for(var i=0;i<initDataArray.length;i++){
				$("#prodModelProperty"+initDataArray[i]+"").attr("checked",true);
				//$("#prodModelProperty"+initDataArray[i]+"").attr("checked","checked");
			}
		}
	}
	
	function dataCheck(){
		
		var routeCateGory=$("input[name='routeCateGory']:checked").val();
		var routeStandard=$("input[name='routeStandard']:checked").val();
		if(routeCateGory==null||routeCateGory==""){
			alert("请选择线路分类!");
			return false;
		}
		if(routeStandard==null||routeStandard==""){
			alert("请选择线路标准!");
			return false;
		}
		
	   var dataWrapStr="";
	   var num=parseInt($("#typeNum").val());
	   if(num!=0){
		   for(var i=1;i<=num;i++){
			 var dataStr="";
			 var choiceFlag= $("#choiceFlag"+i+"").val();
			 if(choiceFlag==""){
				 var checkedValue=$("input[name='productModelProperty"+i+"']:checked").val(); 
				 dataStr="productModelProperty"+i+"_N_"+checkedValue+"";
			 }else{
				 var checkedValue="";
				 $("input[name='productModelProperty"+i+"']:checked").each(function(){
					 checkedValue+=$(this).val()+"*";
				 }); 
				 if(checkedValue!=null&&checkedValue!=""){
					 checkedValue=checkedValue.substring(0,checkedValue.length-1);
				 }
				 dataStr="productModelProperty"+i+"_Y_"+checkedValue+"";
			 }
			 dataWrapStr+=dataStr+",";
		   }
	   }
	   $("#dataStr").val(dataWrapStr);
	   return true;
	}
	
   $(function(){	
   		initDataBind();
   		initProperty1();
	   /**
	    * 搜索按钮事件
	    */ 
       $(".opt_btn_search").click(function(){    
    	    var $likeSelectObj = $(this).siblings(".like_select");
    	    
    	    // 若是显示则隐藏
    	    if ($likeSelectObj.css("display")=="block") {
    	    	$likeSelectObj.hide();
    	    	return;
    	    }     	    
    	    $(".like_select").hide();
    	    
    	    var fid = $(this).attr("firstModelId");
    	    var sid = $(this).attr("secondModelId");
    	    var property = $(this).siblings(".newtext1").val(); 
    	    if(property=="空搜索为查看全部")
    	    	property="";
    	    
    	    // 显示搜索内容
    	    var liValue = searchModelProperty(fid, sid, property);
    	    $likeSelectObj.empty();
    	    $likeSelectObj.html(liValue);
			$likeSelectObj.show();
	   });	
	   
	  /**
	   * 增加按钮事件
	   */
	  $(".opt_btn_add").live("click",function(){	
		   var $likeSelectObj = $(this).siblings(".like_select");
		   var id = $likeSelectObj.find(".like_select_crt").attr("pid");
		   var property = $likeSelectObj.find(".like_select_crt").html();
		   var secondModelId = $(this).attr("secondModelId");
		   var $propertyArray = $(".pro_type_td").find("."+secondModelId);
		   
		   if (property==null || property=="") {
			   alert("请选择要添加属性！");
			   return;
		   }
		   
		   var isMultiChoice = $(this).attr("isMultiChoice");
		   if (isMultiChoice == "N") {
		   		if($propertyArray.find("font").length>0){
		   			 alert("不能多选！");
					 $likeSelectObj.hide();
					 return;
		   		}
		   }else if(isMultiChoice == "2"){
			   if(secondModelId=="14"){
				   if($propertyArray.find("input").length>1){
		   			 alert("该属性最多选2条！");
					 $likeSelectObj.hide();
					 return;
		   			}
			   }
		   }
		   
		   
		   var fontId = $propertyArray.find(".font"+id);
		   if (fontId.length!=0) {
			   alert("不能重复添加，请重新选择！");
			   return;
		   }else{				   
			   var htmlSrc = "<font class='font"+id+"'><input type='checkbox' value='"+id+"' id='id"+id+"' name='modelPropertys'><label>"+property+"</label></font>"
			   $propertyArray.append(htmlSrc);
			   //$("#id"+id).attr("checked",'true');	
			   $likeSelectObj.hide(); 
		   }		     
		});
		
		/**
		 * 点击弹出框的属性事件
		 */ 
	   $(".like_select li").live("click", function(){		   
		   $(this).addClass("like_select_crt").siblings().removeClass("like_select_crt");
		 });
		
	   /**
	    * 点击删除操作
	    */ 
		$(".pro_del").live("click",function(){
			var secondModelId = $(this).attr("secondModelId");			
			var flag = true;
			$("." + secondModelId).find("input").each(function(){
				if($(this).attr("checked")) {
					flag = false;
					var pid = $(this).val();
				    $(".font" + pid).remove();	
	   			}    	
			})			
			if (flag) {
				alert("请选择要删除的属性！")
				return;
		    }
		});
	    
	    /**
	     * 搜索模块属性
	     */
	    function searchModelProperty(firstModelId, secondModelId, property) {
	    	  var liValue = "";
	    	  var propertyName="";
	    	  <s:iterator value="modelPropertyList">
	    	  	  propertyName="<s:property value='property'/>";
				  if(firstModelId=="<s:property value='firstModelId'/>" && secondModelId=="<s:property value='secondModelId'/>" && (property=="" || propertyName.lastIndexOf(property)!=-1)&& "Y"=="<s:property value='isValid'/>") {
					 liValue += "<li pid='<s:property value='id'/>'><s:property value='property'/></li> " 			  
				  }				 
		      </s:iterator>
		      return liValue;		   
	    }
	    
	    $("#other2_save").live("click",function(){
	    	var dataStr="";
	    	$("div").find(".pro_type_td").each(function(){
	    		var lineData="";
	    		var attrData=$(this).attr("secondModelId");
	    		/*lineData="*"+attrData;*/
	    		var flag=false;
	    		$(this).find("."+attrData).each(function(){
	    			$(this).find("input[type='checkbox']").each(function(){
	    				flag=true;
	    				lineData+=$(this).val()+",";
	    			});
	    		});
	    		dataStr+=lineData;
	    	});
	    	if(dataStr!=""&&dataStr!=null){
	    		dataStr=dataStr.substring(0,dataStr.length-1);
	    	}
	    	var productId=$("#productId").val();
	    	var modelProData=productId+"*"+dataStr;
	    	$.ajax({
				type: "post",
				url: "<%=basePath%>prod/saveProductOther.do",
				data:"modelProData="+modelProData+"&btnType=2",
				success: function(data){
					if(data=="OK"){
						alert("处理成功!");
						window.location.href="<%=basePath%>prod/toProductOther.do?productId="+productId;
					}
				},
				error: function(er){
					alert("与服务器交互出现错误!请稍后再试!"+er);
				}
			});
		})
	})	   
   </script>
</html>
