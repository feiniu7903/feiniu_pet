<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>驴妈妈供应商管理系统</title>
<link rel="stylesheet" href="http://pic.lvmama.com/styles/new_v/reset.css">
<link rel="stylesheet" href="http://pic.lvmama.com/styles/msg_ord_snspt.css">
<link rel="stylesheet" href="http://pic.lvmama.com/styles/global_backpop.css">
<link rel="stylesheet" href="http://pic.lvmama.com/styles/ebooking/base.css">
<link rel="stylesheet" href="http://pic.lvmama.com/min/index.php?f=/styles/new_v/ob_common/ui-components.css,/styles/v4/modules/tip.css,/styles/v4/modules/dialog.css,/styles/v4/modules/button.css">
<link rel="stylesheet" type="text/css" href="${basePath}/css/base/jquery.cropzoom.css" />
<link rel="stylesheet" type="text/css" href="${basePath}/css/base/jquery.ui.all.css" />
<link rel="stylesheet" type="text/css" href="${basePath}/css/base/jquery.ui.core.css" />
<link rel="stylesheet" type="text/css" href="${basePath}/css/base/jquery.ui.base.css" />
<link rel="stylesheet" type="text/css" href="${basePath}/css/base/ui-common.css"/>
<link rel="stylesheet" type="text/css" href="${basePath}/css/base/ui-components.css"/>
<link rel="stylesheet" type="text/css" href="${basePath}/css/base/panel-content.css"/>
<link rel="stylesheet" type="text/css" href="${basePath}/css/base/jquery.ui.theme.css"/>
<link rel="stylesheet" type="text/css" href="${basePath}/css/base/jquery-ui-1.8.18.custom.css"/>

<script src="${basePath }js/base/jquery-1.4.4.min.js"></script>
<script src="http://pic.lvmama.com/js/ebooking/ebooking.js"></script>
<script src="http://pic.lvmama.com/js/v4/modules/pandora-dialog.js"></script>
<script type="text/javascript" src="${basePath}js/ebooking/editEbkProdProductInfo.js"></script>
<script type="text/javascript" src="${basePath}js/base/jquery.cropzoom.js"></script>
<script type="text/javascript" src="${basePath}/js/base/jquery.validate.js"></script>
<script type="text/javascript" src="${basePath}/js/base/jquery.form.js"></script>
<script src="${basePath }js/base/jquery-ui-1.8.5.js" defer="defer"></script>
</head>
<body id="body_cpgl">
	<jsp:include page="../../common/head.jsp"></jsp:include>
	<s:include value="./subPage/navigation.jsp"></s:include>
	<!--以上是公用部分-->

	<!--订单详情-->
	<form action="${contextPath }/ebooking/product/saveEbkProdPicture.do" id="confirm" method="post" enctype="multipart/form-data">
		<input name="ebkProdProduct.ebkProdProductId" id="ebkProdProductId" value="${ebkProdProduct.ebkProdProductId}" type="hidden">
		<input name="ebkProdProduct.supplierId" id="supplierId" value="${ebkProdProduct.supplierId }" type="hidden">
		<input name="ebkProdProduct.productType" id="ebkProductViewType" value="${ebkProdProduct.productType }" type="hidden">
		<input name="ebkProductViewType" value="${ebkProdProduct.productType }" type="hidden">
		<input name="pictureType" id="pictureType"  type="hidden">
		<input name="toShowEbkProduct" value="${toShowEbkProduct }" id="toShowEbkProduct" type="hidden">
		
		<!--新增产品基础信息-->
		<div class="xzxx_box">
			<span class="fp_btn kcwh_btn_t">提交审核</span>
			<ul class="xzxx_tab">
				<li class="tab_this" id="EBK_AUDIT_TAB">产品基础信息</li>
				<li id="EBK_AUDIT_TAB_TIME_PRICE">价格/库存维护</li>
			</ul>
			<div class="xzxx_box_list" style="display: block;">
				<s:include value="./subPage/editEbkProductNavigate.jsp"></s:include>
				<ul class="xzxx_list">
					<li style="display: block;">
		            	<div class="cptp_box">
		                    <dl class="cptp_t">
		                        <dd>
		                            <p>小图（尺寸：200*100）；图片&lt;50k，最多一张</p>
		                            <span class="as-file">
			                            <s:if test="toShowEbkProduct!='SHOW_EBK_PRODUCT'">
											<span style="margin-left: 8px;margin-bottom: 8px;" class="fp_btn">上传小图</span>
		                                	<input type="file" name="iconFile" exts="png|jpg|bmp" class="as-input-file"  id="minPic" />
										</s:if>
		                            </span>
		                            
		                        </dd>
		                        <dd>
		                            <p>大图（尺寸：440*220）；每张图片&lt;1000k，4到6张</p>
		                            <span class="as-file">
		                            	<s:if test="toShowEbkProduct!='SHOW_EBK_PRODUCT'">
											<span style="margin-left: 8px;margin-bottom: 8px;" class="fp_btn" >上传大图</span>
		                                	<input type="file" name="bigFile" exts="png|jpg|bmp" class="as-input-file" id="maxPic"  />
										</s:if>
		                            </span>
		                        </dd>
		                    </dl>
		                    <div class="img_box">
							 <table width="80%" border="1" cellspacing="0" cellpadding="0" id="prod_image_list">
					          <tbody>
						          <tr style="background-color: #EEEEEE" id="image_title_tr">
						            <td>编号</td>
						            <td>图片名称</td>
						            <td>类型</td>
						            <td>图片</td>
						            <td>操作</td>
						          </tr>
						          <s:iterator value="ebkProdProduct.comPictures" var="comPicture">
							          <tr data-id="<s:property value="#comPicture.pictureId"/>" data-type="<s:property value="#comPicture.pictureObjectType"/>">
								           <td><s:property value="#comPicture.pictureId"/></td>
								            <td>
								            	<input type="text" value="<s:property value="#comPicture.pictureName"/>" readonly="readonly" >
								            	<s:if test="toShowEbkProduct!='SHOW_EBK_PRODUCT'">
								            		 <s:if test="#comPicture.pictureObjectType=='EBK_PROD_PRODUCT_BIG'">
								            			<a href='javascript:;' name='pic-name-edit-btn' >编辑名称</a>
								            		 </s:if>
												</s:if>
								            </td>
								            <td>
								            <s:if test="#comPicture.pictureObjectType=='EBK_PROD_PRODUCT_SMALL'">
								            	小图
								            </s:if>
								            <s:if test="#comPicture.pictureObjectType=='EBK_PROD_PRODUCT_BIG'">
								            	大图
								            </s:if>
								            </td>
								            <td><img src='http://pic.lvmama.com/pics/<s:property value="#comPicture.pictureUrl"/>' width="200" height="100"  currentpictureid="<s:property value="#comPicture.pictureId"/>"/></td>
								            <td>
								            	<s:if test="toShowEbkProduct!='SHOW_EBK_PRODUCT'">
								            		<span style="margin-left: 8px;margin-bottom: 8px;" class="fp_btn js_min_delete" id='<s:property value="#comPicture.pictureId"/>'>删除</span><br style="margin-bottom: 5px;">
								            		<span style="margin-left: 8px;margin-bottom: 8px;" class="fp_btn js_min_edit" id='<s:property value="#comPicture.pictureId"/>'>编辑</span><br style="margin-bottom: 5px;">
										            <span style="margin-left: 8px;margin-bottom: 8px;" class='fp_btn js_move' data='up' id='<s:property value="#comPicture.pictureId"/>'>上移</span><br style="margin-bottom: 5px;">
									                <span style="margin-left: 8px;margin-bottom: 8px;" class='fp_btn js_move' data='down' id='<s:property value="#comPicture.pictureId"/>'>下移</span>
												</s:if>
								            </td>
							          	</tr>
						          </s:iterator>
						        </tbody>
						       </table>
		                    </div>
		                </div>
		            </li>
				</ul>
			</div>
		</div>
	</form>
	<jsp:include page="../../common/footer.jsp"></jsp:include>
</body>
<script type="text/javascript">
	$(function(){
		$("#minPic").change(function(){
			//计算个数
			var length=$("#prod_image_list").find("tr[data-type='EBK_PROD_PRODUCT_SMALL']").length;
			if(length>=1){
				alert("只能上传一张小图，请先删除再上传！");
				return;
			}
			
			$('#confirm').ajaxForm({
				dataType: 'json',  
			    beforeSend: function() {
					$("#pictureType").val("ICON");
			    },
			    success: function(date) {
			    	var result = eval(date);
			        if(result.success){
			        	appendImg(result);
			        }else{
			        	var errorMessage = errorTimePriceMessage(result.message);
			        	if(undefined==errorMessage){
							errorMessage = result.message;
						}
						alert("图片上传失败 原因："+errorMessage);
			        }
			    }
			}); 

			$('#confirm').submit();

		});


		$("#maxPic").change(function(){
			//计算个数
			var length=$("#prod_image_list").find("tr[data-type='EBK_PROD_PRODUCT_BIG']").length;
			if(length>=6){
				alert("最多只能上传6张大图，请先删除再上传！");
				return;
			}
			
			$('#confirm').ajaxForm({
				dataType: 'json',  
			    beforeSend: function() {
					$("#pictureType").val("BIG");
			    },
			    success: function(date) {
			    	var result = eval(date);
			        if(result.success){
			        	appendImg(result);
			        }else{
			        	var errorMessage = errorTimePriceMessage(result.message);
			        	if(undefined==errorMessage){
							errorMessage = result.message;
						}
						alert("图片编辑失败 原因："+errorMessage);
			        }
			    }
			}); 

			$('#confirm').submit();

		});


		//追加新增图片 
		function appendImg(jsondata){
			var _html="<tr data-id='"+jsondata.fileId+"' data-type='"+jsondata.pictureObjectType+"'>";
			_html+="<td>"+jsondata.fileId+"</td>";
			_html+="<td><input type='text' value='"+jsondata.fileName+"' readonly='readonly' >";
			if(jsondata.pictureObjectType=="EBK_PROD_PRODUCT_BIG"){
				_html+="<a href='javascript:;' name='pic-name-edit-btn' >编辑名称</a>";
			}
			_html+="</td>";
			if(jsondata.pictureObjectType=="EBK_PROD_PRODUCT_SMALL"){
				_html+="<td>小图</td>";
			}
			if(jsondata.pictureObjectType=="EBK_PROD_PRODUCT_BIG"){
				_html+="<td>大图</td>";
			}
			_html+="<td><img src='http://pic.lvmama.com/pics/"+jsondata.fileUrl+"' width='200' height='100' currentpictureid='"+jsondata.fileId+"'/></td>";
			_html+="<td><span style='margin-left: 8px;margin-bottom: 8px;' class='fp_btn js_min_delete' id='"+jsondata.fileId+"'>删除</span><br>";
			_html+="<span style='margin-left: 8px;margin-bottom: 8px;' class='fp_btn js_min_edit' id='"+jsondata.fileId+"'>编辑</span><br>";
			_html+="<span style='margin-left: 8px;margin-bottom: 8px;' class='fp_btn js_move' data='up' id='"+jsondata.fileId+"'>上移</span><br>";
			_html+="<span style='margin-left: 8px;margin-bottom: 8px;' class='fp_btn js_move' data='down' id='"+jsondata.fileId+"'>下移</span>";
			_html+="</td></tr>";
			$("#prod_image_list").append(_html);
		}


		/*删除图片*/
		$('.js_min_delete').live('click',function(){ 
			var pictureId = $(this).attr("id");
			var _this=$(this);
			if('undefined'==pictureId || null==pictureId || ''==pictureId){
				alert("未找到图片值，删除失败");
				return false;
			}
			if(confirm("确定删除图片吗？")){
				$.ajax({ 
					type:"POST", 
					url:'${contextPath }/ebooking/product/deleteEbkProdPic.do', 
					data:{pictureId:$(this).attr("id"),ebkProdProductId:$("#ebkProdProductId").val()},
					async: false, 
					success:function (result) { 
						var message=eval(result); 
						if(message.success=="true" || message.success){
							$(_this).parent().parent().fadeOut("fast",function(){
								$(this).remove();
							});
						}else{
							var errorMessage = errorTimePriceMessage(result.message);
							alert("图片删除失败 原因："+errorMessage);
						}
					} 
				}); 
			}
		});			

		/*图片移动*/
		$('.js_move').live('click',function(){ 
			var pictureId = $(this).attr("id");
			var type=$(this).attr("data");
			var _this=$(this);
			if('undefined'==pictureId || null==pictureId || ''==pictureId){
				alert("未找到图片值，上移失败");
				return false;
			}
			var moveTerm="";
			if("up"==type){//向上移动
				var preTr=$(_this).parent().parent().prev("tr");
				if($(preTr).attr("id")=="image_title_tr"){//到顶了
					alert("到顶了");
					return;
				}
				var prevId=$(preTr).attr("data-id");//前面的
				var currentId=$(_this).parent().parent().attr("data-id");//当前的
				moveTerm=prevId+":"+currentId;
			}
			
			if("down"==type){//向下移动
				var nextTr=$(_this).parent().parent().next("tr");
				if(null==nextTr||"undefined"==$(nextTr).attr("data-id")||null==$(nextTr).attr("data-id")){//到底了
					alert("到底了");
					return;
				}
				var nextId=$(nextTr).attr("data-id");//后面的
				var currentId=$(_this).parent().parent().attr("data-id");//当前的
				moveTerm=nextId+":"+currentId;
			}
			
			$.ajax({ 
				type:"POST", 
				url:'${contextPath }/ebooking/product/moveEbkProdPic.do', 
				data:{moveType:type,moveTerm:moveTerm,ebkProdProductId:$("#ebkProdProductId").val()},
				async: false, 
				success:function (result) { 
					var message=eval(result); 
					if(message.success=="true" || message.success){
						if("up"==type){//向上移动
							var preTr=$(_this).parent().parent().prev("tr");
							if($(preTr).attr("id")!="image_title_tr"){
								$(preTr).before($(_this).parent().parent());
							}
						}
						if("down"==type){//向下移动
							var preTr=$(_this).parent().parent().next("tr");
							if(null!=preTr){
								$(preTr).after($(_this).parent().parent());
							}
						}
					}else{
						var errorMessage = errorTimePriceMessage(result.message);
						alert("图片移动失败 原因："+errorMessage);
					}
				} 
			}); 
		});


		//保存名称
		$("a[name='pic-name-edit-btn']").live('click',function(){
			var _this=$(this);
			if($(_this).html()=="编辑名称"){
				$(_this).html("保存名称");
				$(_this).prev("input").attr("readonly",false);
			}else{
				var pictureId=$(_this).parent().parent().attr("data-id");
				var val=$.trim($(_this).prev("input").val());
				if(val==""){
					alert("请输入名称");
					return;
				}
				$.ajax({ 
					type:"POST", 
					url:'${contextPath }/ebooking/product/saveEbkProdPicName.do', 
					data:{pictureId:pictureId,picName:val,ebkProdProductId:$("#ebkProdProductId").val()},
					async: false, 
					success:function (result) { 
						var message=eval(result);
						if(message.success=="true" || message.success){
							alert("保存成功");
							$(_this).html("编辑名称");
							$(_this).prev("input").attr("readonly",true);
						}else{
							var errorMessage = errorTimePriceMessage(result.message);
							alert("图片名称保存失败 原因："+errorMessage);
						}
					} 
				}); 
			}
		});

	});
</script>
</html>