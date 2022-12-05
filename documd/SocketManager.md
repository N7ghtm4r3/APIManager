## SocketManager
### Usage/Examples

#### Single listener

###### Client 

```java

public class Client {

    public static void main(String[] args) throws Exception {
        SocketManager client = new SocketManager("localhost", 1000);
        
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
        // set to false to allow only a single listener
        SocketManager server = new SocketManager(false); 
        server.startListener(1000, new Runnable() {
            @Override
            public void run() {
                // example of routine
                while (true) {
                    try {
                        // mandatory to accept the request and work on
                        server.acceptRequest(); 
                        
                        // will start the communication reading the content message
                        String request = server.readContent();
                        if (request != null) {
                            switch (request) {
                                case "makeSomething":
                                     // will end the communication sending the response content message
                                    socketManager.writeContent("executed");
                                    break;
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
        SocketManager client = new SocketManager("localhost", 1000);
        
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
        // set to true to allow a multiple listeners
        SocketManager server = new SocketManager(false);
        server.startListener(1000, new Runnable() {
            @Override
            public void run() {
                // example of routine
                while (true) {
                    try {
                        // mandatory to accept the request and work on
                        server.acceptRequest(1000); // port where accept the requests 
                        
                        // will start the communication reading the content message
                        String request = server.readContent();
                        if (request != null) {
                            switch (request) {
                                case "makeSomething":
                                     // will end the communication sending the response content message
                                    socketManager.writeContent("executedFrom1000");
                                    break;
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
                while (true) {
                    try {
                        // mandatory to accept the request and work on
                        server.acceptRequest(1001); // port where accept the requests 
                        
                        // will start the communication reading the content message
                        String request = server.readContent();
                        if (request != null) {
                            switch (request) {
                                case "makeSomething":
                                     // will end the communication sending the response content message
                                    socketManager.writeContent("executedFrom1001");
                                    break;
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