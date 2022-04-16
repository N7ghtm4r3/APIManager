package com.tecknobit.apimanager.Manager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.apache.commons.codec.binary.Hex.encodeHexString;

/**
 * The {@code APIRequest} class is useful to send and manage all json API requests
 * @author Tecknobit N7ghtm4r3
 * **/

public class APIRequest {

    public static final String GET_METHOD = "GET";
    public static final String POST_METHOD = "POST";
    public static final String DELETE_METHOD = "DELETE";
    public static final String PUT_METHOD = "PUT";
    private HttpURLConnection httpURLConnection;
    private String defaultErrorResponse;
    private String errorResponse;
    private String response;
    private int requestTimeout;

    /** Constructor to init APIRequest manager
     * @param #defaultErrorResponse error message to return if is not request error
     * @param #requestTimeout timeout for the requests
     * **/
    public APIRequest(String defaultErrorResponse, int requestTimeout) {
        this.defaultErrorResponse = defaultErrorResponse;
        this.requestTimeout = requestTimeout;
    }

    /** Constructor to init APIRequest manager
     * @param #defaultErrorResponse error message to return if is not request error
     * **/
    public APIRequest(String defaultErrorResponse) {
        this.defaultErrorResponse = defaultErrorResponse;
    }

    /** Constructor to init APIRequest manager
     * @param #requestTimeout timeout for the requests
     * **/
    public APIRequest(int requestTimeout) {
        this.requestTimeout = requestTimeout;
        defaultErrorResponse = "Error is not in api request, check out your code";
    }

    /** Constructor to init APIRequest manager
     * requestTimeout and defaultErrorResponse initialized with default values.
     * **/
    public APIRequest() {
        requestTimeout = 10000;
        defaultErrorResponse = "Error is not in api request, check out your code";
    }

    /** Method to set programmatically timeout for the request
     * @param #requestTimeout: timeout for the requests
     * any return
     * **/
    public void setRequestTimeout(int requestTimeout) {
        this.requestTimeout = requestTimeout;
    }

    /** Method to set programmatically default error message to return if is not request error
     * @param #defaultErrorResponse: error message to return if is not request error
     * any return
     * **/
    public void setDefaultErrorResponse(String defaultErrorResponse) {
        this.defaultErrorResponse = defaultErrorResponse;
    }

    /** Method to make api request
     * @param #requestUrl: url used to make api request
     * @param #method: method used to make api request
     * any return
     * **/
    public void sendAPIRequest(String requestUrl, String method) throws IOException {
        setRequestConnection(requestUrl, method);
        sendRequest();
    }

    /** Method to make api request with one mandatory header es. api key and its key header value
     * @param #requestUrl: url used to make api request
     * @param #method: method used to make api request
     * @param #headerKey: mandatory header key for the request
     * @param #headerValue: mandatory header value for the request
     * any return
     * **/
    public void sendAPIRequest(String requestUrl, String method, String headerKey, String headerValue) throws IOException {
        setRequestConnection(requestUrl, method);
        httpURLConnection.setRequestProperty(headerKey, headerValue);
        sendRequest();
    }

    /** Method to make api request with many mandatory headers es. api key and its key header value
     * @param #requestUrl: url used to make api request
     * @param #method: method used to make api request
     * @param #headers: mandatory headers key and headers value for the request
     * any return
     * **/
    public void sendAPIRequest(String requestUrl, String method, HashMap<String,String> headers) throws IOException {
        setRequestConnection(requestUrl, method);
        for (String key : headers.keySet())
            httpURLConnection.setRequestProperty(key, headers.get(key));
        sendRequest();
    }

    /** Method to set up connection by an endpoint
     * @param #requestUrl: url used to make HTTP request
     * @param #method: method used to make HTTP request
     * any return
     * **/
    private void setRequestConnection(String requestUrl, String method) throws IOException {
        httpURLConnection = (HttpURLConnection) new URL(requestUrl).openConnection();
        httpURLConnection.setRequestMethod(method);
        httpURLConnection.setConnectTimeout(requestTimeout);
    }

    /** Method to send an HTTP request and get it response
     * any params required
     * any return
     * **/
    private void sendRequest() throws IOException {
        httpURLConnection.connect();
        getRequestResponse();
    }

    /** Method to get response of an HTTP request
     * any params required
     * any return
     * **/
    private void getRequestResponse() throws IOException {
        if(response == null){
            StringBuilder stringBuilder = new StringBuilder();
            BufferedReader bufferedReader;
            String line;
            boolean isInError = false;
            try {
                bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
            } catch (IOException e) {
                bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getErrorStream()));
                isInError = true;
            }
            while ((line = bufferedReader.readLine()) != null)
                stringBuilder.append(line);
            response = stringBuilder.toString();
            if(isInError)
                errorResponse = response;
        }
    }

    /** Method to get params signature of an HTTP request
     * @param #key: secret key used to signature request
     * @param #data: data to sign
     * @return signature es. c8db66725ae71d6d79447319e617115f4a920f5agcdabcb2838bd6b712b053c4"
     * **/
    public String getSignature(String key, String data) throws Exception {
        Mac sha256 = Mac.getInstance("HmacSHA256");
        sha256.init(new SecretKeySpec(key.getBytes(UTF_8), "HmacSHA256"));
        return encodeHexString(sha256.doFinal(data.replace("?","").getBytes(UTF_8)));
    }

    /** Method to assemble a String params of an HTTP request
     * @param #mandatoryParams: mandatory params of request (?param=mandatory1&param2=mandatory2)
     * @param #extraParams: not mandatory params of request that have to be concatenated (&param2=valueParam2&param3=valueParam3)
     * @return params as {@link String} assembled es. ?param=mandatory1&param2=mandatory2&param2=valueParam2&param3=valueParam3
     * **/
    public String assembleAdditionalParams(String mandatoryParams, HashMap<String, Object> extraParams){
        StringBuilder params = new StringBuilder(mandatoryParams);
        for (String key : extraParams.keySet())
            params.append("&").append(key).append("=").append(extraParams.get(key));
        return params.toString();
    }

    /** Method to get response of request, already red, without read again {@link HttpURLConnection}'s stream
     * any params required
     * @return response of request as {@link String}
     * **/
    public String getResponse() {
        return response;
    }

    /** Method to get response of request, already red, without read again {@link HttpURLConnection}'s stream
     * any params required
     * @return response of request formatted as {@link JSONObject} or {@link JSONArray} object
     * **/
    public Object getJSONResponse() throws IOException {
        return getJSONResponseObject(response);
    }

    /** Method to get error response of request, already red, without read again {@link HttpURLConnection}'s stream
     * any params required
     * @return error response of request as {@link String} or defaultErrorResponse as {@link String} if is not a request
     * error
     * **/
    public String getErrorResponse(){
        if(errorResponse == null)
            return defaultErrorResponse;
        return errorResponse;
    }

     /** Method to get error response of request, already red, without read again {@link HttpURLConnection}'s stream
     * any params required
     * @return error response of request formatted as {@link JSONObject} or {@link JSONArray} object or defaultErrorResponse
     * as {@link String} if is not a request error
     * **/
    public Object getJSONErrorResponse() {
        if(errorResponse == null)
            return defaultErrorResponse;
        return getJSONResponseObject(errorResponse);
    }

    /** Method to get JSON object of response of request, already red, without read again {@link HttpURLConnection}'s stream
     * @param #response: success response or errorResponse
     * @return response of request formatted as {@link JSONObject} or {@link JSONArray} object
     * @throws JSONException if is not a JSON format response and return response as {@link String}
     * **/
    private Object getJSONResponseObject(String response){
        try {
            if(response.startsWith("["))
                return new JSONArray(response);
            return new JSONObject(response);
        }catch (JSONException e){
            return response;
        }
    }

}