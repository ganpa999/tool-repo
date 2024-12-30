package org.example.util;

import org.apache.pdfbox.pdmodel.*;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class PDFComparer {
    private final String file1Path;
    private final String file2Path;
    private final String outputFilePath;

    public PDFComparer(String file1Path, String file2Path, String outputFilePath) {
        this.file1Path = file1Path;
        this.file2Path = file2Path;
        this.outputFilePath = outputFilePath;
    }

    public void compareAndGenerate() throws IOException {
        Map<String, String> data1 = extractData(file1Path);
        Map<String, String> data2 = extractData(file2Path);

        int differenceCount = 0;

        try (PDDocument document = PDDocument.load(new File(file2Path))) {
            PDPage page = document.getPage(0);
            PDPageContentStream contentStream = new PDPageContentStream(document, page, PDPageContentStream.AppendMode.APPEND, true, true);

            contentStream.setNonStrokingColor(255, 0, 0); // Red color for differences

            for (String key : data1.keySet()) {
                if (!data1.get(key).equals(data2.getOrDefault(key, ""))) {
                    highlightDifference(contentStream, key, data1.get(key), data2.getOrDefault(key, ""));
                    differenceCount++;
                }
            }

            contentStream.close();
            document.save(outputFilePath);
        }

        System.out.println("Total differences found: " + differenceCount);
    }

    private Map<String, String> extractData(String filePath) throws IOException {
        Map<String, String> data = new HashMap<>();
        try (PDDocument document = PDDocument.load(new File(filePath))) {
            PDFTextStripper stripper = new PDFTextStripper();
            String[] lines = stripper.getText(document).split("\n");

            for (String line : lines) {
                if (line.contains(":")) {
                    String[] parts = line.split(":", 2);
                    if (parts.length == 2) {
                        data.put(parts[0].trim(), parts[1].trim());
                    }
                }
            }
        }
        return data;
    }

    private void highlightDifference(PDPageContentStream contentStream, String key, String value1, String value2) throws IOException {
        float x = 100;
        float y = 700 - (key.hashCode() % 500); // Adjust for positioning dynamically
        contentStream.beginText();
        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
        contentStream.newLineAtOffset(x, y);
        contentStream.showText("Key: " + key + ", File1: " + value1 + ", File2: " + value2);
        contentStream.endText();
    }
}

