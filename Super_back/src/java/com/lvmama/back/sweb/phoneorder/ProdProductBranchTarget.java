/**
 * 
 */
package com.lvmama.back.sweb.phoneorder;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import com.lvmama.comm.bee.po.meta.MetaProduct;
import com.lvmama.comm.bee.po.prod.ProdProductBranch;
import com.lvmama.comm.pet.po.sup.SupBCertificateTarget;
import com.lvmama.comm.pet.po.sup.SupPerformTarget;
import com.lvmama.comm.pet.po.sup.SupSettlementTarget;

/**
 * @author yangbin
 *
 */
public class ProdProductBranchTarget {

	private ProdProductBranch prodBranch;
	private List<PerformTarget> performList = new ArrayList<ProdProductBranchTarget.PerformTarget>(0);
	private List<SettlementTarget> settlementList = new ArrayList<ProdProductBranchTarget.SettlementTarget>(0);
	private List<BCertificateTarget> bcertificateList =new ArrayList<ProdProductBranchTarget.BCertificateTarget>(0);
	
	public static class MetaTarget<T>{
		private MetaProduct metaProduct;
		private List<T> targetList;

		public MetaProduct getMetaProduct() {
			return metaProduct;
		}

		public void setMetaProduct(MetaProduct metaProduct) {
			this.metaProduct = metaProduct;
		}

		public List<T> getTargetList() {
			return targetList;
		}

		public void setTargetList(List<T> targetList) {
			this.targetList = targetList;
		}
	
		
	}
	
	public static class PerformTarget extends MetaTarget<SupPerformTarget>{
	}
	public static class SettlementTarget extends MetaTarget<SupSettlementTarget>{		
	}
	
	public static class BCertificateTarget extends MetaTarget<SupBCertificateTarget>{		
	}
	
	public ProdProductBranchTarget(ProdProductBranch prodBranch) {
		super();
		this.prodBranch = prodBranch;
	}
	/**
	 * @return the prodBranch
	 */
	public ProdProductBranch getProdBranch() {
		return prodBranch;
	}	
	
	
	public List<PerformTarget> getPerformList() {
		return performList;
	}
	public void setPerformList(List<PerformTarget> performList) {
		this.performList = performList;
	}
	public List<SettlementTarget> getSettlementList() {
		return settlementList;
	}
	public void setSettlementList(List<SettlementTarget> settlementList) {
		this.settlementList = settlementList;
	}
	public List<BCertificateTarget> getBcertificateList() {
		return bcertificateList;
	}
	public void setBcertificateList(List<BCertificateTarget> bcertificateList) {
		this.bcertificateList = bcertificateList;
	}
	public void setProdBranch(ProdProductBranch prodBranch) {
		this.prodBranch = prodBranch;
	}
	public boolean isTargetEmpty(){
		return CollectionUtils.isEmpty(performList)&&CollectionUtils.isEmpty(bcertificateList)
				&&CollectionUtils.isEmpty(settlementList);
	}
	
}
