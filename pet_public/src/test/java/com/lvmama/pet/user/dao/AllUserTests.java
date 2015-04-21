package com.lvmama.pet.user.dao;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ UserActionCollectionDAOTest.class,
		UserCooperationUsersDAOTest.class, UserEmailCodeDAOTest.class,
		UserGradeLogDAOTest.class, UserMobileCodeDAOTest.class,
		UserPasswordRecallDAOTest.class, UserPointLogDAOTest.class,
		UserPointRulesDAOTest.class, UserUserDAOTest.class })
public class AllUserTests {

}
