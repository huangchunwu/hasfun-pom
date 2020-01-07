package cn.hasfun.framework.java8;

import org.junit.Test;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * https://www.jianshu.com/p/19bd58b30660
 */
public class TimeExample {

    @Test
    public void testTime(){
        long time = System.currentTimeMillis();
        time = time/1000/60/5 * 1000 * 60 * 5 + 1000 * 60 * 5;

        DateTimeFormatter dateTimeFormatter =  DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(time), ZoneId.systemDefault());
        System.out.println(localDateTime.format(dateTimeFormatter));


        Timestamp ts=new Timestamp(time);
        System.out.println(new Date(time));
    }
}
