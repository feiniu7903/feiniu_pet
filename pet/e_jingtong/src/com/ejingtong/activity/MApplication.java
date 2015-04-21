package com.ejingtong.activity;

import com.ejingtong.help.DatabaseHelperOrmlite;

import android.app.Application;

public class MApplication extends Application {
	
	private DatabaseHelperOrmlite databaseHelper = null;

	public DatabaseHelperOrmlite getDatabaseHelper() {
		return databaseHelper;
	}

	public void setDatabaseHelper(DatabaseHelperOrmlite databaseHelper) {
		this.databaseHelper = databaseHelper;
	}
	
}
