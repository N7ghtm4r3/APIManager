package com.tecknobit.apimanager.Manager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.HashMap;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.apache.commons.codec.binary.Hex.encodeHexString;

/**
 * The {@code APIRequest} class is useful to send and manage all json API requests
 * @author Tecknobit N7ghtm4r3
 * **/

public class APIRequest {

    /**
     * {@code GET_METHOD} is the instance that contains GET method for HTTP requests
     * **/
    public static final String GET_METHOD = "GET";

    /**
     * {@code POST_METHOD} is the instance that contains POST method for HTTP requests
     * **/
    public static final String POST_METHOD = "POST";

    /**
     * {@code DELETE_METHOD} is the instance that contains DELETE method for HTTP requests
     * **/
    public static final String DELETE_METHOD = "DELETE";

    /**
     * {@code PUT_METHOD} is the instance that contains PUT method for HTTP requests
     * **/
    public static final String PUT_METHOD = "PUT";

    /**
     * {@code httpURLConnection} is the instance for all requests
     * **/
    private HttpURLConnection httpURLConnection;

    /**
     * {@code defaultErrorResponse} is the instance that contains default error message
     * **/
    private String defaultErrorResponse;

    /**
     * {@code errorResponse} is the instance that contains error message from request
     * **/
    private String errorResponse;

    /**
     * {@code response} is the instance that contains response message from request
     * **/
    private String response;

    /**
     * {@code requestTimeout} is the instance that contains time to keep alive request
     * **/
    private int requestTimeout;

    /** Constructor to init APIRequest manager
     * @param defaultErrorResponse error message to return if is not request error
     * @param requestTimeout timeout for the requests 
     * **/
    public APIRequest(String defaultErrorResponse, int requestTimeout) {
        this.defaultErrorResponse = defaultErrorResponse;
        this.requestTimeout = requestTimeout;
    }

    /** Constructor to init APIRequest manager
     * @param defaultErrorResponse error message to return if is not request error
     * **/
    public APIRequest(String defaultErrorResponse) {
        this.defaultErrorResponse = defaultErrorResponse;
    }

    /** Constructor to init APIRequest manager
     * @param requestTimeout timeout for the requests
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
     * @param requestTimeout: timeout for the requests
     * **/
    public void setRequestTimeout(int requestTimeout) {
        this.requestTimeout = requestTimeout;
    }

    /** Method to set programmatically default error message to return if is not request error
     * @param defaultErrorResponse: error message to return if is not request error
     * **/
    public void setDefaultErrorResponse(String defaultErrorResponse) {
        this.defaultErrorResponse = defaultErrorResponse;
    }

    /** Method to make api request
     * @param requestUrl: url used to make api request
     * @param method: method used to make api request
     * **/
    public void sendAPIRequest(String requestUrl, String method) throws IOException {
        setRequestConnection(requestUrl, method);
        sendRequest();
    }

    /** Method to make api request with one mandatory header es. api key and its key header value
     * @param requestUrl: url used to make api request
     * @param method: method used to make api request
     * @param headerKey: mandatory header key for the request
     * @param headerValue: mandatory header value for the request
     * **/
    public void sendAPIRequest(String requestUrl, String method, String headerKey, String headerValue) throws IOException {
        setRequestConnection(requestUrl, method);
        httpURLConnection.setRequestProperty(headerKey, headerValue);
        sendRequest();
    }

    /** Method to make api request with many mandatory headers es. api key and its key header value
     * @param requestUrl: url used to make api request
     * @param method: method used to make api request
     * @param headers: mandatory headers key and headers value for the request
     * @deprecated in next update {@link HashMap} will be {@link Headers} custom object
     * **/
    public void sendAPIRequest(String requestUrl, String method, HashMap<String, String> headers) throws IOException {
        setRequestConnection(requestUrl, method);
        for (String key : headers.keySet())
            httpURLConnection.setRequestProperty(key, headers.get(key));
        sendRequest();
    }

    /** Method to make api request
     * @param requestUrl: url used to make api request
     * @param params: query params of the request
     * @param method: method used to make api request
     * **/
    public void sendAPIRequest(String requestUrl, Params params, String method) throws IOException {
        setRequestConnection(requestUrl + assembleQueryParams(params), method);
        sendRequest();
    }

    /** Method to make api request with one mandatory header es. api key and its key header value
     * @param requestUrl: url used to make api request
     * @param method: method used to make api request
     * @param params: query params of the request
     * @param headerKey: mandatory header key for the request
     * @param headerValue: mandatory header value for the request
     * **/
    public void sendAPIRequest(String requestUrl, String method, Params params, String headerKey,
                               String headerValue) throws IOException {
        setRequestConnection(requestUrl, method);
        httpURLConnection.setRequestProperty(headerKey, headerValue);
        sendRequest();
    }

    /** Method to make api request with many mandatory headers es. api key and its key header value
     * @param requestUrl: url used to make api request
     * @param method: method used to make api request
     * @param params: query params of the request
     * @param headers: mandatory headers key and headers value for the request
     * @deprecated in next update {@link HashMap} will be {@link Headers} custom object
     * **/
    public void sendAPIRequest(String requestUrl, String method, Params params, HashMap<String, String> headers) throws IOException {
        setRequestConnection(requestUrl, method);
        for (String key : headers.keySet())
            httpURLConnection.setRequestProperty(key, headers.get(key));
        sendRequest();
    }

    /** Method to make api request with body params
     * @param requestUrl: url used to make api request
     * @param method: method used to make api request
     * @param bodyParams: params to insert in the http body request
     * @deprecated in next update {@link HashMap} will be {@link Params} custom object
     * **/
    public void sendBodyAPIRequest(String requestUrl, String method, HashMap<String, Object> bodyParams) throws IOException {
        setRequestConnection(requestUrl, method);
        setBody(requestUrl, method, bodyParams);
        sendRequest();
    }

    /** Method to make api request with body params and with one mandatory header es. api key and its key header value
     * @param requestUrl: url used to make api request
     * @param method: method used to make api request
     * @param headerKey: mandatory header key for the request
     * @param headerValue: mandatory header value for the request
     * @param bodyParams: params to insert in the http body request
     * @deprecated in next update {@link HashMap} will be {@link Params} custom object
     * any return
     * **/
    public void sendBodyAPIRequest(String requestUrl, String method, String headerKey, String headerValue,
                                   HashMap<String, Object> bodyParams) throws IOException {
        setRequestConnection(requestUrl, method);
        httpURLConnection.setRequestProperty(headerKey, headerValue);
        setBody(requestUrl, method, bodyParams);
        sendRequest();
    }

    /** Method to make api request with body params and with many mandatory headers es. api key and its key header value
     * @param requestUrl: url used to make api request
     * @param method: method used to make api request
     * @param headers: mandatory headers key and headers value for the request
     * @param bodyParams: params to insert in the http body request
     * @deprecated in next update {@link HashMap} will be {@link Headers} and {@link Params} custom objects
     * **/
    public void sendBodyAPIRequest(String requestUrl, String method, HashMap<String,String> headers,
                                   HashMap<String, Object> bodyParams) throws IOException {
        setRequestConnection(requestUrl, method);
        for (String key : headers.keySet())
            httpURLConnection.setRequestProperty(key, headers.get(key));
        setBody(requestUrl, method, bodyParams);
        sendRequest();
    }

    /** Method to set up an HTTP requests with body
     * @param requestUrl: url used to make api request
     * @param method: method used to make api request
     * @param bodyParams: params to insert in the http body request
     * @deprecated in next update {@link HashMap} will be {@link Params} custom object
     * **/
    private void setBody(String requestUrl, String method, HashMap<String, Object> bodyParams) throws IOException {
        httpURLConnection.setDoOutput(true);
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(httpURLConnection.getOutputStream()));
        bufferedWriter.write(assembleBodyParams(bodyParams));
        bufferedWriter.flush();
        bufferedWriter.close();
    }

    /** Method to set up connection by an endpoint
     * @param requestUrl: url used to make HTTP request
     * @param method: method used to make HTTP request
     * **/
    private void setRequestConnection(String requestUrl, String method) throws IOException {
        httpURLConnection = (HttpURLConnection) new URL(requestUrl).openConnection();
        httpURLConnection.setRequestMethod(method);
        httpURLConnection.setConnectTimeout(requestTimeout);
    }

    /** Method to send an HTTP request and get it response <br>
     * Any params required
     * **/
    private void sendRequest() throws IOException {
        httpURLConnection.connect();
        getRequestResponse();
    }

    /** Method to get response of an HTTP request <br>
     * Any params required
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
        }else{
            response = null;
            getRequestResponse();
        }
    }

    /** Method to get params signature of an HTTP request
     * @param signatureKey: key used to signature request
     * @param data: data to sign
     * @return signature es. c8db66725ae71d6d79447319e617115f4a920f5agcdabcb2838bd6b712b053c4"
     * **/
    public String getSignature(String signatureKey, String data) throws Exception {
        Mac sha256 = Mac.getInstance("HmacSHA256");
        sha256.init(new SecretKeySpec(signatureKey.getBytes(UTF_8), "HmacSHA256"));
        return encodeHexString(sha256.doFinal(data.replace("?","").getBytes(UTF_8)));
    }

    /** Method to get params signature of an HTTP request
     * @param signatureKey: key bytes used to signature request
     * @param data: data to sign
     * @return signature in base64 form es. c8db66725ae71d6d79447319e617115f4a920f5agcdabcb2838bd6b712b053c4=="
     * **/
    public String getBase64Signature(byte[] signatureKey, String data) throws Exception {
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(new SecretKeySpec(signatureKey, "HmacSHA256"));
        return Base64.getEncoder().encodeToString(mac.doFinal(data.getBytes()));
    }

    /** Method to get params signature of an HTTP request
     * @param signatureKey: key used to signature request
     * @param data: data to sign
     * @return signature in base64 form es. c8db66725ae71d6d79447319e617115f4a920f5agcdabcb2838bd6b712b053c4=="
     * **/
    public String getBase64Signature(String signatureKey, String data) throws Exception {
        return getBase64Signature(Base64.getDecoder().decode(signatureKey),data);
    }

    /** Method to assemble a query params of an HTTP request
     * @param queryParams: queryParams of request (?param=mandatory1&param2=mandatory2)
     * @return query params as {@link String} assembled es. ?param=query1&param2=query2
     * @throws IllegalArgumentException when extra params in list is empty or is null
     * @deprecated in next update {@link HashMap} will be {@link Params} custom object
     * **/
    public String assembleQueryParams(HashMap<String, Object> queryParams){
        return assembleAdditionalParams(null, queryParams);
    }

    /** Method to assemble a body params of an HTTP request
     * @param bodyParams: mandatory params of request (?param=mandatory1&param2=mandatory2)
     * @return body params as {@link String} assembled es. param=mandatory1&param2=mandatory2
     * @throws IllegalArgumentException when extra params in list is empty or is null
     * @deprecated in next update {@link HashMap} will be {@link Params} custom object
     * **/
    public String assembleBodyParams(HashMap<String, Object> bodyParams){
        return assembleAdditionalParams(null, bodyParams).replace("?","");
    }

    /** Method to assemble a query string params of an HTTP request
     * @param mandatoryParams: mandatory params of request (?param=mandatory1&param2=mandatory2)
     * @param extraParams: not mandatory params of request that have to be concatenated (&param2=valueParam2&param3=valueParam3)
     * @return params as {@link String} assembled es. ?param=mandatory1&param2=mandatory2&param2=valueParam2&param3=valueParam3
     * @throws IllegalArgumentException when extra params in list is empty or is null
     * @deprecated in next update {@link HashMap} will be {@link Params} custom object
     * **/
    public String assembleAdditionalParams(String mandatoryParams, HashMap<String, Object> extraParams){
        String queryEncoderChar = "&";
        if(mandatoryParams == null || mandatoryParams.isEmpty() || mandatoryParams.equals("?")) {
            mandatoryParams = "";
            queryEncoderChar = "?";
        }
        StringBuilder params = new StringBuilder(mandatoryParams);
        for (String key : extraParams.keySet()) {
            Object param = extraParams.get(key);
            if((key != null && !key.equals("")) && (param != null && !param.equals(""))) {
                params.append(queryEncoderChar).append(key).append("=").append(param);
                if(queryEncoderChar.equals("?"))
                    queryEncoderChar = "&";
            }else
                throw new NullPointerException("Extra params key or value cannot be empty or null");
        }
        return params.toString();
    }

    /** Method to concatenate a series of same key param
     * @param initialChar: start char of concatenation, can be "" or &
     * @param key: key of param to concatenate es. param
     * @param params: values of param to concatenate es. value1, value2 as {@link Object} arrays
     * @return series of params concatenated as {@link String} es. param=value1&param=value2
     * @throws IllegalArgumentException when one of the params inserted does not respect correct range
     * **/
    public String concatenateParamsList(String initialChar, String key, Object[] params){
        return concatenateParamsList(initialChar, key, new ArrayList<>(Arrays.asList(params)));
    }

    /** Method to concatenate a series of same key param
     * @param initialChar: start char of concatenation, can be "" or &
     * @param key: key of param to concatenate es. param
     * @param params: values of param to concatenate es. value1, value2 as {@link ArrayList}
     * @return series of params concatenated as {@link String} es. param=value1&param=value2
     * @throws IllegalArgumentException when one of the params inserted does not respect correct range
     * **/
    public String concatenateParamsList(String initialChar, String key, ArrayList<Object> params){
        if(!initialChar.isEmpty() && !initialChar.equals("&"))
            throw new IllegalArgumentException("Initial char must be \"\" or &");
        if(key == null || key.isEmpty())
            throw new IllegalArgumentException("Key cannot be empty or null");
        StringBuilder paramsConcatenation = new StringBuilder();
        if(params.size() > 0){
            for (Object param : params){
                if(param == null || param.equals(""))
                    throw new IllegalArgumentException("Param value cannot be null or empty");
                else
                    paramsConcatenation.append(initialChar).append(key).append("=").append(param);
                initialChar = "&";
            }
            return paramsConcatenation.toString();
        }
        throw new IllegalArgumentException("Params list must contains some values");
    }

    /** Method to concatenate a list of params
     * @param separator: char to divide items of the list
     * @param params: values of the list to concatenate
     * @return list of params as {@link String} es. value,value2,value3
     * @throws IllegalArgumentException when one of the params inserted does not respect correct range
     * **/
    public String assembleParamsList(String separator, ArrayList<?> params){
        return assembleParamsList(separator, params.toArray());
    }

    /** Method to concatenate a list of params
     * @param separator: char to divide items of the list
     * @param params: values of the list to concatenate
     * @return list of params as {@link String} es. value,value2,value3
     * @throws IllegalArgumentException when one of the params inserted does not respect correct range
     * **/
    public String assembleParamsList(String separator, Object... params){
        if(separator == null || separator.isEmpty())
            throw new IllegalArgumentException("Separator value cannot be null or blank");
        if(params == null)
            throw new IllegalArgumentException("Params instance must contains some value");
        StringBuilder paramsList = new StringBuilder();
        for (Object param : params) {
            if(param == null || param.equals(""))
                throw new IllegalArgumentException("Param value cannot be null or empty");
            paramsList.append(param).append(separator);
        }
        paramsList.replace(paramsList.length() - 1, paramsList.length(), "");
        return paramsList.toString();
    }

    /** Method to concatenate a list of params
     * @param starterSeparator: initial char to divide items of the list
     * @param enderSeparator: final char to divide items of the list
     * @param params: values of the list to concatenate
     * @return list of params as {@link String} es. ,value","value2",value3"
     * @throws IllegalArgumentException when one of the params inserted does not respect correct range
     * **/
    public String assembleParamsList(String starterSeparator, String enderSeparator, ArrayList<?> params){
        return assembleParamsList(starterSeparator, enderSeparator, params.toArray());
    }

    /** Method to concatenate a list of params
     * @param starterSeparator: initial char to divide items of the list
     * @param enderSeparator: final char to divide items of the list
     * @param params: values of the list to concatenate
     * @return list of params as {@link String} es. ,value","value2",value3"
     * @throws IllegalArgumentException when one of the params inserted does not respect correct range
     * **/
    public String assembleParamsList(String starterSeparator, String enderSeparator, Object... params){
        if(starterSeparator == null || starterSeparator.isEmpty())
            throw new IllegalArgumentException("Separator value cannot be null or blank");
        if(params == null)
            throw new IllegalArgumentException("Params instance must contains some value");
        StringBuilder paramsList = new StringBuilder();
        for (Object param : params) {
            if(param == null || param.equals(""))
                throw new IllegalArgumentException("Param value cannot be null or empty");
            paramsList.append(starterSeparator).append(param).append(enderSeparator);
        }
        paramsList.replace(paramsList.length() - 1, paramsList.length(), "");
        return paramsList.toString();
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
    public <T> T getJSONResponse() {
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

    /** Method to print error response of request, already red, without read again {@link HttpURLConnection}'s stream <br>
     * Any params required
     * **/
    public void printErrorResponse(){
        System.out.println(getErrorResponse());
    }

     /** Method to get error response of request, already red, without read again {@link HttpURLConnection}'s stream
     * any params required
     * @return error response of request formatted as {@link JSONObject} or {@link JSONArray} object or defaultErrorResponse
     * as {@link String} if is not a request error
     * **/
    public <T> T getJSONErrorResponse() {
        if(errorResponse == null)
            return (T) defaultErrorResponse;
        return getJSONResponseObject(errorResponse);
    }

    /** Method to get JSON object of response of request, already red, without read again {@link HttpURLConnection}'s stream
     * @param response: success response or errorResponse
     * @return response of request formatted as {@link JSONObject} or {@link JSONArray} object
     * @throws JSONException if is not a JSON sNotationParse response and return response as {@link String}
     * **/
    private <T> T getJSONResponseObject(String response){
        try {
            if(response.startsWith("["))
                return (T) new JSONArray(response);
            return (T) new JSONObject(response);
        }catch (JSONException e){
            return (T) response;
        }
    }

    /** Method to print error response of request, already red, without read again {@link HttpURLConnection}'s stream <br>
     * @implNote response will be printed in JSON format or in a simple {@link String} format
     * Any params required
     * **/
    public <T> void printJSONErrorResponse(){
        T jsonResponse = getJSONResponse();
        if(jsonResponse instanceof JSONObject)
            System.out.println(((JSONObject) jsonResponse).toString(4));
        else if (jsonResponse instanceof JSONArray)
            System.out.println(((JSONArray) jsonResponse).toString(4));
        else
            System.out.println(jsonResponse);
    }

    /** Method to get status response code of request
     * any params required
     * @return response of request as int, if is in error code will be: -1
     * **/
    public int getResponseStatusCode(){
        try {
            return httpURLConnection.getResponseCode();
        } catch (IOException e) {
            return -1;
        }
    }

    /**
     * The {@code Headers} class is useful to assemble headers values for the request
     * **/

    public static class Headers extends HashMap<String, String>{

        /** Method to add a new header value
         * @param keyHeader: key of the header
         * @param valueHeader: value of the header
         * @throws IllegalArgumentException when params inserted do not respect validity range -> null or blank
         * **/
        public void addHeader(String keyHeader, String valueHeader){
            if(keyHeader == null || keyHeader.isEmpty())
                throw new IllegalArgumentException("Key of the header cannot be null or blank");
            if(valueHeader == null || valueHeader.isEmpty())
                throw new IllegalArgumentException("Value of the header cannot be null or blank");
            put(keyHeader, valueHeader);
        }

        /** Method to remove a header
         * @param keyHeader: key of the header to remove
         * **/
        public void removeHeader(String keyHeader){
            remove(keyHeader);
        }

    }

    /**
     * The {@code Headers} class is useful to assemble params values for the request
     * @implNote this class can be used to assemble body payload or query request params
     * **/

    public static class Params extends HashMap<String, Object>{

        /** Method to add a new param value
         * @param keyParam: key of the param
         * @param valueParam: value of the param
         * @throws IllegalArgumentException when params inserted do not respect validity range -> null or blank
         * **/
        public void addParam(String keyParam, Object valueParam){
            if(keyParam == null || keyParam.isEmpty())
                throw new IllegalArgumentException("Key of the param cannot be null or blank");
            if(valueParam == null || valueParam.equals(""))
                throw new IllegalArgumentException("Value of the param cannot be null or blank");
            put(keyParam, valueParam);
        }

        /** Method to remove a param
         * @param keyParam: key of the param to remove
         * **/
        public void removeParam(String keyParam){
            remove(keyParam);
        }

    }

}
