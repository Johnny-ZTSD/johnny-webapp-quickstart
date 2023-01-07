package cn.johnnyzen.create.simpleFactory.Calendar;

import java.util.Calendar;

/**
 * @project: DesignPatterns
 * @author: Johnny
 * @date: 2020/8/30  22:43:01
 * @description: 简单工厂 - 源码案例 -  Calendar: createCalendar(TimeZone zone, Locale aLocale)
 */

public class Client {
    public static void main(String[] args) {
        Calendar calendar = null;
        /**
         * 1 Calendar
         *      private static Calendar createCalendar(TimeZone zone, Locale aLocale)
         */
        /**
             ...
             Calendar cal = null;

             if (aLocale.hasExtensions()) {
             String caltype = aLocale.getUnicodeLocaleType("ca");
             if (caltype != null) {
             switch (caltype) {
             case "buddhist":
             cal = new BuddhistCalendar(zone, aLocale);
             break;
             case "japanese":
             cal = new JapaneseImperialCalendar(zone, aLocale);
             break;
             case "gregory":
             cal = new GregorianCalendar(zone, aLocale);
             break;
             }
             }
             }
             if (cal == null) {
             // If no known calendar type is explicitly specified,
             // perform the traditional way to create a Calendar:
             // create a BuddhistCalendar for th_TH locale,
             // a JapaneseImperialCalendar for ja_JP_JP locale, or
             // a GregorianCalendar for any other locales.
             // NOTE: The language, country and variant strings are interned.
             if (aLocale.getLanguage() == "th" && aLocale.getCountry() == "TH") {
             cal = new BuddhistCalendar(zone, aLocale);
             } else if (aLocale.getVariant() == "JP" && aLocale.getLanguage() == "ja"
             && aLocale.getCountry() == "JP") {
             cal = new JapaneseImperialCalendar(zone, aLocale);
             } else {
             cal = new GregorianCalendar(zone, aLocale);
             }
             }
             return cal;`
         */
    }
}
