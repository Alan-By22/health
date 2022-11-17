package com.ithuipu;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @author 11752
 * @创建人 zby
 * @创建时间 2022/11/17---14:11
 * @描述信息
 */

public class PoiDemo {

    /**
     * 解析
     */
    @Test
    public void demo1() throws IOException {
        //加载
        Workbook workbook = new XSSFWorkbook("D:\\health.xlsx");
        Sheet sheetAt = workbook.getSheetAt(0);
        for (Row row : sheetAt) {
            for (Cell cell : row) {
                //获取数据
                String stringCellValue = cell.getStringCellValue();
                System.out.println(stringCellValue);
            }
        }
        //释放资源
        workbook.close();
    }

    @Test
    public void demo2() throws IOException {
        //在内存中创建一个Excel
        Workbook workbook = new XSSFWorkbook();
        //创建工作表,指定工作表的名称
        Sheet sheet = workbook.createSheet("ithuipu");
        //创建行,0表示第一行
        Row row = sheet.createRow(0);
        //创建单元格,0表示第一个单元格
        row.createCell(0).setCellValue("编号");
        row.createCell(1).setCellValue("姓名");
        row.createCell(2).setCellValue("年龄");
        row.createCell(3).setCellValue("地址");
        Row row1 = sheet.createRow(1);
        row1.createCell(0).setCellValue("1");
        row1.createCell(1).setCellValue("李四");
        row1.createCell(2).setCellValue("24");
        row1.createCell(3).setCellValue("西安");
        Row row2 = sheet.createRow(2);
        row2.createCell(0).setCellValue("2");
        row2.createCell(1).setCellValue("赵六");
        row2.createCell(2).setCellValue("27");
        row2.createCell(3).setCellValue("北京");
        Row row3 = sheet.createRow(3);
        row3.createCell(0).setCellValue("3");
        row3.createCell(1).setCellValue("李明");
        row3.createCell(2).setCellValue("28");
        row3.createCell(3).setCellValue("上海");

        //通过输出流将workbook对象下载到磁盘
        FileOutputStream fileOutputStream = new FileOutputStream("D:\\ithuipuMessage.xlsx");
        workbook.write(fileOutputStream);
        fileOutputStream.flush();
        fileOutputStream.close();
        workbook.close();
    }


}
