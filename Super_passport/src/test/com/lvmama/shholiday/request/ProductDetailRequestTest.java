package com.lvmama.shholiday.request;

import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.time.DateUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.io.SAXReader;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.lvmama.comm.bee.po.prod.ViewContent;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.shholiday.ShholidayClient;

import com.lvmama.shholiday.service.ShHolidayProductService;
import com.lvmama.shholiday.response.ProductDetailResponse;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration( { "classpath:applicationContext-passport-beans.xml" })
public class ProductDetailRequestTest {
	Log log = LogFactory.getLog(ProductDetailRequestTest.class);
	@Autowired 
	ShholidayClient shholidayClient;
	@Autowired
	ShHolidayProductService shholidayProductService;
	@Test
	public void test() throws Exception {
		Date date =DateUtil.getDayStart(new Date());
		Date end = DateUtils.addMonths(date, 1);
	/*	try {
			shholidayProductService.updateAllProductTimePrices(date, end);
		} catch (Exception e) {
			e.printStackTrace();
		}*/
		
		/*Long time = System.currentTimeMillis();
		shholidayProductService.saveMetaProductForUnStocked("13692852");
		Long timeOut= System.currentTimeMillis()-time;
		log.info("timeOut:" +timeOut);*/
		String productId = "13752729";
//		shholidayProductService.saveMetaProductForUnStocked(productId);
		
		
		/*ProductInfoListReqeust request = new ProductInfoListReqeust("", "", 10, 1);
		ProductInfoListResponse res = shholidayClient.execute(request);
		List<ProductInfo> productinfoList = res.getProductInfos();
		for(ProductInfo p : productinfoList){
			log.info("productId"+p.getSupplierProdId());
			log.info("productName"+p.getSupplierProdName());
		}*/
//		http://super.lvmama.com/passport/shholiday/updateProductInfo.do?productId=13582874
		/*ProductDetailRequest request = new ProductDetailRequest("13752729", date, end);
//		ProductInfoListReqeust request = new ProductInfoListReqeust("", "", "team", 10, 1);
		ProductDetailResponse res = shholidayClient.execute(request);*/
/*		if(res.isSuccess()){
			
			List<ViewContent> contentList = res.getProductInfo().getContents();
			for (ViewContent content : contentList ) {
				if (Constant.VIEW_CONTENT_TYPE.FEATURES.name().equals(content.getContentType())
						|| Constant.VIEW_CONTENT_TYPE.INTERIOR.name().equals(content.getContentType())
						|| Constant.VIEW_CONTENT_TYPE.SHOPPINGEXPLAIN.name().equals(content.getContentType())
						|| Constant.VIEW_CONTENT_TYPE.SERVICEGUARANTEE.name().equals(content.getContentType())
						|| Constant.VIEW_CONTENT_TYPE.TRAFFICINFO.name().equals(content.getContentType())
						|| Constant.VIEW_CONTENT_TYPE.NOCOSTCONTAIN.name().equals(content.getContentType())
						|| Constant.VIEW_CONTENT_TYPE.RECOMMENDPROJECT.name().equals(content.getContentType())
						|| Constant.VIEW_CONTENT_TYPE.ORDERTOKNOWN.name().equals(content.getContentType())
						|| Constant.VIEW_CONTENT_TYPE.PLAYPOINTOUT.name().equals(content.getContentType())
						|| Constant.VIEW_CONTENT_TYPE.ACITONTOKNOW.name().equals(content.getContentType())
						) {
					content.setContent(null);
				}
			}

		}*/
	for(Constant.SH_HOLIDAY_BRANCH_TYPE branchType : Constant.SH_HOLIDAY_BRANCH_TYPE.values()){
		shholidayProductService.updateProductTimePrice(productId, branchType.name(), date, end);
	}
	}
	
	
	@Test
	public void testNotify() throws Exception{
		String url = "http://localhost/passport/shholiday/notify.do";
		String str = this.getXmlStr("ProductNotify.xml");
		Map<String,String> param = new HashMap<String, String>();
		param.put("messageXML", str);
		com.lvmama.passport.utils.HttpsUtil.requestPostForm(url, param);
	}
	
	String getXmlStr(String fileName) throws Exception{
		InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("com/lvmama/shholiday/request/"+fileName);
		SAXReader reader = new SAXReader();
		org.dom4j.Document document = reader.read(inputStream);
		return document.asXML();
	}
	
	/**
	 * 同步产品
	 * @param args
	 * @throws InterruptedException
	 */
	public static void main(String[] args) throws InterruptedException {
		String[] supplierProdId = new String[]{"13686244"};
		System.out.println(supplierProdId.length);
		int i = 1;
		for(String a : supplierProdId){
			System.out.println("run Times: " + i++);
			Map<String, String> requestParas = new HashMap<String, String>();
			requestParas.put("productId", a);
			String result = com.lvmama.passport.utils.HttpsUtil.requestPostForm("http://super.lvmama.com/passport/shholiday/updateProductInfo.do", requestParas );
			System.out.println("supProductId:" + a +"    result:" + result);
			Thread.sleep(20000L);
		}
	}
	
}
