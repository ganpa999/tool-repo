package org.example;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.example.util.ExcelWriter;
import org.example.util.PDFComparator;
import org.example.util.PDFComparer;
import org.example.util.PDFExtractor;

import java.io.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;


public class Main {

    private static final Logger LOGGER = Logger.getLogger(Main.class.getName());
    public static void main(String[] args) {

        try {
            LogManager.getLogManager().readConfiguration(Main.class.getResourceAsStream("/logging.properties"));
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error reading logging configuration", e);
        }

        LOGGER.info("MainClass started");
        // Paths for PDF files and output Excel
        String pdf1Path = "C:\\Ganesh\\backend-java\\Tool1\\src\\main\\resources\\sample1.pdf";
        String pdf2Path = "C:\\Ganesh\\backend-java\\Tool1\\src\\main\\resources\\sample2.pdf";
        String outputExcelPath = "C:\\Ganesh\\backend-java\\Tool1\\src\\main\\resources\\outputExcel.xlsx";
        String outputPdfPath = "C:\\Ganesh\\backend-java\\Tool1\\src\\main\\resources\\outputPdf.pdf";
        String pdf3Path = "C:\\Ganesh\\backend-java\\Tool1\\src\\main\\resources\\Sample11.pdf";
        String pdf4Path= "C:\\Ganesh\\backend-java\\Tool1\\src\\main\\resources\\sample12.pdf";
        String outputPNG= "C:\\Ganesh\\backend-java\\Tool1\\src\\main\\resources\\output.png";

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

        PDFComparator comparator = new PDFComparator();
        comparator.comparePDFs(pdf3Path, pdf4Path, outputPNG);

        LOGGER.info("MainClass completed");
    }
}
