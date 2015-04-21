package com.lvmama.pet.sms.dao;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ SmsContentDAOTest.class, SmsContentLogDAOTest.class,
		SmsInstructionDAOTest.class, SmsReceiveDAOTest.class })
public class AllSmsTests {

}
