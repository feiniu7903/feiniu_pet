package com.lvmama.pet.lvmamacard.service;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.lvmama.comm.pet.po.lvmamacard.LvmamaStoredCard;
import com.lvmama.comm.pet.po.lvmamacard.StoredCardIn;
import com.lvmama.comm.pet.service.lvmamacard.LvmamacardService;
import com.lvmama.comm.vo.Constant;
import com.lvmama.pet.BaseTest;
@ContextConfiguration(locations = { "classpath:/applicationContext-pet-public-beans.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class LvmamacardServiceTest extends BaseTest {

 	@Autowired
	private LvmamacardService lvmamacardService;
	@Test
	public void test(){
	/*	Map param=new HashMap();
		param.put("cardBatchNo", "G00000001");
		param.put("cardNo", "500000000003");
		String[] cardNoList={"500000000005","500000000006"};
		 lvmamacardService.batchCancelLvmamaStoredCardByArray(cardNoList,Constant.STORED_CARD_STATUS.CANCEL.getCode());
*/		 
	LvmamaStoredCard	lvmamaCard=lvmamacardService.getOneStoreCardByCardNo("100000000001");
		 lvmamacardService.doDeplay(lvmamaCard,null);
	}
	
	public static void main(String[] args) {
		System.out.println(0%6);
	}
	
	private StoredCardIn generalBatchCard(Integer amount2, Integer count2,String batchCode) {
		List<LvmamaStoredCard> list=new ArrayList<LvmamaStoredCard>();
		StoredCardIn cardFanwei2=new StoredCardIn();
		//最新的序号
		Integer lastNo=generalCardLastNo(amount2);
 		DecimalFormat decimalformat=new DecimalFormat("00000000");
		for(int i=1;i<=count2;i++){
			String cardCode=Constant.CARD_AMOUNT.getCardPreCode(amount2.toString())+decimalformat.format(lastNo+i);
			LvmamaStoredCard lvmamastoredcard=new LvmamaStoredCard();
			lvmamastoredcard.setType(Integer.valueOf(Constant.CARD_TYPE.newed.getCode()));
			lvmamastoredcard.setAmount(amount2*100L);
			lvmamastoredcard.setBalance(amount2*100L);
			lvmamastoredcard.setCardBatchNo(batchCode);
			lvmamastoredcard.setActiveStatus(Constant.STORED_CARD_ENUM.UNACTIVE.name());
			lvmamastoredcard.setStatus(Constant.STORED_CARD_ENUM.NORMAL.name());
			lvmamastoredcard.setStockStatus(Constant.STORED_CARD_ENUM.NO_STOCK.name());
			lvmamastoredcard.setCardNo(cardCode);
			if(i==1){
				cardFanwei2.setCardNoBegin(lvmamastoredcard.getCardNo());
			}
			if(i==count2){
				cardFanwei2.setCardNoEnd(lvmamastoredcard.getCardNo());
			}
			list.add(lvmamastoredcard);
			//lvmamacardService.insertLvmamaStoredCard(lvmamastoredcard);
		}
		lvmamacardService.batchinsertLvmamaStoredCardForLvmamaStoredCard(list);

 		return null;
	}
	/**
	 * 通过面值类型获取最新的card序列号
	 * @param amount3
	 * @return
	 * @author nixianjun 2013-11-25
	 */
	private Integer generalCardLastNo(Integer amount3) {
		 String code=lvmamacardService.getLastCardNoByAmountForLvmamaStoredCard(amount3);
		 Integer serNo;
		 if(code!=null){
			  serNo=Integer.valueOf(code.substring(4, code.length()));
		 }else {
			 serNo=1 ;
		 }
 		return serNo;
	}
	/**
	 * 获取入库号  role(比如    面值对应代号+8位数字)
	 * @return 
	 * @author nixianjun 2013-11-25
	 * @param amount2 
	 */
	private String generalIncode(Integer amount2) {
	    String code= lvmamacardService.getIncodeForInStorege(amount2);
 		DecimalFormat decimalformat=new DecimalFormat("00000000");
		  if(code!=null){
			 code=Constant.CARD_AMOUNT.getCode("100") +decimalformat.format(Integer.valueOf(code.substring(1, code.length()))+1);
		 }else {
			 code="A00000001" ;
		 }
  	   return code;
	}
}
