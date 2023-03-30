package cn.edu.ecust.service.util;

import java.util.Random;

public class RandomUtil {


    /*
    java 生成指定长度随机字符串
     */
    public static String getRandomStr(int n){
        String str = "abcdefghijklmnopqrstuvwxyz0123456789";
        Random r = new Random();//创建random对象
        StringBuffer buff = new StringBuffer();//StringBuffer
        for (int i = 0; i < n; i++) {
            int it = r.nextInt(str.length());//使用random生成[0,str.length())之间的随机数,不包括length
            buff.append(str.charAt(it));// 把int下标 转为 str中随机字符(数字，大写字母或者小写字母)

        }
        System.out.println(buff.toString());
        return buff.toString();

    }

    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
            getRandomStr(5);//n为随机字符串的长度

        }


    }

}
