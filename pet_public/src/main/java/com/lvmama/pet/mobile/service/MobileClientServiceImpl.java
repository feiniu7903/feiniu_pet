package com.lvmama.pet.mobile.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.lvmama.comm.pet.po.mobile.MobileActBaidu;
import com.lvmama.comm.pet.po.mobile.MobileActBaiduOrder;
import com.lvmama.comm.pet.po.mobile.MobileActBaiduStock;
import com.lvmama.comm.pet.po.mobile.MobileActivityFifaLuckycode;
import com.lvmama.comm.pet.po.mobile.MobileMarkActivity;
import com.lvmama.comm.pet.po.mobile.MobileMarkActivityLog;
import com.lvmama.comm.pet.po.mobile.MobileOrderRelationSamsung;
import com.lvmama.comm.pet.po.mobile.MobilePersistanceLog;
import com.lvmama.comm.pet.po.mobile.MobilePushDevice;
import com.lvmama.comm.pet.po.mobile.MobilePushJob;
import com.lvmama.comm.pet.po.mobile.MobilePushJogTask;
import com.lvmama.comm.pet.po.mobile.MobilePushLocation;
import com.lvmama.comm.pet.po.mobile.MobilePushSendTask;
import com.lvmama.comm.pet.po.mobile.MobilePushSendTaskLog;
import com.lvmama.comm.pet.po.mobile.MobileVersion;
import com.lvmama.comm.pet.service.mobile.MobileClientService;
import com.lvmama.comm.pet.vo.Page;
import com.lvmama.comm.utils.mobile.ClientUtils;
import com.lvmama.comm.vo.Constant;
import com.lvmama.pet.mobile.dao.MobileActBaiduDAO;
import com.lvmama.pet.mobile.dao.MobileActBaiduOrderDAO;
import com.lvmama.pet.mobile.dao.MobileActBaiduStockDAO;
import com.lvmama.pet.mobile.dao.MobileActivityFifaLuckycodeDAO;
import com.lvmama.pet.mobile.dao.MobileMarkActivityDAO;
import com.lvmama.pet.mobile.dao.MobileMarkActivityLogDAO;
import com.lvmama.pet.mobile.dao.MobileOrderRelationSamsungDAO;
import com.lvmama.pet.mobile.dao.MobilePersistanceLogDAO;
import com.lvmama.pet.mobile.dao.MobilePushDeviceDAO;
import com.lvmama.pet.mobile.dao.MobilePushJobDAO;
import com.lvmama.pet.mobile.dao.MobilePushJogTaskDAO;
import com.lvmama.pet.mobile.dao.MobilePushLocationDAO;
import com.lvmama.pet.mobile.dao.MobilePushSendTaskDAO;
import com.lvmama.pet.mobile.dao.MobilePushSendTaskLogDAO;
import com.lvmama.pet.mobile.dao.MobileVersionDAO;

/**
 * 驴途移动端 from 3.0 - 公共service .
 * 
 * @author qinzubo
 * 
 */
public class MobileClientServiceImpl implements MobileClientService {

	@Autowired
	MobileVersionDAO mobileVersionDAO;

	@Autowired
	private MobileMarkActivityDAO mobileMarkActivityDAO;

	@Autowired
	private MobileMarkActivityLogDAO mobileMarkActivityLogDAO;

	@Autowired
	private MobilePushDeviceDAO mobilePushDeviceDAO;
	
	@Autowired
	private MobilePersistanceLogDAO mobilePersistanceLogDAO;

	@Autowired
	private MobilePushJogTaskDAO mobilePushJogTaskDAO;

	@Autowired
	private MobilePushSendTaskDAO mobilePushSendTaskDAO;
	
	@Autowired
	private MobilePushSendTaskLogDAO mobilePushSendTaskLogDAO;
	
	@Autowired
	private MobilePushLocationDAO mobilePushLocationDAO;
	
	@Autowired
	private MobileOrderRelationSamsungDAO mobileOrderRelationSamsungDAO;
	
	@Autowired
	private MobileActivityFifaLuckycodeDAO mobileActivityFifaLuckycodeDAO;
	
	@Autowired
	private MobileActBaiduDAO mobileActBaiduDAO;
	
	@Autowired
	private MobileActBaiduOrderDAO mobileActBaiduOrderDAO;
	
	@Autowired
	private MobileActBaiduStockDAO mobileActBaiduStockDAO;

	@Autowired
	private MobilePushJobDAO mobilePushJobDAO;
	
	@Override
	public MobileVersion insertMobileVersion(MobileVersion mVersion) {
		return mobileVersionDAO.insert(mVersion);
	}

	
	@Override
	public boolean updateMobileVersion(MobileVersion mVersion) {
		int rows = mobileVersionDAO.updateByPrimaryKey(mVersion);
		if (rows > 0) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public MobileVersion selectMobileVersionById(Long id) {
		return mobileVersionDAO.selectByPrimaryKey(id);
	}

	@Override
	public List<MobileVersion> queryMobileVersionList(Map<String, Object> param) {
		return mobileVersionDAO.queryMobileVersionList(param);
	}

	@Override
	public Long countMobileVersionList(Map<String, Object> param) {
		return mobileVersionDAO.countMobileVersionList(param);
	}

	@Override
	public boolean deleteMobileVersionById(Long id) {
		int rows = mobileVersionDAO.deleteByPrimaryKey(id);
		if (rows > 0) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean updateAuditing(String auditing, Long id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		params.put("isAuditing", auditing);
		int rows = mobileVersionDAO.updateAuditing(params);
		if (rows > 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * @see MobileClientService#insertMobileMarkActivityLog(MobileMarkActivityLog)
	 *      ;
	 */
	@Override
	public void insertMobileMarkActivityLog(MobileMarkActivityLog mmaLog) {
		mobileMarkActivityLogDAO.insert(mmaLog);
	}

	/**
	 * @see MobileClientService#insertMobileMarkActivity(MobileMarkActivity);
	 */
	@Override
	public Long insertMobileMarkActivity(MobileMarkActivity mma) {
		return mobileMarkActivityDAO.insert(mma);
	}

	/**
	 * @see MobileClientService#updateMobileMarkActivity(MobileMarkActivity);
	 */
	@Override
	public boolean updateMobileMarkActivity(MobileMarkActivity mma) {
		int updateCount = mobileMarkActivityDAO.updateByPrimaryKey(mma);
		return updateCount > 0 ? true : false;
	}

	/**
	 * @see MobileClientService#getTotalByMobileMarkActivityId(Long);
	 */
	@Override
	public Long getTotalByMobileMarkActivityId(Long mobileMarkActivityId) {
		return mobileMarkActivityDAO
				.getTotalByMobileMarkActivityId(mobileMarkActivityId);
	}

	/**
	 * @see MobileClientService#getRemainTimesByObjectId(Map);
	 */
	@Override
	public Long getUsedTimesByObjectId(Map<String, Object> params) {
		return mobileMarkActivityLogDAO.getUsedTimesByObjectId(params);
	}

	/**
	 * @see MobileClientService#queryMobileMarkActivityLog(Map);
	 */
	@Override
	public MobileMarkActivityLog queryUniqueMobileMarkActivityLog(
			Map<String, Object> param) {
		return mobileMarkActivityLogDAO.queryUniqueMobileMarkActivityLog(param);
	}

	/**
	 * @see MobileClientService#queryMobileMarkActivity(Map);
	 */
	@Override
	public MobileMarkActivity queryUniqueMobileMarkActivity(
			Map<String, Object> param) {
		return mobileMarkActivityDAO.queryUniqueMobileMarkActivity(param);
	}

	/**
	 * @see MobileClientService#getTodayTotalUsedTimesByMMALogId(Map);
	 */
	@Override
	public Long getTodayTotalUsedTimesByMMAId(Map<String, Object> param) {
		return mobileMarkActivityLogDAO.getTodayTotalUsedTimesByMMAId(param);
	}

	@Override
	public Long getOperateNumByMobileMarkActivityId(Long mobileMarkActivityId) {
		return mobileMarkActivityDAO
				.getOperateNumByMobileMarkActivityId(mobileMarkActivityId);
	}

	@Override
	public Long queryTodayTotalMarkCoupon(Map<String, Object> param) {
		return mobileMarkActivityLogDAO.queryTodayTotalMarkCoupon(param);
	}

	@Override
	public Long insertMobilePushDevice(MobilePushDevice mobilePushDevice) {
		return mobilePushDeviceDAO.insert(mobilePushDevice);
	}

	@Override
	public List<MobilePushDevice> pagedQueryMobilPushDevice(
			Map<String, Object> param) {
		return mobilePushDeviceDAO.pagedQueryMobilPushDevice(param);
	}
	
	@Override
	public Long countPagedQueryMobilPushDevice(Map<String, Object> param){
		return mobilePushDeviceDAO.countQueryMobilPushDevice(param);
	}

	
//	public void queryPagedQueryMobilPushDevice(Map<String, Object> param){
//		Long count = mobilePushDeviceDAO.countQueryMobilPushDevice(param);
//		Long pageSize = (Long)param.get("pageSize");
//		Long currentPage = (Long)param.get("page");
//		Page<MobilePushDevice> page = new Page<>(count, pageSize, currentPage);
//		param.put("start", page.getStartRows());
//		param.put("end", page.getEndRows());
//		List<MobilePushDevice> list = mobilePushDeviceDAO.pagedQueryMobilPushDevice(param);
//		//return list;
//		
//	}
	
	@Override
	public Long selectTotalMobilPushDevice() {
		return mobilePushDeviceDAO.selectTotalMobilPushDevice();
	}

	@Override
	public Long selectByObjectId(Map<String, Object> param) {
		return mobilePushDeviceDAO.selectByObjectId(param);
	}


	@Override
	public MobilePushDevice selectMobileDeviceByObjectId(
			Map<String, Object> param) {
		return mobilePushDeviceDAO.selectMobileDeviceByObjectId(param);
	}


	@Override
	public void insert(MobilePushJogTask record) {
		mobilePushJogTaskDAO.insert(record);
	}

	@Override
	public List<MobilePushSendTask> pagedQueryMobilePushSendTask(
			Map<String, Object> param) {
		return mobilePushSendTaskDAO.pagedQueryMobilePushSendTask(param);
	}

	@Override
	public void insertMobilePushSendTask(MobilePushSendTask record) {
		mobilePushSendTaskDAO.insert(record);
	}

	@Override
	public void syscMobilePushJogTask() {

	}


	@Override
	public void insertMobilePushSendTaskLog(MobilePushSendTaskLog record) {
		mobilePushSendTaskLogDAO.insert(record);
	}

	@Override
	public MobilePushJogTask selectByPrimaryKey(Long mobilePushJogTaskId) {
		return mobilePushJogTaskDAO.selectByPrimaryKey(mobilePushJogTaskId);
	}

	@Override
	public int deleteSendTaskByPrimaryKey(Long mobilePushSendTaskId) {
		return mobilePushSendTaskDAO.deleteByPrimaryKey(mobilePushSendTaskId);
	}

	@Override
	public long selectTotalMobilePushSendTask() {
		return mobilePushSendTaskDAO.selectTotalMobilePushSendTask();
	}

	

	public List<MobilePushJogTask> selectAllJobs() {
		return mobilePushJogTaskDAO.selectAllJobs();
	}
	@Override
	public void insertMobilePersistanceLog(MobilePersistanceLog record) {
		 mobilePersistanceLogDAO.insert(record);
	}
	@Override
	public List<MobilePersistanceLog> selectListbyPersistanceobjectId(Long objectId,String objectType) {
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("objectType", objectType);
		param.put("objectId", objectId);
		return mobilePersistanceLogDAO.selectByParam(param);
	}
	
	@Override
	public List<MobilePersistanceLog> selectListbyPersistanceByDeviceId(
			String deviceId, String channel) {
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("deviceId", deviceId);
		param.put("channel", channel);
		return mobilePersistanceLogDAO.selectByParam(param);
	}


	public List<MobilePushDevice> getPushTargetIds(Map<String,Object> param) {
		return mobilePushDeviceDAO.getPushTargetIds(param);
	}
	public void insertMobilePersistanceLog(String firstChannel,String secondChannel,String deviceId,
			Long appVersion,Long objectId,String objectType,String osVersion,String userAgent){
		MobilePersistanceLog mpl = new MobilePersistanceLog();
		mpl.setFirstChannel(firstChannel);
		mpl.setSecondChannel(secondChannel);
		mpl.setDeviceId(deviceId);
		mpl.setLvVersion(appVersion);
		mpl.setObjectId(objectId);
		mpl.setObjectType(objectType);
		mpl.setUserAgent(userAgent);
		mpl.setOsVersion(osVersion);
		this.insertMobilePersistanceLog(mpl);
	}
	
	public	int deleteByDeviceTokens(Map<String,Object> param) {
		return mobilePushDeviceDAO.deleteByDeviceTokens(param);
	}

	public int updateMobilePushJogTask(MobilePushJogTask record){
		return mobilePushJogTaskDAO.updateByPrimaryKey(record);
	}
	
	@Override
	public void syncJobToSendTaskByTaskId(Long jobTaskId) {
		mobilePushSendTaskDAO.syncJobToSendTaskByTaskId(jobTaskId);
	}

	@Override
	public List<MobilePersistanceLog> selectListByDeviceIdAndObjectType(
			String deviceId, String objectType) {
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("deviceId", deviceId);
		param.put("objectType", objectType);
		return mobilePersistanceLogDAO.selectByParam(param);
	}

	@Override
	public Long insert(MobileOrderRelationSamsung record) {
		return mobileOrderRelationSamsungDAO.insert(record);
	}


	@Override
	public int updateByPrimaryKey(MobileOrderRelationSamsung record) {
		return mobileOrderRelationSamsungDAO.updateByPrimaryKey(record);
	}


	@Override
	public MobileOrderRelationSamsung selectByOrderId(Long orderId) {
		return mobileOrderRelationSamsungDAO.selectByOrderId(orderId);
	}

	@Override
	public MobileActivityFifaLuckycode insertMobileActivityFifaLuckycode(
			MobileActivityFifaLuckycode maf) {
		return mobileActivityFifaLuckycodeDAO.insert(maf);
	}

	@Override
	public void insert(MobilePushLocation record) {
		mobilePushLocationDAO.insert(record);
	}
	
	@Override
	public int updateMobilePushDeviceByPrimaryKey(MobilePushDevice mobilePushDevice) {
		return mobilePushDeviceDAO.updateByPrimaryKey(mobilePushDevice);
	}

	@Override
	public boolean updateMobileActivityFifaLuckycode(MobileActivityFifaLuckycode maf) {
		int rows = mobileActivityFifaLuckycodeDAO.updateByPrimaryKey(maf);
		if(rows > 0) {
			return true;
		}
		return false;
	}


	public MobilePushLocation selectByLocationId(Long locationId) {
		return mobilePushLocationDAO.selectByLocationId(locationId);
	}


	@Override
	public List<MobilePushLocation> selectByDeviceId(Long mobilePushDeviceId) {
		return mobilePushLocationDAO.selectByDeviceId(mobilePushDeviceId);
	}



	@Override
	public List<MobilePushJob> queryValidJobs() {
		// TODO Auto-generated method stub
		return mobilePushJobDAO.queryValidJobs();
	}

	@Override
	public Long addMobilePushJob(MobilePushJob pushJob) {
		// TODO Auto-generated method stub
		pushJob.setIsValid("Y");
		pushJob.setStatus(Constant.CLIENT_PUSH_STATUS.NEW.name());
		return mobilePushJobDAO.insert(pushJob);
	}
	
	
	public List<MobilePushJob> queryMobilePushJobs(Map<String,Object> param){
		Long count = mobilePushJobDAO.countQueryJobsByParams(param);
		Long pageSize = 10L;
		if(param.get("pageSize")!=null){
			pageSize = Long.valueOf(param.get("pageSize").toString());
		}
		Long currentPage = 1L;
		if(param.get("page")!=null){
			currentPage = Long.valueOf(param.get("page").toString());
		}
		
		Page<MobilePushJob> page = new Page<MobilePushJob>(count, pageSize, currentPage);
		param.put("startRows", page.getStartRows());
		param.put("endRows", page.getEndRows());
		param.put("isPaging", true);
		List<MobilePushJob> list = mobilePushJobDAO.queryJobsByParams(param);
		return list;
	}
	
	public String validateMobilePushJob(MobilePushJob pushJob) {
		try {
		if(pushJob.isPush2Ipad()||pushJob.isPush2Iphone()){
			if(pushJob.isPushIosDataToLarget()){
				return "推送内容太长 请精简";
			}
		} else if(pushJob.isPush2Android()){
			if(pushJob.androidPushMsgToLarge()){
				return "推送内容太长 请精简";
			}
		}
		} catch(Exception ex){
			ex.printStackTrace();
		}
		return "";
	}
	public void updateMobilePushJob(MobilePushJob pushJob) {
		mobilePushJobDAO.updateByPrimaryKeySelective(pushJob);
	}
	
	public MobilePushJob selectPushJobByPrimaryKey(Long id){
		return mobilePushJobDAO.selectByPrimaryKey(id);
	}

	public List<MobilePersistanceLog> queryMobilePersistanceLogByParam(Map<String, Object> params) {
		return mobilePersistanceLogDAO.selectByParam(params);
	}
	public MobileActivityFifaLuckycode selectMobileActivityFifaLuckycodeById(
			Long id) {
		return mobileActivityFifaLuckycodeDAO.selectByPrimaryKey(id);
	}


	@Override
	public List<MobileActivityFifaLuckycode> queryMobileActivityFifaLuckycodeList(
			Map<String, Object> param) {
		// TODO Auto-generated method stub
		return mobileActivityFifaLuckycodeDAO.getMobileActivityFifaLuckycodeListByPrarms(param);
	}

	@Override
	public List<MobileActivityFifaLuckycode> queryTheWinningUser4Fifa(
			Map<String, Object> param) {
		return mobileActivityFifaLuckycodeDAO.queryTheWinningUser4Fifa(param);
	}
	
	@Override
	public Long countMobileActivityFifaLuckycodeList(Map<String, Object> param) {
		// TODO Auto-generated method stub
		return mobileActivityFifaLuckycodeDAO.countMobileActivityFifaLuckycode(param);
	}


	@Override
	public boolean deleteMobileActivityFifaLuckycodeById(Long id) {
		// TODO Auto-generated method stub
		return mobileActivityFifaLuckycodeDAO.deleteByPrimaryKey(id)>0;
	}


	@Override
	public boolean sendLuckCode(MobileActivityFifaLuckycode maf) {
		// TODO Auto-generated method stub
		return mobileActivityFifaLuckycodeDAO.updateByPrimaryKeySelective(maf) > 0;
	}

	@Override
	public Long getMobileActSeqNextval(Map<String, Object> param) {
		// TODO Auto-generated method stub
		return mobileActivityFifaLuckycodeDAO.getMobileActSeqNextval(param);
	}


	@Override
	public boolean generatorLuckyCode(Map<String, Object> param) {
		if(null != param.get("start") && null != param.get("end")) {
		   int start = Integer.valueOf(param.get("start").toString()); // 开始号码
		   int end = Integer.valueOf(param.get("end").toString());	// 结束号码
		   // 定义一个数组
		   String[] radomArry = ClientUtils.getLuckyCode(start,end);
		   for(int i = 0; i <= end - start ;i++) {
			   // 插入到数据库中
			   this.insertLuckyCode(radomArry[i]);
		   }
		}
		
		return false;
	}

	/**
	 * 幸运码插入数据库
	 * @param luckyCode
	 */
	private void insertLuckyCode(String luckyCode) {
		try{
			MobileActivityFifaLuckycode record = new MobileActivityFifaLuckycode();
			record.setLuckyCode(luckyCode);
			mobileActivityFifaLuckycodeDAO.insert(record);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}


	@Override
	public Long selectMafLuckyCodeSeqCurrval(Map<String, Object> param) {
		// TODO Auto-generated method stub
		return mobileActivityFifaLuckycodeDAO.selectMafLuckyCodeSeqCurrval(param);
	}
	
	public List<MobilePushLocation> selectbyParmas(Map<String, Object> param) {
		return mobilePushLocationDAO.selectbyParmas(param);
		
	}

	/****************      百度活动开始     ********************/

	@Override
	public Long insertMobileActBaidu(MobileActBaidu mab) {
		Long id = mobileActBaiduDAO.insert(mab);
		return id;
	}


	@Override
	public boolean updateMobileActBaidu(MobileActBaidu mab) {
		mobileActBaiduDAO.updateByPrimaryKeySelective(mab);
		return false;
	}


	@Override
	public List<MobileActBaidu> queryMobileActBaiduList(
			Map<String, Object> param) {
		return mobileActBaiduDAO.queryMobileActBaiduList(param);
	}


	@Override
	public boolean updateAddQtyMobileActBaidu(Map<String, Object> param) {
		// TODO Auto-generated method stub
		return mobileActBaiduDAO.updateAddQuantityByParams(param);
	}


	@Override
	public boolean updateMinusQtyMobileActBaidu(Map<String, Object> param) {
		// TODO Auto-generated method stub
		return mobileActBaiduDAO.updateMinusQuantityByParams(param);
	}


	@Override
	public Long insertMobileActBaiduOrder(MobileActBaiduOrder mab) {
		// TODO Auto-generated method stub
		Long id = mobileActBaiduOrderDAO.insert(mab);
		return id;
	}
	@Override
	public int deleteByUserId(String orderid,Long productid) {
		// TODO Auto-generated method stub
		int id = mobileActBaiduOrderDAO.deleteByUserId(orderid,productid);
		return id;
	}

	@Override
	public List<MobileActBaiduOrder> queryMobileActBaiduOrderList(
			Map<String, Object> param) {
		// TODO Auto-generated method stub
		return mobileActBaiduOrderDAO.queryMobileActBaiduOrderList(param);
	}
	
	@Override
	public Long countMobileActBaiduOrderList(
			Map<String, Object> param) {
		// TODO Auto-generated method stub
		return mobileActBaiduOrderDAO.countMobileActBaiduOrder(param);
	}


	@Override
	public Long insertMobileActBaiduStock(MobileActBaiduStock mab) {
		// TODO Auto-generated method stub
		Long id = mobileActBaiduStockDAO.insert(mab);
		return id;
	}


	@Override
	public boolean updateMobileActBaiduStock(MobileActBaiduStock mab) {
		// TODO Auto-generated method stub
		return mobileActBaiduStockDAO.updateByPrimaryKey(mab);
	}


	@Override
	public boolean updateAddQtyMobileActBaiduStock(Map<String, Object> param) {
		// TODO Auto-generated method stub
		return mobileActBaiduStockDAO.updateAddQuantityByParams(param);
	}


	@Override
	public boolean updateMinusQtyMobileActBaiduStock(Map<String, Object> param) {
		// TODO Auto-generated method stub
		return mobileActBaiduStockDAO.updateMinusQuantityByParams(param);
	}


	@Override
	public List<MobileActBaiduStock> queryMobileActBaiduStockList(
			Map<String, Object> param) {
		return mobileActBaiduStockDAO.queryMobileActBaiduStockList(param);
	}


}
