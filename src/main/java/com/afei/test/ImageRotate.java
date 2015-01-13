package com.afei.test;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.*;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStream;

public class ImageRotate {

  public static InputStream rotateImg(BufferedImage image,
                                      int degree,
                                      Color bgcolor) throws IOException {

    int iw = image.getWidth();//原始图象的宽度
    int ih = image.getHeight();//原始图象的高度
    int w ;
    int h ;
    int x ;
    int y ;
    degree = degree % 360;
    if (degree < 0)
      degree = 360 + degree;//将角度转换到0-360度之间
    double ang = Math.toRadians(degree);//将角度转为弧度

    /**
     *确定旋转后的图象的高度和宽度
     */

    if (degree == 180 || degree == 0 || degree == 360) {
      w = iw;
      h = ih;
    } else if (degree == 90 || degree == 270) {
      w = ih;
      h = iw;
    } else {
      //int d = iw + ih;
      /*double cosVal = Math.abs(Math.cos(ang));
      double sinVal = Math.abs(Math.sin(ang));
      w = (int) (sinVal*ih) + (int) (cosVal*iw);
      h = (int) (sinVal*iw) + (int) (cosVal*ih);*/
      int d = iw + ih;
      w = (int) (d * Math.abs(Math.cos(ang)));
      h = (int) (d * Math.abs(Math.sin(ang)));
    }

    x = (w / 2) - (iw / 2);//确定原点坐标
    y = (h / 2) - (ih / 2);
    BufferedImage rotatedImage = new BufferedImage(w, h, image.getType());
    Graphics2D gs = (Graphics2D)rotatedImage.getGraphics();
    if(bgcolor==null){
      rotatedImage  = gs.getDeviceConfiguration().createCompatibleImage(w, h, Transparency.TRANSLUCENT);
    }else{
      gs.setColor(bgcolor);
      gs.fillRect(0, 0, w, h);//以给定颜色绘制旋转后图片的背景
    }

    AffineTransform at = new AffineTransform();
    at.rotate(ang, w / 2, h / 2);//旋转图象
    at.translate(x, y);
    AffineTransformOp op = new AffineTransformOp(at, AffineTransformOp.TYPE_BICUBIC);
    op.filter(image, rotatedImage);
    image = rotatedImage;

    ByteArrayOutputStream  byteOut= new ByteArrayOutputStream();
    ImageOutputStream imageOut = ImageIO.createImageOutputStream(byteOut);

    ImageIO.write(image, "png", imageOut);
    return new ByteArrayInputStream(byteOut.toByteArray());
  }

  private static void rotate(String srcPath,String destPath){
    FileOutputStream fos = null ;
    InputStream is = null;
    try {
      File srcFile = new File(srcPath);
      BufferedImage srcImage = ImageIO.read(srcFile);

      is = rotateImg(srcImage, -90, null);
      fos = new FileOutputStream(destPath);
      byte[] tmp = new byte[1024];
      int t;
      while ((t = is.read(tmp)) >= 0) {
        fos.write(tmp, 0, t);
      }

      fos.flush();
    }catch (IOException ioe){
      ioe.printStackTrace();
    } finally {
      try {
        fos.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
      try {
        is.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  public static void main(String[] args)  {
    rotate("E:\\AngryBirds\\43600_1.png","E:\\AngryBirds\\43600_1_bak.png");
    rotate("E:\\AngryBirds\\43600_2.png","E:\\AngryBirds\\43600_2_bak.png");
    rotate("E:\\AngryBirds\\43600_3.png","E:\\AngryBirds\\43600_3_bak.png");
    rotate("E:\\AngryBirds\\43600_4.png","E:\\AngryBirds\\43600_4_bak.png");

  }


}
