/**
 * 
 */
package com.lvmama.pet.user.dao;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.lvmama.comm.pet.po.user.UserUser;
import com.lvmama.pet.conn.BaseDAOTest;

/**
 * @author liuyi
 *
 */
@ContextConfiguration(locations = { "classpath:applicationContext-pet-public-beans.xml" })
@Transactional 
@TransactionConfiguration(transactionManager = "txManager", defaultRollback = true)
public class UserUserDAOTest extends BaseDAOTest {
	
	@Autowired
	private UserUserDAO userUserDAO;
	
	@Test
	public void testSave() throws Exception {
		UserUser user = new UserUser();
		user.setUserNo("pettest8a62364c9b2f01364c9b2f0b0");
		user.setCityId(null);
		user.setUserName("pettest001");
		user.setPasswordPrompt("什么是PET");
		user.setPasswordAnswer("PET是个新项目");
		user.setRealName("pet");
		user.setAddress("驴妈妈");
		user.setCreatedDate(new Date());
		user.setUpdatedDate(null);
		user.setIsValid("Y");
		user.setMobileNumber("13701930870");
		user.setEmail("libo.wang.vip@gmail.com");
		user.setGender("M");
		user.setIdNumber(null);
		user.setPoint(100l);
		user.setNickName("pettest001");
		user.setMemo(null);
		user.setImageUrl("/uploads/header/user_pic_6.jpg");
		user.setIsEmailChecked("Y");
		user.setRealPass("123456");
		user.setPhoneNumber(null);
		user.setIsAcceptEdm("Y");
		user.setIsMobileChecked("N");
		user.setMemberShipCard(null);
		user.setChannel("PAGEREG");
		user.setGrade("PLATINUM");
		userUserDAO.save(user);
		
		Assert.assertNotNull(user.getUserId());

		Statement stat = conn.createStatement();
		ResultSet rs = stat.executeQuery("select * from user_user where user_no = '" + user.getUserNo() + "'");
		rs.next();

		Assert.assertEquals("user_name", rs.getString("USER_NAME"));
		Assert.assertEquals("user_password", rs.getString("USER_PASSWORD"));
		Assert.assertEquals("is_email_checked", rs.getString("IS_EMAIL_CHECKED"));
		
	}

}
