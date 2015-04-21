package com.lvmama.pet.sweb.container;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.comm.BackBaseAction;
import com.lvmama.comm.pet.po.prod.ProdContainerFromPlace;
import com.lvmama.comm.pet.service.prod.ProdContainerProductService;

@Results({
    @Result(name = "tableEditorFromPlace", location = "/WEB-INF/pages/back/container/tableEditorFromPlace.ftl", type = "freemarker"),
    @Result(name = "adminTableEditorFromPlace", location = "/WEB-INF/pages/back/container/adminTableEditorFromPlace.ftl", type = "freemarker"),
    @Result(name = "adminTableEditorLogin", location = "/WEB-INF/pages/back/container/adminTableEditorLogin.ftl", type = "freemarker")
})
public class TableEditorFromPlaceAction extends BackBaseAction {
    private static final long serialVersionUID = 3876021703505768656L;

    private ProdContainerProductService prodContainerProductService;

    private List<ProdContainerFromPlace> fromPlaces;

    private Long[] id;
    private String[] fromPlaceCode; // 出发地code，用来定位ip区域范围
    private Integer[] fromPlaceSeq;// 出发地排序
    private Long[] blockId;// 出发地对应pc后台推荐块id
    private Long[] searchBlockId;// 出发地对应pc后台推荐里的搜索块id
    private String[] isFromPlaceHidden;// 是否隐藏出发地
    private String[] containerCode;// 容器代码
    private String[] fromPlaceName;// 出发地名称
    private Long[] fromPlaceId;// 出发地id
    private String[] isChanged;

    private Long delId;

    @Action("/tableEditorFromPlace")
    public String tableEditorFromPlace() {
        save();
        fromPlaces = prodContainerProductService.getFromPlaces();
        return "tableEditorFromPlace";
    }

    @Action("/adminTableEditorFromPlace")
    public String adminTableEditorFromPlace() {
        if (this.getSession().getAttribute("username") == null) {
            return "adminTableEditorLogin";
        }
        save();
        fromPlaces = prodContainerProductService.getFromPlaces();
        return "adminTableEditorFromPlace";
    }

    @Action("/adminTableEditorFromPlace/del")
    public String adminTableEditorFromPlaceDel() {
    	prodContainerProductService.deleteFromPlace(delId);
        fromPlaces = prodContainerProductService.getFromPlaces();
        return "adminTableEditorFromPlace";
    }

    private void save() {
        if (isChanged == null) {
            return;
        }

        List<ProdContainerFromPlace> insertedFromPlaces = new ArrayList<ProdContainerFromPlace>();
        List<ProdContainerFromPlace> modifiedFromPlaces = new ArrayList<ProdContainerFromPlace>();
        for (int i = 0; i < isChanged.length; i++) {
            if ("1".equals(isChanged[i])) {
                ProdContainerFromPlace fromPlace = new ProdContainerFromPlace();
                fromPlace.setId(id[i]);
                fromPlace.setFromPlaceCode(fromPlaceCode[i]);
                fromPlace.setFromPlaceSeq(fromPlaceSeq[i]);
                fromPlace.setBlockId(blockId[i]);
                fromPlace.setSearchBlockId(searchBlockId[i]);
                fromPlace.setIsFromPlaceHidden(isFromPlaceHidden[i]);
                fromPlace.setContainerCode(containerCode[i]);
                fromPlace.setFromPlaceName(fromPlaceName[i]);
                fromPlace.setFromPlaceId(fromPlaceId[i]);

                if (id[i] == null) {
                    insertedFromPlaces.add(fromPlace);
                } else {
                    modifiedFromPlaces.add(fromPlace);
                }
            }
        }
        prodContainerProductService.saveFromPlaces(insertedFromPlaces, modifiedFromPlaces);
    }


    public List<ProdContainerFromPlace> getFromPlaces() {
        return fromPlaces;
    }

    public Long[] getId() {
        return id;
    }

    public void setId(Long[] id) {
        this.id = id;
    }

    public String[] getFromPlaceCode() {
        return fromPlaceCode;
    }

    public void setFromPlaceCode(String[] fromPlaceCode) {
        this.fromPlaceCode = fromPlaceCode;
    }

    public Integer[] getFromPlaceSeq() {
        return fromPlaceSeq;
    }

    public void setFromPlaceSeq(Integer[] fromPlaceSeq) {
        this.fromPlaceSeq = fromPlaceSeq;
    }

    public Long[] getBlockId() {
        return blockId;
    }

    public void setBlockId(Long[] blockId) {
        this.blockId = blockId;
    }

    public Long[] getSearchBlockId() {
        return searchBlockId;
    }

    public void setSearchBlockId(Long[] searchBlockId) {
        this.searchBlockId = searchBlockId;
    }

    public String[] getIsFromPlaceHidden() {
        return isFromPlaceHidden;
    }

    public void setIsFromPlaceHidden(String[] isFromPlaceHidden) {
        this.isFromPlaceHidden = isFromPlaceHidden;
    }

    public String[] getContainerCode() {
        return containerCode;
    }

    public void setContainerCode(String[] containerCode) {
        this.containerCode = containerCode;
    }

    public String[] getFromPlaceName() {
        return fromPlaceName;
    }

    public void setFromPlaceName(String[] fromPlaceName) {
        this.fromPlaceName = fromPlaceName;
    }

    public Long[] getFromPlaceId() {
        return fromPlaceId;
    }

    public void setFromPlaceId(Long[] fromPlaceId) {
        this.fromPlaceId = fromPlaceId;
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

	public void setProdContainerProductService(
			ProdContainerProductService prodContainerProductService) {
		this.prodContainerProductService = prodContainerProductService;
	}
    
    
}
