package cn.ymex.kits.kson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * Created by ymex on 2017/6/29.
 */

public class KsonHelper {
    private Object val;

    protected KsonHelper(Object v) {
        this.val = v;
    }

    private boolean isNull() {
        return null == val;
    }

    public String string(String defaultValue) {
        return isNull() ? defaultValue : val.toString();
    }

    public String string() {
        return string("");
    }

    public Object object() {
        return val;
    }

    public boolean Boolean() {
        return this.Boolean(false);
    }

    public boolean Boolean(boolean defaultValue) {

        if (isNull()) {
            return defaultValue;
        }
        if (val instanceof Boolean) {
            return ((Boolean) val).booleanValue();
        }
        try {
            Boolean.valueOf(string());
        } catch (Exception e) {

        }
        return defaultValue;
    }


    public BigDecimal BigDecimal(BigDecimal defaultValue) {
        if (isNull()) {
            return defaultValue;
        }
        if (val instanceof BigDecimal) {
            return (BigDecimal) val;
        }
        if (val instanceof BigInteger) {
            return new BigDecimal((BigInteger) val);
        }
        if (val instanceof Double || val instanceof Float) {
            return new BigDecimal(((Number) val).doubleValue());
        }
        if (val instanceof Long || val instanceof Integer
                || val instanceof Short || val instanceof Byte) {
            return new BigDecimal(((Number) val).longValue());
        }
        // don't check if it's a string in case of unchecked Number subclasses
        try {
            return new BigDecimal(val.toString());
        } catch (Exception e) {
            return defaultValue;
        }
    }


    public BigInteger BigInteger(BigInteger defaultValue) {

        if (isNull()) {
            return defaultValue;
        }
        if (val instanceof BigInteger) {
            return (BigInteger) val;
        }
        if (val instanceof BigDecimal) {
            return ((BigDecimal) val).toBigInteger();
        }
        if (val instanceof Double || val instanceof Float) {
            return new BigDecimal(((Number) val).doubleValue()).toBigInteger();
        }
        if (val instanceof Long || val instanceof Integer
                || val instanceof Short || val instanceof Byte) {
            return BigInteger.valueOf(((Number) val).longValue());
        }
        // don't check if it's a string in case of unchecked Number subclasses
        try {
            // the other opt functions handle implicit conversions, i.e.
            // jo.put("double",1.1d);
            // jo.optInt("double"); -- will return 1, not an error
            // this conversion to BigDecimal then to BigInteger is to maintain
            // that type cast support that may truncate the decimal.
            final String valStr = val.toString();
            if (isDecimalNotation(valStr)) {
                return new BigDecimal(valStr).toBigInteger();
            }
            return new BigInteger(valStr);
        } catch (Exception e) {
            return defaultValue;
        }
    }

    private boolean isDecimalNotation(final String val) {
        return val.indexOf('.') > -1 || val.indexOf('e') > -1
                || val.indexOf('E') > -1 || "-0".equals(val);
    }

    public double Double(String key) {
        return Double(Double.NaN);
    }


    public double Double(double defaultValue) {

        if (isNull()) {
            return defaultValue;
        }
        if (val instanceof Number) {
            return ((Number) val).doubleValue();
        }
        if (val instanceof String) {
            try {
                return Double.parseDouble((String) val);
            } catch (Exception e) {
                return defaultValue;
            }
        }
        return defaultValue;
    }


    public float Float() {
        return this.Float(Float.NaN);
    }


    public float Float(float defaultValue) {

        if (isNull()) {
            return defaultValue;
        }
        if (val instanceof Number) {
            return ((Number) val).floatValue();
        }
        if (val instanceof String) {
            try {
                return Float.parseFloat((String) val);
            } catch (Exception e) {
                return defaultValue;
            }
        }
        return defaultValue;
    }


    public int Int() {
        return this.Int(0);
    }


    public int Int(int defaultValue) {

        if (isNull()) {
            return defaultValue;
        }
        if (val instanceof Number) {
            return ((Number) val).intValue();
        }

        if (val instanceof String) {
            try {
                return new BigDecimal((String) val).intValue();
            } catch (Exception e) {
                return defaultValue;
            }
        }
        return defaultValue;
    }


    public JSONArray JSONArray() {

        return val instanceof JSONArray ? (JSONArray) val : null;
    }


    public JSONObject JSONObject() {

        return val instanceof JSONObject ? (JSONObject) val : null;
    }

    public long Long(String key) {
        return this.Long(0);
    }

    public long Long(long defaultValue) {

        if (isNull()) {
            return defaultValue;
        }
        if (val instanceof Number) {
            return ((Number) val).longValue();
        }

        if (val instanceof String) {
            try {
                return new BigDecimal((String) val).longValue();
            } catch (Exception e) {
                return defaultValue;
            }
        }
        return defaultValue;
    }


    public Number Number() {
        return this.Number(null);
    }

    public Number Number(Number defaultValue) {
        if (isNull()) {
            return defaultValue;
        }
        if (val instanceof Number) {
            return (Number) val;
        }

        if (val instanceof String) {
            try {
                return stringToNumber((String) val);
            } catch (Exception e) {
                return defaultValue;
            }
        }
        return defaultValue;
    }

    private Number stringToNumber(final String val) throws NumberFormatException {
        char initial = val.charAt(0);
        if ((initial >= '0' && initial <= '9') || initial == '-') {
            // decimal representation
            if (isDecimalNotation(val)) {
                // quick dirty way to see if we need a BigDecimal instead of a Double
                // this only handles some cases of overflow or underflow
                if (val.length() > 14) {
                    return new BigDecimal(val);
                }
                final Double d = Double.valueOf(val);
                if (d.isInfinite() || d.isNaN()) {
                    // if we can't parse it as a double, go up to BigDecimal
                    // this is probably due to underflow like 4.32e-678
                    // or overflow like 4.65e5324. The size of the string is small
                    // but can't be held in a Double.
                    return new BigDecimal(val);
                }
                return d;
            }
            // integer representation.
            // This will narrow any values to the smallest reasonable Object representation
            // (Integer, Long, or BigInteger)

            // string version
            // The compare string length method reduces GC,
            // but leads to smaller integers being placed in larger wrappers even though not
            // needed. i.e. 1,000,000,000 -> Long even though it's an Integer
            // 1,000,000,000,000,000,000 -> BigInteger even though it's a Long
            //if(val.length()<=9){
            //    return Integer.valueOf(val);
            //}
            //if(val.length()<=18){
            //    return Long.valueOf(val);
            //}
            //return new BigInteger(val);

            // BigInteger version: We use a similar bitLenth compare as
            // BigInteger#intValueExact uses. Increases GC, but objects hold
            // only what they need. i.e. Less runtime overhead if the value is
            // long lived. Which is the better tradeoff? This is closer to what's
            // in stringToValue.
            BigInteger bi = new BigInteger(val);
            if (bi.bitLength() <= 31) {
                return Integer.valueOf(bi.intValue());
            }
            if (bi.bitLength() <= 63) {
                return Long.valueOf(bi.longValue());
            }
            return bi;
        }
        throw new NumberFormatException("val [" + val + "] is not a valid number.");
    }

}