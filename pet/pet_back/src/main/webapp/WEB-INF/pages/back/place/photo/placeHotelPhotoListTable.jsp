<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<style type="text/css">
.TipDiv 
{ 
background:#ffffff; 
z-index:0;/*z-index很重要，它决定了Div框在页面上的叠加顺序*/ 
position:absolute;/*绝对定位，它决定了该元素可以根据top 和 left 叠在其他元素上*/ 
} 
.TipDiv img 
{ 
margin-right:0px; 
margin-left:0px; 
float:left; 
} 
</style>

<!-- 酒店图片-->
 <div >
		<table class="p_table" id="image-table-list">
			<tr>
				<th><input type="checkbox" name="chk_all" id="chk_all_photo" />全选</th>
				<th>类型</th>
				<th>图片</th>
				<th>排序</th>
				<th>操作</th>
			</tr>
			<s:iterator value="placePhotoList" var="placePhoto">
				<tr data-type="${type}" >
				 	<td><input  type="checkbox" name="chk_list" add="chk_list" value="${placePhotoId}" /> </td>
					<td>
						<s:if test='type=="LARGE"'>大图</s:if>
						<s:elseif test='type=="MIDDLE"'>中图</s:elseif>
						<s:elseif test='type=="SMALL"'>小图</s:elseif>  
						</td>
					<td>
					<img src_dz="http://pic.lvmama.com${imagesUrl}" src='http://pic.lvmama.com${imagesUrl}' width="32" height="28" alt="鼠标指上去放大">
				
					</td>
					<td><s:if test='type=="LARGE"'><input type="text" class="seq_check"   id="chk_list_${placePhotoId}" value='${seq}'/></s:if>
						<s:else>${seq}</s:else></td>
					<td><a href="javascript:changePhoto('${placePhotoId}');" id="imageChangeId"  >替换上传</a>
						<a href="javascript:reomovePhoto('${placePhoto.placeId}','${placePhotoId}');return false;" onclick="reomovePhoto('${placePhoto.placeId}','${placePhotoId}');return false;">删除</a></td>
				</tr>
			</s:iterator>
			
		</table>			
	</div>	
		
		 <script type="text/javascript">
						$(function(){
							var reg = new RegExp("^((-[0-9]+)|([0-9])*)$");
							$("#chk_all_photo").click(function(){
								$("input[add='chk_list']").attr("checked",$(this).attr("checked"));
							});
							$("input[add='chk_list']").each(function () {
						        $(this).click(function () {
						            if ($("input[add='chk_list']:checked").length == $("input[add='chk_list']").length) {
						                $("#chk_all_photo").attr("checked", "checked");
						            }
						            else $("#chk_all_photo").removeAttr("checked");
						        });
						
						    });
							$(".p_table .seq_check").change(function(){
								$(this).parent("td").siblings("td").find("input[type=checkbox]").attr("checked","checked");
								$(this).parent("td").parent("tr").css({"background":"#f5f5f5"});
							});	
						 	$(".p_table .seq_check").blur(function(){
								if(!reg.test(this.value)){
									alert("排序值请输入数字!");
									this.focus();
								}
							}); 				
					
					    
					    	//鼠标经过 
					    	$("img").mousemove(function(e){ 
						    	$(".TipDiv").remove();//若页面上有该元素，则移除该元素...0 
						    	var x=e.clientX - 35;//获取鼠标的x轴坐标 
						    	var y=e.clientY - 35;//获取鼠标的y轴坐标 						    	
						    	var imgs=this.src; 							    	
						    	popDiv(imgs,x,y); 						    	
						    });
						    //标题鼠标离开 
					    	$("img").mouseout(function(){ 
					    	   $(".TipDiv").remove(); 
					    	 });						    
						   
						});
						
						//随鼠标移动
				    	function popDiv(face,xx,yy) 
				    	{ 				    	
							var str=""; 
					    	str+="<div class='TipDiv'>"; 
					    	str+="<img alt='大图' src='"+face+"'/>"; 				    	
					    	str+="</div>"; 					    	
					    	$('#image-table-list').append(str);//在页面上追加该元素，样式如上已经写好 
					    	$(".TipDiv").css({"top":yy+"px","left":xx+"px"});//设置该元素出现的位置				    	
					    	$('.TipDiv').show();
				    	} 

		</script>

</html>