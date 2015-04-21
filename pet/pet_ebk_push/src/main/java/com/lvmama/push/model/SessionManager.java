/*
 * Copyright (C) 2010 Moduad Co., Ltd.
 * 
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along
 * with this program; if not, write to the Free Software Foundation, Inc.,
 * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 */
package com.lvmama.push.model;

import java.util.HashMap;
import java.util.Map;

import org.apache.mina.core.session.IoSession;

/** 
 * This class manages the sessions connected to the server.
 *
 * @author Sehwan Noh (devnoh@gmail.com)
 */
public class SessionManager {

	
	private static SessionManager instance;
	
	private Map<String,ClientSessionInfo> sessionMap = new HashMap<String, ClientSessionInfo>();
	
	 /**
     * Returns the singleton instance of SessionManager.
     * 
     * @return the instance
     */
    public static SessionManager getInstance() {
        if (instance == null) {
            synchronized (SessionManager.class) {
                instance = new SessionManager();
            }
        }
        return instance;
    }
    
    public void putSession(String udid,ClientSessionInfo session){
    	sessionMap.put(udid, session);
    }
    
    public Map<String,ClientSessionInfo> getSessions(){
    	return this.sessionMap;
    }
    
    public void removeSession(String udid){
    	sessionMap.remove(udid);
    }

    
    
}
