package cn.sokary.archs.android;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AppFormatter {

    //region Data manufacturing functions.

    public static int stringToInt(String toString) {
        try {
            return Integer.parseInt(toString);
        }catch (Exception e){}
        return 0;
    }
    public static double stringToDouble(String toString) {
        try {
            return Double.parseDouble(toString);
        }catch (Exception e){}
        return 0;
    }

    public static String intToString(int value) {
        if(value>0){ return String.valueOf(value); }
        return "";
    }
    public static String integerToString(Integer value) {
        if(value==null) return "";
        if(value>0){ return String.valueOf(value); }
        return "";
    }
    public static int integerToInt(Integer value) {
        if(value==null) return 0;
        if(value>0){ return value; }
        return 0;
    }
    public static String doubleToString(Double value) {
        if(value==null) return "";
        if(value>0){ return String.valueOf(value); }
        return "";
    }

    public static String timeToString(String value){
        if(isTime(value)){
            return value;
        }
        return "";
    }
    public static String timeToString(Date value){
        try {
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
            return df.format(value);
        }catch (Exception e){
        }
        return "";
    }
    public static String dateToString(String value){
        if(isDate(value)){
            return value;
        }
        return "";
    }
    public static String dateToString(Date value){
        try {
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
            return df.format(value);
        }catch (Exception e){
        }
        return "";
    }
    public static boolean isTime(String value){
        try {
            if(value==null) return false;
            if(value.substring(0,4).equals("1900")) return false;

            DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
            df.setLenient(false);
            df.parse(value);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }
    
    public static boolean isDate(String value){
        try {
            if(value==null) return false;
            if(value.substring(0,4).equals("1900")) return false;
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
            df.setLenient(false);
            df.parse(value);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }
    public static String left(String value, int length){
        if(value.length()<length) {
            return value;
        }else{
            return value.substring(0, length);
        }
    }
    public static String right(String value, int length){
        if(value.length()<length) {
            return value;
        }else{
            return value.substring(value.length()-length, value.length());
        }
    }
    public static String getCurrentDate(){
        return (new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA)).format(Calendar.getInstance().getTime());

    }
    public static String getCurrentTime(){
        return (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA)).format(Calendar.getInstance().getTime());
    }

    public static int appEntityToId(AppEntity item){
        if(item!=null) return item.id;
        return 0;
    }
    public static String appEntityToName(AppEntity item){
        if(item!=null) return item.name;
        return "";
    }

    public static String strToString(String item) {
        if(item!=null) return item;
        return "";
    }

    //endregion


}
