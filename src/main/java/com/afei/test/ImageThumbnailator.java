package com.afei.test;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * 利用thumbnailator第三方图片压缩组件
 * @author zhenfei.wang
 * @since 1.0.0
 * Created On: 2015-01-09 10:31
 */
public class ImageThumbnailator {

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

    String srcPath = "E:\\imageCompressTest\\test1\\imageThumb\\ImageTest.png";
    for(int i=1;i<=10;i++) {
      System.out.println("Level "+i+"  begin at : "+getCurrentTime());
      decompress(srcPath, i/10f);
      System.out.println("End at : "+getCurrentTime()+"\n\n");
    }
  }

  /**
   * outputQuality：输出的图片质量，范围：0.0~1.0，1为最高质量。注意使用该方法时输出的图片格式必须
   * 为jpg（即outputFormat("jpg")。否则若是输出png格式图片，则该方法作用无效
   * @param srcPath 被解析的图片的路径
   * @param level 压缩图片等级
   * @throws IOException
   */
  public static void decompress(String srcPath, float level) throws IOException {
    int index = srcPath.indexOf(".");
    String destPath = srcPath.substring(0,index)+getCurrentTimestamp()+"-"+level
            +srcPath.substring(index,srcPath.length());
    //String suffix = srcPath.substring(index+1);

    File srcFile = new File(srcPath);
    BufferedImage src = javax.imageio.ImageIO.read(srcFile);
    int imageWidth = src.getWidth(null);
    int imageHeight = src.getHeight(null);

    Thumbnails.Builder<BufferedImage> builder ;
    if ((float)480/800 != (float)imageWidth/imageHeight) {
      if (imageWidth > imageHeight) {
        src = Thumbnails.of(srcPath).height(300).asBufferedImage();
      } else {
        src = Thumbnails.of(srcPath).width(400).asBufferedImage();
      }
      builder = Thumbnails.of(src).sourceRegion(Positions.CENTER, 400, 300).size(400, 300);
    } else {
      builder = Thumbnails.of(src).size(400, 300);
    }

    builder = builder.scale(1f)
            .outputQuality(level);
    builder.outputFormat("jpg").toFile(destPath);
  }

}
