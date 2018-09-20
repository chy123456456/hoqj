package com.keng.base.task;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import org.quartz.CronTrigger;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleTrigger;
import org.quartz.Trigger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

/**
 * Task上下文管理类
 * 
 * @date 2015-7-3 下午10:31:23
 * @version V1.0
 * @Copyright <b>Copyright (c)</b> 2015 微赢互动-版权所有
 * 
 * @Description
 * 
 */
public class TaskContext extends SchedulerFactoryBean {
    private static final Logger          logger                 = LoggerFactory.getLogger(TaskContext.class);
    /** 默认的JOB_GROUP */
    private static final String          DEF_JOB_GROUP          = "TASKS";
    /** 默认的TRIGGER_GROUP */
    private static final String          DEF_TRIGGER_GROUP      = "TASKS";
    public static final SimpleDateFormat DATE_FORMAT            = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static final SimpleDateFormat DATE_FORMAT_COMPACT    = new SimpleDateFormat("yyyyMMddHHmmss");
    /**
     * <p>
     * Used to indicate the 'repeat count' of the trigger is indefinite. Or in other words, the trigger should repeat
     * continually until the trigger's ending timestamp.
     * </p>
     */
    public static final int              REPEAT_INDEFINITELY    = -1;
    public static final int              SUNDAY                 = 1;
    public static final int              MONDAY                 = 2;
    public static final int              TUESDAY                = 3;
    public static final int              WEDNESDAY              = 4;
    public static final int              THURSDAY               = 5;
    public static final int              FRIDAY                 = 6;
    public static final int              SATURDAY               = 7;
    public static final int              LAST_DAY_OF_MONTH      = -1;
    public static final long             MILLISECONDS_IN_MINUTE = 60L * 1000L;
    public static final long             MILLISECONDS_IN_HOUR   = 60L * 60L * 1000L;
    public static final long             SECONDS_IN_DAY         = 24L * 60L * 60L;
    public static final long             MILLISECONDS_IN_DAY    = SECONDS_IN_DAY * 1000L;

    /**
     * 添加一次性任务
     * 
     * @param when
     * @param clazz
     * @param params
     * @return true 表示添加成功. false 表示触发器重复,已经有一个相同的触发器在工作中.
     * @throws SchedulerException
     */
    public <T> boolean addTask(Date when, Class<T> clazz, Object... params) throws SchedulerException {
        Scheduler scheduler = getScheduler();
        String jobName = clazz.getSimpleName();
        addJob(clazz, jobName, scheduler);
        JobDataMap jobDataMap = new JobDataMap();
        for (int i = 0; i < params.length; i += 2) {
            Object key = params[i];
            Object value = params[i + 1];
            jobDataMap.put(key, value);
        }
        Trigger trigger = addTrigger(when, 0, 0, jobName, jobDataMap, scheduler);
        return trigger != null;
    }

    /**
     * 添加一次性任务 廖江红 修改添加 BusinessOrder30Task-1445222440306_订单号_0
     * @param when
     * @param clazz
     * @param params
     * @return true 表示添加成功. false 表示触发器重复,已经有一个相同的触发器在工作中.
     * @throws SchedulerException
     */
    public <T> boolean addTaskOrderNo(Date when, long orderNo, Class<T> clazz, Object... params) throws SchedulerException {
        Scheduler scheduler = getScheduler();
        String jobName = clazz.getSimpleName();
        addJob(clazz, jobName, scheduler);
        JobDataMap jobDataMap = new JobDataMap();
        for (int i = 0; i < params.length; i += 2) {
            Object key = params[i];
            Object value = params[i + 1];
            jobDataMap.put(key, value);
        }
        Trigger trigger = addTrigger(when, 0, orderNo, jobName, jobDataMap, scheduler);
        return trigger != null;
    }

    /**
     * 添加一次性任务
     * 
     * @param when
     * @param clazz
     * @param params
     * @return true 表示添加成功. false 表示触发器重复,已经有一个相同的触发器在工作中.
     * @throws SchedulerException
     */
    public <T> boolean addTask(Date when, Class<T> clazz, @SuppressWarnings("rawtypes") Map params) throws SchedulerException {
        Scheduler scheduler = getScheduler();
        String jobName = clazz.getSimpleName();
        addJob(clazz, jobName, scheduler);
        JobDataMap jobDataMap = new JobDataMap(params);
        Trigger trigger = addTrigger(when, 0, 0, jobName, jobDataMap, scheduler);
        return trigger != null;
    }

    /**
     * 添加重复执行任务
     * 
     * @param when
     * @param repeatCount
     * @param repeatInterval
     * @param clazz
     * @param params
     * @return true 表示添加成功. false 表示触发器重复,已经有一个相同的触发器在工作中.
     * @throws SchedulerException
     */
    public <T> boolean addTask(Date when, int repeatCount, long repeatInterval, Class<T> clazz, @SuppressWarnings("rawtypes") Map params)
            throws SchedulerException {
        Scheduler scheduler = getScheduler();
        String jobName = clazz.getSimpleName();
        addJob(clazz, jobName, scheduler);
        JobDataMap jobDataMap = new JobDataMap(params);
        Trigger trigger = addTrigger(when, repeatCount, repeatInterval, jobName, jobDataMap, scheduler);
        return trigger != null;
    }

    /**
     * 添加重复执行的任务，使用cron表达式触发执行。
     * 
     * @param cronExpression
     * @param clazz
     * @param params
     * @return true 表示添加成功. false 表示触发器重复,已经有一个相同的触发器在工作中.
     * @throws SchedulerException
     * @throws ParseException
     */
    public <T> boolean addTask(String cronExpression, Class<T> clazz, @SuppressWarnings("rawtypes") Map params) throws SchedulerException,
            ParseException {
        Scheduler scheduler = getScheduler();
        String jobName = clazz.getSimpleName();
        addJob(clazz, jobName, scheduler);
        JobDataMap jobDataMap = new JobDataMap(params);
        Trigger trigger = addTrigger(cronExpression, new Date(), null, jobName, jobDataMap, scheduler);
        return trigger != null;
    }

    /**
     * 添加重复执行的任务，使用cron表达式触发执行，可控制开始和结束时间。
     * 
     * @param cronExpression
     * @param start
     * @param end
     * @param clazz
     * @param params
     * @return true 表示添加成功. false 表示触发器重复,已经有一个相同的触发器在工作中.
     * @throws SchedulerException
     * @throws ParseException
     */
    public <T> boolean addTask(String cronExpression, Date start, Date end, Class<T> clazz, @SuppressWarnings("rawtypes") Map params)
            throws SchedulerException, ParseException {
        Scheduler scheduler = getScheduler();
        String jobName = clazz.getSimpleName();
        addJob(clazz, jobName, scheduler);
        JobDataMap jobDataMap = new JobDataMap(params);
        Trigger trigger = addTrigger(cronExpression, start, end, jobName, jobDataMap, scheduler);
        return trigger != null;
    }

    /**
     * 移除重复执行的任务计划
     * 
     * @param when
     * @param repeatInterval
     * @param repeatCount
     * @param clazz
     * @throws SchedulerException
     */
    public <T> boolean delTask(Date when, long repeatInterval, int repeatCount, Class<T> clazz) throws SchedulerException {
        Scheduler scheduler = getScheduler();
        String jobName = clazz.getSimpleName();
        String triggerName = new StringBuffer(jobName).append('-').append(when.getTime()).append('_').append(repeatInterval).append('_')
                .append(repeatCount).toString();
        return scheduler.unscheduleJob(triggerName, DEF_TRIGGER_GROUP);
    }

    /**
     * 移除cron表达式任务计划
     * 
     * @param cronExpression
     * @param clazz
     * @throws SchedulerException
     */
    public <T> boolean delTask(String cronExpression, Class<T> clazz) throws SchedulerException {
        Scheduler scheduler = getScheduler();
        String jobName = clazz.getSimpleName();
        String triggerName = jobName + '-' + cronExpression.replace(' ', '_');
        return scheduler.unscheduleJob(triggerName, DEF_TRIGGER_GROUP);
    }

    /**
     * 计算下一次触发时间，以天为基线
     * 
     * @param date time时间信息（其中只有时分秒信息有效）
     * @return
     */
    public static Date nextFireOfDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        Date now = new Date();
        calendar.setTime(now);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        calendar.setTime(date);
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        if (now.compareTo(calendar.getTime()) >= 0) {// 今天的触发点已过
            calendar.add(Calendar.DAY_OF_MONTH, 1); // 增量到明天的同一时刻
        }
        return calendar.getTime();
    }

    /**
     * 解析日期字符串
     * 
     * @param source
     * @return
     * @throws ParseException
     */
    public static Date parse(String source) throws ParseException {
        return DATE_FORMAT.parse(source);
    }

    /**
     * 解析日期字符串
     * 
     * @param source
     * @return
     * @throws ParseException
     */
    public static Date parseCompact(String source) throws ParseException {
        return DATE_FORMAT_COMPACT.parse(source);
    }

    /**
     * 每小时
     * 
     * @param date
     * @return
     */
    public static String hourly(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int minute = calendar.get(Calendar.MINUTE);
        int second = calendar.get(Calendar.SECOND);
        return hourly(minute, second);
    }

    /**
     * 每天
     * 
     * @param date
     * @return
     */
    public static String daily(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        return daily(hour, minute);
    }

    /**
     * 每周
     * 
     * @param date
     * @return
     */
    public static String weekly(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        return weekly(dayOfWeek, hour, minute);
    }

    /**
     * 每月
     * 
     * @param date
     * @return
     */
    public static String monthly(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        return monthly(dayOfMonth, hour, minute);
    }

    /**
     * 每小时
     * 
     * @param minute
     * @param second
     * @return
     */
    public static String hourly(int minute, int second) {
        validateMinute(minute);
        validateSecond(second);
        return new StringBuffer().append(second).append(' ').append(minute).append(" * ? * *").toString();
    }

    /**
     * 每天
     * 
     * @param hour
     * @param minute
     * @return
     */
    public static String daily(int hour, int minute) {
        validateHour(hour);
        validateMinute(minute);
        return new StringBuffer("0 ").append(minute).append(' ').append(hour).append(" ? * *").toString();
    }

    /**
     * 每个星期
     * 
     * @param dayOfWeek
     * @param hour
     * @param minute
     * @return
     */
    public static String weekly(int dayOfWeek, int hour, int minute) {
        validateDayOfWeek(dayOfWeek);
        validateHour(hour);
        validateMinute(minute);
        return new StringBuffer("0 ").append(minute).append(' ').append(hour).append(" ? * ").append(dayOfWeek).toString();
    }

    /**
     * 每个月
     * 
     * @param dayOfMonth
     * @param hour
     * @param minute
     * @return
     */
    public static String monthly(int dayOfMonth, int hour, int minute) {
        validateDayOfMonth(dayOfMonth);
        validateHour(hour);
        validateMinute(minute);
        return new StringBuffer("0 ").append(minute).append(' ').append(hour).append(dayOfMonth != LAST_DAY_OF_MONTH ? dayOfMonth : 'L')
                .append(" * ?").toString();
    }

    /**
     * 向Scheduler中添加JobDetail
     * 
     * @param clazz
     * @param jobName
     * @param scheduler
     * @return
     * @throws SchedulerException
     */
    public static <T> JobDetail addJob(Class<T> clazz, String jobName, Scheduler scheduler) throws SchedulerException {
        JobDetail jobDetail = scheduler.getJobDetail(jobName, DEF_JOB_GROUP);
        if (jobDetail == null) {
            jobDetail = new JobDetail(jobName, DEF_JOB_GROUP, clazz);
            jobDetail.setDurability(true);
            scheduler.addJob(jobDetail, false);
        } else {
            Class<?> jobClass = jobDetail.getJobClass();
            if (!jobClass.equals(clazz)) {
                throw new RuntimeException("class [" + clazz.getName() + "] name collide with [" + jobClass.getName() + "].");
            }
        }
        return jobDetail;
    }

    /**
     * 向Scheduler中添加CronTrigger
     * 
     * @param cronExpression
     * @param start
     * @param end
     * @param jobName
     * @param params
     * @param scheduler
     * @return
     * @throws SchedulerException
     * @throws ParseException
     */
    public static CronTrigger addTrigger(String cronExpression, Date start, Date end, String jobName, JobDataMap jobDataMap, Scheduler scheduler)
            throws SchedulerException, ParseException {
        String triggerName = jobName + '-' + cronExpression.replace(' ', '_');
        CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerName, DEF_TRIGGER_GROUP);
        if (trigger == null) {
            trigger = new CronTrigger(triggerName, DEF_TRIGGER_GROUP, jobName, DEF_JOB_GROUP, start, end, cronExpression);
            if (jobDataMap != null) trigger.setJobDataMap(jobDataMap);
        } else {
            logger.warn("trigger has already exists. triggerName = {}", triggerName);
            return null;
        }
        scheduler.scheduleJob(trigger);
        return trigger;
    }

    /**
     * 向Scheduler中添加SimpleTrigger
     * 
     * @param when
     * @param repeatCount
     * @param repeatInterval
     * @param jobName
     * @param params
     * @param scheduler
     * @return
     * @throws SchedulerException
     */
    public static SimpleTrigger addTrigger(Date when, int repeatCount, long repeatInterval, String jobName, JobDataMap jobDataMap, Scheduler scheduler)
            throws SchedulerException {
        String triggerName = new StringBuffer(jobName).append('-').append(when.getTime()).append('_').append(repeatInterval).append('_')
                .append(repeatCount).toString();
        SimpleTrigger trigger = (SimpleTrigger) scheduler.getTrigger(triggerName, DEF_TRIGGER_GROUP);
        if (trigger == null) {
            trigger = new SimpleTrigger(triggerName, DEF_TRIGGER_GROUP, jobName, DEF_JOB_GROUP, when, null, repeatCount, repeatInterval);
            if (jobDataMap != null) trigger.setJobDataMap(jobDataMap);
        } else {
            logger.warn("trigger has already exists. triggerName = {}", triggerName);
            return null;
        }
        scheduler.scheduleJob(trigger);
        //启动
        /*if(!scheduler.isShutdown()){  
            scheduler.start();  
        } */
        return trigger;
    }

    /**
     * 从Scheduler中移除CronTrigger
     * 
     * @param cronExpression
     * @param jobName
     * @param scheduler
     * @throws SchedulerException
     */
    public static void delTrigger(String cronExpression, String jobName, Scheduler scheduler) throws SchedulerException {
        String triggerName = jobName + '-' + cronExpression.replace(' ', '_');
        scheduler.unscheduleJob(triggerName, DEF_TRIGGER_GROUP);
    }

    /**
     * 从Scheduler中移除SimpleTrigger
     * 
     * @param when
     * @param repeatInterval
     * @param repeatCount
     * @param jobName
     * @param scheduler
     * @throws SchedulerException
     */
    public static void delTrigger(Date when, long repeatInterval, int repeatCount, String jobName, Scheduler scheduler) throws SchedulerException {
        String triggerName = new StringBuffer(jobName).append('-').append(when.getTime()).append('_').append(repeatInterval).append('_')
                .append(repeatCount).toString();
        scheduler.unscheduleJob(triggerName, DEF_TRIGGER_GROUP);
    }

    public static void validateDayOfWeek(int dayOfWeek) {
        if (dayOfWeek < SUNDAY || dayOfWeek > SATURDAY) {
            throw new IllegalArgumentException("Invalid day of week.");
        }
    }

    public static void validateHour(int hour) {
        if (hour < 0 || hour > 23) {
            throw new IllegalArgumentException("Invalid hour (must be >= 0 and <= 23).");
        }
    }

    public static void validateMinute(int minute) {
        if (minute < 0 || minute > 59) {
            throw new IllegalArgumentException("Invalid minute (must be >= 0 and <= 59).");
        }
    }

    public static void validateSecond(int second) {
        if (second < 0 || second > 59) {
            throw new IllegalArgumentException("Invalid second (must be >= 0 and <= 59).");
        }
    }

    public static void validateDayOfMonth(int day) {
        if ((day < 1 || day > 31) && day != LAST_DAY_OF_MONTH) {
            throw new IllegalArgumentException("Invalid day of month.");
        }
    }

    public static void validateMonth(int month) {
        if (month < 1 || month > 12) {
            throw new IllegalArgumentException("Invalid month (must be >= 1 and <= 12.");
        }
    }

    public static void validateYear(int year) {
        if (year < 1970 || year > 2099) {
            throw new IllegalArgumentException("Invalid year (must be >= 1970 and <= 2099.");
        }
    }
    // public <T> void addOneceTask(String when, Class<T> clazz, @SuppressWarnings("rawtypes") Map params)
    // throws ParseException, SchedulerException {
    // Date date = DEF_DATE_FORMAT.parse(when);
    // addOneceTask(date, clazz, params);
    // }
    //
    // public <T> void addEveryDayTask(String when, Class<T> clazz, @SuppressWarnings("rawtypes") Map params)
    // throws ParseException, SchedulerException {
    // Date date = DEF_DATE_FORMAT.parse("0000-00-00 " + when);
    // addEveryDayTask(date, clazz, params);
    // }
    //
    // public <T> void addEveryDayTask(Date when, Class<T> clazz, @SuppressWarnings("rawtypes") Map params)
    // throws SchedulerException, ParseException {
    // Scheduler scheduler = getScheduler();
    // String jobName = clazz.getSimpleName();
    //
    // JobDetail jobDetail = scheduler.getJobDetail(jobName, DEF_JOB_GROUP);
    // if (jobDetail == null) {
    // jobDetail = new JobDetail(jobName, DEF_JOB_GROUP, clazz);
    // scheduler.addJob(jobDetail, false);
    // }
    //
    // String cronExpression = makeEveryDayCronExpression(when);
    // String triggerName = jobName + '-' + cronExpression.replace(' ', '_');
    // Trigger trigger = scheduler.getTrigger(triggerName, DEF_TRIGGER_GROUP);
    // if (trigger == null) {
    // trigger = new CronTrigger(triggerName, DEF_TRIGGER_GROUP, jobName, DEF_JOB_GROUP, cronExpression);
    // trigger.setStartTime(new Date());
    // trigger.getJobDataMap().putAll(params);
    // } else {
    // throw new RuntimeException("trigger has already exists.");
    // }
    // scheduler.scheduleJob(trigger);
    // }
    // public <T> void addOneceTask(Date when, Class<T> clazz, @SuppressWarnings("rawtypes") Map params)
    // throws SchedulerException, ParseException {
    // Scheduler scheduler = getScheduler();
    // String jobName = clazz.getSimpleName();
    //
    // JobDetail jobDetail = scheduler.getJobDetail(jobName, DEF_JOB_GROUP);
    // if (jobDetail == null) {
    // jobDetail = new JobDetail(jobName, DEF_JOB_GROUP, clazz);
    // scheduler.addJob(jobDetail, false);
    // }
    //
    // String cronExpression = makeCronExpression(when);
    // String triggerName = jobName + '-' + cronExpression.replace(' ', '_');
    // Trigger trigger = scheduler.getTrigger(triggerName, DEF_TRIGGER_GROUP);
    // if (trigger == null) {
    // // new CronTrigger(triggerName, DEF_TRIGGER_GROUP, cronExpression);
    // trigger = new CronTrigger(triggerName, DEF_TRIGGER_GROUP, jobName, DEF_JOB_GROUP, new Date(), when,
    // cronExpression);
    // trigger.getJobDataMap().putAll(params);
    // } else {
    // throw new RuntimeException("trigger has already exists.");
    // }
    // scheduler.scheduleJob(trigger);
    // }
    // public <T> void delEveryDayTask(Date when, Class<T> clazz) throws SchedulerException {
    // Scheduler scheduler = getScheduler();
    // String jobName = clazz.getSimpleName();
    // String cronExpression = makeEveryDayCronExpression(when);
    // String triggerName = jobName + '-' + cronExpression.replace(' ', '_');
    //
    // // CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerName, DEF_TRIGGER_GROUP);
    // scheduler.unscheduleJob(triggerName, DEF_TRIGGER_GROUP);
    // }
    // public static String makeCronExpression(Date when) {
    // Calendar calendar = Calendar.getInstance();
    // calendar.setTime(when);
    //
    // int year = calendar.get(Calendar.YEAR);
    // int month = calendar.get(Calendar.MONTH) + 1;
    // int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
    // int hour = calendar.get(Calendar.HOUR_OF_DAY);
    // int minute = calendar.get(Calendar.MINUTE);
    // int second = calendar.get(Calendar.SECOND);
    //
    // StringBuffer sb = new StringBuffer();
    // sb.append(second).append(' ').append(minute).append(' ').append(hour).append(' ').append(dayOfMonth).append(' ')
    // .append(month).append(" ? ").append(year);
    // return sb.toString();
    // }
    //
    // public static String makeEveryDayCronExpression(Date when) {
    // Calendar calendar = Calendar.getInstance();
    // calendar.setTime(when);
    //
    // int hour = calendar.get(Calendar.HOUR_OF_DAY);
    // int minute = calendar.get(Calendar.MINUTE);
    // int second = calendar.get(Calendar.SECOND);
    //
    // StringBuffer sb = new StringBuffer();
    // sb.append(second).append(' ').append(minute).append(' ').append(hour).append(" * * ?");
    // return sb.toString();
    // }
    //
    // public static void main(String[] args) {
    // System.out.println(makeCronExpression(new Date()));
    // }
    // public static class Crons {
    // public static Crons everyDay(Date when) {
    // return new Crons(when);
    // }
    //
    // private Date date;
    //
    // private Crons(Date date) {
    // super();
    // this.date = date;
    // }
    //
    // }
}
