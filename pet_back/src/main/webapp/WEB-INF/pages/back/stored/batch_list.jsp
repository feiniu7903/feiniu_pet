<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>储值卡管理_卡生成管理_批次查询</title>
		
		<script type="text/javascript" src="<%=basePath%>js/base/jquery-1.4.4.min.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/base/remoteUrlLoad.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/base/form.js"></script>
		<script language="javascript" src="<%=basePath%>js/xiangmu.js" type="text/javascript"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/base/perm.js"></script>
		<link rel="stylesheet" href="<%=request.getContextPath()%>/themes/base/jquery.ui.all.css" />
		<link rel="stylesheet" 	href="<%=request.getContextPath()%>/themes/cc.css" />
		<script type="text/javascript" src="<%=basePath%>js/ui/jquery-ui-1.8.5.js"></script>
		<script type="text/javascript" src="<%=basePath %>js/base/lvmama_dialog.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/base/jquery.datepick-zh-CN.js"></script>	
		<script language="javascript" src="<%=basePath%>js/ord/ord.js" type="text/javascript"></script>
			
		<script type="text/javascript">
	var DATE_FORMAT = /^[0-9]{4}-[0-1]?[0-9]{1}-[0-3]?[0-9]{1}$/;
	$(function() {
		$("input[name='beginTime']" ).datepicker({dateFormat:'yy-mm-dd'});
		$("input[name='endTime']").datepicker({dateFormat:'yy-mm-dd'});
		$("input[name='beginTime']").bind("blur",function(){
			var beginTime = $("input[name='beginTime']").val();
			if(beginTime != ""){
				if(!DATE_FORMAT.test(beginTime)){
					alert("您填写的时期格式不正确!");
					$("input[name='beginTime']").val("");
				}
			}
		})
		$("input[name='endTime']").bind("blur",function(){
			var endTime = $("input[name='endTime']").val();
			if(endTime != ""){
				if(!DATE_FORMAT.test(endTime)){
					alert("您填写的时期格式不正确!");
					$("input[name='endTime']").val("");
				}
			}
		})
		
		
		$("a.batchstatus").click(function(){
					var $dlg=$("#batch_status_dialog");
					var batchId=$(this).attr("batchId");
					var $td=$("#batch_status"+batchId);
					var current=$td.attr("result");
					$dlg.dialog({
						"title":"修改批次状态",
						"width":300,
						"modal":true,
						buttons:{
							"保存":function(){
								var newVal=$dlg.find("textarea").val();
								if(newVal===current){
									alert("请填写作废原因 !!");
									return false;
								}else{
									$.post("<%=basePath%>/stored/batchCancel.do",{"batchId":batchId,"cancelMemo":newVal},function(dt){
										var data=eval("("+dt+")");
										if(data.success){
											$td.attr("result",newVal);
											$td.html("作废");
											$dlg.dialog("close");
										}else{
											alert(data.msg);
										}
									});
								}
							},
							"取消":function(){
								$dlg.dialog("close");
							}
						}
					});
				});
		
		
	});
	
		</script>
		<script type="text/javascript" src="<%=basePath%>js/base/jquery.datepick-zh-CN.js"></script>	
		<script type="text/javascript">
			var stored_url="<%=request.getContextPath()%>/stored";
		</script>
	</head>
	<body>



		<div id="tg_order">
			<div class="wrap">
				<div class="main2">
					<!--=========================主体内容==============================-->
					<ul class="tags">
						<li class="tags_at" id="tags_title_1" onclick='location.href="<%=basePath%>stored/goBatchList.do"'>
							储值卡批次查询
						</li> 
						<li class="tags_none" id=tags_title_2
							onclick='location.href="<%=basePath%>stored/batchGenerate.do"'>
							储值卡批次生成
						</li>
					</ul>
					<div class="table_box" id=tags_content_1>
						<form name='batchForm' id="batchForm" method='post'
							action='<%=basePath%>/stored/batchList.do'>
							<div class="mrtit3">
								<table width="100%" border="0" style="font-size: 12;">
									<tr>
										<td>
											批次号：
										</td>
										<td>
											<input name="batchNo" type="text" value="${batchNo}" id="batchNo"/>
										</td>
										<td>
											生成时间：
										</td>
										<td>
											<input name="beginTime" type="text"
												value="<s:date name="beginTime" format="yyyy-MM-dd"/>" id="beginTime" />
												~
											<input name="endTime" type="text"
												value="<s:date name="endTime" format="yyyy-MM-dd"/>" id="endTime" />
										</td>
										<td colspan="2" align="center">
										    <!--mis:checkPerm permCode="3046"-->
											<input type='submit' name="btnQryOrdItemList" value="查询" class="right-button08" />
											<!--/mis:checkPerm-->
										</td>
									</tr>
								</table>
							</div>
							</form>
							<table cellspacing="1" cellpadding="4" border="0"
								bgcolor="#666666" width="100%" class="newfont06"
								style="font-size: 12; text-align: center;">
								<tbody>
									<tr bgcolor="#eeeeee">
										<td width="10%">
											批次号
										</td>
										<td width="5%">
											卡数量
										</td>
										<td width="20%">
											流水号范围
										</td>
										<td width="5%">
											面值
										</td>
										<td width="14%">
											生成时间
										</td>
										<td width="14%">
											有效期至
										</td>
										<td width="6%">
											批次状态
										</td>
										<td width="8%">
											操作者
										</td>
										<td width="8%">
											操作
										</td>
									</tr>
									<s:iterator value="storedCardBatchPage.items" var="batchItem">
										<tr bgcolor="#ffffff">
											<td>${batchItem.batchNo}</td>
											<td>${batchItem.cardCount}</td>
											<td>${batchItem.beginSerialNo} ~ ${batchItem.endSerialNo}</td>
											<td>${batchItem.amountFloat}</td>
											<td>
											<s:if test="#batchItem.createTime!=null">
											  <s:date name="#batchItem.createTime" format="yyyy-MM-dd HH:mm:ss"/>
										    </s:if>
											</td>
											<td>
											<s:if test="#batchItem.overTime!=null">
											  <s:date name="#batchItem.overTime" format="yyyy-MM-dd HH:mm:ss"/>
										    </s:if>
											</td>
											<td align="center" class="batchstatus" result="${batchItem.status}" id="batch_status_${batchItem.batchId}">
										        <s:property value="#batchItem.zhStatus"/>
									        </td>
											<td>${batchItem.operator}</td>
											<td>
											    <!--mis:checkPerm permCode="3047"-->
												<a href="<%=basePath%>stored/reportBatch.do?batchNo=${batchItem.batchNo}">导出</a>
												<!--/mis:checkPerm-->   
												<a href="javascript:openWin('http://super.lvmama.com/super_back/log/viewSuperLog.zul?parentId=${batchItem.batchId}&parentType=STORED_CARD_BATCH',700,400)">查看日志</a>
												
												
												<s:if test="#batchItem.status=='NORMAL'">  
												 <!--mis:checkPerm permCode="3048"-->
												 <a href="javascript:void(0);" class="batchstatus" batchId="${batchItem.batchId}">作废</a>
												 <!--/mis:checkPerm-->
												</s:if>
											</td>
										</tr>
									</s:iterator>
								</tbody>
							</table>
						
					</div>
					<!--=========================主体内容 end==============================-->
					<table width="98%" border="0" align="center">
						<tr bgcolor="#ffffff">
							<td colspan="2">
								总条数：<s:property value="storedCardBatchPage.totalResultSize"/>
							</td>
							<td colspan="7" align="right">
								<s:property escape="false" 
									value="@com.lvmama.comm.utils.Pagination@pagination(storedCardBatchPage)"/>
							</td>
						</tr>
					</table>
					
				</div>
				<!--main2 end-->
			</div>

			<table width="90%" border="0" align="center">
				
			</table>
			
			<div id="batch_status_dialog" style="display: none">
			<div>批次作废原因</div>
				<form>
					<s:textarea cols="42" rows="10" ></s:textarea>
				</form>
		    </div>
			
			
		</div>
		
	</body>
</html>
