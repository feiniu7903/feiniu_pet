package com.lvmama.comm.pet.po.prod;

import java.io.Serializable;

/**
 * 电子合同
 * 
 * @author Brian
 *
 */
public class ProdEContract implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4342892032781282593L;
	private Long eContractId;
	private String eContractTemplate;
	private Long productId;
	private String travelFormalities = "OTHERS";
	private String otherTravelFormalities = "/";
	private String guideService;
	private String groupType;
	private String agency;
	private String agencyAddress;
	private String pathway;
	private String complement;
	private String margin;
	
	public String getContractNoPreffic() {
		String contactNoPreffic=null;
		if(null!=eContractTemplate){
			String[] array=eContractTemplate.split("-");
			if(null!=array){
				contactNoPreffic=array[0].toLowerCase();
			}
		}
		return contactNoPreffic;
	}
	
	public Long geteContractId() {
		return eContractId;
	}
	public void seteContractId(Long eContractId) {
		this.eContractId = eContractId;
	}
	public Long getProductId() {
		return productId;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	public String getTravelFormalities() {
		return travelFormalities;
	}
	public void setTravelFormalities(String travelFormalities) {
		this.travelFormalities = travelFormalities;
	}
	public String getOtherTravelFormalities() {
		return otherTravelFormalities;
	}
	public void setOtherTravelFormalities(String otherTravelFormalities) {
		this.otherTravelFormalities = otherTravelFormalities;
	}
	public String getGuideService() {
		return guideService;
	}
	public void setGuideService(String guideService) {
		this.guideService = guideService;
	}
	public String getGroupType() {
		return groupType;
	}
	public void setGroupType(String groupType) {
		this.groupType = groupType;
	}
	public String getAgency() {
		return agency;
	}
	public void setAgency(String agency) {
		this.agency = agency;
	}
	public String getAgencyAddress() {
		return agencyAddress;
	}
	public void setAgencyAddress(String agencyAddress) {
		this.agencyAddress = agencyAddress;
	}
	public String getPathway() {
		return pathway;
	}
	public void setPathway(String pathway) {
		this.pathway = pathway;
	}
	public String getComplement() {
		return complement;
	}
	public void setComplement(String complement) {
		this.complement = complement;
	}
	public String getEContractTemplate() {
		return eContractTemplate;
	}
	public void setEContractTemplate(String eContractTemplate) {
		this.eContractTemplate = eContractTemplate;
	}

	public String getMargin() {
		return margin;
	}

	public void setMargin(String margin) {
		this.margin = margin;
	}
	
	
		
}