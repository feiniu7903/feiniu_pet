/**
 * 
 */
package com.lvmama.comm.bee.vo.train;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;

import com.lvmama.comm.vo.Constant;

/**
 * @author yangbin
 *
 */
public class SeatInventory implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5779067840806930529L;
	private String catalog;
	private String bookable;
	public SeatInventory(String catalog, int qty){
		this.setCatalog(catalog);
		if(qty > 0) 
			this.setBookable("true");
		else
			this.setBookable("false");
	}
	public String getCatalog() {
		return catalog;
	}
	public void setCatalog(String catalog) {
		this.catalog = catalog;
	}
	public String getBookable() {
		return bookable;
	}
	public void setBookable(String bookable) {
		this.bookable = bookable;
	}
	
	
	public boolean hasBookable(){
		return StringUtils.equals("true", bookable);
	}
	
	public String getZhCatalog(){
		return Constant.TRAIN_SEAT_CATALOG.getCnName(catalog);
	}
}
