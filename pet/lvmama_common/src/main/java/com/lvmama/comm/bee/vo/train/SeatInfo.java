/**
 * 
 */
package com.lvmama.comm.bee.vo.train;

/**
 * @author yangbin
 *
 */
public class SeatInfo extends SeatInventory {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8987686670448221279L;
	public SeatInfo(long price, String catalog){
		super(catalog, 1);
		this.setPrice(price);
	}
	private long price;//分
	
	private boolean existsFlag=false;

	public long getPrice() {
		return price;
	}

	public void setPrice(long price) {
		this.price = price;
	}
	
	public void makeExitsFlag(){
		existsFlag=true;
	}

	public boolean isExistsFlag() {
		return existsFlag;
	}
	
}
