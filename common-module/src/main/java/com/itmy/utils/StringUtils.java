package com.itmy.utils;


public class StringUtils {
    public static String join(char[] arr, char start, char end, char delimiter){
        StringBuilder sb = new StringBuilder();
        sb.append(start);
        for (char c : arr) {
            sb.append(c).append(delimiter);
        }
        if(sb.length() > 1) {
            sb.deleteCharAt(sb.length() - 1);
        }
        sb.append(end);
        return sb.toString();
    }
}
