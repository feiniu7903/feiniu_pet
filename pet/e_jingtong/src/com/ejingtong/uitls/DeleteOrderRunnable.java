package com.ejingtong.uitls;

import java.sql.SQLException;
import java.util.concurrent.Callable;

import com.ejingtong.help.DatabaseHelperOrmlite;
import com.ejingtong.help.OrderManage;
import com.ejingtong.model.Order;
import com.ejingtong.model.OrderMeta;
import com.ejingtong.model.OrderPerson;
import com.ejingtong.model.PushResponseData;
import com.j256.ormlite.misc.TransactionManager;

public class DeleteOrderRunnable implements Runnable{
	private PushResponseData data;
	public DeleteOrderRunnable(PushResponseData data){
		this.data = data;
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
				try {
					final DatabaseHelperOrmlite helper = OrderManage.getDefaultDbHelper();
					
					TransactionManager.callInTransaction(helper.getConnectionSource(), new Callable<Void>() {
						@Override
						public Void call() throws Exception {
							OrderManage.getInstance().deleteOrder(data);
							return null;
						}
					});
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	}

}
