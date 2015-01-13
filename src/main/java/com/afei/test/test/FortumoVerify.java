package com.afei.test.test;

import com.afei.test.util.MD5Util;

import java.util.*;

/**
 * fortumo支付时后台对fortumo callback参数校验
 * @author zhenfei.wang
 * @since 1.0.0
 * Created On: 2015-01-09 11:16
 */

public class FortumoVerify {
  private static final String THE_SECRET = "b649d5431be4399861d4f27e332abf2d";
  private static final String FORTUMO_PARAMS = "status=OK&operator=EMT&currency=EUR&user_id=248010270629540" +
          "&payment_code=1420958720604a3&user_share=0.5&message_id=ce0d13f600a62778db47e7075434f7ec&keyword=" +
          "&shortcode=&sig=8093e041d8f4ec83c65cfdc54585c73c&price_wo_vat=0.53&country=EE&test=true" +
          "&confirmation_code=&product_name=a26u60646v310t0&billing_type=DCB&sender=" +
          "&service_id=86e51f1d4fd6215495dc5784126a0a94&price=0.64";
  public static void main(String[] args){
    Map<String,String> paramMap = new HashMap<String, String>();
    List<String> paramKeyList = new ArrayList<String>();

    for(String pair : FORTUMO_PARAMS.split("&")){
      String[] pairArray = pair.split("=");
      paramMap.put(pairArray[0],pairArray.length==2?pairArray[1]:"");
      paramKeyList.add(pairArray[0]);
    }
    //fortumo callback的参数排序，然后拼接，最后拼接该价格的SECRET
    Collections.sort(paramKeyList);
    System.out.println(paramKeyList+"\n\n");

    StringBuffer str = new StringBuffer();
    for(String paramKey:paramKeyList){
      //sig字段不需要拼接
      if(paramKey.equals("sig")){
        System.out.println("The sig = "+paramMap.get(paramKey));
        continue;
      }
      str.append(paramKey).append("=").append(paramMap.get(paramKey));
    }
    str.append(THE_SECRET);

    //计算排序拼接后字符串的MD5值:

    System.out.println(str);

    System.out.println("After MD5 : "+ MD5Util.getMD5(str.toString()));

  }
}
