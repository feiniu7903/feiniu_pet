/**
 * 
 * UserCallTypeDAO
 * @author zhangzhenhua
 * @date 2011-05-16
 */
package com.lvmama.pet.conn.dao;

import java.util.List;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.pet.po.conn.ConnType;

public class ConnTypeDAO extends BaseIbatisDAO {

	 public List<ConnType> queryConnTypeByCallBack(String callBack){
		 return super.queryForList("CONN_TYPE.queryConnTypeByCallBack",callBack);
	 }
	 
	 public List<ConnType> queryConnTypeByConnType(ConnType connType){
		 return super.queryForList("CONN_TYPE.queryConnTypeByConnType",connType);
	 }
}