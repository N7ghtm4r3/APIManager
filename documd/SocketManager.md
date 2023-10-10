## SocketManager

For encryption are available the following algorithms:
<ul>
    <li>
        <b>AES</b>
    </li>
    <li>
        <b>RSA</b>
    </li>
</ul>

The other algorithms will be gradually released

### Usage/Examples

#### Single listener

###### Client 

```java

public class Client {

    public static void main(String[] args) throws Exception {
        
        //NOT ENCRYPTED the communication will not be automatically encrypted
        
        SocketManager client = new SocketManager("localhost", 1000);
        
        //ENCRYPTED -- the communication will be automatically encrypted with the keys and the algorithm choosen
                
        AESSocketManager client = new AESSocketManager("localhost", 1000, new AESClientCipher("ivSpec", "secretKey", algorithm));
        
        RSASocketManager client = new RSASocketManager("localhost", 1000, new RSAClientCipher("privateKey", "publicKey"));
        
        // will start the communication sending the content message
        client.writeContent("makeSomething"); 
        
        // will end the communication reading the response content message
        System.out.println(client.readContent()); // --> executed
        
    }
    
}

```

###### Server 

```java

public class Server {
    
    public static void main(String[] args) throws Exception {
        
        //NOT ENCRYPTED the communication will not be automatically encrypted
        
        // set to false to allow only a single listener
        SocketManager server = new SocketManager(false); 
        
        //ENCRYPTED -- the communication will be automatically encrypted with the keys and the algorithm choosen
                
        AESSocketManager server = new AESSocketManager(false, new AESClientCipher("ivSpec", "secretKey", algorithm));
        
        RSASocketManager server = new RSASocketManager(false, new RSAClientCipher("privateKey", "publicKey"));
        
        // set the default error response
        socketManager.setDefaultErrorResponse(SocketManager.StandardResponseCode.FAILED);  
        
        // set the default success response
        socketManager.setDefaultSuccessResponse(SocketManager.StandardResponseCode.SUCCESSFUL);
        
        server.startListener(1000, new Runnable() {
            @Override
            public void run() {
                // example of routine
                while (socketManager.continueListening()) {
                    try {
                        // mandatory to accept the request and work on
                        server.acceptRequest(); 
                        
                        // will start the communication reading the content message
                        String request = server.readContent();
                        if (request != null) {
                            if(request.equals("makeSomething")){// will end the communication sending the response content message
                                socketManager.writeContent("executed");
                                //or
                                socketManager.sendSuccessResponse();
                            } else {
                                socketManager.sendDefaultErrorResponse();
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
    
}

```

#### Multiple listeners

###### Client 

```java

public class Client {

    public static void main(String[] args) throws Exception {
        
        //NOT ENCRYPTED the communication will not be automatically encrypted
                
        SocketManager client = new SocketManager("localhost", 1000);
        
        //ENCRYPTED -- the communication will be automatically encrypted with the keys inserted
                        
        AESSocketManager client = new AESSocketManager("localhost", 1000, new AESClientCipher("ivSpec", "secretKey", algorithm));
        
        RSASocketManager client = new RSASocketManager("localhost", 1000, new RSAClientCipher("privateKey", "publicKey"));
        
        // will start the communication with the listener with 1000 as port, 
        // sending the content message
        client.writeContent("makeSomething"); 
        
        // will end the communication reading the response content message
        System.out.println(client.readContent()); // --> executedFrom1000
        
        // will start the communication with the listener with 1001 as port, 
        // sending the content message
        client.writeContentTo(1001, "makeSomething"); 
        
        // will end the communication reading the response content message
        System.out.println(client.readContent()); // --> executedFrom1001
    }
    
}

```

###### Server 

```java

public class Server {
    
    public static void main(String[] args) throws Exception {
        
        //NOT ENCRYPTED the communication will not be automatically encrypted
        
        // set to true to allow a multiple listeners
        SocketManager server = new SocketManager(true);
        
        //ENCRYPTED -- the communication will be automatically encrypted with the keys inserted
                
        AESSocketManager server = new AESSocketManager(true, new AESClientCipher("ivSpec", "secretKey", algorithm));
        
        RSASocketManager server = new RSASocketManager(false, new RSAClientCipher("privateKey", "publicKey"));
        
        // set the default error response
        socketManager.setDefaultErrorResponse(SocketManager.StandardResponseCode.FAILED);  
        
        // set the default success response
        socketManager.setDefaultSuccessResponse(SocketManager.StandardResponseCode.SUCCESSFUL);
        
        server.startListener(1000, new Runnable() {
            @Override
            public void run() {
                // example of routine
                while (socketManager.continueListeningOn(1000)) {
                    try {
                        // mandatory to accept the request and work on
                        server.acceptRequestOn(1000); // port where accept the requests 
                        
                        // will start the communication reading the content message
                        String request = server.readContent();
                        if (request != null) {
                            if(request.equals("makeSomething")){// will end the communication sending the response content message
                                    socketManager.writeContent("executedFrom1000");
                                    //or
                                    socketManager.sendSuccessResponse();
                            } else {
                                socketManager.sendDefaultErrorResponse();
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        
        // for all the different listeners you must change the service's port  
        server.startListener(1001, new Runnable() {
            @Override
            public void run() {
                // example of routine
                while (socketManager.continueListeningOn(1001)) {
                    try {
                        // mandatory to accept the request and work on
                        server.acceptRequestOn(1001); // port where accept the requests 
                        
                        // will start the communication reading the content message
                        String request = server.readContent();
                        if (request != null) {
                            if(request.equals("makeSomething")){// will end the communication sending the response content message
                                socketManager.writeContent("executedFrom1001");
                                //or
                                socketManager.sendSuccessResponse();
                            } else {
                                socketManager.sendDefaultErrorResponse();
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
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

If you want support project and developer: **0x5f63cc6d13b16dcf39cd8083f21d50151efea60e**

![](https://img.shields.io/badge/Bitcoin-000000?style=for-the-badge&logo=bitcoin&logoColor=white)
![](https://img.shields.io/badge/Ethereum-3C3C3D?style=for-the-badge&logo=Ethereum&logoColor=white)

If you want support project and developer with <a href="https://www.paypal.com/donate/?hosted_button_id=5QMN5UQH7LDT4">PayPal</a>

Copyright © 2023 Tecknobit
