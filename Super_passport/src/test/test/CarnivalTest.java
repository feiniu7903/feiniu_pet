package test;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;

import com.lvmama.passport.processor.impl.client.carnival.CarnivalUtil;
import com.lvmama.passport.processor.impl.client.carnival.model.Ret;
import com.lvmama.passport.utils.HttpsUtil;

/**
 * 嘉年华测试
 * @author gaoxin
 *
 */
public class CarnivalTest {
	private static final Log log = LogFactory.getLog(CarnivalTest.class);
	/**
	 * 获取Sid测试
	 */
	@Test
	public void getsidTest(){
		String url="http://bsd164.kakatong.com/kktv4/get?api_name=Common.Who&reqid=0";
		log.info("------------------------------------Url:"+url);
		String resXml=HttpsUtil.requestGet(url);
		Ret ret=null;
		try {
			ret = CarnivalUtil.getSidRes(resXml);
			log.info("------------------------------------sid:"+ret.getData().getSid());
		} catch (Exception e) {
			log.error("Carnival ApplyCode Exception", e);
		}
	}
	
	/**
	 * 握手
	 */
	@Test
	public void getConTest(){
		String url="http://bsd164.kakatong.com/kktv4/get?api_name=Common.AutoSignIn&reqid=1&sid=9daa9c2be17a9378b7bd986df10ac400";
		log.info("------------------------------------Url:"+url);
		String resXml=HttpsUtil.requestGet(url);
		log.info("------------------------------------resXMl:"+resXml);
		try {
			Ret ret=CarnivalUtil.getSidRes(resXml);
			log.info("------------------------------------message:"+ret.getData().getText());
		} catch (Exception e) {
			log.error("Carnival ApplyCode Exception", e);
		}
	}
	
	
	/**
	 * 登陆
	 */
	@Test
	public void getLoginTest(){
		String url="http://bsd164.kakatong.com/kktv4/get?api_name=Common.SignIn&reqid=2&sid=9daa9c2be17a9378b7bd986df10ac400&copNo=1200&optNo=pos1200&optPass=111111";
		log.info("------------------------------------Url:"+url);
		String resXml=HttpsUtil.requestGet(url);
		log.info("------------------------------------resXMl:"+resXml);
		try {
			Ret ret=CarnivalUtil.getSidRes(resXml);
			log.info("------------------------------------message:"+ret.getData().getText());
		} catch (Exception e) {
			log.error("Carnival ApplyCode Exception", e);
		}
	}
	
	/**
	 * 交易
	 */
	@Test
	public void getTransactionTest(){
		String url="http://bsd164.kakatong.com/kktv4/get?api_name=Trade.SendCoupon&reqid=3&sid=9daa9c2be17a9378b7bd986df10ac400&outSampleID=1483&volume=2&mobile=17864691074&outStance=55577";
		log.info("------------------------------------message:"+url);
		
		String resXml=HttpsUtil.requestGet(url);
		log.info("------------------------------------resXMl:"+resXml);
		try {
			Ret ret=CarnivalUtil.getSidRes(resXml);
			log.info("------------------------------------message:"+ret.getData().getNewCoupons().get(0));
		} catch (Exception e) {
			log.error("Carnival ApplyCode Exception", e);
		}
	}
	@Test
	public void testAll(){
		String url="http://bsd164.kakatong.com/kktv4/get?api_name=Common.Who&reqid=0";
		log.info("------------------------------------Url:"+url);
		String resXml=HttpsUtil.requestGet(url);
		Ret ret=null;
		String sid="";
		try {
			ret = CarnivalUtil.getSidRes(resXml);
			log.info("------------------------------------sid:"+ret.getData().getSid());
			sid=ret.getData().getSid();
		} catch (Exception e) {
			log.error("Carnival ApplyCode Exception", e);
		}
	
		
		url="http://bsd164.kakatong.com/kktv4/get?api_name=Common.AutoSignIn&reqid=1&sid="+sid;
		log.info("------------------------------------Url:"+url);
		resXml=HttpsUtil.requestGet(url);
		log.info("------------------------------------resXMl:"+resXml);
		try {
			 ret=CarnivalUtil.getConRes(resXml);
			log.info("------------------------------------message:"+ret.getData().getText());
		} catch (Exception e) {
			log.error("Carnival ApplyCode Exception", e);
		}
		
		 url="http://bsd164.kakatong.com/kktv4/get?api_name=Common.SignIn&reqid=2&sid="+sid+"&copNo=1200&optNo=pos1200&optPass=111111";
		log.info("------------------------------------Url:"+url);
		 resXml=HttpsUtil.requestGet(url);
		log.info("------------------------------------resXMl:"+resXml);
		try {
			 ret=CarnivalUtil.getLoginRes(resXml);
			log.info("------------------------------------message:"+ret.getData().getText());
		} catch (Exception e) {
			log.error("Carnival ApplyCode Exception", e);
		}
		
		 url="http://bsd164.kakatong.com/kktv4/get?api_name=Trade.SendCoupon&reqid=3&sid="+sid+"&outSampleID=1670&volume=2&mobile=17864691074&outStance=66566";
		log.info("------------------------------------message:"+url);
		 resXml=HttpsUtil.requestGet(url);
		log.info("------------------------------------resXMl:"+resXml);
		try {
			 ret=CarnivalUtil.getTransactionRes(resXml);
			log.info("------------------------------------message:"+ret.getData().getNewCoupons().get(0).getSample()+"            "+ret.getData().getNewCoupons().get(0).getToken()+"      "+ret.getData().getNewCoupons().get(0).getDesc());
		} catch (Exception e) {
			log.error("Carnival ApplyCode Exception", e);
		}
	}
	                       
}
