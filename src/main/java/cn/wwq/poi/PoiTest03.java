package cn.wwq.poi;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;

/**
 * 为单元格内容添加样式
 */
public class PoiTest03 {
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

        //样式处理
        //创建样式对象
        CellStyle cellStyle = wb.createCellStyle();
        cellStyle.setBorderTop(BorderStyle.THIN); //上边框
        cellStyle.setBorderLeft(BorderStyle.THIN); //左边框
        cellStyle.setBorderRight(BorderStyle.THIN); // 右边框
        cellStyle.setBorderBottom(BorderStyle.THIN); //下边框

        //创建字体对象
        Font font = wb.createFont();
        font.setFontName("华文行楷"); //字体
        font.setFontHeightInPoints((short)28);  //字号
        cellStyle.setFont(font);

        //行高
        row.setHeightInPoints(50);
        //列宽宽度 字符宽度
        sheet.setColumnWidth(2,31 * 256);

        cellStyle.setAlignment(HorizontalAlignment.CENTER); // 水平居中
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER); //垂直居中
        //向单元格设置样式
        cell.setCellStyle(cellStyle);



        //3.文件流
        FileOutputStream output = new FileOutputStream("D:\\poi\\wwq2.xlsx");
        //4.写入文件
        wb.write(output);
        output.close();
    }
}
