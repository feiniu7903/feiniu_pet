package com.ejingtong.help;

import java.io.File;
import java.io.InputStream;
import java.sql.SQLException;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.os.Environment;
import android.util.Log;

import com.ejingtong.common.EjingTongException;
import com.ejingtong.model.OrderHead;
import com.ejingtong.model.OrderMeta;
import com.ejingtong.model.OrderPerson;
import com.ejingtong.model.OrderProduct;
import com.ejingtong.model.PushResponseData;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

public class DatabaseHelperOrmlite extends OrmLiteSqliteOpenHelper {
	
	private static final String TAG = "DatabaseHelperOrmlite";
	//private static final String DATABASE_NAME= Environment.getExternalStorageDirectory()+ "/e_jing_tong.db3";
	private static final String DATABASE_NAME =  "e_jing_tong.db3";
	private static final int DATABASE_VERSION = 1;

	public DatabaseHelperOrmlite(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	
	public DatabaseHelperOrmlite(Context context, String databaseName,
			CursorFactory factory, int databaseVersion) {
		super(context, DATABASE_NAME, factory, DATABASE_VERSION);
	}

	public DatabaseHelperOrmlite(Context context, String databaseName,
			CursorFactory factory, int databaseVersion, int configFileId) {
		super(context, DATABASE_NAME, factory, DATABASE_VERSION, configFileId);
	}

	public DatabaseHelperOrmlite(Context context, String databaseName,
			CursorFactory factory, int databaseVersion, File configFile) {
		super(context, DATABASE_NAME, factory, DATABASE_VERSION, configFile);
	}

	public DatabaseHelperOrmlite(Context arg0, String arg1, CursorFactory arg2,
			int arg3, InputStream arg4) {
		super(arg0, arg1, arg2, arg3, arg4);
	}

	/**
	 * 数据库创建的时候调用此方法，通常在此方法里面创建表
	 */
	@Override
	public void onCreate(SQLiteDatabase arg0, ConnectionSource arg1){
		try {
			TableUtils.createTable(arg1, OrderHead.class);
			TableUtils.createTable(arg1, OrderMeta.class);
			TableUtils.createTable(arg1, OrderPerson.class);
			//TableUtils.createTable(arg1, PushResponseData.class);
		} catch (SQLException e) {
			Log.e(TAG, "创建表失败");
			throw new EjingTongException("数据库创建失败", e);
		}
	}

	/**
	 * 数据库更新、升级时调用此方法
	 */
	@Override
	public void onUpgrade(SQLiteDatabase arg0, ConnectionSource arg1, int arg2,
			int arg3) {
		// TODO Auto-generated method stub
	}

	public Dao<OrderHead, Integer> getOrderHeadDao(){
		Dao<OrderHead, Integer> orderHeadDao;
		try {
			orderHeadDao = getDao(OrderHead.class);
		} catch (SQLException e) {
			return null;
		}
		return orderHeadDao;
	}
	
	public Dao<OrderMeta, Integer> getOrderMetaDao(){
		Dao<OrderMeta, Integer> orderMeatDao;
		try {
			orderMeatDao = getDao(OrderMeta.class);
		} catch (SQLException e) {
			return null;
		}
		return orderMeatDao;
	}
	
	public Dao<OrderPerson, Integer> getOrderPersonDao(){
		Dao<OrderPerson, Integer> orderPersonDao;
		try {
			orderPersonDao = getDao(OrderPerson.class);
		} catch (SQLException e) {
			return null;
		}
		return orderPersonDao;
	}
	
	public Dao<PushResponseData, Integer> getPushResponseDataDao(){
		Dao<PushResponseData, Integer> pushDataDao;
		try {
			pushDataDao = getDao(PushResponseData.class);
		} catch (SQLException e) {
			return null;
		}
		return pushDataDao;
	}
	
}
