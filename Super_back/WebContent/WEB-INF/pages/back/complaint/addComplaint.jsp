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
<title>新增投诉</title>
<link rel="stylesheet" type="text/css" href="<%=basePath%>themes/base/jquery.ui.all.css" />
<link rel="stylesheet" type="text/css" href="<%=basePath%>style/ui-components.css" />
<link rel="stylesheet" type="text/css" href="<%=basePath%>style/ui-common.css"></link>
<link rel="stylesheet" type="text/css" href="<%=basePath%>style/panel-content.css"></link>
<script type="text/javascript" src="<%=basePath%>/js/base/jquery-1.4.4.min.js"></script>
<script type="text/javascript" src="<%=basePath%>js/base/jquery-ui-1.8.5.js" ></script>
<script type="text/javascript" src="<%=basePath%>js/base/log.js" ></script>
<script type="text/javascript" src="/pet_back/js/base/dialog.js"></script>
<script type="text/javascript" src="<%=basePath%>/js/complaint/complaint.js"></script>
<script type="text/javascript">
	function saveComplaint() {
		if(!checkComplaint()){
			return false;
		}
		$.post("order/complaint/saveComplaint.do", 
				$("#addComplaint_form").serialize(), 
				function(data) {
				if ("SUCCESS" == data) {
					alert("操作成功");
					if('${flag}'=='true'){
						parent.location.reload(parent.location.href);
					}else{
						window.close();
						window.opener.location.reload();
					}
				}else if("FAILED"==data){
					alert("操作失败");
				} else {
					alert(data);
				}
			});
	}
	function checkdetailsComplaintLength(){
		$("#detailsComplaintCount").text(500-$("#detailsComplaint").val().length);
		$("#detailsComplaintCount1").text(500-$("#detailsComplaint").val().length);
	}
	$(function(){
		checkdetailsComplaintLength();
		$("#detailsComplaint").keyup(function(){
			$("#detailsComplaintCount").text(500-$("#detailsComplaint").val().length);
			$("#detailsComplaintCount1").text(500-$("#detailsComplaint").val().length);
		});
	});
</script>
    <style type="text/css">
        .startCls{
            color: red;
        }
    </style>
</head>
<body>
	<div id="showAddComplaintDiv" class="p_box">
		<form id="addComplaint_form" action="" method="post">
			<input type="hidden" id="flag" name="flag" value="${flag }" />
			<s:if test="#request.sysCode!=null">
				<input type="hidden" name="sysCode" value="${sysCode}" />
			</s:if>
			<table class="p_table form-inline" width="100%">
				<tr>
					<td class="p_label">订单号:</td>
					<td>
						<s:if test="orderId!=null">
							<input type="text" id="orderId" name="orderId" value="${orderId }" readonly="readonly" />
						</s:if>
						<s:else>
							<input type="text" id="orderId" name="orderId" value="${orderId }" />
						</s:else>
					</td>
					<td class="p_label"><span class="startCls">*</span>性别:</td>
					<td>
						<input type="radio" id="genderM" name="gender" value="MAN"
							<s:if test='orderDetail.gender=="M"'>checked</s:if> />男
						<input type="radio" id="genderW" name="gender" value="WOMAN"
							<s:if test='orderDetail.gender=="F"'>checked</s:if> />女
					</td>
				</tr>
				<tr>
					<td class="p_label"><span class="startCls">*</span>投诉会员名:</td>
					<td>
						<s:if test="orderDetail.userName!=null">
							<input type="text" id="complaintName" name="complaintName" value="${orderDetail.userName }" readonly="readonly" />
						</s:if>
						<s:else>
							<input type="text" id="complaintName" name="complaintName" value="${userName }" />
						</s:else>
					</td>
					<td class="p_label"><span class="startCls">*</span>联系电话:</td>
					<td>
						<input type="text" id="contactMobile" name="contactMobile" value="${orderDetail.mobileNumber }" />
					</td>
				</tr>
				<tr>
					<td class="p_label"><span class="startCls">*</span>投诉联系人:</td>
					<td>
						<input type="text" id="contact" name="contact" value="${orderDetail.realName }" />
					</td>
					<td class="p_label"><span class="startCls">*</span>投诉来源:</td>
					<td>
						<select name="source" id="source">
                            <option value="">请选择</option>
                            <option value="VISITORS_CALL" >游客来电</option>
                            <option value="">--------</option>
                            <option value="GOVERNMENT" >政府</option>
                            <option value="MEDIA" >媒体</option>
                            <option value="MICROBLOGGING" >微博</option>
                            <option value="">--------</option>
                            <option value="INTERNAL_FEEDBACK" >内部反馈</option>
                            <option value="SUPPLIER" >供应商</option>
                            <option value="">--------</option>
                            <option value="TOURISM_AUTHORITY" >旅监</option>
                            <option value="CONSUMERS_ASSOCIATION" >消协</option>
                            <option value="TRADE_AND_INDUSTRY_BUREAU" >工商</option>
                            <option value="LAW_ENFORCEMENT" >执法大队</option>
                            <option value="">--------</option>
                            <option value="OTHER" >其他</option>
                        </select>
					</td>
				</tr>
				<tr>
					<td class="p_label"><span class="startCls">*</span>投诉人身份:</td>
					<td>
						<s:select list="@com.lvmama.comm.vo.Constant$NC_COMPLAINT_IDENTITY@values()"
                        	listKey="code" listValue="cnName" headerKey="" headerValue="请选择"
                        	name="identity" value="identity"/>
					</td>
					<td class="p_label"><span class="startCls">*</span>是否紧急:</td>
					<td>
						<s:select list="#{'':'请选择','YES':'是','NO':'否'}" name="urgent"/>
					</td>
				</tr>
				<tr>
					<td class="p_label"><span class="startCls">*</span>回复时效:</td>
					<td>
						<s:select list="@com.lvmama.comm.vo.Constant$NC_COMPLAINT_REPLY_AGING@values()"
                        		listKey="code" listValue="cnName" headerKey="" headerValue="请选择"
                        		name="replyAging" value="replyAging"/>
					</td>
					<td class="p_label">关联订单:</td>
					<td>
						<input type="text" id="relatedOrder" name="relatedOrder" />
						多个时用英文“,”分隔
					</td>
				</tr>
				<tr>
					<td class="p_label">邮箱:</td>
					<td>
						<input type="text" id="email" name="email" />
					</td>
					<s:if test="#request.sysCode==null">
							<td class="p_label">
                        	<span class="startCls">*</span>业务系统:
		                    </td>
							<td>
		                        <s:select list="@com.lvmama.comm.vo.Constant$COMPLAINT_SYS_CODE@values()"
		                           		  listKey="code" listValue="cnName" value="#request.sysCode"
		                                  headerKey="" headerValue="请选择" 
		                                  name="sysCode"/>
							</td>
					</s:if>
				</tr>
				<tr>
					<td class="p_label"><span class="startCls">*</span>投诉详情:</td>
					<td colspan="3">
						<textarea rows="5" style="width: 420px;" maxlength="500" id="detailsComplaint" name="detailsComplaint"></textarea>
						<span style="font-size: 12px;color: red;">
							<span id="detailsComplaintCount"  style="font-size: 12px;color: red;">500</span>/500(你还可输入<span id="detailsComplaintCount1"  style="font-size: 12px;color: red;">500</span>个字)
						</span>
					</td>
				</tr>
			</table>
		</form>
		<p class="tc mt20">
			<button class="btn btn-small w5" type="button" onclick="saveComplaint();">确认提交</button>
		</p>
	</div>
</body>
</html>
