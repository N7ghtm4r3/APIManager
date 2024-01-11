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

## Authors

- [@N7ghtm4r3](https://www.github.com/N7ghtm4r3)

## Support

If you need help using the library or encounter any problems or bugs, please contact us via the following links:

- Support via <a href="mailto:infotecknobitcompany@gmail.com">email</a>
- Support via <a href="https://github.com/N7ghtm4r3/APIManager/issues/new">GitHub</a>

Thank you for your help!

## Badges

[![](https://img.shields.io/badge/Google_Play-414141?style=for-the-badge&logo=google-play&logoColor=white)](https://play.google.com/store/apps/developer?id=Tecknobit)
[![Twitter](https://img.shields.io/badge/Twitter-1DA1F2?style=for-the-badge&logo=twitter&logoColor=white)](https://twitter.com/tecknobit)

[![](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white)](https://www.oracle.com/java/)

[![](https://jitpack.io/v/N7ghtm4r3/APIManager.svg)](https://jitpack.io/#N7ghtm4r3/APIManager)

## Business contact

If you need to contact me for a project 

[![](https://img.shields.io/badge/fiverr-1DBF73?style=for-the-badge&logo=fiverr&logoColor=white)](https://www.fiverr.com/manuel_maurizio)

## Donations

If you want support project and developer

| Crypto                                                                                              | Address                                        | Network  |
|-----------------------------------------------------------------------------------------------------|------------------------------------------------|----------|
| ![](https://img.shields.io/badge/Bitcoin-000000?style=for-the-badge&logo=bitcoin&logoColor=white)   | **3H3jyCzcRmnxroHthuXh22GXXSmizin2yp**         | Bitcoin  |
| ![](https://img.shields.io/badge/Ethereum-3C3C3D?style=for-the-badge&logo=Ethereum&logoColor=white) | **0x1b45bc41efeb3ed655b078f95086f25fc83345c4** | Ethereum |

If you want support project and developer with <a href="https://www.paypal.com/donate/?hosted_button_id=5QMN5UQH7LDT4">PayPal</a>

Copyright © 2024 Tecknobit
