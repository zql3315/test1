package com.infosky.common.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*******************************************************************************
 * pinyin4j is a plug-in, you can kind of Chinese characters into phonetic.Multi-tone character,Tone Detailed view http://pinyin4j.sourceforge.net/
 * 
 */
public class Pinyin4jUtil {

    private static final Logger logger = LoggerFactory.getLogger(Pinyin4jUtil.class);

    private static Map<String, List<String>> pinyinMap = new HashMap<String, List<String>>();

    static {
        try {
            initPinyin("/duoyinzi_dic.txt");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    /***************************************************************************
     * 获取中文汉字拼音 默认输出
     * 
     * @param chinese
     * @return
     */
    public static String getPinyin(String chinese) {
        return getPinyinZh_CN(makeStringByStringSet(chinese));
    }

    /***************************************************************************
     * 拼音大写输出
     * 
     * @param chinese
     * @param only
     *            是否取最优先的,否则是多个多音字组合
     * @return
     */
    public static String getPinyinToUpperCase(String chinese, boolean priority) {
        if (priority) {
            return convertChineseToPinyin(chinese).toUpperCase();
        } else {
            return getPinyinZh_CN(makeStringByStringSet(chinese)).toUpperCase();
        }
    }

    /***************************************************************************
     * 拼音小写输出
     * 
     * @param chinese
     *            中文汉字
     * @param only
     *            是否取最优先的,否则是多个多音字组合
     * @return
     */
    public static String getPinyinToLowerCase(String chinese, boolean priority) {
        if (priority) {
            return convertChineseToPinyin(chinese).toLowerCase();
        } else {
            return getPinyinZh_CN(makeStringByStringSet(chinese)).toLowerCase();
        }
    }

    /***************************************************************************
     * 首字母大写输出
     * 
     * @param chinese
     *            中文汉字
     * @param only
     *            是否取最优先的,否则是多个多音字组合
     * 
     * @return
     */
    public static String getPinyinFirstToUpperCase(String chinese, boolean priority) {
        if (priority) {
            return convertChineseToPinyin(chinese);
        } else {
            return getPinyin(chinese);
        }
    }

    /***************************************************************************
     * 拼音简拼输出
     * 
     * @param chinese
     *            中文汉字
     * @param only
     *            是否取最优先的,否则是多个多音字组合
     * 
     * @return
     */
    public static String getPinyinJianPin(String chinese, boolean priority) {
        if (priority) {
            return getPinyinConvertJianPin(convertChineseToPinyin(chinese));
        } else {
            return getPinyinConvertJianPin(getPinyin(chinese));
        }
    }

    /***************************************************************************
     * 字符集转换
     * 
     * @param chinese
     *            中文汉字
     * @throws BadHanyuPinyinOutputFormatCombination
     */
    public static Set<String> makeStringByStringSet(String chinese) {
        char[] chars = chinese.toCharArray();
        if (chinese != null && !chinese.trim().equalsIgnoreCase("")) {
            char[] srcChar = chinese.toCharArray();
            String[][] temp = new String[chinese.length()][];
            for (int i = 0; i < srcChar.length; i++) {
                char c = srcChar[i];
                // 是中文或者a-z或者A-Z转换拼音
                if (String.valueOf(c).matches("[\\u4E00-\\u9FA5]+")) {
                    try {
                        temp[i] = PinyinHelper.toHanyuPinyinStringArray(chars[i], getDefaultOutputFormat());
                    } catch (BadHanyuPinyinOutputFormatCombination e) {
                        e.printStackTrace();
                    }
                } else if (((int) c >= 65 && (int) c <= 90) || ((int) c >= 97 && (int) c <= 122)) {
                    temp[i] = new String[] {
                        String.valueOf(srcChar[i])
                    };
                } else {
                    temp[i] = new String[] {
                        String.valueOf(srcChar[i])
                    };
                }
            }
            String[] pingyinArray = Exchange(temp);
            Set<String> zhongWenPinYin = new LinkedHashSet<String>();// 去重
            for (int i = 0; i < pingyinArray.length; i++) {
                if (pingyinArray[i] != null) zhongWenPinYin.add(pingyinArray[i]);
            }
            return zhongWenPinYin;
        }
        return null;
    }

    /***************************************************************************
     * Default Format 默认输出格式
     * 
     * @return
     */
    public static HanyuPinyinOutputFormat getDefaultOutputFormat() {
        HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
        format.setCaseType(HanyuPinyinCaseType.LOWERCASE);// 小写
        format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);// 没有音调数字
        format.setVCharType(HanyuPinyinVCharType.WITH_U_AND_COLON);// u显示
        return format;
    }

    /***************************************************************************
     * 
     * @param strJaggedArray
     * @return
     */
    public static String[] Exchange(String[][] strJaggedArray) {
        String[][] temp = DoExchange(strJaggedArray);
        return temp[0];
    }

    /***************************************************************************
     * 
     * @param strJaggedArray
     * @return
     */
    private static String[][] DoExchange(String[][] strJaggedArray) {
        int len = strJaggedArray.length;
        if (len >= 2) {
            int len1 = strJaggedArray[0].length;
            int len2 = strJaggedArray[1].length;
            int newlen = len1 * len2;
            String[] temp = new String[newlen];
            for (int i = 0; i < len1; i++) {
                for (int j = 0; j < len2; j++) {
                    temp[i] = capitalize(strJaggedArray[0][i]) + capitalize(strJaggedArray[1][j]);
                }
            }
            String[][] newArray = new String[len - 1][];
            for (int i = 2; i < len; i++) {
                newArray[i - 1] = strJaggedArray[i];
            }
            newArray[0] = temp;
            return DoExchange(newArray);
        } else {
            int len1 = strJaggedArray[0].length;
            String[] temp = new String[len1];
            for (int i = 0; i < len1; i++) {
                temp[i] = capitalize(strJaggedArray[0][i]);
            }
            strJaggedArray[0] = temp;
            return strJaggedArray;
        }
    }

    /***************************************************************************
     * 首字母大写
     * 
     * @param s
     * @return
     */

    public static String capitalize(String s) {
        char ch[];
        ch = s.toCharArray();
        // if (ch[0] >= 'a' && ch[0] <= 'z') {
        if (ch != null && ch.length > 0) { // 修改[/color]
            if (ch[0] >= 'a' && ch[0] <= 'z') {
                ch[0] = (char) (ch[0] - 32);
            }
        }
        String newString = new String(ch);
        return newString;
    }

    /***************************************************************************
     * 字符串集合转换字符串(逗号分隔)
     * 
     * @param stringSet
     * @return
     */
    public static String getPinyinZh_CN(Set<String> stringSet) {
        StringBuilder str = new StringBuilder();
        int i = 0;
        for (String s : stringSet) {
            if (i == stringSet.size() - 1) {
                str.append(s);
            } else {
                str.append(s + ",");
            }
            i++;
        }
        return str.toString();
    }

    /***************************************************************************
     * 获取每个拼音的简称
     * 
     * @param chinese
     * @return
     */
    public static String getPinyinConvertJianPin(String chinese) {
        String[] strArray = chinese.split(",");
        StringBuffer strChar = new StringBuffer();
        for (String str : strArray) {
            char arr[] = str.toCharArray(); // 将字符串转化成char型数组
            for (int i = 0; i < arr.length; i++) {
                if (arr[i] >= 65 && arr[i] < 91) { // 判断是否是大写字母
                    strChar.append(arr[i]);
                }
            }
            strChar.append(",");
        }
        return strChar.toString().substring(0, strChar.length() - 1);
    }

    /**
     * 去除多音字重复数据
     * 
     * @param theStr
     * @return
     */
    public static List<Map<String, Integer>> discountTheChinese(String theStr) {
        // 去除重复拼音后的拼音列表
        List<Map<String, Integer>> mapList = new ArrayList<Map<String, Integer>>();
        // 用于处理每个字的多音字，去掉重复
        Map<String, Integer> onlyOne = null;
        String[] firsts = theStr.split(" ");
        // 读出每个汉字的拼音
        for (String str : firsts) {
            onlyOne = new Hashtable<String, Integer>();
            String[] china = str.split(",");
            // 多音字处理
            for (String s : china) {
                Integer count = onlyOne.get(s);
                if (count == null) {
                    onlyOne.put(s, new Integer(1));
                } else {
                    onlyOne.remove(s);
                    count++;
                    onlyOne.put(s, count);
                }
            }
            mapList.add(onlyOne);
        }
        return mapList;
    }

    /**
     * 解析并组合拼音，对象合并方案(推荐使用)
     * 
     * @return
     */
    public static String parseTheChineseByObject(List<Map<String, Integer>> list) {
        Map<String, Integer> first = null; // 用于统计每一次,集合组合数据
        // 遍历每一组集合
        for (int i = 0; i < list.size(); i++) {
            // 每一组集合与上一次组合的Map
            Map<String, Integer> temp = new Hashtable<String, Integer>();
            // 第一次循环，first为空
            if (first != null) {
                // 取出上次组合与此次集合的字符，并保存
                for (String s : first.keySet()) {
                    for (String s1 : list.get(i).keySet()) {
                        String str = s + s1;
                        temp.put(str, 1);
                    }
                }
                // 清理上一次组合数据
                if (temp != null && temp.size() > 0) {
                    first.clear();
                }
            } else {
                for (String s : list.get(i).keySet()) {
                    String str = s;
                    temp.put(str, 1);
                }
            }
            // 保存组合数据以便下次循环使用
            if (temp != null && temp.size() > 0) {
                first = temp;
            }
        }
        String returnStr = "";
        if (first != null) {
            // 遍历取出组合字符串
            for (String str : first.keySet()) {
                returnStr += (str + ",");
            }
        }
        if (returnStr.length() > 0) {
            returnStr = returnStr.substring(0, returnStr.length() - 1);
        }
        return returnStr;
    }

    /*****************************************************************************
     * 将某个字符串的首字母 大写
     * 
     * @param str
     * @return
     */
    public static String convertInitialToUpperCase(String str) {
        if (str == null) {
            return null;
        }
        StringBuffer sb = new StringBuffer();
        char[] arr = str.toCharArray();
        for (int i = 0; i < arr.length; i++) {
            char ch = arr[i];
            if (i == 0) {
                sb.append(String.valueOf(ch).toUpperCase());
            } else {
                sb.append(ch);
            }
        }

        return sb.toString();
    }

    /*****************************************************************************
     * 汉字转拼音 首字母大写 最大匹配优先
     * 
     * @param chinese
     * @return
     */
    public static String convertChineseToPinyin(String chinese) {

        StringBuffer pinyin = new StringBuffer();
        char[] arr = chinese.toCharArray();
        for (int i = 0; i < arr.length; i++) {
            char ch = arr[i];
            if (ch > 128) { // 非ASCII码
                // 取得当前汉字的所有全拼
                String[] results = null;
                try {
                    results = PinyinHelper.toHanyuPinyinStringArray(ch, getDefaultOutputFormat());
                    if (results == null || results.length <= 0) {  // 非中文
                        return "";
                    } else {
                        int len = results.length;
                        if (len == 1) { // 不是多音字
                            // pinyin.append(results[0]);
                            String py = results[0];
                            if (py.contains("u:")) {  // 过滤 u:
                                py = py.replace("u:", "v");
                                logger.debug("filter u:" + py);
                            }
                            pinyin.append(convertInitialToUpperCase(py));
                        } else if (results[0].equals(results[1])) {    // 非多音字 有多个音，取第一个
                            // pinyin.append(results[0]);
                            pinyin.append(convertInitialToUpperCase(results[0]));
                        } else { // 多音字
                            logger.debug("多音字：" + ch);
                            int length = chinese.length();
                            boolean flag = false;
                            String s = null;
                            List<String> keyList = null;
                            for (int x = 0; x < len; x++) {
                                String py = results[x];
                                if (py.contains("u:")) {  // 过滤 u:
                                    py = py.replace("u:", "v");
                                    logger.debug("filter u:" + py);
                                }
                                keyList = pinyinMap.get(py);
                                if (i + 3 <= length) {  // 后向匹配2个汉字 大西洋
                                    s = chinese.substring(i, i + 3);
                                    if (keyList != null && (keyList.contains(s))) {
                                        // if (value != null && value.contains(s)) {
                                        logger.debug("last 2 > " + py);
                                        // pinyin.append(results[x]);
                                        pinyin.append(convertInitialToUpperCase(py));
                                        flag = true;
                                        break;
                                    }
                                }
                                if (i + 2 <= length) {  // 后向匹配 1个汉字 大西
                                    s = chinese.substring(i, i + 2);
                                    if (keyList != null && (keyList.contains(s))) {
                                        logger.debug("last 1 > " + py);
                                        // pinyin.append(results[x]);
                                        pinyin.append(convertInitialToUpperCase(py));
                                        flag = true;
                                        break;
                                    }
                                }
                                if ((i - 2 >= 0) && (i + 1 <= length)) {    // 前向匹配2个汉字 龙固大
                                    s = chinese.substring(i - 2, i + 1);
                                    if (keyList != null && (keyList.contains(s))) {
                                        logger.debug("before 2 < " + py);
                                        // pinyin.append(results[x]);
                                        pinyin.append(convertInitialToUpperCase(py));
                                        flag = true;
                                        break;
                                    }
                                }
                                if ((i - 1 >= 0) && (i + 1 <= length)) {    // 前向匹配1个汉字 固大
                                    s = chinese.substring(i - 1, i + 1);
                                    if (keyList != null && (keyList.contains(s))) {
                                        logger.debug("before 1 < " + py);
                                        // pinyin.append(results[x]);
                                        pinyin.append(convertInitialToUpperCase(py));
                                        flag = true;
                                        break;
                                    }
                                }
                                if ((i - 1 >= 0) && (i + 2 <= length)) {    // 前向1个，后向1个 固大西
                                    s = chinese.substring(i - 1, i + 2);
                                    if (keyList != null && (keyList.contains(s))) {
                                        logger.debug("before last 1 <> " + py);
                                        // pinyin.append(results[x]);
                                        pinyin.append(convertInitialToUpperCase(py));
                                        flag = true;
                                        break;
                                    }
                                }
                            }
                            if (!flag) {    // 都没有找到，匹配默认的 读音 大
                                s = String.valueOf(ch);
                                for (int x = 0; x < len; x++) {
                                    String py = results[x];
                                    if (py.contains("u:")) {  // 过滤 u:
                                        py = py.replace("u:", "v");
                                        logger.debug("filter u:");
                                    }
                                    keyList = pinyinMap.get(py);
                                    if (keyList != null && (keyList.contains(s))) {
                                        logger.debug("default = " + py);
                                        // pinyin.append(results[x]); //如果不需要拼音首字母大写 ，直接返回即可
                                        pinyin.append(convertInitialToUpperCase(py));// 拼音首字母 大写
                                        break;
                                    }
                                }
                            }
                        }
                    }

                } catch (BadHanyuPinyinOutputFormatCombination e) {
                    e.printStackTrace();
                    logger.error("results:" + StringUtils.join(results), e.getLocalizedMessage());
                } catch (Exception e) {
                    e.printStackTrace();
                    logger.error("results:" + StringUtils.join(results) + "==" + chinese, e.getLocalizedMessage());
                }
            } else {
                pinyin.append(arr[i]);
            }
        }
        return pinyin.toString();
    }

    /**
     * 初始化 所有的多音字词组
     * 
     * @param fileName
     * @throws UnsupportedEncodingException 
     */
    public static void initPinyin(String fileName) throws UnsupportedEncodingException {
        // 读取多音字的全部拼音表;
        InputStream file = PinyinHelper.class.getResourceAsStream(fileName);

        BufferedReader br = new BufferedReader(new InputStreamReader(file, "UTF-8"));

        String s = null;
        try {
            while ((s = br.readLine()) != null) {

                if (s != null) {
                    String[] arr = s.split("#");
                    String pinyin = arr[0];
                    String chinese = arr[1];

                    if (chinese != null) {
                        String[] strs = chinese.split(" ");
                        List<String> list = Arrays.asList(strs);
                        pinyinMap.put(pinyin, list);
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /***************************************************************************
     * 
     * Test
     * 
     */
    public static void main(String[] args) {
        // String str = "解";//三个音
        String str = "石家庄市";
        // logger.debug("小写输出：" + getPinyinToLowerCase(str));
        // logger.debug("大写输出：" + getPinyinToUpperCase(str));
        logger.info("首字母大写输出：" + getPinyinFirstToUpperCase(str, true));
        logger.info("简拼输出：" + getPinyinJianPin(str, true));
        logger.info("最大匹配优先输出：" + convertChineseToPinyin(str));
        logger.info("最大匹配优先输出1：" + getPinyinConvertJianPin(convertChineseToPinyin(str)));

    }
}
