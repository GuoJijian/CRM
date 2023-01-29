import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.HorizontalAlignment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

public class PoiTest {
    public static void main(String[] args) {
        //创建一个HSSFWorkBook对象，对应一个excel文件
        HSSFWorkbook wb=new HSSFWorkbook();
        //创建一个HSSFSheet对象，对应一个页面
        HSSFSheet sheet=wb.createSheet("学生表");
        //创建一个风格
        HSSFCellStyle style=wb.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);
        //创建一个HSSFRow对象，对应行
        HSSFRow row=sheet.createRow(0);
        //创建一个HSSFCell对象，对应列
        HSSFCell cell = row.createCell(0);
        cell.setCellValue("学号");
        cell=row.createCell(1);
        cell.setCellValue("姓名");
        cell=row.createCell(2);
        cell.setCellValue("成绩");

        //插入十行数据
        for(int i=1;i<=10;i++){
            row=sheet.createRow(i);

            cell=row.createCell(0);
            cell.setCellValue(i);
            cell=row.createCell(1);
            cell.setCellValue("NAME"+i);
            cell=row.createCell(2);
            cell.setCellValue(50+i);
            cell.setCellStyle(style);
        }

        //调用工具方法输出
        try{
            OutputStream os=new FileOutputStream("D:\\作业\\测试\\StudentTable.xls");
            wb.write(os);
        }catch(Exception e){
            e.printStackTrace();
        }

    }
}
