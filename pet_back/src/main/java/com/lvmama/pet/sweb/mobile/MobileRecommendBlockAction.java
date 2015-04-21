package com.lvmama.pet.sweb.mobile;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.comm.BackBaseAction;
import com.lvmama.comm.pet.po.mobile.MobileRecommendBlock;
import com.lvmama.comm.pet.po.mobile.MobileRecommendInfo;
import com.lvmama.comm.pet.po.place.Place;
import com.lvmama.comm.pet.service.mobile.MobileRecommendBlockService;
import com.lvmama.comm.pet.service.mobile.MobileRecommendInfoService;
import com.lvmama.comm.pet.service.place.PlaceService;
import com.lvmama.pet.utils.MobileConstant;

/**
 * 驴途 v3 推荐块管理 
 * @author qinzubo
 *
 */
@Results( {
	@Result(name = "success", location = "/WEB-INF/pages/back/mobile/mobile_recommend_block_list.jsp"),
	@Result(name = "mobileRecommendBlock", type="redirect", location="/mobile/mobileRecommendBlock.do?pageChannel=${pageChannel}"),
	@Result(name = "getMobileRecommendInfoSource", location="/WEB-INF/pages/back/mobile/mobile_recommend_info_source.jsp"),
	@Result(name = "source", location="/WEB-INF/pages/back/mobile/mobile_source_info.jsp"),
	@Result(name = "objectInfo", location="/WEB-INF/pages/back/mobile/mobile_bind_object.jsp")
})
public class MobileRecommendBlockAction extends BackBaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private MobileRecommendBlockService mobileRecommendBlockService; // 推荐块
	
	private MobileRecommendInfoService mobileRecommendInfoService; // 推荐信息

	private PlaceService placeService; 
	
	/**********/
	private List<List<Object>> recos = new ArrayList<List<Object>>();
	// 出发地  列表
	private List<MobileRecommendBlock> freeTourList = new ArrayList<MobileRecommendBlock>();
	private List<Place> mudidi = new ArrayList<Place>(); // 目的地 
	/********* 参数 ********/
	private String pageChannel; // 频道 （首页推荐，自由行推荐，目的地）
	private String delType; // 0:从根节点删 ， 1：子节点 
	private String stage = "1" ;// 产品类别  1:目的地；2：景区；3：酒店；

	private MobileRecommendBlock mobileRecommendBlock; // 
	
	private MobileRecommendInfo mobileRecommendInfo; // 

	/**
	 * 需要添加到推荐表中的对象Id（目的地酒店景区产品等的id）
	 */
	private Long objectId;
    private String keywords;

	/**
	 * 推荐块信息列表页 .
	 * @return
	 * @throws Exception
	 */
	@Action("/mobile/mobileRecommendBlock")
	public String execute() throws Exception {
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("parent", "true");
		List<MobileRecommendBlock> mList = mobileRecommendBlockService.queryMobileRecommendBlockByParam(param);
		for(MobileRecommendBlock mrb:mList) {
			List<Object> lm = new ArrayList<Object>();
			lm.add(mrb);
			
			// 子节点 
			Map<String,Object> m = new HashMap<String,Object>();
		    m.put("parentId", mrb.getId());
		    List<MobileRecommendBlock> mri = mobileRecommendBlockService.queryMobileRecommendBlockByParam(m);
		    lm.add(mri);
		    recos.add(lm);
		}
		
		return SUCCESS;
	}
	
	
	
	/**
	 * 添加模块
	 * @throws Exception
	 */
	public void addMobileRecommendBlock() {
		String json = "{\"flag\":\"false\"}";
		try {
			if (mobileRecommendBlock != null && StringUtils.isNotEmpty(mobileRecommendBlock.getBlockName())) {
				mobileRecommendBlock.setIsValid("Y"); // Y or N
				mobileRecommendBlock.setCreatedTime(new Date()); // 新增日期 
				this.mobileRecommendBlockService.insertMobileRecommendBlock(mobileRecommendBlock);
			}
			json = "{\"flag\":\"true\"}";
		}catch(Exception e){
			e.printStackTrace();
		}
		
		this.responseWrite(json);
	}
	
	/**
	 * 查询模块 
	 */
	public void getMobileRecommendBlockById() {
		MobileRecommendBlock mobileRecomBlock = null;
		try {
			 mobileRecomBlock = mobileRecommendBlockService.getMobileRecommendBlockById(mobileRecommendBlock.getId());
		}catch(Exception e){
			e.printStackTrace();
		}
		this.responseWrite(JSONArray.fromObject(mobileRecomBlock).toString());
	}
	
	/**
	 * 新增 ，修改子项 . 
	 * @return
	 * @throws Exception
	 */
	public String updateRecommendBlock(){
		MobileRecommendBlock target = null;
		try{
			if(null != mobileRecommendBlock.getId()) {
				target = mobileRecommendBlockService.getMobileRecommendBlockById(mobileRecommendBlock.getId());
			}
			
			// 修改
			if(null != target && null != mobileRecommendBlock && !StringUtils.isEmpty(mobileRecommendBlock.getBlockName())) {
				target.setBlockName(mobileRecommendBlock.getBlockName());
				target.setBlockType(mobileRecommendBlock.getBlockType());
				target.setObjectId(mobileRecommendBlock.getObjectId());
				target.setObjectType(mobileRecommendBlock.getObjectType());
				target.setSeqNum(mobileRecommendBlock.getSeqNum());
				target.setReserve1(mobileRecommendBlock.getReserve1());
				target.setReserve2(mobileRecommendBlock.getReserve2());
				target.setReserve3(mobileRecommendBlock.getReserve3());
				target.setReserve4(mobileRecommendBlock.getReserve4());
				target.setReserve5(mobileRecommendBlock.getReserve5());
				target.setCreatedTime(new Date());
				mobileRecommendBlockService.updateMobileRecommendBlock(target);
			} else { // 新增 
				mobileRecommendBlock.setCreatedTime(new Date());
				mobileRecommendBlockService.insertMobileRecommendBlock(mobileRecommendBlock);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return "mobileRecommendBlock";
	}
	
	/**
	 * 删除 . 
	 */
	public void delMobileRecommendBlock() {
		String json = "{\"flag\":\"false\"}";
		try{
			mobileRecommendBlockService.deleteMobileRecommendBlockAndInfo(mobileRecommendBlock,delType);
		}catch(Exception e){
			e.printStackTrace();
		}
		json = "{\"flag\":\"true\"}";
		this.responseWrite(json);
	}
	
	/**
	 * recommendInfo 新增页面 . 
	 * @return
	 */
	public String getMobileRecommendInfoSource() {
		// mobileRecommendBlock.id; //2 
		mobileRecommendBlock = mobileRecommendBlockService.getMobileRecommendBlockById(mobileRecommendBlock.getId());
		if(pageChannel.equals(MobileConstant.PAGE_CHNNEL_INDEX)) { // 首页推荐 
			return "getMobileRecommendInfoSource";
		} else { // 目的地
		    // 获取目的地 
			if(StringUtils.isEmpty(keywords) && null == objectId) {
				return "source";  
			}
			pagination=initPage();
			pagination.setPageSize(15);
			
			Map<String, Object> param = new HashMap<String, Object>();
			//param.put("stage", stage);
			param.put("placeId", objectId);
			param.put("isValid", "Y");
			param.put("name", keywords);
			pagination.setTotalResultSize(placeService.countPlaceListByParam(param));
			if(pagination.getTotalResultSize()>0){
				param.put("startRows", pagination.getStartRows());
				param.put("endRows", pagination.getEndRows());
				mudidi = placeService.queryPlaceListByParam(param);
			}
			pagination.buildUrl(getRequest());
			return "source";
		} 
	}
	
	/**
	 * 对象列表. 
	 * @return list 
	 */ 
	public String getObjectInfoList() {
		 // 获取目的地 
		if(StringUtils.isEmpty(keywords) && null == objectId) {
			return "objectInfo";  
		}
		pagination=initPage();
		pagination.setPageSize(15);
		
		Map<String, Object> param = new HashMap<String, Object>();
		//param.put("stage", stage);
		param.put("placeId", objectId);
		param.put("isValid", "Y");
		param.put("name", keywords);
		pagination.setTotalResultSize(placeService.countPlaceListByParam(param));
		if(pagination.getTotalResultSize()>0){
			param.put("startRows", pagination.getStartRows());
			param.put("endRows", pagination.getEndRows());
			mudidi = placeService.queryPlaceListByParam(param);
		}
		pagination.buildUrl(getRequest());
		return "objectInfo";
	}
	
	/**
	 * 复制 block 以及block下的blockInfo. 
	 */
	public void copyRecommendBlock() {
		String json = "{\"flag\":\"false\"}";
		try{
			if(null == mobileRecommendBlock || null == mobileRecommendBlock.getId()) {
				this.responseWrite(json);
				return ;
			}
			//复制block 
			MobileRecommendBlock mrb = mobileRecommendBlockService.getMobileRecommendBlockById(mobileRecommendBlock.getId());
			if(null != mrb) {
				mrb.setId(null);
				MobileRecommendBlock t_mr = mobileRecommendBlockService.insertMobileRecommendBlock(mrb);
				// 复制block下的子元素. 
				Map<String,Object> param = new HashMap<String,Object>();
				param.put("recommendBlockId", mobileRecommendBlock.getId());
				List<MobileRecommendInfo>  infoList = mobileRecommendInfoService.queryMobileRecommendInfoList(param);
				if(null != infoList && infoList.size() > 0) {
					for(int i = 0;i < infoList.size() ; i++) {
						MobileRecommendInfo mi = infoList.get(i);
						mi.setId(null);
						mi.setRecommendBlockId(t_mr.getId());
						mobileRecommendInfoService.insertMobileRecommendInfo(mi);
					}
				}
			}
			json = "{\"flag\":\"true\"}";
		}catch(Exception e){
			e.printStackTrace();
			json = "{\"flag\":\"false\"}";
		}
		this.responseWrite(json);
	}
	
	private void responseWrite(String info){
		try {
			this.getResponse().setContentType("text/html; charset=utf-8");
			this.getResponse().getWriter().write(info);
		} catch (Exception e) {
			log.info("com.lvmama.pet.sweb.seo:"+e.getMessage());
		}
	}
	
	public List<List<Object>> getRecos() {
		return recos;
	}

	public void setRecos(List<List<Object>> recos) {
		this.recos = recos;
	}

	public String getPageChannel() {
		return pageChannel;
	}

	public void setPageChannel(String pageChannel) {
		this.pageChannel = pageChannel;
	}
	
	public void setMobileRecommendBlockService(
			MobileRecommendBlockService mobileRecommendBlockService) {
		this.mobileRecommendBlockService = mobileRecommendBlockService;
	}

	public void setMobileRecommendInfoService(
			MobileRecommendInfoService mobileRecommendInfoService) {
		this.mobileRecommendInfoService = mobileRecommendInfoService;
	}

	public MobileRecommendBlock getMobileRecommendBlock() {
		return mobileRecommendBlock;
	}

	public void setMobileRecommendBlock(MobileRecommendBlock mobileRecommendBlock) {
		this.mobileRecommendBlock = mobileRecommendBlock;
	}
	
	
	public String getDelType() {
		return delType;
	}
	public void setDelType(String delType) {
		this.delType = delType;
	}
	
	public List<MobileRecommendBlock> getFreeTourList() {
		return freeTourList;
	}

	public List<Place> getMudidi() {
		return mudidi;
	}



	public void setMudidi(List<Place> mudidi) {
		this.mudidi = mudidi;
	}



	public Long getObjectId() {
		return objectId;
	}



	public void setObjectId(Long objectId) {
		this.objectId = objectId;
	}



	public void setPlaceService(PlaceService placeService) {
		this.placeService = placeService;
	}


	public void setFreeTourList(List<MobileRecommendBlock> freeTourList) {
		this.freeTourList = freeTourList;
	}
	
	public String getKeywords() {
		return keywords;
	}


	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}
	
	public MobileRecommendInfo getMobileRecommendInfo() {
		return mobileRecommendInfo;
	}

	public String getStage() {
		return stage;
	}



	public void setStage(String stage) {
		this.stage = stage;
	}



	public void setMobileRecommendInfo(MobileRecommendInfo mobileRecommendInfo) {
		this.mobileRecommendInfo = mobileRecommendInfo;
	}
}
