/** 
 * @param {Object} form  必须为jquery对象
 * @param {Object} type
 * @memberOf {TypeName} 
 * @return {TypeName} 
 */
function ProductValidator(form,type,has_self_pack){
	this.form=form;
	this.type=type;
	this.has_self_pack = has_self_pack;
	return this;
}

ProductValidator.prototype.validate=function(){
	var res=this.validateBase();
	if(res){
		if(this.type=='ROUTE'){
			res=this.validateRoute();
		}
        if(this.type=='OTHER'){
			res=this.validateOther();
		}
	}
	
	return res;
};


/**
 * 基本验证
 */
ProductValidator.prototype.validateBase=function(){
	var productName=this.form.find("input[name=product.productName]").val();
	if($.trim(productName)==''){
		alert("产品名称不可以为空");
		return false;
	}
	
	if($.trim(this.form.find("input[name=product.bizcode]").val())==''){
		alert("产品编号不可以为空");
		return false;
	}
	
	var filialeName=this.form.find("select[name=product.filialeName] :selected").val();
	if($.trim(filialeName)==''){
		alert("所属分公司不可以为空");
		return false;
	}
	
	
	var manageId=this.form.find("input[name=product.managerId]").val();
	if(typeof(manageId)=='undefined'||$.trim(manageId)==''){
		alert("产品经理不能为空");
		return false;
	}
	
	
	//上下线时间校验
	var onlineTime = this.form.find("input[name=product.onlineTime]").val();
	if($.trim(onlineTime)!=''){
		if(!checkDate(onlineTime)){
			alert(" 请输入合法的上线时间，类型为日期，格式为YYYY-MM-DD");
			return false;
		}
	}
	var offlineTime = this.form.find("input[name=product.offlineTime]").val();
	if($.trim(offlineTime)!=''){
		if(!checkDate(offlineTime)){
			alert(" 请输入合法的下线时间，类型为日期，格式为YYYY-MM-DD");
			return false;
		}
	}
	//返现校验
	var isRefundable = this.form.find("input[name=product.isRefundable]:checked").val();
	if(isRefundable == "Y") {
		var isManualBonus = this.form.find("input[name=product.isManualBonus]:checked").val();
		//手动返现,需设置返现金额
		if(isManualBonus == "Y") {
			var maxCashRefund = this.form.find("input[name=product.maxCashRefund]").val();
			//不为空时校验格式
			if($.trim(maxCashRefund) == "") {
				alert("返现金额不能为空");
				return false;
			}
			 if(isNaN(maxCashRefund)||!/^(\d+)$/.test(maxCashRefund)){
				 alert("返现金额必须为整数!");
				 return;
			 }
		}
	}
	//验证不定期提前预订天数
	if(current_product_type == 'HOTEL' || current_product_type == 'ROUTE') {
		var isAperiodic = this.form.find("input[type=radio][name=product.isAperiodic]:checked").val();
		var isAperiodicInput = this.form.find("input[type=hidden][name=product.isAperiodic]");
		if(isAperiodic == "true" || (isAperiodicInput != undefined  && isAperiodicInput.val() == "true")) {
			var aheadBookingDays = this.form.find("input[name=product.aheadBookingDays]").val();
			if($.trim(aheadBookingDays) == "") {
				this.form.find("input[name=product.aheadBookingDays]").focus();
				alert("酒店或线路的提前预约天数不能为空");
				return false;
			}
			var re = /^[1-9]\d*$/;
			if (!re.test(aheadBookingDays)){
				this.form.find("input[name=product.aheadBookingDays]").focus();
				alert("提前预订天数必须为大于0的整数");
				return false;
			}
		}
	}
	
	//验证行程 （天|晚）
	var days = this.form.find("input[name=product.days]").val();
	if(typeof(days) !='undefined' && $.trim(days)!=''){
		var re = /^[1-9]\d*$/;
		if (!re.test(days)){
			this.form.find("input[name=product.days]").focus();
			alert(" 行程天数必须为大于0的整数");
			return false;
		}
	}
	var nights = this.form.find("input[name=product.nights]").val();
	if(typeof(nights) !='undefined' && $.trim(nights)!=''){
		var re = /^[0-9]\d*$/;
		if (!re.test(nights)){
			this.form.find("input[name=product.nights]").focus();
			alert(" 行程晚数必须为大于等于0的整数");
			return false;
		}
	}
	
	//提前确定成团小时数校验
	var aheadConfirmHours = this.form.find("input[name=product.aheadConfirmHours]").val();
	if(typeof(aheadConfirmHours) !='undefined' && $.trim(aheadConfirmHours)!=''){
		var re = /^[0-9]\d*$/;
		if (!re.test(aheadConfirmHours)){
			this.form.find("input[name=product.aheadConfirmHours]").focus();
			alert(" 提前确定成团小时数必须为大于等于0的整数");
			return false;
		}
	}
	//最少成团人数校验
	var initialNum = this.form.find("input[name=product.initialNum]").val();
	if(typeof(initialNum) !='undefined' && $.trim(initialNum)!=''){
		var re = /^[1-9]\d*$/;
		if (!re.test(initialNum)){
			this.form.find("input[name=product.initialNum]").focus();
			alert(" 最少成团人数必须为大于1的整数");
			return false;
		}
	}
	//定金校验
	var payDeposit = this.form.find("input[name=product.payDeposit]").val();
	if(typeof(payDeposit) !='undefined' && $.trim(payDeposit)!=''){
		var re = /^[0-9]\d*$/;
		if (!re.test(payDeposit)){
			this.form.find("input[name=product.payDeposit]").focus();
			alert(" 定金必须为大于1的整数");
			return false;
		}
	}
	//新增签证销售产品时验证
	if(has_visa_prod){
			//签证有效期校验
			var visavalidTime=this.form.find("input[name=product.visaValidTime]").val();
			if(typeof(visavalidTime)=='undefined' || $.trim(visavalidTime)==''){
				alert(" 签证有效期不能为空");
				return false;
			}

			var visaMaterialAheadDay=this.form.find("input[name=product.visaMaterialAheadDay]").val();
			if(typeof(visaMaterialAheadDay)!='undefined' && $.trim(visaMaterialAheadDay)!=''){
				var re = /^[1-9]\d*$/;
				if (!re.test(visaMaterialAheadDay)){
					this.form.find("input[name=product.visaMaterialAheadDay]").focus();
					alert("材料截止收取提前必须为大于0的整数");
					return false;
				}
			}
			//是否自备签校验
			 var visaSelfSign=this.form.find("input[name=product.visaSelfSign]:checked").val();
				if(typeof(visaSelfSign)=='undefined'||$.trim(visaSelfSign)==''){
					alert("是否自备签为必填项");
					return false;
				}
			
	}
	var has_route_visa_prod=false;
	if(current_product_type=="ROUTE"){
		var subProductType=this.form.find("input[name=product.subProductType]:checked").val();
		if("FREENESS_FOREIGN"==subProductType||"GROUP_FOREIGN"==subProductType){
			has_route_visa_prod=true;
		}
	}
	if(has_visa_prod||has_route_visa_prod){
		//签证国家校验
		var country=this.form.find("input[name=product.country]").val();
		if(typeof(country)=='undefined'||$.trim(country)==''){
			alert("签证国家不能为空");
			return false;
		}
		//出签城市校验
		var city=this.form.find("select[name=product.city] :selected").val();
		if(typeof(city)=='undefined'||$.trim(city)==''){
			alert("送签城市不能为空");
			return false;
		}
		//签证类型校验
	    var visaType=this.form.find("input[name=product.visaType]:checked").val();
		if(typeof(visaType)=='undefined'||$.trim(visaType)==''){
			alert("签证类型不能为空");
			return false;
		}
			
	}
	/***/
	
	//只有门票现在是没有产品子类型(现门票加上子类型验证)
	if(!has_visa_prod){
		var subProductType=this.form.find("input[name=product.subProductType]:checked").val();
		if(typeof(subProductType)=='undefined'||$.trim(subProductType)==''){
			alert("产品子类型必须选中一个");
			return false;
		}
	}
	
//	var cashRefund=this.form.find("input[name=cashRefundYuan]").val();
//	if($.trim(cashRefund)==''){
//		alert("返现金额不可以为空");
//		return false;
//	}
//	var v=parseInt(cashRefund);
//	if(v==NaN||v<0){
//		alert("返现金额不可以为空并且不可以小于0");
//		return false;
//	}
	if(this.form.find("input[name=channel][value=TUANGOU]").attr("checked") && this.form.find("input[name=channel][value=FRONTEND]").attr("checked")){
		alert("渠道团购和前台只能选择一个");
		return false;
	}
	if(this.form.find("input[name=channel][value=TUANGOU]").attr("checked")){
		var pg=$("input[name=product.groupMin]").val();
		if($.trim(pg)==''){
			alert("选择团购渠道后 团购最小成团人数 不可以为空");
			return false;
		}
	}
	return true;
};


/**
 * 验证路线特有的信息
 * return true成功，false时代表出现问题
 */
ProductValidator.prototype.validateRoute=function(){
	var subProductType=this.form.find("input[name=product.subProductType]:checked").val();
	if(subProductType!=='FREENESS'){
		var groupType=this.form.find("input[name=product.groupType]:checked").val();
		if(typeof(groupType)==undefined||groupType==''){
			alert("组团类型必须选中");
			return false;
		}
	}
	var isAperiodic = this.form.find("input[type=radio][name=product.isAperiodic]:checked").val();
	var isMultiJourney = this.form.find("input[type=radio][name=product.isMultiJourney]:checked");
	if(typeof(isMultiJourney) != 'undefined' && isMultiJourney != null) {
		if(isAperiodic == "true" && isMultiJourney.val() == "Y") {
			alert("不定期不能设置为多行程");
			return false;
		}
	}
	var isAperiodicInput = this.form.find("input[type=hidden][name=product.isAperiodic]");
		//不定期不做电子合同校验
	if(isAperiodic == "true" || (isAperiodicInput != undefined  && isAperiodicInput.val() == "true")) {
		this.form.find("input[name=productEContract]").attr("checked", false);
	}else{
		if(this.form.find("input[name=productEContract]").attr("checked")){//代表选中状态
			var eContractTemplate=this.form.find("select[name=prodEContract.eContractTemplate] :selected").val();
			if($.trim(eContractTemplate)==''){
				alert("电子合同范本必须选中");
				return false;
			}
			
			var productTravelFormalities=this.form.find("input[name=productTravelFormalities]:checked");
			if(typeof(productTravelFormalities)=='undefined'||productTravelFormalities.size()==0){
				alert("旅游手续不可以为空");
				return false;
			}
			for(var i=0;i<productTravelFormalities.length;i++){
				if(productTravelFormalities[i]=='OTHERS'){
					if($.trim(this.form.find("input[name=prodEContract.otherTravelFormalities]").val())==''){
						alert("选中旅游手续其他时文本框不可以为空");
						return false;
					}
					break;
				}
			}
			
			var productGroupTypes=this.form.find("input[name=productGroupTypes]:checked");
			if(typeof(productGroupTypes)=='undefined'||productGroupTypes.size()==0){
				alert("组团方式必须选中一个");
				return false;
			}
			/**
			if($.trim(this.form.find("input[name=prodEContract.agencyAddress]").val())==''){
				alert("地接社名称/地址/联系人/联系方式/电话 不可以为空");
				return false;
			}
			*/	
		}
	}
	return true;
};


/**
 * 验证其他产品特有的信息
 * return true成功，false时代表出现问题
 */
ProductValidator.prototype.validateOther = function(){

    var subProductType=this.form.find("input[name=product.subProductType]:checked").val();

    if(subProductType=='INSURANCE'){
		var applicableTravelDaysLimit = this.form.find("input[name=product.applicableTravelDaysLimit]").val();
		var applicableTravelDaysCaps = this.form.find("input[name=product.applicableTravelDaysCaps]").val();

		if(typeof(applicableTravelDaysLimit)==undefined || $.trim(applicableTravelDaysLimit)==''){
            alert(" 适用行程天数下限不能为空");
            return false;
		}else{
            var re = /^[1-9]\d*$/;
            if (!re.test(applicableTravelDaysLimit)){
                this.form.find("input[name=product.applicableTravelDaysLimit]").focus();
                alert(" 适用行程天数下限必须为大于0的整数");
                return false;
            }
        }
        if(typeof(applicableTravelDaysCaps)==undefined||$.trim(applicableTravelDaysCaps)==''){
            alert(" 适用行程天数上限不能为空");
            return false;
        }else{
            var re = /^[1-9]\d*$/;
            if (!re.test(applicableTravelDaysCaps)){
                this.form.find("input[name=product.applicableTravelDaysCaps]").focus();
                alert(" 适用行程天数上限必须为大于0的整数");
                return false;
            }
		}
	}

	return true;
};

function checkNumber(){
	
}

function checkDate(date)
{
	var re= /^(((19)|(20))[0-9][0-9])[-](1[0-2]|0[1-9])[-](3[0,1]|[1,2][0-9]|0[1-9])$/;
	return re.test(date);
}
