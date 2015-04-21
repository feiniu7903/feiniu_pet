function setKeyWord(){
  		return true;
  	}
function keyWordFocus(){
  	return true;
}  	
String.prototype.trim = function () {
return this.replace(/^\s+|\s+$/g, "");
};

$(document).ready(function(){
$("#keyWor").suggest("/ajax/ajax!getJsonByKeyWord.do",
{});
});




function callback(item) {
	alert(item.name);
}