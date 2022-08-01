package com.tecknobit.apimanager.Manager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

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
     * {@code DEFAULT_ERROR_RESPONSE} is constant that contains default error message if user not custom it
     * **/
    public static final String DEFAULT_ERROR_RESPONSE  = "Error is not in api request, check out your code";

    /**
     * {@code DEFAULT_REQUEST_TIMEOUT} is constant that contains default request timeout if user not custom it
     * **/
    public static final int DEFAULT_REQUEST_TIMEOUT  = 10000;

    /**
     * {@code HMAC_SHA256_ALGORITHM} is constant that contains HMAC SHA 256 algorithm type
     * **/
    public static final String HMAC_SHA256_ALGORITHM = "HmacSHA256";

    /**
     * {@code HMAC_SHA512_ALGORITHM} is constant that contains HMAC SHA 512 algorithm type
     * **/
    public static final String HMAC_SHA512_ALGORITHM = "HmacSHA512";

    /**
     * {@code MD5_ALGORITHM} is constant that contains MD5 algorithm type
     * **/
    public static final String MD5_ALGORITHM = "MD5";

    /**
     * {@code SHA1_ALGORITHM} is constant that contains SHA-1 algorithm type
     * **/
    public static final String SHA1_ALGORITHM = "SHA-1";

    /**
     * {@code SHA256_ALGORITHM} is constant that contains SHA-256 algorithm type
     * **/
    public static final String SHA256_ALGORITHM = "SHA-256";

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
        requestTimeout = DEFAULT_REQUEST_TIMEOUT;
        this.defaultErrorResponse = defaultErrorResponse;
    }

    /** Constructor to init APIRequest manager
     * @param requestTimeout timeout for the requests
     * **/
    public APIRequest(int requestTimeout) {
        this.requestTimeout = requestTimeout;
        defaultErrorResponse = DEFAULT_ERROR_RESPONSE;
    }

    /** Constructor to init APIRequest manager
     * requestTimeout and defaultErrorResponse initialized with default values.
     * **/
    public APIRequest() {
        requestTimeout = DEFAULT_REQUEST_TIMEOUT;
        defaultErrorResponse = DEFAULT_ERROR_RESPONSE;
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
     * **/
    public void sendAPIRequest(String requestUrl, String method, Headers headers) throws IOException {
        setRequestConnection(requestUrl, method);
        for (String key : headers.getHeadersKeys())
            httpURLConnection.setRequestProperty(key, headers.getHeader(key));
        sendRequest();
    }

    /** Method to make api request
     * @param requestUrl: url used to make api request
     * @param params: query params of the request
     * @param method: method used to make api request
     * **/
    public void sendAPIRequest(String requestUrl, Params params, String method) throws IOException {
        setRequestConnection(requestUrl + encodeQueryParams(params), method);
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
     * **/
    public void sendAPIRequest(String requestUrl, String method, Params params, Headers headers) throws IOException {
        setRequestConnection(requestUrl, method);
        for (String key : headers.getHeadersKeys())
            httpURLConnection.setRequestProperty(key, headers.getHeader(key));
        sendRequest();
    }

    /** Method to make api request with body params
     * @param requestUrl: url used to make api request
     * @param method: method used to make api request
     * @param bodyParams: params to insert in the http body request
     * **/
    public <T> void sendBodyAPIRequest(String requestUrl, String method, Params bodyParams) throws IOException {
        setRequestConnection(requestUrl, method);
        setBodyPayload(requestUrl, method, bodyParams);
        sendRequest();
    }

    /** Method to make api request with body params and with one mandatory header es. api key and its key header value
     * @param requestUrl: url used to make api request
     * @param method: method used to make api request
     * @param headerKey: mandatory header key for the request
     * @param headerValue: mandatory header value for the request
     * @param bodyParams: params to insert in the http body request
     * **/
    public <T> void sendBodyAPIRequest(String requestUrl, String method, String headerKey, String headerValue,
                                       Params bodyParams) throws IOException {
        setRequestConnection(requestUrl, method);
        httpURLConnection.setRequestProperty(headerKey, headerValue);
        setBodyPayload(requestUrl, method, bodyParams);
        sendRequest();
    }

    /** Method to make api request with body params and with many mandatory headers es. api key and its key header value
     * @param requestUrl: url used to make api request
     * @param method: method used to make api request
     * @param headers: mandatory headers key and headers value for the request
     * @param bodyParams: params to insert in the http body request
     * **/
    public <T> void sendBodyAPIRequest(String requestUrl, String method, Headers headers, Params bodyParams) throws IOException {
        setRequestConnection(requestUrl, method);
        for (String key : headers.getHeadersKeys())
            httpURLConnection.setRequestProperty(key, headers.getHeader(key));
        setBodyPayload(requestUrl, method, bodyParams);
        sendRequest();
    }

    /** Method to set up an HTTP requests with body
     * @param requestUrl: url used to make api request
     * @param method: method used to make api request
     * @param bodyParams: params to insert in the http body request
     * **/
    private <T> void setBodyPayload(String requestUrl, String method, Params bodyParams) throws IOException {
        httpURLConnection.setDoOutput(true);
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(httpURLConnection.getOutputStream()));
        bufferedWriter.write(encodeBodyParams(bodyParams));
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

    /** Method to assemble a query params for an HTTP request
     * @param queryParams: queryParams of request (?param=mandatory1&param2=mandatory2)
     * @return query params as {@link String} assembled es. ?param=query1&param2=query2
     * @throws IllegalArgumentException when extra params in list is empty or is null
     * **/
    public <T> String encodeQueryParams(Params queryParams){
        return encodeAdditionalParams(null, queryParams);
    }

    /** Method to assemble a body params of an HTTP request
     * @param bodyParams: mandatory params of request (?param=mandatory1&param2=mandatory2)
     * @return body params as {@link String} assembled es. param=mandatory1&param2=mandatory2
     * @throws IllegalArgumentException when extra params in list is empty or is null
     * **/
    public <T> String encodeBodyParams(Params bodyParams){
        return encodeAdditionalParams(null, bodyParams).replace("?", "");
    }

    /** Method to assemble a query string params of an HTTP request
     * @param mandatoryParams: mandatory params of request (?param=mandatory1&param2=mandatory2)
     * @param extraParams: not mandatory params of request that have to be concatenated (&param2=valueParam2&param3=valueParam3)
     * @return params as {@link String} assembled es. ?param=mandatory1&param2=mandatory2&param2=valueParam2&param3=valueParam3
     * @throws IllegalArgumentException when extra params in list is empty or is null
     * **/
    public <T> String encodeAdditionalParams(String mandatoryParams, Params extraParams){
        String queryEncoderChar = "&";
        if(mandatoryParams == null || mandatoryParams.isEmpty() || mandatoryParams.equals("?")) {
            mandatoryParams = "";
            queryEncoderChar = "?";
        }
        StringBuilder params = new StringBuilder(mandatoryParams);
        for (String key : extraParams.getParamsKeys()) {
            Object param = extraParams.getParam(key);
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
    @SafeVarargs
    public final <T> String concatenateParamsList(String initialChar, String key, T... params){
        return concatenateParamsList(initialChar, key, new ArrayList<>(Arrays.asList(params)));
    }

    /** Method to concatenate a series of same key param
     * @param initialChar: start char of concatenation, can be "" or &
     * @param key: key of param to concatenate es. param
     * @param params: values of param to concatenate es. value1, value2 as {@link ArrayList}
     * @return series of params concatenated as {@link String} es. param=value1&param=value2
     * @throws IllegalArgumentException when one of the params inserted does not respect correct range
     * **/
    public <T> String concatenateParamsList(String initialChar, String key, ArrayList<T> params){
        if(!initialChar.isEmpty() && !initialChar.equals("&"))
            throw new IllegalArgumentException("Initial char must be \"\" or &");
        if(key == null || key.isEmpty())
            throw new IllegalArgumentException("Key cannot be empty or null");
        StringBuilder paramsConcatenation = new StringBuilder();
        if(params.size() > 0){
            for (T param : params){
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
    public <T> String assembleParamsList(String separator, ArrayList<T> params){
        return assembleParamsList(separator, params.toArray());
    }

    /** Method to concatenate a list of params
     * @param separator: char to divide items of the list
     * @param params: values of the list to concatenate
     * @return list of params as {@link String} es. value,value2,value3
     * @throws IllegalArgumentException when one of the params inserted does not respect correct range
     * **/
    @SafeVarargs
    public final <T> String assembleParamsList(String separator, T... params){
        if(separator == null || separator.isEmpty())
            throw new IllegalArgumentException("Separator value cannot be null or blank");
        if(params == null)
            throw new IllegalArgumentException("Params instance must contains some value");
        StringBuilder paramsList = new StringBuilder();
        for (T param : params) {
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
    public <T> String assembleParamsList(String starterSeparator, String enderSeparator, ArrayList<T> params){
        return assembleParamsList(starterSeparator, enderSeparator, params.toArray());
    }

    /** Method to concatenate a list of params
     * @param starterSeparator: initial char to divide items of the list
     * @param enderSeparator: final char to divide items of the list
     * @param params: values of the list to concatenate
     * @return list of params as {@link String} es. ,value","value2",value3"
     * @throws IllegalArgumentException when one of the params inserted does not respect correct range
     * **/
    @SafeVarargs
    public final <T> String assembleParamsList(String starterSeparator, String enderSeparator, T... params){
        if(starterSeparator == null || starterSeparator.isEmpty())
            throw new IllegalArgumentException("Separator value cannot be null or blank");
        if(params == null)
            throw new IllegalArgumentException("Params instance must contains some value");
        StringBuilder paramsList = new StringBuilder();
        for (T param : params) {
            if(param == null || param.equals(""))
                throw new IllegalArgumentException("Param value cannot be null or empty");
            paramsList.append(starterSeparator).append(param).append(enderSeparator);
        }
        paramsList.replace(paramsList.length() - 1, paramsList.length(), "");
        return paramsList.toString();
    }

    /** Method to get response of request, already red, without read again {@link HttpURLConnection}'s stream <br>
     * Any params required
     * @return response of request as {@link String}
     * **/
    public String getResponse() {
        return response;
    }

    /** Method to get response of request, already red, without read again {@link HttpURLConnection}'s stream <br>
     * Any params required
     * @return response of request formatted as {@link JSONObject} or {@link JSONArray} object
     * **/
    public <T> T getJSONResponse() {
        return getJSONResponseObject(response);
    }

    /** Method to get error response of request, already red, without read again {@link HttpURLConnection}'s stream <br>
     * Any params required
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

     /** Method to get error response of request, already red, without read again {@link HttpURLConnection}'s stream <br>
     * Any params required
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
     * Any params required
     * @implNote response will be printed in JSON format or in a simple {@link String} format
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

    /** Method to get status response code of request <br>
     * Any params required
     * @return response of request as int, if is in error code will be: -1
     * **/
    public int getResponseStatusCode(){
        try {
            return httpURLConnection.getResponseCode();
        } catch (IOException e) {
            return -1;
        }
    }

    /** Method to get params signature for an HTTP request
     * @param signatureKey: key used to signature request
     * @param data: data to sign
     * @param algorithm: algorithm used in signature -> HmacSHA256 or HmacSHA512
     * @return signature es. c8db66725ae71d6d79447319e617115f4a920f5agcdabcb2838bd6b712b053c4"
     * **/
    public static String getSignature(String signatureKey, String data, String algorithm) throws Exception {
        if(algorithm == null || (!algorithm.equals(HMAC_SHA256_ALGORITHM) && !algorithm.equals(HMAC_SHA512_ALGORITHM)))
            throw new IllegalArgumentException("Algorithm must be HmacSHA256 or HmacSHA512");
        Mac sha = Mac.getInstance(algorithm);
        sha.init(new SecretKeySpec(signatureKey.getBytes(UTF_8), algorithm));
        return encodeHexString(sha.doFinal(data.replace("?", "").getBytes(UTF_8)));
    }

    /** Method to get params signature for an HTTP request
     * @param signatureKey: key bytes used to signature request
     * @param data: data to sign
     * @param algorithm: algorithm used in signature -> HmacSHA256 or HmacSHA512
     * @return signature in base64 form es. c8db66725ae71d6d79447319e617115f4a920f5agcdabcb2838bd6b712b053c4=="
     * **/
    public static String getBase64Signature(byte[] signatureKey, String data, String algorithm) throws Exception {
        if(algorithm == null || (!algorithm.equals(HMAC_SHA256_ALGORITHM) && !algorithm.equals(HMAC_SHA512_ALGORITHM)))
            throw new IllegalArgumentException("Algorithm must be HmacSHA256 or HmacSHA512");
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(new SecretKeySpec(signatureKey, "HmacSHA256"));
        return Base64.getEncoder().encodeToString(mac.doFinal(data.getBytes()));
    }

    /** Method to get params signature for an HTTP request
     * @param signatureKey: key used to signature request
     * @param data: data to sign
     * @param algorithm: algorithm used in signature -> HmacSHA256 or HmacSHA512
     * @return signature in base64 form es. c8db66725ae71d6d79447319e617115f4a920f5agcdabcb2838bd6b712b053c4=="
     * **/
    public static String getBase64Signature(String signatureKey, String data, String algorithm) throws Exception {
        return getBase64Signature(Base64.getDecoder().decode(signatureKey), data, algorithm);
    }

    /** Method to get digest
     * @param data: data to digest as {@link String}
     * @param algorithm: algorithm used in signature -> MD5,SHA-1 or SHA-256
     * @return digest result as byte array
     * **/
    public static byte[] digest(String data, String algorithm) throws NoSuchAlgorithmException {
        return digest(data.getBytes(), algorithm);
    }

    /** Method to get digest
     * @param data: data to digest as {@link String}
     * @param algorithm: algorithm used in signature -> MD5,SHA-1 or SHA-256
     * @return digest result as {@link String} in {@link Base64} encode
     * **/
    public static String base64Digest(String data, String algorithm) throws NoSuchAlgorithmException {
        return base64Digest(data.getBytes(), algorithm);
    }

    /** Method to get digest
     * @param data: data to digest as byte array
     * @param algorithm: algorithm used in signature -> MD5,SHA-1 or SHA-256
     * @return digest result as {@link String} in {@link Base64} encode
     * **/
    public static String base64Digest(byte[] data, String algorithm) throws NoSuchAlgorithmException {
        return new String(Base64.getEncoder().encode(digest(data, algorithm)));
    }

    /** Method to get digest
     * @param data: data to digest as byte array
     * @param algorithm: algorithm used in signature -> MD5,SHA-1 or SHA-256
     * @return digest result as byte array
     * **/
    public static byte[] digest(byte[] data, String algorithm) throws NoSuchAlgorithmException {
        if(algorithm == null || (!algorithm.equals(MD5_ALGORITHM) && !algorithm.equals(SHA1_ALGORITHM)
                && !algorithm.equals(SHA256_ALGORITHM))) {
            throw new IllegalArgumentException("Algorithm must be MD5,SHA-1 or SHA-256");
        }
        MessageDigest messageDigest = MessageDigest.getInstance(algorithm);
        messageDigest.update(data);
        return messageDigest.digest();
    }

    /**
     * The {@code Headers} class is useful to assemble headers values for the request
     * **/

    public static class Headers {

        /**
         * {@code headers} is the instance that contains headers value
         * **/
        private final HashMap<String, String> headers;

        /** Constructor to init {@link Headers} <br>
         * Any params required
         * **/
        public Headers() {
            headers = new HashMap<>();
        }

        /** Method to add a new header value
         * @param headerKey: key of the header
         * @param valueHeader: value of the header
         * @throws IllegalArgumentException when params inserted do not respect validity range -> null or blank
         * **/
        public void addHeader(String headerKey, String valueHeader) {
            if(headerKey == null || headerKey.isEmpty())
                throw new IllegalArgumentException("Key of the header cannot be null or blank");
            if(valueHeader == null || valueHeader.isEmpty())
                throw new IllegalArgumentException("Value of the header cannot be null or blank");
            headers.put(headerKey, valueHeader);
        }

        /** Method to remove a header
         * @param headerKey: key of the header to remove
         * **/
        public void removeHeader(String headerKey) {
            headers.remove(headerKey);
        }

        /** Method to get a header value
         * @param headerKey: key of the header to get
         * @return header value as {@link String}
         * **/
        public String getHeader(String headerKey) {
            return headers.get(headerKey);
        }

        /** Method to get all headers value<br>
         * Any params required
         * @return all headers value as {@link Collection} of {@link String}
         * **/
        public Collection<String> getAllHeaders() {
            return headers.values();
        }

        /** Method to get all headers keys<br>
         * Any params required
         * @return all headers keys as {@link Set} of {@link String}
         * **/
        public Set<String> getHeadersKeys(){
            return headers.keySet();
        }

        @Override
        public String toString() {
            return headers.toString();
        }

    }

    /**
     * The {@code Params} class is useful to assemble params values for the request
     * @implNote this class can be used to assemble body payload or query request params
     * **/

    public static class Params {

        /**
         * {@code params} is the instance that contains params value
         * **/
        private final HashMap<String, Object> params;

        /** Constructor to init {@link Params} <br>
         * Any params required
         * **/
        public Params() {
            params = new HashMap<>();
        }

        /** Method to add a new param value
         * @param keyParam: key of the param
         * @param valueParam: value of the param
         * @throws IllegalArgumentException when params inserted do not respect validity range -> null or blank
         * **/
        public <T> void addParam(String keyParam, T valueParam){
            if(keyParam == null || keyParam.isEmpty())
                throw new IllegalArgumentException("Key of the param cannot be null or blank");
            if(valueParam == null || valueParam.equals(""))
                throw new IllegalArgumentException("Value of the param cannot be null or blank");
            params.put(keyParam, valueParam);
        }

        /** Method to remove a param
         * @param keyParam: key of the param to remove
         * **/
        public void removeParam(String keyParam){
            params.remove(keyParam);
        }

        /** Method to get a param value
         * @param paramKey: key of the param to get
         * @return params value as {@link String}
         * **/
        public <T> T getParam(String paramKey) {
            return (T) params.get(paramKey);
        }

        /** Method to get all params value<br>
         * Any params required
         * @return all params value as {@link Collection} of {@link T}
         * **/
        public <T> Collection<T> getAllParams() {
            return (Collection<T>) params.values();
        }

        /** Method to get all params keys<br>
         * Any params required
         * @return all params keys as {@link Set} of {@link String}
         * **/
        public Set<String> getParamsKeys(){
            return params.keySet();
        }

        @Override
        public String toString() {
            return params.toString();
        }

    }

}
