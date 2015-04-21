package com.lvmama.back.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.event.InputEvent;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.api.Bandbox;

import com.lvmama.comm.pet.po.place.Place;
import com.lvmama.comm.pet.service.place.PlaceService;
import com.lvmama.comm.spring.SpringBeanProxy;

public class MacroChooseProdTarget extends GenericForwardComposer {

	Bandbox mc_prodTargetBandbox;
	List<Place> targetList;
	private PlaceService placeService = (PlaceService)SpringBeanProxy.getBean("placeService");
	private Long placeId;
	
	public void changecomPlace(InputEvent event) {
		String name = event.getValue();
		Map<String, Object> param = new HashMap<String, Object>();
		if (name != null && !"".equals(name)) {
			param.put("name", name);
		}
		param.put("maxResults", "10");
		targetList = this.placeService.queryPlaceListByParam(param);
	}

	public void doAfterCompose(Component win) throws Exception {
		Components.wireVariables(win, this);
		Events.addEventListeners(win, this);
		Components.addForwards(win, this);
	}
	
	public List<Place> getTargetList() {
		return targetList;
	}

	public Long getPlaceId() {
		return placeId;
	}

}
