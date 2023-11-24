package com.itmy.utils;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class RandomUtils {
    /**
     * 随机产生 指定的个数和指定的总和的数字
     * @param num 个数
     * @param total 总和
     * @param min 最小数字
     * @param result
     * @return 指定和
     */
    public static int[] specifyTotal(int num, int total, int min, int[] result) {
        int random = 0;
        if (num > 1) {
            int useTotal = total - (num - 1) * min;
            random = (int) (Math.random() * (useTotal - 1) + 1);
        } else {
            random = total;
        }
        result[num - 1] = random;
        int surplusTotal = total - random;
        num--;
        if (num > 0) {
            specifyTotal(num, surplusTotal, min, result);
        }
        return result;
    }

    /**
     * 字符串乱序
     * @param str
     * @return
     */
    public static String randomSort(String str){
        List<String> list = Arrays.asList(str.split(""));
        Collections.shuffle(list);
        String result = new String();
        for(String s : list){
            result += s;
        }
        return result;
    }

}
