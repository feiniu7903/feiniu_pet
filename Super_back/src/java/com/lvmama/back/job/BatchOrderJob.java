package com.lvmama.back.job;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import com.lvmama.comm.bee.po.distribution.DistributionTuanDestroyLog;
import com.lvmama.comm.bee.po.distribution.DistributorTuanInfo;
import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.po.ord.OrdOrderBatch;
import com.lvmama.comm.bee.po.ord.OrdOrderBatchOrder;
import com.lvmama.comm.bee.po.pass.PassCode;
import com.lvmama.comm.bee.po.pass.PassPortCode;
import com.lvmama.comm.bee.po.prod.ProdProductBranch;
import com.lvmama.comm.bee.service.distribution.DistributionTuanDestroyLogService;
import com.lvmama.comm.bee.service.distribution.DistributionTuanService;
import com.lvmama.comm.bee.service.ord.OrderBatchService;
import com.lvmama.comm.bee.service.ord.OrderService;
import com.lvmama.comm.bee.service.pass.PassCodeService;
import com.lvmama.comm.bee.service.prod.ProdProductBranchService;
import com.lvmama.comm.bee.vo.ord.BuyInfo;
import com.lvmama.comm.bee.vo.ord.Person;
import com.lvmama.comm.jms.MessageFactory;
import com.lvmama.comm.jms.TopicMessageProducer;
import com.lvmama.comm.pet.client.FSClient;
import com.lvmama.comm.pet.po.pay.PayPayment;
import com.lvmama.comm.pet.po.user.UserUser;
import com.lvmama.comm.pet.service.pay.PayPaymentService;
import com.lvmama.comm.pet.service.user.UserUserProxy;
import com.lvmama.comm.pet.vo.ComFile;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.utils.ExcelImport;
import com.lvmama.comm.utils.SynchronizedLock;
import com.lvmama.comm.vo.Constant;

public class BatchOrderJob {

	private static final Log log = LogFactory.getLog(BatchOrderJob.class);
	private OrderBatchService orderBatchService;
	private ProdProductBranchService prodProductBranchService;
	private OrderService orderServiceProxy;
	private PayPaymentService payPaymentService;
	private TopicMessageProducer resourceMessageProducer;
	private UserUserProxy userUserProxy;
	private DistributionTuanDestroyLogService distributionTuanDestroyLogService;
	private FSClient fsClient;
	private PassCodeService passCodeService;
	/** 关于分销的服务*/
	private DistributionTuanService distributionTuanService;
	
	private String booker="银联旅游卡分销专用";//银联旅游卡分销专用
	private final String channel=Constant.CHANNEL.EXPORT_DIEM.name();
	
	public synchronized void run(){
		if(!Constant.getInstance().isJobRunnable()){
			return ;
		}
		List<OrdOrderBatch> lists = orderBatchService.selectNeedCreateOrder();
		long begin = System.currentTimeMillis();
		log.info("batch job beging: "+ begin);
		if(lists!=null){
			for(OrdOrderBatch batch:lists){
				Map<Object, Object> batchstaus = new HashMap<Object, Object>();
				batchstaus.put("batchId", batch.getBatchId());
				batchstaus.put("status", Constant.ORDER_BATCH_STATUS.BATCHINIG.name());
				orderBatchService.updateBatchStatus(batchstaus);
				
				createOrder(batch);
				
				batchstaus.put("status", Constant.ORDER_BATCH_STATUS.FINISHED.name());
				orderBatchService.updateBatchStatus(batchstaus);
				log.info("batch job end time: "+ (System.currentTimeMillis() - begin)/1000);
			}
		}
	}
	
	public void createOrder(OrdOrderBatch batch){
		try{
			UserUser user = userUserProxy.getUsersByIdentity(booker,UserUserProxy.USER_IDENTITY_TYPE.USER_NAME);
			if(user==null){
				log.error("查询用户出错  "+ booker);
				return ;
			}
			Date date = prodProductBranchService.selectNearBranchTimePriceByBranchId(batch.getProductBranchId());
			if(date==null){
				log.error("查询时间出错  "+ batch.getProductBranchId());
				return;
			}
			ProdProductBranch prodBranch = prodProductBranchService.getProductBranchDetailByBranchId(batch.getProductBranchId(), date, true);
			if(prodBranch==null){
				log.error("查询类别出错  "+ batch.getProductBranchId()  + "  date+ "+ DateUtil.formatDate(date, "yyyy-MM-dd hh:mm:ss"));
				return;
			}
			BuyInfo buyInfo = new BuyInfo();
			buyInfo.setChannel(channel);
			buyInfo.setIsAperiodic(prodBranch.getProdProduct().getIsAperiodic());
			buyInfo.setValidBeginTime(prodBranch.getValidBeginTime());
			buyInfo.setValidEndTime(prodBranch.getValidEndTime());
			BuyInfo.Item item = new BuyInfo.Item();
			item.setProductId(prodBranch.getProductId());
			item.setProductBranchId(prodBranch.getProdBranchId());
			item.setIsDefault("true");
			item.setQuantity(1);
			item.setVisitTime(date);
			item.setValidBeginTime(prodBranch.getValidBeginTime());
			item.setValidEndTime(prodBranch.getValidEndTime());
			List<BuyInfo.Item> list = new ArrayList<BuyInfo.Item>();
			list.add(item);
			buyInfo.setItemList(list);
			buyInfo.setMainProductType(prodBranch.getProdProduct().getProductType());
			buyInfo.setMainSubProductType(prodBranch.getProdProduct().getSubProductType());
			if(prodBranch.getProdProduct().isPaymentToLvmama()){
				buyInfo.setPaymentTarget(Constant.PAYMENT_TARGET.TOLVMAMA.name());
			}else if(prodBranch.getProdProduct().isPaymentToSupplier()){
				buyInfo.setPaymentTarget(Constant.PAYMENT_TARGET.TOSUPPLIER.name());
			}
			buyInfo.setTodayOrder(false);
			buyInfo.setSelfPack("false");
			List<Person> personList = new ArrayList<Person>();
			Person person = new Person();
			person.setName(batch.getContacts());
			person.setPersonType(Constant.ORD_PERSON_TYPE.CONTACT.name());
			personList.add(person);
			buyInfo.setPersonList(personList);
			buyInfo.setUserId(user.getUserNo());
			create(buyInfo,batch);
		}catch(Exception e){
			log.error("批量下单出错"+ e);
		}
	}

	private String generateNumber() {
		String no = "";
		int[] defaultNums = new int[10];
		for (int i = 0; i < defaultNums.length; i++) {
			defaultNums[i] = i;
		}
		Random random = new Random();
		int[] nums = new int[8];
		int canBeUsed = 10;
		for (int i = 0; i < nums.length; i++) {
			int index = random.nextInt(canBeUsed);
			nums[i] = defaultNums[index];
			int temp = defaultNums[index];
			defaultNums[index] = defaultNums[canBeUsed - 1];
			defaultNums[canBeUsed - 1] = temp;
			canBeUsed--;
		}
		if (nums.length > 0) {
			for (int i = 0; i < nums.length; i++) {
				no += nums[i];
			}
		}
		return no;
	}
		private int create(BuyInfo buyInfo,OrdOrderBatch batch){
			int resultCount=0;
			for(int i=0;i<batch.getBatchCount();i++){
				try{
					String mobile = 186 +generateNumber();
					buyInfo.getPersonList().get(0).setMobile(mobile);
					OrdOrder order = orderServiceProxy.createOrderWithOperatorId(buyInfo, batch.getOperatorName());
					OrdOrderBatchOrder ob = new OrdOrderBatchOrder();
					ob.setBatchId(batch.getBatchId());
					ob.setOrderId(order.getOrderId());
					orderBatchService.inserBatchOrder(ob);
					if(order.isNormal()&&order.isUnpay()&&order.isPayToLvmama()){
						try{
							Thread.sleep(2*1000);//等待2秒，避免消息同时发出
						}catch(InterruptedException exx){
							exx.getStackTrace();
						}
						DistributorTuanInfo distributor= this.distributionTuanService.getDistributorTuanById(batch.getDistributorTuanInfo().getDistributorTuanInfoId());
						paymentOrder(order,batch.getOperatorName(),distributor.getPaymentCode());
						resultCount++;
					}
				}catch(Exception ex){
					ex.printStackTrace();
				}
			}
			return resultCount;
		}
		
		
		private boolean paymentOrder(OrdOrder order,String operatorName,String paymentCode) {
			PayPayment payPayment = new PayPayment();
			payPayment.setObjectId(order.getOrderId());
			payPayment.setSerial(payPayment.geneSerialNo());
			String key = "PAYMENT_DISTRIBUTION_ACTION" + payPayment.getSerial();
			if (SynchronizedLock.isOnDoingMemCached(key)) {
				return false;
			}
			try {
				Date clllbackTime = new Date();
				payPayment.setCallbackInfo("分销支付");
				payPayment.setGatewayTradeNo(DateUtil.formatDate(clllbackTime, "yyyyMMddHHmmssSSS")+order.getOrderId());
				payPayment.setPaymentTradeNo(payPayment.getGatewayTradeNo());
				payPayment.setCallbackTime(clllbackTime);
				payPayment.setCreateTime(clllbackTime);
				payPayment.setPaymentGateway(paymentCode);
				payPayment.setAmount(order.getOughtPay());
				payPayment.setOperator(operatorName);
				payPayment.setBizType(Constant.PAYMENT_BIZ_TYPE.SUPER_ORDER.getCode());
				payPayment.setObjectType(Constant.OBJECT_TYPE.ORD_ORDER.name());
				payPayment.setPaymentType(Constant.PAYMENT_OPERATE_TYPE.PAY.name());
				payPayment.setStatus(Constant.PAYMENT_SERIAL_STATUS.SUCCESS.name());
				Long paymentId = payPaymentService.savePayment(payPayment);
				resourceMessageProducer.sendMsg(MessageFactory.newPaymentSuccessCallMessage(paymentId));
			} finally {
				SynchronizedLock.releaseMemCached(key);
			}
			return true;
		}

		
		public synchronized void runCancel(){
			log.info("batch job runCancel: begin");
			if(!Constant.getInstance().isJobRunnable()){
				return ;
			}
			List<OrdOrderBatch> lists = orderBatchService.selectNeedCreateOrder();
			if(lists.size()>0){
				log.info("batch job runCancel: 有创建订单任务 取消任务延迟  创建任务数 ："+ lists.size());
				return ;
			}
			Map<String,Object> params = new HashMap<String,Object>();
			params.put("status", Constant.DISTRIBUTION_TUAN_DESTROY_LOG_STATUS.WAITING.getCode());
			params.put("distType", Constant.DISTRIBUTION_TUAN_DESTROY_LOG_DIST_TYPE.DIST_BATCH.getCode());
			params.put("start", 1L);
			params.put("end", 1000L);
			List<DistributionTuanDestroyLog> couponList =distributionTuanDestroyLogService.queryList(params);
			int totalNum=0;
			for(DistributionTuanDestroyLog batch:couponList){
				try {
					batch.setStartTime(new Date());
					ComFile resultFile = fsClient.downloadFile(batch.getPristineId());
					if(resultFile!=null&&totalNum<2000){
						List<String> list = ExcelImport.excImport(new FileInputStream(resultFile.getFile()));
						List<DistributionTuanDestroyLog> errorList = cancelOrderByCode(list);
						int successNum = list.size()-errorList.size();
						batch.setSuccessNum(Long.valueOf(""+successNum));
						batch.setEndTime(new Date());
						batch.setStatus(Constant.DISTRIBUTION_TUAN_DESTROY_LOG_STATUS.RUNNED.getCode());
						distributionTuanDestroyLogService.update(batch);
						if(errorList.size()>0){
							readErrorLog(batch.getLogId(),"error_"+batch.getFileName(),errorList);
						}
						totalNum = totalNum+list.size();
					}
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					log.info("batch job FileNotFoundException  " + e);
					e.printStackTrace();
				} catch (Exception e) {
					log.info("batch job runCancel: Exception  " + e);
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		}
		
		public List<DistributionTuanDestroyLog> cancelOrderByCode(List<String> list){
			List<DistributionTuanDestroyLog> errorList = new ArrayList<DistributionTuanDestroyLog>();
			for(String code : list){
				doOrderCancel(code,errorList);
			}
			return errorList;
		}
		
		private boolean doOrderCancel(String  addCode,List<DistributionTuanDestroyLog> errorList){
			try {
				
				List<PassCode> passCodes = passCodeService.getPassCodeByAddCode(addCode);
				if(passCodes==null || passCodes.size()<1){
					DistributionTuanDestroyLog error=new DistributionTuanDestroyLog();
					error.setCouponCode(addCode);
					error.setErrorReson("辅助码不存在");
					errorList.add(error);
					return false;
				}
				PassCode passCode = passCodes.get(0);
				OrdOrder order=orderServiceProxy.queryOrdOrderByOrderId(passCode.getOrderId());
				//该位置做废单判断，如果已经过了最晚时间不做处理，只许超级废单直接处理
				if(order==null||!order.isCancelAble() || order.isCanceled()){
					DistributionTuanDestroyLog error=new DistributionTuanDestroyLog();
					error.setCouponCode(addCode);
					error.setErrorReson("当前订单不可以取消");
					errorList.add(error);
					return false;
				}
				
				List<PassPortCode> passPortCodeList = passCodeService.searchPassPortByOrderId(passCode.getOrderId());
				PassPortCode passPortCode = null;
				if(!passPortCodeList.isEmpty() && passPortCodeList.size() >0){
					passPortCode = passPortCodeList.get(0);
				}
				//该位置做废单判断，如果已经过了最晚时间不做处理，只许超级废单直接处理
				if( passPortCode == null || passPortCode.getStatus().equals(Constant.PASSCODE_USE_STATUS.USED)){
					DistributionTuanDestroyLog error=new DistributionTuanDestroyLog();
					error.setCouponCode(addCode);
					error.setErrorReson("辅助码不允许废单");
					errorList.add(error);
					return false;
				}
				boolean flag = orderServiceProxy.cancelOrder(passCode.getOrderId(), "系统定时根据上传辅助码取消订单", "System");
				if(!flag){
					DistributionTuanDestroyLog error=new DistributionTuanDestroyLog();
					error.setCouponCode(addCode);
					error.setErrorReson("内部废单失败");
					errorList.add(error);
				}
				log.info("flag:"+flag+",batchCancelOrder:"+addCode);
				return flag;
				
			} catch (Exception e) {
				DistributionTuanDestroyLog error=new DistributionTuanDestroyLog();
				error.setCouponCode(addCode);
				error.setErrorReson("废单异常");
				errorList.add(error);
				log.info("系统定时废单出错  辅助码 :" + addCode + "原因:"+ e);
				return false;
			}
		}
		
		public void readErrorLog(Long logId,String fileName,List<DistributionTuanDestroyLog> errors) throws Exception{
	        //创建一个EXCEL  
	        Workbook wb = new HSSFWorkbook();  
	        //创建一个SHEET  
	        Sheet sheet1 = wb.createSheet("批量废券码失败日志");  
	        String[] title = {"券码","失败原因"};  
	        int i=0;  
	        //创建一行  
	        Row row = sheet1.createRow((short)0);  
	        //填充标题  
	        for (String  s:title){  
	            Cell cell = row.createCell(i);  
	            cell.setCellValue(s);  
	            i++;  
	        }  
	        //下面是填充数据  
	        int r=1;
	        for (DistributionTuanDestroyLog log :errors) {
	        	 int j=0;
	        	 Row  rowName= sheet1.createRow((short)r); 
	        	 String [] content={log.getCouponCode(),log.getErrorReson()};
	        	 for (String s:content){  
	                 Cell cell = rowName.createCell(j);  
	                 cell.setCellValue(s);  
	                 j++;
	             }  
	        	 r++;
			}
	        
			File file = File.createTempFile("distribution", ".xls");
			FileOutputStream fos = new FileOutputStream(file);
	        wb.write(fos);  
	        try {
				InputStream inputStream=new FileInputStream(file);
				 Long fid = fsClient.uploadFile(fileName, inputStream, "DISTRIBUTION_XLSX");
				 distributionTuanDestroyLogService.updateErrorId(logId,fid);
			} catch (IOException ex) {
				ex.printStackTrace();
			}
			
			fos.close();  
		}
		public void setOrderBatchService(OrderBatchService orderBatchService) {
			this.orderBatchService = orderBatchService;
		}

		public void setProdProductBranchService(
				ProdProductBranchService prodProductBranchService) {
			this.prodProductBranchService = prodProductBranchService;
		}

		public void setOrderServiceProxy(OrderService orderServiceProxy) {
			this.orderServiceProxy = orderServiceProxy;
		}

		public void setPayPaymentService(PayPaymentService payPaymentService) {
			this.payPaymentService = payPaymentService;
		}

		public void setResourceMessageProducer(
				TopicMessageProducer resourceMessageProducer) {
			this.resourceMessageProducer = resourceMessageProducer;
		}

		public void setUserUserProxy(UserUserProxy userUserProxy) {
			this.userUserProxy = userUserProxy;
		}

		public void setDistributionTuanService(
				DistributionTuanService distributionTuanService) {
			this.distributionTuanService = distributionTuanService;
		}

		public DistributionTuanDestroyLogService getDistributionTuanDestroyLogService() {
			return distributionTuanDestroyLogService;
		}

		public void setDistributionTuanDestroyLogService(
				DistributionTuanDestroyLogService distributionTuanDestroyLogService) {
			this.distributionTuanDestroyLogService = distributionTuanDestroyLogService;
		}

		public void setFsClient(FSClient fsClient) {
			this.fsClient = fsClient;
		}

		public void setPassCodeService(PassCodeService passCodeService) {
			this.passCodeService = passCodeService;
		}
		
		
}
