package com.afei.test.util;

import java.security.MessageDigest;

/**
 * 计算字符串的MD5值的工具类（利用JDK的MessageDigest类）
 * @author zhenfei.wang
 * @since 1.0.0
 * Created On: 2015-01-13 14:31
 */
public final class MD5Util {

  /**
   * 计算字符串的MD5值，可以与MySQl的 md5()方法算得的值进行比较，验证该方法的准确性
   * @param s 计算MD5值的字符串
   * @return 字符串的MD5加密值
   */
  public static String getMD5(String s) {
    char hexDigits[]={'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};
    try {
      byte[] btInput = s.getBytes();
      // 获得MD5摘要算法的 MessageDigest 对象
      MessageDigest mdInst = MessageDigest.getInstance("MD5");
      // 使用指定的字节更新摘要
      mdInst.update(btInput);
      // 获得密文
      byte[] md = mdInst.digest();
      // 把密文转换成十六进制的字符串形式
      int j = md.length;
      char str[] = new char[j * 2];
      int k = 0;
      for (int i = 0; i < j; i++) {
        byte byte0 = md[i];
        str[k++] = hexDigits[byte0 >>> 4 & 0xf];
        str[k++] = hexDigits[byte0 & 0xf];
      }
      return new String(str);
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }
  public static void main(String[] args) {
    System.out.println("``After MD5 : "+
            MD5Util.getMD5(""));
    System.out.println("``After MD5 : "+
            MD5Util.getMD5("abcdefghijklmnopqrstuvwxyz"));
    System.out.println("``After MD5 : "+
            MD5Util.getMD5("8a683566bcc7801226b3d8b0cf35fd97"));
    System.out.println("``After MD5 : "+
            MD5Util.getMD5("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz"));
    System.out.println("``After MD5 : "+
            MD5Util.getMD5("message digest"));
  }
}
