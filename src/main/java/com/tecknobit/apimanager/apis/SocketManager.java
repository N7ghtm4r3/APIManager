package com.tecknobit.apimanager.apis;

import com.tecknobit.apimanager.annotations.Wrapper;
import com.tecknobit.apimanager.apis.encryption.aes.ClientCipher;
import com.tecknobit.apimanager.apis.encryption.aes.ClientCipher.Algorithm;
import org.json.JSONObject;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.tecknobit.apimanager.apis.APIRequest.RequestMethod.GET;
import static com.tecknobit.apimanager.apis.encryption.aes.ClientCipher.createIvParameter;
import static com.tecknobit.apimanager.apis.encryption.aes.ClientCipher.createSecretKey;

/**
 * The {@code SocketManager} class is useful to dynamically manage communication by socket
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the usage at <a href="https://github.com/N7ghtm4r3/APIManager/blob/main/documd/SocketManager.md">SocketManager.md</a>
 * @since 2.0.2
 **/
public class SocketManager {

    /**
     * {@code NEW_LINE_REPLACER} is the constants which indicates the replacer to use when the content message contains
     * multiple lines
     **/
    private static final String NEW_LINE_REPLACER = "@-/-/-@";

    /**
     * {@code listeners} list of listeners to use if the {@link #allowMultipleListeners} flag is set to {@code "true"}
     **/
    private final ConcurrentHashMap<Integer, Listener> listeners;

    /**
     * {@code allowMultipleListeners} whether accept multiple listeners at the same time
     **/
    private final boolean allowMultipleListeners;

    /**
     * {@code "executor"} of the routines
     **/
    private final ExecutorService executor;

    /**
     * {@code serverUse} whether the runtime use of this class is server side or not
     **/
    private final boolean serverUse;

    /**
     * {@code serverSocket} socket managed by the server
     **/
    private ServerSocket serverSocket;

    /**
     * {@code cipher} instance to cipher the communication if enabled
     **/
    private ClientCipher cipher;

    /**
     * {@code currentServerPort} current server port used in the communication
     **/
    private int currentServerPort;

    /**
     * {@code currentHost} current server host used in the communication
     **/
    private final String currentHost;

    /**
     * {@code socket} socket used in the communication
     **/
    private Socket socket;

    /**
     * {@code publicHostAddress} public host address
     **/
    private String publicHostAddress;

    /**
     * {@code continueSingleRoutine} whether continue the routine for the single listener if {@link #allowMultipleListeners}
     * is set to {@code "false"}
     **/
    private volatile boolean continueSingleRoutine;

    /**
     * {@code defaultErrorResponse} default error response
     **/
    private String defaultErrorResponse;

    /**
     * {@code defaultSuccessResponse} default successful response
     **/
    private String defaultSuccessResponse;

    /**
     * {@code lastContentRed} last content red with {@link #readContent()} or {@link #readContent(Socket)}
     **/
    private String lastContentRed;

    /**
     * Constructor to init {@link SocketManager}
     *
     * @param host:       server host used in the communication
     * @param serverPort: server port used in the communication
     * @param ivSpec:     initialization vector as {@link String}
     * @param secretKey:  secret key used in the {@link Cipher} as {@link String}
     * @param algorithm:  algorithm used for AES cipher
     * @throws Exception when an error occurred
     * @apiNote this will set {@link #serverUse} to {@code "false"} and will be use as client side
     **/
    public SocketManager(String host, int serverPort, String ivSpec, String secretKey,
                         Algorithm algorithm) throws Exception {
        this(host, serverPort, new ClientCipher(ivSpec, secretKey, algorithm));
    }

    /**
     * Constructor to init {@link SocketManager}
     *
     * @param host:       server host used in the communication
     * @param serverPort: server port used in the communication
     * @param ivSpec:     initialization vector as {@link IvParameterSpec}
     * @param secretKey:  secret key used in the {@link Cipher} as {@link SecretKey}
     * @param algorithm:  algorithm used for AES cipher
     * @throws Exception when an error occurred
     * @apiNote this will set {@link #serverUse} to {@code "false"} and will be use as client side
     **/
    public SocketManager(String host, int serverPort, IvParameterSpec ivSpec, SecretKey secretKey,
                         Algorithm algorithm) throws Exception {
        this(host, serverPort, new ClientCipher(ivSpec, secretKey, algorithm));
    }

    /**
     * Constructor to init {@link SocketManager}
     *
     * @param host:       server host used in the communication
     * @param serverPort: server port used in the communication
     * @apiNote this will set {@link #serverUse} to {@code "false"} and will be use as client side
     **/
    public SocketManager(String host, int serverPort) {
        this(host, serverPort, null);
    }

    /**
     * Constructor to init {@link SocketManager}
     *
     * @param host:       server host used in the communication
     * @param serverPort: server port used in the communication
     * @param cipher:     cipher to cipher the communication
     * @apiNote this will set {@link #serverUse} to {@code "false"} and will be use as client side
     **/
    public SocketManager(String host, int serverPort, ClientCipher cipher) {
        serverUse = false;
        currentHost = host;
        currentServerPort = serverPort;
        allowMultipleListeners = false;
        listeners = null;
        executor = null;
        this.cipher = cipher;
    }

    /**
     * Constructor to init {@link SocketManager}
     *
     * @param allowMultipleListeners: whether accept multiple listeners at the same time
     * @param ivSpec:                 initialization vector as {@link String}
     * @param secretKey:              secret key used in the {@link Cipher} as {@link String}
     * @param algorithm:              algorithm used for AES cipher
     * @throws Exception when an error occurred
     * @apiNote this will set {@link #serverUse} to {@code "true"} and will be use as server side
     **/
    public SocketManager(boolean allowMultipleListeners, String ivSpec, String secretKey,
                         Algorithm algorithm) throws Exception {
        this(allowMultipleListeners, new ClientCipher(ivSpec, secretKey, algorithm));
    }

    /**
     * Constructor to init {@link SocketManager}
     *
     * @param allowMultipleListeners: whether accept multiple listeners at the same time
     * @param ivSpec:                 initialization vector as {@link IvParameterSpec}
     * @param secretKey:              secret key used in the {@link Cipher} as {@link SecretKey}
     * @param algorithm:              algorithm used for AES cipher
     * @throws Exception when an error occurred
     * @apiNote this will set {@link #serverUse} to {@code "true"} and will be use as server side
     **/
    public SocketManager(boolean allowMultipleListeners, IvParameterSpec ivSpec, SecretKey secretKey,
                         Algorithm algorithm) throws Exception {
        this(allowMultipleListeners, new ClientCipher(ivSpec, secretKey, algorithm));
    }

    /**
     * Constructor to init {@link SocketManager}
     *
     * @param allowMultipleListeners: whether accept multiple listeners at the same time
     * @throws UnknownHostException when an error occurred
     * @apiNote this will set {@link #serverUse} to {@code "true"} and will be use as server side
     **/
    public SocketManager(boolean allowMultipleListeners) throws UnknownHostException {
        this(allowMultipleListeners, null);
    }

    /**
     * Constructor to init {@link SocketManager}
     *
     * @param allowMultipleListeners: whether accept multiple listeners at the same time
     * @param cipher:                 cipher to cipher the communication
     * @throws UnknownHostException when an error occurred
     * @apiNote this will set {@link #serverUse} to {@code "true"} and will be use as server side
     **/
    public SocketManager(boolean allowMultipleListeners, ClientCipher cipher) throws UnknownHostException {
        serverUse = true;
        this.allowMultipleListeners = allowMultipleListeners;
        currentHost = InetAddress.getLocalHost().getHostAddress();
        if (allowMultipleListeners) {
            executor = Executors.newCachedThreadPool();
            listeners = new ConcurrentHashMap<>();
            continueSingleRoutine = false;
        } else {
            executor = Executors.newFixedThreadPool(1);
            listeners = null;
        }
        this.cipher = cipher;
    }

    /**
     * Method to start a new listener
     *
     * @param port:    port to use by the listener
     * @param routine: routine to execute by the listener
     * @apiNote this method will be executed only if the {@link #serverUse} is set to {@code "true"}
     * @throws IOException when some errors have been occurred, for example same port for different listeners
     **/
    public void startListener(int port, Runnable routine) throws IOException {
        if (serverUse) {
            if (serverSocket == null || currentServerPort != port) {
                if (allowMultipleListeners) {
                    if (!listeners.containsKey(port))
                        listeners.put(port, new Listener(new ServerSocket(port), true));
                    else
                        exit("You cannot have more listeners on the same port: [" + port + "]");
                } else {
                    if (serverSocket == null) {
                        serverSocket = new ServerSocket(port);
                        currentServerPort = port;
                        continueSingleRoutine = true;
                    } else {
                        exit("If the \"allowMultipleListeners\" flag is set to false you cannot use multiple " +
                                "listeners at the same time");
                    }
                }
            }
            executor.execute(routine);
        }
    }

    /**
     * Method to accept a new socket request <br>
     * Any params required
     *
     * @return socket accepted as {@link Socket}
     * @apiNote this method will be executed only if the {@link #serverUse} is set to {@code "true"}
     * @implSpec this method need to be invoked when {@link #allowMultipleListeners} is set to {@code "false"}
     * @throws IOException when some errors have been occurred
     **/
    @Wrapper
    public Socket acceptRequest() throws IOException {
        return acceptRequestOn(currentServerPort);
    }

    /**
     * Method to accept a new socket request
     *
     * @param port: port of the server socket where this request must be accepted and routed
     * @return socket accepted as {@link Socket}
     *
     * @apiNote this method will be executed only if the {@link #serverUse} is set to {@code "true"}
     * @implSpec this method need to be invoked when {@link #allowMultipleListeners} is set to {@code "true"}
     * @throws IOException when some errors have been occurred
     **/
    public Socket acceptRequestOn(int port) throws IOException {
        if (serverUse) {
            if (allowMultipleListeners) {
                if (listeners.size() == 1)
                    port = listeners.keys().nextElement();
                serverSocket = listeners.get(port).getServerSocket();
            }
            if (!serverSocket.isClosed())
                return socket = serverSocket.accept();
            else
                executor.shutdown();
        }
        return null;
    }

    /**
     * Method to set the default error response to send as error
     *
     * @param defaultErrorResponse: default error response to send as error
     * @apiNote any object will be accepted, but will be set {@link #defaultErrorResponse} as {@link String} calling
     * their {@code "toString()"} method
     **/
    public <T> void setDefaultErrorResponse(T defaultErrorResponse) {
        this.defaultErrorResponse = defaultErrorResponse.toString();
    }

    /**
     * Method to send the {@link #defaultErrorResponse} <br>
     * Any params required
     *
     * @throws Exception when some errors have been occurred
     **/
    public void sendDefaultErrorResponse() throws Exception {
        writeContent(defaultErrorResponse);
    }

    /**
     * Method to send the {@link #defaultErrorResponse}
     *
     * @param port: port of the server socket where this request must be accepted and routed
     * @throws Exception when some errors have been occurred
     **/
    public void sendDefaultErrorResponseTo(int port) throws Exception {
        writeContentTo(port, defaultErrorResponse);
    }

    /**
     * Method to send the {@link #defaultErrorResponse}
     *
     * @param host: host of the server socket where this request must be accepted and routed
     * @throws Exception when some errors have been occurred
     **/
    public void sendDefaultErrorResponseTo(String host) throws Exception {
        writeContentTo(host, defaultErrorResponse);
    }

    /**
     * Method to send the {@link #defaultErrorResponse}
     *
     * @param port: port of the server socket where this request must be accepted and routed
     * @param host: host of the server socket where this request must be accepted and routed
     * @throws Exception when some errors have been occurred
     **/
    public void sendDefaultErrorResponseTo(String host, int port) throws Exception {
        writeContentTo(host, port, defaultErrorResponse);
    }

    /**
     * Method to set the default successful response to send as success
     *
     * @param defaultSuccessResponse: default successful response to send as success
     * @apiNote any object will be accepted, but will be set {@link #defaultSuccessResponse} as {@link String} calling
     * their {@code "toString()"} method
     **/
    public <T> void setDefaultSuccessResponse(T defaultSuccessResponse) {
        this.defaultSuccessResponse = defaultSuccessResponse.toString();
    }

    /**
     * Method to send the {@link #defaultSuccessResponse} <br>
     * Any params required
     *
     * @throws Exception when some errors have been occurred
     **/
    public void sendSuccessResponse() throws Exception {
        writeContent(defaultSuccessResponse);
    }

    /**
     * Method to send the {@link #defaultSuccessResponse}
     *
     * @param port: port of the server socket where this request must be accepted and routed
     * @throws Exception when some errors have been occurred
     **/
    public void sendSuccessResponseTo(int port) throws Exception {
        writeContentTo(port, defaultSuccessResponse);
    }

    /**
     * Method to send the {@link #defaultSuccessResponse}
     *
     * @param host: host of the server socket where this request must be accepted and routed
     * @throws Exception when some errors have been occurred
     **/
    public void sendSuccessResponseTo(String host) throws Exception {
        writeContentTo(host, defaultSuccessResponse);
    }

    /**
     * Method to send the {@link #defaultSuccessResponse}
     *
     * @param port: port of the server socket where this request must be accepted and routed
     * @param host: host of the server socket where this request must be accepted and routed
     * @throws Exception when some errors have been occurred
     **/
    public void sendSuccessResponseTo(String host, int port) throws Exception {
        writeContentTo(host, port, defaultSuccessResponse);
    }

    /**
     * Method to write a content message to send with the socket request
     *
     * @param content: content message to send
     * @throws Exception when some errors have been occurred
     * @apiNote will be accepted any objects, but will be called their {@code "toString()"}'s method to be sent
     **/
    @Wrapper
    public <T> void writeContent(T content) throws Exception {
        writeContentTo(currentHost, currentServerPort, content);
    }

    /**
     * Method to write a content message to send with the socket request
     *
     * @param content: content message to send
     * @param port:    port of the server socket where this request must be accepted and routed
     * @throws Exception when some errors have been occurred
     * @apiNote will be accepted any objects, but will be called their {@code "toString()"}'s method to be sent
     **/
    @Wrapper
    public <T> void writeContentTo(int port, T content) throws Exception {
        writeContentTo(currentHost, port, content);
    }

    /**
     * Method to write a content message to send with the socket request
     *
     * @param host:    host of the server socket where this request must be accepted and routed
     * @param content: content message to send
     * @throws Exception when some errors have been occurred
     * @apiNote will be accepted any objects, but will be called their {@code "toString()"}'s method to be sent
     **/
    @Wrapper
    public <T> void writeContentTo(String host, T content) throws Exception {
        writeContentTo(host, currentServerPort, content);
    }

    /**
     * Method to write a content message to send with the socket request
     *
     * @param host:    host of the server socket where this request must be accepted and routed
     * @param content: content message to send
     * @param port:    port of the server socket where this request must be accepted and routed
     * @throws Exception when some errors have been occurred
     * @apiNote will be accepted any objects, but will be called their {@code "toString()"}'s method to be sent
     **/
    @Wrapper
    public <T> void writeContentTo(String host, int port, T content) throws Exception {
        if (!serverUse)
            socket = new Socket(host, port);
        writeContentTo(socket, content);
    }

    /**
     * Method to write a content message to send with the socket request
     *
     * @param targetSocket: target socket to use to send the content message
     * @param content:      content message to send
     * @throws Exception when some errors have been occurred
     * @apiNote will be accepted any objects, but will be called their {@code "toString()"}'s method to be sent
     **/
    public <T> void writeContentTo(Socket targetSocket, T content) throws Exception {
        String message = content.toString();
        if (message.contains(NEW_LINE_REPLACER)) {
            socket.close();
            exit("\"@-/-/-@\" is a reserved char, please do not insert it");
        }
        if (cipher != null)
            message = cipher.encrypt(message.replaceAll("\n", NEW_LINE_REPLACER));
        writePlainContentTo(targetSocket, message);
    }

    /**
     * Method to write a plain content message to send with the socket request
     *
     * @param content: content message to send
     * @throws Exception when some errors have been occurred
     * @apiNote will be accepted any objects, but will be called their {@code "toString()"}'s method to be sent
     **/
    @Wrapper
    public <T> void writePlainContent(T content) throws Exception {
        writePlainContentTo(currentHost, currentServerPort, content);
    }

    /**
     * Method to write a plain content message to send with the socket request
     *
     * @param content: content message to send
     * @param port:    port of the server socket where this request must be accepted and routed
     * @throws Exception when some errors have been occurred
     * @apiNote will be accepted any objects, but will be called their {@code "toString()"}'s method to be sent
     **/
    @Wrapper
    public <T> void writePlainContentTo(int port, T content) throws Exception {
        writePlainContentTo(currentHost, port, content);
    }

    /**
     * Method to write a plain content message to send with the socket request
     *
     * @param host:    host of the server socket where this request must be accepted and routed
     * @param content: content message to send
     * @throws Exception when some errors have been occurred
     * @apiNote will be accepted any objects, but will be called their {@code "toString()"}'s method to be sent
     **/
    @Wrapper
    public <T> void writePlainContentTo(String host, T content) throws Exception {
        writePlainContentTo(host, currentServerPort, content);
    }

    /**
     * Method to write a plain content message to send with the socket request
     *
     * @param host:    host of the server socket where this request must be accepted and routed
     * @param content: content message to send
     * @param port:    port of the server socket where this request must be accepted and routed
     * @throws Exception when some errors have been occurred
     * @apiNote will be accepted any objects, but will be called their {@code "toString()"}'s method to be sent
     **/
    @Wrapper
    public <T> void writePlainContentTo(String host, int port, T content) throws Exception {
        if (!serverUse)
            socket = new Socket(host, port);
        writePlainContentTo(socket, content);
    }

    /**
     * Method to write a plain content message to send with the socket request
     *
     * @param targetSocket: target socket to use to send the content message
     * @param content:      content message to send
     * @throws Exception when some errors have been occurred
     * @apiNote will be accepted any objects, but will be called their {@code "toString()"}'s method to be sent
     **/
    public <T> void writePlainContentTo(Socket targetSocket, T content) throws Exception {
        PrintWriter printWriter = new PrintWriter(targetSocket.getOutputStream(), true);
        String message = content.toString();
        if (message.contains(NEW_LINE_REPLACER)) {
            socket.close();
            exit("\"@-/-/-@\" is a reserved char, please do not insert it");
        }
        printWriter.println(message);
    }

    /**
     * Method to read a content message received with the socket request <br>
     * Any params required
     *
     * @return content message received as {@link String}
     * @throws Exception when some errors have been occurred
     **/
    @Wrapper
    public String readContent() throws Exception {
        return readContent(socket);
    }

    /**
     * Method to read a content message received with the socket request
     *
     * @param targetSocket: target socket to use to receive the content message
     * @return content message received as {@link String}
     * @throws Exception when some errors have been occurred
     **/
    public String readContent(Socket targetSocket) throws Exception {
        String content = new BufferedReader(new InputStreamReader(targetSocket.getInputStream())).readLine();
        lastContentRed = content;
        if (cipher != null)
            content = cipher.decrypt(content);
        if (!serverUse || content == null)
            targetSocket.close();
        if (content != null)
            content = content.replaceAll(NEW_LINE_REPLACER, "\n");
        return content;
    }

    /**
     * Method to read the last content message red in the stream with the socket request <br>
     * Any params required
     *
     * @return last content message red as {@link String}
     * @throws Exception when some errors have been occurred
     **/
    public String readLastContent() throws Exception {
        if (cipher == null)
            return lastContentRed;
        else
            return cipher.decrypt(lastContentRed);
    }

    /**
     * Method to get if the listener's workflow needs to continue or not <br>
     * Any param required
     *
     * @implNote example of use case:
     * <pre>
     *     {@code
     *          SocketManager socketManager = new SocketManager(false);
     *          socketManager.startListener(3218, new Runnable() {
     *             @Override
     *             public void run() {
     *                 while (socketManager.continueListening()) {
     *                     // do listener's workflow
     *                 }
     *                 // listener's workflow when has been stopped
     *             }
     *         });
     *     }
     * </pre>
     * @apiNote this method need to be invoked when {@link #allowMultipleListeners} is set to {@code "false"}
     **/
    @Wrapper
    public boolean continueListening() {
        return continueListeningOn(currentServerPort);
    }

    /**
     * Method to get if the listener's workflow needs to continue or not
     *
     * @param port: port of the listener from fetch the information about continuation of its workflow
     * @implNote example of use case:
     * <pre>
     *     {@code
     *          SocketManager socketManager = new SocketManager(true);
     *          socketManager.startListener(3218, new Runnable() {
     *             @Override
     *             public void run() {
     *                 while (socketManager.continueListeningOn(3218)) {
     *                     // do listener's workflow
     *                 }
     *                 // listener's workflow when has been stopped
     *             }
     *         });
     *
     *         socketManager.startListener(8123, new Runnable() {
     *            @Override
     *            public void run() {
     *                while (socketManager.continueListeningOn(8123)) {
     *                    // do listener's workflow
     *                }
     *                // listener's workflow when has been stopped
     *            }
     *        });
     *     }
     * </pre>
     * <b>Note</b>: if listener has been stopped will be removed from the {@link #listeners} list
     * @apiNote this method need to be invoked when {@link #allowMultipleListeners} is set to {@code "true"}
     **/
    public boolean continueListeningOn(int port) {
        if (allowMultipleListeners) {
            boolean continueListening = listeners.get(port).continueRoutine();
            if (!continueListening)
                listeners.remove(port);
            return continueListening;
        } else
            return continueSingleRoutine;
    }

    /**
     * Method to stop the listener's workflow
     * Any param required
     *
     * <pre>
     *     {@code
     *          SocketManager socketManager = new SocketManager(false);
     *          socketManager.startListener(3218, new Runnable() {
     *             @Override
     *             public void run() {
     *                 while (socketManager.continueListening()) {
     *                     // do listener's workflow
     *                 }
     *                 // listener's workflow when has been stopped
     *             }
     *         });
     *
     *         // your code's workflow
     *
     *         socketManager.stopListener(); // --> the single listener will be stopped
     *     }
     * </pre>
     *
     * @apiNote this method need to be invoked when {@link #allowMultipleListeners} is set to {@code "false"}
     **/
    @Wrapper
    public void stopListener() {
        stopListenerOn(currentServerPort);
    }

    /**
     * Method to stop the listener's workflow
     *
     * @param port: port of the listener to stop its workflow
     * @implNote example of use case:
     * <pre>
     *     {@code
     *          SocketManager socketManager = new SocketManager(true);
     *          socketManager.startListener(3218, new Runnable() {
     *             @Override
     *             public void run() {
     *                 while (socketManager.continueListeningOn(3218)) {
     *                     // do listener's workflow
     *                 }
     *                 // listener's workflow when has been stopped
     *             }
     *         });
     *
     *         socketManager.startListener(8123, new Runnable() {
     *            @Override
     *            public void run() {
     *                while (socketManager.continueListeningOn(8123)) {
     *                    // do listener's workflow
     *                }
     *                // listener's workflow when has been stopped
     *            }
     *        });
     *
     *         // your code's workflow
     *
     *         socketManager.stopListenerOn(3218);
     *        // the listener on the port '3218' will be stopped,
     *        // while the others listener will continue with
     *        //  their workflow
     *     }
     * </pre>
     * @apiNote this method need to be invoked when {@link #allowMultipleListeners} is set to {@code "true"}
     **/
    public void stopListenerOn(int port) {
        if (allowMultipleListeners)
            listeners.get(port).stopRoutine();
        else {
            continueSingleRoutine = false;
            executor.shutdownNow();
        }
    }

    /**
     * Method to stop the listener's workflow <br>
     * Any params required
     *
     * @implNote example of use case:
     * <pre>
     *     {@code
     *          SocketManager socketManager = new SocketManager(true);
     *          socketManager.startListener(3218, new Runnable() {
     *             @Override
     *             public void run() {
     *                 while (socketManager.continueListeningOn(3218)) {
     *                     // do listener's workflow
     *                 }
     *                 // listener's workflow when has been stopped
     *             }
     *         });
     *
     *         socketManager.startListener(8123, new Runnable() {
     *            @Override
     *            public void run() {
     *                while (socketManager.continueListeningOn(8123)) {
     *                    // do listener's workflow
     *                }
     *                // listener's workflow when has been stopped
     *            }
     *        });
     *
     *         // your code's workflow
     *
     *         socketManager.stopAllListeners();
     *         // all the listeners will be stopped at the same time
     *     }
     * </pre>
     * @apiNote this method need to be invoked when {@link #allowMultipleListeners} is set to {@code "true"}
     **/
    public void stopAllListeners() {
        if (allowMultipleListeners) {
            for (Listener listener : listeners.values())
                listener.stopRoutine();
            executor.shutdownNow();
        }
    }

    /**
     * Method to print an error occurred and exit with status code <b>1</b>
     *
     * @param errorMessage: error message to print
     **/
    private void exit(String errorMessage) {
        System.err.println(errorMessage);
        System.exit(1);
    }

    /**
     * Method to get {@link #listeners} instance <br>
     * Any params required
     *
     * @return {@link #listeners} instance as {@link ConcurrentHashMap} of {@link Listener}
     **/
    public ConcurrentHashMap<Integer, Listener> getListeners() {
        return listeners;
    }

    /**
     * Method to get {@link #allowMultipleListeners} instance <br>
     * Any params required
     *
     * @return {@link #allowMultipleListeners} instance as boolean
     **/
    public boolean areAllowedMultipleListeners() {
        return allowMultipleListeners;
    }

    /**
     * Method to get {@link #executor} instance <br>
     * Any params required
     *
     * @return {@link #executor} instance as {@link ExecutorService}
     **/
    public ExecutorService getExecutor() {
        return executor;
    }

    /**
     * Method to get {@link #serverUse} instance <br>
     * Any params required
     *
     * @return {@link #serverUse} instance as boolean
     **/
    public boolean isServerUse() {
        return serverUse;
    }

    /**
     * Method to get {@link #serverSocket} instance <br>
     * Any params required
     *
     * @return {@link #serverSocket} instance as {@link ServerSocket}
     **/
    public ServerSocket getActiveServerSocket() {
        return serverSocket;
    }

    /**
     * Method to get {@link #currentServerPort} instance <br>
     * Any params required
     *
     * @return {@link #currentServerPort} instance as int
     **/
    public int getCurrentServerPort() {
        return currentServerPort;
    }

    /**
     * Method to get host where the server is running
     *
     * @param publicAddress: whether to get the public address or just the local address
     * @return host as {@link String}
     * @implNote if {@code "publicAddress"} is set to {@code "true"} will be called the <a href="https://www.ipify.org/">
     * ipify API service</a> to fetch the public ip at the first invocation of this method, then will be use an
     * instance instantiated at the first invocation.
     **/
    public String getHost(boolean publicAddress) throws IOException {
        if (publicAddress) {
            if (publicHostAddress == null) {
                APIRequest apiRequest = new APIRequest();
                apiRequest.sendAPIRequest("https://api.ipify.org", GET);
                publicHostAddress = apiRequest.getResponse();
            }
            return publicHostAddress;
        } else
            return currentHost;
    }

    /**
     * Method to get {@link #socket} instance <br>
     * Any params required
     *
     * @return {@link #socket} instance as {@link Socket}
     **/
    public Socket getActiveSocket() {
        return socket;
    }

    /**
     * Method to stop the communication <br>
     * Any params required
     *
     * @throws IOException when an error occurred during the closing of the communication
     **/
    public void closeCommunication() throws IOException {
        if (socket != null && !socket.isClosed())
            socket.close();
    }

    /**
     * This method is used to get the initialization vector <br>
     * Any params required
     *
     * @return initialization vector instance as {@link IvParameterSpec}
     **/
    public IvParameterSpec getIvParameterSpec() {
        if (cipher != null)
            return cipher.getIvParameterSpec();
        else
            return null;
    }

    /**
     * This method is used to get the initialization vector <br>
     * Any params required
     *
     * @return initialization vector instance as {@link String}
     **/
    public String getIvSpec() {
        if (cipher != null)
            return cipher.getStringIvParameterSpec();
        else
            return null;
    }

    /**
     * This method is used to get the secret key <br>
     * Any params required
     *
     * @return the secret key as {@link SecretKey}
     **/
    public SecretKey getOCipherKey() {
        if (cipher != null)
            return cipher.getSecretKey();
        else
            return null;
    }

    /**
     * This method is used to get the secret key <br>
     * Any params required
     *
     * @return the secret key as {@link String}
     **/
    public String getCipherKey() {
        if (cipher != null)
            return cipher.getStringSecretKey();
        else
            return null;
    }

    /**
     * Method to change the cipher keys during the runtime
     *
     * @param ivSpec:    initialization vector as {@link String}
     * @param secretKey: secret key as {@link String}
     * @throws Exception when an error occurred
     **/
    @Wrapper
    public void changeCipherKeys(String ivSpec, String secretKey) throws Exception {
        changeCipherKeys(createIvParameter(ivSpec), createSecretKey(secretKey));
    }

    /**
     * Method to change the cipher keys during the runtime
     *
     * @param ivSpec:    initialization vector as {@link String}
     * @param secretKey: secret key as {@link String}
     * @param algorithm: algorithm used by the {@link #cipher}
     * @throws Exception when an error occurred
     **/
    @Wrapper
    public void changeCipherKeys(String ivSpec, String secretKey, Algorithm algorithm) throws Exception {
        changeCipherKeys(createIvParameter(ivSpec), createSecretKey(secretKey), algorithm);
    }

    /**
     * Method to change the cipher keys during the runtime
     *
     * @param ivSpec:    initialization vector as {@link IvParameterSpec}
     * @param secretKey: secret key as {@link SecretKey}
     * @throws Exception when an error occurred
     **/
    @Wrapper
    public void changeCipherKeys(IvParameterSpec ivSpec, SecretKey secretKey) throws Exception {
        if (cipher != null)
            changeCipherKeys(ivSpec, secretKey, cipher.getAlgorithm());
        else
            throw new Exception("The cipher of the messages is not enabled, you must use the dedicated constructor first");
    }

    /**
     * Method to change the cipher keys during the runtime
     *
     * @param ivSpec:    initialization vector as {@link IvParameterSpec}
     * @param secretKey: secret key as {@link SecretKey}
     * @param algorithm: algorithm used by the {@link #cipher}
     * @throws Exception when an error occurred
     **/
    public void changeCipherKeys(IvParameterSpec ivSpec, SecretKey secretKey, Algorithm algorithm) throws Exception {
        if (cipher != null) {
            cipher.setIvParameterSpec(ivSpec);
            cipher.setSecretKey(secretKey);
            cipher.setAlgorithm(algorithm);
        } else
            throw new Exception("The cipher of the messages is not enabled, you must use the dedicated constructor first");
    }

    /**
     * Method to set the {@link #cipher} instance
     *
     * @param ivSpec:    initialization vector as {@link String}
     * @param secretKey: secret key as {@link String}
     * @param algorithm: algorithm used by the {@link #cipher}
     * @throws Exception when an error occurred
     * @apiNote this will enable the auto-cipher of the communication
     **/
    @Wrapper
    public void setCipher(String ivSpec, String secretKey, Algorithm algorithm) throws Exception {
        setCipher(createIvParameter(ivSpec), createSecretKey(secretKey), algorithm);
    }

    /**
     * Method to set the {@link #cipher} instance
     *
     * @param ivSpec:    initialization vector as {@link IvParameterSpec}
     * @param secretKey: secret key as {@link SecretKey}
     * @param algorithm: algorithm used by the {@link #cipher}
     * @throws Exception when an error occurred
     * @apiNote this will enable the auto-cipher of the communication
     **/
    @Wrapper
    public void setCipher(IvParameterSpec ivSpec, SecretKey secretKey, Algorithm algorithm) throws Exception {
        setCipher(new ClientCipher(ivSpec, secretKey, algorithm));
    }

    /**
     * Method to set the {@link #cipher} instance
     *
     * @param cipher: cipher to use
     * @apiNote this will enable the auto-cipher of the communication
     **/
    public void setCipher(ClientCipher cipher) {
        this.cipher = cipher;
    }

    /**
     * Method to get {@link #cipher} instance <br>
     * Any params required
     *
     * @return {@link #cipher} instance as {@link ClientCipher}
     **/
    public ClientCipher getCipher() {
        return cipher;
    }

    /**
     * This method is used to ping a host on a specific port and get whether is reachable
     *
     * @param timeout: custom timeout to wait for the ping
     * @return {@code "true"} if the host on the port inserted are available, {@code "false"} if is not reachable
     * @implNote to use this method you need to use this class as {@code "client"}, so with {@link #serverUse} set on
     * {@code "false"}, to use {@code "server-side"} you can use {@link #pingHost(String, int, int)} directly
     **/
    @Wrapper
    public boolean pingHost(int timeout) {
        if (!serverUse)
            return pingHost(currentHost, currentServerPort, timeout);
        else
            throw new IllegalStateException("You cannot use this method when this class is used server-side");
    }

    /**
     * {@code StandardResponseCode} list of available response status code
     **/
    public enum StandardResponseCode {

        /**
         * {@code SUCCESSFUL} when the communication ended successfully
         **/
        SUCCESSFUL(200),

        /**
         * {@code GENERIC_RESPONSE} when the communication ended with a generic error
         **/
        GENERIC_RESPONSE(300),

        /**
         * {@code NOT_FOUND} when the communication ended with a not found resource
         **/
        NOT_FOUND(404),

        /**
         * {@code FAILED} when the communication failed
         **/
        FAILED(500);

        /**
         * {@code code} of the response
         **/
        private final int code;

        /**
         * Constructor to init {@link StandardResponseCode}
         *
         * @param code: code of the response
         **/
        StandardResponseCode(int code) {
            this.code = code;
        }

        /**
         * Method to get {@link #code} instance <br>
         * Any params required
         *
         * @return {@link #code} instance as int
         **/
        public int getCode() {
            return code;
        }

    }

    /**
     * This method is used to get an ip address from a {@link Socket}
     *
     * @param socket: socket from fetch the ip
     * @return ip address as {@link String}
     **/
    public static String getIpAddress(Socket socket) {
        return ((InetSocketAddress) socket.getRemoteSocketAddress()).getAddress().getHostAddress();
    }

    /**
     * This method is used to ping a host on a specific port and get whether is reachable
     *
     * @param host:    host to ping
     * @param port:    port of the host to ping
     * @param timeout: custom timeout to wait for the ping
     * @return {@code "true"} if the host on the port inserted are available, {@code "false"} if is not reachable
     **/
    public static boolean pingHost(String host, int port, int timeout) {
        try {
            final Socket socket = new Socket();
            socket.connect(new InetSocketAddress(host, port), timeout);
            socket.close();
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    /**
     * Returns a string representation of the object <br>
     * Any params required
     *
     * @return a string representation of the object as {@link String}
     */
    @Override
    public String toString() {
        JSONObject msg = new JSONObject()
                .put("serverUse", serverUse)
                .put("executor", executor)
                .put("host", currentHost);
        if (serverUse) {
            msg.put("listeners", listeners)
                    .put("areAllowedMultipleListeners", allowMultipleListeners)
                    .put("activeServerSocket", serverSocket)
                    .put("activeSocket", socket);
        } else {
            msg.put("serverSocket", serverSocket)
                    .put("socket", socket)
                    .put("serverPort", currentServerPort);
        }
        return msg.toString();
    }

    /**
     * The {@code Listener} class is useful to manage dynamically the listeners when the {@link SocketManager#allowMultipleListeners}
     * is set to {@code "true"}, allowing the communication and, eventually, the interruption of the routine of the
     * listener
     *
     * @author N7ghtm4r3 - Tecknobit
     * @apiNote see the usage at <a href="https://github.com/N7ghtm4r3/APIManager/blob/main/documd/SocketManager.md">SocketManager.md</a>
     * @since 2.0.4
     **/
    public static class Listener {

        /**
         * {@code serverSocket} for the listener
         **/
        private final ServerSocket serverSocket;

        /**
         * {@code continueRoutine} whether continue the routine
         **/
        private volatile boolean continueRoutine;

        /**
         * Constructor to init {@link Listener}
         *
         * @param serverSocket:    server socket for the listener
         * @param continueRoutine: whether continue the routine
         **/
        public Listener(ServerSocket serverSocket, boolean continueRoutine) {
            this.serverSocket = serverSocket;
            this.continueRoutine = continueRoutine;
        }

        /**
         * Method to get {@link #serverSocket} instance <br>
         * Any params required
         *
         * @return {@link #serverSocket} instance as {@link ServerSocket}
         **/
        public ServerSocket getServerSocket() {
            return serverSocket;
        }

        /**
         * Method to get {@link #continueRoutine} instance <br>
         * Any params required
         *
         * @return {@link #continueRoutine} instance as boolean
         **/
        public boolean continueRoutine() {
            return continueRoutine;
        }

        /**
         * Method to stop the routine of the listener setting {@link #continueRoutine}
         * to {@code "false"} <br>
         * Any params required
         *
         * @apiNote the listener will be stopped and will refuse all the requests to this {@link #serverSocket}
         **/
        public void stopRoutine() {
            continueRoutine = false;
        }

    }

}