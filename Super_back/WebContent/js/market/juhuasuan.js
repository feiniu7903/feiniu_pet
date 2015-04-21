//判断信息是否输入
function checkDetailProduct() {
	if ($("#applyCity").val() == "") {
		alert("请选择报名城市！");
		return false;
	}
	var val = $("#uploadFile").val();
	if (val == "") {
		alert("请上传产品主图！");
		return false;
	}
	var k = val.substr(val.indexOf("."));
	if (!(k.toLowerCase() == ".jpg" || k.toLowerCase() == ".jpeg"
			|| k.toLowerCase() == ".gif" || k.toLowerCase() == ".png")) {
		alert("上传图片格式错误");
		return;
	}
	if ($("#shortName").val() == "") {
		alert("请输入宝贝短名称！");
		return false;
	}
	if ($("#longName").val() == "") {
		alert("请输入宝贝长名称！");
		return false;
	}
	if ($("#originalPrice").val() == "" || $("#originalPrice").val() == 0) {
		alert("请输入宝贝原价！");
		return false;
	}
	if ($("#activityPrice").val() == "" || $("#activityPrice").val() == 0) {
		alert("请输入团购价格！");
		return false;
	}
	if ($("#activityPrice").val() > $("#originalPrice").val()) {
		alert("团购价应小于等于宝贝原价！");
		return false;
	}
	if ($("#discount").val() == "" || $("#discount").val() == 0) {
		alert("折扣不能为空！");
		return false;
	}
	if ($("#itemCount").val() == "" || $("#itemCount").val() == 0) {
		alert("团购数量不能为空！");
		return false;
	}
	if ($("#limitNum").val() == "" || $("#limitNum").val() == 0) {
		alert("每个ID的限购数量不能为空！");
		return false;
	}
	if ($("#locCity").val() == "") {
		alert("请输入所在城市！");
		return false;
	}
	if ($("#viewTicket").val() == "") {
		alert("请选择旅游类型！");
		return false;
	}
	if ($("#startArea2").val() == "") {
		alert("请选择出发地！");
		return false;
	}
	if ($("#endArea2").val() == "") {
		alert("请选择目的地！");
		return false;
	}
	return true;
}

/**
 * 获取出发地信息
 */
function changeStartPlace(obj){
	$("#startArea2").find("option").remove();
	var json = eval($("#destination").val());
	var option = "<option value=''>不限制</option>";
	for(var i=0;i<json.length;i++){
		if(json[i].code==$("#startArea").val()){
			for(var n=0;n<json[i].child.length;n++){
				var child = json[i].child[n];
				option += "<option parentIdx='"+i+"' idx='"+n+"' value='"+child.code+"'>"+child.name+"</option>";
			}
		}
	}
	$("#startArea2").append(option);
}
/**
 * 获取目的地信息
 */
function changePlace(obj){
	$("#endArea2").find("option").remove();
	$("#endArea3").find("option").remove();
	var json = eval($("#destination").val());
	var option = "<option value=''>不限制</option>";
	for(var i=0;i<json.length;i++){
		if(json[i].code==$("#endArea").val()){
			for(var n=0;n<json[i].child.length;n++){
				var child = json[i].child[n];
				option += "<option parentIdx='"+i+"' idx='"+n+"' value='"+child.code+"'>"+child.name+"</option>";
			}
		}
	}
	$("#endArea2").append(option);
	$("#endArea3").append("<option value=''>不限制</option>");
}
function changePlace2(obj){
	$("#endArea3").find("option").remove();
	var parentIdx = $(obj).find("option:selected").attr("parentIdx");
	var idx = $(obj).find("option:selected").attr("idx");
	var json = eval($("#destination").val());
	var option = "<option value=''>不限制</option>";
	if(json[parentIdx].child[idx].child!=undefined){
		var child = json[parentIdx].child[idx].child;
		for(var n=0;n<child.length;n++){
			option += "<option parentIdx='"+parentIdx+"' idx='"+idx+"' value='"+child[n].code+"'>"+child[n].name+"</option>";
		}
	}
	$("#endArea3").append(option);
}
/**
 * 计算商品的折扣
 * @returns {Boolean}
 */
function checkResult() {
	var originalPrice = $("#originalPrice").val(); //宝贝原价
	var activityPrice = $("#activityPrice").val(); //团购价格
	if ($("#activityPrice").val() > $("#originalPrice").val()) {
		alert("团购价应小于等于宝贝原价！");
		document.getElementById('activityPrice').value="";
		document.getElementById('discount').value=0;
		return false;
	}
	var result = parseInt(activityPrice) / parseInt(originalPrice) * 10;
	document.getElementById('discount').value = result.toFixed(1);
}