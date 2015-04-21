package com.lvmama.clutter.service;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSONArray;

/**    
 * @Title: ClientProductServiceTest.java
 * @Package com.lvmama.clutter.service
 * @Description: TODO
 * @author jiangzhihu
 * @date 2014-4-4 下午5:53:22
 * @version V1.0.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext-clutter-beans.xml")
public class ClientProductServiceTest {
	private final Logger logger = Logger.getLogger(this.getClass());
	@Autowired
	private IClientProductService api_com_product_5_0_1;
	@Test
	public void testGetGroupOnList() {
		Map<String,Object> param = new HashMap<String, Object>();
		param.put("page", 1);
		Map<String, Object> resultMap = api_com_product_5_0_1.getGroupOnList(param);
		logger.info(JSONArray.toJSON(resultMap));
	}

}
