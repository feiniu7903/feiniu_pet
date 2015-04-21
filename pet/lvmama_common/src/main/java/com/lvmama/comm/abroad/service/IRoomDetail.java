package com.lvmama.comm.abroad.service;

import com.lvmama.comm.abroad.vo.request.RoomDetailsReq;
import com.lvmama.comm.abroad.vo.response.RoomDetailsRes;

public interface IRoomDetail {
      public RoomDetailsRes getRoomDetail(RoomDetailsReq roomReq,String sessionId);
}
