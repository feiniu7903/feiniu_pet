
package com.lvmama.tnt.partner.hotel;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.lvmama.tnt.partner.comm.RequestVO;
import com.lvmama.tnt.partner.comm.ResponseVO;
import com.lvmama.tnt.partner.hotel.service.ProductService;
import com.lvmama.vst.api.hotel.prod.vo.ProductVo;



@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration({"classpath:applicationContext-test-api-remote.xml"})
@ContextConfiguration({"classpath:applicationContext.xml"})
public class ServiceAPITest {

	@Autowired
	ProductService productService;
	
//	@Autowired
//	com.lvmama.comm.pet.service.shop.ShopProductService shopProductService;
	
	
	@Test
	public void testProductService(){
		RequestVO<Long> prodIdInfo = new RequestVO<Long>();
		prodIdInfo.setBody(180L);
		ResponseVO<ProductVo> response = productService.findProductDetail(prodIdInfo);
		
		System.out.println(response);
		
//		ShopProduct prd=shopProductService.queryByPk(13146L);
//		System.out.println(prd);
	}
	
}
