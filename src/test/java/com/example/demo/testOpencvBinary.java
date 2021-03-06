package com.example.demo;


import com.example.demo.utils.ImageConvertUtil;
import com.example.demo.utils.ImageFilterUtil;
import com.example.demo.utils.ImageOpencvUtil;
import org.opencv.core.Mat;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;

import static org.opencv.highgui.HighGui.imshow;
import static org.opencv.highgui.HighGui.waitKey;
import static org.opencv.imgcodecs.Imgcodecs.imwrite;


/**
 * @author ly
 * @since 2021/4/22
 */
//测试opencv二值化binaryzation()方法——√
public class testOpencvBinary {
    private final static int targetDifferenceValue = 10;

    public static void main(String[] args) throws Exception {
        // 加载动态库
        URL url = ClassLoader.getSystemResource("lib/opencv/opencv_java452.dll");
        System.load(url.getPath());

        //原图路径
        String sourceImage = "E:\\Desktop\\OCRTest\\image\\04.png";
        //二值化后的图片保存路径--在原来的图片主名后加上afterBinary
        String processedImage = sourceImage.substring(0, sourceImage.lastIndexOf(".")) + "afterBinary_GAUSSIAN_C.png";

        //读取图像
        File image = new File(sourceImage);
        BufferedImage bufferedImage = ImageIO.read(image);

        //调用ImageFilterUtil的灰度化gray()方法
        BufferedImage grayImage = ImageFilterUtil.gray(bufferedImage);

        //将灰度化后的图像转为Mat矩阵图像
        Mat grayImg = ImageConvertUtil.BufImg2Mat(grayImage);
        imshow("graImg", grayImg);

        //均值迁移滤波
        Mat denoiseImg = ImageOpencvUtil.pyrMeanShiftFiltering(grayImg);
        imshow("Processed Image", denoiseImg);

        //ImageFilterUtil的gray灰度化方法处理后仍为三通道图像，使用ImageOpencvUtil的gray方法转换为单通道图像
        grayImg = ImageOpencvUtil.gray(denoiseImg);

        //绘制直方图
        Mat histogram = ImageOpencvUtil.getHistogram(denoiseImg);
        imshow("Histogram", histogram);

        //二值化图像
        Mat binaryImg = ImageOpencvUtil.ImgBinarization(grayImg);
        imshow("binaryImg", binaryImg);

        //保存到字符串processedImage对应位置
//        imwrite(processedImage, binaryImg);
        waitKey();
    }
}
