package com.lvmama.pet.sweb.seo;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.BeanUtils;

import com.lvmama.comm.pet.po.place.Place;
import com.lvmama.comm.pet.po.place.PlacePhoto;
import com.lvmama.comm.pet.po.search.ProductSearchInfo;
import com.lvmama.comm.pet.po.seo.RecommendBlock;
import com.lvmama.comm.pet.po.seo.RecommendInfo;
import com.lvmama.comm.pet.service.place.PlacePhotoService;
import com.lvmama.comm.pet.service.place.PlaceService;
import com.lvmama.comm.pet.service.search.ProductSearchInfoService;
import com.lvmama.comm.pet.service.seo.RecommendBlockService;
import com.lvmama.comm.pet.service.seo.RecommendInfoService;
import com.lvmama.comm.utils.pic.UploadCtrl;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.enums.PlacePhotoTypeEnum;

@Results({ 
	@Result(name = "success", location = "/WEB-INF/pages/back/seo/recommend/recommendInfo.jsp"),
	@Result(name = "input", location = "/WEB-INF/pages/back/seo/recommend/uploadImg.jsp")
	})
public class RecommendInfoAction extends com.lvmama.comm.BackBaseAction{
	private static final long serialVersionUID = 6560869729315538305L;
	private RecommendInfoService recommendInfoService;
	private RecommendBlockService recommendBlockService; 
	private ProductSearchInfoService productSearchInfoService;
	private PlaceService placeService; 
	private PlacePhotoService placePhotoService;
	private RecommendBlock recommendBlock;
	private RecommendBlock sonBlock;
	private RecommendInfo recommendInfo;
	Map<String, Object> param ;
	private Long id;
	private Long objectId[]=null;
    private Long srcBlockId;
    private Long destBlockId;
    private File imgUrl;
	private String imgUrlContentType;
	private String imgUrlFileName;
	private Long[] recommendInfoId;
	private Long[] recommendInfoSeq;
	
	@SuppressWarnings("unchecked")
	@Action("/seo/recommendInfo")
	public String execute() {
		if (this.id != null && this.id > 0) {
			sonBlock = (RecommendBlock) this.recommendBlockService.getRecommendBlockById( id);
			Long fatherId = sonBlock.getParentRecommendBlockId();
			recommendBlock = (RecommendBlock) this.recommendBlockService.getRecommendBlockById( fatherId);
			
			pagination=initPage();
			pagination.setPageSize(10);
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("recommendBlockId", id);
			pagination.setTotalResultSize(recommendInfoService.countRecommendInfoByParam(param));
			if(pagination.getTotalResultSize()>0){
				param.put("startRows", pagination.getStartRows());
				param.put("endRows", pagination.getEndRows());
				List<RecommendInfo> list=recommendInfoService.queryRecommendInfoByParam(param);
				pagination.setItems(list);
			}
			pagination.buildUrl(getRequest());
		}
		return SUCCESS;
	}
	
	/**
	 * 添加并保存推荐信息
	 * 
	 * @return
	 * @throws Exception
	 */
	public void saveRecommendInfo() throws Exception {
		String json = "{\"flag\":\"false\"}";
		Long blockId=null;
		if(sonBlock!=null&&sonBlock.getRecommendBlockId()!=null){
			blockId=sonBlock.getRecommendBlockId();
		}else if(recommendInfo!=null&&recommendInfo.getRecommendBlockId()!=null){
			blockId=recommendInfo.getRecommendBlockId();
		}else{
			this.responseWrite(json);
			return;
		}
		RecommendBlock target = (RecommendBlock) this.recommendBlockService.getRecommendBlockById(blockId);
		
		if(target!=null){
			String msg="";
			List<RecommendInfo> ri=null;
			RecommendInfo info=null;
			Map<String,Object> param=null;
			Boolean flag=false;
			if ("4".equalsIgnoreCase(target.getModeType())) {
				if(recommendInfo.getRecommendInfoId()==null){
					flag=true;
				}
				info=new RecommendInfo();
				BeanUtils.copyProperties(recommendInfo,info);
				info=fillRecommendInfo(target,info);
				insertOrUpdateRecommendInfo(info,flag);
			}else{	
				for(int index=0;objectId!=null&&index<objectId.length;index++){
					info=new RecommendInfo();
					BeanUtils.copyProperties(recommendInfo,info);
					info.setRecommObjectId(String.valueOf(objectId[index]));
					
					param=new HashMap<String, Object>();
					param.put("recommObjectId", info.getRecommObjectId());
					param.put("recommendBlockId", target.getRecommendBlockId());
					param.put("dataCode", target.getDataCode());
					param.put("recommendInfoId", info.getRecommendInfoId());
					ri= recommendInfoService.queryRecommendInfoByParam(param);
					info=fillRecommendInfo(target,info);
					if(info==null){
						msg+=" "+param.get("recommObjectId");
					}else{
						if(ri==null||ri.size()<=0){
							flag=true;
						}else{
							info.setRecommendInfoId(ri.get(0).getRecommendInfoId());
						}
						insertOrUpdateRecommendInfo(info,flag);
					}
				}
			}
			json = "{\"flag\":\"true\",\"msg\":\""+msg+"\"}";
		}
		this.responseWrite(json);
	}
	
	public void  insertOrUpdateRecommendInfo(RecommendInfo info,Boolean flag){
		if(flag){
			this.recommendInfoService.insertRecommendInfo(info);
		}else{
			this.recommendInfoService.updateRecommendInfo(info);
		}
	}
	
	
	public void getRecommendInfoById() {
		String json = "";
		recommendInfo = (RecommendInfo) recommendInfoService.getRecommendInfoById(id);
		json = net.sf.json.JSONArray.fromObject(recommendInfo).toString();
		this.responseWrite(json);
	}
	
	public void delRecommendInfo() throws IOException {
		String json = "{\"flag\":\"false\"}";
		this.recommendInfoService.deleteRecommendInfoById(id);
		json = "{\"flag\":\"true\"}";
		this.responseWrite(json);
	}
	
	public void saveRecommendInfoSeq() throws IOException {
		String json = "{\"flag\":\"false\"}";
		if(recommendInfoId!=null&&recommendInfoSeq!=null){
			recommendInfoService.saveRecommendInfoSeq(recommendInfoId, recommendInfoSeq);
			json = "{\"flag\":\"true\"}";
		}
		this.responseWrite(json);
	}
	/**
	 * 填充数据到recommendInfo中
	 * @param target
	 * @param info
	 * @return
	 */
	private RecommendInfo fillRecommendInfo(RecommendBlock target,RecommendInfo info) {
		PlacePhoto placePhoto=null;
		Date date = new Date();
		info.setCreateTime(date);
		info.setUpdateTime(date);
		info.setParentRecommendBlockId(target.getParentRecommendBlockId());
		if (target.getDataCode() != null){
			info.setDataCode(target.getDataCode());
		}
		
		// 目的地和景点及酒店
		if ("1".equalsIgnoreCase(target.getModeType()) || "2".equalsIgnoreCase(target.getModeType()) || "5".equalsIgnoreCase(target.getModeType())||"6".equalsIgnoreCase(target.getModeType())) {
			Place place = (Place) placeService.queryPlaceByPlaceId(new Long(info.getRecommObjectId()));
			if(place==null){
				return info;
			}
			if(StringUtils.isEmpty(info.getUrl())){
				info.setUrl("http://www.lvmama.com/dest/"+place.getPinYinUrl());
			}
			if(StringUtils.isEmpty(info.getTitle())){
				info.setTitle(place.getName());
			}
			placePhoto=new PlacePhoto();
			placePhoto.setPlaceId(place.getPlaceId());
			placePhoto.setType(PlacePhotoTypeEnum.MIDDLE.getCode());
			List<PlacePhoto>  p = (List<PlacePhoto>) placePhotoService.queryByPlacePhoto(placePhoto);
			if (StringUtils.isEmpty(info.getImgUrl()) && p != null && p.size()>0&& StringUtils.isNotEmpty(p.get(0).getImagesUrl())) {
			  info.setImgUrl("http://pic.lvmama.com"+p.get(0).getImagesUrl());
			}
		}
		
		// 产品
		if (target.getModeType().equalsIgnoreCase("3")) {
			param= new HashMap<String, Object>();
			param.put("productId", info.getRecommObjectId());
			List<ProductSearchInfo>  viewProductList = productSearchInfoService.queryProductSearchInfoByParam(param);
			if(viewProductList==null || viewProductList.size()==0){
				return info;
			}
			if(viewProductList!=null&&viewProductList.size()>0){
				ProductSearchInfo  viewProduct=viewProductList.get(0);
				if(StringUtils.isEmpty(info.getTitle())){
					info.setTitle(viewProduct.getProductName());
				}
				if(StringUtils.isEmpty(info.getUrl())){
					info.setUrl("http://www.lvmama.com"+viewProduct.getProductUrl());
				}
				if(StringUtils.isEmpty(info.getImgUrl())){
					info.setImgUrl("http://pic.lvmama.com/pics/"+viewProduct.getSmallImage());
				}
			}
		}
		return info;
	}
 
	public void copyRecommendInfos() throws Exception {
		String json = "{\"flag\":\"false\"}";
		RecommendBlock recommendBlock= recommendBlockService.getRecommendBlockById(srcBlockId);
		if(recommendBlock==null){
			 json = "{\"flag\":\"true\",\"msg\":\""+srcBlockId+"\"}";	
		}else{
        recommendInfoService.saveCopyRecommendInfos(srcBlockId, destBlockId);
        json = "{\"flag\":\"true\"}";
		}
        this.responseWrite(json);
	}
	
	public String uploadImg() throws Exception{
		String json = "{\"flag\":\"false\"}";
		if (imgUrl != null && sonBlock!=null && sonBlock.getRecommendBlockId()!=null) {
			String imgContextPath = Constant.getInstance().getPlaceImagesPath();
			String fileName = System.currentTimeMillis() + ".jpg";
			String fileFullName = imgContextPath + sonBlock.getRecommendBlockId() + "/" + fileName;
			if(UploadCtrl.checkImgSize(imgUrl, 300)){
				json = "{\"flag\":\"true\",\"msg\":\"上传的文件不能大于3MB\"}";
			}else{
			   recommendInfo.setImgUrl(UploadCtrl.postToRemote(imgUrl, fileFullName));
			   json = "{\"flag\":\"true\"}";
			}
		}
		this.responseWrite(json);
		return INPUT;
	}
	
	private void responseWrite(String info){
		try {
			this.getResponse().setContentType("text/html; charset=utf-8");
			this.getResponse().getWriter().write(info);
		} catch (Exception e) {
			log.info("com.lvmama.pet.sweb.seo:"+e.getMessage());
		}
	}
	
	public void setRecommendInfoService(RecommendInfoService recommendInfoService) {
		this.recommendInfoService = recommendInfoService;
	}

	public void setRecommendBlockService(RecommendBlockService recommendBlockService) {
		this.recommendBlockService = recommendBlockService;
	}

	public void setProductSearchInfoService(
			ProductSearchInfoService productSearchInfoService) {
		this.productSearchInfoService = productSearchInfoService;
	}

	public void setPlacePhotoService(PlacePhotoService placePhotoService) {
		this.placePhotoService = placePhotoService;
	}

	public RecommendBlock getRecommendBlock() {
		return recommendBlock;
	}

	public void setRecommendBlock(RecommendBlock recommendBlock) {
		this.recommendBlock = recommendBlock;
	}

	public RecommendBlock getSunBlock() {
		return sonBlock;
	}

	public void setSunBlock(RecommendBlock sunBlock) {
		this.sonBlock = sunBlock;
	}

	public RecommendInfo getRecommendInfo() {
		return recommendInfo;
	}

	public void setRecommendInfo(RecommendInfo recommendInfo) {
		this.recommendInfo = recommendInfo;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setSrcBlockId(Long srcBlockId) {
		this.srcBlockId = srcBlockId;
	}

	public void setDestBlockId(Long destBlockId) {
		this.destBlockId = destBlockId;
	}

	public void setPlaceService(PlaceService placeService) {
		this.placeService = placeService;
	}

	public RecommendBlock getSonBlock() {
		return sonBlock;
	}

	public void setSonBlock(RecommendBlock sonBlock) {
		this.sonBlock = sonBlock;
	}

	public Long[] getObjectId() {
		return objectId;
	}

	public void setObjectId(Long[] objectId) {
		this.objectId = objectId;
	}

	public void setImgUrl(File imgUrl) {
		this.imgUrl = imgUrl;
	}
	
	public String getImagePathContentType() {
		return imgUrlContentType;
	}

	public void setImagePathContentType(String imagePathContentType) {
		this.imgUrlContentType = imagePathContentType;
	}

	public String getImgUrlContentType() {
		return imgUrlContentType;
	}

	public void setImgUrlContentType(String imgUrlContentType) {
		this.imgUrlContentType = imgUrlContentType;
	}

	public String getImgUrlFileName() {
		return imgUrlFileName;
	}

	public void setImgUrlFileName(String imgUrlFileName) {
		this.imgUrlFileName = imgUrlFileName;
	}

	public File getImgUrl() {
		return imgUrl;
	}

	public void setRecommendInfoId(Long[] recommendInfoId) {
		this.recommendInfoId = recommendInfoId;
	}

	public void setRecommendInfoSeq(Long[] recommendInfoSeq) {
		this.recommendInfoSeq = recommendInfoSeq;
	}

}
