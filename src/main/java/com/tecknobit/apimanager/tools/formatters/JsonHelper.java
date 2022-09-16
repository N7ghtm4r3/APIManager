package com.tecknobit.apimanager.tools.formatters;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * The {@code JsonHelper} class is a useful class tool to works with {@link JSONObject} and {@link JSONArray} classes
 *
 * @author Tecknobit N7ghtm4r3
 * @apiNote this class is helpful to avoid the {@link JSONException} because if searched value is not present in JSON, will be
 * returned a default value or a custom value choose by you. For all the operations to get values you don't need to pass
 * the correct JSON path ro reach the wanted value because it will be automatically reached autonomously
 **/

public class JsonHelper{

    /**
     * {@code NUMERIC_DEF_VALUE_IF_MISSED} is constant that memorizes default numeric value if the main is missed
     * **/
    public static final int NUMERIC_DEF_VALUE_IF_MISSED = -1234567890;

    /**
     * {@code NUMERIC_CLASS_CAST_ERROR_VALUE} is constant that memorizes default numeric value if {@link ClassCastException}
     * has been thrown
     * **/
    public static final int NUMERIC_CLASS_CAST_ERROR_VALUE = -987654321;

    /**
     * {@code jsonObjectDetails} is instance that memorizes {@link JSONObject} to work on
     * **/
    private JSONObject jsonObjectDetails;

    /**
     * {@code jsonObjectDetails} is instance that memorizes {@link JSONArray} to work on
     * **/
    private JSONArray jsonArrayDetails;

    /** Constructor to init {@link JsonHelper} tool class
     * @param jsonObjectDetails: jsonObject used to fetch data
     * @param jsonArrayDetails: jsonArray used to fetch data
     * **/
    public JsonHelper(JSONObject jsonObjectDetails, JSONArray jsonArrayDetails) {
        this.jsonObjectDetails = jsonObjectDetails;
        this.jsonArrayDetails = jsonArrayDetails;
    }

    /** Constructor to init {@link JsonHelper} tool class
     * @param jsonObjectDetails: jsonObject used to fetch data
     * **/
    public JsonHelper(JSONObject jsonObjectDetails) {
        this.jsonObjectDetails = jsonObjectDetails;
        jsonArrayDetails = new JSONArray();
    }

    /** Constructor to init {@link JsonHelper} tool class
     * @param jsonArrayDetails: jsonArray used to fetch data
     * **/
    public JsonHelper(JSONArray jsonArrayDetails) {
        this.jsonArrayDetails = jsonArrayDetails;
        jsonObjectDetails = new JSONObject();
    }

    /** Method to get from {@link JSONObject} a string value
     * @param key: key of string value to get from json
     * @exception ClassCastException: when this exception has been trowed will be returned null as default value
     * @return value as {@link String}, if it is not exist will return null value
     * **/
    public String getString(String key){
        return getString(key, null);
    }

    /** Method to get from {@link JSONObject} a string value
     * @param key: key of string value to get from json
     * @param defValue: default value to return if primary value not exists
     * @exception ClassCastException: when this exception has been trowed will be returned null as default value
     * @return value as {@link String}, if it is not exist will return {@code defValue}
     * **/
    public String getString(String key, String defValue){
        try {
            return autoSearch(jsonObjectDetails, key, defValue);
        }catch (ClassCastException e){
            return null;
        }
    }

    /** Method to get from {@link JSONArray} a string value
     * @param index: index of string value to get from json
     * @return value as {@link String}, if it is not exist will return null value
     * **/
    public String getString(int index){
        return getString(index, null);
    }

    /** Method to get from {@link JSONArray} a string value
     * @param index: index of string value to get from json
     * @param defValue: default value to return if primary value not exists
     * @return value as {@link String}, if it is not exist will return {@code defValue}
     * **/
    public String getString(int index, String defValue){
        try {
            return jsonArrayDetails.getString(index);
        }catch (Exception e){
            return defValue;
        }
    }

    /** Method to get from {@link JSONObject} a double value
     * @param key: key of double value to get from json
     * @exception ClassCastException: when this exception has been thrown will be returned {@link #NUMERIC_CLASS_CAST_ERROR_VALUE}
     * as default value
     * @return value as double, if it is not exist will return {@link #NUMERIC_DEF_VALUE_IF_MISSED}
     * **/
    public double getDouble(String key){
        return getDouble(key, NUMERIC_DEF_VALUE_IF_MISSED);
    }

    /** Method to get from {@link JSONObject} a double value
     * @param key: key of double value to get from json
     * @param defValue: default value to return if primary value not exists
     * @exception ClassCastException: when this exception has been thrown will be returned {@link #NUMERIC_CLASS_CAST_ERROR_VALUE}
     * as default value
     * @return value as double, if it is not exist will return {@code defValue}
     * **/
    public double getDouble(String key, double defValue){
        try {
            Object value = autoSearch(jsonObjectDetails, key, defValue);
            if(value instanceof Number)
                return ((Number)value).doubleValue();
            else
                return Double.parseDouble(value.toString());
        }catch (ClassCastException | NumberFormatException e){
            return NUMERIC_CLASS_CAST_ERROR_VALUE;
        }
    }

    /** Method to get from {@link JSONArray} a double value
     * @param index: index of double value to get from json
     * @return value as double, if it is not exist will return {@link #NUMERIC_DEF_VALUE_IF_MISSED}
     * **/
    public double getDouble(int index){
        return getDouble(index, NUMERIC_DEF_VALUE_IF_MISSED);
    }

    /** Method to get from {@link JSONArray} a double value
     * @param index: index of double value to get from json
     * @param defValue: default value to return if primary value not exists
     * @return value as double, if it is not exist will return {@code defValue}
     * **/
    public double getDouble(int index, double defValue){
        try {
            return jsonArrayDetails.getDouble(index);
        }catch (Exception e){
            return defValue;
        }
    }

    /** Method to get from {@link JSONObject} an int value
     * @param key: key of int value to get from json
     * @exception ClassCastException: when this exception has been thrown will be returned {@link #NUMERIC_CLASS_CAST_ERROR_VALUE}
     * as default value
     * @return value as int, if it is not exist will return {@link #NUMERIC_DEF_VALUE_IF_MISSED}
     * **/
    public int getInt(String key){
        return getInt(key, NUMERIC_DEF_VALUE_IF_MISSED);
    }

    /** Method to get from {@link JSONObject} an int value
     * @param key: key of int value to get from json
     * @param defValue: default value to return if primary value not exists
     * @exception ClassCastException: when this exception has been thrown will be returned {@link #NUMERIC_CLASS_CAST_ERROR_VALUE}
     * as default value
     * @return value as int, if it is not exist will return {@code defValue}
     * **/
    public int getInt(String key, int defValue){
        try {
            Object value = autoSearch(jsonObjectDetails, key, defValue);
            if(value instanceof Number)
                return ((Number)value).intValue();
            else
                return Integer.parseInt(value.toString());
        }catch (ClassCastException | NumberFormatException e){
            return NUMERIC_CLASS_CAST_ERROR_VALUE;
        }
    }

    /** Method to get from {@link JSONArray} an int value
     * @param index: index of int value to get from json
     * @return value as int, if it is not exist will return {@link #NUMERIC_DEF_VALUE_IF_MISSED}
     * **/
    public int getInt(int index){
        return getInt(index, NUMERIC_DEF_VALUE_IF_MISSED);
    }

    /** Method to get from {@link JSONArray} an int value
     * @param index: index of int value to get from json
     * @param defValue: default value to return if primary value not exists
     * @return value as int, if it is not exist will return {@code defValue}
     * **/
    public int getInt(int index, int defValue){
        try {
            return jsonArrayDetails.getInt(index);
        }catch (Exception e){
            return defValue;
        }
    }
    
    /** Method to get from {@link JSONObject} a float value
     * @param key: key of float value to get from json
     * @exception ClassCastException: when this exception has been thrown will be returned {@link #NUMERIC_CLASS_CAST_ERROR_VALUE}
     * as default value
     * @return value as float, if it is not exist will return {@link #NUMERIC_DEF_VALUE_IF_MISSED}
     * **/
    public float getFloat(String key){
        return getFloat(key, NUMERIC_DEF_VALUE_IF_MISSED);
    }

    /** Method to get from {@link JSONObject} a float value
     * @param key: key of float value to get from json
     * @param defValue: default value to return if primary value not exists
     * @exception ClassCastException: when this exception has been thrown will be returned {@link #NUMERIC_CLASS_CAST_ERROR_VALUE}
     * as default value
     * @return value as float, if it is not exist will return {@code defValue}
     * **/
    public float getFloat(String key, float defValue){
        try {
            Object value = autoSearch(jsonObjectDetails, key, defValue);
            if(value instanceof Number)
                return ((Number)value).floatValue();
            else
                return Float.parseFloat(value.toString());
        }catch (ClassCastException | NumberFormatException e){
            return NUMERIC_CLASS_CAST_ERROR_VALUE;
        }
    }

    /** Method to get from {@link JSONArray} a float value
     * @param index: index of float value to get from json
     * @return value as float, if it is not exist will return {@link #NUMERIC_DEF_VALUE_IF_MISSED}
     * **/
    public float getFloat(int index){
        return getFloat(index, NUMERIC_DEF_VALUE_IF_MISSED);
    }

    /** Method to get from {@link JSONArray} a float value
     * @param index: index of float value to get from json
     * @param defValue: default value to return if primary value not exists
     * @return value as float, if it is not exist will return {@code defValue}
     * **/
    public float getFloat(int index, float defValue){
        try {
            return jsonArrayDetails.getFloat(index);
        }catch (Exception e){
            return defValue;
        }
    }

    /** Method to get from {@link JSONObject} a long value
     * @param key: key of long value to get from json
     * @exception ClassCastException: when this exception has been thrown will be returned {@link #NUMERIC_CLASS_CAST_ERROR_VALUE}
     * as default value
     * @return value as long, if it is not exist will return {@link #NUMERIC_DEF_VALUE_IF_MISSED}
     * **/
    public long getLong(String key){
        return getLong(key, NUMERIC_DEF_VALUE_IF_MISSED);
    }

    /** Method to get from {@link JSONObject} a long value
     * @param key: key of long value to get from json
     * @param defValue: default value to return if primary value not exists
     * @exception ClassCastException: when this exception has been thrown will be returned {@link #NUMERIC_CLASS_CAST_ERROR_VALUE}
     * as default value
     * @return value as long, if it is not exist will return {@code defValue}
     * **/
    public long getLong(String key, long defValue){
        try {
            Object value = autoSearch(jsonObjectDetails, key, defValue);
            if(value instanceof Number)
                return ((Number)value).longValue();
            else
                return Long.parseLong(value.toString());
        }catch (ClassCastException | NumberFormatException e){
            return NUMERIC_CLASS_CAST_ERROR_VALUE;
        }
    }

    /** Method to get from {@link JSONArray} a long value
     * @param index: index of long value to get from json
     * @return value as long, if it is not exist will return {@link #NUMERIC_DEF_VALUE_IF_MISSED}
     * **/
    public long getLong(int index){
        return getLong(index, NUMERIC_DEF_VALUE_IF_MISSED);
    }

    /** Method to get from {@link JSONArray} a long value
     * @param index: index of long value to get from json
     * @param defValue: default value to return if primary value not exists
     * @return value as long, if it is not exist will return {@code defValue}
     * **/
    public long getLong(int index, long defValue){
        try {
            return jsonArrayDetails.getLong(index);
        }catch (Exception e){
            return defValue;
        }
    }

    /** Method to get from {@link JSONObject} a BigDecimal value
     * @param key: key of BigDecimal value to get from json
     * @exception ClassCastException: when this exception has been trowed will be returned null as default value
     * @return value as {@link BigDecimal}, if it is not exist will return null value
     * **/
    public BigDecimal getBigDecimal(String key){
        return getBigDecimal(key, null);
    }

    /** Method to get from {@link JSONObject} a BigDecimal value
     * @param key: key of BigDecimal value to get from json
     * @param defValue: default value to return if primary value not exists
     * @exception ClassCastException: when this exception has been trowed will be returned null as default value
     * @return value as {@link BigDecimal}, if it is not exist will return {@code defValue}
     * **/
    public BigDecimal getBigDecimal(String key, BigDecimal defValue){
        try {
            Object value = autoSearch(jsonObjectDetails, key, defValue);
            if(value instanceof BigDecimal)
                return (BigDecimal) value;
            return BigDecimal.valueOf(Double.parseDouble(value.toString()));
        }catch (NullPointerException | ClassCastException | NumberFormatException e){
            return BigDecimal.valueOf(NUMERIC_CLASS_CAST_ERROR_VALUE);
        }
    }

    /** Method to get from {@link JSONArray} a BigDecimal value
     * @param index: index of BigDecimal value to get from json
     * @return value as {@link BigDecimal}, if it is not exist will return null value
     * **/
    public BigDecimal getBigDecimal(int index){
        return getBigDecimal(index, null);
    }

    /** Method to get from {@link JSONArray} a BigDecimal value
     * @param index: index of BigDecimal value to get from json
     * @param defValue: default value to return if primary value not exists
     * @return value as {@link BigDecimal}, if it is not exist will return {@code defValue}
     * **/
    public BigDecimal getBigDecimal(int index, BigDecimal defValue){
        try {
            return jsonArrayDetails.getBigDecimal(index);
        }catch (Exception e){
            return defValue;
        }
    }

    /** Method to get from {@link JSONObject} a BigInteger value
     * @param key: key of BigInteger value to get from json
     * @exception ClassCastException: when this exception has been trowed will be returned null as default value
     * @return value as {@link BigInteger}, if it is not exist will return null value
     * **/
    public BigInteger getBigInteger(String key){
        return getBigInteger(key, null);
    }

    /** Method to get from {@link JSONObject} a BigInteger value
     * @param key: key of BigInteger value to get from json
     * @param defValue: default value to return if primary value not exists
     * @exception ClassCastException: when this exception has been trowed will be returned null as default value
     * @return value as {@link BigInteger}, if it is not exist will return {@code defValue}
     * **/
    public BigInteger getBigInteger(String key, BigInteger defValue){
        try {
            Object value = autoSearch(jsonObjectDetails, key, defValue);
            if(value instanceof BigDecimal)
                return (BigInteger) value;
            return BigInteger.valueOf(Integer.parseInt(value.toString()));
        }catch (NullPointerException | ClassCastException | NumberFormatException e){
            return BigInteger.valueOf(NUMERIC_CLASS_CAST_ERROR_VALUE);
        }
    }

    /** Method to get from {@link JSONArray} a BigInteger value
     * @param index: index of BigInteger value to get from json
     * @return value as {@link BigInteger}, if it is not exist will return null value
     * **/
    public BigInteger getBigInteger(int index){
        return getBigInteger(index, null);
    }

    /** Method to get from {@link JSONArray} a BigInteger value
     * @param index: index of BigInteger value to get from json
     * @param defValue: default value to return if primary value not exists
     * @return value as {@link BigInteger}, if it is not exist will return {@code defValue}
     * **/
    public BigInteger getBigInteger(int index, BigInteger defValue){
        try {
            return jsonArrayDetails.getBigInteger(index);
        }catch (Exception e){
            return defValue;
        }
    }

    /** Method to get from {@link JSONObject} a Number value
     * @param key: key of Number value to get from json
     * @exception ClassCastException: when this exception has been trowed will be returned null as default value
     * @return value as {@link Number}, if it is not exist will return null value
     * **/
    public Number getNumber(String key){
        return getNumber(key, NUMERIC_DEF_VALUE_IF_MISSED);
    }

    /** Method to get from {@link JSONObject} a Number value
     * @param key: key of Number value to get from json
     * @param defValue: default value to return if primary value not exists
     * @exception ClassCastException: when this exception has been trowed will be returned null as default value
     * @return value as {@link Number}, if it is not exist will return {@code defValue}
     * **/
    public Number getNumber(String key, Number defValue){
        try {
            Object value = autoSearch(jsonObjectDetails, key, defValue);
            return ((Number)value);
        }catch (ClassCastException | NumberFormatException e){
            return NUMERIC_CLASS_CAST_ERROR_VALUE;
        }
    }

    /** Method to get from {@link JSONArray} a Number value
     * @param index: index of Number value to get from json
     * @return value as {@link Number}, if it is not exist will return null value
     * **/
    public Number getNumber(int index){
        return getNumber(index, null);
    }

    /** Method to get from {@link JSONArray} a Number value
     * @param index: index of Number value to get from json
     * @param defValue: default value to return if primary value not exists
     * @return value as {@link Number}, if it is not exist will return {@code defValue}
     * **/
    public Number getNumber(int index, Number defValue){
        try {
            return jsonArrayDetails.getNumber(index);
        }catch (Exception e){
            return defValue;
        }
    }

    /** Method to get from {@link JSONObject} an object value
     * @param key: key of Object value to get from json
     * @exception ClassCastException: when this exception has been trowed will be returned null as default value
     * @return value as {@link Object}, if it is not exist will return null value
     * **/
    public Object get(String key){
        return get(key, null);
    }

    /** Method to get from {@link JSONObject} an object value
     * @param key: key of Object value to get from json
     * @param defValue: default value to return if primary value not exists
     * @exception ClassCastException: when this exception has been trowed will be returned null as default value
     * @return value as {@link Object}, if it is not exist will return {@code defValue}
     * **/
    public Object get(String key, Object defValue){
        try {
            return autoSearch(jsonObjectDetails, key, defValue);
        }catch (ClassCastException e){
            return null;
        }
    }

    /** Method to get from {@link JSONArray} an object value
     * @param index: index of Object value to get from json
     * @return value as {@link Object}, if it is not exist will return null value
     * **/
    public Object get(int index){
        return get(index, null);
    }

    /** Method to get from {@link JSONArray} an object value
     * @param index: index of Object value to get from json
     * @param defValue: default value to return if primary value not exists
     * @return value as {@link Object}, if it is not exist will return {@code defValue}
     * **/
    public Object get(int index, Object defValue){
        try {
            return jsonArrayDetails.get(index);
        }catch (Exception e){
            return defValue;
        }
    }

    /** Method to get from {@link JSONObject} a boolean value
     * @param key: key of boolean value to get from json
     * @exception ClassCastException: when this exception has been trowed will be returned false as default value
     * @return value as boolean, if it is not exist will return false value
     * **/
    public boolean getBoolean(String key){
        return getBoolean(key, false);
    }

    /** Method to get from {@link JSONObject} a boolean value
     * @param key: key of boolean value to get from json
     * @param defValue: default value to return if primary value not exists
     * @exception ClassCastException: when this exception has been trowed will be returned false as default value
     * @return value as boolean, if it is not exist will return {@code defValue}
     * **/
    public boolean getBoolean(String key, boolean defValue){
        try {
            Object value = autoSearch(jsonObjectDetails, key, defValue);
            if(value instanceof Boolean)
                return (boolean) value;
            else
                return Boolean.parseBoolean(value.toString());
        }catch (ClassCastException e){
            return defValue;
        }
    }

    /** Method to get from {@link JSONArray} a boolean value
     * @param index: index of boolean value to get from json
     * @return value as boolean, if it is not exist will return false value
     * **/
    public boolean getBoolean(int index){
        return getBoolean(index, false);
    }

    /** Method to get from {@link JSONArray} a boolean value
     * @param index: index of boolean value to get from json
     * @param defValue: default value to return if primary value not exists
     * @return value as boolean, if it is not exist will return {@code defValue}
     * **/
    public boolean getBoolean(int index, boolean defValue){
        try {
            return jsonArrayDetails.getBoolean(index);
        }catch (Exception e){
            return defValue;
        }
    }

    /** Method to get from {@link JSONObject}  a list of values
     * @param key: key of JSONArray to get from json
     * @exception ClassCastException: when this exception has been trowed will be returned null as default value
     * @return value as {@link JSONArray}, if it is not exist will return null value
     * **/
    public JSONArray getJSONArray(String key){
        return getJSONArray(key, null);
    }

    /** Method to get from {@link JSONObject}  a list of values
     * @param key: key of JSONArray to get from json
     * @param defValue: default value to return if primary value not exists
     * @exception ClassCastException: when this exception has been trowed will be returned null as default value
     * @return value as {@link JSONArray}, if it is not exist will return {@code defValue}
     * **/
    public JSONArray getJSONArray(String key, JSONArray defValue){
        Object value = autoSearch(jsonObjectDetails, key, defValue);
        if(value instanceof JSONArray)
            return (JSONArray) value;
        return defValue;
    }

    /** Method to get from {@link JSONArray}  a list of values
     * @param index: index of JSONArray to get from json
     * @return value as {@link JSONArray}, if it is not exist will return null value
     * **/
    public JSONArray getJSONArray(int index){
        return getJSONArray(index, null);
    }

    /** Method to get from {@link JSONArray}  a list of values
     * @param index: index of JSONArray to get from json
     * @param defValue: default value to return if primary value not exists
     * @return value as {@link JSONArray}, if it is not exist will return {@code defValue}
     * **/
    public JSONArray getJSONArray(int index, JSONArray defValue){
        try {
            return jsonArrayDetails.getJSONArray(index);
        }catch (Exception e){
            return defValue;
        }
    }

    /** Method to get from {@link JSONObject} a jsonObject
     * @param key: key of JSONObject to get from json
     * @exception ClassCastException: when this exception has been trowed will be returned null as default value
     * @return value as {@link JSONObject}, if it is not exist will return null value
     * **/
    public JSONObject getJSONObject(String key){
        return getJSONObject(key, null);
    }

    /** Method to get from {@link JSONObject} a jsonObject
     * @param key: key of JSONObject to get from json
     * @param defValue: default value to return if primary value not exists
     * @exception ClassCastException: when this exception has been trowed will be returned null as default value
     * @return value as {@link JSONObject}, if it is not exist will return {@code defValue}
     * **/
    public JSONObject getJSONObject(String key, JSONObject defValue){
        Object value = autoSearch(jsonObjectDetails, key, defValue);
        if(value instanceof JSONObject)
            return (JSONObject) value;
        return defValue;
    }

    /** Method to get from {@link JSONArray} a jsonObject
     * @param index: index of JSONObject to get from json
     * @return value as {@link JSONObject}, if it is not exist will return null value
     * **/
    public JSONObject getJSONObject(int index){
        return getJSONObject(index, null);
    }

    /** Method to get from {@link JSONArray} a jsonObject
     * @param index: index of JSONObject to get from json
     * @param defValue: default value to return if primary value not exists
     * @return value as {@link JSONObject}, if it is not exist will return {@code defValue}
     * **/
    public JSONObject getJSONObject(int index, JSONObject defValue){
        try {
            return jsonArrayDetails.getJSONObject(index);
        }catch (Exception e){
            return defValue;
        }
    }

    /** Method to get from {@link JSONObject} a string value
     * @implNote this static method is useful when you have to fetch a single value from {@link JSONObject} avoiding
     * instantiation of {@link JsonHelper} class, but if you have to fetch multiple value from the same
     * {@link JSONObject} is recommended instantiate {@link JsonHelper} class first.
     * @param jsonDetails: JSON from fetch data
     * @param key: key of string value to get from json
     * @exception ClassCastException: when this exception has been trowed will be returned null as default value
     * @return value as {@link String}, if it is not exist will return null value
     * **/
    public static String getString(JSONObject jsonDetails, String key){
        return getString(jsonDetails, key, null);
    }

    /** Method to get from {@link JSONObject} a string value
     * @implNote this static method is useful when you have to fetch a single value from {@link JSONObject} avoiding
     * instantiation of {@link JsonHelper} class, but if you have to fetch multiple value from the same
     * {@link JSONObject} is recommended instantiate {@link JsonHelper} class first.
     * @param jsonDetails: JSON from fetch data
     * @param key: key of string value to get from json
     * @param defValue: default value to return if primary value not exists
     * @exception ClassCastException: when this exception has been trowed will be returned null as default value
     * @return value as {@link String}, if it is not exist will return {@code defValue}
     * **/
    public static String getString(JSONObject jsonDetails, String key, String defValue){
        try {
           return autoSearch(jsonDetails, key, defValue);
        }catch (ClassCastException e){
            return null;
        }
    }

    /**
     * Method to get from {@link JSONObject} a double value
     * @param jsonDetails: JSON from fetch data
     * @param key: key of double value to get from json
     * @return value as double, if it is not exist will return {@link #NUMERIC_DEF_VALUE_IF_MISSED}
     * @throws ClassCastException: when this exception has been thrown will be returned {@link #NUMERIC_CLASS_CAST_ERROR_VALUE}
     *                             as default value
     * @implNote this static method is useful when you have to fetch a single value from {@link JSONObject} avoiding
     * instantiation of {@link JsonHelper} class, but if you have to fetch multiple value from the same
     * {@link JSONObject} is recommended instantiate {@link JsonHelper} class first.
     **/
    public static double getDouble(JSONObject jsonDetails, String key){
        return getDouble(jsonDetails, key, NUMERIC_DEF_VALUE_IF_MISSED);
    }

    /** Method to get from {@link JSONObject} a double value
     * @implNote this static method is useful when you have to fetch a single value from {@link JSONObject} avoiding
     * instantiation of {@link JsonHelper} class, but if you have to fetch multiple value from the same
     * {@link JSONObject} is recommended instantiate {@link JsonHelper} class first.
     * @param jsonDetails: JSON from fetch data
     * @param key: key of double value to get from json
     * @param defValue: default value to return if primary value not exists
     * @exception ClassCastException: when this exception has been thrown will be returned {@link #NUMERIC_CLASS_CAST_ERROR_VALUE}
     * as default value
     * @return value as double, if it is not exist will return {@code defValue}
     * **/
    public static double getDouble(JSONObject jsonDetails, String key, double defValue){
        try {
            Object value = autoSearch(jsonDetails, key, defValue);
            if(value instanceof Number)
                return ((Number)value).doubleValue();
            else
                return Double.parseDouble(value.toString());
        }catch (ClassCastException | NumberFormatException e){
            return NUMERIC_CLASS_CAST_ERROR_VALUE;
        }
    }

    /** Method to get from {@link JSONObject} an int value
     * @implNote this static method is useful when you have to fetch a single value from {@link JSONObject} avoiding
     * instantiation of {@link JsonHelper} class, but if you have to fetch multiple value from the same
     * {@link JSONObject} is recommended instantiate {@link JsonHelper} class first.
     * @param jsonDetails: JSON from fetch data
     * @param key: key of int value to get from json
     * @exception ClassCastException: when this exception has been thrown will be returned {@link #NUMERIC_CLASS_CAST_ERROR_VALUE}
     * as default value
     * @return value as int, if it is not exist will return {@link #NUMERIC_DEF_VALUE_IF_MISSED}
     * **/
    public static int getInt(JSONObject jsonDetails, String key){
        return getInt(jsonDetails, key, NUMERIC_DEF_VALUE_IF_MISSED);
    }

    /** Method to get from {@link JSONObject} an int value
     * @implNote this static method is useful when you have to fetch a single value from {@link JSONObject} avoiding
     * instantiation of {@link JsonHelper} class, but if you have to fetch multiple value from the same
     * {@link JSONObject} is recommended instantiate {@link JsonHelper} class first.
     * @param jsonDetails: JSON from fetch data
     * @param key: key of int value to get from json
     * @param defValue: default value to return if primary value not exists
     * @exception ClassCastException: when this exception has been thrown will be returned {@link #NUMERIC_CLASS_CAST_ERROR_VALUE}
     * as default value
     * @return value as int, if it is not exist will return {@code defValue}
     * **/
    public static int getInt(JSONObject jsonDetails, String key, int defValue){
        try {
            Object value = autoSearch(jsonDetails, key, defValue);
            if(value instanceof Number)
                return ((Number)value).intValue();
            else
                return Integer.parseInt(value.toString());
        }catch (ClassCastException | NumberFormatException e){
            return NUMERIC_CLASS_CAST_ERROR_VALUE;
        }
    }

    /** Method to get from {@link JSONObject} a float value
     * @implNote this static method is useful when you have to fetch a single value from {@link JSONObject} avoiding
     * instantiation of {@link JsonHelper} class, but if you have to fetch multiple value from the same
     * {@link JSONObject} is recommended instantiate {@link JsonHelper} class first.
     * @param jsonDetails: JSON from fetch data
     * @param key: key of float value to get from json
     * @exception ClassCastException: when this exception has been thrown will be returned {@link #NUMERIC_CLASS_CAST_ERROR_VALUE}
     * as default value
     * @return value as float, if it is not exist will return {@link #NUMERIC_DEF_VALUE_IF_MISSED}
     * **/
    public static float getFloat(JSONObject jsonDetails, String key){
        return getFloat(jsonDetails, key, NUMERIC_DEF_VALUE_IF_MISSED);
    }

    /** Method to get from {@link JSONObject} a float value
     * @implNote this static method is useful when you have to fetch a single value from {@link JSONObject} avoiding
     * instantiation of {@link JsonHelper} class, but if you have to fetch multiple value from the same
     * {@link JSONObject} is recommended instantiate {@link JsonHelper} class first.
     * @param jsonDetails: JSON from fetch data
     * @param key: key of float value to get from json
     * @param defValue: default value to return if primary value not exists
     * @exception ClassCastException: when this exception has been thrown will be returned {@link #NUMERIC_CLASS_CAST_ERROR_VALUE}
     * as default value
     * @return value as float, if it is not exist will return {@code defValue}
     * **/
    public static float getFloat(JSONObject jsonDetails, String key, float defValue){
        try {
            Object value = autoSearch(jsonDetails, key, defValue);
            if(value instanceof Number)
                return ((Number)value).floatValue();
            else
                return Float.parseFloat(value.toString());
        }catch (ClassCastException | NumberFormatException e){
            return NUMERIC_CLASS_CAST_ERROR_VALUE;
        }
    }

    /** Method to get from {@link JSONObject} a long value
     * @implNote this static method is useful when you have to fetch a single value from {@link JSONObject} avoiding
     * instantiation of {@link JsonHelper} class, but if you have to fetch multiple value from the same
     * {@link JSONObject} is recommended instantiate {@link JsonHelper} class first.
     * @param jsonDetails: JSON from fetch data
     * @param key: key of long value to get from json
     * @exception ClassCastException: when this exception has been thrown will be returned {@link #NUMERIC_CLASS_CAST_ERROR_VALUE}
     * as default value
     * @return value as long, if it is not exist will return {@link #NUMERIC_DEF_VALUE_IF_MISSED}
     * **/
    public static long getLong(JSONObject jsonDetails, String key){
        return getLong(jsonDetails, key, NUMERIC_DEF_VALUE_IF_MISSED);
    }

    /** Method to get from {@link JSONObject} a long value
     * @implNote this static method is useful when you have to fetch a single value from {@link JSONObject} avoiding
     * instantiation of {@link JsonHelper} class, but if you have to fetch multiple value from the same
     * {@link JSONObject} is recommended instantiate {@link JsonHelper} class first.
     * @param jsonDetails: JSON from fetch data
     * @param key: key of long value to get from json
     * @param defValue: default value to return if primary value not exists
     * @exception ClassCastException: when this exception has been thrown will be returned {@link #NUMERIC_CLASS_CAST_ERROR_VALUE}
     * as default value
     * @return value as long, if it is not exist will return {@code defValue}
     * **/
    public static long getLong(JSONObject jsonDetails, String key, long defValue){
        try {
            Object value = autoSearch(jsonDetails, key, defValue);
            if(value instanceof Number)
                return ((Number)value).longValue();
            else
                return Long.parseLong(value.toString());
        }catch (ClassCastException | NumberFormatException e){
            return NUMERIC_CLASS_CAST_ERROR_VALUE;
        }
    }

    /** Method to get from {@link JSONObject} a BigDecimal value
     * @implNote this static method is useful when you have to fetch a single value from {@link JSONObject} avoiding
     * instantiation of {@link JsonHelper} class, but if you have to fetch multiple value from the same
     * {@link JSONObject} is recommended instantiate {@link JsonHelper} class first.
     * @param jsonDetails: JSON from fetch data
     * @param key: key of BigDecimal value to get from json
     * @exception ClassCastException: when this exception has been trowed will be returned null as default value
     * @return value as {@link BigDecimal}, if it is not exist will return null value
     * **/
    public static BigDecimal getBigDecimal(JSONObject jsonDetails, String key){
        return getBigDecimal(jsonDetails, key, null);
    }

    /** Method to get from {@link JSONObject} a BigDecimal value
     * @implNote this static method is useful when you have to fetch a single value from {@link JSONObject} avoiding
     * instantiation of {@link JsonHelper} class, but if you have to fetch multiple value from the same
     * {@link JSONObject} is recommended instantiate {@link JsonHelper} class first.
     * @param jsonDetails: JSON from fetch data
     * @param key: key of BigDecimal value to get from json
     * @param defValue: default value to return if primary value not exists
     * @exception ClassCastException: when this exception has been trowed will be returned null as default value
     * @return value as {@link BigDecimal}, if it is not exist will return {@code defValue}
     * **/
    public static BigDecimal getBigDecimal(JSONObject jsonDetails, String key, BigDecimal defValue){
        try {
            Object value = autoSearch(jsonDetails, key, defValue);
            if(value instanceof BigDecimal)
                return (BigDecimal) value;
            return BigDecimal.valueOf(Double.parseDouble(value.toString()));
        }catch (NullPointerException | ClassCastException | NumberFormatException e){
            return BigDecimal.valueOf(NUMERIC_CLASS_CAST_ERROR_VALUE);
        }
    }

    /** Method to get from {@link JSONObject} a BigInteger value
     * @implNote this static method is useful when you have to fetch a single value from {@link JSONObject} avoiding
     * instantiation of {@link JsonHelper} class, but if you have to fetch multiple value from the same
     * {@link JSONObject} is recommended instantiate {@link JsonHelper} class first.
     * @param jsonDetails: JSON from fetch data
     * @param key: key of BigInteger value to get from json
     * @exception ClassCastException: when this exception has been trowed will be returned null as default value
     * @return value as {@link BigInteger}, if it is not exist will return null value
     * **/
    public static BigInteger getBigInteger(JSONObject jsonDetails, String key){
        return getBigInteger(jsonDetails, key, null);
    }

    /** Method to get from {@link JSONObject} a BigInteger value
     * @implNote this static method is useful when you have to fetch a single value from {@link JSONObject} avoiding
     * instantiation of {@link JsonHelper} class, but if you have to fetch multiple value from the same
     * {@link JSONObject} is recommended instantiate {@link JsonHelper} class first.
     * @param jsonDetails: JSON from fetch data
     * @param key: key of BigInteger value to get from json
     * @param defValue: default value to return if primary value not exists
     * @exception ClassCastException: when this exception has been trowed will be returned null as default value
     * @return value as {@link BigInteger}, if it is not exist will return {@code defValue}
     * **/
    public static BigInteger getBigInteger(JSONObject jsonDetails, String key, BigInteger defValue){
        try {
            Object value = autoSearch(jsonDetails, key, defValue);
            if(value instanceof BigDecimal)
                return (BigInteger) value;
            return BigInteger.valueOf(Integer.parseInt(value.toString()));
        }catch (NullPointerException | ClassCastException | NumberFormatException e){
            return BigInteger.valueOf(NUMERIC_CLASS_CAST_ERROR_VALUE);
        }
    }

    /** Method to get from {@link JSONObject} a Number value
     * @implNote this static method is useful when you have to fetch a single value from {@link JSONObject} avoiding
     * instantiation of {@link JsonHelper} class, but if you have to fetch multiple value from the same
     * {@link JSONObject} is recommended instantiate {@link JsonHelper} class first.
     * @param jsonDetails: JSON from fetch data
     * @param key: key of Number value to get from json
     * @exception ClassCastException: when this exception has been trowed will be returned null as default value
     * @return value as {@link Number}, if it is not exist will return null value
     * **/
    public static Number getNumber(JSONObject jsonDetails, String key){
        return getNumber(jsonDetails, key, NUMERIC_DEF_VALUE_IF_MISSED);
    }

    /** Method to get from {@link JSONObject} a Number value
     * @implNote this static method is useful when you have to fetch a single value from {@link JSONObject} avoiding
     * instantiation of {@link JsonHelper} class, but if you have to fetch multiple value from the same
     * {@link JSONObject} is recommended instantiate {@link JsonHelper} class first.
     * @param jsonDetails: JSON from fetch data
     * @param key: key of Number value to get from json
     * @param defValue: default value to return if primary value not exists
     * @exception ClassCastException: when this exception has been trowed will be returned null as default value
     * @return value as {@link Number}, if it is not exist will return {@code defValue}
     * **/
    public static Number getNumber(JSONObject jsonDetails, String key, Number defValue){
        try {
            Object value = autoSearch(jsonDetails, key, defValue);
            return ((Number)value);
        }catch (ClassCastException | NumberFormatException e){
            return NUMERIC_CLASS_CAST_ERROR_VALUE;
        }
    }

    /** Method to get from {@link JSONObject} an Object value
     * @implNote this static method is useful when you have to fetch a single value from {@link JSONObject} avoiding
     * instantiation of {@link JsonHelper} class, but if you have to fetch multiple value from the same
     * {@link JSONObject} is recommended instantiate {@link JsonHelper} class first.
     * @param jsonDetails: JSON from fetch data
     * @param key: key of Object value to get from json
     * @exception ClassCastException: when this exception has been trowed will be returned null as default value
     * @return value as {@link Object}, if it is not exist will return null value
     * **/
    public static Object get(JSONObject jsonDetails, String key){
        return get(jsonDetails, key, null);
    }

    /** Method to get from {@link JSONObject} an Object value
     * @implNote this static method is useful when you have to fetch a single value from {@link JSONObject} avoiding
     * instantiation of {@link JsonHelper} class, but if you have to fetch multiple value from the same
     * {@link JSONObject} is recommended instantiate {@link JsonHelper} class first.
     * @param jsonDetails: JSON from fetch data
     * @param key: key of Object value to get from json
     * @param defValue: default value to return if primary value not exists
     * @exception ClassCastException: when this exception has been trowed will be returned null as default value
     * @return value as {@link Object}, if it is not exist will return {@code defValue}
     * **/
    public static Object get(JSONObject jsonDetails, String key, Object defValue){
        try {
           return autoSearch(jsonDetails, key, defValue);
        }catch (ClassCastException e){
            return null;
        }
    }

    /** Method to get from {@link JSONObject} a boolean value
     * @implNote this static method is useful when you have to fetch a single value from {@link JSONObject} avoiding
     * instantiation of {@link JsonHelper} class, but if you have to fetch multiple value from the same
     * {@link JSONObject} is recommended instantiate {@link JsonHelper} class first.
     * @param jsonDetails: JSON from fetch data
     * @param key: key of boolean value to get from json
     * @exception ClassCastException: when this exception has been trowed will be returned false as default value
     * @return value as boolean, if it is not exist will return false value
     * **/
    public static boolean getBoolean(JSONObject jsonDetails, String key){
        return getBoolean(jsonDetails, key, false);
    }

    /** Method to get from {@link JSONObject} a boolean value
     * @implNote this static method is useful when you have to fetch a single value from {@link JSONObject} avoiding
     * instantiation of {@link JsonHelper} class, but if you have to fetch multiple value from the same
     * {@link JSONObject} is recommended instantiate {@link JsonHelper} class first.
     * @param jsonDetails: JSON from fetch data
     * @param key: key of boolean value to get from json
     * @param defValue: default value to return if primary value not exists
     * @exception ClassCastException: when this exception has been trowed will be returned false as default value
     * @return value as boolean, if it is not exist will return {@code defValue}
     * **/
    public static boolean getBoolean(JSONObject jsonDetails, String key, boolean defValue){
        try {
            Object value = autoSearch(jsonDetails, key, defValue);
            if(value instanceof Boolean)
                return (boolean) value;
            else
                return Boolean.parseBoolean(value.toString());
        }catch (ClassCastException e){
            return defValue;
        }
    }

    /** Method to get from {@link JSONObject}  a list of values
     * @implNote this static method is useful when you have to fetch a single value from {@link JSONObject} avoiding
     * instantiation of {@link JsonHelper} class, but if you have to fetch multiple value from the same
     * {@link JSONObject} is recommended instantiate {@link JsonHelper} class first.
     * @param jsonDetails: JSON from fetch data
     * @param key: key of JSONArray to get from json
     * @exception ClassCastException: when this exception has been trowed will be returned null as default value
     * @return value as {@link JSONArray}, if it is not exist will return null value
     * **/
    public static JSONArray getJSONArray(JSONObject jsonDetails, String key){
        return getJSONArray(jsonDetails, key, null);
    }

    /** Method to get from {@link JSONObject}  a list of values
     * @implNote this static method is useful when you have to fetch a single value from {@link JSONObject} avoiding
     * instantiation of {@link JsonHelper} class, but if you have to fetch multiple value from the same
     * {@link JSONObject} is recommended instantiate {@link JsonHelper} class first.
     * @param jsonDetails: JSON from fetch data
     * @param key: key of JSONArray to get from json
     * @param defValue: default value to return if primary value not exists
     * @exception ClassCastException: when this exception has been trowed will be returned null as default value
     * @return value as {@link JSONArray}, if it is not exist will return {@code defValue}
     * **/
    public static JSONArray getJSONArray(JSONObject jsonDetails, String key, JSONArray defValue){
        Object value = autoSearch(jsonDetails, key, defValue);
        if(value instanceof JSONArray)
            return (JSONArray) value;
        return defValue;
    }

    /** Method to get from {@link JSONObject} a jsonObject
     * @implNote this static method is useful when you have to fetch a single value from {@link JSONObject} avoiding
     * instantiation of {@link JsonHelper} class, but if you have to fetch multiple value from the same
     * {@link JSONObject} is recommended instantiate {@link JsonHelper} class first.
     * @param jsonDetails: JSON from fetch data
     * @param key: key of JSONObject to get from json
     * @exception ClassCastException: when this exception has been trowed will be returned null as default value
     * @return value as {@link JSONObject}, if it is not exist will return null value
     * **/
    public static JSONObject getJSONObject(JSONObject jsonDetails, String key){
        return getJSONObject(jsonDetails, key, null);
    }

    /** Method to get from {@link JSONObject} a jsonObject
     * @implNote this static method is useful when you have to fetch a single value from {@link JSONObject} avoiding
     * instantiation of {@link JsonHelper} class, but if you have to fetch multiple value from the same
     * {@link JSONObject} is recommended instantiate {@link JsonHelper} class first.
     * @param jsonDetails: JSON from fetch data
     * @param key: key of JSONObject to get from json
     * @param defValue: default value to return if primary value not exists
     * @exception ClassCastException: when this exception has been trowed will be returned null as default value
     * @return value as {@link JSONObject}, if it is not exist will return {@code defValue}
     * **/
    public static JSONObject getJSONObject(JSONObject jsonDetails, String key, JSONObject defValue){
        Object value = autoSearch(jsonDetails, key, defValue);
        if(value instanceof JSONObject)
            return (JSONObject) value;
        return defValue;
    }

    /** Method to get from {@link JSONArray} a string value
     * @implNote this static method is useful when you have to fetch a single value from {@link JSONArray} avoiding
     * instantiation of {@link JsonHelper} class, but if you have to fetch multiple value from the same
     * {@link JSONArray} is recommended instantiate {@link JsonHelper} class first.
     * @param jsonDetails: JSON from fetch data
     * @param index: index of string value to get from json
     * @return value as {@link String}, if it is not exist will return null value
     * **/
    public static String getString(JSONArray jsonDetails, int index){
        return getString(jsonDetails, index, null);
    }

    /** Method to get from {@link JSONObject} a string value
     * @implNote this static method is useful when you have to fetch a single value from {@link JSONObject} avoiding
     * instantiation of {@link JsonHelper} class, but if you have to fetch multiple value from the same
     * {@link JSONObject} is recommended instantiate {@link JsonHelper} class first.
     * @param jsonDetails: JSON from fetch data
     * @param index: key of string value to get from json
     * @param defValue: default value to return if primary value not exists
     * @return value as {@link String}, if it is not exist will return {@code defValue}
     * **/
    public static String getString(JSONArray jsonDetails, int index, String defValue){
        try {
            return jsonDetails.getString(index);
        }catch (Exception e){
            return defValue;
        }
    }

    /** Method to get from {@link JSONArray} a double value
     * @implNote this static method is useful when you have to fetch a single value from {@link JSONArray} avoiding
     * instantiation of {@link JsonHelper} class, but if you have to fetch multiple value from the same
     * {@link JSONArray} is recommended instantiate {@link JsonHelper} class first.
     * @param jsonDetails: JSON from fetch data
     * @param index: index of double value to get from json
     * @return value as double, if it is not exist will return {@link #NUMERIC_DEF_VALUE_IF_MISSED}
     * **/
    public static double getDouble(JSONArray jsonDetails, int index){
        return getDouble(jsonDetails, index, NUMERIC_DEF_VALUE_IF_MISSED);
    }

    /** Method to get from {@link JSONArray} a double value
     * @implNote this static method is useful when you have to fetch a single value from {@link JSONArray} avoiding
     * instantiation of {@link JsonHelper} class, but if you have to fetch multiple value from the same
     * {@link JSONArray} is recommended instantiate {@link JsonHelper} class first.
     * @param jsonDetails: JSON from fetch data
     * @param index: index of double value to get from json
     * @param defValue: default value to return if primary value not exists
     * @return value as double, if it is not exist will return {@code defValue}
     * **/
    public static double getDouble(JSONArray jsonDetails, int index, double defValue){
        try {
            return jsonDetails.getDouble(index);
        }catch (Exception e){
            return defValue;
        }
    }

    /** Method to get from {@link JSONArray} an int value
     * @implNote this static method is useful when you have to fetch a single value from {@link JSONArray} avoiding
     * instantiation of {@link JsonHelper} class, but if you have to fetch multiple value from the same
     * {@link JSONArray} is recommended instantiate {@link JsonHelper} class first.
     * @param jsonDetails: JSON from fetch data
     * @param index: index of int value to get from json
     * @return value as int, if it is not exist will return {@link #NUMERIC_DEF_VALUE_IF_MISSED}
     * **/
    public static int getInt(JSONArray jsonDetails, int index){
        return getInt(jsonDetails, index, NUMERIC_DEF_VALUE_IF_MISSED);
    }

    /** Method to get from {@link JSONArray} an int value
     * @implNote this static method is useful when you have to fetch a single value from {@link JSONArray} avoiding
     * instantiation of {@link JsonHelper} class, but if you have to fetch multiple value from the same
     * {@link JSONArray} is recommended instantiate {@link JsonHelper} class first.
     * @param jsonDetails: JSON from fetch data
     * @param index: index of int value to get from json
     * @param defValue: default value to return if primary value not exists
     * @return value as int, if it is not exist will return {@code defValue}
     * **/
    public static int getInt(JSONArray jsonDetails, int index, int defValue){
        try {
            return jsonDetails.getInt(index);
        }catch (Exception e){
            return defValue;
        }
    }

    /** Method to get from {@link JSONArray} a float value
     * @implNote this static method is useful when you have to fetch a single value from {@link JSONArray} avoiding
     * instantiation of {@link JsonHelper} class, but if you have to fetch multiple value from the same
     * {@link JSONArray} is recommended instantiate {@link JsonHelper} class first.
     * @param jsonDetails: JSON from fetch data
     * @param index: index of float value to get from json
     * @return value as float, if it is not exist will return {@link #NUMERIC_DEF_VALUE_IF_MISSED}
     * **/
    public static float getFloat(JSONArray jsonDetails, int index){
        return getFloat(jsonDetails, index, NUMERIC_DEF_VALUE_IF_MISSED);
    }

    /** Method to get from {@link JSONArray} a float value
     * @implNote this static method is useful when you have to fetch a single value from {@link JSONArray} avoiding
     * instantiation of {@link JsonHelper} class, but if you have to fetch multiple value from the same
     * {@link JSONArray} is recommended instantiate {@link JsonHelper} class first.
     * @param jsonDetails: JSON from fetch data
     * @param index: index of float value to get from json
     * @param defValue: default value to return if primary value not exists
     * @return value as float, if it is not exist will return {@code defValue}
     * **/
    public static float getFloat(JSONArray jsonDetails, int index, float defValue){
        try {
            return jsonDetails.getFloat(index);
        }catch (Exception e){
            return defValue;
        }
    }

    /** Method to get from {@link JSONArray} a long value
     * @implNote this static method is useful when you have to fetch a single value from {@link JSONArray} avoiding
     * instantiation of {@link JsonHelper} class, but if you have to fetch multiple value from the same
     * {@link JSONArray} is recommended instantiate {@link JsonHelper} class first.
     * @param jsonDetails: JSON from fetch data
     * @param index: index of long value to get from json
     * @return value as long, if it is not exist will return {@link #NUMERIC_DEF_VALUE_IF_MISSED}
     * **/
    public static long getLong(JSONArray jsonDetails, int index){
        return getLong(jsonDetails, index, NUMERIC_DEF_VALUE_IF_MISSED);
    }

    /** Method to get from {@link JSONArray} a long value
     * @implNote this static method is useful when you have to fetch a single value from {@link JSONArray} avoiding
     * instantiation of {@link JsonHelper} class, but if you have to fetch multiple value from the same
     * {@link JSONArray} is recommended instantiate {@link JsonHelper} class first.
     * @param jsonDetails: JSON from fetch data
     * @param index: index of long value to get from json
     * @param defValue: default value to return if primary value not exists
     * @return value as long, if it is not exist will return {@code defValue}
     * **/
    public static long getLong(JSONArray jsonDetails, int index, long defValue){
        try {
            return jsonDetails.getLong(index);
        }catch (Exception e){
            return defValue;
        }
    }

    /** Method to get from {@link JSONArray} a BigDecimal value
     * @implNote this static method is useful when you have to fetch a single value from {@link JSONArray} avoiding
     * instantiation of {@link JsonHelper} class, but if you have to fetch multiple value from the same
     * {@link JSONArray} is recommended instantiate {@link JsonHelper} class first.
     * @param jsonDetails: JSON from fetch data
     * @param index: index of BigDecimal value to get from json
     * @return value as {@link BigDecimal}, if it is not exist will return null value
     * **/
    public static BigDecimal getBigDecimal(JSONArray jsonDetails, int index){
        return getBigDecimal(jsonDetails, index, null);
    }

    /** Method to get from {@link JSONArray} a BigDecimal value
     * @implNote this static method is useful when you have to fetch a single value from {@link JSONArray} avoiding
     * instantiation of {@link JsonHelper} class, but if you have to fetch multiple value from the same
     * {@link JSONArray} is recommended instantiate {@link JsonHelper} class first.
     * @param jsonDetails: JSON from fetch data
     * @param index: index of BigDecimal value to get from json
     * @param defValue: default value to return if primary value not exists
     * @return value as {@link BigDecimal}, if it is not exist will return {@code defValue}
     * **/
    public static BigDecimal getBigDecimal(JSONArray jsonDetails, int index, BigDecimal defValue){
        try {
            return jsonDetails.getBigDecimal(index);
        }catch (Exception e){
            return defValue;
        }
    }

    /** Method to get from {@link JSONArray} a BigInteger value
     * @implNote this static method is useful when you have to fetch a single value from {@link JSONArray} avoiding
     * instantiation of {@link JsonHelper} class, but if you have to fetch multiple value from the same
     * {@link JSONArray} is recommended instantiate {@link JsonHelper} class first.
     * @param jsonDetails: JSON from fetch data
     * @param index: index of BigInteger value to get from json
     * @return value as {@link BigInteger}, if it is not exist will return null value
     * **/
    public static BigInteger getBigInteger(JSONArray jsonDetails, int index){
        return getBigInteger(jsonDetails, index, null);
    }

    /** Method to get from {@link JSONArray} a BigInteger value
     * @implNote this static method is useful when you have to fetch a single value from {@link JSONArray} avoiding
     * instantiation of {@link JsonHelper} class, but if you have to fetch multiple value from the same
     * {@link JSONArray} is recommended instantiate {@link JsonHelper} class first.
     * @param jsonDetails: JSON from fetch data
     * @param index: index of BigInteger value to get from json
     * @param defValue: default value to return if primary value not exists
     * @return value as {@link BigInteger}, if it is not exist will return {@code defValue}
     * **/
    public static BigInteger getBigInteger(JSONArray jsonDetails, int index, BigInteger defValue){
        try {
            return jsonDetails.getBigInteger(index);
        }catch (Exception e){
            return defValue;
        }
    }

    /** Method to get from {@link JSONArray} a Number value
     * @implNote this static method is useful when you have to fetch a single value from {@link JSONArray} avoiding
     * instantiation of {@link JsonHelper} class, but if you have to fetch multiple value from the same
     * {@link JSONArray} is recommended instantiate {@link JsonHelper} class first.
     * @param jsonDetails: JSON from fetch data
     * @param index: index of Number value to get from json
     * @return value as {@link Number}, if it is not exist will return null value
     * **/
    public static Number getNumber(JSONArray jsonDetails, int index){
        return getNumber(jsonDetails, index, null);
    }

    /** Method to get from {@link JSONArray} a Number value
     * @implNote this static method is useful when you have to fetch a single value from {@link JSONArray} avoiding
     * instantiation of {@link JsonHelper} class, but if you have to fetch multiple value from the same
     * {@link JSONArray} is recommended instantiate {@link JsonHelper} class first.
     * @param jsonDetails: JSON from fetch data
     * @param index: index of Number value to get from json
     * @param defValue: default value to return if primary value not exists
     * @return value as {@link Number}, if it is not exist will return {@code defValue}
     * **/
    public static Number getNumber(JSONArray jsonDetails, int index, Number defValue){
        try {
            return jsonDetails.getNumber(index);
        }catch (Exception e){
            return defValue;
        }
    }

    /** Method to get from {@link JSONArray} an object value
     * @implNote this static method is useful when you have to fetch a single value from {@link JSONArray} avoiding
     * instantiation of {@link JsonHelper} class, but if you have to fetch multiple value from the same
     * {@link JSONArray} is recommended instantiate {@link JsonHelper} class first.
     * @param jsonDetails: JSON from fetch data
     * @param index: index of Object value to get from json
     * @return value as {@link Object}, if it is not exist will return null value
     * **/
    public static Object get(JSONArray jsonDetails, int index){
        return get(jsonDetails, index, null);
    }

    /** Method to get from {@link JSONArray} an object value
     * @implNote this static method is useful when you have to fetch a single value from {@link JSONArray} avoiding
     * instantiation of {@link JsonHelper} class, but if you have to fetch multiple value from the same
     * {@link JSONArray} is recommended instantiate {@link JsonHelper} class first.
     * @param jsonDetails: JSON from fetch data
     * @param index: index of Object value to get from json
     * @param defValue: default value to return if primary value not exists
     * @return value as {@link Object}, if it is not exist will return {@code defValue}
     * **/
    public static Object get(JSONArray jsonDetails, int index, Object defValue){
        try {
            return jsonDetails.get(index);
        }catch (Exception e){
            return defValue;
        }
    }

    /** Method to get from {@link JSONArray} a boolean value
     * @implNote this static method is useful when you have to fetch a single value from {@link JSONArray} avoiding
     * instantiation of {@link JsonHelper} class, but if you have to fetch multiple value from the same
     * {@link JSONArray} is recommended instantiate {@link JsonHelper} class first.
     * @param jsonDetails: JSON from fetch data
     * @param index: index of boolean value to get from json
     * @return value as boolean, if it is not exist will return false value
     * **/
    public static boolean getBoolean(JSONArray jsonDetails, int index){
        return getBoolean(jsonDetails, index, false);
    }

    /** Method to get from {@link JSONArray} a boolean value
     * @implNote this static method is useful when you have to fetch a single value from {@link JSONArray} avoiding
     * instantiation of {@link JsonHelper} class, but if you have to fetch multiple value from the same
     * {@link JSONArray} is recommended instantiate {@link JsonHelper} class first.
     * @param jsonDetails: JSON from fetch data
     * @param index: index of boolean value to get from json
     * @param defValue: default value to return if primary value not exists
     * @return value as boolean, if it is not exist will return {@code defValue}
     * **/
    public static boolean getBoolean(JSONArray jsonDetails, int index, boolean defValue){
        try {
            return jsonDetails.getBoolean(index);
        }catch (Exception e){
            return defValue;
        }
    }

    /** Method to get from {@link JSONArray}  a list of values
     * @implNote this static method is useful when you have to fetch a single value from {@link JSONArray} avoiding
     * instantiation of {@link JsonHelper} class, but if you have to fetch multiple value from the same
     * {@link JSONArray} is recommended instantiate {@link JsonHelper} class first.
     * @param jsonDetails: JSON from fetch data
     * @param index: index of JSONArray to get from json
     * @return value as {@link JSONArray}, if it is not exist will return null value
     * **/
    public static JSONArray getJSONArray(JSONArray jsonDetails, int index){
        return getJSONArray(jsonDetails, index, null);
    }

    /** Method to get from {@link JSONArray}  a list of values
     * @implNote this static method is useful when you have to fetch a single value from {@link JSONArray} avoiding
     * instantiation of {@link JsonHelper} class, but if you have to fetch multiple value from the same
     * {@link JSONArray} is recommended instantiate {@link JsonHelper} class first.
     * @param jsonDetails: JSON from fetch data
     * @param index: index of JSONArray to get from json
     * @param defValue: default value to return if primary value not exists
     * @return value as {@link JSONArray}, if it is not exist will return {@code defValue}
     * **/
    public static JSONArray getJSONArray(JSONArray jsonDetails, int index, JSONArray defValue){
        try {
            return jsonDetails.getJSONArray(index);
        }catch (Exception e){
            return defValue;
        }
    }

    /** Method to get from {@link JSONArray} a jsonObject
     * @implNote this static method is useful when you have to fetch a single value from {@link JSONArray} avoiding
     * instantiation of {@link JsonHelper} class, but if you have to fetch multiple value from the same
     * {@link JSONArray} is recommended instantiate {@link JsonHelper} class first.
     * @param jsonDetails: JSON from fetch data
     * @param index: index of JSONObject to get from json
     * @return value as {@link JSONObject}, if it is not exist will return null value
     * **/
    public static JSONObject getJSONObject(JSONArray jsonDetails, int index){
        return getJSONObject(jsonDetails, index, null);
    }

    /** Method to get from {@link JSONArray} a jsonObject
     * @implNote this static method is useful when you have to fetch a single value from {@link JSONArray} avoiding
     * instantiation of {@link JsonHelper} class, but if you have to fetch multiple value from the same
     * {@link JSONArray} is recommended instantiate {@link JsonHelper} class first.
     * @param jsonDetails: JSON from fetch data
     * @param index: index of JSONObject to get from json
     * @param defValue: default value to return if primary value not exists
     * @return value as {@link JSONObject}, if it is not exist will return {@code defValue}
     * **/
    public static JSONObject getJSONObject(JSONArray jsonDetails, int index, JSONObject defValue){
        try {
            return jsonDetails.getJSONObject(index);
        }catch (Exception e){
            return defValue;
        }
    }

    /** Method to get from {@link JSONObject} a list of values automatically
     * @apiNote this method does not need specific path of the {@link JSONObject} to fetch value, but it will in automatic
     * reach the value requested directly from the entire JSON file without path inserted by hand
     * @param searchKey: key for value to fetch
     * @return list of values as {@link ArrayList} of {@link T}, if it is not exist will return null
     * **/
    public <T> ArrayList<T> fetchOList(String searchKey){
        return fetchOList(jsonObjectDetails, searchKey);
    }

    /** Method to get from {@link JSONObject} a list of values automatically
     * @apiNote this method does not need specific path of the {@link JSONObject} to fetch value, but it will in automatic
     * reach the value requested directly from the entire JSON file without path inserted by hand
     * @param json: {@link JSONObject} from fetch list
     * @param searchKey: key for value to fetch
     * @return list of values as {@link ArrayList} of {@link T}, if it is not exist will return null
     * **/
    public static <T> ArrayList<T> fetchOList(JSONObject json, String searchKey){
        return autoSearch(prepareList(json), searchKey);
    }

    /** Method to prepare a {@link JSONObject} for auto list search
     * @apiNote this method will remove all keys that are not {@link JSONArray}
     * @param list: {@link JSONObject} to prepare
     * @return list of values as {@link JSONObject}
     * **/
    private static JSONObject prepareList(JSONObject list){
        Iterator<String> keys = list.keys();
        while (keys.hasNext()){
            String key = keys.next();
            if(!(list.get(key) instanceof JSONArray))
                keys.remove();
        }
        return list;
    }

    /** Method to get from {@link JSONArray} a list of values automatically
     * @apiNote this method does not need specific path of the {@link JSONArray} to fetch value, but it will in automatic
     * reach the value requested directly from the entire JSON file without path inserted by hand
     * @param searchKey: key for value to fetch
     * @return list of values as {@link ArrayList} of {@link T}, if it is not exist will return null
     * **/
    public <T> ArrayList<T> fetchList(String searchKey){
        return fetchList(jsonArrayDetails, searchKey);
    }

    /** Method to get from {@link JSONArray} a list of values automatically
     * @apiNote this method does not need specific path of the {@link JSONArray} to fetch value, but it will in automatic
     * reach the value requested directly from the entire JSON file without path inserted by hand
     * @param list: {@link JSONArray} from fetch list
     * @param searchKey: key for value to fetch
     * @return list of values as {@link ArrayList} of {@link T}, if it is not exist will return null
     * **/
    public static <T> ArrayList<T> fetchList(JSONArray list, String searchKey){
        return autoSearch(list, searchKey);
    }

    /** Method to get from {@link JSONObject} a generic value
     * @apiNote this method does not need specific path of the {@link JSONObject} to fetch value, but it will in automatic
     * reach the value requested directly from the entire JSON file without path inserted by hand
     * @param json: {@link JSONObject} from fetch data
     * @param searchKey: key for value to fetch
     * @return value as {@link T}, if it is not exist will return null
     * **/
    private static <T> T autoSearch(JSONObject json, String searchKey) {
        ArrayList<String> subKeys = new ArrayList<>();
        if(json.toString().contains("\"" + searchKey + "\":")) {
            for (String key : json.keySet()) {
                if (key.equals(searchKey))
                    return (T) json.get(searchKey);
                else {
                    T JSONType = (T) json.get(key);
                    if (((JSONType instanceof JSONObject || JSONType instanceof JSONArray)
                            && JSONType.toString().contains(searchKey)))
                        subKeys.add(key);
                }
            }
            for (String subKey : subKeys) {
                T JSONType = (T) json.get(subKey);
                if (JSONType instanceof JSONObject)
                    return autoSearch(json.getJSONObject(subKey), searchKey);
                else if (JSONType instanceof JSONArray)
                    return autoSearch(json.getJSONArray(subKey), searchKey);
            }
        }
        return null;
    }

    /** Method to get from {@link JSONObject} a generic value
     * @apiNote this method does not need specific path of the {@link JSONObject} to fetch value, but it will in automatic
     * reach the value requested directly from the entire JSON file without path inserted by hand
     * @param json: {@link JSONObject} from fetch data
     * @param searchKey: key for value to fetch
     * @param defValue: default value to return if primary value not exists
     * @return value as {@link T}, if it is not exist will return {@code defValue}
     * **/
    private static <T> T autoSearch(JSONObject json, String searchKey, T defValue) {
        T search = autoSearch(json, searchKey);
        if(search == null)
            return defValue;
        return search;
    }

    /** Method to get from {@link JSONArray} a generic value
     * @apiNote this method does not need specific path of the {@link JSONArray} to fetch value, but it will in automatic
     * reach the value requested directly from the entire JSON file without path inserted by hand
     * @param list: {@link JSONArray} from fetch data
     * @param searchKey: key for value to fetch
     * @return value as {@link ArrayList} of {@link T}, if it is not exist will return null
     * **/
    private static <T> T autoSearch(JSONArray list, String searchKey) {
        if(list.toString().contains("\"" + searchKey + "\":")){
            ArrayList<T> values = new ArrayList<>();
            for (int j = 0; j < list.length(); j++) {
                T result = (T) list.get(j);
                if (result instanceof JSONObject) {
                    try {
                        values.add(autoSearch((JSONObject) result, searchKey));
                    } catch (JSONException e) {
                        return null;
                    }
                } else
                    values.add((T) list.get(j));
            }
            int size = values.size();
            if (size > 0) {
                if (size == 1)
                    return values.get(0);
                return (T) values;
            }
        }
        return null;
    }

    /** Method to get from {@link JSONArray} a generic value
     * @apiNote this method does not need specific path of the {@link JSONArray} to fetch value, but it will in automatic
     * reach the value requested directly from the entire JSON file without path inserted by hand
     * @param list: {@link JSONArray} from fetch data
     * @param searchKey: key for value to fetch
     * @param defValue: default value to return if primary value not exists
     * @return value as {@link ArrayList} of {@link T}, if it is not exist will return {@code defValue}
     * **/
    private static <T> T autoSearch(JSONArray list, String searchKey, T defValue) {
        ArrayList<T> search = autoSearch(list, searchKey);
        if(search == null)
            return defValue;
        return (T) search;
    }

    /**
     * Method to set {@link #jsonObjectDetails} instance
     * @param jsonObjectDetails: new {@link JSONObject}
     * @throws IllegalArgumentException when jsonObjectDetails is null
     * **/
    public void setJSONObjectDetails(JSONObject jsonObjectDetails) {
        if(jsonObjectDetails == null)
            throw new IllegalArgumentException("JSON cannot be null");
        this.jsonObjectDetails = jsonObjectDetails;
    }

    public JSONObject getJSONObjectDetails() {
        return jsonObjectDetails;
    }

    /**
     * Method to set {@link #jsonArrayDetails} instance
     * @param jsonArrayDetails: new {@link JSONArray}
     * @throws IllegalArgumentException when jsonArrayDetails is null
     * **/
    public void setJSONArrayDetails(JSONArray jsonArrayDetails) {
        if(jsonArrayDetails == null)
            throw new IllegalArgumentException("JSON cannot be null");
        this.jsonArrayDetails = jsonArrayDetails;
    }

    public JSONArray getJSONArrayDetails() {
        return jsonArrayDetails;
    }

    @Override
    public String toString() {
        return "JsonHelper{" +
                "jsonObjectDetails=" + jsonObjectDetails +
                ", jsonArrayDetails=" + jsonArrayDetails +
                '}';
    }
    
}
