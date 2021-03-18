package com.didi.middleware.json.adapter;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class TypeUtils {

    private static final Pattern NUMBER_WITH_TRAILING_ZEROS_PATTERN = Pattern.compile("\\.0*$");

    public static short shortValue(BigDecimal decimal) {
        if (decimal == null) {
            return 0;
        }

        int scale = decimal.scale();
        if (scale >= -100 && scale <= 100) {
            return decimal.shortValue();
        }

        return decimal.shortValueExact();
    }

    public static int intValue(BigDecimal decimal) {
        if (decimal == null) {
            return 0;
        }

        int scale = decimal.scale();
        if (scale >= -100 && scale <= 100) {
            return decimal.intValue();
        }

        return decimal.intValueExact();
    }

    public static long longValue(BigDecimal decimal) {
        if (decimal == null) {
            return 0;
        }

        int scale = decimal.scale();
        if (scale >= -100 && scale <= 100) {
            return decimal.longValue();
        }

        return decimal.longValueExact();
    }

    public static Integer castToInt(Object value){
        if(value == null){
            return null;
        }

        if(value instanceof Integer){
            return (Integer) value;
        }

        if(value instanceof BigDecimal){
            return intValue((BigDecimal) value);
        }

        if(value instanceof Number){
            return ((Number) value).intValue();
        }

        if(value instanceof String){
            String strVal = (String) value;
            if(strVal.length() == 0
                    || "null".equals(strVal)
                    || "NULL".equals(strVal)){
                return null;
            }
            if(strVal.indexOf(',') != -1){
                strVal = strVal.replaceAll(",", "");
            }

            Matcher matcher = NUMBER_WITH_TRAILING_ZEROS_PATTERN.matcher(strVal);
            if(matcher.find()) {
                strVal = matcher.replaceAll("");
            }
            return Integer.parseInt(strVal);
        }

        if(value instanceof Boolean){
            return (Boolean) value ? 1 : 0;
        }
        if(value instanceof Map){
            Map map = (Map) value;
            if(map.size() == 2
                    && map.containsKey("andIncrement")
                    && map.containsKey("andDecrement")){
                Iterator iter = map.values().iterator();
                iter.next();
                Object value2 = iter.next();
                return castToInt(value2);
            }
        }
        throw new JSONException("can not cast to int, value : " + value);
    }

    public static Boolean castToBoolean(Object value) {
        if(value == null){
            return null;
        }
        if(value instanceof Boolean){
            return (Boolean) value;
        }

        if(value instanceof BigDecimal){
            return intValue((BigDecimal) value) == 1;
        }

        if(value instanceof Number){
            return ((Number) value).intValue() == 1;
        }

        if(value instanceof String){
            String strVal = (String) value;
            if(strVal.length() == 0
                    || "null".equals(strVal)
                    || "NULL".equals(strVal)){
                return null;
            }
            if("true".equalsIgnoreCase(strVal)
                    || "1".equals(strVal)){
                return Boolean.TRUE;
            }
            if("false".equalsIgnoreCase(strVal)
                    || "0".equals(strVal)){
                return Boolean.FALSE;
            }
            if("Y".equalsIgnoreCase(strVal)
                    || "T".equals(strVal)){
                return Boolean.TRUE;
            }
            if("F".equalsIgnoreCase(strVal)
                    || "N".equals(strVal)){
                return Boolean.FALSE;
            }
        }
        throw new JSONException("can not cast to boolean, value : " + value);
    }

    public static Short castToShort(Object value){
        if(value == null){
            return null;
        }

        if(value instanceof BigDecimal){
            return shortValue((BigDecimal) value);
        }

        if(value instanceof Number){
            return ((Number) value).shortValue();
        }

        if(value instanceof String){
            String strVal = (String) value;
            if(strVal.length() == 0
                    || "null".equals(strVal)
                    || "NULL".equals(strVal)){
                return null;
            }
            return Short.parseShort(strVal);
        }

        throw new JSONException("can not cast to short, value : " + value);
    }

    public static Long castToLong(Object value){
        if(value == null){
            return null;
        }

        if(value instanceof BigDecimal){
            return longValue((BigDecimal) value);
        }

        if(value instanceof Number){
            return ((Number) value).longValue();
        }

        if(value instanceof String){
            String strVal = (String) value;
            if(strVal.length() == 0
                    || "null".equals(strVal)
                    || "NULL".equals(strVal)){
                return null;
            }
            if(strVal.indexOf(',') != -1){
                strVal = strVal.replaceAll(",", "");
            }
            try{
                return Long.parseLong(strVal);
            } catch(NumberFormatException ex){
                //
            }
        }

        if(value instanceof Map){
            Map map = (Map) value;
            if(map.size() == 2
                    && map.containsKey("andIncrement")
                    && map.containsKey("andDecrement")){
                Iterator iter = map.values().iterator();
                iter.next();
                Object value2 = iter.next();
                return castToLong(value2);
            }
        }

        throw new JSONException("can not cast to long, value : " + value);
    }

    public static Float castToFloat(Object value){
        if(value == null){
            return null;
        }
        if(value instanceof Number){
            return ((Number) value).floatValue();
        }
        if(value instanceof String){
            String strVal = value.toString();
            if(strVal.length() == 0
                    || "null".equals(strVal)
                    || "NULL".equals(strVal)){
                return null;
            }
            if(strVal.indexOf(',') != -1){
                strVal = strVal.replaceAll(",", "");
            }
            return Float.parseFloat(strVal);
        }
        throw new JSONException("can not cast to float, value : " + value);
    }

    public static Double castToDouble(Object value){
        if(value == null){
            return null;
        }
        if(value instanceof Number){
            return ((Number) value).doubleValue();
        }
        if(value instanceof String){
            String strVal = value.toString();
            if(strVal.length() == 0
                    || "null".equals(strVal)
                    || "NULL".equals(strVal)){
                return null;
            }
            if(strVal.indexOf(',') != -1){
                strVal = strVal.replaceAll(",", "");
            }
            return Double.parseDouble(strVal);
        }
        throw new JSONException("can not cast to double, value : " + value);
    }

    public static BigDecimal castToBigDecimal(Object value){
        if(value == null){
            return null;
        }
        if(value instanceof BigDecimal){
            return (BigDecimal) value;
        }
        if(value instanceof BigInteger){
            return new BigDecimal((BigInteger) value);
        }
        String strVal = value.toString();
        if(strVal.length() == 0){
            return null;
        }
        if(value instanceof Map && ((Map) value).size() == 0){
            return null;
        }
        return new BigDecimal(strVal);
    }

    public static BigInteger castToBigInteger(Object value){
        if(value == null){
            return null;
        }
        if(value instanceof BigInteger){
            return (BigInteger) value;
        }
        if(value instanceof Float || value instanceof Double){
            return BigInteger.valueOf(((Number) value).longValue());
        }
        if(value instanceof BigDecimal){
            BigDecimal decimal = (BigDecimal) value;
            int scale = decimal.scale();
            if (scale > -1000 && scale < 1000) {
                return ((BigDecimal) value).toBigInteger();
            }
        }
        String strVal = value.toString();
        if(strVal.length() == 0
                || "null".equals(strVal)
                || "NULL".equals(strVal)){
            return null;
        }
        return new BigInteger(strVal);
    }

}
