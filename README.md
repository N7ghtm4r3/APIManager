# APIManager
**v1.0.3**

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
	implementation 'com.github.N7ghtm4r3:APIManager:1.0.3'
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
    <version>1.0.3</version>
</dependency>
```

## Tools available

- **Trading tools** allow you to calculate things like percent, forecast of an asset, etc

The other tools will be gradually released

## Usage/Examples

### No headers requests

```java
APIRequest apiRequest = new APIRequest();

try {
    apiRequest.sendAPIRequest("urlOfRequest",APIRequest.GET_METHOD);
} catch (IOException e) {
    e.printStackTrace();
}

```

### Headers requests

```java
APIRequest apiRequest = new APIRequest();

// single header requests
try {
    apiRequest.sendAPIRequest("urlOfRequest",APIRequest.GET_METHOD,"keyHeader","valueHeader");
} catch (IOException e) {
    e.printStackTrace();
}

// multiple headers requests
HashMap<String, String> headers = new HashMap<>();
headers.put("header1","value1");
headers.put("header2","value2");
APIRequest apiRequest = new APIRequest();
try {
    apiRequest.sendAPIRequest("urlOfRequest",APIRequest.GET_METHOD,headers);
} catch (IOException e) {
    e.printStackTrace();
}

```
### Responses

In this example requests haven't headers, but is the same also for that requests

- String: will return response formatted as String object

```java
try {
    apiRequest.sendAPIRequest("urlOfRequest",APIRequest.GET_METHOD);
    System.out.println(apiRequest.getResponse());
    // example output: Success request!
} catch (IOException e) {
   System.out.println(apiRequest.getErrorResponse());
}

```

- JSON: will return response formatted as JSON (JSONObject or JSONArray)

```java
try {
    apiRequest.sendAPIRequest("urlOfRequest",APIRequest.GET_METHOD);
    System.out.println(apiRequest.getJSONResponse());
    // example output: {"msg":"Success request!"}
} catch (IOException e) {
   System.out.println(apiRequest.getJSONErrorResponse());
}

```

### Errors handling

```java
try {
    System.out.println(apiRequest.sendAPIRequest("urlOfRequest",APIRequest.GET_METHOD););
} catch (Exception e) {
    System.out.println(apiRequest.getErrorResponse());
}
/* NOTE: if is not a request error will appear: "Error is not in api request, check out your code"
  and you will have to work on your code to manage error, you can customize that message*/

```
## 🛠 Skills
- Java

## Authors

- [@N7ghtm4r3](https://www.github.com/N7ghtm4r3)

## Support

For support, email infotecknobitcompany@gmail.com.

## Badges

[![](https://img.shields.io/badge/Google_Play-414141?style=for-the-badge&logo=google-play&logoColor=white)](https://play.google.com/store/apps/developer?id=Tecknobit)
[![](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white)](https://github.com/N7ghtm4r3/BinanceManager/blob/main/README.md)

[![Twitter](https://img.shields.io/twitter/url/https/twitter.com/cloudposse.svg?style=social&label=Tecknobit)](https://twitter.com/tecknobit)
[![](https://jitpack.io/v/N7ghtm4r3/APIManager.svg)](https://jitpack.io/#N7ghtm4r3/APIManager)
## Donations 

If you want support project and developer: **0x5f63cc6d13b16dcf39cd8083f21d50151efea60e**

![](https://img.shields.io/badge/Bitcoin-000000?style=for-the-badge&logo=bitcoin&logoColor=white) 
![](https://img.shields.io/badge/Ethereum-3C3C3D?style=for-the-badge&logo=Ethereum&logoColor=white)

Copyright © 2022 Tecknobit
