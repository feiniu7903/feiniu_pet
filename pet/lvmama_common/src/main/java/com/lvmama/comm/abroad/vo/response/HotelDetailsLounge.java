package com.lvmama.comm.abroad.vo.response;

import java.io.Serializable;

public class HotelDetailsLounge implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4920800605343816273L;
	private String Name;
	private String Image;
	private String Description;
	private String MinCapacity;
	private String MaxCapacity;

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public String getImage() {
		return Image;
	}

	public void setImage(String image) {
		Image = image;
	}

	public String getDescription() {
		return Description;
	}

	public void setDescription(String description) {
		Description = description;
	}

	public String getMinCapacity() {
		return MinCapacity;
	}

	public void setMinCapacity(String minCapacity) {
		MinCapacity = minCapacity;
	}

	public String getMaxCapacity() {
		return MaxCapacity;
	}

	public void setMaxCapacity(String maxCapacity) {
		MaxCapacity = maxCapacity;
	}

}
