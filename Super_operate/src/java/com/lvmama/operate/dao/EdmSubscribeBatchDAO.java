package com.lvmama.operate.dao;
import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.pet.po.edm.EdmSubscribeBatch;
/**
 * EDM邮件发送批次信息持久类
 * @author shangzhengyuan
 * @createDate 20110922
 */

public class EdmSubscribeBatchDAO extends BaseIbatisDAO {
	private static final String EDM_SUBSCRIBE_BATCH_SPACE = "EDM_SUBSCRIBE_BATCH.";
	private static final String INSERT = EDM_SUBSCRIBE_BATCH_SPACE + "insert";
	public EdmSubscribeBatch insert(EdmSubscribeBatch obj){
		super.insert(INSERT, obj);
		return obj;
	}
}
