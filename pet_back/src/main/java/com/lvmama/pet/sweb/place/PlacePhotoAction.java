package com.lvmama.pet.sweb.place;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.comm.BackBaseAction;
import com.lvmama.comm.pet.po.place.Place;
import com.lvmama.comm.pet.po.place.PlacePhoto;
import com.lvmama.comm.pet.service.place.PlacePhotoService;
import com.lvmama.comm.pet.service.place.PlaceService;
import com.lvmama.comm.pet.service.pub.ComLogService;
import com.lvmama.comm.pet.service.pub.ComSearchInfoUpdateService;
import com.lvmama.comm.utils.pic.UploadCtrl;
import com.lvmama.comm.vo.Constant;
import com.lvmama.pet.corputils.ImageUtils;
import com.opensymphony.xwork2.ActionContext;

/**
 * 目的地图片管理
 *
 */
@Results( {
	@Result(name = "input", location = "/WEB-INF/pages/back/place/photo/placeHotelPhotoError.jsp", type = "dispatcher"),
	@Result(name = "placePhotoList", location = "/WEB-INF/pages/back/place/photo/placeHotelPhotoList.jsp", type = "dispatcher"),
	@Result(name = "placeHotelPhotoListTable", location = "/WEB-INF/pages/back/place/photo/placeHotelPhotoListTable.jsp", type = "dispatcher"),
	@Result(name = "placePhotoListVer2", location = "/WEB-INF/pages/back/place/photo/placeHotelPhotoListVer2.jsp", type = "dispatcher"),
	@Result(name = "uploadImg", location = "/WEB-INF/pages/back/place/photo/placeHotelPhotoEdit.jsp", type = "dispatcher"),
	@Result(name = "placePhotoEdit", location = "/WEB-INF/pages/back/place/photo/placeHotelPhotoEdit.jsp", type = "dispatcher")
	})
public class PlacePhotoAction extends BackBaseAction{
	private static final long serialVersionUID = -1933262201783089923L;
	
	/**
	 * 每次修改place信息之后需要向COM_SEARCH_INFO_UPDATE表中增加一条记录，用于同步PLACE表与PLACE_SEARCH_INFO的信息
	 * add by yanggan 
	 */
	private ComSearchInfoUpdateService comSearchInfoUpdateService;
	
	private PlacePhotoService placePhotoService;
	private PlacePhoto placePhoto = new PlacePhoto();
	private ComLogService comLogService;
	private PlaceService placeService;
	private List<PlacePhoto> placePhotoList = new ArrayList<PlacePhoto>();
	private String message;
	private String placeId;
	private String stage;
	private String placePhotoIds;
	
	private PlacePhoto placePhotoSmall;
	private PlacePhoto placePhotoMid;
	private List<PlacePhoto> placePhotoLargerList;
	private String displayFlg;
	
	@Action("/place/placePhotoList")
	public String queryPhotosList(){
		placePhotoList = placePhotoService.queryByPlacePhoto(placePhoto);
		placeId=placePhoto.getPlaceId().toString();
		return "placePhotoList";
	}
	
	@Action("/place/placePhotoListVer2")
	public String queryPhotosListVer2(){
		placePhotoList = placePhotoService.queryByPlacePhoto(placePhoto);
		if(placePhotoList != null && placePhotoList.size()>0){
			placePhotoLargerList= new ArrayList<PlacePhoto>();
			for (PlacePhoto placePhoto : placePhotoList) {
				if("LARGE".equals(placePhoto.getType())){
					placePhotoLargerList.add(placePhoto);
				}
				if("MIDDLE".equals(placePhoto.getType())){
					placePhotoMid = placePhoto;
				}
				if("SMALL".equals(placePhoto.getType())){
					placePhotoSmall = placePhoto;
				}
			}
		}
		placeId=placePhoto.getPlaceId().toString();
		return "placePhotoListVer2";
	}
	
	@Action("/place/placePhotoEdit")
	public String placePhotoEdit(){
		placePhoto = new PlacePhoto();
		if(StringUtils.isNotBlank(placePhotoIds)){
			placePhoto = placePhotoService.queryByPlacePhotoId(Long.valueOf(placePhotoIds));
		}
		return "placePhotoEdit";
	}
	
	@Action("/place/placeHotelPhotoListTable")
	public String queryplacePhotoListTable(){
		placePhotoList = placePhotoService.queryByPlacePhoto(placePhoto);
		placeId=placePhoto.getPlaceId().toString();
		return "placeHotelPhotoListTable";
	}

	@Action("/place/saveOrUpdatePlacePhoto")
	public void saveOrUpdatePlacePhoto() throws Exception{
		java.util.Map<String,Object> map=new HashMap<String,Object>();
		try {
			if (placePhoto.getImagePath() != null) {
				String imgContextPath = Constant.getInstance().getPlaceImagesPath();
				String fileName = System.currentTimeMillis() + ".jpg";
				String fileFullName = imgContextPath + placePhoto.getPlaceId().toString() + "/" + fileName;
				placePhoto.setImagesUrl(UploadCtrl.postToRemote(placePhoto.getImagePath(), fileFullName));
			}
			placePhotoService.saveOrUpdatePlacePhoto(placePhoto);
			comLogService.insert("SCENIC_LOG_PLACE", null, placePhoto.getPlaceId(), super.getSessionUserName(),
					Constant.SCENIC_LOG_PLACE.createOrupdatePlacePhotoInfo.name(), "创建或者修改景区图片信息 ", "新建或者修改景区图片信息", "");
			message = "恭喜你！操作成功！";
			Place place = placeService.queryPlaceByPlaceId(placePhoto.getPlaceId());
			comSearchInfoUpdateService.placeUpdated(place.getPlaceId(),place.getStage());
			map.put("success", true);
			map.put("message", message);
		} catch (Exception e) {
			message = "图片不合格！操作失败！";
			map.put("success", false);
			map.put("message", message);
		}finally{
			this.sendAjaxResultByJson(JSONObject.fromObject(map).toString());
		}
	}
	
	@Action("/place/saveOrUpdatePlacePhotoVer2")
	public void saveOrUpdatePlacePhotoVer2() throws Exception{
		java.util.Map<String,Object> map=new HashMap<String,Object>();
		String cutImgName = String.valueOf(ActionContext.getContext().getSession().get("cutImgName"));
		String imageSource = String.valueOf(ActionContext.getContext().getSession().get("imageSource"));
		String url = getRequest().getSession().getServletContext().getRealPath("/");
		File cutImgTemp = new File(url+cutImgName);
		try {
			if (cutImgTemp.exists()) {
				placePhoto.setImagePath(cutImgTemp);
				String imgContextPath = Constant.getInstance().getPlaceImagesPath();
				String fileName = System.currentTimeMillis() + ".jpg";
				String fileFullName = imgContextPath + placePhoto.getPlaceId().toString() + "/" + fileName;
				placePhoto.setImagesUrl(UploadCtrl.postToRemote(placePhoto.getImagePath(), fileFullName));
			}
			placePhoto.setPlacePhotoDisplay("1");
			placePhotoService.saveOrUpdatePlacePhoto(placePhoto);
			comLogService.insert("SCENIC_LOG_PLACE", null, placePhoto.getPlaceId(), super.getSessionUserName(),
					Constant.SCENIC_LOG_PLACE.createOrupdatePlacePhotoInfo.name(),
					"创建或者修改景区图片信息 ", "新建或者修改景区图片信息", "");
			
			
			message = "恭喜你！操作成功！";
			Place place = placeService.queryPlaceByPlaceId(placePhoto.getPlaceId());
			comSearchInfoUpdateService.placeUpdated(place.getPlaceId(),place.getStage());
			map.put("success", true);
			map.put("message", message);
			File sourceImgTemp = new File(url+imageSource);
			if (sourceImgTemp.isFile() && sourceImgTemp.exists()) {  
				sourceImgTemp.delete();
		    }  
		} catch (Exception e) {
			e.printStackTrace();
			message = "图片不合格！操作失败！";
			map.put("success", false);
			map.put("message", message);
		}finally{
			//图片上传成功后，自动删除临时文件
		    if (cutImgTemp.isFile() && cutImgTemp.exists()) {  
		    	cutImgTemp.delete();
		    }  
			 this.sendAjaxResultByJson(JSONObject.fromObject(map).toString());
		}

	}

	@Action("/place/uploadImg")
	public void uploadImg() throws Exception{
		java.util.Map<String,Object> map=new HashMap<String,Object>();
		String imgTempName = "placeUploadPics/"+String.valueOf(System.currentTimeMillis())+".jpg";
		try {
			String url = ServletActionContext.getServletContext().getRealPath("/");
			FileInputStream fi=new FileInputStream(placePhoto.getImagePath());
			BufferedInputStream in=new BufferedInputStream(fi);
			File f = new File(url+"placeUploadPics");
			if(!f.exists()){
				f.mkdirs();
			}
			FileOutputStream fo=new FileOutputStream(url+imgTempName);
			BufferedOutputStream out=new BufferedOutputStream(fo);
			byte[] buf=new byte[1024];
			int len=in.read(buf);//读文件，将读到的内容放入到buf数组中，返回的是读到的长度
			while(len!=-1){
				out.write(buf, 0, len);
				len=in.read(buf);
			}
			out.close();
			fo.close();
			in.close();
			fi.close();
			message = "上传成功！";
			map.put("success", true);
			map.put("sourceImg", imgTempName);
			ImageUtils image = ImageUtils.load(url+imgTempName);
			map.put("sourceImgWidth", 	image.getImageWidth() );
			map.put("sourceImgHeight", 	image.getImageHeight() );
		} catch (Exception e) {
			e.printStackTrace();
			message = "上传失败！";
			map.put("success", false);
			map.put("sourceImg", placePhoto.getImagePathFileName());
		}finally{
			 this.sendAjaxResultByJson(JSONObject.fromObject(map).toString());
		}

	}
	
	@Action("/place/updatePlacePhotoDisplay")
	public void updatePlacePhotoDisplay() throws SQLException {
 		java.util.Map<String,Object> map=new HashMap<String,Object>();
 		try {
 	 		if(StringUtils.isNotBlank(placePhotoIds)){
 				placePhoto = placePhotoService.queryByPlacePhotoId(Long.valueOf(placePhotoIds));
 			}
 	 		placePhoto.setPlacePhotoDisplay(displayFlg);
 	 		placePhotoService.saveOrUpdatePlacePhoto(placePhoto);
 	 		if(StringUtils.isBlank(displayFlg)){
 	 			message = "更新不显示列表成功！";
 	 		}else{
 	 			message = "显示更新列表成功！";
 	 		}
 			map.put("success", true);
 			map.put("message", message);
		} catch (Exception e) {
			map.put("success", true);
 			map.put("message", "显示更新更新失败！");
		}finally{
			 this.sendAjaxResultByJson(JSONObject.fromObject(map).toString());
		}
	}
	
	@Action("/place/delete")
	public void delete() throws SQLException {
		if(placePhoto.getPlaceId()==null){
			placePhoto.setPlaceId(Long.valueOf(placeId));
		}
		if(placePhoto.getPlacePhotoId()==null){
			placePhoto.setPlacePhotoId(Long.valueOf(placePhotoIds));
		}
		placePhotoService.delete(placePhoto);
		comLogService.insert("SCENIC_LOG_PLACE", null, placePhoto.getPlaceId(), super.getSessionUserName(),
				Constant.SCENIC_LOG_PLACE.deletePlacePhotoInfo.name(),
				"删除景区图片信息", "删除景区图片信息", "");
		
 		java.util.Map<String,Object> map=new HashMap<String,Object>();
		message = "恭喜你！删除成功！";
		Place place = placeService.queryPlaceByPlaceId(placePhoto.getPlaceId());
		comSearchInfoUpdateService.placeUpdated(place.getPlaceId(),place.getStage());
		map.put("success", true);
		map.put("message", message);
		this.sendAjaxResultByJson(JSONObject.fromObject(map).toString());
	}
	
	@Action("/place/savePhotoSeq")
	public String savePhotoSeq() throws Exception {
		try{
			this.placePhotoService.batchSavePhotoSeq(this.getPlacePhotoIds());
			this.outputToClient("success");
		}catch(Exception ex){
			ex.printStackTrace();
			this.outputToClient("error");
		}
		return null;
	}

	public PlacePhoto getPlacePhoto() {
		return placePhoto;
	}

	public String getMessage() {
		return message;
	}

	public void setPlacePhoto(PlacePhoto placePhoto) {
		this.placePhoto = placePhoto;
	}

	public List<PlacePhoto> getPlacePhotoList() {
		return placePhotoList;
	}

	public void setPlacePhotoService(PlacePhotoService placePhotoService) {
		this.placePhotoService = placePhotoService;
	}

	public String getPlaceId() {
		return placeId;
	}

	public void setPlaceId(String placeId) {
		this.placeId = placeId;
	}

	public String getStage() {
		return stage;
	}

	public void setStage(String stage) {
		this.stage = stage;
	}

	public String getPlacePhotoIds() {
		return placePhotoIds;
	}

	public void setPlacePhotoIds(String placePhotoIds) {
		this.placePhotoIds = placePhotoIds;
	}

	public ComLogService getComLogService() {
		return comLogService;
	}

	public void setComLogService(ComLogService comLogService) {
		this.comLogService = comLogService;
	}

	public void setComSearchInfoUpdateService(ComSearchInfoUpdateService comSearchInfoUpdateService) {
		this.comSearchInfoUpdateService = comSearchInfoUpdateService;
	}

	public PlacePhoto getPlacePhotoSmall() {
		return placePhotoSmall;
	}

	public void setPlacePhotoSmall(PlacePhoto placePhotoSmall) {
		this.placePhotoSmall = placePhotoSmall;
	}

	public PlacePhoto getPlacePhotoMid() {
		return placePhotoMid;
	}

	public void setPlacePhotoMid(PlacePhoto placePhotoMid) {
		this.placePhotoMid = placePhotoMid;
	}

	public List<PlacePhoto> getPlacePhotoLargerList() {
		return placePhotoLargerList;
	}

	public void setPlacePhotoLargerList(List<PlacePhoto> placePhotoLargerList) {
		this.placePhotoLargerList = placePhotoLargerList;
	}

	public String getDisplayFlg() {
		return displayFlg;
	}

	public void setDisplayFlg(String displayFlg) {
		this.displayFlg = displayFlg;
	}

	public void setPlaceService(PlaceService placeService) {
		this.placeService = placeService;
	}	
	
}
