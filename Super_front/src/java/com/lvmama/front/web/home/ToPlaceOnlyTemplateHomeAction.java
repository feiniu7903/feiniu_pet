package com.lvmama.front.web.home;

import java.util.ArrayList;
import java.util.List;

import com.lvmama.comm.pet.po.prod.ProdContainer;
import com.lvmama.comm.pet.po.seo.SeoIndexPage;
import com.lvmama.comm.pet.vo.ContainerPlaceBean;
import com.lvmama.comm.utils.MemcachedUtil;
import com.lvmama.comm.utils.ServletUtil;
import com.lvmama.comm.vo.Constant;

public abstract class ToPlaceOnlyTemplateHomeAction extends BaseHomeAction {
	private static final long serialVersionUID = 8420403189081671847L;
		
	protected String containerCode;
	protected String provincePlaceId;
	protected String cityPlaceId;
	protected String fromPlaceCode;
	protected Long fromPlaceId;
	protected String fromPlaceName;

	private List<ContainerPlaceBean> tabPlaceList = new ArrayList<ContainerPlaceBean>();
	
	protected void init(String channel) {
	    provincePlaceId = (String) getRequest().getAttribute(Constant.DEFAULT_PROVINCE_PLACE_ID);
        cityPlaceId = (String) getRequest().getAttribute(Constant.DEFAULT_CITY_PLACE_ID);
        if (provincePlaceId == null) {
    		String ipProvincePlaceId = (String) getRequest().getAttribute(Constant.IP_PROVINCE_PLACE_ID);
    		String ipCityPlaceId = (String) getRequest().getAttribute(Constant.IP_CITY_PLACE_ID);
    		if (!"".equals(ipProvincePlaceId) && ipProvincePlaceId != null) {
    		    provincePlaceId = this.getDefaultPlaceId(containerCode, ipProvincePlaceId, "3548");
            }
            if (provincePlaceId == null || "".equals(provincePlaceId)) {
                provincePlaceId = "79";
                cityPlaceId = "79";
            } else if (!"".equals(ipCityPlaceId)) {
                cityPlaceId = this.getDefaultPlaceId(containerCode, ipCityPlaceId, null);
            }
            ServletUtil.addCookie(super.getResponse(), Constant.DEFAULT_PROVINCE_PLACE_ID, provincePlaceId, 30);
            ServletUtil.addCookie(super.getResponse(), Constant.DEFAULT_CITY_PLACE_ID, cityPlaceId, 30);
        }
		if (fromPlaceCode == null) {
			fromPlaceCode = (String) getRequest().getAttribute(Constant.IP_AREA_LOCATION);
		}
		if (fromPlaceId == null) {
            fromPlaceId = (Long) getRequest().getAttribute(Constant.IP_FROM_PLACE_ID);
        }
		if (fromPlaceName == null) {
		    fromPlaceName = (String) getRequest().getAttribute(Constant.IP_FROM_PLACE_NAME);
		}
		super.initSeoIndexPage(channel);
	}

	private String getDefaultPlaceId(String containerCode, String ipLocationId, String destId) {
		String key = "ToPlaceOnlyTemplateHomeAction_getDefaultPlaceId" + "_" + containerCode + "_" + ipLocationId + "_" + destId;
		ProdContainer prodContainer = (ProdContainer) MemcachedUtil.getInstance().get(key);
		if (prodContainer == null) {
			prodContainer = prodContainerProductService.getToPlace(containerCode, ipLocationId, destId);
			MemcachedUtil.getInstance().set(key, MemcachedUtil.ONE_HOUR, prodContainer);
		}
		if (prodContainer!=null) {
			return prodContainer.getToPlaceId();
		}else{
			return "";
		}
	}

	protected void buildTabPlaceList(String containerCode, String destId) {
		String key = "toplace_getProdContainerToPlaces_" + containerCode + "_null_" + destId;
		tabPlaceList = (List<ContainerPlaceBean>) MemcachedUtil.getInstance().get(key);
		if (tabPlaceList == null || tabPlaceList.size() == 0) {
			tabPlaceList = prodContainerProductService.getProdContainerToPlacesFromParent(containerCode, null, destId);
			MemcachedUtil.getInstance().set(key, MemcachedUtil.ONE_HOUR,tabPlaceList);
		}
	}

	public SeoIndexPage getComSeoIndexPage() {
		return comSeoIndexPage;
	}

	public void setToPlaceId(String toPlaceId) {
		this.provincePlaceId = toPlaceId;
	}

	public String getProvincePlaceId() {
		return provincePlaceId;
	}

	public String getCityPlaceId() {
		return cityPlaceId;
	}

	public List<ContainerPlaceBean> getTabPlaceList() {
		return tabPlaceList;
	}
	
	public void setFromPlaceCode(String fromPlaceCode) {
		this.fromPlaceCode = fromPlaceCode;
	}

	public String getFromPlaceCode() {
		return fromPlaceCode;
	}

    public Long getFromPlaceId() {
        return fromPlaceId;
    }

	public void setFromPlaceId(Long fromPlaceId) {
		this.fromPlaceId = fromPlaceId;
	}

    public String getFromPlaceName() {
        return fromPlaceName;
    }
    
    public void setFromPlaceName(String fromPlaceName) {
        this.fromPlaceName = fromPlaceName;
    }
}
