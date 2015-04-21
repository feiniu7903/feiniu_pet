package com.lvmama.passport.processor;

import com.lvmama.comm.bee.po.pass.PassCode;
import com.lvmama.comm.bee.vo.Passport;

public interface UpdateContentProcessor extends Processor {

	Passport update(PassCode passCode);
	
}
