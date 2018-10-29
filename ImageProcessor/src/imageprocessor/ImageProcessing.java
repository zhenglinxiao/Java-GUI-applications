package imageprocessor;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class ImageProcessing {

    public static BufferedImage convertToGreyScale(final BufferedImage img) {
        BufferedImage output = new BufferedImage(img.getWidth(), img.getHeight(), img.getType());

        for (int j = 0; j < img.getHeight(); ++j) {
            for (int i = 0; i < img.getWidth(); ++i) {
                int argb = img.getRGB(i, j);

                int alpha = (argb & 0xFF000000) >> 24;
                int red = (argb & 0x00FF0000) >> 16;
                int green = (argb & 0x0000FF00) >> 8;
                int blue = (argb & 0x000000FF);

                int grey = (red + green + blue) / 3;

                int greyARGB = alpha << 24 | grey << 16 | grey << 8 | grey;

                output.setRGB(i, j, greyARGB);
            }
        }

        return output;
    }

    public static BufferedImage applyGammaCorrection(BufferedImage img, int sliderValue) {

        BufferedImage output = new BufferedImage(img.getWidth(), img.getHeight(), img.getType());
        for (int i = 0; i < img.getHeight(); i++) {
            for (int j = 0; j < img.getWidth(); j++) {

                // Read color from input pixel
                Color color = new Color(img.getRGB(j, i));

                float[] hsb = Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), null);

                float hue = hsb[0];
                float saturation = hsb[1];
                float brightness = hsb[2];
                float correctedBrightness = (float) Math.pow(brightness, sliderValue / 100.0);

                output.setRGB(j, i, Color.HSBtoRGB(hue, saturation, correctedBrightness));
            }
        }

        return output;
    }

    public static BufferedImage applyGreyScaleConversion(BufferedImage img) {

        BufferedImage output = new BufferedImage(img.getWidth(), img.getHeight(), img.getType());
        for (int i = 0; i < img.getHeight(); i++) {
            for (int j = 0; j < img.getWidth(); j++) {
                Color color = new Color(img.getRGB(j, i));

                // Average RGB color channels
                int r = color.getRed();
                int g = color.getGreen();
                int b = color.getBlue();
                int avg = (r + g + b) / 3;
                
                output.setRGB(j, i, new Color(avg, avg, avg).getRGB());
            }
        }
        
        return output;
    }

    public static BufferedImage applySharpen(BufferedImage img) {

        float[][] sharpenKernel = {{0, -1, 0}, {-1, 5, -1}, {0, -1, 0}};

        return applyConvolutionFilter(img, sharpenKernel);
    }

    public static BufferedImage applyEdgeDetection(BufferedImage img) {

        float[][] edgeDetectionKernel = {{-1, -1, -1}, {-1, 8, -1}, {-1, -1, -1}};

        return applyConvolutionFilter(img, edgeDetectionKernel);
    }

    public static BufferedImage applyBoxBlur(BufferedImage img, int radius) {

        final int ARRAY_DIM = 2 * radius + 1;
        final float[][] boxKernel = new float[ARRAY_DIM][ARRAY_DIM];
        float value = 1.0f / (ARRAY_DIM * ARRAY_DIM);

        for (int i = 0; i < ARRAY_DIM; i++) {
            for (int j = 0; j < ARRAY_DIM; j++) {
                boxKernel[i][j] = value;
            }
        }

        return applyConvolutionFilter(img, boxKernel);
    }

    public static BufferedImage applyGaussianBlur(BufferedImage img) {
//        final float gaussianKernel[][] = new float[][]{
//            {0.00000067f, 0.00002292f, 0.00019117f, 0.00038771f, 0.00019117f, 0.00002292f, 0.00000067f},
//            {0.00002292f, 0.00078634f, 0.00655965f, 0.01330373f, 0.00655965f, 0.00078633f, 0.00002292f},
//            {0.00019117f, 0.00655965f, 0.05472157f, 0.11098164f, 0.05472157f, 0.00655965f, 0.00019117f},
//            {0.00038771f, 0.01330373f, 0.11098164f, 0.22508352f, 0.11098164f, 0.01330373f, 0.00038771f},
//            {0.00019117f, 0.00655965f, 0.05472157f, 0.11098164f, 0.05472157f, 0.00655965f, 0.00019117f},
//            {0.00002292f, 0.00078634f, 0.00655965f, 0.01330373f, 0.00655965f, 0.00078633f, 0.00002292f},
//            {0.00000067f, 0.00002292f, 0.00019117f, 0.00038771f, 0.00019117f, 0.00002292f, 0.00000067f}
//        };

        final float gaussianKernel[][] = new float[][]{
            {1/256f,4/256f,6/256f,4/256f,1/256f},
            {4/256f,16/256f,24/256f,16/256f,4/256f},
            {6/256f,24/256f,36/256f,24/256f,6/256f},
            {4/256f,16/256f,24/256f,16/256f,4/256f},
            {1/256f,4/256f,6/256f,4/256f,1/256f}};

        return applyConvolutionFilter(img, gaussianKernel);
    }

    private static BufferedImage applyConvolutionFilter(BufferedImage img, float[][] kernel) {
        BufferedImage output = new BufferedImage(img.getWidth(), img.getHeight(), img.getType());

        // Set output image from input image (img)
        for (int j = 0; j < img.getHeight(); ++j) {
            for (int i = 0; i < img.getWidth(); ++i) {
                // Calculate the gaussian blur
                Color pixelColor = applyKernel(img, kernel, i, j);
                output.setRGB(i, j, pixelColor.getRGB());
            }
        }

        return output;
    }

    private static Color applyKernel(BufferedImage img, float[][] kernel, int row, int column) {
        float red = 0.0f;
        float green = 0.0f;
        float blue = 0.0f;

        int minIndexH = -(kernel.length / 2);
        int maxIndexH = minIndexH + kernel.length;
        int minIndexV = -(kernel[0].length / 2);
        int maxIndexV = minIndexV + kernel[0].length;

        for (int i = minIndexH; i < maxIndexH; ++i) {
            for (int j = minIndexV; j < maxIndexV; ++j) {
                int indexH = wrapHorizontalIndex(img, row + i);
                int indexV = wrapVerticalIndex(img, column + j);
                Color col = new Color(img.getRGB(indexH, indexV));

                red += col.getRed() * kernel[i - minIndexH][j - minIndexV];
                green += col.getGreen() * kernel[i - minIndexH][j - minIndexV];
                blue += col.getBlue() * kernel[i - minIndexH][j - minIndexV];
            }
        }

        red = Math.min(Math.max(red, 0.0f), 255.0f);
        green = Math.min(Math.max(green, 0.0f), 255.0f);
        blue = Math.min(Math.max(blue, 0.0f), 255.0f);

        return new Color((int) red, (int) green, (int) blue);
    }

    private static int wrapHorizontalIndex(BufferedImage img, int i) {
        // This takes care of negative and positive indices beyond the image resolution
        return (i + img.getWidth()) % img.getWidth();
    }

    private static int wrapVerticalIndex(BufferedImage img, int i) {
        // This takes care of negative and positive indices beyond the image resolution
        return (i + img.getHeight()) % img.getHeight();
    }
}
