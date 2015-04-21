package com.ejingtong.activity.login;


import java.io.File;
import java.lang.reflect.Type;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.util.List;

import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;

import com.ejingtong.R;
import com.ejingtong.activity.BaseActivity;
import com.ejingtong.activity.IndexTabActivity;
import com.ejingtong.activity.servise.MainServise;
import com.ejingtong.activity.setting.SettingActivity;
import com.ejingtong.common.Constans;
import com.ejingtong.help.DatabaseHelperOrmlite;
import com.ejingtong.help.OrderManage;
import com.ejingtong.help.SharedPreferenceHelper;
import com.ejingtong.http.ActionResponse;
import com.ejingtong.http.AsyncDownloadFile;
import com.ejingtong.http.ResClient;
import com.ejingtong.http.ServiceSyncListener;
import com.ejingtong.model.OrderHead;
import com.ejingtong.model.PushResponseData;
import com.ejingtong.model.UpdateInfo;
import com.ejingtong.model.User;
import com.ejingtong.push.ClientStart;
import com.ejingtong.uitls.Order2DbRunnalbe;
import com.ejingtong.uitls.MyThreadPoolExecutor;

import com.ejingtong.view.MDialog;
import com.ejingtong.view.ViewEditText;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.zxing.LuminanceSource;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.stmt.PreparedQuery;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/**
 * 登录界面
 */
public class LoginActivity extends BaseActivity implements OnClickListener{

	private final String KEY_USER_NAME = "key_user_name";
	private final String KEY_PASSWORD = "key_password";
	private final String KEY_IS_SAVE_PASSWORD = "key_is_save_password";
	private ProgressDialog pd;
	
	//用户名相关
	private ViewEditText layoutUserName;
	private String   userName;
	
	//密码相关
	private ViewEditText layoutPassWord;
	private String   password;

	private Button loginButton;

	private CheckBox savePassWordBtn;
	
	private boolean isSavePassWord = true;
	private UpdateInfo updateInfo;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
	
		OrderManage.getInstance(getApplicationContext());
		ClientStart.getInstance(getApplicationContext());
	
		isSavePassWord = SharedPreferenceHelper.getInstance(this, Constans.FILE_NAME_SETTING).getBoleanDefaultTrue(KEY_IS_SAVE_PASSWORD);
		
		Constans.ADDR_LOGIC = SharedPreferenceHelper.getInstance(this, Constans.FILE_NAME_SETTING).getStr(Constans.KEY_ADDR_LOGIC_VALUE, Constans.ADDR_LOGIC);
		Constans.ADDR_PUSH_BACK = SharedPreferenceHelper.getInstance(this, Constans.FILE_NAME_SETTING).getStr(Constans.KEY_ADDR_PUSH_CALLBACK_VALUE, Constans.ADDR_PUSH_BACK);
		
		Constans.ADDR_PUSH_SERVER = SharedPreferenceHelper.getInstance(this, Constans.FILE_NAME_SETTING).getStr(Constans.KEY_ADDR_PUSH_SERVER_VALUE, Constans.ADDR_PUSH_SERVER);
		
		Constans.PORT_PUSH_SERVER = SharedPreferenceHelper.getInstance(this, Constans.FILE_NAME_SETTING).getInt(Constans.KEY_PORT_PUSH_SERVER_VALUE, Constans.PORT_PUSH_SERVER);
		
		initUi();
		
//		初始化手机信息
		 TelephonyManager mTelephonyMgr = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);  
         Log.d("getImsi", "get mTelephonyMgr " + mTelephonyMgr.toString());  
         Constans.IMSI = mTelephonyMgr.getSubscriberId();
//         Constans.IMEI = "869451015167021";
         Constans.IMEI = mTelephonyMgr.getDeviceId();
         File sdFile = getSDcard();
 		if(null == sdFile){
 			showToast("没有检测到sd卡，某些功能不能正常使用");
 		}else{
 			Constans.sdPath = sdFile.toString();
 		}
		 updateApp();
		
	}
	
	private void initUi(){
		layoutUserName = (ViewEditText)findViewById(R.id.login_view_username);
		layoutUserName.setValue(SharedPreferenceHelper.getInstance(this, Constans.FILE_NAME_USER).getStr(KEY_USER_NAME));
		layoutUserName.setBackground(R.drawable.red_stroke_selector);
		layoutUserName.setLable(getString(R.string.user_name) + " :");
		
		layoutPassWord = (ViewEditText)findViewById(R.id.login_view_password);
		layoutPassWord.setValue(SharedPreferenceHelper.getInstance(this, Constans.FILE_NAME_USER).getStr(KEY_PASSWORD));
		layoutPassWord.setBackground(R.drawable.red_stroke_selector);
		
		layoutPassWord.setLable(getString(R.string.password) + " :");
		layoutPassWord.setIsPassWord(true);
		
		loginButton = (Button)findViewById(R.id.login_btn_login);
		loginButton.setOnClickListener(this);
		
		
		savePassWordBtn = (CheckBox)findViewById(R.id.is_save_password);
		savePassWordBtn.setChecked(isSavePassWord);
		savePassWordBtn.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				savePassWord();
			}
		});
		
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()){
		
		//登录事件
		case R.id.login_btn_login:
			login();
			break;
			//忘记密码
//		case R.id.forget_password:
//			forgetPassword();
//			break;
		}
	}
	

	@Override
	public void onAsyncStart() {
		super.onStart();
		showLoading("正在进行登录验证", true, null);
	}

	

	//登录成功返回的数据
	@Override
	public void resAsyncData(String content) {
		super.resAsyncData(content);
		Gson gson = new Gson();
		saveUserInfo();
		Intent intent = new Intent(getApplicationContext(), MainServise.class);
		startService(intent);
		Constans.userInfo = gson.fromJson(content, User.class);
		launcher(IndexTabActivity.class);
		this.finish();
	}

	private void login(){
		userName = layoutUserName.getValue().trim();
		
		if(TextUtils.isEmpty(userName)){
			layoutUserName.reQuestFocause();
			layoutUserName.setError("请输入用户名");
			return;
		}

		password = layoutPassWord.getValue().trim();
		if(TextUtils.isEmpty(password)){
			layoutPassWord.reQuestFocause();
			layoutPassWord.setError("请输入密码");
			return;
		}
		
		if("configuration".equals(userName)){
			launcher(SettingActivity.class);
			return;
		}
		RequestParams params = new RequestParams();
		params.put("userName", userName);
		params.put("password", password);
		ResClient.login(this, params, getAsyncHandler());
		
		
	}
	
	private void savePassWord(){
		// 保存是否存储密码判断条件 
		if(isSavePassWord ^ savePassWordBtn.isChecked()){
			isSavePassWord = savePassWordBtn.isChecked();
			SharedPreferenceHelper.getInstance(this, Constans.FILE_NAME_SETTING).saveBoolean(KEY_IS_SAVE_PASSWORD, isSavePassWord);
		}
	}
	
	private void saveUserInfo(){
		SharedPreferenceHelper.getInstance(this, Constans.FILE_NAME_USER).saveStr(KEY_USER_NAME, userName);
		if(isSavePassWord){
			SharedPreferenceHelper.getInstance(this, Constans.FILE_NAME_USER).saveStr(KEY_PASSWORD, password);
		}else{
			SharedPreferenceHelper.getInstance(this, Constans.FILE_NAME_USER).saveStr(KEY_PASSWORD, "");
		}
	}

	private void startServise(){
	
//		Intent intent4 = new Intent(getApplicationContext(), SyncAddcodeState.class);
//		startService(intent4);
	}
		
	void updateApp() {
		ResClient.get(getApplicationContext(), null, Constans.UPDATE_URL,
				new AsyncHttpResponseHandler() {

					@Override
					public void onFinish() {
						if (updateInfo != null)
							if (isUpdate()) {
								//更新处理
								Update(updateInfo);
							}

						super.onFinish();
					}

					@Override
					public void onSuccess(String arg0) {
						if (arg0 == null) {
							return;
						}
						Log.i("", "***************:arg0:" + arg0);
						Gson gson = new Gson();
						updateInfo = gson.fromJson(arg0, UpdateInfo.class);
						
						super.onSuccess(arg0);
					}

				});

	}
	
	private boolean isUpdate() {
		String newtVersion = updateInfo.getVersion().trim();
		String oldVersion = null;
		try {
			oldVersion = getVersionName().trim();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return !oldVersion.equals(newtVersion);
	}

	private void Update(UpdateInfo updateInfo){
//		showToast("有新的安装包，请更新!");
		downloadFile(updateInfo);
	}
	
	
	public void downloadFile(final UpdateInfo updateInfo) {
//		if (null == Tools.getSDcard()) {
//			MAMessage.ShowMessage(this, "提示信息", "请检查SD卡");
//			return;
//		}

		if(updateInfo == null){
			return;
		}
		
		String realPath = updateInfo.getUrl();
		if(getSDcard() == null){
			showToast("请检查sd卡");
			return;
		}
		
		pd = ProgressDialog.show(this, "", "安装程序下载中", true, true);
		
		String filePath = getSDcard().toString() + "/" + Constans.FILE_URL_DOWNLOAD_UPDATE + "ejingtong.apk";
//		String filePath = Tools.getDataDirectory().toString() + "/duoyun/" + realPath;

		AsyncDownloadFile download = new AsyncDownloadFile(getApplication());
//		try {
//			realPath = realPath.replace(" ", "%20").replace("'", "%27").replace("\"", "%22");
//			realPath = URLEncoder.encode(realPath, "UTF-8");
//			realPath = realPath.replace("+", "%20");
			download.setFileUrl(realPath);
			download.setFilePath(filePath);
			download.setListener(new ServiceSyncListener() {

				@Override
				public void onError(ActionResponse returnObject) {
					Log.i("", "error" + returnObject.getMessage());
					if (pd != null && pd.isShowing()) {
						pd.dismiss();
					}
				}
				
				

				@Override
				public void onProgress(int progress) {
					Log.i("", "进度:" + progress);
					super.onProgress(progress);
				}



				@Override
				public void onSuccess(ActionResponse returnObject) {
					Log.i("", "success" + returnObject.getMessage());
					if (null == returnObject.getData()) {
						return;
					}
					File file = (File) returnObject.getData();
					try {
						Intent intent = new Intent();
						intent.setAction(Intent.ACTION_VIEW);
						intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
						startActivity(intent);
					} catch (ActivityNotFoundException e) {
					}
					if (pd != null && pd.isShowing()) {
						pd.dismiss();
					}
				}

			});
			download.execute("");
//		} catch (UnsupportedEncodingException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
		
	}
}
