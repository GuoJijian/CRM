package com.guojijian.crm.commons.util;

import org.apache.poi.hssf.usermodel.HSSFCell;

//处理excel文本的工具类
public class HSSFUtils {
    public static String getCellValue(HSSFCell cell){
        String result=null;
        if(cell.getCellType()==cell.CELL_TYPE_NUMERIC){
            result=cell.getNumericCellValue()+"";
        }else if(cell.getCellType()==cell.CELL_TYPE_STRING){
            result=cell.getStringCellValue();
        }else if(cell.getCellType()==cell.CELL_TYPE_FORMULA){
            result=cell.getStringCellValue();
        }else if(cell.getCellType()==cell.CELL_TYPE_BOOLEAN){
            result=cell.getBooleanCellValue()+"";
        }else if(cell.getCellType()==cell.CELL_TYPE_ERROR){
            result=cell.getErrorCellValue()+"";
        }
        return result;
    }
}
