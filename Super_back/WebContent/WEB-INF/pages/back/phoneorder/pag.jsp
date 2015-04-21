<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="/tld/lvmama-tags.tld" prefix="z"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>themes/pag.css" />
<s:if test="pagination!=null">
	<z:backTag actionUrl="${basePath}${pagination.actionUrl}"></z:backTag>
	<div class="page_div">
		<span> 总共 <s:property value="pagination.totalRecords" /> 条记录
			每页 <s:property value="pagination.perPageRecord" /> 条记录 </span>
		<span> <s:if test="pagination.totalPages>1">
				<s:if test="pagination.page!=1">
					<a href="#"
						onclick="do_goPage(1,'<s:property value="pagination.actionUrl"/>')">首页</a>
				</s:if>
				<s:else>
							首页
					   </s:else>
				<s:if test="pagination.page==1">
							上一页
					    </s:if>
				<s:if test="pagination.page!=1">
					<a href="#"
						onclick="do_goPage(<s:property value="pagination.previousPage"/>,'<s:property value="pagination.actionUrl"/>')">上一页</a>
				</s:if>
				<s:if test="pagination.page==pagination.totalPages">
							下一页				
					   </s:if>
				<s:if test="pagination.page!=pagination.totalPages">
					<a href="#"
						onclick="do_goPage(<s:property value="pagination.nextPage"/>,'<s:property value="pagination.actionUrl"/>')">下一页</a>
				</s:if>
				<s:if test="pagination.page!=pagination.totalPages">
					<a href="#"
						onclick="do_goPage(<s:property value="pagination.totalPages"/>,'<s:property value="pagination.actionUrl"/>')">尾页</a>
				</s:if>
				<s:else>
						     尾页
					   </s:else>
			</s:if> <s:else>
						首页&nbsp;
						上一页&nbsp;
						下一页&nbsp;
						尾页
				   </s:else> </span>
		<span> 当前第 <s:property value="pagination.page" /> 页 总共 <s:property
				value="pagination.totalPages" /> 页 </span>

		<span> 跳转到 <input type="text" name="page" id="fpages" size="3" />
			<a href="#"
			onclick="goPage(<s:property value="pagination.totalPages"/>,'<s:property value="pagination.actionUrl"/>')">GO!</a>

			&nbsp;&nbsp;&nbsp;&nbsp;显示 <select size="1" name="pagRecord"
				id="pagRecord"
				onchange="do_goPage(1,'<s:property value="pagination.actionUrl"/>')">
				<option value="10"
					<s:if test="pagination.perPageRecord==10">selected</s:if>>
					10
				</option>
				<option value="20"
					<s:if test="pagination.perPageRecord==20">selected</s:if>>
					20
				</option>
				<option value="50"
					<s:if test="pagination.perPageRecord==50">selected</s:if>>
					50
				</option>
				<option value="100"
					<s:if test="pagination.perPageRecord==100">selected</s:if>>
					100
				</option>
				<option value="200"
					<s:if test="pagination.perPageRecord==200">selected</s:if>>
					200
				</option>
			</select> 条 </span>
	</div>
</s:if>

<script type="text/javascript">
<!--
function goPage(maxPage,actionUrl){

	var pp=document.getElementById('fpages').value;
	if(pp==''){
		alert("请输入您要跳转的页码");
		document.getElementById('page').focus();
		return;
	}else if(isNaN(pp)){
		alert("无效字符，请输入您要跳转的页码");
		document.getElementById('page').value="";
		document.getElementById('page').focus();
		return;
	}else if(pp<1){
		alert("您输入的页码太小，重新输入");
		document.getElementById('page').value="";
		document.getElementById('page').focus();
		return;
	}else if(pp>maxPage){
		alert("您输入的页码已超出最大页数，重新输入");
		document.getElementById('page').value="";
		document.getElementById('page').focus();
		return;
	}else{
		do_goPage(pp,actionUrl);
	}
	
}

function do_goPage(page,actionUrl){	
		var pgForm=$('#pgForm');
		var m = pgForm.getForm();
		var ppage=document.getElementById("fpage");
		var pagRecord=document.getElementById("pagRecord");
		var perPageRecord=document.getElementById("perPageRecord");
		var vValue=pagRecord.options[pagRecord.selectedIndex].value;
		perPageRecord.value=vValue;ppage.value=page;
		$('#productList').refresh(pgForm.serialize());
		$('#productList').show();
}
//-->
</script>
