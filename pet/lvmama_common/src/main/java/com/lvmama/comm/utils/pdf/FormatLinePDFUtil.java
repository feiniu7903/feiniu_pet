package com.lvmama.comm.utils.pdf;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FormatLinePDFUtil {
	/**
	 * 
	 * @param sourceString
	 *            源字符串
	 * @param marString
	 *            需要替换的首字符串
	 * @param repString
	 *            替换的字符串
	 * @param size
	 *            每行多少个字
	 * @return 处理后的字符串
	 */
	public static String replaceHtml(String sourceString, String marString,
			String repString, int size) {

		try {
			String souce = "";
			Pattern ptn = Pattern.compile(marString);
			Matcher mat = ptn.matcher(sourceString);
			int count = 0;
			while (mat.find()) {
				count++;
			}
			if (count == 0) {
				return sourceString;
			}

			String varStr = sourceString;
			for (int j = 0; j < count; j++) {

				String start = varStr.substring(varStr.indexOf(marString));
				String res = start.substring(0, start.indexOf("<"));
				String end = res.substring(res.indexOf(">") + 1, res.length());
				String interceptStr = end;
				ArrayList<String> indexList = getStrRight(end, size);
				String result = "";
				int index = 0;
				int index1 = 0;
				if (null != indexList && indexList.size() > 0) {
					for (int i = 0; i < indexList.size(); i++) {
						index = Integer.parseInt(indexList.get(i));
						index1 = i == 0 ? index : index + (repString.length() * i);
						result = StringInsert(end, repString, index1);
						end = result;
					}
				}
				souce = sourceString.replace(interceptStr, end);
				sourceString = souce;
				// 临时变量<.......
				varStr = start.substring(start.indexOf("<") + 1, start.length());
			}
			return souce;
		} catch (Exception e) {
			return sourceString;
		}
	}

	// old为原字符串，insert为要插入的字符串，index为插入位置
	private static String StringInsert(String old, String insert, int index) {
		return old.substring(0, index) + insert
				+ old.substring(index + 1, old.length());
	}

	private static ArrayList<String> getStrRight(String s, int left) {

		ArrayList<String> indexList = new ArrayList<String>();
		if (left >= s.length())
			return indexList;
		char[] ch = s.toCharArray();
		for (int i = left; i < ch.length; i++) {
			// 计算需要插入的下标
			if ((isChinese(ch[i]) || ' ' == ch[i]) && (i % left == 0)) {
				indexList.add((i == 0 ? i + 1 : i) + "");
			}
		}
		return indexList;
	}

	private static boolean isChinese(char c) {
		Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
		if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
				|| ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
				|| ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
				|| ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
				|| ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
				|| ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS
				|| ub == Character.UnicodeBlock.BASIC_LATIN) {
			return true;
		}
		return false;
	}
}
