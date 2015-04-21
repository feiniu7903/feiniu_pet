package com.lvmama.passport.processor;

import com.lvmama.comm.bee.po.pass.PassCode;
import com.lvmama.comm.bee.vo.Passport;

public interface ApplyCodeProcessor extends Processor{

	Passport apply(PassCode passCode);
	
}
