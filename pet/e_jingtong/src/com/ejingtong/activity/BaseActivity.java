package com.ejingtong.activity;

import java.io.File;
import org.json.JSONException;
import org.json.JSONObject;
import android.app.Activity;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.Toast;
import com.ejingtong.R;
import com.ejingtong.activity.login.LoginActivity;
import com.ejingtong.activity.order.OrderDetailActivity;
import com.ejingtong.common.Constans;
import com.ejingtong.help.OrderManage;
import com.ejingtong.help.ProgressDialogHelper;
import com.ejingtong.help.SharedPreferenceHelper;
import com.ejingtong.http.ResClient;
import com.ejingtong.model.Order;
import com.ejingtong.model.ResponseData;
import com.ejingtong.view.MDialog;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.lvmm.zxing.CaptureActivity;

public class BaseActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	protected void showLoading(String title, boolean isCancleable,
			OnCancelListener listener) {
		ProgressDialogHelper.show(this, title, isCancleable, listener);
	}

	protected void dismissLoading() {
		ProgressDialogHelper.dismiss();
	}

	public void showToast(String msg) {
		Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
	}

	// http请求开始
	public void onAsyncStart() {

	}

	// http请求结束
	public void onAsyncFinish() {
		dismissLoading();
	}

	// http请求失败
	public void onAsyncFailure(Throwable error, String content) {
		showToast("请稍后重试");
	}

	// http请求成功
	public void onAsyncSuccess(String content) {
		Log.i("", "返回的数据：" + content);
		// http请求成功后根据返回的code判断数据是否正确，
		if (content == null) {
			showToast("请稍后重试");
			return;
		}

		try {
			JSONObject object = new JSONObject(content);
			if (!object.isNull("code")) {
				if (object.getInt("code") == 0 && !object.isNull("data")) {

					resAsyncData(object.getString("data"));
				} else {
					showToast(object.getString("message"));
				}
			}
		} catch (JSONException e) {
			showToast("数据格式错误");
			e.printStackTrace();
		}
	}

	public void resAsyncData(String content) {

	}

	public void launcher(Class<?> cls, Bundle extras) {
		Intent intent = new Intent(this, cls);
		intent.putExtras(extras);
		startActivity(intent);
		// startActivityForResult(intent, RESULT_OK);
	}

	public void launcher(Class<?> cls) {
		Intent intent = new Intent(this, cls);
		startActivityForResult(intent, RESULT_OK);
	}

	public AsyncHttpResponseHandler getAsyncHandler() {
		AsyncHttpResponseHandler handler = new AsyncHttpResponseHandler() {
			@Override
			public void onStart() {
				onAsyncStart();
				super.onStart();
			}

			@Override
			public void onFinish() {
				onAsyncFinish();
				super.onFinish();
			}

			@Override
			public void onSuccess(String content) {
				onAsyncSuccess(content);
				super.onSuccess(content);
			}

			@Override
			public void onFailure(Throwable error, String content) {
				onAsyncFailure(error, content);
				super.onFailure(error, content);
			}
		};
		return handler;
	}

	public void unRegist() {
		SharedPreferenceHelper.getInstance(this, Constans.FILE_NAME_USER)
				.clean();
		launcher(LoginActivity.class);
	}

	protected void setBackButton() {
		ImageView backBtn = (ImageView) findViewById(R.id.back);
		backBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				BaseActivity.this.finish();
			}
		});
	}

	protected void setHomeButton() {
		ImageView homeBtn = (ImageView) findViewById(R.id.home);
		homeBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				BaseActivity.this.finish();
			}
		});
	}

	public String getVersionName() throws Exception {
		// 获取packagemanager的实例
		PackageManager packageManager = getPackageManager();
		// getPackageName()是你当前类的包名，0代表是获取版本信息
		PackageInfo packInfo = packageManager.getPackageInfo(getPackageName(),
				0);
		String version = packInfo.versionName;
		return version;
	}

	/**
	 * 获取sd卡的状态
	 * @return SD卡的状态 如果SD卡不可用，则返回null 如果可用则返回当前的SD卡的路径
	 */
	public static File getSDcard() {
		File state = null;
		String SDCardState = Environment.getExternalStorageState();
		if (SDCardState.equals(Environment.MEDIA_MOUNTED)) {
			// SD卡可用的状态
			state = Environment.getExternalStorageDirectory();
		}
		return state;
	}

	/**
	 * 获取sd卡的状态
	 * 
	 * @return SD卡的状态 如果SD卡不可用，则返回null 如果可用则返回当前的SD卡的路径
	 */
	public static String getSDcardPath() {
		File state = getSDcard();
		if (null == state) {
			return state.toString();
		} else {
			return "";
		}
	}

	// 下载订单
	public void downloadOrder(final Activity activity, final int addId) {
		showLoading("正在查询 ", true, null);

		if (Constans.userInfo == null) {
			showToast("请登录");
			return;
		}

		RequestParams params = new RequestParams();
		params.put("addCode", addId + "");
		ResClient.getOrder(this, addId + "", params,
				new AsyncHttpResponseHandler() {
					@Override
					public void onFinish() {
						dismissLoading();
						super.onFinish();
					}

					@Override
					public void onStart() {
						// 开始下载
						Log.i("", "开始下载 " + addId);
						super.onStart();
					}

					@Override
					public void onSuccess(String arg0) {
						Log.i("", "下载成功:" + arg0);
						Gson gson = new Gson();
						try {
							ResponseData responseData = gson.fromJson(arg0,
									ResponseData.class);
							if (responseData.getCode() == 0) {
								OrderManage
										.getInstance(getApplicationContext())
										.save2Database(responseData);
								canToPass(activity, responseData.getDatas()
										.get(0));
							} else {
								showToast(responseData.getMessage());
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
						super.onSuccess(arg0);
					}
				});
	}

	public void canToPass(final Activity activity, final Order order) {
		String msg = null;
		final MDialog mDialog = new MDialog(this);

		if (order == null || order.getBaseInfo() == null) {
			msg = "无效订单";
			mDialog.setTitle("提示信息!");
			mDialog.setTip(msg);
			mDialog.setBtn1OnClickListener("确定", new OnClickListener() {
				@Override
				public void onClick(View v) {
					mDialog.dismiss();
					if (activity instanceof CaptureActivity) {
						((CaptureActivity) activity)
								.restartPreviewAfterDelay(1000L);
					}
				}
			});
			mDialog.show();
			return;
		}

		if (!order.getBaseInfo().isPassed()) {
			msg = "验证码已经失效";
			mDialog.setTitle("提示信息!");
			mDialog.setTip(msg);
			mDialog.setBtn1OnClickListener("确定", new OnClickListener() {
				@Override
				public void onClick(View v) {
					mDialog.dismiss();
					if (activity instanceof CaptureActivity) {
						((CaptureActivity) activity)
								.restartPreviewAfterDelay(1000L);
					}
				}
			});
			mDialog.show();
			return;
		}

		String title = "";
		msg = null;
		if (!order.getBaseInfo().isAfterValidTime()) {
			title = "未到游玩时间";
			msg = "您的可通关时间从" + order.getBaseInfo().getValidTime() + "到"
					+ order.getBaseInfo().getInvalidTime();
		} else if (!order.getBaseInfo().isBeforeInvalidTime()) {
			msg = "您的可通关时间从" + order.getBaseInfo().getValidTime() + "到"
					+ order.getBaseInfo().getInvalidTime();
			title = " 已过游玩时间";
		}

		if (msg == null) {
			toDetailActivity(order);
		} else {
			mDialog.setTitle(title);
			mDialog.setTip(msg);
			mDialog.setBtn1OnClickListener("立即通关", new OnClickListener() {
				@Override
				public void onClick(View v) {
					mDialog.dismiss();
					toDetailActivity(order);
					if (activity instanceof CaptureActivity) {
						((CaptureActivity) activity)
								.restartPreviewAfterDelay(1000L);
					}
				}
			});
			mDialog.setBtn2OnClickListener("暂不通关", new OnClickListener() {
				@Override
				public void onClick(View v) {
					mDialog.dismiss();
					if (activity instanceof CaptureActivity) {
						((CaptureActivity) activity)
								.restartPreviewAfterDelay(1000L);
					}
				}
			});
			mDialog.show();
		}
	}

	private void toDetailActivity(Order order) {
		Bundle bundle = new Bundle();
		bundle.putSerializable("order_detail", order);
		launcher(OrderDetailActivity.class, bundle);
	}
}
