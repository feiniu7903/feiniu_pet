String.prototype.trim = function () {
return this.replace(/^\s+|\s+$/g, "");
};
String.prototype.replaceToLower = function () {
	return this.replace(/[^\u4E00-\u9FA5\uF900-\uFA2DA-Za-z0-9\-\u300a\u300b\uff08\uff09\u00b7]+/g, "").toLocaleLowerCase();//去除特殊字符与空格,大写字母转换为小写字母
	//return this.replace(/[\.\'\"]+/g, "").toLocaleLowerCase();//防止SQL恶意注入
};