package com.lvmama.sso.auth;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import com.lvmama.comm.pet.po.user.UserUser;

/** Interface for password-based authentication handlers. */
public interface PasswordHandler extends AuthHandler {

  /**
   * Authenticates the given username/password pair, returning true
   * on success and false on failure.
   * @param request request
   * @param username username
   * @param password password
   * @return UsrUsers
   */
	UserUser authenticate(ServletRequest request, ServletResponse response, String username, String password,String channel);

}
