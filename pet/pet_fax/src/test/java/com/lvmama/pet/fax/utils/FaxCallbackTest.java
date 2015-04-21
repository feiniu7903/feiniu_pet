package com.lvmama.pet.fax.utils;

import org.junit.Test;

import com.lvmama.pet.fax.daemon.TrafaxSendDaemon;

public class FaxCallbackTest {
	
	@Test
	public void testCallback(){
		try{
			TrafaxSendDaemon daemon = new TrafaxSendDaemon();
			daemon.notify("NEW721124", "25", "721124");
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
