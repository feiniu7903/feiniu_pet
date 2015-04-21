package com.ejingtong.uitls;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Callable;

import com.ejingtong.help.DatabaseHelperOrmlite;
import com.ejingtong.help.OrderManage;
import com.ejingtong.model.Order;
import com.j256.ormlite.misc.TransactionManager;

public class Order2DbRunnalbe implements Runnable{
	LinkedList<List<Order>> orderQueue = new LinkedList<List<Order>>();
	public static Order2DbRunnalbe instance;
	
	public static Order2DbRunnalbe getInstance(){
		if(instance==null){
			synchronized (Order2DbRunnalbe.class) {
				 instance = new Order2DbRunnalbe(); 
			}
		}
		return instance;
	}
	
	
	
	
	@Override
	public void run() {
		while(true){
			if(!orderQueue.isEmpty()){
				// TODO Auto-generated method stub
				try {
					final DatabaseHelperOrmlite helper = OrderManage.getDefaultDbHelper();
					
					TransactionManager.callInTransaction(helper.getConnectionSource(), new Callable<Void>() {
						@Override
						public Void call() throws Exception {
							OrderManage.getInstance().saveOrUpdateOrders(orderQueue.poll());
							return null;
						}
					});
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
	}
	public LinkedList<List<Order>> getOrderQueue() {
		return orderQueue;
	}
	public void setOrderQueue(LinkedList<List<Order>> orderQueue) {
		this.orderQueue = orderQueue;
	}

}
