package com.lvmama.sso.auth;

/** Interface for server-based authentication handlers. */
public interface TrustHandler extends AuthHandler {

  /**
   * Allows arbitrary logic to compute an authenticated user from
   * a ServletRequest.
   * @param request request
   * @return username
   */
  String getUsername(javax.servlet.ServletRequest request);

}
