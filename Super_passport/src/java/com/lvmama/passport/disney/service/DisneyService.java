package com.lvmama.passport.disney.service;

import java.util.List;

import com.lvmama.comm.pet.vo.EmailAttachmentData;
import com.lvmama.passport.disney.model.MailBean;
import com.lvmama.passport.disney.model.OrderRespose;

public interface DisneyService {
	public void sendMail(MailBean bean,List<EmailAttachmentData> files) ;
	
	public OrderRespose getOrderStatus(String reservationNo)throws Exception;
}
