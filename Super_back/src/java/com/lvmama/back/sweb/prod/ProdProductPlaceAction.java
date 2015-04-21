package com.lvmama.back.sweb.prod;

import com.lvmama.comm.bee.service.prod.ProdProductPlaceService;
import com.lvmama.comm.jms.MessageFactory;
import com.lvmama.comm.pet.po.place.Place;
import com.lvmama.comm.pet.po.prod.ProdProductPlace;
import com.lvmama.comm.pet.service.place.PlaceService;
import com.lvmama.comm.utils.json.JSONOutput;
import com.lvmama.comm.utils.json.JSONResult;
import com.lvmama.comm.utils.json.JSONResultException;
import com.lvmama.comm.utils.json.ResultHandleT;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.util.Assert;

import java.util.List;

@Results({
        @Result(name = "input", location = "/WEB-INF/pages/back/prod/product_place.jsp"),
        @Result(name = "auditingShow", location = "/WEB-INF/pages/back/prod/auditing/product_place_auditing_show.jsp")
})
public class ProdProductPlaceAction extends ProductAction {

    private static final long serialVersionUID = -5718122063620356709L;

    List<ProdProductPlace> placeList;
    private ProdProductPlaceService prodProductPlaceService;
    private PlaceService placeService;
    private String search;
    private ProdProductPlace place;
    private Long productPlaceId;
    private String target;

    public ProdProductPlaceAction() {
        super();
        setMenuType("place");
    }

    @Action(value = "/prod/toProductPlaceAditingShow")
    public String toProductPlaceAditingShow(){
        this.goEdit();
        return "auditingShow";
    }


    @Action(value = "/prod/toProductPlace")
    @Override
    public String goEdit() {
        if (!doBefore()) {
            return PRODUCT_EXCEPTION_PAGE;
        }
        placeList = prodProductPlaceService.selectByProductId(productId);
        return goAfter();
    }

    @Action("/prod/editProductPlace")
    @Override
    public void save() {
        JSONResult result = new JSONResult();
        try {
            //添加时标地的名称是没有出现在place当中，所以再去读了一次
            Place cp = placeService.queryPlaceByPlaceId(place.getPlaceId());
            if (cp != null) {
                place.setPlaceName(cp.getName());
            }

            place = prodProductPlaceService.insert(place, getOperatorNameAndCheck());
            sendPlaceMsg(place.getProductId());
            result.put("place", JSONObject.fromObject(place));
        } catch (Exception ex) {
            result.raise(new JSONResultException(ex.getMessage()));
        }
        result.output(getResponse());
    }

    /**
     * 指定产品的出发目的地
     */
    @Action("/prod/changeProductFT")
    public void setPlaceFT() {
        JSONResult result = new JSONResult();
        try {
            ResultHandleT<ProdProductPlace> ppp = prodProductPlaceService.changeFT(productPlaceId, target, getOperatorNameAndCheck());
            if (ppp.isFail()) {
                result.raise(ppp.getMsg());
            } else {
                sendPlaceMsg(ppp.getReturnContent().getProductId());
            }
        } catch (Exception ex) {
//			ex.printStackTrace();
            result.raise(new JSONResultException(ex.getMessage()));
        }
        result.output(getResponse());
    }

    @Action("/prod/deletePlace")
    public void deletePlace() {
        JSONResult result = new JSONResult();
        try {
            Assert.notNull(productPlaceId, "标地不存在");
            ProdProductPlace ppp = prodProductPlaceService.selectByPrimaryKey(productPlaceId);
            Assert.notNull(ppp, "标地不存在");
            prodProductPlaceService.delete(productPlaceId, getOperatorNameAndCheck());

            sendPlaceMsg(ppp.getProductId());
        } catch (Exception ex) {
            result.raise(ex);
        }
        result.output(getResponse());
    }

    @Action("/prod/searchPlace")
    public void searchPlace() {
        List<Place> list = this.placeService.selectSuggestPlaceByName(search);
        JSONArray array = new JSONArray();
        if (CollectionUtils.isNotEmpty(list)) {
            for (Place cp : list) {
                JSONObject obj = new JSONObject();
                obj.put("id", cp.getPlaceId());
                obj.put("text", cp.getName());
                array.add(obj);
            }
        }
        JSONOutput.writeJSON(getResponse(), array);
    }

    /**
     * @return the placeList
     */
    public List<ProdProductPlace> getPlaceList() {
        return placeList;
    }

    /**
     * 发送place变更消息.
     */
    private void sendPlaceMsg(final Long productId) {
        try {
            productMessageProducer.sendMsg(MessageFactory.newProductPlaceUpdateMessage(productId));
            
          //发送修改销售产品的通知ebk消息
			productMessageProducer.sendMsg(MessageFactory.newProductUpdateEbkMessage(productId));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    /**
     * @param search the search to set
     */
    public void setSearch(String search) {
        this.search = search;
    }

    /**
     * @return the place
     */
    public ProdProductPlace getPlace() {
        return place;
    }

    /**
     * @param place the place to set
     */
    public void setPlace(ProdProductPlace place) {
        this.place = place;
    }

    /**
     * @param productPlaceId the productPlaceId to set
     */
    public void setProductPlaceId(Long productPlaceId) {
        this.productPlaceId = productPlaceId;
    }

    /**
     * @param target the target to set
     */
    public void setTarget(String target) {
        this.target = target;
    }

    /**
     * @param prodProductPlaceService the prodProductPlaceService to set
     */
    public void setProdProductPlaceService(
            ProdProductPlaceService prodProductPlaceService) {
        this.prodProductPlaceService = prodProductPlaceService;
    }

    public void setPlaceService(PlaceService placeService) {
        this.placeService = placeService;
    }

}
