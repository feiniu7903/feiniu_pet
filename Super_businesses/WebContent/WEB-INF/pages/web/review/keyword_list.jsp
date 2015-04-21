<%@ page language="java" contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<s:set var="basePath"><%=request.getContextPath()+"/"%></s:set>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>关键字</title>
<s:include value="/WEB-INF/pages/pub/reviewHeadJs.jsp" />
</head>
	<body>
		<div id="popDiv" style="display: none"></div>
		<div class="iframe-content">
			<div class="p_box">
				<form action="${basePath}keyword/keyWordList.do" method="post" id="form1">
				<table class="p_table table_center" width="80%">
				<tr>
							<td class="p_label">添加日期:</td>
							<td>
								<input readonly="readonly" name="startDate" id="startDate" value='<s:property value="startDate"/>' type="text" class="Wdate" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>
	至&nbsp;<input readonly="readonly" name="endDate" id="endDate" value="<s:property value="endDate"/>" type="text" class="Wdate" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'startDate\')}'})"/>
							</td>
							<td class="p_label">模糊查找:</td>
							<td>
							   <input id="kContent" name="kContent"  type="text" value="${ kContent}"/>
							</td>
							<td colspan="2" class="p_label">
							<button class="btn btn-small w5" type="submit">查询</button>
							</td>
				</tr>		
						
				</table>	
				</form>		
			</div>
			
			<div class="p_box">
				<table class="p_table table_center">
				<thead>
					<tr>
						<th width="3%">&nbsp;</th>
 					    <th>添加日期</th>
					    <th>关键词</th>
					    <th>操作</th>
					</tr>
				</thead>
				
				<tbody>
				   <s:if test="null != keyWordList">
					<s:iterator value="keyWordList" var="key" status="st">
						<tr>
							<td><input type="checkbox" name="chk_list"  value="${key.kId }"/></td>
 	    					<td>${key.kDate}</td>
	    					<td>${key.kContent }</td>
						    <td class="gl_cz">
                                <a href="javascript:keyWordEdit('${key.kId}')">编辑</a>&nbsp;
                                <a href="javascript:keyWordDelete('${key.kId}')">删除</a>
						    </td>
					    </tr>
					    
					</s:iterator>
					</s:if>
					<tr>
					    <td><input type="checkbox" name="chk_all" id="chk_all"/></td>
					    <td><button class="btn btn-small w3" type="button" onclick="checkFormDelete()">删除</button> &nbsp;<button class="btn btn-small w5" type="button" onclick="javascript:downLoadKeyWord()">全部导出</button></td>
     					<td colspan="2" align="right">总【<s:property value="pagination.totalResultSize"/>】条&nbsp;&nbsp;
     					每页显示<input id="page_size_go" type="text" maxlength="3" style="width: 20px;" name="pageSize" value="${pageSize }" />
     					<a href="javascript:goPageSize()">&nbsp;GO&nbsp;</a>
     					<div style="text-align: right;"><s:property escape="false" value="@com.lvmama.comm.utils.Pagination@pagePost(pagination.pageSize,pagination.totalPageNum,pagination.url,pagination.currentPage)"/></div></td>
    				</tr>
    			</tbody>
				</table>
			</div>
			<form action="${basePath}keyword/keyWordSave.do" method="post" id="form2">
			<table>
			     <tr>
			         <td>
			         <label> 批量添加 请以[英文逗号]和[换行]分隔</label>
			             <s:textarea id="keyWordLists" name="keyWordLists" cssStyle="width:900px; height:100px; "></s:textarea>
			             <button class="btn btn-small w5" type="submit" onclick="return checkFormSave()">添加</button>
			         </td>
			     </tr>
			
			</table>
			</form>
		</div>
	</body>

	<script type="text/javascript">
		$("#chk_all").click(function(){
	        $("input[name='chk_list']").attr("checked",$(this).attr("checked"));
	    });
		
		function checkFormSave(){
		    if(""==$("#keyWordLists").val().trim()){
		        alert("添加关键字不能为空");
		        return false;
		    }
		    $("#form2").submit();
		    return true;
		    
		}

	
		function checkFormDelete(){
		    var keyWordIdList = "";
	        $(":checkbox:checked").each(function(i,n){
	          if (n.value != "on") {
	              keyWordIdList = keyWordIdList + n.value + ",";  
	          }
	        });
	        var kContent = $("#kContent").val();
		    if (keyWordIdList.length == 0) {
		        alert("请选择要删除的");
		        return;
		    }else{
		    	if(confirm("您确定要删除这些数据吗 ?")){
		    		location=${basePath}+"keyword/batchDeleteKeyWord.do?keyWordIdLists="+keyWordIdList+"&kContent="+kContent;
	            };
		    }
		}
	
        function goPageSize(){
        	var pageSize=$("#page_size_go").val();
        	if (pageSize == "" || isNaN(pageSize)) {
                alert("请输入合法的数据");
                $("#page_size_go").focus();
                return;
        	}
            
        	if(0>pageSize){
        		alert("每页最少1条");
        		return;
        	}else if(500<pageSize){
        		alert("每页最多显示500条");
        		return;
        	}else{
            	location=${basePath}+"keyword/keyWordList.do?pageSize="+pageSize;
        	}
        } 
    
		
		function keyWordEdit(keyId) {
			$("#popDiv").load("${basePath}keyword/goUpdatekeyWord.do?keyId="+keyId, function() {
				$(this).dialog({
		        	modal:true,
		            title:"编辑关键字",
		            width:700,
		            height:250
		        });
		     });							
		} 
		
		
		function keyWordDelete(keyId) {
			if(confirm("你确定要删除吗?")){
		          location=${basePath}+"keyword/doDeleteKeyWord.do?keyId="+keyId;
			};
		}
	
		function downLoadKeyWord(){
			if(confirm("您确定要导出吗?")){
				location = ${basePath}+"keyword/writeExportExcelData.do";
			}
		}
		
	</script>

		
</html>