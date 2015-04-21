/*
 * Copyright (C) 2012 ZXing authors
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

package com.lvmm.zxing.camera;

import android.annotation.TargetApi;
import android.hardware.Camera;

/**
 * Implementation for Android API 9 (Gingerbread) and later. This opens up the possibility of accessing
 * front cameras, and rotated cameras.
 */
@TargetApi(9)
public final class GingerbreadOpenCameraInterface implements OpenCameraInterface {
  /**
   * Opens a rear-facing camera with {@link Camera#open(int)}, if one exists, or opens camera 0.
   */
  @Override
  public Camera open() {
    Camera camera = null;
    try {
    	if(camera==null){
        	camera = Camera.open();
        }
	} catch (Exception e) {
		// TODO: handle exception
		e.printStackTrace();
	}
    return camera;
  }

}
