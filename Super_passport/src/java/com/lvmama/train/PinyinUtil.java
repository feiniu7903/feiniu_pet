package com.lvmama.train;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * 拼音处理模块
 *
 * @author hanhui
 */
public class PinyinUtil {

    static private final Logger logger = LoggerFactory.getLogger(PinyinUtil.class);

    static private HanyuPinyinOutputFormat hf = new HanyuPinyinOutputFormat();

    static char[] FORK_HANZI = {
            '行'
    };

    static boolean isFork_HanZi(char name) {
        for (char hz : FORK_HANZI) {
            if (name == hz) {
                return true;
            }
        }
        return false;
    }

    /**
     * 如果name中包含汉字替换为汉语拼音
     *
     * @param name
     * @return
     */
    static public String getMixPinyin(String name) {
    	String key = getMixPinyin_Fork(name);
    	int next = PinyinCache.getInstance().getNext(key, name);
    	if(next == 0)
    		return key;
    	else
    		return key + String.valueOf(next); 
    }

    /**
     * 如果name中包含汉字替换为汉语拼音
     * Simple版本遇到多音字选取第一个
     *
     * @param name
     * @return
     */
    static public String getMixPinyin_Simple(String name) {
        hf.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < name.length(); i++) {
            try {
                char charValue = name.charAt(i);
                String[] pys = PinyinHelper.toHanyuPinyinStringArray(charValue, hf);
                if (pys != null) {
                    result.append(pys[0]);
                } else {
                    result.append(charValue);
                }
            } catch (BadHanyuPinyinOutputFormatCombination e) {
                logger.error("汉字转拼音出现异常", e);
                continue;
            }
        }
        String rsStr = result.toString();
        //System.err.println(name + "===" + rsStr);
        return rsStr;
    }

    /**
     * 如果name中包含汉字替换为汉语拼音
     * Fork版本遇到指定多音字会自动分裂
     * 如果遇到所有多音字都分裂那么内存开销过大可能导致系统内存溢出
     * 现在是对需要处理的多音字指定
     *
     * @param name
     * @return
     */
    static public String getMixPinyin_Fork(String name) {
        hf.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        List<StringBuilder> resultList = new ArrayList<StringBuilder>();
        for (int i = 0; i < name.length(); i++) {
            try {
                char charValue = name.charAt(i);
                String[] pys = PinyinHelper.toHanyuPinyinStringArray(charValue, hf);
                if (pys != null) {
                    if (pys.length == 1) {
                        addStr(resultList, pys[0]);
                    } else {//多音字
                        if (isFork_HanZi(charValue)) {
                            List<String> fpys = fPys(pys);
                            if (resultList.isEmpty()) {
                                for (String py : fpys) {
                                    resultList.add(new StringBuilder(py));
                                }
                            } else {
                                List<StringBuilder> forkList = new ArrayList<StringBuilder>();
                                for (StringBuilder sb : resultList) {
                                    for (String py : fpys) {
                                        forkList.add(new StringBuilder(sb).append(py));
                                    }
                                }
                                resultList = forkList;
                            }
                        } else {
                            addStr(resultList, getPY(charValue, pys));
                        }
                    }
                } else {
                    if (resultList.isEmpty()) {
                        resultList.add(new StringBuilder().append(charValue));
                    } else {
                        for (StringBuilder sb : resultList) {
                            sb.append(charValue);
                        }
                    }
                }
            } catch (BadHanyuPinyinOutputFormatCombination e) {
                logger.error("汉字转拼音出现异常", e);
                continue;
            }
        }

        StringBuilder rs_sb = new StringBuilder();
        for (StringBuilder sb : resultList) {
            rs_sb.append(sb);
        }
        String rsStr = rs_sb.toString();
        //System.err.println(name + "===" + rsStr);
        return rsStr;
    }

    /**
     * 如果name中包含汉字替换为汉语拼音的第一个字母
     *
     * @param name
     * @return
     */
    static public String getMixPy(String name) {
        return getMixPy_Fork(name);
    }

    /**
     * 如果name中包含汉字替换为汉语拼音的第一个字母
     * Fork版本遇到指定多音字会自动分裂
     * 如果遇到所有多音字都分裂那么内存开销过大可能导致系统内存溢出
     * 现在是对需要处理的多音字指定
     *
     * @param name
     * @return
     */
    static public String getMixPy_Fork(String name) {
        hf.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        List<StringBuilder> resultList = new ArrayList<StringBuilder>();
        for (int i = 0; i < name.length(); i++) {
            try {
                char charValue = name.charAt(i);
                String[] pys = PinyinHelper.toHanyuPinyinStringArray(charValue, hf);
                if (pys != null) {
                    if (pys.length == 1) {
                        addStr(resultList, pys[0].substring(0, 1));
                    } else {//多音字
                        if (isFork_HanZi(charValue)) {
                            List<String> fpys = fPys(pys, true);
                            if (resultList.isEmpty()) {
                                for (String py : fpys) {
                                    resultList.add(new StringBuilder(py));
                                }
                            } else {
                                List<StringBuilder> forkList = new ArrayList<StringBuilder>();
                                for (StringBuilder sb : resultList) {
                                    for (String py : fpys) {
                                        forkList.add(new StringBuilder(sb).append(py));
                                    }
                                }
                                resultList = forkList;
                            }
                        } else {
                            addStr(resultList, getPY(charValue, pys).substring(0, 1));
                        }
                    }
                } else {
                    if (resultList.isEmpty()) {
                        resultList.add(new StringBuilder().append(charValue));
                    } else {
                        for (StringBuilder sb : resultList) {
                            sb.append(charValue);
                        }
                    }
                }
            } catch (BadHanyuPinyinOutputFormatCombination e) {
                logger.error("汉字转拼音出现异常", e);
                continue;
            }
        }

        StringBuilder rs_sb = new StringBuilder();
        for (StringBuilder sb : resultList) {
            rs_sb.append(sb);
        }
        String rsStr = rs_sb.toString();
        //System.err.println(name + "===" + rsStr);
        return rsStr;
    }

    static private List<String> fPys(String[] pys) {
        return fPys(pys, false);
    }

    static private List<String> fPys(String[] pys, boolean sub) {
        List<String> fpys = new ArrayList<String>();
        for (String py : pys) {
            String pyv = sub ? py.substring(0, 1) : py;
            if (fpys.contains(pyv)) {
                continue;
            }
            fpys.add(pyv);
        }
        return fpys;
    }

    static private void addStr(List<StringBuilder> rsList, String str) {
        if (rsList.isEmpty()) {
            rsList.add(new StringBuilder(str));
        } else {
            for (StringBuilder sb : rsList) {
                sb.append(str);
            }
        }
    }

    static private String getPY(char hanzi, String[] pys) {
        if (hanzi == '重' || hanzi == '长') {
            return pys[1];
        } else {
            return pys[0];
        }
    }

    /**
     * 测试方法
     *
     * @param args
     */
    static public void main(String[] args) {
//        System.err.println(getMixPy("HHH"));
//        System.err.println(getMixPinyin("HHH"));
//        System.err.println(Runtime.getRuntime().totalMemory() / 1024 / 1024);
        List<String> testData = new ArrayList<String>();
//        for (int i = 0; i < 10000; i++) {
            String mix = getMixPinyin("查看招商银行啊天下车");
            String mix2 = getMixPy("查看招商银行啊天下车");
            System.err.println(mix);
            System.err.println(mix2);
//            testData.add(mix);
//            testData.add(mix2);
//        }
//        System.err.println(Runtime.getRuntime().totalMemory() / 1024 / 1024);
    }

}



