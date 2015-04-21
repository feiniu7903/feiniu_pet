package com.lvmama.front.web.home;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;

import org.apache.commons.lang3.StringUtils;

import com.lvmama.comm.pet.po.prod.ProdContainer;
import com.lvmama.comm.pet.po.prod.ProdContainerFromPlace;
import com.lvmama.comm.pet.po.prod.ProdTag;
import com.lvmama.comm.pet.vo.ContainerPlaceBean;
import com.lvmama.comm.utils.MemcachedUtil;
import com.lvmama.comm.utils.ServletUtil;
import com.lvmama.comm.utils.homePage.PindaoPageUtils;
import com.lvmama.comm.vo.Constant;

public abstract class BothTemplateHomeAction extends BaseHomeAction {
	private static final long serialVersionUID = 3246649538238307362L;
	
	/**
	 * 默认的城市出发地
	 */
	protected static final Long DEFAULT_FROM_PLACE_ID = 79L;

	protected Long fromPlaceId;
	protected String toPlaceId;
	protected String fromPlaceCode;
	protected String fromPlaceName;
 	protected String provinceId;
	protected String stationName;
	private List<ContainerPlaceBean> tabPlaceList = new ArrayList<ContainerPlaceBean>();
	
	protected static final List<ProdTag> emptyList = new ArrayList<ProdTag>();

	public void init(String containerCode, String channel)   {
		if (StringUtils.isBlank(provinceId)) {
			provinceId = (String) ServletUtil.getCookieValue(getRequest(),
					Constant.IP_PROVINCE_PLACE_ID);
			if (provinceId == null) {
				provinceId = (String) getRequest().getAttribute(
						Constant.IP_PROVINCE_PLACE_ID);
			}
		} else {
			ServletUtil.addCookie(getResponse(), Constant.IP_PROVINCE_PLACE_ID,
					provinceId, 30);
		}
		//如果cookie和请求都没有取到省provinceId
		if(StringUtils.isEmpty(provinceId)){
			provinceId=PindaoPageUtils.PROVINCE.shanghai.getCode();
		}
		 fromPlaceId=PindaoPageUtils.executeDataForPindao(provinceId,channel);
		 if(fromPlaceId==null){
			 fromPlaceId=DEFAULT_FROM_PLACE_ID;
		 }
		if (fromPlaceCode == null) {
			fromPlaceCode = PindaoPageUtils.PLACEID_PLACECODE.getPlacecode(fromPlaceId);
		}
		if(fromPlaceName==null){
			fromPlaceName = (String) getRequest().getAttribute(Constant.IP_FROM_PLACE_NAME);

		}
		if(stationName==null){
			stationName=PindaoPageUtils.PROVINCE.getCnName(provinceId);
		}
		super.initSeoIndexPage(channel);
 	}
	@Deprecated
	private String getDefaultToPlaceId(String containerCode) {
		String key = "getProdContainerToPlaces_" + containerCode + "_" + fromPlaceId + "_3548";
		List<ProdContainer> toPlaces = (List<ProdContainer>) MemcachedUtil.getInstance().get(key);
		if (toPlaces == null) {
			toPlaces = prodContainerProductService.getContainers(containerCode, fromPlaceId, "3548");
			MemcachedUtil.getInstance().set(key, MemcachedUtil.ONE_HOUR,toPlaces);
		}
		if (!toPlaces.isEmpty()) {
			return toPlaces.get(0).getToPlaceId();
		}
		return null;
	}

	@Deprecated
	private void buildTabPlaceList(String containerCode) {
		String key = "both_buildTabPlaceList_"+fromPlaceId+"_"+containerCode+"_3548";
		tabPlaceList = (List<ContainerPlaceBean>) MemcachedUtil.getInstance().get(key);
		if (tabPlaceList == null || tabPlaceList.size() == 0) {
			tabPlaceList = prodContainerProductService.getProdContainerToPlacesFromParent(containerCode, fromPlaceId, "3548");
			MemcachedUtil.getInstance().set(key,  MemcachedUtil.ONE_HOUR,tabPlaceList);
		}
	}
	@Deprecated
	protected void getAjaxFromPlaceList(List<ProdContainerFromPlace> fromPlaces) throws IOException {
		JSONArray jsonStr = JSONArray.fromObject(fromPlaces);
		this.getResponse().setContentType("application/json; charset=utf-8");
		this.getResponse().getWriter().println(jsonStr);
	}

	public Long getFromPlaceId() {
		return fromPlaceId;
	}

	public void setFromPlaceId(Long fromPlaceId) {
		this.fromPlaceId = fromPlaceId;
	}

	public String getToPlaceId() {
		return toPlaceId;
	}

	public void setToPlaceId(String toPlaceId) {
		this.toPlaceId = toPlaceId;
	}

	public void setFromPlaceCode(String fromPlaceCode) {
		this.fromPlaceCode = fromPlaceCode;
	}

	public String getFromPlaceCode() {
		return fromPlaceCode;
	}

	public List<ContainerPlaceBean> getTabPlaceList() {
		return tabPlaceList;
	}

    public String getFromPlaceName() {
        return fromPlaceName;
    }

    public void setFromPlaceName(String fromPlaceName) {
        this.fromPlaceName = fromPlaceName;
    }

	/**
	 * @return the provinceId
	 */
	public String getProvinceId() {
		return provinceId;
	}
	/**
	 * @param provinceId the provinceId to set
	 */
	public void setProvinceId(String provinceId) {
		this.provinceId = provinceId;
	}
	/**
	 * @return the stationName
	 */
	public String getStationName() {
		return stationName;
	}
	/**
	 * @param stationName the stationName to set
	 */
	public void setStationName(String stationName) {
		this.stationName = stationName;
	}

}
