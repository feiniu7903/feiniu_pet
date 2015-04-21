package com.lvmama.comm.pet.service.prod;

import java.util.Date;
import java.util.List;

import com.lvmama.comm.vo.ViewCalendarModel;

public interface TimePriceRemoteService {
	List<ViewCalendarModel> loadTimePriceData(Date date,int month);
}	
