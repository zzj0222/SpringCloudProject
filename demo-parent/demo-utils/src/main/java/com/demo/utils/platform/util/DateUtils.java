/*
 * Copyright (c) Koala 2012-2014 All Rights Reserved
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.demo.utils.platform.util;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateUtils {
    private static Logger log = Log.get();
    public static final String HHMM = "HHmm";
    public static final String DDMMMYY = "ddMMMyy";
    public static final String DD_MMMYYYY_HHMM = "ddMMMyyyy HHmm";
    public static final String DD_MMMYYYY_HH_MM = "ddMMMyyyy HH:mm";
    public static final String DD_MMMYYYY_HH_MM_SS = "ddMMMyyyy HH:mm:ss";
    public static final String YYYY_MM_DD = "yyyy-MM-dd";
    public static final String YYYYMMDD = "yyyyMMdd";
    public static final String YYYY2MM2DD = "yyyy/MM/dd";
    public static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
    public static final String YYYY_MM_DD_HH_MM = "yyyy-MM-dd HH:mm";
    public static final String HH_MM = "HH:mm";
    public static final String HH_MM_SS = "HH:mm:ss";
    public static final String YYYY_MM_DD2HH_MM = "yyyy-MM-dd-HH-mm";
    public static final String YYYYMMDDHHMM = "yyyyMMddHHmm";
    public static final String YYYYMMDDHHMMSS = "yyyyMMddHHmmss";

    /**
     * 解析日期<br>
     * 支持格式：<br>
     * generate by: vakin jiang at 2012-3-1
     *
     * @param dateStr
     * @return
     */
    public static Date parseDate(String dateStr) {
        SimpleDateFormat format = null;
        if (StringUtils.isBlank(dateStr)) {
            return null;
        }

        String dateStrTrim = dateStr.trim();
        try {
            if (dateStrTrim.matches("\\d{1,2}[A-Z]{3}")) {
                dateStrTrim = dateStrTrim + (Calendar.getInstance().get(Calendar.YEAR) - 2000);
            }
            // 01OCT12
            if (dateStrTrim.matches("\\d{1,2}[A-Z]{3}\\d{2}")) {
                format = new SimpleDateFormat("ddMMMyy");
            } else if (dateStrTrim.matches("\\d{1,2}[A-Z]{3}\\d{4}.*")) {
                // ,01OCT2012
                // 1224,01OCT2012
                // 12:24
                dateStrTrim = dateStrTrim.replaceAll("[^0-9A-Z]", "").concat("000000").substring(0, 15);
                format = new SimpleDateFormat("ddMMMyyyyHHmmss");
            } else {
                StringBuffer sb = new StringBuffer(dateStrTrim);
                String[] tempArr = dateStrTrim.split("\\s+");
                tempArr = tempArr[0].split("-|\\/");
                if (tempArr.length == 3) {
                    if (tempArr[1].length() == 1) {
                        sb.insert(5, "0");
                    }
                    if (tempArr[2].length() == 1) {
                        sb.insert(8, "0");
                    }
                }
                dateStrTrim = sb.append("000000").toString().replaceAll("[^0-9]", "").substring(0, 14);
                if (dateStrTrim.matches("\\d{14}")) {
                    format = new SimpleDateFormat("yyyyMMddHHmmss");
                }
            }

            Date date = format.parse(dateStrTrim);
            return date;
        } catch (Exception e) {
            throw new RuntimeException("无法解析日期字符[" + dateStr + "]");
        }
    }

    /**
     * 解析日期字符串转化成日期格式<br>
     * generate by: vakin jiang at 2012-3-1
     *
     * @param dateStr
     * @param pattern
     * @return
     */
    public static Date parseDate(String dateStr, String pattern) {
        try {
            SimpleDateFormat format = null;
            if (StringUtils.isBlank(dateStr)) {
                return null;
            }

            if (StringUtils.isNotBlank(pattern)) {
                format = new SimpleDateFormat(pattern);
                return format.parse(dateStr);
            }
            return parseDate(dateStr);
        } catch (Exception e) {
            throw new RuntimeException("无法解析日期字符[" + dateStr + "]");
        }
    }

    /**
     * 获取一天开始时间<br>
     * generate by: vakin jiang at 2011-12-23
     *
     * @param date
     * @return
     */
    public static Date getDayBegin(Date date) {
        String format = DateFormatUtils.format(date, YYYY_MM_DD);
        return parseDate(format.concat(" 00:00:00"));
    }

    /**
     * 获取一天结束时间<br>
     * generate by: vakin jiang at 2011-12-23
     *
     * @param date
     * @return
     */
    public static Date getDayEnd(Date date) {
        String format = DateFormatUtils.format(date, YYYY_MM_DD);
        return parseDate(format.concat(" 23:59:59"));
    }

    /**
     * 时间戳格式转换为日期（年月日）格式<br>
     * generate by: vakin jiang at 2011-12-23
     *
     * @param date
     * @return
     */
    public static Date timestamp2Date(Date date) {
        return formatDate(date, YYYY_MM_DD);
    }

    /**
     * 时间戳格式转换为日期（年月日）格式<br>
     * generate by: vakin jiang at 2011-12-23
     *
     * @param date
     * @return
     */
    public static String formatDate2YYYY_MM_DD(Date date) {
        return format(date, YYYY_MM_DD);
    }


    /**
     * 格式化日期格式为：ddMMMyy
     * @param date
     * @return
     */
    public static String format2ddMMMyy(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("ddMMMyy");
        return format.format(date).toUpperCase();
    }

    /**
     * 格式化日期格式为：ddMMMyy<br>
     * generate by: vakin jiang at 2012-10-17
     *
     * @param dateStr
     * @return
     */
    public static String format2ddMMMyy(String dateStr) {
        SimpleDateFormat format = new SimpleDateFormat("ddMMMyy");
        return format.format(parseDate(dateStr)).toUpperCase();
    }

    /**
     * 格式化日期字符串<br>
     *
     * @param dateStr
     * @param patterns
     * @return
     */
    public static String formatDateStr(String dateStr, String... patterns) {
        String pattern = YYYY_MM_DD_HH_MM_SS;
        if (patterns != null && patterns.length > 0 && StringUtils.isNotBlank(patterns[0])) {
            pattern = patterns[0];
        }
        return DateFormatUtils.format(parseDate(dateStr), pattern);
    }


    /**
     * 格式化日期为日期字符串<br>
     * @param date
     * @param patterns
     * @return
     */
    public static String format(Date date, String... patterns) {
        if (date == null) {
            return "";
        }
        String pattern = YYYY_MM_DD_HH_MM_SS;
        if (patterns != null && patterns.length > 0 && StringUtils.isNotBlank(patterns[0])) {
            pattern = patterns[0];
        }
        return DateFormatUtils.format(date, pattern);
    }

    public static String format2YYYY_MM_DD(Date date) {
        return format(date, YYYY_MM_DD);
    }

    public static String format2YYYYMMDD(Date date) {
        return format(date, YYYYMMDD);
    }

    /**
     * 格式化日期为指定格式<br>
     *
     * @param orig
     * @param patterns
     * @return
     */
    public static Date formatDate(Date orig, String... patterns) {
        String pattern = YYYY_MM_DD_HH_MM_SS;
        if (patterns != null && patterns.length > 0 && StringUtils.isNotBlank(patterns[0])) {
            pattern = patterns[0];
        }
        return parseDate(DateFormatUtils.format(orig, pattern));
    }

    /**
     * 得到当前日期<br>
     *
     * @param pattern
     * @return
     */
    public static String getCurrentDate(String pattern) {
        return DateFormatUtils.format(new Date(), pattern);
    }

    /**
     * 得到当前日期<br>
     *
     * @return
     */
    /**
    public static String getDefaultDate() {
        return getCurrentDate(YYYY_MM_DD);
    }

    /**
     * 得到当前日期<br>
     *
     * @param pattern
     * @return
     */
    public static String getDefaultDateTime(String pattern) {
        return getCurrentDate(pattern);
    }

    /**
     * 得到当前日期<br>
     *
     * @return
     */
    public static String getDefaultDateTime() {
        return getCurrentDate(YYYY_MM_DD_HH_MM_SS);
    }


    /**
     * 得到当前日期<br>
     *
     * @return
     */
    public static String getDefaultTime() {
        return getCurrentDate(HH_MM_SS);
    }

    /**
     * date1 >= date2 return true; date1 < date2 return false;
     *
     * @param date1
     * @param date2
     * @return
     */
    public static boolean compareDate(String date1, String date2) {
        Calendar calendar1 = Calendar.getInstance();
        Calendar calendar2 = Calendar.getInstance();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        try {
            calendar1.setTime(df.parse(date1));
            calendar2.setTime(df.parse(date2));
        } catch (ParseException e) {
            log.error(e.getMessage(), e);
        }
        if (calendar1.compareTo(calendar2) >= 0) {
            return true;
        } else {
            return false;
        }
    }

    public static String getAfterMonth(int month, int day) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = new GregorianCalendar();
        calendar.add(Calendar.MONTH, month);
        calendar.add(Calendar.DATE, day);
        String date = format.format(calendar.getTime());
        return date;
    }

    public static String getAfterDate(int day) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = new GregorianCalendar();
        calendar.add(Calendar.DATE, day);
        String date = format.format(calendar.getTime());
        return date;
    }

    public static String getAfterMinute(int minute) {
        SimpleDateFormat format = new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS);
        Calendar calendar = new GregorianCalendar();
        calendar.add(Calendar.MINUTE, minute);
        String date = format.format(calendar.getTime());
        return date;
    }

    public static String getAfterSecond(int second) {
        SimpleDateFormat format = new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS);
        Calendar calendar = new GregorianCalendar();
        calendar.add(Calendar.SECOND, second);
        String date = format.format(calendar.getTime());
        return date;
    }

    /**
     * 获得上周周日0点时间
     * @return
     */
    public static Date getTimesLastWeekmorning() {
        Calendar cal = Calendar.getInstance();
        cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONDAY), cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
        cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        cal.add(Calendar.DATE,-7);
        return getDayBegin(cal.getTime());
    }

    /**
     * 获得本周日0点时间
     * @return
     */
    public static Date getTimesWeekmorning() {
        Calendar cal = Calendar.getInstance();
        cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONDAY), cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
        cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        return getDayBegin(cal.getTime());
    }

    /**
     * 获得本周六0点时间
     * @return
     */
    public  static Date getTimesWeeknight() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(getTimesWeekmorning());
        cal.add(Calendar.DAY_OF_WEEK, 7);
        return getDayBegin(cal.getTime());
    }

    /**
     * 获得本月第一天0点时间
     * @return
     */
    public static Date getTimesMonthmorning() {
        Calendar cal = Calendar.getInstance();
        cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONDAY), cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH));
        return  cal.getTime();
    }

    /**
     * 获得本月最后一天24点时间
     * @return
     */
    public static Date getTimesMonthnight() {
        Calendar cal = Calendar.getInstance();
        cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONDAY), cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
        cal.set(Calendar.HOUR_OF_DAY, 24);
        return cal.getTime();
    }

    /**
     * 获取过去第几天的日期
     *
     * @param past
     * @return
     */
    public static String getPastDateStr(int past) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) - past);
        Date today = calendar.getTime();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String result = format.format(today);
        return result;
    }

    /**
     * 获取过去第几天的日期
     *
     * @param past
     * @return
     */
    public static Date getPastDate(int past) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) - past);
        return calendar.getTime();
    }

    /**
     * 获取未来 第 past 天的日期
     * @param past
     * @return
     */
    public static String getFetureDateStr(int past) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) + past);
        Date today = calendar.getTime();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String result = format.format(today);
        return result;
    }

    /**
     * 获取未来 第 past 天的日期
     * @param past
     * @return
     */
    public static Date getFetureDate(int past) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) + past);
        return calendar.getTime();
    }


    /**
     * 获取之前的时间
     * @param past
     * @param type  年 月  日 时  分钟
     * @return
     */
    public static Date getBeforeDateTime(int past,int type) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(type, -past);
        return calendar.getTime();
    }

    /**
     * 获取未来的的时间
     * @param feture
     * @param type  年 月  日 时  分钟
     * @return
     */
    public static Date getFetureDateTime(int feture,int type) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(type, +feture);
        return calendar.getTime();
    }


    /**
     * 两个日期相差多少秒
     * @param date1
     * @param date2
     * @return
     */
    public static int getTimeDelta(Date date1,Date date2){
        //单位是秒
        long timeDelta=(date1.getTime()-date2.getTime())/1000;
        int secondsDelta=timeDelta>0?(int)timeDelta:(int)Math.abs(timeDelta);
        return secondsDelta;
    }



    /**
     * 判断时间是否在时间段内
     * @param date
     * @param strDateBegin 开始时间 00:00:00
     * @param strDateEnd   结束时间 00:05:00
     * @return
     */
    public static  Boolean isInDate(Date date, String strDateBegin, String strDateEnd) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String strDate = sdf.format(date);
        // 截取当前时间时分秒
        int strDateH = Integer.parseInt(strDate.substring(11, 13));
        int strDateM = Integer.parseInt(strDate.substring(14, 16));
        int strDateS = Integer.parseInt(strDate.substring(17, 19));
        // 截取开始时间时分秒
        int strDateBeginH = Integer.parseInt(strDateBegin.substring(0, 2));
        int strDateBeginM = Integer.parseInt(strDateBegin.substring(3, 5));
        int strDateBeginS = Integer.parseInt(strDateBegin.substring(6, 8));
        // 截取结束时间时分秒
        int strDateEndH = Integer.parseInt(strDateEnd.substring(0, 2));
        int strDateEndM = Integer.parseInt(strDateEnd.substring(3, 5));
        int strDateEndS = Integer.parseInt(strDateEnd.substring(6, 8));
        if ((strDateH >= strDateBeginH && strDateH <= strDateEndH)) {
            // 当前时间小时数在开始时间和结束时间小时数之间
            if (strDateH > strDateBeginH && strDateH < strDateEndH) {
                return true;
                // 当前时间小时数等于开始时间小时数，分钟数在开始和结束之间
            } else if (strDateH == strDateBeginH && strDateM >= strDateBeginM
                    && strDateM <= strDateEndM) {
                return true;
                // 当前时间小时数等于开始时间小时数，分钟数等于开始时间分钟数，秒数在开始和结束之间
            } else if (strDateH == strDateBeginH && strDateM == strDateBeginM
                    && strDateS >= strDateBeginS && strDateS <= strDateEndS) {
                return true;
            }
            // 当前时间小时数大等于开始时间小时数，等于结束时间小时数，分钟数小等于结束时间分钟数
            else if (strDateH >= strDateBeginH && strDateH == strDateEndH
                    && strDateM <= strDateEndM) {
                return true;
                // 当前时间小时数大等于开始时间小时数，等于结束时间小时数，分钟数等于结束时间分钟数，秒数小等于结束时间秒数
            } else if (strDateH >= strDateBeginH && strDateH == strDateEndH
                    && strDateM == strDateEndM && strDateS <= strDateEndS) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
}
