package com.lvmama.comm.abroad.service;

import com.lvmama.comm.abroad.vo.request.CityReq;
import com.lvmama.comm.abroad.vo.response.CityRes;

public interface ICity {
    public CityRes searchCityByName(CityReq cityreq,String sessionId);
}
