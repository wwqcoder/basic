package cn.wwq.poi;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;

/**
 * 创建单元格内容
 */
public class PoiTest02 {
    public static void main(String[] args) throws Exception {

        //1.创建工作簿
        Workbook wb = new XSSFWorkbook();
        //2.创建表单 sheet
        Sheet sheet = wb.createSheet("wwq");
        //2,1 创建行对象 参数: 索引从0开始
        Row row = sheet.createRow(2);
        //2.2 创建单元格对象 参数： 索引从0开始
        Cell cell = row.createCell(2);
        //向单元格中添加数据
        cell.setCellValue("王玮琦");
        //3.文件流
        FileOutputStream output = new FileOutputStream("D:\\poi\\wwq1.xlsx");
        //4.写入文件
        wb.write(output);
        output.close();
    }
}
