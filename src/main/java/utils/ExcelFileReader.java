package utils;


import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.annotations.DataProvider;

import java.io.FileInputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;

public class ExcelFileReader {


    public String getExcelSheetName(Method method) throws NoSuchMethodException, SecurityException {
        String excelSheetName = null;
        PropertyFileReader propertyFileReader = new PropertyFileReader();
        excelSheetName = propertyFileReader.getProperty("testDataSheetName");
        return excelSheetName;
    }

    @DataProvider(
            name = "getExcelData"
    )
    public Object[][] getData(Method method) {
        int iterationcount = 0;
        XSSFWorkbook wb = null;
        Object[][] data = (Object[][]) null;
        try {
            FileInputStream fis = new FileInputStream("src/main/resources/TestData.xlsx");
            wb = new XSSFWorkbook(fis);
            XSSFSheet sheet = wb.getSheet(this.getExcelSheetName(method));

            int fullRowCount = sheet.getLastRowNum();
            int fullColumnCount = 0;
            String name = method.getName();
            int i;
            String testName;
            for (i = 0; i <= fullRowCount; ++i) {
                testName = sheet.getRow(i).getCell(0).getStringCellValue();
                if (name.equals(testName)) {
                    fullColumnCount = sheet.getRow(i - 1).getLastCellNum();
                    break;
                }
            }

            for (i = 0; i <= fullRowCount; ++i) {
                testName = sheet.getRow(i).getCell(0).getStringCellValue();
                if (name.equals(testName)) {
                    testName = sheet.getRow(i).getCell(1).getStringCellValue();
                    if (testName.equalsIgnoreCase("Yes")) {
                        ++iterationcount;
                    }
                }
            }

            data = new Object[iterationcount][1];
            ArrayList<Object> list = new ArrayList();
            int limiter;
            for (limiter = 0; limiter <= fullRowCount; ++limiter) {
                testName = sheet.getRow(limiter).getCell(0).getStringCellValue();
                if (name.equals(testName)) {
                    int j = 2;
                    while (true) {
                        if (j >= fullColumnCount)
                            break;
                        String columnName = sheet.getRow(limiter - 1).getCell(j).getStringCellValue().trim();
                        list.add(columnName);
                        ++j;
                    }
                }
            }
            limiter = 0;
            DataFormatter formatter = new DataFormatter();
            HashMap<Object, Object> map = null;
            i = 0;
            while (true) {
                if (i > fullRowCount)
                    break;
                testName = sheet.getRow(i).getCell(0).getStringCellValue();
                map = new HashMap();
                if (name.equals(testName)) {
                    String testCondition = sheet.getRow(i).getCell(1).getStringCellValue();
                    if (testCondition.equalsIgnoreCase("Yes")) {
                        for (int j = 2; j < fullColumnCount; ++j) {
                            try {
                                Cell cell = sheet.getRow(i).getCell(j);
                                String cellData = formatter.formatCellValue(cell);
                                map.put(list.get(j - 2), cellData);
                            } catch (NullPointerException np) {
                                np.printStackTrace();
                            }
                        }
                        data[limiter][0] = map;
                        ++limiter;
                    }
                }
                ++i;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }
}

