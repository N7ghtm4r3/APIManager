## RSA encryption package 

### Usage/Examples

#### create keys 

```java
//create first an RSA private key with RSAServerCipher.generateBase64PrivateKey(RSAKeyStrength); method
String PRIVATE_KEY = "key-generated";
```

```java
//create first an RSA public key with RSAServerCipher.generateBase64PublicKey(); method
String PUBLIC_KEY = "key-generated";
```

#### Client 

```java

public class Client {

    public static void main(String[] args) throws Exception {
        RSAClientCipher rsaClientCipher = new RSAClientCipher(PRIVATE_KEY, PUBLIC_KEY);
        System.out.println(rsaClientCipher.encryptBase64("your plain text"));
    }
    /*
        output: D0KXT686X2236g0EK/pwLZUi5W+CoWKCjEeOxWsMpDIjVOr5g8Ak+X7eV+DJz0sLOxFvPPwGing2l/DcvoLjp6sTquUOl0Qq5QBsY9rfYVT2Us
        xBb0vS2vSs599fFKak+lEgQjl49RHwHMpnt9wAxdL37lxWd2G5Qr0EE3LtCfxuBX96izfB8bq45aA+lPv43Zk7MwgqRSqqO7FxLItk1anO+lEM8s
        SghcVeXj7LzoYWqcuF8pn5OmFz0FaKd2VXazlDBOJwM+2C1MXKpjdgmoiZvHBM2LIp99l9E5VQt5fsJagLv8UqTvBb4MGZLIKkliScbUV64AGfRO
        2eiMv7sdC71tZb3vdL+ZsAA4d8nxArtL3ZdUyQwxCfnxIVxFP5GvyYjzXo5bhkRPCGMm1Sm7B9efUuwYTNceKeimhy594EZoWxqSVOjqZyLL3JP
        MV+1n5X7VxlnTrV6e5AAAUt9vd8Px+wS+Qmr4rWSEIeqNBYulFqA4WkCzjyya+yq/eN/jaqS9BGGxlp6x7A5H3sCQnI53JaKn9ACpvxiW6sw1s
        R+eoCWKDwMjPyGEroY/E/+TrRH5EUOJoWRjp/dc1G1xi7uNneI6VBcdekleWLxtrzxKQmjZnzhfGKACo402yT0fJ1Lxd2jvHFkFG3O+RQ6ar
        7R9lonWsxl9kvWuyozJY=
    */
}

```

#### Server 

```java

public class Server {
    
    public static void main(String[] args) throws Exception {
        RSAServerCipher rsaServerCipher = new RSAServerCipher(PRIVATE_KEY, PUBLIC_KEY);
        System.out.println(rsaServerCipher.decryptBase64("D0KXT686X2236g0EK/pwLZUi5W+CoWKCjEeOxWsMpDIjVOr5g8Ak+X7" +
                "eV+DJz0sLOxFvPPwGing2l/DcvoLjp6sTquUOl0Qq5QBsY9rfYVT2UsxBb0vS2vSs599fFKak+lEgQjl49RHwHMpnt9wAxdL37lxWd2G5" +
                "Qr0EE3LtCfxuBX96izfB8bq45aA+lPv43Zk7MwgqRSqqO7FxLItk1anO+lEM8sSghcVeXj7LzoYWqcuF8pn5OmFz0FaKd2VXazlDBOJwM+" +
                "2C1MXKpjdgmoiZvHBM2LIp99l9E5VQt5fsJagLv8UqTvBb4MGZLIKkliScbUV64AGfRO2eiMv7sdC71tZb3vdL+ZsAA4d8nxArtL3ZdUyQw" +
                "xCfnxIVxFP5GvyYjzXo5bhkRPCGMm1Sm7B9efUuwYTNceKeimhy594EZoWxqSVOjqZyLL3JPMV+1n5X7VxlnTrV6e5AAAUt9vd8Px+wS+Qm" +
                "r4rWSEIeqNBYulFqA4WkCzjyya+yq/eN/jaqS9BGGxlp6x7A5H3sCQnI53JaKn9ACpvxiW6sw1sR+eoCWKDwMjPyGEroY/E/+TrRH5EUOJo" +
                "WRjp/dc1G1xi7uNneI6VBcdekleWLxtrzxKQmjZnzhfGKACo402yT0fJ1Lxd2jvHFkFG3O+RQ6ar7R9lonWsxl9kvWuyozJY="));
    }
    //output: your plain text
}

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
