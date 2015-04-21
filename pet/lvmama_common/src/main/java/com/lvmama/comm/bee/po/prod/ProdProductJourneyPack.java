package com.lvmama.comm.bee.po.prod;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.springframework.util.CollectionUtils;

@SuppressWarnings("serial")
public class ProdProductJourneyPack implements Serializable {

	
	private String packName;
	private Long prodJourneyPackId;
	private Long productId;
	private String valid;
	private String onLine;
	private List<ProdPackJourneyProduct> prodPackJourneyProducts;
	
	private List<ProdProductJourney> prodProductJourneys;
	
	private List<String> prodBranchIds;
	
	public String getPackName() {
		return packName;
	}
	public void setPackName(String packName) {
		this.packName = packName;
	}
	
	
	public Long getProdJourneyPackId() {
		return prodJourneyPackId;
	}
	public void setProdJourneyPackId(Long prodJourneyPackId) {
		this.prodJourneyPackId = prodJourneyPackId;
	}
	public String getOnLine() {
		return onLine;
	}
	public void setOnLine(String onLine) {
		this.onLine = onLine;
	}
	public Long getProductId() {
		return productId;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	public String getValid() {
		return valid;
	}
	public void setValid(String valid) {
		this.valid = valid;
	}
	public List<ProdPackJourneyProduct> getProdPackJourenyProducts() {
		return prodPackJourneyProducts;
	}
	public void setProdPackJourenyProducts(
			List<ProdPackJourneyProduct> prodPackJourenyProducts) {
		this.prodPackJourneyProducts = prodPackJourenyProducts;
	}
	public List<ProdProductJourney> getProdProductJourneys() {
		return prodProductJourneys;
	}
	public void setProdProductJourneys(List<ProdProductJourney> prodProductJourneys) {
		this.prodProductJourneys = prodProductJourneys;
	}
	
	
	public List<ProdPackJourneyProduct> getProdPackJourneyProducts() {
		return prodPackJourneyProducts;
	}
	public void setProdPackJourneyProducts(
			List<ProdPackJourneyProduct> prodPackJourneyProducts) {
		this.prodPackJourneyProducts = prodPackJourneyProducts;
	}

	public List<String> getProdBranchIds() {
		return prodBranchIds;
	}
	public void setProdBranchIds(List<String> prodBranchId) {
		this.prodBranchIds = prodBranchId;
	}
	/**
	 * 重设产品选中情况 以便页面显示
	 */
	public void initCheckedProduct() {
		if(CollectionUtils.isEmpty(prodProductJourneys)) return;
		for(ProdProductJourney prodProductJourney:prodProductJourneys){
			if(prodProductJourney.getProdJourneyGroupMap()==null) continue;
			Set<String> key = prodProductJourney.getProdJourneyGroupMap().keySet();
			if(key==null) continue;
			for(Iterator it = key.iterator(); key!=null && it.hasNext();) {
	        	List<ProdJourneyProduct>  prodJourneyProductList= (List) prodProductJourney.getProdJourneyGroupMap().get(it.next());
	        	if(prodJourneyProductList!=null){
	        		for(ProdJourneyProduct pjp:prodJourneyProductList){
	        			prodJourneyProductsetPackProduct(pjp);
		        	}
	        	}
			}
		}
	}
	private void prodJourneyProductsetPackProduct(ProdJourneyProduct pjp) {
		if(prodPackJourneyProducts!=null){
			for(ProdPackJourneyProduct prodPackJourneyProduct:prodPackJourneyProducts){
				if(pjp.getJourneyProductId().equals(prodPackJourneyProduct.getJourneyProductId())){		       
					if(pjp.getProdBranchId().equals(prodPackJourneyProduct.getProdBranchId())){
						pjp.setProdPackJourneyProduct(prodPackJourneyProduct);
					}
				}
			}
		}
	}
	
	/*public static void main(String ary[]){
		
		
		List<ProdPackJourneyProduct> prodPackJourneyProducts = new ArrayList<ProdPackJourneyProduct>();
		ProdPackJourneyProduct pjp = new ProdPackJourneyProduct();
		pjp.setProdBranchId(12l);
		pjp.setProdJourneyId(10l);
		prodPackJourneyProducts.add(pjp);
		
		
		Map<String,List<ProdJourneyProduct>> prodJourneyGroupMap=new HashMap<String,List<ProdJourneyProduct>>();
		List<ProdJourneyProduct> pjpList = new ArrayList<ProdJourneyProduct>();
		
		ProdJourneyProduct pjpt = new ProdJourneyProduct();
		pjpt.setProdBranchId(12l);
		pjpt.setProdJourenyId(10l);
		pjpt.setRequire("");
		pjpList.add(pjpt);
		
		pjpt = new ProdJourneyProduct();
		pjpt.setProdBranchId(13l);
		pjpt.setProdJourenyId(10l);
		pjpt.setRequire("");
		pjpList.add(pjpt);
		
		pjpt = new ProdJourneyProduct();
		pjpt.setProdBranchId(14l);
		pjpt.setProdJourenyId(10l);
		pjpList.add(pjpt);
		prodJourneyGroupMap.put("aa", pjpList);
		
		List<ProdProductJourney> prodProductJourneys = new ArrayList<ProdProductJourney>();
		ProdProductJourney pj = new ProdProductJourney();
		pj.setProdJourneyGroup(prodJourneyGroupMap);
		prodProductJourneys.add(pj);
		
		ProdProductJourneyPack pp = new ProdProductJourneyPack();
		pp.setProdPackJourneyProducts(prodPackJourneyProducts);
		pp.setProdProductJourneys(prodProductJourneys);
		pp.initCheckedProduct();
		System.out.println("dd");
		
		System.out.println("dd" + Long.valueOf("23232"));
		System.out.println("dd" + Long.valueOf("2323"));
	}*/
	
	public static void main(String stree[]){
		List<ProdProductJourneyPack> list = new ArrayList<ProdProductJourneyPack>();
		ProdProductJourneyPack  prodProductJourneyPack = new ProdProductJourneyPack();
		prodProductJourneyPack.setProdJourneyPackId(2l);
		list.add(prodProductJourneyPack);
		prodProductJourneyPack = new ProdProductJourneyPack();
		prodProductJourneyPack.setProdJourneyPackId(3l);
		list.add(prodProductJourneyPack);
		prodProductJourneyPack = new ProdProductJourneyPack();
		prodProductJourneyPack.setProdJourneyPackId(4l);
		list.add(prodProductJourneyPack);
		
		List<ProdProductJourneyPack> list2 = new ArrayList<ProdProductJourneyPack>();
		prodProductJourneyPack = new ProdProductJourneyPack();
		prodProductJourneyPack.setProdJourneyPackId(2l);
		list2.add(prodProductJourneyPack);
		prodProductJourneyPack = new ProdProductJourneyPack();
		prodProductJourneyPack.setProdJourneyPackId(3l);
		list2.add(prodProductJourneyPack);
		prodProductJourneyPack = new ProdProductJourneyPack();
		prodProductJourneyPack.setProdJourneyPackId(5l);
		list2.add(prodProductJourneyPack);
		
		List<ProdProductJourneyPack> list3 = new ArrayList<ProdProductJourneyPack>();
		List<ProdProductJourneyPack> list4 = new ArrayList<ProdProductJourneyPack>();
		for(ProdProductJourneyPack str:list){
			for(ProdProductJourneyPack p:list2){
				if(str.prodJourneyPackId.equals(p.getProdJourneyPackId())){
					list3.add(str);
					list4.add(p);
				}
			}
		}
		list.removeAll(list3);
		list2.removeAll(list4);
		System.out.println("list === " +list.size() + "   list2 === " +list2.size()  + "   list3 === " +list3.size());
	}
}
