package cn.wwq.poi;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;

public class PoiTest01 {
    public static void main(String[] args) throws Exception {

        //1.创建工作簿
        Workbook wb = new XSSFWorkbook();
        //2.创建表单 sheet
        Sheet sheet = wb.createSheet("wwq");
        //3.文件流
        FileOutputStream output = new FileOutputStream("D:\\poi\\wwq.xlsx");
        //4.写入文件
        wb.write(output);
        output.close();
    }
}
