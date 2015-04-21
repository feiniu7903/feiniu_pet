package com.lvmama.distribution.sweb;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.clutter.utils.DistributionParseUtil;
import com.lvmama.clutter.web.BaseAction;
import com.lvmama.comm.bee.po.distribution.DistributionPlaceProduct;
import com.lvmama.distribution.service.DistributionForQiHooService;
/**
 * 奇虎360接口实现
 * 
 * @author gaoxin
 * 
 */
@ParentPackage("struts-default") 
@Results({
	@Result(name = "productResult", location = "/qihoo360/ticket.jsp", type="dispatcher")
})
public class DistributionForQihoo360Action extends BaseAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3318095233772760391L;
	private DistributionForQiHooService distributionForQiHooService;
	private String placeName;
	private List<DistributionPlaceProduct> productList;
	@Action("/360/placeImage")
	public void getplaceImagexml() {
		String placeImage=distributionForQiHooService.creatPlaceImageXml(placeName);
		sendXmlResult(placeImage);
	}
	@Action("/360/ticketPriceList")
	public String getProductHtml(){
		productList=distributionForQiHooService.getProductList(placeName);
		return "productResult";
	}
	
	@Action("/360/placeGuides")
	public void getplaceGuides(){
		String placeGuides=distributionForQiHooService.getPlaceGuides(placeName);
		sendXmlResult(placeGuides);
	}
	
	@Action("/360/routeProduct")
	public void getRouteProduct(){
		int prodCountSum = distributionForQiHooService.getRouteProductCount();
		String url = DistributionParseUtil.getPropertiesByKey("lvmamaroute.indexurl");
		int pageSum=getPagesSum(prodCountSum,300);
		int pageSize=300;
		StringBuilder indexFile=new StringBuilder();
		for(int i = 0 ; i < pageSum ;i++){
			Map<String,Object> map=new HashMap<String, Object>();
			map.put("_startRow", i*pageSize+1);
			map.put("_endRow", (i+1)*pageSize);
			String routeProductXml=distributionForQiHooService.createRouteProductXml(map);
			String fileName="new_"+(i+1)+".xml";
			distributionForQiHooService.createXmlFile(routeProductXml,fileName);
			indexFile.append("").append(url+fileName).append("\n");
		}
		distributionForQiHooService.createXmlFile(indexFile.toString(), "productIndex.txt");
	}

	@Action("/360/updateRouteProduct")
	public void getUpdateRouteProduct(){
		distributionForQiHooService.updateRouteProduct();
	}
	
	/**
	 * 计算总页数
	 * @param proSum
	 * @return
	 */
	private int getPagesSum(int proSum,int pageSize) {
		return proSum % pageSize > 0 ? (proSum / pageSize) + 1 : proSum / pageSize;
	}
	
	public DistributionForQiHooService getDistributionForQiHooService() {
		return distributionForQiHooService;
	}

	public void setDistributionForQiHooService(
			DistributionForQiHooService distributionForQiHooService) {
		this.distributionForQiHooService = distributionForQiHooService;
	}

	public String getPlaceName() {
		return placeName;
	}

	public void setPlaceName(String placeName) {
		try {
			placeName = new String(placeName.getBytes("iso-8859-1"), "utf-8");
			this.placeName = java.net.URLDecoder.decode(placeName, "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
	public List<DistributionPlaceProduct> getProductList() {
		return productList;
	}
	public void setProductList(List<DistributionPlaceProduct> productList) {
		this.productList = productList;
	}

}
