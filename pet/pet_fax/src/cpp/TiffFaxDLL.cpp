// TiffFaxDLL.cpp : 定义 DLL 应用程序的导出函数。
//

#include "stdafx.h"

#include "com_lvmama_pet_fax_utils_MagickImageInfo.h"
#include <memory>

#include "TiffImage.h"
#include "util.h"



JNIEXPORT jstring JNICALL Java_com_lvmama_pet_fax_utils_MagickImageInfo_buildHTiff
	(JNIEnv *env, jclass clazz, jstring file)
{
	std::string str=jstringTostring(env,file);
	//TiffImage *img =new TiffImage(str,type);
	std::auto_ptr<TiffImage> img(new TiffImage(str));
	img->converHorizontal();
	std::string result = img->targetPath();
	return stoJstring(env,result.c_str());
}

JNIEXPORT jstring JNICALL Java_com_lvmama_pet_fax_utils_MagickImageInfo_buildVTiff
	(JNIEnv *env, jclass clazz, jstring file)
{
	std::string str=jstringTostring(env,file);
	//TiffImage *img =new TiffImage(str,type);
	std::auto_ptr<TiffImage> img(new TiffImage(str));
	img->converVertical();
	std::string result = img->targetPath();
	return stoJstring(env,result.c_str());
}
