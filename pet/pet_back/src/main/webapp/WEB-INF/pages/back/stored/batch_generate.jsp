<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="mis" uri="/tld/lvmama-tags.tld"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<html>
	<head>
		<title>储值卡管理_卡生成管理_批次生成卡</title>
		<script type="text/javascript" src="<%=basePath%>js/base/jquery-1.4.4.min.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/base/jquery-ui-1.8.5.js"></script>
		<link rel="stylesheet" type="text/css" href="<%=basePath%>themes/cc.css" />
		<script type="text/javascript" src="<%=basePath%>js/base/remoteUrlLoad.js"></script>
		<link rel="stylesheet" type="text/css" href="<%=basePath%>themes/icon.css">
		<script type="text/javascript" src="<%=basePath%>js/base/form.js"></script>
		<script language="javascript" src="<%=basePath%>js/xiangmu.js" type="text/javascript"></script>
		<script type="text/javascript" src="<%=basePath%>js/base/lvmama_common.js"></script>
		<script language="javascript" src="<%=basePath%>js/ord/ord_item.js" type="text/javascript"></script>
		<link href="<%=basePath%>themes/base/jquery.ui.all.css" rel="stylesheet"></link>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/base/perm.js"></script>
			
		<script type="text/javascript">
			var DATE_FORMAT = /^[0-9]{4}-[0-1]?[0-9]{1}-[0-3]?[0-9]{1}$/;
			$(function() {
				$("input[name='overTime']" ).datepicker({dateFormat:'yy-mm-dd'});
				$("input[name='overTime']").bind("blur",function(){
					var overTime = $("input[name='overTime']").val();
					if(overTime != ""){
						if(!DATE_FORMAT.test(overTime)){
							alert("您填写的时期格式不正确!");
							$("input[name='overTime']").val("");
						}
					}
				})
				$("#AddBatchForm").submit(function(){
							if(!confirm("您确定要生成批次储值卡")){
								return false;
							}
							var $this=$(this);					
							var storedCardId=$(this).attr("storedCardId");
								$.ajax({ 
									type: "POST", 
									url: "<%=basePath%>stored/AddBatchGenerate.do", 
									data: $("#AddBatchForm").serialize(), 
									success: function(dt){
										var data=eval("("+dt+")");
										if(data.success){
											alert("批次储值卡生成成功!! "+data.generateBatchNo);
										}else{
											alert(data.msg);
										}
									} 
								});
						});
			});
		</script>
		<script type="text/javascript" src="<%=basePath%>js/base/jquery.datepick-zh-CN.js"></script>	
		
	</head>
	<body>

		<div id="tg_order">

			<div class="wrap">
				<div class="main2">
					<!--=========================主体内容==============================-->
					<ul class="tags">
						<li class="tags_none" id="tags_title_1" onclick='location.href="<%=basePath%>stored/goBatchList.do"'>
							储值卡批次查询
						</li> 
						<li class="tags_at" id=tags_title_2
							onclick='location.href="<%=basePath%>stored/batchGenerate.do"'>
							储值卡批次生成
						</li>
					</ul>
					<div class="table_box" id=tags_content_1>
						<form name='AddBatchForm' id="AddBatchForm" method='post'
							action='<%=basePath%>stored/AddBatchGenerate.do' onSubmit="return false;">
							<div class="mrtit3">
								<table width="100%" border="0" style="font-size: 12;">
									<tr>
										<td>
											选择面值：<s:select  list="#{'':'请选择','50':'50','100':'100','200':'200','500':'500'}" name="cardAmount" style="width:100px;"></s:select>
										</td>
										<td>
											生成数量：<s:select  list="#{'':'请选择','10':'10','50':'50','100':'100','300':'300','500':'500','1000':'1000','3000':'3000'}" name="cardCount" style="width:100px;"></s:select>
										</td>
										<td>
											过期时间：<input name="overTime" type="text"
												value="<s:date name="overTime" format="yyyy-MM-dd"/>" />
										</td>
										<td colspan="2" align="center">
										    <!--mis:checkPerm permCode="3045">-->
											    <input type='submit' name="AddBatch" value="生成" class="right-button08" />
										    <!--/mis:checkPerm-->
										</td>
									</tr>
								</table>
							</div>
							
						</form>
					</div>
					<!--=========================主体内容 end==============================-->
				</div>
				<!--main2 end-->
			</div>

			<table width="90%" border="0" align="center">
			</table>
		</div>
		
	</body>
</html>

