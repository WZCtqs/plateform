package com.szhbl.common.utils;

import java.util.Random;

public class RandomUtil {
    /**
     * 获取随机字符串 a-z,A-Z,0-9
     * @param length    长度
     * @return
     */
    public static String getRandomString(int length) {
        String str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();

        for (int i = 0; i < length; ++i) {
            //int number = random.nextInt(62);// [0,62)
            sb.append(str.charAt(random.nextInt(62)));
        }
        return sb.toString();
    }

    /**
     * JAVA获得0-9,a-z,A-Z范围的随机数
     *
     * @param length
     *            随机数长度
     * @return String
     */
    public static String getRandomChar(int length) {
        char[] chr = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
                'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };
        Random random = new Random();
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < length; i++) {
            buffer.append(chr[random.nextInt(62)]);
        }
        return buffer.toString();
    }

    /**
     * 获取随机字符串 a-z
     * @param length    长度
     * @return
     */
    public static String getLowerLetter(int length) {
        String str = "abcdefghijklmnopqrstuvwxyz";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; ++i) {
            sb.append(str.charAt(random.nextInt(26)));
        }
        return sb.toString();
    }

    /**
     * 获取随机字符串 0-9
     * @param length    长度
     * @return
     */
    public static String getNumber(int length) {
        String str = "0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; ++i) {
            sb.append(str.charAt(random.nextInt(10)));
        }
        return sb.toString();
    }

    /**
     * 获取随机字符串 0-9,a-z,0-9
     * 有两遍0-9，增加数字概率
     * @param length    长度
     * @return
     */
    public static String getLowerLetterNumber(int length) {
        String str = "0123456789abcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; ++i) {
            sb.append(str.charAt(random.nextInt(46)));
        }
        return sb.toString();
    }

    /**
     * 获取随机密码，lLength位小写英文+nLength位数字
     * @param lLength   字母长度
     * @param nLength   数字长度
     * @return
     */
    public static String getPasswordSimple(int lLength, int nLength) {
        return getLowerLetter(lLength)+getNumber(nLength);
    }

    /**
     * 获取随机密码，包含英文和数字
     * @param length 密码长度
     * @return
     */
    public static String getPasswordComplex(int length) {
        String password;
        while(true){
            password = getLowerLetterNumber(6);
            //必须包含字母和数字
            if (password.matches("(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,20}")) {
                return password;
            }
        }
    }

    public static void main(String[] args) {
//      System.out.println(getRandomString(32));
//      System.out.println(getRandomChar(32));
//      //UUID
//      String uuid = UUID.randomUUID().toString().replaceAll("-", "");
//      System.out.println(uuid);

        System.out.println("密码"+getPasswordSimple(1, 9));
    }
}
