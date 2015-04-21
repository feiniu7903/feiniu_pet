package com.lvmama.comm.utils;

import java.io.IOException;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ExeSh {
	/**
	 * @param scyPath
	 *  同步脚本
	 */
	private static Log LOG = LogFactory.getLog(ExeSh.class);
	public static void exeSh(String scyPath) {
		Runtime rt = Runtime.getRuntime();
		String str = scyPath;
		if (StringUtils.isNotEmpty(str)) {
			Process pcs = null;
			try {
				pcs = rt.exec(str);
				pcs.waitFor();
			} catch (InterruptedException e) {
				LOG.info("InterruptedException同步脚步error(" + str + ")");
			} catch (IOException e) {
				LOG.info("IOException同步脚步error(" + str + ")");
			}
		}
	}
}
