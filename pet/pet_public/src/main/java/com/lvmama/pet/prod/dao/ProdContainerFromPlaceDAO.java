package  com.lvmama.pet.prod.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.pet.po.prod.ProdContainerFromPlace;

public class ProdContainerFromPlaceDAO extends BaseIbatisDAO {
    @SuppressWarnings("unchecked")
    public List<ProdContainerFromPlace> selectFromPlaces(String containerCode, boolean showValidOnly) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("containerCode", containerCode);
        if (showValidOnly) {
            params.put("isFromPlaceHidden", "N");
        }
        return super.queryForList("PROD_CONTAINER_FROM_PLACE.selectFromPlaces", params);
    }

    public ProdContainerFromPlace selectValidFromPlace(String containerCode, String fromPlaceCode) {
        if (StringUtils.isEmpty(fromPlaceCode)) {
            return null;
        }
        Map<String, String> params = new HashMap<String, String>();
        params.put("containerCode", containerCode);
        params.put("fromPlaceCode", fromPlaceCode);
        params.put("isFromPlaceHidden", "N");
        return (ProdContainerFromPlace) super.queryForObject("PROD_CONTAINER_FROM_PLACE.selectFromPlaces", params);
    }

    @SuppressWarnings("unchecked")
    public List<ProdContainerFromPlace> selectValidFromPlaces(String containerCode, Long fromPlaceId) {
        if (fromPlaceId == null) {
            return null;
        }
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("containerCode", containerCode);
        params.put("fromPlaceId", fromPlaceId);
        params.put("isFromPlaceHidden", "N");
        return (List<ProdContainerFromPlace>) super.queryForList("PROD_CONTAINER_FROM_PLACE.selectFromPlaces", params);
    }

    public Long insertFromPlace(ProdContainerFromPlace fromPlace) {
        return (Long) super.insert("PROD_CONTAINER_FROM_PLACE.insertFromPlace", fromPlace);
    }

    public int updateFromPlace(ProdContainerFromPlace fromPlace) {
        return super.update("PROD_CONTAINER_FROM_PLACE.updateFromPlace", fromPlace);
    }

    public int deleteFromPlace(Long id) {
        return super.delete("PROD_CONTAINER_FROM_PLACE.deleteFromPlace", id);
    }
}
