package com.tecknobit.apimanager.Manager;

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

public class APIRequest {

    private HttpURLConnection httpURLConnection;
    private String errorResponse;
    private int requestTimeout;

    public APIRequest() {
        requestTimeout = 10000;
    }

    public APIRequest(int requestTimeout) {
        this.requestTimeout = requestTimeout;
    }

    public void setRequestTimeout(int requestTimeout) {
        this.requestTimeout = requestTimeout;
    }

    public void sendAPIRequest(String requestUrl, String method) throws IOException {
        setRequestConnection(requestUrl, method);
        httpURLConnection.connect();
    }

    public void sendAPIRequest(String requestUrl, String method, String headerKey, String headerValue) throws IOException {
        setRequestConnection(requestUrl, method);
        httpURLConnection.setRequestProperty(headerKey, headerValue);
        httpURLConnection.connect();
    }

    public void sendAPIRequest(String requestUrl, String method, HashMap<String,String> headers) throws IOException {
        setRequestConnection(requestUrl, method);
        for (String key : headers.keySet())
            httpURLConnection.setRequestProperty(key, headers.get(key));
        httpURLConnection.connect();
    }

    private void setRequestConnection(String requestUrl, String method) throws IOException {
        httpURLConnection = (HttpURLConnection) new URL(requestUrl).openConnection();
        httpURLConnection.setRequestMethod(method);
        httpURLConnection.setConnectTimeout(requestTimeout);
    }

    public String getRequestResponse() throws IOException {
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
        if(isInError) {
            errorResponse = stringBuilder.toString();
            return errorResponse;
        }
        return stringBuilder.toString();
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

    public String assembleAdditionalParams(String mandatoryParams, HashMap<String, String> extraParams){
        StringBuilder params = new StringBuilder(mandatoryParams);
        for (String key : extraParams.keySet())
            params.append("&").append(key).append("=").append(extraParams.get(key));
        return params.toString();
    }

    public String getErrorResponse() {
        return errorResponse;
    }

}
