package com.sohel.mpower.qrgen;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Hashtable;

import javax.imageio.ImageIO;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

/**
 * @author Sohel.Sarder, sohel.sarder@gmail.com
 */

public class QRGenerator {

	public static String embedCodeFile = "/home/sohel/QRID.txt";
	public static String QRSavingFolderPath = "/home/sohel/QR/";
	public static String fileType = "png";
	public static int size = 125;

	private static void QRCodeGenerator(String myCodeText) {

		String filePath = QRSavingFolderPath + myCodeText + ".png";

		File myFile = new File(filePath);
		try {
			Hashtable<EncodeHintType, ErrorCorrectionLevel> hintMap = new Hashtable<EncodeHintType, ErrorCorrectionLevel>();
			hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
			QRCodeWriter qrCodeWriter = new QRCodeWriter();
			BitMatrix byteMatrix = qrCodeWriter.encode(myCodeText,
					BarcodeFormat.QR_CODE, size, size, hintMap);
			int SohelWidth = byteMatrix.getWidth();
			BufferedImage image = new BufferedImage(SohelWidth, SohelWidth,
					BufferedImage.TYPE_INT_RGB);
			image.createGraphics();

			Graphics2D graphics = (Graphics2D) image.getGraphics();
			graphics.setColor(Color.WHITE);
			graphics.fillRect(0, 0, SohelWidth, SohelWidth);
			graphics.setColor(Color.BLACK);

			for (int i = 0; i < SohelWidth; i++) {
				for (int j = 0; j < SohelWidth; j++) {
					if (byteMatrix.get(i, j)) {
						graphics.fillRect(i, j, 1, 1);
					}
				}
			}
			ImageIO.write(image, fileType, myFile);
		} catch (WriterException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out
		.println("\n\nYou have successfully created QR Code. codeText:"
				+ myCodeText);
	}

	public static void main(String[] args) throws IOException {
		FileInputStream fstream;
		try {
			fstream = new FileInputStream(embedCodeFile);
			BufferedReader br = new BufferedReader(new InputStreamReader(
					fstream));
			String strLine;
			// Read File Line By Line
			while ((strLine = br.readLine()) != null) {
				// Print the content on the console
				QRCodeGenerator(strLine);
			}
			// Close the input stream
			br.close();
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}
}
