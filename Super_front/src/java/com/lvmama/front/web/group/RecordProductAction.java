/**
 * 
 */
package com.lvmama.front.web.group;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.comm.bee.service.GroupDreamService;
import com.lvmama.comm.vo.Constant;
import com.lvmama.front.web.home.ToPlaceOnlyTemplateHomeAction;

/**
 * 
 * 团购疯抢记录Action
 * @author songlianjun
 *
 */
@Results( { 
	@Result(name = "record", location = "/WEB-INF/pages/group/record.ftl", type = "freemarker")
	
})
public class RecordProductAction  extends ToPlaceOnlyTemplateHomeAction{
	private GroupDreamService groupDreamService;
	private Long page;
	private Long pageSize;
	private Long totalPage;
	private Long totalRows;
	private List<Map> recordList ; 
	private PageRange pageRange;
	
	private static Long dispayMaxPage=7L;
	
	/**
	 * 查询团购记录
	 */
	@Action("/group/record")
	public String execute() throws Exception {
		HttpSession session = getRequest().getSession(true);
		if (getFromPlaceId() != null) {
			session.setAttribute("fromPCode", getFromPlaceCode());
			session.setAttribute("fromPid", getFromPlaceId());
			session.setAttribute("fromPName", getFromPlaceName());
		} else if ((Long) session.getAttribute("fromPid") != null) {
			this.fromPlaceCode = (String) session.getAttribute("fromPCode");
			this.fromPlaceId = (Long) session.getAttribute("fromPid");
			this.fromPlaceName = (String) session.getAttribute("fromPName");
		}
		init(Constant.CHANNEL_ID.CH_TUANGOU.name());
		// TODO Auto-generated method stub
		//
		Map<String,Object> resultMap = groupDreamService.getOnlineAndOffelineProductByChannel(page, pageSize);
		recordList = (List<Map>)resultMap.get("recordList");
		Map pageInfo = (Map)resultMap.get("pageInfo");
		totalPage = (Long)pageInfo.get("totalPage");
		totalRows = (Long)pageInfo.get("totalRows");
		calcPageRange();
		return "record";
	}
	/**
	 * 计算每次显示的页码
	 */
	public void calcPageRange(){
		if(page==null){
			page=1L;
		}
		if(pageSize==null){
			pageSize=10L;
		}
		if(this.totalPage<=dispayMaxPage) {
			pageRange = new PageRange(1L,this.totalPage);
		}else{
			Long min =this.page - 1;
			Long max = this.page + 1;
			if (min < 1) {
				max += (1-min);
				min = 1L;
			}
			if ( max > this.totalPage ) {
				min -= ( max - totalPage );
				max = this.totalPage;
			}
			min = (min>1) ? min : 1;
			pageRange = new PageRange(min,max);
		}
	}
	public Long getPage() {
		return page;
	}
	public void setPage(Long page) {
		this.page = page;
	}
	public Long getPageSize() {
		return pageSize;
	}
	public void setPageSize(Long pageSize) {
		this.pageSize = pageSize;
	}
	public List<Map> getRecordList() {
		return recordList;
	}
	public Long getTotalPage() {
		return totalPage;
	}
	public Long getTotalRows() {
		return totalRows;
	}
	public PageRange getPageRange() {
		return pageRange;
	}
	public void setGroupDreamService(GroupDreamService groupDreamService) {
		this.groupDreamService = groupDreamService;
	}
}