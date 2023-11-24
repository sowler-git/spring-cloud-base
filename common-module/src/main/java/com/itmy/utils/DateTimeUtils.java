package com.itmy.utils;

import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

/**
 * <p>
 * 时间通用工具类
 * </p>
 *
 * @author chenchen
 * @since 2019-08-06 10:07 AM
 */

public class DateTimeUtils {

	public final static Long ONE_DAY_IN_MILLS = 24 * 60 * 60 * 1000L;

	public final static Long ONE_WEEK_IN_MILLS = 7 * ONE_DAY_IN_MILLS;

	private final static Long EIGHT_HOURS_IN_MILLS = 8 * 60 * 60 * 1000L;

	public final static Long DAY_IN_MILLS_1080 = 1080 * ONE_DAY_IN_MILLS;

	public final static Long NANO_PER_MILLS = 1000000L;

	public final static int MIN_DAY_OF_MONTH = 28;

	/**
	 * 一分钟多少毫秒
	 */
	private static final Long SEC = 60 * 1000L;

	/**
	 * 日期时间格式
	 */
	public static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

	/**
	 * 日期时间格式，不格式化秒
	 */
	public static final String DATE_MINUTE_TIME_FORMAT = "yyyy-MM-dd HH:mm";

	/**
	 * 日期格式
	 */
	public static final String DATE_FORMAT = "yyyy-MM-dd";

	/**
	 * 时间格式（分钟级）
	 */
	public static final String MINUTE_TIME_FORMAT = "HH:mm";

	/**
	 * 取当天凌晨时间戳
	 * @return
	 */
	public static Long getStartOfDay() {
		LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(utcNow()), ZoneId.systemDefault());
		LocalDateTime endOfDay = localDateTime.with(LocalTime.MIN);
		return endOfDay.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
	}

	/**
	 * 这个跟 LocalDateTime .now(ZoneOffset.UTC) .toInstant(ZoneOffset.UTC) .toEpochMilli()
	 * 是一个效果
	 * @return
	 */
	public static long utcNow() {
		return System.currentTimeMillis();
	}

	/**
	 * 获取当前时间的秒级的时间戳
	 * @return
	 */
	public static long utcNowForSecond() {
		long now = System.currentTimeMillis();
		return  (now / 1000)  * 1000;
	}

	/**
	 * 获取当前时间的分钟级的时间戳 向上取整
	 * @return
	 */
	public static long utcNowForMinute() {
		long now = System.currentTimeMillis();
		if ((now % (60 * 1000)) == 0) {
			return  (now / (60 * 1000)) * 60 * 1000;
		}
		return  (now / (60 * 1000)) * 60 * 1000 + 60 * 1000;
	}

	public static Long getEndOfDay() {
		LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(utcNow()), ZoneId.systemDefault());
		LocalDateTime endOfDay = localDateTime.with(LocalTime.MAX);
		return endOfDay.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
	}

	/**
	 * 获取某个时区0点的时间戳
	 * @param timeZone
	 * @return
	 */
	public static Long getStartOfDay(String timeZone) {
		if (StringUtils.isEmpty(timeZone)) {
			timeZone = "+08:00";
		}
		LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(utcNow()), ZoneId.of(timeZone));
		LocalDateTime startOfDay = localDateTime.with(LocalTime.MIN);
		return startOfDay.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
	}

	public static Long getMillisByMins(int duration) {
		return duration * SEC;
	}

	public static Long getEndOfDay(Long utcDateTime) {
		LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(utcDateTime),
				ZoneId.systemDefault());
		// LocalDateTime endOfDay = localDateTime.with(LocalTime.MAX);
		LocalDateTime endOfDay = localDateTime.plusDays(1).with(LocalTime.MIN);
		return endOfDay.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
	}

	public static Long getStartOfDay(Long utcDateTime) {
		LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(utcDateTime),
				ZoneId.systemDefault());
		LocalDateTime startOfDay = localDateTime.with(LocalTime.MIN);
		return startOfDay.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
	}

	/**
	 * 获取某个时区的一天的结束
	 * @param utcDateTime
	 * @param timeZone 如：+08:00
	 * @return
	 */
	public static Long getEndOfDay(Long utcDateTime, String timeZone) {
		if (StringUtils.isEmpty(timeZone)) {
			timeZone = "+08:00";
		}
		LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(utcDateTime), ZoneId.of(timeZone));
		// LocalDateTime endOfDay = localDateTime.with(LocalTime.MAX);
		// 本该如此，但会议预定的结束时间是整点，这里相差一秒
		LocalDateTime endOfDay = localDateTime.plusDays(1).with(LocalTime.MIN);
		return endOfDay.atZone(ZoneId.of(timeZone)).toInstant().toEpochMilli();
	}

	/**
	 * 获取某个时区的一天的开始
	 * @param utcDateTime
	 * @param timeZone 如：+08:00
	 * @return
	 */
	public static Long getStartOfDay(Long utcDateTime, String timeZone) {
		if (StringUtils.isEmpty(timeZone)) {
			timeZone = "+08:00";
		}
		LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(utcDateTime), ZoneId.of(timeZone));
		LocalDateTime startOfDay = localDateTime.with(LocalTime.MIN);
		return startOfDay.atZone(ZoneId.of(timeZone)).toInstant().toEpochMilli();
	}

	/**
	 * 根据时区转换为本时区时间
	 * @param utc utc 时间 yyyy-MM-dd HH:mm:ss
	 * @param timeZone 时区
	 * @return
	 */
	public static String utcToLocal(Long utc, String timeZone) {
		if (StringUtils.isEmpty(timeZone)) {
			timeZone = "+08:00";
		}
		LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(utc), ZoneId.of(timeZone));
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		return localDateTime.format(formatter);
	}

	public static LocalDateTime convertTime(Long time) {
		Assert.notNull(time, "time is null");
		return LocalDateTime.ofInstant(Instant.ofEpochMilli(time), ZoneId.systemDefault());
	}

	/**
	 * 根据时区转换为本时区时间
	 * @param utc 时间戳
	 * @param format 日期格式
	 * @param timeZone 时区
	 * @return
	 */
	public static String utcToLocalDate(Long utc, String format, String timeZone) {
		if (StringUtils.isEmpty(timeZone)) {
			timeZone = "+08:00";
		}
		LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(utc), ZoneId.of(timeZone));
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
		return localDateTime.format(formatter);
	}

	private static String addZero(int param) {
		String paramStr = param < 10 ? "0" + param : "" + param;
		return paramStr;
	}

	/**
	 * 相差天数，忽略时分秒
	 * @param start
	 * @param end
	 * @return
	 */
	public static long between(long start, long end) {
		LocalDate s = LocalDateTime.ofInstant(Instant.ofEpochMilli(start), ZoneId.systemDefault()).toLocalDate();
		LocalDate e = LocalDateTime.ofInstant(Instant.ofEpochMilli(end), ZoneId.systemDefault()).toLocalDate();
		return ChronoUnit.DAYS.between(s, e);
	}

	/**
	 * 将Long类型的时间戳转换成String 类型的时间格式，时间格式为：yyyy-MM-dd HH:mm
	 */
	public static String convertTimeToString(Long time) {
		Assert.notNull(time, "time is null");
		DateTimeFormatter ftf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		return ftf.format(LocalDateTime.ofInstant(Instant.ofEpochMilli(time), ZoneId.systemDefault()));
	}

	/**
	 * 将Long类型的时间戳转换成String 类型的时间格式，时间格式为：yyyy
	 */
	public static String convertTimeToYearString(Long time) {
		Assert.notNull(time, "time is null");
		DateTimeFormatter ftf = DateTimeFormatter.ofPattern("yyyy");
		return ftf.format(LocalDateTime.ofInstant(Instant.ofEpochMilli(time), ZoneId.systemDefault()));
	}

	/**
	 * 将Long类型的时间戳转换成String 类型的时间格式，时间格式为：yyyyMM
	 */
	public static String convertTimeToDayString(Long time) {
		Assert.notNull(time, "time is null");
		DateTimeFormatter ftf = DateTimeFormatter.ofPattern("yyyyMM");
		return ftf.format(LocalDateTime.ofInstant(Instant.ofEpochMilli(time), ZoneId.systemDefault()));
	}

	/**
	 * 将Long类型的时间戳转换成String 类型的时间格式，时间格式为：yyyyMMddHHmm
	 */
	public static String convertTimeToMinString(Long time) {
		Assert.notNull(time, "time is null");
		DateTimeFormatter ftf = DateTimeFormatter.ofPattern("yyyyMMddHHmm");
		return ftf.format(LocalDateTime.ofInstant(Instant.ofEpochMilli(time), ZoneId.systemDefault()));
	}

	/**
	 * 将日期成Long类型的时间戳，格式为：format
	 * @param timeZone
	 * @return
	 */
	public static Long convertLocalDateToLong(LocalDate localDate, String timeZone) {
		if (StringUtils.isEmpty(timeZone)) {
			timeZone = "+08:00";
		}
		return localDate.atStartOfDay(ZoneId.of(timeZone)).toInstant().toEpochMilli();
	}

	/**
	 * 返回当前系统 日期
	 * @param format 日期格式
	 * @return
	 */
	public static String nowDate(String format) {
		return LocalDateTime.now().format(DateTimeFormatter.ofPattern(format));
	}

	/**
	 * String 转 日期
	 * @param date
	 * @return
	 */
	public static LocalDateTime toLocalDateTime(String date) {
		DateTimeFormatter ftf = DateTimeFormatter.ofPattern(DateTimeUtils.DATE_TIME_FORMAT);
		return LocalDateTime.parse(date + " 00:00:00", ftf);
	}

	public static boolean isLeapYear(int year) {
		if (Objects.isNull(year)) {
			return false;
		}
		// 是闰年
		if (year % 4 == 0 && year % 100 != 0 || year % 400 == 0) {
			return true;
		}
		return false;
	}

	/**
	 * 时间精度转换(毫秒->秒)
	 * @param time
	 * @return
	 */
	public static Long convertTimeToSecond(Long time) {
		DateTimeFormatter pattern = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		String timeStr = pattern.format(LocalDateTime.ofInstant(Instant.ofEpochMilli(time),ZoneId.systemDefault()));
		LocalDateTime parse = LocalDateTime.parse(timeStr, pattern);
		return parse.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
	}

}
