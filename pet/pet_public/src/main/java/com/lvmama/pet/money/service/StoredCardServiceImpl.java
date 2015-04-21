package com.lvmama.pet.money.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.lvmama.comm.pet.po.money.StoredCard;
import com.lvmama.comm.pet.po.money.StoredCardBatch;
import com.lvmama.comm.pet.po.money.StoredCardStock;
import com.lvmama.comm.pet.po.money.StoredCardUsage;
import com.lvmama.comm.pet.po.pay.BankReturnInfo;
import com.lvmama.comm.pet.po.pay.PayPayment;
import com.lvmama.comm.pet.po.pub.ComLog;
import com.lvmama.comm.pet.service.money.StoredCardService;
import com.lvmama.comm.pet.service.pay.PayPaymentService;
import com.lvmama.comm.pet.vo.Page;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.utils.MD5;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.utils.UtilityTool;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.Constant.ComeFrom;
import com.lvmama.comm.vo.Constant.STORED_CARD_ENUM;
import com.lvmama.pet.money.dao.CashAccountDAO;
import com.lvmama.pet.money.dao.StoredCardBatchDAO;
import com.lvmama.pet.money.dao.StoredCardDAO;
import com.lvmama.pet.money.dao.StoredCardStockDAO;
import com.lvmama.pet.money.dao.StoredCardUsageDAO;
import com.lvmama.pet.pub.dao.ComLogDAO;

/**
 * 
 * @author Libo Wang
 *
 */
public class StoredCardServiceImpl implements StoredCardService{
	private static final Logger LOG = Logger.getLogger(StoredCardServiceImpl.class);
	
	@Autowired
	private StoredCardDAO storedCardDAO;
	@Autowired
	private StoredCardBatchDAO storedCardBatchDAO;
	@Autowired
	private StoredCardStockDAO storedCardStockDAO; 
	@Autowired
	private StoredCardUsageDAO storedCardUsageDAO;
	@Autowired
	protected ComLogDAO comLogDAO;	
	@Autowired
	protected CashAccountDAO cashAccountDAO;	
	@Autowired
	private PayPaymentService payPaymentService;
	
	/**-------------------卡信息--------------------------------------------------------*/
	/**
	 * 保存卡信息记录.
	 * @param storedCard
	 * @return
	 */
	public Long saveStoredCard(StoredCard storedCard){
		return storedCardDAO.insert(storedCard);
	}
	
	/**
	 * 更新卡信息记录.
	 * @param storedCard
	 * @return
	 */
	public void updateStoredCard(StoredCard storedCard){
		 storedCardDAO.update(storedCard);
	}
	
	
	public void updateStoredCard(StoredCard storedCard, String operatorName,
			String type, String cancelMemo) {
		storedCardDAO.update(storedCard);
		if("OTHER".equals(type)){
			 saveComLog("STORED_CARD", storedCard.getStoredCardId(), operatorName,
						Constant.STORED_CARD_ENUM.UPDATE_STORED_CARD.name(), "储值卡作废",cancelMemo);
		}if("BATCH".equals(type)){
			 saveComLog("STORED_CARD", storedCard.getStoredCardId(), operatorName,
						Constant.STORED_CARD_ENUM.UPDATE_STORED_CARD.name(), "储值卡作废","批次作废导致储值卡作废,"+cancelMemo);
			 }
	}
	
	
	public void updateStoredCard(StoredCard storedCard,String operatorName,String type){
		 storedCardDAO.update(storedCard);
		 if("ACTIVESTATUS".equals(type)){
			 saveComLog("STORED_CARD", storedCard.getStoredCardId(), operatorName,
						Constant.STORED_CARD_ENUM.UPDATE_STORED_CARD.name(), "储值卡激活状态","修改储值卡激活状态:"+storedCard.getActiveStatus());
		 }if("STATUS".equals(type)){
			 saveComLog("STORED_CARD", storedCard.getStoredCardId(), operatorName,
						Constant.STORED_CARD_ENUM.UPDATE_STORED_CARD.name(), "储值卡常规状态","修改储值卡常规状态:"+storedCard.getStatus());
		 }if("OVERTIME".equals(type)){
			 saveComLog("STORED_CARD", storedCard.getStoredCardId(), operatorName,
						Constant.STORED_CARD_ENUM.UPDATE_STORED_CARD.name(), "储值卡延期","修改储值卡延期:"+DateFormatUtils.format(storedCard.getOverTime(), "yyyy-MM-dd HH:mm:ss"));
		 }
		 
	}
	
	/**
	 * 根据卡得ID取卡的信息.
	 * @param storedCardId
	 * @return
	 */
	public  StoredCard  queryStoredCardById(long storedCardId){
		return this.storedCardDAO.queryStoredCardById(storedCardId);
	}
	
	/**
	 * 根据卡号取卡的信息.
	 * @param storedCardId
	 * @return
	 */
	public  StoredCard  queryStoredCardByCardNo(String cardNo){
		Map<String, Object> params=new HashMap<String, Object>();
    	params.put("cardNo", cardNo);
		StoredCard card= this.storedCardDAO.queryByCardNo(params);
		return card;
	}
	
	/**
	 * 根据卡号取卡的信息.
	 * @param storedCardId
	 * @return
	 */
	public  StoredCard  queryStoredCardByCardNoAndSerialNo(String cardNo,String serialNo){
		Map<String, Object> params=new HashMap<String, Object>();
    	params.put("cardNo", cardNo);
    	params.put("serialNo", serialNo);
		StoredCard card= this.storedCardDAO.queryByCardNo(params);
		return card;
	}
	
	
	/**
	 * 查卡最大的流水号的卡 .
	 * @return
	 */
	private String selectCardCount(Map<String,Object> parameter){
		StoredCard storedCard=storedCardDAO.lastStoredCard(parameter);
		if(storedCard!=null){
			return storedCard.getSerialNo();
		}else{
			return parameter.get("serialNo")+"00000000";
		}
	}
	
	 /**
     * 生成卡的开始流水号.
     * @param batchCount
     * @return
     */
    private String StringCardNoDecimal(final StoredCardBatch storedCardBatch){
    	String serialNo = UtilityTool.formatDate(storedCardBatch.getCreateTime(), "yyyyMM");
    	Map<String, Object> params=new HashMap<String, Object>();
    	params.put("serialNo", serialNo);
    	return selectCardCount(params);
    }
	
   
    /**
     * 根据卡号取卡的信息.
     * @param cardNo
     * @return
     */
    public StoredCard queryStoredCardByCardNo(final String cardNo,final boolean isCheckStatus){
    	Map<String, Object> params=new HashMap<String, Object>();
    	params.put("cardNo", cardNo);
    	if(isCheckStatus){
			params.put("status", Constant.STORED_CARD_ENUM.NORMAL.name());
			params.put("activeStatus", Constant.STORED_CARD_ENUM.ACTIVE.name());
			params.put("stockStatus",Constant.STORED_CARD_ENUM.OUT_STOCK.name());
    	}
    	List<StoredCard> list = storedCardDAO.queryByParam(params);
    	StoredCard card =new StoredCard();
    	if(list!=null&&list.size()>0){
    		card=list.get(0); 		
    	}
    	return card;
    }
    
    
    /**
	 * 卡数据列表.
	 * @param parameter
	 * @return
	 */
	public Page<StoredCard> selectCardByParam(Map<String,Object> parameter,Long pageSize,Long page){
		Page pageConfig = Page.page(pageSize, page);
		return this.storedCardDAO.selectByParam(parameter,pageConfig);
	}
	
	/**
	 * 取Card的记录数.
	 * @param parameter
	 * @return
	 */
	public Long selectCardByParamCount(Map<String, Object> parameter) {
		return storedCardDAO.selectByParamCount(parameter);
	}
	
	/**
	 * 取相同批次的储值卡的记录List.
	 * @param parameter
	 * @return
	 */
	public List<StoredCard> queryCardListByParam(Map<String, Object> parameter) {
		return storedCardDAO.queryByParam(parameter);
	}
	
	/**
	 * 卡号的生成.
	 * @param year
	 * @return
	 */
	private String generateCardNo(String year,String batchNo){
		String cardNo=null;
         do{
        	  cardNo = year.substring(2,4)+""+StringUtil.getRandomString(1, 15);
        	  String cardNoSub=cardNo.substring(3,4)+""+cardNo.substring(7,8)+""+cardNo.substring(11,12)+""+cardNo.substring(15,16);
        	  cardNo=cardNo+""+generateNumber(cardNoSub);
         }while(isCardNo(cardNo));
         return cardNo;
	}
    
	
	/**
	 * MD5加密取固定位数,取第3位,再取ASCIL.
	 * @param numberNo
	 * @return
	 */
	public String generateNumber(String cardNoSub){
		String numberNo=MD5.transStringMD5(cardNoSub,2,3);
		return String.valueOf(numberNo.hashCode()%9);
	}
	
	
	
	/**
	 * 卡号是否可用.
	 * @param cardNo
	 * @return
	 */
	private boolean isCardNo(String cardNo){
		Map<String, Object> params=new HashMap<String, Object>();
    	params.put("cardNo", cardNo);
    	List<StoredCard> list = storedCardDAO.queryByParam(params);
    	boolean isCard=false;
    	if(list!=null&&list.size()>0){
    		 isCard=true; 		
    	}
    	return isCard;
	}
    
	/**-------------------批次信息--------------------------------------------------------------*/
	
	/**
	 * 根据批次得ID取批次的信息.
	 * @param storedCardId
	 * @return
	 */
	public  StoredCardBatch  queryBatchById(long batchId){
		Map<String, Object> params=new HashMap<String, Object>();
		params.put("batchId", batchId);
    	List<StoredCardBatch> list=storedCardBatchDAO.queryByParam(params);
    	StoredCardBatch batch = new StoredCardBatch();
		if(list!=null&&list.size()>0){
			batch = list.get(0);
		}
         return batch;		
	}
	
	/**
	 * 保存批次信息记录.
	 * @param storedCardBatch
	 * @return
	 */
	public Long saveStoredCardBatch(StoredCardBatch storedCardBatch){
		storedCardBatch.setStatus(Constant.STORED_CARD_ENUM.NORMAL.name());
		storedCardBatchDAO.insert(storedCardBatch);
		saveComLog("STORED_CARD_BATCH", storedCardBatch.getBatchId(), storedCardBatch.getOperator(),
				Constant.STORED_CARD_ENUM.CREATE_STORED_CARD_BATCH.name(), "创建储值卡批次","创建储值卡批次");
		String cardStart=StringCardNoDecimal(storedCardBatch);
		String year=DateUtil.getFormatYear(storedCardBatch.getCreateTime());
		if(storedCardBatch.getCardCount()>0){
			for(int i=0;i<storedCardBatch.getCardCount();i++){
				Long cardStartNum=Long.valueOf(cardStart)+1L+Long.valueOf(i);
				StoredCard storedCard=new StoredCard();
				storedCard.setCardBatchNo(storedCardBatch.getBatchNo());
				storedCard.setCardNo(generateCardNo(year, storedCardBatch.getBatchNo()));
				storedCard.setSerialNo(String.valueOf(cardStartNum));
				storedCard.setAmount(storedCardBatch.getAmount());
				storedCard.setBalance(storedCardBatch.getAmount());
				storedCard.setActiveStatus(Constant.STORED_CARD_ENUM.UNACTIVE.name());
				storedCard.setStatus(Constant.STORED_CARD_ENUM.NORMAL.name());
				storedCard.setStockStatus(Constant.STORED_CARD_ENUM.NO_STOCK.name());
				storedCard.setCreateTime(storedCardBatch.getCreateTime());
				storedCard.setOverTime(storedCardBatch.getOverTime());
				if(i==0){
					storedCardBatch.setBeginSerialNo(String.valueOf(cardStartNum));
				}
				if(i==storedCardBatch.getCardCount()-1L){
					storedCardBatch.setEndSerialNo(String.valueOf(cardStartNum));
				}
				this.saveStoredCard(storedCard);
				saveComLog("STORED_CARD", storedCard.getStoredCardId(), storedCardBatch.getOperator(),
						Constant.STORED_CARD_ENUM.CREATE_STORED_CARD.name(), "创建储值卡","创建储值卡");
			}
		}
		this.updateStoredCardBatch(storedCardBatch);
		return storedCardBatch.getBatchId();
	}
	
	
	/**
	 * 更新批次记录.
	 * @param parameter
	 * @return
	 */
	public void updateStoredCardBatch(StoredCardBatch storedCardBatch){
		storedCardBatchDAO.update(storedCardBatch);
	}
	
	/**
	 * 批次作废.
	 * @param batchId
	 * @param operatorName
	 */
	public void batchCancel(StoredCardBatch batch,String operatorName,String cancelMemo){
		    Map<String, Object> params=new HashMap<String, Object>();
    		params.put("cardBatchNo", batch.getBatchNo());
    		List<StoredCard> cardList = storedCardDAO.queryByParam(params);
    		if(cardList != null&&cardList.size() > 0){
    			for(int i=0;i<cardList.size();i++){
    				StoredCard storedCard=(StoredCard)cardList.get(i);
    				storedCard.setStatus(Constant.STORED_CARD_ENUM.CANCEL.name());
    				this.updateStoredCard(storedCard,operatorName,"BATCH",cancelMemo);
    			}
    		}
    		this.updateStoredCardBatch(batch);
    		saveComLog("STORED_CARD_BATCH", batch.getBatchId(),operatorName,
    				Constant.STORED_CARD_ENUM.UPDATE_STORED_CARD_BATCH.name(), "储值卡批次作废",cancelMemo);
	}
	
	/**
	 * 判断整个批次是否可以作废.
	 * 作废：判断卡状态为正常状态，且库存状态为未入库状态，激活状态为未激活才可以作废.
	 * @return
	 */
	public boolean isBatchCancel(StoredCardBatch batch){
		Map<String, Object> params=new HashMap<String, Object>();
 		params.put("cardBatchNo", batch.getBatchNo());
 		List<StoredCard> contList=storedCardDAO.queryByParam(params);
 		params.put("status",Constant.STORED_CARD_ENUM.NORMAL.name());
 		params.put("activeStatus",Constant.STORED_CARD_ENUM.UNACTIVE.name());
 		params.put("stockStatus",Constant.STORED_CARD_ENUM.NO_STOCK.name());
 		List<StoredCard> cardList = storedCardDAO.queryByParam(params);
 		boolean isSave=false;
		if(contList!=null&&cardList!=null&&contList.size()>0&&cardList.size()>0){
			if(contList.size()==cardList.size()){
				isSave=true;
			}
		}
		return isSave;
	}
	
	
	
	/**
	 * 批次导出数据处理.
	 * @param parameter
	 * @param operatorName
	 * @return
	 */
	public List<StoredCard> doOutputStoredCard(Map<String,Object> parameter,String operatorName){
		List<StoredCardBatch> batchList=storedCardBatchDAO.queryByParam(parameter); 
		if(batchList!=null && batchList.size()>0){
			StoredCardBatch batch = batchList.get(0);
			saveComLog("STORED_CARD_BATCH", batch.getBatchId(),operatorName,
					Constant.STORED_CARD_ENUM.UPDATE_STORED_CARD_BATCH.name(), "储值卡批次导出","储值卡批次导出");
		}
		List<StoredCard> cardList=queryCardListByParam(parameter);
		if(cardList!=null && cardList.size()>0){
			for(int i=0;i<cardList.size();i++){
				StoredCard storedCard=(StoredCard)cardList.get(i);
				saveComLog("STORED_CARD", storedCard.getStoredCardId(), operatorName,
						Constant.STORED_CARD_ENUM.UPDATE_STORED_CARD.name(), "储值卡导出","储值卡导出");
			}	
		}
		return cardList;
	}
	
	
	/**
	 * 批次列表.
	 * @param parameter
	 * @return
	 */
	public Page<StoredCardBatch> selectBatchListByParam(Map<String,Object> parameter,Long pageSize,Long page){
		Page pageConfig = Page.page(pageSize, page);
		return this.storedCardBatchDAO.selectByParam(parameter,pageConfig);
	}
	
	/**
	 * 取CardBatch的记录数.
	 * @param parameter
	 * @return
	 */
	public Long selectBatchCountByParam(Map<String, Object> parameter) {
		return storedCardBatchDAO.selectByParamCount(parameter);
	}
	
	
	/**
	 * 根据时间和面额查询CardBatch的记录数.
	 * @param batchNo
	 * @return
	 */
	public Long selectBatchCount(String batchNo){
		return storedCardBatchDAO.selectBatchCount(batchNo);
	}
	
	
	
	

    /**
     * 根据卡号取该卡号的所有消费纪录.
     * @param cardNo
     * @return
     */
    public List<StoredCardUsage> queryUsageListByCardId(Map<String, Object> parameter){
    	List<StoredCardUsage> list = this.storedCardUsageDAO.queryByParam(parameter);
    	return list;
    }
	
	
	/**-------------------库信息-----------------------------------------------------------*/
	/**
	 * 保存库信息.
	 * @param storedCardStock
	 * @return
	 */
	public Long saveStoredCardStock(StoredCardStock storedCardStock){
		return storedCardStockDAO.insert(storedCardStock);
	}
	
	
	
	/**
	 * 保存储值卡消费记录.
	 * @param storedCardUsage
	 * @return
	 */
	public Long saveStoredCardUsage(StoredCardUsage storedCardUsage){
		return storedCardUsageDAO.insert(storedCardUsage);
	}

	/**
	 * 消费记录.
	 * @param param
	 * @return
	 */
	public List<StoredCardUsage> queryUsageByParam(Map<String, Object> param){
		return storedCardUsageDAO.queryByParam(param);
	}
	
	/**
	 * 统计储值卡,为生成入/出库单做准备.
	 * @param maps
	 * @return
	 */
	@Override
	public List<Map<String, Object>> storedCardStats(Map<String, Object> maps) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		List<StoredCard> storedCardList = storedCardStockDAO.selectCardByParamForCreateStock(maps);
		// 按面额统计数量、总金额
		if (!storedCardList.isEmpty()) {
			float totalAmount = 0;
			long totalCount = 0;
			Map<String, Object> totalMap = new HashMap<String, Object>();
			totalMap.put("totalTotalAmount", 0F);
			totalMap.put("totalTotalCount", 0L);
			for (StoredCard storedCard : storedCardList) {
				totalMap.put("totalTotalCount", (Long) totalMap.get("totalTotalCount") + 1L);
				totalMap.put("totalTotalAmount", (Float) totalMap.get("totalTotalAmount") + storedCard.getAmountFloat());
				if (list.size() == 0) {
					Map<String, Object> first = new HashMap<String, Object>();
					first.put("beginSerialNo", maps.get("beginSerialNo"));
					first.put("endSerialNo", maps.get("endSerialNo"));
					first.put("amountStr", storedCard.getAmount());
					first.put("amount", storedCard.getAmountFloat());
					first.put("totalAmount", storedCard.getAmountFloat());
					first.put("totalCount", 1L);
					list.add(first);
					continue;
				}
				//同样类型的往上累加
				boolean isSameAmount = true;
				for (Map<String, Object> temp : list) {
					if (temp.get("amount").equals(storedCard.getAmountFloat())) {
						totalAmount = (Float) temp.get("totalAmount") + storedCard.getAmountFloat();
						totalCount = (Long) temp.get("totalCount") + 1L;
						temp.put("totalAmount", totalAmount);
						temp.put("totalCount", totalCount);
						isSameAmount = true;
						break;
					} else {
						isSameAmount = false;
					}
				}
				if (!isSameAmount) {
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("beginSerialNo", maps.get("beginSerialNo"));
					map.put("endSerialNo", maps.get("endSerialNo"));
					map.put("amountStr", storedCard.getAmount());
					map.put("amount", storedCard.getAmountFloat());
					map.put("totalAmount", storedCard.getAmountFloat());
					map.put("totalCount", 1L);
					list.add(map);
				}
			}
			list.add(totalMap);
		}
		return list;
	}

	/**
	 * 创建入库单.
	 * @param lists
	 *            参数集合.
	 * @param operatorId
	 *            操作人Id.
	 * @return 入/出库单Id
	 */
	@Override
	public Long buildCardStock(Map<String, Object> maps) {
		String type = (String) maps.get("type");
		// 插入入/出库单
		Long stockId = 0L;
		StoredCardStock storedCardStock = new StoredCardStock();
		Date createTime = new Date();
		storedCardStock.setCreateTime(createTime);
		String operatorName = (String) maps.get("operatorName");
		storedCardStock.setOperator(operatorName);
		storedCardStock.setStatus(Constant.STORED_CARD_ENUM.NORMAL.name());
		if ("INTO".equals(type)) {
			storedCardStock.setStockType(Constant.STORED_CARD_ENUM.INTO_STOCK.name());
			stockId = storedCardStockDAO.insert(storedCardStock);
			this.saveComLog("STORED_CARD_STOCK", stockId, operatorName,
					Constant.STORED_CARD_ENUM.CREATE_INTO_STORED_CARD_STOCK.name(), "创建入库单", "创建入库单");
		} else if ("OUT".equals(type)) {
			storedCardStock.setStockType(Constant.STORED_CARD_ENUM.OUT_STOCK.name());
			storedCardStock.setCustomer((String) maps.get("customer"));
			storedCardStock.setAccepter((String) maps.get("accepter"));
			storedCardStock.setMemo((String) maps.get("memo"));
			stockId = storedCardStockDAO.insert(storedCardStock);
			this.saveComLog("STORED_CARD_STOCK", stockId, operatorName,
					Constant.STORED_CARD_ENUM.CREATE_OUT_STORED_CARD_STOCK.name(), "创建出库单", "创建出库单");
		}

		// 修改储值卡
		Map<String, Object> sqlMaps = new HashMap<String, Object>();
		sqlMaps.put("beginSerialNo", maps.get("beginSerialNo"));
		sqlMaps.put("endSerialNo", maps.get("endSerialNo"));
		sqlMaps.put("notInAmountList", maps.get("notInAmountList"));
		sqlMaps.put("status", Constant.STORED_CARD_ENUM.NORMAL.name());
		if ("INTO".equals(type)) {
			sqlMaps.put("stockStatus", null);
			List<StoredCard> storedCardList = storedCardStockDAO.selectCardByParamForCreateStock(sqlMaps);
			for (StoredCard storedCard : storedCardList) {
				storedCard.setIntoStockId(stockId);
				storedCard.setIntoTime(createTime);
				storedCard.setStockStatus(Constant.STORED_CARD_ENUM.INTO_STOCK.name());
				storedCardDAO.update(storedCard);
				this.saveComLog("STORED_CARD", storedCard.getStoredCardId(), operatorName,
						Constant.STORED_CARD_ENUM.INTO_STOCK.name(), "储值卡入库", "储值卡入库");
			}
		} else if ("OUT".equals(type)) {
			sqlMaps.put("stockStatus", "INTO_STOCK");
			List<StoredCard> storedCardList = storedCardStockDAO.selectCardByParamForCreateStock(sqlMaps);
			for (StoredCard storedCard : storedCardList) {
				storedCard.setOutStockId(stockId);
				storedCard.setOutTime(createTime);
				storedCard.setStockStatus(Constant.STORED_CARD_ENUM.OUT_STOCK.name());
				storedCardDAO.update(storedCard);
				this.saveComLog("STORED_CARD", storedCard.getStoredCardId(), operatorName,
						Constant.STORED_CARD_ENUM.OUT_STOCK.name(), "储值卡出库", "储值卡出库");
			}
		}
		return stockId;
	}

	/**
	 * 查询入/出库单数目.
	 * @param maps
	 * @return
	 */
	@Override
	public Long countCardStockByParameters(Map<String, Object> maps) {
		return storedCardStockDAO.selectCardStockCountByParameters(maps);
	}

	/**
	 * 统计入/出库单.
	 * @param maps
	 * @return
	 */
	@Override
	public Page<Map> cardStockStats(Map<String, Object> maps,Long pageSize,Long page) {
		String stockType = (String) maps.get("stockType");
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Page pageConfig = Page.page(pageSize, page);
		List<StoredCardStock> cardStockList = storedCardStockDAO.selectCardStockByParameters(maps,pageConfig);
		for (StoredCardStock cardStock : cardStockList) {
			Map<String, Object> sqlMap = new HashMap<String, Object>();
			Map<String, Object> resultMap = new HashMap<String, Object>();
			sqlMap.put("stockType", stockType);
			if ("INTO_STOCK".equals(stockType)) {
				sqlMap.put("intoStockId", cardStock.getStockId());
			} else if ("OUT_STOCK".equals(stockType)) {
				sqlMap.put("outStockId", cardStock.getStockId());
				resultMap.put("accepter", cardStock.getAccepter());
			}
			Long totalCount = storedCardDAO.selectByParamCount(sqlMap);
			resultMap.put("totalCount", totalCount);
			resultMap.put("stockId", cardStock.getStockId());
			resultMap.put("status", cardStock.getStatus());
			resultMap.put("createTime", DateUtil.getFormatDate(cardStock.getCreateTime(), "yyyy-MM-dd HH:mm:ss"));
			resultMap.put("operatorName", cardStock.getOperator());
			list.add(resultMap);
		}
		pageConfig.setItems(list);
		return pageConfig;
	}
	/**
	 * 核实出库单统计数据.
	 * @param maps
	 * @return
	 */
	public List<Map<String, Object>> verifyOutStock(Map<String,Object> maps){
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		List<StoredCard> storedCardList = storedCardDAO.selectByParam(maps);
		// 按面额统计数量、总金额
		if (!storedCardList.isEmpty()) {
			long totalAmount = 0;
			long totalCount = 0;
			Map<String, Object> totalMap = new HashMap<String, Object>();
			totalMap.put("totalTotalAmount", 0L);
			totalMap.put("totalTotalCount", 0L);
			for (StoredCard storedCard : storedCardList) {
				totalMap.put("totalTotalCount", (Long) totalMap.get("totalTotalCount") + 1L);
				totalMap.put("totalTotalAmount", (Long) totalMap.get("totalTotalAmount") + storedCard.getAmount());
				if (list.size() == 0) {
					Map<String, Object> first = new HashMap<String, Object>();
					first.put("amount", storedCard.getAmount());
					first.put("totalAmount", storedCard.getAmount());
					first.put("totalCount", 1L);
					list.add(first);
					continue;
				}
				boolean flag = false;
				for (Map<String, Object> temp : list) {
					if (temp.get("amount").equals(storedCard.getAmount())) {
						totalAmount = (Long) temp.get("totalAmount") + storedCard.getAmount();
						totalCount = (Long) temp.get("totalCount") + 1L;
						temp.put("totalAmount", totalAmount);
						temp.put("totalCount", totalCount);
						flag = false;
						break;
					} else {
						flag = true;
					}
				}
				if (flag) {
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("amount", storedCard.getAmount());
					map.put("totalAmount", storedCard.getAmount());
					map.put("totalCount", 1L);
					list.add(map);
				}
			}
			list.add(totalMap);
		}
		return list;
	}
	/**
	 * 移除cardStatisticsList中索引为index的对象.
	 * @param cardStatisticsList
	 * @param index
	 * @return
	 */
	@Override
	public List<Map<String, Object>> removeCardStatisticsByIndex(List<Map<String, Object>> cardStatisticsList, int index) {
		cardStatisticsList.remove(index);
		if (!cardStatisticsList.isEmpty()) {
			int lastIndex = cardStatisticsList.size() - 1;
			cardStatisticsList.remove(lastIndex);
		}
		long totalToalCount = 0L;
		Float totalTotalAmount = 0F;
		for (Map<String, Object> cardStatistics : cardStatisticsList) {
			long totalCount = (Long) cardStatistics.get("totalCount");
			Float totalAmount = (Float) cardStatistics.get("totalAmount");
			totalToalCount = totalCount + totalToalCount;
			totalTotalAmount = totalAmount + totalTotalAmount;
		}
		Map<String, Object> total = new HashMap<String, Object>();
		total.put("totalTotalCount", totalToalCount);
		total.put("totalTotalAmount", totalTotalAmount);
		cardStatisticsList.add(total);
		return cardStatisticsList;
	}

	/**
	 * 添加cardStatisticsList中的对象.
	 * @param cardStatisticsList
	 * @param parameters
	 * @return
	 */
	@Override
	public List<Map<String, Object>> addCardStatistics(List<Map<String, Object>> cardStatisticsList,
			Map<String, Object> parameters) {
		if (cardStatisticsList == null || cardStatisticsList.isEmpty()) {
			cardStatisticsList = this.storedCardStats(parameters);
		} else if (!cardStatisticsList.isEmpty()) {
			List<Map<String, Object>> list = this.storedCardStats(parameters);
			if (!list.isEmpty()) {
				int oldLastIndex = cardStatisticsList.size() - 1;
				Map<String, Object> oldTotalMap = cardStatisticsList.get(cardStatisticsList.size() - 1);
				cardStatisticsList.remove(oldLastIndex);
				for (int i = 0; i < list.size() - 1; i++) {
					cardStatisticsList.add(list.get(i));
				}
				Map<String, Object> newTotalMap = list.get(list.size() - 1);
				long totalTotalCount = (Long) oldTotalMap.get("totalTotalCount")
						+ (Long) newTotalMap.get("totalTotalCount");
				Float totalTotalAmount = (Float) oldTotalMap.get("totalTotalAmount")
						+ (Float) newTotalMap.get("totalTotalAmount");
				newTotalMap.put("totalTotalCount", totalTotalCount);
				newTotalMap.put("totalTotalAmount", totalTotalAmount);
				cardStatisticsList.add(newTotalMap);
			}
		}
		return cardStatisticsList;
	}

	/**
	 * 查询单个入/出库单.
	 * @param stockId
	 * @return
	 */
	@Override
	public StoredCardStock queryCardStockByStockId(Long stockId) {
		return storedCardStockDAO.selectCardStockByStockId(stockId);
	}

	/**
	 * 作废出库单.
	 * @param maps
	 */
	@Override
	public boolean cancleOutStockByParam(Map<String, Object> maps) {
		Long stockId = Long.parseLong((String) maps.get("outStockId"));
		boolean flag = this.isOK(stockId);
		// 判断卡库存状态为未激活状态，且卡是出库状态，常规状态为正常才可以作废
		if (flag) {
			String operatorName = (String) maps.get("operatorName");
			StoredCardStock stock = storedCardStockDAO.selectCardStockByStockId(stockId);
			if (stock != null) {
				stock.setStatus(Constant.STORED_CARD_ENUM.CANCEL.name());
				this.saveComLog("STORED_CARD_STOCK", stockId, operatorName, Constant.STORED_CARD_ENUM.CANCEL.name(),
						"出库单作废", "作废原因：" + (String)maps.get("cancleReason"));
				storedCardStockDAO.update(stock);
				Map<String, Object> sqlMaps = new HashMap<String, Object>();
				sqlMaps.put("outStockId", stockId);
				List<StoredCard> cardList = storedCardDAO.selectByParam(sqlMaps);
				for (StoredCard card : cardList) {
					card.setOutStockId(0L);
					card.setOutTime(null);
					card.setStockStatus(Constant.STORED_CARD_ENUM.INTO_STOCK.name());
					storedCardDAO.update(card);
					this.saveComLog("STORED_CARD", card.getStoredCardId(), operatorName,
							Constant.STORED_CARD_ENUM.UPDATE_STORED_CARD.name(), "更新储值卡",
							"原出库单作废，出库单ID为：" + stockId);
				}
			}
		}
		return flag;
	}

	/**
	 * 激活某出库单的所有储值卡.
	 * @param stockId
	 */
	@Override
	public boolean activeCardByParam(Map<String, Object> maps) {
		Long stockId = Long.parseLong((String) maps.get("outStockId"));
		boolean flag = this.isOK(stockId);
		// 判断卡库存状态为未激活状态，且卡是出库状态，常规状态为正常才可以激活
		if(flag){
			String operatorName = (String) maps.get("operatorName");
			StoredCardStock storedCardStock = storedCardStockDAO.selectCardStockByStockId(stockId);
			// 该出库单的状态为正常时才会激活
			boolean isNormal = Constant.STORED_CARD_ENUM.NORMAL.name().equals(storedCardStock.getStatus());
			if (isNormal) {
				storedCardStock.setActualReceived(Long.parseLong((String)maps.get("actualReceived")));
				storedCardStock.setPaymentUnit((String)maps.get("paymentUnit"));
				storedCardStock.setPaymentType((String)maps.get("paymentType"));
				storedCardStock.setPaymentTime((Date)maps.get("paymentTime"));
				storedCardStockDAO.update(storedCardStock);
				this.saveComLog("STORED_CARD_STOCK", stockId, operatorName,
						Constant.STORED_CARD_ENUM.UPDATE_OUT_STORED_CARD_STOCK.name(), "储值卡激活状态", "激活该出库单内所有储值卡");
				Map<String, Object> sqlMaps = new HashMap<String, Object>();
				sqlMaps.put("outStockId", stockId);
				List<StoredCard> cardList = storedCardDAO.selectByParam(sqlMaps);
				for (StoredCard storedCard : cardList) {
					storedCard.setActiveStatus(Constant.STORED_CARD_ENUM.ACTIVE.name());
					storedCardDAO.update(storedCard);
					this.saveComLog("STORED_CARD", storedCard.getStoredCardId(), operatorName,
							Constant.STORED_CARD_ENUM.UPDATE_STORED_CARD.name(), "储值卡激活状态", "按出库单激活储值卡，出库单ID为:" + stockId);
				}
			}
		}
		
		return flag;
	}
	/**
	 * 是否符合根据出库单来激活、作废储值卡.
	 * @param stockId
	 * @return
	 */
	public boolean isOK(Long stockId){
		boolean flag = true;
		Map<String, Object> sqlMaps = new HashMap<String, Object>();
		sqlMaps.put("outStockId", stockId);
		List<StoredCard> cardList = storedCardDAO.selectByParam(sqlMaps);
		for (StoredCard card : cardList) {
			//卡库存状态为出库，卡常规状态为正常，卡激活状态为未激活
			if (!card.getStatus().equals(Constant.STORED_CARD_ENUM.NORMAL.name())
					|| !card.getStockStatus().equals(Constant.STORED_CARD_ENUM.OUT_STOCK.name())
					|| !card.getActiveStatus().equals(Constant.STORED_CARD_ENUM.UNACTIVE.name())) {
				flag = false;
				break;
			}
		}
		return flag;
	}
	/**
	 * 导出入/出库单中所有储值卡.
	 * @param maps
	 * @return
	 */
	@Override
	public List<StoredCard> storedCardExport(Map<String, Object> maps) {
		List<StoredCard> storedCardList = null;
		String operatorName = (String) maps.get("operatorName");
		if(maps.get("outStockId") != null){
			Long stockId = Long.parseLong((String) maps.get("outStockId"));
			this.saveComLog("STORED_CARD_STOCK", stockId, operatorName,
					Constant.STORED_CARD_ENUM.UPDATE_OUT_STORED_CARD_STOCK.name(), "储值卡导出", "导出该出库单中所有储值卡");
			storedCardList = storedCardDAO.queryByParam(maps);
			for(StoredCard storedCard : storedCardList){
				saveComLog("STORED_CARD", storedCard.getStoredCardId(), operatorName,
						Constant.STORED_CARD_ENUM.UPDATE_STORED_CARD.name(), "储值卡导出","储值卡从出库单中导出，出库单Id为" + stockId);
			}
		} else if(maps.get("intoStockId") != null){
			Long stockId = Long.parseLong((String) maps.get("intoStockId"));
			this.saveComLog("STORED_CARD_STOCK", stockId, operatorName,
					Constant.STORED_CARD_ENUM.UPDATE_INTO_STORED_CARD_STOCK.name(), "储值卡导出", "导出该入库单中所有储值卡");
			storedCardList = storedCardDAO.queryByParam(maps);
			for(StoredCard storedCard : storedCardList){
				saveComLog("STORED_CARD", storedCard.getStoredCardId(), operatorName,
						Constant.STORED_CARD_ENUM.UPDATE_STORED_CARD.name(), "储值卡导出","储值卡从入库单中导出，入库单Id为" + stockId);
			}
		}
		return storedCardList;
	}

	private String whichIsChanged(String filedName,String oldValue,String newValue){
		oldValue = oldValue == null ? "" : oldValue;
		newValue = newValue == null ? "" : newValue;
		String result = oldValue.equals(newValue) ? "" : filedName + ":旧值=" + oldValue + " 新值=" + newValue + "; ";
		return result;
	}	
	
	
	/**
	 * 根据类型,对象ID,日志类型查询相应的日志.
	 * @param objectId
	 * @param objectType
	 * @param logType
	 * @return
	 */
	public List<ComLog> queryComLog(final Long objectId,
			final String objectType){
		return this.comLogDAO.queryComLog(objectId,objectType,null);
	}
	
	/**
	 * 保存操作日志.
	 * @param objectType
	 * @param objectId
	 * @param operatorName
	 * @param logType
	 * @param logName
	 * @param content
	 */
	private void saveComLog(final String objectType, final Long objectId,
			final String operatorName, final String logType,
			final String logName,final String content) {
		final ComLog log = new ComLog();
		log.setObjectType(objectType);
		log.setObjectId(objectId);
		log.setOperatorName(operatorName);
		log.setLogType(logType);
		log.setLogName(logName);
		log.setContent(content);
		comLogDAO.insert(log);
	}
	
	/**
	 * 判断储值卡是不是可以用来支付.
	 * @param cardNo
	 * @return
	 */
	public boolean isCardPay(String cardNo) {
		StoredCard card = this.queryStoredCardByCardNo(cardNo,true);
		boolean isPay = false;
		// 取相应的面值代码.
		if (card.getStoredCardId() != null&&card.getStoredCardId()>0) {
			String batchNoSub = card.getCardBatchNo().substring(8, 9);
			String lastCardNo = card.getCardNo().substring(17, 18);
//			String cardNoSub = "";
//			if ("A".equals(batchNoSub)) {
//				cardNoSub = cardNo.substring(3, 7);
//			}
//			if ("B".equals(batchNoSub)) {
//				cardNoSub = cardNo.substring(4, 8);
//			}
//			if ("C".equals(batchNoSub)) {
//				cardNoSub = cardNo.substring(5, 9);
//			}
//			if ("D".equals(batchNoSub)) {
//				cardNoSub = cardNo.substring(6, 10);
//			}
			String cardNoSub=cardNo.substring(3,4)+""+cardNo.substring(7,8)+""+cardNo.substring(11,12)+""+cardNo.substring(15,16);
			
			String LastCardNoString=this.generateNumber(cardNoSub);
			if (lastCardNo.equals(LastCardNoString)&&card.getBalance()>0) {
				isPay = true;
			}
		}
		return isPay;
	}
	
	/**
	 * 储值卡支付.
	 * 判断储值卡的余额  用应用金额减去已付金额.
	 *
	 * @param orderId
	 *            订单ID
	 * @param operateName
	 *            操作人
	 * @return <code>true</code>代表使用储值卡支付订单成功，<code>false</code>
	 *         代表使用储值卡支付订单失败
	 */
	public Long payFromStoredCard(StoredCard card,Long orderId, String bizType ,Long payAmount,Long operatorId) {
		// 订单应付.
		long actualPayAmount = payAmount;

		if (card.getBalance() < payAmount) {
			actualPayAmount = card.getBalance();
		}
		
		PayPayment payment = new PayPayment();
		payment.setBizType(bizType);
		payment.setObjectType(Constant.OBJECT_TYPE.ORD_ORDER.name());
		payment.setPaymentType(Constant.PAYMENT_OPERATE_TYPE.PAY.name());
		payment.setObjectId(orderId);
		payment.setPaymentGateway(Constant.PAYMENT_GATEWAY.STORED_CARD.name());
		payment.setAmount(actualPayAmount);
		payment.setStatus(Constant.PAYMENT_SERIAL_STATUS.SUCCESS.name());
		payment.setCallbackTime(new Date());
		payment.setSerial(payment.geneSerialNo());
		payment.setCreateTime(new Date());
		payment.setPaymentTradeNo(payment.getSerial());
		Long paymentId = payPaymentService.savePayment(payment);

		//更新储值卡余额
		card.setBalance(card.getBalance() - actualPayAmount);
		this.updateStoredCard(card);

		// 保存储值卡消费记录.
		StoredCardUsage usage = new StoredCardUsage();
		usage.setAmount(actualPayAmount);
		usage.setCardId(card.getStoredCardId());
		usage.setCreateTime(new Date());
		usage.setOperator(operatorId+"");
		usage.setOrderId(orderId);
		usage.setSerial(payment.getSerial());
		usage.setUsageType(Constant.STORED_CARD_ENUM.STORED_PAY.name());
		this.saveStoredCardUsage(usage);
		
		return paymentId;
	}
	

	/**
	 * 有储值卡支付的退款处理
	 * @param userId  用户ID
	 * @param refundmentEventId  退款记录ID
	 * @param refundmentAmount  退款金额，以分为单位
	 * @param orderId 订单ID
	 * @param ordPaymentList 订单支付记录
	 * @return <code>true</code>代表退款成功，<code>false</code>代表退款失败
	 */
	public BankReturnInfo refund2CardAccount(Long payRefundmentId,Long paymentId,Long refundAmount,String serialNo,String operatorId) {
		BankReturnInfo bankReturnInfo = new BankReturnInfo();
		// 判断储值卡有没有退过款.
		Long protectCount = cashAccountDAO.selectProtectCount(payRefundmentId.toString(),ComeFrom.STORED_CRAD_REFUND.toString());
		if (protectCount == 0L) {
			cashAccountDAO.protect(payRefundmentId.toString(), ComeFrom.STORED_CRAD_REFUND.toString());
			refundCardAmount(operatorId, paymentId, refundAmount, serialNo, new Date());
			bankReturnInfo.setSuccessFlag(Constant.PAY_REFUNDMENT_SERIAL_STATUS.SUCCESS.name());
			bankReturnInfo.setCodeInfo("退款成功");
		}else{
			bankReturnInfo.setSuccessFlag(Constant.PAY_REFUNDMENT_SERIAL_STATUS.FAIL.name());
			bankReturnInfo.setCodeInfo("不允许重复退款!");
		}
		return bankReturnInfo;
	}
	

	/**
	 * 储值卡退款.
	 * @param userId
	 * @param orderId
	 * @param refundAmount
	 * @param paymentAmount
	 * @param serialNo
	 * @return
	 */
	private void refundCardAmount(String userId,Long paymentId,
			Long paymentAmount,String serialNo,Date tranDate) {
		//1.储值卡消费记录
		StoredCardUsage usage = storedCardUsageDAO.queryBySerial(serialNo);
		//2.根据储值卡的消费记录取相应的储值卡的信息.
		StoredCard card = storedCardDAO.queryStoredCardById(usage.getCardId());
		PayPayment payment = this.payPaymentService.selectByPaymentId(paymentId);
		Long orderId = null;
		if (payment!=null){
			orderId = payment.getObjectId();
		}
		
		//3.判断退款金额     增加一条相应储值卡的退费记录.
		StoredCardUsage cardUsage = new StoredCardUsage();
		cardUsage.setAmount(paymentAmount);
		cardUsage.setCardId(card.getStoredCardId());
		cardUsage.setCreateTime(tranDate);
		cardUsage.setOperator(userId);
		cardUsage.setOrderId(orderId);
		cardUsage.setSerial(serialNo);
		cardUsage.setUsageType(STORED_CARD_ENUM.STORED_REFUND.name());
		storedCardUsageDAO.insert(cardUsage);
		//4.修改储值卡的余额.
		Long balance = card.getBalance();
		card.setBalance(balance + paymentAmount); 		
		storedCardDAO.update(card);
	}

	@Override
	public boolean updateOutStock(StoredCardStock cardStock, String operatorName) {
		StoredCardStock dbCardStock=storedCardStockDAO.selectCardStockByStockId(cardStock.getStockId());
		if(null!=dbCardStock){
			StringBuilder logBuilder=new StringBuilder();
			if(StringUtils.isBlank(dbCardStock.getCustomer())){
				dbCardStock.setCustomer("");
			}
			if(!dbCardStock.getCustomer().equals(cardStock.getCustomer())){
				logBuilder.append("客户，新值:"+cardStock.getCustomer()+"，旧值:"+dbCardStock.getCustomer()+";");
				dbCardStock.setCustomer(cardStock.getCustomer());
			}
			if(StringUtils.isBlank(dbCardStock.getAccepter())){
				dbCardStock.setAccepter("");
			}
			if(!dbCardStock.getAccepter().equals(cardStock.getAccepter())){
				logBuilder.append("接收人，新值:"+cardStock.getAccepter()+"，旧值:"+dbCardStock.getAccepter()+";");
				dbCardStock.setAccepter(cardStock.getAccepter());
			}
			if(null==dbCardStock.getActualReceived()){
				dbCardStock.setActualReceived(0L);
			}
			if(!dbCardStock.getActualReceived().equals(cardStock.getActualReceived())){
				logBuilder.append("实收金额，新值:"+cardStock.getActualReceived()+"，旧值:"+dbCardStock.getActualReceived()+";");
				dbCardStock.setActualReceived(cardStock.getActualReceived());
			}
			if(StringUtils.isBlank(dbCardStock.getPaymentUnit())){
				dbCardStock.setPaymentUnit("");
			}
			if(!dbCardStock.getPaymentUnit().equals(cardStock.getPaymentUnit())){
				logBuilder.append("付款单位，新值:"+cardStock.getPaymentUnit()+"，旧值:"+dbCardStock.getPaymentUnit()+";");
				dbCardStock.setPaymentUnit(cardStock.getPaymentUnit());
			}
			if(StringUtils.isBlank(dbCardStock.getPaymentType())){
				dbCardStock.setPaymentType("");
			}
			if(!dbCardStock.getPaymentType().equals(cardStock.getPaymentType())){
				logBuilder.append("收款方式，新值:"+cardStock.getPaymentType()+"，旧值:"+dbCardStock.getPaymentType()+";");
				dbCardStock.setPaymentType(cardStock.getPaymentType());
			}
			if(StringUtils.isBlank(dbCardStock.getMemo())){
				dbCardStock.setMemo("");
			}
			if(!dbCardStock.getMemo().equals(cardStock.getMemo())){
				logBuilder.append("备注信息，新值:"+cardStock.getMemo()+"，旧值:"+dbCardStock.getMemo()+";");
				dbCardStock.setMemo(cardStock.getMemo());
			}
			if(StringUtils.isNotBlank(logBuilder.toString())){
				this.saveComLog("STORED_CARD_STOCK",cardStock.getStockId(),operatorName, Constant.STORED_CARD_ENUM.UPDATE_STORED_CARD.name(),"储值卡出库单修改 ", logBuilder.toString());
				storedCardStockDAO.update(dbCardStock);
			}
			return true;
		}
		return false;
	}
}
