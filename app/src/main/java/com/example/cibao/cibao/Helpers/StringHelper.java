package com.example.cibao.cibao.Helpers;

/**
 * 屈彬
 * 2017/3/16
 * 字符串处理类
 */
public class StringHelper {
    /**
     * @show 判断两组字符串是否相同
     * @param str1 比较字符串1
     * @param str2 比较字符串2
     * @return 返回字符串比较结果，相同为true。否则为false
     */
    public static boolean isEqual(String str1, String str2){
        return str1.equals(str2);
    }

    /**
     * @show 判断某个字符串是否为空
     * @param str 字符串
     * @return 判断结果
     */
    public static boolean isNullOrEmpty(String str){
        if(str==null)return true;
        return str.isEmpty();
    }
}
