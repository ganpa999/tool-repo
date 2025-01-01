package org.example.util;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.FileOutputStream;
import java.util.List;
import java.util.logging.Logger;

public class ExcelWriter {

    private static final Logger LOGGER = Logger.getLogger(ExcelWriter.class.getName());
    public static void writeDataToExcel(List<String> column1Data, List<String> column2Data, String outputPath) throws Exception {
        LOGGER.info("ExcelClass is performing a task");
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Data");

        int rowCount = Math.max(column1Data.size(), column2Data.size());

//        for (int i = 0; i < rowCount; i++) {
//            Row row = sheet.createRow(i);
//
//            if (i < column1Data.size()) {
//                Cell cell1 = row.createCell(0);
//                cell1.setCellValue(column1Data.get(i));
//            }
//
//            if (i < column2Data.size()) {
//                Cell cell2 = row.createCell(1);
//                cell2.setCellValue(column2Data.get(i));
//            }
//        }

        int totalDiffernces=0;

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
            if(difference.length()>0){
            totalDiffernces++;
            }
            cell3.setCellValue(difference);

            Cell cell4 = row.createCell(3);
            cell4.setCellValue(totalDiffernces);
        }

        try (FileOutputStream fos = new FileOutputStream(outputPath)) {
            workbook.write(fos);
        }

        System.out.println(totalDiffernces);

        workbook.close();
    }

    private static String calculateDifference(String cell1, String cell2) {
        StringBuilder difference = new StringBuilder();

        String[] words1 = cell1.split("\\s+");
        String[] words2 = cell2.split("\\s+");

        // Identify words in cell1 not in cell2
        for (String word : words1) {
            if (!cell2.contains(word)) {
                if (difference.length() > 0) difference.append(", ");
                difference.append("- ").append(word);
            }
        }

        // Identify words in cell2 not in cell1
        for (String word : words2) {
            if (!cell1.contains(word)) {
                if (difference.length() > 0) difference.append(", ");
                difference.append("+ ").append(word);
            }
        }

        return difference.toString();
    }
}
