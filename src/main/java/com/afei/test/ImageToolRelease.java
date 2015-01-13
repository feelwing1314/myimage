package com.afei.test;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;

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
public class ImageToolRelease {

  private static final float COMPRESS_LEVEL= 0.9F;
  private static final int   DEST_HEIGHT   = 800;
  private static final int   DEST_WIDTH    = 480;

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
    decompressImageOfDir("E:\\imageCompressTest\\FullTest\\src");
  }

  public static void decompressImageOfDir(String imageDir){
    File dir = new File(imageDir);
    if(dir.isDirectory()){
      for(File file:dir.listFiles()){
        System.out.println("Level "+COMPRESS_LEVEL+"  begin at : "+getCurrentTime());
        try {
          decompress(file.getCanonicalPath(),false);
        } catch (IOException e) {
          e.printStackTrace();
        }
        System.out.println("End at : "+getCurrentTime()+"\n\n");
      }
    }
  }

  /**
   * outputQuality：输出的图片质量，范围：0.0~1.0，1为最高质量。注意使用该方法时输出的图片格式必须
   * 为jpg（即outputFormat("jpg")。否则若是输出png格式图片，则该方法作用无效
   * @param srcPath 被解析的图片的路径
   * @param isSame 表示结果文件和原始文件是否是同一文件，如果为false，那么处理后的文件会重新命名并归档
   * @throws java.io.IOException
   */
  private static void decompress(String srcPath, boolean isSame) throws IOException {
    System.out.println("src Path = "+srcPath);
    String destPath ;
    if(!isSame) {
    int index = srcPath.indexOf(".");
      destPath = srcPath.substring(0,index)+getCurrentTimestamp()+"-"+COMPRESS_LEVEL
            +srcPath.substring(index,srcPath.length());
      destPath = destPath.replace("src","result");
    }else{
      destPath = srcPath;
    }

    File srcFile = new File(srcPath);
    BufferedImage src = javax.imageio.ImageIO.read(srcFile);
    int imageWidth = src.getWidth(null);
    int imageHeight = src.getHeight(null);

    Thumbnails.Builder<BufferedImage> builder ;
    if ((float)DEST_WIDTH/DEST_HEIGHT != (float)imageWidth/imageHeight) {

      //如果是只需要旋转90即可的情况
      if((float)DEST_WIDTH/DEST_HEIGHT == (float)imageHeight/imageWidth){
        src = Thumbnails.of(src).rotate(90).scale((double)DEST_WIDTH/DEST_HEIGHT).asBufferedImage();
      }
      else {
        if ((double)imageWidth/imageHeight > (double)DEST_WIDTH/DEST_HEIGHT) {
          src = Thumbnails.of(srcPath).height(DEST_HEIGHT).asBufferedImage();
        } else {
          src = Thumbnails.of(srcPath).width(DEST_WIDTH).asBufferedImage();
        }

      }

      builder = Thumbnails.of(src).sourceRegion(Positions.CENTER, DEST_WIDTH, DEST_HEIGHT)
              .size(DEST_WIDTH, DEST_HEIGHT);

    } else {
      builder = Thumbnails.of(src).size(DEST_WIDTH, DEST_HEIGHT);
    }

    builder.outputQuality(COMPRESS_LEVEL).outputFormat("jpg").toFile(destPath);
  }

}
