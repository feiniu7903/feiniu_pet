package com.ejingtong.help;

import java.sql.SQLException;
import java.sql.Savepoint;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import android.content.Context;

import com.ejingtong.model.Order;
import com.ejingtong.model.OrderHead;
import com.ejingtong.model.OrderMeta;
import com.ejingtong.model.OrderPerson;
import com.ejingtong.model.PushResponseData;
import com.ejingtong.model.ResponseData;
import com.j256.ormlite.android.AndroidDatabaseConnection;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.misc.TransactionManager;
import com.j256.ormlite.stmt.PreparedQuery;

public class OrderManage {
	private final String TAG = "OrderManage";

	private  Context mContext;
	
	private  static DatabaseHelperOrmlite DBHelper;
	private static AndroidDatabaseConnection dbConnection;

	
	private static OrderManage instance;
	
	public static OrderManage getInstance(Context context){
		if(instance == null){
			synchronized (OrderManage.class) {
				instance = new OrderManage(context);
			}
		}
		return instance;
	}
	
	public static DatabaseHelperOrmlite getDefaultDbHelper(){
		return DBHelper;
	}
	
	public static AndroidDatabaseConnection getDataBaseConnection(){
		return dbConnection;
	}
	
	private  OrderManage(Context context){
		mContext = context;
		DBHelper = OpenHelperManager.getHelper(mContext, DatabaseHelperOrmlite.class);
	}
	
	public static OrderManage getInstance(){
		return instance;
	}
	
	public Order searchOrder(int addCode){
		Order order = new Order();
		
		List<OrderHead> orderHeads;
		try {
			
			if(!DBHelper.isOpen()){
				DBHelper.onOpen(DBHelper.getReadableDatabase());
			}
			
			orderHeads = DBHelper.getOrderHeadDao().queryForEq("ADD_CODE", addCode);
			OrderHead orderHead = null;
			if(orderHeads == null || orderHeads.size() < 1){
//				如果本地数据库没有查到数据
				order = null;
				return order;
			}else{
				orderHead = orderHeads.get(0);
			}
			
			Map<String, Object> queryParameter = new HashMap<String, Object>();
			queryParameter.put("ORDER_ID", orderHead.getOrderId());
			
			List<OrderMeta> orderMetas = DBHelper.getOrderMetaDao().queryForFieldValues(queryParameter);
			List<OrderPerson> orderPersons = DBHelper.getOrderPersonDao().queryForFieldValues(queryParameter);
			order.setBaseInfo(orderHead);
			order.setMetas(orderMetas);
			order.setPersons(orderPersons);
		} catch (SQLException e) {
			order = null;
			e.printStackTrace();
		}
		
		return order;
	}
	
	
	
	//删除订单
	public void deleteOrder(PushResponseData data){
		// 删除订单
		
		if(data == null){
			return;
		}
		
		try {
			PreparedQuery<OrderHead> prepareQuery = DBHelper.getOrderHeadDao().queryBuilder().where().between("VISIT_END", "0000-00-00 00:00:00", data.getDateStr()).prepare(); 
			List<OrderHead> orders = DBHelper.getOrderHeadDao().query(prepareQuery);
			for(OrderHead orderHead : orders){
				DBHelper.getOrderMetaDao().executeRaw("DELETE FROM ORDER_ITEM WHERE ORDER_ID = ?", orderHead.getOrderId() + "");
				DBHelper.getOrderPersonDao().executeRaw("DELETE FROM ORDER_PERSON WHERE ORDER_ID = ?", orderHead.getOrderId() + "");
				DBHelper.getOrderHeadDao().delete(orderHead);
			}
			DBHelper.getOrderHeadDao().commit(dbConnection);
			DBHelper.getOrderMetaDao().commit(dbConnection);
			DBHelper.getOrderPersonDao().commit(dbConnection);
			prepareQuery = null;
			orders = null;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	//保存订单
	public void saveOrder(PushResponseData data){
		
	}
	
	
	public void saveOrUpdateOrders(final List<Order> orders) throws SQLException{

	try {
		for (Order order : orders) {
			Dao<OrderHead, Integer> dao = DBHelper.getOrderHeadDao();

			dao.createOrUpdate(order.getBaseInfo());

			//保存metas
			saveMetas(order.getMetas());
			
			//保存persons
			
			savePersons(order.getPersons());
		}
		} catch (Exception ex){
			ex.printStackTrace();
		}
		
	}
	
	public void save2Database(ResponseData data) {
		
//		String time = Tools.getFormateDate("yyyy-MM-dd HH:mm:ss");
		try {
			// 保存订单基本信息(baseinfo)
			OrderHead baseInfo = null;
			
			for (Order order : data.getDatas()) {
				baseInfo = order.getBaseInfo();
				
				Dao<OrderHead, Integer> dao = DBHelper.getOrderHeadDao();
				dao.create(baseInfo);
				
				//保存metas
				saveMetas(order.getMetas());
				
				//保存persons
				
				savePersons(order.getPersons(), baseInfo.getOrderId());

			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	private void saveMetas(List<OrderMeta> metas) {
		if (null == metas) {
			return;
		}

		try {
			for (OrderMeta meta : metas) {
				meta.setRealQuantity(meta.getQuantity());
				DBHelper.getOrderMetaDao().createOrUpdate(meta);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void savePersons(List<OrderPerson> persons, long orderId){
		if(null == persons){
			return;
		}
		
		try {
			for(OrderPerson person : persons){
				person.setOrderId(orderId);
				DBHelper.getOrderPersonDao().createOrUpdate(person);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void savePersons(List<OrderPerson> persons){
		if(null == persons){
			return;
		}
		
		try {
			for(OrderPerson person : persons){
				DBHelper.getOrderPersonDao().createOrUpdate(person);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	//获取已经通关的订单的总数
	public long getUsedOrderCount() throws SQLException{
		PreparedQuery<OrderHead> prepareQuery = DBHelper.getOrderHeadDao().queryBuilder().setCountOf(true).where().eq("USED_STATUS", "USED").prepare();
		return DBHelper.getOrderHeadDao().countOf(prepareQuery);
	}
	
	//获取已经取消的订单的总数
	public long getCancleOrderCount() throws SQLException{
		PreparedQuery<OrderHead> prepareQuery = DBHelper.getOrderHeadDao().queryBuilder().setCountOf(true).where().eq("USED_STATUS", "CANCLED").prepare();
		return DBHelper.getOrderHeadDao().countOf(prepareQuery);
	}
	
	//获取未通关的订单的总数
	public long getUnUsedOrderCount() throws SQLException{
		PreparedQuery<OrderHead> prepareQuery = DBHelper.getOrderHeadDao().queryBuilder().setCountOf(true).where().eq("USED_STATUS", "UNUSED").prepare();
		return DBHelper.getOrderHeadDao().countOf(prepareQuery);
	}

	public DatabaseHelperOrmlite getDBHelper() {
		return DBHelper;
	}
}
