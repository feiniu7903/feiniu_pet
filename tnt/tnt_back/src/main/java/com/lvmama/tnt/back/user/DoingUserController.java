package com.lvmama.tnt.back.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.lvmama.tnt.comm.util.UserClient;
import com.lvmama.tnt.comm.vo.TntConstant;
import com.lvmama.tnt.comm.vo.TntConstant.USER_IDENTITY_TYPE;
import com.lvmama.tnt.user.po.TntUser;
import com.lvmama.tnt.user.po.TntUserDetail;

@Controller
@RequestMapping("/user/doing")
public class DoingUserController extends AbstractUserController {

	@Autowired
	private UserClient tntUserClient;

	@Override
	protected String getStatus() {
		return TntConstant.USER_FINAL_STATUS.DOING.getValue();
	}

	/** 设置合同日期 **/
	@RequestMapping(method = RequestMethod.PUT)
	public String setContractDate(TntUser tntUser) {
		TntUserDetail t = tntUser.getDetail();
		getTntUserService().setContractDate(t);
		return "redirect:doing";
	}

	/** 终止合作 **/
	@RequestMapping(method = RequestMethod.DELETE)
	public String endContract(TntUser tntUser) {
		TntUserDetail t = tntUser.getDetail();
		try {
			if (getTntUserService().endContract(tntUser.getUserId(),
					t.getFailReason())) {
				TntUser tu = getTntUserService().findWithDetailByUserId(
						tntUser.getUserId());
				tntUserClient
						.sendAuthenticationCode(
								USER_IDENTITY_TYPE.EMAIL,
								tu,
								TntConstant.EMAIL_SSO_TEMPLATE.REFUSED_COOPERATE
										.name());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:doing";
	}

	@Override
	protected String getPage() {
		return "doing";
	}
}
