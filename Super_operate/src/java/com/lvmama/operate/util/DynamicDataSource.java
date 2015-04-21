package com.lvmama.operate.util;

import org.apache.log4j.Logger;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
 
public class DynamicDataSource extends AbstractRoutingDataSource {  
 
 static Logger log = Logger.getLogger("DynamicDataSource");  
 @Override 
 protected Object determineCurrentLookupKey() {  
  // TODO Auto-generated method stub  
  return DbContextHolder.getDbType();  
 }  
 
}  
