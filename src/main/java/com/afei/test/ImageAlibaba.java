package com.afei.test;

import com.alibaba.simpleimage.ImageWrapper;
import com.alibaba.simpleimage.render.ReadRender;
import com.alibaba.simpleimage.render.WriteRender;
import org.apache.commons.io.IOUtils;

import java.io.*;

/**
 * @author zhenfei.wang
 * @since 1.0.0
 * Created On: 2015-01-09 16:32
 */

public class ImageAlibaba {
  static File quaDir = new File("E:\\imageCompressTest\\simpleimage\\quality");
  static File resultDir = new File("E:\\imageCompressTest\\simpleimage\\result");

  public static void main(String[] args) throws Exception{
    for (File imgFile : quaDir.listFiles()) {
      if(imgFile.getName().indexOf("jpg") < 0){
        continue;
      }

      InputStream in = new FileInputStream(imgFile);
      OutputStream out = new FileOutputStream(new File(resultDir, "QUALITY_" + imgFile.getName()));
      WriteRender wr = null;
      try {
        ReadRender rr = new ReadRender(in, true);
        wr = new WriteRender(rr, out);

        wr.render();
      } finally {
        IOUtils.closeQuietly(in);
        IOUtils.closeQuietly(out);
      }
    }

    ImageWrapper wi = null;

    wi = readImage(resultDir, "QUALITY_quality_80.jpg");
    System.out.println(wi.getQuality() == 80);

    wi = readImage(resultDir, "QUALITY_quality_90.jpg");
    System.out.println(wi.getQuality() == 90);

    wi = readImage(resultDir, "QUALITY_quality_95.jpg");
    System.out.println(wi.getQuality() == 95);

    wi = readImage(resultDir, "QUALITY_seq_1x1_1x1_1x1.jpg");
    for(int i = 0; i < 3; i++){
      System.out.println( wi.getHorizontalSamplingFactor(0) == 1);
      System.out.println( wi.getVerticalSamplingFactor(0) == 1);
    }

    wi = readImage(resultDir, "QUALITY_seq_1x2_1x1_1x1.jpg");
    System.out.println(wi.getHorizontalSamplingFactor(0) == 1);
    System.out.println(wi.getVerticalSamplingFactor(0) == 2);
    System.out.println(wi.getHorizontalSamplingFactor(1) == 1);
    System.out.println(wi.getVerticalSamplingFactor(1) == 1);
    System.out.println(wi.getHorizontalSamplingFactor(2) == 1);
    System.out.println(wi.getVerticalSamplingFactor(2) == 1);

    wi = readImage(resultDir, "QUALITY_seq_2x1_1x1_1x1.jpg");
    System.out.println(wi.getHorizontalSamplingFactor(0) == 2);
    System.out.println(wi.getVerticalSamplingFactor(0) == 1);
    System.out.println(wi.getHorizontalSamplingFactor(1) == 1);
    System.out.println(wi.getVerticalSamplingFactor(1) == 1);
    System.out.println(wi.getHorizontalSamplingFactor(2) == 1);
    System.out.println(wi.getVerticalSamplingFactor(2) == 1);

    wi = readImage(resultDir, "QUALITY_seq_2x2_1x1_1x1.jpg");
    System.out.println(wi.getHorizontalSamplingFactor(0) == 2);
    System.out.println(wi.getVerticalSamplingFactor(0) == 2);
    System.out.println(wi.getHorizontalSamplingFactor(1) == 1);
    System.out.println(wi.getVerticalSamplingFactor(1) == 1);
    System.out.println(wi.getHorizontalSamplingFactor(2) == 1);
    System.out.println(wi.getVerticalSamplingFactor(2) == 1);
  }

  public static ImageWrapper readImage(File dir, String filename) throws Exception{
    InputStream in = new FileInputStream(new File(dir, filename));
    try {
      ReadRender rr = new ReadRender(in, true);
      return rr.render();
    }finally{
      IOUtils.closeQuietly(in);
    }
  }
}
