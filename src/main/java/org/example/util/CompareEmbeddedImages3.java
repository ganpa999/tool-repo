package org.example.util;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.*;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class CompareEmbeddedImages3 {
    public static void main(String[] args) {
        String tc1Path = "C:\\Ganesh\\backend-java\\Tool1\\src\\main\\resources\\tc1.xlsx"; // Update with the actual file path
        String tc2Path = "C:\\Ganesh\\backend-java\\Tool1\\src\\main\\resources\\tc2.xlsx"; // Update with the actual file path
        String outputPath = "C:\\Ganesh\\backend-java\\Tool1\\src\\main\\resources\\outputTC3.xlsx";

        try (FileInputStream fis1 = new FileInputStream(tc1Path);
             FileInputStream fis2 = new FileInputStream(tc2Path);
             XSSFWorkbook tc1Workbook = new XSSFWorkbook(fis1);
             XSSFWorkbook tc2Workbook = new XSSFWorkbook(fis2);
             XSSFWorkbook outputWorkbook = new XSSFWorkbook()) {

            // Create output sheet
            XSSFSheet outputSheet = outputWorkbook.createSheet("Comparison");
            Row headerRow = outputSheet.createRow(0);
            headerRow.createCell(0).setCellValue("Tc1 (Cells with Images)");
            headerRow.createCell(1).setCellValue("Tc2 (Cells with Images)");

            // Extract image-containing cells for both sheets
            String tc1ImageCells = getImageCells(tc1Workbook);
            String tc2ImageCells = getImageCells(tc2Workbook);

            // Write results to output sheet
            Row dataRow = outputSheet.createRow(1);
            dataRow.createCell(0).setCellValue(tc1ImageCells);
            dataRow.createCell(1).setCellValue(tc2ImageCells);

            // Write output workbook to file
            try (FileOutputStream fos = new FileOutputStream(outputPath)) {
                outputWorkbook.write(fos);
                System.out.println("Output file generated at: " + outputPath);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String getImageCells(XSSFWorkbook workbook) {
        StringBuilder cellsWithImages = new StringBuilder();

        for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
            XSSFSheet sheet = workbook.getSheetAt(i);
            if (sheet.getDrawingPatriarch() != null) {
                for (XSSFShape shape : sheet.getDrawingPatriarch().getShapes()) {
                    if (shape instanceof XSSFPicture) {
                        XSSFPicture picture = (XSSFPicture) shape;
                        XSSFClientAnchor anchor = picture.getPreferredSize();

                        int row = anchor.getRow1() + 1; // 1-based index
                        int col = anchor.getCol1();
                        String cellRef = CellReference.convertNumToColString(col) + row;

                        if (cellsWithImages.length() > 0) {
                            cellsWithImages.append(", ");
                        }
                        cellsWithImages.append(cellRef);
                    }
                }
            }
        }

        return cellsWithImages.toString();
    }
}
