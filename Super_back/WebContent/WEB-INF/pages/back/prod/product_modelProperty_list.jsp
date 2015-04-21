<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<meta content="text/html; charset=utf-8" http-equiv="Content-Type">
<title>super后台&mdash;&mdash;产品属性管理</title>
    <link type="text/css" rel="stylesheet" href="http://super.lvmama.com/super_back/style/houtai.css">
    <%@ include file="/WEB-INF/pages/back/base/jquery.jsp"%>
    <script type="text/javascript" src="<%=basePath%>js/prod/modelPropertyInfo.js"></script>
</head>
<body>
<div class="main main02">
	<div class="row1">
				<form method="post" action="/super_back/prod/modelPropertyList.do" id="searchForm">
					<table width="100%" cellspacing="0" cellpadding="0" border="0" class="newInput">
						<tbody>
                          <tr>
                            <td> 
								有效性:
							</td>
							<td>
								<select id="isValid" name="isValid">
									<option value="">全部</option>
									<option value="Y" <s:if test='isValid=="Y"'>selected='selected'</s:if>>是</option>
									<option value="N" <s:if test='isValid=="N"'>selected='selected'</s:if>>否</option>
								</select>
							</td>
							<td>
								一级模块:
							</td>
							<td>
								<s:select list="firstGradeModelList" name="firstModelId" listKey="code" listValue="name"/>
							</td>
							<td>
								二级模块:
							</td>
							<td>
								<select id="secondGradeModel" name="secondModelId"> </select>
							</td>						
							<td>
								<input type="text" value="" name="property" class="newtext1">
							</td>	
							<td>
								<input type="submit" value="搜索" name="search" id="searchBtn" class="button">
							</td>
                            <td>
                                <a href="#addModelProperty" class="showModelProperty" param="{'objectType':'add'}">
                                <input type="button" value="新增" name="" class="button">
                                </a>
                                
                            </td>													
						</tr>
                        </tbody>
                      </table>
                        
                      <table  cellspacing="0" cellpadding="0" border="0" class="newInput  pro_add">
                        <tbody>
						  <tr>
							<td valign="top">
								适用产品类型:
							</td>
							<td  class="pro_type_td" align="left">
                                <s:checkboxlist list="subProductTypeList" name="productTypes" listKey="code" listValue="name"/>
							</td>						    
						</tr>
					</tbody>
                  </table>
				</form>
   </div>
   <div class="row2">
      <table width="96%" cellspacing="0" cellpadding="0" border="0" class="newTable pro_protab">
          <tr class="newTableTit">
            <th>是否有效</th>
            <th width="80">一级模块</th>
            <th width="80">二级模块</th>
            <th width="50">是否可多选</th>
            <th width="80">名称</th>
            <th width="200">产品类别</th>
            <th width="80">更新时间 </th>
            <th width="80">操作</th>
          </tr>
          <s:iterator value="pagination.records">
          <tr>
            <td class="opt_state"><s:if test='isValid=="Y"'>是</s:if><s:else>否</s:else></td>
            <td><s:property value="getModeTypeById(firstModelId).modelName"/></td>
            <td><s:property value="getModeTypeById(secondModelId).modelName"/></td>
            <td>
              	<s:if test='getModeTypeById(secondModelId).isMultiChoice=="Y"'>是</s:if>
	            <s:if test='getModeTypeById(secondModelId).isMultiChoice=="2"'>是(最多2条)</s:if>
	            <s:if test='getModeTypeById(secondModelId).isMultiChoice=="N"'>否</s:if>
            </td>
            <td align="left"><s:property value="property"/></td>
            <td><s:property value="getChTypes(productType)"/></td>
            <td><s:date format="yyyy-MM-dd HH:mm" name="updateDate"/></td>
            <td class="opt_td">
               <a href="#modifyModelProperty" class="showModelProperty" param="{'id':<s:property value='id'/>,'objectType':'modify'}">查看修改</a>
               <span class="opt_txt opt_dis" params="{'id':<s:property value='id'/>,'isValid':<s:if test='isValid=="Y"'>'N'</s:if><s:else>'Y'</s:else>}"><s:if test='isValid=="Y"'>设为无效</s:if><s:else>设为有效</s:else></span>
            </td>
          </tr>
          </s:iterator>
        </table>
			<table width="90%" border="0" align="center">
				<s:include value="/WEB-INF/pages/back/base/pag.jsp" />
			</table>
   </div>
    
     <script type="text/javascript">  		
		$(".opt_dis").bind("click",function(){
			var params = $(this).attr("params");				
			var flag = window.confirm("确定" + $(this).text() + "？");
			if (flag) {				
				$.post("/super_back/prod/saveOrUpdateProperty.do", eval("("+params+")"), function(json){				
					$("#searchForm").submit();
				});
			}
		})//设置有效性弹出层
   </script>
   
   <script type="text/javascript">    
      $("select[name=firstModelId]").bind("change", function(){
    	  updateSecondGradeModel($(this).val());
      });
      $("select[name=firstModelId]").trigger("change");
      
   	  function updateSecondGradeModel(value) { 
		  $("select[name=secondModelId]").empty();
		  $("select[name=secondModelId]").append("<option value=''>全部</option>");
		  <s:iterator value="modelTypeList">
			  <s:if test="parentId!=null">
			  if(value=="<s:property value='parentId'/>") {
				  $("select[name=secondModelId]").append("<option value='<s:property value='id'/>' <s:if test='id==secondModelId'>selected='selected'</s:if>><s:property value='modelName'/></option>");			  
			  }
			  </s:if>
		   </s:iterator>
	  }
   </script>
   
</body>
</html>