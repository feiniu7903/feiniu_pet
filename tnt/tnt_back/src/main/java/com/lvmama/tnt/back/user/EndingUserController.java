package com.lvmama.tnt.back.user;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lvmama.tnt.comm.vo.TntConstant;

@Controller
@RequestMapping("/user/ending")
public class EndingUserController extends AbstractUserController {

	@Override
	protected String getStatus() {
		return TntConstant.USER_FINAL_STATUS.END.getValue();
	}

	@Override
	protected String getPage() {
		return "ending";
	}

	/** 重新合作 **/
	@RequestMapping("repeatDo/{userId}")
	public void repeatDo(@PathVariable Long userId, HttpServletResponse response) {
		boolean flag = getTntUserService().repeatDoing(userId);
		PrintWriter writer = null;
		try {
			writer = response.getWriter();
			writer.print(flag);
			writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (writer != null) {
				writer.close();
			}
		}
	}

}
