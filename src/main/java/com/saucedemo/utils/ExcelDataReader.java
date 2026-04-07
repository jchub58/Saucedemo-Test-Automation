package com.saucedemo.utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExcelDataReader {
    
    private static final String EXCEL_FILE_PATH = "test-cases/testCases.xlsx";
    
    public static List<Map<String, String>> getTestData() {
        List<Map<String, String>> testData = new ArrayList<>();
        
        try (FileInputStream fis = new FileInputStream(EXCEL_FILE_PATH);
             Workbook workbook = new XSSFWorkbook(fis)) {
            
            Sheet sheet = workbook.getSheetAt(0);
            Row headerRow = sheet.getRow(0);
            
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row != null) {
                    Map<String, String> rowData = new HashMap<>();
                    for (int j = 0; j < headerRow.getLastCellNum(); j++) {
                        Cell headerCell = headerRow.getCell(j);
                        Cell dataCell = row.getCell(j);
                        
                        String header = getCellValue(headerCell);
                        String value = getCellValue(dataCell);
                        
                        rowData.put(header, value);
                    }
                    testData.add(rowData);
                }
            }
            
        } catch (IOException e) {
            throw new RuntimeException("Failed to read Excel file: " + EXCEL_FILE_PATH, e);
        }
        
        return testData;
    }
    
    public static Map<String, String> getTestCaseById(String testCaseId) {
        List<Map<String, String>> allData = getTestData();
        
        for (Map<String, String> row : allData) {
            if (testCaseId.equals(row.get("Test Case ID"))) {
                return row;
            }
        }
        
        throw new RuntimeException("Test case with ID " + testCaseId + " not found");
    }
    
    private static String getCellValue(Cell cell) {
        if (cell == null) {
            return "";
        }
        
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue().trim();
            case NUMERIC:
                return String.valueOf((int) cell.getNumericCellValue());
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            case FORMULA:
                return cell.getCellFormula();
            default:
                return "";
        }
    }
}
