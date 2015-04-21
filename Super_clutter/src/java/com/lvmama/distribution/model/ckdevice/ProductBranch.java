package com.lvmama.distribution.model.ckdevice;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 
 * @author gaoxin
 *
 */
@XmlRootElement
public class ProductBranch {
	
	private List<Branch> branchList;
	
	@XmlElement(name = "branch")
	public List<Branch> getBranchList() {
		return branchList;
	}

	public void setBranchList(List<Branch> branchList) {
		this.branchList = branchList;
	}

}
