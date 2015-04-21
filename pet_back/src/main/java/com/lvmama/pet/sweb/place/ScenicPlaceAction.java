package com.lvmama.pet.sweb.place;

import java.util.List;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import com.lvmama.comm.pet.po.place.PlaceHotelNotice;
import com.lvmama.comm.pet.service.place.PlaceHotelNoticeService;
import com.lvmama.comm.utils.homePage.PlaceUtils;
import com.lvmama.comm.vo.Constant;

/**
 * 景区管理
 * @author duanshuailiang
 *
 */
@Results( {
	@Result(name = "scenicList", location = "/WEB-INF/pages/back/place/scenic/scenic_list.jsp"),
	@Result(name = "scenicAdd", location = "/WEB-INF/pages/back/place/scenic/scenic_add.jsp"),
	@Result(name = "scenicSaleInfoAdd", location = "/WEB-INF/pages/back/place/scenic/scenic_sale_info_add.jsp"),
	@Result(name = "scenicDescAdd", location = "/WEB-INF/pages/back/place/comm/desc_info_add.jsp"),
	@Result(name = "scenicTrafficInfoAdd", location = "/WEB-INF/pages/back/place/comm/traffic_info_add.jsp"),
	@Result(name = "scenicRelationProduct", location = "/WEB-INF/pages/back/place/scenic_relation_product.jsp"),
	@Result(name = "hotelDescAdd", location = "/WEB-INF/pages/back/place/comm/desc_info_add.jsp"),
	//公告
    @Result(name = "placeHotelNoticeList", location = "/WEB-INF/pages/back/place/notice/placeHotelNoticeList.jsp", type = "dispatcher"),
	//目的地探索
	@Result(name = "scenicDescExploreAdd", location = "/WEB-INF/pages/back/place/comm/destination_explore_add.jsp"),
	//服务保障
	@Result(name = "scenicServiceEnsure", location = "/WEB-INF/pages/back/place/comm/serve_ensure_add.jsp")
})
public class ScenicPlaceAction  extends AbstractPlaceAction {
	private static final long serialVersionUID = 1L;
	private PlaceHotelNotice placeHotelNotice = new PlaceHotelNotice();
	private PlaceHotelNoticeService placeHotelNoticeService;
	private List<PlaceHotelNotice> placeNoticeList;

	@Action("/place/scenicList")
	public String execute() throws Exception {
		list();
		initSubjectList();
		return "scenicList";
	}
	@Action("/place/scenicEdit")
	public String scenicEdit() throws Exception {
		edit();
		return "scenicAdd";
	}
	@Action("/place/scenicAdd")
	public String scenicAdd() throws Exception {
		add();
		return "scenicAdd";
	}

	@Action("/place/scenicSaleInfoAdd")
	public String scenicSaleInfoAdd() throws Exception {
		initPlaceByPlaceId();
		return "scenicSaleInfoAdd";
	}
	
	@Action("/place/saveScenicSaleInfo")
	public String saveScenicSaleInfo() throws Exception {
		try{
			update();
			if(this.getMsg()==null||"".equals(this.getMsg()))
				this.setMsg("修改成功!");
		}catch(Exception ex){
			ex.printStackTrace();
			this.setMsg("修改失败!");
		}
		
		initPlaceByPlaceId();
		return "scenicSaleInfoAdd";
	}
	
	@Action("/place/scenicDescAdd")
	public String scenicDescAdd() throws Exception {
		initPlaceByPlaceId();
		return "scenicDescAdd";
	}
	
	//目的地探索
	@Action("/place/scenicDescExploreAdd")
	public String scenicDescExploreAdd() throws Exception {
		initPlaceByPlaceId();
		return "scenicDescExploreAdd";
	}
	
	
	//服务保障
	@Action("/place/scenicServiceEnsure")
	public String scenicServiceEnsure() throws Exception {
		initPlaceByPlaceId();
		return "scenicServiceEnsure";
	}
	
	@Action("/place/scenicTrafficInfoAdd")
	public String scenicTrafficInfoAdd() throws Exception {
		initPlaceByPlaceId();
		return "scenicTrafficInfoAdd";
	}	
	
	@Action("/place/saveScenicDesc")
	public String saveScenicDesc() throws Exception {
		try{
			update();
			if(this.getMsg()==null||"".equals(this.getMsg()))
				this.setMsg("修改成功!");
		}catch(Exception ex){
			ex.printStackTrace();
			this.setMsg("修改失败!");
		}
		initPlaceByPlaceId();
		return "scenicDescAdd";
	}
	
	@Action("/place/doScenicSave")
	public String doSave() throws Exception {
		try{
			save();
			this.setMsg("添加成功!");
		}catch(Exception ex){
			ex.printStackTrace();
			this.setMsg("添加失败!");
		}
		
		return "scenicAdd";
	}
	@Action("/place/doScenicUpdate")
	public String doUpdate() throws Exception {
		try{
			update();
			this.outputToClient("success");
		}catch(Exception ex){
			ex.printStackTrace();
			this.outputToClient("error");
		}
		return null;
	}
	
	/**
	 * 
	 * 酒店和景区关联产品用到
	 */
	@Action("/place/scenicRelationProduct")
	public String scenicrelationProduct() throws Exception {
		this.relationProduct();
		return "scenicRelationProduct";
	}
	@Action("/place/saveProductSeq")
	public String savePhotoSeq() throws Exception {
		this.saveProductSeq();		
		return null;
	}
	/**
	 * 公告
	 * @return
	 * @throws Exception
	 * @author:nixianjun 2013-7-5
	 */
	@Action("/place/scenic/announcementShow")
	public String announcementShow() throws Exception {
	    placeHotelNotice.setNoticeType(PlaceUtils.SCENIC);
        this.placeHotelNotice.setPlaceId(Long.valueOf(super.getPlaceId()));
	    placeNoticeList = placeHotelNoticeService.queryByHotelNotice(placeHotelNotice);
		return "placeHotelNoticeList";
 	}
	/**
	 * 设定stage为景区
	 */
	@Override
	public void setCurrentStage() {
		this.setStage(String.valueOf(Constant.STAGE_OF_SCENIC_SPOT));
	}
	public PlaceHotelNotice getPlaceHotelNotice() {
		return placeHotelNotice;
	}
	public void setPlaceHotelNotice(PlaceHotelNotice placeHotelNotice) {
		this.placeHotelNotice = placeHotelNotice;
	}
	public void setPlaceHotelNoticeService(
			PlaceHotelNoticeService placeHotelNoticeService) {
		this.placeHotelNoticeService = placeHotelNoticeService;
	}
	public List<PlaceHotelNotice> getPlaceNoticeList() {
		return placeNoticeList;
	}
	public void setPlaceNoticeList(List<PlaceHotelNotice> placeNoticeList) {
		this.placeNoticeList = placeNoticeList;
	}
	
}

