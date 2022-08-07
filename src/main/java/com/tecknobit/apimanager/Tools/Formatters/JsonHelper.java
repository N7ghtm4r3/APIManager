package com.tecknobit.apimanager.Tools.Formatters;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;

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
        return autoSearch(jsonObjectDetails, key);
    }

    /** Method to get from {@link JSONObject} a string value
     * @param key: key of string value to get from json
     * @param defValue: default value to return if primary value not exists
     * @return value as {@link String}, if it is not exist will return {@code defValue}
     * **/
    public String getString(String key, String defValue){
        return autoSearch(jsonObjectDetails, key, defValue);
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
        return autoSearch(jsonObjectDetails, key, -1);
    }

    /** Method to get from {@link JSONObject} a double value
     * @param key: key of double value to get from json
     * @param defValue: default value to return if primary value not exists
     * @return value as double, if it is not exist will return {@code defValue}
     * **/
    public double getDouble(String key, double defValue){
        return autoSearch(jsonObjectDetails, key, defValue);
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
        return autoSearch(jsonObjectDetails, key, -1);
    }

    /** Method to get from {@link JSONObject} an int value
     * @param key: key of int value to get from json
     * @param defValue: default value to return if primary value not exists
     * @return value as int, if it is not exist will return {@code defValue}
     * **/
    public int getInt(String key, int defValue){
        return autoSearch(jsonObjectDetails, key, defValue);
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
        return autoSearch(jsonObjectDetails, key, -1);
    }

    /** Method to get from {@link JSONObject} a float value
     * @param key: key of float value to get from json
     * @param defValue: default value to return if primary value not exists
     * @return value as float, if it is not exist will return {@code defValue}
     * **/
    public float getFloat(String key, float defValue){
        return autoSearch(jsonObjectDetails, key, defValue);
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
        return autoSearch(jsonObjectDetails, key, -1);
    }

    /** Method to get from {@link JSONObject} a long value
     * @param key: key of long value to get from json
     * @param defValue: default value to return if primary value not exists
     * @return value as long, if it is not exist will return {@code defValue}
     * **/
    public long getLong(String key, long defValue){
        return autoSearch(jsonObjectDetails, key, defValue);
    }

    /** Method to get from {@link JSONArray} a long value
     * @param index: index of long value to get from json
     * @return value as long, if it is not exist will return -1 value
     * **/
    public long getLong(int index){
        try {
            return jsonArrayDetails.getLong(index);
        }catch (Exception e){
            return -1;
        }
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
     * @return value as {@link BigDecimal}, if it is not exist will return null value
     * **/
    public BigDecimal getBigDecimal(String key){
        return autoSearch(jsonObjectDetails, key);
    }

    /** Method to get from {@link JSONObject} a BigDecimal value
     * @param key: key of BigDecimal value to get from json
     * @param defValue: default value to return if primary value not exists
     * @return value as {@link BigDecimal}, if it is not exist will return {@code defValue}
     * **/
    public BigDecimal getBigDecimal(String key, BigDecimal defValue){
        return autoSearch(jsonObjectDetails, key, defValue);
    }

    /** Method to get from {@link JSONArray} a BigDecimal value
     * @param index: index of BigDecimal value to get from json
     * @return value as {@link BigDecimal}, if it is not exist will return null value
     * **/
    public BigDecimal getBigDecimal(int index){
        try {
            return jsonArrayDetails.getBigDecimal(index);
        }catch (Exception e){
            return null;
        }
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
     * @return value as {@link BigInteger}, if it is not exist will return null value
     * **/
    public BigInteger getBigInteger(String key){
        return autoSearch(jsonObjectDetails, key);
    }

    /** Method to get from {@link JSONObject} a BigInteger value
     * @param key: key of BigInteger value to get from json
     * @param defValue: default value to return if primary value not exists
     * @return value as {@link BigInteger}, if it is not exist will return {@code defValue}
     * **/
    public BigInteger getBigInteger(String key, BigInteger defValue){
        return autoSearch(jsonObjectDetails, key, defValue);
    }

    /** Method to get from {@link JSONArray} a BigInteger value
     * @param index: index of BigInteger value to get from json
     * @return value as {@link BigInteger}, if it is not exist will return null value
     * **/
    public BigInteger getBigInteger(int index){
        try {
            return jsonArrayDetails.getBigInteger(index);
        }catch (Exception e){
            return null;
        }
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
     * @return value as {@link Number}, if it is not exist will return null value
     * **/
    public Number getNumber(String key){
        return autoSearch(jsonObjectDetails, key);
    }

    /** Method to get from {@link JSONObject} a Number value
     * @param key: key of Number value to get from json
     * @param defValue: default value to return if primary value not exists
     * @return value as {@link Number}, if it is not exist will return {@code defValue}
     * **/
    public Number getNumber(String key, Number defValue){
        return autoSearch(jsonObjectDetails, key, defValue);
    }

    /** Method to get from {@link JSONArray} a Number value
     * @param index: index of Number value to get from json
     * @return value as {@link Number}, if it is not exist will return null value
     * **/
    public Number getNumber(int index){
        try {
            return jsonArrayDetails.getNumber(index);
        }catch (Exception e){
            return null;
        }
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
     * @return value as {@link Object}, if it is not exist will return null value
     * **/
    public Object get(String key){
        return autoSearch(jsonObjectDetails, key);
    }

    /** Method to get from {@link JSONObject} an object value
     * @param key: key of Object value to get from json
     * @param defValue: default value to return if primary value not exists
     * @return value as {@link Object}, if it is not exist will return {@code defValue}
     * **/
    public Object get(String key, Object defValue){
        return autoSearch(jsonObjectDetails, key, defValue);
    }

    /** Method to get from {@link JSONArray} an object value
     * @param index: index of Object value to get from json
     * @return value as {@link Object}, if it is not exist will return null value
     * **/
    public Object get(int index){
        try {
            return jsonArrayDetails.get(index);
        }catch (Exception e){
            return null;
        }
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
     * @return value as boolean, if it is not exist will return false value
     * **/
    public boolean getBoolean(String key){
        return autoSearch(jsonObjectDetails, key, false);
    }

    /** Method to get from {@link JSONObject} a boolean value
     * @param key: key of boolean value to get from json
     * @param defValue: default value to return if primary value not exists
     * @return value as boolean, if it is not exist will return {@code defValue}
     * **/
    public boolean getBoolean(String key, boolean defValue){
        return autoSearch(jsonObjectDetails, key, defValue);
    }

    /** Method to get from {@link JSONArray} a boolean value
     * @param index: index of boolean value to get from json
     * @return value as boolean, if it is not exist will return false value
     * **/
    public boolean getBoolean(int index){
        try {
            return jsonArrayDetails.getBoolean(index);
        }catch (Exception e){
            return false;
        }
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
     * @return value as {@link JSONArray}, if it is not exist will return null value
     * **/
    public JSONArray getJSONArray(String key){
        return autoSearch(jsonObjectDetails, key);
    }

    /** Method to get from {@link JSONObject}  a list of values
     * @param key: key of JSONArray to get from json
     * @param defValue: default value to return if primary value not exists
     * @return value as {@link JSONArray}, if it is not exist will return {@code defValue}
     * **/
    public JSONArray getJSONArray(String key, JSONArray defValue){
        return autoSearch(jsonObjectDetails, key, defValue);
    }

    /** Method to get from {@link JSONArray}  a list of values
     * @param index: index of JSONArray to get from json
     * @return value as {@link JSONArray}, if it is not exist will return null value
     * **/
    public JSONArray getJSONArray(int index){
        try {
            return jsonArrayDetails.getJSONArray(index);
        }catch (Exception e){
            return null;
        }
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
     * @return value as {@link JSONObject}, if it is not exist will return null value
     * **/
    public JSONObject getJSONObject(String key){
        return autoSearch(jsonObjectDetails, key);
    }

    /** Method to get from {@link JSONObject} a jsonObject
     * @param key: key of JSONObject to get from json
     * @param defValue: default value to return if primary value not exists
     * @return value as {@link JSONObject}, if it is not exist will return {@code defValue}
     * **/
    public JSONObject getJSONObject(String key, JSONObject defValue){
        return autoSearch(jsonObjectDetails, key, defValue);
    }

    /** Method to get from {@link JSONArray} a jsonObject
     * @param index: index of JSONObject to get from json
     * @return value as {@link JSONObject}, if it is not exist will return null value
     * **/
    public JSONObject getJSONObject(int index){
        try {
            return jsonArrayDetails.getJSONObject(index);
        }catch (Exception e){
            return null;
        }
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
     * @return value as {@link String}, if it is not exist will return null value
     * **/
    public static String getString(JSONObject jsonDetails, String key){
        return autoSearch(jsonDetails, key);
    }

    /** Method to get from {@link JSONObject} a string value
     * @implNote this static method is useful when you have to fetch a single value from {@link JSONObject} avoiding
     * instantiation of {@link JsonHelper} class, but if you have to fetch multiple value from the same
     * {@link JSONObject} is recommended instantiate {@link JsonHelper} class first.
     * @param jsonDetails: JSON from fetch data
     * @param key: key of string value to get from json
     * @param defValue: default value to return if primary value not exists
     * @return value as {@link String}, if it is not exist will return {@code defValue}
     * **/
    public static String getString(JSONObject jsonDetails, String key, String defValue){
        return autoSearch(jsonDetails, key, defValue);
    }

    /** Method to get from {@link JSONObject} a double value
     * @implNote this static method is useful when you have to fetch a single value from {@link JSONObject} avoiding
     * instantiation of {@link JsonHelper} class, but if you have to fetch multiple value from the same
     * {@link JSONObject} is recommended instantiate {@link JsonHelper} class first.
     * @param jsonDetails: JSON from fetch data
     * @param key: key of double value to get from json
     * @return value as double, if it is not exist will return -1 value
     * **/
    public static double getDouble(JSONObject jsonDetails, String key){
        return autoSearch(jsonDetails, key, -1);
    }

    /** Method to get from {@link JSONObject} a double value
     * @implNote this static method is useful when you have to fetch a single value from {@link JSONObject} avoiding
     * instantiation of {@link JsonHelper} class, but if you have to fetch multiple value from the same
     * {@link JSONObject} is recommended instantiate {@link JsonHelper} class first.
     * @param jsonDetails: JSON from fetch data
     * @param key: key of double value to get from json
     * @param defValue: default value to return if primary value not exists
     * @return value as double, if it is not exist will return {@code defValue}
     * **/
    public static double getDouble(JSONObject jsonDetails, String key, double defValue){
        return autoSearch(jsonDetails, key, defValue);
    }

    /** Method to get from {@link JSONObject} an int value
     * @implNote this static method is useful when you have to fetch a single value from {@link JSONObject} avoiding
     * instantiation of {@link JsonHelper} class, but if you have to fetch multiple value from the same
     * {@link JSONObject} is recommended instantiate {@link JsonHelper} class first.
     * @param jsonDetails: JSON from fetch data
     * @param key: key of int value to get from json
     * @return value as int, if it is not exist will return -1 value
     * **/
    public static int getInt(JSONObject jsonDetails, String key){
        return autoSearch(jsonDetails, key, -1);
    }

    /** Method to get from {@link JSONObject} an int value
     * @implNote this static method is useful when you have to fetch a single value from {@link JSONObject} avoiding
     * instantiation of {@link JsonHelper} class, but if you have to fetch multiple value from the same
     * {@link JSONObject} is recommended instantiate {@link JsonHelper} class first.
     * @param jsonDetails: JSON from fetch data
     * @param key: key of int value to get from json
     * @param defValue: default value to return if primary value not exists
     * @return value as int, if it is not exist will return {@code defValue}
     * **/
    public static int getInt(JSONObject jsonDetails, String key, int defValue){
        return autoSearch(jsonDetails, key, defValue);
    }

    /** Method to get from {@link JSONObject} a float value
     * @implNote this static method is useful when you have to fetch a single value from {@link JSONObject} avoiding
     * instantiation of {@link JsonHelper} class, but if you have to fetch multiple value from the same
     * {@link JSONObject} is recommended instantiate {@link JsonHelper} class first.
     * @param jsonDetails: JSON from fetch data
     * @param key: key of float value to get from json
     * @return value as float, if it is not exist will return -1 value
     * **/
    public static float getFloat(JSONObject jsonDetails, String key){
        return autoSearch(jsonDetails, key, -1);
    }

    /** Method to get from {@link JSONObject} a float value
     * @implNote this static method is useful when you have to fetch a single value from {@link JSONObject} avoiding
     * instantiation of {@link JsonHelper} class, but if you have to fetch multiple value from the same
     * {@link JSONObject} is recommended instantiate {@link JsonHelper} class first.
     * @param jsonDetails: JSON from fetch data
     * @param key: key of float value to get from json
     * @param defValue: default value to return if primary value not exists
     * @return value as float, if it is not exist will return {@code defValue}
     * **/
    public static float getFloat(JSONObject jsonDetails, String key, float defValue){
        return autoSearch(jsonDetails, key, defValue);
    }

    /** Method to get from {@link JSONObject} a long value
     * @implNote this static method is useful when you have to fetch a single value from {@link JSONObject} avoiding
     * instantiation of {@link JsonHelper} class, but if you have to fetch multiple value from the same
     * {@link JSONObject} is recommended instantiate {@link JsonHelper} class first.
     * @param jsonDetails: JSON from fetch data
     * @param key: key of long value to get from json
     * @return value as long, if it is not exist will return -1 value
     * **/
    public static long getLong(JSONObject jsonDetails, String key){
        return autoSearch(jsonDetails, key, -1);
    }

    /** Method to get from {@link JSONObject} a long value
     * @implNote this static method is useful when you have to fetch a single value from {@link JSONObject} avoiding
     * instantiation of {@link JsonHelper} class, but if you have to fetch multiple value from the same
     * {@link JSONObject} is recommended instantiate {@link JsonHelper} class first.
     * @param jsonDetails: JSON from fetch data
     * @param key: key of long value to get from json
     * @param defValue: default value to return if primary value not exists
     * @return value as long, if it is not exist will return {@code defValue}
     * **/
    public static long getLong(JSONObject jsonDetails, String key, long defValue){
        return autoSearch(jsonDetails, key, defValue);
    }

    /** Method to get from {@link JSONObject} a BigDecimal value
     * @implNote this static method is useful when you have to fetch a single value from {@link JSONObject} avoiding
     * instantiation of {@link JsonHelper} class, but if you have to fetch multiple value from the same
     * {@link JSONObject} is recommended instantiate {@link JsonHelper} class first.
     * @param jsonDetails: JSON from fetch data
     * @param key: key of BigDecimal value to get from json
     * @return value as {@link BigDecimal}, if it is not exist will return null value
     * **/
    public static BigDecimal getBigDecimal(JSONObject jsonDetails, String key){
        return autoSearch(jsonDetails, key);
    }

    /** Method to get from {@link JSONObject} a BigDecimal value
     * @implNote this static method is useful when you have to fetch a single value from {@link JSONObject} avoiding
     * instantiation of {@link JsonHelper} class, but if you have to fetch multiple value from the same
     * {@link JSONObject} is recommended instantiate {@link JsonHelper} class first.
     * @param jsonDetails: JSON from fetch data
     * @param key: key of BigDecimal value to get from json
     * @param defValue: default value to return if primary value not exists
     * @return value as {@link BigDecimal}, if it is not exist will return {@code defValue}
     * **/
    public static BigDecimal getBigDecimal(JSONObject jsonDetails, String key, BigDecimal defValue){
        return autoSearch(jsonDetails, key, defValue);
    }

    /** Method to get from {@link JSONObject} a BigInteger value
     * @implNote this static method is useful when you have to fetch a single value from {@link JSONObject} avoiding
     * instantiation of {@link JsonHelper} class, but if you have to fetch multiple value from the same
     * {@link JSONObject} is recommended instantiate {@link JsonHelper} class first.
     * @param jsonDetails: JSON from fetch data
     * @param key: key of BigInteger value to get from json
     * @return value as {@link BigInteger}, if it is not exist will return null value
     * **/
    public static BigInteger getBigInteger(JSONObject jsonDetails, String key){
        return autoSearch(jsonDetails, key);
    }

    /** Method to get from {@link JSONObject} a BigInteger value
     * @implNote this static method is useful when you have to fetch a single value from {@link JSONObject} avoiding
     * instantiation of {@link JsonHelper} class, but if you have to fetch multiple value from the same
     * {@link JSONObject} is recommended instantiate {@link JsonHelper} class first.
     * @param jsonDetails: JSON from fetch data
     * @param key: key of BigInteger value to get from json
     * @param defValue: default value to return if primary value not exists
     * @return value as {@link BigInteger}, if it is not exist will return {@code defValue}
     * **/
    public static BigInteger getBigInteger(JSONObject jsonDetails, String key, BigInteger defValue){
        return autoSearch(jsonDetails, key, defValue);
    }

    /** Method to get from {@link JSONObject} a Number value
     * @implNote this static method is useful when you have to fetch a single value from {@link JSONObject} avoiding
     * instantiation of {@link JsonHelper} class, but if you have to fetch multiple value from the same
     * {@link JSONObject} is recommended instantiate {@link JsonHelper} class first.
     * @param jsonDetails: JSON from fetch data
     * @param key: key of Number value to get from json
     * @return value as {@link Number}, if it is not exist will return null value
     * **/
    public static Number getNumber(JSONObject jsonDetails, String key){
        return autoSearch(jsonDetails, key);
    }

    /** Method to get from {@link JSONObject} a Number value
     * @implNote this static method is useful when you have to fetch a single value from {@link JSONObject} avoiding
     * instantiation of {@link JsonHelper} class, but if you have to fetch multiple value from the same
     * {@link JSONObject} is recommended instantiate {@link JsonHelper} class first.
     * @param jsonDetails: JSON from fetch data
     * @param key: key of Number value to get from json
     * @param defValue: default value to return if primary value not exists
     * @return value as {@link Number}, if it is not exist will return {@code defValue}
     * **/
    public static Number getNumber(JSONObject jsonDetails, String key, Number defValue){
        return autoSearch(jsonDetails, key, defValue);
    }

    /** Method to get from {@link JSONObject} an Object value
     * @implNote this static method is useful when you have to fetch a single value from {@link JSONObject} avoiding
     * instantiation of {@link JsonHelper} class, but if you have to fetch multiple value from the same
     * {@link JSONObject} is recommended instantiate {@link JsonHelper} class first.
     * @param jsonDetails: JSON from fetch data
     * @param key: key of Object value to get from json
     * @return value as {@link Object}, if it is not exist will return null value
     * **/
    public static Object get(JSONObject jsonDetails, String key){
        return autoSearch(jsonDetails, key);
    }

    /** Method to get from {@link JSONObject} an Object value
     * @implNote this static method is useful when you have to fetch a single value from {@link JSONObject} avoiding
     * instantiation of {@link JsonHelper} class, but if you have to fetch multiple value from the same
     * {@link JSONObject} is recommended instantiate {@link JsonHelper} class first.
     * @param jsonDetails: JSON from fetch data
     * @param key: key of Object value to get from json
     * @param defValue: default value to return if primary value not exists
     * @return value as {@link Object}, if it is not exist will return {@code defValue}
     * **/
    public static Object get(JSONObject jsonDetails, String key, Object defValue){
        return autoSearch(jsonDetails, key, defValue);
    }

    /** Method to get from {@link JSONObject} a boolean value
     * @implNote this static method is useful when you have to fetch a single value from {@link JSONObject} avoiding
     * instantiation of {@link JsonHelper} class, but if you have to fetch multiple value from the same
     * {@link JSONObject} is recommended instantiate {@link JsonHelper} class first.
     * @param jsonDetails: JSON from fetch data
     * @param key: key of boolean value to get from json
     * @return value as boolean, if it is not exist will return false value
     * **/
    public static boolean getBoolean(JSONObject jsonDetails, String key){
        return autoSearch(jsonDetails, key, false);
    }

    /** Method to get from {@link JSONObject} a boolean value
     * @implNote this static method is useful when you have to fetch a single value from {@link JSONObject} avoiding
     * instantiation of {@link JsonHelper} class, but if you have to fetch multiple value from the same
     * {@link JSONObject} is recommended instantiate {@link JsonHelper} class first.
     * @param jsonDetails: JSON from fetch data
     * @param key: key of boolean value to get from json
     * @param defValue: default value to return if primary value not exists
     * @return value as boolean, if it is not exist will return {@code defValue}
     * **/
    public static boolean getBoolean(JSONObject jsonDetails, String key, boolean defValue){
        return autoSearch(jsonDetails, key, defValue);
    }

    /** Method to get from {@link JSONObject}  a list of values
     * @implNote this static method is useful when you have to fetch a single value from {@link JSONObject} avoiding
     * instantiation of {@link JsonHelper} class, but if you have to fetch multiple value from the same
     * {@link JSONObject} is recommended instantiate {@link JsonHelper} class first.
     * @param jsonDetails: JSON from fetch data
     * @param key: key of JSONArray to get from json
     * @return value as {@link JSONArray}, if it is not exist will return null value
     * **/
    public static JSONArray getJSONArray(JSONObject jsonDetails, String key){
        return autoSearch(jsonDetails, key);
    }

    /** Method to get from {@link JSONObject}  a list of values
     * @implNote this static method is useful when you have to fetch a single value from {@link JSONObject} avoiding
     * instantiation of {@link JsonHelper} class, but if you have to fetch multiple value from the same
     * {@link JSONObject} is recommended instantiate {@link JsonHelper} class first.
     * @param jsonDetails: JSON from fetch data
     * @param key: key of JSONArray to get from json
     * @param defValue: default value to return if primary value not exists
     * @return value as {@link JSONArray}, if it is not exist will return {@code defValue}
     * **/
    public static JSONArray getJSONArray(JSONObject jsonDetails, String key, JSONArray defValue){
        return autoSearch(jsonDetails, key, defValue);
    }

    /** Method to get from {@link JSONObject} a jsonObject
     * @implNote this static method is useful when you have to fetch a single value from {@link JSONObject} avoiding
     * instantiation of {@link JsonHelper} class, but if you have to fetch multiple value from the same
     * {@link JSONObject} is recommended instantiate {@link JsonHelper} class first.
     * @param jsonDetails: JSON from fetch data
     * @param key: key of JSONObject to get from json
     * @return value as {@link JSONObject}, if it is not exist will return null value
     * **/
    public static JSONObject getJSONObject(JSONObject jsonDetails, String key){
        return autoSearch(jsonDetails, key);
    }

    /** Method to get from {@link JSONObject} a jsonObject
     * @implNote this static method is useful when you have to fetch a single value from {@link JSONObject} avoiding
     * instantiation of {@link JsonHelper} class, but if you have to fetch multiple value from the same
     * {@link JSONObject} is recommended instantiate {@link JsonHelper} class first.
     * @param jsonDetails: JSON from fetch data
     * @param key: key of JSONObject to get from json
     * @param defValue: default value to return if primary value not exists
     * @return value as {@link JSONObject}, if it is not exist will return {@code defValue}
     * **/
    public static JSONObject getJSONObject(JSONObject jsonDetails, String key, JSONObject defValue){
        return autoSearch(jsonDetails, key, defValue);
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
        try {
            return jsonDetails.getString(index);
        }catch (Exception e){
            return null;
        }
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
     * @return value as double, if it is not exist will return -1 value
     * **/
    public static double getDouble(JSONArray jsonDetails, int index){
        try {
            return jsonDetails.getDouble(index);
        }catch (Exception e){
            return -1;
        }
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
     * @return value as int, if it is not exist will return -1 value
     * **/
    public static int getInt(JSONArray jsonDetails, int index){
        try {
            return jsonDetails.getInt(index);
        }catch (Exception e){
            return -1;
        }
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
     * @return value as float, if it is not exist will return -1 value
     * **/
    public static float getFloat(JSONArray jsonDetails, int index){
        try {
            return jsonDetails.getFloat(index);
        }catch (Exception e){
            return -1;
        }
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
     * @return value as long, if it is not exist will return -1 value
     * **/
    public static long getLong(JSONArray jsonDetails, int index){
        try {
            return jsonDetails.getLong(index);
        }catch (Exception e){
            return -1;
        }
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
        try {
            return jsonDetails.getBigDecimal(index);
        }catch (Exception e){
            return null;
        }
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
        try {
            return jsonDetails.getBigInteger(index);
        }catch (Exception e){
            return null;
        }
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
        try {
            return jsonDetails.getNumber(index);
        }catch (Exception e){
            return null;
        }
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
        try {
            return jsonDetails.get(index);
        }catch (Exception e){
            return null;
        }
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
        try {
            return jsonDetails.getBoolean(index);
        }catch (Exception e){
            return false;
        }
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
        try {
            return jsonDetails.getJSONArray(index);
        }catch (Exception e){
            return null;
        }
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
        try {
            return jsonDetails.getJSONObject(index);
        }catch (Exception e){
            return null;
        }
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

    /** Method to get from {@link JSONObject} a generic value
     * @apiNote this method does not need specific path of the {@link JSONObject} to fetch value, but it will in automatic
     * reach the value requested directly from the entire JSON file without path inserted by hand
     * @param json: {@link JSONObject} from fetch data
     * @param searchKey: key for value to fetch
     * @return value as {@link T}, if it is not exist will return null
     * **/
    public static <T> T autoSearch(JSONObject json, String searchKey) {
        String jsonString = json.toString();
        for (String key : json.keySet()){
            if(key.equals(searchKey))
                return (T) json.get(searchKey);
            else {
                T genericJSON = (T) json.get(key);
                if(genericJSON instanceof JSONObject) {
                    T search = autoSearch(json.getJSONObject(key), searchKey);
                    if(search != null)
                        return autoSearch(json.getJSONObject(key), searchKey);
                    else
                        return null;
                }else if(json.get(key) instanceof JSONArray) {
                    ArrayList<T> search = autoSearch(json.getJSONArray(key), searchKey);
                    if(search != null)
                        return (T) autoSearch(json.getJSONArray(key), searchKey);
                    else
                        return null;
                }
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
    public static <T> T autoSearch(JSONObject json, String searchKey, T defValue) {
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
    public static <T> ArrayList<T> autoSearch(JSONArray list, String searchKey) {
        ArrayList<T> values = new ArrayList<>();
        for (int j = 0; j < list.length(); j++) {
            T result = (T) list.get(j);
            if(result instanceof JSONObject) {
                try {
                    values.add((T) ((JSONObject) result).get(searchKey));
                }catch (JSONException e) {
                    return null;
                }
            }else
                values.add((T) list.get(j));
        }
        if(values.size() > 0)
            return values;
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
    public static <T> T autoSearch(JSONArray list, String searchKey, T defValue) {
        ArrayList<T> search = autoSearch(list, searchKey);
        if(search == null)
            return defValue;
        return (T) search;
    }

    @Override
    public String toString() {
        return "JsonHelper{" +
                "jsonObjectDetails=" + jsonObjectDetails +
                ", jsonArrayDetails=" + jsonArrayDetails +
                '}';
    }
    
}
