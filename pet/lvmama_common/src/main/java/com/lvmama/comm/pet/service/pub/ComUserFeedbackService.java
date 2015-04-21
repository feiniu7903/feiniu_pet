/**
 * 
 */
package com.lvmama.comm.pet.service.pub;

import java.util.List;

import com.lvmama.comm.pet.po.pub.ComUserFeedback;


/**
 * @author yangbin
 *
 */
public interface ComUserFeedbackService {

	List<ComUserFeedback> queryFeedBackByParams(ComUserFeedback feedBack,
			int beignIndex, int endIndex);

	Long queryFeedBackCountByParams(ComUserFeedback feedBack);

	List<String> getFeedBackTypes();

	List<String> getFeedBackStateIds();

	void update(ComUserFeedback feedBack);

	ComUserFeedback getFeedBackByKey(Long userFeedbackId);
	
	
	/**
	 * 保存用户反馈
	 * @param feedBack
	 */
	void saveUserFeedBack(ComUserFeedback feedBack);
}
