package com.lvmama.distribution.model.qihoo;

import java.util.List;

import com.lvmama.comm.bee.po.distribution.DistributionPlaceImage;
import com.lvmama.comm.bee.po.distribution.DistributionPlaceProduct;
import com.lvmama.distribution.util.DistributionUtil;

public class Document {
	private String placename;
	private List<DistributionPlaceImage> images;
	private String picsrc;
	private String listurl;
	private String downloadurl;
	private List<QiHooNote> QiHooNotes;
	private List<Item> items;
	List<DistributionPlaceProduct> productList;
	public Document(){}
	public Document(List<DistributionPlaceProduct> routeProductList){
		this.productList=routeProductList;
	}
	
	public String buildDocumentForImage(){
		StringBuilder buf = new StringBuilder();
		buf.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
		buf.append("<document>");
		buf.append(DistributionUtil.buildXmlElementInCDATA("placename",this.placename));
		for(DistributionPlaceImage image :images){
			buf.append(image.buildImage());
		}
		buf.append("</document>");
		return buf.toString();
	}
	
	public String buildDocumentForTravelNote(){
		StringBuilder buf = new StringBuilder();
		buf.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
		buf.append("<document>");
		buf.append(DistributionUtil.buildXmlElementInCDATA("placename",this.placename));
		buf.append(DistributionUtil.buildXmlElement("picsrc",this.picsrc));
		buf.append(DistributionUtil.buildXmlElement("listurl",this.listurl));
		buf.append(DistributionUtil.buildXmlElement("downloadurl",this.downloadurl));
		for(QiHooNote qiHooNotes : QiHooNotes){
			buf.append(qiHooNotes.buildNoteXml());
		}
		buf.append("</document>");
		return buf.toString();
	}
	
	public String buildDocumentForRoute(){
		StringBuilder buf = new StringBuilder();
		buf.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
		buf.append("<document>");
		for(DistributionPlaceProduct product:productList){
			if(product!=null){
				Item item = new Item(product);
				buf.append(item.buildItemXml());
			}
		}
		buf.append("</document>");
		return buf.toString();
	}
	public String buildDocumentForUpdateRoute(){
		StringBuilder buf = new StringBuilder();
		buf.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
		buf.append("<document>");
		for(DistributionPlaceProduct product:productList){
			if(product!=null){
				Item item = new Item(product);
				buf.append(item.buildUpdateItemXml());
			}
		}
		buf.append("</document>");
		return buf.toString();
	}

	
	
	public String getPlacename() {
		return placename;
	}
	public void setPlacename(String placename) {
		this.placename = placename;
	}

	public List<DistributionPlaceImage> getImages() {
		return images;
	}

	public void setImages(List<DistributionPlaceImage> images) {
		this.images = images;
	}
	public String getListurl() {
		return listurl;
	}

	public void setListurl(String listurl) {
		this.listurl = listurl;
	}

	public String getDownloadurl() {
		return downloadurl;
	}

	public void setDownloadurl(String downloadurl) {
		this.downloadurl = downloadurl;
	}

	public List<QiHooNote> getQiHooNotes() {
		return QiHooNotes;
	}

	public void setQiHooNotes(List<QiHooNote> qiHooNotes) {
		QiHooNotes = qiHooNotes;
	}

	public String getPicsrc() {
		return picsrc;
	}

	public void setPicsrc(String picsrc) {
		this.picsrc = picsrc;
	}

	public List<Item> getItems() {
		return items;
	}

	public void setItems(List<Item> items) {
		this.items = items;
	}
	public List<DistributionPlaceProduct> getProductList() {
		return productList;
	}
	public void setProductList(List<DistributionPlaceProduct> productList) {
		this.productList = productList;
	}
}
