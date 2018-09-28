package cn.mklaus.demo;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @author klaus
 * @date 2018/9/28 5:56 PM
 */
public class Excel {

    private static String FILE_PATH = "/Users/klaus/Desktop/poi.xls";

    public static void main(String[] args) throws IOException, InvalidFormatException {
        saveDemo();
        readDemo();
    }

    private static void saveDemo() throws IOException {
        // 创建工作薄
        HSSFWorkbook workbook = new HSSFWorkbook();
        // 创建工作表
        HSSFSheet sheet = workbook.createSheet("sheet1");

        HSSFRow hrow = sheet.createRow(0);
        hrow.createCell(0).setCellValue("姓名");
        hrow.createCell(1).setCellValue("性别");
        hrow.createCell(2).setCellValue("年龄");

        for (int i = 0; i < 3; i++) {
            HSSFRow row = sheet.createRow(i);
            row.createCell(0).setCellValue("name" + i);
            row.createCell(1).setCellValue("男");
            row.createCell(2).setCellValue(20 + i);
        }

        File xlsFile = new File(FILE_PATH);
        FileOutputStream xlsStream = new FileOutputStream(xlsFile);
        workbook.write(xlsStream);
    }

    private static void readDemo() throws IOException, InvalidFormatException {
        File file = new File(FILE_PATH);
        Workbook workbook = WorkbookFactory.create(file);

        // 获得工作表个数
        int sheetCount = workbook.getNumberOfSheets();
        // 遍历工作表
        for (int i = 0; i < sheetCount; i++) {
            Sheet sheet = workbook.getSheetAt(i);
            // 获得列数，先获得一行，在得到改行列数
            Row tmp = sheet.getRow(0);
            if (tmp == null) {
                continue;
            }

            // 获得行数
            int rows = sheet.getLastRowNum() + 1;
            int cols = tmp.getPhysicalNumberOfCells();
            // 读取数据
            for (int row = 0; row < rows; row++) {
                Row r = sheet.getRow(row);
                for (int col = 0; col < cols; col++) {
                    System.out.printf("%10s", r.getCell(col).toString());
                }
                System.out.println();
            }
        }
    }

}
