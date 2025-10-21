package de.xxcd.aitravel.utils.general;

/**
 * 字符串工具类
 */
public class StringUtils {

    /**
     * 判断字符串是否为空
     * @param str 字符串
     * @return true：为空；false：非空
     */
    public static boolean isEmpty(String str) {
        return str == null || str.trim().isEmpty() || isBlank(str);
    }

    /**
     * 判断字符串是否非空
     * @param str 字符串
     * @return true：非空；false：为空
     */
    public static boolean idNotEmpty(String str){
        return !isEmpty(str);
    }

    /**
     * 获取字符串长度
     * @param str 字符串
     * @return 字符串长度，null返回0
     */
    public static int length(CharSequence str) {
        return str == null ? 0 : str.length();
    }

    /**
     * 判断字符串是否为空白
     * @param str 字符串
     * @return true：为空白；false：非空白
     */
    public static boolean isBlank(CharSequence str) {

        int strLen = length(str);

        if (strLen == 0) {
            return true;
        } else {
            for (int i = 0; i < strLen; i++) {
                // 只要有一个非空字符即为非空字符串
                if (!Character.isWhitespace(str.charAt(i))) {
                    return false;
                }

            }

            return true;
        }

    }


}
