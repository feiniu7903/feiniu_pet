package com.lvmama.pet.mobile.dao;

import java.util.List;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.pet.po.mobile.UserCreditCard;

public class UserCreditCardDAO extends BaseIbatisDAO {

    public UserCreditCardDAO() {
        super();
    }

    public void insert(UserCreditCard record) {
        super.insert("USER_CREDIT_CARD.insert", record);
    }

    public void insertSelective(UserCreditCard record) {
    	super.insert("USER_CREDIT_CARD.insertSelective", record);
    }
    
    @SuppressWarnings("unchecked")
	public List<UserCreditCard> selectUserCreditCardByUserId(String userId) {
		List<UserCreditCard> creditCards = (List<UserCreditCard>)super.queryForList("USER_CREDIT_CARD.selectUserCreditCardByUserId", userId);
    	return creditCards;
    }
    
	public UserCreditCard selectUserCreditCard(UserCreditCard userCreditCard) {
		UserCreditCard creditCard = (UserCreditCard) super.queryForObject("USER_CREDIT_CARD.selectUserCreditCard", userCreditCard);
    	return creditCard;
    }
    
}