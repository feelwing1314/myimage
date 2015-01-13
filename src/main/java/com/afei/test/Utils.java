package com.afei.test;

/**
 * @author zhenfei.wang
 * @since 1.0.0
 * Created On: 2015-01-08 17:34
 */

public final class Utils {
  /**
   * 判断是linux系统还是window系统
   *
   * @return true -- 是Windows操作系统
   */
  public static boolean isWindowsOS() {
    boolean isWindowsOS = false;
    String osName = System.getProperty("os.name");
    if (osName!=null && osName.toLowerCase().indexOf("window") > -1) {
      isWindowsOS = true;
    }
    return isWindowsOS;
  }
}
