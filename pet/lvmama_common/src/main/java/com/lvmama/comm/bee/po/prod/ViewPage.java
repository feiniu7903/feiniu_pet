package com.lvmama.comm.bee.po.prod;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.lvmama.comm.pet.po.pub.ComPicture;
import com.lvmama.comm.vo.Constant;

public class ViewPage implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5934424022876381474L;
	private Long pageId;
	private Long productId;
	private String title;
	private Date beginSellTime;
	private Date endSellTime;
	private Date createTime;
	private Long step;

	private Date beginSellTimeShort;// 用于zul里数据绑定
	private Date beginSellTimeScale;
	private Date endSellTimeShort;
	private Date endSellTimeScale;

	private ProdProduct product;

	private Map<String, Object> contents = new HashMap<String, Object>();
	private List<ViewContent> contentList = new ArrayList<ViewContent>();
	private List<ComPicture> pictureList = new ArrayList<ComPicture>();
	
	public void initContents(boolean isMultiJourney) {
		for(int i=0;i<Constant.VIEW_CONTENT_TYPE.values().length;i++) {
			String name = Constant.VIEW_CONTENT_TYPE.values()[i].name();
			if(isMultiJourney) {
				if(Constant.VIEW_CONTENT_TYPE.COSTCONTAIN.name().equals(name) || Constant.VIEW_CONTENT_TYPE.NOCOSTCONTAIN.name().equals(name)) {
					continue;
				}
			}
			ViewContent content = new ViewContent();
			content.setPageId(pageId);
			content.setContentType(name);
			contentList.add(content);
		}
	}
	
	public Long getPageId() {
		return pageId;
	}

	public void setPageId(Long pageId) {
		this.pageId = pageId;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title == null ? null : title.trim();
	}

	public Date getBeginSellTime() {
		return beginSellTime;
	}

	public void setBeginSellTime(Date beginSellTime) {
		if (beginSellTime != null) {// 从ibatis获取数据时初始化数据
			this.beginSellTimeShort = beginSellTime;
			this.beginSellTimeScale = beginSellTime;
		}
		this.beginSellTime = beginSellTime;
	}

	public Date getEndSellTime() {
		return endSellTime;
	}

	public void setEndSellTime(Date endSellTime) {
		if (endSellTime != null) {// 从ibatis获取数据时初始化数据
			this.endSellTimeShort = endSellTime;
			this.endSellTimeScale = endSellTime;
		}
		this.endSellTime = endSellTime;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Long getStep() {
		return step;
	}

	public void setStep(Long step) {
		this.step = step;
	}

	public Date getBeginSellTimeShort() {
		return beginSellTimeShort;
	}

	public void setBeginSellTimeShort(Date beginSellTimeShort) {
		this.beginSellTimeShort = beginSellTimeShort;
	}

	public Date getBeginSellTimeScale() {
		return beginSellTimeScale;
	}

	public void setBeginSellTimeScale(Date beginSellTimeScale) {
		this.beginSellTimeScale = beginSellTimeScale;
	}

	public Date getEndSellTimeShort() {
		return endSellTimeShort;
	}

	public void setEndSellTimeShort(Date endSellTimeShort) {
		this.endSellTimeShort = endSellTimeShort;
	}

	public Date getEndSellTimeScale() {
		return endSellTimeScale;
	}

	public void setEndSellTimeScale(Date endSellTimeScale) {
		this.endSellTimeScale = endSellTimeScale;
	}

	public ProdProduct getProduct() {
		return product;
	}

	public void setProduct(ProdProduct product) {
		this.product = product;
	}

	public Map<String, Object> getContents() {
		return contents;
	}

	public void setContents(Map<String, Object> contents) {
		this.contents = contents;
	}

	public List<ViewContent> getContentList() {
		return contentList;
	}

	public void setContentList(List<ViewContent> contentList) {
		this.contentList = contentList;
	}

	public List<ComPicture> getPictureList() {
		return pictureList;
	}

	public void setPictureList(List<ComPicture> pictureList) {
		this.pictureList = pictureList;
	}

	public void initContents(List<ViewContent> contentList) {
		this.contentList = contentList;
		for(int i=0;i<contentList.size();i++) {
			ViewContent content = contentList.get(i);
			contents.put(content.getContentType(), content);
		}
		
	}
	
	public boolean hasContent(String viewContentType){
		if(!contents.containsKey(viewContentType)){
			return false;
		}
		return StringUtils.isNotEmpty(((ViewContent)contents.get(viewContentType)).getContent());
	}
}