package com.cbsys.saleexplore.util;

import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageUtil {

	/**
	 * Crop the image first by considering the ratio of cropRWidth / cropRheight, starting from the center
	 * Resize the image to scale to (cropRWidth, cropRheight)
	 * @param inputImagePath Path of the original image
	 * @param outputImagePath Path to save the resized image
	 * @param cropRWidth absolute width in pixels
	 * @param cropRheight absolute height in pixels
	 * @throws IOException
	 */
	public static void cropResize(String inputImagePath, String outputImagePath,
								  int cropRWidth, int cropRheight) throws IOException {
		// reads input image
		File inputFile = new File(inputImagePath);
		BufferedImage inputImage = ImageIO.read(inputFile);

		// Get image dimensions
		int originalHeight = inputImage.getHeight();
		int originalWidth = inputImage.getWidth();

		// Coordinates of the image's middle
		int oriXMiddle = originalWidth / 2;
		int oriYMiddle = originalHeight / 2;

		float scaleRatio = ((float)cropRWidth / cropRheight);
		float originalRatio = ((float)originalWidth / originalHeight);

		int finalHeight, finalWidth, xc, yc;
		if(originalRatio > scaleRatio){
			// original file is too wide, use its original height
			finalHeight = originalHeight;
			finalWidth = (int)(originalHeight * scaleRatio);
			xc = oriXMiddle - finalWidth / 2;
			yc = 0;
		}else{
			// original file is too high, use its original width
			finalWidth = originalWidth;
			finalHeight = (int)(originalWidth / scaleRatio);
			xc = 0;
			yc = oriYMiddle - finalHeight / 2;
		}

		// Crop
		BufferedImage croppedImage = inputImage.getSubimage(
				xc, // x coordinate of the upper-left corner
				yc, // y coordinate of the upper-left corner
				finalWidth,            // widht
				finalHeight             // height
		);

		resize(croppedImage, outputImagePath, cropRWidth, cropRheight);
	}



	/**
	 * Resizes an image to a absolute width and height (the image may not be
	 * proportional). This may scale or squeeze the image
	 * @param inputImagePath Path of the original image
	 * @param outputImagePath Path to save the resized image
	 * @param scaledWidth absolute width in pixels
	 * @param scaledHeight absolute height in pixels
	 * @throws IOException
	 */
	public static void resize(String inputImagePath,
							  String outputImagePath, int scaledWidth, int scaledHeight)
			throws IOException {
		// reads input image
		File inputFile = new File(inputImagePath);
		BufferedImage inputImage = ImageIO.read(inputFile);
		resize(inputImage, outputImagePath, scaledWidth, scaledHeight);
	}


	/**
	 * Resizes an image by a percentage of original size (proportional).
	 * @param inputImagePath Path of the original image
	 * @param outputImagePath Path to save the resized image
	 * @param percent a double number specifies percentage of the output image
	 * over the input image.
	 * @throws IOException
	 */
	public static void resize(String inputImagePath,
							  String outputImagePath, double percent) throws IOException {
		File inputFile = new File(inputImagePath);
		BufferedImage inputImage = ImageIO.read(inputFile);
		int scaledWidth = (int) (inputImage.getWidth() * percent);
		int scaledHeight = (int) (inputImage.getHeight() * percent);
		resize(inputImagePath, outputImagePath, scaledWidth, scaledHeight);
	}


	/**
	 * Resizes an image to a absolute width and height (the image may not be
	 * proportional). This may scale or squeeze the image
	 * @param inputImage BufferedImage of the image
	 * @param outputImagePath Path to save the resized image
	 * @param scaledWidth absolute width in pixels
	 * @param scaledHeight absolute height in pixels
	 * @throws IOException
	 */
	public static void resize(BufferedImage inputImage, String outputImagePath,
							   int scaledWidth, int scaledHeight) throws IOException{

		// creates output image
		BufferedImage outputImage = new BufferedImage(scaledWidth,
				scaledHeight, inputImage.getType());

		// scales the input image to the output image
		Graphics2D g2d = outputImage.createGraphics();
		g2d.drawImage(inputImage, 0, 0, scaledWidth, scaledHeight, null);
		g2d.dispose();

		// extracts extension of output file
		String formatName = outputImagePath.substring(outputImagePath
				.lastIndexOf(".") + 1);

		// writes to output file
		ImageIO.write(outputImage, formatName, new File(outputImagePath));
	}


}
