package com.tecknobit.apimanager.Manager;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

public class APIRequest {

    private HttpURLConnection httpURLConnection;
    private int requestTimeout;

    public APIRequest() {
        requestTimeout = 10000;
    }

    public APIRequest(int requestTimeout) {
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

    public void setRequestTimeout(int requestTimeout) {
        this.requestTimeout = requestTimeout;
    }

}
