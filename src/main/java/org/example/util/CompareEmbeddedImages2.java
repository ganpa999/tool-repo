package org.example.util;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.PictureData;

import java.io.*;
import java.util.*;

public class CompareEmbeddedImages2 {

    public static void main(String[] args) throws IOException, InvalidFormatException {
        // Load input files
        FileInputStream file1 = new FileInputStream("C:\\Ganesh\\backend-java\\Tool1\\src\\main\\resources\\tc1.xlsx");
        FileInputStream file2 = new FileInputStream("C:\\Ganesh\\backend-java\\Tool1\\src\\main\\resources\\tc2.xlsx");

        // Read Excel files
        XSSFWorkbook workbook1 = new XSSFWorkbook(file1);
        XSSFWorkbook workbook2 = new XSSFWorkbook(file2);

        // Get sheet names or indexes (assuming same sheet structure)
        XSSFSheet sheet1 = workbook1.getSheetAt(0);
        XSSFSheet sheet2 = workbook2.getSheetAt(0);

        // Compare images in both sheets
        List<String> imagesInTc1 = findImagesInSheet(sheet1);
        List<String> imagesInTc2 = findImagesInSheet(sheet2);

        // Create output workbook and sheet
        XSSFWorkbook outputWorkbook = new XSSFWorkbook();
        Sheet outputSheet = outputWorkbook.createSheet("Image Differences");

        // Create header row
        Row header = outputSheet.createRow(0);
        header.createCell(0).setCellValue("tc1 Images");
        header.createCell(1).setCellValue("tc2 Images");

        // Write image cell locations to output sheet
        int rowNum = 1;
        int maxSize = Math.max(imagesInTc1.size(), imagesInTc2.size());

        for (int i = 0; i < maxSize; i++) {
            Row row = outputSheet.createRow(rowNum++);
            if (i < imagesInTc1.size()) {
                row.createCell(0).setCellValue(imagesInTc1.get(i));
            }
            if (i < imagesInTc2.size()) {
                row.createCell(1).setCellValue(imagesInTc2.get(i));
            }
        }

        // Save the output Excel file
        try (FileOutputStream outputStream = new FileOutputStream("output_images_comparison.xlsx")) {
            outputWorkbook.write(outputStream);
        }

        System.out.println("Comparison complete. Output saved to output_images_comparison.xlsx");
    }

    // Method to find all the cells with embedded images
    public static List<String> findImagesInSheet(Sheet sheet) {
        List<String> imageCells = new ArrayList<>();
        Workbook wb = sheet.getWorkbook();
        for (int i = 0; i < wb.getAllPictures().size(); i++) {
            PictureData pictureData = wb.getAllPictures().get(i);
            // Extract image details here
            for (int rowNum = 0; rowNum < sheet.getPhysicalNumberOfRows(); rowNum++) {
                Row row = sheet.getRow(rowNum);
                for (int cellNum = 0; cellNum < row.getPhysicalNumberOfCells(); cellNum++) {
                    Cell cell = row.getCell(cellNum);
                    // Here we can use POI's drawing interface to check embedded images
                    if (isImageCell(cell)) {
                        String cellReference = new CellReference(cell).formatAsString();
                        imageCells.add(cellReference);
                    }
                }
            }
        }
        return imageCells;
    }

    // Placeholder method for checking if a cell contains an image
    public static boolean isImageCell(Cell cell) {
        // Logic to check for image in cell. This could involve checking cell metadata or drawing objects.
        return false;
    }
}
