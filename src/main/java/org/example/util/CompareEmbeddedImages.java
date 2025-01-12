package org.example.util;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;
import java.io.*;
import java.util.*;

public class CompareEmbeddedImages {
    public static void main(String[] args) {
        String tc1Path = "C:\\Ganesh\\backend-java\\Tool1\\src\\main\\resources\\tc1.xlsx"; // Update with the actual file path
        String tc2Path = "C:\\Ganesh\\backend-java\\Tool1\\src\\main\\resources\\tc2.xlsx"; // Update with the actual file path
        String outputPath = "C:\\Ganesh\\backend-java\\Tool1\\src\\main\\resources\\outputTC1.xlsx";

        try {
            Map<String, List<String>> tc1Images = extractImageCells(tc1Path);
            Map<String, List<String>> tc2Images = extractImageCells(tc2Path);

            writeComparisonResult(tc1Images, tc2Images, outputPath);
            System.out.println("Comparison completed. Output saved to " + outputPath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Map<String, List<String>> extractImageCells(String filePath) throws IOException {
        Map<String, List<String>> imageCells = new HashMap<>();

        try (XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream(filePath))) {
            for (int sheetIndex = 0; sheetIndex < workbook.getNumberOfSheets(); sheetIndex++) {
                XSSFSheet sheet = workbook.getSheetAt(sheetIndex);
                String sheetName = sheet.getSheetName();

                List<XSSFPictureData> pictures = workbook.getAllPictures();
                List<String> cellAddresses = new ArrayList<>();

                for (XSSFPictureData pictureData : pictures) {
                    XSSFDrawing drawing = sheet.createDrawingPatriarch();
                    for (XSSFShape shape : drawing.getShapes()) {
                        if (shape instanceof XSSFPicture) {
                            XSSFPicture picture = (XSSFPicture) shape;
                            XSSFClientAnchor anchor = picture.getClientAnchor();
                            String cellAddress = anchor.getRow1() + ":" + anchor.getCol1();
                            cellAddresses.add(cellAddress);
                        }
                    }
                }
                imageCells.put(sheetName, cellAddresses);
            }
        }
        return imageCells;
    }

    private static void writeComparisonResult(Map<String, List<String>> tc1Images,
                                              Map<String, List<String>> tc2Images,
                                              String outputPath) throws IOException {
        try (XSSFWorkbook outputWorkbook = new XSSFWorkbook()) {
            XSSFSheet outputSheet = outputWorkbook.createSheet("Comparison");

            Row headerRow = outputSheet.createRow(0);
            headerRow.createCell(0).setCellValue("Sheet");
            headerRow.createCell(1).setCellValue("TC1 (Cells with Images)");
            headerRow.createCell(2).setCellValue("TC2 (Cells with Images)");

            int rowIndex = 1;
            for (String sheetName : tc1Images.keySet()) {
                Row row = outputSheet.createRow(rowIndex++);
                row.createCell(0).setCellValue(sheetName);
                row.createCell(1).setCellValue(String.join(", ", tc1Images.getOrDefault(sheetName, new ArrayList<>())));
                row.createCell(2).setCellValue(String.join(", ", tc2Images.getOrDefault(sheetName, new ArrayList<>())));
            }

            try (FileOutputStream fos = new FileOutputStream(outputPath)) {
                outputWorkbook.write(fos);
            }
        }
    }
}
