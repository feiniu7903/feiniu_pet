<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="mis" uri="/tld/lvmama-tags.tld"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>super后台——签证销售产品列表</title>

		<s:include value="/WEB-INF/pages/back/base/jquery.jsp" />
		<%@ include file="/WEB-INF/pages/back/base/jsonSuggest.jsp"%>
		<%@ include file="/WEB-INF/pages/back/base/timepicker.jsp"%>
		<script type="text/javascript" src="<%=basePath%>js/base/log.js">
		</script>
		<script type="text/javascript"
			src="<%=basePath%>js/prod/product_list.js">
		</script>
		<script type="text/javascript"
			src="<%=basePath%>js/prod/limit_sale.js">
		</script>
		<script type="text/javascript" src="<%=basePath%>js/prod/condition.js">
		</script>
		<script type="text/javascript">
		  $(function(){
			var country='${country}';
			if(country!=""){
			   $("#country").val(country);
			 }
		  	$("#country").jsonSuggest({
				url : "/pet_back/visa/queryVisaCountry.do",
				maxResults : 10,
				width : 300,
				emptyKeyup : false,
				minCharacters : 1,
				onSelect : function(item) {
					$("#country").val(item.id);
				}
			}).change(function() {
				if ($.trim($(this).val()) == "") {
					$("#country").val("");
				}
			});
		  });
            function addvisaProduct(){
            	window.location.href="<%=basePath%>prod/toAddAsiaProduct.do";
			}
	</script>
	</head>
	<body>
		<div class="main main02">
			<div class="row1">
				<h3 class="newTit">
					产品列表
				</h3>
				<form action="<%=basePath%>prod/queryProductList.do" method="post">
					<input type="hidden" name="productType"
						value="<s:property value="productType"/>"/>
						<input type="hidden" name="subProductType" value="VISA"/>
				<table border="0" cellspacing="0" cellpadding="0" class="newInput"
					width="100%">
					<tr>
						<td><em>产品名称：</em></td>
						<td><input type="text" class="newtext1" name="productName"
							value="<s:property value="productName"/>" /></td>
						<td><em>产品ID：</em></td>
						<td><input type="text" class="newtext1" name="productId"
							value="<s:property value="productId"/>" /></td>
						<td><em>产品编号：</em></td>
						<td><input type="text" class="newtext1" name="bizcode"
							value="<s:property value="bizcode"/>" /></td>
					</tr>
					<tr>
						<td><em>标的：</em></td>
						<td><input type="text" name="placeName" id="searchPlace"
							value="<s:property value="placeName"/>" /> <input type="hidden"
							name="placeId" id="comPlaceId"
							value="<s:property value="placeId"/>" /></td>
						<td><em>上下线时间：</em></td>
						<td><s:select list="#{'':'请选择','true':'有效','false':'无效'}"
								name="onlineStatus"></s:select></td>
						<td><em>所属公司：</em></td>
						<td><s:select list="filialeNameList" name="filialeName"
								listKey="code" listValue="name"></s:select></td>
					</tr>
					<tr>
						<td><em>状态：</em></td>
						<td><s:select list="#{'':'请选择','true':'上线','false':'下线'}"
								name="onLine"></s:select></td>
						<td><em>国家：</em></td>
						<td><input type="text" name="country" id="country" /></td>
						<td><em>签证类型：</em></td>
						<td><s:select name="visaType"
								list="#{'':'-- 请选择  --','GROUP_LEISURE_TORUS_VISA':'团体旅游签证','PERSONAL_VISA':'个人旅游签证','BUSINESS_VISA':'商务签证','VISIT_VISA':'探亲访友签证','STUDENT_VISA':'留学签证','MATCH_VISA':'赛事签证'}" />
						</td>
					</tr>
					<tr>
						<td><em>送签城市：</em></td>
						<td><s:select name="city"
								list="#{'':'-- 请选择  --','SH_VISA_CITY':'上海送签','BJ_VISA_CITY':'北京送签','GZ_VISA_CITY':'广州送签','CD_VISA_CITY':'成都送签','TJ_VISA_CITY':'天津送签','WH_VISA_CITY':'武汉送签','SY_VISA_CITY':'沈阳送签'}" />
						</td>
						<td><input type="submit" class="button" value="查询" /></td>
						<td colspan="3"><mis:checkPerm permCode="1429"
								permParentCode="${permId}">
								<input type="button" class="newProductvisa button" value="新增销售" onclick="addvisaProduct()"/>
							</mis:checkPerm></td>
					</tr>
				</table>
			</form>
			</div>
			<div class="row2">
				<table border="0" cellspacing="0" cellpadding="0" class="newTable">
					<tr class="newTableTit">
						<td class="idw">
							产品ID
						</td>
						<td class="prodw">
							销售产品编号
						</td>
						<td>
							销售产品名称
						</td>
						<td class="timew">
							上线时间
						</td>
						<td class="timew">
							下线时间
						</td>
						<td class="timew">
							录入时间
						</td>
						<td class="statew">
							状态
						</td>
						<td class="opt_w">
							操作
						</td>
					</tr>
					<s:iterator value="pagination.records" var="product">
						<tr id="tr_<s:property value="productId"/>">
							<td>
								<s:property value="productId" />
							</td>
							<td>
								<s:property value="bizcode" />
							</td>
							<td>
								<s:property value="productName" />
							</td>
							<td>
								<s:date name="onlineTime" format="yyyy-MM-dd" />
							</td>
							<td>
								<s:date name="offlineTime" format="yyyy-MM-dd" />
							</td>
							<td>
								<s:date name="createTime" format="yyyy-MM-dd HH:ss:mm" />
							</td>
							<td class="online">
								<s:property value="strOnLine" />
							</td>
							<td>
								<mis:checkPerm permCode="1431" permParentCode="${permId}">
									<a
										href="<%=basePath%>prod/editAsiaProduct.do?productId=<s:property value="productId"/>">修改</a>
								</mis:checkPerm>
								<%-- <mis:checkPerm permCode="1432" permParentCode="${permId}">
									<a href="#delete" class="deleteProduct"
										tit="<s:property value="productName"/>"
										result="<s:property value="productId"/>">删除</a>
								</mis:checkPerm>--%>
								<a href="#copy" class="copyProduct" result="<s:property value="productId"/>" tit="<s:property value="productName"/>">复制</a>
								<mis:checkPerm permCode="2440" permParentCode="${permId}">
									<a href="javascript:void(0);" class="online"
										result="<s:property value="productId"/>"><s:property
											value="buttonOnLine" /> </a>
								</mis:checkPerm>
								<%-- <mis:checkPerm permCode="1479" permParentCode="${permId}"> 
									<a
										href="http://www.lvmama.com/product/preview.do?id=<s:property value="productId"/>"
										target="_blank">预览</a>
								</mis:checkPerm>
								<mis:checkPerm permCode="1438" permParentCode="${permId}">
									<a href="#limitSaleTime" class="limtSale"
										result="<s:property value="productId"/>">时间限制</a>
								</mis:checkPerm>
								<a href="#condition"
									url="<%=basePath%>prod/editProdCondition.do" tt="PROD_PRODUCT"
									class="condition" result="<s:property value="productId"/>">提示</a>
								<mis:checkPerm permCode="1480" permParentCode="${permId}">
									<a href="#recomment" class="recomment"
										result="<s:property value="productId"/>">一句话推荐</a>
								</mis:checkPerm>--%>
								<a href="#log" class="showLogDialog"
									param="{'parentType':'PROD_PRODUCT','parentId':<s:property value="productId"/>}">操作日志</a>
								<%--<a href="#clean" class="doCleanCache" param="${productId }">清除缓存</a>--%>
							</td>
						</tr>
					</s:iterator>
				</table>
			</div>
			<table width="90%" border="0" align="center">
				<s:include value="/WEB-INF/pages/back/base/pag.jsp" />
			</table>
			<div id="limitSaleDiv" style="display: none"></div>
			<div id="conditionDiv" style="display: none"></div>
			<div id="recommendDiv" style="display: none">
				<form onsubmit="return false">
					<input type="hidden" name="product.productId" />
					<center style="margin:10 auto;">建议填写，当前产品的突出特点，产品的优惠活动等抓人眼球的信息。</center>
					<table style="width: 600px;">
						<tr>
							<td width="80">
								推荐一:
							</td>
							<td>
								<input type="text" name="product.recommendInfoFirst" rows="3"
									style="width: 100%" class="length" maxlength="15" />
								字数控制在15字以内，已输入：
								<span class="count"></span>&nbsp;&nbsp;<span style="color:red;">网站频道页，调取使用</span>
							</td>
						</tr>
						<tr>
							<td>
								推荐二:
							</td>
							<td>
								<input type="text" name="product.recommendInfoSecond" rows="3"
									style="width: 100%" class="length" maxlength="30" />
								字数控制在30字以内，已输入：
								<span class="count"></span>&nbsp;&nbsp;<span style="color:red;">网站使用的地方：搜索/dest/频道列表</span>
								
							</td>
						</tr>
						<tr>
							<td>
								推荐三:
							</td>
							<td>
								<textarea type="text" name="product.recommendInfoThird" rows="3"
									style="width: 100%" class="length" maxlength="50"></textarea>
								字数控制在50字以内，已输入：
								<span class="count"></span>
							</td>
						</tr>
					</table>
				</form>
			</div>
		</div>
	</body>
</html>


