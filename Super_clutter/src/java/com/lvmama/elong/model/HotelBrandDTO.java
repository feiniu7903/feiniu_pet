package com.lvmama.elong.model;

import java.io.Serializable;


public class HotelBrandDTO implements Serializable {
	private static final long serialVersionUID = -320147629185190533L;
	protected String brandId;
    protected String name;
    protected String letters;
    
    public HotelBrandDTO(String brandId,String name,String letters){
    	this.brandId=brandId;
    	this.name=name;
    	this.letters=letters;
    }
	public String getBrandId() {
		return brandId;
	}
	public void setBrandId(String brandId) {
		this.brandId = brandId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLetters() {
		return letters;
	}
	public void setLetters(String letters) {
		this.letters = letters;
	}
    
    

}
