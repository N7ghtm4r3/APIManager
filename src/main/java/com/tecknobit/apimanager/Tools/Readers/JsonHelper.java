package com.tecknobit.apimanager.Tools.Readers;

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

    private final JSONObject jsonDetails;

    /** Constructor to init JsonHelper tool class
     * @param #jsonDetails: jsonObject used to fetch data
     * **/
    public JsonHelper(JSONObject jsonDetails) {
        this.jsonDetails = jsonDetails;
    }

    /** Method to get from jsonObject a string value
     * @param #key: key of string value to get from json
     * @return value as {@link String}, if it is not exist will return null value
     * **/
    public String getString(String key){
        try {
            return jsonDetails.getString(key);
        }catch (Exception e){
            return null;
        }
    }

    /** Method to get from jsonObject a double value
     * @param #key: key of double value to get from json
     * @return value as double, if it is not exist will return -1 value
     * **/
    public double getDouble(String key){
        try {
            return jsonDetails.getDouble(key);
        }catch (Exception e){
            return -1;
        }
    }

    /** Method to get from jsonObject an int value
     * @param #key: key of int value to get from json
     * @return value as int, if it is not exist will return -1 value
     * **/
    public int getInt(String key){
        try {
            return jsonDetails.getInt(key);
        }catch (Exception e){
            return -1;
        }
    }

    /** Method to get from jsonObject a float value
     * @param #key: key of float value to get from json
     * @return value as float, if it is not exist will return -1 value
     * **/
    public float getFloat(String key){
        try {
            return jsonDetails.getFloat(key);
        }catch (Exception e){
            return -1;
        }
    }

    /** Method to get from jsonObject a long value
     * @param #key: key of long value to get from json
     * @return value as long, if it is not exist will return -1 value
     * **/
    public long getLong(String key){
        try {
            return jsonDetails.getLong(key);
        }catch (Exception e){
            return -1;
        }
    }

    /** Method to get from jsonObject a BigDecimal value
     * @param #key: key of BigDecimal value to get from json
     * @return value as {@link BigDecimal}, if it is not exist will return null value
     * **/
    public BigDecimal getBigDecimal(String key){
        try {
            return jsonDetails.getBigDecimal(key);
        }catch (Exception e){
            return null;
        }
    }

    /** Method to get from jsonObject a BigInteger value
     * @param #key: key of BigInteger value to get from json
     * @return value as {@link BigInteger}, if it is not exist will return null value
     * **/
    public BigInteger getBigInteger(String key){
        try {
            return jsonDetails.getBigInteger(key);
        }catch (Exception e){
            return null;
        }
    }

    /** Method to get from jsonObject a Number value
     * @param #key: key of Number value to get from json
     * @return value as {@link Number}, if it is not exist will return null value
     * **/
    public Number getNumber(String key){
        try {
            return jsonDetails.getNumber(key);
        }catch (Exception e){
            return null;
        }
    }

    /** Method to get from jsonObject a Object value
     * @param #key: key of Object value to get from json
     * @return value as {@link Object}, if it is not exist will return null value
     * **/
    public Object get(String key){
        try {
            return jsonDetails.get(key);
        }catch (Exception e){
            return null;
        }
    }

    /** Method to get from jsonObject a boolean value
     * @param #key: key of boolean value to get from json
     * @return value as boolean, if it is not exist will return false value
     * **/
    public boolean getBoolean(String key){
        try {
            return jsonDetails.getBoolean(key);
        }catch (Exception e){
            return false;
        }
    }

    /** Method to get from jsonObject  a list of values
     * @param #key: key of JSONArray to get from json
     * @return value as {@link JSONArray}, if it is not exist will return null value
     * **/
    public JSONArray getJSONArray(String key){
        try {
            return jsonDetails.getJSONArray(key);
        }catch (Exception e){
            return null;
        }
    }

    /** Method to get from jsonObject a jsonObject
     * @param #key: key of JSONObject to get from json
     * @return value as {@link JSONObject}, if it is not exist will return null value
     * **/
    public JSONObject getJSONObject(String key){
        try {
            return jsonDetails.getJSONObject(key);
        }catch (Exception e){
            return null;
        }
    }

}
