package test.动态创建管理定时任务;

/**
 * @创建人 zhaojingen
 * @创建时间 2019/10/29
 * @描述
 */
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.quartz.CronExpression;

public class DateToCronUtil {
    private static final String DATEFORMAT = "ss mm HH dd MM ? yyyy";

    /***
     *
     * @param date
     * @param dateFormat : e.g:yyyy-MM-dd HH:mm:ss
     * @return
     * @throws ParseException
     */
    public static String formatDateByPattern(Date date, String dateFormat) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        String formatTimeStr = null;
        if (date != null) {
            formatTimeStr = sdf.format(date);
        }

        if (checkCronValid(formatTimeStr, date)) {
            return formatTimeStr;
        } else {
            return "输入有效日期";
        }
    }

    /***
     * convert Date to cron ,eg. "0 06 10 15 1 ? 2014"
     *
     * @param date : 时间点
     * @return
     * @throws ParseException
     */
    public static String getCron(Date date) throws ParseException {
        return formatDateByPattern(date, DATEFORMAT);
    }

    /**
     * 验证是否有效的cron表达式
     *
     * @param cron
     * @param date
     * @throws ParseException
     */
    private static boolean checkCronValid(String cron, Date date) throws ParseException {

        CronExpression cronExpression = new CronExpression(cron);
        return cronExpression.isSatisfiedBy(date);
    }

//    public static void main(String[] args) throws ParseException {
//        System.out.println(getCron(new Date()));
//    }
}
