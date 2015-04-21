package com.lvmama.back.web.group;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.zkoss.zul.A;

import com.lvmama.back.utils.ZkMessage;
import com.lvmama.back.web.BaseAction;
import com.lvmama.comm.bee.po.group.GroupDream;
import com.lvmama.comm.bee.service.GroupDreamService;
import com.lvmama.comm.pet.po.pub.CodeItem;
import com.lvmama.comm.pet.po.pub.CodeSet;
import com.lvmama.comm.pet.po.pub.ComPicture;
import com.lvmama.comm.pet.service.pub.ComPictureService;
import com.lvmama.comm.vo.Constant;

public class EditGroupDreamAction extends BaseAction {
	private ComPictureService comPictureService;
	private GroupDreamService groupDreamService;
	private GroupDream groupDream = new GroupDream();
	private Long dreamId;
	private List<CodeItem> productTypeList = new ArrayList<CodeItem>();
	private CodeItem productTypeItem;
	private List<ComPicture> pictureList = new ArrayList<ComPicture>();
	
	public void doBefore() {
		//修改
		if (this.dreamId != null) {
			groupDream = groupDreamService.getGroupDream(dreamId);
			productTypeItem = new CodeItem();
			productTypeItem.setCode(groupDream.getProductType());
			//获取图片列表
//			this.pictureList =groupDreamService.getPictureList(dreamId);;
			this.pictureList = getPictureList(dreamId);
		}else {//新增
 			groupDream.setProductType(Constant.PRODUCT_TYPE.TICKET.name());
		}
		productTypeList = CodeSet.getInstance().getCodeList("GROUP_DREAM_PRODUCT_TYPE");
	}

	public void save() {
	    String operatorName=super.getOperatorName();
		if (validate()) {
				groupDream.setOperator(operatorName);
			if (dreamId == null){
//					groupDreamService.addGroupDream(groupDream,this.pictureList,operatorName);
					Long pkId = groupDreamService.addGroupDream(groupDream,operatorName);
					try{
						if(pictureList!=null&&  pictureList.size()>0){
							for(ComPicture picture:pictureList){
								picture.setPictureObjectId(pkId);
								picture.setPictureObjectType("GROUP_DREAM");
							}
							comPictureService.savePictureList(pictureList);
						}
					} catch(RuntimeException e) {
						// 回滚
						groupDreamService.addGroupDreamRollback(groupDream, operatorName);
						throw e;
					}
					alert("保存成功");
				}
			  else{
				groupDream.setOperator(operatorName);
				groupDreamService.updateGroupDream(groupDream,operatorName);
			  }
			this.refreshParent("search");
			this.closeWindow();
		}
	}

	public boolean validate() {

		if(groupDream.getLowDreamPrice()>=groupDream.getHighDreamPrice()){
			ZkMessage.showWarning("最低团购价格应该小于最高团购价格");
			return false;
		}
		return true;
	}
	
	/**
	 * 上传图片操作
	 * 
	 * @param imgsrc
	 */
	public void addImage(String imgname, String imgsrc) {
		if (imgname == null || imgname.equals("")) {
			alert("请录入图片名称");
			return;
		}
		if (imgsrc == null || imgsrc.equals("")) {
			alert("请选择要上传的图片");
			return;
		}

		if (this.groupDream.getDreamId() == null ||this.groupDream.getDreamId() == 0) {
			ComPicture picture = new ComPicture();
			picture.setPictureObjectType("GROUP_DREAM");
			picture.setPictureName(imgname);
			picture.setPictureUrl(imgsrc);
			picture.setIsNew(true);// 标识图片是新建产生的
			picture.setPictureId((long) pictureList.size());
			pictureList.add(picture);
		} else {
			ComPicture picture = new ComPicture();
			picture.setPictureObjectType("GROUP_DREAM");
			picture.setPictureName(imgname);
			picture.setPictureUrl(imgsrc);
			picture.setPictureObjectId(this.groupDream.getDreamId());
//			this.groupDreamService.saveGroupDreamPicture(picture);
//			this.pictureList =groupDreamService.getPictureList(this.groupDream.getDreamId());
			comPictureService.savePicture(picture);
			this.pictureList = getPictureList(groupDream.getDreamId());
		}

	}

	/**
	 * 删除图片
	 * 
	 * @param a
	 */
	public void delImage(A a) {
		Long pictureId = (Long) a.getAttribute("pictureId");
		if (this.groupDream.getDreamId()== null || this.groupDream.getDreamId() == 0) {
			for (Iterator<ComPicture> iter = pictureList.iterator(); iter.hasNext();) {
				ComPicture pic = (ComPicture) iter.next();
				if (pictureId.equals(pic.getPictureId())) {
					iter.remove();
				}
			}
		} else {
//			this.groupDreamService.deleteGroupDreamPicture(pictureId);
//			this.pictureList = groupDreamService.getPictureList(groupDream.getDreamId());
			comPictureService.deletePicture(pictureId);
			this.pictureList = getPictureList(groupDream.getDreamId());
		}
		ZkMessage.showInfo("操作成功");
	}
	/**
	 * 查询团信息的图片列表
	 * 
	 * @author: ranlongfei 2012-8-2 下午5:29:44
	 * @param dreamId
	 * @return
	 */
	private List<ComPicture> getPictureList(Long dreamId) {
		return comPictureService.getPictureByObjectIdAndType(dreamId,"GROUP_DREAM");
	}
	public GroupDreamService getGroupDreamService() {
		return groupDreamService;
	}

	public void setGroupDreamService(GroupDreamService groupDreamService) {
		this.groupDreamService = groupDreamService;
	}

	public GroupDream getGroupDream() {
		return groupDream;
	}

	public void setGroupDream(GroupDream groupDream) {
		this.groupDream = groupDream;
	}

	public List<CodeItem> getProductTypeList() {
		return productTypeList;
	}
	public Long getDreamId() {
		return dreamId;
	}

	public void setDreamId(Long dreamId) {
		this.dreamId = dreamId;
	}

	public CodeItem getProductTypeItem() {
		return productTypeItem;
	}

	public void setProductTypeItem(CodeItem productTypeItem) {
		this.productTypeItem = productTypeItem;
		this.groupDream.setProductType(productTypeItem.getCode());
	}
	
	public void setPictureList(List<ComPicture> pictureList) {
		this.pictureList = pictureList;
	}

	public List<ComPicture> getPictureList() {
		return pictureList;
	}

	public ComPictureService getComPictureService() {
		return comPictureService;
	}

	public void setComPictureService(ComPictureService comPictureService) {
		this.comPictureService = comPictureService;
	}
}
