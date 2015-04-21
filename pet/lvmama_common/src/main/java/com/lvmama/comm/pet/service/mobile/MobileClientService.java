package com.lvmama.comm.pet.service.mobile;

import java.util.List;
import java.util.Map;

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

/**
 * 驴途移动端 from 3.0 - 公共service .
 * @author qinzubo
 *
 */
public interface MobileClientService {
	/**
	 * 新增版本 
	 * @param mVersion 
	 * @return  
	 */
	MobileVersion insertMobileVersion(MobileVersion mVersion);
	
	/**
	 * 更新版本.
	 * @param mVersion  要更新的对象 
	 * @return   更新后的对象
	 */
	boolean updateMobileVersion(MobileVersion mVersion);
	
	/**
	 * 根据主键查找对象.
	 * @param id  主键
	 * @return  对象
	 */
	MobileVersion selectMobileVersionById(Long id);
	
	/**
     * 查询列表 .
     * 如果参数 isPaging 不为null ，表示分页查询. 
     * @param param 
     * @return
     */
	List<MobileVersion> queryMobileVersionList(Map<String,Object> param);
	
	/**
	 * 符合条件的数据量 
	 * @param param
	 * @return long 
	 */
	Long countMobileVersionList(Map<String,Object> param);
	
	/**
	 * 删除
	 * @param id
	 * @return 删除的条数
	 */
	boolean deleteMobileVersionById(Long id);
	
	/**
	 * 更新审核状态 ，
	 * @param param  isValid 和 id 两个参数 
	 * @return true or false 
	 */
	boolean updateAuditing(String auditing,Long id) ;
	
	/**
	 * 增加活动信息日志
	 * @param mmaLog
	 */
	void insertMobileMarkActivityLog(MobileMarkActivityLog mmaLog);
	
	/**
	 * 增加活动信息
	 * @param mma
	 * @return
	 */
	Long insertMobileMarkActivity(MobileMarkActivity mma);
	
	/**
	 * 修改活动信息
	 * @param mma
	 * @return
	 */
	boolean updateMobileMarkActivity(MobileMarkActivity mma);
	
	/**
	 * 根据活动ID获取活动优惠券总数
	 * @param mobileMarkActivityId
	 * @return
	 */
	Long getOperateNumByMobileMarkActivityId(Long mobileMarkActivityId);
	/**
	 * 根据活动ID获取活动优惠券总数
	 * @param mobileMarkActivityId
	 * @return
	 */
	Long getTotalByMobileMarkActivityId(Long mobileMarkActivityId);
	
	/**
	 * 查询该设备剩余抽奖次数
	 * @param params
	 * @return
	 */
	Long getUsedTimesByObjectId(Map<String,Object> params);
	
	/**
	 * 获取活动日志信息
	 * @param param
	 * @return
	 */
	MobileMarkActivityLog queryUniqueMobileMarkActivityLog(Map<String,Object> param);
	
	/**
	 * 获取活动信息
	 * @param param
	 * @return
	 */
	MobileMarkActivity queryUniqueMobileMarkActivity(Map<String, Object> param);
	
	/**
	 * 获取当天总参加活动次数
	 * @param param
	 * @return
	 */
	Long getTodayTotalUsedTimesByMMAId(Map<String, Object> param);
	
	/**
	 * 获取当天总中奖数
	 * @param param
	 * @return
	 */
	Long queryTodayTotalMarkCoupon(Map<String, Object> param);
	
	/**
	 * 增加消息推送设备
	 * @param mma
	 * @return
	 */
	Long insertMobilePushDevice(MobilePushDevice mobilePushDevice);
	
	/**
	 * 分页查询消息推送设备
	 * @param param
	 * @return
	 */
	List<MobilePushDevice> pagedQueryMobilPushDevice(Map<String, Object> param);
	/**
	 * 统计需要分页的总数
	 * @param param
	 * @return 
	 */
	Long countPagedQueryMobilPushDevice(Map<String, Object> param);
	 
	
	/**
	 * 查询消息推送设备总数
	 * @param param
	 * @return
	 */
	Long selectTotalMobilPushDevice();
	
	/**
	 * 查询是否存在该推送设备
	 * @param param
	 * @return
	 */
	Long selectByObjectId(Map<String, Object> param);
	
	/**
	 * 查询是否存在该推送设备
	 * @param param
	 * @return
	 */
	MobilePushDevice selectMobileDeviceByObjectId(Map<String, Object> param);
	
	/**
	 * 增加推送信息
	 * @param record
	 */
	void insert(MobilePushJogTask record);
	
	/**
	 * 同步需要推送的任务
	 * @return
	 */
	void syscMobilePushJogTask();
	
	/**
	 * 分页查询需要推送的设备及信息
	 * @param param
	 * @return
	 */
	List<MobilePushSendTask> pagedQueryMobilePushSendTask(
			Map<String, Object> param);
	
	void insertMobilePushSendTask(MobilePushSendTask record);

	
	void insertMobilePushSendTaskLog(MobilePushSendTaskLog record);
	
	MobilePushJogTask selectByPrimaryKey(Long mobilePushJogTaskId);
	
	int deleteSendTaskByPrimaryKey(Long mobilePushSendTaskId);
	
	/**
	 * 查询需要推送的消息总数
	 * @return
	 */
	long selectTotalMobilePushSendTask();
	

	

	void insertMobilePersistanceLog(MobilePersistanceLog record);
	
	/**
	 * 根据objectId 查询持久化的客户端版本对应关系
	 * @param objectId
	 * @return
	 */
	List<MobilePersistanceLog> selectListbyPersistanceByDeviceId(String deviceId,String channel);
	/**
	 * 根据objectId 查询持久化的客户端版本对应关系
	 * @param objectId
	 * @return
	 */
	List<MobilePersistanceLog> selectListbyPersistanceobjectId(Long objectId,String objectType);
	/**
	 * 客户度持久化数据日志
	 * @param firstChannel
	 * @param secondChannel
	 * @param deviceId
	 * @param appVersion
	 * @param objectId
	 * @param objectType
	 * @param osVersion
	 */
	void insertMobilePersistanceLog(String firstChannel,String secondChannel,String deviceId,
			Long appVersion,Long objectId,String objectType,String osVersion,String userAgent);
	List<MobilePushJogTask> selectAllJobs();
	void syncJobToSendTaskByTaskId(Long taskId);
	List<MobilePushDevice> getPushTargetIds(Map<String,Object> param);
	/**
	 * update 任务task
	 * @param record
	 * @return
	 */
	public int updateMobilePushJogTask(MobilePushJogTask record);
	/**
	 * 删除失效的ios推送devicetoken
	 * @param param
	 * @return
	 */
	int deleteByDeviceTokens(Map<String,Object> param);
	
	/**
	 * 根据设备号和类型查找 
	 * @param deviceId 设备号
	 * @param objectType 类别
	 * @return
	 */
	List<MobilePersistanceLog> selectListByDeviceIdAndObjectType(String deviceId,String objectType);

	/**
	 * 新增手机区域信息
	 * @param record
	 */
	void insert(MobilePushLocation record);
	
	/**
	 * 根据locationId查询手机区域信息
	 * @param locationId
	 * @return
	 */
	MobilePushLocation selectByLocationId(Long locationId);
	
	/**
	 * 根据设备号查询手机区域信息
	 * @param mobilePushDeviceId
	 * @return
	 */
	List<MobilePushLocation> selectByDeviceId(Long mobilePushDeviceId);
	
	/**
	 * 新增三星wallet ticket和orderId关联信息
	 * @param record
	 * @return
	 */
	Long insert(MobileOrderRelationSamsung record);
	
	/**
	 * 更新三星wallet ticket和orderId关联信息
	 * @param record
	 * @return
	 */
	int updateByPrimaryKey(MobileOrderRelationSamsung record);
	
	/**
	 * 根据orderId查询关联信息
	 * @param orderId
	 * @return
	 */
	MobileOrderRelationSamsung selectByOrderId(Long orderId);
	/**
	 * 查询有效的任务
	 * @return
	 */
	List<MobilePushJob> queryValidJobs();
	/**
	 * 添加推送任务
	 * @return
	 */
	Long addMobilePushJob(MobilePushJob pushJob);
	/**
	 * 验证推送任务数据
	 * @param pushJob
	 * @return
	 */
	String validateMobilePushJob(MobilePushJob pushJob);
	/**
	 * 修改推送任务 
	 * @param pushJob
	 */
	void updateMobilePushJob(MobilePushJob pushJob);
	/**
	 * 分页查询推送JOB
	 * @param param
	 * @return
	 */
	List<MobilePushJob> queryMobilePushJobs(Map<String,Object> param);

	/**
	 * 修改设备
	 * @param mobilePushDevice
	 * @return
	 */
	int updateMobilePushDeviceByPrimaryKey(MobilePushDevice mobilePushDevice);

	/**
	 * 插入幸运码版本 
	 * @param mVersion 
	 * @return  
	 */
	MobileActivityFifaLuckycode insertMobileActivityFifaLuckycode(MobileActivityFifaLuckycode maf);
	
	/**
	 * 更新幸运码.
	 * @return   更新后的对象
	 */
	boolean updateMobileActivityFifaLuckycode(MobileActivityFifaLuckycode maf);
	
	/**
	 * 根据主键查找对象.
	 * @param id  主键
	 * @return  对象
	 */
	MobileActivityFifaLuckycode selectMobileActivityFifaLuckycodeById(Long id);
	
	/**
     * 查询列表 .
     * 如果参数 isPaging 不为null ，表示分页查询. 
     * @param param 
     * @return
     */
	List<MobileActivityFifaLuckycode> queryMobileActivityFifaLuckycodeList(Map<String,Object> param);
	
	/**
	 * 符合条件的数据量 
	 * @param param
	 * @return long 
	 */
	Long countMobileActivityFifaLuckycodeList(Map<String,Object> param);
	
	/**
	 * 删除
	 * @param id
	 * @return 删除的条数
	 */
	boolean deleteMobileActivityFifaLuckycodeById(Long id);
	
	/**
	 * 更新审核状态 ，
	 */
	public boolean sendLuckCode(MobileActivityFifaLuckycode maf);
	
	/** 
	 * 获取世界杯活动下一个sequence 
	 */
	public Long getMobileActSeqNextval(Map<String,Object> param);
	
	
	/**
	 * 预生成验证码
	 * @param param
	 * @return
	 */
	public boolean generatorLuckyCode(Map<String,Object> param);
	
	/** 
	 * 当前幸运码序列号 
	 */
	public Long selectMafLuckyCodeSeqCurrval(Map<String,Object> param);

	/**
	 * 幸运号码
	 * @param param
	 * @return
	 */
	public List<MobileActivityFifaLuckycode> queryTheWinningUser4Fifa(Map<String, Object> param);

	/**
	 * 主键查询
	 * @param id
	 * @return
	 */
	MobilePushJob selectPushJobByPrimaryKey(Long id);

	/**
	 * 查询持久化日志信息
	 * @param param
	 * @return
	 */
	List<MobilePersistanceLog> queryMobilePersistanceLogByParam(Map<String, Object> param);
	/**
	 * 查询设备对应的geo信息
	 * @param param
	 * @return
	 */
	List<MobilePushLocation> selectbyParmas(Map<String, Object> param);
	
/****************************** 百度活动 开始 **********************************/	
	/**
	 * 客户端百度活动 - 插入可以参加活动的产品id
	 * @param mVersion 
	 * @return   id
	 */
	Long insertMobileActBaidu(MobileActBaidu mab);
	
	/**
	 * 客户端百度活动 - 更新库存.
	 * @return   更新后的对象
	 */
	boolean updateMobileActBaidu(MobileActBaidu mab);
	
	
	
	/**
	 * 用户订单记录表 - 增加
	 * @return   更新后的对象
	 */
	boolean updateAddQtyMobileActBaidu(Map<String, Object> param);
	
	
	/**
	 * 用户订单记录表 - 减少.
	 * @return   更新后的对象
	 */
	boolean updateMinusQtyMobileActBaidu(Map<String, Object> param);
	
	/**
	 * 用户订单记录表 - 查询列表.
	 * @param param  主键
	 * @return  对象
	 */
	public List<MobileActBaidu> queryMobileActBaiduList(Map<String, Object> param);
	
	/**
	 * 用户订单记录表 - 插入可以参加活动的产品id
	 * @param mVersion 
	 * @return   id
	 */
	Long insertMobileActBaiduOrder(MobileActBaiduOrder mab);
	
	/**
	 * 更加订单删除记录
	 * @param orderid
	 * @return
	 */
	int deleteByUserId(String userId,Long productId);
	
	/**
	 * 用户订单记录表 - 查询列表.
	 * @param param  主键
	 * @return  对象
	 */
	public List<MobileActBaiduOrder> queryMobileActBaiduOrderList(Map<String, Object> param);
	
	/**
	 * 总数
	 * @param param
	 * @return
	 */
	Long countMobileActBaiduOrderList(Map<String, Object> param);
	
	
	
	/**
	 * 可销售总表 - 插入可以参加活动的产品id
	 * @param mVersion 
	 * @return   id
	 */
	Long insertMobileActBaiduStock(MobileActBaiduStock mab);
	/**
	 * 可销售总表- 更新库存.
	 * @return   更新后的对象
	 */
	boolean updateMobileActBaiduStock(MobileActBaiduStock mab);
	
	/**
	 * 可销售总表 - 增加
	 * @return   更新后的对象
	 */
	boolean updateAddQtyMobileActBaiduStock(Map<String, Object> param);
	
	
	/**
	 * 可销售总表 - 更新库存.
	 * @return   更新后的对象
	 */
	boolean updateMinusQtyMobileActBaiduStock(Map<String, Object> param);
	
	/**
	 * 总表 - 查询列表.
	 * @param param  主键
	 * @return  对象
	 */
	public List<MobileActBaiduStock> queryMobileActBaiduStockList(Map<String, Object> param);
	
	/****************************** 百度活动 结束 **********************************/	
}
