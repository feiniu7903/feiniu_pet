package com.lvmama.operate.job;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import org.apache.log4j.Logger;

import com.lvmama.comm.pet.client.FSClient;
import com.lvmama.operate.OrdEContract;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.operate.dao.OrdContractTempDAO;

public class OrdEContractTempJob {
	private final static Logger LOG = Logger.getLogger(OrdEContractTempJob.class);
	private OrdContractTempDAO ordContractTempDAO;
	private FSClient fsClient;
	private static int EACH_COUNT = 100;
	private static int ROW_NUM = 200;
	public void run(){
		try{
			 execute("ORD_ORDER_ROUTE_TRAVEL","travel.xml","updateOrderTravel");
		}catch(Exception e){
			LOG.error(" update contract content is error!"+e);
			e.printStackTrace();
		}
	}
	private void execute(final String tableName,final String fileName,final String sqlName) throws Exception{
		 Map<String,Object> parameters = new HashMap<String,Object>();
		 parameters.put("rn",ROW_NUM);
		 parameters.put("tableName",tableName);
		 
		 boolean firstExecute = false;
		 int count = EACH_COUNT;
		 List<OrdEContract> results=null;
		 do{ 
			 LOG.info(DateUtil.formatDate(new java.util.Date(), "yyyy-MM-dd hh:mm:ss")+" "+tableName);
			 results = ordContractTempDAO.selectTravel(parameters);
			 LOG.info(DateUtil.formatDate(new java.util.Date(), "yyyy-MM-dd hh:mm:ss")+" > "+results.size());
			 firstExecute = results.size()>0 && 0<count--;
			 if(!firstExecute)return;
			 Long fileId = null;
			 for(OrdEContract result:results){
				 byte[] content = null;
				 String travel = result.getComplementXml();
				 if(StringUtil.isEmptyString(travel)){
					 continue;
				 }
				 content = travel.getBytes("UTF-8");
				 if(null==content){
					 continue;
				 }
				 InputStream input = new ByteArrayInputStream(content);
				 fileId = fsClient.uploadFile(result.getOrderId()+"_"+fileName, input, "eContract",DateUtil.formatDate(new Date(), "yyyyMMddhhmm"));
				 input.close();
				 result.setFileId(fileId);
				 result.setContent(null);
				 content=null;
			 }
			 ordContractTempDAO.update(sqlName,results);
			 LOG.info(DateUtil.formatDate(new java.util.Date(), "yyyy-MM-dd hh:mm:ss")+" > end");
		 }while(firstExecute);
	}
	public void setFsClient(FSClient fsClient) {
		this.fsClient = fsClient;
	}
	public void setOrdContractTempDAO(OrdContractTempDAO ordContractTempDAO) {
		this.ordContractTempDAO = ordContractTempDAO;
	}
}
