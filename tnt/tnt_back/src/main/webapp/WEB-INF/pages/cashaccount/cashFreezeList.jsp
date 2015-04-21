<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="lv" uri="/WEB-INF/pages//tld/lvmama-tags.tld"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="/WEB-INF/pages/common/head_meta.jsp"></jsp:include>
</head>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"+ request.getServerName() + path+"/";
%>
<body>
	<div class="iframe_search">
		<div class="ui_title f14">
				<ul class="ui_tab">
					<li ${beg }><a href="<c:url value="/cashaccount/fin/beg/queryFreezeList.do"/>">未处理</a></li>
					<li ${end }><a href="<c:url value="/cashaccount/fin/end/queryFreezeList.do"/>">已处理</a></li>
				</ul>
		</div>
		
	</div>
	<div class="iframe_content mt20">
	<c:if test="${tntCashFreezeQueueList!=null}">
			<div class="p_box">
				<table class="p_table table_center">
					<thead>
						<tr>
							<th>冻结单号</th>
							<th>分销商名称</th>
							<th>公司名称</th>
							<th>冻结金额</th>
							<th>冻结日期</th>
							<th>解冻确认日期</th>
							<th>状态</th>
							<th>操作</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="tntCashFreezeQueue" items="${tntCashFreezeQueueList}">
							<tr>
								<td>${tntCashFreezeQueue.freezeQueueId}</td>
								<td>${tntCashFreezeQueue.tntUser.realName}</td>
								<td>${tntCashFreezeQueue.tntUser.detail.companyNameOrPerson}</td>
								<td>${tntCashFreezeQueue.freezeAmountY}</td>
								<td>${tntCashFreezeQueue.cnCreateTime}</td>
								<td>${tntCashFreezeQueue.cnReleaseTime}</td>
								<td>${tntCashFreezeQueue.cnStatus}</td>
								<td>
									<c:if test='${tntCashFreezeQueue.status=="WAIT_RELEASE"}'>
										<a href="javascript:void(0)" onclick="release(this,'${tntCashFreezeQueue.freezeQueueId}');">解冻</a>
									</c:if>
									<a param="{'objectType':'TNT_ACCOUNT','objectId':'${tntCashFreezeQueue.freezeQueueId}','logType':'ACCOUNT_FREEZE'}" class="showLogDialog" href="javascript:void(0)">日志</a>
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
	</div>
	
</body>
<jsp:include page="/WEB-INF/pages/common/foot.jsp"></jsp:include>
<script type="text/javascript" src="${basePath }/tnt_back/js/log.js" ></script>
<script type="text/javascript">
function release(obj,drawId){
	if(confirm("确认解冻吗!")){
		 $.ajax({
			type: "post",
			url:  "/tnt_back/cashaccount/releaseFreeze.do?freezeQueueId="+drawId,
			success: function(response) {
				alert("解冻成功！");
				location.reload();
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
