//去除空格
function trim(string) {
    return string.replace(/(^\s*)|(\s*$)/g, "")
}

//读取Cookie
function getCookie(objName) {
	var arrStr = document.cookie.split("; ");
	for (var i = 0; i < arrStr.length; i++) {
		var temp = arrStr[i].split("=");
		if (temp[0] == objName) {
			return temp[1];
		}
	}
	return '';
}

//检测格式
function checkFormat(format, input) {
    var myReg = new RegExp(format);
    return myReg.test(input)
}

