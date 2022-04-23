package com.migration.migration.utils;


import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

import javax.imageio.ImageIO;

import org.apache.tika.Tika;
import org.springframework.stereotype.Service;

import com.migration.migration.exception.ImageValidationException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ImageUtils {

	private static final Long IMAGE_FILE_SIZE_MAX_LIMIT = 100000L;
	private static final String IMAGE_PNG_EXTENSION = "jpeg";
	private static final String IMAGE_JPEG_EXTENSION = "png";
	private static final String IMAGE_JPG_EXTENSION = "jpg";
	private static final int IMAGE_WIDTH_MIN_LIMIT = 150;
	private static final int IMAGE_WIDTH_MAX_LIMIT = 350;
	private static final int IMAGE_HEIGHT_MIN_LIMIT = 100;
	private static final int IMAGE_HEIGHT_MAX_LIMIT = 250;

	/*
	 * java.util.zip defalter and inflater
	 * https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/util/zip/
	 * Deflater.html
	 */
	public static byte[] compress(byte[] data) {
		Deflater deflater = new Deflater();
		deflater.setInput(data);
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
		deflater.finish();
		byte[] buffer = new byte[1024];
		while (!deflater.finished()) {
			int count = deflater.deflate(buffer);
			outputStream.write(buffer, 0, count);
		}
		try {
			outputStream.close();
		} catch (Exception exp) {
			log.error("Exception occured while compressing the image. ", exp);
		}
		byte[] output = outputStream.toByteArray();
		log.trace("original uncompressed image {} kb", data.length / 1024);
		log.trace("compressed image size{} kb", output.length / 1024);
		return output;
	}

	public static byte[] decompress(byte[] data) {
		Inflater inflater = new Inflater();
		inflater.setInput(data);
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
		byte[] buffer = new byte[1024];
		while (!inflater.finished()) {
			int count = 0;
			try {
				count = inflater.inflate(buffer);
				if (count <= 0)
					break;
			} catch (DataFormatException e) {
				break;
			}
			outputStream.write(buffer, 0, count);
		}
		try {
			outputStream.close();
		} catch (IOException exp) {
			log.error("Exception occured while decompressing the image. ", exp);
		}
		byte[] output = outputStream.toByteArray();
		log.trace("original compressed image {} kb", data.length / 1024);
		log.trace("decompressed image size {} kb", output.length / 1024);
		return output;
	}

	public static boolean imageValidation(String value) {
		boolean isValid = true;
		String message = "Exception occured while validating the image.";
		byte[] decodedBytes = Base64.getDecoder().decode(value.getBytes());
		log.trace("the size of image received {} :", decodedBytes.length);
		if (decodedBytes.length > IMAGE_FILE_SIZE_MAX_LIMIT) {
			isValid= false;
			message = "Invalid file size. image size should not be greater than 100KB";
		}
		String contentType = new Tika().detect(decodedBytes);
		String imageExtension = Arrays.stream(contentType.split("/")).collect(Collectors.toList()).get(1);
		List<String> imageExtensions = Arrays.asList(IMAGE_PNG_EXTENSION, IMAGE_JPEG_EXTENSION, IMAGE_JPG_EXTENSION);
		if (!imageExtensions.contains(imageExtension)) {
			message = "Please upload a valid image (JPEG/PNG/JPG) file";
			isValid = false;
		}
		ByteArrayInputStream bais = new ByteArrayInputStream(decodedBytes);
		BufferedImage convertedImage = null;
		try {
			convertedImage = ImageIO.read(bais);
			int imageHeight = convertedImage.getHeight();
			int imageWidth = convertedImage.getWidth();
			if ((imageWidth < IMAGE_WIDTH_MIN_LIMIT || imageWidth > IMAGE_WIDTH_MAX_LIMIT)
					&& (imageHeight < IMAGE_HEIGHT_MIN_LIMIT || IMAGE_HEIGHT_MAX_LIMIT > 250)) {
				message = "Invalid file size. image size should be Length: Min: 150 Pixels, Max 350 Pixels Width: Min 100 Pixels, Max 250 Pixels";
				isValid = false;
			}

		} catch (Exception exp) {
			log.error("Exception occured while validating the image. ", exp);
			isValid = false;
		}
		if (!isValid) {
			throw new ImageValidationException(message);
		}
		log.trace("the width of image received {} :", convertedImage.getWidth());
		log.trace("the height of image received {} :", convertedImage.getHeight());
		return isValid;
	}

}