<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="mis" uri="/tld/lvmama-tags.tld"%>
<s:set var="basePath"><%=request.getContextPath() + "/"%></s:set>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>储值卡出库</title>
<s:include value="/WEB-INF/pages/pub/lvmamacard.jsp" />
<style>
#ss{
	background-color: 
}
</style>
</head>
<body>
<script type="text/javascript">
    $(function(){
        $("#exportExcel").click(function(){
        	var _outStatus = $("#outStatus").val();
        	location.href="${basePath}outStorage/outexcel.do?outStatus="+_outStatus;
        	/*$.ajax({type : "POST",
		           url : "${basePath}outStorage/outexcel.do",data : {"outStatus":_outStatus},
		           success : function(json) {}
			   });*/
        });
        
        $("#addPre").click(function(){
        	location.href = "${basePath}outStorage/outStorageAddPre.do";
        });
        
        $(".outCodeTr").find('td:lt(4)').click(function(){
        	var _id = $(this).parent().attr("id");
        	$("."+_id).is(':visible') ? $("."+_id).hide():$("."+_id).show();
        });
        
        $(".outCodeTr").hover(function(){
        	$(this).find('td:lt(4)').css("backgroundColor","grey");
        },function(){
        	$(this).find('td:lt(4)').css("backgroundColor","#FFFFFF");
        });
        
        $(".delOut").click(function(){
        	if(confirm("确定删除吗？")){
        		var _outCode = $(this).attr("outCode");
        		$.ajax({type : "POST",
  		           url : "${basePath}outStorage/outStorageDelete.do",data : {"outCode":_outCode},
  		           success : function(json) {
  		               if(json=="true"){
  		                   alert("删除成功");
  		                   location.href = '${basePath}outStorage/outStoragePre.do'; 
  		               }else{
  		                   alert("删除失败!");
  		               }
  		           }
  			   });
        	}
        });
        
        $(".payOut").click(function(){
        	if(confirm("确定付款吗？")){
        		var _outCode = $(this).attr("outCode");
        		$.ajax({type : "POST",
   		           url : "${basePath}outStorage/outStoragePay.do",data : {"outCode":_outCode,"outStatus":2},
   		           success : function(json) {
   		               if(json=="true"){
   		                   alert("付款成功");
   		                   location.href = '${basePath}outStorage/outStoragePre.do'; 
   		               }else{
   		                   alert("付款失败!");
   		               }
   		           }
   			   });
        	}
        });
    });
</script>
    <div id="popDiv" style="display: none">
    	
    </div>
    <div class="iframe-content">
        <div class="p_box">
            <form action="${basePath}outStorage/outStoragePre.do" method="post" id="form1">
                <table class="p_table form-inline" width="100%">
                       <tr>
                           <td class="p_label">出库号:</td>
                            <td><input  type="text" name="outCode" value="${outCode}" />
                            </td>
                            <td class="p_label">销售人员:</td>
                            <td><input type="text" name="salePerson" value="${salePerson}" />
                            </td>
                            <td class="p_label">出库时间:</td>
                            <td>
                            	<input  name="outDate"  value="<s:date name="outDate" format="yyyy-MM-dd"></s:date>" class="Wdate" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" />
                            </td>
                       </tr>
                       <tr>
                            
                            <td class="p_label">卡号:</td>
                            <td>
                            	<input name="cardNo" value="${cardNo}" />
                            </td>
                            </td>
                            <td class="p_label">状态:</td>
                            <td><select id="outStatus" name="outStatus">
                             <option   value="">全部</option>
                                <s:iterator value="outStatusList" var="var" status="st">
                                <s:if test="outStatus == key">
                                	<option selected="selected" value="${key}">${value}</option>
                                </s:if>
                                <s:else>
                             <option   value="${key}">${value}</option>
                             	</s:else>
                                </s:iterator>
                            </td>
                            <td colspan="2"><input type="submit" class="btn btn-small w5" value="查&nbsp;&nbsp;询" />&nbsp;&nbsp;&nbsp;&nbsp;<input type="button" id="exportExcel" class="btn btn-small w5" value="导&nbsp;&nbsp;出" /></td>
                    </tr>
                </table>
            </form>
        </div>
        <div class="p_box">
        
            <table class="p_table ">
            	<tr>
            		<td colspan="8"><mis:checkPerm permCode="3423"><input class="btn btn-small w5" type="button" id="addPre" value="新增出库"></input></mis:checkPerm></td>
            	</tr>
                <tr>
                    <th width="100">出库号</th>
                    <th>销售人员</th>
                    <th>出库时间</th>
                    <th>状态</th>
                    <th>操作</th>
                </tr>
			<s:iterator value="outList" var="bean">
				<tr class="outCodeTr" id="${bean.outCode}">
					<td><input type="hidden" value="${bean.outId}" name="outId"/>${bean.outCode}</td>
					<td >${bean.salePerson}</td>
					<td ><s:date name="outDate" format="yyyy年MM月dd日"></s:date></td>
					<td>${cnOutStatus}</td>
					<td><s:if test="#bean.outStatus == 0">
						 <mis:checkPerm permCode="3424">
							<a href="javascript:void(0)" onclick="javascript:loadPopDiv('${basePath}outStorage/showDiv.do?outCode=${bean.outCode}','',400,170);">确认</a>&nbsp;
						</mis:checkPerm>
						<mis:checkPerm permCode="3425">
							<a href="${basePath}outStorage/outStorageUpdPre.do?outCode=${bean.outCode}">修改</a>&nbsp;
						</mis:checkPerm>
						<mis:checkPerm permCode="3426">
							<a href="javascript:void(0)" outCode="${bean.outCode}" class="delOut">删除</a>&nbsp;
						</mis:checkPerm>
						<mis:checkPerm permCode="3427">
							<a href="${basePath}outStorage/outStoragePrint.do?outCode=${bean.outCode}">打印</a>
						</mis:checkPerm>
						</s:if>
						<s:elseif test="#bean.outStatus==1">
						<mis:checkPerm permCode="3428">
							<a href="javascript:void(0)" outCode="${bean.outCode}" class="payOut">付款</a>&nbsp;
						</mis:checkPerm>
						<mis:checkPerm permCode="3429">
							<a href="${basePath}outStorage/outStorageQuery.do?outCode=${bean.outCode}">查看</a>
						</mis:checkPerm>
						</s:elseif>
						<s:else>
						<mis:checkPerm permCode="3429">
							<a href="${basePath}outStorage/outStorageQuery.do?outCode=${bean.outCode}">查看</a>
						</mis:checkPerm>
						</s:else>
					</td>	
				</tr>
				<tr class="${bean.outCode}" style="display: none">
					<td colspan="2" style="text-align: center">&nbsp;明细</td>
					<td>面值</td>
                    <td>数量</td>
                    <td>卡号范围</td>
				</tr>
				<s:iterator value="#bean.details" var="b">
					<tr class="${bean.outCode}" style="display: none">
						<td colspan="2">&nbsp;</td>
						<td>${b.outDetailsAmount}</td>
						<td>${b.outDetailsCount}</td>
						<td><s:if test="#b.cardNoBegin != null">
							${b.cardNoBegin}~${b.cardNoEnd}
							</s:if>
						</td>
					</tr>
				</s:iterator>
			</s:iterator>
				<tr>
					<td colspan="4">总<s:property value="pagination.totalResultSize" />条&nbsp;&nbsp;卡总量：<s:if test="storedOutSum.countSum==null">0</s:if>${storedOutSum.countSum}&nbsp;&nbsp;总金额：<s:if test="storedOutSum.moneySum==null">0</s:if>${storedOutSum.moneySum}</td>
					<td>
						<div style="text-align: right;">
							<s:property escape="false" value="@com.lvmama.comm.utils.Pagination@pagePost(pagination.pageSize,pagination.totalPageNum,pagination.url,pagination.currentPage)" />
						</div>
					</td>
				</tr>
            </table>
        </div>
    </div>
</body>
</html>