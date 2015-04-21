package com.lvmama.pet.fax.servlet;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.pet.fax.utils.Constant;

public class FaxWriter {
	private static final Log log = LogFactory.getLog(FaxWriter.class);

	private static String[] config1={	
		"[General]\n",
		"Account = test\n",
		 "Subject = \n",
		 "Comments = \n",
		 "FaxFlowAccounts = \n"
	};
	
	//		 "SerialNO = 1003\n",
	private static String[] config2={	
		 "Priority = 1 \n",
		 "EmailResult = \n",
		 "ReceiverList = \n"		 
	};
	
	//"ReceiverNumber_1= 88889797 \n",
	private static String[] config3={
		 "ReceiverName_1= \n",
		 "ReceiverCompany_1 = \n",
		 "EastFax = Yes"		
	};
	
	public void write(Map<String, String> map, File file) {
		String telephone = map.get("telephone");
		String serialno = map.get("serialno");
		if (telephone!=null && file.exists()) {
			try {
				File dest = new File(Constant.getFaxDirectory()+"/"+serialno+"_1.xls");
				log.info("Moved FAX FILE TO : " + dest);
				file.renameTo(dest);
				writeEpi(serialno, telephone);
			}catch(IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void writeEpi(String serialNo, String telephone) throws IOException {
		File epiFile=new File(Constant.getFaxDirectory()+"/"+serialNo+".epi");
		log.info("Saved EPI FILE : "+epiFile.getAbsolutePath());
		StringBuffer strBuff=new StringBuffer();
		for(int i=0;i<config1.length;i++){
			strBuff.append(config1[i]);
		}
		strBuff.append("SerialNO = "+serialNo+"\n");
		for(int i=0;i<config2.length;i++){
			strBuff.append(config2[i]);
		}
		strBuff.append("ReceiverNumber_1= "+telephone+"\n");
		for(int i=0;i<config3.length;i++){
			strBuff.append(config3[i]);
		}
		
		BufferedWriter bw=new BufferedWriter(new OutputStreamWriter(new FileOutputStream(epiFile)));
		bw.write(strBuff.toString());
		bw.close();
	}
}
