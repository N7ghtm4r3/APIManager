package com.tecknobit.apimanager.apis;

import com.google.api.client.http.*;
import com.google.api.client.http.apache.v2.ApacheHttpTransport;
import com.tecknobit.apimanager.annotations.Wrapper;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

import static com.tecknobit.apimanager.apis.APIRequest.RequestMethod.DELETE;
import static java.lang.Long.MAX_VALUE;
import static java.nio.charset.StandardCharsets.UTF_8;
import static java.util.Base64.getDecoder;
import static java.util.Base64.getEncoder;
import static org.apache.commons.codec.binary.Hex.encodeHexString;

/**
 * The {@code APIRequest} class is useful to send and manage any {@code "API"} requests and their responses
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the usage at <a href="https://github.com/N7ghtm4r3/APIManager/blob/main/documd/APIRequest.md">APIRequest.md</a>
 * @since 1.0.0
 **/
public class APIRequest {

    /**
     * {@code DEFAULT_ERROR_RESPONSE} is constant that contains default error message if user not custom it
     **/
    public static final String DEFAULT_ERROR_RESPONSE = "Error is not in api request, check out your code";

    /**
     * {@code DEFAULT_REQUEST_TIMEOUT} is constant that contains default request timeout if user not custom it
     **/
    public static final int DEFAULT_REQUEST_TIMEOUT = 10000;

    /**
     * {@code HMAC_SHA256_ALGORITHM} is constant that contains {@code "HMAC SHA 256"} algorithm type
     * **/
    public static final String HMAC_SHA256_ALGORITHM = "HmacSHA256";

    /**
     * {@code HMAC_SHA512_ALGORITHM} is constant that contains {@code "HMAC SHA 512"} algorithm type
     * **/
    public static final String HMAC_SHA512_ALGORITHM = "HmacSHA512";

    /**
     * {@code MD5_ALGORITHM} is constant that contains {@code "MD5"} algorithm type
     * **/
    public static final String MD5_ALGORITHM = "MD5";

    /**
     * {@code SHA1_ALGORITHM} is constant that contains {@code "SHA-1"} algorithm type
     * **/
    public static final String SHA1_ALGORITHM = "SHA-1";

    /**
     * {@code SHA256_ALGORITHM} is constant that contains {@code "SHA-256"} algorithm type
     **/
    public static final String SHA256_ALGORITHM = "SHA-256";

    /**
     * {@code transport} is the instance useful for the requests
     **/
    private static final ApacheHttpTransport transport = new ApacheHttpTransport();

    /**
     * {@code requestTimeout} is the instance that contains time to keep alive request
     **/
    private int requestTimeout;

    /**
     * {@code defaultErrorResponse} is the instance that contains default error message
     **/
    private String defaultErrorResponse;

    /**
     * {@code request} is the instance useful for the requests
     **/
    private HttpRequest request;

    /**
     * {@code response} is the instance that contains response message from request to return
     **/
    private String response;

    /**
     * {@code errorResponse} is the instance that contains error message from request
     **/
    private String errorResponse;

    /**
     * {@code statusCode} status code of a request
     **/
    private int statusCode;

    /**
     * Constructor to init {@link APIRequest}
     *
     * @param defaultErrorResponse error message to return if is not request error
     * @param requestTimeout       timeout for the requests
     **/
    public APIRequest(String defaultErrorResponse, int requestTimeout) {
        this.defaultErrorResponse = defaultErrorResponse;
        this.requestTimeout = requestTimeout;
    }

    /**
     * Constructor to init {@link APIRequest}
     *
     * @param defaultErrorResponse error message to return if is not request error
     **/
    public APIRequest(String defaultErrorResponse) {
        requestTimeout = DEFAULT_REQUEST_TIMEOUT;
        this.defaultErrorResponse = defaultErrorResponse;
    }

    /**
     * Constructor to init {@link APIRequest}
     *
     * @param requestTimeout timeout for the requests
     **/
    public APIRequest(int requestTimeout) {
        this.requestTimeout = requestTimeout;
        defaultErrorResponse = DEFAULT_ERROR_RESPONSE;
    }

    /**
     * Constructor to init {@link APIRequest} <br>
     * <p>
     * Any params required
     *
     * @apiNote {@link #requestTimeout} and {@link #defaultErrorResponse} will be instantiated with the default values:
     * <ul>
     *     <li>
     *         {@link #requestTimeout} = {@link #DEFAULT_REQUEST_TIMEOUT}
     *     </li>
     *     <li>
     *         {@link #defaultErrorResponse} = {@link #DEFAULT_ERROR_RESPONSE}
     *     </li>
     * </ul>
     **/
    public APIRequest() {
        requestTimeout = DEFAULT_REQUEST_TIMEOUT;
        defaultErrorResponse = DEFAULT_ERROR_RESPONSE;
    }

    /**
     * Method to send an api request with a single header
     *
     * @param requestUrl:  {@code "URL"} used in the api request
     * @param method:      method used in the api request
     * @param headerKey:   header key for the request
     * @param headerValue: header value for the request
     **/
    public <T> void sendAPIRequest(String requestUrl, RequestMethod method, String headerKey,
                                   T headerValue) throws IOException {
        setRequest(requestUrl, method);
        request.setHeaders(new HttpHeaders().set(headerKey, headerValue));
        performRequest();
    }

    /**
     * Method to send an api request with different headers
     *
     * @param requestUrl: {@code "URL"} used in the api request
     * @param method:     method used in the api request
     * @param headers:    headers for the request
     **/
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
     **/
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
     **/
    public <T> void sendAPIRequest(String requestUrl, RequestMethod method, String headerKey, T headerValue,
                                   Params queryParams) throws IOException {
        setRequest(requestUrl + queryParams.createQueryString(), method);
        request.setHeaders(new HttpHeaders().set(headerKey, headerValue));
        performRequest();
    }

    /**
     * Method to send an api request with different headers
     *
     * @param requestUrl:  {@code "URL"} used in the api request
     * @param method:      method used in the api request
     * @param headers:     headers for the request
     * @param queryParams: query params of the request
     **/
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
     **/
    public void sendPayloadedAPIRequest(String requestUrl, RequestMethod method, Params payload) throws IOException {
        if (method.equals(DELETE))
            execDeletePayloadedRequest(requestUrl, payload, false, null);
        else {
            setRequest(requestUrl, method, payload, false);
            performRequest();
        }
    }

    /**
     * Method to send an api request with a payload and with a single header
     *
     * @param requestUrl:  {@code "URL"} used in the api request
     * @param method:      method used in the api request
     * @param headerKey:   header key for the request
     * @param headerValue: header value for the request
     * @param payload:     params to insert in the payload for the {@code "HTTP"} request
     **/
    public <T> void sendPayloadedAPIRequest(String requestUrl, RequestMethod method, String headerKey, T headerValue,
                                            Params payload) throws IOException {
        if (method.equals(DELETE)) {
            Headers headers = new Headers();
            headers.addHeader(headerKey, headerValue);
            execDeletePayloadedRequest(requestUrl, payload, false, headers);
        } else {
            setRequest(requestUrl, method, payload, false);
            request.setHeaders(new HttpHeaders().set(headerKey, headerValue));
            performRequest();
        }
    }

    /**
     * Method to send an api request with a payload and with different headers
     *
     * @param requestUrl: {@code "URL"} used in the api request
     * @param method:     method used in the api request
     * @param headers:    headers for the request
     * @param payload:    params to insert in the payload for the {@code "HTTP"} request
     **/
    public void sendPayloadedAPIRequest(String requestUrl, RequestMethod method, Headers headers,
                                        Params payload) throws IOException {
        if (method.equals(DELETE))
            execDeletePayloadedRequest(requestUrl, payload, false, headers);
        else {
            setRequest(requestUrl, method, payload, false);
            setHeaders(headers);
            performRequest();
        }
    }

    /**
     * Method to send an api request with a payload formatted in {@code "JSON"}
     *
     * @param requestUrl: {@code "URL"} used in the api request
     * @param method:     method used in the api request
     * @param payload:    params to insert in the payload for the {@code "HTTP"} request
     **/
    public void sendJSONPayloadedAPIRequest(String requestUrl, RequestMethod method, Params payload) throws IOException {
        if (method.equals(DELETE))
            execDeletePayloadedRequest(requestUrl, payload, true, null);
        else {
            setRequest(requestUrl, method, payload, true);
            performRequest();
        }
    }

    /**
     * Method to send an api request with a payload formatted in {@code "JSON"} and with a single header
     *
     * @param requestUrl:  {@code "URL"} used in the api request
     * @param method:      method used in the api request
     * @param headerKey:   header key for the request
     * @param headerValue: header value for the request
     * @param payload:     params to insert in the payload for the {@code "HTTP"} request
     **/
    public <T> void sendJSONPayloadedAPIRequest(String requestUrl, RequestMethod method, String headerKey, T headerValue,
                                                Params payload) throws IOException {
        if (method.equals(DELETE)) {
            Headers headers = new Headers();
            headers.addHeader(headerKey, headerValue);
            execDeletePayloadedRequest(requestUrl, payload, true, headers);
        } else {
            setRequest(requestUrl, method, payload, true);
            request.setHeaders(new HttpHeaders().set(headerKey, headerValue));
            performRequest();
        }
    }

    /**
     * Method to send an api request with a payload formatted in {@code "JSON"} and with different headers
     *
     * @param requestUrl: {@code "URL"} used in the api request
     * @param method:     method used in the api request
     * @param headers:    headers for the request
     * @param payload:    params to insert in the payload for the {@code "HTTP"} request
     **/
    public void sendJSONPayloadedAPIRequest(String requestUrl, RequestMethod method, Headers headers,
                                            Params payload) throws IOException {
        if (method.equals(DELETE))
            execDeletePayloadedRequest(requestUrl, payload, true, headers);
        else {
            setRequest(requestUrl, method, payload, true);
            setHeaders(headers);
            performRequest();
        }
    }

    /**
     * Method to send a {@link RequestMethod#DELETE} api request with a payload attached
     *
     * @param requestUrl:    {@code "URL"} used in the api request
     * @param payload:       params to insert in the payload for the {@code "HTTP"} request
     * @param isJsonPayload: flag whether payload is to send formatted in {@code "JSON"} or not
     * @param headers:       headers for the request
     **/
    private void execDeletePayloadedRequest(String requestUrl, Params payload, boolean isJsonPayload,
                                            Headers headers) throws IOException {
        HttpURLConnection request = (HttpURLConnection) new URL(requestUrl).openConnection();
        request.setRequestMethod(DELETE.name());
        if (headers != null)
            for (String key : headers.getHeadersKeys())
                request.setRequestProperty(key, headers.getHeader(key));
        request.setDoOutput(true);
        OutputStreamWriter oPayload = new OutputStreamWriter(request.getOutputStream());
        String sPayload;
        if (isJsonPayload)
            sPayload = payload.createJSONPayload().toString();
        else
            sPayload = payload.createPayload();
        oPayload.write(sPayload);
        oPayload.close();
        request.connect();
        BufferedReader responseReader;
        statusCode = request.getResponseCode();
        try {
            response = readInputStream(request.getInputStream());
        } catch (IOException e) {
            errorResponse = readInputStream(request.getErrorStream());
        }
    }

    /**
     * Method to set up connection by an endpoint
     *
     * @param requestUrl: {@code "URL"} used to make {@code "HTTP"} request
     * @param method:     method used to make {@code "HTTP"} request
     **/
    private void setRequest(String requestUrl, RequestMethod method) throws IOException {
        request = transport.createRequestFactory().buildRequest(method.name(), new GenericUrl(requestUrl), null);
        request.setConnectTimeout(requestTimeout);
    }

    /**
     * Method to set up connection by an endpoint
     *
     * @param requestUrl:    {@code "URL"} used to make {@code "HTTP"} request
     * @param method:        method used to make {@code "HTTP"} request
     * @param payload:       params to insert in the payload for the {@code "HTTP"} request
     * @param isJsonPayload: flag whether payload is to send formatted in {@code "JSON"} or not
     **/
    private void setRequest(String requestUrl, RequestMethod method, Params payload, boolean isJsonPayload) throws IOException {
        ByteArrayContent content = null;
        if (payload != null) {
            String cPayload;
            if (isJsonPayload)
                cPayload = payload.createJSONPayload().toString();
            else
                cPayload = payload.createPayload();
            content = ByteArrayContent.fromString(null, cPayload);
        }
        request = transport.createRequestFactory().buildRequest(method.name(), new GenericUrl(requestUrl), content);
        request.setConnectTimeout(requestTimeout);
    }

    /**
     * Method to get the response of an {@code "HTTP"} request <br>
     * <p>
     * Any params required
     **/
    private void performRequest() throws IOException {
        errorResponse = null;
        request.setThrowExceptionOnExecuteError(false);
        HttpResponse gResponse = request.execute();
        InputStream content = gResponse.getContent();
        statusCode = gResponse.getStatusCode();
        if (content != null) {
            response = readInputStream(content);
            if (!gResponse.isSuccessStatusCode()) {
                errorResponse = response;
                response = null;
                throw new IOException();
            }
        } else
            response = String.valueOf(gResponse.getStatusCode());
    }

    /**
     * Method to read a stream from a response
     *
     * @param stream: the stream to read
     * @return stream red from the response as {@link String}
     **/
    private String readInputStream(InputStream stream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(stream));
        StringBuilder response = new StringBuilder();
        String line;
        while ((line = bufferedReader.readLine()) != null)
            response.append(line);
        return response.toString();
    }

    /**
     * Method to set the headers of an {@code "HTTP"} request <br>
     *
     * @param headers: headers for the request
     **/
    private void setHeaders(Headers headers) {
        HttpHeaders httpHeaders = new HttpHeaders();
        for (String key : headers.getHeadersKeys())
            httpHeaders.set(key, headers.getHeader(key));
        request.setHeaders(httpHeaders);
    }

    /**
     * Method to parse the response of the request and format it as {@code "JSON"}
     *
     * @param response: response to parse
     * @return the response formatted as {@code "JSON"} or a simple {@link String} if is not possible format as {@code "JSON"}
     **/
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
     * Method to get the response of the request <br>
     * Any params required
     *
     * @return response of the request as {@link String}
     **/
    public String getResponse() {
        return response;
    }

    /**
     * Method to get the response of the request <br>
     * Any params required
     *
     * @return response of the request formatted as {@code "JSON"} or as simple {@link String} if is not possible format
     * as {@code "JSON"}
     **/
    public <T> T getJSONResponse() {
        return parseResponseAsJson(response);
    }

    /**
     * Method to get the error response of the request <br>
     * Any params required
     *
     * @return error response of the request formatted or {@link #defaultErrorResponse} as {@link String}
     **/
    public String getErrorResponse() {
        if (errorResponse == null)
            return defaultErrorResponse;
        return errorResponse;
    }

    /**
     * Method to print the error response of the request <br>
     * Any params required
     **/
    public void printErrorResponse() {
        System.out.println(getErrorResponse());
    }

    /**
     * Method to get the error response of the request <br>
     * Any params required
     *
     * @return error response of the request formatted as {@code "JSON"} or {@link #defaultErrorResponse} as {@link String}
     * if is not a request error
     **/
    public <T> T getJSONErrorResponse() {
        if (errorResponse == null)
            return (T) defaultErrorResponse;
        return parseResponseAsJson(errorResponse);
    }

    /**
     * Method to print the error response of the request <br>
     * <p>
     * Any params required
     *
     * @apiNote the response will be printed formatted as {@code "JSON"} or a simple {@link String}
     * if is not possible format as {@code "JSON"}
     **/
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
     * Any params required
     *
     * @return response code of the request as int
     **/
    public int getResponseStatusCode() {
        return statusCode;
    }

    /**
     * Method to concatenate a series of same key param
     *
     * @param initialChar: start char of concatenation, can be "" or &
     * @param key:         key of param to concatenate es. param
     * @param params:      values of param to concatenate es. value1, value2 as {@link ArrayList}
     * @return series of params concatenated as {@link String} es. param=value1&param=value2
     * @throws IllegalArgumentException when one of the params inserted does not respect correct range
     **/
    @Wrapper
    public <T> String concatenateParamsList(String initialChar, String key, ArrayList<T> params) {
        return concatenateParamsList(initialChar, key, params.toArray());
    }

    /**
     * Method to concatenate a series of same key param
     *
     * @param initialChar: start char of concatenation, can be "" or &
     * @param key:         key of param to concatenate es. param
     * @param params:      values of param to concatenate es. value1, value2 as {@link Object} arrays
     * @return series of params concatenated as {@link String} es. param=value1&param=value2
     * @throws IllegalArgumentException when one of the params inserted does not respect correct range
     **/
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
                    paramsConcatenation.append(initialChar).append(key).append("=").append(param);
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
     * @return list of params as {@link String} es. value,value2,value3
     * @throws IllegalArgumentException when one of the params inserted does not respect correct range
     **/
    @Wrapper
    public <T> String assembleParamsList(String separator, ArrayList<T> params) {
        return assembleParamsList(separator, params.toArray());
    }

    /**
     * Method to concatenate a list of params
     *
     * @param separator: char to divide items of the list
     * @param params:    values of the list to concatenate
     * @return list of params as {@link String} es. value,value2,value3
     * @throws IllegalArgumentException when one of the params inserted does not respect correct range
     **/
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
            paramsList.append(param).append(separator);
        }
        paramsList.replace(paramsList.length() - 1, paramsList.length(), "");
        return paramsList.toString();
    }

    /**
     * Method to concatenate a list of params
     *
     * @param starterSeparator: initial char to divide items of the list
     * @param enderSeparator:   final char to divide items of the list
     * @param params:           values of the list to concatenate
     * @return list of params as {@link String} es. ,value","value2",value3"
     * @throws IllegalArgumentException when one of the params inserted does not respect correct range
     **/
    @Wrapper
    public <T> String assembleParamsList(String starterSeparator, String enderSeparator, ArrayList<T> params) {
        return assembleParamsList(starterSeparator, enderSeparator, params.toArray());
    }

    /**
     * Method to concatenate a list of params
     *
     * @param starterSeparator: initial char to divide items of the list
     * @param enderSeparator:   final char to divide items of the list
     * @param params:           values of the list to concatenate
     * @return list of params as {@link String} es. ,value","value2",value3"
     * @throws IllegalArgumentException when one of the params inserted does not respect correct range
     **/
    @SafeVarargs
    public final <T> String assembleParamsList(String starterSeparator, String enderSeparator, T... params) {
        if (starterSeparator == null || starterSeparator.isEmpty())
            throw new IllegalArgumentException("Separator value cannot be null or blank");
        if (params == null)
            throw new IllegalArgumentException("Params instance must contains some value");
        StringBuilder paramsList = new StringBuilder();
        for (T param : params) {
            if (param == null || param.equals(""))
                throw new IllegalArgumentException("Param value cannot be null or empty");
            paramsList.append(starterSeparator).append(param).append(enderSeparator);
        }
        paramsList.replace(paramsList.length() - 1, paramsList.length(), "");
        return paramsList.toString();
    }

    /**
     * Method to assemble a query params for an {@code "HTTP"} request
     *
     * @param queryParams: queryParams of request (?param=mandatory1&param2=mandatory2)
     * @return query params as {@link String} assembled es. ?param=query1&param2=query2
     * @throws IllegalArgumentException when extra params in list is empty or is null
     **/
    @Wrapper
    public <T> String encodeQueryParams(Params queryParams) {
        return encodeAdditionalParams(null, queryParams);
    }

    /**
     * Method to assemble a body params of an {@code "HTTP"} request
     *
     * @param bodyParams: mandatory params of request (?param=mandatory1&param2=mandatory2)
     * @return body params as {@link String} assembled es. param=mandatory1&param2=mandatory2
     * @throws IllegalArgumentException when extra params in list is empty or is null
     **/
    @Wrapper
    public <T> String encodeBodyParams(Params bodyParams) {
        return encodeAdditionalParams(null, bodyParams).replace("?", "");
    }

    /**
     * Method to assemble a query string params of an {@code "HTTP"} request
     *
     * @param mandatoryParams: mandatory params of request (?param=mandatory1&param2=mandatory2)
     * @param extraParams:     not mandatory params of request that have to be concatenated (&param2=valueParam2&param3=valueParam3)
     * @return params as {@link String} assembled es. ?param=mandatory1&param2=mandatory2&param2=valueParam2&param3=valueParam3
     * @throws IllegalArgumentException when extra params in list is empty or is null
     **/
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
            if ((key != null && !key.equals(""))) {
                params.append(queryEncoderChar).append(key).append("=").append((Object) extraParams.getParam(key));
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
     **/
    public static String base64Digest(String data, String algorithm) throws NoSuchAlgorithmException {
        return base64Digest(data.getBytes(), algorithm);
    }

    /**
     * Method to get digest
     *
     * @param data:      data to digest as byte array
     * @param algorithm: algorithm used in signature -> MD5,SHA-1 or SHA-256
     * @return digest result as {@link String} in {@link Base64} encode
     **/
    public static String base64Digest(byte[] data, String algorithm) throws NoSuchAlgorithmException {
        return new String(getEncoder().encode(digest(data, algorithm)));
    }

    /**
     * Method to get digest
     *
     * @param data:      data to digest as {@link String}
     * @param algorithm: algorithm used in signature -> MD5,SHA-1 or SHA-256
     * @return digest result as {@link String}
     **/
    public static String stringDigest(String data, String algorithm) throws NoSuchAlgorithmException {
        return encodeHexString(digest(data, algorithm));
    }

    /**
     * Method to get digest
     *
     * @param data:      data to digest as byte array
     * @param algorithm: algorithm used in signature -> MD5,SHA-1 or SHA-256
     * @return digest result as {@link String}
     **/
    public static String stringDigest(byte[] data, String algorithm) throws NoSuchAlgorithmException {
        return encodeHexString(digest(data, algorithm));
    }

    /**
     * Method to get digest
     *
     * @param data:      data to digest as {@link String}
     * @param algorithm: algorithm used in signature -> MD5,SHA-1 or SHA-256
     * @return digest result as byte array
     **/
    public static byte[] digest(String data, String algorithm) throws NoSuchAlgorithmException {
        return digest(data.getBytes(), algorithm);
    }

    /**
     * Method to get digest
     *
     * @param data:      data to digest as byte array
     * @param algorithm: algorithm used in signature -> MD5,SHA-1 or SHA-256
     * @return digest result as byte array
     **/
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
     * @return signature es. c8db66725ae71d6d79447319e617115f4a920f5agcdabcb2838bd6b712b053c4"
     **/
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
     * @return signature in base64 form es. c8db66725ae71d6d79447319e617115f4a920f5agcdabcb2838bd6b712b053c4=="
     **/
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
     * @return signature in base64 form es. c8db66725ae71d6d79447319e617115f4a920f5agcdabcb2838bd6b712b053c4=="
     **/
    public static String getBase64Signature(String signatureKey, String data, String algorithm) throws Exception {
        return getBase64Signature(getDecoder().decode(signatureKey), data, algorithm);
    }

    /**
     * Method to download a file from an {@code "URL"} source
     *
     * @param url:      source URL from download the file
     * @param pathName: path name for the file, this must include also the suffix es. -> download.{suffix}
     * @param save:     flag whether save the file, if is set to {@code "false"} will be created a temporary file
     *                  that will be deleted on exit
     * @return file downloaded as {@link File}
     * @throws IOException when path name is invalid or an error occurred during the download of the file
     **/
    public static File downloadFile(String url, String pathName, boolean save) throws IOException {
        if (url == null)
            throw new IOException("The URL source cannot be null");
        if (pathName == null || pathName.replace(" ", "").isEmpty())
            throw new IOException("The path name for the file cannot be null or blank");
        try {
            ReadableByteChannel byteChannel = Channels.newChannel(new URL(url).openStream());
            if (!pathName.contains("."))
                throw new IOException("Path name must also contains the suffix for the file");
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
     * Method to create a data URI scheme
     *
     * @param file: file from create the data URI scheme
     * @return <b>{@code [<media type>][;base64],data}</b> as {@link String}
     * @throws IOException when an error occurred
     **/
    public static String createDataURIScheme(File file) throws IOException {
        Path path = file.toPath();
        return "data:" + Files.probeContentType(path) + ";base64," + getEncoder().encodeToString(Files.readAllBytes(path));
    }

    /**
     * Method to send an api request
     *
     * @param requestUrl: {@code "URL"} used in the api request
     * @param method:     method used in the api request
     **/
    public void sendAPIRequest(String requestUrl, RequestMethod method) throws IOException {
        setRequest(requestUrl, method);
        performRequest();
    }

    /**
     * Method to set programmatically default error message to return if is not request error
     *
     * @param defaultErrorResponse: error message to return if is not request error
     **/
    public void setDefaultErrorResponse(String defaultErrorResponse) {
        this.defaultErrorResponse = defaultErrorResponse;
    }

    /**
     * Method to set programmatically timeout for the request
     *
     * @param requestTimeout: timeout for the requests
     **/
    public void setRequestTimeout(int requestTimeout) {
        this.requestTimeout = requestTimeout;
    }

    /**
     * Returns a string representation of the object <br>
     * Any params required
     *
     * @return a string representation of the object as {@link String}
     */
    @Override
    public String toString() {
        return new JSONObject(this).toString();
    }

    /**
     * The {@code Headers} class is useful to assemble headers values for the request
     **/
    public static class Headers {

        /**
         * {@code headers} is the instance that contains headers value
         **/
        private final HashMap<String, String> headers;

        /**
         * Constructor to init {@link Headers} <br>
         * Any params required
         **/
        public Headers() {
            headers = new HashMap<>();
        }

        /**
         * Constructor to init {@link Headers} <br>
         *
         * @param mergeHeaders: other headers to merge with current {@link Headers}
         **/
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
         **/
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
         **/
        public void mergeHeaders(Headers mergeHeaders) {
            if (mergeHeaders != null)
                for (String key : mergeHeaders.getHeadersKeys())
                    addHeader(key, mergeHeaders.getHeader(key));
        }

        /**
         * Method to remove a header
         *
         * @param headerKey: key of the header to remove
         **/
        public void removeHeader(String headerKey) {
            headers.remove(headerKey);
        }

        /** Method to clear all headers <br>
         * Any params required
         * **/
        public void clear(){
            headers.clear();
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

        /**
         * Method to get all headers keys<br>
         * Any params required
         *
         * @return all headers keys as {@link Set} of {@link String}
         **/
        public Set<String> getHeadersKeys() {
            return headers.keySet();
        }

        /**
         * Returns a string representation of the object <br>
         * Any params required
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
     * **/
    public static class Params {

        /**
         * {@code apiRequest} is the instance for {@link APIRequest} object
         **/
        private final APIRequest apiRequest;

        /**
         * {@code params} is the instance that contains params value
         **/
        private final HashMap<String, Object> params;

        /**
         * Constructor to init {@link Params} <br>
         * Any params required
         **/
        public Params() {
            apiRequest = new APIRequest();
            params = new HashMap<>();
        }

        /**
         * Constructor to init {@link Params} <br>
         *
         * @param mergeParams: other params to merge with current {@link Params}
         **/
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
         **/
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
         **/
        public void mergeParams(Params mergeParams) {
            if (mergeParams != null)
                for (String key : mergeParams.getParamsKeys())
                    addParam(key, mergeParams.getParam(key));
        }

        /**
         * Method to remove a param
         *
         * @param keyParam: key of the param to remove
         **/
        public void removeParam(String keyParam) {
            params.remove(keyParam);
        }

        /**
         * Method to clear all params <br>
         * Any params required
         **/
        public void clear() {
            params.clear();
        }

        /**
         * Method to get a param value
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

        /**
         * Method to get all params keys<br>
         * Any params required
         *
         * @return all params keys as {@link Set} of {@link String}
         **/
        public Set<String> getParamsKeys() {
            return params.keySet();
        }

        /**
         * Method to assemble a query params string <br>
         * Any params required
         *
         * @return query params as {@link String} assembled es. ?param=query1&param2=query2
         * @throws IllegalArgumentException when extra params in list is empty or is null
         **/
        public String createQueryString() {
            return apiRequest.encodeQueryParams(this);
        }

        /**
         * Method to assemble a query params string
         *
         * @param mandatoryParams: mandatory params of the request as {@link Params}
         * @return query params as {@link String} assembled es. ?param=query1&param2=query2
         * @throws IllegalArgumentException when extra params in list is empty or is null
         **/
        public String createQueryString(String mandatoryParams) {
            return apiRequest.encodeAdditionalParams(mandatoryParams, this);
        }

        /**
         * Method to assemble a query params string
         *
         * @param mandatoryParams: mandatory params of the request as {@link Params}
         * @return query params as {@link Params} assembled es. ?param=query1&param2=query2
         * @throws IllegalArgumentException when extra params in list is empty or is null
         **/
        public String createQueryString(Params mandatoryParams) {
            return apiRequest.encodeAdditionalParams(mandatoryParams.createQueryString(), this);
        }

        /**
         * Method to assemble a body params of an {@code "HTTP"} request <br>
         * Any params required
         *
         * @return body params as {@link String} assembled es. param=mandatory1&param2=mandatory2
         * @throws IllegalArgumentException when extra params in list is empty or is null
         **/
        public String createPayload() {
            return apiRequest.encodeBodyParams(this);
        }

        /**
         * Method to assemble a body params of an {@code "HTTP"} request
         *
         * @param extraPayload: extra payload data to add
         * @return body params as {@link String} assembled es. param=mandatory1&param2=mandatory2
         * @throws IllegalArgumentException when extra params in list is empty or is null
         **/
        public String createPayload(Params extraPayload) {
            mergeParams(extraPayload);
            return apiRequest.encodeBodyParams(this);
        }

        /**
         * Method to assemble a body params of an {@code "HTTP"} request <br>
         * Any params required
         *
         * @return body params as {@link JSONObject}
         * @throws IllegalArgumentException when extra params in list is empty or is null
         **/
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
         **/
        public JSONObject createJSONPayload(Params extraPayload) {
            mergeParams(extraPayload);
            return createJSONPayload();
        }

        /**
         * Returns a string representation of the object <br>
         * Any params required
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
     **/
    public enum RequestMethod {

        /**
         * {@code "GET"} method for {@code "HTTP"} requests
         **/
        GET,

        /**
         * {@code "POST"} method for {@code "HTTP"} requests
         **/
        POST,

        /**
         * {@code "DELETE"} method for {@code "HTTP"} requests
         **/
        DELETE,

        /**
         * {@code "PUT"} method for {@code "HTTP"} requests
         **/
        PUT,

        /**
         * {@code "PATCH"} method for {@code "HTTP"} requests
         **/
        PATCH

    }

}
