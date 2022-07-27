package com.tecknobit.apimanager.Tools.Formatters;

import org.json.JSONArray;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * The {@code JsonHelper} class is a useful class tool to fetch data from a JSONObject and get a default
 * value if is not exist in that JSONObject
 * @author Tecknobit N7ghtm4r3
 * **/

public class JsonHelper{

    /**
     * {@code jsonObjectDetails} is instance that memorize json object to work on
     * **/
    private final JSONObject jsonObjectDetails;

    /**
     * {@code jsonObjectDetails} is instance that memorize json object to work on
     * **/
    private final JSONArray jsonArrayDetails;

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
     * @return value as {@link String}, if it is not exist will return null value
     * **/
    public String getString(String key){
        try {
            return jsonObjectDetails.getString(key);
        }catch (Exception e){
            return null;
        }
    }

    /** Method to get from {@link JSONObject} a string value
     * @param key: key of string value to get from json
     * @param defValue: default value to return if primary value not exists
     * @return value as {@link String}, if it is not exist will return {@code defValue}
     * **/
    public String getString(String key, String defValue){
        try {
            return jsonObjectDetails.getString(key);
        }catch (Exception e){
            return defValue;
        }
    }

    /** Method to get from {@link JSONArray} a string value
     * @param index: index of string value to get from json
     * @return value as {@link String}, if it is not exist will return null value
     * **/
    public String getString(int index){
        try {
            return jsonArrayDetails.getString(index);
        }catch (Exception e){
            return null;
        }
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
     * @return value as double, if it is not exist will return -1 value
     * **/
    public double getDouble(String key){
        try {
            return jsonObjectDetails.getDouble(key);
        }catch (Exception e){
            return -1;
        }
    }

    /** Method to get from {@link JSONObject} a double value
     * @param key: key of double value to get from json
     * @param defValue: default value to return if primary value not exists
     * @return value as double, if it is not exist will return {@code defValue}
     * **/
    public double getDouble(String key, double defValue){
        try {
            return jsonObjectDetails.getDouble(key);
        }catch (Exception e){
            return defValue;
        }
    }

    /** Method to get from {@link JSONArray} a double value
     * @param index: index of double value to get from json
     * @return value as double, if it is not exist will return -1 value
     * **/
    public double getDouble(int index){
        try {
            return jsonArrayDetails.getDouble(index);
        }catch (Exception e){
            return -1;
        }
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
     * @return value as int, if it is not exist will return -1 value
     * **/
    public int getInt(String key){
        try {
            return jsonObjectDetails.getInt(key);
        }catch (Exception e){
            return -1;
        }
    }

    /** Method to get from {@link JSONObject} an int value
     * @param key: key of int value to get from json
     * @param defValue: default value to return if primary value not exists
     * @return value as int, if it is not exist will return {@code defValue}
     * **/
    public int getInt(String key, int defValue){
        try {
            return jsonObjectDetails.getInt(key);
        }catch (Exception e){
            return defValue;
        }
    }

    /** Method to get from {@link JSONArray} an int value
     * @param index: index of int value to get from json
     * @return value as int, if it is not exist will return -1 value
     * **/
    public int getInt(int index){
        try {
            return jsonArrayDetails.getInt(index);
        }catch (Exception e){
            return -1;
        }
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
     * @return value as float, if it is not exist will return -1 value
     * **/
    public float getFloat(String key){
        try {
            return jsonObjectDetails.getFloat(key);
        }catch (Exception e){
            return -1;
        }
    }

    /** Method to get from {@link JSONObject} a float value
     * @param key: key of float value to get from json
     * @param defValue: default value to return if primary value not exists
     * @return value as float, if it is not exist will return {@code defValue}
     * **/
    public float getFloat(String key, float defValue){
        try {
            return jsonObjectDetails.getFloat(key);
        }catch (Exception e){
            return defValue;
        }
    }

    /** Method to get from {@link JSONArray} a float value
     * @param index: index of float value to get from json
     * @return value as float, if it is not exist will return -1 value
     * **/
    public float getFloat(int index){
        try {
            return jsonArrayDetails.getFloat(index);
        }catch (Exception e){
            return -1;
        }
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
     * @return value as long, if it is not exist will return -1 value
     * **/
    public long getLong(String key){
        try {
            return jsonObjectDetails.getLong(key);
        }catch (Exception e){
            return -1;
        }
    }

    /** Method to get from {@link JSONObject} a long value
     * @param key: key of long value to get from json
     * @param defValue: default value to return if primary value not exists
     * @return value as long, if it is not exist will return {@code defValue}
     * **/
    public long getLong(String key, long defValue){
        try {
            return jsonObjectDetails.getLong(key);
        }catch (Exception e){
            return defValue;
        }
    }

    /** Method to get from {@link JSONObject} a BigDecimal value
     * @param key: key of BigDecimal value to get from json
     * @return value as {@link BigDecimal}, if it is not exist will return null value
     * **/
    public BigDecimal getBigDecimal(String key){
        try {
            return jsonObjectDetails.getBigDecimal(key);
        }catch (Exception e){
            return null;
        }
    }

    /** Method to get from {@link JSONObject} a BigDecimal value
     * @param key: key of BigDecimal value to get from json
     * @param defValue: default value to return if primary value not exists
     * @return value as {@link BigDecimal}, if it is not exist will return {@code defValue}
     * **/
    public BigDecimal getBigDecimal(String key, BigDecimal defValue){
        try {
            return jsonObjectDetails.getBigDecimal(key);
        }catch (Exception e){
            return defValue;
        }
    }

    /** Method to get from {@link JSONObject} a BigInteger value
     * @param key: key of BigInteger value to get from json
     * @return value as {@link BigInteger}, if it is not exist will return null value
     * **/
    public BigInteger getBigInteger(String key){
        try {
            return jsonObjectDetails.getBigInteger(key);
        }catch (Exception e){
            return null;
        }
    }

    /** Method to get from {@link JSONObject} a BigInteger value
     * @param key: key of BigInteger value to get from json
     * @param defValue: default value to return if primary value not exists
     * @return value as {@link BigInteger}, if it is not exist will return {@code defValue}
     * **/
    public BigInteger getBigInteger(String key, BigInteger defValue){
        try {
            return jsonObjectDetails.getBigInteger(key);
        }catch (Exception e){
            return defValue;
        }
    }

    /** Method to get from {@link JSONObject} a Number value
     * @param key: key of Number value to get from json
     * @return value as {@link Number}, if it is not exist will return null value
     * **/
    public Number getNumber(String key){
        try {
            return jsonObjectDetails.getNumber(key);
        }catch (Exception e){
            return null;
        }
    }

    /** Method to get from {@link JSONObject} a Number value
     * @param key: key of Number value to get from json
     * @param defValue: default value to return if primary value not exists
     * @return value as {@link Number}, if it is not exist will return {@code defValue}
     * **/
    public Number getNumber(String key, Number defValue){
        try {
            return jsonObjectDetails.getNumber(key);
        }catch (Exception e){
            return defValue;
        }
    }

    /** Method to get from {@link JSONObject} a Object value
     * @param key: key of Object value to get from json
     * @return value as {@link Object}, if it is not exist will return null value
     * **/
    public Object get(String key){
        try {
            return jsonObjectDetails.get(key);
        }catch (Exception e){
            return null;
        }
    }

    /** Method to get from {@link JSONObject} a Object value
     * @param key: key of Object value to get from json
     * @param defValue: default value to return if primary value not exists
     * @return value as {@link Object}, if it is not exist will return {@code defValue}
     * **/
    public Object get(String key, Object defValue){
        try {
            return jsonObjectDetails.get(key);
        }catch (Exception e){
            return defValue;
        }
    }

    /** Method to get from {@link JSONObject} a boolean value
     * @param key: key of boolean value to get from json
     * @return value as boolean, if it is not exist will return false value
     * **/
    public boolean getBoolean(String key){
        try {
            return jsonObjectDetails.getBoolean(key);
        }catch (Exception e){
            return false;
        }
    }

    /** Method to get from {@link JSONObject} a boolean value
     * @param key: key of boolean value to get from json
     * @param defValue: default value to return if primary value not exists
     * @return value as boolean, if it is not exist will return {@code defValue}
     * **/
    public boolean getBoolean(String key, boolean defValue){
        try {
            return jsonObjectDetails.getBoolean(key);
        }catch (Exception e){
            return defValue;
        }
    }

    /** Method to get from {@link JSONObject}  a list of values
     * @param key: key of JSONArray to get from json
     * @return value as {@link JSONArray}, if it is not exist will return null value
     * **/
    public JSONArray getJSONArray(String key){
        try {
            return jsonObjectDetails.getJSONArray(key);
        }catch (Exception e){
            return null;
        }
    }

    /** Method to get from {@link JSONObject}  a list of values
     * @param key: key of JSONArray to get from json
     * @param defValue: default value to return if primary value not exists
     * @return value as {@link JSONArray}, if it is not exist will return {@code defValue}
     * **/
    public JSONArray getJSONArray(String key, JSONArray defValue){
        try {
            return jsonObjectDetails.getJSONArray(key);
        }catch (Exception e){
            return defValue;
        }
    }

    /** Method to get from {@link JSONObject} a jsonObject
     * @param key: key of JSONObject to get from json
     * @return value as {@link JSONObject}, if it is not exist will return null value
     * **/
    public JSONObject getJSONObject(String key){
        try {
            return jsonObjectDetails.getJSONObject(key);
        }catch (Exception e){
            return null;
        }
    }

    /** Method to get from {@link JSONObject} a jsonObject
     * @param key: key of JSONObject to get from json
     * @param defValue: default value to return if primary value not exists
     * @return value as {@link JSONObject}, if it is not exist will return {@code defValue}
     * **/
    public JSONObject getJSONObject(String key, JSONObject defValue){
        try {
            return jsonObjectDetails.getJSONObject(key);
        }catch (Exception e){
            return defValue;
        }
    }

    /** Method to get from {@link JSONObject} a string value
     * @implNote this static method is useful when you have to fetch a single value from {@link JSONObject} avoiding
     * instantiation of {@link JsonHelper} class, but if you have to fetch multiple value from the same
     * {@link JSONObject} is recommended instantiate {@link JsonHelper} class first.
     * @param key: key of string value to get from json
     * @return value as {@link String}, if it is not exist will return null value
     * **/
    public static String getString(JSONObject jsonDetails, String key){
        try {
            return jsonDetails.getString(key);
        }catch (Exception e){
            return null;
        }
    }

    /** Method to get from {@link JSONObject} a string value
     * @implNote this static method is useful when you have to fetch a single value from {@link JSONObject} avoiding
     * instantiation of {@link JsonHelper} class, but if you have to fetch multiple value from the same
     * {@link JSONObject} is recommended instantiate {@link JsonHelper} class first.
     * @param key: key of string value to get from json
     * @param defValue: default value to return if primary value not exists
     * @return value as {@link String}, if it is not exist will return {@code defValue}
     * **/
    public static String getString(JSONObject jsonDetails, String key, String defValue){
        try {
            return jsonDetails.getString(key);
        }catch (Exception e){
            return defValue;
        }
    }

    /** Method to get from {@link JSONObject} a double value
     * @implNote this static method is useful when you have to fetch a single value from {@link JSONObject} avoiding
     * instantiation of {@link JsonHelper} class, but if you have to fetch multiple value from the same
     * {@link JSONObject} is recommended instantiate {@link JsonHelper} class first.
     * @param key: key of double value to get from json
     * @return value as double, if it is not exist will return -1 value
     * **/
    public static double getDouble(JSONObject jsonDetails, String key){
        try {
            return jsonDetails.getDouble(key);
        }catch (Exception e){
            return -1;
        }
    }

    /** Method to get from {@link JSONObject} a double value
     * @implNote this static method is useful when you have to fetch a single value from {@link JSONObject} avoiding
     * instantiation of {@link JsonHelper} class, but if you have to fetch multiple value from the same
     * {@link JSONObject} is recommended instantiate {@link JsonHelper} class first.
     * @param key: key of double value to get from json
     * @param defValue: default value to return if primary value not exists
     * @return value as double, if it is not exist will return {@code defValue}
     * **/
    public static double getDouble(JSONObject jsonDetails, String key, double defValue){
        try {
            return jsonDetails.getDouble(key);
        }catch (Exception e){
            return defValue;
        }
    }

    /** Method to get from {@link JSONObject} an int value
     * @implNote this static method is useful when you have to fetch a single value from {@link JSONObject} avoiding
     * instantiation of {@link JsonHelper} class, but if you have to fetch multiple value from the same
     * {@link JSONObject} is recommended instantiate {@link JsonHelper} class first.
     * @param key: key of int value to get from json
     * @return value as int, if it is not exist will return -1 value
     * **/
    public static int getInt(JSONObject jsonDetails, String key){
        try {
            return jsonDetails.getInt(key);
        }catch (Exception e){
            return -1;
        }
    }

    /** Method to get from {@link JSONObject} an int value
     * @implNote this static method is useful when you have to fetch a single value from {@link JSONObject} avoiding
     * instantiation of {@link JsonHelper} class, but if you have to fetch multiple value from the same
     * {@link JSONObject} is recommended instantiate {@link JsonHelper} class first.
     * @param key: key of int value to get from json
     * @param defValue: default value to return if primary value not exists
     * @return value as int, if it is not exist will return {@code defValue}
     * **/
    public static int getInt(JSONObject jsonDetails, String key, int defValue){
        try {
            return jsonDetails.getInt(key);
        }catch (Exception e){
            return defValue;
        }
    }

    /** Method to get from {@link JSONObject} a float value
     * @implNote this static method is useful when you have to fetch a single value from {@link JSONObject} avoiding
     * instantiation of {@link JsonHelper} class, but if you have to fetch multiple value from the same
     * {@link JSONObject} is recommended instantiate {@link JsonHelper} class first.
     * @param key: key of float value to get from json
     * @return value as float, if it is not exist will return -1 value
     * **/
    public static float getFloat(JSONObject jsonDetails, String key){
        try {
            return jsonDetails.getFloat(key);
        }catch (Exception e){
            return -1;
        }
    }

    /** Method to get from {@link JSONObject} a float value
     * @implNote this static method is useful when you have to fetch a single value from {@link JSONObject} avoiding
     * instantiation of {@link JsonHelper} class, but if you have to fetch multiple value from the same
     * {@link JSONObject} is recommended instantiate {@link JsonHelper} class first.
     * @param key: key of float value to get from json
     * @param defValue: default value to return if primary value not exists
     * @return value as float, if it is not exist will return {@code defValue}
     * **/
    public static float getFloat(JSONObject jsonDetails, String key, float defValue){
        try {
            return jsonDetails.getFloat(key);
        }catch (Exception e){
            return defValue;
        }
    }

    /** Method to get from {@link JSONObject} a long value
     * @implNote this static method is useful when you have to fetch a single value from {@link JSONObject} avoiding
     * instantiation of {@link JsonHelper} class, but if you have to fetch multiple value from the same
     * {@link JSONObject} is recommended instantiate {@link JsonHelper} class first.
     * @param key: key of long value to get from json
     * @return value as long, if it is not exist will return -1 value
     * **/
    public static long getLong(JSONObject jsonDetails, String key){
        try {
            return jsonDetails.getLong(key);
        }catch (Exception e){
            return -1;
        }
    }

    /** Method to get from {@link JSONObject} a long value
     * @implNote this static method is useful when you have to fetch a single value from {@link JSONObject} avoiding
     * instantiation of {@link JsonHelper} class, but if you have to fetch multiple value from the same
     * {@link JSONObject} is recommended instantiate {@link JsonHelper} class first.
     * @param key: key of long value to get from json
     * @param defValue: default value to return if primary value not exists
     * @return value as long, if it is not exist will return {@code defValue}
     * **/
    public static long getLong(JSONObject jsonDetails, String key, long defValue){
        try {
            return jsonDetails.getLong(key);
        }catch (Exception e){
            return defValue;
        }
    }

    /** Method to get from {@link JSONObject} a BigDecimal value
     * @implNote this static method is useful when you have to fetch a single value from {@link JSONObject} avoiding
     * instantiation of {@link JsonHelper} class, but if you have to fetch multiple value from the same
     * {@link JSONObject} is recommended instantiate {@link JsonHelper} class first.
     * @param key: key of BigDecimal value to get from json
     * @return value as {@link BigDecimal}, if it is not exist will return null value
     * **/
    public static BigDecimal getBigDecimal(JSONObject jsonDetails, String key){
        try {
            return jsonDetails.getBigDecimal(key);
        }catch (Exception e){
            return null;
        }
    }

    /** Method to get from {@link JSONObject} a BigDecimal value
     * @implNote this static method is useful when you have to fetch a single value from {@link JSONObject} avoiding
     * instantiation of {@link JsonHelper} class, but if you have to fetch multiple value from the same
     * {@link JSONObject} is recommended instantiate {@link JsonHelper} class first.
     * @param key: key of BigDecimal value to get from json
     * @param defValue: default value to return if primary value not exists
     * @return value as {@link BigDecimal}, if it is not exist will return {@code defValue}
     * **/
    public static BigDecimal getBigDecimal(JSONObject jsonDetails, String key, BigDecimal defValue){
        try {
            return jsonDetails.getBigDecimal(key);
        }catch (Exception e){
            return defValue;
        }
    }

    /** Method to get from {@link JSONObject} a BigInteger value
     * @implNote this static method is useful when you have to fetch a single value from {@link JSONObject} avoiding
     * instantiation of {@link JsonHelper} class, but if you have to fetch multiple value from the same
     * {@link JSONObject} is recommended instantiate {@link JsonHelper} class first.
     * @param key: key of BigInteger value to get from json
     * @return value as {@link BigInteger}, if it is not exist will return null value
     * **/
    public static BigInteger getBigInteger(JSONObject jsonDetails, String key){
        try {
            return jsonDetails.getBigInteger(key);
        }catch (Exception e){
            return null;
        }
    }

    /** Method to get from {@link JSONObject} a BigInteger value
     * @implNote this static method is useful when you have to fetch a single value from {@link JSONObject} avoiding
     * instantiation of {@link JsonHelper} class, but if you have to fetch multiple value from the same
     * {@link JSONObject} is recommended instantiate {@link JsonHelper} class first.
     * @param key: key of BigInteger value to get from json
     * @param defValue: default value to return if primary value not exists
     * @return value as {@link BigInteger}, if it is not exist will return {@code defValue}
     * **/
    public static BigInteger getBigInteger(JSONObject jsonDetails, String key, BigInteger defValue){
        try {
            return jsonDetails.getBigInteger(key);
        }catch (Exception e){
            return defValue;
        }
    }

    /** Method to get from {@link JSONObject} a Number value
     * @implNote this static method is useful when you have to fetch a single value from {@link JSONObject} avoiding
     * instantiation of {@link JsonHelper} class, but if you have to fetch multiple value from the same
     * {@link JSONObject} is recommended instantiate {@link JsonHelper} class first.
     * @param key: key of Number value to get from json
     * @return value as {@link Number}, if it is not exist will return null value
     * **/
    public static Number getNumber(JSONObject jsonDetails, String key){
        try {
            return jsonDetails.getNumber(key);
        }catch (Exception e){
            return null;
        }
    }

    /** Method to get from {@link JSONObject} a Number value
     * @implNote this static method is useful when you have to fetch a single value from {@link JSONObject} avoiding
     * instantiation of {@link JsonHelper} class, but if you have to fetch multiple value from the same
     * {@link JSONObject} is recommended instantiate {@link JsonHelper} class first.
     * @param key: key of Number value to get from json
     * @param defValue: default value to return if primary value not exists
     * @return value as {@link Number}, if it is not exist will return {@code defValue}
     * **/
    public static Number getNumber(JSONObject jsonDetails, String key, Number defValue){
        try {
            return jsonDetails.getNumber(key);
        }catch (Exception e){
            return defValue;
        }
    }

    /** Method to get from {@link JSONObject} a Object value
     * @implNote this static method is useful when you have to fetch a single value from {@link JSONObject} avoiding
     * instantiation of {@link JsonHelper} class, but if you have to fetch multiple value from the same
     * {@link JSONObject} is recommended instantiate {@link JsonHelper} class first.
     * @param key: key of Object value to get from json
     * @return value as {@link Object}, if it is not exist will return null value
     * **/
    public static Object get(JSONObject jsonDetails, String key){
        try {
            return jsonDetails.get(key);
        }catch (Exception e){
            return null;
        }
    }

    /** Method to get from {@link JSONObject} a Object value
     * @implNote this static method is useful when you have to fetch a single value from {@link JSONObject} avoiding
     * instantiation of {@link JsonHelper} class, but if you have to fetch multiple value from the same
     * {@link JSONObject} is recommended instantiate {@link JsonHelper} class first.
     * @param key: key of Object value to get from json
     * @param defValue: default value to return if primary value not exists
     * @return value as {@link Object}, if it is not exist will return {@code defValue}
     * **/
    public static Object get(JSONObject jsonDetails, String key, Object defValue){
        try {
            return jsonDetails.get(key);
        }catch (Exception e){
            return defValue;
        }
    }

    /** Method to get from {@link JSONObject} a boolean value
     * @implNote this static method is useful when you have to fetch a single value from {@link JSONObject} avoiding
     * instantiation of {@link JsonHelper} class, but if you have to fetch multiple value from the same
     * {@link JSONObject} is recommended instantiate {@link JsonHelper} class first.
     * @param key: key of boolean value to get from json
     * @return value as boolean, if it is not exist will return false value
     * **/
    public static boolean getBoolean(JSONObject jsonDetails, String key){
        try {
            return jsonDetails.getBoolean(key);
        }catch (Exception e){
            return false;
        }
    }

    /** Method to get from {@link JSONObject} a boolean value
     * @implNote this static method is useful when you have to fetch a single value from {@link JSONObject} avoiding
     * instantiation of {@link JsonHelper} class, but if you have to fetch multiple value from the same
     * {@link JSONObject} is recommended instantiate {@link JsonHelper} class first.
     * @param key: key of boolean value to get from json
     * @param defValue: default value to return if primary value not exists
     * @return value as boolean, if it is not exist will return {@code defValue}
     * **/
    public static boolean getBoolean(JSONObject jsonDetails, String key, boolean defValue){
        try {
            return jsonDetails.getBoolean(key);
        }catch (Exception e){
            return defValue;
        }
    }

    /** Method to get from {@link JSONObject}  a list of values
     * @implNote this static method is useful when you have to fetch a single value from {@link JSONObject} avoiding
     * instantiation of {@link JsonHelper} class, but if you have to fetch multiple value from the same
     * {@link JSONObject} is recommended instantiate {@link JsonHelper} class first.
     * @param key: key of JSONArray to get from json
     * @return value as {@link JSONArray}, if it is not exist will return null value
     * **/
    public static JSONArray getJSONArray(JSONObject jsonDetails, String key){
        try {
            return jsonDetails.getJSONArray(key);
        }catch (Exception e){
            return null;
        }
    }

    /** Method to get from {@link JSONObject}  a list of values
     * @implNote this static method is useful when you have to fetch a single value from {@link JSONObject} avoiding
     * instantiation of {@link JsonHelper} class, but if you have to fetch multiple value from the same
     * {@link JSONObject} is recommended instantiate {@link JsonHelper} class first.
     * @param key: key of JSONArray to get from json
     * @param defValue: default value to return if primary value not exists
     * @return value as {@link JSONArray}, if it is not exist will return {@code defValue}
     * **/
    public static JSONArray getJSONArray(JSONObject jsonDetails, String key, JSONArray defValue){
        try {
            return jsonDetails.getJSONArray(key);
        }catch (Exception e){
            return defValue;
        }
    }

    /** Method to get from {@link JSONObject} a jsonObject
     * @implNote this static method is useful when you have to fetch a single value from {@link JSONObject} avoiding
     * instantiation of {@link JsonHelper} class, but if you have to fetch multiple value from the same
     * {@link JSONObject} is recommended instantiate {@link JsonHelper} class first.
     * @param key: key of JSONObject to get from json
     * @return value as {@link JSONObject}, if it is not exist will return null value
     * **/
    public static JSONObject getJSONObject(JSONObject jsonDetails, String key){
        try {
            return jsonDetails.getJSONObject(key);
        }catch (Exception e){
            return null;
        }
    }

    /** Method to get from {@link JSONObject} a jsonObject
     * @implNote this static method is useful when you have to fetch a single value from {@link JSONObject} avoiding
     * instantiation of {@link JsonHelper} class, but if you have to fetch multiple value from the same
     * {@link JSONObject} is recommended instantiate {@link JsonHelper} class first.
     * @param key: key of JSONObject to get from json
     * @param defValue: default value to return if primary value not exists
     * @return value as {@link JSONObject}, if it is not exist will return {@code defValue}
     * **/
    public static JSONObject getJSONObject(JSONObject jsonDetails, String key, JSONObject defValue){
        try {
            return jsonDetails.getJSONObject(key);
        }catch (Exception e){
            return defValue;
        }
    }

    @Override
    public String toString() {
        return "JsonHelper{" +
                "jsonObjectDetails=" + jsonObjectDetails +
                ", jsonArrayDetails=" + jsonArrayDetails +
                '}';
    }
    
}
