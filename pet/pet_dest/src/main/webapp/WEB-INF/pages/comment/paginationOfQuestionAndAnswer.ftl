 <div class="view-title">
	<h3><span>问答</span>——${place.name}</h3>
 </div>
 <dl class="jd_ask_content">
	<@s.iterator value="placeQAS">
		<dd>
			<h3 class="color_orange"><i class="jd_question_icon"></i>${question}</h3>
                        <p>${answer}</p>
                </dd>
	</@s.iterator>
 </dl>
<div class="pages rosestyle">
	<div class="Pages"> 
		<@s.property escape="false" value="@com.lvmama.comm.utils.Pagination@pagination(defaultPageSize,totalPage,'javascript:loadPaginationOfQuestionAndAnswer(argPage);',currentPage,'js')"/>
	</div>
</div>