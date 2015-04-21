
<!-- 搜索框开始 -->
		<div class="dstnt_left_tbox">
             <div class="dstnt_left_tbox_inr">
                   <div class="dstnt_tit1"><i class="dstnt_icon1"></i>目的地<span class="dstnt_tit_s1">开启精彩旅程</span></div>
                   <div class="dstnt_searchbox">

                        <div class="dstnt_search_r">
                            <input name="" type="text"  class="dstnt_sc_input" id="dstnt_sc_input" autocomplete="off" tabtype='<@s.property value="tabType"/>'>
                            <div class="dstnt_sc_btn" id="searchBtn"></div>
                        </div>
                        <div class="dstnt_search_l">
                            目的地：
                        </div>

                   </div><!--searchbox-->
                   <div class="dstnt_rcmd">
                       <h4 class="dstnt_tit3">热门推荐</h4>
                       <div class="dstnt_rcmd_place">
                       		<@s.iterator value="hotRecommandPlaceList" status="hot" >
                       			<a href='<@s.property value="url"/>'><@s.property value="title"/></a>
                       		</@s.iterator>
                       </div>
                   </div><!--dstnt_rcmd-->
               </div><!--inner-->
            </div>
		<!-- 搜索结束 -->

