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