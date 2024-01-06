## QRCodeHelper

This API is based on the official <a href="https://github.com/zxing/zxing">zxing library</a>

### Usage/Examples

```java
public class QRCode {

    public static void main(String[] args) throws IOException, WriterException {
            QRCodeHelper qrCodeHelper = new QRCodeHelper();
            
            // This will create a file at "yourPath/file.itsSuffix" with a squared QRCode
            // You need to add the "com.google.zxing:javase" dependencies to your project to work correctly
            qrCodeHelper.createQRCode("YOUR DATA", "yourPath/file.itsSuffix", 250);
    
            // This will create a file at "yourPath/file.itsSuffix" with a custom dimensions QRCode
            // You need to add the "com.google.zxing:javase" dependencies to your project to work correctly
            qrCodeHelper.createQRCode("YOUR DATA", "yourPath/file.itsSuffix", 250, 200);
            
            // This will read a QRCode and shows its content
            qrCodeHelper.readQRCode("yourPath/file.itsSuffix");
            
            // This will read a QRCode and shows its content
            qrCodeHelper.readQRCode(new File("yourPath/file.itsSuffix"));
            
            // DEFAULT HTML PAGE
            
            // This will create and host a temporary file at "yourPath/file.itsSuffix" with a squared QRCode
            qrCodeHelper.hostQRCode(/*your_port*/,"YOUR DATA", "yourPath/file.itsSuffix", 250, false);
    
            // This will create and host a temporary file at "yourPath/file.itsSuffix" with a custom dimensions QRCode
            qrCodeHelper.hostQRCode(/*your_port*/,"YOUR DATA", "yourPath/file.itsSuffix", 250, 200, false);
    
            // CUSTOM HTMl PAGE 
               /*
                * <html>
                *    <body>
                *        <h1>Hey a custom page for my QRCode</h1>
                *        <qrcode> <!-- YOU MUST INSERT THIS TAG TO INSERT THE QRCODE CREATED -->
                *    </body>
                * </html> 
                * */
            
            // This will create and host a temporary file at "yourPath/file.itsSuffix" with a squared QRCode
            qrCodeHelper.hostQRCode(/*your_port*/,"YOUR DATA", "yourPath/file.itsSuffix", 250, false, 
                    new File("yourPath/customPage.html"));
    
            // This will create and host a temporary file at "yourPath/file.itsSuffix" with a custom dimensions QRCode
            qrCodeHelper.hostQRCode(/*your_port*/,"YOUR DATA", "yourPath/file.itsSuffix", 250, 200, false, 
                    new File("yourPath/customPage.html"));
            
            // This will stop the single running host 
            qrCodeHelper.stopHosting();
            
            // This will stop the running host on "your_port"
            qrCodeHelper.stopHostingOn(/*your_port*/);
            
            // This will stop all the running hosts
            qrCodeHelper.stopAllHosting();
            
        }
        
}
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

Copyright © 2023 Tecknobit
