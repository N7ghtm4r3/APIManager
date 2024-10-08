package com.tecknobit.apimanager.apis;

import com.tecknobit.apimanager.annotations.Wrapper;
import okhttp3.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.*;

import static com.tecknobit.apimanager.apis.APIRequest.RequestMethod.PATCH;
import static java.lang.Long.MAX_VALUE;
import static java.nio.charset.StandardCharsets.UTF_8;
import static java.util.Base64.getDecoder;
import static java.util.Base64.getEncoder;
import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static org.apache.commons.codec.binary.Hex.encodeHexString;

/**
 * The {@code APIRequest} class is useful to send and manage any {@code "API"} requests and their responses
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the usage at <a href="https://github.com/N7ghtm4r3/APIManager/blob/main/documd/APIRequest.md">APIRequest.md</a>
 * @since 1.0.0
 */
public class APIRequest {

    /**
     * {@code DEFAULT_ERROR_RESPONSE} is constant that contains default error message if user not custom it
     */
    public static final String DEFAULT_ERROR_RESPONSE = "Error is not in api request, check out your code";

    /**
     * {@code DEFAULT_REQUEST_TIMEOUT} is constant that contains default request timeout if user not custom it
     */
    public static final int DEFAULT_REQUEST_TIMEOUT = 10000;

    /**
     * {@code HMAC_SHA256_ALGORITHM} is constant that contains {@code "HMAC SHA 256"} algorithm type
     */
    public static final String HMAC_SHA256_ALGORITHM = "HmacSHA256";

    /**
     * {@code HMAC_SHA512_ALGORITHM} is constant that contains {@code "HMAC SHA 512"} algorithm type
     */
    public static final String HMAC_SHA512_ALGORITHM = "HmacSHA512";

    /**
     * {@code MD5_ALGORITHM} is constant that contains {@code "MD5"} algorithm type
     * */
    public static final String MD5_ALGORITHM = "MD5";

    /**
     * {@code SHA1_ALGORITHM} is constant that contains {@code "SHA-1"} algorithm type
     * */
    public static final String SHA1_ALGORITHM = "SHA-1";

    /**
     * {@code SHA256_ALGORITHM} is constant that contains {@code "SHA-256"} algorithm type
     */
    public static final String SHA256_ALGORITHM = "SHA-256";

    /**
     * **okHttpClient** -> the http client to execute the requests
     */
    private OkHttpClient okHttpClient;

    /**
     * {@code request} is the instance useful for the requests
     */
    private Request.Builder request;

    /**
     * {@code contentType} is the type of the content of the payload request, default is {@code "text/plain"}
     */
    private MediaType contentType = MediaType.parse("text/plain");

    /**
     * {@code defaultErrorResponse} is the instance that contains default error message
     */
    private String defaultErrorResponse;

    /**
     * {@code enableCertificatesValidation} whether enable the <b>SSL</b> certificates validation, this for example
     * when the certificate is a self-signed certificate to by-pass
     */
    private boolean enableCertificatesValidation;

    /**
     * {@code isSuccessfulRequest} whether the request has been successful
     */
    private boolean isSuccessfulRequest;

    /**
     * {@code response} is the instance that contains response message from request to return
     */
    private String response;

    /**
     * {@code errorResponse} is the instance that contains error message from request
     */
    private String errorResponse;

    /**
     * {@code statusCode} status code of a request
     */
    private int statusCode;

    /**
     * Constructor to init {@link APIRequest}
     *
     * @param enableCertificatesValidation: whether enable the <b>SSL</b> certificates validation, this for example
     *                                      when the certificate is a self-signed certificate to by-pass
     */
    public APIRequest(boolean enableCertificatesValidation) {
        this(DEFAULT_ERROR_RESPONSE, DEFAULT_REQUEST_TIMEOUT, enableCertificatesValidation);
    }

    /**
     * Constructor to init {@link APIRequest}
     *
     * @param defaultErrorResponse error message to return if is not request error
     */
    public APIRequest(String defaultErrorResponse) {
        this(defaultErrorResponse, DEFAULT_REQUEST_TIMEOUT);
    }

    /**
     * Constructor to init {@link APIRequest}
     *
     * @param defaultErrorResponse          error message to return if is not request error
     * @param enableCertificatesValidation: whether enable the <b>SSL</b> certificates validation, this for example
     *                                      when the certificate is a self-signed certificate to by-pass
     */
    public APIRequest(String defaultErrorResponse, boolean enableCertificatesValidation) {
        this(defaultErrorResponse, DEFAULT_REQUEST_TIMEOUT, enableCertificatesValidation);
    }

    /**
     * Constructor to init {@link APIRequest}
     *
     * @param requestTimeout timeout for the requests
     */
    public APIRequest(long requestTimeout) {
        this(DEFAULT_ERROR_RESPONSE, requestTimeout);
    }

    /**
     * Constructor to init {@link APIRequest}
     *
     * @param requestTimeout                timeout for the requests
     * @param enableCertificatesValidation: whether enable the <b>SSL</b> certificates validation, this for example
     *                                      when the certificate is a self-signed certificate to by-pass
     */
    public APIRequest(long requestTimeout, boolean enableCertificatesValidation) {
        this(DEFAULT_ERROR_RESPONSE, requestTimeout, enableCertificatesValidation);
    }

    /**
     * Constructor to init {@link APIRequest}
     *
     * @param defaultErrorResponse error message to return if is not request error
     * @param requestTimeout       timeout for the requests
     */
    public APIRequest(String defaultErrorResponse, long requestTimeout) {
        this.defaultErrorResponse = defaultErrorResponse;
        okHttpClient = new OkHttpClient.Builder().connectTimeout(requestTimeout, MILLISECONDS).build();
    }

    /**
     * Constructor to init {@link APIRequest}
     *
     * @param defaultErrorResponse          error message to return if is not request error
     * @param requestTimeout                timeout for the requests
     * @param enableCertificatesValidation: whether enable the <b>SSL</b> certificates validation, this for example
     *                                      when the certificate is a self-signed certificate to by-pass
     */
    public APIRequest(String defaultErrorResponse, long requestTimeout, boolean enableCertificatesValidation) {
        this.defaultErrorResponse = defaultErrorResponse;
        okHttpClient = new OkHttpClient.Builder().connectTimeout(requestTimeout, MILLISECONDS).build();
        this.enableCertificatesValidation = enableCertificatesValidation;
    }

    /**
     * Constructor to init {@link APIRequest} <br>
     * <p>
     * No-any params required
     *
     * @apiNote the request details will be instantiated with the default values:
     * <ul>
     *     <li>
     *         request timeout  = {@link #DEFAULT_REQUEST_TIMEOUT}
     *     </li>
     *     <li>
     *         {@link #defaultErrorResponse} = {@link #DEFAULT_ERROR_RESPONSE}
     *     </li>
     *     <li>
     *         {@link #enableCertificatesValidation} = false
     *     </li>
     * </ul>
     */
    public APIRequest() {
        defaultErrorResponse = DEFAULT_ERROR_RESPONSE;
        okHttpClient = new OkHttpClient();
        enableCertificatesValidation = false;
    }

    /**
     * Method to set the content type of the payload of the request
     *
     * @param contentType: the content type to set
     */
    public void setContentType(String contentType) {
        this.contentType = MediaType.parse(contentType);
    }

    /**
     * Method to validate a self-signed SLL certificate and bypass the checks of its validity<br>
     * No-any params required
     *
     * @apiNote this method disable all checks on the SLL certificate validity, so is recommended to use for test only or
     * in a private distribution on own infrastructure
     */
    public void validateSelfSignedCertificate() {
        TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
            public X509Certificate[] getAcceptedIssuers() {
                return new X509Certificate[]{};
            }

            public void checkClientTrusted(X509Certificate[] certs, String authType) {
            }

            public void checkServerTrusted(X509Certificate[] certs, String authType) {
            }

        }};
        try {
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, trustAllCerts, new SecureRandom());
            OkHttpClient.Builder builder = okHttpClient.newBuilder();
            builder.sslSocketFactory(sslContext.getSocketFactory(), (X509TrustManager) trustAllCerts[0]);
            builder.hostnameVerifier(((hostname, session) -> true));
            okHttpClient = builder.build();
        } catch (Exception ignored) {
        }
    }

    /**
     * Method to send an api request with a single header
     *
     * @param requestUrl:  {@code "URL"} used in the api request
     * @param method:      method used in the api request
     * @param headerKey:   header key for the request
     * @param headerValue: header value for the request
     */
    public <T> void sendAPIRequest(String requestUrl, RequestMethod method, String headerKey,
                                   T headerValue) throws IOException {
        setRequest(requestUrl, method);
        addHeader(headerKey, headerValue);
        performRequest();
    }

    /**
     * Method to send an api request with different headers
     *
     * @param requestUrl: {@code "URL"} used in the api request
     * @param method:     method used in the api request
     * @param headers:    headers for the request
     */
    public void sendAPIRequest(String requestUrl, RequestMethod method, Headers headers) throws IOException {
        setRequest(requestUrl, method);
        setHeaders(headers);
        performRequest();
    }

    /**
     * Method to send an api request
     *
     * @param requestUrl:  {@code "URL"} used in the api request
     * @param method:      method used in the api request
     * @param queryParams: query queryParams of the request
     */
    public void sendAPIRequest(String requestUrl, RequestMethod method, Params queryParams) throws IOException {
        setRequest(requestUrl + queryParams.createQueryString(), method);
        performRequest();
    }

    /**
     * Method to send an api request with a single header
     *
     * @param requestUrl:  {@code "URL"} used in the api request
     * @param method:      method used in the api request
     * @param headerKey:   header key for the request
     * @param headerValue: header value for the request
     * @param queryParams: query params of the request
     */
    public <T> void sendAPIRequest(String requestUrl, RequestMethod method, String headerKey, T headerValue,
                                   Params queryParams) throws IOException {
        setRequest(requestUrl + queryParams.createQueryString(), method);
        addHeader(headerKey, headerValue);
        performRequest();
    }

    /**
     * Method to send an api request with different headers
     *
     * @param requestUrl:  {@code "URL"} used in the api request
     * @param method:      method used in the api request
     * @param headers:     headers for the request
     * @param queryParams: query params of the request
     */
    public void sendAPIRequest(String requestUrl, RequestMethod method, Headers headers,
                               Params queryParams) throws IOException {
        setRequest(requestUrl + queryParams.createQueryString(), method);
        setHeaders(headers);
        performRequest();
    }

    /**
     * Method to send an api request with a payload
     *
     * @param requestUrl: {@code "URL"} used in the api request
     * @param method:     method used in the api request
     * @param payload:    params to insert in the payload for the {@code "HTTP"} request
     */
    public void sendPayloadedAPIRequest(String requestUrl, RequestMethod method, Params payload) throws IOException {
        setRequest(requestUrl, method, payload, false);
        performRequest();
    }

    /**
     * Method to send an api request with a payload and with a single header
     *
     * @param requestUrl:  {@code "URL"} used in the api request
     * @param method:      method used in the api request
     * @param headerKey:   header key for the request
     * @param headerValue: header value for the request
     * @param payload:     params to insert in the payload for the {@code "HTTP"} request
     */
    public <T> void sendPayloadedAPIRequest(String requestUrl, RequestMethod method, String headerKey, T headerValue,
                                            Params payload) throws IOException {
        setRequest(requestUrl, method, payload, false);
        addHeader(headerKey, headerValue);
        performRequest();
    }

    /**
     * Method to send an api request with a payload and with different headers
     *
     * @param requestUrl: {@code "URL"} used in the api request
     * @param method:     method used in the api request
     * @param headers:    headers for the request
     * @param payload:    params to insert in the payload for the {@code "HTTP"} request
     */
    public void sendPayloadedAPIRequest(String requestUrl, RequestMethod method, Headers headers,
                                        Params payload) throws IOException {
        setRequest(requestUrl, method, payload, false);
        setHeaders(headers);
        performRequest();
    }

    /**
     * Method to send an api request with a payload
     *
     * @param requestUrl: {@code "URL"} used in the api request
     * @param method:     method used in the api request
     * @param payload:    the multipart payload of the request
     */
    public void sendPayloadedAPIRequest(String requestUrl, RequestMethod method, MultipartBody payload) throws IOException {
        setRequest(requestUrl, method, payload);
        performRequest();
    }

    /**
     * Method to send an api request with a payload and with a single header
     *
     * @param requestUrl:  {@code "URL"} used in the api request
     * @param method:      method used in the api request
     * @param headerKey:   header key for the request
     * @param headerValue: header value for the request
     * @param payload:     the multipart payload of the request
     */
    public <T> void sendPayloadedAPIRequest(String requestUrl, RequestMethod method, String headerKey, T headerValue,
                                            MultipartBody payload) throws IOException {
        setRequest(requestUrl, method, payload);
        addHeader(headerKey, headerValue);
        performRequest();
    }

    /**
     * Method to send an api request with a payload and with different headers
     *
     * @param requestUrl: {@code "URL"} used in the api request
     * @param method:     method used in the api request
     * @param headers:    headers for the request
     * @param payload:    the multipart payload of the request
     */
    public void sendPayloadedAPIRequest(String requestUrl, RequestMethod method, Headers headers,
                                        MultipartBody payload) throws IOException {
        setRequest(requestUrl, method, payload);
        setHeaders(headers);
        performRequest();
    }

    /**
     * Method to send an api request with a payload formatted in {@code "JSON"}
     *
     * @param requestUrl: {@code "URL"} used in the api request
     * @param method:     method used in the api request
     * @param payload:    params to insert in the payload for the {@code "HTTP"} request
     */
    public void sendJSONPayloadedAPIRequest(String requestUrl, RequestMethod method, Params payload) throws IOException {
        setRequest(requestUrl, method, payload, true);
        performRequest();
    }

    /**
     * Method to send an api request with a payload formatted in {@code "JSON"} and with a single header
     *
     * @param requestUrl:  {@code "URL"} used in the api request
     * @param method:      method used in the api request
     * @param headerKey:   header key for the request
     * @param headerValue: header value for the request
     * @param payload:     params to insert in the payload for the {@code "HTTP"} request
     */
    public <T> void sendJSONPayloadedAPIRequest(String requestUrl, RequestMethod method, String headerKey, T headerValue,
                                                Params payload) throws IOException {
        setRequest(requestUrl, method, payload, true);
        addHeader(headerKey, headerValue);
        performRequest();
    }

    /**
     * Method to send an api request with a payload formatted in {@code "JSON"} and with different headers
     *
     * @param requestUrl: {@code "URL"} used in the api request
     * @param method:     method used in the api request
     * @param headers:    headers for the request
     * @param payload:    params to insert in the payload for the {@code "HTTP"} request
     */
    public void sendJSONPayloadedAPIRequest(String requestUrl, RequestMethod method, Headers headers,
                                            Params payload) throws IOException {
        setRequest(requestUrl, method, payload, true);
        setHeaders(headers);
        performRequest();
    }

    /**
     * Method to set up the request details
     *
     * @param requestUrl: {@code "URL"} used to make {@code "HTTP"} request
     * @param method:     method used to make {@code "HTTP"} request
     */
    @Wrapper
    private void setRequest(String requestUrl, RequestMethod method) {
        setRequest(requestUrl, method, null, false);
    }

    /**
     * Method to set up the request details
     *
     * @param requestUrl:    {@code "URL"} used to make {@code "HTTP"} request
     * @param method:        method used to make {@code "HTTP"} request
     * @param payload:       params to insert in the payload for the {@code "HTTP"} request
     * @param isJsonPayload: flag whether payload is to send formatted in {@code "JSON"} or not
     */
    private void setRequest(String requestUrl, RequestMethod method, Params payload, boolean isJsonPayload) {
        RequestBody requestBody = null;
        if (payload != null) {
            String cPayload;
            if (isJsonPayload) {
                setContentType("application/json");
                cPayload = payload.createJSONPayload().toString();
            } else
                cPayload = payload.createPayload();
            requestBody = RequestBody.create(contentType, cPayload);
        } else if (payload == null && method == PATCH)
            requestBody = RequestBody.create(contentType, "");
        request = new Request.Builder()
                .method(method.name(), requestBody)
                .url(requestUrl);
    }

    /**
     * Method to set up the request details
     *
     * @param requestUrl: {@code "URL"} used to make {@code "HTTP"} request
     * @param method:     method used to make {@code "HTTP"} request
     * @param payload:    the multipart payload of the request
     */
    private void setRequest(String requestUrl, RequestMethod method, MultipartBody payload) {
        request = new Request.Builder()
                .method(method.name(), payload)
                .url(requestUrl);
    }

    /**
     * Method to get the response of an {@code "HTTP"} request <br>
     *
     * No-any params required
     */
    private void performRequest() throws IOException {
        errorResponse = null;
        response = null;
        if(enableCertificatesValidation)
            validateSelfSignedCertificate();
        Call call = okHttpClient.newCall(request.build());
        Response rResponse = call.execute();
        String requestResponse = rResponse.body().string();
        isSuccessfulRequest = rResponse.isSuccessful();
        if (isSuccessfulRequest)
            response = requestResponse;
        else
            errorResponse = requestResponse;
        statusCode = rResponse.code();
        okHttpClient.connectionPool().evictAll();
    }

    /**
     * Method to set the headers of an {@code "HTTP"} request <br>
     *
     * @param headerKey:   header key for the request
     * @param headerValue: header value for the request
     */
    private <T> void addHeader(String headerKey, T headerValue) {
        Headers headers = new Headers();
        headers.addHeader(headerKey, headerValue);
        setHeaders(headers);
    }

    /**
     * Method to set the headers of an {@code "HTTP"} request <br>
     *
     * @param headers: headers for the request
     */
    private void setHeaders(Headers headers) {
        request.headers(okhttp3.Headers.of(headers.headers));
    }

    /**
     * Method to parse the response of the request and format it as {@code "JSON"}
     *
     * @param response: response to parse
     * @return the response formatted as {@code "JSON"} or a simple {@link String} if is not possible format as {@code "JSON"}
     */
    private <T> T parseResponseAsJson(String response) {
        try {
            if (response.startsWith("["))
                return (T) new JSONArray(response);
            return (T) new JSONObject(response);
        } catch (JSONException e) {
            return (T) response;
        }
    }

    /**
     * Method to get whether the response has been successful <br>
     * No-any params required
     *
     * @return whether the response has been successful as boolean
     */
    public boolean isSuccessful() {
        return isSuccessfulRequest;
    }

    /**
     * Method to get the response of the request <br>
     * No-any params required
     *
     * @return response of the request as {@link String}
     */
    public String getResponse() {
        return response;
    }

    /**
     * Method to get the response of the request <br>
     * No-any params required
     *
     * @return response of the request formatted as {@code "JSON"} or as simple {@link String} if is not possible format
     * as {@code "JSON"}
     */
    public <T> T getJSONResponse() {
        return parseResponseAsJson(response);
    }

    /**
     * Method to get the error response of the request <br>
     * No-any params required
     *
     * @return error response of the request formatted or {@link #defaultErrorResponse} as {@link String}
     */
    public String getErrorResponse() {
        if (errorResponse == null)
            return defaultErrorResponse;
        return errorResponse;
    }

    /**
     * Method to print the error response of the request <br>
     * No-any params required
     */
    public void printErrorResponse() {
        System.out.println(getErrorResponse());
    }

    /**
     * Method to get the error response of the request <br>
     * No-any params required
     *
     * @return error response of the request formatted as {@code "JSON"} or {@link #defaultErrorResponse} as {@link String}
     * if is not a request error
     */
    public <T> T getJSONErrorResponse() {
        if (errorResponse == null)
            return (T) defaultErrorResponse;
        return parseResponseAsJson(errorResponse);
    }

    /**
     * Method to print the error response of the request <br>
     * <p>
     * No-any params required
     *
     * @apiNote the response will be printed formatted as {@code "JSON"} or a simple {@link String}
     * if is not possible format as {@code "JSON"}
     */
    public <T> void printJSONErrorResponse() {
        T jsonResponse = getJSONResponse();
        if (jsonResponse instanceof JSONObject)
            System.out.println(((JSONObject) jsonResponse).toString(4));
        else if (jsonResponse instanceof JSONArray)
            System.out.println(((JSONArray) jsonResponse).toString(4));
        else
            System.out.println(jsonResponse);
    }

    /**
     * Method to get status response code of the request <br>
     * No-any params required
     *
     * @return response code of the request as int
     */
    public int getResponseStatusCode() {
        return statusCode;
    }

    /**
     * Method to concatenate a series of same key param
     *
     * @param initialChar: start char of concatenation, can be "" or &
     * @param key:         key of param to concatenate e.g. param
     * @param params:      values of param to concatenate e.g. value1, value2 as {@link ArrayList}
     * @return series of params concatenated as {@link String} e.g. param=value1&param=value2
     * @throws IllegalArgumentException when one of the params inserted does not respect correct range
     */
    @Wrapper
    public <T> String concatenateParamsList(String initialChar, String key, ArrayList<T> params) {
        return concatenateParamsList(initialChar, key, params.toArray());
    }

    /**
     * Method to concatenate a series of same key param
     *
     * @param initialChar: start char of concatenation, can be "" or &
     * @param key:         key of param to concatenate e.g. param
     * @param params:      values of param to concatenate e.g. value1, value2 as {@link Object} arrays
     * @return series of params concatenated as {@link String} e.g. param=value1&param=value2
     * @throws IllegalArgumentException when one of the params inserted does not respect correct range
     */
    @SafeVarargs
    public final <T> String concatenateParamsList(String initialChar, String key, T... params) {
        if (!initialChar.isEmpty() && !initialChar.equals("&"))
            throw new IllegalArgumentException("Initial char must be \"\" or &");
        if (key == null || key.isEmpty())
            throw new IllegalArgumentException("Key cannot be empty or null");
        StringBuilder paramsConcatenation = new StringBuilder();
        if (params.length > 0) {
            for (T param : params) {
                if (param == null || param.equals(""))
                    throw new IllegalArgumentException("Param value cannot be null or empty");
                else
                    paramsConcatenation.append(initialChar).append(key).append("=")
                            .append(URLEncoder.encode(param.toString(), UTF_8));
                initialChar = "&";
            }
            return paramsConcatenation.toString();
        }
        throw new IllegalArgumentException("Params list must contains some values");
    }

    /**
     * Method to concatenate a list of params
     *
     * @param separator: char to divide items of the list
     * @param params:    values of the list to concatenate
     * @return list of params as {@link String} e.g. value,value2,value3
     * @throws IllegalArgumentException when one of the params inserted does not respect correct range
     */
    @Wrapper
    public <T> String assembleParamsList(String separator, ArrayList<T> params) {
        return assembleParamsList(separator, params.toArray());
    }

    /**
     * Method to concatenate a list of params
     *
     * @param separator: char to divide items of the list
     * @param params:    values of the list to concatenate
     * @return list of params as {@link String} e.g. value,value2,value3
     * @throws IllegalArgumentException when one of the params inserted does not respect correct range
     */
    @SafeVarargs
    public final <T> String assembleParamsList(String separator, T... params) {
        if (separator == null || separator.isEmpty())
            throw new IllegalArgumentException("Separator value cannot be null or blank");
        if (params == null)
            throw new IllegalArgumentException("Params instance must contains some value");
        StringBuilder paramsList = new StringBuilder();
        for (T param : params) {
            if (param == null || param.equals(""))
                throw new IllegalArgumentException("Param value cannot be null or empty");
            paramsList.append(URLEncoder.encode(param.toString(), UTF_8)).append(separator);
        }
        int listLength = paramsList.length();
        return paramsList.delete(listLength - separator.length(), listLength).toString();
    }

    /**
     * Method to concatenate a list of params
     *
     * @param starterSeparator: initial char to divide items of the list
     * @param enderSeparator:   final char to divide items of the list
     * @param params:           values of the list to concatenate
     * @return list of params as {@link String} e.g. ,"value","value2",value3"
     * @throws IllegalArgumentException when one of the params inserted does not respect correct range
     */
    @Wrapper
    public <T> String assembleSeparatedParamsList(String starterSeparator, String enderSeparator, ArrayList<T> params) {
        return assembleSeparatedParamsList(starterSeparator, enderSeparator, params.toArray());
    }

    /**
     * Method to concatenate a list of params
     *
     * @param starterSeparator: initial char to divide items of the list
     * @param enderSeparator:   final char to divide items of the list
     * @param params:           values of the list to concatenate
     * @return list of params as {@link String} e.g. ,"value","value2",value3"
     * @throws IllegalArgumentException when one of the params inserted does not respect correct range
     */
    @SafeVarargs
    public final <T> String assembleSeparatedParamsList(String starterSeparator, String enderSeparator, T... params) {
        if (starterSeparator == null || starterSeparator.isEmpty())
            throw new IllegalArgumentException("Separator value cannot be null or blank");
        if (params == null)
            throw new IllegalArgumentException("Params instance must contains some value");
        StringBuilder paramsList = new StringBuilder();
        for (T param : params) {
            if (param == null || param.equals(""))
                throw new IllegalArgumentException("Param value cannot be null or empty");
            paramsList.append(starterSeparator).append(URLEncoder.encode(param.toString(), UTF_8)).append(enderSeparator);
        }
        int listLength = paramsList.length();
        if (enderSeparator != null)
            paramsList = paramsList.delete(listLength - enderSeparator.length(), listLength);
        return paramsList.toString();
    }

    /**
     * Method to assemble a query params for an {@code "HTTP"} request
     *
     * @param queryParams: queryParams of request (?param=mandatory1&param2=mandatory2)
     * @return query params as {@link String} assembled e.g. ?param=query1&param2=query2
     * @throws IllegalArgumentException when extra params in list is empty or is null
     */
    @Wrapper
    public <T> String encodeQueryParams(Params queryParams) {
        return encodeAdditionalParams(null, queryParams);
    }

    /**
     * Method to assemble a body params of an {@code "HTTP"} request
     *
     * @param bodyParams: mandatory params of request (?param=mandatory1&param2=mandatory2)
     * @return body params as {@link String} assembled e.g. param=mandatory1&param2=mandatory2
     * @throws IllegalArgumentException when extra params in list is empty or is null
     */
    @Wrapper
    public <T> String encodeBodyParams(Params bodyParams) {
        return encodeAdditionalParams(null, bodyParams).replace("?", "");
    }

    /**
     * Method to assemble a query string params of an {@code "HTTP"} request
     *
     * @param mandatoryParams: mandatory params of request (?param=mandatory1&param2=mandatory2)
     * @param extraParams:     not mandatory params of request that have to be concatenated 
     *                   (&param2=valueParam2&param3=valueParam3)
     * @return params as {@link String} assembled e.g.
     * ?param=mandatory1&param2=mandatory2&param2=valueParam2&param3=valueParam3
     * @throws IllegalArgumentException when extra params in list is empty or is null
     */
    public <T> String encodeAdditionalParams(String mandatoryParams, Params extraParams) {
        String queryEncoderChar = "&";
        if (mandatoryParams == null || mandatoryParams.isEmpty() || mandatoryParams.equals("?")) {
            mandatoryParams = "";
            queryEncoderChar = "?";
        } else {
            if (mandatoryParams.charAt(0) != '?')
                throw new IllegalArgumentException("Mandatory params bust be in right HTTP encode form");
            else {
                String[] encodeChecker = mandatoryParams.replaceFirst("\\?", "").replaceAll("&",
                        "=").split("=");
                if (encodeChecker.length % 2 != 0)
                    throw new IllegalArgumentException("Mandatory params bust be in right HTTP encode form");
            }
        }
        StringBuilder params = new StringBuilder(mandatoryParams);
        for (String key : extraParams.getParamsKeys()) {
            if ((key != null && !key.isEmpty())) {
                params.append(queryEncoderChar).append(key).append("=")
                        .append(URLEncoder.encode(extraParams.getParam(key), UTF_8));
                if (queryEncoderChar.equals("?"))
                    queryEncoderChar = "&";
            } else
                throw new NullPointerException("Extra params key cannot be empty or null");
        }
        return params.toString();
    }

    /**
     * Method to get digest
     *
     * @param data:      data to digest as {@link String}
     * @param algorithm: algorithm used in signature -> MD5,SHA-1 or SHA-256
     * @return digest result as {@link String} in {@link Base64} encode
     */
    public static String base64Digest(String data, String algorithm) throws NoSuchAlgorithmException {
        return base64Digest(data.getBytes(), algorithm);
    }

    /**
     * Method to get digest
     *
     * @param data:      data to digest as byte array
     * @param algorithm: algorithm used in signature -> MD5,SHA-1 or SHA-256
     * @return digest result as {@link String} in {@link Base64} encode
     */
    public static String base64Digest(byte[] data, String algorithm) throws NoSuchAlgorithmException {
        return new String(getEncoder().encode(digest(data, algorithm)));
    }

    /**
     * Method to get digest
     *
     * @param data:      data to digest as {@link String}
     * @param algorithm: algorithm used in signature -> MD5,SHA-1 or SHA-256
     * @return digest result as {@link String}
     */
    public static String stringDigest(String data, String algorithm) throws NoSuchAlgorithmException {
        return encodeHexString(digest(data, algorithm));
    }

    /**
     * Method to get digest
     *
     * @param data:      data to digest as byte array
     * @param algorithm: algorithm used in signature -> MD5,SHA-1 or SHA-256
     * @return digest result as {@link String}
     */
    public static String stringDigest(byte[] data, String algorithm) throws NoSuchAlgorithmException {
        return encodeHexString(digest(data, algorithm));
    }

    /**
     * Method to get digest
     *
     * @param data:      data to digest as {@link String}
     * @param algorithm: algorithm used in signature -> MD5,SHA-1 or SHA-256
     * @return digest result as byte array
     */
    public static byte[] digest(String data, String algorithm) throws NoSuchAlgorithmException {
        return digest(data.getBytes(), algorithm);
    }

    /**
     * Method to get digest
     *
     * @param data:      data to digest as byte array
     * @param algorithm: algorithm used in signature -> MD5,SHA-1 or SHA-256
     * @return digest result as byte array
     */
    public static byte[] digest(byte[] data, String algorithm) throws NoSuchAlgorithmException {
        if (algorithm == null || (!algorithm.equals(MD5_ALGORITHM) && !algorithm.equals(SHA1_ALGORITHM)
                && !algorithm.equals(SHA256_ALGORITHM))) {
            throw new IllegalArgumentException("Algorithm must be MD5,SHA-1 or SHA-256");
        }
        MessageDigest messageDigest = MessageDigest.getInstance(algorithm);
        messageDigest.update(data);
        return messageDigest.digest();
    }

    /**
     * Method to get params signature for an {@code "HTTP"} request
     *
     * @param signatureKey: key used to signature request
     * @param data:         data to sign
     * @param algorithm:    algorithm used in signature -> HmacSHA256 or HmacSHA512
     * @return signature e.g. c8db66725ae71d6d79447319e617115f4a920f5agcdabcb2838bd6b712b053c4"
     */
    public static String getSignature(String signatureKey, String data, String algorithm) throws Exception {
        if (algorithm == null || (!algorithm.equals(HMAC_SHA256_ALGORITHM) && !algorithm.equals(HMAC_SHA512_ALGORITHM)))
            throw new IllegalArgumentException("Algorithm must be HmacSHA256 or HmacSHA512");
        Mac sha = Mac.getInstance(algorithm);
        sha.init(new SecretKeySpec(signatureKey.getBytes(UTF_8), algorithm));
        return encodeHexString(sha.doFinal(data.replace("?", "").getBytes(UTF_8)));
    }

    /**
     * Method to get params signature for an {@code "HTTP"} request
     *
     * @param signatureKey: key bytes used to signature request
     * @param data:         data to sign
     * @param algorithm:    algorithm used in signature -> HmacSHA256 or HmacSHA512
     * @return signature in base64 form e.g. c8db66725ae71d6d79447319e617115f4a920f5agcdabcb2838bd6b712b053c4=="
     */
    public static String getBase64Signature(byte[] signatureKey, String data, String algorithm) throws Exception {
        if (algorithm == null || (!algorithm.equals(HMAC_SHA256_ALGORITHM) && !algorithm.equals(HMAC_SHA512_ALGORITHM)))
            throw new IllegalArgumentException("Algorithm must be HmacSHA256 or HmacSHA512");
        Mac mac = Mac.getInstance(algorithm);
        mac.init(new SecretKeySpec(signatureKey, algorithm));
        return getEncoder().encodeToString(mac.doFinal(data.getBytes()));
    }

    /**
     * Method to get params signature for an {@code "HTTP"} request
     *
     * @param signatureKey: key used to signature request
     * @param data:         data to sign
     * @param algorithm:    algorithm used in signature -> HmacSHA256 or HmacSHA512
     * @return signature in base64 form e.g. c8db66725ae71d6d79447319e617115f4a920f5agcdabcb2838bd6b712b053c4=="
     */
    public static String getBase64Signature(String signatureKey, String data, String algorithm) throws Exception {
        return getBase64Signature(getDecoder().decode(signatureKey), data, algorithm);
    }

    /**
     * Method to download a file from an {@code "URL"} source
     *
     * @param url:      source URL from download the file
     * @param pathName: path name for the file, this must include also the suffix e.g. -> download.{suffix}
     * @param save:     flag whether save the file, if is set to {@code "false"} will be created a temporary file
     *                  that will be deleted on exit
     * @return file downloaded as {@link File}
     * @throws IOException when path name is invalid or an error occurred during the download of the file
     */
    public static File downloadFile(String url, String pathName, boolean save) throws IOException {
        if (url == null)
            throw new IOException("The URL source cannot be null");
        if (pathName == null || pathName.replace(" ", "").isEmpty())
            throw new IOException("The path name for the file cannot be null or blank");
        try {
            ReadableByteChannel byteChannel = Channels.newChannel(new URL(url).openStream());
            if (!pathName.contains("."))
                throw new IOException("Path name must also contains the suffix for the file");
            checkToCreateDirs(pathName);
            try (FileOutputStream fileOutputStream = new FileOutputStream(pathName)) {
                FileChannel fileChannel = fileOutputStream.getChannel();
                fileChannel.transferFrom(byteChannel, 0, MAX_VALUE);
                if (!save) {
                    String suffix = "." + pathName.split("\\.")[1];
                    File runtimeFile = File.createTempFile(pathName.replace(suffix, ""), suffix);
                    runtimeFile.deleteOnExit();
                    return runtimeFile;
                } else
                    return new File(pathName);
            }
        } catch (MalformedURLException e) {
            throw new IOException("The URL source is not a valid source: " + e.getLocalizedMessage());
        }
    }

    /**
     * Method to check if the pathname (without the name and the suffix of the file to save) given by the user exists then
     * if not exists create that pathname
     *
     * @param pathName: the pathname where save the file
     * @throws IOException when an error occurred during the creation of the directories
     */
    private static void checkToCreateDirs(String pathName) throws IOException {
        int lastIndexOf = pathName.lastIndexOf("/");
        if (lastIndexOf != -1) {
            String pathValue = pathName.substring(0, pathName.lastIndexOf("/"));
            Path path = Path.of(pathValue);
            if (!Files.exists(path)) {
                boolean success = new File(pathValue).mkdirs();
                if (!success)
                    throw new IOException("The pathname cannot be created");
            }
        }
    }

    /**
     * Method to create a data URI scheme
     *
     * @param file: file from create the data URI scheme
     * @return <b>{@code [<media type>][;base64],data}</b> as {@link String}
     * @throws IOException when an error occurred
     */
    public static String createDataURIScheme(File file) throws IOException {
        Path path = file.toPath();
        return "data:" + Files.probeContentType(path) + ";base64," + getEncoder().encodeToString(Files.readAllBytes(path));
    }

    /**
     * Method to send an api request
     *
     * @param requestUrl: {@code "URL"} used in the api request
     * @param method:     method used in the api request
     */
    public void sendAPIRequest(String requestUrl, RequestMethod method) throws IOException {
        setRequest(requestUrl, method);
        performRequest();
    }

    /**
     * Method to set programmatically default error message to return if is not request error
     *
     * @param defaultErrorResponse: error message to return if is not request error
     */
    public void setDefaultErrorResponse(String defaultErrorResponse) {
        this.defaultErrorResponse = defaultErrorResponse;
    }

    /**
     * Method to set programmatically timeout for the request
     *
     * @param requestTimeout: timeout for the requests
     */
    public void setConnectionTimeout(long requestTimeout) {
        okHttpClient = okHttpClient.newBuilder()
                .connectTimeout(requestTimeout, MILLISECONDS)
                .build();
    }

    /**
     * Method to set programmatically {@link #enableCertificatesValidation} value
     *
     * @param enableCertificatesValidation: whether enable the <b>SSL</b> certificates validation, this for example
     *                                      when the certificate is a self-signed certificate to by-pass
     */
    public void enableCertificatesValidation(boolean enableCertificatesValidation) {
        this.enableCertificatesValidation = enableCertificatesValidation;
    }

    /**
     * Returns a string representation of the object <br>
     * No-any params required
     *
     * @return a string representation of the object as {@link String}
     */
    @Override
    public String toString() {
        return new JSONObject(this).toString();
    }

    /**
     * The {@code Headers} class is useful to assemble headers values for the request
     */
    public static class Headers {

        /**
         * {@code headers} is the instance that contains headers value
         */
        private final HashMap<String, String> headers;

        /**
         * Constructor to init {@link Headers} <br>
         * No-any params required
         */
        public Headers() {
            headers = new HashMap<>();
        }

        /**
         * Constructor to init {@link Headers} <br>
         *
         * @param mergeHeaders: other headers to merge with current {@link Headers}
         */
        public Headers(Headers mergeHeaders) {
            headers = new HashMap<>();
            mergeHeaders(mergeHeaders);
        }

        /**
         * Method to add a new header value
         *
         * @param headerKey:   key of the header
         * @param headerValue: value of the header
         * @throws IllegalArgumentException when params inserted do not respect validity range -> null or blank
         */
        public <T> void addHeader(String headerKey, T headerValue) {
            String header = null;
            if (headerValue != null)
                header = headerValue.toString();
            if (headerKey == null || headerKey.isEmpty())
                throw new IllegalArgumentException("Key of the header cannot be null or blank");
            headers.put(headerKey, header);
        }

        /**
         * Method to merge another headers value in the same object
         *
         * @param mergeHeaders: headers to merge
         * @apiNote if {@code mergeHeaders} is null merge will not execute
         */
        public void mergeHeaders(Headers mergeHeaders) {
            if (mergeHeaders != null)
                for (String key : mergeHeaders.getHeadersKeys())
                    addHeader(key, mergeHeaders.getHeader(key));
        }

        /**
         * Method to remove a header
         *
         * @param headerKey: key of the header to remove
         */
        public void removeHeader(String headerKey) {
            headers.remove(headerKey);
        }

        /**
         * Method to clear all headers <br>
         * No-any params required
         */
        public void clear() {
            headers.clear();
        }

        /**
         * Method to get a header value
         *
         * @param headerKey: key of the header to get
         * @return header value as {@link String}
         */
        public String getHeader(String headerKey) {
            return headers.get(headerKey);
        }

        /** Method to get all headers value<br>
         * No-any params required
         * @return all headers value as {@link Collection} of {@link String}
         * */
        public Collection<String> getAllHeaders() {
            return headers.values();
        }

        /**
         * Method to get all headers keys<br>
         * No-any params required
         *
         * @return all headers keys as {@link Set} of {@link String}
         */
        public Set<String> getHeadersKeys() {
            return headers.keySet();
        }

        /**
         * Returns a string representation of the object <br>
         * No-any params required
         *
         * @return a string representation of the object as {@link String}
         */
        @Override
        public String toString() {
            return headers.toString();
        }

    }

    /**
     * The {@code Params} class is useful to assemble params values for the request
     * @implNote this class can be used to assemble body payload or query request params
     * */
    public static class Params {

        /**
         * {@code apiRequest} is the instance for {@link APIRequest} object
         */
        private final APIRequest apiRequest;

        /**
         * {@code params} is the instance that contains params value
         */
        private final HashMap<String, Object> params;

        /**
         * Constructor to init {@link Params} <br>
         * No-any params required
         */
        public Params() {
            apiRequest = new APIRequest();
            params = new HashMap<>();
        }

        /**
         * Constructor to init {@link Params} <br>
         *
         * @param mergeParams: other params to merge with current {@link Params}
         */
        public Params(Params mergeParams) {
            apiRequest = new APIRequest();
            params = new HashMap<>();
            mergeParams(mergeParams);
        }

        /**
         * Method to add a new param value
         *
         * @param keyParam:   key of the param
         * @param valueParam: value of the param
         * @throws IllegalArgumentException when params inserted do not respect validity range -> null or blank
         */
        public <T> void addParam(String keyParam, T valueParam) {
            if (keyParam == null || keyParam.isEmpty())
                throw new IllegalArgumentException("Key of the param cannot be null or blank");
            params.put(keyParam, valueParam);
        }

        /**
         * Method to merge another params value in the same object
         *
         * @param mergeParams: params to merge
         * @apiNote if {@code mergeParams} is null merge will not execute
         */
        public void mergeParams(Params mergeParams) {
            if (mergeParams != null)
                for (String key : mergeParams.getParamsKeys())
                    addParam(key, mergeParams.getParam(key));
        }

        /**
         * Method to remove a param
         *
         * @param keyParam: key of the param to remove
         */
        public void removeParam(String keyParam) {
            params.remove(keyParam);
        }

        /**
         * Method to clear all params <br>
         * No-any params required
         */
        public void clear() {
            params.clear();
        }

        /**
         * Method to get a param value
         * @param paramKey: key of the param to get
         * @return params value as {@link String}
         * */
        public <T> T getParam(String paramKey) {
            return (T) params.get(paramKey);
        }

        /** Method to get all params value<br>
         * No-any params required
         * @return all params value as {@link Collection} of {@link T}
         * */
        public <T> Collection<T> getAllParams() {
            return (Collection<T>) params.values();
        }

        /**
         * Method to get all params keys<br>
         * No-any params required
         *
         * @return all params keys as {@link Set} of {@link String}
         */
        public Set<String> getParamsKeys() {
            return params.keySet();
        }

        /**
         * Method to assemble a query params string <br>
         * No-any params required
         *
         * @return query params as {@link String} assembled e.g. ?param=query1&param2=query2
         * @throws IllegalArgumentException when extra params in list is empty or is null
         */
        public String createQueryString() {
            return apiRequest.encodeQueryParams(this);
        }

        /**
         * Method to assemble a query params string
         *
         * @param mandatoryParams: mandatory params of the request as {@link Params}
         * @return query params as {@link String} assembled e.g. ?param=query1&param2=query2
         * @throws IllegalArgumentException when extra params in list is empty or is null
         */
        public String createQueryString(String mandatoryParams) {
            return apiRequest.encodeAdditionalParams(mandatoryParams, this);
        }

        /**
         * Method to assemble a query params string
         *
         * @param mandatoryParams: mandatory params of the request as {@link Params}
         * @return query params as {@link Params} assembled e.g. ?param=query1&param2=query2
         * @throws IllegalArgumentException when extra params in list is empty or is null
         */
        public String createQueryString(Params mandatoryParams) {
            return apiRequest.encodeAdditionalParams(mandatoryParams.createQueryString(), this);
        }

        /**
         * Method to assemble a body params of an {@code "HTTP"} request <br>
         * No-any params required
         *
         * @return body params as {@link String} assembled e.g. param=mandatory1&param2=mandatory2
         * @throws IllegalArgumentException when extra params in list is empty or is null
         */
        public String createPayload() {
            return apiRequest.encodeBodyParams(this);
        }

        /**
         * Method to assemble a body params of an {@code "HTTP"} request
         *
         * @param extraPayload: extra payload data to add
         * @return body params as {@link String} assembled e.g. param=mandatory1&param2=mandatory2
         * @throws IllegalArgumentException when extra params in list is empty or is null
         */
        public String createPayload(Params extraPayload) {
            mergeParams(extraPayload);
            return apiRequest.encodeBodyParams(this);
        }

        /**
         * Method to assemble a body params of an {@code "HTTP"} request <br>
         * No-any params required
         *
         * @return body params as {@link JSONObject}
         * @throws IllegalArgumentException when extra params in list is empty or is null
         */
        public JSONObject createJSONPayload() {
            JSONObject payload = new JSONObject();
            for (String key : getParamsKeys())
                payload.put(key, (Object) getParam(key));
            return payload;
        }

        /**
         * Method to assemble a body params of an {@code "HTTP"} request
         *
         * @param extraPayload: extra payload data to add
         * @return body params as {@link JSONObject}
         * @throws IllegalArgumentException when extra params in list is empty or is null
         */
        public JSONObject createJSONPayload(Params extraPayload) {
            mergeParams(extraPayload);
            return createJSONPayload();
        }

        /**
         * Returns a string representation of the object <br>
         * No-any params required
         *
         * @return a string representation of the object as {@link String}
         */
        @Override
        public String toString() {
            return params.toString();
        }

    }

    /**
     * {@code RequestMethod} list of available request methods
     */
    public enum RequestMethod {

        /**
         * {@code "GET"} method for {@code "HTTP"} requests
         */
        GET,

        /**
         * {@code "POST"} method for {@code "HTTP"} requests
         */
        POST,

        /**
         * {@code "DELETE"} method for {@code "HTTP"} requests
         */
        DELETE,

        /**
         * {@code "PUT"} method for {@code "HTTP"} requests
         */
        PUT,

        /**
         * {@code "PATCH"} method for {@code "HTTP"} requests
         */
        PATCH

    }

}
