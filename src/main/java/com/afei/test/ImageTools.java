package com.afei.test;

import org.im4java.core.ConvertCmd;
import org.im4java.core.IMOperation;

/**
 * 使用im4java处理图片，
 * ConvertCmd convert = new ConvertCmd(boolean);
 * 当boolean的值为true时，使用GraphicsMagick的实现；当boolean的值为false时，使用ImageMagick的实现；
 */
public class ImageTools {

  /**
   * ImageMagick的路径
   */
  public static String magickPath = null;

  private static boolean useGraphicsMagick = true ;

  static{
    /**
     * 获取ImageMagick的路径,linux下不要设置此值，不然会报错
     */
    if(Utils.isWindowsOS()) {
      magickPath = "D:\\DevSpace\\ImageMagick-6.9.0-Q16";
      if(useGraphicsMagick){
        magickPath = "D:\\DevSpace\\GraphicsMagick-1.3.20-Q8";
      }
    }
  }

  /**
   * 根据坐标裁剪图片
   *
   * @param srcPath   要裁剪图片的路径
   * @param newPath   裁剪图片后的路径
   * @param x   起始横坐标
   * @param y   起始挫坐标
   * @param x1  结束横坐标
   * @param y1  结束挫坐标
   */
  public static void cutImage(String srcPath, String newPath, int x, int y, int x1,
                              int y1)  throws Exception {
    int width = x1 - x;
    int height = y1 - y;
    IMOperation op = new IMOperation();
    op.addImage(srcPath);

    //width：裁剪的宽度 height：裁剪的高度 x：裁剪的横坐标 y：裁剪的挫坐标
    op.crop(width, height, x, y);
    op.addImage(newPath);
    ConvertCmd convert = new ConvertCmd(useGraphicsMagick);

    //linux下不要设置此值，不然会报错
    if(Utils.isWindowsOS()) {
      convert.setSearchPath(magickPath);
    }
    convert.run(op);
  }

  /**
   * 根据宽度缩放图片
   * @param width  缩放后的图片宽度
   * @param srcPath   源图片路径
   * @param newPath   缩放后图片的路径
   */
  public static void cutImage(int width, String srcPath, String newPath) throws Exception {
    IMOperation op = new IMOperation();
    op.addImage(srcPath);
    op.resize(width, null);
    op.addImage(newPath);
    ConvertCmd convert = new ConvertCmd(useGraphicsMagick);
    //linux下不要设置此值，不然会报错
    if(Utils.isWindowsOS()) {
      convert.setSearchPath(magickPath);
    }
    convert.run(op);
  }

  /**
   * 给图片加水印
   * @param srcPath 源图片路径
   */
  public static void addImgText(String srcPath,String destPath) throws Exception {
    IMOperation op = new IMOperation();
    op.addImage();
    op.font("Arial").gravity("southeast").pointsize(18).fill("#FF0000").draw("text 100,100 www.tclclouds.com");
    op.addImage();
    ConvertCmd convert = new ConvertCmd(useGraphicsMagick);

    //linux下不要设置此值，不然会报错
    if(Utils.isWindowsOS()) {
      convert.setSearchPath(magickPath);
    }
    if(destPath==null){
      convert.run(op, srcPath, srcPath);
    }else {
      //第三个参数为destPath，当其等于第二个参数时，表示在原图上增加水印
      convert.run(op, srcPath, destPath);
    }
  }

  /**
   * 根据尺寸缩放图片
   * @param width  缩放后的图片宽度
   * @param height  缩放后的图片高度
   * @param srcPath   源图片路径
   * @param newPath   缩放后图片的路径
   */
  public static void cutImage(int width, int height, String srcPath, String newPath) throws Exception {
    IMOperation op = new IMOperation();
    op.addImage(srcPath);
    op.resize(width, height);
    op.addImage(newPath);
    ConvertCmd convert = new ConvertCmd();

    if(Utils.isWindowsOS()) {
      convert.setSearchPath(magickPath);
    }
    convert.run(op);
  }


  public static void main(String[] args) throws Exception{
    //cutImage("D:\\apple870.jpg", "D:\\apple870eee.jpg", 98, 48, 370, 320);
    /*cutImage(240,400, "E://839612_6.png", "E://839612_6_small.png");
    cutImage(480,800, "E://839612_6_small.png", "E://839612_6_ok.png");*/
    addImgText("E://MeganFox.jpg","E://MeganFox_watermark2.jpg");
  }
}
