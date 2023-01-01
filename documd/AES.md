## AES encryption package 
### AES algorithms available

- **CBC**
- **CFB**
- **OFB**
- **CTR**

The other algorithms will be gradually released

### Usage/Examples

#### create keys 

```java
    //create an initialization vector with GenericServerCipher.createIvParameterSpecString(); method
    String IV_SPEC = "ciiz/UZ+oRliqYXVogTYfw==";
```

```java
  //create a secret key with GenericServerCipher.createSecretKeyString(keySize); method
  String SECRET_KEY = "ousG/n0Q7UD6bgamAxktgu3nU1jMntv3YuF1g19mb9c="; 
```

#### Client 

```java
import com.tecknobit.apimanager.apis.encryption.aes.ClientCipher.Algorithm;

public class Client {

    public static void main(String[] args) throws Exception {
        Algorithm algorithm = Algorithm.CBC_ALGORITHM;
        ClientCipher cipher = new ClientCipher(IV_SPEC, SECRET_KEY, algorithm);
        System.out.println(cipher.encryptRequest("your plain text"));
    }
    //output: 26XBx/esnnrehi/GH3tpnQ==
}

```

#### Server 

```java
import com.tecknobit.apimanager.apis.encryption.aes.serverside.GenericServerCipher.KeySize;

public class Server {
    
    //in this example will be CBC AES type
    public static void main(String[] args) throws Exception {
        KeySize keySize = KeySize.k256;
        CBCServerCipher serverCipher = new CBCServerCipher(IV_SPEC, SECRET_KEY, keySize); 
        System.out.println(cipher.decryptRequest("26XBx/esnnrehi/GH3tpnQ=="));
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

## Donations

If you want support project and developer: **0x5f63cc6d13b16dcf39cd8083f21d50151efea60e**

![](https://img.shields.io/badge/Bitcoin-000000?style=for-the-badge&logo=bitcoin&logoColor=white)
![](https://img.shields.io/badge/Ethereum-3C3C3D?style=for-the-badge&logo=Ethereum&logoColor=white)

If you want support project and developer with <a href="https://www.paypal.com/donate/?hosted_button_id=5QMN5UQH7LDT4">PayPal</a>

Copyright © 2023 Tecknobit