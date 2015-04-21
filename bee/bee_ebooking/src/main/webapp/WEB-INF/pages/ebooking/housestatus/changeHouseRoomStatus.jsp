<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<title>驴妈妈供应商管理系统</title>
<link rel="stylesheet" href="http://pic.lvmama.com/styles/new_v/reset.css">
<link rel="stylesheet" href="http://pic.lvmama.com/styles/msg_ord_snspt.css">
<link rel="stylesheet" href="http://pic.lvmama.com/styles/global_backpop.css">
<link rel="stylesheet" media="all"
	href="http://pic.lvmama.com/styles/ebooking/base.css">
<script src="http://pic.lvmama.com/js/jquery1.8.js"></script>
<script src="${contextPath}/js/base/jquery.validate.js"></script>
<script src="http://pic.lvmama.com/js/ebooking/ebooking.js"></script>
<script src="${contextPath}/js/base/jquery.form.js"></script>

<style type="text/css">
.baoliu {
	width: 120px;
	color: #555;
}
</style>
<script type="text/javascript">

	$(function() {
		$("#baoliuQuantityInput").val('${ebkHouseStatus.baoliuQuantity}');
		$("#baoliuQuantityId").val('${ebkHouseStatus.baoliuQuantity}');
		$("#week_all").click(
				function() {
					if ($(this).attr("checked")) {
						$("input[name='ebkHouseStatus.applyWeek']").attr(
								"checked", true);
						$("input[name='ebkHouseStatus.applyWeek']").attr(
								"disabled", true);
					} else {
						$("input[name='ebkHouseStatus.applyWeek']").attr(
								"checked", false);
						$("input[name='ebkHouseStatus.applyWeek']").attr(
								"disabled", false);
					}
				});
		$("input[name='ebkHouseStatus.applyWeek']").each(function() {
			$(this).click(function() {
				if ($(this).attr("checked") == false) {
					$("#week_all").attr("checked", false);
				}
			});
		});
		
		$("#baoliuAdd").click(function() {
			$("#baoliuQuantityId").val("输入增加的数量");
		});
		$("#baoliuReduce").click(function() {
			$("#baoliuQuantityId").val("输入减少的数量");
		});

		
		$("#manfangTrueId").click(function() {
			$("input[name='ebkHouseStatus.baoliu']").attr(
					"disabled", true);
			$("input[name='ebkHouseStatus.chaomai']").attr(
					"disabled", true);
			$("#baoliuQuantityId").attr("disabled",true);
			
		});
		$("#manfangFalseId").click(function() {
			$("input[name='ebkHouseStatus.baoliu']").attr(
					"disabled", false);
			$("input[name='ebkHouseStatus.chaomai']").attr(
					"disabled", false);
			$("#baoliuQuantityId").attr("disabled",false);
		});
		
		$("#baoliuQuantityId").change(function(){
			var v=$(this).val();
			var result=0;
			if(!(v=='输入增加的数量'||v=='输入减少的数量')){
				var val=parseInt(v);
				if(!isNaN(val)){
					result=val;
				}
			}
			$("input[name='ebkHouseStatus.baoliuQuantity']").val(result);			
		});		
	});
	function checkAndSubmit() {
		if(!controlSumit()){
			return false;
		}
		var options = {
				url:"${basePath}ebooking/housestatus/submitChangeHouseRootStatus.do",
				type : 'POST',
 				success:function(data){
           		   if(data== "success") {
						 alert("操作成功!");
						 reSerachHousePriceTable();
 					} else {
						alert("操作失败："+data);
					}
           		    bClicked = false; 
				},
				error:function(){
                      alert("出现错误");
                     bClicked = false; 
                 }
			};
		
		$('#changeHouseRootStatusForm').ajaxSubmit(options);
	}
	function reSerachHousePriceTable(){
		
		var beginDate=$('input[name="ebkHouseStatus.beginDate"]').val();
		var endDate=$('input[name="ebkHouseStatus.endDate"]').val();
		var optionsTable = {
				url:"${basePath}ebooking/housestatus/houseRoomTimePriceTable.do",
				type : 'POST',
				data:{"ebkHouseStatus.metaProductBranchId":'${ebkHouseStatus.metaProductBranchId}',
					"ebkHouseStatus.beginDate":beginDate,
					"ebkHouseStatus.endDate":endDate
					},
				dataType: "html",
				success:function(html,textStatus){
               	if(textStatus=="success"){
               		 $("#timePriceDiv").html(html);
					}
				},
				error:function(){
                    alert("error");
               }
			};
	   $.ajax(optionsTable);
	}

	///
	var bClicked = false; 
	function controlSumit() {
		if (bClicked) {
			return false;
		}
		bClicked = true;
 		return true;
	}
</script>
</head>
<body id="body_ftwh">
	<jsp:include page="../../common/head.jsp"></jsp:include>
	<div class="breadcrumb">
		<ul class="breadcrumb_nav">
			<li class="home">首页</li>
			<li>库存维护</li>
		</ul>
		<a href="http://www.lvmama.com/zt/ppt/ebk/fangtai-guide.ppt" class="ppt_xz">库存维护操作PPT下载</a>
	</div>
	<!--以上是公用部分-->
	<!--订单处理开始-->
	<dl class="order_nav">
		<dt>库存维护</dt>
	</dl>
	<div class="roomStatus">
		<form id="changeHouseRootStatusForm"
			method="post" enctype="multipart/form-data">
			<input type="hidden" name="ebkHouseStatus.metaProductBranchId"
				value="<s:property value="ebkHouseStatus.metaProductBranchId"/>">
			<input type="hidden" name="ebkHouseStatus.beginDate"
				value="<s:date name="ebkHouseStatus.beginDate" format="yyyy-MM-dd"/>">
			<input type="hidden" name="ebkHouseStatus.endDate"
				value="<s:date name="ebkHouseStatus.endDate" format="yyyy-MM-dd"/>">

			<dl>
				<dt class="width10"></dt>
				<dd>
					<ul class="roomStatus_list">
						<li><strong>您已选房型：</strong>
							<p>
								<b><s:property value="ebkHouseStatus.metaProductBranchName" />(<s:property
										value="ebkHouseStatus.metaProductBranchId" />)</b>
							</p></li>
						<li><strong>已选时间：</strong>
							<p>
								<b><s:date name="ebkHouseStatus.beginDate"
										format="yyyy-MM-dd" /></b> 至 <b><s:date
										name="ebkHouseStatus.endDate" format="yyyy-MM-dd" /></b>
							</p></li>
						<li><strong>星期：</strong>
							<p>
								<label for="week_all"><input id="week_all"
									name="week_all" type="checkbox" value="">全部</label> <label
									for="week_1"><input id="week_1"
									name="ebkHouseStatus.applyWeek" type="checkbox" value="一">星期一</label>
								<label for="week_2"><input id="week_2"
									name="ebkHouseStatus.applyWeek" type="checkbox" value="二">星期二</label>
								<label for="week_3"><input id="week_3"
									name="ebkHouseStatus.applyWeek" type="checkbox" value="三">星期三</label>
								<label for="week_4"><input id="week_4"
									name="ebkHouseStatus.applyWeek" type="checkbox" value="四">星期四</label>
								<label for="week_5"><input id="week_5"
									name="ebkHouseStatus.applyWeek" type="checkbox" value="五">星期五</label>
								<label for="week_6"><input id="week_6"
									name="ebkHouseStatus.applyWeek" type="checkbox" value="六">星期六</label>
								<label for="week_7"><input id="week_7"
									name="ebkHouseStatus.applyWeek" type="checkbox" value="七">星期日</label>
							</p></li>
						<li><strong>增减保留房：</strong>
							<p>
								<label style="margin-right: 17px;"><input
									name="ebkHouseStatus.baoliu" id="baoliuAdd" type="radio" value="true"
									class="baoliu_add" checked>增加</label> <label
									style="margin-left: 20px;"><input
									name="ebkHouseStatus.baoliu" id="baoliuReduce" type="radio" value="false">减少</label>
								<input id="baoliuQuantityInput" type="hidden" name="ebkHouseStatus.baoliuQuantity" value="0"/>
								<input type="text" name="quantity" id="baoliuQuantityId"
									value="输入增加的数量" class="baoliu"
									onFocus="if (value =='输入增加的数量' || value =='输入减少的数量'){value =''}"
									onBlur="if (value ==''){value='输入增加的数量' || value =='输入减少的数量'}" />(为空或非数字不做修改)
							</p></li>
						<li><strong>是否超卖：</strong>
							<p>
								<label style="margin-right: 49px;"><input
									name="ebkHouseStatus.chaomai" id="chaomaiTrueId" type="radio"
									value="true" checked>是</label> <label><input
									name="ebkHouseStatus.chaomai" id="chaomaiFalseId" type="radio"
									value="false">否</label>
								<span style="color:red;">超卖选择“是”代表当天保留房售完后订单自动变为需确认房源；选择“否”代表当天保留房售完后自动关房。</span>	
							</p></li>
						<li><strong>是否满房：</strong>
							<p>
								<label><input name="ebkHouseStatus.manfang" id="manfangFalseId" type="radio"
									value="MAN_FANG_FALSE" checked>非满房</label> <label><input
									name="ebkHouseStatus.manfang" id="manfangTrueId" type="radio"
									value="MAN_FANG_TRUE">满房</label>
							</p></li>
						<li>
					  <input  class="roomStatus_cx"  type="button" value="保存修改" onclick="checkAndSubmit();"/>
						</li>
					</ul>
				</dd>
			</dl>
		</form>
		<a class="chaxun_new" id="reSreach"
			href="${basePath}ebooking/housestatus/applyHouseRoomStatus.do">重新查询</a>
	</div>
	<!-- 时间价格表 -->
	<div id="timePriceDiv">
		 <jsp:include page="./houseRoomTimePriceTable.jsp"></jsp:include>
	</div>
	<!--公用底部-->
	<script type="text/javascript"
		src="http://pic.lvmama.com/js/ui/lvmamaUI/lvmamaUI.js" charset="utf-8"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			if ("input[class='baoliu_add']:checked") {
				$(".baoliu").show();
			} else {
				$(".baoliu").hide();
			}
		});
	</script>
	<jsp:include page="../../common/footer.jsp"></jsp:include>
</body>
</html>


