package com.lvmama.passport.processor.impl.client.carnival;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.passport.processor.impl.client.carnival.model.Ret;
import com.lvmama.passport.utils.HttpsUtil;
import com.lvmama.passport.utils.WebServiceConstant;
/**
 * 嘉年华业务类
 * @author gaoxin
 *
 */
public class CarnivalClient {
	/** 日志工具类*/
	private static Log log = LogFactory.getLog(CarnivalClient.class);
	
	/**
	 * 申请SId
	 */
	public static Ret getSid() throws Exception {
		String url=WebServiceConstant.getProperties("jianianhua.url")+"?api_name=Common.Who&reqid=0";
		log.info("getSid Url:"+url);
		String resXml = HttpsUtil.requestGet(url);
		if(resXml.startsWith(HttpsUtil.HTTP_ERROR_PREFIX)){
			throw new Exception(resXml.substring(HttpsUtil.HTTP_ERROR_PREFIX.length()));
		}
		log.info("getSid ResXml:"+resXml);
		Ret ret=new Ret();
		ret = CarnivalUtil.getSidRes(resXml);
		log.info("sid:"+ret.getData().getSid());
		return ret;
	}
	
	/**
	 * 握手
	 */
	public static Ret check(String sid) throws Exception{
		String url=WebServiceConstant.getProperties("jianianhua.url")+"?api_name=Common.AutoSignIn&reqid=1&sid="+sid;
		log.info("check Url:"+url);
		String resXml = HttpsUtil.requestGet(url);
		if(resXml.startsWith(HttpsUtil.HTTP_ERROR_PREFIX)){
			throw new Exception(resXml.substring(HttpsUtil.HTTP_ERROR_PREFIX.length()));
		}
		log.info("check resXMl:"+resXml);
		Ret ret=new Ret();
		ret=CarnivalUtil.getConRes(resXml);
		log.info("message:"+ret.getData().getText());
		return ret;
	}
	
	/**
	 * 登陆
	 */
	public static Ret getLogin(String sid)throws Exception{
		String url=WebServiceConstant.getProperties("jianianhua.url")+"?api_name=Common.SignIn&reqid=2&sid="+sid+"&copNo="+WebServiceConstant.getProperties("jianianhua.copNo")+"&optNo="+WebServiceConstant.getProperties("jianianhua.user")+"&optPass="+WebServiceConstant.getProperties("jianianhua.password");
		log.info("doLogin Url:"+url);
		String resXml = HttpsUtil.requestGet(url);
		if(resXml.startsWith(HttpsUtil.HTTP_ERROR_PREFIX)){
			throw new Exception(resXml.substring(HttpsUtil.HTTP_ERROR_PREFIX.length()));
		}
		log.info("doLogin resXMl:"+resXml);
		Ret ret=new Ret();
		ret=CarnivalUtil.getLoginRes(resXml);
		log.info("message:"+ret.getData().getText());
		return ret;
	}
	/**
	 * 交易
	 * @param sid
	 * @param volume 份数
	 * @param mobile 手机号
	 * @param outStance 流水号
	 */
	public static Ret getTransaction(String sid,String volume,String mobile,String outStance,String outSampleID)throws Exception{
		String url=WebServiceConstant.getProperties("jianianhua.url")+"?api_name=Trade.SendCoupon&reqid=3&sid="+sid+"&outSampleID="+outSampleID+"&volume="+volume+"&mobile="+mobile+"&outStance="+outStance;
		log.info("doTransaction url:"+url);
		String resXml = HttpsUtil.requestGet(url);
		if(resXml.startsWith(HttpsUtil.HTTP_ERROR_PREFIX)){
			throw new Exception(resXml.substring(HttpsUtil.HTTP_ERROR_PREFIX.length()));
		}
		log.info("doTransaction resXMl:"+resXml);
		Ret ret=new Ret();
		ret=CarnivalUtil.getTransactionRes(resXml);
		if("0".equals(ret.getCode())){
			if(ret.getData().getNewCoupons().size()<=0){
				log.info("doTransaction message:"+ret.getData().getNewCoupons().get(0).getSample());
			}
		}
		return ret;
	}
	/**
	 * 取消交易
	 * @param sid
	 * @param outStance
	 * @return
	 * @throws Exception
	 */
	public static Map<String,String> doDestroyOrder(String sid,String outStance)throws Exception{
		Map<String,String> map=new HashMap<String, String>();
		String url=WebServiceConstant.getProperties("jianianhua.url")+"?api_name=Trade.SendUndo&reqid=4&sid="+sid+"&outStance="+outStance;
		log.info("message:"+url);
		String resXml = HttpsUtil.requestGet(url);
		if(resXml.startsWith(HttpsUtil.HTTP_ERROR_PREFIX)){
			throw new Exception(resXml.substring(HttpsUtil.HTTP_ERROR_PREFIX.length()));
		}
		log.info("destroy resXMl:"+resXml);
		map=CarnivalUtil.getDestroyRes(resXml);
		return map;
	}
}
