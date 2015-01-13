package com.afei.test;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;

/**
 * 利用ImageWriter类的write方法，通过ImageWriteParam指定压缩质量
 * @author zhenfei.wang
 * @since 1.0.0
 * Created On: 2015-01-09 10:31
 */
public class ImageWriteCompress {

  /**
   *
   * 自己设置压缩质量来把图片压缩成byte[]
   * 利用ImageWriter类的write方法，通过ImageWriteParam指定压缩质量
   * @param image 压缩源图片
   * @param quality 压缩质量，在0-1之间，
   * @return 返回的字节数组
   */
  private byte[] bufferedImageToBytes(BufferedImage image, float quality) {
    System.out.println("JPEG: " + quality + "质量开始打包" + getCurrentTime());
    // 如果图片空，返回空
    if (image == null) {
      return null;
    }
    // 得到指定Format图片的writer
    Iterator<ImageWriter> iter = ImageIO
            .getImageWritersByFormatName("jpeg");// 得到迭代器
    ImageWriter writer = iter.next(); // 得到writer

    // 得到指定writer的输出参数设置(ImageWriteParam )
    ImageWriteParam iwp = writer.getDefaultWriteParam();
    iwp.setCompressionMode(ImageWriteParam.MODE_EXPLICIT); // 设置可否压缩
    iwp.setCompressionQuality(quality); // 设置压缩质量参数

    iwp.setProgressiveMode(ImageWriteParam.MODE_DISABLED);

    ColorModel colorModel = ColorModel.getRGBdefault();
    // 指定压缩时使用的色彩模式
    iwp.setDestinationType(new javax.imageio.ImageTypeSpecifier(colorModel,
            colorModel.createCompatibleSampleModel(16, 16)));

    // 开始打包图片，写入byte[]
    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(); // 取得内存输出流
    IIOImage iIamge = new IIOImage(image, null, null);
    try {
      // 此处因为ImageWriter中用来接收write信息的output要求必须是ImageOutput
      // 通过ImageIo中的静态方法，得到byteArrayOutputStream的ImageOutput
      writer.setOutput(ImageIO
              .createImageOutputStream(byteArrayOutputStream));
      writer.write(null, iIamge, iwp);
    } catch (IOException e) {
      System.out.println("write error. ");
      e.printStackTrace();
    }
    System.out.println("jpeg" + quality + "质量完成打包-----" + getCurrentTime()
            + "----length----" + byteArrayOutputStream.toByteArray().length);
    return byteArrayOutputStream.toByteArray();
  }

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

  public static void main(String args[]) throws Exception {

    String srcPath = "E:\\imageCompressTest\\test3\\imageWrite\\ImageTest.jpg";
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

    ImageWriteCompress util = new ImageWriteCompress();
    byte[] imageByte = util.bufferedImageToBytes(srcImage,level);
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
