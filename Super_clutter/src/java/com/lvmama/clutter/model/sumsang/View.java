package com.lvmama.clutter.model.sumsang;

import java.io.Serializable;

import com.lvmama.clutter.utils.JSONUtil;

public class View implements Serializable {
	private static final long serialVersionUID = -7956612427815193776L;
	private String id;
	private ElementDataImage image;
	private ElementDataText text;
	private ElementDataBarcode barcode;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public ElementDataImage getImage() {
		return image;
	}

	public void setImage(ElementDataImage image) {
		this.image = image;
	}

	public ElementDataText getText() {
		return text;
	}

	public void setText(ElementDataText text) {
		this.text = text;
	}

	public ElementDataBarcode getBarcode() {
		return barcode;
	}

	public void setBarcode(ElementDataBarcode barcode) {
		this.barcode = barcode;
	}

	@Override
	public String toString() {
		return "View [id=" + id + ", image=" + image + ", text=" + text
				+ ", barcode=" + barcode + "]";
	}

	public static void main(String[] args) {
		View view = new View();
		view.setId("test");
		ElementDataImage image = new ElementDataImage();
		image.setBgcolor("#FFFFFF");
		view.setImage(image);
		System.out.println(JSONUtil.jsonPropertyFilter(view));
	}

}
