package org.learnless.chap12;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.*;
import java.util.Calendar;
import java.util.Date;

import static java.time.temporal.TemporalAdjusters.lastDayOfMonth;
import static java.time.temporal.TemporalAdjusters.next;

/**
 * Java8 新的日期和时间API
 * Created by learnless on 18.2.6.
 */
public class DateTimeExamples {
    private static final ThreadLocal<DateFormat> formatters = new ThreadLocal<DateFormat>() {
        protected DateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd HH:MM:ss");
        }
    };

    public static void main(String[] args) {
//        useOldDate();   /*使用Java8之前*/
//        useLocalDate(); /*Java8日期API*/
//        useLocalTime(); /*Java8时间API*/
//        exercise(); /*练习*/
//        useTemporalAdjuster();
//        useMyTemporalAdjuster();
        useDateFormatter();
    }

    /**
     * 实现这样一个功能，计算明天日期，过滤掉周末
     */
    private static void useMyTemporalAdjuster() {
        //实现方式1.以实现TemporalAdjuster接口
        LocalDate date = LocalDate.of(2018, 2, 9);
        LocalDate result = date.with(new NextWorkingDay());

        //实现方式2.lambda表达式
        LocalDate result2 = date.with(temporal -> {
            int add = 1;
            DayOfWeek now = DayOfWeek.of(temporal.get(ChronoField.DAY_OF_WEEK));
            if (now == DayOfWeek.FRIDAY) {
                add = 3;
            } else if (now == DayOfWeek.SATURDAY) {
                add = 2;
            }

            return temporal.plus(add, ChronoUnit.DAYS);
        });

        //3.可以定义temporalAdjuster对象，方便调用
        TemporalAdjuster temporalAdjuster = TemporalAdjusters.ofDateAdjuster(temporal -> {
            int add = 1;
            DayOfWeek now = DayOfWeek.of(temporal.get(ChronoField.DAY_OF_WEEK));
            if (now == DayOfWeek.FRIDAY) {
                add = 3;
            } else if (now == DayOfWeek.SATURDAY) {
                add = 2;
            }

            return temporal.plus(add, ChronoUnit.DAYS);
        });
        LocalDate result3 = date.with(temporalAdjuster);

        System.out.println(result);
        System.out.println(result2);
        System.out.println(result3);
    }

    private static void exercise() {
        LocalDate date = LocalDate.of(2018, 2, 16);
        //直观方式操作
        LocalDate date2 = date.withYear(2019);  //2019-02-16
        LocalDate date3 = date2.withMonth(5);  //2019-05-16
        LocalDate date4 = date3.with(ChronoField.DAY_OF_MONTH, 4);  //2019-05-04

        //相对方式修改
        LocalDate date5 = date.plusYears(2);    //+
        LocalDate date6 = date.plus(2, ChronoUnit.YEARS);

        //LocalTime LocalDateTime操作基本一致
        LocalDateTime dateTime = LocalDateTime.of(LocalDate.now(), LocalTime.now());
        LocalDateTime dateTime2 = dateTime.minus(2, ChronoUnit.MINUTES);    //-

    }

    private static void useDateFormatter() {
        //自定义格式化
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime dateTime = LocalDateTime.of(LocalDate.now(), LocalTime.now());

        //格式化format
        String format = dateTime.format(DateTimeFormatter.BASIC_ISO_DATE);  //20180206
        String format1 = dateTime.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);    //2018-02-06T20:35:29.627
        String format2 = dateTime.format(formatter);    //2018-02-06 20:38:44

        //解析parse
        LocalDateTime parse = LocalDateTime.parse("2018-02-06T20:35:29", DateTimeFormatter.ISO_DATE_TIME);
        LocalDateTime parse1 = LocalDateTime.parse("2018-02-06 20:45:53", formatter);

        System.out.println();
    }

    private static void useTemporalAdjuster() {
        LocalDate date = LocalDate.now();
        LocalDate date2 = date.with(lastDayOfMonth());
        LocalDate date3 = date.with(next(DayOfWeek.MONDAY));    //下周一

        System.out.println();
    }

    private static void useLocalTime() {
        LocalTime now = LocalTime.now();    //当前时间
        System.out.println(now);    //18:15:26.941
        LocalTime time = LocalTime.of(20, 30, 10);  //构造自己时间
        System.out.println(time);   //20:30:10

        int hour = time.getHour();
        int minute = time.getMinute();
        int second = time.getSecond();

        int hour2 = time.get(ChronoField.HOUR_OF_DAY);
        int minutes = time.get(ChronoField.MINUTE_OF_HOUR);
        int second2 = time.get(ChronoField.SECOND_OF_MINUTE);

        LocalTime parse = LocalTime.parse("18:15:26");  //解析时间

        //合并日期和时间
        LocalDateTime now1 = LocalDateTime.now();   //当前时间
        LocalDateTime dateTime = LocalDateTime.of(2018, 2, 28, 8, 30, 10);
        //跟LocalDate LocalTime操作基本一致

        LocalDate date = LocalDate.now();
        //多种方式创建
        LocalDateTime of = LocalDateTime.of(date, time);  //推荐
        LocalDateTime of2 = date.atTime(time);
        LocalDateTime of3 = date.atTime(20, 30, 10);
        LocalDateTime of4 = time.atDate(date);

        //有LocalDateTime提取LocalDate LocalTime
        LocalDate date1 = of.toLocalDate();
        LocalTime time1 = of.toLocalTime();

        //机器的日期和时间
        Instant instant = Instant.now();
        Instant instant1 = Instant.ofEpochSecond(3);
        Instant instant2 = Instant.ofEpochSecond(3, 0);
        Instant instant3 = Instant.ofEpochSecond(2, 1_000_000_000);//两秒之后加上1秒
        Instant instant4 = Instant.ofEpochSecond(3, -1_000_000_000);//三秒之前的1秒

//        int i = Instant.now().get(ChronoField.YEAR);    //error,机器时间不支持ChronoField,可使用Duration Period

        //Duration 不能创建日期 Period 只能创建日期
        Duration duration = Duration.between(now, time);
        long seconds = duration.getSeconds();
        Duration duration1 = Duration.between(instant, instant1);

        Period period = Period.between(LocalDate.of(2018, 2, 6), LocalDate.of(2018, 3, 7));
        int periodDays = period.getDays();

        //创建Duration period对象
        Duration dt1 = Duration.of(2, ChronoUnit.HOURS);
        Duration dt2 = Duration.ofHours(2);

        Period period1 = Period.of(1, 3, 10);   //一年三个月十天
        Period period2 = Period.ofYears(1);
        period2 = period2.plusMonths(3);
        period2 = period2.plusDays(10);

        System.out.println();
    }

    private static void useLocalDate() {
        LocalDate now = LocalDate.now();    //当前日期
        System.out.println(now);    //默认格式 2018-02-06
        LocalDate date = LocalDate.of(2018, 2, 6); //构造自己日期
        System.out.println(date);

        int year = date.getYear();
        Month month = date.getMonth();
        int day = date.getDayOfMonth();
        DayOfWeek dayOfWeek = date.getDayOfWeek();
        int lengthOfMonth = date.lengthOfMonth();
        boolean leapYear = date.isLeapYear();
        System.out.println(leapYear);

        //使用TemporalField接口
        int year2 = date.get(ChronoField.YEAR);
        int month2 = date.get(ChronoField.MONTH_OF_YEAR);
        int day2 = date.get(ChronoField.DAY_OF_MONTH);

        LocalDate parse = LocalDate.parse("2018-03-10");   //解析日期
        System.out.println(parse);
    }

    private static void useOldDate() {
        Date date = new Date(118, 2, 3);    //2018-03-03
        System.out.println(date);
        String format = formatters.get().format(date);
        System.out.println(format);

        //太难用
        Calendar calendar = Calendar.getInstance();
        calendar.set(2018, Calendar.FEBRUARY, 10);
        System.out.println(calendar);
        System.out.println(calendar.getWeekYear() + "-" + calendar.MONTH + "-" + calendar.DAY_OF_YEAR);
    }


}

class NextWorkingDay implements TemporalAdjuster {

    @Override
    public Temporal adjustInto(Temporal temporal) {
        int add = 1;
        DayOfWeek now = DayOfWeek.of(temporal.get(ChronoField.DAY_OF_WEEK));
        if (now == DayOfWeek.FRIDAY) {
            add = 3;
        } else if (now == DayOfWeek.SATURDAY) {
            add = 2;
        }

        return temporal.plus(add, ChronoUnit.DAYS);
    }
}