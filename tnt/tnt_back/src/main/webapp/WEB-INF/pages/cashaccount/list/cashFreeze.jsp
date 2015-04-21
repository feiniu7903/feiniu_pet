<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="lv" uri="/WEB-INF/pages//tld/lvmama-tags.tld"%>
<!DOCTYPE html>
<html>
<head>
</head>
<body>
	<c:if test="${tntCashFreezeQueueList!=null}">
			<div class="p_box">
				<table class="p_table table_center">
					<thead>
						<tr>
							<th>冻结日期</th>
							<th>冻结金额</th>
							<th>解冻日期</th>
							<th>冻结原因</th>
							<th>状态</th>
							<th>操作</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="tntRefundment" items="${tntCashFreezeQueueList}">
							<tr>
								<td>${tntRefundment.cnCreateTime}</td>
								<td>${tntRefundment.freezeAmountY}</td>
								<td>${tntRefundment.cnReleaseTime}</td>
								<td>${tntRefundment.reason}</td>
								<td>${tntRefundment.cnStatus}</td>
								<td>
									<c:if test='${tntRefundment.status=="FREEZE"}'>
										<a href="#" onclick="release(this,'${tntRefundment.freezeQueueId}');">解冻</a>
									</c:if>
									<%-- <c:if test='${tntRefundment.status!="FREEZE"}'>
										已解冻
									</c:if> --%>
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
				<c:if test="${page!=null }">
					<div class="paging freezeClass_pags">
						<p class="page_msg cc3">
							共 <em class="cc1">${page.totalResultSize }</em> 条记录，每页显示 10 条记录
						</p>
						<div class="paging">${page.pagination}</div>
					</div>
				</c:if>
			</div>
		</c:if>
		<c:if test="${tntCashFreezeQueueList==null}">
			<div class="no_data mt20">
				<i class="icon-warn32"></i>暂无相关条目！
			</div>
		</c:if>
</body>
<script type="text/javascript">
$(function(){
	$(".freezeClass_pags").children().children().children().find("a").click(function(){
		if($(this).attr("href")!="#"){
			query($(this).attr("href"),"freezeClass");
			return false;
		}
	});
});
function release(obj,drawId){
	if(confirm("确认解冻吗!")){
		$.ajax({
			type: "post",
			url:  "/tnt_back/cashaccount/releaseFreeze.do?freezeQueueId="+drawId,
			success: function(response) {
				alert("解冻成功！");
				$(obj).parent().prev().html("已解冻");
				$(obj).parent().html("");
			},
			error : function(XmlHttpRequest, textStatus, errorThrown) {
				alert("系统处理异常，请确保您提交的数据的正确！");
			}
		});	
	}
	return false;
};
</script>
</html>
