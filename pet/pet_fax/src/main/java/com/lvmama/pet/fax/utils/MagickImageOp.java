/**
 * 
 */
package com.lvmama.pet.fax.utils;

/**
 * 为MagickImageInfo操作类
 * @author yangbin
 *
 */
public class MagickImageOp {

	public static boolean isWinOS(){
		 String osName = System.getProperty("os.name");
		 return osName.toLowerCase().contains("windows");
	}
	
	private void init(){
		if(Constant.isImageMagick() && isWinOS()){
			System.loadLibrary("TiffFaxDLL");
		}
	}
	private static MagickImageOp instance=null;
	
	private MagickImageOp(){
		init();
	}
	
	public static MagickImageOp getInstance(){
		if(instance==null){
			instance = new MagickImageOp();
		}
		return instance;
	}
	
	/**
	 * 生成tiff的文件
	 * @param file
	 * @param type
	 * @return
	 */
	public String buildHTiff(final String file){		
		return MagickImageInfo.buildHTiff(file);
	}
	
	/**
	 * 生成tiff的文件
	 * @param file
	 * @param type
	 * @return
	 */
	public String buildVTiff(final String file){
		return MagickImageInfo.buildVTiff(file);
	}
}
