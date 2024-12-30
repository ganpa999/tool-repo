package org.example.util;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.util.List;
public class tryblock {
    public static void writeDataToExcel(List<String> column1Data, List<String> column2Data, String outputPath) throws Exception {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Data");

        int rowCount = Math.max(column1Data.size(), column2Data.size());

        for (int i = 0; i < rowCount; i++) {
            Row row = sheet.createRow(i);

            String cell1Value = i < column1Data.size() ? column1Data.get(i) : "";
            String cell2Value = i < column2Data.size() ? column2Data.get(i) : "";

            // Column 1
            Cell cell1 = row.createCell(0);
            cell1.setCellValue(cell1Value);

            // Column 2
            Cell cell2 = row.createCell(1);
            cell2.setCellValue(cell2Value);

            // Column 3 (Difference)
            Cell cell3 = row.createCell(2);
            String difference = calculateDifference(cell1Value, cell2Value);
            cell3.setCellValue(difference);
        }

        try (FileOutputStream fos = new FileOutputStream(outputPath)) {
            workbook.write(fos);
        }

        workbook.close();
    }

    private static String calculateDifference(String cell1, String cell2) {
        StringBuilder difference = new StringBuilder();

        String[] words1 = cell1.split("\\s+");
        String[] words2 = cell2.split("\\s+");

        // Identify words in cell1 not in cell2
        for (String word : words1) {
            if (!cell2.contains(word)) {
                if (difference.length() > 0) difference.append(" ");
                difference.append("- ").append(word);
            }
        }

        // Identify words in cell2 not in cell1
        for (String word : words2) {
            if (!cell1.contains(word)) {
                if (difference.length() > 0) difference.append(" ");
                difference.append("+ ").append(word);
            }
        }

        return difference.toString();
    }

    public static void main(String[] args) throws Exception {
        List<String> column1Data = List.of("john matin", "john matin", "john","john","89987");
        List<String> column2Data = List.of("john peter", "john", "john peter","john","100000");
        String outputPath = "output.xlsx";

        writeDataToExcel(column1Data, column2Data, outputPath);
    }
}
