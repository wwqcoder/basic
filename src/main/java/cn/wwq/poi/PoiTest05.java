package cn.wwq.poi;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class PoiTest05 {
    public static void main(String[] args) throws Exception {

        //1.根据已有的excel创建工作簿
        Workbook wb = new XSSFWorkbook("D:\\poi\\demo.xlsx");
        //2.获取Sheet
        Sheet sheet = wb.getSheetAt(0); //索引
        //3.获取Sheet中的每行及单元格
        for (int rowNum = 0; rowNum <= sheet.getLastRowNum(); rowNum++) {
            //根据索引获取每一个行
            Row row = sheet.getRow(rowNum);
            StringBuilder sb = new StringBuilder();
            for (int cellNum = 2; cellNum < row.getLastCellNum(); cellNum++){
                //根据索引获取每一个单元格
                Cell cell = row.getCell(cellNum);
                //获取每个单元格的内容
                Object value = getCellValue(cell);
                sb.append(value).append("-");
            }
            System.out.println(sb.toString());

        }

    }

    private static Object getCellValue(Cell cell) {
        //获取到单元格的属性列表
        CellType cellType = cell.getCellType();
        //根据单元格数据类型获取数据
        Object value = null;
        switch (cellType){
            case STRING:
                value = cell.getStringCellValue();
                break;
            case BOOLEAN:
                value = cell.getBooleanCellValue();
                break;
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)){
                    //日期格式
                    value = cell.getDateCellValue();
                }else {
                    //数字
                    value = cell.getNumericCellValue();
                }
                break;
            case FORMULA:
                value = cell.getCellFormula();
                break;
            default:
                break;
        }
        return value;
    }
}
