package com.tecknobit.apimanager.apis;

import com.tecknobit.apimanager.annotations.Wrapper;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
    private final ConcurrentHashMap<Integer, ServerSocket> listeners;

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
     * {@code currentServerPort} current server port used in the communication
     **/
    private int currentServerPort;

    /**
     * {@code currentHost} current server host used in the communication
     **/
    private String currentHost;

    /**
     * {@code socket} socket used in the communication
     **/
    private Socket socket;

    /**
     * Constructor to init {@link SocketManager}
     *
     * @param host:       server host used in the communication
     * @param serverPort: server port used in the communication
     * @apiNote this will set {@link #serverUse} to {@code "false"} and will be use as client side
     **/
    public SocketManager(String host, int serverPort) {
        serverUse = false;
        currentHost = host;
        currentServerPort = serverPort;
        allowMultipleListeners = false;
        listeners = null;
        executor = null;
    }

    /**
     * Constructor to init {@link SocketManager}
     *
     * @param allowMultipleListeners: whether accept multiple listeners at the same time
     * @apiNote this will set {@link #serverUse} to {@code "true"} and will be use as server side
     **/
    public SocketManager(boolean allowMultipleListeners) {
        serverUse = true;
        this.allowMultipleListeners = allowMultipleListeners;
        if (allowMultipleListeners) {
            executor = Executors.newCachedThreadPool();
            listeners = new ConcurrentHashMap<>();
        } else {
            executor = Executors.newFixedThreadPool(1);
            listeners = null;
        }
    }

    /**
     * Method to start a new listener
     *
     * @param port:    port to use by the listener
     * @param routine: routine to execute by the listener
     * @throws IOException when some errors have been occurred, for example same port for different listeners
     * @apiNote this method will be executed only if the {@link #serverUse} is set to {@code "true"}
     **/
    public void startListener(int port, Runnable routine) throws IOException {
        if (serverUse) {
            if (serverSocket == null || currentServerPort != port) {
                if (allowMultipleListeners) {
                    if (!listeners.containsKey(port))
                        listeners.put(port, new ServerSocket(port));
                    else
                        exit("You cannot have more listeners on the same port: [" + port + "]");
                } else {
                    if (serverSocket == null) {
                        serverSocket = new ServerSocket(port);
                        currentServerPort = port;
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
     **/
    @Wrapper(wrapper_of = "acceptRequest(int port)")
    public Socket acceptRequest() throws IOException {
        return acceptRequest(currentServerPort);
    }

    /**
     * Method to accept a new socket request
     *
     * @param port: port of the server socket where this request must be accepted and routed
     * @return socket accepted as {@link Socket}
     * @implNote this method is useful when {@link #allowMultipleListeners} is set to {@code "true"} to use the correct
     * server-socket instance
     * @apiNote this method will be executed only if the {@link #serverUse} is set to {@code "true"}
     **/
    public Socket acceptRequest(int port) throws IOException {
        if (serverUse) {
            if (allowMultipleListeners) {
                if (listeners.size() == 1)
                    port = listeners.keys().nextElement();
                serverSocket = listeners.get(port);
            }
            return socket = serverSocket.accept();
        }
        return null;
    }

    /**
     * Method to write a content message to send with the socket request
     *
     * @param content: content message to send
     * @apiNote will be accepted any objects, but will be called their {@code "toString()"}'s method to be sent
     **/
    @Wrapper(wrapper_of = "writeContentTo(String host, int port, T content)")
    public <T> void writeContent(T content) throws IOException {
        writeContentTo(currentHost, currentServerPort, content);
    }

    /**
     * Method to write a content message to send with the socket request
     *
     * @param content: content message to send
     * @param port:    port of the server socket where this request must be accepted and routed
     * @apiNote will be accepted any objects, but will be called their {@code "toString()"}'s method to be sent
     **/
    @Wrapper(wrapper_of = "writeContentTo(String host, int port, T content)")
    public <T> void writeContentTo(int port, T content) throws IOException {
        writeContentTo(currentHost, port, content);
    }

    /**
     * Method to write a content message to send with the socket request
     *
     * @param host:    host of the server socket where this request must be accepted and routed
     * @param content: content message to send
     * @apiNote will be accepted any objects, but will be called their {@code "toString()"}'s method to be sent
     **/
    @Wrapper(wrapper_of = "writeContentTo(String host, int port, T content)")
    public <T> void writeContentTo(String host, T content) throws IOException {
        writeContentTo(host, currentServerPort, content);
    }

    /**
     * Method to write a content message to send with the socket request
     *
     * @param host:    host of the server socket where this request must be accepted and routed
     * @param content: content message to send
     * @param port:    port of the server socket where this request must be accepted and routed
     * @apiNote will be accepted any objects, but will be called their {@code "toString()"}'s method to be sent
     **/
    @Wrapper(wrapper_of = "writeContentTo(Socket targetSocket, T content)")
    public <T> void writeContentTo(String host, int port, T content) throws IOException {
        if (!serverUse)
            socket = new Socket(host, port);
        writeContentTo(socket, content);
    }

    /**
     * Method to write a content message to send with the socket request
     *
     * @param targetSocket: target socket to use to send the content message
     * @param content:      content message to send
     * @apiNote will be accepted any objects, but will be called their {@code "toString()"}'s method to be sent
     **/
    @Wrapper(wrapper_of = "writeContentTo(Socket targetSocket, T content)")
    public <T> void writeContentTo(Socket targetSocket, T content) throws IOException {
        PrintWriter printWriter = new PrintWriter(targetSocket.getOutputStream(), true);
        String message = content.toString();
        if (message.contains(NEW_LINE_REPLACER)) {
            socket.close();
            exit("\"@-/-/-@\" is a reserved char, please do not insert it");
        }
        printWriter.println(message.replaceAll("\n", NEW_LINE_REPLACER));
    }

    /**
     * Method to read a content message received with the socket request <br>
     * Any params required
     *
     * @return content message received as {@link String}
     **/
    @Wrapper(wrapper_of = "readContent(Socket targetSocket)")
    public String readContent() throws IOException {
        return readContent(socket);
    }

    /**
     * Method to read a content message received with the socket request
     *
     * @param targetSocket: target socket to use to receive the content message
     * @return content message received as {@link String}
     **/
    public String readContent(Socket targetSocket) throws IOException {
        String content = new BufferedReader(new InputStreamReader(targetSocket.getInputStream())).readLine();
        if (!serverUse || content == null)
            targetSocket.close();
        if (content != null)
            content = content.replaceAll(NEW_LINE_REPLACER, "\n");
        return content;
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
     * @return {@link #listeners} instance as {@link ConcurrentHashMap} of {@link ServerSocket}
     **/
    public ConcurrentHashMap<Integer, ServerSocket> getListeners() {
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
     * Method to get {@link #currentHost} instance <br>
     * Any params required
     *
     * @return {@link #currentHost} instance as {@link String}
     **/
    public String getHost() {
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

}