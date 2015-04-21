package com.lvmama.service;

import java.util.Date;

import com.lvmama.comm.bee.service.TrainDataSyncService;

public interface LocalTrainDataSyncService extends TrainDataSyncService{

	void syncLineInfo(String key,Date date);
}
