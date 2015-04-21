package com.lvmama.clutter.service.client.v5_0;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.lvmama.clutter.service.impl.ClientOtherServiceImpl;
import com.lvmama.comm.pet.po.mobile.MobileActivityFifaLuckycode;
import com.lvmama.comm.pet.vo.Page;

public class ClientOtherServiceV50 extends ClientOtherServiceImpl{

	public Map<String,Object> getStation(Map<String,Object> prams){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("stationCode", "SH");
		return map;
	}
	
	@Override
	public Map<String, Object> generatorLuckyCode(Map<String, Object> param) {
		// TODO Auto-generated method stub
		boolean b = mobileClientService.generatorLuckyCode(param);
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("isSuccess", b);
		return  map;
	}
	
	@Override
	public Map<String, Object> getFifaInfo(Map<String, Object> param) {
		param.put("doNothing", "yes");
		Long tableMaxDate = mobileClientService.selectMafLuckyCodeSeqCurrval(param);
		param.put("useridNotNull", "true");
		Long hasSend = mobileClientService.selectMafLuckyCodeSeqCurrval(param);
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("tableMaxDate",tableMaxDate);
		map.put("hasSend",hasSend);
		return map;
	}

	@Override
	public Map<String, Object> getFifaList(Map<String, Object> params) {
		params.put("doNothing", "yes");
		Page p = initPage(params, 20, 1);
		params.put("isPaging", "true"); // 是否使用分页
		params.put("startRows", p.getStartRows());
		params.put("endRows", p.getEndRows());
		List<MobileActivityFifaLuckycode>  list = mobileClientService.queryMobileActivityFifaLuckycodeList(params);
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("datas",list);
		return map;
	}
	
	public Page initPage(Map params, long count, long page) {
		try {
			Object oCount = params.get("count");
			Object oPage = params.get("page");
			if (null != oCount) {
				count = Long.valueOf(oCount + "");
			}
			if (null != oPage) {
				page = Long.valueOf(oPage + "");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		// 初始化分页信息
		return new Page(count < 1 ? 10 : count, page < 1 ? 1 : page);
	}

}
