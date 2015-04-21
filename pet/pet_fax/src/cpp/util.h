#include <jni.h>
#include <cstring>
#include <string>

jstring stoJstring( JNIEnv* env, const char* pat )
{
//定义java String类 strClass
jclass strClass = (env)->FindClass("Ljava/lang/String;");
//获取java String类方法String(byte[],String)的构造器,用于将本地byte[]数组转换为一个新String
jmethodID ctorID = (env)->GetMethodID(strClass, "<init>", "([BLjava/lang/String;)V");

jbyteArray bytes = (env)->NewByteArray(strlen(pat));//建立byte数组
(env)->SetByteArrayRegion(bytes, 0, strlen(pat), (jbyte*)pat);//将char* 转换为byte数组

jstring encoding = (env)->NewStringUTF("GB2312"); // 设置String, 保存语言类型,用于byte数组转换至String时的参数
return (jstring)(env)->NewObject(strClass, ctorID, bytes, encoding);//将byte数组转换为java String,并输出
}

std::string   jstringTostring(JNIEnv*   env,   jstring   jstr)  
{  
   char*   rtn   =   NULL;  
   jclass   clsstring   =   env->FindClass("java/lang/String");   
   jstring   strencode   =   env->NewStringUTF("GB2312");  
   jmethodID   mid   =   env->GetMethodID(clsstring,   "getBytes",   "(Ljava/lang/String;)[B");   
   jbyteArray   barr=   (jbyteArray)env->CallObjectMethod(jstr,mid,strencode);  
   jsize   alen   =   env->GetArrayLength(barr);  
   jbyte*   ba   =   env->GetByteArrayElements(barr,JNI_FALSE);  
   if(alen   >   0)  
   {  
    rtn   =   (char*)malloc(alen+1);         //new   char[alen+1];  
    memcpy(rtn,ba,alen);  
    rtn[alen]=0;  
   }  
   env->ReleaseByteArrayElements(barr,ba,0);  
   std::string stemp(rtn);
   free(rtn);
   return   stemp;  
}  