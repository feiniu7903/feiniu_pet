package com.ejingtong.help;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface.OnCancelListener;
import android.view.Gravity;

import com.ejingtong.R;


public class ProgressDialogHelper {

	private static ProgressDialog mProgressDialog;
	
	public static void show(Activity activity, String title, boolean isCancable, OnCancelListener listener){
//		if(mProgressDialog == null){
			mProgressDialog = new ProgressDialog(activity);
			mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			mProgressDialog.setIndeterminateDrawable(activity.getResources()
					.getDrawable(R.anim.loading));

			

			mProgressDialog.setIndeterminate(true);
			
			mProgressDialog.getWindow().setGravity(Gravity.CENTER_VERTICAL);

//		}
		mProgressDialog.setCancelable(isCancable);
		if(title != null){
			mProgressDialog.setMessage(title);
		}
		
		
		if(listener != null){
			mProgressDialog.setOnCancelListener(listener);
		}
		
		
		if(mProgressDialog.isShowing()){
			mProgressDialog.dismiss();
		}
		mProgressDialog.show();
	}

	public static void dismiss(){
		if(mProgressDialog != null && mProgressDialog.isShowing()){
			mProgressDialog.dismiss();
		}
	} 
}
