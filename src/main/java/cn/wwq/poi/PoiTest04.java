package cn.wwq.poi;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileOutputStream;

public class PoiTest04 {
    public static void main(String[] args) throws Exception {

        //1.创建工作簿
        Workbook wb = new XSSFWorkbook();
        //2.创建表单 sheet
        Sheet sheet = wb.createSheet("wwq");
        //读取图片流
        FileInputStream in = new FileInputStream("D:\\poi\\1.jpg");
        //转化成二进制数组
        byte[] bytes = IOUtils.toByteArray(in);
        in.read(bytes);
        //向POI内存中添加一张图片，返回图片在图片集合中的索引
        //参数1： 二进制数组  参数2： 图片类型
        int index = wb.addPicture(bytes, Workbook.PICTURE_TYPE_JPEG);
        //绘制图片的工具类
        CreationHelper helper = wb.getCreationHelper();
        //创建一个绘图对象
        Drawing<?> drawing = sheet.createDrawingPatriarch();
        //创建锚点，设置图片的坐标
        ClientAnchor anchor = helper.createClientAnchor();
        anchor.setRow1(0);
        anchor.setCol1(0);
        //绘制图片 参数1：图片位置 参数2：图片索引
        Picture picture = drawing.createPicture(anchor, index);

        picture.resize();

        //3.文件流
        FileOutputStream output = new FileOutputStream("D:\\poi\\wwq3.xlsx");
        //4.写入文件
        wb.write(output);
        in.close();
        output.close();
    }
}
