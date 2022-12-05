## APIRequest 
### Usage/Examples

```java
APIRequest apiRequest = new APIRequest();

try {
    apiRequest.sendAPIRequest("urlOfRequest", APIRequest.GET_METHOD);
} catch (IOException e) {
    e.printStackTrace();
}
```

### Headers requests

```java
APIRequest apiRequest = new APIRequest();

// single header requests
try {
    apiRequest.sendAPIRequest("urlOfRequest", APIRequest.GET_METHOD, "keyHeader", "valueHeader");
} catch (IOException e) {
    e.printStackTrace();
}

// multiple headers requests
Headers headers = new Headers();
headers.addHeader("header1", "value1");
headers.addHeader("header2", "value2");

try {
    apiRequest.sendAPIRequest("urlOfRequest", APIRequest.GET_METHOD, headers);
} catch (IOException e) {
    e.printStackTrace();
}
```
### Responses

In this example requests haven't headers, but is the same also for that requests

- String: will return response formatted as String object

```java
try {
    apiRequest.sendAPIRequest("urlOfRequest", APIRequest.GET_METHOD);
    System.out.println(apiRequest.getResponse());
    // example output: Success request!
} catch (IOException e) {
   System.out.println(apiRequest.getErrorResponse());
}
```

- JSON: will return response formatted as JSON (JSONObject or JSONArray)

```java
try {
    apiRequest.sendAPIRequest("urlOfRequest", APIRequest.GET_METHOD);
    System.out.println(apiRequest.getJSONResponse());
    // example output: {"msg": "Success request!"}
} catch (IOException e) {
   System.out.println(apiRequest.getJSONErrorResponse());
}
```

### Errors handling

```java
try {
    System.out.println(apiRequest.sendAPIRequest("urlOfRequest", APIRequest.GET_METHOD););
} catch (Exception e) {
    System.out.println(apiRequest.getErrorResponse());
}
/* NOTE: if is not a request error will appear: "Error is not in api request, check out your code"
  and you will have to work on your code to manage error, you can customize that message*/
```

### Android's implementation

To use this library on Android you must follow two simple steps:

- Add permission to AndroidManifest file

```xml
<uses-permission android:name="android.permission.INTERNET" />
```

- Create a single one strict detector

```java
StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
StrictMode.setThreadPolicy(policy);
```