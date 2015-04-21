/**
 * 
 */
package com.lvmama.pet.fax.utils;

/**
 * @author yangbin
 *
 */
public class MagickImageInfo {

	/**
	 * 生成tiff的文件
	 * @param file
	 * @param type
	 * @return
	 */
	public static native String buildHTiff(final String file);
	
	/**
	 * 生成tiff的文件
	 * @param file
	 * @param type
	 * @return
	 */
	public static native String buildVTiff(final String file);
	
	/**
	 * Tiff生成jpg文件
	 * @param file
	 * @param preffix 生成的图片前辍
	 * @return 
	 */
	public static native String convertTiff(final String file);
}
