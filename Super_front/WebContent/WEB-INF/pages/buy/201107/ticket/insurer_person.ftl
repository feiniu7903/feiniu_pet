<style type="text/css"> 
.add_xu{width:800px; font-size:12px; overflow:hidden; zoom:1; margin-top:20px; margin-left:10px;} 
.add_xu li.add_xu_add{overflow:hidden; zoom:1;border:none} 
.add_xu li.add_xu_add .laber_names{float: left;line-height: 20px;min-height: 20px;padding: 3px 0 3px 15px;text-align: right;width: 100px; display:block;} 
.add_xu li.add_xu_add em{ color:#FF0000; margin-right:5px;} 
.add_xu li.add_xu_add .inp_txt{border: 1px solid #7F9DB9;height: 20px;line-height: 20px;padding: 2px 3px;vertical-align: middle;} 
.add_xu li.add_xu_add span{color:#666} 
} 
</style>			
	<!--========= 订单联系人信息 S ========-->
	<script type="text/javascript">
	//--订单联系人信息------------------------------------------------------------------------------------//
	//保存最近一次选中的复选框对象.
	var checkedBuyRouteName = null;
	//点击"订单联系人信息"复选框.
	function updateFetchTicketInfo2(obj){
	if ($(obj).attr("checked")) {
		if (checkedBuyRouteName != null) {
			$(checkedBuyRouteName).attr("checked",false);
		}
		$("#buyRouteName").val($(obj).attr("receiverName"));
		$("#buyRouteMobile").val($(obj).attr("mobile"));
		$("#buyTicketPersonId").val($(obj).val());
		$("#buyRouteEmail").val($(obj).attr("email"));
		$("#saveToLinkmans").attr("checked",false);
		$("#saveToLinkmans").attr("disabled",true);
		checkedBuyRouteName = obj;
	} else {
		$("#buyRouteName").val("");
		$("#buyRouteMobile").val("");
		$("#buyTicketPersonId").val("");
		$("#buyRouteEmail").val("");
		$("#saveToLinkmans").attr("checked",true);
		$("#saveToLinkmans").attr("disabled",false);
		checkedBuyRouteName = null;
	}
		
	//联系人姓名 文本框.
	$("#buyRouteName").focus(function () {
		clickNameOrMobile();
	});
	//联系人手机 文本框.
	$("#buyRouteMobile").focus(function () {
		clickNameOrMobile();
	});
	//点击联系人姓名、手机文本框.
	function clickNameOrMobile() {
		if (checkedBuyRouteName != null) {
			$(checkedBuyRouteName).attr("checked",false);
			$("#saveToLinkmans").attr("disabled",false);
			$("#saveToLinkmans").attr("checked",true);
			checkedBuyRouteName = null;
		}
	}
	}
	//--游玩人信息-------------------------------------------------------------------------------------//
	//记录当前复选框被选中的数量.
	var count = 0;
	//游玩人的数量.
	var fillTravellerNum = parseInt("${fillTravellerNum}");
	//如果游玩人数为6人以上,则仅显示一个游玩人信息的填写文本框.
	/*
	if (fillTravellerNum > 6) {
		fillTravellerNum = 1;
	}
	*/
	//
	var dictionary = new Dictionary();
	//初始化map.
	for (var i = 0; i < fillTravellerNum; i++) {
		dictionary.put(i,null);
	}
	//点击"游玩人信息"复选框.
	function updateFetchTicketInfo3(obj) {
		//选中复选框.
		if ($(obj).attr("checked")) {
			 if (count >= fillTravellerNum) {
			 	$(obj).attr("checked",false);
			 	alert("too many!");
			 	return;
			 }
			 
			 for (var i = 0; i < fillTravellerNum; i++) {
			 	var value =  dictionary.get(i);
			 	if (value == null) {
			 		dictionary.put(i,obj);
			 		setTravelerInfos(i,obj,true);
			 		count++;
			 		
			 		//如果所选中的复选框数量等于游玩人数量,则禁用未被选中的复选框.
			 		if (count >= fillTravellerNum) {
			 			var checkboxArray = $("#receiverDivId").find("input");
			 			for (var i = 0; i < checkboxArray.length; i++) { 
			 				if ($(checkboxArray[i]).attr("checked") == false) {
			 					$(checkboxArray[i]).attr("disabled",true);
			 				}
			 			}
			 		}
			 		break;
			 	}
			 } 
		}
		//取消复选框. 
		else {
			 for (var i = 0; i < fillTravellerNum; i++) {
			 	var value = dictionary.get(i);
			 	if ($(value).val() == $(obj).val()) {
			 		dictionary.remove(i);
			 		setTravelerInfos(i,obj,false);
			 		count--;
			 		
			 		//如果所选择的复选框数量小于游玩人数量,则恢复禁用未被选中的复选框.
			 		if (count < fillTravellerNum) {
			 			var checkboxArray = $("#receiverDivId").find("input");
			 			for (var i = 0; i < checkboxArray.length; i++) { 
			 				if ($(checkboxArray[i]).attr("checked") == false) {
			 					$(checkboxArray[i]).attr("disabled",false);
			 				}
			 			}
			 		}
			 		break;
			 	}
			 }
		}
	}
	
/**
 * 根据常用联系人信息补全游玩人信息.
 * @param {Object} index 游玩人的序号.
 * @param {Object} obj   常用联系人对象.
 * @param {Object} flag  是否选中标识, true为选中操作,false为取消操作.
 */
function setTravelerInfos(index,obj,flag) {
		if (flag) {
			$("#recrName" + (index + 1)).val($(obj).attr("receiverName"));
			$("#receiverId" + (index + 1)).val($(obj).val());
			$("#cardType" + (index + 1)).find("option:eq(1)").attr("selected",true);
			$("#cardNum" + (index + 1)).val($(obj).attr("cardNum"));
			$("#mobileNumber" + (index + 1)).val($(obj).attr("mobile"));
			$("#mobileNumber" + (index + 1)).attr("mode","super");
		} else {
			$("#recrName" + (index + 1)).val("");
			$("#receiverId" + (index + 1)).val("");
			$("#cardType" + (index + 1)).find("option:eq(1)").attr("selected",true);
			$("#cardNum" + (index + 1)).val("");
			$("#mobileNumber" + (index + 1)).val("");
			$("#mobileNumber" + (index + 1)).attr("mode","simple");
			$("#mobileNumber" + (index + 1)).parent().parent().find("span[id='cardInfoSpan" + (index + 1) + "']").show();
			$("#mobileNumber" + (index + 1)).parent().parent().find("span[id='childrenBrithdayInfoSpan" + (index + 1) + "']").remove();
			$("#mobileNumber" + (index + 1)).parent().parent().find("input[name='childrenInfo']").attr("checked",false);
		}
	}
	
/**
 * 一个简单且不完善的map实现.
 */
function Dictionary(){
		this.data = new Array();
		var size = 0;
		this.put = function(key,value){
			size++;
			this.data[key] = value;
		};

		this.get = function(key){
			return this.data[key];
		};

		this.remove = function(key){
			if (size > 0) {
				size--;
			}
			this.data[key] = null;
		};
 
		this.isEmpty = function(){
			//return this.data.length == 0;
			return size == 0;
		};

		this.size = function(){
			//return this.data.length;
			return size;
		};
	}	
	var minDate = new Date(Date.parse(new Date()) - 1000 * 60 * 60 * 24 * 365 * 100);
	var maxDate = new Date();
	$(document).ready(function () {
		//初始化页面时,当游玩人的证件类型为"客服联系我时提供",隐藏"证件号码"输入框.
		$("select.select-left").each(function(){
			var cardNumDivId = "cardNumDivId" + $(this).attr("id").replace(/cardType/g,"") ;
			if ($(this).val() == "CUSTOMER_SERVICE_ADVICE") {
				$("#" + cardNumDivId).hide();
			}
		});
		 
		$("input[name='brithdayDate']" ).datepicker({dateFormat:'yy-mm-dd',minDate:minDate,maxDate:maxDate,changeYear: true,changeMonth: true, monthNamesShort:['一月','二月','三月','四月','五月','六月', '七月','八月','九月','十月','十一月','十二月'],dayNamesMin: ['日','一','二','三','四','五','六'],yearRange:"1910:2050"});
		
	});
	
	
	</script>
   
    <input type="hidden" id="forceTravellerInfoOptions" values="${forceTravellerInfoOptions}"/>
    <!--========= 游玩人信息 S ========-->
	<h3><s></s>游玩人信息<em>（*为必填项，请准确填写游客信息，以便购买保险。若填写的信息与真实信息不符，保险公司将无法承担赔偿责任。）</em><a href="javascript:void(0)" class="qijiashuoming" id="qijiashuoming" onmouseover="showDiv('qijiashuoming','shuoming1')">填写说明</a></h3>
	<div class="shuoming" id="shuoming1">
            <strong>填写说明：</strong><br>
            1.乘客姓名必须与登机时所使用证件上的名字一致，如“王小明”。<br>
            2.如持护照登机，使用中文姓名，必须确认护照上有中文姓名。<br>
            3.持护照登机的外宾，必须按照护照顺序区分姓与名，<br>&nbsp;&nbsp;例如“Smith/Black”，不区分大小写。
    </div>
	<ul class="player-info">
		
		<@s.if test="isLogin() && !receiversList.empty">
		<br/>
		<div id="receiverDivId" style="width:900px;">
		 <div class="lianxi_contant">
				<h4 class="title_add">请选择常用人姓名</h4>
				<ul class="lianxi_person">
    		<@s.iterator value="receiversList" status="st">
    		<!-- 进行格式布局显示,每一行显示5列. -->
    			<@s.if test="((#st.index) % 5 == 0)"><tr></@s.if>
    				<li>
    					<input type="checkbox"  value="${receiverId?if_exists}" card_type="${cardType?if_exists}" card_num="${cardNum?if_exists}" mobile="<@s.property value="@com.lvmama.comm.utils.StringUtil@hiddenMobile(mobileNumber)" />" receiverName="${receiverName?if_exists}" class="inp-checkbox" onclick="updateFetchTicketInfo3(this);"/> 
    					<label class="label_names">
					<#if receiverName?length gt 15>
     &nbsp;${receiverName?substring(0,14)}...&nbsp;<br/>
<#else>
     &nbsp;${receiverName?if_exists}&nbsp;<br/>
</#if>
</label>
    	 			</li>	
    	 		 <@s.if test="((#st.index) % 5 == 4)"></tr></@s.if>
    		</@s.iterator>
    	 </ul>
    	 </div>
    		</div>
    		<br/>
    		</@s.if>
    		
		<#list 1..fillTravellerNum as i>
			<li>
				<h1 class="list-player">
					<span class="players">
						第<em>${i}</em>位游玩人
					</span>
					<@s.if test="isLogin()">
						<span class="list-player-input">
							<input type="checkbox" class="middle" name="travellerList[${i}].useOffen" value="true" checked="checked"/>&nbsp;保存到常用姓名
						</span>
					</@s.if>
				</h1>
				</li>
				<ul class="add_xu">
					<li class="add_xu_add">
					<label class="laber_names"><em>*</em>游玩人姓名：</label>
					<span><input type="text" size="30" class="inp-txt" rName="rName" id="recrName${i}" name="travellerList[${i}].receiverName"  maxlength="16"/></span>&nbsp;&nbsp;
						<@s.if test="isLogin()">
							<!-- @Deprecated 此receiversList下拉框已弃用. -->
							<select id="receiversList" onchange="addReceInfo(${i},this)" style="visibility:hidden">
	       						<option value="">从常用联系人中选</option>
	       						<@s.iterator value="receiversList">
	   								<option value="${receiverId?if_exists}" email="${email?if_exists}" card_type="${cardType?if_exists}" card_num="${cardNum?if_exists}" mobile="${mobileNumber?if_exists}" name="${receiverName?if_exists}">${receiverName?if_exists}</option>
	   							</@s.iterator>
	       					</select>
						</@s.if>
						<#if i==1><span>
							<#if mainProdBranch.prodProduct.productType=='TRAFFIC' && mainProdBranch.prodProduct.subProductType=='TRAIN'>火车票以实名制预订，正确填写乘客人信息。
							<#else>复制联系人信息</#if>
							</span></#if>
					</li>
					<!-- 游玩人手机信息填写仅第一个游玩人为必填项 -->
					<li class="add_xu_add">
						<label class="laber_names"><#if i == 1 ><em>*</em></#if>游玩人手机：</label>
						<span><input type="text" size="30" class="inp-txt" rMobileNumber="rMobileNumber" id="mobileNumber${i}" name="travellerList[${i}].mobileNumber"/></span>
						<#if i==1><span>（免费接收短信，作为订购与取票凭证）</span></#if>
					</li>
					<span  id="cardInfoSpan${i}" style="display:inline">
					<li class="add_xu_add" id="cardInfoLi">
					<span>
						<label class="laber_names"><em>*</em>证件号码：</label>
						<select class="select-left" id="cardType${i}" cardType="cardType" name="travellerList[${i}].cardType" onChange="return checkType('cardType${i}','cardNum${i}')">
							<option value="">请选择证件类型</option>
							<option value="ID_CARD" selected="selected">身份证</option>
							<option value="HUZHAO">护照</option>
							<!-- 当游玩人为2人以上时，下拉框架中显示“客服联系我时提供”项 -->
							<#if fillTravellerNum &gt; 2><option value="CUSTOMER_SERVICE_ADVICE">客服联系我时提供</option></#if>
						</select>
						<span  id="cardNumDivId${i}" class="add_xu_add" cardTypeSpan='cardTypeSpan'>
							<input type="text" size="30" class="inp-txt" onBlur="return checkType('cardType${i}','cardNum${i}')" id="cardNum${i}" rTraveller="rTraveller" name="travellerList[${i}].cardNum"/> 
						</span> 
					</span>
					</li>
					</span> 
					
					<span id="childrenBrithdayInfoSpan${i}" name="childrenBrithdayInfoSpan" style="display:none">
						<li class="add_xu_add">
						   <span>
						   	<label id="brithdayDateLabelId" class="laber_names"><em id="brithdayDateEmId">*</em>出生年月：</label> <input id="brithdayDate${i}" name="brithdayDate" type="text" value=""  readonly="true"/>
							</span> 
						</li>
					</span>		
				 	<span id="genderSpan${i}" name="genderSpan" style="display:none">
						<li class="add_xu_add">
						   <span>
						   	<label id="genderLabelId" class="laber_names"><em id="genderEmId">*</em>游玩人性别：</label> 
						   	<input id="genderM${i}" name="travellerList[${i}].gender" type="radio" value="M" />男&nbsp;
						   	<input id="genderF${i}" name="travellerList[${i}].gender" type="radio" value="F" />女
							</span> 
						</li>
					</span>		
				<input type="hidden" id="receiverId${i}" name="travellerList[${i}].receiverId"/>
			</ul>	 
			 	</li>
			
		</#list>	
	</ul>
    <!--========= 游玩人信息 E ========-->   
    
     
