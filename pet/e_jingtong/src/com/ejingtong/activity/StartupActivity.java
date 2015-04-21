package com.ejingtong.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;

import com.ejingtong.R;

public class StartupActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_startup);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
//		getMenuInflater().inflate(R.menu.activity_startup, menu);
		return true;
	}

}
