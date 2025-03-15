package code;

import image.APImage;
import image.Pixel;


public class ImageManipulation {
    /** CHALLENGE 0: Display Image
     *  Write a statement that will display the image in a window
     */

    public static void main(String[] args) {

        // Test Grayscale
//        grayScale("cyberpunk2077.jpg");

        // Test Black and White
//         blackAndWhite("cyberpunk2077.jpg");

        // Test Edge Detection (threshold=20)
//         edgeDetection("cyberpunk2077.jpg", 20);

        // Test Reflect Image
//         reflectImage("cyberpunk2077.jpg");

        // Test Rotate Image
//         rotateImage("cyberpunk2077.jpg");
    }
    /** CHALLENGE ONE: Grayscale
     *
     * INPUT: thme complete path file name of the image
     * OUTPUT: a grayscale copy of the image
     *
     * To convert a colour image to grayscale, we need to visit every pixel in the image ...
     * Calculate the average of the red, green, and blue components of the pixel.
     * Set the red, green, and blue components to this average value. */
    public static void grayScale(String pathOfFile) {
        APImage image = new APImage(pathOfFile);
        int width = image.getWidth();
        int height = image.getHeight();

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Pixel p = image.getPixel(x, y);
                int avg = getAverageColour(p);
                p.setRed(avg);
                p.setGreen(avg);
                p.setBlue(avg);
            }
        }

        image.draw();
    }
    /** A helper method that can be used to assist you in each challenge.
     * This method simply calculates the average of the RGB values of a single pixel.
     * @param pixel
     * @return the average RGB value
     */
    private static int getAverageColour(Pixel pixel) {
        return (pixel.getRed() + pixel.getGreen() + pixel.getBlue()) / 3;
    }

    /** CHALLENGE TWO: Black and White
     *
     * INPUT: the complete path file name of the image
     * OUTPUT: a black and white copy of the image
     *
     * To convert a colour image to black and white, we need to visit every pixel in the image ...
     * Calculate the average of the red, green, and blue components of the pixel.
     * If the average is less than 128, set the pixel to black
     * If the average is equal to or greater than 128, set the pixel to white */
    public static void blackAndWhite(String pathOfFile) {
        APImage image = new APImage(pathOfFile);
        int width = image.getWidth();
        int height = image.getHeight();

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Pixel p = image.getPixel(x, y);
                int avg = getAverageColour(p);
                if (avg < 128) {
                    p.setRed(0);
                    p.setGreen(0);
                    p.setBlue(0);
                } else {
                    p.setRed(255);
                    p.setGreen(255);
                    p.setBlue(255);
                }
            }
        }

        image.draw();
    }

    /** CHALLENGE Three: Edge Detection
     *
     * INPUT: the complete path file name of the image
     * OUTPUT: an outline of the image. The amount of information will correspond to the threshold.
     *
     * Edge detection is an image processing technique for finding the boundaries of objects within images.
     * It works by detecting discontinuities in brightness. Edge detection is used for image segmentation
     * and data extraction in areas such as image processing, computer vision, and machine vision.
     *
     * There are many different edge detection algorithms. We will use a basic edge detection technique
     * For each pixel, we will calculate ...
     * 1. The average colour value of the current pixel
     * 2. The average colour value of the pixel to the left of the current pixel
     * 3. The average colour value of the pixel below the current pixel
     * If the difference between 1. and 2. OR if the difference between 1. and 3. is greater than some threshold value,
     * we will set the current pixel to black. This is because an absolute difference that is greater than our threshold
     * value should indicate an edge and thus, we colour the pixel black.
     * Otherwise, we will set the current pixel to white
     * NOTE: We want to be able to apply edge detection using various thresholds
     * For example, we could apply edge detection to an image using a threshold of 20 OR we could apply
     * edge detection to an image using a threshold of 35
     *  */
    public static void edgeDetection(String pathToFile, int threshold) {
        APImage original = new APImage(pathToFile);
        APImage newImage = original.clone();
        int width = original.getWidth();
        int height = original.getHeight();

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Pixel currentPixel = original.getPixel(x, y);
                int currentAvg = getAverageColour(currentPixel);

                int leftAvg = currentAvg;
                if (x > 0) {
                    Pixel leftPixel = original.getPixel(x - 1, y);
                    leftAvg = getAverageColour(leftPixel);
                }

                int bottomAvg = currentAvg;
                if (y < height - 1) {
                    Pixel bottomPixel = original.getPixel(x, y + 1);
                    bottomAvg = getAverageColour(bottomPixel);
                }

                int diffLeft = Math.abs(currentAvg - leftAvg);
                int diffBottom = Math.abs(currentAvg - bottomAvg);

                Pixel newPixel = newImage.getPixel(x, y);
                if (diffLeft > threshold || diffBottom > threshold) {
                    newPixel.setRed(0);
                    newPixel.setGreen(0);
                    newPixel.setBlue(0);
                } else {
                    newPixel.setRed(255);
                    newPixel.setGreen(255);
                    newPixel.setBlue(255);
                }
            }
        }

        newImage.draw();
    }
    /** CHALLENGE Four: Reflect Image
     *
     * INPUT: the complete path file name of the image
     * OUTPUT: the image reflected about the y-axis
     *
     */
    public static void reflectImage(String pathToFile) {
        APImage image = new APImage(pathToFile);
        int width = image.getWidth();
        int height = image.getHeight();

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width / 2; x++) {
                Pixel left = image.getPixel(x, y);
                Pixel right = image.getPixel(width - 1 - x, y);
                Pixel temp = left.clone();
                image.setPixel(x, y, right.clone());
                image.setPixel(width - 1 - x, y, temp);
            }
        }

        image.draw();
    }

    /** CHALLENGE Five: Rotate Image
     *
     * INPUT: the complete path file name of the image
     * OUTPUT: the image rotated 90 degrees CLOCKWISE
     *
     *  */
    public static void rotateImage(String pathToFile) {
        APImage original = new APImage(pathToFile);
        int originalWidth = original.getWidth();
        int originalHeight = original.getHeight();

        APImage rotated = new APImage(originalHeight, originalWidth);

        for (int y = 0; y < originalHeight; y++) {
            for (int x = 0; x < originalWidth; x++) {
                Pixel p = original.getPixel(x, y);
                int newX = y;
                int newY = originalWidth - 1 - x;
                rotated.setPixel(newX, newY, p.clone());
            }
        }

        rotated.draw();
    }
}