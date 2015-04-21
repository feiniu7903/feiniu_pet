
$(function() {
	// keyword关键字搜索 
	$("#keyword").keypress(function(e) {
		var key = e.which;
		if( key == 13) {
			// 提交表单 
			$("#hidden_keyword").val(encodeURIComponent($("#keyword").val()));
			$("#key_search").submit();
		}
	});
	
});

// 改变主题 
function changeSubjects(s_subjects) {
	var str = ' <li data-type="subjects" onclick="bindClick(this);" data-value=" ">全部主题</li>';
	if(null != s_subjects && s_subjects.length > 0) {
		s_subjects = s_subjects.replace("[","").replace("]","");
		if($.trim(s_subjects)!='') {
			s_subjects = s_subjects.split(',');
			for(var i = 0; i < s_subjects.length;i++) {
				str += '<li data-type="subjects" onclick="bindClick(this);" data-value="'+$.trim(s_subjects[i])+'">'+s_subjects[i]+'</li>';
			}
		}
	}
	$("#tab_subjects").html(str);
}
