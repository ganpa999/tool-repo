package org.example;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.example.util.ExcelWriter;
import org.example.util.PDFComparer;
import org.example.util.PDFExtractor;

import java.io.*;
import java.util.*;


public class Main {
    public static void main(String[] args) {
        // Paths for PDF files and output Excel
        String pdf1Path = "C:\\Ganesh\\backend-java\\Tool1\\src\\main\\resources\\sample1.pdf";
        String pdf2Path = "C:\\Ganesh\\backend-java\\Tool1\\src\\main\\resources\\sample2.pdf";
        String outputExcelPath = "C:\\Ganesh\\backend-java\\Tool1\\src\\main\\resources\\outputExcel.xlsx";
        String outputPdfPath = "C:\\Ganesh\\backend-java\\Tool1\\src\\main\\resources\\outputPdf.pdf";

        try {
            // Extract data from PDFs
            List<String> dataFromPdf1 = PDFExtractor.extractTextFromPDF(pdf1Path);
            List<String> dataFromPdf2 = PDFExtractor.extractTextFromPDF(pdf2Path);

            // Write data to Excel
            ExcelWriter.writeDataToExcel(dataFromPdf1, dataFromPdf2, outputExcelPath);

            System.out.println("Data successfully written to " + outputExcelPath);

            PDFComparer comparer = new PDFComparer(pdf1Path, pdf2Path, outputPdfPath);
            comparer.compareAndGenerate();

            System.out.println("Data successfully written to " + outputPdfPath);
            System.out.println("data successfully written for both cases.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
