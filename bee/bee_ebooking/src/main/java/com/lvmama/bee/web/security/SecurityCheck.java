package com.lvmama.bee.web.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface SecurityCheck {
	CheckResult check(HttpServletRequest request,HttpServletResponse response);
}
