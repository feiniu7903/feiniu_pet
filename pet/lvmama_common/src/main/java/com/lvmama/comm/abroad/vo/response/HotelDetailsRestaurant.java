package com.lvmama.comm.abroad.vo.response;

import java.io.Serializable;
public class HotelDetailsRestaurant implements Serializable{

		/**
	 * 
	 */
	private static final long serialVersionUID = -7280120505243802535L;
		private String Name;
		private String Image;
		private String Description;
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
}
