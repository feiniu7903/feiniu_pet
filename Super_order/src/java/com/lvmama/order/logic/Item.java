/**
 * 
 */
package com.lvmama.order.logic;

import java.util.Date;

/**
 * @author taiqichao
 *
 */
public class Item {

	private Long metaBranchId;
	private Date date;
	
	
	public Long getMetaBranchId() {
		return metaBranchId;
	}
	public void setMetaBranchId(Long metaBranchId) {
		this.metaBranchId = metaBranchId;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result
				+ ((metaBranchId == null) ? 0 : metaBranchId.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Item other = (Item) obj;
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
			return false;
		if (metaBranchId == null) {
			if (other.metaBranchId != null)
				return false;
		} else if (!metaBranchId.equals(other.metaBranchId))
			return false;
		return true;
	}
	public Item(Long metaBranchId, Date date) {
		super();
		this.metaBranchId = metaBranchId;
		this.date = date;
	}
	
}
