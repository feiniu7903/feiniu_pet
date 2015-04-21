package com.lvmama.clutter.service.client.v5_0;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @Title: ClientUserServiceV50Test.java
 * @Package com.lvmama.clutter.service.client.v5_0
 * @Description: TODO
 * @author jiangzhihu
 * @date 2014-3-19 下午2:26:54
 * @version V1.0.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext-clutter-beans.xml")
public class ClientUserServiceV50Test {

	@Autowired
	private ClientUserServiceV50 api_com_user_5_0_0;

	@Test
	public void testCommitOrderComment() throws Exception {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put(
				"latitudeInfo",
				"FFFFFFFFFFFFFFFFFFFFFFFFFFFF_5,40289ed133edcf720133edcf727a001c_2,40289ed133edcf720133edcf727b001d_3,40289ed133edcf720133edcf727b001e_4,40289ed133edcf720133edcf727b001f_5");
		param.put("objectId", 1336544);
		param.put("content", "test");
		param.put("longitude", 121.382484);
		param.put("latitude", 31.237921);
		param.put("userNo", "5ad32f1a1ccdf220011ccfc96dd80014");
		param.put("imageUrls",
				new String[] { "/uploads/comment/IMG_20130529_214857.JPEG" });
		// param.put(key, value);
		api_com_user_5_0_0.commitOrderComment(param);
	}

}
