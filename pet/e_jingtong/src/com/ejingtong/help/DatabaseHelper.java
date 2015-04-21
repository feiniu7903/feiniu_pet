package com.ejingtong.help;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;

public class DatabaseHelper extends SQLiteOpenHelper {
	private static String DATABASENAME = "e_jing_tong.db3";
	private static final int DATABASEVERSION = 1;

	public DatabaseHelper(Context context) {
		super(context, DATABASENAME, null, DATABASEVERSION);
	}
	 
	public DatabaseHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
	}
	
	public DatabaseHelper(Context context, String name, CursorFactory factory){
		this(context,name,null,DATABASEVERSION);
	}
	
	public DatabaseHelper(Context context,String name){
		this(context,name,null);
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		createOrderHeadTable(db);
		createOrderMeta(db);
		createOrderPerson(db);
		createOrderProduct(db);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}

	public boolean deleteDatabase(Context context) {
		return context.deleteDatabase(DATABASENAME);
	}
	
	public static void setCurrentDataBase(String databaseName){
		DATABASENAME = databaseName;
	}	
	
	//创建订单基本信息表
	private void createOrderHeadTable(SQLiteDatabase db){
		String strSql = "CREATE TABLE IF NOT EXISTS order_head (" + 
						"order_id INTEGER NOT NULL PRIMARY KEY," + 
						" order_view_status VARCHAR(20)," + 
						" perform_status VARCHAR(20)" + 
						")";
		
		db.execSQL(strSql);
	}
	
	//创建采购产品信息表	
	private void createOrderMeta(SQLiteDatabase db){
		String strSql = "CREATE TABLE IF NOT EXISTS order_meta (" + 
						" primary_id INTEGER PRIMARY KEY," +
						" order_id INTEGER" + 
						" product_name VARCHAR(100)," + 
						" sell_price INTEGER" + 
						")";
		db.execSQL(strSql);
		
	}
	
	//创建产品信息表
	private void createOrderProduct(SQLiteDatabase db){
		String strSql = "CREATE TABLE IF NOT EXISTS order_product (" +
						" primary_id INTEGER PRIMARY KEY," +
						" order_id INTEGER" + 
						" quantity INTEGER" + 
						")";
		
		db.execSQL(strSql);
	}
	
	//创建产品信息表
	private void createOrderPerson(SQLiteDatabase db){
		String strSql = "CREATE TABLE IF NOT EXISTS order_person (" +
				"primary_id INTEGER PRIMARY KEY," +
				" order_id INTEGER," +
				" name VARCHAR(50)," + 
				" mobile VARCHAR(20)," +
				" zh_cert_type VARCHAR(50)," + 
				" cert_type VARCHAR(50)," + 
				" cert_no VARCHAR(50)" + 
				")";

		db.execSQL(strSql);
	}
	
	
}
