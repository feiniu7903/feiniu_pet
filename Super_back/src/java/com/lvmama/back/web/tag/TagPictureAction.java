package com.lvmama.back.web.tag;

import com.lvmama.back.web.BaseAction;
import com.lvmama.comm.vo.Constant;

/**
 * 图标预览类 对标签的图片进行预览
 * 
 * @author lipengcheng
 * 
 */
public class TagPictureAction extends BaseAction {

	private static final long serialVersionUID = 1467640733168807672L;
	private Long picId; // 传入的图标ID
	private String picUrl; // 存放拼接的URL

	/**
	 * 加载
	 * 
	 * @author lipengcheng
	 */
	public void doAfter() {
		showPic();
	}

	/**
	 * 通过传过来的图标ID,将其拼接为图标的url,进行预览
	 * 
	 * @author lipengcheng
	 */
	public void showPic() {
		this.picUrl = Constant.TAG_PIC_URL + this.picId.toString() + ".gif";
	}

	// set方法接收页面传入的数据
	public void setPicId(Long picId) {
		this.picId = picId;
	}

	// get方法向页面传递数据
	public String getPicUrl() {
		return picUrl;
	}

}
