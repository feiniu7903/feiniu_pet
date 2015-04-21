package com.lvmama.comm.utils.lvmamacard;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.jxcell.View;

/**
 * excel加密
 * @author nixianjun 2013.12.11
 *
 */
public class JxcellEnc {
 
	public static void main(String[] args) {
		doEnc("C://Users/nixianjun/Desktop/lvmamacardcard1.xls", "C://Users/nixianjun/Desktop/lvmamacardcard2.xls", "23");
	}
	public static void doEnc(String  infile,String outfile,String password){

		View m_view = new View();
		FileOutputStream fileOut = null;
		FileInputStream filein = null;
		try {

			filein = new FileInputStream(infile);
			m_view.read(filein);
			m_view.editCopyRight();
			fileOut = new FileOutputStream(outfile);
			m_view.write(fileOut, password); // 加密内容
  		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

			if (filein != null) {
				try {
					filein.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (fileOut != null) {
				try {
					fileOut.close();
				} catch (IOException e) {
					e.printStackTrace();
				}

			}
		}
	
	}
}
