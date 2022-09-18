# APIManager
**v1.1.7**

This is a Java Based library useful to work with all json api services.

## Implementation

Add the JitPack repository to your build file

### Gradle

- Add it in your root build.gradle at the end of repositories

```gradle
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}
```
- Add the dependency

```gradle
dependencies {
    implementation 'com.github.N7ghtm4r3:APIManager:1.1.7'
}
```

### Maven

- Add it in your root build.gradle at the end of repositories

```xml
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>
```
- Add the dependency

```xml
<dependency>
    <groupId>com.github.N7ghtm4r3</groupId>
    <artifactId>APIManager</artifactId>
    <version>1.1.7</version>
</dependency>
```

## 🛠 Skills
- Java

## Tools available

- **Trading tool** allow you to calculate things like percent, forecast of an asset, etc
- **JsonHelper tool** allow you to fetch data from JSONObject and JSONArray with default value if not exits and create list of values in automatically and this with auto search path to get value requested, so you don't have to passing correct path by hand
- **Cryptocurrency tool** allow you to fetch details about one coin like name, index, logo image and other

The other tools will be gradually released

## Usage/Examples

### No headers requests

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
    // example output: {"msg":"Success request!"}
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
[![](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white)](https://github.com/N7ghtm4r3/APIManager/blob/main/README.md)

[![Twitter](https://img.shields.io/twitter/url/https/twitter.com/cloudposse.svg?style=social&label=Tecknobit)](https://twitter.com/tecknobit)
[![](https://jitpack.io/v/N7ghtm4r3/APIManager.svg)](https://jitpack.io/#N7ghtm4r3/APIManager)
## Donations 

If you want support project and developer: **0x5f63cc6d13b16dcf39cd8083f21d50151efea60e**

![](https://img.shields.io/badge/Bitcoin-000000?style=for-the-badge&logo=bitcoin&logoColor=white) 
![](https://img.shields.io/badge/Ethereum-3C3C3D?style=for-the-badge&logo=Ethereum&logoColor=white)

If you want support project and developer with <a href="https://www.paypal.com/donate/?hosted_button_id=5QMN5UQH7LDT4">PayPal</a>

Copyright © 2022 Tecknobit
