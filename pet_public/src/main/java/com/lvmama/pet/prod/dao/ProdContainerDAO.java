package com.lvmama.pet.prod.dao;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.math.NumberUtils;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.pet.po.prod.ProdContainer;

public class ProdContainerDAO extends BaseIbatisDAO{
	/**
	 * 根据参数查询涉及到的所有容器id
	 * @param containerCode
	 * @param fromPlaceId
	 * @param toPlaceIdStr
	 * @return
	 */
	public List<Long> selectContainerIdListProductBelongsTo(String containerCode, Long fromPlaceId, String toPlaceIdStr) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("containerCode", containerCode);
        params.put("fromPlaceId", fromPlaceId);
        params.put("toPlaceIdStr", toPlaceIdStr);
        return super.queryForList("PROD_CONTAINER.selectContainerIdListProductBelongsTo", params);
    }
	
	public List<ProdContainer> selectToPlaces(String containerCode, Long fromPlaceId, String destId, boolean showValidOnly) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("containerCode", containerCode);
        params.put("fromPlaceId", fromPlaceId);
        params.put("destId", destId);
        if (showValidOnly) {
            params.put("isToPlaceHidden", "N");
        }
        params.put("isShownInMore", "N");
        return selectByParams(params);
    }

    public List<ProdContainer> selectToPlacesMore(String containerCode, Long fromPlaceId, String destId, String zoneName, boolean showValidOnly) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("containerCode", containerCode);
        params.put("fromPlaceId", fromPlaceId);
        params.put("destId", destId);
        params.put("zoneName", zoneName);
        if (showValidOnly) {
            params.put("isToPlaceHidden", "N");
        }
        params.put("isShownInMore", "Y");
        return selectByParams(params);
    }
    public ProdContainer selectToPlace(String containerCode, String ipLocationId, String destId, boolean showValidOnly) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("containerCode", containerCode);
        params.put("ipLocationId", ipLocationId);
        params.put("destId", destId);
        if (showValidOnly) {
            params.put("isToPlaceHidden", "N");
        }
        params.put("isShownInMore", "N");
        return (ProdContainer) super.queryForObject("PROD_CONTAINER.selectByParams", params);
    }
    public List<String> selectZoneNames(String containerCode, Long fromPlaceId) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("containerCode", containerCode);
        params.put("fromPlaceId", fromPlaceId);
        return super.queryForList("PROD_CONTAINER.selectZoneNames", params);
    }

    private List<ProdContainer> selectByParams(Map<String, Object> params) {
        return super.queryForList("PROD_CONTAINER.selectByParams", params);
    }
    
    public Long insertContainer(ProdContainer prodContainer) {
        return (Long) super.insert("PROD_CONTAINER.insertContainer", prodContainer);
    }

    public int updateContainer(ProdContainer prodContainer) {
        return super.update("PROD_CONTAINER.updateContainer", prodContainer);
    }
    public int deleteContainer(Long id) {
        return super.delete("PROD_CONTAINER.deleteContainer", id);
    }
    
    public List<ProdContainer> getContainerNameCodePairs() {
        return super.queryForList("PROD_CONTAINER.getContainerNameCodePairs");
    }
    public List<ProdContainer> getFromPlacesByContainerCode(String containerCode) {
        return super.queryForList("PROD_CONTAINER.getFromPlacesByContainerCode", containerCode);
    }
    public List<ProdContainer> getToPlacesByContainerCodeAndDestId(String containerCode, String destId) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("containerCode", containerCode);
        params.put("destId", destId);
        return super.queryForList("PROD_CONTAINER.getToPlacesByContainerCodeAndDestId", params);
    }
    public List<ProdContainer> getToPlacesByContainerCodeAndFromPlaceId(String containerCode, String fromPlaceId) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("containerCode", containerCode);
        long num=NumberUtils.toLong(fromPlaceId);
        if(num>0){
        	params.put("fromPlaceId", num);
        }else{
        	return Collections.emptyList();
        }
        return super.queryForList("PROD_CONTAINER.getToPlacesByContainerCodeAndFromPlaceId", params);
    }
    public Long isFromPlaceEmpty(String containerCode){
        return (Long)super.queryForObject("PROD_CONTAINER.isFromPlaceEmpty", containerCode);
    }
}
