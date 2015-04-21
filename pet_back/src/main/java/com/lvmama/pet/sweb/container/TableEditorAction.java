package com.lvmama.pet.sweb.container;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.comm.BackBaseAction;
import com.lvmama.comm.pet.po.place.Place;
import com.lvmama.comm.pet.po.prod.ProdContainer;
import com.lvmama.comm.pet.po.prod.ProdContainerFromPlace;
import com.lvmama.comm.pet.service.place.PlaceService;
import com.lvmama.comm.pet.service.prod.ProdContainerProductService;
import com.lvmama.comm.pet.service.pub.PlaceCityService;
import com.lvmama.comm.utils.UtilityTool;

@Results({
    @Result(name = "tableEditor", location = "/WEB-INF/pages/back/container/tableEditor.ftl", type = "freemarker"),
    @Result(name = "adminTableEditor", location = "/WEB-INF/pages/back/container/adminTableEditor.ftl", type = "freemarker"),
    @Result(name = "adminTableEditorLogin", location = "/WEB-INF/pages/back/container/adminTableEditorLogin.ftl", type = "freemarker")
})
public class TableEditorAction extends BackBaseAction {
    private static final long serialVersionUID = 2389829443827310752L;
    private ProdContainerProductService prodContainerProductService;
    private PlaceCityService placeCityService;
    private List<ProdContainer> containers;
    private List<ProdContainerFromPlace> fromPlaces;
    private List<ProdContainer> toPlaces;
    private PlaceService placeService;
  

	private Long[] id;
    private String[] containerName;// 容器名称
    private String[] containerCode;// 容器代码
    private Long[] fromPlaceId;// 出发地ID
    private String[] toPlaceId;// 目的地ID
    private String[] fromPlaceName;// 出发地名称
    private String[] toPlaceName;// 目的地名称
    private String[] destId;// 上级目的地ID
    private Integer[] toPlaceSeq;// 目的地排序
    private String[] ipLocationId;// 当目的地为省时：存usr_capital表的capital_id；当目的地为市时：存usr_city表的city_id
    private String[] isToPlaceHidden;// 是否隐藏目的地
    private String[] isShownInMore;// 是否显示在更多里
    private String[] zoneName;// 区域名称，如：华东、华北
    private Integer[] zoneSeq;// 区域排序
    private String[] displayedToPlaceName;// 目的地显示在频道页前台的显示名称
    private String[] referredFromPlaceId; //引用同目的地下另一个出发地的产品
    private String[] isChanged;

    private Long delId;
    private String placeName;
    private String selContainerCode = "DZMP_RECOMMEND";
    private Long selFromPlaceId;
    private String selToPlaceId = "3548";

    private String username;
    private String password;

    @Action("/tableEditor")
    public String tableEditor() {
        save();
        view();
        return "tableEditor";
    }

    @Action("/adminTableEditor")
    public String adminTableEditor() {
        if (this.getSession().getAttribute("username") == null) {
            return "adminTableEditorLogin";
        }
        save();
        view();
        return "adminTableEditor";
    }

    @Action("/adminTableEditor/del")
    public String adminTableEditorDel() {
        if (delId != null) {
        	prodContainerProductService.deleteContainerProduct(delId, null);
        	prodContainerProductService.deleteContainer(delId);
        }
        containers = prodContainerProductService.getContainers(selContainerCode, selFromPlaceId, selToPlaceId);
        return "adminTableEditor";
    }

    @Action("/tableEditor/ajaxGetIpLocationId")
    public void ajaxGetIpLocationId() throws IOException {
        String ipLocationId = placeCityService.getCapitalOrCityIdByName(placeName);
        this.getResponse().setContentType("text/plain; charset=utf-8");
        this.getResponse().getWriter().println(ipLocationId);
    }

    @Action("/tableEditor/ajaxGetToPlaceId")
    public void ajaxGetToPlaceId() throws IOException {
        Place comPlace = placeService.getPlaceByName(placeName,"Y");
        String toPlaceId = "";
        if (comPlace != null) {
            toPlaceId = String.valueOf(comPlace.getPlaceId());
        }
        this.getResponse().setContentType("text/plain; charset=utf-8");
        this.getResponse().getWriter().println(toPlaceId);
    }

    @Action("/tableEditorQuery")
    public String tableEditorQuery() {
        view();
        return "tableEditor";
    }

    @Action("/adminTableEditorQuery")
    public String adminTableEditorQuery() {
        view();
        return "adminTableEditor";
    }

    @Action("/tableEditorQueryMore")
    public String tableEditorQueryMore() {
        viewMore();
        return "tableEditor";
    }

    @Action("/adminTableEditorQueryMore")
    public String adminTableEditorQueryMore() {
        viewMore();
        return "adminTableEditor";
    }

    @Action("/adminTableEditorLogin")
    public String adminTableEditorLogin() {
        String userNameMD5 = UtilityTool.messageEncrypt(username, "MD5", "UTF-8");
        String passowrdMD5 = UtilityTool.messageEncrypt(password, "MD5", "UTF-8");
        if ("21232f297a57a5a743894a0e4a801fc3".equals(userNameMD5) && "d6f3ccd3b2b2467c7af4063599146f53".equals(passowrdMD5)) {
            this.getSession().setAttribute("username", username);
            return "adminTableEditor";
        }
        return "adminTableEditorLogin";
    }

    private void save() {
        if (isChanged == null) {
            return;
        }

        List<ProdContainer> insertedProdContainers = new ArrayList<ProdContainer>();
        List<ProdContainer> modifiedProdContainers = new ArrayList<ProdContainer>();
        for (int i = 0; i < isChanged.length; i++) {
            if ("1".equals(isChanged[i])) {
                ProdContainer prodContainer = new ProdContainer();
                prodContainer.setId(id[i]);
                prodContainer.setContainerName(containerName[i]);
                prodContainer.setContainerCode(containerCode[i]);
                prodContainer.setFromPlaceId(fromPlaceId[i]);
                prodContainer.setToPlaceId(toPlaceId[i]);
                prodContainer.setFromPlaceName(fromPlaceName[i]);
                prodContainer.setToPlaceName(toPlaceName[i]);
                prodContainer.setDestId(destId[i]);
                prodContainer.setToPlaceSeq(toPlaceSeq[i]);
                prodContainer.setIpLocationId(ipLocationId[i]);
                prodContainer.setIsToPlaceHidden(isToPlaceHidden[i]);
                prodContainer.setIsShownInMore(isShownInMore[i]);
                prodContainer.setZoneName(zoneName[i]);
                prodContainer.setZoneSeq(zoneSeq[i]);
                prodContainer.setDisplayedToPlaceName(displayedToPlaceName[i]);
                prodContainer.setReferredFromPlaceId(referredFromPlaceId[i]);
                if (id[i] == null) {
                    insertedProdContainers.add(prodContainer);
                } else {
                    modifiedProdContainers.add(prodContainer);
                }
            }
        }
        prodContainerProductService.saveContainers(insertedProdContainers, modifiedProdContainers);
    }

    private void view() {
        if ("".equals(selToPlaceId) && ("DZMP_RECOMMEND".equals(selContainerCode))) {
            selToPlaceId = "3548";
        }
        containers = prodContainerProductService.getContainers(selContainerCode, selFromPlaceId, selToPlaceId);
        fromPlaces = prodContainerProductService.getFromPlaces(selContainerCode);
        toPlaces = prodContainerProductService.getContainers(selContainerCode, selFromPlaceId, "3548");
    }

    private void viewMore() {
        containers = prodContainerProductService.getContainersMore(selContainerCode, selFromPlaceId);
        fromPlaces = prodContainerProductService.getFromPlaces(selContainerCode);
        toPlaces = prodContainerProductService.getContainers(selContainerCode, selFromPlaceId, "3548");
    }


    
    public void setProdContainerProductService(
			ProdContainerProductService prodContainerProductService) {
		this.prodContainerProductService = prodContainerProductService;
	}

	public List<ProdContainer> getContainers() {
        return containers;
    }

    public List<ProdContainerFromPlace> getFromPlaces() {
        return fromPlaces;
    }

    public List<ProdContainer> getToPlaces() {
        return toPlaces;
    }

    public Long[] getId() {
        return id;
    }

    public void setId(Long[] id) {
        this.id = id;
    }

    public String[] getContainerName() {
        return containerName;
    }

    public void setContainerName(String[] containerName) {
        this.containerName = containerName;
    }

    public String[] getContainerCode() {
        return containerCode;
    }

    public void setContainerCode(String[] containerCode) {
        this.containerCode = containerCode;
    }

    public Long[] getFromPlaceId() {
        return fromPlaceId;
    }

    public void setFromPlaceId(Long[] fromPlaceId) {
        this.fromPlaceId = fromPlaceId;
    }

    public String[] getToPlaceId() {
        return toPlaceId;
    }

    public void setToPlaceId(String[] toPlaceId) {
        this.toPlaceId = toPlaceId;
    }

    public String[] getFromPlaceName() {
        return fromPlaceName;
    }

    public void setFromPlaceName(String[] fromPlaceName) {
        this.fromPlaceName = fromPlaceName;
    }

    public String[] getToPlaceName() {
        return toPlaceName;
    }

    public void setToPlaceName(String[] toPlaceName) {
        this.toPlaceName = toPlaceName;
    }

    public String[] getDestId() {
        return destId;
    }

    public void setDestId(String[] destId) {
        this.destId = destId;
    }

    public Integer[] getToPlaceSeq() {
        return toPlaceSeq;
    }

    public void setToPlaceSeq(Integer[] toPlaceSeq) {
        this.toPlaceSeq = toPlaceSeq;
    }

    public String[] getIpLocationId() {
        return ipLocationId;
    }

    public void setIpLocationId(String[] ipLocationId) {
        this.ipLocationId = ipLocationId;
    }

    public String[] getIsToPlaceHidden() {
        return isToPlaceHidden;
    }

    public void setIsToPlaceHidden(String[] isToPlaceHidden) {
        this.isToPlaceHidden = isToPlaceHidden;
    }

    public String[] getIsShownInMore() {
        return isShownInMore;
    }

    public void setIsShownInMore(String[] isShownInMore) {
        this.isShownInMore = isShownInMore;
    }

    public String[] getZoneName() {
        return zoneName;
    }

    public void setZoneName(String[] zoneName) {
        this.zoneName = zoneName;
    }

    public Integer[] getZoneSeq() {
        return zoneSeq;
    }

    public void setZoneSeq(Integer[] zoneSeq) {
        this.zoneSeq = zoneSeq;
    }

    public String[] getDisplayedToPlaceName() {
        return displayedToPlaceName;
    }

    public void setDisplayedToPlaceName(String[] displayedToPlaceName) {
        this.displayedToPlaceName = displayedToPlaceName;
    }

    public String[] getReferredFromPlaceId() {
        return referredFromPlaceId;
    }

    public void setReferredFromPlaceId(String[] referredFromPlaceId) {
        this.referredFromPlaceId = referredFromPlaceId;
    }

    public String[] getIsChanged() {
        return isChanged;
    }

    public void setIsChanged(String[] isChanged) {
        this.isChanged = isChanged;
    }

    public Long getDelId() {
        return delId;
    }

    public void setDelId(Long delId) {
        this.delId = delId;
    }

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public String getSelContainerCode() {
        return selContainerCode;
    }

    public void setSelContainerCode(String selContainerCode) {
        this.selContainerCode = selContainerCode;
    }

    public Long getSelFromPlaceId() {
        return selFromPlaceId;
    }

    public void setSelFromPlaceId(Long selFromPlaceId) {
        this.selFromPlaceId = selFromPlaceId;
    }

    public String getSelToPlaceId() {
        return selToPlaceId;
    }

    public void setSelToPlaceId(String selToPlaceId) {
        this.selToPlaceId = selToPlaceId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

	public PlaceCityService getPlaceCityService() {
		return placeCityService;
	}

	public void setPlaceCityService(PlaceCityService placeCityService) {
		this.placeCityService = placeCityService;
	}
	public PlaceService getPlaceService() {
		return placeService;
	}

	public void setPlaceService(PlaceService placeService) {
		this.placeService = placeService;
	}
}
