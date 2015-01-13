package com.afei.test.test;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.zip.Deflater;

/**
 * @author zhenfei.wang
 * @since 1.0.0
 * Created On: 2015-01-09 11:16
 */

public class TestCode {
  public static void main(String[] args){

    try {
      System.out.println(URLEncoder.encode("a6970u488v4.3.2t0","UTF-8"));
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    }
  }
}
