<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="l" uri="/tld/lvmama-tags.tld"%>
<%
 String basePath = request.getContextPath();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>新增推荐的信息</title>
<link rel="stylesheet" href="<%=basePath %>/css/place/backstage_table.css"/>
<link rel="stylesheet" href="<%=basePath %>/css/place/panel.css"/>
<link rel="stylesheet" href="<%=basePath %>/js/base/autocomplete/jquery.ui.autocomplete.css"/>
<s:include value="/WEB-INF/pages/pub/jquery.jsp"/>
<script type="text/javascript">
	var basePath="<%=basePath %>";
</script>
<script type="text/javascript" src="<%=basePath %>/js/base/autocomplete/jquery.ui.autocomplete.js"></script>
<script type="text/javascript" src="<%=basePath %>/js/place/houtai.js"></script>
<script type="text/javascript" src="<%=basePath %>/js/seo/panel_custom.js"></script>
<script type="text/javascript" src="<%=basePath %>/js/mobile/mobile_recommend.js"></script>
<style type="text/css">
	.ul_detail li {
	    display: inline;
	    float: left;
	    margin-bottom: 5px;
	    text-align: left;
	    width: 550px;
	}
	.js_zs {
		left:54%;
	}
</style>
<script type="text/javascript">
  function sendSeqs(obj) {
	  if(null != obj && null != $(obj).val()) {
		  $("#rseq").val($(obj).val());
	  }
  }

  /**
    * id回传 。 
  **/
  function bindBlock(placeId,name,stage,pinyin) {
	  window.parent.$("#recommendBlockSonForm #objectId").val(placeId);
	  window.parent.$("#recommendBlockSonForm #son_name").val(name);
	  window.parent.$("#recommendBlockSonForm #objectType").val(stage);
	  window.parent.$("#recommendBlockSonForm #reserve1").val(pinyin);
	  window.parent.$("#bindBlock").hide();
	  
  }
  
</script>
</head>
  
<body style="background:#fff">
	<input type="hidden" id="sonBlockId" value="<s:property value="mobileRecommendBlock.id"/>"/>
		  <!-- 列表  -->
		  <div id="panel_content">
		        <div class="p_oper">
		           <form action="<%=basePath %>/mobile/mobileRecommendBlock!getObjectInfoList.do" id="destForm" method="post" onsubmit="return chkSearchSource(this);">
				    <input type="hidden" id="recommendBlockId" name="mobileRecommendBlock.id" value="<s:property value="mobileRecommendBlock.id"/>"/>
				    <input type="hidden" id="pageChannel"  name="pageChannel" value="<s:property value="pageChannel"/>"/>
					<table>
						<tr>
						    <td>ID:<input type="text" name="objectId" value="<s:property value="objectId"/>" id="objectId"></td>  
			                <td>名称:<input type="text" name="keywords" value="<s:property value="keywords"/>"></td>
			                <td>产品类型: 
			                   <input type="radio" value="1" name="stage" checked/>目的地  
						       <input type="radio" value="2" name="stage" />景区  
						     </td> 
							<td><input type="submit" value="查询"></td>
						</tr>
					</table>
				   </form>
		        </div>
		             <div class="oper_box">
		             <table class="p_table">
		                <thead>
		                   <tr>
		                       <th>对象ID</th>
		                       <th>对象名称</th>
		                       <th>拼音</th>
		                       <th>操作</th>
		                   </tr>
		                </thead>
		                <tbody>
		 				<s:iterator value="mudidi">
		 				 <tr>
		                  <td><s:property value="placeId"/></td>
		                  <td><a href="http://www.lvmama.com/dest/<s:property value="pinYinUrl"/>" target="_blank"><s:property value="name"/></a></td>
		                  <td><s:property value="pinYin"/></td>
		                  <td>
			                  <a href="javascript:void(0)" 
				                  onclick="bindBlock('<s:property value="placeId"/>','<s:property value="name"/>','<s:property value="stage"/>','<s:property value="pinYin"/>');" >
				                                                添加
			                  </a>
		                  </td>
		                </tr>
		 				</s:iterator>
		                  </tbody>
		                 </table>
		                 <div class="p_page">
		                      <div class="pages">
		                     	 <s:property escape="false" value="@com.lvmama.comm.utils.Pagination@pagination(pagination)"/>
		                      </div>
		                 </div>
		             </div>
             </div>
  </body>
</html>
