/*
 * Copyright (C) 2008 ZXing authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.lvmm.zxing;

import java.io.IOException;
import java.util.Collection;
import java.util.Map;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.AssetFileDescriptor;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.util.Log;
import android.view.KeyEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.ejingtong.R;
import com.ejingtong.activity.BaseActivity;
import com.ejingtong.help.OrderManage;
import com.ejingtong.model.Order;
import com.ejingtong.view.MDialog;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.DecodeHintType;
import com.google.zxing.Result;
import com.lvmm.zxing.camera.CameraManager;
import com.lvmm.zxing.decoding.ResultHandler;
import com.lvmm.zxing.decoding.ResultHandlerFactory;
import com.lvmm.zxing.view.ViewfinderView;

/**
 * This activity opens the camera and does the actual scanning on a background thread. It draws a
 * viewfinder to help the user place the barcode correctly, shows feedback as the image processing
 * is happening, and then overlays the results when a scan is successful.
 * @author dswitkin@google.com (Daniel Switkin)
 * @author Sean Owen
 */
public final class CaptureActivity extends BaseActivity implements SurfaceHolder.Callback {
  private static final String TAG = CaptureActivity.class.getSimpleName();
  public static final int HISTORY_REQUEST_CODE = 0x0000bacc;
  private CameraManager cameraManager;
  private CaptureActivityHandler handler;
  private Result savedResultToShow;
  private ViewfinderView viewfinderView;
  private View resultView;
  private Result lastResult;
  private boolean hasSurface;
  private IntentSource source;
  private Collection<BarcodeFormat> decodeFormats;
  private Map<DecodeHintType,?> decodeHints;
  private String characterSet;
  private InactivityTimer inactivityTimer;
  private AmbientLightManager ambientLightManager;
  
  private MediaPlayer mediaPlayer;
  private boolean playBeep;
  private static final float BEEP_VOLUME = 0.10f;
  private boolean vibrate;

  ViewfinderView getViewfinderView() {
    return viewfinderView;
  }

  public Handler getHandler() {
    return handler;
  }

  CameraManager getCameraManager() {
    return cameraManager;
  }

  @Override
  public void onCreate(Bundle icicle) {
    super.onCreate(icicle);
    Window window = getWindow();
    window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    
		if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
			Log.i("info", "landscape=================");
		} else if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
			Log.e("info", "portrait---------------------");
		}
		setContentView(R.layout.capture);
    
	try {
		PackageInfo info = this.getPackageManager().getPackageInfo(
				this.getPackageName(), 0);
		String packageNames = info.packageName;
		Log.e(TAG, "---------------"+packageNames);
	} catch (NameNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
    
    hasSurface = false;
    inactivityTimer = new InactivityTimer(this);
    ambientLightManager = new AmbientLightManager(this);
  }

  @Override
  protected void onResume() {
    super.onResume();
    // CameraManager must be initialized here, not in onCreate(). This is necessary because we don't
    // want to open the camera driver and measure the screen size if we're going to show the help on
    // first launch. That led to bugs where the scanning rectangle was the wrong size and partially
    // off screen.
    try {
    	cameraManager = new CameraManager(getApplication());
	} catch (Exception e) {
		// TODO: handle exception
		e.printStackTrace();
	}

    viewfinderView = (ViewfinderView) findViewById(R.id.viewfinder_view);
    viewfinderView.setCameraManager(cameraManager);
    resultView = findViewById(R.id.result_view);

    handler = null;
    lastResult = null;

    resetStatusView();

    SurfaceView surfaceView = (SurfaceView) findViewById(R.id.preview_view);
    surfaceView.setVisibility(View.VISIBLE);
    SurfaceHolder surfaceHolder = surfaceView.getHolder();
    if (hasSurface) {
      // The activity was paused but not stopped, so the surface still exists. Therefore
      // surfaceCreated() won't be called, so init the camera here.
      initCamera(surfaceHolder);
    } else {
      // Install the callback and wait for surfaceCreated() to init the camera.
      surfaceHolder.addCallback(this);
      surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
    }

    ambientLightManager.start(cameraManager);
    inactivityTimer.onResume();
    
    setBeepSound();

    source = IntentSource.NONE;
    decodeFormats = null;
    characterSet = null;
    restartPreviewAfterDelay(0L);
  }

  @Override
  protected void onPause() {
    if (handler != null) {
      handler.quitSynchronously();
      handler = null;
    }
    inactivityTimer.onPause();
    ambientLightManager.stop();
    cameraManager.closeDriver();
    
    SurfaceView surfaceView = (SurfaceView) findViewById(R.id.preview_view);
    surfaceView.setVisibility(View.INVISIBLE);
    super.onPause();
  }

  @Override
  protected void onDestroy() {
    inactivityTimer.shutdown();
    cameraManager.closeDriver();
    super.onDestroy();
  }
  
  private long firstTime;
  @Override
  public boolean onKeyDown(int keyCode, KeyEvent event) {
    switch (keyCode) {
    	case KeyEvent.KEYCODE_BACK:
    		if ((source == IntentSource.NONE)
					&& lastResult != null) {
				restartPreviewAfterDelay(0L);
				return true;
			}else{
				long secondeTime = System.currentTimeMillis();
				if ((secondeTime - firstTime) > 1000) {
					showToast("再按一次退出");
					firstTime = secondeTime;
					return true;
				} else {
					// ClientStart.getInstance().disConnect();
					return super.onKeyDown(keyCode, event);
				}
			}
    }
    return super.onKeyDown(keyCode, event);
  }

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		this.finish();
		super.onBackPressed();
		Log.e(TAG, "*** *********onBackPressed()******111111*");
	}

  @Override
  public void onActivityResult(int requestCode, int resultCode, Intent intent) {
	  
  }

  private void decodeOrStoreSavedBitmap(Bitmap bitmap, Result result) {
    // Bitmap isn't used yet -- will be used soon
    if (handler == null) {
      savedResultToShow = result;
    } else {
      if (result != null) {
        savedResultToShow = result;
      }
      if (savedResultToShow != null) {
        Message message = Message.obtain(handler, R.id.decode_succeeded, savedResultToShow);
        handler.sendMessage(message);
      }
      savedResultToShow = null;
    }
  }

  @Override
  public void surfaceCreated(SurfaceHolder holder) {
    if (holder == null) {
      Log.e(TAG, "*** WARNING *** surfaceCreated() gave us a null surface!");
    }
    if (!hasSurface) {
      hasSurface = true;
      initCamera(holder);
    }
  }

  @Override
  public void surfaceDestroyed(SurfaceHolder holder) {
    hasSurface = false;
  }

  @Override
  public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

  }

  /**
   * A valid barcode has been found, so give an indication of success and show the results.
   * @param rawResult The contents of the barcode.
   * @param scaleFactor amount by which thumbnail was scaled
   * @param barcode   A greyscale bitmap of the camera data which was decoded.
   */
  public void handleDecode(Result rawResult, Bitmap barcode, float scaleFactor) {
    inactivityTimer.onActivity();
    lastResult = rawResult;
    ResultHandler resultHandler = ResultHandlerFactory.makeResultHandler(this, rawResult);
    boolean fromLiveScan = barcode != null;
    if (!fromLiveScan) {
    	Log.e(TAG, "*** ****************xxxxxxxxxxxxxxx**************-----"+fromLiveScan);
      playBeepSoundAndVibrate();
    }
    switch (source) {
      case NONE:
        handleDecodeInternally(rawResult, resultHandler, barcode);
        Log.e(TAG, "*** ****************3333333333333333*******************");
        break;
    }
  }
  // Put up our own UI for how to handle the decoded contents.
  private void handleDecodeInternally(Result rawResult, ResultHandler resultHandler, Bitmap barcode) {
    viewfinderView.setVisibility(View.GONE);
    resultView.setVisibility(View.VISIBLE);
    try {
    	TextView contentsTextView = (TextView) findViewById(R.id.contents_text_view);
    	CharSequence displayContents = resultHandler.getDisplayContents();
    	contentsTextView.setText(displayContents);
    	
    		int addCode = -1;
    		String strCode = getAddCode(displayContents.toString());
    		final MDialog mDialog = new MDialog(this);
    		mDialog.setTitle("提示信息!");
    		mDialog.setBtn1OnClickListener("确定", new OnClickListener() {
    			@Override
    			public void onClick(View v) {
    				restartPreviewAfterDelay(0L);
    				mDialog.dismiss();
    			}
    		});
    		if (null == strCode || "".equals(strCode)) {
    			mDialog.setTip("非法的标识码");
    			mDialog.show();
    			return;
    		}
    		try {
    			addCode = Integer.parseInt(strCode);
    		} catch (Exception e) {
    			mDialog.setTip("非法的标识码");
    			mDialog.show();
    			return;
    		}

    		if (addCode == -1) {
    			mDialog.setTip("非法的标识码");
    			mDialog.show();
    			return;
    		}

    		Order order = OrderManage.getInstance(getApplicationContext())
    				.searchOrder(addCode);
    		if (order == null) {
    			downloadOrder(this, addCode);
    		} else {
    			canToPass(this, order);
    		}
	} catch (Exception e) {
		// TODO: handle exception
		e.printStackTrace();
	}
  }

	private final String prefix = "LVMM";
	private final String postfix = "TCOD";
	// 判断是否是特定标记
	public String getAddCode(String src) {
		if (src != null && src.length() > 0) {
			if (src.startsWith(prefix) && src.endsWith(postfix)) {
				String addCode = src.substring(prefix.length(), src.length()
						- postfix.length());
				boolean hasOtherChar = addCode.matches("\\D");
				if (!hasOtherChar && addCode.length() == 8)
					return addCode;
			}
		}
		return null;
	}

  private void initCamera(SurfaceHolder surfaceHolder) {
    if (surfaceHolder == null) {
      throw new IllegalStateException("No SurfaceHolder provided");
    }
    if (cameraManager.isOpen()) {
      Log.w(TAG, "initCamera() while already open -- late SurfaceView callback?");
      return;
    }
    try {
      cameraManager.openDriver(surfaceHolder);
      // Creating the handler starts the preview, which can also throw a RuntimeException.
      if (handler == null) {
        handler = new CaptureActivityHandler(this, decodeFormats, decodeHints, characterSet, cameraManager);
      }
      decodeOrStoreSavedBitmap(null, null);
    } catch (IOException ioe) {
      Log.w(TAG, ioe);
      displayFrameworkBugMessageAndExit();
    } catch (RuntimeException e) {
      // Barcode Scanner has seen crashes in the wild of this variety:
      // java.?lang.?RuntimeException: Fail to connect to camera service
      Log.w(TAG, "Unexpected error initializing camera", e);
      displayFrameworkBugMessageAndExit();
    }
  }

  private void displayFrameworkBugMessageAndExit() {
    AlertDialog.Builder builder = new AlertDialog.Builder(this);
    builder.setTitle(getString(R.string.app_name));
    builder.setMessage(getString(R.string.msg_camera_framework_bug));
    builder.setPositiveButton(R.string.button_ok, new FinishListener(this));
    builder.setOnCancelListener(new FinishListener(this));
    builder.show();
  }

  public void restartPreviewAfterDelay(long delayMS) {
    if (handler != null) {
      handler.sendEmptyMessageDelayed(R.id.restart_preview, delayMS);
    }
    resetStatusView();
  }

  private void resetStatusView() {
    resultView.setVisibility(View.GONE);
    viewfinderView.setVisibility(View.VISIBLE);
    lastResult = null;
  }

  public void drawViewfinder() {
    viewfinderView.drawViewfinder();
  }
//////////////
	private void setBeepSound() {
		playBeep = true;
		AudioManager audioService = (AudioManager) getSystemService(AUDIO_SERVICE);
		if (audioService.getRingerMode() != AudioManager.RINGER_MODE_NORMAL) {
			playBeep = false;
		}
		initBeepSound();
		vibrate = true;
	}

	private void initBeepSound() {
		if (playBeep && mediaPlayer == null) {
			// The volume on STREAM_SYSTEM is not adjustable, and users found it
			// too loud,
			// so we now play on the music stream.
			setVolumeControlStream(AudioManager.STREAM_MUSIC);
			mediaPlayer = new MediaPlayer();
			mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
			mediaPlayer.setOnCompletionListener(beepListener);
			AssetFileDescriptor file = getResources().openRawResourceFd(
					R.raw.beep);
			try {
				mediaPlayer.setDataSource(file.getFileDescriptor(),
						file.getStartOffset(), file.getLength());
				file.close();
				mediaPlayer.setVolume(BEEP_VOLUME, BEEP_VOLUME);
				mediaPlayer.prepare();
			} catch (IOException e) {
				mediaPlayer = null;
			}
		}
	}

	private static final long VIBRATE_DURATION = 200L;

	private void playBeepSoundAndVibrate() {
		if (playBeep && mediaPlayer != null) {
			mediaPlayer.start();
		}
		if (vibrate) {
			Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
			vibrator.vibrate(VIBRATE_DURATION);
		}
	}

	/**
	 * When the beep has finished playing, rewind to queue up another one.
	 */
	private final OnCompletionListener beepListener = new OnCompletionListener() {
		public void onCompletion(MediaPlayer mediaPlayer) {
			mediaPlayer.seekTo(0);
		}
	};
	// //////////////
}
