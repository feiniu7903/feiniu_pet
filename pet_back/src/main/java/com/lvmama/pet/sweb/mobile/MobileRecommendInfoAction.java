package com.lvmama.pet.sweb.mobile;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.comm.BackBaseAction;
import com.lvmama.comm.pet.po.mobile.MobileRecommendBlock;
import com.lvmama.comm.pet.po.mobile.MobileRecommendInfo;
import com.lvmama.comm.pet.service.mobile.MobileRecommendBlockService;
import com.lvmama.comm.pet.service.mobile.MobileRecommendInfoService;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.utils.pic.UploadCtrl;
import com.lvmama.comm.vo.Constant;
import com.lvmama.pet.utils.MobileConstant;

/**
 * 驴途 v3 推荐管理 
 * @author qinzubo
 */
@Results( {
	@Result(name = "index", location = "/WEB-INF/pages/back/mobile/mobile_index.jsp"),
	@Result(name = "success_index", location = "/WEB-INF/pages/back/mobile/mobile_recommend_info_index.jsp"),
	@Result(name = "success_free_tour", location = "/WEB-INF/pages/back/mobile/mobile_recommend_info_free_tour.jsp"),
	@Result(name = "success_dest", location = "/WEB-INF/pages/back/mobile/mobile_recommend_info_dest.jsp"),
	@Result(name = "input", location = "/WEB-INF/pages/back/mobile/uploadImg.jsp"),
	@Result(name = "uploadHDImg", location = "/WEB-INF/pages/back/mobile/uploadHDImg.jsp")
})
public class MobileRecommendInfoAction extends BackBaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private MobileRecommendBlockService mobileRecommendBlockService;
	private MobileRecommendInfoService mobileRecommendInfoService;

	/*** 参数  ***/
	private String recommendBlockId;// blockId 
	private String  pageChannel;    // 推荐的频道 ，首页推荐 ，自由行推荐 ，目的地推荐等.
	private File imgFileUrl;
	  
	private MobileRecommendInfo mobileRecommendInfo; // 
	private MobileRecommendBlock sonBlock;
	private MobileRecommendBlock recommendBlock;
	
	/**
	 * 推荐信息列表页 .
	 * @return
	 * @throws Exception
	 */
	@Action("/mobile/mobileRecommendInfo")
	public String execute() throws Exception {
		try{
			// 获取block信息 
			Long blockId = Long.valueOf(recommendBlockId);
			sonBlock = mobileRecommendBlockService.getMobileRecommendBlockById(blockId);
			recommendBlock = mobileRecommendBlockService.getMobileRecommendBlockById(sonBlock.getParentId());
			
			
			// 分页相关  
			pagination=initPage();
			pagination.setPageSize(15);
			Map<String, Object> param = new HashMap<String,Object>();
			param.put("recommendBlockId", blockId);
			if(null != mobileRecommendInfo) {
				param.put("tag", mobileRecommendInfo.getTag());
			}
			pagination.setTotalResultSize(mobileRecommendInfoService.countMobileRecommendInfoList(param));
			if(pagination.getTotalResultSize()>0){
				param.put("startRows", pagination.getStartRows());
				param.put("endRows", pagination.getEndRows());
				param.put("isPaging", "true"); // 分页 
				List<MobileRecommendInfo> mlist = mobileRecommendInfoService.queryMobileRecommendInfoList(param);
				pagination.setItems(mlist);
			}
			pagination.buildUrl(getRequest());
			
		}catch(Exception e){
			e.printStackTrace();
		}
		// 首页推荐
		if(MobileConstant.BLOCK_TYPE_INDEX.equals(sonBlock.getBlockType())) {
			return "success_index";
		} else if(MobileConstant.BLOCK_TYPE_DEST.equals(sonBlock.getBlockType())) { // 目的地
			return "success_dest";
		} else {
			return "success_free_tour"; // 目的地 
		}
	}
	
	/**
	 * 保存mobileRecommendInfo. 
	 */
	public void saveMobileRecommendInfo() {
		String json = "{\"flag\":\"false\"}";
		String msg = "修改成功 !";
		try{
			if(null == mobileRecommendInfo || null == mobileRecommendInfo.getRecommendBlockId()|| mobileRecommendInfo.getRecommendBlockId() == 0 ) {
				this.responseWrite(json);
			}
			if(null == mobileRecommendInfo.getId()) {// 新增 
				msg = "新增成功 !";
				mobileRecommendInfoService.insertMobileRecommendInfo(mobileRecommendInfo);
			}  else {
				mobileRecommendInfoService.updateMobileRecommendInfo(mobileRecommendInfo);
			}
			json = "{\"flag\":\"true\",\"msg\":\""+msg+"\"}";
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		this.responseWrite(json);
	}
	
	/**
	 * 保存目的地. 
	 */
	public void saveMobileRecommendInfoDest() {
		String json = "{\"flag\":\"false\"}";
		String msg = "新增成功 !";
		try{
			if(null == pageChannel || null == mobileRecommendInfo || null == mobileRecommendInfo.getRecommendBlockId()  ) {
				this.responseWrite(json);
				return;
			}
			
			// 判断是否绑定 
			List<MobileRecommendInfo> mList = null;
			Map<String,Object> params = new HashMap<String,Object>();
			params.put("recommendBlockId", mobileRecommendInfo.getRecommendBlockId());
			params.put("objectId", mobileRecommendInfo.getObjectId());
			params.put("objectType", mobileRecommendInfo.getObjectType());
			if(pageChannel.equals(MobileConstant.PAGE_CHNNEL_DEST)){
				String[] tags = mobileRecommendInfo.getTag().split(","); // 标签 
				for(int i = 0 ;i < tags.length ;i++) {
					params.put("tag", tags[i]);
					mList= mobileRecommendInfoService.queryMobileRecommendInfoList(params);
					if(null != mList && mList.size() > 0) {
						json = "{\"flag\":\"false\",\"msg\":\"["+mobileRecommendInfo.getRecommendTitle()+"] - ["
					            +Constant.CLIENT_RECOMMEND_TAG.getCnName(tags[i])+"] 已经绑定了!\"}";
						this.responseWrite(json);
						return ;
					}
				}
			} else {
				mList= mobileRecommendInfoService.queryMobileRecommendInfoList(params);
				if(null != mList && mList.size() > 0) {
					json = "{\"flag\":\"false\",\"msg\":\"["+mobileRecommendInfo.getRecommendTitle()+"] 已经绑定了!\"}";
					this.responseWrite(json);
					return ;
				}
			}
			
			
			// 如果是目的地
			if(pageChannel.equals(MobileConstant.PAGE_CHNNEL_DEST) && null != mobileRecommendInfo.getTag()) {
				String[] tags = mobileRecommendInfo.getTag().split(","); // 标签 
				for(int i = 0 ;i < tags.length ;i++) {
					mobileRecommendInfo.setTag(tags[i]);
					// 绑定目的地 . 
					mobileRecommendInfoService.insertMobileRecommendInfo(mobileRecommendInfo);
					msg = "新增成功 !";
				}
			} else if(pageChannel.equals(MobileConstant.PAGE_CHNNEL_FREE_TOUR)) { // 自由行
				mobileRecommendInfoService.insertMobileRecommendInfo(mobileRecommendInfo);
				msg = "新增成功 !";
			}
			
			json = "{\"flag\":\"true\",\"msg\":\""+msg+"\"}";
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		this.responseWrite(json);
	}
	
	/**
	 * 跳转页面. 
	 * @return
	 * @throws Exception
	 */
	public String uploadImg() throws Exception{
		String json = "{\"flag\":\"false\"}";
		String msg = "";
		if (imgFileUrl != null && sonBlock!=null && sonBlock.getId() != null) {
			String imgContextPath = Constant.getInstance().getPlaceImagesPath();
			String fileName = System.currentTimeMillis() + ".jpg";
			String fileFullName = imgContextPath + sonBlock.getId() + "/" + fileName;
			
			// 判断图片宽度和高度. 首页推荐：720*270  目的地：82*82  自由行：92*92  攻略首页：（第一行）240*350 （第二行）240*170 （其余）170*170
			// 首页推荐 
            // 根据子block得到父block的id .首页 81 ， 自由行 121 ， 攻略 114 ，115 ，度假目的地101
			MobileRecommendBlock mb = mobileRecommendBlockService.getMobileRecommendBlockById(sonBlock.getId());
			if(null != mb && null != mb.getParentId()) {
				Long pid = mb.getParentId(); // 父节点id 
				if("81".equals(pid+"")) { // 首页   720*270 
					if(UploadCtrl.checedImgWidthAndHeight(imgFileUrl, 720, 270)) {
						msg = "图片尺寸最大为720*270";
					}
				} else if("121".equals(pid+"")) { // 自由行 92*92 
					if(UploadCtrl.checedImgWidthAndHeight(imgFileUrl, 92, 92)) {
						msg = "图片尺寸最大为92*92";
					}
				}else if("682".equals(pid+"")) { // 新自由行576*220
					if(UploadCtrl.checedImgWidthAndHeight(imgFileUrl,576, 220)) {
						msg = "图片尺寸最大为576*220";
					}
				}else if("681".equals(pid+"")) { // 新首页   720*270  640*340 
					if(UploadCtrl.checedImgWidthAndHeight(imgFileUrl,640,340)) {
						msg = "图片尺寸最大为640*340";
					}
				} else if("114".equals(pid+"") || "115".equals(pid+"")) { // 攻略  （第一行）240*350 （第二行）240*170 （其余）170*170
					if(UploadCtrl.checedImgWidthAndHeight(imgFileUrl, 240, 350)) {
						msg = "图片尺寸最大为240*350";
					}
					json = "{\"flag\":\"true\",\"msg\":\"图片尺寸最大为240*350\"}";
				} else if("101".equals(pid+"")) { // 度假目的地   82*82 
					if(UploadCtrl.checedImgWidthAndHeight(imgFileUrl, 82, 82)) {
						msg = "图片尺寸最大为82*82";
					}
				}
				
				
			}
			if(!"".equals(msg)) {
				json = "{\"flag\":\"true\",\"msg\":\""+msg+"\"}";
				getRequest().setAttribute("uploadMsg", msg);
			}else if(UploadCtrl.checkImgSize(imgFileUrl, 1024)){
				json = "{\"flag\":\"true\",\"msg\":\"上传的文件不能大于1MB\"}";
				getRequest().setAttribute("uploadMsg", msg);
			}else{
			   mobileRecommendInfo.setRecommendImageUrl(UploadCtrl.postToRemote(imgFileUrl, fileFullName));
			   json = "{\"flag\":\"true\"}";
			}
		}
		this.responseWrite(json);
		return INPUT;
	}
	
	/**
	 * 上传高清图片，暂时供ipad使用，跳转页面. 
	 * @return
	 * @throws Exception
	 */
	public String uploadHDImg() throws Exception{
		String json = "{\"flag\":\"false\"}";
		String msg = "";
		if (imgFileUrl != null && sonBlock!=null && sonBlock.getId() != null) {
			String imgContextPath = Constant.getInstance().getPlaceImagesPath();
			String fileName = System.currentTimeMillis() + ".jpg";
			String fileFullName = imgContextPath + sonBlock.getId() + "/" + fileName;
			
			// 判断图片宽度和高度. 首页推荐：720*270  目的地：82*82  自由行：92*92  攻略首页：（第一行）240*350 （第二行）240*170 （其余）170*170
			// 首页推荐 
            // 根据子block得到父block的id .首页 81 ， 自由行 121 ， 攻略 114 ，115 ，度假目的地101
			MobileRecommendBlock mb = mobileRecommendBlockService.getMobileRecommendBlockById(sonBlock.getId());
			if(null != mb && null != mb.getParentId()) {
				Long pid = mb.getParentId(); // 父节点id 
				if("81".equals(pid+"")) { // 首页   720*270 
					if(UploadCtrl.checedImgWidthAndHeight(imgFileUrl, 1640, 512)) {
						msg = "图片尺寸最大为1640*512";
					}
				} else if("681".equals(pid+"")) { // 新首页   720*270 
					if(UploadCtrl.checedImgWidthAndHeight(imgFileUrl, 1640, 512)) {
						msg = "图片尺寸最大为1640*512";
					}
				} else if("682".equals(pid+"")) { // 自由行列表banner 720x270 
					if(UploadCtrl.checedImgWidthAndHeight(imgFileUrl, 720, 270)) {
						msg = "图片尺寸最大为720*670";
					}
				} else if("121".equals(pid+"")) { // 自由行  92*92 
					if(UploadCtrl.checedImgWidthAndHeight(imgFileUrl, 92, 92)) {
						msg = "图片尺寸最大为92*92";
					}
				} else if("114".equals(pid+"") || "115".equals(pid+"")) { // 攻略  （第一行）240*350 （第二行）240*170 （其余）170*170
					if(UploadCtrl.checedImgWidthAndHeight(imgFileUrl, 240, 350)) {
						msg = "图片尺寸最大为240*350";
					}
					json = "{\"flag\":\"true\",\"msg\":\"图片尺寸最大为240*350\"}";
				} else if("101".equals(pid+"")) { // 度假目的地   82*82 
					if(UploadCtrl.checedImgWidthAndHeight(imgFileUrl, 180, 180)) {
						msg = "图片尺寸最大为180*180";
					}
				}
				
				
			}
			if(!"".equals(msg)) {
				json = "{\"flag\":\"true\",\"msg\":\""+msg+"\"}";
				getRequest().setAttribute("uploadMsg", msg);
			}else if(UploadCtrl.checkImgSize(imgFileUrl, 1024)){
				json = "{\"flag\":\"true\",\"msg\":\"上传的文件不能大于1MB\"}";
				getRequest().setAttribute("uploadMsg", msg);
			}else{
			   //mobileRecommendInfo.setRecommendImageUrl(UploadCtrl.postToRemote(imgFileUrl, fileFullName));
			   mobileRecommendInfo.setRecommendHDImageUrl(UploadCtrl.postToRemote(imgFileUrl, fileFullName));
			   json = "{\"flag\":\"true\"}";
			}
		}
		this.responseWrite(json);
		return "uploadHDImg";
	}
	
	/**
	 * 更改状态 . 
	 */
	public void changeRecommendInfoStatus() {
		String json = "{\"flag\":\"false\"}";
		try{
			if(null != mobileRecommendInfo && null != mobileRecommendInfo.getId() && null != mobileRecommendInfo.getIsValid()) {
				boolean b = mobileRecommendInfoService.updateStatus(mobileRecommendInfo.getIsValid(), mobileRecommendInfo.getId());
				if(b) {
					json = "{\"flag\":\"true\"}";
				}
			}else {
				String ids = getRequest().getParameter("ids");
				String status = getRequest().getParameter("status");
				if(!StringUtils.isEmpty(ids) && !StringUtil.isEmptyString(status)) {
					String[] id = ids.split(",");
					for(int i = 0; i < id.length ;i++) {
						 mobileRecommendInfoService.updateStatus(status, Long.valueOf(id[i]));
					}
					json = "{\"flag\":\"true\"}";
				}
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		this.responseWrite(json);
	}
	
	/**
	 * 删除状态 . 
	 */
	public void delMobileRecommendInfo() {
		String json = "{\"flag\":\"false\"}";
		try{
			if(null != mobileRecommendInfo && null != mobileRecommendInfo.getId()) {
				mobileRecommendInfoService.deleteMobileRecommendInfoById( mobileRecommendInfo.getId());
				json = "{\"flag\":\"true\"}";
			} else {
				String ids = getRequest().getParameter("delIds");
				if(!StringUtil.isEmptyString(ids)) {
					String[] id = ids.split(",");
					for(int i = 0; i < id.length; i++) {
						mobileRecommendInfoService.deleteMobileRecommendInfoById( Long.valueOf(id[i]));
						json = "{\"flag\":\"true\"}";
					}
				}
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		this.responseWrite(json);
	}
	
	
	/**
	 * 复制所选记录 . 
	 */
	public void copyMobileRecommendInfo() {
		String json = "{\"flag\":\"false\"}";
		try{
			String ids = getRequest().getParameter("ids");
			String tag = getRequest().getParameter("tag");
			if(!StringUtil.isEmptyString(ids)) {
				String[] id = ids.split(",");
				for(int i = 0; i < id.length; i++) {
					MobileRecommendInfo mif = mobileRecommendInfoService.selectMobileRecommendInfoById(Long.valueOf(id[i]));
					mif.setId(null);
					mif.setTag(tag);
					mobileRecommendInfoService.insertMobileRecommendInfo(mif);
				}
				json = "{\"flag\":\"true\"}";
			}
		}catch(Exception e) {
			e.printStackTrace();
			json = "{\"flag\":\"false\"}";
		}
		this.responseWrite(json);
	}
	
	/**
	 * 目的地（dest） 和 自由行（freeTour）修改 tag ，imgUrl 和 tag(Dest中) . 
	 */
	public void updateRecommendInfoSeq() {
		String json = "{\"flag\":\"false\"}";
		try{
			if(null != mobileRecommendInfo && null != mobileRecommendInfo.getId()) {
				MobileRecommendInfo m = mobileRecommendInfoService.selectMobileRecommendInfoById(mobileRecommendInfo.getId());
				if(null != m) {
					m.setSeqNum(mobileRecommendInfo.getSeqNum());
					m.setRecommendHDImageUrl(mobileRecommendInfo.getRecommendHDImageUrl());
					m.setRecommendImageUrl(mobileRecommendInfo.getRecommendImageUrl());
					m.setTag(mobileRecommendInfo.getTag());
					m.setPrice(mobileRecommendInfo.getPrice());
					m.setRecommendContent(mobileRecommendInfo.getRecommendContent());
					if(null != mobileRecommendInfo.getLatitude() && null != mobileRecommendInfo.getLongitude()) {
						m.setLatitude(mobileRecommendInfo.getLatitude());
						m.setLongitude(mobileRecommendInfo.getLongitude());
					}
					mobileRecommendInfoService.updateMobileRecommendInfo(m);
					json = "{\"flag\":\"true\",\"msg\":\"修改成功\"}";
				}
				//boolean b  = mobileRecommendInfoService.updateSeq(mobileRecommendInfo.getSeq()+"", mobileRecommendInfo.getId());
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		this.responseWrite(json);
	}
	
	
	private void responseWrite(String info){
		try {
			this.getResponse().setContentType("text/html; charset=utf-8");
			this.getResponse().getWriter().write(info);
		} catch (Exception e) {
			log.info("com.lvmama.pet.sweb.mobile:"+e.getMessage());
		}
	}
	
	public void setMobileRecommendInfoService(
			MobileRecommendInfoService mobileRecommendInfoService) {
		this.mobileRecommendInfoService = mobileRecommendInfoService;
	}

	
	public MobileRecommendBlock getSonBlock() {
		return sonBlock;
	}

	public void setSonBlock(MobileRecommendBlock sonBlock) {
		this.sonBlock = sonBlock;
	}

	public MobileRecommendBlock getRecommendBlock() {
		return recommendBlock;
	}

	public void setRecommendBlock(MobileRecommendBlock recommendBlock) {
		this.recommendBlock = recommendBlock;
	}

	public void setMobileRecommendBlockService(
			MobileRecommendBlockService mobileRecommendBlockService) {
		this.mobileRecommendBlockService = mobileRecommendBlockService;
	}
	
	public String getRecommendBlockId() {
		return recommendBlockId;
	}

	public void setRecommendBlockId(String recommendBlockId) {
		this.recommendBlockId = recommendBlockId;
	}

	public MobileRecommendInfo getMobileRecommendInfo() {
		return mobileRecommendInfo;
	}

	public void setMobileRecommendInfo(MobileRecommendInfo mobileRecommendInfo) {
		this.mobileRecommendInfo = mobileRecommendInfo;
	}

	public String getPageChannel() {
		return pageChannel;
	}

	public void setPageChannel(String pageChannel) {
		this.pageChannel = pageChannel;
	}

	public File getImgFileUrl() {
		return imgFileUrl;
	}

	public void setImgFileUrl(File imgFileUrl) {
		this.imgFileUrl = imgFileUrl;
	}
}
