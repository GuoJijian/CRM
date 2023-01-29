package com.guojijian.crm.commons.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 对时间类型进行处理
 */
public class DateUtils {

    /**
     *将时间类型进行格式化 ：yyyy-MM-dd HH-mm-ss
     */
    public static String dataFormatDataTime(Date date){
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
        String dateStr=sdf.format(date);
        return dateStr;
    }
}
