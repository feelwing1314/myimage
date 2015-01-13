package com.afei.test;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGEncodeParam;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * @author zhenfei.wang
 * @since 1.0.0
 * Created On: 2015-01-09 10:31
 */
public class ImageJPEGCodec {

  /**
   * 自己定义格式，得到当前系统时间
   */
  private static String getCurrentTime() {
    Calendar c = new GregorianCalendar();
    int hour = c.get(Calendar.HOUR_OF_DAY);
    int min = c.get(Calendar.MINUTE);
    int second = c.get(Calendar.SECOND);
    int millsecond = c.get(Calendar.MILLISECOND);
    return hour + ":" + min + ":" + second + "-" + millsecond;
  }

  /**
   * 自己定义格式，得到当前系统时间
   */
  private static String getCurrentTimestamp() {
    DateFormat format = new SimpleDateFormat("yyyyMMddHHmmssSSS");
    return format.format(new Date());
  }

  /**
   *  通过 com.sun.image.codec.jpeg.JPEGCodec提供的编码器来实现图像压缩
   */
  private byte[] newCompressImage(BufferedImage image, float quality) {
    // 如果图片空，返回空
    if (image == null) {
      return null;
    }
    // 开始开始，写入byte[]
    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(); // 取得内存输出流
    // 设置压缩参数
    JPEGEncodeParam param = JPEGCodec.getDefaultJPEGEncodeParam(image);
    param.setQuality(quality, false);
    // 设置编码器
    JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(
            byteArrayOutputStream, param);
    System.out.println("newCompressive" + quality + "质量开始打包"
            + getCurrentTime());
    try {
      encoder.encode(image);
    } catch (Exception ef){
      ef.printStackTrace();
    }
    System.out.println("newCompressive" + quality + "质量打包完成"
            + getCurrentTime() + "----lenth----"
            + byteArrayOutputStream.toByteArray().length);
    return byteArrayOutputStream.toByteArray();

  }
  public static void main(String args[]) throws Exception {

    String srcPath = "E:\\imageCompressTest\\test3\\imageJPEGCodec\\ImageTest.jpg";
    for(int i=1;i<=10;i++) {
      System.out.println("Level "+i+"  begin at : "+getCurrentTime());
      decompress(srcPath, i/10f);
      System.out.println("End at : "+getCurrentTime()+"\n\n");
    }
  }


  public static void decompress(String srcPath, float level) throws IOException {
    int index = srcPath.indexOf(".");
    String destPath = srcPath.substring(0,index)+getCurrentTimestamp()+"-"+level
            +srcPath.substring(index,srcPath.length());
    File srcFile = new File(srcPath);
    BufferedImage srcImage = ImageIO.read(srcFile);

    ImageJPEGCodec util = new ImageJPEGCodec();
    byte[] imageByte = util.newCompressImage(srcImage,level);
    toImage(imageByte,destPath);
  }

  private static void toImage(byte[] imageByte, String destPath){
    FileOutputStream fos = null ;
    try {
      fos = new FileOutputStream(destPath);
      fos.write(imageByte);

      fos.flush();
    }catch (IOException ioe){
      ioe.printStackTrace();
    } finally {
      try {
        if(fos!=null) {
          fos.close();
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

}
