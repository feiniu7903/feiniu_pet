<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="mis" uri="/tld/lvmama-tags.tld"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	response.setHeader("Pragma","No-cache"); 
	response.setHeader("Cache-Control","no-cache"); 
	response.setDateHeader("Expires", 0); 
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>储值卡管理_卡日常管理</title>
		<script type="text/javascript">
	   		var path='<%=basePath%>';
		</script>
		<script type="text/javascript" src="<%=basePath%>js/base/jquery-1.4.4.min.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/base/remoteUrlLoad.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/base/form.js"></script>
		<script language="javascript" src="<%=basePath%>js/xiangmu.js" type="text/javascript"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/base/perm.js"></script>
		<link rel="stylesheet"
			href="<%=request.getContextPath()%>/themes/base/jquery.ui.all.css" />
		<link rel="stylesheet"
			href="<%=request.getContextPath()%>/themes/cc.css" />
		<script type="text/javascript" src="<%=basePath%>js/ui/jquery-ui-1.8.5.js"></script>
		<script type="text/javascript" src="<%=basePath %>js/base/lvmama_dialog.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/base/jquery.datepick-zh-CN.js"></script>	
		<script language="javascript" src="<%=basePath%>js/ord/ord.js" type="text/javascript"></script>
		<script type="text/javascript">
		var DATE_FORMAT = /^[0-9]{4}-[0-1]?[0-9]{1}-[0-3]?[0-9]{1}$/;
			$(function(){		
				$("input[name='beginOutTime']" ).datepicker({dateFormat:'yy-mm-dd'});
		        $("input[name='endOutTime']").datepicker({dateFormat:'yy-mm-dd'});
		        $("input[name='beginOverTime']" ).datepicker({dateFormat:'yy-mm-dd'});
		        $("input[name='endOverTime']").datepicker({dateFormat:'yy-mm-dd'});
		        
		        $("input[name='beginOutTime']").bind("blur",function(){
					var beginOutTime = $("input[name='beginOutTime']").val();
					if(beginOutTime != ""){
						if(!DATE_FORMAT.test(beginOutTime)){
							alert("您填写的时期格式不正确!");
							$("input[name='beginOutTime']").val("");
						}
					}
				})
				$("input[name='endOutTime']").bind("blur",function(){
					var endOutTime = $("input[name='endOutTime']").val();
					if(endOutTime != ""){
						if(!DATE_FORMAT.test(endOutTime)){
							alert("您填写的时期格式不正确!");
							$("input[name='endOutTime']").val("");
						}
					}
				})
				$("input[name='beginOverTime']").bind("blur",function(){
					var beginOverTime = $("input[name='beginOverTime']").val();
					if(beginOverTime != ""){
						if(!DATE_FORMAT.test(beginOverTime)){
							alert("您填写的时期格式不正确!");
							$("input[name='beginOverTime']").val("");
						}
					}
				})
				$("input[name='endOverTime']").bind("blur",function(){
					var endOverTime = $("input[name='endOverTime']").val();
					if(endOverTime != ""){
						if(!DATE_FORMAT.test(endOverTime)){
							alert("您填写的时期格式不正确!");
							$("input[name='endOverTime']").val("");
						}
					}
				})
				$("a.cardStatus").click(function(){
					var $dlg=$("#card_status_dialog");
					var storedCardId=$(this).attr("storedCardId");
					var $td=$("#card_status_"+storedCardId);
					var current=$td.attr("result");
					$dlg.find("option[value="+current+"]").attr("selected","selected");
					$dlg.dialog({
						"title":"修改常规状态",
						"width":300,
						"modal":true,
						buttons:{
							"保存":function(){
								var newVal=$dlg.find("select option:selected").val();
								if(newVal===current){
									alert("您没有选中要修改的新状态!!");
									return false;
								}else{
									$.post("<%=basePath%>/stored/changeStatus.do",{"scardId":storedCardId,"cardStatus":newVal},function(dt){
										var data=eval("("+dt+")");
										if(data.success){
											$td.attr("result",newVal);
											$td.html($dlg.find("select option:selected").text());
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
				
				$("a.status").click(function(){
					var $dlg=$("#status_dialog");
					var storedCardId=$(this).attr("storedCardId");
					var $td=$("#card_status_"+storedCardId);
					var current=$td.attr("result");
					
					$dlg.dialog({
						"title":"修改储值卡状态",
						"width":300,
						"modal":true,
						buttons:{
							"保存":function(){
								var newVal=$dlg.find("textarea").val();
								if(newVal===current){
									alert("请填写作废原因 !!");
									return false;
								}else{
									$.post("<%=basePath%>/stored/changeCancelStatus.do",{"scardId":storedCardId,"cancelMemo":newVal},function(dt){
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
				
				$("a.cardActiveStatus").click(function(){
					var $dlg=$("#card_active_status_dialog");
					var storedCardId=$(this).attr("storedCardId");
					var $td=$("#card_active_status_"+storedCardId);
					var current=$td.attr("result");
					$dlg.find("option[value="+current+"]").attr("selected","selected");
					$dlg.dialog({
						"title":"修改激活状态",
						"width":300,
						"modal":true,
						buttons:{
							"保存":function(){
								var newVal=$dlg.find("select option:selected").val();
								if(newVal===current){
									alert("您没有选中要修改的新状态!!");
									return false;
								}else{
									$.post("<%=basePath%>/stored/changeActiceStatus.do",{"scardId":storedCardId,"cardActiveStatus":newVal},function(dt){
										var data=eval("("+dt+")");
										if(data.success){
											$td.attr("result",newVal);
											$td.html($dlg.find("select option:selected").text());
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
				
				
				
				$("a.cardOverTime").click(function(){
					if(!confirm("您确定储值卡要延期")){
						return false;
					}
					var $this=$(this);					
					var storedCardId=$(this).attr("storedCardId");
						$.post("<%=basePath%>/stored/changeCardOver.do",{"scardId":storedCardId},function(dt){
							var data=eval("("+dt+")");
							if(data.success){								
								$("#cardOverTime_"+storedCardId).html(data.overTime);
								alert("修改成功");
							}else{
								alert(data.msg);
							}
						});
				});
			})

     //显示弹出层
   function showCardUsagelDiv(divName, cardId) {
	 document.getElementById(divName).style.display = "block";
	 document.getElementById("bg").style.display = "block";
	 //请求数据,重新载入层
	 $("#" + divName).reload({"cardId":cardId});
   }
			
			
		</script>
	</head>
	<body>
		<div id="table_box">
					<!--=========================主体内容==============================-->
					<div class="table_box"  id=tags_content_1>
						<div class="mrtit3">
							<form name='form1' method='post'
								action='<%=basePath%>/stored/cardList.do'>
								<table width="98%" border="0"  class="newfont06"
								style="font-size: 12; text-align: left;">
									<tr>
									    <td>批次号:</td>
										<td> <input name="batchNo" type="text" value="${batchNo}"/> </td>
									
									    <td>流水号:</td>
										<td> <input name="serialNo" type="text" value="${serialNo}"/> </td>
										<td> 出库时间:</td>
										<td> <input name="beginOutTime" type="text" class="date" value="<s:date name="beginOutTime" format="yyyy-MM-dd"/>" />
											~
											<input name="endOutTime"  type="text" class="date" value="<s:date name="endOutTime" format="yyyy-MM-dd"/>" />
										</td>
										<td> 过期时间:</td>
										<td><input name="beginOverTime" type="text"
												value="<s:date name="beginOverTime" format="yyyy-MM-dd"/>" id="beginOverTime" />
											~
											 <input name="endOverTime" type="text"
												value="<s:date name="endOverTime" format="yyyy-MM-dd"/>" id="endOverTime" />
										</td>
									</tr>	
								  </table>
                                 <table width="98%" border="0"  class="newfont06"
								style="font-size: 12; text-align: riight;">
									<tr>	
										<td> 常规状态:</td>
										<td>
										<s:select  list="#{'':'全部','NORMAL':'正常','CANCEL':'作废','FINISHED':'过期','FREEZE':'冻结'}" name="status" styleClass="wight:10px;"></s:select>
                                        </td>
										<td> 激活状态:</td>
										<td>
										<s:select  list="#{'':'全部','ACTIVE':'已激活','UNACTIVE':'未激活'}" name="activeStatus"></s:select>
                                          </td>
										<td> 在库状态:</td>
										<td> 
										<s:select  list="#{'':'全部','INTO_STOCK':'在库','OUT_STOCK':'出库'}" name="stockStatus"></s:select>
                                        </td><td></td>
									</tr>
									<tr>
										<td></td><td></td><td></td><td></td><td></td><td></td><td></td>
										<td>
										    <!--mis:checkPerm permCode="3060">-->
											   <input type='submit' name="btnOrdListQuery" value="查 询" class="right-button08" />
											<!--mis:checkPerm-->
											   <input type='button' value="返  回" class="right-button08" onclick="javascript:history.go(-1)"/>
										</td>
									</tr>
								</table>
							</form>
						</div>
						
						<table style="font-size: 12px" cellspacing="1" cellpadding="4"
							border="0" bgcolor="#666666" width="98%" class="newfont06">
							<tbody>
								<tr bgcolor="#eeeeee">
								    <td width="11%" align="center">批次号</td>
									<td width="11%" align="center"> 流水号 </td>
									<td width="7%" align="center">面值</td>
									<td width="7%" align="center">目前余额</td>
									<td width="13%" align="center"> 生成时间 </td>
									<td width="13%" align="center"> 有效期至 </td>
									<td width="8%" align="center"> 常规状态 </td>
									<td width="8%" align="center"> 激活状态 </td>
									<td width="8%" align="center"> 在库状态  </td>
									<td width="14%" align="center"> 操作</td>
								</tr>
								<s:iterator value="storedCardPage.items" var="card">
									<tr bgcolor="#ffffff">
									<td  align="center">${card.cardBatchNo}</td>
									<td align="center"> ${card.serialNo} </td>
									<td align="center">${card.amountFloat}</td>
									<td align="center">${card.balanceFloat}</td>
									<td align="center"> 
									    <s:if test="#card.createTime!=null">
											  <s:date name="#card.createTime" format="yyyy-MM-dd HH:mm:ss"/>
										</s:if>
									</td>
									<td align="center" id="cardOverTime_${card.storedCardId}"> 
									    <s:if test="#card.overTime!=null">
											  <s:date name="#card.overTime" format="yyyy-MM-dd HH:mm:ss"/>
										</s:if>
									</td>
									<td align="center" class="cardStatus" result="${card.status}" id="card_status_${card.storedCardId}">
										<s:property value="#card.zhStatus"/>
									</td>
									<td  align="center"  class="cardActiveStatus"  result="${card.activeStatus}" id="card_active_status_${card.storedCardId}">
									    ${card.zhActiveStatus}
									</td>
									<td align="center"> ${card.zhStockStatus}  </td>
									<td align="center">
									    <!--mis:checkPerm permCode="3061">-->
										   <a href="javascript:showCardUsagelDiv('usageDiv','${card.storedCardId}');">消费记录</a>
										<!--mis:checkPerm-->
										
										<s:if test="#card.isChangeOverTime()==true">
										   <!--mis:checkPerm permCode="3062">-->
										   <a href="#cardOverTime" class="cardOverTime" storedCardId="${card.storedCardId}">延期</a>
										   <!--mis:checkPerm-->
										</s:if>
										
										<s:if test="#card.isCancel()==true">
										 <!--mis:checkPerm permCode="3063">-->
										   <a href="javascript:void(0);" class="status" storedCardId="${card.storedCardId}">作废</a>
										<!--mis:checkPerm-->
										</s:if>
										
										<s:if test="#card.isActive()==true">
										 <!--mis:checkPerm permCode="3064">-->
										   <a href="javascript:void(0);" class="cardActiveStatus" storedCardId="${card.storedCardId}">修改激活状态</a>
										<!--mis:checkPerm-->
										</s:if>
										 <!--mis:checkPerm permCode="3065">-->
										<a href="javascript:void(0);" class="cardStatus" storedCardId="${card.storedCardId}">修改常规状态</a>
										<!--mis:checkPerm-->
										<a href="javascript:openWin('http://super.lvmama.com/super_back/log/viewSuperLog.zul?parentId=${card.storedCardId}&parentType=STORED_CARD',700,400)">查看日志</a>
									
									</td>
									</tr>
								</s:iterator>
								<tr><td colspan="17"  bgcolor="#eeeeee"> </td></tr>
							</tbody>
						</table>
					</div>
					<!--=========================主体内容 end==============================-->
			<table width="98%" border="0" align="center">
				<tr bgcolor="#ffffff">
					<td colspan="2">
						总条数：<s:property value="storedCardPage.totalResultSize"/>
					</td>
					<td colspan="7" align="right">
						<s:property escape="false" 
							value="@com.lvmama.comm.utils.Pagination@pagination(storedCardPage)"/>
					</td>
				</tr>
			</table>
			<!--wrap end-->
			<div class="orderpop" id="usageDiv" style="display: none;"
			    href="<%=basePath%>usage/showCardUsage.do">
		    </div>
		    
		    <div id="bg" class="bg" style="display: none;">
			<iframe
				style="position: absolute; width: 100%; height: 100%; _filter: alpha(opacity = 0); opacity =0; border-style: none; z-index: -1">
			</iframe>
		</div>
		<!--main2 end-->
		<div id="card_status_dialog" style="display: none">
			<div>修改当前储值卡常规状态</div>
			<form>
				常规状态:<select>
				    	<option value="NORMAL">正常</option>
				   	    <option value="FREEZE">冻结</option>
				</select>
			</form>
		</div>
		
		<div id="status_dialog" style="display: none">
			<div>储值卡作废原因</div>
			<form>
				<s:textarea cols="42" rows="10" ></s:textarea>
			</form>
		</div>
		
		<div id="card_active_status_dialog" style="display: none">
			<div>修改当前储值卡激活状态</div>
			<form>
				激活状态:<select>
					<s:iterator value="cardActiveStatusList" id="vs">
						<option value="${vs.code}">${vs.cnName}</option>
					</s:iterator>
				</select>
			</form>
		</div>
		
	</body>
</html>
