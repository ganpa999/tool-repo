package org.example.util;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
public class PDFComparator {


    public void comparePDFs(String pdf1Path, String pdf2Path, String outputPath) {
        try {
            PDDocument doc1 = PDDocument.load(new File(pdf1Path));
            PDDocument doc2 = PDDocument.load(new File(pdf2Path));

            PDFRenderer renderer1 = new PDFRenderer(doc1);
            PDFRenderer renderer2 = new PDFRenderer(doc2);

            BufferedImage image1 = renderer1.renderImage(0);
            BufferedImage image2 = renderer2.renderImage(0);

            ImageComparator imageComparator = new ImageComparator();
            BufferedImage resultImage = imageComparator.compareImages(image1, image2);

            // Save the result image
            ImageUtils.saveImage(resultImage, outputPath);

            // Print the difference details
            System.out.println("Total differences found: " + imageComparator.getDifferencesCount());
            System.out.println("Difference coordinates: " + imageComparator.getDifferenceCoordinates());

            doc1.close();
            doc2.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
