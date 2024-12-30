package org.example.util;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PDFExtractor {
    public static List<String> extractTextFromPDF(String pdfPath) throws Exception {
        PDDocument document = PDDocument.load(new File(pdfPath));
        PDFTextStripper pdfStripper = new PDFTextStripper();
        String text = pdfStripper.getText(document);
        document.close();

        List<String> words = new ArrayList<>();
        for (String line : text.split("\\n")) {
            words.addAll(Arrays.asList(line.split("\\s+"))); // Split by whitespace
        }

        // Split text into lines and return as a list
//        return Arrays.asList(text.split("\\n"));
        return words;
    }
}
